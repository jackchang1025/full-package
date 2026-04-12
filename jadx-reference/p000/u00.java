package p000;

import android.database.sqlite.SQLiteStatement;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class u00 extends t00 implements l31 {

    /* renamed from: a2 */
    public final SQLiteStatement f60314a2;

    public u00(SQLiteStatement sQLiteStatement) {
        super(sQLiteStatement);
        this.f60314a2 = sQLiteStatement;
    }

    /* renamed from: a0 */
    public final int m214812a0() {
        return this.f60314a2.executeUpdateDelete();
    }
}
