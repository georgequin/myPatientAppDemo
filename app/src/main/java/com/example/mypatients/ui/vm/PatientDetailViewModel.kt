package com.example.mypatients.ui.vm

import androidx.lifecycle.ViewModel
import com.example.mypatients.data.PatientRepository
import com.example.mypatients.data.local.PatientEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PatientDetailViewModel @Inject constructor(
    private val repo: PatientRepository
) : ViewModel() {
    fun patient(id: Long): Flow<PatientEntity?> = repo.observePatient(id)
}
