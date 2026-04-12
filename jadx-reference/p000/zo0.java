package p000;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import java.io.File;
import java.io.IOException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class zo0 {

    /* renamed from: a0 */
    public static final qr0 f61560a0 = new qr0();

    /* renamed from: a1 */
    public static final Object f61561a1 = new Object();

    /* renamed from: a2 */
    public static fh0 f61562a2 = null;

    /* renamed from: a0 */
    public static long m215429a0(Context context) {
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return Build.VERSION.SDK_INT >= 33 ? xo0.m215203a0(packageManager, context).lastUpdateTime : packageManager.getPackageInfo(context.getPackageName(), 0).lastUpdateTime;
    }

    /* renamed from: a1 */
    public static fh0 m215430a1() {
        fh0 fh0Var = new fh0(7);
        f61562a2 = fh0Var;
        qr0 qr0Var = f61560a0;
        qr0Var.getClass();
        if (AbstractC0573h9.f56630a5.mo213000b1(qr0Var, null, fh0Var)) {
            AbstractC0573h9.m213009a2(qr0Var);
        }
        return f61562a2;
    }

    /* renamed from: a2 */
    public static void m215431a2(Context context, boolean z) {
        yo0 yo0VarM215301a0;
        int i;
        if (z || f61562a2 == null) {
            synchronized (f61561a1) {
                if (!z) {
                    try {
                        if (f61562a2 != null) {
                            return;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                int i2 = Build.VERSION.SDK_INT;
                if (i2 >= 28 && i2 != 30) {
                    File file = new File(new File("/data/misc/profiles/ref/", context.getPackageName()), "primary.prof");
                    long length = file.length();
                    int i3 = 0;
                    boolean z2 = file.exists() && length > 0;
                    File file2 = new File(new File("/data/misc/profiles/cur/0/", context.getPackageName()), "primary.prof");
                    long length2 = file2.length();
                    boolean z3 = file2.exists() && length2 > 0;
                    try {
                        long jM215429a0 = m215429a0(context);
                        File file3 = new File(context.getFilesDir(), "profileInstalled");
                        if (file3.exists()) {
                            try {
                                yo0VarM215301a0 = yo0.m215301a0(file3);
                            } catch (IOException unused) {
                                m215430a1();
                                return;
                            }
                        } else {
                            yo0VarM215301a0 = null;
                        }
                        if (yo0VarM215301a0 != null && yo0VarM215301a0.f61351a2 == jM215429a0 && (i = yo0VarM215301a0.f61350a1) != 2) {
                            i3 = i;
                        } else if (z2) {
                            i3 = 1;
                        } else if (z3) {
                            i3 = 2;
                        }
                        if (z && z3 && i3 != 1) {
                            i3 = 2;
                        }
                        if (yo0VarM215301a0 != null && yo0VarM215301a0.f61350a1 == 2 && i3 == 1 && length < yo0VarM215301a0.f61352a3) {
                            i3 = 3;
                        }
                        yo0 yo0Var = new yo0(1, i3, jM215429a0, length2);
                        if (yo0VarM215301a0 == null || !yo0VarM215301a0.equals(yo0Var)) {
                            try {
                                yo0Var.m215302a1(file3);
                            } catch (IOException unused2) {
                            }
                        }
                        m215430a1();
                        return;
                    } catch (PackageManager.NameNotFoundException unused3) {
                        m215430a1();
                        return;
                    }
                }
                m215430a1();
            }
        }
    }
}
