package p000;

import androidx.appcompat.widget.SearchView;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class cv0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55535a0;

    /* renamed from: a1 */
    public final /* synthetic */ SearchView f55536a1;

    public /* synthetic */ cv0(SearchView searchView, int i) {
        this.f55535a0 = i;
        this.f55536a1 = searchView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f55535a0) {
            case 0:
                this.f55536a1.m209906b8();
                break;
            default:
                AbstractC0945oa abstractC0945oa = this.f55536a1.f44024e0;
                if (abstractC0945oa instanceof x21) {
                    abstractC0945oa.mo214167a1(null);
                    break;
                }
                break;
        }
    }
}
