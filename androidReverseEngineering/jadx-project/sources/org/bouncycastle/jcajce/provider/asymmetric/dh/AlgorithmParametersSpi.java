package org.bouncycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.pkcs.DHParameter;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
    DHParameterSpec currentSpec;

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded() {
        try {
            return new DHParameter(this.currentSpec.getP(), this.currentSpec.getG(), this.currentSpec.getL()).getEncoded(ASN1Encoding.DER);
        } catch (IOException unused) {
            throw new RuntimeException("Error encoding DHParameters");
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
        if (!(algorithmParameterSpec instanceof DHParameterSpec)) {
            throw new InvalidParameterSpecException("DHParameterSpec required to initialise a Diffie-Hellman algorithm parameters object");
        }
        this.currentSpec = (DHParameterSpec) algorithmParameterSpec;
    }

    @Override // java.security.AlgorithmParametersSpi
    public String engineToString() {
        return "Diffie-Hellman Parameters";
    }

    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
        if (cls == DHParameterSpec.class || cls == AlgorithmParameterSpec.class) {
            return this.currentSpec;
        }
        throw new InvalidParameterSpecException("unknown parameter spec passed to DH parameters object.");
    }

    @Override // java.security.AlgorithmParametersSpi
    public byte[] engineGetEncoded(String str) {
        if (isASN1FormatString(str)) {
            return engineGetEncoded();
        }
        return null;
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr) {
        try {
            DHParameter dHParameter = DHParameter.getInstance(bArr);
            this.currentSpec = dHParameter.getL() != null ? new DHParameterSpec(dHParameter.getP(), dHParameter.getG(), dHParameter.getL().intValue()) : new DHParameterSpec(dHParameter.getP(), dHParameter.getG());
        } catch (ArrayIndexOutOfBoundsException unused) {
            throw new IOException("Not a valid DH Parameter encoding.");
        } catch (ClassCastException unused2) {
            throw new IOException("Not a valid DH Parameter encoding.");
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    public void engineInit(byte[] bArr, String str) {
        if (!isASN1FormatString(str)) {
            throw new IOException(AbstractC0000a.m15k("Unknown parameter format ", str));
        }
        engineInit(bArr);
    }
}
