package p000;

import android.R;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.SparseArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import androidx.core.internal.view.SupportMenu;
import androidx.core.internal.view.SupportMenuItem;
import androidx.viewpager2.widget.ViewPager2;
import androidx.work.impl.WorkDatabase_Impl;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashSet;
import okio.Segment;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class x31 {

    /* renamed from: a0 */
    public final Object f61012a0;

    /* renamed from: a1 */
    public final Object f61013a1;

    /* renamed from: a2 */
    public Object f61014a2;

    /* renamed from: a3 */
    public final Object f61015a3;

    public x31(Context context, pg1 pg1Var) {
        t60.m214695b6(context, "context");
        Context applicationContext = context.getApplicationContext();
        t60.m214694b5(applicationContext, "context.applicationContext");
        C0421dl c0421dl = new C0421dl(applicationContext, pg1Var, 0);
        Context applicationContext2 = context.getApplicationContext();
        t60.m214694b5(applicationContext2, "context.applicationContext");
        C0421dl c0421dl2 = new C0421dl(applicationContext2, pg1Var, 1);
        Context applicationContext3 = context.getApplicationContext();
        t60.m214694b5(applicationContext3, "context.applicationContext");
        int i = tj0.f60235a0;
        sj0 sj0Var = new sj0(applicationContext3, pg1Var);
        Context applicationContext4 = context.getApplicationContext();
        t60.m214694b5(applicationContext4, "context.applicationContext");
        C0421dl c0421dl3 = new C0421dl(applicationContext4, pg1Var, 2);
        this.f61012a0 = c0421dl;
        this.f61013a1 = c0421dl2;
        this.f61014a2 = sj0Var;
        this.f61015a3 = c0421dl3;
    }

    /* renamed from: a0 */
    public void m215108a0(Object obj, ArrayList arrayList, HashSet hashSet) {
        if (arrayList.contains(obj)) {
            return;
        }
        if (hashSet.contains(obj)) {
            throw new RuntimeException("This graph contains cyclic dependencies");
        }
        hashSet.add(obj);
        ArrayList arrayList2 = (ArrayList) ((t01) this.f61013a1).getOrDefault(obj, null);
        if (arrayList2 != null) {
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                m215108a0(arrayList2.get(i), arrayList, hashSet);
            }
        }
        hashSet.remove(obj);
        arrayList.add(obj);
    }

    /* renamed from: a1 */
    public z21 m215109a1(AbstractC0903n7 abstractC0903n7) {
        ArrayList arrayList = (ArrayList) this.f61014a2;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            z21 z21Var = (z21) arrayList.get(i);
            if (z21Var != null && z21Var.f61429a1 == abstractC0903n7) {
                return z21Var;
            }
        }
        z21 z21Var2 = new z21((Context) this.f61013a1, abstractC0903n7);
        arrayList.add(z21Var2);
        return z21Var2;
    }

    /* renamed from: a2 */
    public v31 m215110a2(jg1 jg1Var) {
        String str = jg1Var.f57334a0;
        int i = jg1Var.f57335a1;
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61012a0;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT * FROM SystemIdInfo WHERE work_spec_id=? AND generation=?", 2);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        js0VarAcquire.mo213346b6(2, i);
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            int iM212484b4 = cq0.m212484b4(cursorM213580c7, "work_spec_id");
            int iM212484b42 = cq0.m212484b4(cursorM213580c7, "generation");
            int iM212484b43 = cq0.m212484b4(cursorM213580c7, "system_id");
            v31 v31Var = null;
            String string = null;
            if (cursorM213580c7.moveToFirst()) {
                if (!cursorM213580c7.isNull(iM212484b4)) {
                    string = cursorM213580c7.getString(iM212484b4);
                }
                v31Var = new v31(string, cursorM213580c7.getInt(iM212484b42), cursorM213580c7.getInt(iM212484b43));
            }
            return v31Var;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a3 */
    public void m215111a3(v31 v31Var) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f61012a0;
        workDatabase_Impl.m212857a1();
        workDatabase_Impl.m212858a2();
        try {
            ((C1216sb) this.f61013a1).m214590a5(v31Var);
            workDatabase_Impl.m212863b2();
        } finally {
            workDatabase_Impl.m212860a9();
        }
    }

    /* renamed from: a4 */
    public boolean m215112a4(AbstractC0903n7 abstractC0903n7, MenuItem menuItem) {
        return ((ActionMode.Callback) this.f61012a0).onActionItemClicked(m215109a1(abstractC0903n7), new jf0((Context) this.f61013a1, (SupportMenuItem) menuItem));
    }

    /* renamed from: a5 */
    public boolean m215113a5(AbstractC0903n7 abstractC0903n7, Menu menu) {
        ActionMode.Callback callback = (ActionMode.Callback) this.f61012a0;
        z21 z21VarM215109a1 = m215109a1(abstractC0903n7);
        t01 t01Var = (t01) this.f61015a3;
        Menu wf0Var = (Menu) t01Var.getOrDefault(menu, null);
        if (wf0Var == null) {
            wf0Var = new wf0((Context) this.f61013a1, (SupportMenu) menu);
            t01Var.put(menu, wf0Var);
        }
        return callback.onCreateActionMode(z21VarM215109a1, wf0Var);
    }

    /* renamed from: a6 */
    public void m215114a6() {
        int iMo211032a0;
        fc1 fc1Var = (fc1) this.f61013a1;
        fc1 fc1Var2 = (fc1) this.f61012a0;
        ViewPager2 viewPager2 = (ViewPager2) this.f61015a3;
        int i = R.id.accessibilityActionPageLeft;
        xa1.m215149b1(viewPager2, R.id.accessibilityActionPageLeft);
        xa1.m215146a8(viewPager2, 0);
        xa1.m215149b1(viewPager2, R.id.accessibilityActionPageRight);
        xa1.m215146a8(viewPager2, 0);
        xa1.m215149b1(viewPager2, R.id.accessibilityActionPageUp);
        xa1.m215146a8(viewPager2, 0);
        xa1.m215149b1(viewPager2, R.id.accessibilityActionPageDown);
        xa1.m215146a8(viewPager2, 0);
        if (viewPager2.getAdapter() == null || (iMo211032a0 = viewPager2.getAdapter().mo211032a0()) == 0 || !viewPager2.f45489b7) {
            return;
        }
        if (viewPager2.getOrientation() != 0) {
            if (viewPager2.f45475a3 < iMo211032a0 - 1) {
                xa1.m215150b2(viewPager2, new C0745k4(R.id.accessibilityActionPageDown), null, fc1Var2);
            }
            if (viewPager2.f45475a3 > 0) {
                xa1.m215150b2(viewPager2, new C0745k4(R.id.accessibilityActionPageUp), null, fc1Var);
                return;
            }
            return;
        }
        boolean z = viewPager2.f45478a6.m214312c5() == 1;
        int i2 = z ? 16908360 : 16908361;
        if (z) {
            i = 16908361;
        }
        if (viewPager2.f45475a3 < iMo211032a0 - 1) {
            xa1.m215150b2(viewPager2, new C0745k4(i2), null, fc1Var2);
        }
        if (viewPager2.f45475a3 > 0) {
            xa1.m215150b2(viewPager2, new C0745k4(i), null, fc1Var);
        }
    }

    public x31(WorkDatabase_Impl workDatabase_Impl) {
        this.f61012a0 = workDatabase_Impl;
        this.f61013a1 = new C1216sb(workDatabase_Impl, 2);
        this.f61014a2 = new w31(workDatabase_Impl, 0);
        this.f61015a3 = new w31(workDatabase_Impl, 1);
    }

    public x31(int i) {
        switch (i) {
            case 5:
                this.f61012a0 = new C0130bd();
                this.f61013a1 = new SparseArray();
                this.f61014a2 = new nc0();
                this.f61015a3 = new C0130bd();
                break;
            default:
                this.f61012a0 = new vn0(10);
                this.f61013a1 = new t01();
                this.f61014a2 = new ArrayList();
                this.f61015a3 = new HashSet();
                break;
        }
    }

    public x31(Typeface typeface, zf0 zf0Var) {
        int i;
        int i2;
        int i3;
        int i4;
        this.f61015a3 = typeface;
        this.f61012a0 = zf0Var;
        this.f61014a2 = new ag0(Segment.SHARE_MINIMUM);
        int iM215362a0 = zf0Var.m215362a0(6);
        if (iM215362a0 != 0) {
            int i5 = iM215362a0 + zf0Var.f61455a0;
            i = ((ByteBuffer) zf0Var.f61458a3).getInt(((ByteBuffer) zf0Var.f61458a3).getInt(i5) + i5);
        } else {
            i = 0;
        }
        this.f61013a1 = new char[i * 2];
        int iM215362a02 = zf0Var.m215362a0(6);
        if (iM215362a02 != 0) {
            int i6 = iM215362a02 + zf0Var.f61455a0;
            i2 = ((ByteBuffer) zf0Var.f61458a3).getInt(((ByteBuffer) zf0Var.f61458a3).getInt(i6) + i6);
        } else {
            i2 = 0;
        }
        for (int i7 = 0; i7 < i2; i7++) {
            C1384wo c1384wo = new C1384wo(this, i7);
            yf0 yf0VarM215084a1 = c1384wo.m215084a1();
            int iM215362a03 = yf0VarM215084a1.m215362a0(4);
            Character.toChars(iM215362a03 != 0 ? ((ByteBuffer) yf0VarM215084a1.f61458a3).getInt(iM215362a03 + yf0VarM215084a1.f61455a0) : 0, (char[]) this.f61013a1, i7 * 2);
            yf0 yf0VarM215084a12 = c1384wo.m215084a1();
            int iM215362a04 = yf0VarM215084a12.m215362a0(16);
            if (iM215362a04 != 0) {
                int i8 = iM215362a04 + yf0VarM215084a12.f61455a0;
                i3 = ((ByteBuffer) yf0VarM215084a12.f61458a3).getInt(((ByteBuffer) yf0VarM215084a12.f61458a3).getInt(i8) + i8);
            } else {
                i3 = 0;
            }
            b81.m210566a6(i3 > 0, "invalid metadata codepoint length");
            ag0 ag0Var = (ag0) this.f61014a2;
            yf0 yf0VarM215084a13 = c1384wo.m215084a1();
            int iM215362a05 = yf0VarM215084a13.m215362a0(16);
            if (iM215362a05 != 0) {
                int i9 = iM215362a05 + yf0VarM215084a13.f61455a0;
                i4 = ((ByteBuffer) yf0VarM215084a13.f61458a3).getInt(((ByteBuffer) yf0VarM215084a13.f61458a3).getInt(i9) + i9);
            } else {
                i4 = 0;
            }
            ag0Var.m209800a0(c1384wo, 0, i4 - 1);
        }
    }

    public x31(Context context, ActionMode.Callback callback) {
        this.f61013a1 = context;
        this.f61012a0 = callback;
        this.f61014a2 = new ArrayList();
        this.f61015a3 = new t01();
    }

    public x31(ViewPager2 viewPager2) {
        this.f61015a3 = viewPager2;
        this.f61012a0 = new fc1(this, 0);
        this.f61013a1 = new fc1(this, 1);
    }
}
