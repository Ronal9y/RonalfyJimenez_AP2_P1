package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun HuacalEditScreen(
    idEntrada: Int?,
    goBack: () -> Unit
) {
    val vm: HuacalViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        vm.onEvent(HuacalEvent.ClearMessages)
        if (idEntrada != null && idEntrada > 0 && state.id == null)
            vm.onEvent(HuacalEvent.Select(idEntrada))
    }

    val success = state.successMessage
    LaunchedEffect(success) {
        if (success != null) {
            delay(800)
            vm.onEvent(HuacalEvent.ClearMessages)
            goBack()
        }
    }

    HuacalScreen(idEntrada = idEntrada, goBack = goBack)
}