package android.sun.security.x509;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.BitArray;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import com.guard.wallet.entity.BuildConfig;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class X509Key implements PublicKey {
    private static final long serialVersionUID = -5359250853002055002L;
    protected AlgorithmId algid;
    protected byte[] encodedKey;

    @Deprecated
    protected byte[] key = null;
    private int unusedBits = 0;
    private BitArray bitStringKey = null;

    public X509Key() {
    }

    public static PublicKey buildX509Key(AlgorithmId algorithmId, BitArray bitArray) {
        Provider provider;
        Class<?> loadClass;
        DerOutputStream derOutputStream = new DerOutputStream();
        encode(derOutputStream, algorithmId, bitArray);
        try {
            return KeyFactory.getInstance(algorithmId.getName()).generatePublic(new X509EncodedKeySpec(derOutputStream.toByteArray()));
        } catch (NoSuchAlgorithmException unused) {
            try {
                try {
                    provider = Security.getProvider("SUN");
                } catch (ClassNotFoundException | InstantiationException unused2) {
                }
                if (provider == null) {
                    throw new InstantiationException();
                }
                String property = provider.getProperty("PublicKey.X.509." + algorithmId.getName());
                if (property == null) {
                    throw new InstantiationException();
                }
                try {
                    loadClass = Class.forName(property);
                } catch (ClassNotFoundException unused3) {
                    ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
                    loadClass = systemClassLoader != null ? systemClassLoader.loadClass(property) : null;
                }
                Object newInstance = loadClass != null ? loadClass.newInstance() : null;
                if (newInstance instanceof X509Key) {
                    X509Key x509Key = (X509Key) newInstance;
                    x509Key.algid = algorithmId;
                    x509Key.setKey(bitArray);
                    x509Key.parseKeyBits();
                    return x509Key;
                }
                return new X509Key(algorithmId, bitArray);
            } catch (IllegalAccessException unused4) {
                throw new IOException(AbstractC0000a.m30z(BuildConfig.FLAVOR, " [internal error]"));
            }
        } catch (InvalidKeySpecException e2) {
            throw new InvalidKeyException(e2.getMessage(), e2);
        }
    }

    public static PublicKey parse(DerValue derValue) {
        if (derValue.tag != 48) {
            throw new IOException("corrupt subject key");
        }
        try {
            PublicKey buildX509Key = buildX509Key(AlgorithmId.parse(derValue.data.getDerValue()), derValue.data.getUnalignedBitString());
            if (derValue.data.available() == 0) {
                return buildX509Key;
            }
            throw new IOException("excess subject key");
        } catch (InvalidKeyException e2) {
            throw new IOException("subject key, " + e2.getMessage(), e2);
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

    private void writeObject(ObjectOutputStream objectOutputStream) {
        objectOutputStream.write(getEncoded());
    }

    public void decode(InputStream inputStream) {
        try {
            DerValue derValue = new DerValue(inputStream);
            if (derValue.tag != 48) {
                throw new InvalidKeyException("invalid key format");
            }
            this.algid = AlgorithmId.parse(derValue.data.getDerValue());
            setKey(derValue.data.getUnalignedBitString());
            parseKeyBits();
            if (derValue.data.available() != 0) {
                throw new InvalidKeyException("excess key data");
            }
        } catch (IOException e2) {
            throw new InvalidKeyException(AbstractC0000a.m8d(e2, new StringBuilder("IOException: ")));
        }
    }

    public final void encode(DerOutputStream derOutputStream) {
        encode(derOutputStream, this.algid, getKey());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Key)) {
            return false;
        }
        try {
            return Arrays.equals(getEncodedInternal(), obj instanceof X509Key ? ((X509Key) obj).getEncodedInternal() : ((Key) obj).getEncoded());
        } catch (InvalidKeyException unused) {
            return false;
        }
    }

    @Override // java.security.Key
    public String getAlgorithm() {
        return this.algid.getName();
    }

    public AlgorithmId getAlgorithmId() {
        return this.algid;
    }

    @Override // java.security.Key
    public byte[] getEncoded() {
        try {
            return (byte[]) getEncodedInternal().clone();
        } catch (InvalidKeyException unused) {
            return null;
        }
    }

    public byte[] getEncodedInternal() {
        byte[] bArr = this.encodedKey;
        if (bArr == null) {
            try {
                DerOutputStream derOutputStream = new DerOutputStream();
                encode(derOutputStream);
                bArr = derOutputStream.toByteArray();
                this.encodedKey = bArr;
            } catch (IOException e2) {
                throw new InvalidKeyException(AbstractC0000a.m8d(e2, new StringBuilder("IOException : ")));
            }
        }
        return bArr;
    }

    @Override // java.security.Key
    public String getFormat() {
        return "X.509";
    }

    public BitArray getKey() {
        byte[] bArr = this.key;
        BitArray bitArray = new BitArray((bArr.length * 8) - this.unusedBits, bArr);
        this.bitStringKey = bitArray;
        return (BitArray) bitArray.clone();
    }

    public int hashCode() {
        try {
            byte[] encodedInternal = getEncodedInternal();
            int length = encodedInternal.length;
            for (byte b : encodedInternal) {
                length += (b & 255) * 37;
            }
            return length;
        } catch (InvalidKeyException unused) {
            return 0;
        }
    }

    public void parseKeyBits() {
        encode();
    }

    public void setKey(BitArray bitArray) {
        this.bitStringKey = (BitArray) bitArray.clone();
        this.key = bitArray.toByteArray();
        int length = bitArray.length() % 8;
        this.unusedBits = length == 0 ? 0 : 8 - length;
    }

    public String toString() {
        return "algorithm = " + this.algid.toString() + ", unparsed keybits = \n" + new HexDumpEncoder().encodeBuffer(this.key);
    }

    private X509Key(AlgorithmId algorithmId, BitArray bitArray) {
        this.algid = algorithmId;
        setKey(bitArray);
        encode();
    }

    public static void encode(DerOutputStream derOutputStream, AlgorithmId algorithmId, BitArray bitArray) {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        algorithmId.encode(derOutputStream2);
        derOutputStream2.putUnalignedBitString(bitArray);
        derOutputStream.write((byte) 48, derOutputStream2);
    }

    public byte[] encode() {
        return (byte[]) getEncodedInternal().clone();
    }

    public void decode(byte[] bArr) {
        decode(new ByteArrayInputStream(bArr));
    }
}
