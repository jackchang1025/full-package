package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.HostCookies
// ADAPT: loadForRequest() uses okhttp3.Cookie.Builder public API instead of vendor internal builder (p0.l/p0.m/q0.c)

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class HostCookies implements Serializable {
    private List<CookieVO> cookies;
    private String host;

    public HostCookies() {
        this.cookies = new LinkedList<>();
    }

    public HostCookies(String host, List<CookieVO> cookies) {
        new LinkedList<>();
        this.host = host;
        this.cookies = cookies;
    }

    public List<CookieVO> getCookies() {
        return this.cookies;
    }

    public String getHost() {
        return this.host;
    }

    public List<Cookie> loadForRequest() {
        LinkedList<Cookie> result = new LinkedList<>();
        List<CookieVO> list = this.cookies;
        if (list != null && !list.isEmpty()) {
            for (CookieVO cookieVO : this.cookies) {
                String name = cookieVO.getName();
                if (name == null) {
                    throw new NullPointerException("name == null");
                }
                if (!name.trim().equals(name)) {
                    throw new IllegalArgumentException("name is not trimmed");
                }
                String value = cookieVO.getValue();
                if (value == null) {
                    throw new NullPointerException("value == null");
                }
                if (!value.trim().equals(value)) {
                    throw new IllegalArgumentException("value is not trimmed");
                }
                String domain = cookieVO.getDomain();
                if (domain == null) {
                    throw new NullPointerException("domain == null");
                }
                String path = cookieVO.getPath();
                if (!path.startsWith("/")) {
                    throw new IllegalArgumentException("path must start with '/'");
                }
                long expiresAt = cookieVO.getExpiresAt().longValue();
                if (expiresAt <= 0) {
                    expiresAt = Long.MIN_VALUE;
                }
                if (expiresAt > 253402300799999L) {
                    expiresAt = 253402300799999L;
                }

                Cookie.Builder builder = new Cookie.Builder()
                        .name(name)
                        .value(value)
                        .domain(domain)
                        .path(path)
                        .expiresAt(expiresAt);
                if (cookieVO.getSecure() != null && cookieVO.getSecure().booleanValue()) {
                    builder.secure();
                }
                if (cookieVO.getHttpOnly() != null && cookieVO.getHttpOnly().booleanValue()) {
                    builder.httpOnly();
                }
                if (cookieVO.getHostOnly() != null && cookieVO.getHostOnly().booleanValue()) {
                    builder.hostOnlyDomain(domain);
                }
                result.add(builder.build());
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
        return "HostCookies{host='" + host + "', cookies=" + cookies + '}';
    }
}
