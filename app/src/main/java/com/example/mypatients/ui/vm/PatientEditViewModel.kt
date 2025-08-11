package com.example.mypatients.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypatients.data.PatientRepository
import com.example.mypatients.data.local.PatientEntity
import com.example.mypatients.data.model.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientEditViewModel @Inject constructor(
    private val repo: PatientRepository
) : ViewModel() {

    private val _saving = MutableStateFlow(false)
    val saving = _saving.asStateFlow()

    fun save(
        id: Long?,
        name: String,
        age: Int,
        gender: Gender,
        phone: String,
        condition: String,
        onSaved: (Long) -> Unit
    ) = viewModelScope.launch {
        _saving.emit(true)
        val pid = repo.upsert(
            PatientEntity(
                id = id ?: 0,
                name = name.trim(),
                age = age,
                gender = gender,
                phone = phone.trim(),
                condition = condition.trim()
            )
        )
        _saving.emit(false)
        onSaved(pid)
    }
}
