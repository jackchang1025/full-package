package org.bouncycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import org.bouncycastle.util.encoders.Base64;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PemReader extends BufferedReader {
    private static final String BEGIN = "-----BEGIN ";
    private static final String END = "-----END ";

    public PemReader(Reader reader) {
        super(reader);
    }

    private PemObject loadObject(String str) {
        String readLine;
        String m15k = AbstractC0000a.m15k(END, str);
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        while (true) {
            readLine = readLine();
            if (readLine == null) {
                break;
            }
            int indexOf = readLine.indexOf(58);
            if (indexOf >= 0) {
                arrayList.add(new PemHeader(readLine.substring(0, indexOf), readLine.substring(indexOf + 1).trim()));
            } else {
                if (readLine.indexOf(m15k) != -1) {
                    break;
                }
                stringBuffer.append(readLine.trim());
            }
        }
        if (readLine != null) {
            return new PemObject(str, arrayList, Base64.decode(stringBuffer.toString()));
        }
        throw new IOException(AbstractC0000a.m30z(m15k, " not found"));
    }

    public PemObject readPemObject() {
        String readLine;
        String substring;
        int indexOf;
        do {
            readLine = readLine();
            if (readLine == null) {
                break;
            }
        } while (!readLine.startsWith(BEGIN));
        if (readLine == null || (indexOf = (substring = readLine.substring(11)).indexOf(45)) <= 0 || !substring.endsWith("-----") || substring.length() - indexOf != 5) {
            return null;
        }
        return loadObject(substring.substring(0, indexOf));
    }
}
