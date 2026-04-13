package org.bouncycastle.tsp.ers;

import java.util.ArrayList;
import org.bouncycastle.asn1.tsp.PartialHashtree;
import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public class BinaryTreeRootCalculator implements ERSRootNodeCalculator {
    @Override // org.bouncycastle.tsp.ers.ERSRootNodeCalculator
    public byte[] computeRootHash(DigestCalculator digestCalculator, PartialHashtree[] partialHashtreeArr) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 <= partialHashtreeArr.length - 2; i2 += 2) {
            arrayList.add(ERSUtil.calculateBranchHash(digestCalculator, ERSUtil.computeNodeHash(digestCalculator, partialHashtreeArr[i2]), ERSUtil.computeNodeHash(digestCalculator, partialHashtreeArr[i2 + 1])));
        }
        if (partialHashtreeArr.length % 2 == 1) {
            arrayList.add(ERSUtil.computeNodeHash(digestCalculator, partialHashtreeArr[partialHashtreeArr.length - 1]));
        }
        while (true) {
            ArrayList arrayList2 = new ArrayList((arrayList.size() + 1) / 2);
            for (int i3 = 0; i3 <= arrayList.size() - 2; i3 += 2) {
                arrayList2.add(ERSUtil.calculateBranchHash(digestCalculator, (byte[]) arrayList.get(i3), (byte[]) arrayList.get(i3 + 1)));
            }
            if (arrayList.size() % 2 == 1) {
                arrayList2.add(arrayList.get(arrayList.size() - 1));
            }
            if (arrayList2.size() <= 1) {
                return (byte[]) arrayList2.get(0);
            }
            arrayList = arrayList2;
        }
    }
}
