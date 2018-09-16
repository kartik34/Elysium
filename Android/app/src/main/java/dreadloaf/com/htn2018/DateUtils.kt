package dreadloaf.com.htn2018

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateUtils {

    companion object {
        fun getTodayDateFormatted() : String{
            val dtf = DateTimeFormatter.ofPattern("HH:mm:ss")

            return LocalDate.now().toString() +" "+ LocalTime.now().format(dtf)

        }
    }


}