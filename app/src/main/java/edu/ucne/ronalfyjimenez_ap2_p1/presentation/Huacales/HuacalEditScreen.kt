package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HuacalEditScreen(
    idEntrada: Int?,
    goBack: () -> Unit
) {
    val viewModel: HuacalViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(idEntrada) {
        if (idEntrada != null) {
            viewModel.onEvent(HuacalEvent.Select(idEntrada))
        }
    }

    LaunchedEffect(state.value.successMessage) {
        if (state.value.successMessage != null) {
            kotlinx.coroutines.delay(1000)
            goBack()
        }
    }

    HuacalScreen(
        idEntrada = idEntrada,
        goBack = goBack
    )
}