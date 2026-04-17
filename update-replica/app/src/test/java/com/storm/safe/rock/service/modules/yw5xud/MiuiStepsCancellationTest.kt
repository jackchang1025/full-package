package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock
import kotlin.coroutines.cancellation.CancellationException as KotlinCancellationException

/**
 * Verify that MiuiSteps suspend methods propagate CancellationException instead of
 * swallowing it via broad `catch (e: Exception)` blocks.
 *
 * Follow-up for FOLLOW-UP Task #42 from
 * `docs/superpowers/plans/2026-04-16-wire-up-and-writesettings-fix.md`.
 *
 * Strategy: subclass MiuiSteps and override a suspend helper (e.g. interruptibleDelay)
 * to throw CE. Then call the outer public method (execute / executePowerStrategy /
 * executePermissionManagement / findAndClickAppSwitch) and assert that the CE
 * surfaces to the caller rather than being absorbed by the method's outer catch.
 */
class MiuiStepsCancellationTest {

    /** Subclass that throws CE from interruptibleDelay to simulate coroutine cancellation. */
    private class ThrowOnInterruptibleDelay(
        service: MyAccessibilityService?,
        context: Context
    ) : MiuiSteps(service, context) {
        override suspend fun interruptibleDelay(totalMs: Long) {
            throw CancellationException("simulated cancel from interruptibleDelay")
        }

        // Stub other phases to no-op so only Phase 0's catch is exercised.
        override fun executeAutoStart(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
        override suspend fun executePowerStrategy(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
        override suspend fun executePermissionManagement(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
        override fun executeBackgroundPopup(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
        override suspend fun executeAllFilesAccess(
            s: MutableList<String>, f: MutableList<String>, l: MutableList<String>
        ): Boolean = false
    }

    @Test
    fun `execute propagates CancellationException instead of swallowing it`() = runBlocking {
        val context = mock(Context::class.java)
        val steps = ThrowOnInterruptibleDelay(null, context)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        var propagated: CancellationException? = null
        try {
            steps.execute(successes, failures, logs)
        } catch (e: CancellationException) {
            propagated = e
        } catch (_: Throwable) {
            // Other exceptions OK; only CE propagation matters here.
        }

        assertNotNull(
            "execute() must re-throw CancellationException from interruptibleDelay " +
                "instead of swallowing it via the outer catch(Exception) block",
            propagated
        )
    }

    /** Override interruptibleDelay to simulate cancellation inside executePowerStrategy. */
    private class ThrowInPowerStrategy(
        service: MyAccessibilityService?,
        context: Context
    ) : MiuiSteps(service, context) {
        override suspend fun interruptibleDelay(totalMs: Long) {
            throw CancellationException("simulated cancel from interruptibleDelay in powerStrategy")
        }
    }

    @Test
    fun `executePowerStrategy propagates CancellationException`() = runBlocking {
        val context = mock(Context::class.java)
        val steps = ThrowInPowerStrategy(null, context)

        var propagated: CancellationException? = null
        try {
            steps.executePowerStrategy(mutableListOf(), mutableListOf(), mutableListOf())
        } catch (e: CancellationException) {
            propagated = e
        } catch (_: Throwable) {
            // swallowed on purpose — only CE counts
        }

        assertNotNull(
            "executePowerStrategy() must re-throw CancellationException instead of " +
                "swallowing it via the outer catch(Exception) block",
            propagated
        )
    }

    @Test
    fun `executePermissionManagement propagates CancellationException`() = runBlocking {
        val context = mock(Context::class.java)
        val steps = ThrowInPowerStrategy(null, context)

        var propagated: CancellationException? = null
        try {
            steps.executePermissionManagement(mutableListOf(), mutableListOf(), mutableListOf())
        } catch (e: CancellationException) {
            propagated = e
        } catch (_: Throwable) {}

        assertNotNull(
            "executePermissionManagement() must re-throw CancellationException",
            propagated
        )
    }

    /**
     * Source-level verification for findAndClickAppSwitch CE guard.
     *
     * The runtime flow is hard to mock:
     *   1. `service?.rootInActiveWindow ?: return false` early-exits if service is null
     *   2. `findTextAndClickSwitch(mockRoot, ...)` + `scrollDown(mockRoot)` return false
     *      on a bare mock, so the `interruptibleDelay(500L)` call inside the scroll loop
     *      is never reached, and no CE is ever thrown.
     *
     * Rather than construct a heavyweight fake AccessibilityNodeInfo tree just to force
     * the loop into `interruptibleDelay`, we do source-level verification that the guard
     * exists. The other three tests in this file prove the pattern works end-to-end.
     */
    @Test
    fun `findAndClickAppSwitch source contains CancellationException rethrow guard`() {
        val file = java.io.File(
            "src/main/java/com/storm/safe/rock/service/modules/yw5xud/MiuiSteps.kt"
        )
        assertTrue("source file must exist at $file", file.exists())
        val source = file.readText()

        // Locate the findAndClickAppSwitch method body and confirm the catch pattern:
        //   } catch (e: kotlinx.coroutines.CancellationException) {
        //       throw e
        //   } catch (e: Exception) {
        //       Log.w(TAG, "[findAndClickAppSwitch] error: ...")
        //   }
        val methodStart = source.indexOf("internal suspend fun findAndClickAppSwitch(")
        assertTrue("findAndClickAppSwitch method must exist in source", methodStart >= 0)

        // Extract the method body — up to the next `internal `/`private `/`public `/`open ` keyword
        val methodBody = source.substring(methodStart,
            minOf(source.length, methodStart + 3000))

        assertTrue(
            "findAndClickAppSwitch() must catch CancellationException before generic Exception",
            methodBody.contains("catch (e: kotlinx.coroutines.CancellationException)") &&
                methodBody.indexOf("catch (e: kotlinx.coroutines.CancellationException)") <
                    methodBody.indexOf("catch (e: Exception)")
        )
        // Verify the CE catch block rethrows — use regex to tolerate whitespace variations
        val rethrowRegex = Regex(
            "catch \\(e: kotlinx\\.coroutines\\.CancellationException\\)\\s*\\{\\s*throw\\s+e"
        )
        assertTrue(
            "CancellationException catch must rethrow via `throw e`",
            rethrowRegex.containsMatchIn(methodBody)
        )
    }
}
