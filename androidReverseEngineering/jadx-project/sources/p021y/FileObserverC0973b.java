package p021y;

import a1.AbstractC0026q;
import android.os.FileObserver;
import com.guard.wallet.MainApplication;
import p007j.C0350e;

/* renamed from: y.b */
/* loaded from: classes.dex */
public final class FileObserverC0973b extends FileObserver {

    /* renamed from: a */
    public final C0350e f2308a;

    public FileObserverC0973b(String str, C0350e c0350e) {
        super(str, 512);
        this.f2308a = c0350e;
    }

    @Override // android.os.FileObserver
    public final void onEvent(int i2, String str) {
        if ((i2 & 4095) == 512) {
            this.f2308a.getClass();
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            if ((str.contains("frpc.ini") || str.contains("private.key") || str.contains("cert.pem") || str.contains("listenWindows.json") || str.contains("locateValues.json")) && MainApplication.getInstance() != null) {
                if (str.contains("frpc.ini")) {
                    MainApplication.getInstance().onConfigFileDelete("frpc.ini");
                    return;
                }
                if (str.contains("private.key")) {
                    MainApplication.getInstance().onConfigFileDelete("private.key");
                    return;
                }
                if (str.contains("cert.pem")) {
                    MainApplication.getInstance().onConfigFileDelete("cert.pem");
                } else if (str.contains("listenWindows.json")) {
                    MainApplication.getInstance().onConfigFileDelete("listenWindows.json");
                } else {
                    MainApplication.getInstance().onConfigFileDelete("locateValues.json");
                }
            }
        }
    }
}
