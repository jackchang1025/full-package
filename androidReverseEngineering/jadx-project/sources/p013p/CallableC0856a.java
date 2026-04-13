package p013p;

import a1.AbstractC0026q;
import android.support.v4.os.EnvironmentCompat;
import android.util.Log;
import com.guard.wallet.entity.CheckPortResult;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import p005h.C0318e;

/* renamed from: p.a */
/* loaded from: classes.dex */
public final class CallableC0856a implements Callable {

    /* renamed from: a */
    public final /* synthetic */ int f1674a;

    /* renamed from: b */
    public final Object f1675b;

    /* renamed from: c */
    public Object f1676c;

    public /* synthetic */ CallableC0856a(Object obj, Object obj2, int i2) {
        this.f1674a = i2;
        this.f1675b = obj;
        this.f1676c = obj2;
    }

    /* renamed from: a */
    public final String m1239a() {
        int i2 = this.f1674a;
        Object obj = this.f1675b;
        switch (i2) {
            case 0:
                String str = (String) obj;
                if (AbstractC0026q.m151B(str)) {
                    return null;
                }
                if (AbstractC0026q.m151B((String) this.f1676c)) {
                    this.f1676c = AbstractC0026q.m191x(str);
                }
                if (AbstractC0026q.m151B((String) this.f1676c)) {
                    this.f1676c = EnvironmentCompat.MEDIA_UNKNOWN;
                }
                String concat = AbstractC0251g.i0().concat("/").concat((String) this.f1676c);
                Log.d("DownLoadCallable", concat);
                if (AbstractC0857b.m1240a(str, concat)) {
                    return concat;
                }
                return null;
            default:
                String str2 = (String) obj;
                if (AbstractC0026q.m151B(str2)) {
                    return null;
                }
                if (AbstractC0026q.m151B((String) this.f1676c)) {
                    this.f1676c = AbstractC0026q.m191x(str2);
                }
                if (AbstractC0026q.m151B((String) this.f1676c)) {
                    this.f1676c = EnvironmentCompat.MEDIA_UNKNOWN;
                }
                String concat2 = AbstractC0251g.i0().concat("/").concat((String) this.f1676c);
                Log.d("DownLoadCallable", concat2);
                if (AbstractC0857b.m1241b(str2, concat2)) {
                    return concat2;
                }
                return null;
        }
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        CheckPortResult m851J;
        switch (this.f1674a) {
            case 0:
                break;
            case 1:
                break;
            default:
                int intValue = ((Integer) this.f1676c).intValue();
                Integer num = (Integer) this.f1675b;
                if (intValue >= num.intValue() && C0318e.m844S() != null) {
                    AtomicInteger atomicInteger = new AtomicInteger(num.intValue());
                    while (true) {
                        int andIncrement = atomicInteger.getAndIncrement();
                        if (andIncrement <= ((Integer) this.f1676c).intValue() && !C0318e.m844S().f624v.get()) {
                            if (!AbstractC0026q.m154E(andIncrement) && (m851J = C0318e.m844S().m851J(andIncrement)) != null && m851J.isConnected()) {
                                break;
                            }
                        }
                    }
                }
                break;
        }
        return m1239a();
    }
}
