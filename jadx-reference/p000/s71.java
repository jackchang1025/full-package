package p000;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class s71 implements Cloneable {

    /* renamed from: b9 */
    public static final int[] f59896b9 = {2, 1, 3, 4};

    /* renamed from: c0 */
    public static final fh0 f59897c0 = new fh0(15);

    /* renamed from: c1 */
    public static final ThreadLocal f59898c1 = new ThreadLocal();

    /* renamed from: b0 */
    public ArrayList f59909b0;

    /* renamed from: b1 */
    public ArrayList f59910b1;

    /* renamed from: a0 */
    public final String f59899a0 = getClass().getName();

    /* renamed from: a1 */
    public long f59900a1 = -1;

    /* renamed from: a2 */
    public long f59901a2 = -1;

    /* renamed from: a3 */
    public TimeInterpolator f59902a3 = null;

    /* renamed from: a4 */
    public final ArrayList f59903a4 = new ArrayList();

    /* renamed from: a5 */
    public final ArrayList f59904a5 = new ArrayList();

    /* renamed from: a6 */
    public x31 f59905a6 = new x31(5);

    /* renamed from: a7 */
    public x31 f59906a7 = new x31(5);

    /* renamed from: a8 */
    public C0166cb f59907a8 = null;

    /* renamed from: a9 */
    public final int[] f59908a9 = f59896b9;

    /* renamed from: b2 */
    public final ArrayList f59911b2 = new ArrayList();

    /* renamed from: b3 */
    public int f59912b3 = 0;

    /* renamed from: b4 */
    public boolean f59913b4 = false;

    /* renamed from: b5 */
    public boolean f59914b5 = false;

    /* renamed from: b6 */
    public ArrayList f59915b6 = null;

    /* renamed from: b7 */
    public ArrayList f59916b7 = new ArrayList();

    /* renamed from: b8 */
    public fh0 f59917b8 = f59897c0;

    /* renamed from: a1 */
    public static void m214569a1(x31 x31Var, View view, y71 y71Var) {
        C0130bd c0130bd = (C0130bd) x31Var.f61012a0;
        C0130bd c0130bd2 = (C0130bd) x31Var.f61015a3;
        SparseArray sparseArray = (SparseArray) x31Var.f61013a1;
        nc0 nc0Var = (nc0) x31Var.f61014a2;
        c0130bd.put(view, y71Var);
        int id = view.getId();
        if (id >= 0) {
            if (sparseArray.indexOfKey(id) >= 0) {
                sparseArray.put(id, null);
            } else {
                sparseArray.put(id, view);
            }
        }
        WeakHashMap weakHashMap = xa1.f61054a0;
        String strM213811b0 = la1.m213811b0(view);
        if (strM213811b0 != null) {
            if (c0130bd2.containsKey(strM213811b0)) {
                c0130bd2.put(strM213811b0, null);
            } else {
                c0130bd2.put(strM213811b0, view);
            }
        }
        if (view.getParent() instanceof ListView) {
            ListView listView = (ListView) view.getParent();
            if (listView.getAdapter().hasStableIds()) {
                long itemIdAtPosition = listView.getItemIdAtPosition(listView.getPositionForView(view));
                if (nc0Var.f58493a0) {
                    nc0Var.m214065a1();
                }
                if (t60.m214688a6(itemIdAtPosition, nc0Var.f58494a1, nc0Var.f58496a3) < 0) {
                    fa1.m212780b7(view, true);
                    nc0Var.m214067a3(itemIdAtPosition, view);
                    return;
                }
                View view2 = (View) nc0Var.m214066a2(itemIdAtPosition, null);
                if (view2 != null) {
                    fa1.m212780b7(view2, false);
                    nc0Var.m214067a3(itemIdAtPosition, null);
                }
            }
        }
    }

    /* renamed from: b3 */
    public static C0130bd m214570b3() {
        ThreadLocal threadLocal = f59898c1;
        C0130bd c0130bd = (C0130bd) threadLocal.get();
        if (c0130bd != null) {
            return c0130bd;
        }
        C0130bd c0130bd2 = new C0130bd();
        threadLocal.set(c0130bd2);
        return c0130bd2;
    }

    /* renamed from: b8 */
    public static boolean m214571b8(y71 y71Var, y71 y71Var2, String str) {
        Object obj = y71Var.f61262a0.get(str);
        Object obj2 = y71Var2.f61262a0.get(str);
        if (obj == null && obj2 == null) {
            return false;
        }
        if (obj == null || obj2 == null) {
            return true;
        }
        return !obj.equals(obj2);
    }

    /* renamed from: a0 */
    public void m214572a0(r71 r71Var) {
        if (this.f59915b6 == null) {
            this.f59915b6 = new ArrayList();
        }
        this.f59915b6.add(r71Var);
    }

    /* renamed from: a2 */
    public abstract void mo210780a2(y71 y71Var);

    /* renamed from: a3 */
    public final void m214573a3(View view, boolean z) {
        if (view == null) {
            return;
        }
        view.getId();
        if (view.getParent() instanceof ViewGroup) {
            y71 y71Var = new y71(view);
            if (z) {
                mo210782a5(y71Var);
            } else {
                mo210780a2(y71Var);
            }
            y71Var.f61264a2.add(this);
            mo210781a4(y71Var);
            if (z) {
                m214569a1(this.f59905a6, view, y71Var);
            } else {
                m214569a1(this.f59906a7, view, y71Var);
            }
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                m214573a3(viewGroup.getChildAt(i), z);
            }
        }
    }

    /* renamed from: a5 */
    public abstract void mo210782a5(y71 y71Var);

    /* renamed from: a6 */
    public final void m214574a6(ViewGroup viewGroup, boolean z) {
        m214575a7(z);
        ArrayList arrayList = this.f59903a4;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f59904a5;
        if (size <= 0 && arrayList2.size() <= 0) {
            m214573a3(viewGroup, z);
            return;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            View viewFindViewById = viewGroup.findViewById(((Integer) arrayList.get(i)).intValue());
            if (viewFindViewById != null) {
                y71 y71Var = new y71(viewFindViewById);
                if (z) {
                    mo210782a5(y71Var);
                } else {
                    mo210780a2(y71Var);
                }
                y71Var.f61264a2.add(this);
                mo210781a4(y71Var);
                if (z) {
                    m214569a1(this.f59905a6, viewFindViewById, y71Var);
                } else {
                    m214569a1(this.f59906a7, viewFindViewById, y71Var);
                }
            }
        }
        for (int i2 = 0; i2 < arrayList2.size(); i2++) {
            View view = (View) arrayList2.get(i2);
            y71 y71Var2 = new y71(view);
            if (z) {
                mo210782a5(y71Var2);
            } else {
                mo210780a2(y71Var2);
            }
            y71Var2.f61264a2.add(this);
            mo210781a4(y71Var2);
            if (z) {
                m214569a1(this.f59905a6, view, y71Var2);
            } else {
                m214569a1(this.f59906a7, view, y71Var2);
            }
        }
    }

    /* renamed from: a7 */
    public final void m214575a7(boolean z) {
        if (z) {
            ((C0130bd) this.f59905a6.f61012a0).clear();
            ((SparseArray) this.f59905a6.f61013a1).clear();
            ((nc0) this.f59905a6.f61014a2).m214064a0();
        } else {
            ((C0130bd) this.f59906a7.f61012a0).clear();
            ((SparseArray) this.f59906a7.f61013a1).clear();
            ((nc0) this.f59906a7.f61014a2).m214064a0();
        }
    }

    @Override // 
    /* renamed from: a8, reason: merged with bridge method [inline-methods] */
    public s71 clone() {
        try {
            s71 s71Var = (s71) super.clone();
            s71Var.f59916b7 = new ArrayList();
            s71Var.f59905a6 = new x31(5);
            s71Var.f59906a7 = new x31(5);
            s71Var.f59909b0 = null;
            s71Var.f59910b1 = null;
            return s71Var;
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }

    /* renamed from: a9 */
    public Animator mo212988a9(ViewGroup viewGroup, y71 y71Var, y71 y71Var2) {
        return null;
    }

    /* renamed from: b0 */
    public void mo210784b0(ViewGroup viewGroup, x31 x31Var, x31 x31Var2, ArrayList arrayList, ArrayList arrayList2) {
        Animator animatorMo212988a9;
        int i;
        int i2;
        View view;
        y71 y71Var;
        Animator animator;
        C0130bd c0130bdM214570b3 = m214570b3();
        SparseIntArray sparseIntArray = new SparseIntArray();
        int size = arrayList.size();
        int i3 = 0;
        while (i3 < size) {
            y71 y71Var2 = (y71) arrayList.get(i3);
            y71 y71Var3 = (y71) arrayList2.get(i3);
            y71 y71Var4 = null;
            if (y71Var2 != null && !y71Var2.f61264a2.contains(this)) {
                y71Var2 = null;
            }
            if (y71Var3 != null && !y71Var3.f61264a2.contains(this)) {
                y71Var3 = null;
            }
            if (!(y71Var2 == null && y71Var3 == null) && ((y71Var2 == null || y71Var3 == null || mo214579b6(y71Var2, y71Var3)) && (animatorMo212988a9 = mo212988a9(viewGroup, y71Var2, y71Var3)) != null)) {
                String str = this.f59899a0;
                if (y71Var3 != null) {
                    view = y71Var3.f61263a1;
                    String[] strArrMo212989b4 = mo212989b4();
                    if (strArrMo212989b4 != null && strArrMo212989b4.length > 0) {
                        y71Var = new y71(view);
                        y71 y71Var5 = (y71) ((C0130bd) x31Var2.f61012a0).getOrDefault(view, null);
                        i = size;
                        if (y71Var5 != null) {
                            int i4 = 0;
                            while (i4 < strArrMo212989b4.length) {
                                String str2 = strArrMo212989b4[i4];
                                y71Var.f61262a0.put(str2, y71Var5.f61262a0.get(str2));
                                i4++;
                                i3 = i3;
                                y71Var5 = y71Var5;
                            }
                        }
                        i2 = i3;
                        int i5 = c0130bdM214570b3.f60117a2;
                        for (int i6 = 0; i6 < i5; i6++) {
                            q71 q71Var = (q71) c0130bdM214570b3.getOrDefault((Animator) c0130bdM214570b3.m214679a7(i6), null);
                            if (q71Var.f59422a2 != null && q71Var.f59420a0 == view && q71Var.f59421a1.equals(str) && q71Var.f59422a2.equals(y71Var)) {
                                animator = null;
                                break;
                            }
                        }
                    } else {
                        i = size;
                        i2 = i3;
                        y71Var = null;
                    }
                    animator = animatorMo212988a9;
                    animatorMo212988a9 = animator;
                    y71Var4 = y71Var;
                } else {
                    i = size;
                    i2 = i3;
                    view = y71Var2.f61263a1;
                }
                if (animatorMo212988a9 != null) {
                    jd1 jd1Var = hd1.f56654a0;
                    cf1 cf1Var = new cf1(viewGroup);
                    q71 q71Var2 = new q71();
                    q71Var2.f59420a0 = view;
                    q71Var2.f59421a1 = str;
                    q71Var2.f59422a2 = y71Var4;
                    q71Var2.f59423a3 = cf1Var;
                    q71Var2.f59424a4 = this;
                    c0130bdM214570b3.put(animatorMo212988a9, q71Var2);
                    this.f59916b7.add(animatorMo212988a9);
                }
            } else {
                i = size;
                i2 = i3;
            }
            i3 = i2 + 1;
            size = i;
        }
        if (sparseIntArray.size() != 0) {
            for (int i7 = 0; i7 < sparseIntArray.size(); i7++) {
                Animator animator2 = (Animator) this.f59916b7.get(sparseIntArray.keyAt(i7));
                animator2.setStartDelay(animator2.getStartDelay() + (sparseIntArray.valueAt(i7) - Long.MAX_VALUE));
            }
        }
    }

    /* renamed from: b1 */
    public final void m214576b1() {
        int i = this.f59912b3 - 1;
        this.f59912b3 = i;
        if (i == 0) {
            ArrayList arrayList = this.f59915b6;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.f59915b6.clone();
                int size = arrayList2.size();
                for (int i2 = 0; i2 < size; i2++) {
                    ((r71) arrayList2.get(i2)).mo212985a3(this);
                }
            }
            for (int i3 = 0; i3 < ((nc0) this.f59905a6.f61014a2).m214068a4(); i3++) {
                View view = (View) ((nc0) this.f59905a6.f61014a2).m214069a5(i3);
                if (view != null) {
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    fa1.m212780b7(view, false);
                }
            }
            for (int i4 = 0; i4 < ((nc0) this.f59906a7.f61014a2).m214068a4(); i4++) {
                View view2 = (View) ((nc0) this.f59906a7.f61014a2).m214069a5(i4);
                if (view2 != null) {
                    WeakHashMap weakHashMap2 = xa1.f61054a0;
                    fa1.m212780b7(view2, false);
                }
            }
            this.f59914b5 = true;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x002c, code lost:
    
        if (r2 < 0) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x002e, code lost:
    
        if (r6 == false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0030, code lost:
    
        r5 = r4.f59910b1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0033, code lost:
    
        r5 = r4.f59909b0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x003b, code lost:
    
        return (p000.y71) r5.get(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x003c, code lost:
    
        return null;
     */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final y71 m214577b2(View view, boolean z) {
        C0166cb c0166cb = this.f59907a8;
        if (c0166cb != null) {
            return c0166cb.m214577b2(view, z);
        }
        ArrayList arrayList = z ? this.f59909b0 : this.f59910b1;
        if (arrayList == null) {
            return null;
        }
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            }
            y71 y71Var = (y71) arrayList.get(i);
            if (y71Var == null) {
                return null;
            }
            if (y71Var.f61263a1 == view) {
                break;
            }
            i++;
        }
    }

    /* renamed from: b4 */
    public String[] mo212989b4() {
        return null;
    }

    /* renamed from: b5 */
    public final y71 m214578b5(View view, boolean z) {
        C0166cb c0166cb = this.f59907a8;
        if (c0166cb != null) {
            return c0166cb.m214578b5(view, z);
        }
        return (y71) ((C0130bd) (z ? this.f59905a6 : this.f59906a7).f61012a0).getOrDefault(view, null);
    }

    /* renamed from: b6 */
    public boolean mo214579b6(y71 y71Var, y71 y71Var2) {
        if (y71Var != null && y71Var2 != null) {
            String[] strArrMo212989b4 = mo212989b4();
            if (strArrMo212989b4 != null) {
                for (String str : strArrMo212989b4) {
                    if (m214571b8(y71Var, y71Var2, str)) {
                        return true;
                    }
                }
            } else {
                Iterator it = y71Var.f61262a0.keySet().iterator();
                while (it.hasNext()) {
                    if (m214571b8(y71Var, y71Var2, (String) it.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: b7 */
    public final boolean m214580b7(View view) {
        int id = view.getId();
        ArrayList arrayList = this.f59903a4;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f59904a5;
        return (size == 0 && arrayList2.size() == 0) || arrayList.contains(Integer.valueOf(id)) || arrayList2.contains(view);
    }

    /* renamed from: b9 */
    public void mo210785b9(View view) {
        if (this.f59914b5) {
            return;
        }
        C0130bd c0130bdM214570b3 = m214570b3();
        int i = c0130bdM214570b3.f60117a2;
        jd1 jd1Var = hd1.f56654a0;
        WindowId windowId = view.getWindowId();
        for (int i2 = i - 1; i2 >= 0; i2--) {
            q71 q71Var = (q71) c0130bdM214570b3.m214681a9(i2);
            if (q71Var.f59420a0 != null && q71Var.f59423a3.f46132a0.equals(windowId)) {
                ((Animator) c0130bdM214570b3.m214679a7(i2)).pause();
            }
        }
        ArrayList arrayList = this.f59915b6;
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = (ArrayList) this.f59915b6.clone();
            int size = arrayList2.size();
            for (int i3 = 0; i3 < size; i3++) {
                ((r71) arrayList2.get(i3)).mo212983a1();
            }
        }
        this.f59913b4 = true;
    }

    /* renamed from: c0 */
    public void m214581c0(r71 r71Var) {
        ArrayList arrayList = this.f59915b6;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(r71Var);
        if (this.f59915b6.size() == 0) {
            this.f59915b6 = null;
        }
    }

    /* renamed from: c1 */
    public void mo210786c1(View view) {
        if (this.f59913b4) {
            if (!this.f59914b5) {
                C0130bd c0130bdM214570b3 = m214570b3();
                int i = c0130bdM214570b3.f60117a2;
                jd1 jd1Var = hd1.f56654a0;
                WindowId windowId = view.getWindowId();
                for (int i2 = i - 1; i2 >= 0; i2--) {
                    q71 q71Var = (q71) c0130bdM214570b3.m214681a9(i2);
                    if (q71Var.f59420a0 != null && q71Var.f59423a3.f46132a0.equals(windowId)) {
                        ((Animator) c0130bdM214570b3.m214679a7(i2)).resume();
                    }
                }
                ArrayList arrayList = this.f59915b6;
                if (arrayList != null && arrayList.size() > 0) {
                    ArrayList arrayList2 = (ArrayList) this.f59915b6.clone();
                    int size = arrayList2.size();
                    for (int i3 = 0; i3 < size; i3++) {
                        ((r71) arrayList2.get(i3)).mo212984a2();
                    }
                }
            }
            this.f59913b4 = false;
        }
    }

    /* renamed from: c2 */
    public void mo210787c2() {
        m214582c9();
        C0130bd c0130bdM214570b3 = m214570b3();
        ArrayList arrayList = this.f59916b7;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Animator animator = (Animator) obj;
            if (c0130bdM214570b3.containsKey(animator)) {
                m214582c9();
                if (animator != null) {
                    animator.addListener(new vm0(this, c0130bdM214570b3));
                    long j = this.f59901a2;
                    if (j >= 0) {
                        animator.setDuration(j);
                    }
                    long j2 = this.f59900a1;
                    if (j2 >= 0) {
                        animator.setStartDelay(animator.getStartDelay() + j2);
                    }
                    TimeInterpolator timeInterpolator = this.f59902a3;
                    if (timeInterpolator != null) {
                        animator.setInterpolator(timeInterpolator);
                    }
                    animator.addListener(new C0847m3(9, this));
                    animator.start();
                }
            }
        }
        this.f59916b7.clear();
        m214576b1();
    }

    /* renamed from: c3 */
    public void mo210788c3(long j) {
        this.f59901a2 = j;
    }

    /* renamed from: c5 */
    public void mo210790c5(TimeInterpolator timeInterpolator) {
        this.f59902a3 = timeInterpolator;
    }

    /* renamed from: c6 */
    public void mo210791c6(fh0 fh0Var) {
        if (fh0Var == null) {
            this.f59917b8 = f59897c0;
        } else {
            this.f59917b8 = fh0Var;
        }
    }

    /* renamed from: c8 */
    public void mo210793c8(long j) {
        this.f59900a1 = j;
    }

    /* renamed from: c9 */
    public final void m214582c9() {
        if (this.f59912b3 == 0) {
            ArrayList arrayList = this.f59915b6;
            if (arrayList != null && arrayList.size() > 0) {
                ArrayList arrayList2 = (ArrayList) this.f59915b6.clone();
                int size = arrayList2.size();
                for (int i = 0; i < size; i++) {
                    ((r71) arrayList2.get(i)).mo214186a0();
                }
            }
            this.f59914b5 = false;
        }
        this.f59912b3++;
    }

    /* renamed from: d0 */
    public String mo210794d0(String str) {
        StringBuilder sbM37b8 = AbstractC0003a2.m37b8(str);
        sbM37b8.append(getClass().getSimpleName());
        sbM37b8.append("@");
        sbM37b8.append(Integer.toHexString(hashCode()));
        sbM37b8.append(": ");
        String string = sbM37b8.toString();
        if (this.f59901a2 != -1) {
            StringBuilder sbM39c0 = AbstractC0003a2.m39c0(string, "dur(");
            sbM39c0.append(this.f59901a2);
            sbM39c0.append(") ");
            string = sbM39c0.toString();
        }
        if (this.f59900a1 != -1) {
            StringBuilder sbM39c02 = AbstractC0003a2.m39c0(string, "dly(");
            sbM39c02.append(this.f59900a1);
            sbM39c02.append(") ");
            string = sbM39c02.toString();
        }
        if (this.f59902a3 != null) {
            StringBuilder sbM39c03 = AbstractC0003a2.m39c0(string, "interp(");
            sbM39c03.append(this.f59902a3);
            sbM39c03.append(") ");
            string = sbM39c03.toString();
        }
        ArrayList arrayList = this.f59903a4;
        int size = arrayList.size();
        ArrayList arrayList2 = this.f59904a5;
        if (size <= 0 && arrayList2.size() <= 0) {
            return string;
        }
        String strM32b3 = AbstractC0003a2.m32b3(string, "tgts(");
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (i > 0) {
                    strM32b3 = AbstractC0003a2.m32b3(strM32b3, ", ");
                }
                StringBuilder sbM37b82 = AbstractC0003a2.m37b8(strM32b3);
                sbM37b82.append(arrayList.get(i));
                strM32b3 = sbM37b82.toString();
            }
        }
        if (arrayList2.size() > 0) {
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                if (i2 > 0) {
                    strM32b3 = AbstractC0003a2.m32b3(strM32b3, ", ");
                }
                StringBuilder sbM37b83 = AbstractC0003a2.m37b8(strM32b3);
                sbM37b83.append(arrayList2.get(i2));
                strM32b3 = sbM37b83.toString();
            }
        }
        return AbstractC0003a2.m32b3(strM32b3, ")");
    }

    public final String toString() {
        return mo210794d0("");
    }

    /* renamed from: c7 */
    public void mo210792c7() {
    }

    /* renamed from: a4 */
    public void mo210781a4(y71 y71Var) {
    }

    /* renamed from: c4 */
    public void mo210789c4(t60 t60Var) {
    }
}
