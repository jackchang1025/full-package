package org.bouncycastle.util.io.pem;

import java.io.BufferedWriter;
import java.io.Writer;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.encoders.Base64;

/* loaded from: classes.dex */
public class PemWriter extends BufferedWriter {
    private static final int LINE_LENGTH = 64;
    private char[] buf;
    private final int nlLength;

    public PemWriter(Writer writer) {
        super(writer);
        this.buf = new char[64];
        String lineSeparator = Strings.lineSeparator();
        this.nlLength = lineSeparator != null ? lineSeparator.length() : 2;
    }

    private void writeEncoded(byte[] bArr) {
        char[] cArr;
        int i2;
        byte[] encode = Base64.encode(bArr);
        int i3 = 0;
        while (i3 < encode.length) {
            int i4 = 0;
            while (true) {
                cArr = this.buf;
                if (i4 != cArr.length && (i2 = i3 + i4) < encode.length) {
                    cArr[i4] = (char) encode[i2];
                    i4++;
                }
            }
            write(cArr, 0, i4);
            newLine();
            i3 += this.buf.length;
        }
    }

    private void writePostEncapsulationBoundary(String str) {
        write("-----END " + str + "-----");
        newLine();
    }

    private void writePreEncapsulationBoundary(String str) {
        write("-----BEGIN " + str + "-----");
        newLine();
    }

    public int getOutputSize(PemObject pemObject) {
        int length = ((pemObject.getType().length() + 10 + this.nlLength) * 2) + 6 + 4;
        if (!pemObject.getHeaders().isEmpty()) {
            for (PemHeader pemHeader : pemObject.getHeaders()) {
                length += pemHeader.getValue().length() + pemHeader.getName().length() + 2 + this.nlLength;
            }
            length += this.nlLength;
        }
        return ((((r5 + 64) - 1) / 64) * this.nlLength) + (((pemObject.getContent().length + 2) / 3) * 4) + length;
    }

    public void writeObject(PemObjectGenerator pemObjectGenerator) {
        PemObject generate = pemObjectGenerator.generate();
        writePreEncapsulationBoundary(generate.getType());
        if (!generate.getHeaders().isEmpty()) {
            for (PemHeader pemHeader : generate.getHeaders()) {
                write(pemHeader.getName());
                write(": ");
                write(pemHeader.getValue());
                newLine();
            }
            newLine();
        }
        writeEncoded(generate.getContent());
        writePostEncapsulationBoundary(generate.getType());
    }
}
