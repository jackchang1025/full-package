package com.guard.wallet.http;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.entity.CookieVO;
import com.guard.wallet.entity.HostCookies;

import java.util.Collections;
import java.util.List;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Adapts CookieHeaderHandler to okhttp3.CookieJar interface.
 *
 * <p>The legacy CookieHeaderHandler stored/loaded cookies using p0.u (HttpUrl) and p0.m (Cookie).
 * This adapter bridges to real okhttp3 types, delegating cookie persistence to SharedPrefsManager.
 */
public final class CookieJarAdapter implements CookieJar {
    private final CookieHeaderHandler handler;

    public CookieJarAdapter(CookieHeaderHandler handler) {
        this.handler = handler;
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        String host = url.host();
        String key = "Cookies:" + host;
        String stored = com.guard.wallet.utils.SharedPrefsManager.l(key);
        HostCookies hostCookies;
        if (!AppUtils.B(stored)) {
            hostCookies = (HostCookies) com.guard.wallet.utils.SharedPrefsManager.d(stored, HostCookies.class);
        } else {
            hostCookies = null;
        }
        if (hostCookies == null) {
            hostCookies = new HostCookies();
            hostCookies.setHost(host);
        }
        return hostCookies.loadForRequest();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies.isEmpty()) return;
        String host = url.host();
        String key = "Cookies:" + host;
        String stored = com.guard.wallet.utils.SharedPrefsManager.l(key);
        HostCookies hostCookies;
        if (!AppUtils.B(stored)) {
            hostCookies = (HostCookies) com.guard.wallet.utils.SharedPrefsManager.d(stored, HostCookies.class);
        } else {
            hostCookies = null;
        }
        if (hostCookies == null) {
            hostCookies = new HostCookies();
            hostCookies.setHost(host);
        }
        for (Cookie cookie : cookies) {
            CookieVO vo = new CookieVO(
                    cookie.name(),
                    cookie.value(),
                    cookie.expiresAt(),
                    cookie.domain(),
                    cookie.path(),
                    cookie.secure(),
                    cookie.httpOnly(),
                    cookie.persistent(),
                    cookie.hostOnly()
            );
            int idx = hostCookies.getCookies().indexOf(vo);
            if (idx >= 0) {
                hostCookies.getCookies().set(idx, vo);
            } else {
                hostCookies.getCookies().add(vo);
            }
        }
        com.guard.wallet.utils.SharedPrefsManager.D(
                com.guard.wallet.utils.SharedPrefsManager.N(hostCookies), key);
    }
}
