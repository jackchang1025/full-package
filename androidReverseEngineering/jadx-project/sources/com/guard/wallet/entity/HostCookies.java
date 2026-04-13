package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import p0.C0870l;
import p0.C0871m;
import q0.AbstractC0887c;

/* loaded from: classes.dex */
public class HostCookies implements Serializable {
    private List<CookieVO> cookies;
    private String host;

    public HostCookies() {
        this.cookies = new LinkedList();
    }

    public List<CookieVO> getCookies() {
        return this.cookies;
    }

    public String getHost() {
        return this.host;
    }

    public List<C0871m> loadForRequest() {
        LinkedList linkedList = new LinkedList();
        List<CookieVO> list = this.cookies;
        if (list != null && !list.isEmpty()) {
            for (CookieVO cookieVO : this.cookies) {
                C0870l c0870l = new C0870l();
                String name = cookieVO.getName();
                if (name == null) {
                    throw new NullPointerException("name == null");
                }
                if (!name.trim().equals(name)) {
                    throw new IllegalArgumentException("name is not trimmed");
                }
                c0870l.f1852a = name;
                String value = cookieVO.getValue();
                if (value == null) {
                    throw new NullPointerException("value == null");
                }
                if (!value.trim().equals(value)) {
                    throw new IllegalArgumentException("value is not trimmed");
                }
                c0870l.f1853b = value;
                String domain = cookieVO.getDomain();
                if (domain == null) {
                    throw new NullPointerException("domain == null");
                }
                String m1304a = AbstractC0887c.m1304a(domain);
                if (m1304a == null) {
                    throw new IllegalArgumentException("unexpected domain: ".concat(domain));
                }
                c0870l.f1855d = m1304a;
                c0870l.f1860i = false;
                String path = cookieVO.getPath();
                if (!path.startsWith("/")) {
                    throw new IllegalArgumentException("path must start with '/'");
                }
                c0870l.f1856e = path;
                long longValue = cookieVO.getExpiresAt().longValue();
                if (longValue <= 0) {
                    longValue = Long.MIN_VALUE;
                }
                if (longValue > 253402300799999L) {
                    longValue = 253402300799999L;
                }
                c0870l.f1854c = longValue;
                c0870l.f1859h = true;
                if (cookieVO.getSecure().booleanValue()) {
                    c0870l.f1857f = true;
                }
                if (cookieVO.getHttpOnly().booleanValue()) {
                    c0870l.f1858g = true;
                }
                if (cookieVO.getHostOnly().booleanValue()) {
                    c0870l.f1858g = true;
                }
                linkedList.add(new C0871m(c0870l));
            }
        }
        return linkedList;
    }

    public void setCookies(List<CookieVO> list) {
        this.cookies = list;
    }

    public void setHost(String str) {
        this.host = str;
    }

    @NonNull
    public String toString() {
        return "HostCookies{host='" + this.host + "', cookies=" + this.cookies + '}';
    }

    public HostCookies(String str, List<CookieVO> list) {
        new LinkedList();
        this.host = str;
        this.cookies = list;
    }
}
