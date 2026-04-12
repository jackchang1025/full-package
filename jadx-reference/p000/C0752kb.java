package p000;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* renamed from: kb */
/* loaded from: classes2.dex */
public class C0752kb {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    private C0752kb() {
    }

    public static C0752kb compose() {
        return new C0752kb();
    }

    public C0752kb bool(boolean z) {
        this.bos.write(z ? 1 : 0);
        return this;
    }

    public byte[] build() {
        return this.bos.toByteArray();
    }

    public C0752kb bytes(InterfaceC1394wy interfaceC1394wy) throws IOException {
        try {
            this.bos.write(interfaceC1394wy.getEncoded());
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public C0752kb pad(int i, int i2) {
        while (i2 >= 0) {
            try {
                this.bos.write(i);
                i2--;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return this;
    }

    public C0752kb padUntil(int i, int i2) {
        while (this.bos.size() < i2) {
            this.bos.write(i);
        }
        return this;
    }

    public C0752kb u16str(int i) {
        int i2 = i & 65535;
        this.bos.write((byte) (i2 >>> 8));
        this.bos.write((byte) i2);
        return this;
    }

    public C0752kb u32str(int i) {
        this.bos.write((byte) (i >>> 24));
        this.bos.write((byte) (i >>> 16));
        this.bos.write((byte) (i >>> 8));
        this.bos.write((byte) i);
        return this;
    }

    public C0752kb u64str(long j) {
        u32str((int) (j >>> 32));
        u32str((int) j);
        return this;
    }

    public C0752kb bytes(byte[] bArr) throws IOException {
        try {
            this.bos.write(bArr);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public C0752kb bytes(byte[] bArr, int i, int i2) {
        try {
            this.bos.write(bArr, i, i2);
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public C0752kb bytes(InterfaceC1394wy[] interfaceC1394wyArr) throws IOException {
        try {
            for (InterfaceC1394wy interfaceC1394wy : interfaceC1394wyArr) {
                this.bos.write(interfaceC1394wy.getEncoded());
            }
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public C0752kb bytes(byte[][] bArr) throws IOException {
        try {
            for (byte[] bArr2 : bArr) {
                this.bos.write(bArr2);
            }
            return this;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public C0752kb bytes(byte[][] bArr, int i, int i2) throws IOException {
        while (i != i2) {
            try {
                this.bos.write(bArr[i]);
                i++;
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        return this;
    }
}
