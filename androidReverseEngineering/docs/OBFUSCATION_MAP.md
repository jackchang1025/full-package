# Obfuscation Package Map

Maps obfuscated package names in `src/` to their original libraries or app-internal roles.

Generated: 2026-04-04

---

## External Libraries

| 混淆包 | 原始库 | 原始包名 | 依据 |
|--------|--------|----------|------|
| `p0/` | OkHttp 4.x | `okhttp3` | ProxySelector, HostnameVerifier, SSLSocketFactory; "OkHttp Dispatcher" string |
| `f0/` | AndroidAsync (AsyncServer) | `com.koushikdutta.async` | "AsyncServer/Selector closed" string; async IO Runnable/callback pattern |
| `l0/` | Okio 3.x (buffered I/O) | `okio` | Buffer/Source/Sink pattern; used by p0 (OkHttp) |
| `h0/` | OkHttp 4.x — Cancellable/AsyncTimeout | `okhttp3.internal` | cancel()/isCancelled() interface; used by m0 |
| `m0/` | OkHttp 4.x — AsyncTimeout impl | `okhttp3.internal` | extends h0.h; delegates close() to f0.o |
| `s0/` | OkHttp 4.x — RetryAndFollowUpInterceptor | `okhttp3.internal.http` | implements p0.w (Interceptor); SSL/timeout retry logic |
| `t0/` | OkHttp 4.x — BridgeInterceptor/CallServerInterceptor | `okhttp3.internal.http` | implements p0.w; imports p0.f0/i0/j0/k0 |
| `u0/` | Okio 3.x — RealBufferedSource | `okio` | implements a1.t (Source); holds a1.i (Buffer) |
| `v0/` | OkHttp 4.x — internal IOException | `okhttp3.internal` | extends IOException; used in interceptor chain |
| `w0/` | OkHttp 4.x — Platform SSL/ALPN | `okhttp3.internal.platform` | extends w0.d; configures SSLSocket + ALPN |
| `x0/` | OkHttp 4.x — NullProxySelector | `okhttp3.internal` | extends ProxySelector; always returns Proxy.NO_PROXY |
| `j0/` | AndroidAsync/OkHttp bridge — DataSink | `com.koushikdutta.async` | interface d(f0.q, l0.g) — async request + Okio buffer |
| `k0/` | AndroidAsync — HTTP parser | `com.koushikdutta.async.http` | extends f0.q; parses HTTP headers (i0.b name-value pairs) |
| `n0/` | AndroidAsync — HTTP state/constants | `com.koushikdutta.async.http` | references f0.m constants; HTTP request state holder |
| `i0/` | HTTP header name-value pair | `com.koushikdutta.async.http` (or okhttp3) | Cloneable; two String fields; "Name may not be null" guard |
| `k1/` | WebSocket frame decoder | `nv-websocket-client internal` | extends k1.c; throws i1.c(1007) for invalid UTF-8 |
| `n1/` | WebSocket async write thread | `nv-websocket-client internal` | Thread + LinkedBlockingQueue for outbound frames |
| `o1/` | WebSocket UTF-8 validator | `nv-websocket-client internal` | static a(ByteBuffer) validates UTF-8; large lookup table |
| `i1/` | WebSocket error code exception | `nv-websocket-client internal` | Exception with int error code; thrown by k1 (code 1007) |
| `z0/` | OkHttp 4.x — CertificatePinner/TrustManager | `okhttp3.internal.tls` | extends a1.q; X509Certificate chain; SSLPeerUnverifiedException |
| `b1/` | DNS resolver / NSD helper | `internal (c1 wrapper)` | CountDownLatch + AtomicReference; implements c1.b; resolves InetAddress |
| `com/google/json/` | Gson | `com.google.gson` | Complete Gson API (JsonElement, JsonObject, TypeAdapter, etc.) |

---

## App-Internal Obfuscated Packages

| 混淆包 | 功能角色 | 依据 |
|--------|----------|------|
| `a/` | BouncyCastle crypto / string condition builder | imports org.bouncycastle ASN1; StringCondition + CombineFilter |
| `a0/` | Accessibility predicate / keep-alive engine selector | implements Predicate; imports all o.* vendor engine classes |
| `b0/` | UiObject filter interface | interface Boolean c(UiObject) |
| `bridge/` | Name-collision bridge (a.a → bridge.aa) | project-generated; resolves 'a' shadowing inside package 'o' |
| `c0/` | Android SyncAdapter stub | extends AbstractThreadedSyncAdapter; "SyncAdapter account sync" log |
| `c1/` | Network Service Discovery (NSD) manager | implements NsdManager.DiscoveryListener + ResolveListener |
| `d/` | Parcelable.Creator wrappers | implements Parcelable.Creator; delegates to o0.d/g |
| `d0/` | Screen capture / video encoder | Bitmap, MediaFormat, ConcurrentLinkedQueue; timer-based capture loop |
| `e/` | Accessibility node traversal Runnable | AccessibilityNodeInfo, RootInActiveWindowResult, BlockViewVO |
| `e0/` | Overlay UI builder | Activity, LinearLayout, TextView; builds floating overlay views |
| `e1/` | Heartbeat / scheduled ping scheduler | ScheduledExecutorService; 60s timeout; WebSocket keepalive base |
| `f/` | Abstract command with Bundle payload | abstract void a(Bundle); command dispatch pattern |
| `f1/` | Stub — logger or event emitter | methods t(), w(Exception), x(String) |
| `g/` | Biometric authentication callback | extends BiometricPrompt.AuthenticationCallback |
| `g0/` | Generic exception callback interface | interface void a(Exception) |
| `g1/` | Stub — result/status value object | int field 'a' |
| `h/` | Device settings reader | Settings.Global/System, ContentResolver; reads system settings |
| `i/` | String matcher / comparator | implements i.b; String pattern + boolean + int mode |
| `j/` | Message record state updater Runnable | MessageRecordVO + DeviceRecordStateVO; posts state updates |
| `j1/` | Stub — decompilation incomplete | empty stub |
| `k/` | UI selector / node finder | UiObject, BoundsFilter, ClassNameFilters, DescFilters |
| `l/` | Activity lifecycle manager | implements ActivityLifecycleCallbacks; tracks LockActivity |
| `l1/` | Stub — decompilation incomplete | empty stub |
| `m/` | Camera2 capture session callbacks | extends CameraCaptureSession.CaptureCallback |
| `n/` | ListenPropResponse comparator | implements Comparator; sorts by timestamp |
| `o0/` | ValueAnimator update listener | implements AnimatorUpdateListener; float start/end animation |
| `p/` | Port connectivity checker Callable | implements Callable; CheckPortResult; AtomicInteger retry |
| `q0/` | Named-thread Runnable base | saves/restores Thread.currentThread().getName() |
| `r/` | Integer sequence constants (1..80) | static int[] {1..80}; command/action code tables |
| `s/` | Message record value object | MessageRecordVO fields; two constructors |
| `t/` | Marker interface | empty interface — type tag |
| `u/` | Accessibility screenshot callback | implements TakeScreenshotCallback; AtomicBoolean/AtomicInteger |
| `v/` | Location data collector | singleton; android.location.Location → DeviceLocationVO |
| `w/` | Power-save mode manager | "木马正在运行,进入省电模式保活策略"; switches keep-alive strategy |
| `x/` | MediaProjection / screen recording manager | MediaProjection, VirtualDisplay, ImageReader |
| `y/` | ContentObserver for system settings | extends ContentObserver; ConcurrentHashMap(5); monitors Uri changes |
| `z/` | UiObject traversal interface | interface int a() + UiObject c(UiObject) |
| `io/github/muntashirakon/` | AppOps / crypto utilities (MuntashirAkon libs) | io.github.muntashirakon.crypto (spake2); open-source Android utility |

---

## o/ Package — Accessibility Delegates & Vendor Keep-Alive Engines

| 文件 | 角色 | 依据 |
|------|------|------|
| `e.java` | AccessibilityDelegate 基类 | Log "AccessibilityDelegate"; 所有 delegate 继承此类 |
| `b.java` | 保活引擎抽象接口 | abstract class; method v() 被所有厂商引擎调用 |
| `c.java` | 保活引擎基类 | extends e; 处理电池优化对话框 |
| `v.java` | OppoEngine (OPPO ColorOS) | COLORS_* keys; ColorOS 电源管理 |
| `q.java` | XiaomiEngine (小米 MIUI) | MIUI_* keys; com.miui.securitycenter AutoStartManagementActivity |
| `n.java` | HuaweiEngine (华为) | HUA_WEI_* keys; com.huawei.systemmanager StartupAppControlActivity |
| `i0.java` | VivoEngine (vivo) | VIVO_* keys; com.vivo.permissionmanager/abe |
| `e0.java` | TranssionEngine (传音) | com.transsion.phonemaster AutoStartActivity |
| `g.java` | AospEngine (通用 AOSP) | Log "o.g"; 通用省电管理，保存保活策略 |
| `k.java` | AccessibilityDelegate 主类 | C级; Log "AccessibilityDelegate"; 事件分发中枢 |
| `a0.java` | PairAccessibilityDelegate | Log "PairAccessibilityDelegate"; ADB WiFi 配对 UI |
| `g0.java` | UseDeviceCredentialDelegate | logError "UseDeviceCredentialDelegate" |
| `h.java` | EventSubscribeDelegate | EventSubscribe + ListenWindow; 事件订阅处理 |
| `i.java` | ConfirmLockDelegate | Log "ConfirmLockDelegate"; 手势锁屏确认 |
| `l.java` | ListenWindowQueueDelegate | ConcurrentLinkedQueue; 窗口监听事件队列 |
| `o.java` | ListenWindowThreadDelegate | thread.l; 工作线程分发窗口事件 |
| `t.java` | BoolCondition/StringCondition 复合 delegate | BoolCondition + StringCondition 组合条件 |
| `x.java` | PackageInstallerDelegate | Log "PackageInstallerDelegate"; 允许安装对话框 |
| `a.java` | 通用 int+Object Runnable | 按 int mode 分发 |
| `b0.java` | AccessibilityEvent 处理 Runnable | ReadEventMessage, helper.a bounds |
| `d.java` | ListenWindow 分发 Runnable | helper.o/r, CombineFilter, ReqListenHelper |
| `d0.java` | 节点搜索 Runnable | CombineFilterWithUpLevel |
| `f.java` | CombineFilter 搜索 Runnable | UiObject 结果, utils.h |
| `f0.java` | CombineFiltersWithOr 搜索 Runnable | helper.i, http.l |
| `h0.java` | 保活状态检查 Runnable | AtomicInteger, utils.g |
| `j.java` | 无障碍检查 Runnable | CheckedResult, MyAccessibilityService, o.a0 |
| `m.java` | BlockView/节点操作 Runnable | helper.g, o.c, o.n, r.e |
| `p.java` | XiaomiEngine 窗口匹配 Runnable | Log "o.q" keepAliveInAutoStartManage |
| `s.java` | 无障碍状态 Runnable | MyAccessibilityService, AtomicReference, o.t, r.f |
| `u.java` | OppoEngine 窗口匹配 Runnable | Log "o.v" keepAliveInStartup |
| `w.java` | CombineFiltersWithOr 搜索 Runnable | MyAccessibilityService, utils.f |
| `y.java` | ADB 配对端口/码 Runnable | PairPortAndCodeResult, thread.h |
| `z.java` | 保活引擎执行 Runnable | o.a0, r.g |
| `j0.java` | 搜索结果值对象 | UiObject + int + String, Serializable |
| `c0.java` | 线程池管理器 | newFixedThreadPool(2), AtomicBoolean, AtomicLong |
| `r.java` | 保活结果/状态日志 | Log "o.r" |

---

## com/guard/wallet/utils/ & helper/ Package Mapping

### utils/ (工具类)

| 文件 | 角色 |
|------|------|
| `a.java` | DialogInterface.OnCancelListener (清除 utils.b 对话框引用) |
| `b.java` | AlertDialog 构建器 + AccessibilityUtils (WeakReference, AtomicBoolean/Integer) |
| `c.java` | ActivityManager.RunningTaskInfo bridge (isVisible) |
| `d.java` | BuildConfig 访问器 — 返回服务器 URL、版本、语言配置 |
| `e.java` | 设备状态工具: 屏幕指标、Activity 引用、PowerManager、TelephonyManager |
| `f.java` | LocateValuesUtils: ConcurrentHashMap + Gson 缓存定位/属性值 |
| `g.java` | 主工具类 (ApplicationUtil + ReceiverUtils + AccountUtils + UnLockUtils, 1683行) |
| `h.java` | SharedUtils: SharedPreferences + Gson 持久化 ADBConfig、CheckPortResult |
| `i.java` | 雪花 ID 生成器 (datacenter/worker ID + 时间戳) |
| `j.java` | PictureInPicture + AttachedSurfaceControl bridge (API 31+) |
| `k.java` | 显示/设置工具: 屏幕亮度、SurfaceControl、无障碍设置 |

### helper/ (辅助类)

| 文件 | 角色 |
|------|------|
| `a.java` | AccessibilityNodeInfoCompat Rect 工具 (边界、坐标映射) |
| `b.java` | UiObject Predicate (ThreadPoolExecutor Future 支持) |
| `c.java` | Consumer bridge → helper.d.b (UI 对象队列消费者) |
| `d.java` | UI 对象缓存/队列管理器 (ConcurrentHashMap + ConcurrentLinkedQueue) |
| `e.java` | ViewTreeObserver.OnWindowAttachListener (BlockTextView 挂载事件) |
| `f.java` | 解锁/无障碍操作 Runnable (触发解锁流程) |
| `g.java` | BlockView 遮罩管理器 (WindowManager, AtomicReference, ReentrantLock) |
| `h.java` | 手势执行 Runnable: 通过 AccessibilityService 分发 Point[] 触摸路径 |
| `i.java` | LockCipherHelper: 调用解锁设备 API (ReqUnlockDeviceVO) |
| `j.java` | AlertDialog.OnClickListener (语言/引导对话框) |
| `k.java` | DialogInterface.OnDismissListener (按 int mode 路由关闭事件) |
| `l.java` | DialogInterface.OnClickListener (WiFi/通知对话框, String 参数) |
| `m.java` | Runnable: 显示通知/对话框 (5 个 String 参数 + int mode) |
| `n.java` | WiFi 引导 + 通知对话框弹出辅助 (WindowManager 悬浮层) |
| `o.java` | 图案遮罩管理器 (WindowManager, CombineFilter, ReentrantLock, plug.d) |
| `p.java` | Predicate<UiObject> (int mode 触摸事件过滤) |
| `q.java` | View.OnTouchListener (CombineFilter + 触摸事件分发, plug.f) |
| `r.java` | 触摸遮罩管理器 (WindowManager, CombineFilter, ReqListenHelper, plug.f) |

### server/ (HTTP 服务器)

| 文件 | 角色 |
|------|------|
| `a.java` | 异步上传辅助 Runnable: 上传应用图标 (UploadAppIconVO) |
| `b.java` | HttpServer: 4591行 HTTP 服务器 (AndroidAsync); 244 个处理方法; POST/GET 路由分发器 |
| `c.java` | WebSocket 服务器: Thread + ConcurrentLinkedQueue + AtomicBoolean |

---

## Library Dependency Graph

```
OkHttp 4.x cluster:
  p0 (core) → h0 (Cancellable) → m0 (impl)
  p0 → s0 (RetryInterceptor)
  p0 → t0 (BridgeInterceptor)
  p0 → w0 (SSL/ALPN)
  p0 → x0 (NullProxySelector)
  p0 → v0 (IOException)
  p0 → z0 (CertificatePinner)
  p0 → j0 (DataSink bridge)

Okio 3.x cluster:
  l0 (Buffer/Source/Sink) ← used by p0
  u0 (RealBufferedSource) ← wraps l0

AndroidAsync cluster:
  f0 (AsyncServer core)
  k0 (HTTP parser) → f0
  n0 (HTTP state) → f0
  j0 (DataSink) → f0 + l0

WebSocket cluster:
  k1 (frame decoder) → o1 (UTF-8 validator) → i1 (error exception)
  n1 (write thread) → e1 (heartbeat)

HTTP header:
  i0 (name-value pair) ← used by k0, p0
```
