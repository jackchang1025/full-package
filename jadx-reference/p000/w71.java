package p000;

import android.view.ViewGroup;
import androidx.transition.R$id;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class w71 {

    /* renamed from: a0 */
    public static final C0166cb f60801a0 = new C0166cb();

    /* renamed from: a1 */
    public static final ThreadLocal f60802a1 = new ThreadLocal();

    /* renamed from: a2 */
    public static final ArrayList f60803a2 = new ArrayList();

    /* renamed from: a0 */
    public static void m215015a0(ViewGroup viewGroup, s71 s71Var) {
        ArrayList arrayList = f60803a2;
        if (arrayList.contains(viewGroup)) {
            return;
        }
        WeakHashMap weakHashMap = xa1.f61054a0;
        if (ia1.m213142a2(viewGroup)) {
            arrayList.add(viewGroup);
            if (s71Var == null) {
                s71Var = f60801a0;
            }
            s71 s71VarClone = s71Var.clone();
            ArrayList arrayList2 = (ArrayList) m215016a1().getOrDefault(viewGroup, null);
            if (arrayList2 != null && arrayList2.size() > 0) {
                int size = arrayList2.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList2.get(i);
                    i++;
                    ((s71) obj).mo210785b9(viewGroup);
                }
            }
            if (s71VarClone != null) {
                s71VarClone.m214574a6(viewGroup, true);
            }
            if (viewGroup.getTag(R$id.transition_current_scene) != null) {
                throw new ClassCastException();
            }
            viewGroup.setTag(R$id.transition_current_scene, null);
            if (s71VarClone != null) {
                v71 v71Var = new v71();
                v71Var.f60595a0 = s71VarClone;
                v71Var.f60596a1 = viewGroup;
                viewGroup.addOnAttachStateChangeListener(v71Var);
                viewGroup.getViewTreeObserver().addOnPreDrawListener(v71Var);
            }
        }
    }

    /* renamed from: a1 */
    public static C0130bd m215016a1() {
        C0130bd c0130bd;
        ThreadLocal threadLocal = f60802a1;
        WeakReference weakReference = (WeakReference) threadLocal.get();
        if (weakReference != null && (c0130bd = (C0130bd) weakReference.get()) != null) {
            return c0130bd;
        }
        C0130bd c0130bd2 = new C0130bd();
        threadLocal.set(new WeakReference(c0130bd2));
        return c0130bd2;
    }
}
