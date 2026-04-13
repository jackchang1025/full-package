package com.guard.wallet.entity;

import android.util.Log;
import androidx.annotation.NonNull;
import java.io.Serializable;
import java.net.IDN;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Stores cookies for a specific host.
 * The loadForRequest() method converts CookieVO list to OkHttp Cookie objects.
 *
 * Vendor: com.guard.wallet.entity.HostCookies (127 lines)
 * Vendor uses obfuscated p0.l (Cookie.Builder) / p0.m (Cookie).
 */
public class HostCookies implements Serializable {
    private static final String TAG = "HostCookies";
    private List<CookieVO> cookies;
    private String host;

    public HostCookies() {
        this.cookies = new LinkedList<>();
    }

    public HostCookies(String host, List<CookieVO> cookies) {
        this.host = host;
        this.cookies = cookies;
    }

    public List<CookieVO> getCookies() {
        return this.cookies;
    }

    public String getHost() {
        return this.host;
    }

    /**
     * Converts stored CookieVO list to OkHttp Cookie objects for use in requests.
     *
     * Vendor logic:
     * - Validates name, value, domain, path
     * - Converts domain via IDN.toASCII
     * - Clamps expiresAt to [Long.MIN_VALUE, 253402300799999]
     * - Sets persistent=true, secure/httpOnly/hostOnly flags from CookieVO
     */
    public List<Cookie> loadForRequest() {
        LinkedList<Cookie> result = new LinkedList<>();
        List<CookieVO> cookieList = this.cookies;
        if (cookieList == null || cookieList.isEmpty()) {
            return result;
        }

        for (CookieVO vo : cookieList) {
            try {
                String name = vo.getName();
                if (name == null) {
                    throw new NullPointerException("name == null");
                }
                if (!name.trim().equals(name)) {
                    throw new IllegalArgumentException("name is not trimmed");
                }

                String value = vo.getValue();
                if (value == null) {
                    throw new NullPointerException("value == null");
                }
                if (!value.trim().equals(value)) {
                    throw new IllegalArgumentException("value is not trimmed");
                }

                String domain = vo.getDomain();
                if (domain == null) {
                    throw new NullPointerException("domain == null");
                }

                // Canonicalize domain via IDN
                String canonicalDomain;
                try {
                    canonicalDomain = IDN.toASCII(domain);
                } catch (Exception e) {
                    throw new IllegalArgumentException("unexpected domain: " + domain);
                }
                if (canonicalDomain == null) {
                    throw new IllegalArgumentException("unexpected domain: " + domain);
                }

                String path = vo.getPath();
                if (path == null || !path.startsWith("/")) {
                    throw new IllegalArgumentException("path must start with '/'");
                }

                // Clamp expiresAt
                long expiresAt = vo.getExpiresAt() != null ? vo.getExpiresAt() : 0L;
                if (expiresAt <= 0L) {
                    expiresAt = Long.MIN_VALUE;
                }
                if (expiresAt > 253402300799999L) {
                    expiresAt = 253402300799999L;
                }

                // Build OkHttp Cookie using the public API
                // We need a dummy HttpUrl for the builder
                String scheme = Boolean.TRUE.equals(vo.getSecure()) ? "https" : "http";
                HttpUrl url = HttpUrl.parse(scheme + "://" + canonicalDomain + path);
                if (url == null) {
                    continue;
                }

                Cookie.Builder builder = new Cookie.Builder()
                        .name(name)
                        .value(value)
                        .path(path)
                        .expiresAt(expiresAt);

                if (Boolean.TRUE.equals(vo.getHostOnly())) {
                    builder.hostOnlyDomain(canonicalDomain);
                } else {
                    builder.domain(canonicalDomain);
                }

                if (Boolean.TRUE.equals(vo.getSecure())) {
                    builder.secure();
                }
                if (Boolean.TRUE.equals(vo.getHttpOnly())) {
                    builder.httpOnly();
                }

                result.add(builder.build());
            } catch (Exception e) {
                Log.w(TAG, "loadForRequest: skip invalid cookie", e);
            }
        }

        return result;
    }

    public void setCookies(List<CookieVO> cookies) {
        this.cookies = cookies;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @NonNull
    @Override
    public String toString() {
        return "HostCookies{host='" + this.host + "', cookies=" + this.cookies + '}';
    }
}
