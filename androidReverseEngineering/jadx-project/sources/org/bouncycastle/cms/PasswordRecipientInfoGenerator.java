package org.bouncycastle.cms;

import java.security.SecureRandom;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.PasswordRecipientInfo;
import org.bouncycastle.asn1.cms.RecipientInfo;
import org.bouncycastle.asn1.pkcs.PBKDF2Params;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cms.PasswordRecipient;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.util.Arrays;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class PasswordRecipientInfoGenerator implements RecipientInfoGenerator {
    private int blockSize;
    private int iterationCount;
    private ASN1ObjectIdentifier kekAlgorithm;
    private AlgorithmIdentifier keyDerivationAlgorithm;
    private int keySize;
    protected char[] password;
    private PasswordRecipient.PRF prf;
    private SecureRandom random;
    private byte[] salt;
    private int schemeID;

    public PasswordRecipientInfoGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier, char[] cArr) {
        this(aSN1ObjectIdentifier, cArr, getKeySize(aSN1ObjectIdentifier), ((Integer) PasswordRecipientInformation.BLOCKSIZES.get(aSN1ObjectIdentifier)).intValue());
    }

    private static int getKeySize(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Integer num = (Integer) PasswordRecipientInformation.KEYSIZES.get(aSN1ObjectIdentifier);
        if (num != null) {
            return num.intValue();
        }
        throw new IllegalArgumentException(AbstractC0413b.m1009c("cannot find key size for algorithm: ", aSN1ObjectIdentifier));
    }

    public abstract byte[] calculateDerivedKey(int i2, AlgorithmIdentifier algorithmIdentifier, int i3);

    @Override // org.bouncycastle.cms.RecipientInfoGenerator
    public RecipientInfo generate(GenericKey genericKey) {
        byte[] bArr = new byte[this.blockSize];
        if (this.random == null) {
            this.random = new SecureRandom();
        }
        this.random.nextBytes(bArr);
        if (this.salt == null) {
            byte[] bArr2 = new byte[20];
            this.salt = bArr2;
            this.random.nextBytes(bArr2);
        }
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_PBKDF2, new PBKDF2Params(this.salt, this.iterationCount, this.prf.prfAlgID));
        this.keyDerivationAlgorithm = algorithmIdentifier;
        DEROctetString dEROctetString = new DEROctetString(generateEncryptedBytes(new AlgorithmIdentifier(this.kekAlgorithm, new DEROctetString(bArr)), calculateDerivedKey(this.schemeID, algorithmIdentifier, this.keySize), genericKey));
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.kekAlgorithm);
        aSN1EncodableVector.add(new DEROctetString(bArr));
        return new RecipientInfo(new PasswordRecipientInfo(this.keyDerivationAlgorithm, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_PWRI_KEK, new DERSequence(aSN1EncodableVector)), dEROctetString));
    }

    public abstract byte[] generateEncryptedBytes(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, GenericKey genericKey);

    public PasswordRecipientInfoGenerator setPRF(PasswordRecipient.PRF prf) {
        this.prf = prf;
        return this;
    }

    public PasswordRecipientInfoGenerator setPasswordConversionScheme(int i2) {
        this.schemeID = i2;
        return this;
    }

    public PasswordRecipientInfoGenerator setSaltAndIterationCount(byte[] bArr, int i2) {
        this.salt = Arrays.clone(bArr);
        this.iterationCount = i2;
        return this;
    }

    public PasswordRecipientInfoGenerator setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public PasswordRecipientInfoGenerator(ASN1ObjectIdentifier aSN1ObjectIdentifier, char[] cArr, int i2, int i3) {
        this.password = cArr;
        this.schemeID = 1;
        this.kekAlgorithm = aSN1ObjectIdentifier;
        this.keySize = i2;
        this.blockSize = i3;
        this.prf = PasswordRecipient.PRF.HMacSHA1;
        this.iterationCount = 1024;
    }
}
