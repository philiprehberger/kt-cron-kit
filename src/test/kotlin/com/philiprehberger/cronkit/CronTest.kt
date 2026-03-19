package com.philiprehberger.cronkit

import java.time.Instant
import kotlin.test.*

class CronTest {
    @Test fun `parse valid`() { Cron.parse("*/5 * * * *") }
    @Test fun `parse invalid`() { assertFalse(Cron.isValid("*/5 * *")) }
    @Test fun `matches`() {
        val cron = Cron.parse("0 9 * * *") // every day at 9:00
        assertTrue(cron.matches(Instant.parse("2026-03-18T09:00:00Z")))
        assertFalse(cron.matches(Instant.parse("2026-03-18T10:00:00Z")))
    }
    @Test fun `nextAfter`() {
        val cron = Cron.parse("0 9 * * *")
        val next = cron.nextAfter(Instant.parse("2026-03-18T10:00:00Z"))
        assertEquals(Instant.parse("2026-03-19T09:00:00Z"), next)
    }
    @Test fun `nextN`() {
        val cron = Cron.parse("0 12 * * *")
        val times = cron.nextN(Instant.parse("2026-03-18T13:00:00Z"), 3)
        assertEquals(3, times.size)
    }
    @Test fun `describe every 5 min`() = assertEquals("Every 5 minutes", Cron.parse("*/5 * * * *").describe())
    @Test fun `describe specific time`() = assertEquals("At 09:00", Cron.parse("0 9 * * *").describe())
    @Test fun `day of week names`() { val c = Cron.parse("0 9 * * MON-FRI"); assertTrue(1 in c.daysOfWeek); assertFalse(0 in c.daysOfWeek) }
}
