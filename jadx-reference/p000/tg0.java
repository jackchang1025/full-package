package p000;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.ContentInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.C0041a1;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.AbstractC0215a3;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.navigationrail.NavigationRailView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class tg0 implements sf0, ze0, vk0, InterfaceC1505z5, ef0, vd0, InterfaceC0859mf, InterfaceC0861mh, InterfaceC0812l9, InterfaceC1374wf, od0, fd1 {

    /* renamed from: a2 */
    public static final tg0 f60216a2 = new tg0(0, false);

    /* renamed from: a0 */
    public final /* synthetic */ int f60217a0;

    /* renamed from: a1 */
    public Object f60218a1;

    public /* synthetic */ tg0(int i, Object obj) {
        this.f60217a0 = i;
        this.f60218a1 = obj;
    }

    @Override // p000.sf0
    /* renamed from: a0 */
    public void mo210850a0(bf0 bf0Var, boolean z) {
        if (bf0Var instanceof r21) {
            ((r21) bf0Var).f59605c5.mo210698b0().m210690a2(false);
        }
        sf0 sf0Var = ((C0041a1) this.f60218a1).f44142a4;
        if (sf0Var != null) {
            sf0Var.mo210850a0(bf0Var, z);
        }
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a1 */
    public ClipData mo213995a1() {
        return ((ContentInfo) this.f60218a1).getClip();
    }

    @Override // p000.InterfaceC0812l9
    /* renamed from: a2 */
    public boolean mo210913a2(View view) {
        DrawerLayout drawerLayout = (DrawerLayout) this.f60218a1;
        if (!DrawerLayout.m210102a9(view) || drawerLayout.m210109a5(view) == 2) {
            return false;
        }
        drawerLayout.m210105a1(view);
        return true;
    }

    @Override // p000.ze0
    /* renamed from: a4 */
    public boolean mo214682a4(bf0 bf0Var, MenuItem menuItem) {
        switch (this.f60217a0) {
            case 3:
                InterfaceC0902n6 interfaceC0902n6 = ((ActionMenuView) this.f60218a1).f43874c5;
                if (interfaceC0902n6 == null) {
                    return false;
                }
                Iterator it = ((CopyOnWriteArrayList) ((y61) interfaceC0902n6).f61259a0.f44121d2.f56089a2).iterator();
                if (it.hasNext()) {
                    throw AbstractC0003a2.m25a6(it);
                }
                return false;
            case 27:
                AbstractC0215a3 abstractC0215a3 = (AbstractC0215a3) this.f60218a1;
                abstractC0215a3.getClass();
                abstractC0215a3.getClass();
                return false;
            default:
                ((NavigationView) this.f60218a1).getClass();
                return false;
        }
    }

    @Override // p000.ef0
    /* renamed from: a5 */
    public void mo209943a5(bf0 bf0Var, MenuItem menuItem) {
        ((ViewOnKeyListenerC0542gn) this.f60218a1).f56524a5.removeCallbacksAndMessages(bf0Var);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // p000.vk0
    /* renamed from: a6 */
    public xf1 mo213324a6(View view, xf1 xf1Var) {
        xf1 xf1Var2;
        int i = 0;
        switch (this.f60217a0) {
            case 5:
                AppBarLayout appBarLayout = (AppBarLayout) this.f60218a1;
                WeakHashMap weakHashMap = xa1.f61054a0;
                xf1Var2 = fa1.m212764a1(appBarLayout) ? xf1Var : null;
                if (!tk0.m214759a0(appBarLayout.f49024a6, xf1Var2)) {
                    appBarLayout.f49024a6 = xf1Var2;
                    if (appBarLayout.f49039c1 != null && appBarLayout.getTopInset() > 0) {
                        i = 1;
                    }
                    appBarLayout.setWillNotDraw(i ^ 1);
                    appBarLayout.requestLayout();
                }
                return xf1Var;
            case 9:
                CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) this.f60218a1;
                WeakHashMap weakHashMap2 = xa1.f61054a0;
                xf1Var2 = fa1.m212764a1(collapsingToolbarLayout) ? xf1Var : null;
                if (!tk0.m214759a0(collapsingToolbarLayout.f49080c6, xf1Var2)) {
                    collapsingToolbarLayout.f49080c6 = xf1Var2;
                    collapsingToolbarLayout.requestLayout();
                }
                return xf1Var.f61102a0.mo214536a2();
            default:
                vf1 vf1Var = xf1Var.f61102a0;
                CoordinatorLayout coordinatorLayout = (CoordinatorLayout) this.f60218a1;
                if (!tk0.m214759a0(coordinatorLayout.f44821b3, xf1Var)) {
                    coordinatorLayout.f44821b3 = xf1Var;
                    boolean z = xf1Var.m215174a3() > 0;
                    coordinatorLayout.f44822b4 = z;
                    coordinatorLayout.setWillNotDraw(!z && coordinatorLayout.getBackground() == null);
                    if (!vf1Var.mo214538b2()) {
                        int childCount = coordinatorLayout.getChildCount();
                        while (i < childCount) {
                            View childAt = coordinatorLayout.getChildAt(i);
                            WeakHashMap weakHashMap3 = xa1.f61054a0;
                            if (!fa1.m212764a1(childAt) || ((C0907nb) childAt.getLayoutParams()).f58470a0 == null || !vf1Var.mo214538b2()) {
                                i++;
                            }
                        }
                    }
                    coordinatorLayout.requestLayout();
                }
                return xf1Var;
        }
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a7 */
    public int mo213996a7() {
        return ((ContentInfo) this.f60218a1).getFlags();
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: a8 */
    public ContentInfo mo213997a8() {
        return (ContentInfo) this.f60218a1;
    }

    @Override // p000.ef0
    /* renamed from: a9 */
    public void mo209944a9(bf0 bf0Var, ff0 ff0Var) {
        ViewOnKeyListenerC0542gn viewOnKeyListenerC0542gn = (ViewOnKeyListenerC0542gn) this.f60218a1;
        Handler handler = viewOnKeyListenerC0542gn.f56524a5;
        handler.removeCallbacksAndMessages(null);
        ArrayList arrayList = viewOnKeyListenerC0542gn.f56526a7;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                i = -1;
                break;
            } else if (bf0Var == ((C0541gm) arrayList.get(i)).f56517a1) {
                break;
            } else {
                i++;
            }
        }
        if (i == -1) {
            return;
        }
        int i2 = i + 1;
        handler.postAtTime(new RunnableC0540gl(this, i2 < arrayList.size() ? (C0541gm) arrayList.get(i2) : null, ff0Var, bf0Var), bf0Var, SystemClock.uptimeMillis() + 200);
    }

    @Override // p000.ze0
    /* renamed from: b0 */
    public void mo214683b0(bf0 bf0Var) {
        switch (this.f60217a0) {
            case 3:
                y61 y61Var = ((ActionMenuView) this.f60218a1).f43869c0;
                if (y61Var != null) {
                    y61Var.mo214683b0(bf0Var);
                    break;
                }
                break;
        }
    }

    @Override // p000.InterfaceC0859mf
    /* renamed from: b2 */
    public void mo213989b2(Uri uri) {
        ((ContentInfo.Builder) this.f60218a1).setLinkUri(uri);
    }

    @Override // p000.InterfaceC0861mh
    /* renamed from: b3 */
    public int mo213998b3() {
        return ((ContentInfo) this.f60218a1).getSource();
    }

    @Override // p000.InterfaceC1374wf
    /* renamed from: b4 */
    public void mo212870b4(cq0 cq0Var) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(0, 1, 15L, TimeUnit.SECONDS, new LinkedBlockingDeque(), new ThreadFactoryC0756kf("EmojiCompatInitializer"));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        threadPoolExecutor.execute(new RunnableC0029ai(this, cq0Var, threadPoolExecutor, 1));
    }

    @Override // p000.fd1
    /* renamed from: b5 */
    public xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        boolean zM212764a1;
        boolean zM212764a12;
        vf1 vf1Var = xf1Var.f61102a0;
        NavigationRailView navigationRailView = (NavigationRailView) this.f60218a1;
        Boolean bool = navigationRailView.f49684a6;
        if (bool != null) {
            zM212764a1 = bool.booleanValue();
        } else {
            WeakHashMap weakHashMap = xa1.f61054a0;
            zM212764a1 = fa1.m212764a1(navigationRailView);
        }
        if (zM212764a1) {
            gd1Var.f56446a1 += vf1Var.mo214391a5(7).f56155a1;
        }
        Boolean bool2 = navigationRailView.f49685a7;
        if (bool2 != null) {
            zM212764a12 = bool2.booleanValue();
        } else {
            WeakHashMap weakHashMap2 = xa1.f61054a0;
            zM212764a12 = fa1.m212764a1(navigationRailView);
        }
        if (zM212764a12) {
            gd1Var.f56448a3 += vf1Var.mo214391a5(7).f56157a3;
        }
        WeakHashMap weakHashMap3 = xa1.f61054a0;
        boolean z = ga1.m212904a3(view) == 1;
        int iM215172a1 = xf1Var.m215172a1();
        int iM215173a2 = xf1Var.m215173a2();
        int i = gd1Var.f56445a0;
        if (z) {
            iM215172a1 = iM215173a2;
        }
        int i2 = i + iM215172a1;
        gd1Var.f56445a0 = i2;
        ga1.m212911b0(view, i2, gd1Var.f56446a1, gd1Var.f56447a2, gd1Var.f56448a3);
        return xf1Var;
    }

    @Override // p000.sf0
    /* renamed from: b6 */
    public boolean mo210851b6(bf0 bf0Var) {
        C0041a1 c0041a1 = (C0041a1) this.f60218a1;
        if (bf0Var == c0041a1.f44140a2) {
            return false;
        }
        c0041a1.f44162c4 = ((r21) bf0Var).f59606c6.f56205a0;
        sf0 sf0Var = c0041a1.f44142a4;
        if (sf0Var != null) {
            return sf0Var.mo210851b6(bf0Var);
        }
        return false;
    }

    @Override // p000.InterfaceC0859mf
    /* renamed from: b7 */
    public void mo213990b7(int i) {
        ((ContentInfo.Builder) this.f60218a1).setFlags(i);
    }

    /* renamed from: b8 */
    public C0748k7 mo214746b8(int i) {
        return null;
    }

    /* renamed from: b9 */
    public C0748k7 mo214747b9(int i) {
        return null;
    }

    @Override // p000.InterfaceC0859mf
    public C0862mi build() {
        return new C0862mi(new tg0(((ContentInfo.Builder) this.f60218a1).build()));
    }

    /* renamed from: c0 */
    public void m214748c0() {
        ((C1499z) this.f60218a1).f61421c9.m210190d2();
    }

    /* renamed from: c3 */
    public boolean mo214749c3(int i, int i2, Bundle bundle) {
        return false;
    }

    @Override // p000.InterfaceC0859mf
    public void setExtras(Bundle bundle) {
        ((ContentInfo.Builder) this.f60218a1).setExtras(bundle);
    }

    public String toString() {
        switch (this.f60217a0) {
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return "ContentInfoCompat{" + ((ContentInfo) this.f60218a1) + "}";
            default:
                return super.toString();
        }
    }

    public /* synthetic */ tg0(int i, boolean z) {
        this.f60217a0 = i;
    }

    public tg0(TextView textView) {
        this.f60217a0 = 19;
        this.f60218a1 = new C1387wr(textView);
    }

    public tg0(EditText editText) {
        this.f60217a0 = 18;
        this.f60218a1 = new eo0(editText);
    }

    public tg0(int i) {
        Handler handler;
        Handler handlerM214544a1;
        this.f60217a0 = i;
        switch (i) {
            case 14:
                Looper mainLooper = Looper.getMainLooper();
                if (Build.VERSION.SDK_INT >= 28) {
                    handlerM214544a1 = AbstractC1186rn.m214544a1(mainLooper);
                } else {
                    try {
                        handler = (Handler) Handler.class.getDeclaredConstructor(Looper.class, Handler.Callback.class, Boolean.TYPE).newInstance(mainLooper, null, Boolean.TRUE);
                    } catch (IllegalAccessException | InstantiationException | NoSuchMethodException unused) {
                        handler = new Handler(mainLooper);
                    } catch (InvocationTargetException e) {
                        Throwable cause = e.getCause();
                        if (!(cause instanceof RuntimeException)) {
                            if (cause instanceof Error) {
                                throw ((Error) cause);
                            }
                            throw new RuntimeException(cause);
                        }
                        throw ((RuntimeException) cause);
                    }
                    handlerM214544a1 = handler;
                }
                this.f60218a1 = handlerM214544a1;
                return;
            default:
                if (Build.VERSION.SDK_INT >= 26) {
                    this.f60218a1 = new C0750k9(this);
                    return;
                } else {
                    this.f60218a1 = new C0749k8(this);
                    return;
                }
        }
    }

    public tg0(Context context) {
        this.f60217a0 = 17;
        this.f60218a1 = context.getApplicationContext();
    }

    public tg0(Uri uri, ClipDescription clipDescription, Uri uri2) {
        this.f60217a0 = 23;
        if (Build.VERSION.SDK_INT >= 25) {
            this.f60218a1 = new v50(uri, clipDescription, uri2);
        } else {
            this.f60218a1 = new pg1(uri, clipDescription, uri2);
        }
    }

    public tg0(ContentInfo contentInfo) {
        this.f60217a0 = 12;
        contentInfo.getClass();
        this.f60218a1 = AbstractC0858me.m213979a6(contentInfo);
    }

    public tg0(ClipData clipData, int i) {
        this.f60217a0 = 11;
        this.f60218a1 = AbstractC0858me.m213977a4(clipData, i);
    }

    /* renamed from: c1 */
    private final void m214742c1(bf0 bf0Var) {
    }

    /* renamed from: c2 */
    private final void m214743c2(bf0 bf0Var) {
    }

    @Override // p000.InterfaceC1505z5
    /* renamed from: a3 */
    public void mo214744a3(int i) {
    }

    @Override // p000.InterfaceC1505z5
    /* renamed from: b1 */
    public void mo214745b1(int i) {
    }
}
