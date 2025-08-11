package com.example.mypatients.ui.components

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

@Composable
fun AppSnackbar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState)
}