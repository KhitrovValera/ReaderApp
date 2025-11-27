package com.example.readerapp.data.local.common

import androidx.room.TypeConverter

class ListStringConverter {
    @TypeConverter
    fun fromListString(list: List<String>): String {
        return list.joinToString("|")
    }

    @TypeConverter
    fun toListString(data: String): List<String> {
        return if (data.isEmpty()) emptyList()
        else data.split("|")
    }
}