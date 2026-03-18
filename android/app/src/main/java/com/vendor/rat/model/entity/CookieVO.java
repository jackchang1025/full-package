package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.CookieVO

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

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

    public CookieVO(String name, String value, Long expiresAt, String domain, String path,
                    Boolean secure, Boolean httpOnly, Boolean persistent, Boolean hostOnly) {
        this.name = name;
        this.value = value;
        this.expiresAt = expiresAt;
        this.domain = domain;
        this.path = path;
        this.secure = secure;
        this.httpOnly = httpOnly;
        this.persistent = persistent;
        this.hostOnly = hostOnly;
    }

    @Override
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

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setExpiresAt(Long expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setHostOnly(Boolean hostOnly) {
        this.hostOnly = hostOnly;
    }

    public void setHttpOnly(Boolean httpOnly) {
        this.httpOnly = httpOnly;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPersistent(Boolean persistent) {
        this.persistent = persistent;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @NonNull
    @Override
    public String toString() {
        return "CookieVO{name='" + name + "', value='" + value + "', expiresAt=" + expiresAt
                + ", domain='" + domain + "', path='" + path + "', secure=" + secure
                + ", httpOnly=" + httpOnly + ", persistent=" + persistent
                + ", hostOnly=" + hostOnly + '}';
    }
}
