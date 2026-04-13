package android.sun.security.pkcs;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.Debug;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.x509.AlgorithmId;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyRep;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKCS8Key implements PrivateKey {
    private static final long serialVersionUID = -3836890099307167124L;
    public static final BigInteger version = BigInteger.ZERO;
    protected AlgorithmId algid;
    protected byte[] encodedKey;
    protected byte[] key;

    public PKCS8Key() {
    }

    private PKCS8Key(AlgorithmId algorithmId, byte[] bArr) {
        this.algid = algorithmId;
        this.key = bArr;
        encode();
    }

    public static PrivateKey buildPKCS8Key(AlgorithmId algorithmId, byte[] bArr) {
        Provider provider;
        Class<?> loadClass;
        DerOutputStream derOutputStream = new DerOutputStream();
        encode(derOutputStream, algorithmId, bArr);
        try {
            return KeyFactory.getInstance(algorithmId.getName()).generatePrivate(new PKCS8EncodedKeySpec(derOutputStream.toByteArray()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException unused) {
            try {
                try {
                    provider = Security.getProvider("SUN");
                } catch (IllegalAccessException unused2) {
                    throw new IOException(AbstractC0000a.m30z(BuildConfig.FLAVOR, " [internal error]"));
                }
            } catch (ClassNotFoundException | InstantiationException unused3) {
            }
            if (provider == null) {
                throw new InstantiationException();
            }
            String property = provider.getProperty("PrivateKey.PKCS#8." + algorithmId.getName());
            if (property == null) {
                throw new InstantiationException();
            }
            try {
                loadClass = Class.forName(property);
            } catch (ClassNotFoundException unused4) {
                ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                loadClass = systemClassLoader != null ? systemClassLoader.loadClass(property) : null;
            }
            Object newInstance = loadClass != null ? loadClass.newInstance() : null;
            if (newInstance instanceof PKCS8Key) {
                PKCS8Key pKCS8Key = (PKCS8Key) newInstance;
                pKCS8Key.algid = algorithmId;
                pKCS8Key.key = bArr;
                pKCS8Key.parseKeyBits();
                return pKCS8Key;
            }
            PKCS8Key pKCS8Key2 = new PKCS8Key();
            pKCS8Key2.algid = algorithmId;
            pKCS8Key2.key = bArr;
            return pKCS8Key2;
        }
    }

    public static PKCS8Key parse(DerValue derValue) {
        PrivateKey parseKey = parseKey(derValue);
        if (parseKey instanceof PKCS8Key) {
            return (PKCS8Key) parseKey;
        }
        throw new IOException("Provider did not return PKCS8Key");
    }

    public static PrivateKey parseKey(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("corrupt private key");
        }
        BigInteger bigInteger = derValue.data.getBigInteger();
        BigInteger bigInteger2 = version;
        if (!bigInteger2.equals(bigInteger)) {
            throw new IOException("version mismatch: (supported: " + Debug.toHexString(bigInteger2) + ", parsed: " + Debug.toHexString(bigInteger));
        }
        try {
            PrivateKey buildPKCS8Key = buildPKCS8Key(AlgorithmId.parse(derValue.data.getDerValue()), derValue.data.getOctetString());
            if (derValue.data.available() == 0) {
                return buildPKCS8Key;
            }
            throw new IOException("excess private key");
        } catch (InvalidKeyException unused) {
            throw new IOException("corrupt private key");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) {
        try {
            decode(objectInputStream);
        } catch (InvalidKeyException e2) {
            e2.printStackTrace();
            throw new IOException("deserialized key is invalid: " + e2.getMessage());
        }
    }

    public void decode(InputStream inputStream) {
        try {
            DerValue derValue = new DerValue(inputStream);
            if (derValue.tag != 48) {
                throw new InvalidKeyException("invalid key format");
            }
            BigInteger bigInteger = derValue.data.getBigInteger();
            BigInteger bigInteger2 = version;
            if (bigInteger.equals(bigInteger2)) {
                this.algid = AlgorithmId.parse(derValue.data.getDerValue());
                this.key = derValue.data.getOctetString();
                parseKeyBits();
                derValue.data.available();
                return;
            }
            throw new IOException("version mismatch: (supported: " + Debug.toHexString(bigInteger2) + ", parsed: " + Debug.toHexString(bigInteger));
        } catch (IOException e2) {
            throw new InvalidKeyException(AbstractC0000a.m8d(e2, new StringBuilder("IOException : ")));
        }
    }

    public final void encode(DerOutputStream derOutputStream) {
        encode(derOutputStream, this.algid, this.key);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Key)) {
            return false;
        }
        byte[] bArr = this.encodedKey;
        if (bArr == null) {
            bArr = getEncoded();
        }
        byte[] encoded = ((Key) obj).getEncoded();
        if (bArr.length != encoded.length) {
            return false;
        }
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] != encoded[i2]) {
                return false;
            }
        }
        return true;
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algid.getName();
    }

    public AlgorithmId getAlgorithmId() {
        return this.algid;
    }

    @Override // java.security.Key
    public synchronized byte[] getEncoded() {
        byte[] bArr;
        try {
            bArr = encode();
        } catch (InvalidKeyException unused) {
            bArr = null;
        }
        return bArr;
    }

    @Override // java.security.Key
    public String getFormat() {
        return "PKCS#8";
    }

    public int hashCode() {
        byte[] encoded = getEncoded();
        int i2 = 0;
        for (int i3 = 1; i3 < encoded.length; i3++) {
            i2 += encoded[i3] * i3;
        }
        return i2;
    }

    public void parseKeyBits() {
        encode();
    }

    public String toString() {
        return "algorithm = " + this.algid.toString() + ", unparsed keybits = \n" + new HexDumpEncoder().encodeBuffer(this.key);
    }

    public Object writeReplace() {
        return new KeyRep(KeyRep.Type.PRIVATE, getAlgorithm(), getFormat(), getEncoded());
    }

    public static void encode(DerOutputStream derOutputStream, AlgorithmId algorithmId, byte[] bArr) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.putInteger(version);
        algorithmId.encode(derOutputStream2);
        derOutputStream2.putOctetString(bArr);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public byte[] encode() {
        if (this.encodedKey == null) {
            try {
                DerOutputStream derOutputStream = new DerOutputStream();
                encode(derOutputStream);
                this.encodedKey = derOutputStream.toByteArray();
            } catch (IOException e2) {
                throw new InvalidKeyException(AbstractC0000a.m8d(e2, new StringBuilder("IOException : ")));
            }
        }
        return (byte[]) this.encodedKey.clone();
    }

    public void decode(byte[] bArr) {
        decode(new ByteArrayInputStream(bArr));
    }
}
