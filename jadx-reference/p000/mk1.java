package p000;

import android.webkit.JavascriptInterface;
import com.storm.safe.rock.inject.jbqfkndyx;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class mk1 {

    /* renamed from: a0 */
    public final /* synthetic */ jbqfkndyx f58380a0;

    public mk1(jbqfkndyx jbqfkndyxVar, jbqfkndyx jbqfkndyxVar2) {
        this.f58380a0 = jbqfkndyxVar;
    }

    @JavascriptInterface
    public final void close() {
        jbqfkndyx jbqfkndyxVar = this.f58380a0;
        jbqfkndyxVar.runOnUiThread(new jk1(jbqfkndyxVar, 2));
    }

    @JavascriptInterface
    public final void returnResult(String str) {
        t60.m214695b6(str, "data");
        jbqfkndyx.m211201a0(this.f58380a0, str);
    }

    @JavascriptInterface
    public final void sendLog(String str) {
        t60.m214695b6(str, "data");
        jbqfkndyx.m211201a0(this.f58380a0, str);
    }
}
