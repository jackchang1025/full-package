package org.bouncycastle.asn1;

import java.io.OutputStream;

/* loaded from: classes.dex */
public abstract class BERGenerator extends ASN1Generator {
    private boolean _isExplicit;
    private int _tagNo;
    private boolean _tagged;

    public BERGenerator(OutputStream outputStream) {
        super(outputStream);
        this._tagged = false;
    }

    private void writeHdr(int i2) {
        this._out.write(i2);
        this._out.write(128);
    }

    @Override // org.bouncycastle.asn1.ASN1Generator
    public OutputStream getRawOutputStream() {
        return this._out;
    }

    public void writeBEREnd() {
        this._out.write(0);
        this._out.write(0);
        if (this._tagged && this._isExplicit) {
            this._out.write(0);
            this._out.write(0);
        }
    }

    public void writeBERHeader(int i2) {
        if (this._tagged) {
            int i3 = this._tagNo | 128;
            if (this._isExplicit) {
                writeHdr(i3 | 32);
            } else {
                if ((i2 & 32) == 0) {
                    writeHdr(i3);
                    return;
                }
                i2 = i3 | 32;
            }
        }
        writeHdr(i2);
    }

    public BERGenerator(OutputStream outputStream, int i2, boolean z2) {
        super(outputStream);
        this._tagged = true;
        this._isExplicit = z2;
        this._tagNo = i2;
    }
}
