package edu.ucne.ronalfyjimenez_ap2_p1.presentation.Huacales

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuacalListScreen(
    onAdd: () -> Unit,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    val viewModel: HuacalListViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var filtroGlobal by remember { mutableStateOf("") }

    LaunchedEffect(filtroGlobal) {
        viewModel.onEvent(HuacalListEvent.Filter(filtroGlobal.takeIf { it.isNotBlank() }))
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Entradas de Huacales",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Huacal")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {

            OutlinedTextField(
                value = filtroGlobal,
                onValueChange = { filtroGlobal = it },
                label = { Text("Buscar...") },
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            val lista = state.lista
            val totalRegistros = lista.size
            val totalDinero = lista.sumOf { it.cantidad * it.precio }

            if (lista.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Sin datos",
                            tint = Color.Gray,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            "No hay entradas de huacales",
                            color = Color.Gray
                        )
                        Text(
                            "Presione el botón + para agregar una nueva entrada",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(lista) { huacal ->
                        HuacalRow(
                            h = huacal,
                            onEdit = { onEdit(huacal.idEntrada) },
                            onDelete = { onDelete(huacal.idEntrada) }
                        )
                    }
                }
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            totalRegistros.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text("Registros", fontSize = 12.sp)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "$${"%,.2f".format(totalDinero)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text("Total", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}
@Composable
private fun HuacalRow(
    h: edu.ucne.ronalfyjimenez_ap2_p1.data.local.entity.HuacalEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(h.nombreCliente, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("Fecha: ${h.fecha}")
                Text("Cantidad: ${h.cantidad} · Precio: $${h.precio}")
            }

            IconButton(onClick = { showMenu = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Opciones")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Editar") },
                    leadingIcon = { Icon(Icons.Default.Edit, null) },
                    onClick = { showMenu = false; onEdit() }
                )
                DropdownMenuItem(
                    text = { Text("Eliminar") },
                    leadingIcon = { Icon(Icons.Default.Delete, null) },
                    onClick = { showMenu = false; onDelete() }
                )
            }
        }
    }
}