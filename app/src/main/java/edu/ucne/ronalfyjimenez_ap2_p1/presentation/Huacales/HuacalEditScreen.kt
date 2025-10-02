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
    val viewModel: HuacalViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    var shouldNavigateBack by remember { mutableStateOf(false) }

    LaunchedEffect(idEntrada) {
        if (idEntrada != null && idEntrada > 0 && viewModel.state.value.id == null) {
            viewModel.onEvent(HuacalEvent.Select(idEntrada))
        }
    }

    val success by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(success.successMessage) {
        success.successMessage?.let {
            delay(800)
            viewModel.onEvent(HuacalEvent.ClearForm)
            goBack()
        }
    }

    LaunchedEffect(shouldNavigateBack) {
        if (shouldNavigateBack) {
            goBack()
        }
    }

    HuacalScreen(
        idEntrada = idEntrada,
        goBack = {

            goBack()
        }
    )
}