package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HuacalDeleteScreen(
    idEntrada: Int,
    goBack: () -> Unit
) {
    val viewModel: HuacalViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(idEntrada) {
        viewModel.onEvent(HuacalEvent.Select(idEntrada))
    }

    AlertDialog(
        onDismissRequest = goBack,
        title = { Text("Confirmar eliminación") },
        text = {
            Text("¿Eliminar entrada de ${state.cliente} (${state.cantidad} huacales)?")
        },
        confirmButton = {
            TextButton(
                onClick = {
                    viewModel.onEvent(HuacalEvent.Delete)
                    goBack()
                }
            ) {
                Text("Eliminar", color = androidx.compose.material3.MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = goBack) {
                Text("Cancelar")
            }
        }
    )
}