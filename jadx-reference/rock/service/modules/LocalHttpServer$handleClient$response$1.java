package com.storm.safe.rock.service.modules;

import java.util.Map;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONObject;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer$handleClient$response$1", m214403f = "LocalHttpServer.kt", m214404l = {319}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class LocalHttpServer$handleClient$response$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52800a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0322a7 f52801a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52802a3;

    /* renamed from: a4 */
    public final /* synthetic */ String f52803a4;

    /* renamed from: a5 */
    public final /* synthetic */ Object f52804a5;

    /* renamed from: a6 */
    public final /* synthetic */ Ref$ObjectRef f52805a6;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.LocalHttpServer$handleClient$response$1$1", m214403f = "LocalHttpServer.kt", m214404l = {320}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.modules.LocalHttpServer$handleClient$response$1$1 */
    final class C03031 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public int f52806a1;

        /* renamed from: a2 */
        public final /* synthetic */ C0322a7 f52807a2;

        /* renamed from: a3 */
        public final /* synthetic */ String f52808a3;

        /* renamed from: a4 */
        public final /* synthetic */ String f52809a4;

        /* renamed from: a5 */
        public final /* synthetic */ Object f52810a5;

        /* renamed from: a6 */
        public final /* synthetic */ Ref$ObjectRef f52811a6;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C03031(C0322a7 c0322a7, String str, String str2, Map map, Ref$ObjectRef ref$ObjectRef, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52807a2 = c0322a7;
            this.f52808a3 = str;
            this.f52809a4 = str2;
            this.f52810a5 = map;
            this.f52811a6 = ref$ObjectRef;
        }

        /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Object, java.util.Map] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C03031(this.f52807a2, this.f52808a3, this.f52809a4, this.f52810a5, this.f52811a6, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) {
            return ((C03031) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
        }

        /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.Object, java.util.Map] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            int i = this.f52806a1;
            if (i != 0) {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return obj;
            }
            kg1.m213544f4(obj);
            String str = (String) this.f52811a6.f57626a0;
            this.f52806a1 = 1;
            Object objM211584a0 = C0322a7.m211584a0(this.f52807a2, this.f52809a4, this.f52810a5, str, this);
            return objM211584a0 == coroutineSingletons ? coroutineSingletons : objM211584a0;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalHttpServer$handleClient$response$1(C0322a7 c0322a7, String str, String str2, Map map, Ref$ObjectRef ref$ObjectRef, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52801a2 = c0322a7;
        this.f52802a3 = str;
        this.f52803a4 = str2;
        this.f52804a5 = map;
        this.f52805a6 = ref$ObjectRef;
    }

    /* JADX WARN: Type inference failed for: r4v0, types: [java.lang.Object, java.util.Map] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new LocalHttpServer$handleClient$response$1(this.f52801a2, this.f52802a3, this.f52803a4, this.f52804a5, this.f52805a6, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalHttpServer$handleClient$response$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Object, java.util.Map] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52800a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            C03031 c03031 = new C03031(this.f52801a2, this.f52802a3, this.f52803a4, this.f52804a5, this.f52805a6, null);
            this.f52800a1 = 1;
            obj = AbstractC0780a0.m213697a8(30000L, c03031, this);
            if (obj == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        JSONObject jSONObject = (JSONObject) obj;
        if (jSONObject != null) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("error", "request timeout");
        return jSONObject2;
    }
}
