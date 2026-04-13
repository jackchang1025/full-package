package org.bouncycastle.x509;

import java.security.cert.CertPath;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.i18n.LocalizedException;

/* loaded from: classes.dex */
public class CertPathReviewerException extends LocalizedException {
    private CertPath certPath;
    private int index;

    public CertPathReviewerException(ErrorBundle errorBundle) {
        super(errorBundle);
        this.index = -1;
        this.certPath = null;
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getIndex() {
        return this.index;
    }

    public CertPathReviewerException(ErrorBundle errorBundle, Throwable th) {
        super(errorBundle, th);
        this.index = -1;
        this.certPath = null;
    }

    public CertPathReviewerException(ErrorBundle errorBundle, Throwable th, CertPath certPath, int i2) {
        super(errorBundle, th);
        this.index = -1;
        this.certPath = null;
        if (certPath == null || i2 == -1) {
            throw new IllegalArgumentException();
        }
        if (i2 < -1 || i2 >= certPath.getCertificates().size()) {
            throw new IndexOutOfBoundsException();
        }
        this.certPath = certPath;
        this.index = i2;
    }

    public CertPathReviewerException(ErrorBundle errorBundle, CertPath certPath, int i2) {
        super(errorBundle);
        this.index = -1;
        this.certPath = null;
        if (certPath == null || i2 == -1) {
            throw new IllegalArgumentException();
        }
        if (i2 < -1 || i2 >= certPath.getCertificates().size()) {
            throw new IndexOutOfBoundsException();
        }
        this.certPath = certPath;
        this.index = i2;
    }
}
