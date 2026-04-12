package p000;

import android.content.Context;
import androidx.room.RoomDatabase$JournalMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ds0 {

    /* renamed from: a0 */
    public final Context f55867a0;

    /* renamed from: a1 */
    public final String f55868a1;

    /* renamed from: a5 */
    public Executor f55872a5;

    /* renamed from: a6 */
    public Executor f55873a6;

    /* renamed from: a7 */
    public C0474ez f55874a7;

    /* renamed from: a8 */
    public boolean f55875a8;

    /* renamed from: b1 */
    public boolean f55878b1;

    /* renamed from: b5 */
    public HashSet f55882b5;

    /* renamed from: a2 */
    public final ArrayList f55869a2 = new ArrayList();

    /* renamed from: a3 */
    public final ArrayList f55870a3 = new ArrayList();

    /* renamed from: a4 */
    public final ArrayList f55871a4 = new ArrayList();

    /* renamed from: a9 */
    public final RoomDatabase$JournalMode f55876a9 = RoomDatabase$JournalMode.f45365a0;

    /* renamed from: b0 */
    public boolean f55877b0 = true;

    /* renamed from: b2 */
    public final long f55879b2 = -1;

    /* renamed from: b3 */
    public final jl0 f55880b3 = new jl0(4);

    /* renamed from: b4 */
    public final LinkedHashSet f55881b4 = new LinkedHashSet();

    public ds0(Context context, String str) {
        this.f55867a0 = context;
        this.f55868a1 = str;
    }

    /* renamed from: a0 */
    public final void m212636a0(cg0... cg0VarArr) {
        if (this.f55882b5 == null) {
            this.f55882b5 = new HashSet();
        }
        for (cg0 cg0Var : cg0VarArr) {
            HashSet hashSet = this.f55882b5;
            t60.m214692b3(hashSet);
            hashSet.add(Integer.valueOf(cg0Var.f46133a0));
            HashSet hashSet2 = this.f55882b5;
            t60.m214692b3(hashSet2);
            hashSet2.add(Integer.valueOf(cg0Var.f46134a1));
        }
        this.f55880b3.m213322a3((cg0[]) Arrays.copyOf(cg0VarArr, cg0VarArr.length));
    }
}
