package org.bouncycastle.operator.bc;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;

/* loaded from: classes.dex */
public class BcDigestCalculatorProvider implements DigestCalculatorProvider {
    private BcDigestProvider digestProvider = BcDefaultDigestProvider.INSTANCE;

    public class DigestOutputStream extends OutputStream {
        private Digest dig;

        public DigestOutputStream(Digest digest) {
            this.dig = digest;
        }

        public byte[] getDigest() {
            byte[] bArr = new byte[this.dig.getDigestSize()];
            this.dig.doFinal(bArr, 0);
            return bArr;
        }

        @Override // java.io.OutputStream
        public void write(int i2) {
            this.dig.update((byte) i2);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr) {
            this.dig.update(bArr, 0, bArr.length);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i2, int i3) {
            this.dig.update(bArr, i2, i3);
        }
    }

    @Override // org.bouncycastle.operator.DigestCalculatorProvider
    public DigestCalculator get(final AlgorithmIdentifier algorithmIdentifier) {
        final DigestOutputStream digestOutputStream = new DigestOutputStream(this.digestProvider.get(algorithmIdentifier));
        return new DigestCalculator() { // from class: org.bouncycastle.operator.bc.BcDigestCalculatorProvider.1
            @Override // org.bouncycastle.operator.DigestCalculator
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return algorithmIdentifier;
            }

            @Override // org.bouncycastle.operator.DigestCalculator
            public byte[] getDigest() {
                return digestOutputStream.getDigest();
            }

            @Override // org.bouncycastle.operator.DigestCalculator
            public OutputStream getOutputStream() {
                return digestOutputStream;
            }
        };
    }
}
