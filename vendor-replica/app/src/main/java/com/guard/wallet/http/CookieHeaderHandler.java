package com.guard.wallet.http;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.entity.CookieVO;
import com.guard.wallet.entity.HostCookies;
import com.koushikdutta.async.util.TaggedList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 多用途 Cookie/Header 处理器 — vendor http/h (300 lines).
 *
 * 根据 mode 参数承担不同职责:
 *   mode 0 = Cookie 管理 (CookieJar 桥接)
 *   mode 1 = Semaphore 等待器 (Future 阻塞等待)
 *   mode 4 = Header 构建器 (TaggedQueryMap 存储 headers)
 *   mode 8 = 限流器 (ConnectionPool 令牌桶)
 *   mode 9 = 集合操作 (LinkedHashSet)
 */
public final class CookieHeaderHandler {
    public final int mode;
    public Object delegate;

    public CookieHeaderHandler(int var1) {
        this.mode = var1;
        if (var1 == 4) {
            this.delegate = new TaggedQueryMap();
        } else if (var1 == 8) {
            this.delegate = new ConnectionPool();
        } else if (var1 == 9) {
            this.delegate = new LinkedHashSet();
        } else {
            this.delegate = new Semaphore(0);
        }
    }

    public CookieHeaderHandler(TimeUnit var1) {
        this.mode = 8;
        this.delegate = new ConnectionPool();
    }

    public final List<Cookie> loadCookiesForRequest(HttpUrl url) {
        String host = url.host();
        String var2 = com.guard.wallet.utils.SharedPrefsManager.l("Cookies:" + host);
        HostCookies var4;
        if (!AppUtils.B(var2)) {
            var4 = (HostCookies) com.guard.wallet.utils.SharedPrefsManager.d(var2, HostCookies.class);
        } else {
            var4 = null;
        }
        HostCookies var3 = var4;
        if (var4 == null) {
            var3 = new HostCookies();
            var3.setHost(host);
        }
        return var3.loadForRequest();
    }

    public final void saveCookiesFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (!cookies.isEmpty()) {
            String host = url.host();
            String var6 = "Cookies:" + host;
            String var4 = com.guard.wallet.utils.SharedPrefsManager.l(var6);
            HostCookies var10;
            if (!AppUtils.B(var4)) {
                var10 = (HostCookies) com.guard.wallet.utils.SharedPrefsManager.d(var4, HostCookies.class);
            } else {
                var10 = null;
            }
            HostCookies var5 = var10;
            if (var10 == null) {
                var5 = new HostCookies();
                var5.setHost(host);
            }
            for (Cookie cookie : cookies) {
                CookieVO var9 = new CookieVO(
                        cookie.name(), cookie.value(), cookie.expiresAt(),
                        cookie.domain(), cookie.path(), cookie.secure(),
                        cookie.httpOnly(), cookie.persistent(), cookie.hostOnly());
                int var3 = var5.getCookies().indexOf(var9);
                if (var3 >= 0) {
                    var5.getCookies().set(var3, var9);
                } else {
                    var5.getCookies().add(var9);
                }
            }
            com.guard.wallet.utils.SharedPrefsManager.D(com.guard.wallet.utils.SharedPrefsManager.N(var5), var6);
        }
    }

    /** 从集合中移除元素 (mode 9) */
    public final void removeFromSet(Object item) {
        synchronized (this) {
            ((Set) this.delegate).remove(item);
        }
    }

    public final void addHeader(String var1, String var2) {
        String var6 = var1.toLowerCase(Locale.US);
        QueryParameterMap var5 = (QueryParameterMap) this.delegate;
        List var4 = (List) var5.get(var6);
        List var3 = var4;
        if (var4 == null) {
            var3 = var5.createValueList();
            var5.put(var6, var3);
        }
        var3.add(var2);
        TaggedList var9 = (TaggedList) ((QueryParameterMap) this.delegate).get(var6);
        synchronized (var9) {
            if (var9.tag() == null) {
                var9.tag(var1);
            }
        }
    }

    public final void parseAndAddHeader(String var1) {
        if (var1 != null) {
            String[] var2 = var1.trim().split(":", 2);
            if (var2.length == 2) {
                this.addHeader(var2[0].trim(), var2[1].trim());
            } else {
                this.addHeader(var2[0].trim(), "");
            }
        }
    }

    public final String getHeaderValue(String var1) {
        return ((QueryParameterMap) this.delegate).getFirst(var1.toLowerCase(Locale.US));
    }

    public final void setHeader(String var1, String var2) {
        if (var2 == null || !var2.contains("\n") && !var2.contains("\r")) {
            String var4 = var1.toLowerCase(Locale.US);
            QueryParameterMap var3 = (QueryParameterMap) this.delegate;
            List var5 = var3.createValueList();
            var5.add(var2);
            var3.put(var4, var5);
            TaggedList var8 = (TaggedList) ((QueryParameterMap) this.delegate).get(var4);
            synchronized (var8) {
                if (var8.tag() == null) {
                    var8.tag(var1);
                }
            }
        } else {
            throw new IllegalArgumentException("value must not contain a new line or line feed");
        }
    }

    public final String buildRequestLine(String var1) {
        StringBuilder var3 = this.buildHeaderBlock();
        StringBuilder var2 = new StringBuilder();
        var2.append(var1);
        var2.append("\r\n");
        return var3.insert(0, var2.toString()).toString();
    }

    public final StringBuilder buildHeaderBlock() {
        StringBuilder var1 = new StringBuilder(256);
        if (this.mode == 4 && this.delegate instanceof QueryParameterMap) {
            for (Object rawKey : ((QueryParameterMap) this.delegate).keySet()) {
                String var3 = (String) rawKey;
                TaggedList var9 = (TaggedList) ((QueryParameterMap) this.delegate).get(var3);
                for (Object rawVal : var9) {
                    String var4 = (String) rawVal;
                    String var5;
                    synchronized (var9) {
                        var5 = (String) var9.tag();
                    }
                    var1.append(var5);
                    var1.append(": ");
                    var1.append(var4);
                    var1.append("\r\n");
                }
            }
        }
        var1.append("\r\n");
        return var1;
    }

    @Override
    public final String toString() {
        switch (this.mode) {
            case 4:
                return this.buildHeaderBlock().toString();
            default:
                return super.toString();
        }
    }
}
