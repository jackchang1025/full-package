package p000;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import p000.s41;

/* loaded from: classes2.dex */
public class oh1 {

    /* renamed from: ca */
    private r20 f58836ca;
    private s41.C1207a0 entry;

    public oh1(s41.C1207a0 c1207a0, boolean z, r20 r20Var) {
        C1452yc extension;
        this.entry = c1207a0;
        this.f58836ca = r20Var;
        if (z && c1207a0.hasExtensions() && (extension = c1207a0.getExtensions().getExtension(C1452yc.certificateIssuer)) != null) {
            this.f58836ca = r20.getInstance(extension.getParsedValue());
        }
    }

    public r20 getCertificateIssuer() {
        return this.f58836ca;
    }

    public Set getCriticalExtensionOIDs() {
        return C0543go.getCriticalExtensionOIDs(this.entry.getExtensions());
    }

    public C1452yc getExtension(C0160c5 c0160c5) {
        C1454ye extensions = this.entry.getExtensions();
        if (extensions != null) {
            return extensions.getExtension(c0160c5);
        }
        return null;
    }

    public List getExtensionOIDs() {
        return C0543go.getExtensionOIDs(this.entry.getExtensions());
    }

    public C1454ye getExtensions() {
        return this.entry.getExtensions();
    }

    public Set getNonCriticalExtensionOIDs() {
        return C0543go.getNonCriticalExtensionOIDs(this.entry.getExtensions());
    }

    public Date getRevocationDate() {
        return this.entry.getRevocationDate().getDate();
    }

    public BigInteger getSerialNumber() {
        return this.entry.getUserCertificate().getValue();
    }

    public boolean hasExtensions() {
        return this.entry.hasExtensions();
    }
}
