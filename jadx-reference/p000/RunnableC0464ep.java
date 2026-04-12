package p000;

import androidx.appcompat.widget.ActionMenuView;
import com.google.android.material.bottomappbar.BottomAppBar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ep */
/* loaded from: classes2.dex */
public final class RunnableC0464ep implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ ActionMenuView f56090a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f56091a1;

    /* renamed from: a2 */
    public final /* synthetic */ boolean f56092a2;

    /* renamed from: a3 */
    public final /* synthetic */ BottomAppBar f56093a3;

    public RunnableC0464ep(BottomAppBar bottomAppBar, ActionMenuView actionMenuView, int i, boolean z) {
        this.f56093a3 = bottomAppBar;
        this.f56090a0 = actionMenuView;
        this.f56091a1 = i;
        this.f56092a2 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.f56091a1;
        boolean z = this.f56092a2;
        BottomAppBar bottomAppBar = this.f56093a3;
        this.f56090a0.setTranslationX(bottomAppBar.m210924c7(r3, i, z));
    }
}
