package p000;

import android.content.Context;
import androidx.room.RoomDatabase$JournalMode;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qh */
/* loaded from: classes.dex */
public final class C1110qh {

    /* renamed from: a0 */
    public final Context f59506a0;

    /* renamed from: a1 */
    public final String f59507a1;

    /* renamed from: a2 */
    public final j31 f59508a2;

    /* renamed from: a3 */
    public final jl0 f59509a3;

    /* renamed from: a4 */
    public final List f59510a4;

    /* renamed from: a5 */
    public final boolean f59511a5;

    /* renamed from: a6 */
    public final RoomDatabase$JournalMode f59512a6;

    /* renamed from: a7 */
    public final Executor f59513a7;

    /* renamed from: a8 */
    public final Executor f59514a8;

    /* renamed from: a9 */
    public final boolean f59515a9;

    /* renamed from: b0 */
    public final boolean f59516b0;

    /* renamed from: b1 */
    public final Set f59517b1;

    public C1110qh(Context context, String str, j31 j31Var, jl0 jl0Var, List list, boolean z, RoomDatabase$JournalMode roomDatabase$JournalMode, Executor executor, Executor executor2, boolean z2, boolean z3, Set set, List list2, List list3) {
        t60.m214695b6(jl0Var, "migrationContainer");
        t60.m214695b6(executor, "queryExecutor");
        t60.m214695b6(executor2, "transactionExecutor");
        t60.m214695b6(list2, "typeConverters");
        t60.m214695b6(list3, "autoMigrationSpecs");
        this.f59506a0 = context;
        this.f59507a1 = str;
        this.f59508a2 = j31Var;
        this.f59509a3 = jl0Var;
        this.f59510a4 = list;
        this.f59511a5 = z;
        this.f59512a6 = roomDatabase$JournalMode;
        this.f59513a7 = executor;
        this.f59514a8 = executor2;
        this.f59515a9 = z2;
        this.f59516b0 = z3;
        this.f59517b1 = set;
    }
}
