package org.bouncycastle.tls;

/* loaded from: classes.dex */
public class TlsFatalAlert extends TlsException {
    protected short alertDescription;

    public TlsFatalAlert(short s2) {
        this(s2, (String) null);
    }

    private static String getMessage(short s2, String str) {
        String text = AlertDescription.getText(s2);
        if (str == null) {
            return text;
        }
        return text + "; " + str;
    }

    public short getAlertDescription() {
        return this.alertDescription;
    }

    public TlsFatalAlert(short s2, String str) {
        this(s2, str, null);
    }

    public TlsFatalAlert(short s2, String str, Throwable th) {
        super(getMessage(s2, str), th);
        this.alertDescription = s2;
    }

    public TlsFatalAlert(short s2, Throwable th) {
        this(s2, null, th);
    }
}
