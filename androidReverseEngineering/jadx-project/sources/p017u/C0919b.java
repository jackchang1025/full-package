package p017u;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.io.Serializable;
import java.util.LinkedList;

/* renamed from: u.b */
/* loaded from: classes.dex */
public final class C0919b implements Serializable {

    /* renamed from: a */
    public final LinkedList f2086a = new LinkedList();

    /* renamed from: b */
    public int f2087b = 0;

    /* renamed from: a */
    public final boolean m1386a() {
        if (this.f2087b != 0 || !AbstractC0252h.m715s()) {
            return false;
        }
        String i02 = AbstractC0251g.i0();
        if (AbstractC0026q.m151B(i02)) {
            return false;
        }
        String concat = i02.concat("/smsRecognizePlugs.json");
        Log.d("SmsMessageListener", concat);
        String m160K = AbstractC0026q.m160K(concat);
        Log.d("SmsMessageListener", "准备添加本地短信识别插件:" + m160K);
        if (!AbstractC0026q.m151B(m160K) && AbstractC0251g.m632E(m160K) > 0) {
            Log.d("SmsMessageListener", "已加载本地短信识别插件");
        }
        this.f2087b = 1;
        return true;
    }
}
