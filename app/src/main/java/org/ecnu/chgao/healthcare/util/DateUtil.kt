package org.ecnu.chgao.healthcare.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by chgao on 17-6-13.
 */
class DateUtil

fun dateDetail(date: Date, year: Boolean = false, month: Boolean = true, day: Boolean = true, hour: Boolean = true, min: Boolean = true, second: Boolean = false): String? {
    val formatPatten = StringBuilder()
    if (year)
        formatPatten.append("yyyy年")
    if (month)
        formatPatten.append("MM月")
    if (day)
        formatPatten.append("dd日")
    if (hour) {
        if (formatPatten.isNotEmpty())
            formatPatten.append(" ")
        formatPatten.append("HH:")
    }
    if (min)
        formatPatten.append("mm")
    if (second)
        formatPatten.append("ss")

    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat(formatPatten.toString())
    return dateFormat.format(date)
}

fun getTodayDate(): String {
    val date = Date(System.currentTimeMillis())
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)
}

fun time2DateString(time: Long): String {
    val date = Date(time)
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(date)
}