package com.storm.safe.rock.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import com.storm.safe.rock.service.dqtvuisjd;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class htvekhdt extends Activity {

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.htvekhdt$a0 */
    public static final class C0245a0 {
        public /* synthetic */ C0245a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0245a0() {
        }
    }

    static {
        new C0245a0(null);
    }

    /* renamed from: a0 */
    public final void m211186a0(boolean z) {
        dqtvuisjd.f52358m1.setPermissionRequesting(false);
        try {
            Intent intent = new Intent(getPackageName() + ".STORAGE_PERMISSION_RESULT");
            intent.putExtra("granted", z);
            intent.setPackage(getPackageName());
            sendBroadcast(intent);
        } catch (Exception e) {
            tz0.m214807a7("发送广播失败: ", e.getMessage(), "StoragePermission");
        }
    }

    @Override // android.app.Activity
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 153) {
            m211186a0(Build.VERSION.SDK_INT >= 30 ? Environment.isExternalStorageManager() : false);
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            dqtvuisjd.f52358m1.setPermissionRequesting(true);
            int i = Build.VERSION.SDK_INT;
            if (i >= 30 ? Environment.isExternalStorageManager() : AbstractC1117qo.m214411a7(this, "android.permission.READ_EXTERNAL_STORAGE") == 0) {
                m211186a0(true);
                finish();
                return;
            }
            if (i < 30) {
                AbstractC1117qo.m214459f8(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 152);
                return;
            }
            try {
                Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 153);
            } catch (Exception unused) {
                t60.m214726f4("StoragePermission", "直接跳转失败，使用备用方案");
                startActivityForResult(new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION"), 153);
            }
        } catch (Exception e) {
            t60.m214704c5("StoragePermission", "权限请求异常: " + e.getMessage());
            m211186a0(false);
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        super.onDestroy();
        dqtvuisjd.f52358m1.setPermissionRequesting(false);
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        t60.m214695b6(strArr, "permissions");
        t60.m214695b6(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 152) {
            m211186a0(!(iArr.length == 0) && iArr[0] == 0);
            finish();
        }
    }
}
