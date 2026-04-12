package p000;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class yo0 {

    /* renamed from: a0 */
    public final int f61349a0;

    /* renamed from: a1 */
    public final int f61350a1;

    /* renamed from: a2 */
    public final long f61351a2;

    /* renamed from: a3 */
    public final long f61352a3;

    public yo0(int i, int i2, long j, long j2) {
        this.f61349a0 = i;
        this.f61350a1 = i2;
        this.f61351a2 = j;
        this.f61352a3 = j2;
    }

    /* renamed from: a0 */
    public static yo0 m215301a0(File file) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new FileInputStream(file));
        try {
            yo0 yo0Var = new yo0(dataInputStream.readInt(), dataInputStream.readInt(), dataInputStream.readLong(), dataInputStream.readLong());
            dataInputStream.close();
            return yo0Var;
        } finally {
        }
    }

    /* renamed from: a1 */
    public final void m215302a1(File file) throws IOException {
        file.delete();
        DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(file));
        try {
            dataOutputStream.writeInt(this.f61349a0);
            dataOutputStream.writeInt(this.f61350a1);
            dataOutputStream.writeLong(this.f61351a2);
            dataOutputStream.writeLong(this.f61352a3);
            dataOutputStream.close();
        } catch (Throwable th) {
            try {
                dataOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && (obj instanceof yo0)) {
            yo0 yo0Var = (yo0) obj;
            if (this.f61350a1 == yo0Var.f61350a1 && this.f61351a2 == yo0Var.f61351a2 && this.f61349a0 == yo0Var.f61349a0 && this.f61352a3 == yo0Var.f61352a3) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.f61350a1), Long.valueOf(this.f61351a2), Integer.valueOf(this.f61349a0), Long.valueOf(this.f61352a3));
    }
}
