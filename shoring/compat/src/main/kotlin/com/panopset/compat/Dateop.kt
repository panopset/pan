package com.panopset.compat

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

object Dateop {
    var ZONE_ID = ZoneId.of("America/Los_Angeles")
    fun show(date: LocalDate?): String {
        return YMD_FMT.format(date)
    }

    fun parse(source: String): LocalDate {
        return LocalDate.parse(source.trim { it <= ' ' }, YMD_FMT)
    }

    private val YMD_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    private fun fill2(value: Int): String {
        return if (value < 10) "0$value" else "" + value
    }

    fun createDateKey(ld: LocalDate): String {
        return createDateKey(ld.year, ld.monthValue, ld.dayOfMonth)
    }

    fun createMonthKey(ld: LocalDate): String {
        return createMonthKey(ld.year, ld.monthValue)
    }

    private fun createDateKey(yr: Int, mn: Int, dy: Int): String {
        return String.format("%s-%s", createMonthKey(yr, mn), fill2(dy))
    }

    private fun createMonthKey(yr: Int, mn: Int): String {
        return String.format("%d-%s", yr, fill2(mn))
    }

    fun toPacific(oldest: LocalDate): Date {
        // http://stackoverflow.com/questions/33066904
        return Date.from(oldest.atStartOfDay(ZONE_ID).toInstant())
    }

    fun toLocalDate(date: Date): LocalDate {
        // http://stackoverflow.com/questions/21242110
        return Instant.ofEpochMilli(date.time).atZone(ZONE_ID).toLocalDate()
    }
}
