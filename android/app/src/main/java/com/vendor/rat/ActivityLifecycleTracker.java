package com.vendor.rat;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.vendor.rat.keepalive.KeepAliveManager;

/**
 * Activity 生命周期追踪器
 * 用于检测应用前后台切换
 */
public class ActivityLifecycleTracker implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "LifecycleTracker";
    private int activityCount = 0;
    private boolean isAppInForeground = false;

    @Override
    public void onActivityStarted(Activity activity) {
        activityCount++;
        if (!isAppInForeground) {
            isAppInForeground = true;
            Log.d(TAG, "App entered foreground");
        }
    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount <= 0) {
            isAppInForeground = false;
            Log.d(TAG, "App entered background");
            // 进入后台时确保服务存活
            KeepAliveManager.getInstance()
                .ensureServicesRunning(MainApplication.getApplication());
        }
    }

    @Override public void onActivityCreated(Activity a, Bundle b) {}
    @Override public void onActivityResumed(Activity activity) {}
    @Override public void onActivityPaused(Activity activity) {}
    @Override public void onActivitySaveInstanceState(Activity a, Bundle b) {}
    @Override public void onActivityDestroyed(Activity activity) {}
}
