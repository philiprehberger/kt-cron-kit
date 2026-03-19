package com.philiprehberger.cronkit

import java.time.Instant

/** Entry point for cron expression parsing. */
public object Cron {
    /** Parse a 5-field cron expression. */
    public fun parse(expression: String): CronExpression {
        val parts = expression.trim().split(Regex("\\s+"))
        require(parts.size == 5) { "Expected 5 fields, got ${parts.size}: $expression" }
        return CronExpression(
            minutes = parseField(parts[0], 0, 59),
            hours = parseField(parts[1], 0, 23),
            daysOfMonth = parseField(parts[2], 1, 31),
            months = parseField(parts[3], 1, 12),
            daysOfWeek = parseField(parts[4], 0, 6),
        )
    }

    /** Returns true if the expression is valid. */
    public fun isValid(expression: String): Boolean = try { parse(expression); true } catch (_: Exception) { false }
}

private val DOW_NAMES = mapOf("SUN" to 0, "MON" to 1, "TUE" to 2, "WED" to 3, "THU" to 4, "FRI" to 5, "SAT" to 6)
private val MONTH_NAMES = mapOf("JAN" to 1, "FEB" to 2, "MAR" to 3, "APR" to 4, "MAY" to 5, "JUN" to 6, "JUL" to 7, "AUG" to 8, "SEP" to 9, "OCT" to 10, "NOV" to 11, "DEC" to 12)

internal fun parseField(field: String, min: Int, max: Int): Set<Int> {
    val result = mutableSetOf<Int>()
    for (part in field.uppercase().split(",")) {
        val stepParts = part.split("/")
        val rangePart = stepParts[0]
        val step = if (stepParts.size > 1) stepParts[1].toInt() else 1

        val (start, end) = when {
            rangePart == "*" -> min to max
            rangePart.contains("-") -> {
                val (a, b) = rangePart.split("-").map { resolveValue(it, min, max) }
                a to b
            }
            else -> { val v = resolveValue(rangePart, min, max); v to v }
        }
        var i = start
        while (i <= end) { result.add(i); i += step }
    }
    return result
}

private fun resolveValue(token: String, min: Int, max: Int): Int {
    DOW_NAMES[token]?.let { return it }
    MONTH_NAMES[token]?.let { return it }
    val v = token.toInt()
    require(v in min..max) { "Value $v out of range $min..$max" }
    return v
}
