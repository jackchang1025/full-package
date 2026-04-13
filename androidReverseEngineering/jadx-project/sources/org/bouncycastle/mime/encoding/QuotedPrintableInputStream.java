package org.bouncycastle.mime.encoding;

import java.io.FilterInputStream;
import java.io.InputStream;

/* loaded from: classes.dex */
public class QuotedPrintableInputStream extends FilterInputStream {
    public QuotedPrintableInputStream(InputStream inputStream) {
        super(inputStream);
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() {
        int i2;
        int i3;
        int read = ((FilterInputStream) this).in.read();
        if (read == -1) {
            return -1;
        }
        while (read == 61) {
            int read2 = ((FilterInputStream) this).in.read();
            if (read2 == -1) {
                throw new IllegalStateException("Quoted '=' at end of stream");
            }
            if (read2 == 13) {
                read = ((FilterInputStream) this).in.read();
                if (read == 10) {
                }
            } else if (read2 != 10) {
                if (read2 >= 48 && read2 <= 57) {
                    i2 = read2 - 48;
                } else {
                    if (read2 < 65 || read2 > 70) {
                        throw new IllegalStateException("Expecting '0123456789ABCDEF after quote that was not immediately followed by LF or CRLF");
                    }
                    i2 = (read2 - 65) + 10;
                }
                int i4 = i2 << 4;
                int read3 = ((FilterInputStream) this).in.read();
                if (read3 >= 48 && read3 <= 57) {
                    i3 = read3 - 48;
                } else {
                    if (read3 < 65 || read3 > 70) {
                        throw new IllegalStateException("Expecting second '0123456789ABCDEF after quote that was not immediately followed by LF or CRLF");
                    }
                    i3 = (read3 - 65) + 10;
                }
                return i4 | i3;
            }
            read = ((FilterInputStream) this).in.read();
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        while (i4 != i3) {
            int read = read();
            if (read < 0) {
                break;
            }
            bArr[i4 + i2] = (byte) read;
            i4++;
        }
        if (i4 == 0) {
            return -1;
        }
        return i4;
    }
}
