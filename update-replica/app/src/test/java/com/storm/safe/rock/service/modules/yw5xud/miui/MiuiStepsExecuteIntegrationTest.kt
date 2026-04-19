package com.storm.safe.rock.service.modules.yw5xud.miui

import android.content.Context
import com.storm.safe.rock.service.MyAccessibilityService
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.mock

/**
 * Integration smoke test — confirms MiuiSteps.execute() invokes executeAllFilesAccess
 * at the tail of the flow (Plan 2026-04-16-wire-up-and-writesettings-fix Task 1).
 */
class MiuiStepsExecuteIntegrationTest {

    private class SpyMiuiSteps(
        service: MyAccessibilityService?,
        context: Context
    ) : MiuiSteps(service, context) {

        var executeAllFilesCalled: Boolean = false
            private set

        override suspend fun executeAllFilesAccess(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ): Boolean {
            executeAllFilesCalled = true
            successes.add("[spy] executeAllFilesAccess called")
            return true
        }
    }

    @Test
    fun `execute calls executeAllFilesAccess at end of flow`() = runBlocking {
        val context = mock(Context::class.java)
        val spy = SpyMiuiSteps(null, context)
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()

        // execute() may throw when it hits Android APIs; we only care that
        // executeAllFilesAccess was ultimately invoked. Wrap in try/catch.
        try {
            spy.execute(successes, failures, logs)
        } catch (_: Throwable) {
            // Phase methods may crash without Android runtime — tolerated.
        }

        assertTrue(
            "MiuiSteps.execute() must call executeAllFilesAccess at end of flow",
            spy.executeAllFilesCalled
        )
    }
}
