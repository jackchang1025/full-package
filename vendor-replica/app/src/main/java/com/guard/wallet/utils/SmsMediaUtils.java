package com.guard.wallet.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

/**
 * SMS/媒体/通讯录工具类。
 */
public final class SmsMediaUtils {
    private static final String TAG = "SmsMediaUtils";

    private SmsMediaUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.A(String) — 删除指定号码的短信 */
    public static void deleteSmsFromAddress(String address) {
        if (address == null || address.isEmpty()) return;
        Context context = ctx();
        if (context == null) return;
        try {
            Uri smsUri = Uri.parse("content://sms");
            context.getContentResolver().delete(smsUri, "address=?", new String[]{address});
        } catch (Exception e) {
            Log.e(TAG, "deleteSms error", e);
        }
    }

    /** g.B(String, String) — 通过 URI 删除媒体文件 */
    public static boolean deleteMediaFile(String uriString, String selection) {
        Context context = ctx();
        if (context == null || uriString == null) return false;
        try {
            Uri uri = Uri.parse(uriString);
            int deleted = context.getContentResolver().delete(uri, selection, null);
            return deleted > 0;
        } catch (Exception e) {
            Log.e(TAG, "deleteMedia error", e);
            return false;
        }
    }

    /** g.f(String) — 拨打电话 */
    public static boolean makePhoneCall(String number) {
        Context context = ctx();
        if (context == null || number == null || number.isEmpty()) return false;
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "makePhoneCall error", e);
            return false;
        }
    }
}
