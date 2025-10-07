package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuacalScreen(
    idEntrada: Int? = null,
    goBack: () -> Unit
) {
    val viewModel: HuacalViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (idEntrada != null && idEntrada > 0 && state.value.id == null) {
            viewModel.onEvent(HuacalEvent.Select(idEntrada))
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (idEntrada == null) "Nuevo Huacal" else "Editar Huacal",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = goBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            OutlinedTextField(
                value = state.value.fecha,
                onValueChange = { viewModel.onEvent(HuacalEvent.FechaChange(it)) },
                label = { Text("Fecha (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "Fecha")
                },
                placeholder = { Text("Ej: ${LocalDate.now()}") },
                isError = state.value.fecha.isBlank() && state.value.errorMessage != null
            )

            OutlinedTextField(
                value = state.value.cliente,
                onValueChange = { viewModel.onEvent(HuacalEvent.ClienteChange(it)) },
                label = { Text("Nombre del Cliente") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = state.value.cliente.isBlank() && state.value.errorMessage != null
            )

            OutlinedTextField(
                value = state.value.cantidad,
                onValueChange = { viewModel.onEvent(HuacalEvent.CantidadChange(it)) },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                singleLine = true,
                isError = state.value.cantidad.isBlank() && state.value.errorMessage != null
            )

            OutlinedTextField(
                value = state.value.precio,
                onValueChange = { viewModel.onEvent(HuacalEvent.PrecioChange(it)) },
                label = { Text("Precio Unitario") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                isError = state.value.precio.isBlank() && state.value.errorMessage != null
            )

            if (state.value.cantidad.isNotBlank() && state.value.precio.isNotBlank()) {
                val cantidad = state.value.cantidad.toIntOrNull() ?: 0
                val precio = state.value.precio.toDoubleOrNull() ?: 0.0
                val total = cantidad * precio

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Total Calculado:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "$${String.format("%.2f", total)}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "$cantidad unidades × $${String.format("%.2f", precio)} c/u",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Button(
                onClick = {
                    viewModel.onEvent(HuacalEvent.Save)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                enabled = state.value.fecha.isNotBlank() &&
                        state.value.cliente.isNotBlank() &&
                        state.value.cantidad.isNotBlank() &&
                        state.value.precio.isNotBlank()
            ) {
                Icon(Icons.Default.Save, contentDescription = "Guardar")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar")
            }

            state.value.successMessage?.let { message ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Green.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "✓ Éxito",
                            color = Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = message,
                            color = Color.Green,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Redirigiendo a la lista...",
                            color = Color.Green,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            state.value.errorMessage?.let { message ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red.copy(alpha = 0.1f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "✗ Error",
                            color = Color.Red,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = message,
                            color = Color.Red,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}