package com.storm.safe.rock.service.modules.base

import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class AccessibilityDelegateTest {

    /** Concrete test implementation for exercising the abstract base class. */
    class TestDelegate(context: android.content.Context) : AccessibilityDelegate("TestDelegate", context) {
        var lastEvent: AccessibilityEvent? = null
        var lastPackage: String? = null
        var lastClassName: String? = null

        override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {
            lastEvent = event
            lastPackage = packageName
            lastClassName = className
        }

        override fun getListenWindows(): List<ListenWindow> = listOf(
            ListenWindow("com.android.settings", "SettingsActivity"),
            ListenWindow("com.miui.securitycenter")
        )
    }

    private fun createDelegate(): TestDelegate =
        TestDelegate(RuntimeEnvironment.getApplication())

    // --- Lifecycle tests ---

    @Test
    fun `initially not active`() {
        val d = createDelegate()
        assertFalse(d.isActive)
    }

    @Test
    fun `activate sets isActive to true`() {
        val d = createDelegate()
        d.activate()
        assertTrue(d.isActive)
    }

    @Test
    fun `deactivate sets isActive to false`() {
        val d = createDelegate()
        d.activate()
        d.deactivate()
        assertFalse(d.isActive)
    }

    @Test
    fun `tag is set from constructor`() {
        val d = createDelegate()
        assertEquals("TestDelegate", d.tag)
    }

    @Test
    fun `context is set from constructor`() {
        val ctx = RuntimeEnvironment.getApplication()
        val d = TestDelegate(ctx)
        assertSame(ctx, d.context)
    }

    // --- Node tracking tests ---

    @Test
    fun `trackNode increases tracked count`() {
        val d = createDelegate()
        val node = mock(AccessibilityNodeInfo::class.java)
        d.trackNode(node)
        assertEquals(1, d.getTrackedNodeCount())
    }

    @Test
    fun `trackNode with two nodes increases count to two`() {
        val d = createDelegate()
        val node1 = mock(AccessibilityNodeInfo::class.java)
        val node2 = mock(AccessibilityNodeInfo::class.java)
        d.trackNode(node1)
        d.trackNode(node2)
        assertEquals(2, d.getTrackedNodeCount())
    }

    @Test
    fun `duplicate trackNode does not increase count`() {
        val d = createDelegate()
        val node = mock(AccessibilityNodeInfo::class.java)
        d.trackNode(node)
        d.trackNode(node)
        assertEquals(1, d.getTrackedNodeCount())
    }

    @Test
    fun `recycleNodes calls recycle on each node and clears set`() {
        val d = createDelegate()
        val node1 = mock(AccessibilityNodeInfo::class.java)
        val node2 = mock(AccessibilityNodeInfo::class.java)
        d.trackNode(node1)
        d.trackNode(node2)
        d.recycleNodes()
        assertEquals(0, d.getTrackedNodeCount())
        verify(node1).recycle()
        verify(node2).recycle()
    }

    @Test
    fun `recycleNodes handles exception from recycle gracefully`() {
        val d = createDelegate()
        val node = mock(AccessibilityNodeInfo::class.java)
        doThrow(RuntimeException("already recycled")).`when`(node).recycle()
        d.trackNode(node)
        d.recycleNodes() // should not throw
        assertEquals(0, d.getTrackedNodeCount())
    }

    @Test
    fun `recycleNodes on empty set does not throw`() {
        val d = createDelegate()
        d.recycleNodes() // should not throw
        assertEquals(0, d.getTrackedNodeCount())
    }

    @Test
    fun `getTrackedNodeCount returns zero initially`() {
        val d = createDelegate()
        assertEquals(0, d.getTrackedNodeCount())
    }

    // --- Window matching tests ---

    @Test
    fun `matchesWindow matches correct package and class`() {
        val d = createDelegate()
        assertTrue(d.matchesWindow("com.android.settings", "SettingsActivity"))
    }

    @Test
    fun `matchesWindow matches package-only listen window`() {
        val d = createDelegate()
        assertTrue(d.matchesWindow("com.miui.securitycenter", "SomeActivity"))
    }

    @Test
    fun `matchesWindow rejects non-matching package`() {
        val d = createDelegate()
        assertFalse(d.matchesWindow("com.other.app", "OtherActivity"))
    }

    @Test
    fun `matchesWindow rejects matching package but wrong class`() {
        val d = createDelegate()
        // "com.android.settings" is in listen windows with "SettingsActivity"
        assertFalse(d.matchesWindow("com.android.settings", "OtherActivity"))
    }

    // --- Event dispatch tests ---

    @Test
    fun `onAccessibilityEvent dispatches to subclass`() {
        val d = createDelegate()
        val event = mock(AccessibilityEvent::class.java)
        d.onAccessibilityEvent(event, "com.test", "TestClass")
        assertSame(event, d.lastEvent)
        assertEquals("com.test", d.lastPackage)
        assertEquals("TestClass", d.lastClassName)
    }

    // --- Dispose tests ---

    @Test
    fun `dispose deactivates the delegate`() {
        val d = createDelegate()
        d.activate()
        d.dispose()
        assertFalse(d.isActive)
    }

    @Test
    fun `dispose recycles all tracked nodes`() {
        val d = createDelegate()
        val node = mock(AccessibilityNodeInfo::class.java)
        d.activate()
        d.trackNode(node)
        d.dispose()
        assertEquals(0, d.getTrackedNodeCount())
        verify(node).recycle()
    }

    @Test
    fun `dispose on already inactive delegate does not throw`() {
        val d = createDelegate()
        d.dispose() // should not throw
        assertFalse(d.isActive)
    }

    // --- Service reference tests ---

    @Test
    fun `service is null when using two-arg constructor`() {
        val d = createDelegate()
        assertNull(d.service)
    }

    @Test
    fun `performGlobalAction returns false when service is null`() {
        val d = createDelegate()
        assertFalse(d.performGlobalAction(1)) // GLOBAL_ACTION_BACK
    }

    // --- executeAuthorization tests ---

    @Test
    fun `executeAuthorization returns success when doExecute adds successes`() {
        val d = object : AccessibilityDelegate("AuthTest", RuntimeEnvironment.getApplication()) {
            override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {}
            override fun getListenWindows() = emptyList<ListenWindow>()
            override suspend fun doExecute(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) {
                successes.add("自启动 Switch 已开启")
            }
        }
        val result = kotlinx.coroutines.runBlocking { d.executeAuthorization() }
        assertTrue(result.isSuccess)
        assertEquals(1, result.successes.size)
        assertEquals(0, result.failures.size)
    }

    @Test
    fun `executeAuthorization returns failure when doExecute throws`() {
        val d = object : AccessibilityDelegate("FailTest", RuntimeEnvironment.getApplication()) {
            override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {}
            override fun getListenWindows() = emptyList<ListenWindow>()
            override suspend fun doExecute(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) {
                throw RuntimeException("模拟异常")
            }
        }
        val result = kotlinx.coroutines.runBlocking { d.executeAuthorization() }
        assertFalse(result.isSuccess)
        assertEquals(1, result.failures.size)
        assertTrue(result.failures[0].contains("模拟异常"))
    }

    @Test
    fun `executeAuthorization recycles nodes even on exception`() {
        val node = mock(AccessibilityNodeInfo::class.java)
        val d = object : AccessibilityDelegate("RecycleTest", RuntimeEnvironment.getApplication()) {
            override fun onAccessibilityEvent(event: AccessibilityEvent, packageName: String, className: String) {}
            override fun getListenWindows() = emptyList<ListenWindow>()
            override suspend fun doExecute(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>) {
                trackNode(node)
                throw RuntimeException("boom")
            }
        }
        kotlinx.coroutines.runBlocking { d.executeAuthorization() }
        assertEquals(0, d.getTrackedNodeCount())
        verify(node).recycle()
    }
}
