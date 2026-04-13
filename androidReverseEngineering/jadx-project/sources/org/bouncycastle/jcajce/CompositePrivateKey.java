package org.bouncycastle.jcajce;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class CompositePrivateKey implements PrivateKey {
    private final List<PrivateKey> keys;

    public CompositePrivateKey(PrivateKey... privateKeyArr) {
        if (privateKeyArr == null || privateKeyArr.length == 0) {
            throw new IllegalArgumentException("at least one public key must be provided");
        }
        ArrayList arrayList = new ArrayList(privateKeyArr.length);
        for (int i2 = 0; i2 != privateKeyArr.length; i2++) {
            arrayList.add(privateKeyArr[i2]);
        }
        this.keys = Collections.unmodifiableList(arrayList);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof CompositePrivateKey) {
            return this.keys.equals(((CompositePrivateKey) obj).keys);
        }
        return false;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return "Composite";
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        for (int i2 = 0; i2 != this.keys.size(); i2++) {
            aSN1EncodableVector.add(PrivateKeyInfo.getInstance(this.keys.get(i2).getEncoded()));
        }
        try {
            return new PrivateKeyInfo(new AlgorithmIdentifier(MiscObjectIdentifiers.id_alg_composite), new DERSequence(aSN1EncodableVector)).getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("unable to encode composite key: ")));
        }
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    public List<PrivateKey> getPrivateKeys() {
        return this.keys;
    }

    public int hashCode() {
        return this.keys.hashCode();
    }
}
