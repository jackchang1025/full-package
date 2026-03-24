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
