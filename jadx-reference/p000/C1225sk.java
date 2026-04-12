package p000;

import android.content.res.AssetManager;
import android.os.Build;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.concurrent.Executor;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sk */
/* loaded from: classes.dex */
public final class C1225sk {

    /* renamed from: a0 */
    public final Executor f60007a0;

    /* renamed from: a1 */
    public final to0 f60008a1;

    /* renamed from: a2 */
    public final byte[] f60009a2;

    /* renamed from: a3 */
    public final File f60010a3;

    /* renamed from: a4 */
    public final String f60011a4;

    /* renamed from: a5 */
    public boolean f60012a5 = false;

    /* renamed from: a6 */
    public C1230sp[] f60013a6;

    /* renamed from: a7 */
    public byte[] f60014a7;

    public C1225sk(AssetManager assetManager, Executor executor, to0 to0Var, String str, File file) {
        this.f60007a0 = executor;
        this.f60008a1 = to0Var;
        this.f60011a4 = str;
        this.f60010a3 = file;
        int i = Build.VERSION.SDK_INT;
        byte[] bArr = null;
        if (i <= 33) {
            switch (i) {
                case 24:
                case 25:
                    bArr = t60.f60167b9;
                    break;
                case 26:
                    bArr = t60.f60166b8;
                    break;
                case 27:
                    bArr = t60.f60165b7;
                    break;
                case 28:
                case 29:
                case 30:
                    bArr = t60.f60164b6;
                    break;
                case 31:
                case 32:
                case 33:
                    bArr = t60.f60163b5;
                    break;
            }
        }
        this.f60009a2 = bArr;
    }

    /* renamed from: a0 */
    public final FileInputStream m214619a0(AssetManager assetManager, String str) {
        try {
            return assetManager.openFd(str).createInputStream();
        } catch (FileNotFoundException e) {
            String message = e.getMessage();
            if (message == null) {
                return null;
            }
            message.contains("compressed");
            return null;
        }
    }

    /* renamed from: a1 */
    public final void m214620a1(int i, Serializable serializable) {
        this.f60007a0.execute(new RunnableC1224sj(i, 0, this, serializable));
    }
}
