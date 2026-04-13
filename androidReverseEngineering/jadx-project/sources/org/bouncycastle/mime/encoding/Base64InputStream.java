package org.bouncycastle.mime.encoding;

import java.io.EOFException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class Base64InputStream extends InputStream {
    private static final byte[] decodingTable = new byte[128];
    InputStream in;
    int[] outBuf = new int[3];
    int bufPtr = 3;

    static {
        for (int i2 = 65; i2 <= 90; i2++) {
            decodingTable[i2] = (byte) (i2 - 65);
        }
        for (int i3 = 97; i3 <= 122; i3++) {
            decodingTable[i3] = (byte) ((i3 - 97) + 26);
        }
        for (int i4 = 48; i4 <= 57; i4++) {
            decodingTable[i4] = (byte) ((i4 - 48) + 52);
        }
        byte[] bArr = decodingTable;
        bArr[43] = 62;
        bArr[47] = 63;
    }

    public Base64InputStream(InputStream inputStream) {
        this.in = inputStream;
    }

    private int decode(int i2, int i3, int i4, int i5, int[] iArr) {
        if (i5 < 0) {
            throw new EOFException("unexpected end of file in armored stream.");
        }
        if (i4 == 61) {
            byte[] bArr = decodingTable;
            iArr[2] = (((bArr[i2] & 255) << 2) | ((bArr[i3] & 255) >> 4)) & 255;
            return 2;
        }
        if (i5 == 61) {
            byte[] bArr2 = decodingTable;
            byte b = bArr2[i2];
            byte b2 = bArr2[i3];
            byte b3 = bArr2[i4];
            iArr[1] = ((b << 2) | (b2 >> 4)) & 255;
            iArr[2] = ((b2 << 4) | (b3 >> 2)) & 255;
            return 1;
        }
        byte[] bArr3 = decodingTable;
        byte b4 = bArr3[i2];
        byte b5 = bArr3[i3];
        byte b6 = bArr3[i4];
        byte b7 = bArr3[i5];
        iArr[0] = ((b4 << 2) | (b5 >> 4)) & 255;
        iArr[1] = ((b5 << 4) | (b6 >> 2)) & 255;
        iArr[2] = ((b6 << 6) | b7) & 255;
        return 0;
    }

    private int readIgnoreSpace() {
        while (true) {
            int read = this.in.read();
            if (read != 9 && read != 32) {
                return read;
            }
        }
    }

    private int readIgnoreSpaceFirst() {
        while (true) {
            int read = this.in.read();
            if (read != 9 && read != 10 && read != 13 && read != 32) {
                return read;
            }
        }
    }

    @Override // java.io.InputStream
    public int available() {
        return 0;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.in.close();
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.bufPtr > 2) {
            int readIgnoreSpaceFirst = readIgnoreSpaceFirst();
            if (readIgnoreSpaceFirst < 0) {
                return -1;
            }
            this.bufPtr = decode(readIgnoreSpaceFirst, readIgnoreSpace(), readIgnoreSpace(), readIgnoreSpace(), this.outBuf);
        }
        int[] iArr = this.outBuf;
        int i2 = this.bufPtr;
        this.bufPtr = i2 + 1;
        return iArr[i2];
    }
}
