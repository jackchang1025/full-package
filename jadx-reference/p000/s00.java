package p000;

import android.database.sqlite.SQLiteDatabase;
import androidx.sqlite.p025db.framework.C0090a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class s00 {
    public /* synthetic */ s00(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0090a1 getWrappedDb(q00 q00Var, SQLiteDatabase sQLiteDatabase) {
        t60.m214695b6(q00Var, "refHolder");
        t60.m214695b6(sQLiteDatabase, "sqLiteDatabase");
        C0090a1 c0090a1 = q00Var.f59351a0;
        if (c0090a1 != null && c0090a1.f45452a0.equals(sQLiteDatabase)) {
            return c0090a1;
        }
        C0090a1 c0090a12 = new C0090a1(sQLiteDatabase);
        q00Var.f59351a0 = c0090a12;
        return c0090a12;
    }

    private s00() {
    }
}
