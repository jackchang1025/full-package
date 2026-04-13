package e0;

import android.view.View;
import android.view.ViewTreeObserver;
import com.guard.wallet.utils.AbstractC0255k;

/* renamed from: e0.h */
/* loaded from: classes.dex */
public final class ViewTreeObserverOnGlobalLayoutListenerC0270h implements ViewTreeObserver.OnGlobalLayoutListener {

    /* renamed from: a */
    public final View f452a;

    public ViewTreeObserverOnGlobalLayoutListenerC0270h(View view) {
        this.f452a = view;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        AbstractC0255k.m728b(this.f452a);
    }
}
