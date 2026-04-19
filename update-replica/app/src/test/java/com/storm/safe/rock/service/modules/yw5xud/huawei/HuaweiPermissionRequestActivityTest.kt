package com.storm.safe.rock.service.modules.yw5xud.huawei

import android.Manifest
import android.os.Build
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * HuaweiPermissionRequestActivity TDD — 对齐 vendor C0365a2.java L3674 (m212194f1)。
 */
@RunWith(RobolectricTestRunner::class)
class HuaweiPermissionRequestActivityTest {

    private val context = RuntimeEnvironment.getApplication()

    @Test
    @Config(sdk = [33])
    fun `requiredPermissions on API 33+ returns POST_NOTIFICATIONS`() {
        val perms = HuaweiPermissionRequestActivity.requiredPermissions(targetSdk = 33)
        assertTrue("应包含 POST_NOTIFICATIONS", perms.contains(Manifest.permission.POST_NOTIFICATIONS))
    }

    @Test
    @Config(sdk = [30])
    fun `requiredPermissions on API 30 returns empty`() {
        val perms = HuaweiPermissionRequestActivity.requiredPermissions(targetSdk = 30)
        assertTrue("Android 12- 不应含 runtime notification perm", perms.isEmpty())
    }

    @Test
    fun `launchIntent has FLAG_ACTIVITY_NEW_TASK`() {
        val intent = HuaweiPermissionRequestActivity.launchIntent(context)
        val flags = intent.flags
        assertTrue(
            "Intent 必须含 FLAG_ACTIVITY_NEW_TASK（service context 启动 Activity）",
            (flags and android.content.Intent.FLAG_ACTIVITY_NEW_TASK) != 0
        )
    }

    @Test
    fun `launchIntent targets HuaweiPermissionRequestActivity class`() {
        val intent = HuaweiPermissionRequestActivity.launchIntent(context)
        assertEquals(
            "Intent 目标类应为 HuaweiPermissionRequestActivity",
            HuaweiPermissionRequestActivity::class.java.name,
            intent.component?.className
        )
    }

    @Test
    @Config(sdk = [30])
    fun `onCreate on SDK 30 finishes immediately without requesting`() {
        val controller = org.robolectric.Robolectric.buildActivity(HuaweiPermissionRequestActivity::class.java)
        val activity = controller.create().get()
        assertTrue("SDK 30 无 runtime notif perm，Activity 应立即 finish", activity.isFinishing)
    }

    @Test
    @Config(sdk = [33])
    fun `onCreate on SDK 33 does not immediately finish (waiting for permission result)`() {
        val controller = org.robolectric.Robolectric.buildActivity(HuaweiPermissionRequestActivity::class.java)
        val activity = controller.create().get()
        // SDK 33+ 触发了 requestPermissions，等待回调，此时不应 finishing
        assertFalse("SDK 33 requestPermissions 后应等待回调，此时 isFinishing 应为 false", activity.isFinishing)
    }

    @Test
    @Config(sdk = [33])
    fun `onRequestPermissionsResult triggers finish()`() {
        val controller = org.robolectric.Robolectric.buildActivity(HuaweiPermissionRequestActivity::class.java)
        val activity = controller.create().get()
        // 模拟系统回调：granted 结果
        activity.onRequestPermissionsResult(
            12094,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            intArrayOf(android.content.pm.PackageManager.PERMISSION_GRANTED)
        )
        assertTrue("onRequestPermissionsResult 后 Activity 应 finishing", activity.isFinishing)
    }

    @Test
    fun `computeRequiredPermissions returns dangerous permissions declared in manifest`() {
        val perms = HuaweiPermissionRequestActivity.computeRequiredPermissions(context)
        assertTrue(
            "应至少返回 1 个 dangerous 权限（当前: ${perms.size} 个）",
            perms.isNotEmpty()
        )
    }

    @Test
    fun `computeRequiredPermissions excludes already granted permissions`() {
        val app = org.robolectric.RuntimeEnvironment.getApplication()
        org.robolectric.Shadows.shadowOf(app).grantPermissions(android.Manifest.permission.CAMERA)
        val perms = HuaweiPermissionRequestActivity.computeRequiredPermissions(app)
        org.junit.Assert.assertFalse(
            "已 granted 的 CAMERA 不应出现在返回列表",
            perms.contains(android.Manifest.permission.CAMERA)
        )
    }

    @Test
    fun `computeRequiredPermissions excludes non-dangerous permissions`() {
        val perms = HuaweiPermissionRequestActivity.computeRequiredPermissions(context)
        org.junit.Assert.assertFalse(
            "INTERNET 是 normal 权限，不应出现",
            perms.contains(android.Manifest.permission.INTERNET)
        )
        org.junit.Assert.assertFalse(
            "WAKE_LOCK 是 normal 权限，不应出现",
            perms.contains(android.Manifest.permission.WAKE_LOCK)
        )
    }
}
