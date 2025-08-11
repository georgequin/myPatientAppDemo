package com.example.mypatients.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM patients ORDER BY name ASC")
    fun observeAll(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE name LIKE '%' || :q || '%' ORDER BY name ASC")
    fun search(q: String): Flow<List<PatientEntity>>

    @Query("SELECT * FROM patients WHERE id = :id")
    fun observeById(id: Long): Flow<PatientEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: PatientEntity): Long

    @Delete
    suspend fun delete(entity: PatientEntity)

    @Query("SELECT * FROM patients ORDER BY name ASC")
    suspend fun listAll(): List<PatientEntity>
}
