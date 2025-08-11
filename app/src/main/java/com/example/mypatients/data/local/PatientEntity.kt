package com.example.mypatients.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mypatients.data.model.Gender

@Entity(tableName = "patients")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val age: Int,
    val gender: Gender,
    val phone: String,
    val condition: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)
