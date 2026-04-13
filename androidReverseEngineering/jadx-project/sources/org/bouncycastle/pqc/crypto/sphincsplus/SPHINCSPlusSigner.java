package org.bouncycastle.pqc.crypto.sphincsplus;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class SPHINCSPlusSigner implements MessageSigner {
    private SPHINCSPlusPrivateKeyParameters privKey;
    private SPHINCSPlusPublicKeyParameters pubKey;
    private SecureRandom random;

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public byte[] generateSignature(byte[] bArr) {
        SPHINCSPlusEngine engine = this.privKey.getParameters().getEngine();
        byte[] bArr2 = new byte[engine.f1615N];
        SecureRandom secureRandom = this.random;
        if (secureRandom != null) {
            secureRandom.nextBytes(bArr2);
        }
        Fors fors = new Fors(engine);
        byte[] PRF_msg = engine.PRF_msg(this.privKey.sk.prf, bArr2, bArr);
        PK pk = this.privKey.pk;
        IndexedDigest H_msg = engine.H_msg(PRF_msg, pk.seed, pk.root, bArr);
        byte[] bArr3 = H_msg.digest;
        long j2 = H_msg.idx_tree;
        int i2 = H_msg.idx_leaf;
        ADRS adrs = new ADRS();
        adrs.setType(3);
        adrs.setTreeAddress(j2);
        adrs.setKeyPairAddress(i2);
        SPHINCSPlusPrivateKeyParameters sPHINCSPlusPrivateKeyParameters = this.privKey;
        SIG_FORS[] sign = fors.sign(bArr3, sPHINCSPlusPrivateKeyParameters.sk.seed, sPHINCSPlusPrivateKeyParameters.pk.seed, adrs);
        byte[] pkFromSig = fors.pkFromSig(sign, bArr3, this.privKey.pk.seed, adrs);
        new ADRS().setType(2);
        byte[] sign2 = new HT(engine, this.privKey.getSeed(), this.privKey.getPublicSeed()).sign(pkFromSig, j2, i2);
        int length = sign.length + 2;
        byte[][] bArr4 = new byte[length][];
        int i3 = 0;
        bArr4[0] = PRF_msg;
        while (i3 != sign.length) {
            int i4 = i3 + 1;
            SIG_FORS sig_fors = sign[i3];
            bArr4[i4] = Arrays.concatenate(sig_fors.sk, Arrays.concatenate(sig_fors.authPath));
            i3 = i4;
        }
        bArr4[length - 1] = sign2;
        return Arrays.concatenate(bArr4);
    }

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!z2) {
            this.pubKey = (SPHINCSPlusPublicKeyParameters) cipherParameters;
        } else {
            if (!(cipherParameters instanceof ParametersWithRandom)) {
                this.privKey = (SPHINCSPlusPrivateKeyParameters) cipherParameters;
                return;
            }
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.privKey = (SPHINCSPlusPrivateKeyParameters) parametersWithRandom.getParameters();
            this.random = parametersWithRandom.getRandom();
        }
    }

    @Override // org.bouncycastle.pqc.crypto.MessageSigner
    public boolean verifySignature(byte[] bArr, byte[] bArr2) {
        SPHINCSPlusEngine engine = this.pubKey.getParameters().getEngine();
        ADRS adrs = new ADRS();
        SIG sig = new SIG(engine.f1615N, engine.f1614K, engine.f1611A, engine.f1612D, engine.H_PRIME, engine.WOTS_LEN, bArr2);
        byte[] r2 = sig.getR();
        SIG_FORS[] sig_fors = sig.getSIG_FORS();
        SIG_XMSS[] sig_ht = sig.getSIG_HT();
        IndexedDigest H_msg = engine.H_msg(r2, this.pubKey.getSeed(), this.pubKey.getRoot(), bArr);
        byte[] bArr3 = H_msg.digest;
        long j2 = H_msg.idx_tree;
        int i2 = H_msg.idx_leaf;
        adrs.setLayerAddress(0);
        adrs.setTreeAddress(j2);
        adrs.setType(3);
        adrs.setKeyPairAddress(i2);
        byte[] pkFromSig = new Fors(engine).pkFromSig(sig_fors, bArr3, this.pubKey.getSeed(), adrs);
        adrs.setType(2);
        return new HT(engine, null, this.pubKey.getSeed()).verify(pkFromSig, sig_ht, this.pubKey.getSeed(), j2, i2, this.pubKey.getRoot());
    }
}
