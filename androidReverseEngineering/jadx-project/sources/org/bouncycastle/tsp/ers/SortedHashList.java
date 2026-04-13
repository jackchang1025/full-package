package org.bouncycastle.tsp.ers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class SortedHashList {
    private static final Comparator<byte[]> hashComp = new ByteArrayComparator();
    private final LinkedList<byte[]> baseList = new LinkedList<>();

    public void add(byte[] bArr) {
        if (this.baseList.size() == 0 || hashComp.compare(bArr, this.baseList.get(0)) < 0) {
            this.baseList.addFirst(bArr);
            return;
        }
        int i2 = 1;
        while (i2 < this.baseList.size() && hashComp.compare(this.baseList.get(i2), bArr) <= 0) {
            i2++;
        }
        if (i2 == this.baseList.size()) {
            this.baseList.add(bArr);
        } else {
            this.baseList.add(i2, bArr);
        }
    }

    public List<byte[]> toList() {
        return new ArrayList(this.baseList);
    }
}
