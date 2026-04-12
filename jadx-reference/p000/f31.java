package p000;

import android.database.sqlite.SQLiteDatabase;
import androidx.sqlite.p025db.framework.C0090a1;
import java.io.File;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class f31 {

    /* renamed from: a0 */
    public final int f56145a0;

    static {
        new e31(null);
    }

    public f31(int i) {
        this.f56145a0 = i;
    }

    /* renamed from: a0 */
    public static void m212733a0(String str) {
        if (str.equalsIgnoreCase(":memory:")) {
            return;
        }
        int length = str.length() - 1;
        int i = 0;
        boolean z = false;
        while (i <= length) {
            boolean z2 = t60.m214697b8(str.charAt(!z ? i : length), 32) <= 0;
            if (z) {
                if (!z2) {
                    break;
                } else {
                    length--;
                }
            } else if (z2) {
                i++;
            } else {
                z = true;
            }
        }
        if (str.subSequence(i, length + 1).toString().length() == 0) {
            return;
        }
        try {
            SQLiteDatabase.deleteDatabase(new File(str));
        } catch (Exception unused) {
        }
    }

    /* renamed from: a1 */
    public abstract void mo212734a1(C0090a1 c0090a1);

    /* renamed from: a2 */
    public abstract void mo212735a2(C0090a1 c0090a1);

    /* renamed from: a3 */
    public abstract void mo212736a3(C0090a1 c0090a1, int i, int i2);

    /* renamed from: a4 */
    public abstract void mo212737a4(C0090a1 c0090a1);

    /* renamed from: a5 */
    public abstract void mo212738a5(d31 d31Var, int i, int i2);
}
