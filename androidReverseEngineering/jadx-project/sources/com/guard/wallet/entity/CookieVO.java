package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes.dex */
public class CookieVO implements Serializable {
    private String domain;
    private Long expiresAt;
    private Boolean hostOnly;
    private Boolean httpOnly;
    private String name;
    private String path;
    private Boolean persistent;
    private Boolean secure;
    private String value;

    public CookieVO() {
    }

    public CookieVO(String str, String str2, Long l2, String str3, String str4, Boolean bool, Boolean bool2, Boolean bool3, Boolean bool4) {
        this.name = str;
        this.value = str2;
        this.expiresAt = l2;
        this.domain = str3;
        this.path = str4;
        this.secure = bool;
        this.httpOnly = bool2;
        this.persistent = bool3;
        this.hostOnly = bool4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.name.equals(((CookieVO) obj).name);
    }

    public String getDomain() {
        return this.domain;
    }

    public Long getExpiresAt() {
        return this.expiresAt;
    }

    public Boolean getHostOnly() {
        return this.hostOnly;
    }

    public Boolean getHttpOnly() {
        return this.httpOnly;
    }

    public String getName() {
        return this.name;
    }

    public String getPath() {
        return this.path;
    }

    public Boolean getPersistent() {
        return this.persistent;
    }

    public Boolean getSecure() {
        return this.secure;
    }

    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    public void setExpiresAt(Long l2) {
        this.expiresAt = l2;
    }

    public void setHostOnly(Boolean bool) {
        this.hostOnly = bool;
    }

    public void setHttpOnly(Boolean bool) {
        this.httpOnly = bool;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setPath(String str) {
        this.path = str;
    }

    public void setPersistent(Boolean bool) {
        this.persistent = bool;
    }

    public void setSecure(Boolean bool) {
        this.secure = bool;
    }

    public void setValue(String str) {
        this.value = str;
    }

    @NonNull
    public String toString() {
        return "CookieVO{name='" + this.name + "', value='" + this.value + "', expiresAt=" + this.expiresAt + ", domain='" + this.domain + "', path='" + this.path + "', secure=" + this.secure + ", httpOnly=" + this.httpOnly + ", persistent=" + this.persistent + ", hostOnly=" + this.hostOnly + '}';
    }
}
