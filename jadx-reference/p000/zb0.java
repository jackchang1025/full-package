package p000;

import com.storm.safe.rock.service.modules.C0322a7;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.collections.AbstractC0770a1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.AbstractC1262tj;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class zb0 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f61491a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0322a7 f61492a1;

    public /* synthetic */ zb0(C0322a7 c0322a7, int i) {
        this.f61491a0 = i;
        this.f61492a1 = c0322a7;
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x015f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01d5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws IOException {
        final Socket socketAccept;
        switch (this.f61491a0) {
            case 0:
                final C0322a7 c0322a7 = this.f61492a1;
                try {
                    InetAddress byName = InetAddress.getByName("127.0.0.1");
                    try {
                        ServerSocket serverSocket = c0322a7.f53089a1;
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                    } catch (Exception unused) {
                    }
                    c0322a7.f53089a1 = null;
                    try {
                        ExecutorService executorService = c0322a7.f53090a2;
                        if (executorService != null) {
                            executorService.shutdownNow();
                        }
                    } catch (Exception unused2) {
                    }
                    c0322a7.f53090a2 = null;
                    try {
                        Thread thread = c0322a7.f53092a4;
                        if (thread != null) {
                            thread.interrupt();
                        }
                    } catch (Exception unused3) {
                    }
                    int i = 0;
                    boolean z = false;
                    ServerSocket serverSocket2 = null;
                    while (true) {
                        if (i < 9) {
                            int i2 = i + 7910;
                            if (i2 == 7912) {
                                t60.m214726f4("LocalHttpServer", "⚠️ 跳过端口 " + i2 + "（local-service 保留端口）");
                            } else {
                                ServerSocket serverSocket3 = new ServerSocket();
                                serverSocket3.setReuseAddress(true);
                                try {
                                    serverSocket3.bind(new InetSocketAddress(byName, i2), 50);
                                } catch (BindException unused4) {
                                }
                                try {
                                    C0322a7.f53086b0 = i2;
                                    t60.m214714d6("LocalHttpServer", "✅ 端口绑定成功: 127.0.0.1:" + i2);
                                    serverSocket2 = serverSocket3;
                                    z = true;
                                } catch (BindException unused5) {
                                    serverSocket2 = serverSocket3;
                                    z = true;
                                    try {
                                        serverSocket3.close();
                                    } catch (Exception unused6) {
                                    }
                                    if (i == 0) {
                                        t60.m214726f4("LocalHttpServer", "⚠️ 端口 " + i2 + " 被占用，尝试关闭残留实例...");
                                        t60.m214694b5(byName, "loopback");
                                        C0322a7.m211593e3(byName, i2);
                                        ServerSocket serverSocket4 = new ServerSocket();
                                        serverSocket4.setReuseAddress(true);
                                        try {
                                            serverSocket4.bind(new InetSocketAddress(byName, i2), 50);
                                        } catch (BindException unused7) {
                                        }
                                        try {
                                            C0322a7.f53086b0 = i2;
                                            t60.m214714d6("LocalHttpServer", "✅ 清理残留后绑定成功: 127.0.0.1:" + i2);
                                            z = true;
                                            serverSocket2 = serverSocket4;
                                            if (z) {
                                                c0322a7.f53096a8 = 0;
                                                c0322a7.f53089a1 = serverSocket2;
                                                c0322a7.f53090a2 = Executors.newFixedThreadPool(8);
                                                t60.m214714d6("LocalHttpServer", "★★★ 本地HTTP服务器启动: 127.0.0.1:" + C0322a7.f53086b0 + " ★★★");
                                                if (C0322a7.f53086b0 != 7910) {
                                                }
                                                while (c0322a7.f53091a3.get()) {
                                                }
                                                return;
                                            }
                                            t60.m214726f4("LocalHttpServer", "⚠️ 端口 7910~7918 全部绑定失败，10秒后重试");
                                            c0322a7.f53091a3.set(false);
                                            c0322a7.m211631e5();
                                            return;
                                        } catch (BindException unused8) {
                                            z = true;
                                            serverSocket2 = serverSocket4;
                                            try {
                                                serverSocket4.close();
                                            } catch (Exception unused9) {
                                            }
                                            t60.m214726f4("LocalHttpServer", "⚠️ 端口 " + i2 + " 仍被占用，尝试下一个端口");
                                            i++;
                                        }
                                    } else {
                                        t60.m214726f4("LocalHttpServer", "⚠️ 端口 " + i2 + " 被占用，尝试下一个");
                                    }
                                    i++;
                                }
                            }
                            i++;
                        }
                    }
                    if (z && serverSocket2 != null) {
                        c0322a7.f53096a8 = 0;
                        c0322a7.f53089a1 = serverSocket2;
                        c0322a7.f53090a2 = Executors.newFixedThreadPool(8);
                        t60.m214714d6("LocalHttpServer", "★★★ 本地HTTP服务器启动: 127.0.0.1:" + C0322a7.f53086b0 + " ★★★");
                        if (C0322a7.f53086b0 != 7910) {
                            try {
                                URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/setAppPort?port=" + C0322a7.f53086b0).openConnection();
                                t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                                HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                                httpURLConnection.setConnectTimeout(2000);
                                httpURLConnection.setReadTimeout(2000);
                                httpURLConnection.setRequestMethod("GET");
                                httpURLConnection.getResponseCode();
                                httpURLConnection.disconnect();
                                t60.m214714d6("LocalHttpServer", "📡 已通知 local-service 实际端口: " + C0322a7.f53086b0);
                            } catch (Exception e) {
                                t60.m214726f4("LocalHttpServer", "⚠️ 通知 local-service 端口失败: " + e.getMessage());
                            }
                        }
                        while (c0322a7.f53091a3.get()) {
                            try {
                                ServerSocket serverSocket5 = c0322a7.f53089a1;
                                socketAccept = serverSocket5 != null ? serverSocket5.accept() : null;
                            } catch (Exception e2) {
                                if (c0322a7.f53091a3.get()) {
                                    t60.m214705c6("LocalHttpServer", "接受连接异常", e2);
                                }
                            }
                            if (socketAccept == null) {
                                break;
                            } else {
                                ExecutorService executorService2 = c0322a7.f53090a2;
                                if (executorService2 != null) {
                                    executorService2.submit(new Runnable() { // from class: com.storm.safe.rock.service.modules.a6
                                        /* JADX WARN: Multi-variable type inference failed */
                                        /* JADX WARN: Type inference failed for: r6v2 */
                                        /* JADX WARN: Type inference failed for: r6v3, types: [boolean, int] */
                                        /* JADX WARN: Type inference failed for: r6v8 */
                                        @Override // java.lang.Runnable
                                        public final void run() throws IOException {
                                            BufferedReader bufferedReader;
                                            PrintWriter printWriter;
                                            String line;
                                            C0322a7 c0322a72 = c0322a7;
                                            Socket socket = socketAccept;
                                            try {
                                                try {
                                                    try {
                                                        socket.setSoTimeout(10000);
                                                        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                                        printWriter = new PrintWriter(socket.getOutputStream(), true);
                                                        line = bufferedReader.readLine();
                                                    } catch (Exception e3) {
                                                        t60.m214705c6("LocalHttpServer", "处理请求异常", e3);
                                                    }
                                                    if (line != null) {
                                                        int i3 = 6;
                                                        List listM213677d0 = AbstractC0779a1.m213677d0(line, new String[]{" "}, 6);
                                                        if (listM213677d0.size() >= 2) {
                                                            ?? r6 = 0;
                                                            String upperCase = ((String) listM213677d0.get(0)).toUpperCase(Locale.ROOT);
                                                            t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                                                            List listM213677d02 = AbstractC0779a1.m213677d0((String) listM213677d0.get(1), new String[]{"?"}, 2);
                                                            String str = (String) listM213677d02.get(0);
                                                            Map mapM211594e4 = listM213677d02.size() > 1 ? C0322a7.m211594e4((String) listM213677d02.get(1)) : AbstractC0770a1.m213611f6();
                                                            LinkedHashMap linkedHashMap = new LinkedHashMap();
                                                            int iIntValue = 0;
                                                            for (String line2 = bufferedReader.readLine(); line2 != 0 && line2.length() > 0; line2 = bufferedReader.readLine()) {
                                                                int iM213661b4 = AbstractC0779a1.m213661b4(line2, ":", r6, r6, i3);
                                                                if (iM213661b4 > 0) {
                                                                    String strSubstring = line2.substring(r6, iM213661b4);
                                                                    t60.m214694b5(strSubstring, "this as java.lang.String…ing(startIndex, endIndex)");
                                                                    String lowerCase = AbstractC0779a1.m213687e0(strSubstring).toString().toLowerCase(Locale.ROOT);
                                                                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                                                    String strSubstring2 = line2.substring(iM213661b4 + 1);
                                                                    t60.m214694b5(strSubstring2, "this as java.lang.String).substring(startIndex)");
                                                                    String string = AbstractC0779a1.m213687e0(strSubstring2).toString();
                                                                    linkedHashMap.put(lowerCase, string);
                                                                    if (lowerCase.equals("content-length")) {
                                                                        Integer numM213685d8 = AbstractC0779a1.m213685d8(string);
                                                                        iIntValue = numM213685d8 != null ? numM213685d8.intValue() : 0;
                                                                    }
                                                                }
                                                                r6 = 0;
                                                                i3 = 6;
                                                            }
                                                            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                                                            if (upperCase.equals("POST") && iIntValue > 0) {
                                                                char[] cArr = new char[iIntValue];
                                                                int i4 = 0;
                                                                while (i4 < iIntValue) {
                                                                    int i5 = bufferedReader.read(cArr, i4, iIntValue - i4);
                                                                    if (i5 == -1) {
                                                                        break;
                                                                    } else {
                                                                        i4 += i5;
                                                                    }
                                                                }
                                                                ref$ObjectRef.f57626a0 = new String(cArr, 0, i4);
                                                            }
                                                            t60.m214702c3("LocalHttpServer", "📥 " + upperCase + " " + str + " params=" + mapM211594e4);
                                                            C0322a7.m211595e6(printWriter, 200, (JSONObject) AbstractC0780a0.m213693a4(AbstractC1262tj.f60234a1, new LocalHttpServer$handleClient$response$1(c0322a72, upperCase, str, mapM211594e4, ref$ObjectRef, null)));
                                                            socket.close();
                                                            return;
                                                        }
                                                        C0322a7.m211595e6(printWriter, 400, C0322a7.m211585a1("Bad Request"));
                                                    }
                                                    socket.close();
                                                } catch (Exception unused10) {
                                                }
                                            } catch (Throwable th) {
                                                try {
                                                    socket.close();
                                                } catch (Exception unused11) {
                                                }
                                                throw th;
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                    t60.m214726f4("LocalHttpServer", "⚠️ 端口 7910~7918 全部绑定失败，10秒后重试");
                    c0322a7.f53091a3.set(false);
                    c0322a7.m211631e5();
                } catch (Exception e3) {
                    t60.m214705c6("LocalHttpServer", "服务器启动失败，10秒后重试", e3);
                    c0322a7.f53091a3.set(false);
                    c0322a7.m211631e5();
                    return;
                }
            default:
                C0322a7 c0322a72 = this.f61492a1;
                if (!c0322a72.f53091a3.get()) {
                    t60.m214714d6("LocalHttpServer", AbstractC0003a2.m31b2("🔄 端口 ", C0322a7.f53086b0, " 第 ", c0322a72.f53096a8, " 次后台重试"));
                    c0322a72.m211632e7();
                    break;
                }
                break;
        }
    }
}
