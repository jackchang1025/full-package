package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
class UnparseableExtension extends Extension {
    private String name;
    private Throwable why;

    public UnparseableExtension(Extension extension, Throwable th) {
        super(extension);
        this.name = BuildConfig.FLAVOR;
        try {
            Class cls = OIDMap.getClass(extension.getExtensionId());
            if (cls != null) {
                this.name = ((String) cls.getDeclaredField("NAME").get(null)) + " ";
            }
        } catch (Exception unused) {
        }
        this.why = th;
    }

    @Override // android.sun.security.x509.Extension, android.sun.security.x509.CertAttrSet
    public String toString() {
        return super.toString() + "Unparseable " + this.name + "extension due to\n" + this.why + "\n\n" + new HexDumpEncoder().encodeBuffer(getExtensionValue());
    }
}
