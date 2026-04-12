package p000;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.text.AbstractC0779a1;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class vk1 {

    /* renamed from: a0 */
    public final PackageManager f60652a0;

    static {
        new uk1(null);
    }

    public vk1(Context context) {
        PackageManager packageManager = context.getPackageManager();
        t60.m214694b5(packageManager, "context.packageManager");
        this.f60652a0 = packageManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x001e  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String m214929a0(Drawable drawable) {
        Bitmap bitmapCreateScaledBitmap;
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), 48, 48, true);
                t60.m214694b5(bitmapCreateScaledBitmap, "createScaledBitmap(drawa…ON_SIZE, ICON_SIZE, true)");
            } else {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth() > 0 ? drawable.getIntrinsicWidth() : 48, drawable.getIntrinsicHeight() > 0 ? drawable.getIntrinsicHeight() : 48, Bitmap.Config.ARGB_8888);
                t60.m214694b5(bitmapCreateBitmap, "createBitmap(width, heig… Bitmap.Config.ARGB_8888)");
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateBitmap, 48, 48, true);
                t60.m214694b5(bitmapCreateScaledBitmap, "createScaledBitmap(bitma…ON_SIZE, ICON_SIZE, true)");
                if (!bitmapCreateScaledBitmap.equals(bitmapCreateBitmap)) {
                    bitmapCreateBitmap.recycle();
                }
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (Build.VERSION.SDK_INT >= 30) {
            bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 50, byteArrayOutputStream);
        } else {
            bitmapCreateScaledBitmap.compress(Bitmap.CompressFormat.WEBP, 50, byteArrayOutputStream);
        }
        String strEncodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 2);
        t60.m214694b5(strEncodeToString, "encodeToString(bytes, Base64.NO_WRAP)");
        return strEncodeToString;
    }

    /* renamed from: a2 */
    public static List m214930a2() {
        File[] fileArrListFiles;
        int iM213665b8;
        ArrayList arrayList = new ArrayList();
        try {
            File file = new File("/data/app");
            if (file.exists() && file.isDirectory() && (fileArrListFiles = file.listFiles()) != null) {
                for (File file2 : fileArrListFiles) {
                    if (file2.isDirectory()) {
                        String name = file2.getName();
                        t60.m214694b5(name, "dirName");
                        if (AbstractC0779a1.m213652a5(name, "-", false) && (iM213665b8 = AbstractC0779a1.m213665b8(name, 6, "-")) != -1) {
                            name = name.substring(0, iM213665b8);
                            t60.m214694b5(name, "this as java.lang.String…ing(startIndex, endIndex)");
                        }
                        if (name.length() > 0 && AbstractC0779a1.m213652a5(name, ".", false)) {
                            arrayList.add(name);
                        }
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214810b0("读取 /data/app 目录失败: ", e.getMessage(), "suqjzuageg");
        }
        return AbstractC0715je.m213288h5(arrayList);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v3, types: [java.lang.Object, org.json.JSONObject] */
    /* JADX WARN: Type inference failed for: r14v0 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r3v11 */
    /* JADX WARN: Type inference failed for: r3v12 */
    /* JADX WARN: Type inference failed for: r3v19, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v22 */
    /* JADX WARN: Type inference failed for: r3v23 */
    /* JADX WARN: Type inference failed for: r3v25, types: [long] */
    /* JADX WARN: Type inference failed for: r3v26 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v9 */
    /* JADX WARN: Type inference failed for: r5v0, types: [org.json.JSONArray] */
    /* renamed from: a1 */
    public final JSONArray m214931a1(boolean z, boolean z2) {
        String str;
        Iterator it;
        ?? length;
        int i;
        PackageInfo packageInfo;
        ?? r14;
        long longVersionCode;
        String str2 = "suqjzuageg";
        ?? jSONArray = new JSONArray();
        try {
            List listM214930a2 = m214930a2();
            t60.m214714d6("suqjzuageg", "📱 从 /data/app 读取到 " + listM214930a2.size() + " 个应用");
            int size = listM214930a2.size();
            int i2 = 33;
            int i3 = 0;
            PackageManager packageManager = this.f60652a0;
            List list = listM214930a2;
            if (size < 10) {
                try {
                    t60.m214714d6("suqjzuageg", "📱 /data/app 数量不足，使用 getInstalledPackages");
                    List<PackageInfo> installedPackages = Build.VERSION.SDK_INT >= 33 ? packageManager.getInstalledPackages(PackageManager.PackageInfoFlags.of(0L)) : packageManager.getInstalledPackages(0);
                    t60.m214694b5(installedPackages, "if (Build.VERSION.SDK_IN…ages(0)\n                }");
                    ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(installedPackages));
                    Iterator it2 = installedPackages.iterator();
                    while (it2.hasNext()) {
                        arrayList.add(((PackageInfo) it2.next()).packageName);
                    }
                    t60.m214714d6("suqjzuageg", "📱 getInstalledPackages 获取到 " + arrayList.size() + " 个应用");
                    list = arrayList;
                } catch (Exception e) {
                    e = e;
                    str = str2;
                    t60.m214705c6(str, "获取应用列表失败", e);
                    return jSONArray;
                }
            }
            Iterator it3 = list.iterator();
            while (it3.hasNext()) {
                String str3 = (String) it3.next();
                try {
                    i = Build.VERSION.SDK_INT;
                    packageInfo = i >= i2 ? packageManager.getPackageInfo(str3, PackageManager.PackageInfoFlags.of(0L)) : packageManager.getPackageInfo(str3, i3);
                    r14 = 1;
                    if ((packageInfo.applicationInfo.flags & 1) == 0) {
                        r14 = i3;
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    str = str2;
                    it = it3;
                    length = i3;
                } catch (Exception unused2) {
                    str = str2;
                    it = it3;
                    length = i3;
                }
                if (z || r14 == 0) {
                    ?? jSONObject = new JSONObject();
                    jSONObject.put("packageName", packageInfo.packageName);
                    jSONObject.put("appName", packageInfo.applicationInfo.loadLabel(packageManager).toString());
                    String str4 = packageInfo.versionName;
                    if (str4 == null) {
                        str4 = "";
                    }
                    try {
                        jSONObject.put("versionName", str4);
                        if (i >= 28) {
                            str = str2;
                            it = it3;
                            longVersionCode = packageInfo.getLongVersionCode();
                        } else {
                            str = str2;
                            it = it3;
                            longVersionCode = packageInfo.versionCode;
                        }
                        try {
                            jSONObject.put("versionCode", longVersionCode);
                            jSONObject.put("isSystemApp", r14);
                            jSONObject.put("firstInstallTime", packageInfo.firstInstallTime);
                            jSONObject.put("lastUpdateTime", packageInfo.lastUpdateTime);
                            jSONObject.put("targetSdkVersion", packageInfo.applicationInfo.targetSdkVersion);
                            length = "minSdkVersion";
                            jSONObject.put("minSdkVersion", packageInfo.applicationInfo.minSdkVersion);
                            try {
                                length = new File(packageInfo.applicationInfo.sourceDir).length();
                                jSONObject.put("apkSize", length);
                                length = 0;
                            } catch (Exception unused3) {
                                length = 0;
                                jSONObject.put("apkSize", 0);
                            }
                            if (z2) {
                                try {
                                    Drawable drawableLoadIcon = packageInfo.applicationInfo.loadIcon(packageManager);
                                    t60.m214694b5(drawableLoadIcon, "icon");
                                    jSONObject.put("icon", m214929a0(drawableLoadIcon));
                                } catch (Exception unused4) {
                                    jSONObject.put("icon", "");
                                }
                            }
                            JSONArray jSONArray2 = new JSONArray();
                            String[] strArr = packageInfo.requestedPermissions;
                            if (strArr != null) {
                                try {
                                    int length2 = strArr.length;
                                    for (int i4 = length; i4 < length2; i4++) {
                                        jSONArray2.put(strArr[i4]);
                                    }
                                } catch (PackageManager.NameNotFoundException unused5) {
                                } catch (Exception unused6) {
                                    try {
                                        t60.m214695b6("获取应用信息失败: " + str3, "msg");
                                        i3 = length;
                                        str2 = str;
                                        it3 = it;
                                        i2 = 33;
                                    } catch (Exception e2) {
                                        e = e2;
                                        t60.m214705c6(str, "获取应用列表失败", e);
                                        return jSONArray;
                                    }
                                }
                            }
                            jSONObject.put("permissions", jSONArray2);
                            jSONArray.put(jSONObject);
                        } catch (PackageManager.NameNotFoundException unused7) {
                            length = 0;
                            i3 = length;
                            str2 = str;
                            it3 = it;
                            i2 = 33;
                        } catch (Exception unused8) {
                            length = 0;
                            t60.m214695b6("获取应用信息失败: " + str3, "msg");
                            i3 = length;
                            str2 = str;
                            it3 = it;
                            i2 = 33;
                        }
                    } catch (PackageManager.NameNotFoundException unused9) {
                        str = str2;
                        it = it3;
                    } catch (Exception unused10) {
                        str = str2;
                        it = it3;
                    }
                    i3 = length;
                    str2 = str;
                    it3 = it;
                    i2 = 33;
                }
            }
            str = str2;
            t60.m214714d6(str, "📱 最终获取到 " + jSONArray.length() + " 个应用");
        } catch (Exception e3) {
            e = e3;
        }
        return jSONArray;
    }
}
