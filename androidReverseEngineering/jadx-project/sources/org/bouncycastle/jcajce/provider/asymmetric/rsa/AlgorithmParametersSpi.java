package org.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.jcajce.provider.util.DigestFactory;
import org.bouncycastle.jcajce.util.MessageDigestUtils;

/* loaded from: classes.dex */
public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {

    public static class OAEP extends AlgorithmParametersSpi {
        OAEPParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            ASN1ObjectIdentifier oid = DigestFactory.getOID(this.currentSpec.getDigestAlgorithm());
            DERNull dERNull = DERNull.INSTANCE;
            try {
                return new RSAESOAEPparams(new AlgorithmIdentifier(oid, dERNull), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(((MGF1ParameterSpec) this.currentSpec.getMGFParameters()).getDigestAlgorithm()), dERNull)), new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(((PSource.PSpecified) this.currentSpec.getPSource()).getValue()))).getEncoded(ASN1Encoding.DER);
            } catch (IOException unused) {
                throw new RuntimeException("Error encoding OAEPParameters");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof OAEPParameterSpec)) {
                throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
            }
            this.currentSpec = (OAEPParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "OAEP Parameters";
        }

        @Override // org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == OAEPParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) {
            if (isASN1FormatString(str) || str.equalsIgnoreCase("X.509")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) {
            try {
                RSAESOAEPparams rSAESOAEPparams = RSAESOAEPparams.getInstance(bArr);
                if (rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
                    this.currentSpec = new OAEPParameterSpec(MessageDigestUtils.getDigestName(rSAESOAEPparams.getHashAlgorithm().getAlgorithm()), OAEPParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(rSAESOAEPparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), new PSource.PSpecified(ASN1OctetString.getInstance(rSAESOAEPparams.getPSourceAlgorithm().getParameters()).getOctets()));
                } else {
                    throw new IOException("unknown mask generation function: " + rSAESOAEPparams.getMaskGenAlgorithm().getAlgorithm());
                }
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            } catch (ClassCastException unused2) {
                throw new IOException("Not a valid OAEP Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) {
            if (!str.equalsIgnoreCase("X.509") && !str.equalsIgnoreCase("ASN.1")) {
                throw new IOException("Unknown parameter format ".concat(str));
            }
            engineInit(bArr);
        }
    }

    public static class PSS extends AlgorithmParametersSpi {
        PSSParameterSpec currentSpec;

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded() {
            PSSParameterSpec pSSParameterSpec = this.currentSpec;
            ASN1ObjectIdentifier oid = DigestFactory.getOID(pSSParameterSpec.getDigestAlgorithm());
            DERNull dERNull = DERNull.INSTANCE;
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(oid, dERNull);
            MGF1ParameterSpec mGF1ParameterSpec = (MGF1ParameterSpec) pSSParameterSpec.getMGFParameters();
            if (mGF1ParameterSpec != null) {
                return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(mGF1ParameterSpec.getDigestAlgorithm()), dERNull)), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded(ASN1Encoding.DER);
            }
            return new RSASSAPSSparams(algorithmIdentifier, new AlgorithmIdentifier(pSSParameterSpec.getMGFAlgorithm().equals("SHAKE128") ? NISTObjectIdentifiers.id_shake128 : NISTObjectIdentifiers.id_shake256), new ASN1Integer(pSSParameterSpec.getSaltLength()), new ASN1Integer(pSSParameterSpec.getTrailerField())).getEncoded(ASN1Encoding.DER);
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(AlgorithmParameterSpec algorithmParameterSpec) {
            if (!(algorithmParameterSpec instanceof PSSParameterSpec)) {
                throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
            }
            this.currentSpec = (PSSParameterSpec) algorithmParameterSpec;
        }

        @Override // java.security.AlgorithmParametersSpi
        public String engineToString() {
            return "PSS Parameters";
        }

        @Override // org.bouncycastle.jcajce.provider.asymmetric.rsa.AlgorithmParametersSpi
        public AlgorithmParameterSpec localEngineGetParameterSpec(Class cls) {
            if (cls == PSSParameterSpec.class || cls == AlgorithmParameterSpec.class) {
                return this.currentSpec;
            }
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
        }

        @Override // java.security.AlgorithmParametersSpi
        public byte[] engineGetEncoded(String str) {
            if (str.equalsIgnoreCase("X.509") || str.equalsIgnoreCase("ASN.1")) {
                return engineGetEncoded();
            }
            return null;
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr) {
            PSSParameterSpec pSSParameterSpec;
            try {
                RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(bArr);
                ASN1ObjectIdentifier algorithm = rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm();
                if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
                    pSSParameterSpec = new PSSParameterSpec(MessageDigestUtils.getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()), PSSParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(rSASSAPSSparams.getMaskGenAlgorithm().getParameters()).getAlgorithm())), rSASSAPSSparams.getSaltLength().intValue(), rSASSAPSSparams.getTrailerField().intValue());
                } else {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_shake128;
                    if (!algorithm.equals((ASN1Primitive) aSN1ObjectIdentifier) && !algorithm.equals((ASN1Primitive) NISTObjectIdentifiers.id_shake256)) {
                        throw new IOException("unknown mask generation function: " + rSASSAPSSparams.getMaskGenAlgorithm().getAlgorithm());
                    }
                    pSSParameterSpec = new PSSParameterSpec(MessageDigestUtils.getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()), algorithm.equals((ASN1Primitive) aSN1ObjectIdentifier) ? "SHAKE128" : "SHAKE256", null, rSASSAPSSparams.getSaltLength().intValue(), rSASSAPSSparams.getTrailerField().intValue());
                }
                this.currentSpec = pSSParameterSpec;
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            } catch (ClassCastException unused2) {
                throw new IOException("Not a valid PSS Parameter encoding.");
            }
        }

        @Override // java.security.AlgorithmParametersSpi
        public void engineInit(byte[] bArr, String str) {
            if (!isASN1FormatString(str) && !str.equalsIgnoreCase("X.509")) {
                throw new IOException("Unknown parameter format ".concat(str));
            }
            engineInit(bArr);
        }
    }

    @Override // java.security.AlgorithmParametersSpi
    public AlgorithmParameterSpec engineGetParameterSpec(Class cls) {
        if (cls != null) {
            return localEngineGetParameterSpec(cls);
        }
        throw new NullPointerException("argument to getParameterSpec must not be null");
    }

    public boolean isASN1FormatString(String str) {
        return str == null || str.equals("ASN.1");
    }

    public abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class cls);
}
