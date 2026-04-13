package org.bouncycastle.cert.jcajce;

import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.CollectionStore;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcaCertStore extends CollectionStore {
    public JcaCertStore(Collection collection) {
        super(convertCerts(collection));
    }

    private static Collection convertCerts(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        for (Object obj : collection) {
            if (obj instanceof X509Certificate) {
                try {
                    arrayList.add(new X509CertificateHolder(((X509Certificate) obj).getEncoded()));
                } catch (IOException e2) {
                    throw new CertificateEncodingException(AbstractC0000a.m8d(e2, new StringBuilder("unable to read encoding: ")));
                }
            } else {
                arrayList.add((X509CertificateHolder) obj);
            }
        }
        return arrayList;
    }
}
