package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity
import java.time.LocalDate

data class HuacalUiState(
    val id: Int? = null,
    val fecha: String = "",
    val cliente: String = "",
    val cantidad: String = "",
    val precio: String = "",
    val mensaje: String? = null,
    val errorMessage: String? = null,
    val successMessage: String? = null
)