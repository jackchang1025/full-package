package com.vendor.rat.helper;

import android.util.Log;
import android.view.ViewTreeObserver;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Vendor: com.guard.wallet.helper.e
 * Listener for BlockView window attach/detach events.
 */
public final class BlockViewAttachListener implements ViewTreeObserver.OnWindowAttachListener {
    @Override
    public final void onWindowAttached() {
        Log.d("BlockViewHelper", "BlockTextView 已显示至窗口");
        BlockViewHelper.viewShowing.set(true);
    }

    @Override
    public final void onWindowDetached() {
        Log.d("BlockViewHelper", "BlockTextView 已从窗口移除");
        BlockViewHelper.viewShowing.set(false);
    }
}
