package android.sun.security.x509;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CertificateAlgorithmId implements CertAttrSet<String> {
    public static final String ALGORITHM = "algorithm";
    public static final String IDENT = "x509.info.algorithmID";
    public static final String NAME = "algorithmID";
    private AlgorithmId algId;

    public CertificateAlgorithmId(DerInputStream derInputStream) {
        this.algId = AlgorithmId.parse(derInputStream.getDerValue());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void delete(String str) {
        if (!str.equalsIgnoreCase("algorithm")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
        }
        this.algId = null;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void encode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.algId.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Object get(String str) {
        if (str.equalsIgnoreCase("algorithm")) {
            return this.algId;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public Enumeration<String> getElements() {
        return AbstractC0000a.m24t("algorithm");
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String getName() {
        return "algorithmID";
    }

    @Override // android.sun.security.x509.CertAttrSet
    public void set(String str, Object obj) {
        if (!(obj instanceof AlgorithmId)) {
            throw new IOException("Attribute must be of type AlgorithmId.");
        }
        if (!str.equalsIgnoreCase("algorithm")) {
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
        }
        this.algId = (AlgorithmId) obj;
    }

    @Override // android.sun.security.x509.CertAttrSet
    public String toString() {
        if (this.algId == null) {
            return BuildConfig.FLAVOR;
        }
        return this.algId.toString() + ", OID = " + this.algId.getOID().toString() + "\n";
    }

    public CertificateAlgorithmId(AlgorithmId algorithmId) {
        this.algId = algorithmId;
    }

    public CertificateAlgorithmId(InputStream inputStream) {
        this.algId = AlgorithmId.parse(new DerValue(inputStream));
    }
}
