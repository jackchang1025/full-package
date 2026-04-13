package org.bouncycastle.tls;

import java.io.IOException;
import org.bouncycastle.tls.crypto.TlsCrypto;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.tls.crypto.TlsMAC;
import org.bouncycastle.tls.crypto.TlsMACOutputStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class DTLSVerifier {
    private final TlsMAC cookieMAC;
    private final TlsMACOutputStream cookieMACOutputStream;

    public DTLSVerifier(TlsCrypto tlsCrypto) {
        TlsMAC createCookieMAC = createCookieMAC(tlsCrypto);
        this.cookieMAC = createCookieMAC;
        this.cookieMACOutputStream = new TlsMACOutputStream(createCookieMAC);
    }

    private static TlsMAC createCookieMAC(TlsCrypto tlsCrypto) {
        TlsHMAC createHMAC = tlsCrypto.createHMAC(3);
        int macLength = createHMAC.getMacLength();
        byte[] bArr = new byte[macLength];
        tlsCrypto.getSecureRandom().nextBytes(bArr);
        createHMAC.setKey(bArr, 0, macLength);
        return createHMAC;
    }

    public synchronized DTLSRequest verifyRequest(byte[] bArr, byte[] bArr2, int i2, int i3, DatagramSender datagramSender) {
        TlsMAC tlsMAC;
        boolean z2 = true;
        try {
            this.cookieMAC.update(bArr, 0, bArr.length);
            DTLSRequest readClientRequest = DTLSReliableHandshake.readClientRequest(bArr2, i2, i3, this.cookieMACOutputStream);
            if (readClientRequest != null) {
                byte[] calculateMAC = this.cookieMAC.calculateMAC();
                try {
                    if (Arrays.constantTimeAreEqual(calculateMAC, readClientRequest.getClientHello().getCookie())) {
                        return readClientRequest;
                    }
                    DTLSReliableHandshake.sendHelloVerifyRequest(datagramSender, readClientRequest.getRecordSeq(), calculateMAC);
                    z2 = false;
                } catch (IOException unused) {
                    z2 = false;
                    if (z2) {
                        tlsMAC = this.cookieMAC;
                        tlsMAC.reset();
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    z2 = false;
                    if (z2) {
                        this.cookieMAC.reset();
                    }
                    throw th;
                }
            }
        } catch (IOException unused2) {
        } catch (Throwable th2) {
            th = th2;
        }
        if (z2) {
            tlsMAC = this.cookieMAC;
            tlsMAC.reset();
        }
        return null;
    }
}
