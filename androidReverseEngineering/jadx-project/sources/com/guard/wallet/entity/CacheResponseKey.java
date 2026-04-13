package com.guard.wallet.entity;

import android.support.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

/* loaded from: classes.dex */
public class CacheResponseKey implements Serializable {
    private Long batchId;
    private String subscribeId;

    public CacheResponseKey() {
    }

    public CacheResponseKey(String str, Long l2) {
        this.subscribeId = str;
        this.batchId = l2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CacheResponseKey cacheResponseKey = (CacheResponseKey) obj;
        return this.subscribeId.equals(cacheResponseKey.subscribeId) && this.batchId.equals(cacheResponseKey.batchId);
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public String getSubscribeId() {
        return this.subscribeId;
    }

    public int hashCode() {
        return Objects.hash(this.subscribeId, this.batchId);
    }

    public void setBatchId(Long l2) {
        this.batchId = l2;
    }

    public void setSubscribeId(String str) {
        this.subscribeId = str;
    }

    @NonNull
    public String toString() {
        return "CacheResponseKey{subscribeId='" + this.subscribeId + "', batchId='" + this.batchId + "'}";
    }
}
