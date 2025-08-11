package com.example.mypatients.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypatients.data.model.Gender
import com.example.mypatients.ui.vm.PatientEditViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPatientScreen(
    editId: Long?,
    onDone: () -> Unit,
    vm: PatientEditViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.Male) }
    var phone by remember { mutableStateOf("") }
    var condition by remember { mutableStateOf("") }
    val saving by vm.saving.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (editId == null) "Add Patient" else "Edit Patient") }
            )
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(Gender.Male, Gender.Female, Gender.Other).forEach { g ->
                    FilterChip(
                        selected = gender == g,
                        onClick = { gender = g },
                        label = { Text(g.name) }
                    )
                }
            }

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = condition,
                onValueChange = { condition = it },
                label = { Text("Medical condition") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                enabled = !saving && name.isNotBlank() && (age.toIntOrNull() ?: -1) >= 0,
                onClick = {
                    vm.save(
                        id = editId,
                        name = name,
                        age = age.toIntOrNull() ?: 0,
                        gender = gender,
                        phone = phone,
                        condition = condition
                    ) { onDone() }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                if (saving) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Saving...")
                } else {
                    Text("Save")
                }
            }
        }
    }
}
