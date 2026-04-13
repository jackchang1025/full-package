package org.bouncycastle.operator.jcajce;

import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.ProviderException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.bouncycastle.operator.AsymmetricKeyUnwrapper;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.OperatorException;

/* loaded from: classes.dex */
public class JceAsymmetricKeyUnwrapper extends AsymmetricKeyUnwrapper {
    private Map extraMappings;
    private OperatorHelper helper;
    private PrivateKey privKey;
    private boolean unwrappedKeyMustBeEncodable;

    public JceAsymmetricKeyUnwrapper(AlgorithmIdentifier algorithmIdentifier, PrivateKey privateKey) {
        super(algorithmIdentifier);
        this.helper = new OperatorHelper(new DefaultJcaJceHelper());
        this.extraMappings = new HashMap();
        this.privKey = privateKey;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0043, code lost:
    
        if (r2.length == 0) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0049 A[Catch: BadPaddingException -> 0x0066, IllegalBlockSizeException -> 0x007f, InvalidKeyException -> 0x0098, TRY_ENTER, TryCatch #4 {InvalidKeyException -> 0x0098, BadPaddingException -> 0x0066, IllegalBlockSizeException -> 0x007f, blocks: (B:2:0x0000, B:16:0x0049, B:17:0x0060), top: B:1:0x0000 }] */
    @Override // org.bouncycastle.operator.KeyUnwrapper
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public GenericKey generateUnwrappedKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr) {
        Key unwrap;
        try {
            Cipher createAsymmetricWrapper = this.helper.createAsymmetricWrapper(getAlgorithmIdentifier().getAlgorithm(), this.extraMappings);
            AlgorithmParameters createAlgorithmParameters = this.helper.createAlgorithmParameters(getAlgorithmIdentifier());
            Key key = null;
            try {
                if (createAlgorithmParameters != null) {
                    createAsymmetricWrapper.init(4, this.privKey, createAlgorithmParameters);
                } else {
                    createAsymmetricWrapper.init(4, this.privKey);
                }
                unwrap = createAsymmetricWrapper.unwrap(bArr, this.helper.getKeyAlgorithmName(algorithmIdentifier.getAlgorithm()), 3);
            } catch (IllegalStateException | UnsupportedOperationException | GeneralSecurityException | ProviderException | Exception unused) {
            }
            if (this.unwrappedKeyMustBeEncodable) {
                byte[] encoded = unwrap.getEncoded();
                if (encoded != null) {
                }
                if (key == null) {
                    createAsymmetricWrapper.init(2, this.privKey);
                    key = new SecretKeySpec(createAsymmetricWrapper.doFinal(bArr), algorithmIdentifier.getAlgorithm().getId());
                }
                return new JceGenericKey(algorithmIdentifier, key);
            }
            key = unwrap;
            if (key == null) {
            }
            return new JceGenericKey(algorithmIdentifier, key);
        } catch (InvalidKeyException e2) {
            throw new OperatorException("key invalid: " + e2.getMessage(), e2);
        } catch (BadPaddingException e3) {
            throw new OperatorException("bad padding: " + e3.getMessage(), e3);
        } catch (IllegalBlockSizeException e4) {
            throw new OperatorException("illegal blocksize: " + e4.getMessage(), e4);
        }
    }

    public JceAsymmetricKeyUnwrapper setAlgorithmMapping(ASN1ObjectIdentifier aSN1ObjectIdentifier, String str) {
        this.extraMappings.put(aSN1ObjectIdentifier, str);
        return this;
    }

    public JceAsymmetricKeyUnwrapper setMustProduceEncodableUnwrappedKey(boolean z2) {
        this.unwrappedKeyMustBeEncodable = z2;
        return this;
    }

    public JceAsymmetricKeyUnwrapper setProvider(String str) {
        this.helper = new OperatorHelper(new NamedJcaJceHelper(str));
        return this;
    }

    public JceAsymmetricKeyUnwrapper setProvider(Provider provider) {
        this.helper = new OperatorHelper(new ProviderJcaJceHelper(provider));
        return this;
    }
}
