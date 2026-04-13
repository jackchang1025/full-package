package android.sun.security.pkcs;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.DerValue;
import android.sun.security.x509.GeneralNames;
import android.sun.security.x509.SerialNumber;

/* loaded from: classes.dex */
class ESSCertId {
    private static volatile HexDumpEncoder hexDumper;
    private byte[] certHash;
    private GeneralNames issuer;
    private SerialNumber serialNumber;

    public ESSCertId(DerValue derValue) {
        this.certHash = derValue.data.getDerValue().toByteArray();
        if (derValue.data.available() > 0) {
            DerValue derValue2 = derValue.data.getDerValue();
            this.issuer = new GeneralNames(derValue2.data.getDerValue());
            this.serialNumber = new SerialNumber(derValue2.data.getDerValue());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("[\n\tCertificate hash (SHA-1):\n");
        if (hexDumper == null) {
            hexDumper = new HexDumpEncoder();
        }
        stringBuffer.append(hexDumper.encode(this.certHash));
        if (this.issuer != null && this.serialNumber != null) {
            stringBuffer.append("\n\tIssuer: " + this.issuer + "\n");
            StringBuilder sb = new StringBuilder("\t");
            sb.append(this.serialNumber);
            stringBuffer.append(sb.toString());
        }
        stringBuffer.append("\n]");
        return stringBuffer.toString();
    }
}
