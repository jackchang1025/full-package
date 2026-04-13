package org.bouncycastle.jcajce.provider.keystore.util;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchProviderException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.jcajce.BCLoadStoreParameter;
import org.bouncycastle.jcajce.provider.util.DigestFactory;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.pqc.jcajce.spec.McElieceCCA2KeyGenParameterSpec;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.Streams;

/* loaded from: classes.dex */
public class JKSKeyStoreSpi extends KeyStoreSpi {
    private static final String NOT_IMPLEMENTED_MESSAGE = "BC JKS store is read-only and only supports certificate entries";
    private final Hashtable<String, BCJKSTrustedCertEntry> certificateEntries = new Hashtable<>();
    private final JcaJceHelper helper;

    public static final class BCJKSTrustedCertEntry {
        final Certificate cert;
        final Date date;

        public BCJKSTrustedCertEntry(Date date, Certificate certificate) {
            this.date = date;
            this.cert = certificate;
        }
    }

    public static final class ErasableByteStream extends ByteArrayInputStream {
        public ErasableByteStream(byte[] bArr, int i2, int i3) {
            super(bArr, i2, i3);
        }

        public void erase() {
            Arrays.fill(((ByteArrayInputStream) this).buf, (byte) 0);
        }
    }

    public JKSKeyStoreSpi(JcaJceHelper jcaJceHelper) {
        this.helper = jcaJceHelper;
    }

    private void addPassword(Digest digest, char[] cArr) {
        for (int i2 = 0; i2 < cArr.length; i2++) {
            digest.update((byte) (cArr[i2] >> '\b'));
            digest.update((byte) cArr[i2]);
        }
        digest.update(Strings.toByteArray("Mighty Aphrodite"), 0, 16);
    }

    private CertificateFactory createCertFactory(String str) {
        JcaJceHelper jcaJceHelper = this.helper;
        if (jcaJceHelper == null) {
            return CertificateFactory.getInstance(str);
        }
        try {
            return jcaJceHelper.createCertificateFactory(str);
        } catch (NoSuchProviderException e2) {
            throw new CertificateException(e2.toString());
        }
    }

    private ErasableByteStream validateStream(InputStream inputStream, char[] cArr) {
        Digest digest = DigestFactory.getDigest(McElieceCCA2KeyGenParameterSpec.SHA1);
        byte[] readAll = Streams.readAll(inputStream);
        if (cArr == null) {
            return new ErasableByteStream(readAll, 0, readAll.length - digest.getDigestSize());
        }
        addPassword(digest, cArr);
        digest.update(readAll, 0, readAll.length - digest.getDigestSize());
        int digestSize = digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        digest.doFinal(bArr, 0);
        byte[] bArr2 = new byte[digestSize];
        System.arraycopy(readAll, readAll.length - digestSize, bArr2, 0, digestSize);
        if (Arrays.constantTimeAreEqual(bArr, bArr2)) {
            return new ErasableByteStream(readAll, 0, readAll.length - digestSize);
        }
        Arrays.fill(readAll, (byte) 0);
        throw new IOException("password incorrect or store tampered with");
    }

    @Override // java.security.KeyStoreSpi
    public Enumeration<String> engineAliases() {
        Enumeration<String> keys;
        synchronized (this.certificateEntries) {
            keys = this.certificateEntries.keys();
        }
        return keys;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineContainsAlias(String str) {
        boolean containsKey;
        if (str == null) {
            throw new NullPointerException("alias value is null");
        }
        synchronized (this.certificateEntries) {
            containsKey = this.certificateEntries.containsKey(str);
        }
        return containsKey;
    }

    @Override // java.security.KeyStoreSpi
    public void engineDeleteEntry(String str) {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public Certificate engineGetCertificate(String str) {
        synchronized (this.certificateEntries) {
            BCJKSTrustedCertEntry bCJKSTrustedCertEntry = this.certificateEntries.get(str);
            if (bCJKSTrustedCertEntry == null) {
                return null;
            }
            return bCJKSTrustedCertEntry.cert;
        }
    }

    @Override // java.security.KeyStoreSpi
    public String engineGetCertificateAlias(Certificate certificate) {
        synchronized (this.certificateEntries) {
            for (Map.Entry<String, BCJKSTrustedCertEntry> entry : this.certificateEntries.entrySet()) {
                if (entry.getValue().cert.equals(certificate)) {
                    return entry.getKey();
                }
            }
            return null;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Certificate[] engineGetCertificateChain(String str) {
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public Date engineGetCreationDate(String str) {
        synchronized (this.certificateEntries) {
            BCJKSTrustedCertEntry bCJKSTrustedCertEntry = this.certificateEntries.get(str);
            if (bCJKSTrustedCertEntry == null) {
                return null;
            }
            return bCJKSTrustedCertEntry.date;
        }
    }

    @Override // java.security.KeyStoreSpi
    public Key engineGetKey(String str, char[] cArr) {
        return null;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsCertificateEntry(String str) {
        boolean containsKey;
        synchronized (this.certificateEntries) {
            containsKey = this.certificateEntries.containsKey(str);
        }
        return containsKey;
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineIsKeyEntry(String str) {
        return false;
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(InputStream inputStream, char[] cArr) {
        Hashtable hashtable;
        if (inputStream == null) {
            return;
        }
        ErasableByteStream validateStream = validateStream(inputStream, cArr);
        synchronized (this.certificateEntries) {
            try {
                DataInputStream dataInputStream = new DataInputStream(validateStream);
                int readInt = dataInputStream.readInt();
                int readInt2 = dataInputStream.readInt();
                if (readInt == -17957139) {
                    CertificateFactory certificateFactory = null;
                    int i2 = 2;
                    if (readInt2 == 1) {
                        hashtable = null;
                        certificateFactory = createCertFactory("X.509");
                    } else {
                        if (readInt2 != 2) {
                            throw new IllegalStateException("unable to discern store version");
                        }
                        hashtable = new Hashtable();
                    }
                    int readInt3 = dataInputStream.readInt();
                    int i3 = 0;
                    while (i3 < readInt3) {
                        int readInt4 = dataInputStream.readInt();
                        if (readInt4 == 1) {
                            throw new IOException(NOT_IMPLEMENTED_MESSAGE);
                        }
                        if (readInt4 != i2) {
                            throw new IllegalStateException("unable to discern entry type");
                        }
                        String readUTF = dataInputStream.readUTF();
                        Date date = new Date(dataInputStream.readLong());
                        if (readInt2 == i2) {
                            String readUTF2 = dataInputStream.readUTF();
                            if (hashtable.containsKey(readUTF2)) {
                                certificateFactory = (CertificateFactory) hashtable.get(readUTF2);
                            } else {
                                CertificateFactory createCertFactory = createCertFactory(readUTF2);
                                hashtable.put(readUTF2, createCertFactory);
                                certificateFactory = createCertFactory;
                            }
                        }
                        int readInt5 = dataInputStream.readInt();
                        byte[] bArr = new byte[readInt5];
                        dataInputStream.readFully(bArr);
                        ErasableByteStream erasableByteStream = new ErasableByteStream(bArr, 0, readInt5);
                        try {
                            Certificate generateCertificate = certificateFactory.generateCertificate(erasableByteStream);
                            if (erasableByteStream.available() != 0) {
                                throw new IOException("password incorrect or store tampered with");
                            }
                            erasableByteStream.erase();
                            this.certificateEntries.put(readUTF, new BCJKSTrustedCertEntry(date, generateCertificate));
                            i3++;
                            i2 = 2;
                        } catch (Throwable th) {
                            erasableByteStream.erase();
                            throw th;
                        }
                    }
                }
                if (validateStream.available() != 0) {
                    throw new IOException("password incorrect or store tampered with");
                }
            } finally {
                validateStream.erase();
            }
        }
    }

    @Override // java.security.KeyStoreSpi
    public boolean engineProbe(InputStream inputStream) {
        DataInputStream dataInputStream = inputStream instanceof DataInputStream ? (DataInputStream) inputStream : new DataInputStream(inputStream);
        int readInt = dataInputStream.readInt();
        int readInt2 = dataInputStream.readInt();
        return readInt == -17957139 && (readInt2 == 1 || readInt2 == 2);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetCertificateEntry(String str, Certificate certificate) {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public int engineSize() {
        return this.certificateEntries.size();
    }

    @Override // java.security.KeyStoreSpi
    public void engineStore(OutputStream outputStream, char[] cArr) {
        throw new IOException(NOT_IMPLEMENTED_MESSAGE);
    }

    @Override // java.security.KeyStoreSpi
    public void engineLoad(KeyStore.LoadStoreParameter loadStoreParameter) {
        if (loadStoreParameter == null) {
            engineLoad(null, null);
        } else {
            if (!(loadStoreParameter instanceof BCLoadStoreParameter)) {
                throw new IllegalArgumentException("no support for 'param' of type ".concat(loadStoreParameter.getClass().getName()));
            }
            engineLoad(((BCLoadStoreParameter) loadStoreParameter).getInputStream(), ParameterUtil.extractPassword(loadStoreParameter));
        }
    }

    @Override // java.security.KeyStoreSpi
    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) {
        throw new KeyStoreException(NOT_IMPLEMENTED_MESSAGE);
    }
}
