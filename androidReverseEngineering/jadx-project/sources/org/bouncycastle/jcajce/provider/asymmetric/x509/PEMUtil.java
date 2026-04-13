package org.bouncycastle.jcajce.provider.asymmetric.x509;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.util.encoders.Base64;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class PEMUtil {
    private final Boundaries[] _supportedBoundaries;

    public class Boundaries {
        private final String _footer;
        private final String _header;

        private Boundaries(String str) {
            this._header = AbstractC0000a.m16l("-----BEGIN ", str, "-----");
            this._footer = AbstractC0000a.m16l("-----END ", str, "-----");
        }

        public boolean isTheExpectedFooter(String str) {
            return str.startsWith(this._footer);
        }

        public boolean isTheExpectedHeader(String str) {
            return str.startsWith(this._header);
        }
    }

    public PEMUtil(String str) {
        this._supportedBoundaries = new Boundaries[]{new Boundaries(str), new Boundaries(AbstractC0000a.m15k("X509 ", str)), new Boundaries(PEMParser.TYPE_PKCS7)};
    }

    private Boundaries getBoundaries(String str) {
        Boundaries boundaries;
        int i2 = 0;
        while (true) {
            Boundaries[] boundariesArr = this._supportedBoundaries;
            if (i2 == boundariesArr.length) {
                return null;
            }
            boundaries = boundariesArr[i2];
            if (boundaries.isTheExpectedHeader(str) || boundaries.isTheExpectedFooter(str)) {
                break;
            }
            i2++;
        }
        return boundaries;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x001e, code lost:
    
        if (r0.length() == 0) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String readLine(InputStream inputStream) {
        int read;
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            read = inputStream.read();
            if (read == 13 || read == 10 || read < 0) {
                break;
            }
            stringBuffer.append((char) read);
        }
        if (read < 0) {
            if (stringBuffer.length() == 0) {
                return null;
            }
            return stringBuffer.toString();
        }
        if (read == 13) {
            inputStream.mark(1);
            int read2 = inputStream.read();
            if (read2 == 10) {
                inputStream.mark(1);
            }
            if (read2 > 0) {
                inputStream.reset();
            }
        }
        return stringBuffer.toString();
    }

    public ASN1Sequence readPEMObject(InputStream inputStream, boolean z2) {
        StringBuffer stringBuffer = new StringBuffer();
        Boundaries boundaries = null;
        while (boundaries == null) {
            String readLine = readLine(inputStream);
            if (readLine == null) {
                break;
            }
            boundaries = getBoundaries(readLine);
            if (boundaries != null && !boundaries.isTheExpectedHeader(readLine)) {
                throw new IOException("malformed PEM data: found footer where header was expected");
            }
        }
        if (boundaries == null) {
            if (z2) {
                throw new IOException("malformed PEM data: no header found");
            }
            return null;
        }
        Boundaries boundaries2 = null;
        while (boundaries2 == null) {
            String readLine2 = readLine(inputStream);
            if (readLine2 == null) {
                break;
            }
            boundaries2 = getBoundaries(readLine2);
            if (boundaries2 == null) {
                stringBuffer.append(readLine2);
            } else if (!boundaries.isTheExpectedFooter(readLine2)) {
                throw new IOException("malformed PEM data: header/footer mismatch");
            }
        }
        if (boundaries2 == null) {
            throw new IOException("malformed PEM data: no footer found");
        }
        if (stringBuffer.length() == 0) {
            return null;
        }
        try {
            return ASN1Sequence.getInstance(Base64.decode(stringBuffer.toString()));
        } catch (Exception unused) {
            throw new IOException("malformed PEM data encountered");
        }
    }
}
