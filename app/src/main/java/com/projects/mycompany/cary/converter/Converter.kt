package com.projects.mycompany.cary.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.Serializable
import java.util.*


class Converter:Serializable {

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)!!

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun fromTimeStamp(value: Long?) =  value?.let { Date(it) }

    @TypeConverter
    fun toTimeStamp(date: Date?) = date?.time
}