package com.guard.wallet.helper;

import android.content.DialogInterface;

/**
 * 对话框取消按钮监听器 — 点击后调用 utils.g.Y0 回调。
 *
 * vendor 原名: com.guard.wallet.helper.l
 */
public final class NegativeClickListener implements DialogInterface.OnClickListener {
    public final String a;
    public final String b;

    public NegativeClickListener(String a, String b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public final void onClick(DialogInterface dialog, int which) {
        com.guard.wallet.utils.SystemHelper.Y0(a, b);
    }
}
