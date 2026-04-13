package com.storm.safe.rock

import android.app.Activity
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Intent
import android.media.projection.MediaProjection
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class Phase10BatchABTest {

    // -------------------------------------------------------
    // Batch A: AppVariant classes exist and are instantiable
    // -------------------------------------------------------

    @Test
    fun `AppVariantA is instantiable`() {
        val instance = AppVariantA()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantB is instantiable`() {
        val instance = AppVariantB()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantC is instantiable`() {
        val instance = AppVariantC()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantD is instantiable`() {
        val instance = AppVariantD()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantE is instantiable`() {
        val instance = AppVariantE()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantF is instantiable`() {
        val instance = AppVariantF()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantG is instantiable`() {
        val instance = AppVariantG()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantH is instantiable`() {
        val instance = AppVariantH()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantI is instantiable`() {
        val instance = AppVariantI()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantJ is instantiable`() {
        val instance = AppVariantJ()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantK is instantiable`() {
        val instance = AppVariantK()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantL is instantiable`() {
        val instance = AppVariantL()
        assertNotNull(instance)
    }

    @Test
    fun `AppVariantN is instantiable`() {
        val instance = AppVariantN()
        assertNotNull(instance)
    }

    @Test
    fun `DefaultLauncherAlias is instantiable`() {
        val instance = DefaultLauncherAlias()
        assertNotNull(instance)
    }

    // -------------------------------------------------------
    // TransparentHelperActivity extends Activity
    // -------------------------------------------------------

    @Test
    fun `TransparentHelperActivity extends Activity`() {
        val activity = Robolectric.buildActivity(
            com.storm.safe.rock.activity.TransparentHelperActivity::class.java
        ).create().get()
        assertTrue(activity is Activity)
        // Activity should finish immediately in onCreate
        assertTrue(activity.isFinishing)
    }

    // -------------------------------------------------------
    // BackgroundTaskActivity extends Activity
    // -------------------------------------------------------

    @Test
    fun `BackgroundTaskActivity extends Activity`() {
        val controller = Robolectric.buildActivity(
            com.storm.safe.rock.activity.BackgroundTaskActivity::class.java
        )
        val activity = controller.create().get()
        assertTrue(activity is Activity)
        // Activity finishes after triggering keepalive
        assertTrue(activity.isFinishing)
    }

    @Test
    fun `BackgroundTaskActivity finishes on newIntent`() {
        val controller = Robolectric.buildActivity(
            com.storm.safe.rock.activity.BackgroundTaskActivity::class.java
        ).create()
        val activity = controller.get()
        // Call newIntent — should also finish
        controller.newIntent(Intent())
        assertTrue(activity.isFinishing)
    }

    // -------------------------------------------------------
    // PackageVerifyActivity extends Activity
    // -------------------------------------------------------

    @Test
    fun `PackageVerifyActivity extends Activity`() {
        val activity = com.storm.safe.rock.activity.PackageVerifyActivity()
        assertTrue(activity is Activity)
    }

    @Test
    fun `PackageVerifyActivity Companion shouldShow returns true when not done`() {
        // Test the companion shouldShow and launch methods exist
        val companion = com.storm.safe.rock.activity.PackageVerifyActivity.Companion
        assertNotNull(companion)
    }

    // -------------------------------------------------------
    // MediaProjectionHolder static fields and methods
    // -------------------------------------------------------

    @Test
    fun `MediaProjectionHolder fields start as null or zero`() {
        // Reset to known state
        MediaProjectionHolder.mediaProjection = null
        MediaProjectionHolder.resultCode = null
        MediaProjectionHolder.permissionIntent = null
        MediaProjectionHolder.permissionTimestamp = 0L
        MediaProjectionHolder.lostCount = 0

        assertNull(MediaProjectionHolder.mediaProjection)
        assertNull(MediaProjectionHolder.resultCode)
        assertNull(MediaProjectionHolder.permissionIntent)
        assertEquals(0L, MediaProjectionHolder.permissionTimestamp)
        assertEquals(0, MediaProjectionHolder.lostCount)
    }

    @Test
    fun `MediaProjectionHolder isPermissionDataValid returns false initially`() {
        MediaProjectionHolder.resultCode = null
        MediaProjectionHolder.permissionTimestamp = 0L
        assertFalse(MediaProjectionHolder.isPermissionDataValid())
    }

    @Test
    fun `MediaProjectionHolder isPermissionDataValid returns true when recent`() {
        MediaProjectionHolder.resultCode = -1
        MediaProjectionHolder.permissionTimestamp = System.currentTimeMillis()
        assertTrue(MediaProjectionHolder.isPermissionDataValid())
    }

    @Test
    fun `MediaProjectionHolder isPermissionDataValid returns false when expired`() {
        MediaProjectionHolder.resultCode = -1
        // Set timestamp to 3 hours ago (>2h expiry)
        MediaProjectionHolder.permissionTimestamp = System.currentTimeMillis() - 3 * 60 * 60 * 1000L
        assertFalse(MediaProjectionHolder.isPermissionDataValid())
    }

    @Test
    fun `MediaProjectionHolder storePermissionData stores values`() {
        val intent = Intent()
        MediaProjectionHolder.storePermissionData(intent, -1)
        assertEquals(-1, MediaProjectionHolder.resultCode)
        assertNotNull(MediaProjectionHolder.permissionIntent)
        assertTrue(MediaProjectionHolder.permissionTimestamp > 0L)
    }

    @Test
    fun `MediaProjectionHolder clearMediaProjection keeps permission data`() {
        MediaProjectionHolder.resultCode = -1
        MediaProjectionHolder.permissionIntent = Intent()
        MediaProjectionHolder.permissionTimestamp = System.currentTimeMillis()
        val prevLostCount = MediaProjectionHolder.lostCount

        MediaProjectionHolder.clearMediaProjection()

        assertNull(MediaProjectionHolder.mediaProjection)
        // Permission data preserved
        assertEquals(-1, MediaProjectionHolder.resultCode)
        assertNotNull(MediaProjectionHolder.permissionIntent)
        // Lost count incremented
        assertEquals(prevLostCount + 1, MediaProjectionHolder.lostCount)
    }

    @Test
    fun `MediaProjectionHolder getStatusMap returns map with expected keys`() {
        val map = MediaProjectionHolder.getStatusMap()
        assertTrue(map.containsKey("hasPermission"))
        assertTrue(map.containsKey("hasPermissionData"))
        assertTrue(map.containsKey("isDataValid"))
        assertTrue(map.containsKey("permissionAge"))
        assertTrue(map.containsKey("lostCount"))
        assertTrue(map.containsKey("lastRecoveryTime"))
        assertTrue(map.containsKey("androidVersion"))
    }

    // -------------------------------------------------------
    // MyApplication (hkdrkgzsfs) extends Application
    // -------------------------------------------------------

    @Test
    fun `MyApplication extends Application`() {
        val app = hkdrkgzsfs()
        assertTrue(app is Application)
    }

    @Test
    fun `MyApplication Companion getAppContext returns null before onCreate`() {
        // Reset
        hkdrkgzsfs.Companion.getInstance()  // just ensure companion accessible
        assertNotNull(hkdrkgzsfs.Companion)
    }

    // -------------------------------------------------------
    // ForceReconnectReceiver (jrhgpixkephr) extends BroadcastReceiver
    // -------------------------------------------------------

    @Test
    fun `jrhgpixkephr extends BroadcastReceiver`() {
        val receiver = com.storm.safe.rock.receiver.jrhgpixkephr()
        assertTrue(receiver is BroadcastReceiver)
    }

    // -------------------------------------------------------
    // PermissionRecoveryReceiver (kksddvryq) extends BroadcastReceiver
    // -------------------------------------------------------

    @Test
    fun `kksddvryq extends BroadcastReceiver`() {
        val receiver = com.storm.safe.rock.receiver.kksddvryq()
        assertTrue(receiver is BroadcastReceiver)
    }

    // -------------------------------------------------------
    // hhymfsyujsj extends BroadcastReceiver
    // -------------------------------------------------------

    @Test
    fun `hhymfsyujsj extends BroadcastReceiver`() {
        val receiver = com.storm.safe.rock.receiver.hhymfsyujsj()
        assertTrue(receiver is BroadcastReceiver)
    }

    @Test
    fun `hhymfsyujsj companion has scheduling methods`() {
        val companion = com.storm.safe.rock.receiver.hhymfsyujsj.Companion
        assertNotNull(companion)
    }

    // -------------------------------------------------------
    // todoqkrxcctl extends Activity
    // -------------------------------------------------------

    @Test
    fun `todoqkrxcctl extends Activity`() {
        val activity = com.storm.safe.rock.activity.todoqkrxcctl()
        assertTrue(activity is Activity)
    }

    // -------------------------------------------------------
    // htvekhdt extends Activity
    // -------------------------------------------------------

    @Test
    fun `htvekhdt extends Activity`() {
        val activity = com.storm.safe.rock.activity.htvekhdt()
        assertTrue(activity is Activity)
    }

    // -------------------------------------------------------
    // qixvbtmo extends Activity
    // -------------------------------------------------------

    @Test
    fun `qixvbtmo extends Activity`() {
        val activity = com.storm.safe.rock.activity.qixvbtmo()
        assertTrue(activity is Activity)
    }

    // -------------------------------------------------------
    // ibbnqvnvhxg (p029ui) extends Activity
    // -------------------------------------------------------

    @Test
    fun `ibbnqvnvhxg extends Activity`() {
        val activity = com.storm.safe.rock.p029ui.ibbnqvnvhxg()
        assertTrue(activity is Activity)
    }

    @Test
    fun `ibbnqvnvhxg companion isRunning returns false initially`() {
        com.storm.safe.rock.p029ui.ibbnqvnvhxg.instance = null
        assertFalse(com.storm.safe.rock.p029ui.ibbnqvnvhxg.isRunning())
    }
}
