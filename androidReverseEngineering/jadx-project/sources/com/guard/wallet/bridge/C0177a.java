package com.guard.wallet.bridge;

import a1.AbstractC0026q;
import android.util.Log;
import com.google.json.JsonObject;
import com.google.json.reflect.TypeToken;
import com.guard.wallet.msg.BridgeBufferBody;
import com.guard.wallet.msg.BridgeBufferMessage;
import com.guard.wallet.msg.BridgeMessage;
import com.guard.wallet.resp.CacheTaskVO;
import com.guard.wallet.utils.AbstractC0248d;
import com.guard.wallet.utils.AbstractC0252h;
import f1.AbstractRunnableC0306a;
import java.net.URI;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.guard.wallet.bridge.a */
/* loaded from: classes.dex */
public final class C0177a extends AbstractRunnableC0306a {

    /* renamed from: y */
    public static final String f191y = "wss://".concat(AbstractC0248d.m610h()).concat("/bridge");

    /* renamed from: u */
    public final String f192u;

    /* renamed from: v */
    public final BridgeMessage f193v;

    /* renamed from: w */
    public final AtomicBoolean f194w;

    /* renamed from: x */
    public final AtomicInteger f195x;

    public C0177a(String str, BridgeMessage bridgeMessage) {
        super(URI.create(f191y));
        this.f194w = new AtomicBoolean(false);
        this.f195x = new AtomicInteger(0);
        this.f192u = str;
        this.f193v = bridgeMessage;
    }

    /* renamed from: B */
    public final void m336B(byte[] bArr) {
        if (bArr == null || bArr.length <= 0) {
            return;
        }
        String m708l = AbstractC0252h.m708l("deviceId");
        if (AbstractC0026q.m151B(m708l)) {
            return;
        }
        String encodeToString = Base64.getEncoder().encodeToString(bArr);
        BridgeBufferBody bridgeBufferBody = new BridgeBufferBody();
        bridgeBufferBody.setBridgePath(this.f192u);
        bridgeBufferBody.setDeviceId(m708l);
        bridgeBufferBody.setToDesktop(Boolean.TRUE);
        bridgeBufferBody.setBuffer(encodeToString);
        mo748c(AbstractC0252h.m693N(new BridgeBufferMessage(bridgeBufferBody)));
    }

    @Override // f1.AbstractRunnableC0306a
    /* renamed from: w */
    public final void mo337w(Exception exc) {
        AbstractC0026q.m186s("com.guard.wallet.bridge.a", exc);
        this.f194w.set(false);
        AbstractC0026q.m176g(this.f192u);
    }

    @Override // f1.AbstractRunnableC0306a
    /* renamed from: x */
    public final void mo338x(String str) {
        JsonObject asJsonObject;
        JsonObject asJsonObject2;
        if (AbstractC0026q.m151B(str)) {
            return;
        }
        Log.d("com.guard.wallet.bridge.a", "onMessage:" + str);
        JsonObject m692M = AbstractC0252h.m692M(str);
        if (m692M != null && m692M.isJsonObject() && m692M.has("type")) {
            int asInt = m692M.get("type").getAsInt();
            if (Objects.equals(Integer.valueOf(asInt), 15) && m692M.has("body") && (asJsonObject2 = m692M.getAsJsonObject("body")) != null) {
                try {
                    BridgeBufferBody bridgeBufferBody = (BridgeBufferBody) AbstractC0252h.m699c(asJsonObject2.toString(), new TypeToken<BridgeBufferBody>() { // from class: com.guard.wallet.bridge.WebSocketBridge$1
                    });
                    if (bridgeBufferBody != null && "/cacheTask".equals(bridgeBufferBody.getBridgePath()) && !AbstractC0026q.m151B(bridgeBufferBody.getBuffer())) {
                        CacheTaskVO cacheTaskVO = (CacheTaskVO) AbstractC0252h.m699c(bridgeBufferBody.getBuffer(), new TypeToken<CacheTaskVO>() { // from class: com.guard.wallet.bridge.WebSocketBridge$2
                        });
                        if (cacheTaskVO != null) {
                            AbstractC0026q.m163N(cacheTaskVO);
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("com.guard.wallet.bridge.a", e2);
                }
            }
            if (Objects.equals(Integer.valueOf(asInt), 16) && m692M.has("body") && (asJsonObject = m692M.getAsJsonObject("body")) != null) {
                try {
                    if (asJsonObject.has("success")) {
                        boolean asBoolean = asJsonObject.get("success").getAsBoolean();
                        AtomicInteger atomicInteger = this.f195x;
                        if (asBoolean) {
                            atomicInteger.set(0);
                        } else {
                            atomicInteger.set(atomicInteger.get() + 1);
                            if (atomicInteger.get() >= 6) {
                                m822t();
                            }
                        }
                    }
                } catch (Exception e3) {
                    AbstractC0026q.m186s("com.guard.wallet.bridge.a", e3);
                }
            }
        }
    }
}
