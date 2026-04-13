package p013p;

import a1.AbstractC0026q;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/* renamed from: p.c */
/* loaded from: classes.dex */
public final class CallableC0858c implements Callable {

    /* renamed from: a */
    public final String f1678a;

    /* renamed from: b */
    public final String f1679b;

    /* renamed from: c */
    public final long f1680c;

    /* renamed from: d */
    public final long f1681d;

    public CallableC0858c(String str, long j2, long j3, String str2) {
        this.f1678a = str;
        this.f1679b = str2;
        this.f1680c = j2;
        this.f1681d = j3;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        String str = this.f1678a;
        if (!AbstractC0026q.m151B(str)) {
            long j2 = this.f1681d;
            long j3 = this.f1680c;
            if (j2 >= j3) {
                String str2 = this.f1679b;
                if (!AbstractC0026q.m151B(str2)) {
                    try {
                        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                        httpURLConnection.setConnectTimeout(5000);
                        httpURLConnection.setRequestMethod("GET");
                        httpURLConnection.setRequestProperty("Range", "bytes=" + j3 + "-" + j2);
                        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
                        httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                        InputStream inputStream = httpURLConnection.getInputStream();
                        RandomAccessFile randomAccessFile = new RandomAccessFile(str2, "rw");
                        randomAccessFile.seek(j3);
                        byte[] bArr = new byte[4096];
                        long j4 = 0;
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            randomAccessFile.write(bArr, 0, read);
                            j4 += read;
                        }
                        inputStream.close();
                        randomAccessFile.close();
                        httpURLConnection.disconnect();
                        if (j4 == (j2 - j3) + 1) {
                            return Boolean.TRUE;
                        }
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("SliceDownloadCallable", e2);
                    }
                }
            }
        }
        return Boolean.FALSE;
    }
}
