package com.vendor.rat.auto.pipeline.stage;

import android.util.Log;

import com.vendor.rat.auto.pipeline.PipelineContext;
import com.vendor.rat.auto.pipeline.PipelineStage;

import java.util.concurrent.TimeUnit;

/**
 * Stage 5: 等待厂商引擎完成
 *
 * 通过 CountDownLatch 阻塞管道线程，等待 AutoEngine.Z() 完成时
 * 调用 latch.countDown() 释放。超时 120 秒。
 */
public class VendorEngineStage implements PipelineStage {

    private static final String TAG = "VendorEngineStage";
    private static final long TIMEOUT_MS = 120_000;

    @Override
    public void handle(PipelineContext passable, Runnable next) {
        // Skip for unsupported devices
        if (!passable.isHuawei() && !passable.isXiaomi() && !passable.isOppo()) {
            next.run();
            return;
        }

        Log.d(TAG, "Waiting for vendor engine completion (timeout=" + TIMEOUT_MS + "ms)");

        try {
            boolean completed = passable.getVendorEngineLatch()
                .await(TIMEOUT_MS, TimeUnit.MILLISECONDS);

            if (completed) {
                passable.setVendorEngineCompleted(true);
                Log.d(TAG, "Vendor engine completed successfully");
            } else {
                Log.w(TAG, "Vendor engine timed out after " + TIMEOUT_MS + "ms");
                passable.setVendorEngineCompleted(false);
                // 超时也继续管道 — 后续 stage 仍可执行权限请求等
            }
        } catch (InterruptedException e) {
            Log.w(TAG, "Vendor engine wait interrupted");
        }

        next.run();
    }
}
