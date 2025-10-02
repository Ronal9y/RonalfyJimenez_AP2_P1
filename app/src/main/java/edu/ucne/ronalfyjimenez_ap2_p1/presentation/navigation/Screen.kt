package edu.ucne.ronalfyjimenez_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screen {
        @Serializable
        data object List : Screen()

        @Serializable
        data class Edit(val idEntrada: Int?) : Screen()

        @Serializable
        data class Delete(val idEntrada: Int) : Screen()

        @Serializable
        data object Register : Screen()
}