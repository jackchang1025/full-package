package com.storm.safe.rock.activity;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.storm.safe.rock.service.dqtvuisjd;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.RunnableC0941o6;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class todoqkrxcctl extends Activity {

    /* renamed from: a0 */
    public static final /* synthetic */ int f51922a0 = 0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.todoqkrxcctl$a0 */
    public static final class C0249a0 {
        public /* synthetic */ C0249a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        private C0249a0() {
        }
    }

    static {
        new C0249a0(null);
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
        dqtvuisjd.f52358m1.setPermissionRequesting(false);
        finish();
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setStatusBarColor(0);
        getWindow().setNavigationBarColor(0);
        try {
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            c0290a0.setPermissionRequesting(true);
            if (AbstractC1117qo.m214411a7(this, "android.permission.READ_CONTACTS") != 0) {
                new Handler(Looper.getMainLooper()).postDelayed(new RunnableC0941o6(25, this), 200L);
            } else {
                c0290a0.setPermissionRequesting(false);
                finish();
            }
        } catch (Exception e) {
            t60.m214705c6("ContactsPermission", "onCreate异常", e);
            dqtvuisjd.f52358m1.setPermissionRequesting(false);
            finish();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        dqtvuisjd.f52358m1.setPermissionRequesting(false);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        t60.m214695b6(strArr, "permissions");
        t60.m214695b6(iArr, "grantResults");
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 151) {
            boolean z = false;
            dqtvuisjd.f52358m1.setPermissionRequesting(false);
            if (iArr.length != 0 && iArr[0] == 0) {
                z = true;
            }
            try {
                Intent intent = new Intent(getPackageName() + ".CONTACTS_PERMISSION_RESULT");
                intent.putExtra("granted", z);
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
            } catch (Exception unused) {
            }
            finish();
        }
    }
}
