package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;
import org.bouncycastle.asn1.eac.CertificateBody;

/* loaded from: classes.dex */
public class ASN1OutputStream {
    private OutputStream os;

    public ASN1OutputStream(OutputStream outputStream) {
        this.os = outputStream;
    }

    public static ASN1OutputStream create(OutputStream outputStream) {
        return new ASN1OutputStream(outputStream);
    }

    public static int getLengthOfDL(int i2) {
        if (i2 < 128) {
            return 1;
        }
        int i3 = 2;
        while (true) {
            i2 >>>= 8;
            if (i2 == 0) {
                return i3;
            }
            i3++;
        }
    }

    public static int getLengthOfEncodingDL(boolean z2, int i2) {
        return getLengthOfDL(i2) + (z2 ? 1 : 0) + i2;
    }

    public static int getLengthOfIdentifier(int i2) {
        if (i2 < 31) {
            return 1;
        }
        int i3 = 2;
        while (true) {
            i2 >>>= 7;
            if (i2 == 0) {
                return i3;
            }
            i3++;
        }
    }

    public void close() {
        this.os.close();
    }

    public void flush() {
        this.os.flush();
    }

    public void flushInternal() {
    }

    public DEROutputStream getDERSubStream() {
        return new DEROutputStream(this.os);
    }

    public DLOutputStream getDLSubStream() {
        return new DLOutputStream(this.os);
    }

    public final void write(int i2) {
        this.os.write(i2);
    }

    public final void writeDL(int i2) {
        if (i2 < 128) {
            write(i2);
            return;
        }
        int i3 = 5;
        byte[] bArr = new byte[5];
        do {
            i3--;
            bArr[i3] = (byte) i2;
            i2 >>>= 8;
        } while (i2 != 0);
        int i4 = 5 - i3;
        int i5 = i3 - 1;
        bArr[i5] = (byte) (i4 | 128);
        write(bArr, i5, i4 + 1);
    }

    public void writeElements(ASN1Encodable[] aSN1EncodableArr) {
        for (ASN1Encodable aSN1Encodable : aSN1EncodableArr) {
            aSN1Encodable.toASN1Primitive().encode(this, true);
        }
    }

    public final void writeEncodingDL(boolean z2, int i2, byte b) {
        writeIdentifier(z2, i2);
        writeDL(1);
        write(b);
    }

    public final void writeEncodingIL(boolean z2, int i2, ASN1Encodable[] aSN1EncodableArr) {
        writeIdentifier(z2, i2);
        write(128);
        writeElements(aSN1EncodableArr);
        write(0);
        write(0);
    }

    public final void writeIdentifier(boolean z2, int i2) {
        if (z2) {
            write(i2);
        }
    }

    public final void writeObject(ASN1Encodable aSN1Encodable) {
        if (aSN1Encodable == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(aSN1Encodable.toASN1Primitive(), true);
        flushInternal();
    }

    public void writePrimitive(ASN1Primitive aSN1Primitive, boolean z2) {
        aSN1Primitive.encode(this, z2);
    }

    public void writePrimitives(ASN1Primitive[] aSN1PrimitiveArr) {
        for (ASN1Primitive aSN1Primitive : aSN1PrimitiveArr) {
            aSN1Primitive.encode(this, true);
        }
    }

    public static ASN1OutputStream create(OutputStream outputStream, String str) {
        return str.equals(ASN1Encoding.DER) ? new DEROutputStream(outputStream) : str.equals(ASN1Encoding.DL) ? new DLOutputStream(outputStream) : new ASN1OutputStream(outputStream);
    }

    public final void write(byte[] bArr, int i2, int i3) {
        this.os.write(bArr, i2, i3);
    }

    public final void writeEncodingDL(boolean z2, int i2, byte b, byte[] bArr, int i3, int i4) {
        writeIdentifier(z2, i2);
        writeDL(i4 + 1);
        write(b);
        write(bArr, i3, i4);
    }

    public final void writeIdentifier(boolean z2, int i2, int i3) {
        if (z2) {
            if (i3 < 31) {
                write(i2 | i3);
                return;
            }
            byte[] bArr = new byte[6];
            int i4 = 5;
            bArr[5] = (byte) (i3 & CertificateBody.profileType);
            while (i3 > 127) {
                i3 >>>= 7;
                i4--;
                bArr[i4] = (byte) ((i3 & CertificateBody.profileType) | 128);
            }
            int i5 = i4 - 1;
            bArr[i5] = (byte) (31 | i2);
            write(bArr, i5, 6 - i5);
        }
    }

    public final void writeObject(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive == null) {
            throw new IOException("null object detected");
        }
        writePrimitive(aSN1Primitive, true);
        flushInternal();
    }

    public final void writeEncodingDL(boolean z2, int i2, int i3, byte[] bArr) {
        writeIdentifier(z2, i2, i3);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    public final void writeEncodingDL(boolean z2, int i2, byte[] bArr) {
        writeIdentifier(z2, i2);
        writeDL(bArr.length);
        write(bArr, 0, bArr.length);
    }

    public final void writeEncodingDL(boolean z2, int i2, byte[] bArr, int i3, int i4) {
        writeIdentifier(z2, i2);
        writeDL(i4);
        write(bArr, i3, i4);
    }

    public final void writeEncodingDL(boolean z2, int i2, byte[] bArr, int i3, int i4, byte b) {
        writeIdentifier(z2, i2);
        writeDL(i4 + 1);
        write(bArr, i3, i4);
        write(b);
    }
}
