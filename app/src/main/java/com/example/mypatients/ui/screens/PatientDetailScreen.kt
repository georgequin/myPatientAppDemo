package com.example.mypatients.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypatients.ui.vm.PatientDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientDetailScreen(
    id: Long,
    onBack: () -> Unit,
    vm: PatientDetailViewModel = hiltViewModel()
) {
    val patient by vm.patient(id).collectAsState(initial = null)
    Scaffold(topBar = { TopAppBar(title = { Text("Patient Details") }) }) { pad ->
        patient?.let { p ->
            Column(Modifier.padding(pad).padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(p.name, style = MaterialTheme.typography.headlineSmall)
                Text("Age: ${p.age}")
                Text("Gender: ${p.gender}")
                Text("Phone: ${p.phone}")
                Text("Condition: ${p.condition}")
                Button(onClick = onBack) { Text("Back") }
            }
        } ?: Box(Modifier.padding(pad).fillMaxSize()) { Text("Loading...") }
    }
}
