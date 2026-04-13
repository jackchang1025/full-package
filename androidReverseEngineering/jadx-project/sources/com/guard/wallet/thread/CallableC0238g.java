package com.guard.wallet.thread;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.concurrent.Callable;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;
import p012o.AbstractC0413b;
import p012o.a0;

/* renamed from: com.guard.wallet.thread.g */
/* loaded from: classes.dex */
public final class CallableC0238g implements Callable {

    /* renamed from: a */
    public final a0 f374a;

    /* renamed from: b */
    public final boolean f375b;

    public CallableC0238g(boolean z2, a0 a0Var) {
        this.f375b = z2;
        this.f374a = a0Var;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        a0 a0Var;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            boolean z2 = this.f375b;
            a0Var = this.f374a;
            if (!z2 || i3 >= 5 || !a0Var.m996M()) {
                break;
            }
            Log.e("com.guard.wallet.thread.g", "无线配对成功,仍然停留在配对对话框,等待自动关闭");
            i3++;
            AbstractC0251g.T0(5);
        }
        while (i2 <= 5 && a0Var.m996M()) {
            Log.d("com.guard.wallet.thread.g", "无线配对已结束,等待5秒后,仍然停留在配对对话框");
            UiObject m1072k = a0Var.m1072k();
            CombineFilter combineFilter = new CombineFilter();
            StringCondition m1008b = AbstractC0413b.m1008b(combineFilter, AbstractC0000a.m7c(combineFilter, "className", "android.widget.Button"), TextBundle.TEXT_ENTRY);
            m1008b.setContains(AbstractC0250f.m627b("PAIR_CANCEL_TEXT"));
            combineFilter.getStringConditions().add(m1008b);
            UiObject findOneByCombine = m1072k.findOneByCombine(combineFilter);
            if (findOneByCombine != null && findOneByCombine.click()) {
                Log.d("com.guard.wallet.thread.g", "无线配对已结束,等待5秒后,仍然停留在配对对话框 已取消配对");
            }
            i2++;
            AbstractC0251g.T0(5);
        }
        return Boolean.valueOf(!a0Var.m996M());
    }
}
