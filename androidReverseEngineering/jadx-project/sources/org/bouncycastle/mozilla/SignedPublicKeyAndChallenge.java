package org.bouncycastle.mozilla;

import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERBitString;
import org.bouncycastle.asn1.mozilla.PublicKeyAndChallenge;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.operator.ContentVerifier;
import org.bouncycastle.operator.ContentVerifierProvider;
import org.bouncycastle.util.Encodable;

/* loaded from: classes.dex */
public class SignedPublicKeyAndChallenge implements Encodable {
    protected final org.bouncycastle.asn1.mozilla.SignedPublicKeyAndChallenge spkacSeq;

    public SignedPublicKeyAndChallenge(org.bouncycastle.asn1.mozilla.SignedPublicKeyAndChallenge signedPublicKeyAndChallenge) {
        this.spkacSeq = signedPublicKeyAndChallenge;
    }

    public String getChallenge() {
        return this.spkacSeq.getPublicKeyAndChallenge().getChallenge().getString();
    }

    @Override // org.bouncycastle.util.Encodable
    public byte[] getEncoded() {
        return toASN1Structure().getEncoded();
    }

    public PublicKey getPublicKey(String str) {
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.spkacSeq.getPublicKeyAndChallenge().getSubjectPublicKeyInfo();
        try {
            return KeyFactory.getInstance(subjectPublicKeyInfo.getAlgorithm().getAlgorithm().getId(), str).generatePublic(new X509EncodedKeySpec(new DERBitString(subjectPublicKeyInfo).getOctets()));
        } catch (Exception unused) {
            throw new InvalidKeyException("error encoding public key");
        }
    }

    public PublicKeyAndChallenge getPublicKeyAndChallenge() {
        return this.spkacSeq.getPublicKeyAndChallenge();
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return this.spkacSeq.getPublicKeyAndChallenge().getSubjectPublicKeyInfo();
    }

    public boolean isSignatureValid(ContentVerifierProvider contentVerifierProvider) {
        ContentVerifier contentVerifier = contentVerifierProvider.get(this.spkacSeq.getSignatureAlgorithm());
        OutputStream outputStream = contentVerifier.getOutputStream();
        this.spkacSeq.getPublicKeyAndChallenge().encodeTo(outputStream, ASN1Encoding.DER);
        outputStream.close();
        return contentVerifier.verify(this.spkacSeq.getSignature().getOctets());
    }

    public ASN1Primitive toASN1Primitive() {
        return this.spkacSeq.toASN1Primitive();
    }

    public org.bouncycastle.asn1.mozilla.SignedPublicKeyAndChallenge toASN1Structure() {
        return this.spkacSeq;
    }

    public boolean verify() {
        return verify(null);
    }

    public SignedPublicKeyAndChallenge(byte[] bArr) {
        this.spkacSeq = org.bouncycastle.asn1.mozilla.SignedPublicKeyAndChallenge.getInstance(bArr);
    }

    public boolean verify(String str) {
        String id = this.spkacSeq.getSignatureAlgorithm().getAlgorithm().getId();
        Signature signature = str == null ? Signature.getInstance(id) : Signature.getInstance(id, str);
        signature.initVerify(getPublicKey(str));
        try {
            signature.update(this.spkacSeq.getPublicKeyAndChallenge().getEncoded());
            return signature.verify(this.spkacSeq.getSignature().getBytes());
        } catch (Exception unused) {
            throw new InvalidKeyException("error encoding public key");
        }
    }
}
