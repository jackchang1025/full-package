package p007j;

import a1.AbstractC0026q;
import b0.InterfaceC0077a;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.helper.AbstractC0186i;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.http.C0204i;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.resp.CacheTaskResponseVO;
import com.guard.wallet.utils.AbstractC0252h;
import g0.InterfaceC0309a;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import org.bouncycastle.i18n.TextBundle;
import p0.C0879u;
import p0.InterfaceC0863e;
import p0.e0;
import p0.f0;
import p0.j0;
import p0.l0;
import p015s.C0897b;
import p016t.InterfaceC0910a;
import p016t.InterfaceC0911b;

/* renamed from: j.e */
/* loaded from: classes.dex */
public final class C0350e implements InterfaceC0863e, InterfaceC0910a, InterfaceC0911b, InterfaceC0309a, InterfaceC0077a {

    /* renamed from: d */
    public final /* synthetic */ int f689d;

    public /* synthetic */ C0350e(int i2) {
        this.f689d = i2;
    }

    @Override // g0.InterfaceC0309a
    /* renamed from: a */
    public final void mo293a(Exception exc) {
        AbstractC0026q.m186s("HttpCompletedCallback", exc);
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: b */
    public final void mo389b(e0 e0Var, IOException iOException) {
        switch (this.f689d) {
            case 1:
                AbstractC0026q.m186s("DefaultCallback", iOException);
                if (!(iOException instanceof C0897b)) {
                    C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
                    AbstractC0207l.m441x(e0Var, this);
                    break;
                }
                break;
            case 2:
                AbstractC0026q.m186s("LockCiphersCallback", iOException);
                if (!(iOException instanceof C0897b)) {
                    C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
                    AbstractC0207l.m441x(e0Var, this);
                    break;
                }
                break;
            default:
                AbstractC0026q.m186s("RunCacheTaskCallback", iOException);
                if (!(iOException instanceof C0897b)) {
                    f0 f0Var = e0Var.f1773c;
                    C0879u c0879u = f0Var.f1777a;
                    C0879u c0879u2 = f0Var.f1777a;
                    C0204i.m400c(c0879u.f1914h);
                    try {
                        CacheTaskResponseVO cacheTaskResponseVO = new CacheTaskResponseVO();
                        if (!AbstractC0026q.m151B(c0879u2.m1299n().getPath())) {
                            cacheTaskResponseVO.setReqUri(c0879u2.m1299n().getPath());
                        }
                        ApiResult apiResult = new ApiResult();
                        apiResult.setSuccess(Boolean.FALSE);
                        apiResult.setCount(0);
                        apiResult.setCode(400);
                        apiResult.setMsg("Bad Request");
                        cacheTaskResponseVO.setResponse(AbstractC0252h.m693N(apiResult));
                        AbstractC0207l.m435r(cacheTaskResponseVO);
                        break;
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("RunCacheTaskCallback", e2);
                        return;
                    }
                }
                break;
        }
    }

    @Override // b0.InterfaceC0077a
    /* renamed from: c */
    public final Boolean mo292c(UiObject uiObject) {
        return Boolean.valueOf(uiObject.accessibilityFocused());
    }

    @Override // p0.InterfaceC0863e
    /* renamed from: d */
    public final void mo390d(e0 e0Var, j0 j0Var) {
        String m1269z;
        int i2 = this.f689d;
        l0 l0Var = j0Var.f1835g;
        switch (i2) {
            case 1:
                try {
                    C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
                    j0Var.close();
                    break;
                } catch (Exception e2) {
                    AbstractC0026q.m186s("DefaultCallback", e2);
                    return;
                }
            case 2:
                C0204i.m400c(e0Var.f1773c.f1777a.f1914h);
                if (l0Var != null) {
                    AbstractC0186i.m355a(l0Var.m1269z());
                }
                j0Var.close();
                break;
            default:
                f0 f0Var = e0Var.f1773c;
                C0879u c0879u = f0Var.f1777a;
                C0879u c0879u2 = f0Var.f1777a;
                C0204i.m400c(c0879u.f1914h);
                try {
                    CacheTaskResponseVO cacheTaskResponseVO = new CacheTaskResponseVO();
                    if (!AbstractC0026q.m151B(c0879u2.m1299n().getPath())) {
                        cacheTaskResponseVO.setReqUri(c0879u2.m1299n().getPath());
                    }
                    String m1265x = j0Var.m1265x("content-type", "application/json;charset=utf-8");
                    if (l0Var != null) {
                        if (Objects.equals(m1265x, "image/webp")) {
                            byte[] m1268x = l0Var.m1268x();
                            m1269z = m1268x.length > 0 ? Base64.getEncoder().encodeToString(m1268x) : null;
                        } else {
                            m1269z = l0Var.m1269z();
                        }
                        cacheTaskResponseVO.setResponse(m1269z);
                    }
                    AbstractC0207l.m435r(cacheTaskResponseVO);
                } catch (Exception e3) {
                    AbstractC0026q.m186s("RunCacheTaskCallback", e3);
                }
                j0Var.close();
                break;
        }
    }

    /* renamed from: e */
    public final int m885e(UiObject uiObject) {
        switch (this.f689d) {
            case 4:
                return uiObject.childCount();
            case 5:
            case 10:
            case 12:
            case 13:
            case 15:
            case 16:
            case 18:
            default:
                return uiObject.rowSpan();
            case 6:
                return uiObject.columnCount();
            case 7:
                return uiObject.column();
            case 8:
                return uiObject.columnSpan();
            case 9:
                return uiObject.depth();
            case 11:
                return uiObject.drawingOrder();
            case 14:
                return uiObject.indexInParent();
            case 17:
                return uiObject.regionCount();
            case 19:
                return uiObject.rowCount();
            case 20:
                return uiObject.row();
        }
    }

    /* renamed from: f */
    public final String m886f(UiObject uiObject) {
        switch (this.f689d) {
            case 5:
                if (uiObject != null) {
                    return uiObject.className();
                }
                return null;
            case 10:
                if (uiObject != null) {
                    return uiObject.desc();
                }
                return null;
            case 12:
                if (uiObject != null) {
                    return uiObject.hintText();
                }
                return null;
            case 13:
                if (uiObject != null) {
                    return uiObject.id();
                }
                return null;
            case 15:
                if (uiObject != null) {
                    return uiObject.packageName();
                }
                return null;
            case 16:
                if (uiObject != null) {
                    return uiObject.paneTitle();
                }
                return null;
            case 18:
                if (uiObject != null) {
                    return uiObject.roleDesc();
                }
                return null;
            case 22:
                if (uiObject != null) {
                    return uiObject.stateDesc();
                }
                return null;
            case 23:
                if (uiObject != null) {
                    return uiObject.text();
                }
                return null;
            case 24:
                if (uiObject != null) {
                    return uiObject.tooltipText();
                }
                return null;
            default:
                if (uiObject != null) {
                    return uiObject.uniqueId();
                }
                return null;
        }
    }

    @Override // b0.InterfaceC0077a
    public final String toString() {
        switch (this.f689d) {
            case 4:
                return "childCount";
            case 5:
                return "className";
            case 6:
                return "columnCount";
            case 7:
                return "column";
            case 8:
                return "columnSpan";
            case 9:
                return "depth";
            case 10:
                return "desc";
            case 11:
                return "drawingOrder";
            case 12:
                return "hintText";
            case 13:
                return "id";
            case 14:
                return "indexInParent";
            case 15:
                return "packageName";
            case 16:
                return "paneTitle";
            case 17:
                return "regionCount";
            case 18:
                return "roleDesc";
            case 19:
                return "rowCount";
            case 20:
                return "row";
            case 21:
                return "rowSpan";
            case 22:
                return "stateDesc";
            case 23:
                return TextBundle.TEXT_ENTRY;
            case 24:
                return "tooltip";
            case 25:
                return "uniqueId";
            case 26:
            case 27:
            case 28:
            default:
                return super.toString();
            case 29:
                return "accessibilityFocused";
        }
    }
}
