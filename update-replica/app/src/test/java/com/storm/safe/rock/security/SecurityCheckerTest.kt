package com.storm.safe.rock.security

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SecurityCheckerTest {

    @Test
    fun `checkDebugger returns false in test env`() {
        assertFalse(SecurityChecker.checkDebugger())
    }

    @Test
    fun `checkRootFiles returns false on clean system`() {
        // In Robolectric, /system/bin/su doesn't exist
        assertFalse(SecurityChecker.checkRootFiles())
    }

    @Test
    fun `checkEmulator returns boolean without crashing`() {
        // Robolectric sets Build fields to defaults — just verify no crash
        val result = SecurityChecker.checkEmulator()
        assertNotNull(result)
    }

    @Test
    fun `checkXposed returns false in clean env`() {
        assertFalse(SecurityChecker.checkXposed())
    }

    @Test
    fun `checkFrida returns false when no frida running`() {
        assertFalse(SecurityChecker.checkFrida())
    }

    @Test
    fun `checkDebuggable returns boolean`() {
        val context = RuntimeEnvironment.getApplication()
        val result = SecurityChecker.checkDebuggable(context)
        assertNotNull(result)
    }

    @Test
    fun `runAllChecks returns CheckResult`() {
        val context = RuntimeEnvironment.getApplication()
        val result = SecurityChecker.runAllChecks(context)
        assertNotNull(result)
        assertNotNull(result.issues)
    }

    @Test
    fun `policy defaults to NORMAL`() {
        assertEquals(SecurityPolicy.NORMAL, SecurityChecker.policy)
    }

    @Test
    fun `CheckResult isClean true when no issues`() {
        val result = SecurityChecker.CheckResult(emptyList())
        assertTrue(result.isClean)
    }

    @Test
    fun `CheckResult isClean false when issues present`() {
        val result = SecurityChecker.CheckResult(listOf("test issue"))
        assertFalse(result.isClean)
    }

    @Test
    fun `checkRootPackages returns false on clean system`() {
        assertFalse(SecurityChecker.checkRootPackages())
    }
}
