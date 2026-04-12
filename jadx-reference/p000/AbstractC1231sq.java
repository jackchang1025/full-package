package p000;

import android.database.Cursor;
import androidx.work.impl.WorkDatabase_Impl;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sq */
/* loaded from: classes2.dex */
public abstract class AbstractC1231sq {

    /* renamed from: a0 */
    public static final /* synthetic */ int f60062a0 = 0;

    static {
        t60.m214694b5(C1351vv.m214966b1("DiagnosticsWrkr"), "tagWithPrefix(\"DiagnosticsWrkr\")");
    }

    /* renamed from: a0 */
    public static final void m214657a0(og1 og1Var, zg1 zg1Var, x31 x31Var, ArrayList arrayList) {
        StringBuilder sb = new StringBuilder("\n Id \t Class Name\t Job Id\t State\t Unique Name\t Tags\t");
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            wg1 wg1Var = (wg1) obj;
            jg1 jg1VarM212483b3 = cq0.m212483b3(wg1Var);
            String str = wg1Var.f60912a0;
            v31 v31VarM215110a2 = x31Var.m215110a2(jg1VarM212483b3);
            Integer numValueOf = v31VarM215110a2 != null ? Integer.valueOf(v31VarM215110a2.f60573a2) : null;
            WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) og1Var.f58832a0;
            js0 js0VarAcquire = js0.f57367a8.acquire("SELECT name FROM workname WHERE work_spec_id=?", 1);
            if (str == null) {
                js0VarAcquire.mo213343a9(1);
            } else {
                js0VarAcquire.mo213341a6(1, str);
            }
            workDatabase_Impl.m212857a1();
            Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
            try {
                ArrayList arrayList2 = new ArrayList(cursorM213580c7.getCount());
                while (cursorM213580c7.moveToNext()) {
                    arrayList2.add(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0));
                }
                cursorM213580c7.close();
                js0VarAcquire.m213344b0();
                sb.append("\n" + str + "\t " + wg1Var.f60914a2 + "\t " + numValueOf + "\t " + wg1Var.f60913a1.name() + "\t " + AbstractC0715je.m213295i2(arrayList2, ",", null, null, null, 62) + "\t " + AbstractC0715je.m213295i2(zg1Var.m215411a7(str), ",", null, null, null, 62) + '\t');
            } catch (Throwable th) {
                cursorM213580c7.close();
                js0VarAcquire.m213344b0();
                throw th;
            }
        }
        t60.m214694b5(sb.toString(), "StringBuilder().apply(builderAction).toString()");
    }
}
