package p000;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PointF;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.List;
import kotlin.coroutines.AbstractC0775a0;
import kotlinx.coroutines.android.C0785a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class a30 {

    /* renamed from: a0 */
    public final dqtvuisjd f31a0;

    /* renamed from: a1 */
    public final Context f32a1;

    /* renamed from: a2 */
    public final C0873ms f33a2;

    static {
        new z20(null);
    }

    public a30(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "service");
        this.f31a0 = dqtvuisjdVar;
        Context applicationContext = dqtvuisjdVar.getApplicationContext();
        t60.m214694b5(applicationContext, "service.applicationContext");
        this.f32a1 = applicationContext;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f33a2 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
    }

    /* renamed from: a1 */
    public static void m50a1(a30 a30Var, float f, float f2) {
        a30Var.getClass();
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            a30Var.f31a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 1L, 1000L)).build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("GestureExecutor", "执行长按失败", e);
        }
    }

    /* renamed from: a0 */
    public final void m51a0(float f, float f2) {
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            this.f31a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 1L, 50L)).build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("GestureExecutor", "执行点击失败", e);
        }
    }

    /* renamed from: a2 */
    public final void m52a2(List list) {
        t60.m214695b6(list, "pathPoints");
        t60.m214704c5("GestureExecutor", "🔓🔓🔓 [performPatternDrag] ========== 进入函数 ==========");
        if (list.isEmpty()) {
            t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 路径点为空");
            return;
        }
        if (list.size() < 2) {
            t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 路径点不足2个，无法执行图案手势");
            return;
        }
        int i = 0;
        PointF pointF = (PointF) list.get(0);
        PointF pointF2 = (PointF) AbstractC0715je.m213296i3(list);
        t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 路径点数: " + list.size());
        t60.m214704c5("GestureExecutor", AbstractC0003a2.m29b0("🔓 [performPatternDrag] 起点: (", pointF.x, ", ", pointF.y, ")"));
        t60.m214704c5("GestureExecutor", AbstractC0003a2.m29b0("🔓 [performPatternDrag] 终点: (", pointF2.x, ", ", pointF2.y, ")"));
        for (Object obj : list) {
            int i2 = i + 1;
            if (i < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            PointF pointF3 = (PointF) obj;
            t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 路径点[" + i + "]: (" + pointF3.x + ", " + pointF3.y + ")");
            i = i2;
        }
        try {
            Path path = new Path();
            path.moveTo(pointF.x, pointF.y);
            int size = list.size();
            for (int i3 = 1; i3 < size; i3++) {
                path.lineTo(((PointF) list.get(i3)).x, ((PointF) list.get(i3)).y);
            }
            long size2 = (list.size() - 1) * 180;
            long jMax = Math.max(1000L, Math.max(1000L, size2));
            t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 时长: 请求=1000ms, 计算=" + size2 + "ms, 最终=" + jMax + "ms");
            GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 1L, jMax)).build();
            t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] 准备调用 dispatchGesture...");
            try {
                boolean zDispatchGesture = this.f31a0.dispatchGesture(gestureDescriptionBuild, new C0429du(2), null);
                t60.m214704c5("GestureExecutor", "🔓 [performPatternDrag] dispatchGesture 返回值: " + zDispatchGesture);
                if (zDispatchGesture) {
                    return;
                }
                t60.m214704c5("GestureExecutor", "❌ [performPatternDrag] dispatchGesture返回false! 可能原因:");
                t60.m214704c5("GestureExecutor", "   - 无障碍服务未正确连接");
                t60.m214704c5("GestureExecutor", "   - 另一个手势正在执行中");
                t60.m214704c5("GestureExecutor", "   - 手势参数无效");
            } catch (Exception e) {
                e = e;
                t60.m214705c6("GestureExecutor", "❌ [performPatternDrag] 执行异常", e);
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* renamed from: a3 */
    public final void m53a3(float f, float f2, float f3, float f4, long j) {
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            this.f31a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 1L, Math.max(j, 300L))).build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("GestureExecutor", "执行滑动失败", e);
        }
    }

    /* renamed from: a4 */
    public final void m54a4(ArrayList arrayList, long j) {
        if (arrayList.size() < 2) {
            return;
        }
        float f = ((PointF) arrayList.get(0)).x;
        float f2 = ((PointF) arrayList.get(0)).y;
        try {
            Path path = new Path();
            path.moveTo(((PointF) arrayList.get(0)).x, ((PointF) arrayList.get(0)).y);
            int size = arrayList.size();
            for (int i = 1; i < size; i++) {
                path.lineTo(((PointF) arrayList.get(i)).x, ((PointF) arrayList.get(i)).y);
            }
            this.f31a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, Math.max(j, 300L))).build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("GestureExecutor", "执行多点滑动失败", e);
        }
    }
}
