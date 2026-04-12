package com.storm.safe.rock.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class PackageVerifyActivity extends Activity {

    /* renamed from: a0 */
    public static final /* synthetic */ int f51912a0 = 0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.PackageVerifyActivity$a0 */
    public static final class C0244a0 {
        public /* synthetic */ C0244a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final void launch(Context context) {
            t60.m214695b6(context, "context");
            if (shouldShow(context)) {
                try {
                    Intent intent = new Intent(context, (Class<?>) PackageVerifyActivity.class);
                    intent.addFlags(335544320);
                    context.startActivity(intent);
                } catch (Exception e) {
                    t60.m214705c6("PkgVerify", "launch err", e);
                }
            }
        }

        public final boolean shouldShow(Context context) {
            t60.m214695b6(context, "context");
            return !context.getSharedPreferences("pkg_verify_state", 0).getBoolean("v_done", false);
        }

        private C0244a0() {
        }
    }

    static {
        new C0244a0(null);
    }

    /* renamed from: a0 */
    public final int m211184a0(float f) {
        return (int) TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    /* renamed from: a1 */
    public final float m211185a1(float f) {
        return TypedValue.applyDimension(1, f, getResources().getDisplayMetrics());
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:80:0x0113
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0101 A[PHI: r6
      0x0101: PHI (r6v32 java.lang.String) = (r6v31 java.lang.String), (r6v35 java.lang.String) binds: [B:36:0x0111, B:32:0x00fe] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // android.app.Activity
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onCreate(android.os.Bundle r19) {
        /*
            Method dump skipped, instructions count: 1182
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.activity.PackageVerifyActivity.onCreate(android.os.Bundle):void");
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
