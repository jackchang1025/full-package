package p000;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: en */
/* loaded from: classes2.dex */
public final class C0462en extends b81 {

    /* renamed from: c2 */
    public final /* synthetic */ int f56078c2;

    /* renamed from: c3 */
    public final /* synthetic */ BottomAppBar f56079c3;

    public C0462en(BottomAppBar bottomAppBar, int i) {
        this.f56079c3 = bottomAppBar;
        this.f56078c2 = i;
    }

    @Override // p000.b81
    /* renamed from: d8 */
    public final void mo210605d8(FloatingActionButton floatingActionButton) {
        int i = BottomAppBar.f49145g6;
        floatingActionButton.setTranslationX(this.f56079c3.m210925c8(this.f56078c2));
        floatingActionButton.m211051b1(new C0461em(), true);
    }
}
