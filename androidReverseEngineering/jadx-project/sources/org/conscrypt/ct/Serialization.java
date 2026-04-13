package org.conscrypt.ct;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class Serialization {
    private static final int DER_LENGTH_LONG_FORM_FLAG = 128;
    private static final int DER_TAG_MASK = 63;
    private static final int DER_TAG_OCTET_STRING = 4;

    private Serialization() {
    }

    public static byte readByte(InputStream inputStream) {
        try {
            int read = inputStream.read();
            if (read != -1) {
                return (byte) read;
            }
            throw new SerializationException("Premature end of input, could not read byte.");
        } catch (IOException e2) {
            throw new SerializationException(e2);
        }
    }

    public static byte[] readDEROctetString(InputStream inputStream) {
        int readByte = readByte(inputStream) & 63;
        if (readByte != 4) {
            throw new SerializationException(AbstractC0000a.m11g("Wrong DER tag, expected OCTET STRING, got ", readByte));
        }
        int readNumber = readNumber(inputStream, 1);
        if ((readNumber & 128) != 0) {
            readNumber = readNumber(inputStream, readNumber & (-129));
        }
        return readFixedBytes(inputStream, readNumber);
    }

    public static byte[] readFixedBytes(InputStream inputStream, int i2) {
        try {
            if (i2 < 0) {
                throw new SerializationException("Negative length: " + i2);
            }
            byte[] bArr = new byte[i2];
            int read = inputStream.read(bArr);
            if (read >= i2) {
                return bArr;
            }
            throw new SerializationException("Premature end of input, expected " + i2 + " bytes, only read " + read);
        } catch (IOException e2) {
            throw new SerializationException(e2);
        }
    }

    public static byte[][] readList(InputStream inputStream, int i2, int i3) {
        ArrayList arrayList = new ArrayList();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(readVariableBytes(inputStream, i2));
        while (byteArrayInputStream.available() > 0) {
            try {
                arrayList.add(readVariableBytes(byteArrayInputStream, i3));
            } catch (IOException e2) {
                throw new SerializationException(e2);
            }
        }
        return (byte[][]) arrayList.toArray(new byte[arrayList.size()][]);
    }

    public static long readLong(InputStream inputStream, int i2) {
        if (i2 > 8 || i2 < 0) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("Invalid width: ", i2));
        }
        long j2 = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j2 = (j2 << 8) | (readByte(inputStream) & 255);
        }
        return j2;
    }

    public static int readNumber(InputStream inputStream, int i2) {
        if (i2 > 4 || i2 < 0) {
            throw new SerializationException(AbstractC0000a.m11g("Invalid width: ", i2));
        }
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            i3 = (i3 << 8) | (readByte(inputStream) & 255);
        }
        return i3;
    }

    public static byte[] readVariableBytes(InputStream inputStream, int i2) {
        return readFixedBytes(inputStream, readNumber(inputStream, i2));
    }

    public static void writeFixedBytes(OutputStream outputStream, byte[] bArr) {
        try {
            outputStream.write(bArr);
        } catch (IOException e2) {
            throw new SerializationException(e2);
        }
    }

    public static void writeNumber(OutputStream outputStream, long j2, int i2) {
        if (i2 < 0) {
            throw new SerializationException(AbstractC0000a.m11g("Negative width: ", i2));
        }
        if (i2 < 8 && j2 >= (1 << (i2 * 8))) {
            throw new SerializationException("Number too large, " + j2 + " does not fit in " + i2 + " bytes");
        }
        while (i2 > 0) {
            if ((i2 - 1) * 8 < 64) {
                try {
                    outputStream.write((byte) ((j2 >> ((int) r0)) & 255));
                } catch (IOException e2) {
                    throw new SerializationException(e2);
                }
            } else {
                outputStream.write(0);
            }
            i2--;
        }
    }

    public static void writeVariableBytes(OutputStream outputStream, byte[] bArr, int i2) {
        writeNumber(outputStream, bArr.length, i2);
        writeFixedBytes(outputStream, bArr);
    }

    public static byte[][] readList(byte[] bArr, int i2, int i3) {
        return readList(new ByteArrayInputStream(bArr), i2, i3);
    }

    public static byte[] readDEROctetString(byte[] bArr) {
        return readDEROctetString(new ByteArrayInputStream(bArr));
    }
}
