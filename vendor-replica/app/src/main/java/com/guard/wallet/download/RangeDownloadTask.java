package com.guard.wallet.download;

import com.guard.wallet.core.AppUtils;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * 分片下载 Callable。
 * 使用 HTTP Range 请求下载指定字节范围，写入 RandomAccessFile 对应偏移。
 *
 * vendor 原始路径: p/c.java
 */
public final class RangeDownloadTask implements Callable {
    public final String a;
    public final String b;
    public final long c;
    public final long d;

    public RangeDownloadTask(String url, long start, long end, String filePath) {
        this.a = url;
        this.b = filePath;
        this.c = start;
        this.d = end;
    }

    @Override
    public final Object call() {
        String urlStr = this.a;
        if (AppUtils.B(urlStr)) return Boolean.FALSE;
        long endPos = this.d;
        long startPos = this.c;
        if (endPos < startPos) return Boolean.FALSE;
        String filePath = this.b;
        if (AppUtils.B(filePath)) return Boolean.FALSE;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
            conn.setRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)");
            conn.setRequestProperty("Connection", "Keep-Alive");
            InputStream input = conn.getInputStream();
            RandomAccessFile raf = new RandomAccessFile(filePath, "rw");
            raf.seek(startPos);
            byte[] buffer = new byte[4096];
            long totalRead = 0L;
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                raf.write(buffer, 0, bytesRead);
                totalRead += bytesRead;
            }
            input.close();
            raf.close();
            conn.disconnect();
            return totalRead == endPos - startPos + 1L ? Boolean.TRUE : Boolean.FALSE;
        } catch (Exception ex) {
            AppUtils.s("SliceDownloadCallable", ex);
        }
        return Boolean.FALSE;
    }
}
