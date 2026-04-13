package org.bouncycastle.cms.bc;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.PasswordRecipient;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class BcPasswordRecipient implements PasswordRecipient {
    private final char[] password;
    private int schemeID = 1;

    public BcPasswordRecipient(char[] cArr) {
        this.password = cArr;
    }

    @Override // org.bouncycastle.cms.PasswordRecipient
    public byte[] calculateDerivedKey(int i2, AlgorithmIdentifier algorithmIdentifier, int i3) {
        PBKDF2Params pBKDF2Params = PBKDF2Params.getInstance(algorithmIdentifier.getParameters());
        byte[] PKCS5PasswordToBytes = i2 == 0 ? PBEParametersGenerator.PKCS5PasswordToBytes(this.password) : PBEParametersGenerator.PKCS5PasswordToUTF8Bytes(this.password);
        try {
            PKCS5S2ParametersGenerator pKCS5S2ParametersGenerator = new PKCS5S2ParametersGenerator(EnvelopedDataHelper.getPRF(pBKDF2Params.getPrf()));
            pKCS5S2ParametersGenerator.init(PKCS5PasswordToBytes, pBKDF2Params.getSalt(), pBKDF2Params.getIterationCount().intValue());
            return ((KeyParameter) pKCS5S2ParametersGenerator.generateDerivedParameters(i3)).getKey();
        } catch (Exception e2) {
            throw new CMSException(AbstractC0000a.m9e(e2, new StringBuilder("exception creating derived key: ")), e2);
        }
    }

    public KeyParameter extractSecretKey(AlgorithmIdentifier algorithmIdentifier, AlgorithmIdentifier algorithmIdentifier2, byte[] bArr, byte[] bArr2) {
        Wrapper createRFC3211Wrapper = EnvelopedDataHelper.createRFC3211Wrapper(algorithmIdentifier.getAlgorithm());
        createRFC3211Wrapper.init(false, new ParametersWithIV(new KeyParameter(bArr), ASN1OctetString.getInstance(algorithmIdentifier.getParameters()).getOctets()));
        try {
            return new KeyParameter(createRFC3211Wrapper.unwrap(bArr2, 0, bArr2.length));
        } catch (InvalidCipherTextException e2) {
            throw new CMSException("unable to unwrap key: " + e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.cms.PasswordRecipient
    public char[] getPassword() {
        return this.password;
    }

    @Override // org.bouncycastle.cms.PasswordRecipient
    public int getPasswordConversionScheme() {
        return this.schemeID;
    }

    public BcPasswordRecipient setPasswordConversionScheme(int i2) {
        this.schemeID = i2;
        return this;
    }
}
