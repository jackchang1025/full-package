package p000;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class c81 {

    /* renamed from: a0 */
    public static final kg1 f46076a0;

    /* renamed from: a1 */
    public static final pc0 f46077a1;

    static {
        int i = Build.VERSION.SDK_INT;
        if (i >= 29) {
            f46076a0 = new h81();
        } else if (i >= 28) {
            f46076a0 = new g81();
        } else if (i >= 26) {
            f46076a0 = new f81();
        } else if (e81.f55943a6 != null) {
            f46076a0 = new e81();
        } else {
            f46076a0 = new d81();
        }
        f46077a1 = new pc0(16);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0044  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Typeface m210770a0(Context context, InterfaceC0881n interfaceC0881n, Resources resources, int i, String str, int i2, int i3, cq0 cq0Var, boolean z) {
        Typeface typefaceMo212560a8;
        Typeface typefaceCreate;
        int i4 = 11;
        int i5 = -3;
        if (interfaceC0881n instanceof C1091q) {
            C1091q c1091q = (C1091q) interfaceC0881n;
            String str2 = c1091q.f59348a3;
            typefaceMo212560a8 = null;
            boolean z2 = false;
            Object[] objArr = 0;
            Object[] objArr2 = 0;
            Object[] objArr3 = 0;
            if (str2 == null || str2.isEmpty()) {
                typefaceCreate = null;
                if (typefaceCreate == null) {
                    if (cq0Var != null) {
                        new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(cq0Var, i4, typefaceCreate));
                    }
                    return typefaceCreate;
                }
                int i6 = 1;
                Object[] objArr4 = !z ? cq0Var != null : c1091q.f59347a2 != 0;
                int i7 = z ? c1091q.f59346a1 : -1;
                Handler handler = new Handler(Looper.getMainLooper());
                jl0 jl0Var = new jl0();
                jl0Var.f57345a0 = cq0Var;
                C1094q2 c1094q2 = c1091q.f59345a0;
                eo0 eo0Var = new eo0(jl0Var, handler);
                int i8 = 4;
                if (objArr4 == true) {
                    pc0 pc0Var = AbstractC0802l.f57815a0;
                    String str3 = ((String) c1094q2.f59369a5) + "-" + i3;
                    Typeface typeface = (Typeface) AbstractC0802l.f57815a0.m214243a0(str3);
                    if (typeface != null) {
                        handler.post(new RunnableC0884n2(jl0Var, typeface, i8, z2));
                        typefaceMo212560a8 = typeface;
                    } else if (i7 == -1) {
                        C0739k c0739kM213767a0 = AbstractC0802l.m213767a0(str3, context, c1094q2, i3);
                        eo0Var.m212716c0(c0739kM213767a0);
                        typefaceMo212560a8 = c0739kM213767a0.f57402a0;
                    } else {
                        try {
                            try {
                                try {
                                    try {
                                        C0739k c0739k = (C0739k) AbstractC0802l.f57816a1.submit(new CallableC0603i(str3, context, c1094q2, i3, 0)).get(i7, TimeUnit.MILLISECONDS);
                                        eo0Var.m212716c0(c0739k);
                                        typefaceMo212560a8 = c0739k.f57402a0;
                                    } catch (TimeoutException unused) {
                                        throw new InterruptedException("timeout");
                                    }
                                } catch (InterruptedException e) {
                                    throw e;
                                }
                            } catch (ExecutionException e2) {
                                throw new RuntimeException(e2);
                            }
                        } catch (InterruptedException unused2) {
                            ((Handler) eo0Var.f56089a2).post(new RunnableC0503fo(eo0Var.f56088a1, i5, (int) (objArr3 == true ? 1 : 0)));
                        }
                    }
                } else {
                    pc0 pc0Var2 = AbstractC0802l.f57815a0;
                    String str4 = ((String) c1094q2.f59369a5) + "-" + i3;
                    Typeface typeface2 = (Typeface) AbstractC0802l.f57815a0.m214243a0(str4);
                    if (typeface2 != null) {
                        handler.post(new RunnableC0884n2(jl0Var, typeface2, i8, objArr2 == true ? 1 : 0));
                        typefaceMo212560a8 = typeface2;
                    } else {
                        C0700j c0700j = new C0700j(objArr == true ? 1 : 0, eo0Var);
                        synchronized (AbstractC0802l.f57817a2) {
                            try {
                                t01 t01Var = AbstractC0802l.f57818a3;
                                ArrayList arrayList = (ArrayList) t01Var.getOrDefault(str4, null);
                                if (arrayList != null) {
                                    arrayList.add(c0700j);
                                } else {
                                    ArrayList arrayList2 = new ArrayList();
                                    arrayList2.add(c0700j);
                                    t01Var.put(str4, arrayList2);
                                    CallableC0603i callableC0603i = new CallableC0603i(str4, context, c1094q2, i3, 1);
                                    ThreadPoolExecutor threadPoolExecutor = AbstractC0802l.f57816a1;
                                    C0700j c0700j2 = new C0700j(i6, str4);
                                    Handler handler2 = Looper.myLooper() == null ? new Handler(Looper.getMainLooper()) : new Handler();
                                    RunnableC0818lf runnableC0818lf = new RunnableC0818lf(3);
                                    runnableC0818lf.f57902a1 = callableC0603i;
                                    runnableC0818lf.f57903a2 = c0700j2;
                                    runnableC0818lf.f57904a3 = handler2;
                                    threadPoolExecutor.execute(runnableC0818lf);
                                }
                            } finally {
                            }
                        }
                    }
                }
            } else {
                typefaceCreate = Typeface.create(str2, 0);
                Typeface typefaceCreate2 = Typeface.create(Typeface.DEFAULT, 0);
                if (typefaceCreate == null || typefaceCreate.equals(typefaceCreate2)) {
                }
                if (typefaceCreate == null) {
                }
            }
        } else {
            typefaceMo212560a8 = f46076a0.mo212560a8(context, (C0934o) interfaceC0881n, resources, i3);
            if (cq0Var != null) {
                if (typefaceMo212560a8 != null) {
                    new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(cq0Var, i4, typefaceMo212560a8));
                } else {
                    cq0Var.m212500a0(-3);
                }
            }
        }
        if (typefaceMo212560a8 != null) {
            f46077a1.m214244a1(m210771a1(resources, i, str, i2, i3), typefaceMo212560a8);
        }
        return typefaceMo212560a8;
    }

    /* renamed from: a1 */
    public static String m210771a1(Resources resources, int i, String str, int i2, int i3) {
        return resources.getResourcePackageName(i) + '-' + str + '-' + i2 + '-' + i + '-' + i3;
    }
}
