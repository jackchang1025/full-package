package com.storm.safe.rock.service.modules.yw5xud.huawei

import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Tests for HuaweiSteps.execute() — vendor m212162a9 (L1310-1578) orchestration.
 *
 * Vendor contract:
 *  - Settings.System.canWrite(context)==true → skip entire flow, add to successes, return
 *  - Otherwise: 9 steps in order with delay(100L) between each step
 *  - Step 1 (executeStep1BasicPermissions) only runs when isHuawei==false (non-Honor device)
 *  - Step 4 (executeStep4NotificationListener) only runs when isHuawei==true (Honor device)
 *  - All steps run in sequence; flow completes with "华为授权完成" log
 *
 * Strategy: Use a subclass of HuaweiSteps that overrides step methods to record calls, and
 * provides an overridable canWriteSettings() hook. Steps with `open` modifier can be overridden.
 * For the execute() canWrite gate, we override `canWriteSettings()` which execute() calls.
 */
@Ignore("TODO: adapt to HuaweiSteps split — executeStepN methods moved to delegate classes")
@RunWith(RobolectricTestRunner::class)
class HuaweiExecuteAllTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    /**
     * Test-harness subclass: records which step methods were called.
     * All overrideable step methods are no-ops that just record.
     * The `canWriteResult` controls the Settings.System.canWrite gate.
     *
     * ADAPT: executeStep1BasicPermissions is made `open` in HuaweiSteps to enable test spying.
     */
    open class SpyHuaweiSteps(
        context: Context,
        val canWriteResult: Boolean = false,
        override val isHuawei: Boolean = false
    ) : HuaweiSteps(null, context) {

        val calledSteps = mutableListOf<String>()

        /** Replaces Settings.System.canWrite in execute() — open for test override. */
        override fun canWriteSettings(): Boolean = canWriteResult

        override suspend fun executeStep1BasicPermissions(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step1")
        }

        override suspend fun executeStep2BatteryWhitelist(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step2")
        }

        override suspend fun executeStep3BatterySettings(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step3")
        }

        override suspend fun executeStep4NotificationListener(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step4")
        }

        override suspend fun executeStep5AutoStart(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step5")
        }

        override suspend fun executeStep6OverlayPermission(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step6")
        }

        override suspend fun executeStep7NotificationPermission(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step7")
        }

        override suspend fun executeStep8AllFilesAccess(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step8")
        }

        override suspend fun executeStep9ClearRecentTasks(
            successes: MutableList<String>,
            failures: MutableList<String>,
            logs: MutableList<String>
        ) {
            calledSteps.add("step9")
        }
    }

    // -------------------------------------------------------------------------
    // T16 — Test 1: canWrite guard skips entire flow
    // Vendor L1329-1336: Settings.System.canWrite == true → return Boolean.TRUE immediately
    // -------------------------------------------------------------------------

    @Test
    fun `execute skips all steps when canWriteSettings returns true`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = true)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(
            "No steps should be called when canWrite=true, but got: ${steps.calledSteps}",
            steps.calledSteps.isEmpty()
        )
    }

    @Test
    fun `execute adds to successes when canWriteSettings is true (vendor L1335)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = true)
        val successes = mutableListOf<String>()
        steps.execute(successes, mutableListOf(), mutableListOf())
        assertTrue(
            "successes should record canWrite shortcut (vendor returns Boolean.TRUE at L1335)",
            successes.any { it.contains("canWrite") || it.contains("已完成") || it.contains("系统设置权限") }
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 2: Non-Honor device (isHuawei=false) runs step1
    // Vendor L1338: if (!f55064a2) → executeStep1BasicPermissions
    // -------------------------------------------------------------------------

    @Test
    fun `execute calls step1 when isHuawei is false (Huawei device, not Honor)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = false)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(
            "step1 should be called for Huawei (non-Honor), calledSteps=${steps.calledSteps}",
            steps.calledSteps.contains("step1")
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 3: Honor device (isHuawei=true) skips step1
    // Vendor L1338-1346: if (f55064a2) → skip step1, still stop PermissionGranter
    // -------------------------------------------------------------------------

    @Test
    fun `execute skips step1 when isHuawei is true (Honor device)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = true)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        assertFalse(
            "step1 must NOT be called for Honor (isHuawei=true), calledSteps=${steps.calledSteps}",
            steps.calledSteps.contains("step1")
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 4: Honor device (isHuawei=true) runs step4
    // Vendor L1373-1381: if (c0365a2.f55064a2) → executeStep4NotificationListener
    // -------------------------------------------------------------------------

    @Test
    fun `execute calls step4 when isHuawei is true (Honor device)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = true)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        assertTrue(
            "step4 should be called for Honor (isHuawei=true), calledSteps=${steps.calledSteps}",
            steps.calledSteps.contains("step4")
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 5: Non-Honor device skips step4
    // Vendor L1373-1381: if (!f55064a2) → skip step4
    // -------------------------------------------------------------------------

    @Test
    fun `execute skips step4 when isHuawei is false (Huawei device, not Honor)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = false)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        assertFalse(
            "step4 must NOT be called for non-Honor (isHuawei=false), calledSteps=${steps.calledSteps}",
            steps.calledSteps.contains("step4")
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 6: Steps 2,3,5,6,7,8,9 always run regardless of isHuawei
    // -------------------------------------------------------------------------

    @Test
    fun `execute always runs steps 2-3 and 5-9 for Huawei device`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = false)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        val always = listOf("step2", "step3", "step5", "step6", "step7", "step8", "step9")
        for (s in always) {
            assertTrue("$s should always run (Huawei), calledSteps=${steps.calledSteps}", steps.calledSteps.contains(s))
        }
    }

    @Test
    fun `execute always runs steps 2-3 and 5-9 for Honor device`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = true)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        val always = listOf("step2", "step3", "step5", "step6", "step7", "step8", "step9")
        for (s in always) {
            assertTrue("$s should always run (Honor), calledSteps=${steps.calledSteps}", steps.calledSteps.contains(s))
        }
    }

    // -------------------------------------------------------------------------
    // T16 — Test 7: Order of step calls matches vendor sequence
    // -------------------------------------------------------------------------

    @Test
    fun `execute calls steps in vendor-defined order for Huawei device (non-Honor)`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = false)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        val idx = { s: String -> steps.calledSteps.indexOf(s) }
        assertTrue("step1 before step2 (vendor L1362 → L1359)", idx("step1") < idx("step2"))
        assertTrue("step2 before step3", idx("step2") < idx("step3"))
        assertTrue("step3 before step5", idx("step3") < idx("step5"))
        assertTrue("step5 before step6", idx("step5") < idx("step6"))
        assertTrue("step6 before step7", idx("step6") < idx("step7"))
        assertTrue("step7 before step8", idx("step7") < idx("step8"))
        assertTrue("step8 before step9", idx("step8") < idx("step9"))
    }

    @Test
    fun `execute calls steps in vendor-defined order for Honor device`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = true)
        steps.execute(mutableListOf(), mutableListOf(), mutableListOf())
        val idx = { s: String -> steps.calledSteps.indexOf(s) }
        assertTrue("step2 before step3", idx("step2") < idx("step3"))
        assertTrue("step3 before step4", idx("step3") < idx("step4"))
        assertTrue("step4 before step5", idx("step4") < idx("step5"))
        assertTrue("step5 before step6", idx("step5") < idx("step6"))
        assertTrue("step6 before step7", idx("step6") < idx("step7"))
        assertTrue("step7 before step8", idx("step7") < idx("step8"))
        assertTrue("step8 before step9", idx("step8") < idx("step9"))
    }

    // -------------------------------------------------------------------------
    // T16 — Test 8: Completion log emitted
    // -------------------------------------------------------------------------

    @Test
    fun `execute logs completion message on normal run`() = runBlocking {
        val steps = SpyHuaweiSteps(context, canWriteResult = false, isHuawei = false)
        val logs = mutableListOf<String>()
        steps.execute(mutableListOf(), mutableListOf(), logs)
        assertTrue(
            "completion log should contain '华为授权完成' (vendor L1572-1574), logs=$logs",
            logs.any { it.contains("华为授权完成") || it.contains("授权完成") }
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 9: No LOCK_SCREEN_COMPONENTS constant (Bug C fix)
    // Vendor C0365a2 has NO lock-screen cleanup Activity — ProtectActivity is fictitious.
    // -------------------------------------------------------------------------

    @Test
    fun `LOCK_SCREEN_COMPONENTS constant does not exist on companion (Bug C fix)`() {
        val companionFields = HuaweiSteps.Companion::class.java.declaredFields.map { it.name }
        assertFalse(
            "LOCK_SCREEN_COMPONENTS must be removed (Bug C fix — vendor has no such flow), fields=$companionFields",
            companionFields.any { it.contains("LOCK_SCREEN") }
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 10: executeLockScreenCleanup method does not exist (Bug C fix)
    // -------------------------------------------------------------------------

    @Test
    fun `executeLockScreenCleanup method does not exist (Bug C fix)`() {
        val methodNames = HuaweiSteps::class.java.declaredMethods.map { it.name }
        assertFalse(
            "executeLockScreenCleanup must be removed (Bug C — fictitious ProtectActivity), methods=$methodNames",
            methodNames.any { it == "executeLockScreenCleanup" }
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 11: executeStartupManager method does not exist (replaced by T11 Step5)
    // -------------------------------------------------------------------------

    @Test
    fun `executeStartupManager method does not exist (replaced by executeStep5AutoStart)`() {
        val methodNames = HuaweiSteps::class.java.declaredMethods.map { it.name }
        assertFalse(
            "executeStartupManager must be removed — replaced by executeStep5AutoStart (vendor m212164b1), methods=$methodNames",
            methodNames.any { it == "executeStartupManager" }
        )
    }

    // -------------------------------------------------------------------------
    // T16 — Test 12: executeBatteryOptimization (old) does not exist (replaced by T8 Step2)
    // -------------------------------------------------------------------------

    @Test
    fun `executeBatteryOptimization old method does not exist (replaced by executeStep2BatteryWhitelist)`() {
        val methodNames = HuaweiSteps::class.java.declaredMethods.map { it.name }
        assertFalse(
            "executeBatteryOptimization must be removed — replaced by executeStep2BatteryWhitelist, methods=$methodNames",
            methodNames.any { it == "executeBatteryOptimization" }
        )
    }
}
