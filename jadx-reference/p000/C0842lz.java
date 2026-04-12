package p000;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: lz */
/* loaded from: classes2.dex */
public class C0842lz extends InputStream {
    private InputStream _currentStream;
    private boolean _first = true;
    private final C0404d6 _parser;

    public C0842lz(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    private InterfaceC0162c7 getNextParser() throws IOException {
        InterfaceC0117b0 object = this._parser.readObject();
        if (object == null) {
            return null;
        }
        if (object instanceof InterfaceC0162c7) {
            return (InterfaceC0162c7) object;
        }
        throw new IOException("unknown object encountered: " + object.getClass());
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        InterfaceC0162c7 nextParser;
        if (this._currentStream == null) {
            if (!this._first || (nextParser = getNextParser()) == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getOctetStream();
        }
        while (true) {
            int i = this._currentStream.read();
            if (i >= 0) {
                return i;
            }
            InterfaceC0162c7 nextParser2 = getNextParser();
            if (nextParser2 == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = nextParser2.getOctetStream();
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        InterfaceC0162c7 nextParser;
        int i3 = 0;
        if (this._currentStream == null) {
            if (!this._first || (nextParser = getNextParser()) == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getOctetStream();
        }
        while (true) {
            int i4 = this._currentStream.read(bArr, i + i3, i2 - i3);
            if (i4 >= 0) {
                i3 += i4;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                InterfaceC0162c7 nextParser2 = getNextParser();
                if (nextParser2 == null) {
                    this._currentStream = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this._currentStream = nextParser2.getOctetStream();
            }
        }
    }
}
