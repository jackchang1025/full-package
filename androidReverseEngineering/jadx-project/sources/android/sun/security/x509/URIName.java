package android.sun.security.x509;

import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class URIName implements GeneralNameInterface {
    private String host;
    private DNSName hostDNS;
    private IPAddressName hostIP;
    private URI uri;

    public URIName(DerValue derValue) {
        this(derValue.getIA5String());
    }

    public static URIName nameConstraint(DerValue derValue) {
        String iA5String = derValue.getIA5String();
        try {
            URI uri = new URI(iA5String);
            if (uri.getScheme() != null) {
                throw new IOException(AbstractC0000a.m15k("invalid URI name constraint (should not include scheme):", iA5String));
            }
            String schemeSpecificPart = uri.getSchemeSpecificPart();
            try {
                return new URIName(uri, schemeSpecificPart, schemeSpecificPart.charAt(0) == '.' ? new DNSName(schemeSpecificPart.substring(1)) : new DNSName(schemeSpecificPart));
            } catch (IOException e2) {
                throw ((IOException) new IOException(AbstractC0000a.m15k("invalid URI name constraint:", iA5String)).initCause(e2));
            }
        } catch (URISyntaxException e3) {
            throw ((IOException) new IOException(AbstractC0000a.m15k("invalid URI name constraint:", iA5String)).initCause(e3));
        }
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int constrains(GeneralNameInterface generalNameInterface) {
        if (generalNameInterface == null || generalNameInterface.getType() != 6) {
            return -1;
        }
        URIName uRIName = (URIName) generalNameInterface;
        String host = uRIName.getHost();
        if (host.equalsIgnoreCase(this.host)) {
            return 0;
        }
        Object hostObject = uRIName.getHostObject();
        if (this.hostDNS == null || !(hostObject instanceof DNSName)) {
            return 3;
        }
        boolean z2 = this.host.charAt(0) == '.';
        boolean z3 = host.charAt(0) == '.';
        int constrains = this.hostDNS.constrains((DNSName) hostObject);
        if (!z2 && !z3 && (constrains == 2 || constrains == 1)) {
            constrains = 3;
        }
        return (z2 == z3 || constrains != 0) ? constrains : z2 ? 2 : 1;
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public void encode(DerOutputStream derOutputStream) {
        derOutputStream.putIA5String(this.uri.toASCIIString());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof URIName) {
            return this.uri.equals(((URIName) obj).getURI());
        }
        return false;
    }

    public String getHost() {
        return this.host;
    }

    public Object getHostObject() {
        IPAddressName iPAddressName = this.hostIP;
        return iPAddressName != null ? iPAddressName : this.hostDNS;
    }

    public String getName() {
        return this.uri.toString();
    }

    public String getScheme() {
        return this.uri.getScheme();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int getType() {
        return 6;
    }

    public URI getURI() {
        return this.uri;
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    @Override // android.sun.security.x509.GeneralNameInterface
    public int subtreeDepth() {
        try {
            return new DNSName(this.host).subtreeDepth();
        } catch (IOException e2) {
            throw new UnsupportedOperationException(e2.getMessage());
        }
    }

    public String toString() {
        return "URIName: " + this.uri.toString();
    }

    public URIName(String str) {
        try {
            URI uri = new URI(str);
            this.uri = uri;
            if (uri.getScheme() == null) {
                throw new IOException(AbstractC0000a.m15k("URI name must include scheme:", str));
            }
            String host = this.uri.getHost();
            this.host = host;
            if (host != null) {
                if (host.charAt(0) == '[') {
                    String str2 = this.host;
                    try {
                        this.hostIP = new IPAddressName(str2.substring(1, str2.length() - 1));
                    } catch (IOException unused) {
                        throw new IOException(AbstractC0000a.m15k("invalid URI name (host portion is not a valid IPv6 address):", str));
                    }
                } else {
                    try {
                        try {
                            this.hostDNS = new DNSName(this.host);
                        } catch (Exception unused2) {
                            throw new IOException(AbstractC0000a.m15k("invalid URI name (host portion is not a valid DNS name, IPv4 address, or IPv6 address):", str));
                        }
                    } catch (IOException unused3) {
                        this.hostIP = new IPAddressName(this.host);
                    }
                }
            }
        } catch (URISyntaxException e2) {
            throw ((IOException) new IOException(AbstractC0000a.m15k("invalid URI name:", str)).initCause(e2));
        }
    }

    public URIName(URI uri, String str, DNSName dNSName) {
        this.uri = uri;
        this.host = str;
        this.hostDNS = dNSName;
    }
}
