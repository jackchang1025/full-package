package p000;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: c8 */
/* loaded from: classes2.dex */
public class C0163c8 {

    /* renamed from: os */
    private OutputStream f46075os;

    public C0163c8(OutputStream outputStream) {
        this.f46075os = outputStream;
    }

    public static C0163c8 create(OutputStream outputStream) {
        return new C0163c8(outputStream);
    }

    public static int getLengthOfDL(int i) {
        if (i < 128) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 8;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public static int getLengthOfEncodingDL(boolean z, int i) {
        return getLengthOfDL(i) + (z ? 1 : 0) + i;
    }

    public static int getLengthOfIdentifier(int i) {
        if (i < 31) {
            return 1;
        }
        int i2 = 2;
        while (true) {
            i >>>= 7;
            if (i == 0) {
                return i2;
            }
            i2++;
        }
    }

    public void close() throws IOException {
        this.f46075os.close();
    }

    public void flush() throws IOException {
        this.f46075os.flush();
    }

    public C1062pa getDERSubStream() {
        return new C1062pa(this.f46075os);
    }

    public C1081pt getDLSubStream() {
        return new C1081pt(this.f46075os);
    }

    public final void write(int i) throws IOException {
        this.f46075os.write(i);
    }

    public final void writeDL(int i) throws IOException {
        if (i < 128) {
            write(i);
            return;
        }
        int i2 = 5;
        byte[] bArr = new byte[5];
        while (true) {
            int i3 = i2 - 1;
            bArr[i3] = (byte) i;
            i >>>= 8;
            if (i == 0) {
                int i4 = i2 - 2;
                bArr[i4] = (byte) ((5 - i3) | 128);
                write(bArr, i4, 6 - i3);
                return;
            }
            i2 = i3;
        }
    }

    public void writeElements(InterfaceC0117b0[] interfaceC0117b0Arr) throws IOException {
        for (InterfaceC0117b0 interfaceC0117b0 : interfaceC0117b0Arr) {
            interfaceC0117b0.toASN1Primitive().encode(this, true);
        }
    }

    public final void writeEncodingDL(boolean z, int i, byte b) throws IOException {
        writeIdentifier(z, i);
        writeDL(1);
        write(b);
    }

    public final void writeEncodingIL(boolean z, int i, InterfaceC0117b0[] interfaceC0117b0Arr) throws IOException {
        writeIdentifier(z, i);
        write(128);
        writeElements(interfaceC0117b0Arr);
        write(0);
        write(0);
    }

    public final void writeIdentifier(boolean z, int i) throws IOException {
        if (z) {
            write(i);
        }
    }

    public final void writeObject(InterfaceC0117b0 interfaceC0117b0) throws IOException {
        if (interfaceC0117b0 == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(interfaceC0117b0.toASN1Primitive(), true);
        flushInternal();
    }

    public void writePrimitive(AbstractC0164c9 abstractC0164c9, boolean z) throws IOException {
        abstractC0164c9.encode(this, z);
    }

    public void writePrimitives(AbstractC0164c9[] abstractC0164c9Arr) throws IOException {
        for (AbstractC0164c9 abstractC0164c9 : abstractC0164c9Arr) {
            abstractC0164c9.encode(this, true);
        }
    }

    public static C0163c8 create(OutputStream outputStream, String str) {
        return str.equals("DER") ? new C1062pa(outputStream) : str.equals("DL") ? new C1081pt(outputStream) : new C0163c8(outputStream);
    }

    public final void write(byte[] bArr, int i, int i2) throws IOException {
        this.f46075os.write(bArr, i, i2);
    }

    public final void writeEncodingDL(boolean z, int i, byte b, byte[] bArr, int i2, int i3) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3 + 1);
        write(b);
        write(bArr, i2, i3);
    }

    public final void writeIdentifier(boolean z, int i, int i2) throws IOException {
        if (z) {
            if (i2 < 31) {
                write(i | i2);
                return;
            }
            byte[] bArr = new byte[6];
            int i3 = 5;
            bArr[5] = (byte) (i2 & 127);
            while (i2 > 127) {
                i2 >>>= 7;
                i3--;
                bArr[i3] = (byte) ((i2 & 127) | 128);
            }
            int i4 = i3 - 1;
            bArr[i4] = (byte) (31 | i);
            write(bArr, i4, 6 - i4);
        }
    }

    public final void writeObject(AbstractC0164c9 abstractC0164c9) throws IOException {
        if (abstractC0164c9 == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(abstractC0164c9, true);
        flushInternal();
    }

    public final void writeEncodingDL(boolean z, int i, int i2, byte[] bArr) throws IOException {
        writeIdentifier(z, i, i2);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    public final void writeEncodingDL(boolean z, int i, byte[] bArr) throws IOException {
        writeIdentifier(z, i);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    public final void writeEncodingDL(boolean z, int i, byte[] bArr, int i2, int i3) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3);
        write(bArr, i2, i3);
    }

    public final void writeEncodingDL(boolean z, int i, byte[] bArr, int i2, int i3, byte b) throws IOException {
        writeIdentifier(z, i);
        writeDL(i3 + 1);
        write(bArr, i2, i3);
        write(b);
    }

    public void flushInternal() throws IOException {
    }
}
