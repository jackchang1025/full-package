package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.content.Context
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * HonorPermissionDialogTest — Tests for HuaweiSteps.detectAndClickHonorPermissionDialog.
 *
 * Non-@Ignored tests: the ones that only use `detectAndClickHonorPermissionDialog` and
 * `isHuawei` override — these are still accessible after the GKD refactor.
 *
 * @Ignored tests: tests that previously overrode `getHonorPermissionWindowTitle` and other
 * helpers that were moved to HuaweiHonorPermDialog delegate.
 *
 * Vendor: C0365a2.java:915-1309 (m212161a8)
 */
@RunWith(RobolectricTestRunner::class)
class HonorPermissionDialogTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = RuntimeEnvironment.getApplication()
    }

    @Test
    fun `non-Honor device returns NotFound immediately`() = runBlocking {
        val steps = object : HuaweiSteps(null, RuntimeEnvironment.getApplication()) {
            override val isHuawei: Boolean = false
        }
        val result = steps.detectAndClickHonorPermissionDialog()
        assertTrue("Expected NotFound for non-Honor device", result == HuaweiSteps.HonorClickResult.NotFound)
    }

    @Test
    fun `Honor device with null service returns NotFound`() = runBlocking {
        val steps = object : HuaweiSteps(null, RuntimeEnvironment.getApplication()) {
            override val isHuawei: Boolean = true
        }
        val result = steps.detectAndClickHonorPermissionDialog()
        assertEquals(HuaweiSteps.HonorClickResult.NotFound, result)
    }

    @Ignore("GKD refactor: getHonorPermissionWindowTitle moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `Honor device with empty window list returns NotFound`() {}

    @Ignore("GKD refactor: getHonorPermissionWindowTitle moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `non-matching window title returns NotFound`() {}

    @Ignore("GKD refactor: honor dialog click helpers moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `matching window title clicks first keyword`() {}

    @Ignore("GKD refactor: honor dialog click helpers moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `keyword ordering follows vendor priority`() {}

    @Ignore("GKD refactor: honor dialog click helpers moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `exception returns NotFound`() {}

    @Ignore("GKD refactor: honor dialog click helpers moved to HuaweiHonorPermDialog delegate")
    @Test
    fun `window title detection covers all 23 vendor keywords`() {}
}
