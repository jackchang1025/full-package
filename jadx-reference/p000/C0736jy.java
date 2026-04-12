package p000;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.fragment.app.C0070a6;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: jy */
/* loaded from: classes.dex */
public final class C0736jy {

    /* renamed from: a0 */
    public Random f57392a0 = new Random();

    /* renamed from: a1 */
    public final HashMap f57393a1 = new HashMap();

    /* renamed from: a2 */
    public final HashMap f57394a2 = new HashMap();

    /* renamed from: a3 */
    public final HashMap f57395a3 = new HashMap();

    /* renamed from: a4 */
    public ArrayList f57396a4 = new ArrayList();

    /* renamed from: a5 */
    public final transient HashMap f57397a5 = new HashMap();

    /* renamed from: a6 */
    public final HashMap f57398a6 = new HashMap();

    /* renamed from: a7 */
    public final Bundle f57399a7 = new Bundle();

    /* renamed from: a0 */
    public final boolean m213358a0(int i, int i2, Intent intent) {
        String str = (String) this.f57393a1.get(Integer.valueOf(i));
        if (str == null) {
            return false;
        }
        C1092q0 c1092q0 = (C1092q0) this.f57397a5.get(str);
        if (c1092q0 != null) {
            C0070a6 c0070a6 = c1092q0.f59349a0;
            if (this.f57396a4.contains(str)) {
                c0070a6.m210157a0(c1092q0.f59350a1.mo212876f2(intent, i2));
                this.f57396a4.remove(str);
                return true;
            }
        }
        this.f57398a6.remove(str);
        this.f57399a7.putParcelable(str, new ActivityResult(intent, i2));
        return true;
    }

    /* renamed from: a1 */
    public final og1 m213359a1(String str, AbstractC1117qo abstractC1117qo, C0070a6 c0070a6) {
        int i;
        HashMap map;
        HashMap map2 = this.f57394a2;
        if (((Integer) map2.get(str)) == null) {
            int iNextInt = this.f57392a0.nextInt(2147418112);
            while (true) {
                i = iNextInt + 65536;
                Integer numValueOf = Integer.valueOf(i);
                map = this.f57393a1;
                if (!map.containsKey(numValueOf)) {
                    break;
                }
                iNextInt = this.f57392a0.nextInt(2147418112);
            }
            map.put(Integer.valueOf(i), str);
            map2.put(str, Integer.valueOf(i));
        }
        this.f57397a5.put(str, new C1092q0(c0070a6, abstractC1117qo));
        HashMap map3 = this.f57398a6;
        if (map3.containsKey(str)) {
            Object obj = map3.get(str);
            map3.remove(str);
            c0070a6.m210157a0(obj);
        }
        Bundle bundle = this.f57399a7;
        ActivityResult activityResult = (ActivityResult) bundle.getParcelable(str);
        if (activityResult != null) {
            bundle.remove(str);
            c0070a6.m210157a0(abstractC1117qo.mo212876f2(activityResult.f43762a1, activityResult.f43761a0));
        }
        return new og1(this, str);
    }
}
