package p015s;

import com.guard.wallet.MainApplication;
import com.guard.wallet.req.MessageRecordVO;
import java.util.Date;
import java.util.List;

/* renamed from: s.a */
/* loaded from: classes.dex */
public final class C0896a {

    /* renamed from: a */
    public final /* synthetic */ int f1988a;

    /* renamed from: b */
    public Integer f1989b;

    /* renamed from: c */
    public Object f1990c;

    /* renamed from: d */
    public Object f1991d;

    /* renamed from: e */
    public Integer f1992e;

    public C0896a() {
        this.f1988a = 0;
        this.f1989b = 1;
    }

    /* renamed from: a */
    public final void m1330a(MessageRecordVO messageRecordVO) {
        Long valueOf = Long.valueOf(new Date().getTime());
        this.f1992e = Integer.valueOf(this.f1992e.intValue() + 1);
        if (valueOf.longValue() - ((Long) this.f1991d).longValue() >= this.f1989b.intValue() || this.f1992e.intValue() >= ((Integer) this.f1990c).intValue()) {
            MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
            this.f1992e = 0;
            this.f1991d = valueOf;
        }
    }

    public final String toString() {
        switch (this.f1988a) {
            case 0:
                return "ExceptionEntity{direction=" + this.f1989b + ", reason='" + ((String) this.f1990c) + "', error='" + ((String) this.f1991d) + "', reasonList=" + ((List) this.f1992e) + '}';
            default:
                return super.toString();
        }
    }

    public C0896a(Integer num, Integer num2) {
        this.f1988a = 1;
        this.f1991d = 0L;
        this.f1992e = 0;
        this.f1989b = num;
        this.f1990c = num2;
    }
}
