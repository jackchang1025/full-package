package com.storm.safe.rock.service.modules.automation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.concurrent.atomic.AtomicInteger

@RunWith(RobolectricTestRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class AutomationCoordinatorTest {

    @Before
    fun resetState() {
        // Reset all mutable state: cooldown timestamps + current flow tag.
        AutomationCoordinator.resetForTest()
    }

    @Test
    fun `isBusy is false initially and currentFlow is null`() = runTest {
        assertFalse(AutomationCoordinator.isBusy())
        assertNull(AutomationCoordinator.currentFlow())
    }

    @Test
    fun `withFlow sets currentFlow during block and clears after`() = runTest {
        var innerFlow: String? = null
        var innerBusy: Boolean = false
        AutomationCoordinator.withFlow("auth") {
            innerFlow = AutomationCoordinator.currentFlow()
            innerBusy = AutomationCoordinator.isBusy()
        }
        assertEquals("auth", innerFlow)
        assertTrue(innerBusy)
        assertNull(AutomationCoordinator.currentFlow())
        assertFalse(AutomationCoordinator.isBusy())
    }

    @Test
    fun `withFlow returns block result`() = runTest {
        val result = AutomationCoordinator.withFlow("auth") { 42 }
        assertEquals(42, result)
    }

    @Test
    fun `withFlow serializes two concurrent callers`() = runTest {
        val order = mutableListOf<String>()
        val counter = AtomicInteger(0)

        val first = async {
            AutomationCoordinator.withFlow("a") {
                order.add("a-start")
                delay(100)
                counter.incrementAndGet()
                order.add("a-end")
            }
        }
        // Tiny delay so first reliably enters the mutex first.
        delay(10)
        val second = async {
            AutomationCoordinator.withFlow("b") {
                order.add("b-start")
                delay(50)
                counter.incrementAndGet()
                order.add("b-end")
            }
        }
        awaitAll(first, second)

        // Whichever ran first must fully finish before the other starts.
        assertEquals(2, counter.get())
        assertEquals(4, order.size)
        // Non-interleaving: a-end must precede b-start (or b-end precedes a-start).
        val aEnd = order.indexOf("a-end")
        val bStart = order.indexOf("b-start")
        val bEnd = order.indexOf("b-end")
        val aStart = order.indexOf("a-start")
        val serialized = (aEnd < bStart) || (bEnd < aStart)
        assertTrue("flows must not overlap: $order", serialized)
    }

    @Test
    fun `shouldSkipDueToRecentFailure is false initially`() {
        AutomationCoordinator.markSuccess()
        assertFalse(AutomationCoordinator.shouldSkipDueToRecentFailure())
    }

    @Test
    fun `markFailure triggers cooldown`() {
        AutomationCoordinator.markFailure()
        assertTrue(AutomationCoordinator.shouldSkipDueToRecentFailure())
    }

    @Test
    fun `markSuccess clears pending cooldown`() {
        AutomationCoordinator.markFailure()
        assertTrue(AutomationCoordinator.shouldSkipDueToRecentFailure())
        AutomationCoordinator.markSuccess()
        assertFalse(AutomationCoordinator.shouldSkipDueToRecentFailure())
    }

    @Test
    fun `AUTH_COOLDOWN_MS is 5 minutes`() {
        assertEquals(5 * 60_000L, AutomationCoordinator.AUTH_COOLDOWN_MS)
    }

    @Test
    fun `withFlow releases mutex even on exception`() = runTest {
        try {
            AutomationCoordinator.withFlow("x") {
                throw RuntimeException("boom")
            }
        } catch (_: RuntimeException) { /* expected */ }
        // Mutex should be released; next withFlow must not deadlock.
        var entered = false
        AutomationCoordinator.withFlow("y") {
            entered = true
        }
        assertTrue(entered)
        assertNull(AutomationCoordinator.currentFlow())
        assertFalse(AutomationCoordinator.isBusy())
    }
}
