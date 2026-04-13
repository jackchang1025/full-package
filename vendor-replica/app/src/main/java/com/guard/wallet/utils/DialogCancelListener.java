package com.guard.wallet.utils;

import android.content.DialogInterface;

/**
 * DialogCancelListener -- 弹窗取消监听器。
 * 当无障碍引导弹窗被取消时，清空弹窗引用。
 * vendor 原始类名: com.guard.wallet.utils.a
 */
public final class DialogCancelListener implements DialogInterface.OnCancelListener {
    @Override
    public final void onCancel(DialogInterface dialog) {
        GuideDialogUtils.guideDialogRef = null;
    }
}
