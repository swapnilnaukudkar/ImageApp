package com.swapnil.imageapp.utils

import java.text.SimpleDateFormat
import java.util.*


object utils {

    fun validate(date: String): Boolean {
        return if (date.length == 10) {
            val dateStr = date.split("-")
            if (dateStr.size == 3) {
                val day: String = dateStr[0]
                val month: String = dateStr[1]
                val year: Int = dateStr[2].toInt()
                if (day == "31" &&
                    (month == "4" || month == "6" || month == "9" || month == "11" || month == "04" || month == "06" || month == "09")
                ) {
                    false // only 1,3,5,7,8,10,12 has 31 days
                } else if (month == "2" || month == "02") {
                    //leap year
                    if (year % 4 == 0) {
                        !(day == "30" || day == "31")
                    } else {
                        !(day == "29" || day == "30" || day == "31")
                    }
                } else {
                    true
                }
            } else {
                false
            }
        } else {
            false
        }
    }

    fun dateConverter(date: String): String {
        val date = SimpleDateFormat("dd-MM-yyyy").parse(date) as Date
        return SimpleDateFormat("yyyy-MM-dd").format(date)
    }
}