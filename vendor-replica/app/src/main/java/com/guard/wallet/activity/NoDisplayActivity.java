package com.guard.wallet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.guard.wallet.utils.SharedPrefsManager;

/**
 * NoDisplayActivity — 不可见 Activity，仅作为 Context 提供者。
 *
 * vendor activity/NoDisplayActivity.java 翻译。
 *
 * 使用 Theme_NoDisplay 主题，不绘制任何 UI。
 * onResume 中立即 finish() 自身，生命周期极短。
 * 通过 volatile + synchronized 的 singleton 模式对外暴露实例引用，
 * 供 DeviceUtils.b() 等需要 Activity Context 的场景回退使用。
 */
public class NoDisplayActivity extends Activity {
    /** 单例引用，volatile 保证多线程可见性 */
    public static volatile NoDisplayActivity instance;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(16973909); // android.R.style.Theme_NoDisplay
        instance = this;
        SharedPrefsManager.I();
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.d("NoDisplayActivity",
                "NoDisplayActivity onResume:" + Thread.currentThread().getId());
        this.finish();
    }

    @Override
    public final void onStart() {
        super.onStart();
        Log.d("NoDisplayActivity",
                "NoDisplayActivity onStart:" + Thread.currentThread().getId());
    }

    @Override
    public final void onStop() {
        Log.d("NoDisplayActivity",
                "NoDisplayActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }

    @Override
    public final void onDestroy() {
        Log.d("NoDisplayActivity",
                "NoDisplayActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (instance != null) {
            synchronized (NoDisplayActivity.class) {
                instance = null;
            }
        }
    }

    @Override
    public final void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
