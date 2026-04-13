package org.bouncycastle.jce.provider;

import java.security.InvalidAlgorithmParameterException;
import java.security.cert.CRL;
import java.security.cert.CRLSelector;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertStoreSpi;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.jce.MultiCertStoreParameters;

/* loaded from: classes.dex */
public class MultiCertStoreSpi extends CertStoreSpi {
    private MultiCertStoreParameters params;

    public MultiCertStoreSpi(CertStoreParameters certStoreParameters) {
        super(certStoreParameters);
        if (certStoreParameters instanceof MultiCertStoreParameters) {
            this.params = (MultiCertStoreParameters) certStoreParameters;
        } else {
            throw new InvalidAlgorithmParameterException("org.bouncycastle.jce.provider.MultiCertStoreSpi: parameter must be a MultiCertStoreParameters object\n" + certStoreParameters.toString());
        }
    }

    @Override // java.security.cert.CertStoreSpi
    public Collection engineGetCRLs(CRLSelector cRLSelector) {
        boolean searchAllStores = this.params.getSearchAllStores();
        Iterator it = this.params.getCertStores().iterator();
        List arrayList = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        while (it.hasNext()) {
            Collection<? extends CRL> cRLs = ((CertStore) it.next()).getCRLs(cRLSelector);
            if (searchAllStores) {
                arrayList.addAll(cRLs);
            } else if (!cRLs.isEmpty()) {
                return cRLs;
            }
        }
        return arrayList;
    }

    @Override // java.security.cert.CertStoreSpi
    public Collection engineGetCertificates(CertSelector certSelector) {
        boolean searchAllStores = this.params.getSearchAllStores();
        Iterator it = this.params.getCertStores().iterator();
        List arrayList = searchAllStores ? new ArrayList() : Collections.EMPTY_LIST;
        while (it.hasNext()) {
            Collection<? extends Certificate> certificates = ((CertStore) it.next()).getCertificates(certSelector);
            if (searchAllStores) {
                arrayList.addAll(certificates);
            } else if (!certificates.isEmpty()) {
                return certificates;
            }
        }
        return arrayList;
    }
}
