package org.bouncycastle.jcajce.provider.symmetric.util;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class IvAlgorithmParameters extends BaseAlgorithmParameters {
    private byte[] iv;

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded() {
        return engineGetEncoded("ASN.1");
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof IvParameterSpec)) {
            throw new InvalidParameterSpecException("IvParameterSpec required to initialise a IV parameters algorithm parameters object");
        }
        this.iv = ((IvParameterSpec) algorithmParameterSpec).getIV();
    }

    @Override // java.security.AlgorithmParametersSpi
    public String engineToString() {
        return "IV Parameters";
    }

    @Override // org.bouncycastle.jcajce.provider.symmetric.util.BaseAlgorithmParameters
    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == IvParameterSpec.class || cls == AlgorithmParameterSpec.class) {
            return new IvParameterSpec(this.iv);
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to IV parameters object.");
    }

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str)) {
            return new DEROctetString(engineGetEncoded("RAW")).getEncoded();
        }
        if (str.equals("RAW")) {
            return Arrays.clone(this.iv);
        }
        return null;
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr) {
        if (bArr.length % 8 != 0 && bArr[0] == 4 && bArr[1] == bArr.length - 2) {
            bArr = ((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets();
        }
        this.iv = Arrays.clone(bArr);
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr, String str) {
        if (isASN1FormatString(str)) {
            try {
                engineInit(((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets());
            } catch (Exception e2) {
                throw new IOException(AbstractC0000a.m14j("Exception decoding: ", e2));
            }
        } else {
            if (!str.equals("RAW")) {
                throw new IOException("Unknown parameters format in IV parameters object");
            }
            engineInit(bArr);
        }
    }
}
