package p000;

import android.content.Intent;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: p7 */
/* loaded from: classes.dex */
public final class C1058p7 extends AbstractC1117qo {
    static {
        new C1057p6(null);
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: f2 */
    public final Object mo212876f2(Intent intent, int i) {
        if (i != -1) {
            return AbstractC0770a1.m213611f6();
        }
        if (intent == null) {
            return AbstractC0770a1.m213611f6();
        }
        String[] stringArrayExtra = intent.getStringArrayExtra("androidx.activity.result.contract.extra.PERMISSIONS");
        int[] intArrayExtra = intent.getIntArrayExtra("androidx.activity.result.contract.extra.PERMISSION_GRANT_RESULTS");
        if (intArrayExtra == null || stringArrayExtra == null) {
            return AbstractC0770a1.m213611f6();
        }
        ArrayList arrayList = new ArrayList(intArrayExtra.length);
        for (int i2 : intArrayExtra) {
            arrayList.add(Boolean.valueOf(i2 == 0));
        }
        ArrayList arrayListM210725e8 = AbstractC0134bh.m210725e8(stringArrayExtra);
        Iterator it = arrayListM210725e8.iterator();
        Iterator it2 = arrayList.iterator();
        ArrayList arrayList2 = new ArrayList(Math.min(AbstractC0717jg.m213310g9(arrayListM210725e8), AbstractC0717jg.m213310g9(arrayList)));
        while (it.hasNext() && it2.hasNext()) {
            arrayList2.add(new Pair(it.next(), it2.next()));
        }
        return AbstractC0770a1.m213617g2(arrayList2);
    }
}
