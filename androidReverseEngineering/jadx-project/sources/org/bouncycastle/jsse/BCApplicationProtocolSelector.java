package org.bouncycastle.jsse;

import java.util.List;

/* loaded from: classes.dex */
public interface BCApplicationProtocolSelector<T> {
    String select(T t2, List<String> list);
}
