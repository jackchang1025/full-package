package com.guard.wallet.download;

import com.guard.wallet.core.AppUtils;
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

/**
 * HTTP 断点续传下载管理器。
 * a() = 分片下载（支持 Range），失败回退到 b()。
 * b() = 简单流式下载。
 *
 * vendor 原始路径: p/b.java
 */
public abstract class DownloadManager {
    public static final ConcurrentHashMap a = new ConcurrentHashMap();

    public static boolean a(String var0, String var1) {
        if (AppUtils.B(var0)) return false;
        ConcurrentHashMap var16 = a;
        if (var16.containsKey(var0) || AppUtils.B(var1)) return false;

        long var5 = 0L;
        boolean supportsRange = false;
        if (!AppUtils.B(var0)) {
            try {
                URL url = new URL(var0);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("HEAD");
                var5 = conn.getHeaderFieldLong("Content-Length", 0L);
                supportsRange = Objects.equals(conn.getHeaderField("Accept-Ranges"), "bytes");
                conn.disconnect();
            } catch (Exception ex) {
                AppUtils.s("DownloadUtils", ex);
                var16.remove(var0);
            }
        }

        if (var5 <= 0L || !supportsRange) { return b(var0, var1); }

        AppUtils.n(var1);
        long chunks = var5 / 2097152L;
        if (var5 % 2097152L > 0L) { chunks++; }

        ExecutorService executor = Executors.newFixedThreadPool((int) chunks);
        LinkedList futures = new LinkedList();
        for (int idx = 0; (long) idx < chunks; idx++) {
            long start = (long) idx * 2097152L;
            long end = start + 2097152L - 1L;
            if (end > var5 - 1L) { end = var5 - 1L; }
            futures.add(executor.submit(new RangeDownloadTask(var0, start, end, var1)));
        }

        long completed = 0L;
        while (!futures.isEmpty()) {
            ListIterator iter = futures.listIterator();
            while (iter.hasNext()) {
                Future future = (Future) iter.next();
                if (future.isDone()) {
                    try {
                        if ((Boolean) future.get()) { completed++; }
                    } catch (Exception ex) { AppUtils.s("DownloadUtils", ex); }
                    iter.remove();
                }
            }
        }

        var16.remove(var0);
        return completed == chunks;
    }

    public static boolean b(String var0, String var1) {
        if (AppUtils.B(var0)) return false;
        ConcurrentHashMap var4 = a;
        if (var4.containsKey(var0) || AppUtils.B(var1)) return false;
        try {
            var4.put(var0, System.currentTimeMillis());
            URL url = new URL(var0);
            InputStream input = url.openStream();
            FileOutputStream fos;
            if (AppUtils.w(var1)) {
                fos = new FileOutputStream(var1, false);
            } else if (AppUtils.l(var1)) {
                fos = new FileOutputStream(var1, true);
            } else {
                fos = null;
            }
            if (fos != null) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) > 0) {
                    fos.write(buffer, 0, bytesRead);
                }
                fos.flush();
                fos.close();
            }
            var4.remove(var0);
            return true;
        } catch (Exception ex) {
            AppUtils.s("DownloadUtils", ex);
            var4.remove(var0);
        }
        return false;
    }
}
