package p000;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.material.search.SearchView;
import com.storm.safe.rock.service.dqtvuisjd;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: eb */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnTouchListenerC0450eb implements View.OnTouchListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f55948a0;

    public /* synthetic */ ViewOnTouchListenerC0450eb(int i) {
        this.f55948a0 = i;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        switch (this.f55948a0) {
            case 0:
                C0451ec c0451ec = C0454ef.f55976c3;
                break;
            case 1:
                int i = SearchView.f49728c6;
                break;
            default:
                dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                t60.m214702c3("dqtvuisjd", "🛡️ 图标位置触摸被拦截");
                break;
        }
        return true;
    }
}
