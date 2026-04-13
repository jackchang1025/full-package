package org.bouncycastle.mime;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import org.bouncycastle.mime.smime.SMimeParserContext;

/* loaded from: classes.dex */
public class CanonicalOutputStream extends FilterOutputStream {
    protected static byte[] newline = {13, 10};
    private final boolean is7Bit;
    protected int lastb;

    public CanonicalOutputStream(SMimeParserContext sMimeParserContext, Headers headers, OutputStream outputStream) {
        super(outputStream);
        this.lastb = -1;
        this.is7Bit = headers.getContentType() != null ? (headers.getContentType() == null || headers.getContentType().equals("binary")) ? false : true : sMimeParserContext.getDefaultContentTransferEncoding().equals("7bit");
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) {
        if (this.is7Bit) {
            if (i2 == 13) {
                ((FilterOutputStream) this).out.write(newline);
            } else if (i2 == 10) {
                if (this.lastb != 13) {
                    ((FilterOutputStream) this).out.write(newline);
                }
            }
            this.lastb = i2;
        }
        ((FilterOutputStream) this).out.write(i2);
        this.lastb = i2;
    }

    public void writeln() {
        ((FilterOutputStream) this).out.write(newline);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        for (int i4 = i2; i4 != i2 + i3; i4++) {
            write(bArr[i4]);
        }
    }
}
