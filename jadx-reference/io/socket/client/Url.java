package io.socket.client;

import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import p000.AbstractC0003a2;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class Url {
    private static Pattern PATTERN_AUTHORITY = Pattern.compile("^(.*@)?([^:]+)(:\\d+)?$");

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    public static class ParsedURI {

        /* renamed from: id */
        public final String f57210id;
        public final URI uri;

        public ParsedURI(URI uri, String str) {
            this.uri = uri;
            this.f57210id = str;
        }
    }

    private Url() {
    }

    private static String extractHostFromAuthorityPart(String str) {
        if (str == null) {
            throw new RuntimeException("unable to parse the host from the authority");
        }
        Matcher matcher = PATTERN_AUTHORITY.matcher(str);
        if (matcher.matches()) {
            return matcher.group(2);
        }
        throw new RuntimeException("unable to parse the host from the authority");
    }

    public static ParsedURI parse(URI uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !scheme.matches("^https?|wss?$")) {
            scheme = "https";
        }
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equals(scheme) || "ws".equals(scheme)) {
                port = 80;
            } else if ("https".equals(scheme) || "wss".equals(scheme)) {
                port = 443;
            }
        }
        String rawPath = uri.getRawPath();
        if (rawPath == null || rawPath.length() == 0) {
            rawPath = "/";
        }
        String rawUserInfo = uri.getRawUserInfo();
        String rawQuery = uri.getRawQuery();
        String rawFragment = uri.getRawFragment();
        String host = uri.getHost();
        if (host == null) {
            host = extractHostFromAuthorityPart(uri.getRawAuthority());
        }
        StringBuilder sbM39c0 = AbstractC0003a2.m39c0(scheme, "://");
        sbM39c0.append(rawUserInfo != null ? rawUserInfo.concat("@") : "");
        sbM39c0.append(host);
        sbM39c0.append(port != -1 ? tz0.m214802a2(port, ":") : "");
        sbM39c0.append(rawPath);
        sbM39c0.append(rawQuery != null ? "?".concat(rawQuery) : "");
        sbM39c0.append(rawFragment != null ? "#".concat(rawFragment) : "");
        return new ParsedURI(URI.create(sbM39c0.toString()), scheme + "://" + host + ":" + port);
    }
}
