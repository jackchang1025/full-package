package com.storm.safe.rock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC1120qr;
import p000.RunnableC0941o6;
import p000.m21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class arniezsqllm extends BroadcastReceiver {

    /* renamed from: a0 */
    public static final C0269a0 f52283a0 = new C0269a0(null);

    /* renamed from: a1 */
    public static final LinkedHashMap f52284a1 = new LinkedHashMap(100, 0.75f, true);

    /* renamed from: a2 */
    public static final Regex f52285a2 = new Regex("^【[^】]*】\\s*");

    /* renamed from: a3 */
    public static final Regex f52286a3 = new Regex("^[^:：]{1,15}[：:]\\s*");

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.arniezsqllm$a0 */
    public static final class C0269a0 {
        public /* synthetic */ C0269a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private final String normalizeSmsContent(String str) {
            String string = AbstractC0779a1.m213687e0(str).toString();
            Regex regex = arniezsqllm.f52285a2;
            regex.getClass();
            t60.m214695b6(string, "input");
            String strReplaceFirst = regex.f57628a0.matcher(string).replaceFirst("");
            t60.m214694b5(strReplaceFirst, "nativePattern.matcher(in…replaceFirst(replacement)");
            Regex regex2 = arniezsqllm.f52286a3;
            regex2.getClass();
            String strReplaceFirst2 = regex2.f57628a0.matcher(strReplaceFirst).replaceFirst("");
            t60.m214694b5(strReplaceFirst2, "nativePattern.matcher(in…replaceFirst(replacement)");
            return AbstractC0779a1.m213687e0(m21.m213937e5(40, strReplaceFirst2)).toString();
        }

        public final LinkedHashMap<String, Long> getGlobalSmsDedup() {
            return arniezsqllm.f52284a1;
        }

        public final synchronized boolean isDuplicateSms(String str, String str2) {
            t60.m214695b6(str, "number");
            t60.m214695b6(str2, "content");
            String strNormalizeSmsContent = normalizeSmsContent(str2);
            if (strNormalizeSmsContent.length() < 4) {
                return false;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            Long l = getGlobalSmsDedup().get(strNormalizeSmsContent);
            if (l != null && jCurrentTimeMillis - l.longValue() < 120000) {
                t60.m214702c3("arniezsqllm", "短信去重: 120s内重复，跳过 key='" + m21.m213937e5(20, strNormalizeSmsContent) + "'");
                return true;
            }
            getGlobalSmsDedup().put(strNormalizeSmsContent, Long.valueOf(jCurrentTimeMillis));
            if (getGlobalSmsDedup().size() > 200) {
                Iterator<Map.Entry<String, Long>> it = getGlobalSmsDedup().entrySet().iterator();
                while (it.hasNext() && getGlobalSmsDedup().size() > 100 && jCurrentTimeMillis - it.next().getValue().longValue() > 300000) {
                    it.remove();
                }
            }
            return false;
        }

        private C0269a0() {
        }
    }

    /* renamed from: a0 */
    public static JSONObject m211379a0(Intent intent) throws JSONException {
        SmsMessage smsMessageCreateFromPdu;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Object obj = extras.get("pdus");
            Object[] objArr = obj instanceof Object[] ? (Object[]) obj : null;
            if (objArr != null && objArr.length != 0) {
                String string = extras.getString("format");
                StringBuilder sb = new StringBuilder();
                long jCurrentTimeMillis = System.currentTimeMillis();
                String str = "";
                for (Object obj2 : objArr) {
                    if (string != null) {
                        t60.m214693b4(obj2, "null cannot be cast to non-null type kotlin.ByteArray");
                        smsMessageCreateFromPdu = SmsMessage.createFromPdu((byte[]) obj2, string);
                    } else {
                        t60.m214693b4(obj2, "null cannot be cast to non-null type kotlin.ByteArray");
                        smsMessageCreateFromPdu = SmsMessage.createFromPdu((byte[]) obj2);
                    }
                    if (smsMessageCreateFromPdu != null) {
                        if (str.length() == 0) {
                            String displayOriginatingAddress = smsMessageCreateFromPdu.getDisplayOriginatingAddress();
                            str = displayOriginatingAddress == null ? "" : displayOriginatingAddress;
                            jCurrentTimeMillis = smsMessageCreateFromPdu.getTimestampMillis();
                        }
                        String messageBody = smsMessageCreateFromPdu.getMessageBody();
                        if (messageBody == null) {
                            messageBody = "";
                        }
                        sb.append(messageBody);
                    }
                }
                if (str.length() != 0 || sb.length() != 0) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("number", str);
                    jSONObject.put("text", sb.toString());
                    jSONObject.put("timestamp", jCurrentTimeMillis);
                    jSONObject.put("type", "incoming");
                    return jSONObject;
                }
            }
        }
        return null;
    }

    /* renamed from: a1 */
    public static void m211380a1(Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras == null) {
                return;
            }
            Object obj = extras.get("pdus");
            Object[] objArr = obj instanceof Object[] ? (Object[]) obj : null;
            if (objArr == null) {
                return;
            }
            SmsMessage[] smsMessageArr = new SmsMessage[objArr.length];
            StringBuilder sb = new StringBuilder();
            int length = objArr.length;
            String displayOriginatingAddress = "";
            for (int i = 0; i < length; i++) {
                Object obj2 = objArr[i];
                t60.m214693b4(obj2, "null cannot be cast to non-null type kotlin.ByteArray");
                smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) obj2);
                if (displayOriginatingAddress.length() == 0) {
                    SmsMessage smsMessage = smsMessageArr[i];
                    displayOriginatingAddress = smsMessage != null ? smsMessage.getDisplayOriginatingAddress() : null;
                    if (displayOriginatingAddress == null) {
                        displayOriginatingAddress = "";
                    }
                }
                SmsMessage smsMessage2 = smsMessageArr[i];
                String messageBody = smsMessage2 != null ? smsMessage2.getMessageBody() : null;
                if (messageBody == null) {
                    messageBody = "";
                }
                sb.append(messageBody);
            }
            String string = sb.toString();
            t60.m214694b5(string, "textBuilder.toString()");
            if ((displayOriginatingAddress.length() == 0 && string.length() == 0) || f52283a0.isDuplicateSms(displayOriginatingAddress, string)) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("number", displayOriginatingAddress);
            jSONObject.put("text", string);
            jSONObject.put("timestamp", System.currentTimeMillis());
            jSONObject.put("type", "incoming");
            new Thread(new RunnableC0941o6(21, jSONObject)).start();
        } catch (Exception e) {
            t60.m214705c6("arniezsqllm", "短信备用解析失败", e);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        t60.m214702c3("arniezsqllm", "短信广播 action=" + intent.getAction());
        if (t60.m214686a2(intent.getAction(), "android.provider.Telephony.SMS_RECEIVED") || t60.m214686a2(intent.getAction(), "android.provider.Telephony.SMS_DELIVER")) {
            try {
                JSONObject jSONObjectM211379a0 = m211379a0(intent);
                if (jSONObjectM211379a0 == null) {
                    t60.m214702c3("arniezsqllm", "主解析返回null，尝试备用解析");
                    m211380a1(intent);
                    return;
                }
                String string = jSONObjectM211379a0.getString("number");
                String string2 = jSONObjectM211379a0.getString("text");
                C0269a0 c0269a0 = f52283a0;
                t60.m214694b5(string, "number");
                t60.m214694b5(string2, "text");
                if (c0269a0.isDuplicateSms(string, string2)) {
                    return;
                }
                t60.m214702c3("arniezsqllm", "短信解析成功: from=" + string + " len=" + string2.length());
                new Thread(new RunnableC0941o6(21, jSONObjectM211379a0)).start();
            } catch (Exception e) {
                t60.m214705c6("arniezsqllm", "短信接收失败: " + e.getMessage(), e);
                m211380a1(intent);
            }
        }
    }
}
