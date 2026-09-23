package com.micarroaldia.app.data

import java.util.Calendar
import java.util.TimeZone

data class SimpleDate(val year: Int, val month: Int, val day: Int) : Comparable<SimpleDate> {

    override fun compareTo(other: SimpleDate): Int =
        compareValuesBy(this, other, { it.year }, { it.month }, { it.day })

    fun format(separator: String = "/"): String =
        "%02d%s%02d%s%04d".format(day, separator, month, separator, year)

    fun plusDays(days: Int): SimpleDate = toCalendar().apply { add(Calendar.DAY_OF_MONTH, days) }.toSimpleDate()

    fun daysUntil(other: SimpleDate): Int =
        ((other.toCalendar().timeInMillis - toCalendar().timeInMillis) / MILLIS_PER_DAY).toInt()

    private fun toCalendar(): Calendar = utcCalendar().apply {
        clear()
        set(year, month - 1, day)
    }

    companion object {
        private const val MILLIS_PER_DAY = 24 * 60 * 60 * 1000L

        fun today(): SimpleDate = Calendar.getInstance().toSimpleDate()

        fun daysInMonth(year: Int, month: Int): Int = utcCalendar().apply {
            clear()
            set(year, month - 1, 1)
        }.getActualMaximum(Calendar.DAY_OF_MONTH)

        fun firstWeekdayOfMonth(year: Int, month: Int): Int = utcCalendar().apply {
            clear()
            set(year, month - 1, 1)
        }.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY

        private fun utcCalendar(): Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        private fun Calendar.toSimpleDate() =
            SimpleDate(get(Calendar.YEAR), get(Calendar.MONTH) + 1, get(Calendar.DAY_OF_MONTH))
    }
}
