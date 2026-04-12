package p000;

import android.view.ViewGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: xx */
/* loaded from: classes2.dex */
public final class C1434xx implements InterfaceC1451yb {

    /* renamed from: a0 */
    public final /* synthetic */ int f61199a0;

    /* renamed from: a1 */
    public final /* synthetic */ ExtendedFloatingActionButton f61200a1;

    public /* synthetic */ C1434xx(ExtendedFloatingActionButton extendedFloatingActionButton, int i) {
        this.f61199a0 = i;
        this.f61200a1 = extendedFloatingActionButton;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a0 */
    public final int mo214256a0() {
        switch (this.f61199a0) {
            case 0:
                return this.f61200a1.getCollapsedPadding();
            default:
                return this.f61200a1.f49488c7;
        }
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a2 */
    public final int mo214258a2() {
        switch (this.f61199a0) {
            case 0:
                return this.f61200a1.getCollapsedPadding();
            default:
                return this.f61200a1.f49487c6;
        }
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a7 */
    public final int mo214263a7() {
        switch (this.f61199a0) {
            case 0:
                return this.f61200a1.getCollapsedSize();
            default:
                ExtendedFloatingActionButton extendedFloatingActionButton = this.f61200a1;
                return (extendedFloatingActionButton.getMeasuredWidth() - (extendedFloatingActionButton.getCollapsedPadding() * 2)) + extendedFloatingActionButton.f49487c6 + extendedFloatingActionButton.f49488c7;
        }
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a8 */
    public final ViewGroup.LayoutParams mo214264a8() {
        switch (this.f61199a0) {
            case 0:
                ExtendedFloatingActionButton extendedFloatingActionButton = this.f61200a1;
                return new ViewGroup.LayoutParams(extendedFloatingActionButton.getCollapsedSize(), extendedFloatingActionButton.getCollapsedSize());
            default:
                return new ViewGroup.LayoutParams(-2, -2);
        }
    }

    @Override // p000.InterfaceC1451yb
    public final int getHeight() {
        switch (this.f61199a0) {
            case 0:
                return this.f61200a1.getCollapsedSize();
            default:
                return this.f61200a1.getMeasuredHeight();
        }
    }
}
