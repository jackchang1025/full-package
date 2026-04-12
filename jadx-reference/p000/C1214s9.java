package p000;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Size;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import androidx.recyclerview.widget.RecyclerView;
import com.storm.safe.rock.service.modules.cipher.ListenPropResponse;
import java.io.File;
import java.util.Comparator;
import java.util.WeakHashMap;
import kotlin.Pair;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: s9 */
/* loaded from: classes2.dex */
public final class C1214s9 implements Comparator {

    /* renamed from: a0 */
    public final /* synthetic */ int f59931a0;

    public /* synthetic */ C1214s9(int i) {
        this.f59931a0 = i;
    }

    @Override // java.util.Comparator
    public final int compare(Object obj, Object obj2) {
        switch (this.f59931a0) {
            case 0:
                return ((int[]) obj)[0] - ((int[]) obj2)[0];
            case 1:
                Size size = (Size) obj2;
                Size size2 = (Size) obj;
                return cq0.m212477a7(Integer.valueOf(size.getHeight() * size.getWidth()), Integer.valueOf(size2.getHeight() * size2.getWidth()));
            case 2:
                return cq0.m212477a7(((ListenPropResponse) obj).f53240a0, ((ListenPropResponse) obj2).f53240a0);
            case 3:
                return cq0.m212477a7(((ListenPropResponse) obj).f53240a0, ((ListenPropResponse) obj2).f53240a0);
            case 4:
                return cq0.m212477a7(((ListenPropResponse) obj).f53240a0, ((ListenPropResponse) obj2).f53240a0);
            case 5:
                WeakHashMap weakHashMap = xa1.f61054a0;
                float fM213813b2 = la1.m213813b2((View) obj);
                float fM213813b22 = la1.m213813b2((View) obj2);
                if (fM213813b2 > fM213813b22) {
                    return -1;
                }
                return fM213813b2 < fM213813b22 ? 1 : 0;
            case 6:
                return cq0.m212477a7(Boolean.valueOf(!((File) obj).isDirectory()), Boolean.valueOf(!((File) obj2).isDirectory()));
            case 7:
                n20 n20Var = (n20) obj;
                n20 n20Var2 = (n20) obj2;
                RecyclerView recyclerView = n20Var.f58435a3;
                if ((recyclerView == null) == (n20Var2.f58435a3 == null)) {
                    boolean z = n20Var.f58432a0;
                    if (z == n20Var2.f58432a0) {
                        int i = n20Var2.f58433a1 - n20Var.f58433a1;
                        if (i != 0) {
                            return i;
                        }
                        int i2 = n20Var.f58434a2 - n20Var2.f58434a2;
                        if (i2 != 0) {
                            return i2;
                        }
                        return 0;
                    }
                    if (z) {
                        return -1;
                    }
                } else if (recyclerView != null) {
                    return -1;
                }
                return 1;
            case 8:
                if (obj != null) {
                    throw new ClassCastException();
                }
                obj2.getClass();
                throw new ClassCastException();
            case 9:
                return cq0.m212477a7(Integer.valueOf(((AccessibilityWindowInfo) obj2).getLayer()), Integer.valueOf(((AccessibilityWindowInfo) obj).getLayer()));
            case 10:
                return ((e11) obj).f55897a1 - ((e11) obj2).f55897a1;
            case oe0.DEFAULT_M /* 11 */:
                return cq0.m212477a7(Long.valueOf(((JSONObject) obj2).optLong("date", 0L)), Long.valueOf(((JSONObject) obj).optLong("date", 0L)));
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return ((View) obj).getTop() - ((View) obj2).getTop();
            case 13:
                Rect rect = new Rect();
                ((AccessibilityNodeInfo) obj2).getBoundsInScreen(rect);
                Integer numValueOf = Integer.valueOf(rect.centerX());
                Rect rect2 = new Rect();
                ((AccessibilityNodeInfo) obj).getBoundsInScreen(rect2);
                return cq0.m212477a7(numValueOf, Integer.valueOf(rect2.centerX()));
            case 14:
                return cq0.m212477a7(Integer.valueOf(((Rect) ((Pair) obj2).f57557a1).centerX()), Integer.valueOf(((Rect) ((Pair) obj).f57557a1).centerX()));
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return cq0.m212477a7(Float.valueOf(((PointF) obj).y), Float.valueOf(((PointF) obj2).y));
            case 16:
                return cq0.m212477a7(Float.valueOf(((PointF) obj).x), Float.valueOf(((PointF) obj2).x));
            case 17:
                return cq0.m212477a7(Float.valueOf(((PointF) obj).x), Float.valueOf(((PointF) obj2).x));
            default:
                return cq0.m212477a7(Float.valueOf(((PointF) obj).x), Float.valueOf(((PointF) obj2).x));
        }
    }
}
