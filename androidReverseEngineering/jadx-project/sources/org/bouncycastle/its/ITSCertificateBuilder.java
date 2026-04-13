package org.bouncycastle.its;

import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.oer.its.CrlSeries;
import org.bouncycastle.oer.its.HashedId;
import org.bouncycastle.oer.its.PsidGroupPermissions;
import org.bouncycastle.oer.its.PsidSsp;
import org.bouncycastle.oer.its.SequenceOfPsidGroupPermissions;
import org.bouncycastle.oer.its.SequenceOfPsidSsp;
import org.bouncycastle.oer.its.ToBeSignedCertificate;

/* loaded from: classes.dex */
public class ITSCertificateBuilder {
    protected HashedId cracaId;
    protected CrlSeries crlSeries;
    protected final ITSCertificate issuer;
    protected final ToBeSignedCertificate.Builder tbsCertificateBuilder;
    protected ASN1Integer version;

    public ITSCertificateBuilder(ITSCertificate iTSCertificate, ToBeSignedCertificate.Builder builder) {
        this.version = new ASN1Integer(3L);
        this.cracaId = new HashedId.HashedId3(new byte[3]);
        this.crlSeries = new CrlSeries(0);
        this.issuer = iTSCertificate;
        this.tbsCertificateBuilder = builder;
        builder.setCracaId(this.cracaId);
        builder.setCrlSeries(this.crlSeries);
    }

    public ITSCertificate getIssuer() {
        return this.issuer;
    }

    public ITSCertificateBuilder setAppPermissions(PsidSsp... psidSspArr) {
        SequenceOfPsidSsp.Builder builder = SequenceOfPsidSsp.builder();
        for (int i2 = 0; i2 != psidSspArr.length; i2++) {
            builder.setItem(psidSspArr[i2]);
        }
        this.tbsCertificateBuilder.setAppPermissions(builder.createSequenceOfPsidSsp());
        return this;
    }

    public ITSCertificateBuilder setCertIssuePermissions(PsidGroupPermissions... psidGroupPermissionsArr) {
        this.tbsCertificateBuilder.setCertIssuePermissions(SequenceOfPsidGroupPermissions.builder().addGroupPermission(psidGroupPermissionsArr).createSequenceOfPsidGroupPermissions());
        return this;
    }

    public ITSCertificateBuilder setCracaId(byte[] bArr) {
        HashedId.HashedId3 hashedId3 = new HashedId.HashedId3(bArr);
        this.cracaId = hashedId3;
        this.tbsCertificateBuilder.setCracaId(hashedId3);
        return this;
    }

    public ITSCertificateBuilder setCrlSeries(int i2) {
        CrlSeries crlSeries = new CrlSeries(i2);
        this.crlSeries = crlSeries;
        this.tbsCertificateBuilder.setCrlSeries(crlSeries);
        return this;
    }

    public ITSCertificateBuilder setValidityPeriod(ITSValidityPeriod iTSValidityPeriod) {
        this.tbsCertificateBuilder.setValidityPeriod(iTSValidityPeriod.toASN1Structure());
        return this;
    }

    public ITSCertificateBuilder setVersion(int i2) {
        this.version = new ASN1Integer(i2);
        return this;
    }

    public ITSCertificateBuilder(ToBeSignedCertificate.Builder builder) {
        this(null, builder);
    }
}
