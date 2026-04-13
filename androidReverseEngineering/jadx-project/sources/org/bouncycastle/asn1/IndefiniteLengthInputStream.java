package org.bouncycastle.asn1;

import java.io.EOFException;
import java.io.InputStream;

/* loaded from: classes.dex */
class IndefiniteLengthInputStream extends LimitedInputStream {
    private int _b1;
    private int _b2;
    private boolean _eofOn00;
    private boolean _eofReached;

    public IndefiniteLengthInputStream(InputStream inputStream, int i2) {
        super(inputStream, i2);
        this._eofReached = false;
        this._eofOn00 = true;
        this._b1 = inputStream.read();
        int read = inputStream.read();
        this._b2 = read;
        if (read < 0) {
            throw new EOFException();
        }
        checkForEof();
    }

    private boolean checkForEof() {
        if (!this._eofReached && this._eofOn00 && this._b1 == 0 && this._b2 == 0) {
            this._eofReached = true;
            setParentEofDetect(true);
        }
        return this._eofReached;
    }

    @Override // java.io.InputStream
    public int read() {
        if (checkForEof()) {
            return -1;
        }
        int read = this._in.read();
        if (read < 0) {
            throw new EOFException();
        }
        int i2 = this._b1;
        this._b1 = this._b2;
        this._b2 = read;
        return i2;
    }

    public void setEofOn00(boolean z2) {
        this._eofOn00 = z2;
        checkForEof();
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        if (this._eofOn00 || i3 < 3) {
            return super.read(bArr, i2, i3);
        }
        if (this._eofReached) {
            return -1;
        }
        int read = this._in.read(bArr, i2 + 2, i3 - 2);
        if (read < 0) {
            throw new EOFException();
        }
        bArr[i2] = (byte) this._b1;
        bArr[i2 + 1] = (byte) this._b2;
        this._b1 = this._in.read();
        int read2 = this._in.read();
        this._b2 = read2;
        if (read2 >= 0) {
            return read + 2;
        }
        throw new EOFException();
    }
}
