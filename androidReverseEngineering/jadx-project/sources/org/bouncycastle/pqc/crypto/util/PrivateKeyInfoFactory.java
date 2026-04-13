package org.bouncycastle.pqc.crypto.util;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.asn1.McElieceCCA2PrivateKey;
import org.bouncycastle.pqc.asn1.PQCObjectIdentifiers;
import org.bouncycastle.pqc.asn1.SPHINCS256KeyParams;
import org.bouncycastle.pqc.asn1.XMSSKeyParams;
import org.bouncycastle.pqc.asn1.XMSSMTKeyParams;
import org.bouncycastle.pqc.asn1.XMSSMTPrivateKey;
import org.bouncycastle.pqc.asn1.XMSSPrivateKey;
import org.bouncycastle.pqc.crypto.lms.Composer;
import org.bouncycastle.pqc.crypto.lms.HSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.lms.LMSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.mceliece.McElieceCCA2PrivateKeyParameters;
import org.bouncycastle.pqc.crypto.newhope.NHPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.qtesla.QTESLAPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.sphincs.SPHINCSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.BDS;
import org.bouncycastle.pqc.crypto.xmss.BDSStateMap;
import org.bouncycastle.pqc.crypto.xmss.XMSSMTPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xmss.XMSSUtil;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class PrivateKeyInfoFactory {
    private PrivateKeyInfoFactory() {
    }

    public static PrivateKeyInfo createPrivateKeyInfo(AsymmetricKeyParameter asymmetricKeyParameter) {
        return createPrivateKeyInfo(asymmetricKeyParameter, null);
    }

    private static XMSSPrivateKey xmssCreateKeyStructure(XMSSPrivateKeyParameters xMSSPrivateKeyParameters) {
        byte[] encoded = xMSSPrivateKeyParameters.getEncoded();
        int treeDigestSize = xMSSPrivateKeyParameters.getParameters().getTreeDigestSize();
        int height = xMSSPrivateKeyParameters.getParameters().getHeight();
        int bytesToXBigEndian = (int) XMSSUtil.bytesToXBigEndian(encoded, 0, 4);
        if (!XMSSUtil.isIndexValid(height, bytesToXBigEndian)) {
            throw new IllegalArgumentException("index out of bounds");
        }
        byte[] extractBytesAtOffset = XMSSUtil.extractBytesAtOffset(encoded, 4, treeDigestSize);
        int i2 = 4 + treeDigestSize;
        byte[] extractBytesAtOffset2 = XMSSUtil.extractBytesAtOffset(encoded, i2, treeDigestSize);
        int i3 = i2 + treeDigestSize;
        byte[] extractBytesAtOffset3 = XMSSUtil.extractBytesAtOffset(encoded, i3, treeDigestSize);
        int i4 = i3 + treeDigestSize;
        byte[] extractBytesAtOffset4 = XMSSUtil.extractBytesAtOffset(encoded, i4, treeDigestSize);
        int i5 = i4 + treeDigestSize;
        byte[] extractBytesAtOffset5 = XMSSUtil.extractBytesAtOffset(encoded, i5, encoded.length - i5);
        try {
            BDS bds = (BDS) XMSSUtil.deserialize(extractBytesAtOffset5, BDS.class);
            return bds.getMaxIndex() != (1 << height) - 1 ? new XMSSPrivateKey(bytesToXBigEndian, extractBytesAtOffset, extractBytesAtOffset2, extractBytesAtOffset3, extractBytesAtOffset4, extractBytesAtOffset5, bds.getMaxIndex()) : new XMSSPrivateKey(bytesToXBigEndian, extractBytesAtOffset, extractBytesAtOffset2, extractBytesAtOffset3, extractBytesAtOffset4, extractBytesAtOffset5);
        } catch (ClassNotFoundException e2) {
            throw new IOException("cannot parse BDS: " + e2.getMessage());
        }
    }

    private static XMSSMTPrivateKey xmssmtCreateKeyStructure(XMSSMTPrivateKeyParameters xMSSMTPrivateKeyParameters) {
        byte[] encoded = xMSSMTPrivateKeyParameters.getEncoded();
        int treeDigestSize = xMSSMTPrivateKeyParameters.getParameters().getTreeDigestSize();
        int height = xMSSMTPrivateKeyParameters.getParameters().getHeight();
        int i2 = (height + 7) / 8;
        long bytesToXBigEndian = (int) XMSSUtil.bytesToXBigEndian(encoded, 0, i2);
        if (!XMSSUtil.isIndexValid(height, bytesToXBigEndian)) {
            throw new IllegalArgumentException("index out of bounds");
        }
        int i3 = i2 + 0;
        byte[] extractBytesAtOffset = XMSSUtil.extractBytesAtOffset(encoded, i3, treeDigestSize);
        int i4 = i3 + treeDigestSize;
        byte[] extractBytesAtOffset2 = XMSSUtil.extractBytesAtOffset(encoded, i4, treeDigestSize);
        int i5 = i4 + treeDigestSize;
        byte[] extractBytesAtOffset3 = XMSSUtil.extractBytesAtOffset(encoded, i5, treeDigestSize);
        int i6 = i5 + treeDigestSize;
        byte[] extractBytesAtOffset4 = XMSSUtil.extractBytesAtOffset(encoded, i6, treeDigestSize);
        int i7 = i6 + treeDigestSize;
        byte[] extractBytesAtOffset5 = XMSSUtil.extractBytesAtOffset(encoded, i7, encoded.length - i7);
        try {
            BDSStateMap bDSStateMap = (BDSStateMap) XMSSUtil.deserialize(extractBytesAtOffset5, BDSStateMap.class);
            return bDSStateMap.getMaxIndex() != (1 << height) - 1 ? new XMSSMTPrivateKey(bytesToXBigEndian, extractBytesAtOffset, extractBytesAtOffset2, extractBytesAtOffset3, extractBytesAtOffset4, extractBytesAtOffset5, bDSStateMap.getMaxIndex()) : new XMSSMTPrivateKey(bytesToXBigEndian, extractBytesAtOffset, extractBytesAtOffset2, extractBytesAtOffset3, extractBytesAtOffset4, extractBytesAtOffset5);
        } catch (ClassNotFoundException e2) {
            throw new IOException("cannot parse BDSStateMap: " + e2.getMessage());
        }
    }

    public static PrivateKeyInfo createPrivateKeyInfo(AsymmetricKeyParameter asymmetricKeyParameter, ASN1Set aSN1Set) {
        if (asymmetricKeyParameter instanceof QTESLAPrivateKeyParameters) {
            QTESLAPrivateKeyParameters qTESLAPrivateKeyParameters = (QTESLAPrivateKeyParameters) asymmetricKeyParameter;
            return new PrivateKeyInfo(Utils.qTeslaLookupAlgID(qTESLAPrivateKeyParameters.getSecurityCategory()), new DEROctetString(qTESLAPrivateKeyParameters.getSecret()), aSN1Set);
        }
        if (asymmetricKeyParameter instanceof SPHINCSPrivateKeyParameters) {
            SPHINCSPrivateKeyParameters sPHINCSPrivateKeyParameters = (SPHINCSPrivateKeyParameters) asymmetricKeyParameter;
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.sphincs256, new SPHINCS256KeyParams(Utils.sphincs256LookupTreeAlgID(sPHINCSPrivateKeyParameters.getTreeDigest()))), new DEROctetString(sPHINCSPrivateKeyParameters.getKeyData()));
        }
        if (asymmetricKeyParameter instanceof NHPrivateKeyParameters) {
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PQCObjectIdentifiers.newHope);
            short[] secData = ((NHPrivateKeyParameters) asymmetricKeyParameter).getSecData();
            byte[] bArr = new byte[secData.length * 2];
            for (int i2 = 0; i2 != secData.length; i2++) {
                Pack.shortToLittleEndian(secData[i2], bArr, i2 * 2);
            }
            return new PrivateKeyInfo(algorithmIdentifier, new DEROctetString(bArr));
        }
        if (asymmetricKeyParameter instanceof LMSPrivateKeyParameters) {
            LMSPrivateKeyParameters lMSPrivateKeyParameters = (LMSPrivateKeyParameters) asymmetricKeyParameter;
            byte[] build = Composer.compose().u32str(1).bytes(lMSPrivateKeyParameters).build();
            return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_hss_lms_hashsig), new DEROctetString(build), aSN1Set, Composer.compose().u32str(1).bytes(lMSPrivateKeyParameters.getPublicKey()).build());
        }
        if (asymmetricKeyParameter instanceof HSSPrivateKeyParameters) {
            HSSPrivateKeyParameters hSSPrivateKeyParameters = (HSSPrivateKeyParameters) asymmetricKeyParameter;
            byte[] build2 = Composer.compose().u32str(hSSPrivateKeyParameters.getL()).bytes(hSSPrivateKeyParameters).build();
            return new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.id_alg_hss_lms_hashsig), new DEROctetString(build2), aSN1Set, Composer.compose().u32str(hSSPrivateKeyParameters.getL()).bytes(hSSPrivateKeyParameters.getPublicKey().getLMSPublicKey()).build());
        }
        if (asymmetricKeyParameter instanceof XMSSPrivateKeyParameters) {
            XMSSPrivateKeyParameters xMSSPrivateKeyParameters = (XMSSPrivateKeyParameters) asymmetricKeyParameter;
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.xmss, new XMSSKeyParams(xMSSPrivateKeyParameters.getParameters().getHeight(), Utils.xmssLookupTreeAlgID(xMSSPrivateKeyParameters.getTreeDigest()))), xmssCreateKeyStructure(xMSSPrivateKeyParameters), aSN1Set);
        }
        if (asymmetricKeyParameter instanceof XMSSMTPrivateKeyParameters) {
            XMSSMTPrivateKeyParameters xMSSMTPrivateKeyParameters = (XMSSMTPrivateKeyParameters) asymmetricKeyParameter;
            return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.xmss_mt, new XMSSMTKeyParams(xMSSMTPrivateKeyParameters.getParameters().getHeight(), xMSSMTPrivateKeyParameters.getParameters().getLayers(), Utils.xmssLookupTreeAlgID(xMSSMTPrivateKeyParameters.getTreeDigest()))), xmssmtCreateKeyStructure(xMSSMTPrivateKeyParameters), aSN1Set);
        }
        if (!(asymmetricKeyParameter instanceof McElieceCCA2PrivateKeyParameters)) {
            throw new IOException("key parameters not recognized");
        }
        McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = (McElieceCCA2PrivateKeyParameters) asymmetricKeyParameter;
        return new PrivateKeyInfo(new AlgorithmIdentifier(PQCObjectIdentifiers.mcElieceCca2), new McElieceCCA2PrivateKey(mcElieceCCA2PrivateKeyParameters.getN(), mcElieceCCA2PrivateKeyParameters.getK(), mcElieceCCA2PrivateKeyParameters.getField(), mcElieceCCA2PrivateKeyParameters.getGoppaPoly(), mcElieceCCA2PrivateKeyParameters.getP(), Utils.getAlgorithmIdentifier(mcElieceCCA2PrivateKeyParameters.getDigest())));
    }
}
