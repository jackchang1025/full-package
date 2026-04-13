package android.sun.security.pkcs;

import android.sun.misc.BASE64Encoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.AlgorithmId;
import android.sun.security.x509.X500Name;
import android.sun.security.x509.X509Key;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

/* loaded from: classes.dex */
public class PKCS10 {
    private PKCS10Attributes attributeSet;
    private byte[] encoded;
    private X500Name subject;
    private PublicKey subjectPublicKeyInfo;

    public PKCS10(PublicKey publicKey) {
        this.subjectPublicKeyInfo = publicKey;
        this.attributeSet = new PKCS10Attributes();
    }

    public void encodeAndSign(X500Name x500Name, Signature signature) {
        if (this.encoded != null) {
            throw new SignatureException("request is already signed");
        }
        this.subject = x500Name;
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(BigInteger.ZERO);
        x500Name.encode(derOutputStream);
        derOutputStream.write(this.subjectPublicKeyInfo.getEncoded());
        this.attributeSet.encode(derOutputStream);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write((byte) 48, derOutputStream);
        byte[] byteArray = derOutputStream2.toByteArray();
        signature.update(byteArray, 0, byteArray.length);
        byte[] sign = signature.sign();
        try {
            AlgorithmId.getAlgorithmId(signature.getAlgorithm()).encode(derOutputStream2);
            derOutputStream2.putBitString(sign);
            DerOutputStream derOutputStream3 = new DerOutputStream();
            derOutputStream3.write((byte) 48, derOutputStream2);
            this.encoded = derOutputStream3.toByteArray();
        } catch (NoSuchAlgorithmException e2) {
            throw new SignatureException(e2);
        }
    }

    public boolean equals(Object obj) {
        byte[] encoded;
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PKCS10) || this.encoded == null || (encoded = ((PKCS10) obj).getEncoded()) == null) {
            return false;
        }
        return Arrays.equals(this.encoded, encoded);
    }

    public PKCS10Attributes getAttributes() {
        return this.attributeSet;
    }

    public byte[] getEncoded() {
        byte[] bArr = this.encoded;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    public X500Name getSubjectName() {
        return this.subject;
    }

    public PublicKey getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public int hashCode() {
        int i2 = 0;
        if (this.encoded != null) {
            int i3 = 1;
            while (true) {
                byte[] bArr = this.encoded;
                if (i3 >= bArr.length) {
                    break;
                }
                i2 += bArr[i3] * i3;
                i3++;
            }
        }
        return i2;
    }

    public void print(PrintStream printStream) {
        if (this.encoded == null) {
            throw new SignatureException("Cert request was not signed");
        }
        BASE64Encoder bASE64Encoder = new BASE64Encoder();
        printStream.println("-----BEGIN NEW CERTIFICATE REQUEST-----");
        bASE64Encoder.encodeBuffer(this.encoded, printStream);
        printStream.println("-----END NEW CERTIFICATE REQUEST-----");
    }

    public String toString() {
        return "[PKCS #10 certificate request:\n" + this.subjectPublicKeyInfo.toString() + " subject: <" + this.subject + ">\n attributes: " + this.attributeSet.toString() + "\n]";
    }

    public PKCS10(PublicKey publicKey, PKCS10Attributes pKCS10Attributes) {
        this.subjectPublicKeyInfo = publicKey;
        this.attributeSet = pKCS10Attributes;
    }

    public PKCS10(byte[] bArr) {
        this.encoded = bArr;
        DerValue[] sequence = new DerInputStream(bArr).getSequence(3);
        if (sequence.length != 3) {
            throw new IllegalArgumentException("not a PKCS #10 request");
        }
        byte[] byteArray = sequence[0].toByteArray();
        AlgorithmId parse = AlgorithmId.parse(sequence[1]);
        byte[] bitString = sequence[2].getBitString();
        if (!sequence[0].data.getBigInteger().equals(BigInteger.ZERO)) {
            throw new IllegalArgumentException("not PKCS #10 v1");
        }
        this.subject = new X500Name(sequence[0].data);
        this.subjectPublicKeyInfo = X509Key.parse(sequence[0].data.getDerValue());
        this.attributeSet = sequence[0].data.available() != 0 ? new PKCS10Attributes(sequence[0].data) : new PKCS10Attributes();
        if (sequence[0].data.available() != 0) {
            throw new IllegalArgumentException("illegal PKCS #10 data");
        }
        try {
            Signature signature = Signature.getInstance(parse.getName());
            signature.initVerify(this.subjectPublicKeyInfo);
            signature.update(byteArray);
            if (signature.verify(bitString)) {
            } else {
                throw new SignatureException("Invalid PKCS #10 signature");
            }
        } catch (InvalidKeyException unused) {
            throw new SignatureException("invalid key");
        }
    }
}
