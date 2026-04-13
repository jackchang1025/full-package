package p013p;

import a1.AbstractC0026q;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* renamed from: p.b */
/* loaded from: classes.dex */
public abstract class AbstractC0857b {

    /* renamed from: a */
    public static final ConcurrentHashMap f1677a = new ConcurrentHashMap();

    /* JADX WARN: Removed duplicated region for block: B:12:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f5  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m1240a(String str, String str2) {
        long headerFieldLong;
        boolean equals;
        long j2;
        if (!AbstractC0026q.m151B(str)) {
            ConcurrentHashMap concurrentHashMap = f1677a;
            if (!concurrentHashMap.containsKey(str) && !AbstractC0026q.m151B(str2)) {
                if (!AbstractC0026q.m151B(str)) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("HEAD");
                        headerFieldLong = httpURLConnection.getHeaderFieldLong("Content-Length", 0L);
                        equals = Objects.equals(httpURLConnection.getHeaderField("Accept-Ranges"), "bytes");
                        httpURLConnection.disconnect();
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("DownloadUtils", e2);
                        concurrentHashMap.remove(str);
                    }
                    if (headerFieldLong > 0 && equals) {
                        j2 = headerFieldLong;
                        if (j2 > 0) {
                            return m1241b(str, str2);
                        }
                        AbstractC0026q.m181n(str2);
                        long j3 = j2 / 2097152;
                        if (j2 % 2097152 > 0) {
                            j3++;
                        }
                        long j4 = j3;
                        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool((int) j4);
                        LinkedList linkedList = new LinkedList();
                        int i2 = 0;
                        while (true) {
                            long j5 = i2;
                            if (j5 >= j4) {
                                break;
                            }
                            long j6 = j5 * 2097152;
                            long j7 = (j6 + 2097152) - 1;
                            long j8 = j2 - 1;
                            if (j7 <= j8) {
                                j8 = j7;
                            }
                            LinkedList linkedList2 = linkedList;
                            linkedList2.add(newFixedThreadPool.submit(new CallableC0858c(str, j6, j8, str2)));
                            i2++;
                            linkedList = linkedList2;
                            j4 = j4;
                        }
                        long j9 = j4;
                        LinkedList linkedList3 = linkedList;
                        long j10 = 0;
                        while (!linkedList3.isEmpty()) {
                            ListIterator listIterator = linkedList3.listIterator();
                            while (listIterator.hasNext()) {
                                Future future = (Future) listIterator.next();
                                if (future.isDone()) {
                                    try {
                                        if (((Boolean) future.get()).booleanValue()) {
                                            j10++;
                                        }
                                    } catch (Exception e3) {
                                        AbstractC0026q.m186s("DownloadUtils", e3);
                                    }
                                    listIterator.remove();
                                }
                            }
                        }
                        concurrentHashMap.remove(str);
                        return j10 == j9;
                    }
                }
                j2 = 0;
                if (j2 > 0) {
                }
            }
        }
        return false;
    }

    /* renamed from: b */
    public static boolean m1241b(String str, String str2) {
        if (!AbstractC0026q.m151B(str)) {
            ConcurrentHashMap concurrentHashMap = f1677a;
            if (!concurrentHashMap.containsKey(str) && !AbstractC0026q.m151B(str2)) {
                try {
                    concurrentHashMap.put(str, Long.valueOf(System.currentTimeMillis()));
                    InputStream openStream = new URL(str).openStream();
                    FileOutputStream fileOutputStream = AbstractC0026q.m190w(str2) ? new FileOutputStream(str2, false) : AbstractC0026q.m179l(str2) ? new FileOutputStream(str2, true) : null;
                    if (fileOutputStream != null) {
                        byte[] bArr = new byte[1024];
                        while (true) {
                            int read = openStream.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    concurrentHashMap.remove(str);
                    return true;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("DownloadUtils", e2);
                    concurrentHashMap.remove(str);
                }
            }
        }
        return false;
    }
}
