package android.sun.security.provider;

import android.sun.misc.IOUtils;
import android.sun.security.pkcs.EncryptedPrivateKeyInfo;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.DigestInputStream;
import java.security.DigestOutputStream;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/* loaded from: classes.dex */
abstract class JavaKeyStore extends KeyStoreSpi {
    private static final int MAGIC = -17957139;
    private static final int VERSION_1 = 1;
    private static final int VERSION_2 = 2;
    private final Hashtable<String, Object> entries = new Hashtable<>();

    public static final class CaseExactJKS extends JavaKeyStore {
        @Override // android.sun.security.provider.JavaKeyStore
        public String convertAlias(String str) {
            return str;
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Enumeration engineAliases() {
            return super.engineAliases();
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineContainsAlias(String str) {
            return super.engineContainsAlias(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineDeleteEntry(String str) {
            super.engineDeleteEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Certificate engineGetCertificate(String str) {
            return super.engineGetCertificate(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ String engineGetCertificateAlias(Certificate certificate) {
            return super.engineGetCertificateAlias(certificate);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Certificate[] engineGetCertificateChain(String str) {
            return super.engineGetCertificateChain(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Date engineGetCreationDate(String str) {
            return super.engineGetCreationDate(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Key engineGetKey(String str, char[] cArr) {
            return super.engineGetKey(str, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineIsCertificateEntry(String str) {
            return super.engineIsCertificateEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineIsKeyEntry(String str) {
            return super.engineIsKeyEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineLoad(InputStream inputStream, char[] cArr) {
            super.engineLoad(inputStream, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetCertificateEntry(String str, Certificate certificate) {
            super.engineSetCertificateEntry(str, certificate);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            super.engineSetKeyEntry(str, key, cArr, certificateArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ int engineSize() {
            return super.engineSize();
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineStore(OutputStream outputStream, char[] cArr) {
            super.engineStore(outputStream, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            super.engineSetKeyEntry(str, bArr, certificateArr);
        }
    }

    public static final class JKS extends JavaKeyStore {
        @Override // android.sun.security.provider.JavaKeyStore
        public String convertAlias(String str) {
            return str.toLowerCase();
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Enumeration engineAliases() {
            return super.engineAliases();
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineContainsAlias(String str) {
            return super.engineContainsAlias(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineDeleteEntry(String str) {
            super.engineDeleteEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Certificate engineGetCertificate(String str) {
            return super.engineGetCertificate(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ String engineGetCertificateAlias(Certificate certificate) {
            return super.engineGetCertificateAlias(certificate);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Certificate[] engineGetCertificateChain(String str) {
            return super.engineGetCertificateChain(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Date engineGetCreationDate(String str) {
            return super.engineGetCreationDate(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ Key engineGetKey(String str, char[] cArr) {
            return super.engineGetKey(str, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineIsCertificateEntry(String str) {
            return super.engineIsCertificateEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ boolean engineIsKeyEntry(String str) {
            return super.engineIsKeyEntry(str);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineLoad(InputStream inputStream, char[] cArr) {
            super.engineLoad(inputStream, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetCertificateEntry(String str, Certificate certificate) {
            super.engineSetCertificateEntry(str, certificate);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
            super.engineSetKeyEntry(str, key, cArr, certificateArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ int engineSize() {
            return super.engineSize();
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineStore(OutputStream outputStream, char[] cArr) {
            super.engineStore(outputStream, cArr);
        }

        @Override // android.sun.security.provider.JavaKeyStore, java.security.KeyStoreSpi
        public /* bridge */ /* synthetic */ void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
            super.engineSetKeyEntry(str, bArr, certificateArr);
        }
    }

    public static class KeyEntry {
        Certificate[] chain;
        Date date;
        byte[] protectedPrivKey;

        private KeyEntry() {
        }
    }

    public static class TrustedCertEntry {
        Certificate cert;
        Date date;

        private TrustedCertEntry() {
        }
    }

    private MessageDigest getPreKeyedHash(char[] cArr) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        int length = cArr.length * 2;
        byte[] bArr = new byte[length];
        int i2 = 0;
        for (char c : cArr) {
            int i3 = i2 + 1;
            bArr[i2] = (byte) (c >> '\b');
            i2 = i3 + 1;
            bArr[i3] = (byte) c;
        }
        messageDigest.update(bArr);
        for (int i4 = 0; i4 < length; i4++) {
            bArr[i4] = 0;
        }
        messageDigest.update("Mighty Aphrodite".getBytes(StandardCharsets.UTF_8));
        return messageDigest;
    }

    public abstract String convertAlias(String str);

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        return this.entries.keys();
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        return this.entries.containsKey(convertAlias(str));
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) {
        synchronized (this.entries) {
            this.entries.remove(convertAlias(str));
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        Object obj = this.entries.get(convertAlias(str));
        if (obj == null) {
            return null;
        }
        if (obj instanceof TrustedCertEntry) {
            return ((TrustedCertEntry) obj).cert;
        }
        Certificate[] certificateArr = ((KeyEntry) obj).chain;
        if (certificateArr == null) {
            return null;
        }
        return certificateArr[0];
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        Certificate certificate2;
        Certificate[] certificateArr;
        Enumeration<String> keys = this.entries.keys();
        while (keys.hasMoreElements()) {
            String nextElement = keys.nextElement();
            Object obj = this.entries.get(nextElement);
            if (obj instanceof TrustedCertEntry) {
                certificate2 = ((TrustedCertEntry) obj).cert;
            } else if (obj != null && (certificateArr = ((KeyEntry) obj).chain) != null) {
                certificate2 = certificateArr[0];
            }
            if (certificate2.equals(certificate)) {
                return nextElement;
            }
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        Certificate[] certificateArr;
        Object obj = this.entries.get(convertAlias(str));
        if (!(obj instanceof KeyEntry) || (certificateArr = ((KeyEntry) obj).chain) == null) {
            return null;
        }
        return (Certificate[]) certificateArr.clone();
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        Object obj = this.entries.get(convertAlias(str));
        if (obj != null) {
            return obj instanceof TrustedCertEntry ? new Date(((TrustedCertEntry) obj).date.getTime()) : new Date(((KeyEntry) obj).date.getTime());
        }
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) {
        Object obj = this.entries.get(convertAlias(str));
        if (!(obj instanceof KeyEntry)) {
            return null;
        }
        if (cArr == null) {
            throw new UnrecoverableKeyException("Password must not be null");
        }
        try {
            return new KeyProtector(cArr).recover(new EncryptedPrivateKeyInfo(((KeyEntry) obj).protectedPrivKey));
        } catch (IOException unused) {
            throw new UnrecoverableKeyException("Private key not stored as PKCS #8 EncryptedPrivateKeyInfo");
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        return this.entries.get(convertAlias(str)) instanceof TrustedCertEntry;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return this.entries.get(convertAlias(str)) instanceof KeyEntry;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) {
        DataInputStream dataInputStream;
        MessageDigest messageDigest;
        Hashtable hashtable;
        CertificateFactory certificateFactory;
        MessageDigest messageDigest2;
        int i2;
        boolean z2;
        synchronized (this.entries) {
            try {
                if (inputStream == null) {
                    return;
                }
                C00401 c00401 = null;
                if (cArr != null) {
                    messageDigest = getPreKeyedHash(cArr);
                    dataInputStream = new DataInputStream(new DigestInputStream(inputStream, messageDigest));
                } else {
                    dataInputStream = new DataInputStream(inputStream);
                    messageDigest = null;
                }
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                if (readInt == MAGIC) {
                    int i3 = 2;
                    boolean z3 = true;
                    if (readInt2 == 1 || readInt2 == 2) {
                        if (readInt2 == 1) {
                            certificateFactory = CertificateFactory.getInstance("X509");
                            hashtable = null;
                        } else {
                            hashtable = new Hashtable(3);
                            certificateFactory = null;
                        }
                        this.entries.clear();
                        int readInt3 = dataInputStream.readInt();
                        int i4 = 0;
                        while (i4 < readInt3) {
                            int readInt4 = dataInputStream.readInt();
                            if (readInt4 == z3) {
                                KeyEntry keyEntry = new KeyEntry();
                                String readUTF = dataInputStream.readUTF();
                                messageDigest2 = messageDigest;
                                keyEntry.date = new Date(dataInputStream.readLong());
                                keyEntry.protectedPrivKey = IOUtils.readFully(dataInputStream, dataInputStream.readInt(), z3);
                                int readInt5 = dataInputStream.readInt();
                                if (readInt5 > 0) {
                                    ArrayList arrayList = new ArrayList(Math.min(readInt5, 10));
                                    int i5 = 0;
                                    while (i5 < readInt5) {
                                        if (readInt2 == i3) {
                                            String readUTF2 = dataInputStream.readUTF();
                                            if (hashtable.containsKey(readUTF2)) {
                                                certificateFactory = (CertificateFactory) hashtable.get(readUTF2);
                                            } else {
                                                CertificateFactory certificateFactory2 = CertificateFactory.getInstance(readUTF2);
                                                hashtable.put(readUTF2, certificateFactory2);
                                                certificateFactory = certificateFactory2;
                                            }
                                        }
                                        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(IOUtils.readFully(dataInputStream, dataInputStream.readInt(), z3));
                                        arrayList.add(certificateFactory.generateCertificate(byteArrayInputStream));
                                        byteArrayInputStream.close();
                                        i5++;
                                        i3 = 2;
                                        z3 = true;
                                    }
                                    keyEntry.chain = (Certificate[]) arrayList.toArray(new Certificate[readInt5]);
                                }
                                this.entries.put(readUTF, keyEntry);
                                c00401 = null;
                                i2 = 2;
                                z2 = true;
                            } else {
                                messageDigest2 = messageDigest;
                                if (readInt4 != i3) {
                                    throw new IOException("Unrecognized keystore entry");
                                }
                                c00401 = null;
                                TrustedCertEntry trustedCertEntry = new TrustedCertEntry();
                                String readUTF3 = dataInputStream.readUTF();
                                trustedCertEntry.date = new Date(dataInputStream.readLong());
                                i2 = 2;
                                if (readInt2 == 2) {
                                    String readUTF4 = dataInputStream.readUTF();
                                    if (hashtable.containsKey(readUTF4)) {
                                        certificateFactory = (CertificateFactory) hashtable.get(readUTF4);
                                    } else {
                                        CertificateFactory certificateFactory3 = CertificateFactory.getInstance(readUTF4);
                                        hashtable.put(readUTF4, certificateFactory3);
                                        certificateFactory = certificateFactory3;
                                    }
                                }
                                z2 = true;
                                ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(IOUtils.readFully(dataInputStream, dataInputStream.readInt(), true));
                                trustedCertEntry.cert = certificateFactory.generateCertificate(byteArrayInputStream2);
                                byteArrayInputStream2.close();
                                this.entries.put(readUTF3, trustedCertEntry);
                            }
                            i4++;
                            i3 = i2;
                            z3 = z2;
                            messageDigest = messageDigest2;
                        }
                        MessageDigest messageDigest3 = messageDigest;
                        if (cArr != null) {
                            byte[] digest = messageDigest3.digest();
                            byte[] bArr = new byte[digest.length];
                            dataInputStream.readFully(bArr);
                            for (int i6 = 0; i6 < digest.length; i6++) {
                                if (digest[i6] != bArr[i6]) {
                                    throw ((IOException) new IOException("Keystore was tampered with, or password was incorrect").initCause(new UnrecoverableKeyException("Password verification failed")));
                                }
                            }
                        }
                        return;
                    }
                }
                throw new IOException("Invalid keystore format");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) {
        synchronized (this.entries) {
            if (this.entries.get(convertAlias(str)) instanceof KeyEntry) {
                throw new KeyStoreException("Cannot overwrite own certificate");
            }
            TrustedCertEntry trustedCertEntry = new TrustedCertEntry();
            trustedCertEntry.cert = certificate;
            trustedCertEntry.date = new Date();
            this.entries.put(convertAlias(str), trustedCertEntry);
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
        if (!(key instanceof PrivateKey)) {
            throw new KeyStoreException("Cannot store non-PrivateKeys");
        }
        try {
            synchronized (this.entries) {
                KeyEntry keyEntry = new KeyEntry();
                keyEntry.date = new Date();
                keyEntry.protectedPrivKey = new KeyProtector(cArr).protect(key);
                if (certificateArr == null || certificateArr.length == 0) {
                    keyEntry.chain = null;
                } else {
                    keyEntry.chain = (Certificate[]) certificateArr.clone();
                }
                this.entries.put(convertAlias(str), keyEntry);
            }
        } catch (NoSuchAlgorithmException unused) {
            throw new KeyStoreException("Key protection algorithm not found");
        }
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.entries.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) {
        synchronized (this.entries) {
            try {
                if (cArr == null) {
                    throw new IllegalArgumentException("password can't be null");
                }
                MessageDigest preKeyedHash = getPreKeyedHash(cArr);
                DataOutputStream dataOutputStream = new DataOutputStream(new DigestOutputStream(outputStream, preKeyedHash));
                dataOutputStream.writeInt(MAGIC);
                dataOutputStream.writeInt(2);
                dataOutputStream.writeInt(this.entries.size());
                Enumeration<String> keys = this.entries.keys();
                while (keys.hasMoreElements()) {
                    String nextElement = keys.nextElement();
                    Object obj = this.entries.get(nextElement);
                    if (obj instanceof KeyEntry) {
                        dataOutputStream.writeInt(1);
                        dataOutputStream.writeUTF(nextElement);
                        dataOutputStream.writeLong(((KeyEntry) obj).date.getTime());
                        dataOutputStream.writeInt(((KeyEntry) obj).protectedPrivKey.length);
                        dataOutputStream.write(((KeyEntry) obj).protectedPrivKey);
                        int length = ((KeyEntry) obj).chain == null ? 0 : ((KeyEntry) obj).chain.length;
                        dataOutputStream.writeInt(length);
                        for (int i2 = 0; i2 < length; i2++) {
                            byte[] encoded = ((KeyEntry) obj).chain[i2].getEncoded();
                            dataOutputStream.writeUTF(((KeyEntry) obj).chain[i2].getType());
                            dataOutputStream.writeInt(encoded.length);
                            dataOutputStream.write(encoded);
                        }
                    } else {
                        dataOutputStream.writeInt(2);
                        dataOutputStream.writeUTF(nextElement);
                        dataOutputStream.writeLong(((TrustedCertEntry) obj).date.getTime());
                        byte[] encoded2 = ((TrustedCertEntry) obj).cert.getEncoded();
                        dataOutputStream.writeUTF(((TrustedCertEntry) obj).cert.getType());
                        dataOutputStream.writeInt(encoded2.length);
                        dataOutputStream.write(encoded2);
                    }
                }
                dataOutputStream.write(preKeyedHash.digest());
                dataOutputStream.flush();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
        synchronized (this.entries) {
            try {
                try {
                    new EncryptedPrivateKeyInfo(bArr);
                    KeyEntry keyEntry = new KeyEntry();
                    keyEntry.date = new Date();
                    keyEntry.protectedPrivKey = (byte[]) bArr.clone();
                    if (certificateArr == null || certificateArr.length == 0) {
                        keyEntry.chain = null;
                    } else {
                        keyEntry.chain = (Certificate[]) certificateArr.clone();
                    }
                    this.entries.put(convertAlias(str), keyEntry);
                } catch (IOException unused) {
                    throw new KeyStoreException("key is not encoded as EncryptedPrivateKeyInfo");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
