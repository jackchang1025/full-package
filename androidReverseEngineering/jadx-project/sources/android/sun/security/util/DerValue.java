package android.sun.security.util;

import android.sun.misc.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: classes.dex */
public class DerValue {
    public static final byte TAG_APPLICATION = 64;
    public static final byte TAG_CONTEXT = Byte.MIN_VALUE;
    public static final byte TAG_PRIVATE = -64;
    public static final byte TAG_UNIVERSAL = 0;
    public static final byte tag_BMPString = 30;
    public static final byte tag_BitString = 3;
    public static final byte tag_Boolean = 1;
    public static final byte tag_Enumerated = 10;
    public static final byte tag_GeneralString = 27;
    public static final byte tag_GeneralizedTime = 24;
    public static final byte tag_IA5String = 22;
    public static final byte tag_Integer = 2;
    public static final byte tag_Null = 5;
    public static final byte tag_ObjectId = 6;
    public static final byte tag_OctetString = 4;
    public static final byte tag_PrintableString = 19;
    public static final byte tag_Sequence = 48;
    public static final byte tag_SequenceOf = 48;
    public static final byte tag_Set = 49;
    public static final byte tag_SetOf = 49;
    public static final byte tag_T61String = 20;
    public static final byte tag_UTF8String = 12;
    public static final byte tag_UniversalString = 28;
    public static final byte tag_UtcTime = 23;
    protected DerInputBuffer buffer;
    public final DerInputStream data;
    private int length;
    public byte tag;

    public DerValue(byte b, String str) {
        this.data = init(b, str);
    }

    private byte[] append(byte[] bArr, byte[] bArr2) {
        if (bArr == null) {
            return bArr2;
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static byte createTag(byte b, boolean z2, byte b2) {
        byte b3 = (byte) (b | b2);
        return z2 ? (byte) (b3 | 32) : b3;
    }

    private static boolean doEquals(DerValue derValue, DerValue derValue2) {
        boolean equals;
        synchronized (derValue.data) {
            synchronized (derValue2.data) {
                derValue.data.reset();
                derValue2.data.reset();
                equals = derValue.buffer.equals(derValue2.buffer);
            }
        }
        return equals;
    }

    private DerInputStream init(byte b, String str) {
        String str2;
        this.tag = b;
        if (b != 12) {
            if (b != 22 && b != 27) {
                if (b == 30) {
                    str2 = "UnicodeBigUnmarked";
                } else if (b != 19) {
                    if (b != 20) {
                        throw new IllegalArgumentException("Unsupported DER string type");
                    }
                    str2 = LocalizedMessage.DEFAULT_ENCODING;
                }
            }
            str2 = "ASCII";
        } else {
            str2 = "UTF8";
        }
        byte[] bytes = str.getBytes(str2);
        this.length = bytes.length;
        DerInputBuffer derInputBuffer = new DerInputBuffer(bytes);
        this.buffer = derInputBuffer;
        DerInputStream derInputStream = new DerInputStream(derInputBuffer);
        derInputStream.mark(Integer.MAX_VALUE);
        return derInputStream;
    }

    public static boolean isPrintableStringChar(char c) {
        if ((c < 'a' || c > 'z') && ((c < 'A' || c > 'Z') && ((c < '0' || c > '9') && c != ' ' && c != ':' && c != '=' && c != '?'))) {
            switch (c) {
                default:
                    switch (c) {
                    }
                    return true;
                case '\'':
                case '(':
                case ')':
                    return true;
            }
        }
        return true;
    }

    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.write(this.tag);
        derOutputStream.putLength(this.length);
        int i2 = this.length;
        if (i2 > 0) {
            byte[] bArr = new byte[i2];
            synchronized (this.data) {
                this.buffer.reset();
                if (this.buffer.read(bArr) != this.length) {
                    throw new IOException("short DER value read (encode)");
                }
                derOutputStream.write(bArr);
            }
        }
    }

    public boolean equals(DerValue derValue) {
        if (this == derValue) {
            return true;
        }
        if (this.tag != derValue.tag) {
            return false;
        }
        DerInputStream derInputStream = this.data;
        if (derInputStream == derValue.data) {
            return true;
        }
        return System.identityHashCode(derInputStream) > System.identityHashCode(derValue.data) ? doEquals(this, derValue) : doEquals(derValue, this);
    }

    public String getAsString() {
        byte b = this.tag;
        if (b == 12) {
            return getUTF8String();
        }
        if (b == 19) {
            return getPrintableString();
        }
        if (b == 20) {
            return getT61String();
        }
        if (b == 22) {
            return getIA5String();
        }
        if (b == 30) {
            return getBMPString();
        }
        if (b == 27) {
            return getGeneralString();
        }
        return null;
    }

    public String getBMPString() {
        if (this.tag == 30) {
            return new String(getDataBytes(), "UnicodeBigUnmarked");
        }
        throw new IOException("DerValue.getBMPString, not BMP " + ((int) this.tag));
    }

    public BigInteger getBigInteger() {
        if (this.tag == 2) {
            return this.buffer.getBigInteger(this.data.available(), false);
        }
        throw new IOException("DerValue.getBigInteger, not an int " + ((int) this.tag));
    }

    public byte[] getBitString() {
        if (this.tag == 3) {
            return this.buffer.getBitString();
        }
        throw new IOException("DerValue.getBitString, not a bit string " + ((int) this.tag));
    }

    public boolean getBoolean() {
        if (this.tag != 1) {
            throw new IOException("DerValue.getBoolean, not a BOOLEAN " + ((int) this.tag));
        }
        if (this.length == 1) {
            return this.buffer.read() != 0;
        }
        throw new IOException("DerValue.getBoolean, invalid length " + this.length);
    }

    public final DerInputStream getData() {
        return this.data;
    }

    public byte[] getDataBytes() {
        byte[] bArr = new byte[this.length];
        synchronized (this.data) {
            this.data.reset();
            this.data.getBytes(bArr);
        }
        return bArr;
    }

    public int getEnumerated() {
        if (this.tag == 10) {
            return this.buffer.getInteger(this.data.available());
        }
        throw new IOException("DerValue.getEnumerated, incorrect tag: " + ((int) this.tag));
    }

    public String getGeneralString() {
        if (this.tag == 27) {
            return new String(getDataBytes(), "ASCII");
        }
        throw new IOException("DerValue.getGeneralString, not GeneralString " + ((int) this.tag));
    }

    public Date getGeneralizedTime() {
        if (this.tag == 24) {
            return this.buffer.getGeneralizedTime(this.data.available());
        }
        throw new IOException("DerValue.getGeneralizedTime, not a GeneralizedTime: " + ((int) this.tag));
    }

    public String getIA5String() {
        if (this.tag == 22) {
            return new String(getDataBytes(), "ASCII");
        }
        throw new IOException("DerValue.getIA5String, not IA5 " + ((int) this.tag));
    }

    public int getInteger() {
        if (this.tag == 2) {
            return this.buffer.getInteger(this.data.available());
        }
        throw new IOException("DerValue.getInteger, not an int " + ((int) this.tag));
    }

    public ObjectIdentifier getOID() {
        if (this.tag == 6) {
            return new ObjectIdentifier(this.buffer);
        }
        throw new IOException("DerValue.getOID, not an OID " + ((int) this.tag));
    }

    public byte[] getOctetString() {
        if (this.tag != 4 && !isConstructed((byte) 4)) {
            throw new IOException("DerValue.getOctetString, not an Octet String: " + ((int) this.tag));
        }
        int i2 = this.length;
        byte[] bArr = new byte[i2];
        if (i2 == 0) {
            return bArr;
        }
        if (this.buffer.read(bArr) != this.length) {
            throw new IOException("short read on DerValue buffer");
        }
        if (isConstructed()) {
            DerInputStream derInputStream = new DerInputStream(bArr);
            bArr = null;
            while (derInputStream.available() != 0) {
                bArr = append(bArr, derInputStream.getOctetString());
            }
        }
        return bArr;
    }

    public BigInteger getPositiveBigInteger() {
        if (this.tag == 2) {
            return this.buffer.getBigInteger(this.data.available(), true);
        }
        throw new IOException("DerValue.getBigInteger, not an int " + ((int) this.tag));
    }

    public String getPrintableString() {
        if (this.tag == 19) {
            return new String(getDataBytes(), "ASCII");
        }
        throw new IOException("DerValue.getPrintableString, not a string " + ((int) this.tag));
    }

    public String getT61String() {
        if (this.tag == 20) {
            return new String(getDataBytes(), LocalizedMessage.DEFAULT_ENCODING);
        }
        throw new IOException("DerValue.getT61String, not T61 " + ((int) this.tag));
    }

    public final byte getTag() {
        return this.tag;
    }

    public Date getUTCTime() {
        if (this.tag == 23) {
            return this.buffer.getUTCTime(this.data.available());
        }
        throw new IOException("DerValue.getUTCTime, not a UtcTime: " + ((int) this.tag));
    }

    public String getUTF8String() {
        if (this.tag == 12) {
            return new String(getDataBytes(), "UTF8");
        }
        throw new IOException("DerValue.getUTF8String, not UTF-8 " + ((int) this.tag));
    }

    public BitArray getUnalignedBitString() {
        if (this.tag == 3) {
            return this.buffer.getUnalignedBitString();
        }
        throw new IOException("DerValue.getBitString, not a bit string " + ((int) this.tag));
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean isApplication() {
        return (this.tag & TAG_PRIVATE) == 64;
    }

    public boolean isConstructed() {
        return (this.tag & 32) == 32;
    }

    public boolean isContextSpecific() {
        return (this.tag & TAG_PRIVATE) == 128;
    }

    public boolean isPrivate() {
        return (this.tag & TAG_PRIVATE) == 192;
    }

    public boolean isUniversal() {
        return (this.tag & TAG_PRIVATE) == 0;
    }

    public int length() {
        return this.length;
    }

    public void resetTag(byte b) {
        this.tag = b;
    }

    public byte[] toByteArray() {
        DerOutputStream derOutputStream = new DerOutputStream();
        encode(derOutputStream);
        this.data.reset();
        return derOutputStream.toByteArray();
    }

    public DerInputStream toDerInputStream() {
        byte b = this.tag;
        if (b == 48 || b == 49) {
            return new DerInputStream(this.buffer);
        }
        throw new IOException("toDerInputStream rejects tag type " + ((int) this.tag));
    }

    public String toString() {
        try {
            String asString = getAsString();
            if (asString != null) {
                return "\"" + asString + "\"";
            }
            byte b = this.tag;
            if (b == 5) {
                return "[DerValue, null]";
            }
            if (b == 6) {
                return "OID." + getOID();
            }
            return "[DerValue, tag = " + ((int) this.tag) + ", length = " + this.length + "]";
        } catch (IOException unused) {
            throw new IllegalArgumentException("misformatted DER value");
        }
    }

    public DerValue(byte b, byte[] bArr) {
        this.tag = b;
        DerInputBuffer derInputBuffer = new DerInputBuffer((byte[]) bArr.clone());
        this.buffer = derInputBuffer;
        this.length = bArr.length;
        DerInputStream derInputStream = new DerInputStream(derInputBuffer);
        this.data = derInputStream;
        derInputStream.mark(Integer.MAX_VALUE);
    }

    private DerInputStream init(boolean z2, InputStream inputStream) {
        this.tag = (byte) inputStream.read();
        byte read = (byte) inputStream.read();
        int length = DerInputStream.getLength(read & 255, inputStream);
        this.length = length;
        if (length == -1) {
            int available = inputStream.available();
            byte[] bArr = new byte[available + 2];
            bArr[0] = this.tag;
            bArr[1] = read;
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            dataInputStream.readFully(bArr, 2, available);
            dataInputStream.close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new DerIndefLenConverter().convert(bArr));
            if (this.tag != byteArrayInputStream.read()) {
                throw new IOException("Indefinite length encoding not supported");
            }
            this.length = DerInputStream.getLength(byteArrayInputStream);
            inputStream = byteArrayInputStream;
        }
        if (z2 && inputStream.available() != this.length) {
            throw new IOException("extra data given to DerValue constructor");
        }
        DerInputBuffer derInputBuffer = new DerInputBuffer(IOUtils.readFully(inputStream, this.length, true));
        this.buffer = derInputBuffer;
        return new DerInputStream(derInputBuffer);
    }

    public boolean equals(Object obj) {
        if (obj instanceof DerValue) {
            return equals((DerValue) obj);
        }
        return false;
    }

    public byte[] getBitString(boolean z2) {
        if (z2 || this.tag == 3) {
            return this.buffer.getBitString();
        }
        throw new IOException("DerValue.getBitString, not a bit string " + ((int) this.tag));
    }

    public BitArray getUnalignedBitString(boolean z2) {
        if (z2 || this.tag == 3) {
            return this.buffer.getUnalignedBitString();
        }
        throw new IOException("DerValue.getBitString, not a bit string " + ((int) this.tag));
    }

    public boolean isConstructed(byte b) {
        return isConstructed() && (this.tag & 31) == b;
    }

    public boolean isContextSpecific(byte b) {
        return isContextSpecific() && (this.tag & 31) == b;
    }

    public DerValue(DerInputBuffer derInputBuffer) {
        int i2;
        this.tag = (byte) derInputBuffer.read();
        byte read = (byte) derInputBuffer.read();
        int length = DerInputStream.getLength(read & 255, derInputBuffer);
        this.length = length;
        if (length == -1) {
            DerInputBuffer dup = derInputBuffer.dup();
            int available = dup.available();
            byte[] bArr = new byte[available + 2];
            bArr[0] = this.tag;
            bArr[1] = read;
            DataInputStream dataInputStream = new DataInputStream(dup);
            dataInputStream.readFully(bArr, 2, available);
            dataInputStream.close();
            DerInputBuffer derInputBuffer2 = new DerInputBuffer(new DerIndefLenConverter().convert(bArr));
            if (this.tag != derInputBuffer2.read()) {
                throw new IOException("Indefinite length encoding not supported");
            }
            this.length = DerInputStream.getLength(derInputBuffer2);
            DerInputBuffer dup2 = derInputBuffer2.dup();
            this.buffer = dup2;
            dup2.truncate(this.length);
            this.data = new DerInputStream(this.buffer);
            i2 = this.length + 2;
        } else {
            DerInputBuffer dup3 = derInputBuffer.dup();
            this.buffer = dup3;
            dup3.truncate(this.length);
            this.data = new DerInputStream(this.buffer);
            i2 = this.length;
        }
        derInputBuffer.skip(i2);
    }

    public DerValue(InputStream inputStream) {
        this.data = init(false, inputStream);
    }

    public DerValue(String str) {
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i2 >= str.length()) {
                z2 = true;
                break;
            } else if (!isPrintableStringChar(str.charAt(i2))) {
                break;
            } else {
                i2++;
            }
        }
        this.data = init(z2 ? tag_PrintableString : tag_UTF8String, str);
    }

    public DerValue(byte[] bArr) {
        this.data = init(true, (InputStream) new ByteArrayInputStream(bArr));
    }

    public DerValue(byte[] bArr, int i2, int i3) {
        this.data = init(true, (InputStream) new ByteArrayInputStream(bArr, i2, i3));
    }
}
