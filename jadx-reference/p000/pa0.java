package p000;

import java.io.InputStream;

/* loaded from: classes2.dex */
public abstract class pa0 extends InputStream {
    protected final InputStream _in;
    private int _limit;

    public pa0(InputStream inputStream, int i) {
        this._in = inputStream;
        this._limit = i;
    }

    public int getLimit() {
        return this._limit;
    }

    public void setParentEofDetect(boolean z) {
        InputStream inputStream = this._in;
        if (inputStream instanceof m50) {
            ((m50) inputStream).setEofOn00(z);
        }
    }
}
