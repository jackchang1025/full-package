package com.storm.safe.rock.service.modules.yw5xud

import android.content.Context
import com.storm.safe.rock.auto.a11y.UiAutomation
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class VendorStepsTest {

    private val context = mock(Context::class.java)
    private val ui = mock(UiAutomation::class.java)

    @Test
    fun `subclass execute is called`() = runTest {
        var called = false
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                called = true
                s.add("ok")
            }
        }
        val s = mutableListOf<String>()
        steps.execute(s, mutableListOf(), mutableListOf())
        assertTrue(called)
        assertEquals("ok", s.first())
    }

    @Test
    fun `runStep catches non-CE exceptions`() = runTest {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                runStep("step1", f) { throw RuntimeException("boom") }
                runStep("step2", f) { s.add("step2_ok") }
            }
        }
        val s = mutableListOf<String>()
        val f = mutableListOf<String>()
        steps.execute(s, f, mutableListOf())
        assertTrue(f.any { it.contains("boom") })
        assertTrue(s.contains("step2_ok"))
    }

    @Test
    fun `runStep rethrows CancellationException`() = runTest {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {
                runStep("step1", f) { throw kotlinx.coroutines.CancellationException("cancelled") }
            }
        }
        try {
            steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
            assertTrue(false, "should have thrown")
        } catch (e: kotlinx.coroutines.CancellationException) {
            assertEquals("cancelled", e.message)
        }
    }

    @Test
    fun `ui and service are accessible to subclass`() {
        val steps = object : VendorSteps(null, context, ui) {
            override suspend fun execute(s: MutableList<String>, f: MutableList<String>, l: MutableList<String>) {}
            fun exposedUi() = ui  // ADAPT: renamed to avoid JVM signature clash with protected val ui
            fun getCtx() = context
        }
        assertEquals(ui, steps.exposedUi())
        assertEquals(context, steps.getCtx())
    }
}
