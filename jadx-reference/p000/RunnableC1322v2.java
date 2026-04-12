package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import androidx.profileinstaller.ProfileInstallerInitializer;
import com.storm.safe.rock.service.modules.yw5xud.umrkmgrri;
import com.storm.safe.rock.service.tisxhskrc;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: v2 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1322v2 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f60567a0;

    /* renamed from: a1 */
    public final /* synthetic */ Context f60568a1;

    public /* synthetic */ RunnableC1322v2(Context context, int i) {
        this.f60567a0 = i;
        this.f60568a1 = context;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e6  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() throws InterruptedException, PackageManager.NameNotFoundException, IOException {
        dc0 dc0Var;
        Object systemService;
        Context context;
        switch (this.f60567a0) {
            case 0:
                if (Build.VERSION.SDK_INT >= 33) {
                    Context context2 = this.f60568a1;
                    ComponentName componentName = new ComponentName(context2, "androidx.appcompat.app.AppLocalesMetadataHolderService");
                    if (context2.getPackageManager().getComponentEnabledSetting(componentName) != 1) {
                        if (AbstractC0496fi.m212821a0()) {
                            Iterator it = AbstractC1325v5.f60581a6.iterator();
                            while (true) {
                                xc0 xc0Var = (xc0) it;
                                if (xc0Var.hasNext()) {
                                    AbstractC1325v5 abstractC1325v5 = (AbstractC1325v5) ((WeakReference) xc0Var.next()).get();
                                    if (abstractC1325v5 != null && (context = ((LayoutInflaterFactory2C1367w8) abstractC1325v5).f60809b0) != null) {
                                        systemService = context.getSystemService("locale");
                                    }
                                } else {
                                    systemService = null;
                                }
                            }
                            dc0Var = systemService != null ? new dc0(new ec0(AbstractC1324v4.m214894a0(systemService))) : dc0.f55690a1;
                            if (dc0Var.f55691a0.f55969a0.isEmpty()) {
                                String strM213532d8 = kg1.m213532d8(context2);
                                Object systemService2 = context2.getSystemService("locale");
                                if (systemService2 != null) {
                                    AbstractC1324v4.m214895a1(systemService2, AbstractC1323v3.m214893a0(strM213532d8));
                                }
                            }
                            context2.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
                        } else {
                            dc0Var = AbstractC1325v5.f60577a2;
                            if (dc0Var == null) {
                            }
                            if (dc0Var.f55691a0.f55969a0.isEmpty()) {
                            }
                            context2.getPackageManager().setComponentEnabledSetting(componentName, 1, 1);
                        }
                    }
                }
                AbstractC1325v5.f60580a5 = true;
                break;
            case 1:
                C0922nq.uploadPendingCrashLogs$lambda$4(this.f60568a1);
                break;
            case 2:
                (Build.VERSION.SDK_INT >= 28 ? wo0.m215085a0(Looper.getMainLooper()) : new Handler(Looper.getMainLooper())).postDelayed(new RunnableC1322v2(this.f60568a1, 3), new Random().nextInt(Math.max(1000, 1)) + 5000);
                break;
            case 3:
                new ThreadPoolExecutor(0, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue()).execute(new RunnableC1322v2(this.f60568a1, 4));
                break;
            case 4:
                AbstractC1117qo.m214464g3(this.f60568a1, new ExecutorC0111av(1), AbstractC1117qo.f59539a3, false);
                break;
            case 5:
                tisxhskrc.C0380a0.tryForceRebindAccessibility$lambda$3(this.f60568a1);
                break;
            default:
                umrkmgrri.C0373a0.start$lambda$0(this.f60568a1);
                break;
        }
    }

    public /* synthetic */ RunnableC1322v2(ProfileInstallerInitializer profileInstallerInitializer, Context context) {
        this.f60567a0 = 2;
        this.f60568a1 = context;
    }
}
