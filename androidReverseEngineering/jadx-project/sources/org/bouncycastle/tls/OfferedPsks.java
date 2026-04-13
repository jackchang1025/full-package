package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.tls.crypto.TlsHash;
import org.bouncycastle.tls.crypto.TlsHashOutputStream;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class OfferedPsks {
    protected final Vector binders;
    protected final int bindersSize;
    protected final Vector identities;

    public static class BindersConfig {
        final int bindersSize;
        final TlsSecret[] earlySecrets;
        final short[] pskKeyExchangeModes;
        final TlsPSK[] psks;

        public BindersConfig(TlsPSK[] tlsPSKArr, short[] sArr, TlsSecret[] tlsSecretArr, int i2) {
            this.psks = tlsPSKArr;
            this.pskKeyExchangeModes = sArr;
            this.earlySecrets = tlsSecretArr;
            this.bindersSize = i2;
        }
    }

    public static class SelectedConfig {
        final TlsSecret earlySecret;
        final int index;
        final TlsPSK psk;
        final short[] pskKeyExchangeModes;

        public SelectedConfig(int i2, TlsPSK tlsPSK, short[] sArr, TlsSecret tlsSecret) {
            this.index = i2;
            this.psk = tlsPSK;
            this.pskKeyExchangeModes = sArr;
            this.earlySecret = tlsSecret;
        }
    }

    public OfferedPsks(Vector vector) {
        this(vector, null, -1);
    }

    public static void encodeBinders(OutputStream outputStream, TlsCrypto tlsCrypto, TlsHandshakeHash tlsHandshakeHash, BindersConfig bindersConfig) {
        TlsPSK[] tlsPSKArr = bindersConfig.psks;
        TlsSecret[] tlsSecretArr = bindersConfig.earlySecrets;
        int i2 = bindersConfig.bindersSize - 2;
        TlsUtils.checkUint16(i2);
        TlsUtils.writeUint16(i2, outputStream);
        int i3 = 0;
        for (int i4 = 0; i4 < tlsPSKArr.length; i4++) {
            TlsPSK tlsPSK = tlsPSKArr[i4];
            TlsSecret tlsSecret = tlsSecretArr[i4];
            int hashForPRF = TlsCryptoUtils.getHashForPRF(tlsPSK.getPRFAlgorithm());
            TlsHash createHash = tlsCrypto.createHash(hashForPRF);
            tlsHandshakeHash.copyBufferTo(new TlsHashOutputStream(createHash));
            byte[] calculatePSKBinder = TlsUtils.calculatePSKBinder(tlsCrypto, true, hashForPRF, tlsSecret, createHash.calculateHash());
            i3 += calculatePSKBinder.length + 1;
            TlsUtils.writeOpaque8(calculatePSKBinder, outputStream);
        }
        if (i2 != i3) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    public static OfferedPsks parse(InputStream inputStream) {
        Vector vector = new Vector();
        int readUint16 = TlsUtils.readUint16(inputStream);
        if (readUint16 < 7) {
            throw new TlsFatalAlert((short) 50);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(TlsUtils.readFully(readUint16, inputStream));
        do {
            vector.add(PskIdentity.parse(byteArrayInputStream));
        } while (byteArrayInputStream.available() > 0);
        Vector vector2 = new Vector();
        int readUint162 = TlsUtils.readUint16(inputStream);
        if (readUint162 < 33) {
            throw new TlsFatalAlert((short) 50);
        }
        ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(TlsUtils.readFully(readUint162, inputStream));
        do {
            vector2.add(TlsUtils.readOpaque8(byteArrayInputStream2, 32));
        } while (byteArrayInputStream2.available() > 0);
        return new OfferedPsks(vector, vector2, readUint162 + 2);
    }

    public void encode(OutputStream outputStream) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.identities.size(); i3++) {
            i2 += ((PskIdentity) this.identities.elementAt(i3)).getEncodedLength();
        }
        TlsUtils.checkUint16(i2);
        TlsUtils.writeUint16(i2, outputStream);
        for (int i4 = 0; i4 < this.identities.size(); i4++) {
            ((PskIdentity) this.identities.elementAt(i4)).encode(outputStream);
        }
        if (this.binders != null) {
            int i5 = 0;
            for (int i6 = 0; i6 < this.binders.size(); i6++) {
                i5 += ((byte[]) this.binders.elementAt(i6)).length + 1;
            }
            TlsUtils.checkUint16(i5);
            TlsUtils.writeUint16(i5, outputStream);
            for (int i7 = 0; i7 < this.binders.size(); i7++) {
                TlsUtils.writeOpaque8((byte[]) this.binders.elementAt(i7), outputStream);
            }
        }
    }

    public Vector getBinders() {
        return this.binders;
    }

    public int getBindersSize() {
        return this.bindersSize;
    }

    public Vector getIdentities() {
        return this.identities;
    }

    public int getIndexOfIdentity(PskIdentity pskIdentity) {
        int size = this.identities.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (pskIdentity.equals(this.identities.elementAt(i2))) {
                return i2;
            }
        }
        return -1;
    }

    private OfferedPsks(Vector vector, Vector vector2, int i2) {
        if (vector == null || vector.isEmpty()) {
            throw new IllegalArgumentException("'identities' cannot be null or empty");
        }
        if (vector2 != null && vector.size() != vector2.size()) {
            throw new IllegalArgumentException("'binders' must be the same length as 'identities' (or null)");
        }
        if ((vector2 != null) != (i2 >= 0)) {
            throw new IllegalArgumentException("'bindersSize' must be >= 0 iff 'binders' are present");
        }
        this.identities = vector;
        this.binders = vector2;
        this.bindersSize = i2;
    }

    public static int getBindersSize(TlsPSK[] tlsPSKArr) {
        int i2 = 0;
        for (TlsPSK tlsPSK : tlsPSKArr) {
            i2 += TlsCryptoUtils.getHashOutputSize(TlsCryptoUtils.getHashForPRF(tlsPSK.getPRFAlgorithm())) + 1;
        }
        TlsUtils.checkUint16(i2);
        return i2 + 2;
    }
}
