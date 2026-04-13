package android.sun.security.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Vector;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.i18n.LocalizedMessage;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DerInputStream {
    DerInputBuffer buffer;
    public byte tag;

    public DerInputStream(DerInputBuffer derInputBuffer) {
        this.buffer = derInputBuffer;
        derInputBuffer.mark(Integer.MAX_VALUE);
    }

    private void init(byte[] bArr, int i2, int i3) {
        if (i2 + 2 > bArr.length || i2 + i3 > bArr.length) {
            throw new IOException("Encoding bytes too short");
        }
        if (DerIndefLenConverter.isIndefinite(bArr[i2 + 1])) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            this.buffer = new DerInputBuffer(new DerIndefLenConverter().convert(bArr2));
        } else {
            this.buffer = new DerInputBuffer(bArr, i2, i3);
        }
        this.buffer.mark(Integer.MAX_VALUE);
    }

    private String readString(byte b, String str, String str2) {
        if (this.buffer.read() != b) {
            throw new IOException(AbstractC0000a.m16l("DER input not a ", str, " string"));
        }
        int length = getLength(this.buffer);
        byte[] bArr = new byte[length];
        if (length == 0 || this.buffer.read(bArr) == length) {
            return new String(bArr, str2);
        }
        throw new IOException(AbstractC0000a.m16l("short read of DER ", str, " string"));
    }

    public int available() {
        return this.buffer.available();
    }

    public String getBMPString() {
        return readString(DerValue.tag_BMPString, "BMP", "UnicodeBigUnmarked");
    }

    public BigInteger getBigInteger() {
        if (this.buffer.read() != 2) {
            throw new IOException("DER input, Integer tag error");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getBigInteger(getLength(derInputBuffer), false);
    }

    public byte[] getBitString() {
        if (this.buffer.read() != 3) {
            throw new IOException("DER input not an bit string");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getBitString(getLength(derInputBuffer));
    }

    public int getByte() {
        return this.buffer.read() & 255;
    }

    public void getBytes(byte[] bArr) {
        if (bArr.length != 0 && this.buffer.read(bArr) != bArr.length) {
            throw new IOException("short read of DER octet string");
        }
    }

    public DerValue getDerValue() {
        return new DerValue(this.buffer);
    }

    public int getEnumerated() {
        if (this.buffer.read() != 10) {
            throw new IOException("DER input, Enumerated tag error");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getInteger(getLength(derInputBuffer));
    }

    public String getGeneralString() {
        return readString(DerValue.tag_GeneralString, "General", "ASCII");
    }

    public Date getGeneralizedTime() {
        if (this.buffer.read() != 24) {
            throw new IOException("DER input, GeneralizedTime tag invalid ");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getGeneralizedTime(getLength(derInputBuffer));
    }

    public String getIA5String() {
        return readString(DerValue.tag_IA5String, "IA5", "ASCII");
    }

    public int getInteger() {
        if (this.buffer.read() != 2) {
            throw new IOException("DER input, Integer tag error");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getInteger(getLength(derInputBuffer));
    }

    public int getLength() {
        return getLength(this.buffer);
    }

    public void getNull() {
        if (this.buffer.read() != 5 || this.buffer.read() != 0) {
            throw new IOException("getNull, bad data");
        }
    }

    public ObjectIdentifier getOID() {
        return new ObjectIdentifier(this);
    }

    public byte[] getOctetString() {
        if (this.buffer.read() != 4) {
            throw new IOException("DER input not an octet string");
        }
        int length = getLength(this.buffer);
        byte[] bArr = new byte[length];
        if (length == 0 || this.buffer.read(bArr) == length) {
            return bArr;
        }
        throw new IOException("short read of DER octet string");
    }

    public BigInteger getPositiveBigInteger() {
        if (this.buffer.read() != 2) {
            throw new IOException("DER input, Integer tag error");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getBigInteger(getLength(derInputBuffer), true);
    }

    public String getPrintableString() {
        return readString(DerValue.tag_PrintableString, "Printable", "ASCII");
    }

    public DerValue[] getSequence(int i2) {
        byte read = (byte) this.buffer.read();
        this.tag = read;
        if (read == 48) {
            return readVector(i2);
        }
        throw new IOException("Sequence tag error");
    }

    public DerValue[] getSet(int i2) {
        byte read = (byte) this.buffer.read();
        this.tag = read;
        if (read == 49) {
            return readVector(i2);
        }
        throw new IOException("Set tag error");
    }

    public String getT61String() {
        return readString(DerValue.tag_T61String, "T61", LocalizedMessage.DEFAULT_ENCODING);
    }

    public Date getUTCTime() {
        if (this.buffer.read() != 23) {
            throw new IOException("DER input, UTCtime tag invalid ");
        }
        DerInputBuffer derInputBuffer = this.buffer;
        return derInputBuffer.getUTCTime(getLength(derInputBuffer));
    }

    public String getUTF8String() {
        return readString(DerValue.tag_UTF8String, "UTF-8", "UTF8");
    }

    public BitArray getUnalignedBitString() {
        if (this.buffer.read() != 3) {
            throw new IOException("DER input not a bit string");
        }
        int length = getLength(this.buffer) - 1;
        int read = (length * 8) - this.buffer.read();
        byte[] bArr = new byte[length];
        if (length == 0 || this.buffer.read(bArr) == length) {
            return new BitArray(read, bArr);
        }
        throw new IOException("short read of DER bit string");
    }

    public void mark(int i2) {
        this.buffer.mark(i2);
    }

    public int peekByte() {
        return this.buffer.peek();
    }

    public DerValue[] readVector(int i2) {
        byte read = (byte) this.buffer.read();
        int length = getLength(read & 255, this.buffer);
        if (length == -1) {
            int available = this.buffer.available();
            byte[] bArr = new byte[available + 2];
            bArr[0] = this.tag;
            bArr[1] = read;
            DataInputStream dataInputStream = new DataInputStream(this.buffer);
            dataInputStream.readFully(bArr, 2, available);
            dataInputStream.close();
            DerInputBuffer derInputBuffer = new DerInputBuffer(new DerIndefLenConverter().convert(bArr));
            this.buffer = derInputBuffer;
            if (this.tag != derInputBuffer.read()) {
                throw new IOException("Indefinite length encoding not supported");
            }
            length = getLength(this.buffer);
        }
        if (length == 0) {
            return new DerValue[0];
        }
        DerInputStream subStream = this.buffer.available() == length ? this : subStream(length, true);
        Vector vector = new Vector(i2);
        do {
            vector.addElement(new DerValue(subStream.buffer));
        } while (subStream.available() > 0);
        if (subStream.available() != 0) {
            throw new IOException("extra data at end of vector");
        }
        int size = vector.size();
        DerValue[] derValueArr = new DerValue[size];
        for (int i3 = 0; i3 < size; i3++) {
            derValueArr[i3] = (DerValue) vector.elementAt(i3);
        }
        return derValueArr;
    }

    public void reset() {
        this.buffer.reset();
    }

    public DerInputStream subStream(int i2, boolean z2) {
        DerInputBuffer dup = this.buffer.dup();
        dup.truncate(i2);
        if (z2) {
            this.buffer.skip(i2);
        }
        return new DerInputStream(dup);
    }

    public byte[] toByteArray() {
        return this.buffer.toByteArray();
    }

    public DerInputStream(byte[] bArr) {
        init(bArr, 0, bArr.length);
    }

    public static int getLength(int i2, InputStream inputStream) {
        if ((i2 & 128) == 0) {
            return i2;
        }
        int i3 = i2 & CertificateBody.profileType;
        if (i3 == 0) {
            return -1;
        }
        if (i3 < 0 || i3 > 4) {
            StringBuilder m21q = AbstractC0000a.m21q("DerInputStream.getLength(): lengthTag=", i3, ", ");
            m21q.append(i3 < 0 ? "incorrect DER encoding." : "too big.");
            throw new IOException(m21q.toString());
        }
        int i4 = 0;
        while (i3 > 0) {
            i4 = (i4 << 8) + (inputStream.read() & 255);
            i3--;
        }
        return i4;
    }

    public DerValue[] getSet(int i2, boolean z2) {
        byte read = (byte) this.buffer.read();
        this.tag = read;
        if (z2 || read == 49) {
            return readVector(i2);
        }
        throw new IOException("Set tag error");
    }

    public DerInputStream(byte[] bArr, int i2, int i3) {
        init(bArr, i2, i3);
    }

    public static int getLength(InputStream inputStream) {
        return getLength(inputStream.read(), inputStream);
    }
}
