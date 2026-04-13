package android.sun.security.util;

import android.support.v4.view.ViewCompat;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.bouncycastle.i18n.LocalizedMessage;

/* loaded from: classes.dex */
public class DerOutputStream extends ByteArrayOutputStream implements DerEncoder {
    private static ByteArrayLexOrder lexOrder = new ByteArrayLexOrder();
    private static ByteArrayTagOrder tagOrder = new ByteArrayTagOrder();

    public DerOutputStream() {
    }

    public DerOutputStream(int i2) {
        super(i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0056 A[LOOP:1: B:13:0x0054->B:14:0x0056, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void putIntegerContents(int i2) {
        int i3;
        byte[] bArr = {r8, (byte) ((16711680 & i2) >>> 16), (byte) ((65280 & i2) >>> 8), (byte) (i2 & 255)};
        byte b = (byte) ((i2 & ViewCompat.MEASURED_STATE_MASK) >>> 24);
        int i4 = 0;
        if (b != -1) {
            if (b == 0) {
                i3 = 0;
                while (i4 < 3 && bArr[i4] == 0) {
                    i4++;
                    if ((bArr[i4] & DerValue.TAG_CONTEXT) != 0) {
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            putLength(4 - i4);
            while (i4 < 4) {
                write(bArr[i4]);
                i4++;
            }
        }
        i3 = 0;
        while (i4 < 3 && bArr[i4] == -1) {
            i4++;
            if ((bArr[i4] & DerValue.TAG_CONTEXT) != 128) {
                break;
            } else {
                i3++;
            }
        }
        i4 = i3;
        putLength(4 - i4);
        while (i4 < 4) {
        }
    }

    private void putTime(Date date, byte b) {
        String str;
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        if (b == 23) {
            str = "yyMMddHHmmss'Z'";
        } else {
            b = DerValue.tag_GeneralizedTime;
            str = "yyyyMMddHHmmss'Z'";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(str, Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        byte[] bytes = simpleDateFormat.format(date).getBytes(LocalizedMessage.DEFAULT_ENCODING);
        write(b);
        putLength(bytes.length);
        write(bytes);
    }

    private void writeString(String str, byte b, String str2) {
        byte[] bytes = str.getBytes(str2);
        write(b);
        putLength(bytes.length);
        write(bytes);
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        outputStream.write(toByteArray());
    }

    public void putBMPString(String str) {
        writeString(str, DerValue.tag_BMPString, "UnicodeBigUnmarked");
    }

    public void putBitString(byte[] bArr) {
        write(3);
        putLength(bArr.length + 1);
        write(0);
        write(bArr);
    }

    public void putBoolean(boolean z2) {
        write(1);
        putLength(1);
        write(z2 ? 255 : 0);
    }

    public void putDerValue(DerValue derValue) {
        derValue.encode(this);
    }

    public void putEnumerated(int i2) {
        write(10);
        putIntegerContents(i2);
    }

    public void putGeneralString(String str) {
        writeString(str, DerValue.tag_GeneralString, "ASCII");
    }

    public void putGeneralizedTime(Date date) {
        putTime(date, DerValue.tag_GeneralizedTime);
    }

    public void putIA5String(String str) {
        writeString(str, DerValue.tag_IA5String, "ASCII");
    }

    public void putInteger(int i2) {
        write(2);
        putIntegerContents(i2);
    }

    public void putLength(int i2) {
        byte b;
        byte b2;
        byte b3;
        if (i2 >= 128) {
            if (i2 < 256) {
                b3 = -127;
            } else {
                if (i2 < 65536) {
                    b2 = -126;
                } else {
                    if (i2 < 16777216) {
                        b = -125;
                    } else {
                        write(-124);
                        b = (byte) (i2 >> 24);
                    }
                    write(b);
                    b2 = (byte) (i2 >> 16);
                }
                write(b2);
                b3 = (byte) (i2 >> 8);
            }
            write(b3);
        }
        write((byte) i2);
    }

    public void putNull() {
        write(5);
        putLength(0);
    }

    public void putOID(ObjectIdentifier objectIdentifier) {
        objectIdentifier.encode(this);
    }

    public void putOctetString(byte[] bArr) {
        write((byte) 4, bArr);
    }

    public void putOrderedSet(byte b, DerEncoder[] derEncoderArr) {
        putOrderedSet(b, derEncoderArr, tagOrder);
    }

    public void putOrderedSetOf(byte b, DerEncoder[] derEncoderArr) {
        putOrderedSet(b, derEncoderArr, lexOrder);
    }

    public void putPrintableString(String str) {
        writeString(str, DerValue.tag_PrintableString, "ASCII");
    }

    public void putSequence(DerValue[] derValueArr) {
        DerOutputStream derOutputStream = new DerOutputStream();
        for (DerValue derValue : derValueArr) {
            derValue.encode(derOutputStream);
        }
        write((byte) 48, derOutputStream);
    }

    public void putSet(DerValue[] derValueArr) {
        DerOutputStream derOutputStream = new DerOutputStream();
        for (DerValue derValue : derValueArr) {
            derValue.encode(derOutputStream);
        }
        write((byte) 49, derOutputStream);
    }

    public void putT61String(String str) {
        writeString(str, DerValue.tag_T61String, LocalizedMessage.DEFAULT_ENCODING);
    }

    public void putTag(byte b, boolean z2, byte b2) {
        byte b3 = (byte) (b | b2);
        if (z2) {
            b3 = (byte) (b3 | 32);
        }
        write(b3);
    }

    public void putTruncatedUnalignedBitString(BitArray bitArray) {
        putUnalignedBitString(bitArray.truncate());
    }

    public void putUTCTime(Date date) {
        putTime(date, DerValue.tag_UtcTime);
    }

    public void putUTF8String(String str) {
        writeString(str, DerValue.tag_UTF8String, "UTF8");
    }

    public void putUnalignedBitString(BitArray bitArray) {
        byte[] byteArray = bitArray.toByteArray();
        write(3);
        putLength(byteArray.length + 1);
        write((byteArray.length * 8) - bitArray.length());
        write(byteArray);
    }

    public void write(byte b, DerOutputStream derOutputStream) {
        write(b);
        putLength(((ByteArrayOutputStream) derOutputStream).count);
        write(((ByteArrayOutputStream) derOutputStream).buf, 0, ((ByteArrayOutputStream) derOutputStream).count);
    }

    public void writeImplicit(byte b, DerOutputStream derOutputStream) {
        write(b);
        write(((ByteArrayOutputStream) derOutputStream).buf, 1, ((ByteArrayOutputStream) derOutputStream).count - 1);
    }

    private void putOrderedSet(byte b, DerEncoder[] derEncoderArr, Comparator<byte[]> comparator) {
        int length = derEncoderArr.length;
        DerOutputStream[] derOutputStreamArr = new DerOutputStream[length];
        for (int i2 = 0; i2 < derEncoderArr.length; i2++) {
            DerOutputStream derOutputStream = new DerOutputStream();
            derOutputStreamArr[i2] = derOutputStream;
            derEncoderArr[i2].derEncode(derOutputStream);
        }
        byte[][] bArr = new byte[length][];
        for (int i3 = 0; i3 < length; i3++) {
            bArr[i3] = derOutputStreamArr[i3].toByteArray();
        }
        Arrays.sort(bArr, comparator);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (int i4 = 0; i4 < length; i4++) {
            derOutputStream2.write(bArr[i4]);
        }
        write(b, derOutputStream2);
    }

    public void putInteger(Integer num) {
        putInteger(num.intValue());
    }

    public void write(byte b, byte[] bArr) {
        write(b);
        putLength(bArr.length);
        write(bArr, 0, bArr.length);
    }

    public void putInteger(BigInteger bigInteger) {
        write(2);
        byte[] byteArray = bigInteger.toByteArray();
        putLength(byteArray.length);
        write(byteArray, 0, byteArray.length);
    }
}
