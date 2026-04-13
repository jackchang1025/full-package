package p008k;

import a1.AbstractC0026q;
import android.graphics.Rect;
import android.util.Log;
import b0.C0078b;
import com.guard.wallet.condition.BoolCondition;
import com.guard.wallet.condition.IntCondition;
import com.guard.wallet.condition.StringCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.BooleanFilter;
import com.guard.wallet.filter.BoundsFilter;
import com.guard.wallet.filter.ClassNameFilters;
import com.guard.wallet.filter.DescFilters;
import com.guard.wallet.filter.Filter;
import com.guard.wallet.filter.HintTextFilters;
import com.guard.wallet.filter.IdFilters;
import com.guard.wallet.filter.IntFilters;
import com.guard.wallet.filter.PackageNameFilters;
import com.guard.wallet.filter.PanelTitleFilters;
import com.guard.wallet.filter.RoleDescFilters;
import com.guard.wallet.filter.Selector;
import com.guard.wallet.filter.StateDescFilters;
import com.guard.wallet.filter.TextFilters;
import com.guard.wallet.filter.TooltipFilters;
import com.guard.wallet.filter.UniqueIdFilters;
import com.guard.wallet.filter.WindowTitleFilters;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.bouncycastle.i18n.TextBundle;
import p007j.C0350e;

/* renamed from: k.a */
/* loaded from: classes.dex */
public final class C0356a implements Serializable {

    /* renamed from: a */
    public final Selector f700a = new Selector();

    /* renamed from: b */
    public final C0350e f701b = new C0350e(27);

    /* renamed from: A */
    public final void m888A(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(PackageNameFilters.equals(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: B */
    public final void m889B(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(PackageNameFilters.contains(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: C */
    public final void m890C(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(PackageNameFilters.endsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: D */
    public final void m891D(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(PackageNameFilters.matches(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: E */
    public final void m892E(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(PackageNameFilters.startsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: F */
    public final C0356a m893F(StringCondition stringCondition) {
        char c;
        try {
            if (!AbstractC0026q.m151B(stringCondition.getProperty())) {
                String property = stringCondition.getProperty();
                switch (property.hashCode()) {
                    case -2086369598:
                        if (property.equals("stateDesc")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1504006192:
                        if (property.equals("paneTitle")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1473774508:
                        if (property.equals("hintText")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1140076541:
                        if (property.equals("tooltip")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -294460212:
                        if (property.equals("uniqueId")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -267073497:
                        if (property.equals("roleDesc")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -9888733:
                        if (property.equals("className")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3355:
                        if (property.equals("id")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3079825:
                        if (property.equals("desc")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 3556653:
                        if (property.equals(TextBundle.TEXT_ENTRY)) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 908759025:
                        if (property.equals("packageName")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        m897J(stringCondition);
                        break;
                    case 1:
                        m904Q(stringCondition);
                        break;
                    case 2:
                        m902O(stringCondition);
                        break;
                    case 3:
                        m895H(stringCondition);
                        break;
                    case 4:
                        m898K(stringCondition);
                        break;
                    case 5:
                        m894G(stringCondition);
                        break;
                    case 6:
                        m896I(stringCondition);
                        break;
                    case 7:
                        m903P(stringCondition);
                        break;
                    case '\b':
                        m899L(stringCondition);
                        break;
                    case '\t':
                        m901N(stringCondition);
                        break;
                    case '\n':
                        m900M(stringCondition);
                        break;
                }
                return this;
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        return this;
    }

    /* renamed from: G */
    public final C0356a m894G(StringCondition stringCondition) {
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
            m917g(stringCondition.getEquals());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            m918h(stringCondition.getContains());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            m921k(stringCondition.getPrefix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            m919i(stringCondition.getSuffix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            m920j(stringCondition.getRegex());
            return this;
        }
        return this;
    }

    /* renamed from: H */
    public final C0356a m895H(StringCondition stringCondition) {
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
            m922l(stringCondition.getEquals());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            m923m(stringCondition.getContains());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            m926p(stringCondition.getPrefix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            m924n(stringCondition.getSuffix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            m925o(stringCondition.getRegex());
            return this;
        }
        return this;
    }

    /* renamed from: I */
    public final C0356a m896I(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(HintTextFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(HintTextFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(HintTextFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(HintTextFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(HintTextFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: J */
    public final C0356a m897J(StringCondition stringCondition) {
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
            m931u(stringCondition.getEquals());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            m932v(stringCondition.getContains());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            m935y(stringCondition.getPrefix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            m933w(stringCondition.getSuffix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            m934x(stringCondition.getRegex());
            return this;
        }
        return this;
    }

    /* renamed from: K */
    public final C0356a m898K(StringCondition stringCondition) {
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
            m888A(stringCondition.getEquals());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            m889B(stringCondition.getContains());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            m892E(stringCondition.getPrefix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            m890C(stringCondition.getSuffix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            m891D(stringCondition.getRegex());
            return this;
        }
        return this;
    }

    /* renamed from: L */
    public final C0356a m899L(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(PanelTitleFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(PanelTitleFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(PanelTitleFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(PanelTitleFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(PanelTitleFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: M */
    public final C0356a m900M(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(RoleDescFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(RoleDescFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(RoleDescFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(RoleDescFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(RoleDescFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: N */
    public final C0356a m901N(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(StateDescFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(StateDescFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(StateDescFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(StateDescFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(StateDescFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: O */
    public final C0356a m902O(StringCondition stringCondition) {
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
            m905R(stringCondition.getEquals());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            m906S(stringCondition.getContains());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            m909V(stringCondition.getPrefix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            m907T(stringCondition.getSuffix());
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            m908U(stringCondition.getRegex());
            return this;
        }
        return this;
    }

    /* renamed from: P */
    public final C0356a m903P(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(TooltipFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(TooltipFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(TooltipFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(TooltipFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(TooltipFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: Q */
    public final C0356a m904Q(StringCondition stringCondition) {
        boolean m151B;
        Selector selector;
        try {
            m151B = AbstractC0026q.m151B(stringCondition.getEquals());
            selector = this.f700a;
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
        if (!m151B) {
            String equals = stringCondition.getEquals();
            try {
                if (!AbstractC0026q.m151B(equals)) {
                    selector.add(UniqueIdFilters.equals(equals));
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("UiGlobalSelector", e3);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getContains())) {
            String contains = stringCondition.getContains();
            try {
                if (!AbstractC0026q.m151B(contains)) {
                    selector.add(UniqueIdFilters.contains(contains));
                }
            } catch (Exception e4) {
                AbstractC0026q.m186s("UiGlobalSelector", e4);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
            String prefix = stringCondition.getPrefix();
            try {
                if (!AbstractC0026q.m151B(prefix)) {
                    selector.add(UniqueIdFilters.startsWith(prefix));
                }
            } catch (Exception e5) {
                AbstractC0026q.m186s("UiGlobalSelector", e5);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getSuffix())) {
            String suffix = stringCondition.getSuffix();
            try {
                if (!AbstractC0026q.m151B(suffix)) {
                    selector.add(UniqueIdFilters.endsWith(suffix));
                }
            } catch (Exception e6) {
                AbstractC0026q.m186s("UiGlobalSelector", e6);
            }
            return this;
        }
        if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
            String regex = stringCondition.getRegex();
            try {
                if (!AbstractC0026q.m151B(regex)) {
                    selector.add(UniqueIdFilters.matches(regex));
                }
            } catch (Exception e7) {
                AbstractC0026q.m186s("UiGlobalSelector", e7);
            }
            return this;
        }
        return this;
        AbstractC0026q.m186s("UiGlobalSelector", e2);
        return this;
    }

    /* renamed from: R */
    public final void m905R(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(TextFilters.equals(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: S */
    public final void m906S(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(TextFilters.contains(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: T */
    public final void m907T(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(TextFilters.endsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: U */
    public final void m908U(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(TextFilters.matches(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: V */
    public final void m909V(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(TextFilters.startsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: W */
    public final void m910W(String str, StringCondition stringCondition) {
        try {
            if (AbstractC0026q.m151B(str) || !"WINDOW_TITLE".equals(stringCondition.getProperty())) {
                return;
            }
            if (!AbstractC0026q.m151B(stringCondition.getEquals())) {
                m911a(WindowTitleFilters.equals(str, stringCondition.getEquals()));
            }
            if (!AbstractC0026q.m151B(stringCondition.getContains())) {
                m911a(WindowTitleFilters.contains(str, stringCondition.getContains()));
            }
            if (!AbstractC0026q.m151B(stringCondition.getRegex())) {
                m911a(WindowTitleFilters.matches(str, stringCondition.getRegex()));
            }
            if (!AbstractC0026q.m151B(stringCondition.getPrefix())) {
                m911a(WindowTitleFilters.startsWith(str, stringCondition.getPrefix()));
            }
            if (AbstractC0026q.m151B(stringCondition.getSuffix())) {
                return;
            }
            m911a(WindowTitleFilters.endsWith(str, stringCondition.getSuffix()));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: a */
    public final void m911a(Filter filter) {
        if (filter != null) {
            this.f700a.add(filter);
        }
    }

    /* renamed from: b */
    public final void m912b(C0350e c0350e, int i2, String str) {
        char c;
        Filter equals;
        try {
            if (AbstractC0026q.m151B(str)) {
                str = "EQUALS";
            }
            switch (str.hashCode()) {
                case -1583968932:
                    if (str.equals("LESS_THAN_EQUAL")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -1112834937:
                    if (str.equals("LESS_THAN")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 360410235:
                    if (str.equals("GREATER_THAN_EQUAL")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 972152550:
                    if (str.equals("GREATER_THAN")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1630331595:
                    if (str.equals("NOT_EQUALS")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 2052813759:
                    if (str.equals("EQUALS")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            Selector selector = this.f700a;
            if (c == 0) {
                equals = IntFilters.equals(c0350e, i2);
            } else if (c == 1) {
                equals = IntFilters.notEquals(c0350e, i2);
            } else if (c == 2) {
                equals = IntFilters.gt(c0350e, i2);
            } else if (c == 3) {
                equals = IntFilters.gte(c0350e, i2);
            } else if (c == 4) {
                equals = IntFilters.lt(c0350e, i2);
            } else if (c != 5) {
                return;
            } else {
                equals = IntFilters.lte(c0350e, i2);
            }
            selector.add(equals);
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: c */
    public final C0356a m913c(BoolCondition boolCondition) {
        char c;
        if (boolCondition != null) {
            try {
                String filterKey = boolCondition.getFilterKey();
                int i2 = 19;
                int i3 = 18;
                int i4 = 17;
                int i5 = 16;
                int i6 = 15;
                int i7 = 14;
                int i8 = 13;
                int i9 = 12;
                int i10 = 11;
                int i11 = 10;
                int i12 = 9;
                int i13 = 0;
                switch (filterKey.hashCode()) {
                    case -1979905218:
                        if (filterKey.equals("contentInvalid")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1964681502:
                        if (filterKey.equals("clickable")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1724171933:
                        if (filterKey.equals("textSelectable")) {
                            c = 22;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1609594047:
                        if (filterKey.equals("enabled")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1371475228:
                        if (filterKey.equals("dismissable")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1207192371:
                        if (filterKey.equals("multiLine")) {
                            c = 15;
                            break;
                        }
                        c = 65535;
                        break;
                    case -994557277:
                        if (filterKey.equals("screenReaderFocusable")) {
                            c = 17;
                            break;
                        }
                        c = 65535;
                        break;
                    case -691041417:
                        if (filterKey.equals("focused")) {
                            c = 11;
                            break;
                        }
                        c = 65535;
                        break;
                    case -635423245:
                        if (filterKey.equals("contextClickable")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 66669991:
                        if (filterKey.equals("scrollable")) {
                            c = 18;
                            break;
                        }
                        c = 65535;
                        break;
                    case 398964322:
                        if (filterKey.equals("checkable")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case 742313895:
                        if (filterKey.equals("checked")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 746986311:
                        if (filterKey.equals("importantForAccessibility")) {
                            c = '\r';
                            break;
                        }
                        c = 65535;
                        break;
                    case 783360658:
                        if (filterKey.equals("canOpenPopup")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case 795311618:
                        if (filterKey.equals("heading")) {
                            c = '\f';
                            break;
                        }
                        c = 65535;
                        break;
                    case 918550520:
                        if (filterKey.equals("visibleToUser")) {
                            c = 23;
                            break;
                        }
                        c = 65535;
                        break;
                    case 997604294:
                        if (filterKey.equals("longClickable")) {
                            c = 14;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1191572123:
                        if (filterKey.equals("selected")) {
                            c = 19;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1216985755:
                        if (filterKey.equals("password")) {
                            c = 16;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1602416228:
                        if (filterKey.equals("editable")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1629011506:
                        if (filterKey.equals("focusable")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case 1933057242:
                        if (filterKey.equals("textEntryKey")) {
                            c = 21;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1976364617:
                        if (filterKey.equals("accessibilityFocused")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case 2062895929:
                        if (filterKey.equals("showingHintText")) {
                            c = 20;
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                Selector selector = this.f700a;
                switch (c) {
                    case 0:
                        try {
                            selector.add(new BooleanFilter(new C0350e(29), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e2) {
                            AbstractC0026q.m186s("UiGlobalSelector", e2);
                        }
                        return this;
                    case 1:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i13), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("UiGlobalSelector", e3);
                        }
                        return this;
                    case 2:
                        try {
                            selector.add(new BooleanFilter(new C0078b(1), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("UiGlobalSelector", e4);
                        }
                        return this;
                    case 3:
                        try {
                            selector.add(new BooleanFilter(new C0078b(2), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e5) {
                            AbstractC0026q.m186s("UiGlobalSelector", e5);
                        }
                        return this;
                    case 4:
                        try {
                            selector.add(new BooleanFilter(new C0078b(3), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e6) {
                            AbstractC0026q.m186s("UiGlobalSelector", e6);
                        }
                        return this;
                    case 5:
                        try {
                            selector.add(new BooleanFilter(new C0078b(4), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e7) {
                            AbstractC0026q.m186s("UiGlobalSelector", e7);
                        }
                        return this;
                    case 6:
                        try {
                            selector.add(new BooleanFilter(new C0078b(5), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e8) {
                            AbstractC0026q.m186s("UiGlobalSelector", e8);
                        }
                        return this;
                    case 7:
                        try {
                            selector.add(new BooleanFilter(new C0078b(6), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e9) {
                            AbstractC0026q.m186s("UiGlobalSelector", e9);
                        }
                        return this;
                    case '\b':
                        try {
                            selector.add(new BooleanFilter(new C0078b(7), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e10) {
                            AbstractC0026q.m186s("UiGlobalSelector", e10);
                        }
                        return this;
                    case '\t':
                        try {
                            selector.add(new BooleanFilter(new C0078b(8), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e11) {
                            AbstractC0026q.m186s("UiGlobalSelector", e11);
                        }
                        return this;
                    case '\n':
                        try {
                            selector.add(new BooleanFilter(new C0078b(i12), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e12) {
                            AbstractC0026q.m186s("UiGlobalSelector", e12);
                        }
                        return this;
                    case 11:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i11), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e13) {
                            AbstractC0026q.m186s("UiGlobalSelector", e13);
                        }
                        return this;
                    case '\f':
                        try {
                            selector.add(new BooleanFilter(new C0078b(i10), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e14) {
                            AbstractC0026q.m186s("UiGlobalSelector", e14);
                        }
                        return this;
                    case '\r':
                        try {
                            selector.add(new BooleanFilter(new C0078b(i9), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e15) {
                            AbstractC0026q.m186s("UiGlobalSelector", e15);
                        }
                        return this;
                    case 14:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i8), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e16) {
                            AbstractC0026q.m186s("UiGlobalSelector", e16);
                        }
                        return this;
                    case 15:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i7), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e17) {
                            AbstractC0026q.m186s("UiGlobalSelector", e17);
                        }
                        return this;
                    case 16:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i6), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e18) {
                            AbstractC0026q.m186s("UiGlobalSelector", e18);
                        }
                        return this;
                    case 17:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i5), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e19) {
                            AbstractC0026q.m186s("UiGlobalSelector", e19);
                        }
                        return this;
                    case 18:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i4), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e20) {
                            AbstractC0026q.m186s("UiGlobalSelector", e20);
                        }
                        return this;
                    case 19:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i3), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e21) {
                            AbstractC0026q.m186s("UiGlobalSelector", e21);
                        }
                        return this;
                    case 20:
                        try {
                            selector.add(new BooleanFilter(new C0078b(i2), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e22) {
                            AbstractC0026q.m186s("UiGlobalSelector", e22);
                        }
                        return this;
                    case 21:
                        try {
                            selector.add(new BooleanFilter(new C0078b(20), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e23) {
                            AbstractC0026q.m186s("UiGlobalSelector", e23);
                        }
                        return this;
                    case 22:
                        try {
                            selector.add(new BooleanFilter(new C0078b(21), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e24) {
                            AbstractC0026q.m186s("UiGlobalSelector", e24);
                        }
                        return this;
                    case 23:
                        try {
                            selector.add(new BooleanFilter(new C0078b(22), Boolean.valueOf(boolCondition.isFilterValue())));
                        } catch (Exception e25) {
                            AbstractC0026q.m186s("UiGlobalSelector", e25);
                        }
                        return this;
                    default:
                        Log.d("UiGlobalSelector", "未识别布尔条件");
                        return this;
                }
            } catch (Exception e26) {
                AbstractC0026q.m186s("UiGlobalSelector", e26);
            }
            AbstractC0026q.m186s("UiGlobalSelector", e26);
        }
        return this;
    }

    /* renamed from: d */
    public final void m914d(int i2, int i3, int i4, int i5) {
        try {
            this.f700a.add(new BoundsFilter(new Rect(i2, i3, i4, i5), 0));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: e */
    public final void m915e(int i2, int i3, int i4, int i5) {
        try {
            this.f700a.add(new BoundsFilter(new Rect(i2, i3, i4, i5), 2));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: f */
    public final void m916f(int i2, int i3, int i4, int i5) {
        try {
            this.f700a.add(new BoundsFilter(new Rect(i2, i3, i4, i5), 1));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: g */
    public final void m917g(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(ClassNameFilters.equals(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: h */
    public final void m918h(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(ClassNameFilters.contains(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: i */
    public final void m919i(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(ClassNameFilters.endsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: j */
    public final void m920j(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(ClassNameFilters.matches(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: k */
    public final void m921k(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(ClassNameFilters.startsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: l */
    public final void m922l(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(DescFilters.equals(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: m */
    public final void m923m(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(DescFilters.contains(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: n */
    public final void m924n(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(DescFilters.endsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: o */
    public final void m925o(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(DescFilters.matches(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: p */
    public final void m926p(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(DescFilters.startsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: q */
    public final UiObject m927q(UiObject uiObject) {
        if (uiObject != null) {
            try {
                UiObjectCollection m929s = m929s(uiObject, Integer.MAX_VALUE);
                if (m929s.size() == 0) {
                    return null;
                }
                return m929s.get(m929s.size() - 1);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiGlobalSelector", e2);
            }
        }
        return null;
    }

    /* renamed from: r */
    public final UiObjectCollection m928r(UiObject uiObject) {
        return m929s(uiObject, Integer.MAX_VALUE);
    }

    /* renamed from: s */
    public final UiObjectCollection m929s(UiObject uiObject, int i2) {
        Selector selector;
        this.f701b.getClass();
        ArrayList arrayList = new ArrayList();
        if (uiObject != null && (selector = this.f700a) != null) {
            if (i2 <= 0) {
                i2 = Integer.MAX_VALUE;
            }
            try {
                ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
                concurrentLinkedQueue.offer(uiObject);
                while (!concurrentLinkedQueue.isEmpty()) {
                    UiObject uiObject2 = (UiObject) concurrentLinkedQueue.poll();
                    if (uiObject2 != null) {
                        boolean booleanValue = selector.filter(uiObject2).booleanValue();
                        if (booleanValue) {
                            arrayList.add(uiObject2);
                            if (arrayList.size() >= i2) {
                                break;
                            }
                        }
                        for (int i3 = 0; i3 <= uiObject2.childCount() - 1; i3++) {
                            UiObject child = uiObject2.child(i3);
                            if (child != null) {
                                concurrentLinkedQueue.offer(child);
                            }
                        }
                        if (!booleanValue && !uiObject2.equals(uiObject)) {
                            uiObject2.recycle();
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("DFS2", e2);
            }
        }
        return UiObjectCollection.of(arrayList);
    }

    /* renamed from: t */
    public final UiObject m930t(UiObject uiObject) {
        if (uiObject != null) {
            try {
                UiObjectCollection m929s = m929s(uiObject, 1);
                if (m929s.size() == 0) {
                    return null;
                }
                return m929s.get(0);
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiGlobalSelector", e2);
            }
        }
        return null;
    }

    public final String toString() {
        return this.f700a.toString();
    }

    /* renamed from: u */
    public final void m931u(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(IdFilters.equals(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: v */
    public final void m932v(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(IdFilters.contains(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: w */
    public final void m933w(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(IdFilters.endsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: x */
    public final void m934x(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(IdFilters.matches(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: y */
    public final void m935y(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            this.f700a.add(IdFilters.startsWith(str));
        } catch (Exception e2) {
            AbstractC0026q.m186s("UiGlobalSelector", e2);
        }
    }

    /* renamed from: z */
    public final C0356a m936z(IntCondition intCondition) {
        char c;
        if (intCondition != null) {
            try {
            } catch (Exception e2) {
                AbstractC0026q.m186s("UiGlobalSelector", e2);
            }
            if (intCondition.getFilterValue() >= 0) {
                String filterKey = intCondition.getFilterKey();
                int i2 = 4;
                int i3 = 9;
                int i4 = 8;
                int i5 = 6;
                int i6 = 7;
                switch (filterKey.hashCode()) {
                    case -2105498688:
                        if (filterKey.equals("columnSpan")) {
                            c = 2;
                            break;
                        }
                        c = 65535;
                        break;
                    case -1591577989:
                        if (filterKey.equals("regionCount")) {
                            c = '\n';
                            break;
                        }
                        c = 65535;
                        break;
                    case -1354837162:
                        if (filterKey.equals("column")) {
                            c = 0;
                            break;
                        }
                        c = 65535;
                        break;
                    case -860736679:
                        if (filterKey.equals("columnCount")) {
                            c = 1;
                            break;
                        }
                        c = 65535;
                        break;
                    case -713407024:
                        if (filterKey.equals("drawingOrder")) {
                            c = 5;
                            break;
                        }
                        c = 65535;
                        break;
                    case 113114:
                        if (filterKey.equals("row")) {
                            c = 7;
                            break;
                        }
                        c = 65535;
                        break;
                    case 17743701:
                        if (filterKey.equals("rowCount")) {
                            c = '\b';
                            break;
                        }
                        c = 65535;
                        break;
                    case 95472323:
                        if (filterKey.equals("depth")) {
                            c = 3;
                            break;
                        }
                        c = 65535;
                        break;
                    case 346647841:
                        if (filterKey.equals("indexInParent")) {
                            c = 6;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1329151315:
                        if (filterKey.equals("childCount")) {
                            c = 4;
                            break;
                        }
                        c = 65535;
                        break;
                    case 1386522692:
                        if (filterKey.equals("rowSpan")) {
                            c = '\t';
                            break;
                        }
                        c = 65535;
                        break;
                    default:
                        c = 65535;
                        break;
                }
                switch (c) {
                    case 0:
                        try {
                            m912b(new C0350e(i6), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e3) {
                            AbstractC0026q.m186s("UiGlobalSelector", e3);
                        }
                        return this;
                    case 1:
                        try {
                            m912b(new C0350e(i5), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e4) {
                            AbstractC0026q.m186s("UiGlobalSelector", e4);
                        }
                        return this;
                    case 2:
                        try {
                            m912b(new C0350e(i4), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e5) {
                            AbstractC0026q.m186s("UiGlobalSelector", e5);
                        }
                        return this;
                    case 3:
                        try {
                            m912b(new C0350e(i3), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e6) {
                            AbstractC0026q.m186s("UiGlobalSelector", e6);
                        }
                        return this;
                    case 4:
                        try {
                            m912b(new C0350e(i2), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e7) {
                            AbstractC0026q.m186s("UiGlobalSelector", e7);
                        }
                        return this;
                    case 5:
                        m912b(new C0350e(11), intCondition.getFilterValue(), intCondition.getCompare());
                        return this;
                    case 6:
                        try {
                            m912b(new C0350e(14), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e8) {
                            AbstractC0026q.m186s("UiGlobalSelector", e8);
                        }
                        return this;
                    case 7:
                        try {
                            m912b(new C0350e(20), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e9) {
                            AbstractC0026q.m186s("UiGlobalSelector", e9);
                        }
                        return this;
                    case '\b':
                        try {
                            m912b(new C0350e(19), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e10) {
                            AbstractC0026q.m186s("UiGlobalSelector", e10);
                        }
                        return this;
                    case '\t':
                        try {
                            m912b(new C0350e(21), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e11) {
                            AbstractC0026q.m186s("UiGlobalSelector", e11);
                        }
                        return this;
                    case '\n':
                        try {
                            m912b(new C0350e(17), intCondition.getFilterValue(), intCondition.getCompare());
                        } catch (Exception e12) {
                            AbstractC0026q.m186s("UiGlobalSelector", e12);
                        }
                        return this;
                    default:
                        Log.d("UiGlobalSelector", "未识别整型条件");
                        return this;
                }
                AbstractC0026q.m186s("UiGlobalSelector", e2);
            }
        }
        return this;
    }
}
