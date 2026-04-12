package p000;

import android.R;
import android.content.Context;
import android.widget.TextView;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class lj1 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58020a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f58021a1;

    /* renamed from: a2 */
    public final /* synthetic */ int f58022a2;

    /* renamed from: a3 */
    public final /* synthetic */ Context f58023a3;

    public /* synthetic */ lj1(Context context, int i, int i2, int i3) {
        this.f58020a0 = i3;
        this.f58023a3 = context;
        this.f58021a1 = i;
        this.f58022a2 = i2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f58020a0;
        int i2 = this.f58022a2;
        int i3 = this.f58021a1;
        Context context = this.f58023a3;
        switch (i) {
            case 0:
                mj1.wakeScreen$lambda$2(context, i3, i2);
                return;
            default:
                iuzxujjtqev iuzxujjtqevVar = (iuzxujjtqev) context;
                iuzxujjtqev.C0254a0 c0254a0 = iuzxujjtqev.f51956e2;
                t60.m214695b6(iuzxujjtqevVar, "this$0");
                TextView textView = iuzxujjtqevVar.f51958c3;
                if (textView == null) {
                    t60.m214724f2("statusText");
                    throw null;
                }
                textView.setText(iuzxujjtqevVar.getString(R$string.status_partial_denied, Integer.valueOf(i3), Integer.valueOf(i2)));
                TextView textView2 = iuzxujjtqevVar.f51958c3;
                if (textView2 != null) {
                    textView2.setTextColor(iuzxujjtqevVar.getColor(R.color.holo_orange_dark));
                    return;
                } else {
                    t60.m214724f2("statusText");
                    throw null;
                }
        }
    }
}
