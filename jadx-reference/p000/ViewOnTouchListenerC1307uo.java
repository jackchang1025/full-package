package p000;

import android.view.MotionEvent;
import android.view.View;
import com.google.android.material.search.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: uo */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnTouchListenerC1307uo implements View.OnTouchListener {

    /* renamed from: a0 */
    public final /* synthetic */ int f60483a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f60484a1;

    public /* synthetic */ ViewOnTouchListenerC1307uo(int i, Object obj) {
        this.f60483a0 = i;
        this.f60484a1 = obj;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        int i = this.f60483a0;
        Object obj = this.f60484a1;
        switch (i) {
            case 0:
                C1309uq c1309uq = (C1309uq) obj;
                if (motionEvent.getAction() == 1) {
                    long jCurrentTimeMillis = System.currentTimeMillis() - c1309uq.f60500b4;
                    if (jCurrentTimeMillis < 0 || jCurrentTimeMillis > 300) {
                        c1309uq.f60498b2 = false;
                    }
                    c1309uq.m214859b9();
                    c1309uq.f60498b2 = true;
                    c1309uq.f60500b4 = System.currentTimeMillis();
                    break;
                }
                break;
            default:
                SearchView searchView = (SearchView) obj;
                int i2 = SearchView.f49728c6;
                if (searchView.m211088a2()) {
                    searchView.m211087a1();
                    break;
                }
                break;
        }
        return false;
    }
}
