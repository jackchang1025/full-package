package com.guard.wallet.biometric;

import android.os.CancellationSignal;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.OverlayViewHelper;

/**
 * 生物识别取消监听器 — CancellationSignal.OnCancelListener 实现。
 * 用户取消生物识别提示时关闭 ConfirmDeviceActivity。
 *
 * vendor 原始路径: g/b.java
 */
public final class BiometricCancelListener implements CancellationSignal.OnCancelListener {

    @Override
    public final void onCancel() {
        if (ConfirmDeviceActivity.getInstance() != null) {
            ConfirmDeviceActivity.getInstance().finish();
        }

        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(null, false);
        }
    }
}
