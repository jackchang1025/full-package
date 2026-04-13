package android.sun.security.pkcs12;

import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.AlgorithmId;
import java.security.AlgorithmParameters;

/* loaded from: classes.dex */
class MacData {
    private byte[] digest;
    private String digestAlgorithmName;
    private AlgorithmParameters digestAlgorithmParams;
    private byte[] encoded;
    private int iterations;
    private byte[] macSalt;

    public MacData(DerInputStream derInputStream) {
        this.encoded = null;
        DerValue[] sequence = derInputStream.getSequence(2);
        DerValue[] sequence2 = new DerInputStream(sequence[0].toByteArray()).getSequence(2);
        AlgorithmId parse = AlgorithmId.parse(sequence2[0]);
        this.digestAlgorithmName = parse.getName();
        this.digestAlgorithmParams = parse.getParameters();
        this.digest = sequence2[1].getOctetString();
        this.macSalt = sequence[1].getOctetString();
        if (sequence.length > 2) {
            this.iterations = sequence[2].getInteger();
        } else {
            this.iterations = 1;
        }
    }

    public byte[] getDigest() {
        return this.digest;
    }

    public String getDigestAlgName() {
        return this.digestAlgorithmName;
    }

    public byte[] getEncoded() {
        Object clone;
        byte[] bArr = this.encoded;
        if (bArr != null) {
            clone = bArr.clone();
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            DerOutputStream derOutputStream2 = new DerOutputStream();
            DerOutputStream derOutputStream3 = new DerOutputStream();
            AlgorithmId.get(this.digestAlgorithmName).encode(derOutputStream3);
            derOutputStream3.putOctetString(this.digest);
            derOutputStream2.write((byte) 48, derOutputStream3);
            derOutputStream2.putOctetString(this.macSalt);
            derOutputStream2.putInteger(this.iterations);
            derOutputStream.write((byte) 48, derOutputStream2);
            byte[] byteArray = derOutputStream.toByteArray();
            this.encoded = byteArray;
            clone = byteArray.clone();
        }
        return (byte[]) clone;
    }

    public int getIterations() {
        return this.iterations;
    }

    public byte[] getSalt() {
        return this.macSalt;
    }

    public MacData(String str, byte[] bArr, byte[] bArr2, int i2) {
        this.encoded = null;
        if (str == null) {
            throw new NullPointerException("the algName parameter must be non-null");
        }
        AlgorithmId algorithmId = AlgorithmId.get(str);
        this.digestAlgorithmName = algorithmId.getName();
        this.digestAlgorithmParams = algorithmId.getParameters();
        if (bArr == null) {
            throw new NullPointerException("the digest parameter must be non-null");
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("the digest parameter must not be empty");
        }
        this.digest = (byte[]) bArr.clone();
        this.macSalt = bArr2;
        this.iterations = i2;
        this.encoded = null;
    }

    public MacData(AlgorithmParameters algorithmParameters, byte[] bArr, byte[] bArr2, int i2) {
        this.encoded = null;
        if (algorithmParameters == null) {
            throw new NullPointerException("the algParams parameter must be non-null");
        }
        AlgorithmId algorithmId = AlgorithmId.get(algorithmParameters);
        this.digestAlgorithmName = algorithmId.getName();
        this.digestAlgorithmParams = algorithmId.getParameters();
        if (bArr == null) {
            throw new NullPointerException("the digest parameter must be non-null");
        }
        if (bArr.length == 0) {
            throw new IllegalArgumentException("the digest parameter must not be empty");
        }
        this.digest = (byte[]) bArr.clone();
        this.macSalt = bArr2;
        this.iterations = i2;
        this.encoded = null;
    }
}
