package org.bouncycastle.tsp.ers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.asn1.tsp.PartialHashtree;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class ERSUtil {
    private static final Comparator<byte[]> hashComp = new ByteArrayComparator();

    private ERSUtil() {
    }

    public static List<byte[]> buildHashList(DigestCalculator digestCalculator, List<ERSData> list) {
        SortedHashList sortedHashList = new SortedHashList();
        for (int i2 = 0; i2 != list.size(); i2++) {
            sortedHashList.add(list.get(i2).getHash(digestCalculator));
        }
        return sortedHashList.toList();
    }

    public static byte[] calculateBranchHash(DigestCalculator digestCalculator, byte[] bArr, byte[] bArr2) {
        return hashComp.compare(bArr, bArr2) <= 0 ? calculateDigest(digestCalculator, bArr, bArr2) : calculateDigest(digestCalculator, bArr2, bArr);
    }

    public static byte[] calculateDigest(DigestCalculator digestCalculator, InputStream inputStream) {
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            Streams.pipeAll(inputStream, outputStream);
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e2) {
            throw ExpUtil.createIllegalState(AbstractC0000a.m8d(e2, new StringBuilder("unable to calculate hash: ")), e2);
        }
    }

    public static byte[] computeNodeHash(DigestCalculator digestCalculator, PartialHashtree partialHashtree) {
        byte[][] values = partialHashtree.getValues();
        return values.length > 1 ? calculateDigest(digestCalculator, buildHashList(values).iterator()) : values[0];
    }

    public static List<byte[]> buildHashList(byte[][] bArr) {
        SortedHashList sortedHashList = new SortedHashList();
        for (int i2 = 0; i2 != bArr.length; i2++) {
            sortedHashList.add(bArr[i2]);
        }
        return sortedHashList.toList();
    }

    public static byte[] calculateBranchHash(DigestCalculator digestCalculator, byte[][] bArr) {
        return bArr.length == 2 ? calculateBranchHash(digestCalculator, bArr[0], bArr[1]) : calculateDigest(digestCalculator, buildHashList(bArr).iterator());
    }

    public static byte[] calculateDigest(DigestCalculator digestCalculator, Iterator<byte[]> it) {
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            while (it.hasNext()) {
                outputStream.write(it.next());
            }
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e2) {
            throw ExpUtil.createIllegalState(AbstractC0000a.m8d(e2, new StringBuilder("unable to calculate hash: ")), e2);
        }
    }

    public static byte[] calculateDigest(DigestCalculator digestCalculator, byte[] bArr) {
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(bArr);
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e2) {
            throw ExpUtil.createIllegalState(AbstractC0000a.m8d(e2, new StringBuilder("unable to calculate hash: ")), e2);
        }
    }

    public static byte[] calculateDigest(DigestCalculator digestCalculator, byte[] bArr, byte[] bArr2) {
        try {
            OutputStream outputStream = digestCalculator.getOutputStream();
            outputStream.write(bArr);
            outputStream.write(bArr2);
            outputStream.close();
            return digestCalculator.getDigest();
        } catch (IOException e2) {
            throw ExpUtil.createIllegalState(AbstractC0000a.m8d(e2, new StringBuilder("unable to calculate hash: ")), e2);
        }
    }
}
