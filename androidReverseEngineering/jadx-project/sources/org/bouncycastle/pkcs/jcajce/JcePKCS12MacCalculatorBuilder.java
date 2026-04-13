package org.bouncycastle.pkcs.jcajce;

import java.io.OutputStream;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.Mac;
import javax.crypto.spec.PBEParameterSpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCS12PBEParams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.PKCS12Key;
import org.bouncycastle.jcajce.io.MacOutputStream;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS12MacCalculatorBuilder;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JcePKCS12MacCalculatorBuilder implements PKCS12MacCalculatorBuilder {
    private ASN1ObjectIdentifier algorithm;
    private JcaJceHelper helper;
    private int iterationCount;
    private SecureRandom random;
    private int saltLength;

    public JcePKCS12MacCalculatorBuilder() {
        this(OIWObjectIdentifiers.idSHA1);
    }

    @Override // org.bouncycastle.pkcs.PKCS12MacCalculatorBuilder
    public MacCalculator build(char[] cArr) {
        if (this.random == null) {
            this.random = new SecureRandom();
        }
        try {
            final Mac createMac = this.helper.createMac(this.algorithm.getId());
            int macLength = createMac.getMacLength();
            this.saltLength = macLength;
            final byte[] bArr = new byte[macLength];
            this.random.nextBytes(bArr);
            PBEParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, this.iterationCount);
            final PKCS12Key pKCS12Key = new PKCS12Key(cArr);
            createMac.init(pKCS12Key, pBEParameterSpec);
            return new MacCalculator() { // from class: org.bouncycastle.pkcs.jcajce.JcePKCS12MacCalculatorBuilder.1
                @Override // org.bouncycastle.operator.MacCalculator
                public AlgorithmIdentifier getAlgorithmIdentifier() {
                    return new AlgorithmIdentifier(JcePKCS12MacCalculatorBuilder.this.algorithm, new PKCS12PBEParams(bArr, JcePKCS12MacCalculatorBuilder.this.iterationCount));
                }

                @Override // org.bouncycastle.operator.MacCalculator
                public GenericKey getKey() {
                    return new GenericKey(getAlgorithmIdentifier(), pKCS12Key.getEncoded());
                }

                @Override // org.bouncycastle.operator.MacCalculator
                public byte[] getMac() {
                    return createMac.doFinal();
                }

                @Override // org.bouncycastle.operator.MacCalculator
                public OutputStream getOutputStream() {
                    return new MacOutputStream(createMac);
                }
            };
        } catch (Exception e2) {
            throw new OperatorCreationException(AbstractC0000a.m9e(e2, new StringBuilder("unable to create MAC calculator: ")), e2);
        }
    }

    @Override // org.bouncycastle.pkcs.PKCS12MacCalculatorBuilder
    public AlgorithmIdentifier getDigestAlgorithmIdentifier() {
        return new AlgorithmIdentifier(this.algorithm, DERNull.INSTANCE);
    }

    public JcePKCS12MacCalculatorBuilder setIterationCount(int i2) {
        this.iterationCount = i2;
        return this;
    }

    public JcePKCS12MacCalculatorBuilder setProvider(String str) {
        this.helper = new NamedJcaJceHelper(str);
        return this;
    }

    public JcePKCS12MacCalculatorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this.helper = new DefaultJcaJceHelper();
        this.iterationCount = 1024;
        this.algorithm = aSN1ObjectIdentifier;
    }

    public JcePKCS12MacCalculatorBuilder setProvider(Provider provider) {
        this.helper = new ProviderJcaJceHelper(provider);
        return this;
    }
}
