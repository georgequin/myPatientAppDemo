package com.example.mypatients.data

import com.example.mypatients.data.local.PatientDao
import com.example.mypatients.data.local.PatientEntity
import com.example.mypatients.data.remote.ApiService
import com.example.mypatients.data.remote.PostPayload
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PatientRepository {
    fun observePatients(query: String?): Flow<List<PatientEntity>>
    fun observePatient(id: Long): Flow<PatientEntity?>
    suspend fun upsert(p: PatientEntity): Long
    suspend fun delete(p: PatientEntity)
    suspend fun syncUnsynced(): Pair<Int, Int>
}

class DefaultPatientRepository @Inject constructor(
    private val dao: PatientDao,
    private val api: ApiService
) : PatientRepository {

    override fun observePatients(query: String?) =
        (if (query.isNullOrBlank()) dao.observeAll() else dao.search(query))

    override fun observePatient(id: Long) = dao.observeById(id)

    override suspend fun upsert(p: PatientEntity): Long =
        dao.upsert(p.copy(updatedAt = System.currentTimeMillis()))

    override suspend fun delete(p: PatientEntity) = dao.delete(p)

    override suspend fun syncUnsynced(): Pair<Int, Int> {
        val items = dao.listAll()
        var ok = 0; var fail = 0
        for (p in items) {
            val payload = PostPayload(
                title = p.name,
                body = "Age: ${p.age}, Gender: ${p.gender}, Phone: ${p.phone}, Condition: ${p.condition}"
            )
            runCatching { api.postPatient(payload) }
                .onSuccess { resp -> if (resp.isSuccessful) ok++ else fail++ }
                .onFailure { fail++ }
        }
        return ok to fail
    }
}