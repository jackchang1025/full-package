package org.bouncycastle.cert.crmf;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.cmp.CMPObjectIdentifiers;
import org.bouncycastle.asn1.cmp.PBMParameter;
import org.bouncycastle.asn1.iana.IANAObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.GenericKey;
import org.bouncycastle.operator.MacCalculator;
import org.bouncycastle.operator.RuntimeOperatorException;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class PKMACBuilder {
    private PKMACValuesCalculator calculator;
    private int iterationCount;
    private AlgorithmIdentifier mac;
    private int maxIterations;
    private AlgorithmIdentifier owf;
    private PBMParameter parameters;
    private SecureRandom random;
    private int saltLength;

    private PKMACBuilder(AlgorithmIdentifier algorithmIdentifier, int i2, AlgorithmIdentifier algorithmIdentifier2, PKMACValuesCalculator pKMACValuesCalculator) {
        this.saltLength = 20;
        this.owf = algorithmIdentifier;
        this.iterationCount = i2;
        this.mac = algorithmIdentifier2;
        this.calculator = pKMACValuesCalculator;
    }

    private void checkIterationCountCeiling(int i2) {
        int i3 = this.maxIterations;
        if (i3 > 0 && i2 > i3) {
            throw new IllegalArgumentException(AbstractC0000a.m17m(AbstractC0000a.m21q("iteration count exceeds limit (", i2, " > "), this.maxIterations, ")"));
        }
    }

    private MacCalculator genCalculator(final PBMParameter pBMParameter, char[] cArr) {
        byte[] uTF8ByteArray = Strings.toUTF8ByteArray(cArr);
        byte[] octets = pBMParameter.getSalt().getOctets();
        final byte[] bArr = new byte[uTF8ByteArray.length + octets.length];
        System.arraycopy(uTF8ByteArray, 0, bArr, 0, uTF8ByteArray.length);
        System.arraycopy(octets, 0, bArr, uTF8ByteArray.length, octets.length);
        this.calculator.setup(pBMParameter.getOwf(), pBMParameter.getMac());
        int intValueExact = pBMParameter.getIterationCount().intValueExact();
        do {
            bArr = this.calculator.calculateDigest(bArr);
            intValueExact--;
        } while (intValueExact > 0);
        return new MacCalculator() { // from class: org.bouncycastle.cert.crmf.PKMACBuilder.1
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();

            @Override // org.bouncycastle.operator.MacCalculator
            public AlgorithmIdentifier getAlgorithmIdentifier() {
                return new AlgorithmIdentifier(CMPObjectIdentifiers.passwordBasedMac, pBMParameter);
            }

            @Override // org.bouncycastle.operator.MacCalculator
            public GenericKey getKey() {
                return new GenericKey(getAlgorithmIdentifier(), bArr);
            }

            @Override // org.bouncycastle.operator.MacCalculator
            public byte[] getMac() {
                try {
                    return PKMACBuilder.this.calculator.calculateMac(bArr, this.bOut.toByteArray());
                } catch (CRMFException e2) {
                    throw new RuntimeOperatorException("exception calculating mac: " + e2.getMessage(), e2);
                }
            }

            @Override // org.bouncycastle.operator.MacCalculator
            public OutputStream getOutputStream() {
                return this.bOut;
            }
        };
    }

    public MacCalculator build(char[] cArr) {
        PBMParameter pBMParameter = this.parameters;
        if (pBMParameter != null) {
            return genCalculator(pBMParameter, cArr);
        }
        byte[] bArr = new byte[this.saltLength];
        if (this.random == null) {
            this.random = new SecureRandom();
        }
        this.random.nextBytes(bArr);
        return genCalculator(new PBMParameter(bArr, this.owf, this.iterationCount, this.mac), cArr);
    }

    public PKMACBuilder setIterationCount(int i2) {
        if (i2 < 100) {
            throw new IllegalArgumentException("iteration count must be at least 100");
        }
        checkIterationCountCeiling(i2);
        this.iterationCount = i2;
        return this;
    }

    public PKMACBuilder setParameters(PBMParameter pBMParameter) {
        checkIterationCountCeiling(pBMParameter.getIterationCount().intValueExact());
        this.parameters = pBMParameter;
        return this;
    }

    public PKMACBuilder setSaltLength(int i2) {
        if (i2 < 8) {
            throw new IllegalArgumentException("salt length must be at least 8 bytes");
        }
        this.saltLength = i2;
        return this;
    }

    public PKMACBuilder setSecureRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
        return this;
    }

    public PKMACBuilder(PKMACValuesCalculator pKMACValuesCalculator) {
        this(new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1), 1000, new AlgorithmIdentifier(IANAObjectIdentifiers.hmacSHA1, DERNull.INSTANCE), pKMACValuesCalculator);
    }

    public PKMACBuilder(PKMACValuesCalculator pKMACValuesCalculator, int i2) {
        this.saltLength = 20;
        this.maxIterations = i2;
        this.calculator = pKMACValuesCalculator;
    }
}
