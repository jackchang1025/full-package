package org.bouncycastle.jcajce.provider.symmetric;

import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKey;
import org.bouncycastle.asn1.misc.MiscObjectIdentifiers;
import org.bouncycastle.crypto.PasswordConverter;
import org.bouncycastle.crypto.generators.SCrypt;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jcajce.provider.config.ConfigurableProvider;
import org.bouncycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory;
import org.bouncycastle.jcajce.provider.util.AlgorithmProvider;
import org.bouncycastle.jcajce.spec.ScryptKeySpec;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class SCRYPT {

    public static class BasePBKDF2 extends BaseSecretKeyFactory {
        private int scheme;

        public BasePBKDF2(String str, int i2) {
            super(str, MiscObjectIdentifiers.id_scrypt);
            this.scheme = i2;
        }

        @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseSecretKeyFactory, javax.crypto.SecretKeyFactorySpi
        public SecretKey engineGenerateSecret(KeySpec keySpec) {
            if (!(keySpec instanceof ScryptKeySpec)) {
                throw new InvalidKeySpecException("Invalid KeySpec");
            }
            ScryptKeySpec scryptKeySpec = (ScryptKeySpec) keySpec;
            if (scryptKeySpec.getSalt() == null) {
                throw new IllegalArgumentException("Salt S must be provided.");
            }
            if (scryptKeySpec.getCostParameter() <= 1) {
                throw new IllegalArgumentException("Cost parameter N must be > 1.");
            }
            if (scryptKeySpec.getKeyLength() <= 0) {
                throw new InvalidKeySpecException("positive key length required: " + scryptKeySpec.getKeyLength());
            }
            if (scryptKeySpec.getPassword().length == 0) {
                throw new IllegalArgumentException("password empty");
            }
            return new BCPBEKey(this.algName, new KeyParameter(SCrypt.generate(PasswordConverter.UTF8.convert(scryptKeySpec.getPassword()), scryptKeySpec.getSalt(), scryptKeySpec.getCostParameter(), scryptKeySpec.getBlockSize(), scryptKeySpec.getParallelizationParameter(), scryptKeySpec.getKeyLength() / 8)));
        }
    }

    public static class Mappings extends AlgorithmProvider {
        private static final String PREFIX = SCRYPT.class.getName();

        @Override // org.bouncycastle.jcajce.provider.util.AlgorithmProvider
        public void configure(ConfigurableProvider configurableProvider) {
            StringBuilder sb = new StringBuilder();
            String str = PREFIX;
            AbstractC0413b.m1030x(sb, str, "$ScryptWithUTF8", configurableProvider, "SecretKeyFactory.SCRYPT");
            AbstractC0413b.m1029w(str, "$ScryptWithUTF8", configurableProvider, "SecretKeyFactory", MiscObjectIdentifiers.id_scrypt);
        }
    }

    public static class ScryptWithUTF8 extends BasePBKDF2 {
        public ScryptWithUTF8() {
            super("SCRYPT", 5);
        }
    }

    private SCRYPT() {
    }
}
