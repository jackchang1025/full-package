package org.bouncycastle.jcajce.util;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.cert.CertPathBuilder;
import java.security.cert.CertPathValidator;
import java.security.cert.CertStore;
import java.security.cert.CertStoreParameters;
import java.security.cert.CertificateFactory;
import javax.crypto.Cipher;
import javax.crypto.ExemptionMechanism;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;

/* loaded from: classes.dex */
public interface JcaJceHelper {
    AlgorithmParameterGenerator createAlgorithmParameterGenerator(String str);

    AlgorithmParameters createAlgorithmParameters(String str);

    CertPathBuilder createCertPathBuilder(String str);

    CertPathValidator createCertPathValidator(String str);

    CertStore createCertStore(String str, CertStoreParameters certStoreParameters);

    CertificateFactory createCertificateFactory(String str);

    Cipher createCipher(String str);

    MessageDigest createDigest(String str);

    ExemptionMechanism createExemptionMechanism(String str);

    KeyAgreement createKeyAgreement(String str);

    KeyFactory createKeyFactory(String str);

    KeyGenerator createKeyGenerator(String str);

    KeyPairGenerator createKeyPairGenerator(String str);

    KeyStore createKeyStore(String str);

    Mac createMac(String str);

    MessageDigest createMessageDigest(String str);

    SecretKeyFactory createSecretKeyFactory(String str);

    SecureRandom createSecureRandom(String str);

    Signature createSignature(String str);
}
