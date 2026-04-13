package com.guard.wallet.helper;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import android.view.ViewTreeObserver;
import com.guard.wallet.req.BlockViewVO;

/**
 * ViewTreeObserver 窗口附加监听器。
 *
 * <p>监听 BlockTextView 在窗口中的附加/分离状态，
 * 通过 {@link BlockViewManager#f} 原子变量通知 BlockView 管理器。</p>
 *
 * <p>vendor 原始类: {@code com.guard.wallet.helper.e}</p>
 */
public final class WindowAttachListener implements ViewTreeObserver.OnWindowAttachListener {

    @Override
    public final void onWindowAttached() {
        Log.d("com.guard.wallet.helper.BlockViewManager", "BlockTextView 已显示至窗口");
        BlockViewManager.f.set(true);
    }

    @Override
    public final void onWindowDetached() {
        Log.d("com.guard.wallet.helper.BlockViewManager", "BlockTextView 已从窗口移除");
        BlockViewManager.f.set(false);
    }

    /** Inner Runnable used by BlockViewManager.a() to post BlockView creation to main thread */
    public static class a implements Runnable {
        public final BlockViewVO a;
        public final int b;

        public a(BlockViewVO vo, int type) {
            this.a = vo;
            this.b = type;
        }

        @Override
        public void run() {
            try {
                switch (b) {
                    case 3:
                        BlockViewManager.b(a);
                        break;
                    default:
                        BlockViewManager.b(a);
                        break;
                }
            } catch (Exception ex) {
                AppUtils.s("com.guard.wallet.helper.e.a", ex);
            }
        }
    }
}
