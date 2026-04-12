package p000;

import android.animation.Animator;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowId;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class v71 implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {

    /* renamed from: a0 */
    public s71 f60595a0;

    /* renamed from: a1 */
    public ViewGroup f60596a1;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:108:0x024f  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x01fa A[EDGE_INSN: B:137:0x01fa->B:92:0x01fa BREAK  A[LOOP:1: B:18:0x0085->B:91:0x01ef], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0202  */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v17, types: [t01] */
    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onPreDraw() {
        ArrayList arrayList;
        int i;
        C0130bd c0130bd;
        C0130bd c0130bd2;
        int i2;
        int[] iArr;
        C0130bd c0130bd3;
        int i3;
        int i4;
        int i5;
        q71 q71Var;
        View view;
        boolean z;
        C0130bd c0130bd4;
        y71 y71Var;
        View view2;
        View view3;
        boolean z2;
        ?? r7;
        s71 s71Var = this.f60595a0;
        ViewGroup viewGroup = this.f60596a1;
        viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
        viewGroup.removeOnAttachStateChangeListener(this);
        boolean z3 = true;
        if (!w71.f60803a2.remove(viewGroup)) {
            return true;
        }
        C0130bd c0130bdM215016a1 = w71.m215016a1();
        Long l = null;
        ArrayList arrayList2 = (ArrayList) c0130bdM215016a1.getOrDefault(viewGroup, null);
        if (arrayList2 != null) {
            arrayList = arrayList2.size() > 0 ? new ArrayList(arrayList2) : null;
            arrayList2.add(s71Var);
            s71Var.m214572a0(new u71(this, c0130bdM215016a1));
            i = 0;
            s71Var.m214574a6(viewGroup, false);
            if (arrayList != null) {
                int size = arrayList.size();
                int i6 = 0;
                while (i6 < size) {
                    Object obj = arrayList.get(i6);
                    i6++;
                    ((s71) obj).mo210786c1(viewGroup);
                }
            }
            s71Var.f59909b0 = new ArrayList();
            s71Var.f59910b1 = new ArrayList();
            x31 x31Var = s71Var.f59905a6;
            x31 x31Var2 = s71Var.f59906a7;
            c0130bd = new C0130bd((C0130bd) x31Var.f61012a0);
            c0130bd2 = new C0130bd((C0130bd) x31Var2.f61012a0);
            i2 = 0;
            while (true) {
                iArr = s71Var.f59908a9;
                if (i2 < iArr.length) {
                    break;
                }
                int i7 = iArr[i2];
                if (i7 == z3) {
                    z = z3;
                    c0130bd4 = c0130bd;
                    for (int i8 = c0130bd4.f60117a2 - 1; i8 >= 0; i8--) {
                        View view4 = (View) c0130bd4.m214679a7(i8);
                        if (view4 != null && s71Var.m214580b7(view4) && (y71Var = (y71) c0130bd2.remove(view4)) != null && s71Var.m214580b7(y71Var.f61263a1)) {
                            s71Var.f59909b0.add((y71) c0130bd4.m214680a8(i8));
                            s71Var.f59910b1.add(y71Var);
                        }
                    }
                } else if (i7 == 2) {
                    z = z3;
                    c0130bd4 = c0130bd;
                    C0130bd c0130bd5 = (C0130bd) x31Var.f61015a3;
                    C0130bd c0130bd6 = (C0130bd) x31Var2.f61015a3;
                    int i9 = c0130bd5.f60117a2;
                    for (int i10 = 0; i10 < i9; i10++) {
                        View view5 = (View) c0130bd5.m214681a9(i10);
                        if (view5 != null && s71Var.m214580b7(view5) && (view2 = (View) c0130bd6.getOrDefault(c0130bd5.m214679a7(i10), null)) != null && s71Var.m214580b7(view2)) {
                            y71 y71Var2 = (y71) c0130bd4.getOrDefault(view5, null);
                            y71 y71Var3 = (y71) c0130bd2.getOrDefault(view2, null);
                            if (y71Var2 != null && y71Var3 != null) {
                                s71Var.f59909b0.add(y71Var2);
                                s71Var.f59910b1.add(y71Var3);
                                c0130bd4.remove(view5);
                                c0130bd2.remove(view2);
                            }
                        }
                    }
                } else if (i7 != 3) {
                    if (i7 == 4) {
                        nc0 nc0Var = (nc0) x31Var.f61014a2;
                        nc0 nc0Var2 = (nc0) x31Var2.f61014a2;
                        int iM214068a4 = nc0Var.m214068a4();
                        int i11 = i;
                        while (i11 < iM214068a4) {
                            View view6 = (View) nc0Var.m214069a5(i11);
                            if (view6 == null || !s71Var.m214580b7(view6)) {
                                z2 = z3;
                                r7 = c0130bd;
                            } else {
                                if (nc0Var.f58493a0) {
                                    nc0Var.m214065a1();
                                }
                                z2 = z3;
                                C0130bd c0130bd7 = c0130bd;
                                View view7 = (View) nc0Var2.m214066a2(nc0Var.f58494a1[i11], l);
                                if (view7 == null || !s71Var.m214580b7(view7)) {
                                    r7 = c0130bd7;
                                } else {
                                    r7 = c0130bd7;
                                    y71 y71Var4 = (y71) r7.getOrDefault(view6, l);
                                    y71 y71Var5 = (y71) c0130bd2.getOrDefault(view7, l);
                                    if (y71Var4 != null && y71Var5 != null) {
                                        s71Var.f59909b0.add(y71Var4);
                                        s71Var.f59910b1.add(y71Var5);
                                        r7.remove(view6);
                                        c0130bd2.remove(view7);
                                    }
                                }
                            }
                            i11++;
                            c0130bd = r7;
                            z3 = z2;
                            l = null;
                        }
                    }
                    z = z3;
                    c0130bd4 = c0130bd;
                } else {
                    z = z3;
                    c0130bd4 = c0130bd;
                    SparseArray sparseArray = (SparseArray) x31Var.f61013a1;
                    SparseArray sparseArray2 = (SparseArray) x31Var2.f61013a1;
                    int size2 = sparseArray.size();
                    for (int i12 = 0; i12 < size2; i12++) {
                        View view8 = (View) sparseArray.valueAt(i12);
                        if (view8 != null && s71Var.m214580b7(view8) && (view3 = (View) sparseArray2.get(sparseArray.keyAt(i12))) != null && s71Var.m214580b7(view3)) {
                            y71 y71Var6 = (y71) c0130bd4.getOrDefault(view8, null);
                            y71 y71Var7 = (y71) c0130bd2.getOrDefault(view3, null);
                            if (y71Var6 != null && y71Var7 != null) {
                                s71Var.f59909b0.add(y71Var6);
                                s71Var.f59910b1.add(y71Var7);
                                c0130bd4.remove(view8);
                                c0130bd2.remove(view3);
                            }
                        }
                    }
                }
                i2++;
                c0130bd = c0130bd4;
                z3 = z;
                i = 0;
                l = null;
            }
            boolean z4 = z3;
            c0130bd3 = c0130bd;
            for (i3 = 0; i3 < c0130bd3.f60117a2; i3++) {
                y71 y71Var8 = (y71) c0130bd3.m214681a9(i3);
                if (s71Var.m214580b7(y71Var8.f61263a1)) {
                    s71Var.f59909b0.add(y71Var8);
                    s71Var.f59910b1.add(null);
                }
            }
            for (i4 = 0; i4 < c0130bd2.f60117a2; i4++) {
                y71 y71Var9 = (y71) c0130bd2.m214681a9(i4);
                if (s71Var.m214580b7(y71Var9.f61263a1)) {
                    s71Var.f59910b1.add(y71Var9);
                    s71Var.f59909b0.add(null);
                }
            }
            C0130bd c0130bdM214570b3 = s71.m214570b3();
            int i13 = c0130bdM214570b3.f60117a2;
            jd1 jd1Var = hd1.f56654a0;
            WindowId windowId = viewGroup.getWindowId();
            i5 = i13 - 1;
            while (i5 >= 0) {
                Animator animator = (Animator) c0130bdM214570b3.m214679a7(i5);
                if (animator != null && (q71Var = (q71) c0130bdM214570b3.getOrDefault(animator, null)) != null && (view = q71Var.f59420a0) != null && q71Var.f59423a3.f46132a0.equals(windowId)) {
                    y71 y71Var10 = q71Var.f59422a2;
                    boolean z5 = z4;
                    y71 y71VarM214578b5 = s71Var.m214578b5(view, z5);
                    y71 y71VarM214577b2 = s71Var.m214577b2(view, z5);
                    if (y71VarM214578b5 == null && y71VarM214577b2 == null) {
                        y71VarM214577b2 = (y71) ((C0130bd) s71Var.f59906a7.f61012a0).getOrDefault(view, null);
                    }
                    if ((y71VarM214578b5 != null || y71VarM214577b2 != null) && q71Var.f59424a4.mo214579b6(y71Var10, y71VarM214577b2)) {
                        if (animator.isRunning() || animator.isStarted()) {
                            animator.cancel();
                        } else {
                            c0130bdM214570b3.remove(animator);
                        }
                    }
                }
                i5--;
                z4 = true;
            }
            s71Var.mo210784b0(viewGroup, s71Var.f59905a6, s71Var.f59906a7, s71Var.f59909b0, s71Var.f59910b1);
            s71Var.mo210787c2();
            return true;
        }
        arrayList2 = new ArrayList();
        c0130bdM215016a1.put(viewGroup, arrayList2);
        arrayList2.add(s71Var);
        s71Var.m214572a0(new u71(this, c0130bdM215016a1));
        i = 0;
        s71Var.m214574a6(viewGroup, false);
        if (arrayList != null) {
        }
        s71Var.f59909b0 = new ArrayList();
        s71Var.f59910b1 = new ArrayList();
        x31 x31Var3 = s71Var.f59905a6;
        x31 x31Var22 = s71Var.f59906a7;
        c0130bd = new C0130bd((C0130bd) x31Var3.f61012a0);
        c0130bd2 = new C0130bd((C0130bd) x31Var22.f61012a0);
        i2 = 0;
        while (true) {
            iArr = s71Var.f59908a9;
            if (i2 < iArr.length) {
            }
            i2++;
            c0130bd = c0130bd4;
            z3 = z;
            i = 0;
            l = null;
        }
        boolean z42 = z3;
        c0130bd3 = c0130bd;
        while (i3 < c0130bd3.f60117a2) {
        }
        while (i4 < c0130bd2.f60117a2) {
        }
        C0130bd c0130bdM214570b32 = s71.m214570b3();
        int i132 = c0130bdM214570b32.f60117a2;
        jd1 jd1Var2 = hd1.f56654a0;
        WindowId windowId2 = viewGroup.getWindowId();
        i5 = i132 - 1;
        while (i5 >= 0) {
        }
        s71Var.mo210784b0(viewGroup, s71Var.f59905a6, s71Var.f59906a7, s71Var.f59909b0, s71Var.f59910b1);
        s71Var.mo210787c2();
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewDetachedFromWindow(View view) {
        ViewGroup viewGroup = this.f60596a1;
        viewGroup.getViewTreeObserver().removeOnPreDrawListener(this);
        viewGroup.removeOnAttachStateChangeListener(this);
        w71.f60803a2.remove(viewGroup);
        ArrayList arrayList = (ArrayList) w71.m215016a1().getOrDefault(viewGroup, null);
        if (arrayList != null && arrayList.size() > 0) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((s71) obj).mo210786c1(viewGroup);
            }
        }
        this.f60595a0.m214575a7(true);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public final void onViewAttachedToWindow(View view) {
    }
}
