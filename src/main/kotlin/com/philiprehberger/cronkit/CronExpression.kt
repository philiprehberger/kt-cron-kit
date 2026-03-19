package com.philiprehberger.cronkit

import java.time.*

/** A parsed cron expression with scheduling methods. */
public class CronExpression internal constructor(
    internal val minutes: Set<Int>,
    internal val hours: Set<Int>,
    internal val daysOfMonth: Set<Int>,
    internal val months: Set<Int>,
    internal val daysOfWeek: Set<Int>,
) {
    /** Find the next matching time after [instant] (UTC). */
    public fun nextAfter(instant: Instant): Instant {
        var dt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).plusMinutes(1).withSecond(0).withNano(0)
        for (i in 0 until 525960) { // max ~1 year of minutes
            if (matchesDateTime(dt)) return dt.toInstant(ZoneOffset.UTC)
            dt = dt.plusMinutes(1)
        }
        throw IllegalStateException("No matching time found within 1 year")
    }

    /** Find the next [count] matching times after [instant]. */
    public fun nextN(instant: Instant, count: Int): List<Instant> {
        val results = mutableListOf<Instant>()
        var current = instant
        repeat(count) { val next = nextAfter(current); results.add(next); current = next }
        return results
    }

    /** Find the previous matching time before [instant] (UTC). */
    public fun previousBefore(instant: Instant): Instant {
        var dt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC).minusMinutes(1).withSecond(0).withNano(0)
        for (i in 0 until 525960) {
            if (matchesDateTime(dt)) return dt.toInstant(ZoneOffset.UTC)
            dt = dt.minusMinutes(1)
        }
        throw IllegalStateException("No matching time found within 1 year")
    }

    /** Check if [instant] matches this expression (UTC, ignoring seconds). */
    public fun matches(instant: Instant): Boolean {
        val dt = LocalDateTime.ofInstant(instant, ZoneOffset.UTC)
        return matchesDateTime(dt)
    }

    private fun matchesDateTime(dt: LocalDateTime): Boolean {
        return dt.minute in minutes && dt.hour in hours && dt.dayOfMonth in daysOfMonth &&
            dt.monthValue in months && (dt.dayOfWeek.value % 7) in daysOfWeek
    }

    /** Human-readable description. */
    public fun describe(): String {
        val minDesc = describeSet(minutes, 0, 59, "minute")
        val hourDesc = describeSet(hours, 0, 23, "hour")
        return when {
            minutes.size == 60 && hours.size == 24 -> "Every minute"
            minutes.size == 1 && hours.size == 1 -> "At %02d:%02d".format(hours.first(), minutes.first())
            minutes.size < 60 && hours.size == 24 -> minDesc
            else -> "$minDesc, $hourDesc"
        }
    }

    private fun describeSet(values: Set<Int>, min: Int, max: Int, unit: String): String {
        if (values.size == max - min + 1) return "Every $unit"
        if (values.size == 1) return "At $unit ${values.first()}"
        val sorted = values.sorted()
        if (sorted.size >= 2) {
            val step = sorted[1] - sorted[0]
            if (sorted.all { (it - sorted[0]) % step == 0 }) return "Every $step ${unit}s"
        }
        return "At ${unit}s ${sorted.joinToString(", ")}"
    }
}
