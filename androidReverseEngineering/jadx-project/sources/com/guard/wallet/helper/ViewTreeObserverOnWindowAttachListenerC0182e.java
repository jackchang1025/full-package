package com.guard.wallet.helper;

import android.util.Log;
import android.view.ViewTreeObserver;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.guard.wallet.helper.e */
/* loaded from: classes.dex */
public final class ViewTreeObserverOnWindowAttachListenerC0182e implements ViewTreeObserver.OnWindowAttachListener {
    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public final void onWindowAttached() {
        AtomicReference atomicReference = AbstractC0184g.f203a;
        Log.d("com.guard.wallet.helper.g", "BlockTextView 已显示至窗口");
        AbstractC0184g.f208f.set(true);
    }

    @Override // android.view.ViewTreeObserver.OnWindowAttachListener
    public final void onWindowDetached() {
        AtomicReference atomicReference = AbstractC0184g.f203a;
        Log.d("com.guard.wallet.helper.g", "BlockTextView 已从窗口移除");
        AbstractC0184g.f208f.set(false);
    }
}
