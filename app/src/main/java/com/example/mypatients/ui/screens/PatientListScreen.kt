package com.example.mypatients.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypatients.data.local.PatientEntity
import com.example.mypatients.ui.vm.PatientListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    onAdd: () -> Unit,
    onOpen: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    vm: PatientListViewModel = hiltViewModel()
) {
    val list by vm.patients.collectAsState()
    var query by rememberSaveable(stateSaver = TextFieldValue.Saver) { mutableStateOf(TextFieldValue("")) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var syncing by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MyPatients") },
                actions = {
                    // Sync action with tiny progress feedback
                    TextButton(
                        enabled = !syncing,
                        onClick = {
                            syncing = true
                            vm.sync { ok, fail ->
                                syncing = false
                                scope.launch {
                                    snackbarHostState.showSnackbar("Synced: $ok, Failed: $fail")
                                }
                            }
                        }
                    ) {
                        if (syncing) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(16.dp)
                                    .padding(end = 8.dp),
                                strokeWidth = 2.dp
                            )
                        }
                        Text("Sync")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add patient") },
                icon = { Icon(Icons.Default.Add, contentDescription = "Add") },
                onClick = onAdd
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxSize()
        ) {
            // Search
            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    vm.setQuery(it.text)
                },
                label = { Text("Search by name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // List / Empty state
            if (list.isEmpty()) {
                EmptyState(
                    title = if (query.text.isBlank()) "No patients yet" else "No matches",
                    subtitle = if (query.text.isBlank())
                        "Tap “Add patient” to create your first record."
                    else
                        "Try a different name or clear the search."
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(list, key = { it.id }) { p ->
                        PatientRow(
                            p = p,
                            onOpen = onOpen,
                            onEdit = onEdit,
                            onDelete = { vm.delete(p) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(title: String, subtitle: String) {
    Surface(
        tonalElevation = 2.dp,
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {
        Column(Modifier.padding(20.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = LocalContentColor.current.copy(alpha = 0.75f))
        }
    }
}

@Composable
private fun PatientRow(
    p: PatientEntity,
    onOpen: (Long) -> Unit,
    onEdit: (Long) -> Unit,
    onDelete: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    var confirmDelete by remember { mutableStateOf(false) }

    ElevatedCard(
        onClick = { onOpen(p.id) },
        shape = MaterialTheme.shapes.large
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(Modifier.weight(1f)) {
                    Text(p.name, style = MaterialTheme.typography.titleMedium)
                    Spacer(Modifier.height(2.dp))
                    Text(
                        "${p.gender} • ${p.age} yrs • ${p.phone}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Box {
                    TextButton(onClick = { showMenu = true }) { Text("More") }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("Edit") },
                            onClick = { showMenu = false; onEdit(p.id) }
                        )
                        DropdownMenuItem(
                            text = { Text("Delete") },
                            onClick = { showMenu = false; confirmDelete = true }
                        )
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(p.condition, style = MaterialTheme.typography.bodySmall, color = LocalContentColor.current.copy(alpha = 0.85f))
        }
    }

    if (confirmDelete) {
        AlertDialog(
            onDismissRequest = { confirmDelete = false },
            title = { Text("Delete patient") },
            text = { Text("Are you sure you want to delete ${p.name}? This cannot be undone.") },
            confirmButton = {
                TextButton(onClick = { confirmDelete = false; onDelete() }) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { confirmDelete = false }) { Text("Cancel") }
            }
        )
    }
}
