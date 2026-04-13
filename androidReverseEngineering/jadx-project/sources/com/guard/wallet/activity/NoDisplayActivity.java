package com.guard.wallet.activity;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.utils.AbstractC0252h;

/* loaded from: classes.dex */
public class NoDisplayActivity extends Activity {

    /* renamed from: a */
    public static volatile NoDisplayActivity f190a;

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(R.style.Theme.NoDisplay);
        f190a = this;
        AbstractC0252h.m688I();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("NoDisplayActivity", "NoDisplayActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (f190a != null) {
            synchronized (NoDisplayActivity.class) {
                f190a = null;
            }
        }
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("NoDisplayActivity", "NoDisplayActivity onResume:" + Thread.currentThread().getId());
        finish();
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        Log.d("NoDisplayActivity", "NoDisplayActivity onStart:" + Thread.currentThread().getId());
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("NoDisplayActivity", "NoDisplayActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
