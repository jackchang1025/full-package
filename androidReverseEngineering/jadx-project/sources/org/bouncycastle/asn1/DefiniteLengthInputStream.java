package org.bouncycastle.asn1;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
class DefiniteLengthInputStream extends LimitedInputStream {
    private static final byte[] EMPTY_BYTES = new byte[0];
    private final int _originalLength;
    private int _remaining;

    public DefiniteLengthInputStream(InputStream inputStream, int i2, int i3) {
        super(inputStream, i3);
        if (i2 <= 0) {
            if (i2 < 0) {
                throw new IllegalArgumentException("negative lengths not allowed");
            }
            setParentEofDetect(true);
        }
        this._originalLength = i2;
        this._remaining = i2;
    }

    public int getRemaining() {
        return this._remaining;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this._remaining == 0) {
            return -1;
        }
        int read = this._in.read();
        if (read >= 0) {
            int i2 = this._remaining - 1;
            this._remaining = i2;
            if (i2 == 0) {
                setParentEofDetect(true);
            }
            return read;
        }
        throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
    }

    public void readAllIntoByteArray(byte[] bArr) {
        int i2 = this._remaining;
        if (i2 != bArr.length) {
            throw new IllegalArgumentException("buffer length not right for data");
        }
        if (i2 == 0) {
            return;
        }
        int limit = getLimit();
        int i3 = this._remaining;
        if (i3 >= limit) {
            throw new IOException("corrupted stream - out of bounds length found: " + this._remaining + " >= " + limit);
        }
        int readFully = i3 - Streams.readFully(this._in, bArr, 0, bArr.length);
        this._remaining = readFully;
        if (readFully == 0) {
            setParentEofDetect(true);
            return;
        }
        throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
    }

    public byte[] toByteArray() {
        if (this._remaining == 0) {
            return EMPTY_BYTES;
        }
        int limit = getLimit();
        int i2 = this._remaining;
        if (i2 >= limit) {
            throw new IOException("corrupted stream - out of bounds length found: " + this._remaining + " >= " + limit);
        }
        byte[] bArr = new byte[i2];
        int readFully = i2 - Streams.readFully(this._in, bArr, 0, i2);
        this._remaining = readFully;
        if (readFully == 0) {
            setParentEofDetect(true);
            return bArr;
        }
        throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        int i4 = this._remaining;
        if (i4 == 0) {
            return -1;
        }
        int read = this._in.read(bArr, i2, Math.min(i3, i4));
        if (read >= 0) {
            int i5 = this._remaining - read;
            this._remaining = i5;
            if (i5 == 0) {
                setParentEofDetect(true);
            }
            return read;
        }
        throw new EOFException("DEF length " + this._originalLength + " object truncated by " + this._remaining);
    }
}
