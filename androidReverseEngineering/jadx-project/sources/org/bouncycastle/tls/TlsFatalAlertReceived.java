package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class TlsFatalAlertReceived extends TlsException {
    protected short alertDescription;

    public TlsFatalAlertReceived(short s2) {
        super(AlertDescription.getText(s2));
        this.alertDescription = s2;
    }

    public short getAlertDescription() {
        return this.alertDescription;
    }
}
