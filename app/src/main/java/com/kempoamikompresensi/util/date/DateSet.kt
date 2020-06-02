package com.kempoamikompresensi.util.date

import java.text.SimpleDateFormat
import java.util.*


object DateSet {

    fun getDateToday(): String {
        val date = SimpleDateFormat("yyyy-MM-dd")
        val today = date.format(Date())
        return today.toString()
    }

    fun selectedDate(date: String): String {
        val setting = date.substring(date.indexOf("-") + 1)
        return setting.substring(setting.lastIndexOf("-") + 1)
    }

    fun getFormatDateTimeNow(): String {
        val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return date.format(Date())
    }

    fun getTimestampNow(): Long {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val now = format.format(Date())
        val date: Date = format.parse(now)
        return date.time
    }

    fun getDatestampToday(): Long {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val now = format.format(Date())
        val date: Date = format.parse(now)
        return date.time
    }

    fun descripTimestamp(dateTime: Long): String {
        val date = Date(dateTime)
        val format = SimpleDateFormat("HH:mm:ss dd-MM-yyyy")
        return format.format(date)
    }

    fun descripTimestampToTime(dateTime: Long): String {
        val date = Date(dateTime)
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }

    fun descripTimestampToDate(dateTime: Long): String {
        val date = Date(dateTime)
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }


    fun selectedMonth(date: String): String {
        val dateOfMonth = date.substring(date.indexOf("-") + 1)
        return dateOfMonth.substring(0, dateOfMonth.indexOf("-"))
    }

    fun selectedYear(date: String): String {
        return date.substring(0, date.indexOf("-"))
    }

}