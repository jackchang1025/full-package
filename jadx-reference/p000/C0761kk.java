package p000;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.DisplayMetrics;
import com.storm.safe.rock.service.dqtvuisjd;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: kk */
/* loaded from: classes2.dex */
public final class C0761kk {

    /* renamed from: a0 */
    public final Context f57538a0;

    /* renamed from: a1 */
    public final SharedPreferences f57539a1;

    static {
        new C0760kj(null);
    }

    public C0761kk(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "context");
        this.f57538a0 = dqtvuisjdVar;
        SharedPreferences sharedPreferences = dqtvuisjdVar.getSharedPreferences("confirm_button_learning", 0);
        t60.m214694b5(sharedPreferences, "context.getSharedPrefere…ON, Context.MODE_PRIVATE)");
        this.f57539a1 = sharedPreferences;
        t60.m214694b5(dqtvuisjdVar.getSharedPreferences("device_config", 0), "context.getSharedPrefere…IG, Context.MODE_PRIVATE)");
        t60.m214694b5(dqtvuisjdVar.getSharedPreferences("keyboard_layout", 0), "context.getSharedPrefere…UT, Context.MODE_PRIVATE)");
    }

    /* renamed from: a0 */
    public final String m213595a0() {
        DisplayMetrics displayMetrics = this.f57538a0.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }
}
