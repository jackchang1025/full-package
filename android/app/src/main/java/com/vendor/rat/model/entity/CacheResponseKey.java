package com.vendor.rat.model.entity;

// ADAPT: vendor = com.guard.wallet.entity.CacheResponseKey

import androidx.annotation.NonNull;
import java.io.Serializable;
import java.util.Objects;

public class CacheResponseKey implements Serializable {
    private Long batchId;
    private String subscribeId;

    public CacheResponseKey() {
    }

    public CacheResponseKey(String subscribeId, Long batchId) {
        this.subscribeId = subscribeId;
        this.batchId = batchId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CacheResponseKey cacheResponseKey = (CacheResponseKey) obj;
        return this.subscribeId.equals(cacheResponseKey.subscribeId)
                && this.batchId.equals(cacheResponseKey.batchId);
    }

    public Long getBatchId() {
        return this.batchId;
    }

    public String getSubscribeId() {
        return this.subscribeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.subscribeId, this.batchId);
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    @NonNull
    @Override
    public String toString() {
        return "CacheResponseKey{subscribeId='" + subscribeId + "', batchId='" + batchId + "'}";
    }
}
