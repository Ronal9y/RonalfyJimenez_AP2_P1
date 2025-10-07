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
import javax.inject.Inject

@HiltViewModel
class HuacalListViewModel @Inject constructor(
    private val repo: HuacalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HuacalListState())
    val state: StateFlow<HuacalListState> = _state.asStateFlow()

    init {
        loadHuacales()
    }

    private fun loadHuacales() {
        viewModelScope.launch {
            repo.observeAll().collect { lista ->
                _state.update { it.copy(lista = lista) }
            }
        }
    }

    fun onEvent(e: HuacalListEvent) {
        when (e) {
            is HuacalListEvent.Filter -> {
                viewModelScope.launch {
                    repo.observeFilteredGeneral(e.query ?: "").collect { lista ->
                        _state.update { it.copy(lista = lista) }
                    }
                }
            }
        }
    }
}

data class HuacalListState(
    val lista: List<HuacalEntity> = emptyList()
)

sealed interface HuacalListEvent {
    data class Filter(val query: String?) : HuacalListEvent
}