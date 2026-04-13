package com.guard.wallet.http;

import android.text.TextUtils;

/**
 * HTTP 名值对（不可变，可克隆）。
 * 用于 QueryParameterMap.iterator() 将多值映射条目展平为迭代器元素。
 * 源自 vendor: i0/a.java
 */
public final class NameValuePair implements Cloneable {
    public final String name;
    public final String value;

    public NameValuePair(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        this.name = name;
        this.value = value;
    }

    @Override
    public final Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public final boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof NameValuePair) {
            NameValuePair other = (NameValuePair) obj;
            if (this.name.equals(other.name) && TextUtils.equals(this.value, other.value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode() ^ this.value.hashCode();
    }

    @Override
    public final String toString() {
        return this.name + "=" + this.value;
    }
}
