package p000;

import android.content.ClipData;
import android.view.PointerIcon;
import android.view.View;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class na1 {
    /* renamed from: a0 */
    public static void m214057a0(View view) {
        view.cancelDragAndDrop();
    }

    /* renamed from: a1 */
    public static void m214058a1(View view) {
        view.dispatchFinishTemporaryDetach();
    }

    /* renamed from: a2 */
    public static void m214059a2(View view) {
        view.dispatchStartTemporaryDetach();
    }

    /* renamed from: a3 */
    public static void m214060a3(View view, PointerIcon pointerIcon) {
        view.setPointerIcon(pointerIcon);
    }

    /* renamed from: a4 */
    public static boolean m214061a4(View view, ClipData clipData, View.DragShadowBuilder dragShadowBuilder, Object obj, int i) {
        return view.startDragAndDrop(clipData, dragShadowBuilder, obj, i);
    }

    /* renamed from: a5 */
    public static void m214062a5(View view, View.DragShadowBuilder dragShadowBuilder) {
        view.updateDragShadow(dragShadowBuilder);
    }
}
