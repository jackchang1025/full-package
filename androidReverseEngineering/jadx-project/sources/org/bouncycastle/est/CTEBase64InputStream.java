package org.bouncycastle.est;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
class CTEBase64InputStream extends InputStream {
    protected boolean end;
    protected final Long max;
    protected long read;
    protected int rp;
    protected final InputStream src;
    protected int wp;
    protected final byte[] rawBuf = new byte[1024];
    protected final byte[] data = new byte[768];
    protected final OutputStream dataOutputStream = new OutputStream() { // from class: org.bouncycastle.est.CTEBase64InputStream.1
        @Override // java.io.OutputStream
        public void write(int i2) {
            CTEBase64InputStream cTEBase64InputStream = CTEBase64InputStream.this;
            byte[] bArr = cTEBase64InputStream.data;
            int i3 = cTEBase64InputStream.wp;
            cTEBase64InputStream.wp = i3 + 1;
            bArr[i3] = (byte) i2;
        }
    };

    public CTEBase64InputStream(InputStream inputStream, Long l2) {
        this.src = inputStream;
        this.max = l2;
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.src.close();
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0054, code lost:
    
        org.bouncycastle.util.encoders.Base64.decode(r11.rawBuf, 0, r2, r11.dataOutputStream);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x005c, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0068, code lost:
    
        throw new java.io.IOException(p000a.AbstractC0000a.m14j("Decode Base64 Content-Transfer-Encoding: ", r0));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int pullFromSrc() {
        int read;
        if (this.read >= this.max.longValue()) {
            return -1;
        }
        int i2 = 0;
        do {
            read = this.src.read();
            if (read >= 33 || read == 13 || read == 10) {
                byte[] bArr = this.rawBuf;
                if (i2 >= bArr.length) {
                    throw new IOException("Content Transfer Encoding, base64 line length > 1024");
                }
                bArr[i2] = (byte) read;
                this.read++;
                i2++;
            } else if (read >= 0) {
                this.read++;
            }
            if (read <= -1 || i2 >= this.rawBuf.length || read == 10) {
                break;
            }
        } while (this.read < this.max.longValue());
        if (read == -1) {
            return -1;
        }
        return this.wp;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.rp == this.wp) {
            this.rp = 0;
            this.wp = 0;
            int pullFromSrc = pullFromSrc();
            if (pullFromSrc == -1) {
                return pullFromSrc;
            }
        }
        byte[] bArr = this.data;
        int i2 = this.rp;
        this.rp = i2 + 1;
        return bArr[i2] & 255;
    }
}
