package com.vendor.rat.auto.engine;

import android.view.accessibility.AccessibilityEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

/**
 * AutoEngine.dispatchState() 异常兜底 + 状态管理测试
 *
 * 纯 JVM 测试，使用 TestableEngine 内部类暴露 protected 方法
 */
public class AutoEngineDispatchStateTest {

    private TestableEngine engine;

    @Before
    public void setUp() {
        engine = new TestableEngine();
    }

    @After
    public void tearDown() {
        engine.destroy();
    }

    // ============ 状态管理 ============

    @Test
    public void dispatchState_addsTargetState() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        engine.doDispatchState("STATE_A", latch::countDown);
        latch.await(2, TimeUnit.SECONDS);
        assertTrue(engine.getStateQueue().contains("STATE_A"));
    }

    @Test
    public void dispatchState_clearsSiblingStates() throws Exception {
        // 先添加要被清除的状态
        engine.getStateQueue().add("SIBLING_1");
        engine.getStateQueue().add("SIBLING_2");

        CountDownLatch latch = new CountDownLatch(1);
        engine.doDispatchState("TARGET", latch::countDown, "SIBLING_1", "SIBLING_2");
        latch.await(2, TimeUnit.SECONDS);

        assertTrue(engine.getStateQueue().contains("TARGET"));
        assertFalse(engine.getStateQueue().contains("SIBLING_1"));
        assertFalse(engine.getStateQueue().contains("SIBLING_2"));
    }

    @Test
    public void dispatchState_duplicate_returnsFalse() {
        engine.getStateQueue().add("EXISTING");
        boolean result = engine.doDispatchState("EXISTING", () -> {});
        assertFalse(result);
    }

    @Test
    public void dispatchState_executesHandler() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        boolean dispatched = engine.doDispatchState("RUN_ME", latch::countDown);
        assertTrue(dispatched);
        assertTrue("handler should execute within 2s", latch.await(2, TimeUnit.SECONDS));
    }

    // ============ 异常兜底 ============

    @Test
    public void dispatchState_handlerException_doesNotCrash() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        engine.doDispatchState("CRASH", () -> {
            latch.countDown();
            throw new RuntimeException("boom");
        });
        assertTrue("handler should execute", latch.await(2, TimeUnit.SECONDS));
        // scheduler 仍活着 — 能调度新任务即为证明
        CountDownLatch latch2 = new CountDownLatch(1);
        engine.getStateQueue().clear(); // 清除 CRASH 状态以便添加新状态
        engine.doDispatchState("AFTER", latch2::countDown);
        assertTrue("scheduler still alive", latch2.await(2, TimeUnit.SECONDS));
    }

    @Test
    public void dispatchState_handlerException_stateStillInQueue() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        engine.doDispatchState("FAILING", () -> {
            latch.countDown();
            throw new RuntimeException("boom");
        });
        latch.await(2, TimeUnit.SECONDS);
        // state 在 handler 执行前就已添加到队列，异常不会移除它
        assertTrue(engine.getStateQueue().contains("FAILING"));
    }

    @Test
    public void dispatchState_afterHandlerException_canDispatchAgain() throws Exception {
        // 先让一个 handler 崩溃
        CountDownLatch crashLatch = new CountDownLatch(1);
        engine.doDispatchState("CRASH", () -> {
            crashLatch.countDown();
            throw new RuntimeException("boom");
        });
        crashLatch.await(2, TimeUnit.SECONDS);

        // 新状态仍可正常分发
        AtomicBoolean ran = new AtomicBoolean(false);
        CountDownLatch okLatch = new CountDownLatch(1);
        engine.doDispatchState("OK", () -> {
            ran.set(true);
            okLatch.countDown();
        });
        okLatch.await(2, TimeUnit.SECONDS);
        assertTrue(ran.get());
    }

    @Test
    public void dispatchState_schedulerShutdown_returnsTrueButNoExec() throws Exception {
        engine.scheduler.shutdownNow();
        engine.scheduler.awaitTermination(1, TimeUnit.SECONDS);

        AtomicBoolean ran = new AtomicBoolean(false);
        boolean dispatched = engine.doDispatchState("POST_SHUTDOWN", () -> ran.set(true));

        // state 仍加入队列 → 返回 true
        assertTrue(dispatched);
        assertTrue(engine.getStateQueue().contains("POST_SHUTDOWN"));

        // 但 handler 不会被执行
        Thread.sleep(200);
        assertFalse(ran.get());
    }

    // ============ TestableEngine ============

    private static class TestableEngine extends AutoEngine {

        TestableEngine() {
            super(new ArrayList<>(), "com.test.pkg");
        }

        @Override
        public void onWindowMatched(String packageName, String className,
                                    AccessibilityEvent event) {
            // no-op
        }

        @Override
        public void execute() {
            // no-op
        }

        public ConcurrentLinkedQueue<String> getStateQueue() {
            return stateQueue;
        }

        public boolean doDispatchState(String targetState, Runnable handler,
                                       String... clearStates) {
            return dispatchState(targetState, handler, clearStates);
        }
    }
}
