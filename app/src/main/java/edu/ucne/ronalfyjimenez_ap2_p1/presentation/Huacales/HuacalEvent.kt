package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

sealed interface HuacalEvent {
    data class ClienteChange(val v: String) : HuacalEvent
    data class CantidadChange(val v: String) : HuacalEvent
    data class PrecioChange(val v: String) : HuacalEvent
    data object Save : HuacalEvent
    data object Delete : HuacalEvent
    data class Select(val id: Int) : HuacalEvent
    data class Filter(val cliente: String?) : HuacalEvent
    data object ClearMessages : HuacalEvent
}