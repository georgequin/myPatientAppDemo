package com.example.mypatients.data.local

import androidx.room.TypeConverter
import com.example.mypatients.data.model.Gender

class Converters {
    @TypeConverter fun toGender(v: String): Gender = Gender.valueOf(v)
    @TypeConverter fun fromGender(g: Gender): String = g.name
}
