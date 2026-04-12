package p000;

import java.io.IOException;
import java.io.InputStream;

/* renamed from: ly */
/* loaded from: classes2.dex */
public class C0841ly extends InputStream {
    private InterfaceC0008a7 _currentParser;
    private InputStream _currentStream;
    private final boolean _octetAligned;
    private final C0404d6 _parser;
    private boolean _first = true;
    private int _padBits = 0;

    public C0841ly(C0404d6 c0404d6, boolean z) {
        this._parser = c0404d6;
        this._octetAligned = z;
    }

    private InterfaceC0008a7 getNextParser() throws IOException {
        InterfaceC0117b0 object = this._parser.readObject();
        if (object == null) {
            if (!this._octetAligned || this._padBits == 0) {
                return null;
            }
            throw new IOException("expected octet-aligned bitstring, but found padBits: " + this._padBits);
        }
        if (object instanceof InterfaceC0008a7) {
            if (this._padBits == 0) {
                return (InterfaceC0008a7) object;
            }
            throw new IOException("only the last nested bitstring can have padding");
        }
        throw new IOException("unknown object encountered: " + object.getClass());
    }

    public int getPadBits() {
        return this._padBits;
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (this._currentStream == null) {
            if (!this._first) {
                return -1;
            }
            InterfaceC0008a7 nextParser = getNextParser();
            this._currentParser = nextParser;
            if (nextParser == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getBitStream();
        }
        while (true) {
            int i = this._currentStream.read();
            if (i >= 0) {
                return i;
            }
            this._padBits = this._currentParser.getPadBits();
            InterfaceC0008a7 nextParser2 = getNextParser();
            this._currentParser = nextParser2;
            if (nextParser2 == null) {
                this._currentStream = null;
                return -1;
            }
            this._currentStream = nextParser2.getBitStream();
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3 = 0;
        if (this._currentStream == null) {
            if (!this._first) {
                return -1;
            }
            InterfaceC0008a7 nextParser = getNextParser();
            this._currentParser = nextParser;
            if (nextParser == null) {
                return -1;
            }
            this._first = false;
            this._currentStream = nextParser.getBitStream();
        }
        while (true) {
            int i4 = this._currentStream.read(bArr, i + i3, i2 - i3);
            if (i4 >= 0) {
                i3 += i4;
                if (i3 == i2) {
                    return i3;
                }
            } else {
                this._padBits = this._currentParser.getPadBits();
                InterfaceC0008a7 nextParser2 = getNextParser();
                this._currentParser = nextParser2;
                if (nextParser2 == null) {
                    this._currentStream = null;
                    if (i3 < 1) {
                        return -1;
                    }
                    return i3;
                }
                this._currentStream = nextParser2.getBitStream();
            }
        }
    }
}
