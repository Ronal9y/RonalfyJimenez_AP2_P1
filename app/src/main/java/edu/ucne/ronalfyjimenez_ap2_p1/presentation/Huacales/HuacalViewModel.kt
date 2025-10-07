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

        if (_state.value.id == null) {
            _state.update { it.copy(fecha = LocalDate.now().toString()) }
        }
    }

    fun onEvent(e: HuacalEvent) {
        when (e) {
            is HuacalEvent.FechaChange -> {
                _state.update { it.copy(fecha = e.v) }
            }
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
                                fecha = h.fecha,
                                cliente = h.nombreCliente,
                                cantidad = h.cantidad.toString(),
                                precio = h.precio.toString()
                            )
                        }
                    }
                }
            }
            HuacalEvent.Save -> {
                viewModelScope.launch {
                    if (_state.value.fecha.isBlank() || _state.value.cliente.isBlank() ||
                        _state.value.cantidad.isBlank() || _state.value.precio.isBlank()) {
                        _state.update {
                            it.copy(
                                errorMessage = "Todos los campos son requeridos",
                                successMessage = null
                            )
                        }
                        return@launch
                    }

                    val fecha = try {
                        LocalDate.parse(_state.value.fecha)
                    } catch (e: Exception) {
                        _state.update {
                            it.copy(
                                errorMessage = "Formato de fecha inválido. Use YYYY-MM-DD",
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

                    val cliente = _state.value.cliente.trim()
                    val idActual = _state.value.id ?: 0

                    if (repo.existeNombre(cliente, idActual)) {
                        _state.update {
                            it.copy(
                                errorMessage = "Ya existe un registro con ese nombre de cliente",
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
                                successMessage = if (_state.value.id == null)
                                    "Huacal guardado exitosamente"
                                else
                                    "Huacal actualizado exitosamente",
                                errorMessage = null
                            )
                        }
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
            HuacalEvent.ClearMessages -> {
                _state.update {
                    it.copy(
                        errorMessage = null,
                        successMessage = null
                    )
                }
            }
            HuacalEvent.ClearForm -> {
                _state.update {
                    HuacalUiState(fecha = LocalDate.now().toString())
                }
            }
        }
    }

    private fun limpiarFormulario() {
        _state.update {
            it.copy(
                id = null,
                fecha = LocalDate.now().toString(),
                cliente = "",
                cantidad = "",
                precio = "",
                errorMessage = null
            )
        }
    }
}