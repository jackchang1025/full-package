package android.sun.security.pkcs;

import android.sun.security.util.DerValue;
import java.io.IOException;

/* loaded from: classes.dex */
public class SigningCertificateInfo {
    private byte[] ber = null;
    private ESSCertId[] certId = null;

    public SigningCertificateInfo(byte[] bArr) {
        parse(bArr);
    }

    public void parse(byte[] bArr) {
        DerValue derValue = new DerValue(bArr);
        if (derValue.tag != 48) {
            throw new IOException("Bad encoding for signingCertificate");
        }
        DerValue[] sequence = derValue.data.getSequence(1);
        this.certId = new ESSCertId[sequence.length];
        for (int i2 = 0; i2 < sequence.length; i2++) {
            this.certId[i2] = new ESSCertId(sequence[i2]);
        }
        if (derValue.data.available() > 0) {
            for (int i3 = 0; i3 < derValue.data.getSequence(1).length; i3++) {
            }
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[\n");
        int i2 = 0;
        while (true) {
            ESSCertId[] eSSCertIdArr = this.certId;
            if (i2 >= eSSCertIdArr.length) {
                stringBuffer.append("\n]");
                return stringBuffer.toString();
            }
            stringBuffer.append(eSSCertIdArr[i2].toString());
            i2++;
        }
    }
}
