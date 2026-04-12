package p000;

import android.animation.ValueAnimator;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0041a1;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.C0071a7;
import androidx.lifecycle.C0077a1;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.work.Worker;
import androidx.work.impl.WorkDatabase_Impl;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.storm.safe.rock.activity.yojggfhv;
import com.storm.safe.rock.view.ParticleView;
import io.socket.engineio.parser.Base64;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.collections.EmptySet;
import kotlin.collections.builders.SetBuilder;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ca */
/* loaded from: classes.dex */
public final class RunnableC0165ca implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f46084a0;

    /* renamed from: a1 */
    public final /* synthetic */ Object f46085a1;

    public /* synthetic */ RunnableC0165ca(int i, Object obj) {
        this.f46084a0 = i;
        this.f46085a1 = obj;
    }

    /* renamed from: a0 */
    public SetBuilder m210776a0() throws IOException {
        y60 y60Var = (y60) this.f46085a1;
        SetBuilder setBuilder = new SetBuilder();
        WorkDatabase_Impl workDatabase_Impl = y60Var.f61247a0;
        w01 w01Var = new w01("SELECT * FROM room_table_modification_log WHERE invalidated = 1;");
        int i = fs0.f56318b0;
        Cursor cursorM212861b0 = workDatabase_Impl.m212861b0(w01Var);
        while (cursorM212861b0.moveToNext()) {
            try {
                setBuilder.add(Integer.valueOf(cursorM212861b0.getInt(0)));
            } finally {
            }
        }
        cursorM212861b0.close();
        SetBuilder setBuilderM213503a3 = kg1.m213503a3(setBuilder);
        if (setBuilderM213503a3.f57600a0.isEmpty()) {
            return setBuilderM213503a3;
        }
        if (((y60) this.f46085a1).f61253a6 == null) {
            throw new IllegalStateException("Required value was null.");
        }
        u00 u00Var = ((y60) this.f46085a1).f61253a6;
        if (u00Var == null) {
            throw new IllegalArgumentException("Required value was null.");
        }
        u00Var.m214812a0();
        return setBuilderM213503a3;
    }

    /* JADX WARN: Removed duplicated region for block: B:99:0x021c  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        View viewM210107a3;
        int width;
        int i;
        Set setM210776a0;
        Object obj;
        boolean z;
        C0041a1 c0041a1;
        long j = 40000;
        switch (this.f46084a0) {
            case 0:
                kb0 kb0Var = (kb0) this.f46085a1;
                C1304ul c1304ul = kb0Var.f57493a2;
                C0153bz c0153bz = kb0Var.f57491a0;
                if (kb0Var.f57505b4) {
                    if (kb0Var.f57503b2) {
                        kb0Var.f57503b2 = false;
                        long jCurrentAnimationTimeMillis = AnimationUtils.currentAnimationTimeMillis();
                        c0153bz.f46016a4 = jCurrentAnimationTimeMillis;
                        c0153bz.f46018a6 = -1L;
                        c0153bz.f46017a5 = jCurrentAnimationTimeMillis;
                        c0153bz.f46019a7 = 0.5f;
                    }
                    if ((c0153bz.f46018a6 > 0 && AnimationUtils.currentAnimationTimeMillis() > c0153bz.f46018a6 + c0153bz.f46020a8) || !kb0Var.m213480a4()) {
                        kb0Var.f57505b4 = false;
                        return;
                    }
                    if (kb0Var.f57504b3) {
                        kb0Var.f57504b3 = false;
                        long jUptimeMillis = SystemClock.uptimeMillis();
                        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
                        c1304ul.onTouchEvent(motionEventObtain);
                        motionEventObtain.recycle();
                    }
                    if (c0153bz.f46017a5 == 0) {
                        throw new RuntimeException("Cannot compute scroll delta before calling start()");
                    }
                    long jCurrentAnimationTimeMillis2 = AnimationUtils.currentAnimationTimeMillis();
                    float fM210750a0 = c0153bz.m210750a0(jCurrentAnimationTimeMillis2);
                    long j2 = jCurrentAnimationTimeMillis2 - c0153bz.f46017a5;
                    c0153bz.f46017a5 = jCurrentAnimationTimeMillis2;
                    lb0.m213828a1(kb0Var.f57507b6, (int) (j2 * ((fM210750a0 * 4.0f) + ((-4.0f) * fM210750a0 * fM210750a0)) * c0153bz.f46015a3));
                    WeakHashMap weakHashMap = xa1.f61054a0;
                    fa1.m212775b2(c1304ul, this);
                    return;
                }
                return;
            case 1:
                C0454ef c0454ef = (C0454ef) this.f46085a1;
                if (c0454ef.f55992b4 == null || !c0454ef.f55983a5) {
                    return;
                }
                try {
                    C0454ef c0454ef2 = (C0454ef) this.f46085a1;
                    int i2 = c0454ef2.f55994b6;
                    int i3 = ((i2 >= 30 && i2 >= 60 && i2 >= 85 && i2 >= 95) ? 0 : 1) + i2;
                    if (i3 > 95) {
                        i3 = 95;
                    }
                    if (i3 != i2) {
                        c0454ef2.f55994b6 = i3;
                        TextView textView = c0454ef2.f55993b5;
                        if (textView != null) {
                            textView.setText(i3 + "%");
                        }
                        int i4 = (int) (((C0454ef) this.f46085a1).f55978a0.getResources().getDisplayMetrics().widthPixels * (((C0454ef) this.f46085a1).f55991b3.equals("update") ? 1.0f : 0.6f));
                        int i5 = (int) ((i4 * r2.f55994b6) / 100.0f);
                        View view = ((C0454ef) this.f46085a1).f55992b4;
                        if (view != null) {
                            view.setLayoutParams(new FrameLayout.LayoutParams(i5, -1));
                        }
                    }
                    C0454ef c0454ef3 = (C0454ef) this.f46085a1;
                    int i6 = c0454ef3.f55994b6;
                    if (i6 < 30) {
                        j = 30000;
                    } else if (i6 >= 60) {
                        j = i6 < 85 ? 45000L : i6 < 95 ? 50000L : 120000L;
                    }
                    c0454ef3.f55996b8.postDelayed(this, j);
                    return;
                } catch (Exception e) {
                    t60.m214705c6("BlackScreenOverlay", "❌ 更新进度动画出错", e);
                    return;
                }
            case 2:
                C0473ey c0473ey = (C0473ey) this.f46085a1;
                c0473ey.f56122a2 = false;
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) c0473ey.f56124a4;
                bb1 bb1Var = bottomSheetBehavior.f49217d8;
                if (bb1Var != null && bb1Var.m210637a6()) {
                    c0473ey.m212726a0(c0473ey.f56121a1);
                    return;
                } else {
                    if (bottomSheetBehavior.f49216d7 == 2) {
                        bottomSheetBehavior.m210947c9(c0473ey.f56121a1);
                        return;
                    }
                    return;
                }
            case 3:
                Carousel carousel = (Carousel) this.f46085a1;
                carousel.f44469b4.setProgress(0.0f);
                carousel.getClass();
                carousel.getClass();
                int i7 = carousel.f44468b3;
                throw null;
            case 4:
                try {
                    super/*android.app.Activity*/.onBackPressed();
                    return;
                } catch (IllegalStateException e2) {
                    if (!TextUtils.equals(e2.getMessage(), "Can not perform this action after onSaveInstanceState")) {
                        throw e2;
                    }
                    return;
                }
            case 5:
                AnimationAnimationListenerC1183rk animationAnimationListenerC1183rk = (AnimationAnimationListenerC1183rk) this.f46085a1;
                animationAnimationListenerC1183rk.f59788a0.endViewTransition(animationAnimationListenerC1183rk.f59789a1);
                animationAnimationListenerC1183rk.f59790a2.m215007a1();
                return;
            case 6:
                DialogInterfaceOnCancelListenerC1235su dialogInterfaceOnCancelListenerC1235su = (DialogInterfaceOnCancelListenerC1235su) this.f46085a1;
                dialogInterfaceOnCancelListenerC1235su.f60088e7.onDismiss(dialogInterfaceOnCancelListenerC1235su.f60096f5);
                return;
            case 7:
                C1297ue c1297ue = (C1297ue) this.f46085a1;
                DrawerLayout drawerLayout = c1297ue.f60416b3;
                int i8 = c1297ue.f60414b1.f45796b4;
                int i9 = c1297ue.f60413b0;
                boolean z2 = i9 == 3;
                if (z2) {
                    viewM210107a3 = drawerLayout.m210107a3(3);
                    width = (viewM210107a3 != null ? -viewM210107a3.getWidth() : 0) + i8;
                } else {
                    viewM210107a3 = drawerLayout.m210107a3(5);
                    width = drawerLayout.getWidth() - i8;
                }
                if (viewM210107a3 != null) {
                    if (((!z2 || viewM210107a3.getLeft() >= width) && (z2 || viewM210107a3.getLeft() <= width)) || drawerLayout.m210109a5(viewM210107a3) != 0) {
                        return;
                    }
                    C1296ud c1296ud = (C1296ud) viewM210107a3.getLayoutParams();
                    c1297ue.f60414b1.m210648b7(viewM210107a3, width, viewM210107a3.getTop());
                    c1296ud.f60380a2 = true;
                    drawerLayout.invalidate();
                    View viewM210107a32 = drawerLayout.m210107a3(i9 == 3 ? 5 : 3);
                    if (viewM210107a32 != null) {
                        drawerLayout.m210105a1(viewM210107a32);
                    }
                    if (drawerLayout.f44984b7) {
                        return;
                    }
                    long jUptimeMillis2 = SystemClock.uptimeMillis();
                    MotionEvent motionEventObtain2 = MotionEvent.obtain(jUptimeMillis2, jUptimeMillis2, 3, 0.0f, 0.0f, 0);
                    int childCount = drawerLayout.getChildCount();
                    for (int i10 = 0; i10 < childCount; i10++) {
                        drawerLayout.getChildAt(i10).dispatchTouchEvent(motionEventObtain2);
                    }
                    motionEventObtain2.recycle();
                    drawerLayout.f44984b7 = true;
                    return;
                }
                return;
            case 8:
                C1304ul c1304ul2 = (C1304ul) this.f46085a1;
                c1304ul2.f60471b1 = null;
                c1304ul2.drawableStateChanged();
                return;
            case 9:
                C1491ys c1491ys = (C1491ys) this.f46085a1;
                ValueAnimator valueAnimator = c1491ys.f61394c5;
                int i11 = c1491ys.f61395c6;
                if (i11 != 1) {
                    i = 2;
                    if (i11 != 2) {
                        return;
                    }
                } else {
                    i = 2;
                    valueAnimator.cancel();
                }
                c1491ys.f61395c6 = 3;
                float[] fArr = new float[i];
                fArr[0] = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                fArr[1] = 0.0f;
                valueAnimator.setFloatValues(fArr);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                return;
            case 10:
                ((C0071a7) this.f46085a1).m210181b9(true);
                return;
            case oe0.DEFAULT_M /* 11 */:
                ReentrantReadWriteLock.ReadLock lock = ((y60) this.f46085a1).f61247a0.f56326a7.readLock();
                t60.m214694b5(lock, "readWriteLock.readLock()");
                lock.lock();
                try {
                    try {
                    } finally {
                        lock.unlock();
                    }
                } catch (SQLiteException unused) {
                    setM210776a0 = EmptySet.f57570a0;
                } catch (IllegalStateException unused2) {
                    setM210776a0 = EmptySet.f57570a0;
                }
                if (((y60) this.f46085a1).m215246a0() && ((y60) this.f46085a1).f61251a4.compareAndSet(true, false) && !((y60) this.f46085a1).f61247a0.m212859a6().mo210447c3().mo210437b3()) {
                    d31 d31VarMo210447c3 = ((y60) this.f46085a1).f61247a0.m212859a6().mo210447c3();
                    d31VarMo210447c3.mo210441b9();
                    try {
                        setM210776a0 = m210776a0();
                        d31VarMo210447c3.mo210440b8();
                        if (setM210776a0.isEmpty()) {
                            return;
                        }
                        y60 y60Var = (y60) this.f46085a1;
                        synchronized (y60Var.f61255a8) {
                            Iterator it = y60Var.f61255a8.iterator();
                            while (true) {
                                jt0 jt0Var = (jt0) it;
                                if (jt0Var.hasNext()) {
                                    ((x60) ((Map.Entry) jt0Var.next()).getValue()).m215125a0(setM210776a0);
                                }
                            }
                        }
                        return;
                    } finally {
                        d31VarMo210447c3.mo210432a1();
                    }
                }
                return;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                synchronized (((C0077a1) this.f46085a1).f45198a0) {
                    obj = ((C0077a1) this.f46085a1).f45203a5;
                    ((C0077a1) this.f46085a1).f45203a5 = C0077a1.f45197b0;
                }
                ((C0077a1) this.f46085a1).m210242a4(obj);
                return;
            case 13:
                ((ViewGroup) this.f46085a1).setNestedScrollingEnabled(true);
                return;
            case 14:
                ((MotionLayout) this.f46085a1).f44577h1.m210004a0();
                return;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                ParticleView particleView = (ParticleView) this.f46085a1;
                ArrayList arrayList = particleView.f55232a0;
                int width2 = particleView.getWidth();
                Integer numValueOf = Integer.valueOf(width2);
                if (width2 <= 0) {
                    numValueOf = null;
                }
                if (numValueOf != null) {
                    int iIntValue = numValueOf.intValue();
                    int height = particleView.getHeight();
                    Integer numValueOf2 = height > 0 ? Integer.valueOf(height) : null;
                    if (numValueOf2 != null) {
                        int iIntValue2 = numValueOf2.intValue();
                        int size = arrayList.size();
                        for (int i12 = 0; i12 < size; i12++) {
                            km0 km0Var = (km0) arrayList.get(i12);
                            float f = km0Var.f57547a1 - km0Var.f57550a4;
                            km0Var.f57547a1 = f;
                            km0Var.f57546a0 += km0Var.f57551a5;
                            if (f < iIntValue2 * 0.3f) {
                                km0Var.f57549a3 -= 0.008f;
                            }
                            if (f < -20.0f || km0Var.f57549a3 <= 0.0f) {
                                arrayList.set(i12, particleView.m212472a0(iIntValue, iIntValue2, false));
                            }
                        }
                    }
                }
                particleView.invalidate();
                if (particleView.f55235a3) {
                    particleView.f55234a2.postDelayed(this, 40L);
                    return;
                }
                return;
            case 16:
                RecyclerView recyclerView = (RecyclerView) this.f46085a1;
                lq0 lq0Var = recyclerView.f45288d4;
                if (lq0Var != null) {
                    C1176rd c1176rd = (C1176rd) lq0Var;
                    long j3 = c1176rd.f58136a3;
                    ArrayList arrayList2 = c1176rd.f59675a7;
                    boolean zIsEmpty = arrayList2.isEmpty();
                    ArrayList arrayList3 = c1176rd.f59677a9;
                    boolean zIsEmpty2 = arrayList3.isEmpty();
                    ArrayList arrayList4 = c1176rd.f59678b0;
                    boolean zIsEmpty3 = arrayList4.isEmpty();
                    ArrayList arrayList5 = c1176rd.f59676a8;
                    boolean zIsEmpty4 = arrayList5.isEmpty();
                    if (zIsEmpty && zIsEmpty2 && zIsEmpty4 && zIsEmpty3) {
                        z = false;
                    } else {
                        int i13 = 0;
                        for (int size2 = arrayList2.size(); i13 < size2; size2 = size2) {
                            Object obj2 = arrayList2.get(i13);
                            i13++;
                            dr0 dr0Var = (dr0) obj2;
                            View view2 = dr0Var.f55849a0;
                            boolean z3 = zIsEmpty4;
                            ViewPropertyAnimator viewPropertyAnimatorAnimate = view2.animate();
                            c1176rd.f59684b6.add(dr0Var);
                            viewPropertyAnimatorAnimate.setDuration(j3).alpha(0.0f).setListener(new C1160qy(c1176rd, dr0Var, viewPropertyAnimatorAnimate, view2)).start();
                            zIsEmpty4 = z3;
                            arrayList2 = arrayList2;
                        }
                        boolean z4 = zIsEmpty4;
                        arrayList2.clear();
                        if (!zIsEmpty2) {
                            ArrayList arrayList6 = new ArrayList();
                            arrayList6.addAll(arrayList3);
                            c1176rd.f59680b2.add(arrayList6);
                            arrayList3.clear();
                            RunnableC1159qx runnableC1159qx = new RunnableC1159qx(c1176rd, arrayList6, 0);
                            if (zIsEmpty) {
                                runnableC1159qx.run();
                            } else {
                                View view3 = ((C1175rc) arrayList6.get(0)).f59668a0.f55849a0;
                                WeakHashMap weakHashMap2 = xa1.f61054a0;
                                fa1.m212776b3(view3, runnableC1159qx, j3);
                            }
                        }
                        if (!zIsEmpty3) {
                            ArrayList arrayList7 = new ArrayList();
                            arrayList7.addAll(arrayList4);
                            c1176rd.f59681b3.add(arrayList7);
                            arrayList4.clear();
                            RunnableC1159qx runnableC1159qx2 = new RunnableC1159qx(c1176rd, arrayList7, 1);
                            if (zIsEmpty) {
                                runnableC1159qx2.run();
                            } else {
                                View view4 = ((C1174rb) arrayList7.get(0)).f59660a0.f55849a0;
                                WeakHashMap weakHashMap3 = xa1.f61054a0;
                                fa1.m212776b3(view4, runnableC1159qx2, j3);
                            }
                        }
                        if (z4) {
                            z = false;
                        } else {
                            ArrayList arrayList8 = new ArrayList();
                            arrayList8.addAll(arrayList5);
                            c1176rd.f59679b1.add(arrayList8);
                            arrayList5.clear();
                            RunnableC1159qx runnableC1159qx3 = new RunnableC1159qx(c1176rd, arrayList8, 2);
                            if (zIsEmpty && zIsEmpty2 && zIsEmpty3) {
                                runnableC1159qx3.run();
                                z = false;
                            } else {
                                if (zIsEmpty) {
                                    j3 = 0;
                                }
                                long jMax = Math.max(!zIsEmpty2 ? c1176rd.f58137a4 : 0L, !zIsEmpty3 ? c1176rd.f58138a5 : 0L) + j3;
                                z = false;
                                View view5 = ((dr0) arrayList8.get(0)).f55849a0;
                                WeakHashMap weakHashMap4 = xa1.f61054a0;
                                fa1.m212776b3(view5, runnableC1159qx3, jMax);
                            }
                        }
                    }
                }
                recyclerView.f45312f8 = z;
                return;
            case 17:
                ((StaggeredGridLayoutManager) this.f46085a1).m210399h6();
                return;
            case 18:
                ActionMenuView actionMenuView = ((Toolbar) this.f46085a1).f44089a0;
                if (actionMenuView == null || (c0041a1 = actionMenuView.f43868b9) == null) {
                    return;
                }
                c0041a1.m209942b3();
                return;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                ((bb1) this.f46085a1).m210645b4(0);
                return;
            case 20:
                View view6 = (View) this.f46085a1;
                ((InputMethodManager) view6.getContext().getSystemService("input_method")).showSoftInput(view6, 1);
                return;
            case 21:
                Worker worker = (Worker) this.f46085a1;
                try {
                    worker.f45533a4.m210484a8(worker.mo210458a6());
                    return;
                } catch (Throwable th) {
                    worker.f45533a4.m210485a9(th);
                    return;
                }
            case 22:
                fh1 fh1Var = (fh1) this.f46085a1;
                wg1 wg1Var = fh1Var.f56261a3;
                try {
                    try {
                        try {
                            sb0 sb0Var = (sb0) fh1Var.f56273b5.get();
                            if (sb0Var == null) {
                                C1351vv c1351vvM214963a5 = C1351vv.m214963a5();
                                int i14 = fh1.f56257b7;
                                String str = wg1Var.f60914a2;
                                c1351vvM214963a5.getClass();
                            } else {
                                C1351vv c1351vvM214963a52 = C1351vv.m214963a5();
                                int i15 = fh1.f56257b7;
                                String str2 = wg1Var.f60914a2;
                                sb0Var.toString();
                                c1351vvM214963a52.getClass();
                                fh1Var.f56264a6 = sb0Var;
                            }
                        } catch (Throwable th2) {
                            fh1Var.m212814a1();
                            throw th2;
                        }
                    } catch (InterruptedException | ExecutionException unused3) {
                        C1351vv c1351vvM214963a53 = C1351vv.m214963a5();
                        int i16 = fh1.f56257b7;
                        c1351vvM214963a53.getClass();
                    }
                } catch (CancellationException unused4) {
                    C1351vv c1351vvM214963a54 = C1351vv.m214963a5();
                    int i17 = fh1.f56257b7;
                    c1351vvM214963a54.getClass();
                }
                fh1Var.m212814a1();
                return;
            default:
                long jCurrentTimeMillis = System.currentTimeMillis();
                yojggfhv yojggfhvVar = (yojggfhv) this.f46085a1;
                long j4 = jCurrentTimeMillis - yojggfhvVar.f51934b0;
                int iM214413a9 = AbstractC1117qo.m214413a9((int) ((j4 / 40000) * 80), 0, 80);
                ProgressBar progressBar = yojggfhvVar.f51930a6;
                if (progressBar != null) {
                    progressBar.setProgress(iM214413a9);
                }
                TextView textView2 = yojggfhvVar.f51931a7;
                if (textView2 != null) {
                    textView2.setText(iM214413a9 + "%");
                }
                if (iM214413a9 < 80 && j4 < 40000) {
                    Handler handler = yojggfhvVar.f51932a8;
                    if (handler != null) {
                        handler.postDelayed(this, 100L);
                        return;
                    }
                    return;
                }
                ProgressBar progressBar2 = yojggfhvVar.f51930a6;
                if (progressBar2 != null) {
                    progressBar2.setProgress(80);
                }
                TextView textView3 = yojggfhvVar.f51931a7;
                if (textView3 == null) {
                    return;
                }
                textView3.setText("80%");
                return;
        }
    }

    public RunnableC0165ca(fh1 fh1Var, String str) {
        this.f46084a0 = 22;
        this.f46085a1 = fh1Var;
    }
}
