package p000;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimation;
import android.view.WindowInsetsAnimation$Callback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class gf1 extends WindowInsetsAnimation$Callback {

    /* renamed from: a0 */
    public final C0816ld f56457a0;

    /* renamed from: a1 */
    public List f56458a1;

    /* renamed from: a2 */
    public ArrayList f56459a2;

    /* renamed from: a3 */
    public final HashMap f56460a3;

    public gf1(C0816ld c0816ld) {
        super(0);
        this.f56460a3 = new HashMap();
        this.f56457a0 = c0816ld;
    }

    /* renamed from: a0 */
    public final jf1 m212945a0(WindowInsetsAnimation windowInsetsAnimation) {
        jf1 jf1Var = (jf1) this.f56460a3.get(windowInsetsAnimation);
        if (jf1Var == null) {
            jf1Var = new jf1(0, null, 0L);
            if (Build.VERSION.SDK_INT >= 30) {
                jf1Var.f57330a0 = new hf1(windowInsetsAnimation);
            }
            this.f56460a3.put(windowInsetsAnimation, jf1Var);
        }
        return jf1Var;
    }

    public final void onEnd(WindowInsetsAnimation windowInsetsAnimation) {
        m212945a0(windowInsetsAnimation);
        ((View) this.f56457a0.f57883a3).setTranslationY(0.0f);
        this.f56460a3.remove(windowInsetsAnimation);
    }

    public final void onPrepare(WindowInsetsAnimation windowInsetsAnimation) {
        m212945a0(windowInsetsAnimation);
        C0816ld c0816ld = this.f56457a0;
        View view = (View) c0816ld.f57883a3;
        int[] iArr = (int[]) c0816ld.f57884a4;
        view.getLocationOnScreen(iArr);
        c0816ld.f57880a0 = iArr[1];
    }

    public final WindowInsets onProgress(WindowInsets windowInsets, List list) {
        ArrayList arrayList = this.f56459a2;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList(list.size());
            this.f56459a2 = arrayList2;
            this.f56458a1 = Collections.unmodifiableList(arrayList2);
        } else {
            arrayList.clear();
        }
        for (int size = list.size() - 1; size >= 0; size--) {
            WindowInsetsAnimation windowInsetsAnimationM213375b0 = AbstractC0740k0.m213375b0(list.get(size));
            jf1 jf1VarM212945a0 = m212945a0(windowInsetsAnimationM213375b0);
            jf1VarM212945a0.f57330a0.mo213036a3(windowInsetsAnimationM213375b0.getFraction());
            this.f56459a2.add(jf1VarM212945a0);
        }
        xf1 xf1VarM215170a6 = xf1.m215170a6(null, windowInsets);
        this.f56457a0.m213833a0(xf1VarM215170a6, this.f56458a1);
        return xf1VarM215170a6.m215175a5();
    }

    public final WindowInsetsAnimation.Bounds onStart(WindowInsetsAnimation windowInsetsAnimation, WindowInsetsAnimation.Bounds bounds) {
        m212945a0(windowInsetsAnimation);
        f60 f60VarM212749a2 = f60.m212749a2(bounds.getLowerBound());
        f60 f60VarM212749a22 = f60.m212749a2(bounds.getUpperBound());
        C0816ld c0816ld = this.f56457a0;
        View view = (View) c0816ld.f57883a3;
        int[] iArr = (int[]) c0816ld.f57884a4;
        view.getLocationOnScreen(iArr);
        int i = c0816ld.f57880a0 - iArr[1];
        c0816ld.f57881a1 = i;
        view.setTranslationY(i);
        AbstractC0740k0.m213379b4();
        return AbstractC0740k0.m213373a8(f60VarM212749a2.m212750a3(), f60VarM212749a22.m212750a3());
    }
}
