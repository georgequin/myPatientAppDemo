package com.example.mypatients.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mypatients.data.PatientRepository
import com.example.mypatients.data.local.PatientEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientListViewModel @Inject constructor(
    private val repo: PatientRepository
) : ViewModel() {

    private val query = MutableStateFlow("")

    val patients: StateFlow<List<PatientEntity>> = query
        .debounce(250)
        .flatMapLatest { q -> repo.observePatients(q.ifBlank { null }) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun setQuery(q: String) = viewModelScope.launch { query.emit(q) }

    fun delete(p: PatientEntity) = viewModelScope.launch { repo.delete(p) }

    fun sync(onDone: (ok: Int, fail: Int) -> Unit) = viewModelScope.launch {
        val (ok, fail) = repo.syncUnsynced()
        onDone(ok, fail)
    }
}
