package com.storm.safe.rock.security;

import android.os.Build;
import android.os.Debug;
import com.storm.safe.rock.hkdrkgzsfs;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlin.text.AbstractC0779a1;
import okio.Segment;
import p000.AbstractC0577hd;
import p000.AbstractC0715je;
import p000.AbstractC1517zh;
import p000.k21;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.security.a0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0276a0 {

    /* renamed from: a0 */
    public static boolean f52294a0;

    /* renamed from: a1 */
    public static SecurityManager$SecurityPolicy f52295a1 = SecurityManager$SecurityPolicy.f52291a0;

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00de, code lost:
    
        r2 = java.lang.Runtime.getRuntime().exec(new java.lang.String[]{p000.k21.m213444a0("eQZwWHg="), p000.k21.m213444a0("ZAI=")});
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00f6, code lost:
    
        r0 = new java.io.BufferedReader(new java.io.InputStreamReader(r2.getInputStream()));
        r3 = r0.readLine();
        r0.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x010b, code lost:
    
        if (r3 == null) goto L28;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0111, code lost:
    
        if (r3.length() != 0) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0114, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0116, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0118, code lost:
    
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0119, code lost:
    
        r0 = !r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x011a, code lost:
    
        r2.destroy();
        r0 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x011e, code lost:
    
        r2.destroy();
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0121, code lost:
    
        throw r0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:164:0x0561 A[Catch: Exception -> 0x0572, TRY_LEAVE, TryCatch #6 {Exception -> 0x0572, blocks: (B:162:0x0545, B:164:0x0561), top: B:209:0x0545 }] */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0575  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0583  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x0586  */
    /* JADX WARN: Removed duplicated region for block: B:186:0x05a5  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x01bd A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:227:0x00de A[EDGE_INSN: B:227:0x00de->B:19:0x00de BREAK  A[LOOP:0: B:13:0x00b9->B:18:0x00d9], EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:258:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x023c A[PHI: r2 r6 r8 r19 r20 r21
      0x023c: PHI (r2v36 int) = (r2v5 int), (r2v5 int), (r2v37 int) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]
      0x023c: PHI (r6v13 java.lang.String) = (r6v2 java.lang.String), (r6v2 java.lang.String), (r6v14 java.lang.String) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]
      0x023c: PHI (r8v18 int) = (r8v2 int), (r8v2 int), (r8v19 int) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]
      0x023c: PHI (r19v3 char) = (r19v1 char), (r19v1 char), (r19v4 char) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]
      0x023c: PHI (r20v6 boolean) = (r20v1 boolean), (r20v1 boolean), (r20v7 boolean) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]
      0x023c: PHI (r21v3 int) = (r21v1 int), (r21v1 int), (r21v4 int) binds: [B:42:0x01bb, B:60:0x0237, B:17:0x00ce] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0240  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x024d  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x02bb  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m211382a0(hkdrkgzsfs hkdrkgzsfsVar) {
        k21 k21Var;
        int i;
        boolean z;
        int i2;
        boolean z2;
        int i3;
        int i4;
        char c;
        int i5;
        String str;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean zM214686a2;
        Process processExec;
        char c2;
        SecurityChecker$checkXposed$d$1 securityChecker$checkXposed$d$1;
        String strM213444a0;
        boolean z7;
        File file;
        Process processExec2;
        int iOrdinal;
        boolean z8;
        boolean z9;
        boolean z10;
        ArrayList arrayList = new ArrayList();
        try {
            if (!Debug.isDebuggerConnected() && !Debug.waitingForDebugger()) {
                if (t60.m214696b7()) {
                }
                k21Var = k21.f57429a0;
                SecurityChecker$checkRootFiles$d$1 securityChecker$checkRootFiles$d$1 = new SecurityChecker$checkRootFiles$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                i = 14;
                z = false;
                String[] strArr = {securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnxzcDgBZGKUYWQDdkLfYkFweg=="), securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnxhaXkYYh5k"), securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnxqczgfZEI="), securityChecker$checkRootFiles$d$1.invoke("Yl56U95+RGI="), securityChecker$checkRootFiles$d$1.invoke("dV5nUN5xXn1icjgdZEI="), securityChecker$checkRootFiles$d$1.invoke("dV5nUN5xXn1icjgdeFPef2QC"), securityChecker$checkRootFiles$d$1.invoke("dV5nUN5xXn1icjgdc0mfeGJeRA=="), securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnxnYm9eeFPef2QC"), securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnxqczgfcFedeHACdleCP2Q="), securityChecker$checkRootFiles$d$1.invoke("Yl5gSJRkHnx2Yg=="), securityChecker$checkRootFiles$d$1.invoke("Yl48RJhyHn92Yg=="), securityChecker$checkRootFiles$d$1.invoke("fF50UIJ5HnpgP2UeVA=="), securityChecker$checkRootFiles$d$1.invoke("Yl56U95+XD9kcGQYWg=="), securityChecker$checkRootFiles$d$1.invoke("dV5nUN5xVXAsc3YceFaaYg==")};
                i2 = 0;
                while (true) {
                    if (i2 < i) {
                        try {
                            break;
                        } catch (Exception unused) {
                            z2 = false;
                        }
                    } else {
                        if (new File(strArr[i2]).exists()) {
                            break;
                        }
                        i2++;
                        i = 14;
                    }
                }
                if (z6) {
                    arrayList.add("检测到 Root");
                }
                if (f52295a1 != SecurityManager$SecurityPolicy.f52292a1) {
                    String str2 = Build.FINGERPRINT;
                    t60.m214694b5(str2, "FINGERPRINT");
                    Boolean boolValueOf = Boolean.valueOf(AbstractC0779a1.m213679d2(str2, false, "generic"));
                    Boolean boolValueOf2 = Boolean.valueOf(AbstractC0779a1.m213679d2(str2, false, "unknown"));
                    String str3 = Build.MODEL;
                    t60.m214694b5(str3, "MODEL");
                    Boolean boolValueOf3 = Boolean.valueOf(AbstractC0779a1.m213652a5(str3, "google_sdk", false));
                    Boolean boolValueOf4 = Boolean.valueOf(AbstractC0779a1.m213652a5(str3, "Emulator", false));
                    Boolean boolValueOf5 = Boolean.valueOf(AbstractC0779a1.m213652a5(str3, "Android SDK built for x86", false));
                    String str4 = Build.MANUFACTURER;
                    t60.m214694b5(str4, "MANUFACTURER");
                    Boolean boolValueOf6 = Boolean.valueOf(AbstractC0779a1.m213652a5(str4, "Genymotion", false));
                    String str5 = Build.BRAND;
                    t60.m214694b5(str5, "BRAND");
                    if (AbstractC0779a1.m213679d2(str5, false, "generic")) {
                        String str6 = Build.DEVICE;
                        t60.m214694b5(str6, "DEVICE");
                        boolean z11 = AbstractC0779a1.m213679d2(str6, false, "generic") ? z3 ? 1 : 0 : false;
                        Boolean boolValueOf7 = Boolean.valueOf(z11);
                        String str7 = Build.PRODUCT;
                        Boolean boolValueOf8 = Boolean.valueOf(t60.m214686a2(str7, "google_sdk"));
                        Boolean boolValueOf9 = Boolean.valueOf(t60.m214686a2(str7, "sdk"));
                        Boolean boolValueOf10 = Boolean.valueOf(t60.m214686a2(str7, "sdk_x86"));
                        Boolean boolValueOf11 = Boolean.valueOf(t60.m214686a2(str7, "vbox86p"));
                        Boolean boolValueOf12 = Boolean.valueOf(t60.m214686a2(str7, "emulator"));
                        Boolean boolValueOf13 = Boolean.valueOf(t60.m214686a2(str7, "simulator"));
                        String str8 = Build.HARDWARE;
                        t60.m214694b5(str8, "HARDWARE");
                        Boolean[] boolArr = {boolValueOf, boolValueOf2, boolValueOf3, boolValueOf4, boolValueOf5, boolValueOf6, boolValueOf7, boolValueOf8, boolValueOf9, boolValueOf10, boolValueOf11, boolValueOf12, boolValueOf13, Boolean.valueOf(AbstractC0779a1.m213652a5(str8, "goldfish", false)), Boolean.valueOf(AbstractC0779a1.m213652a5(str8, "ranchu", false)), Boolean.valueOf(t60.m214686a2(Build.BOARD, "unknown"))};
                        int i6 = 0;
                        while (true) {
                            if (i6 >= 16) {
                                String[] strArr2 = {"/dev/socket/qemud", "/dev/qemu_pipe", "/system/lib/libc_malloc_debug_qemu.so", "/sys/qemu_trace", "/system/bin/qemu-props", "/dev/socket/genyd", "/dev/socket/baseband_genyd"};
                                int i7 = 0;
                                while (true) {
                                    if (i7 >= i4) {
                                        try {
                                            String[] strArr3 = {"init.svc.qemud", "init.svc.qemu-props", "qemu.hw.mainkeys", "qemu.sf.fake_camera", "ro.kernel.android.qemud", "ro.kernel.qemu.gles", "ro.kernel.qemu"};
                                            for (int i8 = 0; i8 < i4; i8++) {
                                                try {
                                                    InputStream inputStream = Runtime.getRuntime().exec(new String[]{str, strArr3[i8]}).getInputStream();
                                                    t60.m214694b5(inputStream, "getRuntime().exec(arrayO…             .inputStream");
                                                    String line = new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), i5).readLine();
                                                    z10 = !((line == null || line.length() == 0) ? z3 ? 1 : 0 : false);
                                                } catch (Exception unused2) {
                                                    z10 = false;
                                                }
                                                if (z10) {
                                                    z8 = z3 ? 1 : 0;
                                                    break;
                                                }
                                            }
                                        } catch (Exception unused3) {
                                        }
                                        z8 = false;
                                        if (z8) {
                                            break;
                                        } else {
                                            z9 = false;
                                        }
                                    } else if (new File(strArr2[i7]).exists()) {
                                        break;
                                    } else {
                                        i7++;
                                    }
                                }
                            } else if (boolArr[i6].booleanValue()) {
                                break;
                            } else {
                                i6++;
                            }
                        }
                        z9 = z3 ? 1 : 0;
                        if (z9) {
                            arrayList.add("检测到模拟器");
                        }
                    }
                }
                securityChecker$checkXposed$d$1 = new SecurityChecker$checkXposed$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                throw new Exception("stack check");
            }
            throw new Exception("stack check");
            if (!z2) {
                i3 = 4;
                i4 = 7;
                c = 3;
                i5 = 8192;
                str = "getprop";
                z3 = true;
                SecurityChecker$checkRootPackages$d$1 securityChecker$checkRootPackages$d$1 = new SecurityChecker$checkRootPackages$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                String[] strArr4 = {securityChecker$checkRootPackages$d$1.invoke("fhI9XJ5+WWJlZGIecB+Vf34Dd1iCPmQ="), securityChecker$checkRootPackages$d$1.invoke("fhI9XJlkQ3hzdWUQaEWCP2EEYVSCZUN0"), securityChecker$checkRootPackages$d$1.invoke("ZBRwH5B4X3hqd3IDYh+BZGMUZkI="), securityChecker$checkRootPackages$d$1.invoke("fhI9XJ57QmRqeXMaZUSQZWJfY0SDdUJkcXQ="), securityChecker$checkRootPackages$d$1.invoke("fhI9XJBqWXJzYnkeP1aUZWEcfEOFf1RjbHxyB3Nb"), securityChecker$checkRootPackages$d$1.invoke("fhI9XJBiVXxsY3MYcB+BYWQAYVCfcVhlZn8="), securityChecker$checkRootPackages$d$1.invoke("fhI9XJ5kW2FrfmAfP0SQfHgWeEI="), securityChecker$checkRootPackages$d$1.invoke("dBxjH5l4Qj9zZGUUYkSDdA=="), securityChecker$checkRootPackages$d$1.invoke("fhI9XJh7Vn92fnICP0Oechw=")};
                for (int i9 = 0; i9 < 9; i9++) {
                    try {
                        if (new File("/data/data/" + strArr4[i9]).exists()) {
                            z4 = true;
                            break;
                        }
                    } catch (Exception unused4) {
                        z4 = false;
                        if (z4) {
                            try {
                                Map mapM213614f9 = AbstractC0770a1.m213614f9(new Pair("ro.debuggable", "1"), new Pair("ro.secure", "0"));
                                if (!mapM213614f9.isEmpty()) {
                                    for (Map.Entry entry : mapM213614f9.entrySet()) {
                                        String str9 = (String) entry.getKey();
                                        String str10 = (String) entry.getValue();
                                        try {
                                            processExec = Runtime.getRuntime().exec(new String[]{str, str9});
                                        } catch (Exception unused5) {
                                            zM214686a2 = false;
                                        }
                                        try {
                                            InputStream inputStream2 = processExec.getInputStream();
                                            t60.m214694b5(inputStream2, "process.inputStream");
                                            zM214686a2 = t60.m214686a2(new BufferedReader(new InputStreamReader(inputStream2, AbstractC0577hd.f56650a0), Segment.SIZE).readLine(), str10);
                                            processExec.destroy();
                                            if (zM214686a2) {
                                                z5 = true;
                                                break;
                                            }
                                        } catch (Throwable th) {
                                            throw th;
                                        }
                                    }
                                }
                            } catch (Exception unused6) {
                            }
                            z5 = false;
                            if (z5) {
                                z6 = z3 ? 1 : 0;
                                c2 = c;
                            } else {
                                z6 = false;
                                c2 = c;
                            }
                        }
                        if (z6) {
                        }
                        if (f52295a1 != SecurityManager$SecurityPolicy.f52292a1) {
                        }
                        securityChecker$checkXposed$d$1 = new SecurityChecker$checkXposed$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                        throw new Exception("stack check");
                    }
                }
                z4 = false;
                if (z4) {
                }
                if (z6) {
                }
                if (f52295a1 != SecurityManager$SecurityPolicy.f52292a1) {
                }
                securityChecker$checkXposed$d$1 = new SecurityChecker$checkXposed$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                throw new Exception("stack check");
            }
            z3 = true;
            c = 3;
            i3 = 4;
            i4 = 7;
            i5 = 8192;
            str = "getprop";
            z6 = z3 ? 1 : 0;
            c2 = c;
            if (z6) {
            }
            if (f52295a1 != SecurityManager$SecurityPolicy.f52292a1) {
            }
            securityChecker$checkXposed$d$1 = new SecurityChecker$checkXposed$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
            throw new Exception("stack check");
        } catch (Exception e) {
            String str11 = (String) securityChecker$checkXposed$d$1.invoke("YQlgXpV1");
            String str12 = (String) securityChecker$checkXposed$d$1.invoke("dBVhH5N/H2dtcGUVeF7fdWEJYF6VdQ==");
            StackTraceElement[] stackTrace = e.getStackTrace();
            t60.m214694b5(stackTrace, "e.stackTrace");
            int length = stackTrace.length;
            int i10 = 0;
            while (true) {
                if (i10 < length) {
                    StackTraceElement stackTraceElement = stackTrace[i10];
                    String className = stackTraceElement.getClassName();
                    t60.m214694b5(className, "element.className");
                    if (AbstractC0779a1.m213652a5(className, str11, false)) {
                        break;
                    }
                    String className2 = stackTraceElement.getClassName();
                    t60.m214694b5(className2, "element.className");
                    if (AbstractC0779a1.m213652a5(className2, str12, false)) {
                        break;
                    } else {
                        i10++;
                    }
                } else {
                    try {
                        String[] strArr5 = new String[2];
                        strArr5[0] = securityChecker$checkXposed$d$1.invoke("fgNrH55gVGItdXIHYkOeeB8=");
                        strArr5[z3 ? 1 : 0] = securityChecker$checkXposed$d$1.invoke("dAFgQ4J5H2V6YjkCYUmCfnUUdh+Qfl1zdA==");
                        for (int i11 = 0; i11 < 2; i11++) {
                            InputStream inputStream3 = Runtime.getRuntime().exec(new String[]{str, strArr5[i11]}).getInputStream();
                            t60.m214694b5(inputStream3, "getRuntime().exec(arrayO…             .inputStream");
                            String line2 = new BufferedReader(new InputStreamReader(inputStream3, AbstractC0577hd.f56650a0), Segment.SIZE).readLine();
                            if (line2 != null && line2.length() != 0) {
                                break;
                            }
                        }
                    } catch (Exception unused7) {
                    }
                    try {
                        String[] strArr6 = new String[i3];
                        strArr6[0] = securityChecker$checkXposed$d$1.invoke("dBVhH5N/H2dtcGUVeF7fdWEJYF6VdVg/cH92BX1dg3Q=");
                        strArr6[z3 ? 1 : 0] = securityChecker$checkXposed$d$1.invoke("fhhlH99xSXRsYXICVQ==");
                        strArr6[2] = securityChecker$checkXposed$d$1.invoke("Yx49VpR9Rn5icjkFdVSBaWIed1ScPl9wZHBlFA==");
                        strArr6[c2] = securityChecker$checkXposed$d$1.invoke("Yx49VoJ8XmFmYjkVcFyQf3QWQw==");
                        for (int i12 = 0; i12 < 4; i12++) {
                            if (new File("/data/data/" + strArr6[i12]).exists()) {
                                break;
                            }
                        }
                    } catch (Exception unused8) {
                    }
                    SecurityChecker$checkFrida$d$1 securityChecker$checkFrida$d$1 = new SecurityChecker$checkFrida$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
                    try {
                        new Socket("127.0.0.1", 27042).close();
                    } catch (Exception unused9) {
                        String str13 = (String) securityChecker$checkFrida$d$1.invoke("Yxd3WHE=");
                        try {
                            processExec2 = Runtime.getRuntime().exec("ps");
                        } catch (Exception unused10) {
                        }
                        try {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec2.getInputStream()));
                            Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
                            while (true) {
                                String line3 = bufferedReader.readLine();
                                ref$ObjectRef.f57626a0 = line3;
                                if (line3 == null) {
                                    processExec2.destroy();
                                    break;
                                }
                                boolean z12 = z3;
                                if (AbstractC0779a1.m213652a5(line3, str13, false) == z12) {
                                    processExec2.destroy();
                                    break;
                                }
                                z3 = z12;
                            }
                            String[] strArr7 = {securityChecker$checkFrida$d$1.invoke("eB11U5hiUHVkPHMQdFbfZX4C"), securityChecker$checkFrida$d$1.invoke("eB11U5hiUHViPHIWZV+CPx4=")};
                            try {
                                file = new File("/proc/self/maps");
                            } catch (Exception unused11) {
                            }
                            if (file.exists()) {
                                String strM215420f8 = AbstractC1517zh.m215420f8(file);
                                for (int i13 = 0; i13 < 2; i13++) {
                                    if (AbstractC0779a1.m213652a5(strM215420f8, strArr7[i13], false)) {
                                        break;
                                    }
                                }
                                strM213444a0 = k21.m213444a0("fhI9XJBjQ2RoeGRfc0SFYnADdkU=");
                                try {
                                } catch (Exception unused12) {
                                }
                                if (!new File("/data/data/" + strM213444a0 + "/").exists()) {
                                    if (new File("/data/app/".concat(strM213444a0)).exists()) {
                                        break;
                                    } else {
                                        z7 = false;
                                    }
                                }
                            } else {
                                strM213444a0 = k21.m213444a0("fhI9XJBjQ2RoeGRfc0SFYnADdkU=");
                                if (!new File("/data/data/" + strM213444a0 + "/").exists()) {
                                }
                            }
                            if (z7) {
                            }
                            if ((hkdrkgzsfsVar.getApplicationInfo().flags & 2) != 0) {
                            }
                            if (z) {
                            }
                            iOrdinal = f52295a1.ordinal();
                            if (iOrdinal == 0) {
                            }
                            if (arrayList.isEmpty()) {
                            }
                        } catch (Throwable th2) {
                            processExec2.destroy();
                            throw th2;
                        }
                    }
                }
            }
            z7 = true;
            if (z7) {
                arrayList.add("检测到 Hook 框架");
            }
            try {
                if ((hkdrkgzsfsVar.getApplicationInfo().flags & 2) != 0) {
                    z = true;
                }
            } catch (Exception unused13) {
            }
            if (z) {
                arrayList.add("APK 可调试");
            }
            iOrdinal = f52295a1.ordinal();
            if (iOrdinal == 0 && iOrdinal != 1 && iOrdinal != 2) {
                throw new NoWhenBranchMatchedException();
            }
            if (arrayList.isEmpty()) {
                t60.m214726f4("SecurityManager", "⚠️ 安全问题: ".concat(AbstractC0715je.m213295i2(arrayList, ", ", null, null, null, 62)));
                return;
            }
            return;
        }
        arrayList.add("检测到调试器");
        k21Var = k21.f57429a0;
        SecurityChecker$checkRootFiles$d$1 securityChecker$checkRootFiles$d$12 = new SecurityChecker$checkRootFiles$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
        i = 14;
        z = false;
        String[] strArr8 = {securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnxzcDgBZGKUYWQDdkLfYkFweg=="), securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnxhaXkYYh5k"), securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnxqczgfZEI="), securityChecker$checkRootFiles$d$12.invoke("Yl56U95+RGI="), securityChecker$checkRootFiles$d$12.invoke("dV5nUN5xXn1icjgdZEI="), securityChecker$checkRootFiles$d$12.invoke("dV5nUN5xXn1icjgdeFPef2QC"), securityChecker$checkRootFiles$d$12.invoke("dV5nUN5xXn1icjgdc0mfeGJeRA=="), securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnxnYm9eeFPef2QC"), securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnxqczgfcFedeHACdleCP2Q="), securityChecker$checkRootFiles$d$12.invoke("Yl5gSJRkHnx2Yg=="), securityChecker$checkRootFiles$d$12.invoke("Yl48RJhyHn92Yg=="), securityChecker$checkRootFiles$d$12.invoke("fF50UIJ5HnpgP2UeVA=="), securityChecker$checkRootFiles$d$12.invoke("Yl56U95+XD9kcGQYWg=="), securityChecker$checkRootFiles$d$12.invoke("dV5nUN5xVXAsc3YceFaaYg==")};
        i2 = 0;
        while (true) {
            if (i2 < i) {
            }
            i2++;
            i = 14;
        }
        if (z6) {
        }
        if (f52295a1 != SecurityManager$SecurityPolicy.f52292a1) {
        }
        securityChecker$checkXposed$d$1 = new SecurityChecker$checkXposed$d$1(1, k21Var, k21.class, "d", "d(Ljava/lang/String;)Ljava/lang/String;", 0);
    }
}
