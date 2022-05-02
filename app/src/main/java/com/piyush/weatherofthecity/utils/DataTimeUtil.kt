package com.piyush.weatherofthecity.utils

import java.text.SimpleDateFormat
import java.util.*

object DataTimeUtil {

    fun convertEpochTimeToDateString(format: String, epochTime: Long): String{
        val simpleDateFormat = SimpleDateFormat(format, Locale.getDefault())
        return simpleDateFormat.format(Date(epochTime * 1000))
    }
}