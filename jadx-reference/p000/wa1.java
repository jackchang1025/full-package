package p000;

import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.R$id;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class wa1 {

    /* renamed from: a3 */
    public static final ArrayList f60875a3 = new ArrayList();

    /* renamed from: a0 */
    public WeakHashMap f60876a0;

    /* renamed from: a1 */
    public SparseArray f60877a1;

    /* renamed from: a2 */
    public WeakReference f60878a2;

    /* renamed from: a0 */
    public final View m215043a0(View view) {
        int size;
        WeakHashMap weakHashMap = this.f60876a0;
        if (weakHashMap == null || !weakHashMap.containsKey(view)) {
            return null;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View viewM215043a0 = m215043a0(viewGroup.getChildAt(childCount));
                if (viewM215043a0 != null) {
                    return viewM215043a0;
                }
            }
        }
        ArrayList arrayList = (ArrayList) view.getTag(R$id.tag_unhandled_key_listeners);
        if (arrayList == null || arrayList.size() - 1 < 0) {
            return null;
        }
        arrayList.get(size).getClass();
        throw new ClassCastException();
    }
}
