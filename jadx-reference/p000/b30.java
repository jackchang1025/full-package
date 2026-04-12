package p000;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.DisplayMetrics;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0319a4;
import org.json.JSONArray;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class b30 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f45678a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0319a4 f45679a1;

    public /* synthetic */ b30(C0319a4 c0319a4, int i) {
        this.f45678a0 = i;
        this.f45679a1 = c0319a4;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f45678a0) {
            case 0:
                dqtvuisjd dqtvuisjdVar = this.f45679a1.f53055a1;
                try {
                    AccessibilityServiceInfo serviceInfo = dqtvuisjdVar.getServiceInfo();
                    if (serviceInfo != null) {
                        serviceInfo.flags &= -5;
                        dqtvuisjdVar.setServiceInfo(serviceInfo);
                        t60.m214702c3("GestureRecorderManager", "🔐 触摸探索模式已关闭");
                        if (Build.VERSION.SDK_INT >= 30) {
                            DisplayMetrics displayMetrics = dqtvuisjdVar.getResources().getDisplayMetrics();
                            Region region = new Region();
                            int i = displayMetrics.heightPixels;
                            region.op(new Rect(0, i - 200, displayMetrics.widthPixels, i), Region.Op.UNION);
                            dqtvuisjdVar.setTouchExplorationPassthroughRegion(0, region);
                            dqtvuisjdVar.setGestureDetectionPassthroughRegion(0, region);
                            break;
                        }
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    t60.m214705c6("GestureRecorderManager", "❌ 关闭触摸探索失败", e);
                    return;
                }
                break;
            case 1:
                C0319a4 c0319a4 = this.f45679a1;
                t60.m214695b6(c0319a4, "this$0");
                try {
                    AccessibilityServiceInfo serviceInfo2 = c0319a4.f53055a1.getServiceInfo();
                    if (serviceInfo2 == null) {
                        t60.m214704c5("GestureRecorderManager", "🔍 [HOVER-DEBUG] serviceInfo=null, 无法启用触摸探索");
                    } else {
                        int i2 = serviceInfo2.flags;
                        int i3 = serviceInfo2.eventTypes;
                        serviceInfo2.flags = i2 | 4;
                        c0319a4.f53055a1.setServiceInfo(serviceInfo2);
                        boolean z = true;
                        c0319a4.f53060a6 = true;
                        if ((serviceInfo2.flags & 4) == 0) {
                            z = false;
                        }
                        t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] 触摸探索已启用! oldFlags=0x" + Integer.toHexString(i2) + " → newFlags=0x" + Integer.toHexString(serviceInfo2.flags) + " hasTouchExplore=" + z + " eventTypes=0x" + Integer.toHexString(i3));
                        if (Build.VERSION.SDK_INT >= 30) {
                            DisplayMetrics displayMetrics2 = c0319a4.f53055a1.getResources().getDisplayMetrics();
                            Region region2 = new Region();
                            region2.op(new Rect(0, 0, displayMetrics2.widthPixels - 3, displayMetrics2.heightPixels - 3), Region.Op.UNION);
                            c0319a4.f53055a1.setGestureDetectionPassthroughRegion(0, region2);
                            t60.m214726f4("GestureRecorderManager", "🔍 [HOVER-DEBUG] setGestureDetectionPassthroughRegion: " + displayMetrics2.widthPixels + "x" + displayMetrics2.heightPixels);
                        }
                    }
                    break;
                } catch (Exception e2) {
                    t60.m214705c6("GestureRecorderManager", "❌ 启用触摸探索失败", e2);
                    return;
                }
            case 2:
                C0319a4 c0319a42 = this.f45679a1;
                try {
                    c0319a42.f53056a2 = true;
                    c0319a42.f53057a3 = new JSONArray();
                    c0319a42.f53058a4 = new JSONArray();
                    break;
                } catch (Exception e3) {
                    t60.m214705c6("GestureRecorderManager", "❌ 启动录制失败", e3);
                    c0319a42.f53056a2 = false;
                    return;
                }
            default:
                C0319a4 c0319a43 = this.f45679a1;
                t60.m214695b6(c0319a43, "this$0");
                c0319a43.f53059a5 = false;
                break;
        }
    }
}
