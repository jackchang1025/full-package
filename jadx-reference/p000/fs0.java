package p000;

import android.database.Cursor;
import android.os.Looper;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlin.collections.EmptySet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class fs0 {

    /* renamed from: b0 */
    public static final /* synthetic */ int f56318b0 = 0;

    /* renamed from: a0 */
    public volatile d31 f56319a0;

    /* renamed from: a1 */
    public Executor f56320a1;

    /* renamed from: a2 */
    public k31 f56321a2;

    /* renamed from: a4 */
    public boolean f56323a4;

    /* renamed from: a5 */
    public List f56324a5;

    /* renamed from: a9 */
    public final LinkedHashMap f56328a9;

    /* renamed from: a3 */
    public final y60 f56322a3 = mo210468a3();

    /* renamed from: a6 */
    public final LinkedHashMap f56325a6 = new LinkedHashMap();

    /* renamed from: a7 */
    public final ReentrantReadWriteLock f56326a7 = new ReentrantReadWriteLock();

    /* renamed from: a8 */
    public final ThreadLocal f56327a8 = new ThreadLocal();

    static {
        new es0(null);
    }

    public fs0() {
        t60.m214694b5(Collections.synchronizedMap(new LinkedHashMap()), "synchronizedMap(mutableMapOf())");
        this.f56328a9 = new LinkedHashMap();
    }

    /* renamed from: b3 */
    public static Object m212855b3(Class cls, k31 k31Var) {
        if (cls.isInstance(k31Var)) {
            return k31Var;
        }
        if (k31Var instanceof InterfaceC1196rw) {
            return m212855b3(cls, ((InterfaceC1196rw) k31Var).m214550a0());
        }
        return null;
    }

    /* renamed from: a0 */
    public final void m212856a0() {
        if (!this.f56323a4 && Looper.getMainLooper().getThread() == Thread.currentThread()) {
            throw new IllegalStateException("Cannot access database on the main thread since it may potentially lock the UI for a long period of time.");
        }
    }

    /* renamed from: a1 */
    public final void m212857a1() {
        if (!m212859a6().mo210447c3().mo210437b3() && this.f56327a8.get() != null) {
            throw new IllegalStateException("Cannot access database on a different coroutine context inherited from a suspending transaction.");
        }
    }

    /* renamed from: a2 */
    public final void m212858a2() {
        m212856a0();
        m212856a0();
        d31 d31VarMo210447c3 = m212859a6().mo210447c3();
        this.f56322a3.m215249a3(d31VarMo210447c3);
        if (d31VarMo210447c3.mo210438b5()) {
            d31VarMo210447c3.mo210441b9();
        } else {
            d31VarMo210447c3.mo210433a2();
        }
    }

    /* renamed from: a3 */
    public abstract y60 mo210468a3();

    /* renamed from: a4 */
    public abstract k31 mo210469a4(C1110qh c1110qh);

    /* renamed from: a5 */
    public List mo210470a5(Map map) {
        t60.m214695b6(map, "autoMigrationSpecs");
        return EmptyList.f57568a0;
    }

    /* renamed from: a6 */
    public final k31 m212859a6() {
        k31 k31Var = this.f56321a2;
        if (k31Var != null) {
            return k31Var;
        }
        t60.m214724f2("internalOpenHelper");
        throw null;
    }

    /* renamed from: a7 */
    public Set mo210471a7() {
        return EmptySet.f57570a0;
    }

    /* renamed from: a8 */
    public Map mo210472a8() {
        return AbstractC0770a1.m213611f6();
    }

    /* renamed from: a9 */
    public final void m212860a9() {
        m212859a6().mo210447c3().mo210432a1();
        if (m212859a6().mo210447c3().mo210437b3()) {
            return;
        }
        y60 y60Var = this.f56322a3;
        if (y60Var.f61251a4.compareAndSet(false, true)) {
            Executor executor = y60Var.f61247a0.f56320a1;
            if (executor != null) {
                executor.execute(y60Var.f61258b1);
            } else {
                t60.m214724f2("internalQueryExecutor");
                throw null;
            }
        }
    }

    /* renamed from: b0 */
    public final Cursor m212861b0(m31 m31Var) {
        t60.m214695b6(m31Var, "query");
        m212856a0();
        m212857a1();
        return m212859a6().mo210447c3().mo210434a3(m31Var);
    }

    /* renamed from: b1 */
    public final Object m212862b1(Callable callable) {
        m212858a2();
        try {
            Object objCall = callable.call();
            m212863b2();
            return objCall;
        } finally {
            m212860a9();
        }
    }

    /* renamed from: b2 */
    public final void m212863b2() {
        m212859a6().mo210447c3().mo210440b8();
    }
}
