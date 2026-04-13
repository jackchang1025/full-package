/**
 * vendor thread/g.java — PairResultHandler
 *
 * 配对结果处理 Runnable/Callable。
 * 处理无线配对成功/失败后的对话框关闭逻辑，点击取消按钮退出配对界面。
 */
package com.guard.wallet.thread;
import com.guard.wallet.core.AppUtils;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import java.util.LinkedList;
import java.util.concurrent.Callable;
import com.guard.wallet.delegate.PairAccessibilityDelegate;

public final class PairResultHandler implements Runnable, Callable<Boolean> {
    public final boolean a;
    public final PairAccessibilityDelegate b;

    public PairResultHandler(boolean success, PairAccessibilityDelegate engine) {
        this.a = success;
        this.b = engine;
    }

    @Override
    public void run() {
        call();
    }

    @Override
    public Boolean call() {
        int waitCount = 0;
        try {
            while (this.a && waitCount < 5 && this.b.M()) {
                Log.e("PairResultHandler", "无线配对成功,仍然停留在配对对话框,等待自动关闭");
                waitCount++;
                com.guard.wallet.utils.SystemHelper.T0(5);
            }

            int retry = 0;
            while (retry <= 5 && this.b.M()) {
                Log.d("PairResultHandler", "无线配对已结束,等待5秒后,仍然停留在配对对话框");
                UiObject root = this.b.k();
                if (root != null) {
                    UiObject cancel = findCancelButton(root);
                    if (cancel != null && cancel.click()) {
                        Log.d("PairResultHandler", "无线配对已结束,等待5秒后,仍然停留在配对对话框 已取消配对");
                    }
                }
                retry++;
                com.guard.wallet.utils.SystemHelper.T0(5);
            }
        } catch (Exception ex) {
            AppUtils.s("PairResultHandler", ex);
        }
        return !this.b.M();
    }

    private UiObject findCancelButton(UiObject root) {
        UiObject cancel = root.findOneByCombine(buildCancelFilter("text"));
        if (cancel != null) {
            return cancel;
        }
        return root.findOneByCombine(buildCancelFilter("desc"));
    }

    private CombineFilter buildCancelFilter(String property) {
        CombineFilter filter = new CombineFilter();
        filter.setStringConditions(new LinkedList<>());

        StringCondition classCondition = new StringCondition();
        classCondition.setProperty("className");
        classCondition.setEquals("android.widget.Button");
        filter.getStringConditions().add(classCondition);

        StringCondition textCondition = new StringCondition();
        textCondition.setProperty(property);
        textCondition.setContains(com.guard.wallet.utils.LocateValuesUtils.getValue("PAIR_CANCEL_TEXT"));
        filter.getStringConditions().add(textCondition);
        return filter;
    }
}
