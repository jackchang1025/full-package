package org.bouncycastle.cert.path.validations;

import org.bouncycastle.cert.path.CertPath;

/* loaded from: classes.dex */
public class CertificatePoliciesValidationBuilder {
    private boolean isAnyPolicyInhibited;
    private boolean isExplicitPolicyRequired;
    private boolean isPolicyMappingInhibited;

    public CertificatePoliciesValidation build(int i2) {
        return new CertificatePoliciesValidation(i2, this.isExplicitPolicyRequired, this.isAnyPolicyInhibited, this.isPolicyMappingInhibited);
    }

    public void setAnyPolicyInhibited(boolean z2) {
        this.isAnyPolicyInhibited = z2;
    }

    public void setExplicitPolicyRequired(boolean z2) {
        this.isExplicitPolicyRequired = z2;
    }

    public void setPolicyMappingInhibited(boolean z2) {
        this.isPolicyMappingInhibited = z2;
    }

    public CertificatePoliciesValidation build(CertPath certPath) {
        return build(certPath.length());
    }
}
