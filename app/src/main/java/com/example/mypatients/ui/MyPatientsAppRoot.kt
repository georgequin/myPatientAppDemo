package com.example.mypatients.ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.mypatients.ui.screens.EditPatientScreen
import com.example.mypatients.ui.screens.PatientDetailScreen
import com.example.mypatients.ui.screens.PatientListScreen

@Composable
fun MyPatientsAppRoot() {
    val nav = rememberNavController()
    MaterialTheme {
        NavHost(navController = nav, startDestination = "patients") {
            composable("patients") {
                PatientListScreen(
                    onAdd = { nav.navigate("edit") },
                    onOpen = { id -> nav.navigate("detail/$id") },
                    onEdit = { id -> nav.navigate("edit?id=$id") }
                )
            }
            composable(
                "edit?id={id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType; defaultValue = -1L })
            ) {
                val id = it.arguments?.getLong("id")?.takeIf { v -> v != -1L }
                EditPatientScreen(
                    editId = id,
                    onDone = { nav.popBackStack() }
                )
            }
            composable(
                "detail/{id}",
                arguments = listOf(navArgument("id") { type = NavType.LongType })
            ) {
                val id = it.arguments!!.getLong("id")
                PatientDetailScreen(id = id, onBack = { nav.popBackStack() })
            }
        }
    }
}
