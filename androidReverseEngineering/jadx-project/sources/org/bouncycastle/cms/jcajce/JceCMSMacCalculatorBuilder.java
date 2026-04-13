package org.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.Provider;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.io.MacOutputStream;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.jcajce.JceGenericKey;

/* loaded from: classes.dex */
public class JceCMSMacCalculatorBuilder {
    private AlgorithmParameters algorithmParameters;
    private EnvelopedDataHelper helper;
    private final int keySize;
    private final ASN1ObjectIdentifier macOID;
    private SecureRandom random;

    public class CMSMacCalculator implements MacCalculator {
        private AlgorithmIdentifier algorithmIdentifier;
        private SecretKey encKey;
        private Mac mac;

        public CMSMacCalculator(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
            KeyGenerator createKeyGenerator = JceCMSMacCalculatorBuilder.this.helper.createKeyGenerator(aSN1ObjectIdentifier);
            secureRandom = secureRandom == null ? new SecureRandom() : secureRandom;
            if (i2 < 0) {
                createKeyGenerator.init(secureRandom);
            } else {
                createKeyGenerator.init(i2, secureRandom);
            }
            this.encKey = createKeyGenerator.generateKey();
            this.algorithmIdentifier = JceCMSMacCalculatorBuilder.this.helper.getAlgorithmIdentifier(aSN1ObjectIdentifier, algorithmParameters == null ? JceCMSMacCalculatorBuilder.this.helper.generateParameters(aSN1ObjectIdentifier, this.encKey, secureRandom) : algorithmParameters);
            this.mac = JceCMSMacCalculatorBuilder.this.helper.createContentMac(this.encKey, this.algorithmIdentifier);
        }

        @Override // org.bouncycastle.operator.MacCalculator
        public AlgorithmIdentifier getAlgorithmIdentifier() {
            return this.algorithmIdentifier;
        }

        @Override // org.bouncycastle.operator.MacCalculator
        public GenericKey getKey() {
            return new JceGenericKey(this.algorithmIdentifier, this.encKey);
        }

        @Override // org.bouncycastle.operator.MacCalculator
        public byte[] getMac() {
            return this.mac.doFinal();
        }

        @Override // org.bouncycastle.operator.MacCalculator
        public OutputStream getOutputStream() {
            return new MacOutputStream(this.mac);
        }
    }

    public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        this(aSN1ObjectIdentifier, -1);
    }

    public MacCalculator build() {
        return new CMSMacCalculator(this.macOID, this.keySize, this.algorithmParameters, this.random);
    }

    public JceCMSMacCalculatorBuilder setAlgorithmParameters(AlgorithmParameters algorithmParameters) {
        this.algorithmParameters = algorithmParameters;
        return this;
    }

    public JceCMSMacCalculatorBuilder setProvider(String str) {
        this.helper = new EnvelopedDataHelper(new NamedJcaJceExtHelper(str));
        return this;
    }

    public JceCMSMacCalculatorBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public JceCMSMacCalculatorBuilder(ASN1ObjectIdentifier aSN1ObjectIdentifier, int i2) {
        this.helper = new EnvelopedDataHelper(new DefaultJcaJceExtHelper());
        this.macOID = aSN1ObjectIdentifier;
        this.keySize = i2;
    }

    public JceCMSMacCalculatorBuilder setProvider(Provider provider) {
        this.helper = new EnvelopedDataHelper(new ProviderJcaJceExtHelper(provider));
        return this;
    }
}
