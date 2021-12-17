package com.example.clock.business.datasources.alarm

import androidx.room.TypeConverter
import com.example.clock.business.model.WeekDays
import com.google.gson.Gson


class Converter {

    @TypeConverter
    fun listToJson(value: List<WeekDays>?) = Gson().toJson(value)


    @TypeConverter
    fun JsonToList(value: String) = Gson().fromJson(value, Array<WeekDays>::class.java).toList()
}