package org.bouncycastle.asn1;

import java.io.OutputStream;

/* loaded from: classes.dex */
public class BEROctetStringGenerator extends BERGenerator {

    public class BufferedBEROctetStream extends OutputStream {
        private byte[] _buf;
        private DEROutputStream _derOut;
        private int _off = 0;

        public BufferedBEROctetStream(byte[] bArr) {
            this._buf = bArr;
            this._derOut = new DEROutputStream(BEROctetStringGenerator.this._out);
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            int i2 = this._off;
            if (i2 != 0) {
                DEROctetString.encode(this._derOut, true, this._buf, 0, i2);
            }
            this._derOut.flushInternal();
            BEROctetStringGenerator.this.writeBEREnd();
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            byte[] bArr = this._buf;
            int i3 = this._off;
            int i4 = i3 + 1;
            this._off = i4;
            bArr[i3] = (byte) i2;
            if (i4 == bArr.length) {
                DEROctetString.encode(this._derOut, true, bArr, 0, bArr.length);
                this._off = 0;
            }
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            int i4;
            byte[] bArr2 = this._buf;
            int length = bArr2.length;
            int i5 = this._off;
            int i6 = length - i5;
            if (i3 < i6) {
                System.arraycopy(bArr, i2, bArr2, i5, i3);
                this._off += i3;
                return;
            }
            if (i5 > 0) {
                System.arraycopy(bArr, i2, bArr2, i5, i6);
                i4 = i6 + 0;
                DEROctetString.encode(this._derOut, true, this._buf, 0, length);
            } else {
                i4 = 0;
            }
            while (true) {
                int i7 = i3 - i4;
                if (i7 < length) {
                    System.arraycopy(bArr, i2 + i4, this._buf, 0, i7);
                    this._off = i7;
                    return;
                } else {
                    DEROctetString.encode(this._derOut, true, bArr, i2 + i4, length);
                    i4 += length;
                }
            }
        }
    }

    public BEROctetStringGenerator(OutputStream outputStream) {
        super(outputStream);
        writeBERHeader(36);
    }

    public OutputStream getOctetOutputStream() {
        return getOctetOutputStream(new byte[1000]);
    }

    public BEROctetStringGenerator(OutputStream outputStream, int i2, boolean z2) {
        super(outputStream, i2, z2);
        writeBERHeader(36);
    }

    public OutputStream getOctetOutputStream(byte[] bArr) {
        return new BufferedBEROctetStream(bArr);
    }
}
