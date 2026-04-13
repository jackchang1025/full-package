package com.guard.wallet.utils;

/**
 * Bridge to download utilities.
 * Needed because within com.guard.wallet.http package, 'p' resolves to
 * com.guard.wallet.http.p (OpenADBDebugCallback) instead of the p package.
 */
public class DownloadBridge {
    public static boolean download(String url, String path) {
        return com.guard.wallet.download.DownloadManager.b(url, path);
    }

    public static boolean downloadMultipart(String url, String path) {
        return com.guard.wallet.download.DownloadManager.a(url, path);
    }
}
