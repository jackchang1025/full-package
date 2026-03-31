package com.vendor.rat.auto.pipeline;

import android.util.Log;
import com.vendor.rat.auto.pipeline.stage.*;
import com.vendor.rat.helper.BlockViewHelper;
import com.vendor.rat.service.MyAccessibilityService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 自动化管道 — 对齐 Laravel Illuminate\Pipeline\Pipeline
 *
 * Laravel:  Pipeline::send($passable)->through($pipes)->then($dest)
 * Java:     AutomationPipeline.send(ctx).through(pipes).then(dest)
 *
 * 洋葱模型: 每个 pipe 包裹下一个，形成嵌套调用链。
 * pipe.handle(passable, next) 中调用 next.run() 继续，不调用则短路终止。
 */
public class AutomationPipeline {

    private static final String TAG = "Pipeline";

    // Laravel: $passable
    private PipelineContext passable;

    // Laravel: $pipes
    private final List<PipelineStage> pipes = new ArrayList<>();

    // Laravel: $finally
    private Runnable finallyCallback;

    // 静态引用，供 AutoEngine.Z() 获取当前管道上下文
    private static volatile PipelineContext currentContext;

    private AutomationPipeline() {}

    // ============ Fluent Builder (对齐 Laravel) ============

    /** Laravel: Pipeline::send($passable) */
    public static AutomationPipeline send(PipelineContext passable) {
        AutomationPipeline p = new AutomationPipeline();
        p.passable = passable;
        return p;
    }

    /** Laravel: ->through($pipes) */
    public AutomationPipeline through(PipelineStage... pipes) {
        this.pipes.addAll(Arrays.asList(pipes));
        return this;
    }

    /** Laravel: ->through($pipes) — List 版本 */
    public AutomationPipeline through(List<PipelineStage> pipes) {
        this.pipes.addAll(pipes);
        return this;
    }

    /** Laravel: ->pipe($pipes) — 追加 */
    public AutomationPipeline pipe(PipelineStage... pipes) {
        this.pipes.addAll(Arrays.asList(pipes));
        return this;
    }

    /** Laravel: ->finally(Closure) */
    public AutomationPipeline andFinally(Runnable callback) {
        this.finallyCallback = callback;
        return this;
    }

    // ============ 执行 (对齐 Laravel then/thenReturn) ============

    /**
     * Laravel: ->then(Closure $destination)
     *
     * 构建洋葱: array_reduce(array_reverse($pipes), carry(), destination)
     * 然后执行最外层 Runnable。
     */
    public void then(Runnable destination) {
        // 洋葱构建: 从最后一个 pipe 开始包裹
        List<PipelineStage> reversed = new ArrayList<>(pipes);
        Collections.reverse(reversed);

        // destination 是洋葱最内层
        Runnable pipeline = destination;

        // 逐层包裹 — 对齐 Laravel carry()
        for (PipelineStage pipe : reversed) {
            final Runnable next = pipeline;
            final String pipeName = pipe.getClass().getSimpleName();
            pipeline = () -> {
                long start = System.currentTimeMillis();
                Log.d(TAG, ">>> " + pipeName);
                try {
                    pipe.handle(passable, next);
                } catch (Exception e) {
                    Log.e(TAG, "!!! " + pipeName + " ERROR: " + e.getMessage(), e);
                }
                long elapsed = System.currentTimeMillis() - start;
                Log.d(TAG, "<<< " + pipeName + " (" + elapsed + "ms)");
            };
        }

        // 执行洋葱
        try {
            pipeline.run();
        } finally {
            if (finallyCallback != null) {
                finallyCallback.run();
            }
        }
    }

    /** Laravel: ->thenReturn() — 执行后返回 passable */
    public PipelineContext thenReturn() {
        then(() -> {}); // 空 destination
        return passable;
    }

    // ============ 工厂方法 ============

    /**
     * 创建标准自动化管道并在后台线程执行
     */
    public static void executeStandard(MyAccessibilityService service) {
        PipelineContext ctx = new PipelineContext(service);
        currentContext = ctx;

        new Thread(() -> {
            Log.i(TAG, "=== Pipeline START ===");
            long start = System.currentTimeMillis();

            AutomationPipeline.send(ctx)
                .through(
                    new VersionCheckStage(),
                    new CompletionCheckStage(),
                    new ShowOverlayStage(),
                    new LaunchSettingsStage(),
                    new VendorEngineStage(),
                    new NavigateToAppStage(),
                    new PermissionRequestStage(),
                    new MediaProjectionStage(),
                    new RemoveOverlayStage(),
                    new MarkCompletedStage()
                )
         .andFinally(() -> {
         // Safety net: ensure overlay is removed even if pipeline fails
  try {
     if (BlockViewHelper.isShowing()) {
      Log.w(TAG, "Overlay still showing after pipeline, removing as safety net");
      BlockViewHelper.removeViewInternal();
       }
        } catch (Exception e) {
        Log.e(TAG, "Failed to remove overlay in finally", e);
   }
         currentContext = null;
     long elapsed = System.currentTimeMillis() - start;
       Log.i(TAG, "=== Pipeline END (" + elapsed + "ms) ===");
     })
                .thenReturn();

        }, "automation-pipeline").start();
    }

    /** 获取当前管道上下文 (供 AutoEngine.Z() 调用) */
    public static PipelineContext getCurrentContext() {
        return currentContext;
    }
}
