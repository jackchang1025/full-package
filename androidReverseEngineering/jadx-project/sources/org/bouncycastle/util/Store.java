package org.bouncycastle.util;

import java.util.Collection;

/* loaded from: classes.dex */
public interface Store<T> {
    Collection<T> getMatches(Selector<T> selector);
}
