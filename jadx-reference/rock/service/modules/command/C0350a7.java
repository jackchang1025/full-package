package com.storm.safe.rock.service.modules.command;

import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.json.JSONObject;
import p000.InterfaceC0726jp;
import p000.jj0;
import p000.kg1;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a7 */
/* loaded from: classes2.dex */
public final class C0350a7 {

    /* renamed from: a0 */
    public final uz0 f53597a0;

    /* renamed from: a1 */
    public final CopyOnWriteArrayList f53598a1;

    /* renamed from: a2 */
    public final ConcurrentHashMap f53599a2;

    static {
        new jj0(null);
    }

    public C0350a7(uz0 uz0Var) {
        t60.m214695b6(uz0Var, "context");
        this.f53597a0 = uz0Var;
        this.f53598a1 = new CopyOnWriteArrayList();
        this.f53599a2 = new ConcurrentHashMap();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211883a0(JSONObject jSONObject, ContinuationImpl continuationImpl) {
        NetworkCommandDispatcher$dispatch$1 networkCommandDispatcher$dispatch$1;
        Exception e;
        String str;
        Exception e2;
        String str2;
        if (continuationImpl instanceof NetworkCommandDispatcher$dispatch$1) {
            networkCommandDispatcher$dispatch$1 = (NetworkCommandDispatcher$dispatch$1) continuationImpl;
            int i = networkCommandDispatcher$dispatch$1.f53523a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                networkCommandDispatcher$dispatch$1.f53523a3 = i - Integer.MIN_VALUE;
            } else {
                networkCommandDispatcher$dispatch$1 = new NetworkCommandDispatcher$dispatch$1(this, continuationImpl);
            }
        }
        Object obj = networkCommandDispatcher$dispatch$1.f53521a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = networkCommandDispatcher$dispatch$1.f53523a3;
        if (i2 != 0) {
            if (i2 == 1) {
                str = networkCommandDispatcher$dispatch$1.f53520a0;
                try {
                    kg1.m213544f4(obj);
                    return Boolean.TRUE;
                } catch (Exception e3) {
                    e = e3;
                    t60.m214705c6("CommandDispatcher", "处理命令 " + str + " 失败", e);
                    return Boolean.FALSE;
                }
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str2 = networkCommandDispatcher$dispatch$1.f53520a0;
            try {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            } catch (Exception e4) {
                e2 = e4;
                t60.m214705c6("CommandDispatcher", "处理命令 " + str2 + " 失败", e2);
                return Boolean.FALSE;
            }
        }
        kg1.m213544f4(obj);
        String strOptString = jSONObject.optString(StringUtil.m212470a0("KFYcN0w2CA=="), "");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("params");
        t60.m214694b5(strOptString, "command");
        if (strOptString.length() == 0) {
            t60.m214726f4("CommandDispatcher", "收到空命令");
            return Boolean.FALSE;
        }
        t60.m214702c3("CommandDispatcher", "分发命令: ".concat(strOptString));
        ConcurrentHashMap concurrentHashMap = this.f53599a2;
        InterfaceC0726jp interfaceC0726jp = (InterfaceC0726jp) concurrentHashMap.get(strOptString);
        uz0 uz0Var = this.f53597a0;
        if (interfaceC0726jp == null) {
            Iterator it = this.f53598a1.iterator();
            while (it.hasNext()) {
                InterfaceC0726jp interfaceC0726jp2 = (InterfaceC0726jp) it.next();
                if (interfaceC0726jp2.mo210872a0(strOptString)) {
                    try {
                        concurrentHashMap.put(strOptString, interfaceC0726jp2);
                        networkCommandDispatcher$dispatch$1.f53520a0 = strOptString;
                        networkCommandDispatcher$dispatch$1.f53523a3 = 2;
                        if (interfaceC0726jp2.mo210874a2(strOptString, jSONObjectOptJSONObject, uz0Var, networkCommandDispatcher$dispatch$1) != coroutineSingletons) {
                            return Boolean.TRUE;
                        }
                    } catch (Exception e5) {
                        e2 = e5;
                        str2 = strOptString;
                        t60.m214705c6("CommandDispatcher", "处理命令 " + str2 + " 失败", e2);
                        return Boolean.FALSE;
                    }
                }
            }
            t60.m214726f4("CommandDispatcher", "未找到命令处理器: ".concat(strOptString));
            return Boolean.FALSE;
        }
        try {
            networkCommandDispatcher$dispatch$1.f53520a0 = strOptString;
            networkCommandDispatcher$dispatch$1.f53523a3 = 1;
            if (interfaceC0726jp.mo210874a2(strOptString, jSONObjectOptJSONObject, uz0Var, networkCommandDispatcher$dispatch$1) != coroutineSingletons) {
                return Boolean.TRUE;
            }
        } catch (Exception e6) {
            e = e6;
            str = strOptString;
            t60.m214705c6("CommandDispatcher", "处理命令 " + str + " 失败", e);
            return Boolean.FALSE;
        }
        return coroutineSingletons;
    }
}
