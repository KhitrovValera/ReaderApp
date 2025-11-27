package com.example.readerapp.data.local.common

import androidx.room.TypeConverter

class ListIntConverter {
    @TypeConverter
    fun fromListInt(list: List<Int>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toListInt(data: String): List<Int> {
        return if (data.isEmpty()) emptyList()
        else data.split(",").mapNotNull { it.toIntOrNull() }
    }
}