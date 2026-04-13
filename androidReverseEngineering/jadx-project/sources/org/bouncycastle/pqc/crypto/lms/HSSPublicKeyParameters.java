package org.bouncycastle.pqc.crypto.lms;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class HSSPublicKeyParameters extends LMSKeyParameters implements LMSContextBasedVerifier {

    /* renamed from: l */
    private final int f1549l;
    private final LMSPublicKeyParameters lmsPublicKey;

    public HSSPublicKeyParameters(int i2, LMSPublicKeyParameters lMSPublicKeyParameters) {
        super(false);
        this.f1549l = i2;
        this.lmsPublicKey = lMSPublicKeyParameters;
    }

    public static HSSPublicKeyParameters getInstance(Object obj) {
        if (obj instanceof HSSPublicKeyParameters) {
            return (HSSPublicKeyParameters) obj;
        }
        if (obj instanceof DataInputStream) {
            return new HSSPublicKeyParameters(((DataInputStream) obj).readInt(), LMSPublicKeyParameters.getInstance(obj));
        }
        if (!(obj instanceof byte[])) {
            if (obj instanceof InputStream) {
                return getInstance(Streams.readAll((InputStream) obj));
            }
            throw new IllegalArgumentException("cannot parse " + obj);
        }
        DataInputStream dataInputStream = null;
        try {
            DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream((byte[]) obj));
            try {
                HSSPublicKeyParameters hSSPublicKeyParameters = getInstance(dataInputStream2);
                dataInputStream2.close();
                return hSSPublicKeyParameters;
            } catch (Throwable th) {
                th = th;
                dataInputStream = dataInputStream2;
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HSSPublicKeyParameters hSSPublicKeyParameters = (HSSPublicKeyParameters) obj;
        if (this.f1549l != hSSPublicKeyParameters.f1549l) {
            return false;
        }
        return this.lmsPublicKey.equals(hSSPublicKeyParameters.lmsPublicKey);
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSContextBasedVerifier
    public LMSContext generateLMSContext(byte[] bArr) {
        try {
            HSSSignature hSSSignature = HSSSignature.getInstance(bArr, getL());
            LMSSignedPubKey[] signedPubKey = hSSSignature.getSignedPubKey();
            return signedPubKey[signedPubKey.length - 1].getPublicKey().generateOtsContext(hSSSignature.getSignature()).withSignedPublicKeys(signedPubKey);
        } catch (IOException e2) {
            throw new IllegalStateException(AbstractC0000a.m8d(e2, new StringBuilder("cannot parse signature: ")));
        }
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSKeyParameters, org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return Composer.compose().u32str(this.f1549l).bytes(this.lmsPublicKey.getEncoded()).build();
    }

    public int getL() {
        return this.f1549l;
    }

    public LMSPublicKeyParameters getLMSPublicKey() {
        return this.lmsPublicKey;
    }

    public int hashCode() {
        return this.lmsPublicKey.hashCode() + (this.f1549l * 31);
    }

    @Override // org.bouncycastle.pqc.crypto.lms.LMSContextBasedVerifier
    public boolean verify(LMSContext lMSContext) {
        LMSSignedPubKey[] signedPubKeys = lMSContext.getSignedPubKeys();
        if (signedPubKeys.length != getL() - 1) {
            return false;
        }
        LMSPublicKeyParameters lMSPublicKey = getLMSPublicKey();
        boolean z2 = false;
        for (int i2 = 0; i2 < signedPubKeys.length; i2++) {
            if (!LMS.verifySignature(lMSPublicKey, signedPubKeys[i2].getSignature(), signedPubKeys[i2].getPublicKey().toByteArray())) {
                z2 = true;
            }
            lMSPublicKey = signedPubKeys[i2].getPublicKey();
        }
        return lMSPublicKey.verify(lMSContext) & (!z2);
    }
}
