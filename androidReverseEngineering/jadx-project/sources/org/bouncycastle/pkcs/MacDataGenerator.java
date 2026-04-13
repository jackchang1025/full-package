package org.bouncycastle.pkcs;

import java.io.OutputStream;
import org.bouncycastle.asn1.pkcs.MacData;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.DigestInfo;
import org.bouncycastle.operator.MacCalculator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class MacDataGenerator {
    private PKCS12MacCalculatorBuilder builder;

    public MacDataGenerator(PKCS12MacCalculatorBuilder pKCS12MacCalculatorBuilder) {
        this.builder = pKCS12MacCalculatorBuilder;
    }

    public MacData build(char[] cArr, byte[] bArr) {
        try {
            MacCalculator build = this.builder.build(cArr);
            OutputStream outputStream = build.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
            AlgorithmIdentifier algorithmIdentifier = build.getAlgorithmIdentifier();
            DigestInfo digestInfo = new DigestInfo(this.builder.getDigestAlgorithmIdentifier(), build.getMac());
            PKCS12PBEParams pKCS12PBEParams = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
            return new MacData(digestInfo, pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
        } catch (Exception e2) {
            throw new PKCSException(AbstractC0000a.m9e(e2, new StringBuilder("unable to process data: ")), e2);
        }
    }
}
