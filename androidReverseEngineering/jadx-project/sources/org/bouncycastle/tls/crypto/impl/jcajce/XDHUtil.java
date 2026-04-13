package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.interfaces.XDHPublicKey;
import org.bouncycastle.jcajce.spec.RawEncodedKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.tls.TlsFatalAlert;

/* loaded from: classes.dex */
class XDHUtil {
    private static X509EncodedKeySpec createX509EncodedKeySpec(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        return new X509EncodedKeySpec(new SubjectPublicKeyInfo(new AlgorithmIdentifier(aSN1ObjectIdentifier), bArr).getEncoded(ASN1Encoding.DER));
    }

    public static PublicKey decodePublicKey(JcaTlsCrypto jcaTlsCrypto, String str, ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr) {
        try {
            KeyFactory createKeyFactory = jcaTlsCrypto.getHelper().createKeyFactory(str);
            if (createKeyFactory.getProvider() instanceof BouncyCastleProvider) {
                try {
                    return createKeyFactory.generatePublic(new RawEncodedKeySpec(bArr));
                } catch (Exception unused) {
                }
            }
            return createKeyFactory.generatePublic(createX509EncodedKeySpec(aSN1ObjectIdentifier, bArr));
        } catch (Exception e2) {
            throw new TlsFatalAlert((short) 47, (Throwable) e2);
        }
    }

    public static byte[] encodePublicKey(PublicKey publicKey) {
        if (publicKey instanceof XDHPublicKey) {
            return ((XDHPublicKey) publicKey).getUEncoding();
        }
        if (!"X.509".equals(publicKey.getFormat())) {
            throw new TlsFatalAlert((short) 80, "Public key format unrecognized");
        }
        try {
            return SubjectPublicKeyInfo.getInstance(publicKey.getEncoded()).getPublicKeyData().getOctets();
        } catch (Exception e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
