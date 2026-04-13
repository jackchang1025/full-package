package com.guard.wallet.util;

import com.guard.wallet.req.ListenPropResponse;
import java.nio.ByteBuffer;
import java.util.Comparator;

/**
 * 多模式通用比较器。
 * vendor 原始路径: n/a.java
 *
 * <ul>
 *   <li>mode 0 — 按字符串长度升序比较</li>
 *   <li>mode 1 — 按 {@link ListenPropResponse#getTimestamp()} 升序比较</li>
 *   <li>mode 2+ — 按 ByteBuffer capacity 比较</li>
 * </ul>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class MultiModeComparator implements Comparator {
    public final int mode;

    public MultiModeComparator(int mode) {
        this.mode = mode;
    }

    @Override
    public final int compare(Object obj1, Object obj2) {
        switch (this.mode) {
            case 0:
                // 按字符串长度比较
                String s1 = (String) obj1;
                String s2 = (String) obj2;
                return Integer.compare(s1.length() - s2.length(), 0);
            case 1:
                // 按 ListenPropResponse.timestamp 比较
                ListenPropResponse r1 = (ListenPropResponse) obj1;
                ListenPropResponse r2 = (ListenPropResponse) obj2;
                return r1.getTimestamp().compareTo(r2.getTimestamp());
            default:
                // 按 ByteBuffer capacity 比较
                ByteBuffer b1 = (ByteBuffer) obj1;
                ByteBuffer b2 = (ByteBuffer) obj2;
                if (b1.capacity() != b2.capacity()) {
                    return b1.capacity() > b2.capacity() ? 1 : -1;
                }
                return 0;
        }
    }
}
