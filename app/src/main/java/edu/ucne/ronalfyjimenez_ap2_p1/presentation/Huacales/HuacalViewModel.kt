package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity
import edu.ucne.ronalfyjimenez_ap2_p1.data.repository.HuacalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HuacalViewModel @Inject constructor(
    private val repo: HuacalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HuacalUiState())
    val state: StateFlow<HuacalUiState> = _state.asStateFlow()

    init {

        observeHuacales()
    }

    private fun observeHuacales() {
        viewModelScope.launch {
            repo.observeAll().collect { lista ->
                _state.update { it.copy(lista = lista) }
            }
        }
    }

    fun onEvent(e: HuacalEvent) {
        when (e) {
            is HuacalEvent.ClienteChange -> {
                _state.update { it.copy(cliente = e.v) }
            }
            is HuacalEvent.CantidadChange -> {
                _state.update { it.copy(cantidad = e.v) }
            }
            is HuacalEvent.PrecioChange -> {
                _state.update { it.copy(precio = e.v) }
            }
            is HuacalEvent.Select -> {
                viewModelScope.launch {
                    val huacal = repo.getById(e.id)
                    huacal?.let { h ->
                        _state.update {
                            it.copy(
                                id = h.idEntrada,
                                cliente = h.nombreCliente,
                                cantidad = h.cantidad.toString(),
                                precio = h.precio.toString(),
                                fecha = h.fecha
                            )
                        }
                    }
                }
            }
            HuacalEvent.Save -> {
                viewModelScope.launch {
                    if (_state.value.cliente.isBlank() || _state.value.cantidad.isBlank() || _state.value.precio.isBlank()) {
                        _state.update {
                            it.copy(
                                errorMessage = "Todos los campos son requeridos",
                                successMessage = null
                            )
                        }
                        return@launch
                    }

                    val cantidad = _state.value.cantidad.toIntOrNull()
                    val precio = _state.value.precio.toDoubleOrNull()

                    if (cantidad == null || precio == null) {
                        _state.update {
                            it.copy(
                                errorMessage = "Cantidad y precio deben ser números válidos",
                                successMessage = null
                            )
                        }
                        return@launch
                    }

                    try {
                        val huacal = HuacalEntity(
                            idEntrada = _state.value.id ?: 0,
                            fecha = _state.value.fecha,
                            nombreCliente = _state.value.cliente,
                            cantidad = cantidad,
                            precio = precio
                        )
                        repo.save(huacal)
                        _state.update {
                            it.copy(
                                successMessage = if (_state.value.id == null) "Huacal guardado exitosamente" else "Huacal actualizado exitosamente",
                                errorMessage = null
                            )
                        }
                        limpiarFormulario()
                    } catch (ex: Exception) {
                        _state.update {
                            it.copy(
                                errorMessage = "Error al guardar: ${ex.message}",
                                successMessage = null
                            )
                        }
                    }
                }
            }
            HuacalEvent.Delete -> {
                viewModelScope.launch {
                    val id = _state.value.id ?: return@launch
                    try {
                        repo.deleteById(id)
                        _state.update {
                            it.copy(
                                successMessage = "Huacal eliminado exitosamente",
                                errorMessage = null
                            )
                        }
                        limpiarFormulario()
                    } catch (ex: Exception) {
                        _state.update {
                            it.copy(
                                errorMessage = "Error al eliminar: ${ex.message}",
                                successMessage = null
                            )
                        }
                    }
                }
            }
            is HuacalEvent.Filter -> {
                viewModelScope.launch {

                    repo.observeFiltered(
                        cliente = e.cliente,
                        fecha = null,
                        minCant = null,
                        maxCant = null
                    ).collect { listaFiltrada ->
                        _state.update { it.copy(lista = listaFiltrada) }
                    }
                }
            }
            HuacalEvent.ClearMessages -> {
                _state.update {
                    it.copy(
                        mensaje = null,
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }
        }
    }

    private fun limpiarFormulario() {
        _state.update {
            it.copy(
                id = null,
                cliente = "",
                cantidad = "",
                precio = "",
                fecha = java.time.LocalDate.now().toString()   // <-- String
            )
        }
    }
}