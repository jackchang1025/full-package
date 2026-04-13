package org.bouncycastle.jsse.util;

import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/* loaded from: classes.dex */
public class URLConnectionUtil {
    protected final SSLSocketFactory sslSocketFactory;

    public URLConnectionUtil() {
        this(null);
    }

    public URLConnection configureConnection(URL url, URLConnection uRLConnection) {
        if (!(uRLConnection instanceof HttpsURLConnection)) {
            return uRLConnection;
        }
        HttpsURLConnection httpsURLConnection = (HttpsURLConnection) uRLConnection;
        SSLSocketFactory sSLSocketFactory = this.sslSocketFactory;
        if (sSLSocketFactory == null) {
            sSLSocketFactory = httpsURLConnection.getSSLSocketFactory();
        }
        httpsURLConnection.setSSLSocketFactory(createSSLSocketFactory(sSLSocketFactory, url));
        return httpsURLConnection;
    }

    public SSLSocketFactory createSSLSocketFactory(SSLSocketFactory sSLSocketFactory, URL url) {
        return new SNISocketFactory(sSLSocketFactory, url);
    }

    public URLConnection openConnection(URL url) {
        return configureConnection(url, url.openConnection());
    }

    public InputStream openInputStream(URL url) {
        return openConnection(url).getInputStream();
    }

    public URLConnectionUtil(SSLSocketFactory sSLSocketFactory) {
        this.sslSocketFactory = sSLSocketFactory;
    }

    public URLConnection openConnection(URL url, Proxy proxy) {
        return configureConnection(url, url.openConnection(proxy));
    }
}
