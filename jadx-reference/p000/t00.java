package p000;

import android.database.sqlite.SQLiteProgram;
import java.io.Closeable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class t00 implements l31 {

    /* renamed from: a0 */
    public final /* synthetic */ int f60109a0 = 0;

    /* renamed from: a1 */
    public final Closeable f60110a1;

    public t00(SQLiteProgram sQLiteProgram) {
        t60.m214695b6(sQLiteProgram, "delegate");
        this.f60110a1 = sQLiteProgram;
    }

    @Override // p000.l31
    /* renamed from: a6 */
    public final void mo213341a6(int i, String str) {
        switch (this.f60109a0) {
            case 0:
                t60.m214695b6(str, "value");
                ((SQLiteProgram) this.f60110a1).bindString(i, str);
                break;
            default:
                t60.m214695b6(str, "value");
                ((js0) this.f60110a1).mo213341a6(i, str);
                break;
        }
    }

    @Override // p000.l31
    /* renamed from: a9 */
    public final void mo213343a9(int i) {
        switch (this.f60109a0) {
            case 0:
                ((SQLiteProgram) this.f60110a1).bindNull(i);
                break;
            default:
                ((js0) this.f60110a1).mo213343a9(i);
                break;
        }
    }

    @Override // p000.l31
    /* renamed from: b1 */
    public final void mo213345b1(int i, double d) {
        switch (this.f60109a0) {
            case 0:
                ((SQLiteProgram) this.f60110a1).bindDouble(i, d);
                break;
            default:
                ((js0) this.f60110a1).mo213345b1(i, d);
                break;
        }
    }

    @Override // p000.l31
    /* renamed from: b6 */
    public final void mo213346b6(int i, long j) {
        switch (this.f60109a0) {
            case 0:
                ((SQLiteProgram) this.f60110a1).bindLong(i, j);
                break;
            default:
                ((js0) this.f60110a1).mo213346b6(i, j);
                break;
        }
    }

    @Override // p000.l31
    /* renamed from: c1 */
    public final void mo213347c1(int i, byte[] bArr) {
        switch (this.f60109a0) {
            case 0:
                t60.m214695b6(bArr, "value");
                ((SQLiteProgram) this.f60110a1).bindBlob(i, bArr);
                break;
            default:
                t60.m214695b6(bArr, "value");
                ((js0) this.f60110a1).mo213347c1(i, bArr);
                break;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public final void close() {
        switch (this.f60109a0) {
            case 0:
                ((SQLiteProgram) this.f60110a1).close();
                break;
            default:
                ((js0) this.f60110a1).getClass();
                break;
        }
    }

    public t00(js0 js0Var) {
        this.f60110a1 = js0Var;
    }
}
