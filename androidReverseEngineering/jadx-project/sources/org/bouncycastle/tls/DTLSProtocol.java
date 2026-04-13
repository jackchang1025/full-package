package org.bouncycastle.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public abstract class DTLSProtocol {
    public static void applyMaxFragmentLengthExtension(DTLSRecordLayer dTLSRecordLayer, short s2) {
        if (s2 >= 0) {
            if (!MaxFragmentLength.isValid(s2)) {
                throw new TlsFatalAlert((short) 80);
            }
            dTLSRecordLayer.setPlaintextLimit(1 << (s2 + 8));
        }
    }

    public static short evaluateMaxFragmentLengthExtension(boolean z2, Hashtable hashtable, Hashtable hashtable2, short s2) {
        short maxFragmentLengthExtension = TlsExtensionsUtils.getMaxFragmentLengthExtension(hashtable2);
        if (maxFragmentLengthExtension < 0 || (MaxFragmentLength.isValid(maxFragmentLengthExtension) && (z2 || maxFragmentLengthExtension == TlsExtensionsUtils.getMaxFragmentLengthExtension(hashtable)))) {
            return maxFragmentLengthExtension;
        }
        throw new TlsFatalAlert(s2);
    }

    public static byte[] generateCertificate(TlsContext tlsContext, Certificate certificate, OutputStream outputStream) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        certificate.encode(tlsContext, byteArrayOutputStream, outputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] generateSupplementalData(Vector vector) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsProtocol.writeSupplementalData(byteArrayOutputStream, vector);
        return byteArrayOutputStream.toByteArray();
    }

    public static void sendCertificateMessage(TlsContext tlsContext, DTLSReliableHandshake dTLSReliableHandshake, Certificate certificate, OutputStream outputStream) {
        SecurityParameters securityParametersHandshake = tlsContext.getSecurityParametersHandshake();
        if (securityParametersHandshake.getLocalCertificate() != null) {
            throw new TlsFatalAlert((short) 80);
        }
        if (certificate == null) {
            certificate = Certificate.EMPTY_CHAIN;
        }
        dTLSReliableHandshake.sendMessage((short) 11, generateCertificate(tlsContext, certificate, outputStream));
        securityParametersHandshake.localCertificate = certificate;
    }

    public static int validateSelectedCipherSuite(int i2, short s2) {
        int encryptionAlgorithm = TlsUtils.getEncryptionAlgorithm(i2);
        if (encryptionAlgorithm == -1 || encryptionAlgorithm == 1 || encryptionAlgorithm == 2) {
            throw new TlsFatalAlert(s2);
        }
        return i2;
    }

    public void processFinished(byte[] bArr, byte[] bArr2) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        byte[] readFully = TlsUtils.readFully(bArr2.length, byteArrayInputStream);
        TlsProtocol.assertEmpty(byteArrayInputStream);
        if (!Arrays.constantTimeAreEqual(bArr2, readFully)) {
            throw new TlsFatalAlert((short) 40);
        }
    }
}
