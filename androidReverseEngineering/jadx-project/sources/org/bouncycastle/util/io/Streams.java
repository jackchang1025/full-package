package org.bouncycastle.util.io;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public final class Streams {
    private static int BUFFER_SIZE = 4096;

    public static void drain(InputStream inputStream) {
        int i2 = BUFFER_SIZE;
        while (inputStream.read(new byte[i2], 0, i2) >= 0) {
        }
    }

    public static void pipeAll(InputStream inputStream, OutputStream outputStream) {
        pipeAll(inputStream, outputStream, BUFFER_SIZE);
    }

    public static long pipeAllLimited(InputStream inputStream, long j2, OutputStream outputStream) {
        int i2 = BUFFER_SIZE;
        byte[] bArr = new byte[i2];
        long j3 = 0;
        while (true) {
            int read = inputStream.read(bArr, 0, i2);
            if (read < 0) {
                return j3;
            }
            long j4 = read;
            if (j2 - j3 < j4) {
                throw new StreamOverflowException("Data Overflow");
            }
            j3 += j4;
            outputStream.write(bArr, 0, read);
        }
    }

    public static byte[] readAll(InputStream inputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAll(inputStream, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] readAllLimited(InputStream inputStream, int i2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        pipeAllLimited(inputStream, i2, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static int readFully(InputStream inputStream, byte[] bArr) {
        return readFully(inputStream, bArr, 0, bArr.length);
    }

    public static void writeBufTo(ByteArrayOutputStream byteArrayOutputStream, OutputStream outputStream) {
        byteArrayOutputStream.writeTo(outputStream);
    }

    public static void pipeAll(InputStream inputStream, OutputStream outputStream, int i2) {
        byte[] bArr = new byte[i2];
        while (true) {
            int read = inputStream.read(bArr, 0, i2);
            if (read < 0) {
                return;
            } else {
                outputStream.write(bArr, 0, read);
            }
        }
    }

    public static int readFully(InputStream inputStream, byte[] bArr, int i2, int i3) {
        int i4 = 0;
        while (i4 < i3) {
            int read = inputStream.read(bArr, i2 + i4, i3 - i4);
            if (read < 0) {
                break;
            }
            i4 += read;
        }
        return i4;
    }
}
