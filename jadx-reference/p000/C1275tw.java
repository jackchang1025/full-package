package p000;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: tw */
/* loaded from: classes2.dex */
public final class C1275tw extends AnimatorListenerAdapter {

    /* renamed from: a0 */
    public final /* synthetic */ int f60287a0;

    /* renamed from: a1 */
    public final /* synthetic */ AbstractC1277tx f60288a1;

    public /* synthetic */ C1275tw(AbstractC1277tx abstractC1277tx, int i) {
        this.f60287a0 = i;
        this.f60288a1 = abstractC1277tx;
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        switch (this.f60287a0) {
            case 1:
                super.onAnimationEnd(animator);
                AbstractC1277tx abstractC1277tx = this.f60288a1;
                super/*android.graphics.drawable.Drawable*/.setVisible(false, false);
                ArrayList arrayList = abstractC1277tx.f60296a5;
                if (arrayList != null && !abstractC1277tx.f60297a6) {
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((C0410dc) obj).m212579a0(abstractC1277tx);
                    }
                    break;
                }
                break;
            default:
                super.onAnimationEnd(animator);
                break;
        }
    }

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        switch (this.f60287a0) {
            case 0:
                super.onAnimationStart(animator);
                AbstractC1277tx abstractC1277tx = this.f60288a1;
                ArrayList arrayList = abstractC1277tx.f60296a5;
                if (arrayList != null && !abstractC1277tx.f60297a6) {
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList.get(i);
                        i++;
                        ((C0410dc) obj).m212580a1(abstractC1277tx);
                    }
                    break;
                }
                break;
            default:
                super.onAnimationStart(animator);
                break;
        }
    }
}
