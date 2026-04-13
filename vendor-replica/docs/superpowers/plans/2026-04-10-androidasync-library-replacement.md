# AndroidAsync 库替换逆向代码 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 用 AndroidAsync 3.1.0 原始库替换 57 个手动逆向的 NIO/HTTP 文件，消除 StackOverflow 等逆向缺陷，减少 ~3500 行代码。

**Architecture:** 分 3 层替换：底层 NIO（删除 28 文件，直接用库 AsyncServer）→ HTTP Server（删除 17 文件，用库 AsyncHttpServer）→ 适配层（更新 HttpResponseHelper + 13 个 Handler 的接口签名）。保留所有业务逻辑不变。

**Tech Stack:** AndroidAsync 3.1.0 (已在 build.gradle 中), AsyncHttpServer, AsyncHttpServerRequest/Response, Multimap

---

## 文件结构规划

### 需要删除的文件（57 个逆向文件）

**nio/ 包（28 文件）— 全部删除：**
- `NioAsyncServer.java` — 库提供 `AsyncServer`
- `SelectorThread.java` — 库内部实现
- `SelectorHandle.java` — 库内部实现
- `AsyncThreadFactory.java` — 库内部实现
- `ScheduledTask.java` — 库内部实现
- `TaskComparator.java` — 库内部实现
- `SyncTaskQueue.java` — 库内部实现
- `NioNetworkSocket.java` — 库提供 `AsyncNetworkSocket`
- `ChannelBase.java` — 库内部实现
- `ChannelReadAdapter.java` — 库内部实现
- `ServerSocketHandle.java` — 库内部实现
- `ServerBindTask.java` — 由 `AsyncHttpServer.listen()` 替代
- `AsyncSocketContract.java` — 库提供 `AsyncSocket`
- `DataEmitterContract.java` — 库提供 `DataEmitter`
- `DataSinkContract.java` — 库提供 `DataSink`
- `NioByteBufferList.java` — 库提供 `ByteBufferList`
- `ProtocolParser.java` — 库内部实现
- `DataFrameReader.java` — 库内部实现
- `FixedLengthReader.java` — 库内部实现
- `DelimiterReader.java` — 库内部实现
- `StringCallback.java` — 库内部实现
- `DataConsumerCallback.java` — 库内部实现
- `FilteredEmitter.java` — 库提供 `FilteredDataEmitter`
- `ChunkedWriter.java` — 库内部实现
- `WriteTask.java` — 库内部实现
- `PauseResumeTask.java` — 库内部实现
- `CompletionBridge.java` — 库内部实现
- `WrappedIOException.java` — 库内部实现

**http/server/ 包（17 文件）— 全部删除：**
- `HttpServer.java` — 库提供 `AsyncHttpServer`
- `HttpRouter.java` — 库提供 `AsyncHttpServerRouter`
- `HttpConnectionHandler.java` — 库内部实现
- `HttpConnectionCallback.java` — 库内部实现（`ListenCallback`）
- `HttpCloseCallback.java` — 库内部实现
- `HttpRequestBase.java` — 库提供 `AsyncHttpServerRequest`
- `HttpRequestParser.java` — 库内部实现
- `HttpContinueCallback.java` — 库内部实现
- `HttpResponse.java` — 库提供 `AsyncHttpServerResponse`
- `HttpResponseImpl.java` — 库内部实现
- `HttpResponseWriter.java` — 库内部实现
- `HttpResponseParser.java` — 库内部实现
- `HttpRoute.java` — 库内部实现
- `HttpRequestCallback.java` — 库提供 `HttpServerRequestCallback`
- `HttpRequestBuilder.java` — 库内部实现
- `HttpServerMarker.java` — 库内部实现

**callback/ 包（3 文件）— 全部删除：**
- `DataHandler.java` — 库提供 `DataCallback`
- `ExceptionCallback.java` — 库提供 `CompletedCallback`
- `CloseCallback.java` — 库提供 `CompletedCallback`

**future/ 包（9 文件）— 全部删除：**
- `SimpleFuture.java` — 库提供 `SimpleFuture`
- `SimpleCancellable.java` — 库提供 `SimpleCancellable`
- `CompletedFuture.java` — 库内部
- `Cancellable.java` — 库提供
- `FutureCallback.java` — 库提供
- `TaskCallback.java` — 库内部
- `TransformFunction.java` — 库内部
- `TwoFutureBridge.java` — 库内部
- `TransformBridge.java` — 库内部

### 需要修改的文件

| 文件 | 改动范围 | 说明 |
|------|----------|------|
| `server/ApiRouter.java` | **重写** | 核心：用 AsyncHttpServer 替换自定义 HttpServer |
| `server/HttpResponseHelper.java` | **重写** | 接口从 `HttpResponse` 改为 `AsyncHttpServerResponse` |
| 13 个 `server/handler/*.java` | **批量替换** | 方法签名 `HttpResponse` → `AsyncHttpServerResponse` |
| `thread/StrategyThread.java` | **删除 HTTP body 部分** | 库自动解析 POST body |
| `http/CookieHeaderHandler.java` | **简化** | 移除 NIO 数据管道相关代码 |
| `http/filter/*.java` (5 文件) | **删除** | 库内置 chunked/gzip 处理 |
| `http/body/*.java` (4 文件) | **删除** | 库内置 body 解析 |
| `http/HttpDataEmitterStub.java` | **删除** | 库内部 |
| `core/AppUtils.java` | **局部修改** | 移除 `p()` 和 `T()` 中的 NIO 引用 |
| `infra/ProtocolDispatcher.java` | **简化** | 移除 DataHandler 实现 |
| `delegate/AdbBridge.java` | **局部修改** | 移除 NIO 写入任务引用 |
| `adb/AdbConnectionBuilder.java` | **不变** | 使用原生 Socket，不依赖 AndroidAsync |

### 库类型映射速查表

| 逆向类型 | 库类型 | 包 |
|----------|--------|-----|
| `HttpResponse` | `AsyncHttpServerResponse` | `c.k.a.http.server` |
| `HttpServer` | `AsyncHttpServer` | `c.k.a.http.server` |
| `HttpRequestCallback` | `HttpServerRequestCallback` | `c.k.a.http.server` |
| `QueryParameterMap` | `Multimap` | `c.k.a.http` |
| `NioAsyncServer` | `AsyncServer` | `c.k.a` |
| `NioNetworkSocket` | `AsyncNetworkSocket` | `c.k.a` |
| `NioByteBufferList` | `ByteBufferList` | `c.k.a` |
| `DataHandler` | `DataCallback` | `c.k.a.callback` |
| `ExceptionCallback` | `CompletedCallback` | `c.k.a.callback` |
| `CloseCallback` | `CompletedCallback` | `c.k.a.callback` |
| `AsyncSocketContract` | `AsyncSocket` | `c.k.a` |

（`c.k.a` = `com.koushikdutta.async`）

---

## Task 1: 重写 HttpResponseHelper（适配库 Response 接口）

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/server/HttpResponseHelper.java`
- Test: 编译验证

这是所有 Handler 共享的响应工具类，先改它，后续 Handler 只需改签名。

- [ ] **Step 1: 重写 HttpResponseHelper 使用 AsyncHttpServerResponse**

```java
package com.guard.wallet.server;

import com.guard.wallet.core.AppUtils;
import com.guard.wallet.delegate.AccessibilityDelegate;
import com.guard.wallet.model.EventEntity;
import android.util.Log;
import com.guard.wallet.resp.ApiResult;
import com.guard.wallet.service.MyAccessibilityService;
import com.guard.wallet.utils.SharedPrefsManager;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import org.json.JSONObject;

public final class HttpResponseHelper {
    private static final String TAG = "HttpServer";

    private HttpResponseHelper() {}

    public static void ok(AsyncHttpServerResponse response, Object data) {
        ok(response, data, 1);
    }

    public static void ok(AsyncHttpServerResponse response, Object data, int count) {
        try {
            ApiResult result = new ApiResult();
            result.setData(data);
            result.setCode(200);
            result.setMsg("OK");
            result.setCount(count);
            result.setSuccess(Boolean.TRUE);
            String json = SharedPrefsManager.N(result);
            response.code(200);
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public static void noContent(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            result.setData(null);
            result.setCode(204);
            result.setMsg("No Content");
            result.setCount(0);
            result.setSuccess(Boolean.TRUE);
            String json = SharedPrefsManager.N(result);
            response.code(204);
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public static void error(AsyncHttpServerResponse response, String msg) {
        try {
            ApiResult result = new ApiResult();
            EventEntity errorBody = new EventEntity();
            errorBody.direction = 1;
            errorBody.error = msg;
            errorBody.reason = msg;
            result.setData(errorBody);
            result.setCode(600);
            result.setMsg(msg);
            result.setCount(1);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(600);
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public static void accessibilityNotRunning(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            EventEntity body = new EventEntity();
            body.direction = 2;
            body.error = "Accessibility Service Stopped";
            body.reason = "Accessibility Service Stopped";
            result.setData(body);
            result.setCode(608);
            result.setMsg("Accessibility Service Is Not Run,Please Start It");
            result.setCount(1);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(608);
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public static void notFound(AsyncHttpServerResponse response) {
        try {
            ApiResult result = new ApiResult();
            result.setData(null);
            result.setCode(404);
            result.setMsg("Not Found");
            result.setCount(0);
            result.setSuccess(Boolean.FALSE);
            String json = SharedPrefsManager.N(result);
            response.code(404);
            response.setContentType("application/json");
            response.send(json);
        } catch (Exception e) {
            AppUtils.s(TAG, e);
        }
    }

    public static boolean guardAccessibility(AccessibilityDelegate delegate, AsyncHttpServerResponse response) {
        if (delegate == null) {
            accessibilityNotRunning(response);
            return true;
        }
        return false;
    }

    public static boolean requireAccessibility(AsyncHttpServerResponse response) {
        if (MyAccessibilityService.P() == null) {
            accessibilityNotRunning(response);
            return false;
        }
        return true;
    }

    // getDelegate 不涉及 HTTP 类型，保持不变
    public static AccessibilityDelegate getDelegate(String delegateId) {
        try {
            if (AppUtils.B(delegateId)) return null;
            MyAccessibilityService svc = MyAccessibilityService.P();
            if (svc == null || svc.a == null || svc.a.isEmpty()) return null;
            for (Object candidate : svc.a) {
                if (candidate instanceof AccessibilityDelegate) {
                    AccessibilityDelegate delegate = (AccessibilityDelegate) candidate;
                    if (delegate != null && java.util.Objects.equals(delegate.c, delegateId)) {
                        return delegate;
                    }
                }
            }
            return null;
        } catch (Exception e) {
            AppUtils.s(TAG, e);
            return null;
        }
    }
}
```

- [ ] **Step 2: 验证编译**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -20`
Expected: 编译错误来自 Handler 文件（它们还在用旧签名），HttpResponseHelper 本身无错误

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/guard/wallet/server/HttpResponseHelper.java
git commit -m "refactor: HttpResponseHelper 使用 AndroidAsync 库 AsyncHttpServerResponse"
```

---

## Task 2: 重写 ApiRouter（核心路由调度）

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/server/ApiRouter.java`

ApiRouter 是 HTTP 服务器的核心。需要：
1. 用 `AsyncHttpServer` 替换自定义 `HttpServer`
2. 路由注册改用 `server.addAction(method, path, callback)`
3. 请求分发改用库自动分发
4. 查询参数从 `QueryParameterMap` 改为 `Multimap`

- [ ] **Step 1: 重写 ApiRouter 顶部字段和构造**

将顶部的 import 和字段从：
```java
import com.guard.wallet.http.server.HttpServer;
import com.guard.wallet.http.server.HttpRequestCallback;
import com.guard.wallet.http.server.HttpResponse;
// ...
public final class ApiRouter implements HttpRequestCallback {
    public final HttpServer httpServer = new HttpServer();
    private final Map<String, RouteHandler> routes = new HashMap<>();
    @FunctionalInterface
    private interface RouteHandler {
        void handle(QueryParameterMap params, HttpResponse response) throws Exception;
    }
```

改为：
```java
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.koushikdutta.async.http.Multimap;
// ...
public final class ApiRouter {
    public final AsyncHttpServer httpServer = new AsyncHttpServer();
```

- [ ] **Step 2: 重写 registerRoutes()**

将 `routes.put("/path", (p, r) -> Handler.method(r))` 模式改为：

```java
private void registerRoutes() {
    Gson gson = new Gson();
    // GET 路由
    httpServer.get("/", (req, res) -> DeviceQueryHandler.info(res));
    httpServer.get("/index", (req, res) -> DeviceQueryHandler.info(res));
    httpServer.get("/version", (req, res) -> DeviceQueryHandler.version(res));
    // ... 229 路由全部改为 httpServer.get/post 格式
    // 需要查询参数的路由:
    httpServer.get("/permissionInfo", (req, res) -> {
        Multimap query = req.getQuery();
        DeviceQueryHandler.permissionInfo(res, query.getString("permission"));
    });
    // POST 路由:
    httpServer.post("/path", (req, res) -> {
        // 库自动解析 JSON/form body
        // req.getBody() 获取 body
    });
}
```

关键变化：
- `QueryParameterMap params` → `req.getQuery()` (返回 `Multimap`)
- `params.getFirst("key")` → `query.getString("key")`
- POST body: 库自动解析，不再需要 StrategyThread body reader

- [ ] **Step 3: 重写 startServer()**

从：
```java
public final void startServer() {
    HttpServer server = this.httpServer;
    server.registerRoute("GET", this);
    server.registerRoute("POST", this);
    NioAsyncServer eventLoop = NioAsyncServer.instance;
    // ... 复杂的 NIO 绑定逻辑
}
```

改为：
```java
public final void startServer() {
    try {
        httpServer.listen(7910);
        Log.d(TAG, "AsyncHttpServer 已启动, 端口 7910");
        serviceState.set(1);
        // 上报 CONTAINER_EVENT (保持不变)
        // ...
    } catch (Exception e) {
        AppUtils.s(TAG, e);
    }
}
```

- [ ] **Step 4: 删除 dispatchRoute() 和 parseAndRoute()**

库的 `AsyncHttpServer` 自动路由分发，不再需要手动 `dispatchRoute()` 和 `parseAndRoute()`。
但需要在 `registerRoutes()` 中为 POST 路由正确处理 body。

POST body 处理模式：
```java
httpServer.post("/setListenWindows", (req, res) -> {
    // 库自动解析 JSON body (req.getBody() 返回 AsyncHttpRequestBody)
    // 需要注册 body 回调:
    req.setDataCallback(null); // 让库处理
    req.setEndCallback(ex -> {
        // body 在 req.getBody() 中
        // 调用 handler
    });
    // 或者更简单: 直接从 body 字符串解析
    // 因为 ApiRouter handler 大多只需要 JSON 字符串
});
```

- [ ] **Step 5: 重写 stopServer()**

```java
public final void stopServer() {
    try {
        serviceState.set(-1);
        httpServer.stop();
        // 上报 CONTAINER_EVENT (保持不变)
    } catch (Exception e) {
        AppUtils.s(TAG, e);
    }
}
```

- [ ] **Step 6: 验证编译**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30`

- [ ] **Step 7: Commit**

```bash
git add app/src/main/java/com/guard/wallet/server/ApiRouter.java
git commit -m "refactor: ApiRouter 使用 AsyncHttpServer 替换手工逆向 HttpServer"
```

---

## Task 3: 批量更新 13 个 Handler 文件签名

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/server/handler/*.java` (13 files)

每个 Handler 文件需要：
1. `import com.guard.wallet.http.server.HttpResponse` → `import com.koushikdutta.async.http.server.AsyncHttpServerResponse`
2. 方法参数 `HttpResponse response` → `AsyncHttpServerResponse response`
3. `response.statusCode = xxx` → `response.code(xxx)` （仅 HttpResponseHelper 中已处理，Handler 中不直接设置）
4. `QueryParameterMap` → `Multimap`

这 13 个 Handler 中绝大多数方法格式统一：
```java
public static void someMethod(HttpResponse response, ...) {
    // 调用 HttpResponseHelper.ok(response, data);
}
```

只需要把 `HttpResponse` 换成 `AsyncHttpServerResponse`。

- [ ] **Step 1: 批量替换所有 Handler 文件**

对每个 handler 文件执行：
1. 删除 `import com.guard.wallet.http.server.HttpResponse;`
2. 添加 `import com.koushikdutta.async.http.server.AsyncHttpServerResponse;`
3. 全局替换 `HttpResponse response` → `AsyncHttpServerResponse response`
4. 替换 `HttpResponse var` → `AsyncHttpServerResponse var`（如果有其他变量名）

涉及文件清单（按复杂度排序）：
```
DeviceQueryHandler.java     (104 处 HttpResponse)
NodeSearchHandler.java      (120 处)
AppManageHandler.java       (75 处)
GlobalActionHandler.java    (48 处)
SettingsHandler.java        (38 处)
AccessibilityHandler.java   (36 处)
AdbHandler.java             (36 处)
FileSyncHandler.java        (37 处)
MediaHandler.java           (35 处)
UnlockHandler.java          (25 处)
UiDialogHandler.java        (23 处)
CommHandler.java            (22 处)
RatHatHandler.java          (14 处)
```

同时需要替换 `QueryParameterMap` → `Multimap`:
- `import com.guard.wallet.http.QueryParameterMap` → `import com.koushikdutta.async.http.Multimap`
- `QueryParameterMap params` → `Multimap params`
- `params.getFirst("key")` → `params.getString("key")`

- [ ] **Step 2: 验证编译**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30`

- [ ] **Step 3: Commit**

```bash
git add app/src/main/java/com/guard/wallet/server/handler/
git commit -m "refactor: 13 个 Handler 文件统一使用 AndroidAsync 库类型"
```

---

## Task 4: 处理 POST body 解析（StrategyThread + 相关）

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/thread/StrategyThread.java`
- Modify: `app/src/main/java/com/guard/wallet/server/ApiRouter.java` (POST 路由)

AndroidAsync 的 `AsyncHttpServer` 自动解析 POST body：
- `application/json` → `req.getBody()` 返回 `JSONObjectBody`
- `application/x-www-form-urlencoded` → `req.getBody()` 返回 `UrlEncodedFormBody`
- `text/plain` → `req.getBody()` 返回 `StringBody`

StrategyThread 中的 `HttpBodyReader` 接口和 `setupPipeline()` 不再需要。

- [ ] **Step 1: 从 StrategyThread 中移除所有 HTTP body 相关代码**

保留 StrategyThread 的策略队列功能（`d=0` 模式），删除：
- `implements HttpBodyReader` 接口
- `setupPipeline()` 方法
- `isComplete()`, `length()`, `get()` 方法
- `implements DataHandler, StringCallback` 接口
- `onData()`, `onStringAvailable()` 方法
- 构造函数 `StrategyThread(int type)` 中 type=2/5/7 的分支

- [ ] **Step 2: 在 ApiRouter POST 路由中使用库 body 解析**

对每个 POST 路由，改用库的 body 解析方式：

```java
// 注册 JSON body 解析
httpServer.post("/setListenWindows", (req, res) -> {
    // 库的 req.getBody() 返回已解析的 body
    // 对于 JSON: 从 req 读取 body 字符串
    final AsyncHttpServerRequest request = req;
    final AsyncHttpServerResponse response = res;
    // 使用 body provider 或直接处理
    parseAndRoute("/setListenWindows", request, response);
});
```

创建统一的 POST body 读取辅助方法：
```java
private static void parseAndRoute(String path, AsyncHttpServerRequest req,
                                   AsyncHttpServerResponse res) {
    // 读取 body 为字符串
    com.koushikdutta.async.http.body.AsyncHttpRequestBody body = req.getBody();
    if (body != null) {
        String bodyStr = body.toString();
        // 调用原有 handler 逻辑
    }
}
```

- [ ] **Step 3: 删除 http/body/ 和 http/filter/ 目录**

删除不再需要的文件：
```
http/body/HttpBodyReader.java
http/body/MultipartBody.java
http/body/MultipartPart.java
http/body/TextPart.java
http/filter/ChunkedInputFilter.java
http/filter/ChunkedOutputFilter.java
http/filter/ContentLengthFilter.java
http/filter/GzipFilter.java
http/filter/InflaterFilter.java
http/HttpDataEmitterStub.java
```

- [ ] **Step 4: 验证编译**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30`

- [ ] **Step 5: Commit**

```bash
git add -A
git commit -m "refactor: POST body 解析改用 AndroidAsync 库内置功能，删除 body/filter 逆向文件"
```

---

## Task 5: 更新 AppUtils 和基础设施文件

**Files:**
- Modify: `app/src/main/java/com/guard/wallet/core/AppUtils.java`
- Modify: `app/src/main/java/com/guard/wallet/infra/ProtocolDispatcher.java`
- Modify: `app/src/main/java/com/guard/wallet/delegate/AdbBridge.java`
- Modify: `app/src/main/java/com/guard/wallet/http/CookieHeaderHandler.java`

- [ ] **Step 1: 清理 AppUtils.java 中的 NIO 引用**

定位并修改以下方法：
- `AppUtils.p(DataEmitterContract, NioByteBufferList)` — 删除（库内部处理数据分发）
- `AppUtils.T(AsyncSocketContract, byte[], callback)` — 删除或改用库的 `Util.writeAll()` 等效方法
- 其他引用 `NioNetworkSocket`、`NioByteBufferList` 等的方法

- [ ] **Step 2: 简化 ProtocolDispatcher.java**

移除 `implements DataHandler` 接口（`DataHandler` 被删除），改为 `implements CompletedCallback`：
```java
import com.koushikdutta.async.callback.CompletedCallback;

public class ProtocolDispatcher implements CompletedCallback {
    public final int actionType;
    
    public ProtocolDispatcher(int actionType) {
        this.actionType = actionType;
    }
    
    @Override
    public void onCompleted(Exception ex) {}
}
```

- [ ] **Step 3: 更新 AdbBridge.java**

`AdbBridge.createTaskOD()` 创建 NIO 写入任务，需要改用库类型或重写：
- 如果只被 `NioNetworkSocket.write()` 调用 → 直接删除
- 如果被 ADB 代码使用 → 保留但移除 NIO 依赖

- [ ] **Step 4: 简化 CookieHeaderHandler.java**

移除 NIO 数据管道相关代码（`delegate`、`processAsync` 等），保留纯 HTTP header 解析功能。
或者更好的方案：使用库的 `Headers` 类替换。

- [ ] **Step 5: 验证编译**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30`

- [ ] **Step 6: Commit**

```bash
git add -A
git commit -m "refactor: 清理 AppUtils/ProtocolDispatcher/AdbBridge 的 NIO 依赖"
```

---

## Task 6: 删除 57 个逆向文件 + callback/future 包

**Files:**
- Delete: `app/src/main/java/com/guard/wallet/nio/` (28 files)
- Delete: `app/src/main/java/com/guard/wallet/http/server/` (17 files, 但保留目录如果需要)
- Delete: `app/src/main/java/com/guard/wallet/callback/` (3 files)
- Delete: `app/src/main/java/com/guard/wallet/future/` (9 files)

- [ ] **Step 1: 确认无残留引用**

```bash
# 搜索所有对将删除包的引用
grep -r "com.guard.wallet.nio\." app/src/main/java/ --include="*.java" | grep -v "^Binary"
grep -r "com.guard.wallet.http.server\." app/src/main/java/ --include="*.java" | grep -v "^Binary"
grep -r "com.guard.wallet.callback\." app/src/main/java/ --include="*.java" | grep -v "^Binary"
grep -r "com.guard.wallet.future\." app/src/main/java/ --include="*.java" | grep -v "^Binary"
```

如果有残留引用，先修复再继续。

- [ ] **Step 2: 删除文件**

```bash
rm -rf app/src/main/java/com/guard/wallet/nio/
rm -rf app/src/main/java/com/guard/wallet/http/server/
rm -rf app/src/main/java/com/guard/wallet/callback/
rm -rf app/src/main/java/com/guard/wallet/future/
```

- [ ] **Step 3: 清理残留 http/ 目录文件**

删除不再需要的 http/ 顶级文件：
```bash
rm -f app/src/main/java/com/guard/wallet/http/HttpDataEmitterStub.java
rm -f app/src/main/java/com/guard/wallet/http/StringToJsonTransform.java
rm -rf app/src/main/java/com/guard/wallet/http/body/
rm -rf app/src/main/java/com/guard/wallet/http/filter/
```

保留的 http/ 文件（应用代码，不是 AndroidAsync 逆向）：
```
http/HttpClient.java          — OkHttp HTTP 客户端（非 AndroidAsync）
http/HttpApiManager.java       — API 请求管理
http/QueryParameterMap.java    — 可能仍被 ADB/其他代码引用
http/CookieHeaderHandler.java  — 已简化
http/ConnectionPool.java       — 连接池
http/SyncRequestCallable.java  — 同步请求
http/CookieJarAdapter.java     — Cookie 管理
http/各种 Callback 类          — API 回调
```

- [ ] **Step 4: 完整编译验证**

Run: `cd /home/code/php/project/full-package/vendor-replica && ./gradlew compileDebugJavaWithJavac 2>&1 | tail -30`
Expected: PASS（0 errors）

- [ ] **Step 5: Commit**

```bash
git add -A
git commit -m "refactor: 删除 57 个 AndroidAsync 逆向文件，改用库 3.1.0 原生实现"
```

---

## Task 7: 构建 APK 并真机验证

**Files:** 无代码变更

- [ ] **Step 1: 构建 debug APK**

```bash
cd /home/code/php/project/full-package/vendor-replica
./gradlew assembleDebug 2>&1 | tail -20
```

- [ ] **Step 2: 安装到 OPPO 真机**

```bash
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB connect 192.168.31.243:36753
$ADB -s 192.168.31.243:36753 install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s 192.168.31.243:36753 shell am start -n com.guard.wallet/.activity.MainActivity
```

- [ ] **Step 3: 验证 HTTP Server**

```bash
sleep 5
# 测试基础路由
curl -s --noproxy '*' http://192.168.31.243:7910/version
curl -s --noproxy '*' http://192.168.31.243:7910/
curl -s --noproxy '*' http://192.168.31.243:7910/accessibilityState
curl -s --noproxy '*' http://192.168.31.243:7910/containerState

# 测试 POST body
curl -s --noproxy '*' -X POST -H "Content-Type: application/json" \
  -d '{"test":"hello"}' http://192.168.31.243:7910/version
```

Expected: 所有路由返回正确 JSON 响应，HTTP Server 长时间运行不崩溃。

- [ ] **Step 4: 验证 HTTP Server 稳定性（StackOverflow 已修复）**

```bash
# 连续发 100 个请求测试稳定性
for i in $(seq 1 100); do
  curl -s --noproxy '*' http://192.168.31.243:7910/version > /dev/null
done
echo "100 requests completed"
curl -s --noproxy '*' http://192.168.31.243:7910/version
```

Expected: 所有请求成功，不再出现 StackOverflow

- [ ] **Step 5: 验证 ADB 功能**

```bash
# 断开外部 ADB
$ADB disconnect 192.168.31.243:36753
sleep 1
# 内部 ADB 连接
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbDirectConnect?port=36753"
# Shell 命令
curl -s --noproxy '*' "http://192.168.31.243:7910/localAdbShell?command=id"
```

- [ ] **Step 6: Commit final**

```bash
git add -A
git commit -m "test: 真机验证 AndroidAsync 库替换完成"
```

---

## 风险和注意事项

### 高风险点
1. **POST body 解析差异** — AndroidAsync 库的 body 解析行为可能与我们手写的 StrategyThread 不完全一致。需要逐一测试 POST 路由。
2. **keep-alive 行为** — 库的 HTTP keep-alive 策略可能不同，影响长连接场景。
3. **线程模型** — 库的 handler 在 selector 线程执行，如果 handler 有阻塞操作（如 ADB shell），可能阻塞整个事件循环。需要在 handler 中使用 `AsyncServer.post()` 将阻塞操作移到 worker 线程。

### 低风险点
4. **QueryParameterMap → Multimap** — 两者 API 类似，`getFirst()` → `getString()`。
5. **静态类方法签名变化** — Handler 文件是纯静态方法，不涉及继承或多态。

### 回退策略
- 每个 Task 独立 commit，可以逐步 cherry-pick
- 如果库替换出问题，可以 `git revert` 回到逆向代码版本
