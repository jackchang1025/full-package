package p000;

import java.util.Iterator;

/* loaded from: classes2.dex */
public interface l21 extends d70 {
    boolean add(String str);

    String get(int i);

    @Override // p000.d70, java.lang.Iterable
    /* synthetic */ Iterator iterator();

    int size();

    String[] toStringArray();

    String[] toStringArray(int i, int i2);
}
