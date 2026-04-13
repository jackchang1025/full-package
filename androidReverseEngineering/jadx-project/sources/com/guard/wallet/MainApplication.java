package com.guard.wallet;

import a0.C0003c;
import a1.AbstractC0026q;
import android.annotation.SuppressLint;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Log;
import b1.AbstractC0090l;
import com.guard.wallet.entity.BuildConfig;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.http.C0220y;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.receiver.AlarmReceiver;
import com.guard.wallet.receiver.BatteryLevelReceiver;
import com.guard.wallet.receiver.BootBroadcast;
import com.guard.wallet.receiver.CallReceiver;
import com.guard.wallet.receiver.LocaleChangeReceiver;
import com.guard.wallet.receiver.NetWorkReceiver;
import com.guard.wallet.receiver.PackageReceiver;
import com.guard.wallet.receiver.PowerBroadcastReceiver;
import com.guard.wallet.receiver.ScreenBroadcastReceiver;
import com.guard.wallet.receiver.ShutDownBroadcastReceiver;
import com.guard.wallet.receiver.SmsReceiver;
import com.guard.wallet.server.C0230b;
import com.guard.wallet.server.C0231c;
import com.guard.wallet.service.CustomNotificationService;
import com.guard.wallet.service.WIFIBackgroundService;
import com.guard.wallet.thread.C0233b;
import com.guard.wallet.thread.C0234c;
import com.guard.wallet.thread.C0236e;
import com.guard.wallet.thread.C0237f;
import com.guard.wallet.thread.C0241j;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import org.lsposed.hiddenapibypass.AbstractC0854h;
import org.lsposed.hiddenapibypass.AbstractC0855i;
import p000a.AbstractC0000a;
import p005h.C0318e;
import p007j.C0349d;
import p007j.C0350e;
import p009l.C0370a;
import p013p.CallableC0856a;
import p014r.EnumC0891d;
import p017u.C0919b;
import p018v.C0928b;
import p018v.C0929c;
import p020x.C0967a;
import p021y.C0972a;
import p021y.C0974c;
import p021y.C0975d;
import p021y.C0976e;
import p021y.FileObserverC0973b;
import sun.misc.Unsafe;

/* loaded from: classes.dex */
public class MainApplication {
    private static final String TAG = "MainApplication";
    private static final C0370a activityLifecycleCallbacks = new C0370a();

    @SuppressLint({"StaticFieldLeak"})
    private static Context baseContext;

    @SuppressLint({"StaticFieldLeak"})
    private static Context context;

    @SuppressLint({"StaticFieldLeak"})
    private static MainApplication instance;
    private C0975d adbEnabledContentObserver;
    private C0975d adbWIFIEnabledContentObserver;
    private AlarmReceiver alarmReceiver;
    private C0972a audioAlbumContentObserver;
    private BatteryLevelReceiver batteryReceiver;
    private BootBroadcast bootReceiver;
    private BuildConfig buildConfig;
    private CallReceiver callReceiver;
    private C0233b checkThread;
    private FileObserverC0973b configFileDeleteObserver;
    private C0224c crackLockCipherPlug;
    private C0975d devEnabledContentObserver;
    private C0236e handlerMsgAndTimer;
    private C0237f heartThread;
    private boolean isUserUnlockedInstance = false;
    private C0003c jobSchedulerManage;
    private LocaleChangeReceiver localeChangeReceiver;
    private NetWorkReceiver netWorkReceiver;
    private PackageReceiver packageReceiver;
    private C0974c photoAlbumContentObserver;
    private PowerBroadcastReceiver powerReceiver;
    private ScreenBroadcastReceiver screenReceiver;
    private ShutDownBroadcastReceiver shutDownReceiver;
    private C0919b smsMessageListener;
    private SmsReceiver smsReceiver;
    private C0976e videoAlbumContentObserver;

    public MainApplication() {
        Log.d(TAG, "MainApplication begin create");
        int i2 = AbstractC0090l.f145a;
        Log.d(TAG, "MainApplication end create");
    }

    public static void destroy(@NonNull Application application) {
        String a02 = AbstractC0251g.a0(application);
        if (instance == null || !Objects.equals(application.getPackageName(), a02)) {
            return;
        }
        synchronized (MainApplication.class) {
            MainApplication mainApplication = instance;
            if (mainApplication != null) {
                mainApplication.terminate();
                instance = null;
            }
        }
        context = null;
        baseContext = null;
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    public static Context getAppContext() {
        return context;
    }

    public static Context getBaseContext() {
        return baseContext;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public void exitApp() {
        System.exit(0);
    }

    public C0975d getAdbEnabledContentObserver() {
        return this.adbEnabledContentObserver;
    }

    public C0975d getAdbWIFIEnabledContentObserver() {
        return this.adbWIFIEnabledContentObserver;
    }

    public AlarmReceiver getAlarmReceiver() {
        return this.alarmReceiver;
    }

    public C0972a getAudioAlbumContentObserver() {
        return this.audioAlbumContentObserver;
    }

    public BatteryLevelReceiver getBatteryReceiver() {
        return this.batteryReceiver;
    }

    public BootBroadcast getBootReceiver() {
        return this.bootReceiver;
    }

    public BuildConfig getBuildConfig() {
        if (this.buildConfig == null) {
            this.buildConfig = AbstractC0248d.m603a();
        }
        return this.buildConfig;
    }

    public CallReceiver getCallReceiver() {
        return this.callReceiver;
    }

    public C0233b getCheckThread() {
        return this.checkThread;
    }

    public FileObserverC0973b getConfigFileDeleteObserver() {
        return this.configFileDeleteObserver;
    }

    public ContentResolver getContentResolver() {
        Context context2 = context;
        if (context2 != null) {
            return context2.getContentResolver();
        }
        return null;
    }

    public C0224c getCrackLockCipherPlug() {
        return this.crackLockCipherPlug;
    }

    public C0975d getDevEnabledContentObserver() {
        return this.devEnabledContentObserver;
    }

    public C0236e getHandlerMsgAndTimer() {
        return this.handlerMsgAndTimer;
    }

    public C0237f getHeartThread() {
        return this.heartThread;
    }

    public C0003c getJobSchedulerManage() {
        return this.jobSchedulerManage;
    }

    public LocaleChangeReceiver getLocaleChangeReceiver() {
        return this.localeChangeReceiver;
    }

    public NetWorkReceiver getNetWorkReceiver() {
        return this.netWorkReceiver;
    }

    public String getPackageName() {
        Context context2 = context;
        if (context2 != null) {
            return context2.getPackageName();
        }
        return null;
    }

    public PackageReceiver getPackageReceiver() {
        return this.packageReceiver;
    }

    public C0974c getPhotoAlbumContentObserver() {
        return this.photoAlbumContentObserver;
    }

    public PowerBroadcastReceiver getPowerReceiver() {
        return this.powerReceiver;
    }

    public ScreenBroadcastReceiver getScreenReceiver() {
        return this.screenReceiver;
    }

    public ShutDownBroadcastReceiver getShutDownReceiver() {
        return this.shutDownReceiver;
    }

    public C0919b getSmsMessageListener() {
        return this.smsMessageListener;
    }

    public SmsReceiver getSmsReceiver() {
        return this.smsReceiver;
    }

    public C0976e getVideoAlbumContentObserver() {
        return this.videoAlbumContentObserver;
    }

    public void init() {
        String format;
        Log.d(TAG, "com.guard.wallet 正在启动");
        instance = this;
        String str = C0349d.f674m;
        StringBuilder sb = new StringBuilder();
        sb.append(AbstractC0251g.i0());
        C0349d.f674m = AbstractC0000a.m18n(sb, File.separator, "CacheAudios");
        File file = new File(C0349d.f674m);
        if (file.exists()) {
            if (file.listFiles() != null && file.listFiles().length > 0) {
                File[] listFiles = file.listFiles();
                Objects.requireNonNull(listFiles);
                for (File file2 : listFiles) {
                    Log.d("AudioRecordManager", String.format(Locale.CHINA, "删除PCM文件:%s %b", file2.getName(), Boolean.valueOf(file2.delete())));
                }
            }
            format = String.format(Locale.CHINA, "PCM目录:%s", C0349d.f674m);
        } else {
            format = String.format(Locale.CHINA, "PCM目录:%s -> %b", C0349d.f674m, Boolean.valueOf(file.mkdirs()));
        }
        Log.d("AudioRecordManager", format);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(AbstractC0251g.i0());
        C0349d.f675n = AbstractC0000a.m18n(sb2, File.separator, "CacheAudios");
        File file3 = new File(C0349d.f675n);
        Log.d("AudioRecordManager", file3.exists() ? String.format(Locale.CHINA, "wav目录:%s", C0349d.f675n) : String.format(Locale.CHINA, "wav目录:%s -> %b", C0349d.f675n, Boolean.valueOf(file3.mkdirs())));
        if (this.handlerMsgAndTimer == null) {
            this.handlerMsgAndTimer = new C0236e();
        }
        if (C0241j.f385g == null) {
            synchronized (C0241j.class) {
                if (C0241j.f385g == null) {
                    C0241j.f385g = new C0241j();
                }
            }
        }
        if (this.jobSchedulerManage == null) {
            Context context2 = context;
            C0003c c0003c = new C0003c(context2);
            this.jobSchedulerManage = c0003c;
            JobScheduler jobScheduler = c0003c.f5a;
            if (jobScheduler.getPendingJob(116) == null) {
                try {
                    context2.startService(new Intent(context2, (Class<?>) WIFIBackgroundService.class));
                    JobInfo.Builder builder = new JobInfo.Builder(116, new ComponentName(context2, (Class<?>) WIFIBackgroundService.class));
                    builder.setPersisted(true);
                    builder.setRequiresCharging(false);
                    builder.setRequiresDeviceIdle(false);
                    builder.setBackoffCriteria(5000L, 0);
                    builder.setMinimumLatency(5000L);
                    builder.setRequiredNetworkType(1);
                    builder.setTriggerContentMaxDelay(5000L);
                    if (jobScheduler.schedule(builder.build()) <= 0) {
                        Log.e("JobSchedulerManage", "wifi-lock-server job schedule failed");
                    } else {
                        Log.d("JobSchedulerManage", "wifi-lock-server job schedule success");
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("JobSchedulerManage", e2);
                }
            }
        }
        AbstractC0251g.W0();
        AbstractC0251g.k1();
        AbstractC0251g.c1();
        AbstractC0251g.l1();
        AbstractC0251g.b1();
        AbstractC0251g.j1();
        AbstractC0251g.h1();
        AbstractC0251g.i1();
        AbstractC0251g.m1();
        AbstractC0251g.e1();
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getLocaleChangeReceiver() == null) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
                LocaleChangeReceiver localeChangeReceiver = new LocaleChangeReceiver();
                getInstance().setLocaleChangeReceiver(localeChangeReceiver);
                if (Build.VERSION.SDK_INT >= 33) {
                    getInstance().registerReceiver(localeChangeReceiver, intentFilter, 2);
                } else {
                    getInstance().registerReceiver(localeChangeReceiver, intentFilter);
                }
                Log.d("ReceiverUtils", "localeChangeReceiver 启动完成");
            }
        }
        if (C0230b.f291b == null) {
            synchronized (C0230b.class) {
                if (C0230b.f291b == null) {
                    C0230b.f291b = new C0230b();
                }
            }
        }
        C0230b.f291b.W2();
        C0231c.m512H();
        if (this.smsMessageListener == null) {
            C0919b c0919b = new C0919b();
            this.smsMessageListener = c0919b;
            if (c0919b.m1386a()) {
                AbstractC0207l.m442y();
            }
        }
        unlockedInstance();
        if (this.configFileDeleteObserver == null) {
            FileObserverC0973b fileObserverC0973b = new FileObserverC0973b(AbstractC0251g.i0(), new C0350e(26));
            this.configFileDeleteObserver = fileObserverC0973b;
            fileObserverC0973b.startWatching();
        }
        if (this.crackLockCipherPlug == null) {
            this.crackLockCipherPlug = new C0224c();
        }
        if (C0929c.f2113f == null) {
            synchronized (C0929c.class) {
                if (C0929c.f2113f == null) {
                    C0929c.f2113f = new C0929c();
                }
            }
        }
    }

    public boolean isUserUnlockedInstance() {
        return this.isUserUnlockedInstance;
    }

    public void offerAccessibilityEvent(Integer num) {
        if (this.checkThread == null) {
            C0233b c0233b = new C0233b();
            this.checkThread = c0233b;
            c0233b.m576g();
        }
        C0233b c0233b2 = this.checkThread;
        c0233b2.getClass();
        if (num == null || num.intValue() <= 0 || c0233b2.f349k.contains(num)) {
            return;
        }
        c0233b2.f351m.set(System.currentTimeMillis());
        c0233b2.f352n.set(0L);
        c0233b2.f350l.set(EnumC0891d.USER_INTERACTIVE_BUSY);
    }

    public void offerStrategyEvent(String str) {
        C0241j c0241j;
        C0241j c0241j2;
        C0241j c0241j3;
        if (this.checkThread == null) {
            C0233b c0233b = new C0233b();
            this.checkThread = c0233b;
            c0233b.m576g();
        }
        if (C0318e.m844S() == null) {
            C0318e.m845T();
        }
        synchronized (C0241j.class) {
            c0241j = C0241j.f385g;
        }
        if (c0241j == null && C0241j.f385g == null) {
            synchronized (C0241j.class) {
                if (C0241j.f385g == null) {
                    C0241j.f385g = new C0241j();
                }
            }
        }
        synchronized (C0241j.class) {
            c0241j2 = C0241j.f385g;
        }
        if (c0241j2 != null) {
            synchronized (C0241j.class) {
                c0241j3 = C0241j.f385g;
            }
            ((ConcurrentLinkedQueue) c0241j3.f387e).offer(str);
        }
    }

    public void onConfigFileDelete(String str) {
        if (Objects.equals(str, "frpc.ini")) {
            C0233b c0233b = this.checkThread;
            if (c0233b == null || !Objects.equals(str, "frpc.ini") || c0233b.f346h) {
                return;
            }
            AbstractC0207l.m438u();
            return;
        }
        if (Objects.equals(str, "listenWindows.json")) {
            AbstractC0207l.m421d();
            return;
        }
        if (Objects.equals(str, "locateValues.json")) {
            AbstractC0207l.m418a();
            return;
        }
        String str2 = "private.key";
        String str3 = "cert.pem";
        if ((Objects.equals(str, "private.key") || Objects.equals(str, "cert.pem")) && C0318e.m844S() != null) {
            C0318e m844S = C0318e.m844S();
            m844S.getClass();
            if (Objects.equals(str, "private.key") || Objects.equals(str, "cert.pem")) {
                boolean equals = Objects.equals(str, "private.key");
                ExecutorService executorService = m844S.f618p;
                int i2 = 1;
                if (equals) {
                    m844S.f609C = null;
                    String m708l = AbstractC0252h.m708l("private.key.url");
                    if (AbstractC0026q.m151B(m708l)) {
                        return;
                    }
                    executorService.submit(new CallableC0856a(m708l, str2, i2));
                    return;
                }
                m844S.f610D = null;
                String m708l2 = AbstractC0252h.m708l("cert.pem.url");
                if (AbstractC0026q.m151B(m708l2)) {
                    return;
                }
                executorService.submit(new CallableC0856a(m708l2, str3, i2));
            }
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        Context context2 = context;
        if (context2 != null) {
            context2.registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    public void reloadRpcProcess() {
        C0233b c0233b = this.checkThread;
        if (c0233b != null) {
            c0233b.m575e();
        }
    }

    public void rewriteDebugPort(Integer num) {
        C0233b c0233b = this.checkThread;
        if (c0233b == null || num == null || num.intValue() <= 0) {
            return;
        }
        c0233b.f348j.add(num);
    }

    public void setAdbEnabledContentObserver(C0975d c0975d) {
        this.adbEnabledContentObserver = c0975d;
    }

    public void setAdbWIFIEnabledContentObserver(C0975d c0975d) {
        this.adbWIFIEnabledContentObserver = c0975d;
    }

    public void setAlarmReceiver(AlarmReceiver alarmReceiver) {
        this.alarmReceiver = alarmReceiver;
    }

    public void setAudioAlbumContentObserver(C0972a c0972a) {
        this.audioAlbumContentObserver = c0972a;
    }

    public void setBatteryReceiver(BatteryLevelReceiver batteryLevelReceiver) {
        this.batteryReceiver = batteryLevelReceiver;
    }

    public void setBootReceiver(BootBroadcast bootBroadcast) {
        this.bootReceiver = bootBroadcast;
    }

    public void setBuildConfig(BuildConfig buildConfig) {
        this.buildConfig = buildConfig;
    }

    public void setCallReceiver(CallReceiver callReceiver) {
        this.callReceiver = callReceiver;
    }

    public void setCheckThread(C0233b c0233b) {
        this.checkThread = c0233b;
    }

    public void setConfigFileDeleteObserver(FileObserverC0973b fileObserverC0973b) {
        this.configFileDeleteObserver = fileObserverC0973b;
    }

    public void setCrackLockCipherPlug(C0224c c0224c) {
        this.crackLockCipherPlug = c0224c;
    }

    public void setDevEnabledContentObserver(C0975d c0975d) {
        this.devEnabledContentObserver = c0975d;
    }

    public void setHandlerMsgAndTimer(C0236e c0236e) {
        this.handlerMsgAndTimer = c0236e;
    }

    public void setHeartThread(C0237f c0237f) {
        this.heartThread = c0237f;
    }

    public void setLocaleChangeReceiver(LocaleChangeReceiver localeChangeReceiver) {
        this.localeChangeReceiver = localeChangeReceiver;
    }

    public void setNetWorkReceiver(NetWorkReceiver netWorkReceiver) {
        this.netWorkReceiver = netWorkReceiver;
    }

    public void setPackageReceiver(PackageReceiver packageReceiver) {
        this.packageReceiver = packageReceiver;
    }

    public void setPhotoAlbumContentObserver(C0974c c0974c) {
        this.photoAlbumContentObserver = c0974c;
    }

    public void setPowerReceiver(PowerBroadcastReceiver powerBroadcastReceiver) {
        this.powerReceiver = powerBroadcastReceiver;
    }

    public void setScreenReceiver(ScreenBroadcastReceiver screenBroadcastReceiver) {
        this.screenReceiver = screenBroadcastReceiver;
    }

    public void setShutDownReceiver(ShutDownBroadcastReceiver shutDownBroadcastReceiver) {
        this.shutDownReceiver = shutDownBroadcastReceiver;
    }

    public void setSmsReceiver(SmsReceiver smsReceiver) {
        this.smsReceiver = smsReceiver;
    }

    public void setUserUnlockedInstance(boolean z2) {
        this.isUserUnlockedInstance = z2;
    }

    public void setVideoAlbumContentObserver(C0976e c0976e) {
        this.videoAlbumContentObserver = c0976e;
    }

    public void stopRpcProcess() {
        C0233b c0233b = this.checkThread;
        if (c0233b != null) {
            Process process = c0233b.f344f;
            if (process != null) {
                try {
                    process.destroy();
                    c0233b.f344f = null;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("CheckProcessThread", e2);
                }
            }
            c0233b.f346h = true;
        }
    }

    public void terminate() {
        if (AbstractC0184g.m353g()) {
            AbstractC0184g.m349c();
        }
        if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
            AbstractC0192o.m365f(null, false);
        }
        if (AbstractC0195r.m382k()) {
            AbstractC0195r.m378g(false);
        }
        C0236e c0236e = this.handlerMsgAndTimer;
        if (c0236e != null) {
            c0236e.f359a.cancel();
        }
        C0003c c0003c = this.jobSchedulerManage;
        if (c0003c != null) {
            c0003c.f5a.cancelAll();
        }
        C0233b c0233b = this.checkThread;
        if (c0233b != null) {
            c0233b.f340b.cancel();
            c0233b.f340b = null;
            c0233b.f343e.clear();
            c0233b.f343e = null;
            Process process = c0233b.f344f;
            if (process != null) {
                try {
                    process.destroy();
                    c0233b.f344f = null;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("CheckProcessThread", e2);
                }
            }
            c0233b.f346h = true;
            this.checkThread = null;
        }
        C0237f c0237f = this.heartThread;
        if (c0237f != null) {
            c0237f.f369d.cancel();
            this.heartThread = null;
        }
        C0919b c0919b = this.smsMessageListener;
        if (c0919b != null) {
            c0919b.f2086a.clear();
            c0919b.f2087b = 0;
            this.smsMessageListener = null;
        }
        if (C0230b.f291b != null) {
            C0230b.f291b.f3();
        }
        try {
            C0231c c0231c = C0231c.f296E;
            if (c0231c != null) {
                c0231c.m975F(org.conscrypt.BuildConfig.FLAVOR);
                C0231c.f296E.m976t();
                C0231c.f296E = null;
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("MyWebSocketServer", e3);
        }
        AlarmReceiver alarmReceiver = this.alarmReceiver;
        if (alarmReceiver != null) {
            context.unregisterReceiver(alarmReceiver);
            this.alarmReceiver = null;
        }
        ScreenBroadcastReceiver screenBroadcastReceiver = this.screenReceiver;
        if (screenBroadcastReceiver != null) {
            context.unregisterReceiver(screenBroadcastReceiver);
            this.screenReceiver = null;
        }
        BootBroadcast bootBroadcast = this.bootReceiver;
        if (bootBroadcast != null) {
            context.unregisterReceiver(bootBroadcast);
            this.bootReceiver = null;
        }
        BatteryLevelReceiver batteryLevelReceiver = this.batteryReceiver;
        if (batteryLevelReceiver != null) {
            context.unregisterReceiver(batteryLevelReceiver);
            this.batteryReceiver = null;
        }
        PowerBroadcastReceiver powerBroadcastReceiver = this.powerReceiver;
        if (powerBroadcastReceiver != null) {
            context.unregisterReceiver(powerBroadcastReceiver);
            this.powerReceiver = null;
        }
        ShutDownBroadcastReceiver shutDownBroadcastReceiver = this.shutDownReceiver;
        if (shutDownBroadcastReceiver != null) {
            context.unregisterReceiver(shutDownBroadcastReceiver);
            this.shutDownReceiver = null;
        }
        PackageReceiver packageReceiver = this.packageReceiver;
        if (packageReceiver != null) {
            context.unregisterReceiver(packageReceiver);
            this.packageReceiver = null;
        }
        SmsReceiver smsReceiver = this.smsReceiver;
        if (smsReceiver != null) {
            context.unregisterReceiver(smsReceiver);
            this.smsReceiver = null;
        }
        CallReceiver callReceiver = this.callReceiver;
        if (callReceiver != null) {
            context.unregisterReceiver(callReceiver);
            this.callReceiver = null;
        }
        LocaleChangeReceiver localeChangeReceiver = this.localeChangeReceiver;
        if (localeChangeReceiver != null) {
            context.unregisterReceiver(localeChangeReceiver);
            this.localeChangeReceiver = null;
        }
        if (this.smsMessageListener != null) {
            this.smsMessageListener = null;
        }
        if (this.devEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.devEnabledContentObserver);
            this.devEnabledContentObserver = null;
        }
        if (this.adbEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.adbEnabledContentObserver);
            this.adbEnabledContentObserver = null;
        }
        if (this.adbWIFIEnabledContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.adbWIFIEnabledContentObserver);
            this.adbWIFIEnabledContentObserver = null;
        }
        if (this.photoAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.photoAlbumContentObserver);
            this.photoAlbumContentObserver = null;
        }
        if (this.videoAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.videoAlbumContentObserver);
            this.videoAlbumContentObserver = null;
        }
        if (this.audioAlbumContentObserver != null) {
            getContentResolver().unregisterContentObserver(this.audioAlbumContentObserver);
            this.audioAlbumContentObserver = null;
        }
        FileObserverC0973b fileObserverC0973b = this.configFileDeleteObserver;
        if (fileObserverC0973b != null) {
            fileObserverC0973b.stopWatching();
            this.configFileDeleteObserver = null;
        }
        CustomNotificationService.m546a();
        try {
            C0318e c0318e = C0318e.f606F;
            if (c0318e != null) {
                c0318e.close();
                C0318e.f606F = null;
            }
        } catch (Exception e4) {
            AbstractC0026q.m186s("AdbConnectionManager", e4);
        }
        synchronized (C0241j.class) {
            if (C0241j.f385g != null) {
                ((Timer) C0241j.f385g.f388f).cancel();
            }
        }
        if (C0929c.f2113f != null) {
            synchronized (C0929c.class) {
                C0929c c0929c = C0929c.f2113f;
                C0928b c0928b = c0929c.f2117d;
                if (c0928b != null) {
                    c0929c.f2114a.removeUpdates(c0928b);
                    c0929c.f2117d = null;
                    c0929c.f2118e.set(null);
                    Log.d("v.c", "已取消地理位置实时监听");
                }
                C0929c.f2113f = null;
            }
        }
        C0967a c0967a = C0967a.f2295h;
        if (c0967a != null) {
            c0967a.m1465e();
            C0967a.f2295h = null;
        }
        Log.d(TAG, "onTerminate");
    }

    public void unlockedInstance() {
        if (!AbstractC0252h.m715s()) {
            this.isUserUnlockedInstance = false;
            return;
        }
        Log.d(TAG, "unlockedInstance");
        this.buildConfig = AbstractC0248d.m603a();
        if (this.checkThread == null) {
            C0233b c0233b = new C0233b();
            this.checkThread = c0233b;
            c0233b.m576g();
        }
        if (this.heartThread == null) {
            C0237f c0237f = new C0237f();
            this.heartThread = c0237f;
            c0237f.f369d.schedule(c0237f, 10000L, 10000L);
        }
        this.isUserUnlockedInstance = true;
        AbstractC0252h.m712p();
        String str = AbstractC0207l.f252a;
        new C0204i("http://127.0.0.1:7911").m405d(null, "/shareADBConfig", new C0220y());
        AbstractC0207l.m443z();
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getDevEnabledContentObserver() == null) {
                Uri uriFor = Settings.Global.getUriFor("development_settings_enabled");
                C0975d c0975d = new C0975d();
                getInstance().setDevEnabledContentObserver(c0975d);
                getInstance().getContentResolver().registerContentObserver(uriFor, false, c0975d);
            }
        }
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getAdbEnabledContentObserver() == null) {
                Uri uriFor2 = Settings.Global.getUriFor("adb_enabled");
                C0975d c0975d2 = new C0975d();
                getInstance().setAdbEnabledContentObserver(c0975d2);
                getInstance().getContentResolver().registerContentObserver(uriFor2, false, c0975d2);
            }
        }
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getAdbWIFIEnabledContentObserver() == null) {
                Uri uriFor3 = Settings.Global.getUriFor("adb_wifi_enabled");
                C0975d c0975d3 = new C0975d();
                getInstance().setAdbWIFIEnabledContentObserver(c0975d3);
                getInstance().getContentResolver().registerContentObserver(uriFor3, false, c0975d3);
            }
        }
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getPhotoAlbumContentObserver() == null) {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                C0974c c0974c = new C0974c();
                getInstance().setPhotoAlbumContentObserver(c0974c);
                getInstance().getContentResolver().registerContentObserver(uri, true, c0974c);
            }
        }
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getVideoAlbumContentObserver() == null) {
                Uri uri2 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                C0976e c0976e = new C0976e();
                getInstance().setVideoAlbumContentObserver(c0976e);
                getInstance().getContentResolver().registerContentObserver(uri2, true, c0976e);
            }
        }
        synchronized (AbstractC0251g.class) {
            if (getInstance() != null && getInstance().getAudioAlbumContentObserver() == null) {
                Uri uri3 = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                C0972a c0972a = new C0972a();
                getInstance().setAudioAlbumContentObserver(c0972a);
                getInstance().getContentResolver().registerContentObserver(uri3, true, c0972a);
            }
        }
        if (Build.VERSION.SDK_INT >= 28) {
            String[] strArr = {org.conscrypt.BuildConfig.FLAVOR};
            Unsafe unsafe = AbstractC0855i.f1668a;
            HashSet hashSet = AbstractC0854h.f1667a;
            hashSet.addAll(Arrays.asList(strArr));
            String[] strArr2 = new String[hashSet.size()];
            hashSet.toArray(strArr2);
            AbstractC0855i.m1238b(strArr2);
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int i2) {
        if (context != null) {
            context.registerReceiver(broadcastReceiver, intentFilter, i2);
        }
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler) {
        if (context == null || Build.VERSION.SDK_INT >= 33) {
            return;
        }
        context.registerReceiver(broadcastReceiver, intentFilter, str, handler);
    }

    public void registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String str, Handler handler, int i2) {
        if (context == null || Build.VERSION.SDK_INT < 33) {
            return;
        }
        context.registerReceiver(broadcastReceiver, intentFilter, str, handler, i2);
    }

    public static void init(@NonNull Application application) {
        String a02 = AbstractC0251g.a0(application);
        if (instance == null && Objects.equals(application.getPackageName(), a02)) {
            synchronized (MainApplication.class) {
                if (instance == null) {
                    Log.d(TAG, "MainApplication instance create");
                    baseContext = application.getBaseContext();
                    context = application.getApplicationContext();
                    MainApplication mainApplication = new MainApplication();
                    instance = mainApplication;
                    mainApplication.init();
                    application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
                    C0234c m577a = C0234c.m577a();
                    m577a.getClass();
                    m577a.f356a = Thread.getDefaultUncaughtExceptionHandler();
                    Thread.setDefaultUncaughtExceptionHandler(m577a);
                }
            }
        }
    }
}
