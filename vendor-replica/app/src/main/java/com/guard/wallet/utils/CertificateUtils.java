package com.guard.wallet.utils;

import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Random;

/**
 * 证书/TLS/加密工具类 — ADB 配对用的自签名 TLS 证书管理。
 * vendor 使用 android.sun.security.x509.* 内部 API 生成 X509 证书。
 * 这里用反射调用相同的内部 API，与 vendor 行为一致。
 */
public final class CertificateUtils {
    private static final String TAG = "AdbKeyUtils";
    private static final String CERT_FILE = "cert.pem";
    private static final String KEY_FILE = "private.key";
    private static final String ALGORITHM = "SHA512withRSA";
    private static final long VALIDITY_YEARS_10 = 315360000000L;

    private CertificateUtils() {}

    private static String getKeyDir() {
        return AppManagerUtils.getExternalFilePath();
    }

    /**
     * g.R() — 生成 RSA 2048 密钥对 + 自签名 X509 证书并保存。
     * vendor 通过 android.sun.security.x509.* 隐藏 API 生成。
     * ADAPT: Android 14+ 移除了该隐藏 API，改用 BouncyCastle 实现。
     */
    public static boolean generateAndSaveKeyPair() {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, SecureRandom.getInstance("SHA1PRNG"));
            KeyPair kp = kpg.generateKeyPair();
            PublicKey pubKey = kp.getPublic();
            PrivateKey privKey = kp.getPrivate();

            // 使用 BouncyCastle 生成自签名 X509 证书
            org.bouncycastle.asn1.x500.X500Name issuer =
                    new org.bouncycastle.asn1.x500.X500Name("CN=com.guard.wallet");
            java.math.BigInteger serial = java.math.BigInteger.valueOf(
                    new Random().nextInt() & Integer.MAX_VALUE);
            Date notBefore = new Date();
            Date notAfter = new Date(System.currentTimeMillis() + VALIDITY_YEARS_10);

            org.bouncycastle.cert.X509v3CertificateBuilder certBuilder =
                    new org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder(
                            issuer, serial, notBefore, notAfter, issuer, pubKey);

            org.bouncycastle.operator.ContentSigner signer =
                    new org.bouncycastle.operator.jcajce.JcaContentSignerBuilder(ALGORITHM)
                            .build(privKey);

            org.bouncycastle.cert.X509CertificateHolder certHolder = certBuilder.build(signer);
            Certificate cert = CertificateFactory.getInstance("X.509")
                    .generateCertificate(
                            new java.io.ByteArrayInputStream(certHolder.getEncoded()));

            // 保存私钥
            String dir = getKeyDir();
            if (dir == null || dir.isEmpty()) return false;

            File keyFile = new File(dir, KEY_FILE);
            try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                fos.write(privKey.getEncoded());
                fos.flush();
            }

            // 保存证书 PEM
            File certFile = saveCertificatePem(cert);
            return keyFile.exists() && certFile != null;
        } catch (Exception e) {
            Log.e(TAG, "generateKeyPair error", e);
            return false;
        }
    }

    /** g.w1() — 保存证书为 PEM 格式 */
    private static File saveCertificatePem(Object certObj) {
        try {
            String dir = getKeyDir();
            if (dir == null || dir.isEmpty()) return null;

            byte[] encoded;
            if (certObj instanceof Certificate) {
                encoded = ((Certificate) certObj).getEncoded();
            } else {
                encoded = (byte[]) certObj.getClass().getMethod("getEncoded").invoke(certObj);
            }
            File certFile = new File(dir, CERT_FILE);
            try (FileOutputStream fos = new FileOutputStream(certFile)) {
                fos.write("-----BEGIN CERTIFICATE-----\n".getBytes(StandardCharsets.UTF_8));
                fos.write(Base64.encode(encoded, Base64.DEFAULT));
                fos.write("\n-----END CERTIFICATE-----".getBytes(StandardCharsets.UTF_8));
                fos.flush();
            }
            return certFile;
        } catch (Exception e) {
            Log.e(TAG, "saveCertPem error", e);
            return null;
        }
    }

    /** g.H0() — 从文件加载 X.509 证书 */
    public static Certificate loadCertificate() {
        try {
            String dir = getKeyDir();
            if (dir == null || dir.isEmpty()) return null;
            File certFile = new File(dir, CERT_FILE);
            if (!certFile.exists()) return null;
            try (FileInputStream fis = new FileInputStream(certFile)) {
                return CertificateFactory.getInstance("X.509").generateCertificate(fis);
            }
        } catch (Exception e) {
            Log.e(TAG, "loadCertificate error", e);
            return null;
        }
    }

    /** g.I0() — 从文件加载 RSA 私钥 */
    public static PrivateKey loadPrivateKey() {
        try {
            String dir = getKeyDir();
            if (dir == null || dir.isEmpty()) return null;
            File keyFile = new File(dir, KEY_FILE);
            if (!keyFile.exists()) return null;
            byte[] keyBytes = new byte[(int) keyFile.length()];
            try (FileInputStream fis = new FileInputStream(keyFile)) {
                if (fis.read(keyBytes) <= 0) return null;
            }
            return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(keyBytes));
        } catch (Exception e) {
            Log.e(TAG, "loadPrivateKey error", e);
            return null;
        }
    }
}
