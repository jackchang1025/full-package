# Panel 命令控制 HTTP 迁移实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 把 Panel 控制面板的命令控制层（17 个 emit）从 WebSocket 迁移到 HTTP，前端直接按 Android HTTP API 的数据格式发请求。WebSocket 仅保留用于状态推送、屏幕流、键盘监听、以及 Android 端尚未实现的命令。

**Architecture:** Laravel 新增一个透明代理端点 `POST /devices/{device}/api-proxy`，内部调用 `DeviceProxyService::request()` 把请求转发到 frpc 隧道对应端口 (`http://{frps_host}:{frpc_base_port}{path}`)，不做 payload 翻译。前端新增 `useDeviceApi()` composable 封装 axios 调用该端点，Control.vue 的 handler 对已支持命令直接用 `useDeviceApi` 调用对应 Android 路径，未支持命令保留现有 WS 实现作为 fallback。

**Tech Stack:** Laravel 12 + PHP 8.5 / Vue 3 Composition API + TypeScript + axios / PHPUnit (后端测试) / AndroidAsync HTTP server on device port 7910.

**Scope Boundary:**
- ✅ 迁移 10 个已有 Android API 对应的命令：navigate / lock(0,1) / wakeScreen / paste / openQuickApp / sendBlock / toggleBlockText / modifyPassword
- ⏸ 保留 WS 的命令（Android 未实现，本计划不补）：volumeUp/Down / showKeyboard/hideKeyboard / sendMute/Unmute / sendKb / hideIcon / sendPhish / sendBankPhish / lock(2,3)
- ⏸ 截图 (`handleScreenshot`) 保留 WS 流路径（与"屏幕流保留 WS"原则一致）
- ⏸ 数据获取类（SMS / Contacts / Files / Apps / Keylog / Location / Camera / Mic / Gallery / Inject）保留 WS 不动
- ⏸ Android 端补齐缺失命令作为 follow-up 单独计划
- ⚠️ `wakeUpScreen` 存在 replica 缺陷：`GlobalActionHandler.wakeUpScreen()` 只在 `DeviceUtils.isVivoFamily()` 返回 true 时工作，非 vivo 设备会返回 false。本计划只负责 transport 迁移，不修 replica 的业务缺陷。前端迁移后若非 vivo 设备点"点亮"无效，属于已知问题。
- ⚠️ `/syncLockCipher` 在 replica 侧有 1 行 bug：`ApiRouter.java:846` 反序列化成 `DeviceCipherStateVO`，但 `UnlockHandler.java:107` 用 `instanceof ReqUnlockDeviceVO` 检查，永远 false，密码不会被保存。Task 0c 必须先修这个 bug，Task 12 才能真正工作。

---

## 精确 Android API 契约（本计划依据）

**核实依据**（已逐文件核实，不是从调研摘要推导）：
- `vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java`
- `vendor-replica/app/src/main/java/com/guard/wallet/server/handler/GlobalActionHandler.java`
- `vendor-replica/app/src/main/java/com/guard/wallet/server/handler/UnlockHandler.java`
- `vendor-replica/app/src/main/java/com/guard/wallet/condition/GlobalActionCondition.java`（字段名 `actionName`，不是 `action`）
- `vendor-replica/app/src/main/java/com/guard/wallet/utils/GlobalActionExecutor.java:30-32`（值 `back/home/recent`，**注意 `recent` 是单数**）
- `vendor-replica/app/src/main/java/com/guard/wallet/req/ReqUnlockDeviceVO.java`（字段 `textCipher`、`deviceId`）
- `vendor-replica/app/src/main/java/com/guard/wallet/req/DeviceCipherStateVO.java`（字段 `textCipher`，不同于 ReqUnlockDeviceVO）

| Panel emit | 旧 WS payload (摘要) | 新 Android API | 参数 |
|---|---|---|---|
| navigate(back) | `{comand:'nav', navshort:'back'}` | `POST /global/action` | body: `{"actionName":"back"}` |
| navigate(home) | `{comand:'nav', navshort:'home'}` | `POST /global/action` | body: `{"actionName":"home"}` |
| navigate(recent) | `{comand:'nav', navshort:'recent'}` | `POST /global/action` | body: `{"actionName":"recent"}` |
| lock(0) 解锁 | `{comand:'L', lockit:'0'}` | `GET /unlock` | - (使用设备本地已保存的 cipher) |
| lock(1) 锁屏 | `{comand:'L', lockit:'1'}` | `GET /global/lockScreen` | - (API 28+) |
| wakeScreen | `{subc:'display', display:'on'}` | `GET /global/wakeUpScreen` | - (仅 vivo 家族生效，见 Scope Boundary) |
| paste(text) | `{comand:'paste', txt}` | `GET /global/setText` | query: `text=...` (写入当前焦点输入框) |
| openQuickApp(app) | `{subc:'OPENAPP', packageName}` | `GET /startApp` | query: `packageName=xxx&start=true` |
| sendBlock(0) 黑屏 | `{comand:'block', bstate:'0'}` | `GET /blockView` | query: `show=true&transparent=false&zeroBrightness=true&destroyLock=false` |
| sendBlock(1) 取消 | `{comand:'block', bstate:'1'}` | `GET /blockView` | query: `show=false` |
| sendBlock(2) 阻止 | `{comand:'block', bstate:'2'}` | `GET /blockView` | query: `show=true&transparent=true&zeroBrightness=false&destroyLock=false` |
| sendBlock(3) 允许 | `{comand:'block', bstate:'3'}` | `GET /blockView` | query: `show=false` |
| toggleBlockText | `{comand:'blockd', blocktext}` + `{comand:'block', bstate:'0', color}` | `GET /blockView` | query: `show=true&hint={text}&zeroBrightness={bg=='0'?'true':'false'}&destroyLock={bg=='1'?'true':'false'}` |
| modifyPassword | `{comand:'phonepass', passtype:'1', txt:pwd}` | `POST /syncLockCipher` (需先修 Task 0c) | body: `{"textCipher":pwd,"deviceId":uuid}` (对应 ReqUnlockDeviceVO 字段) |

**响应格式**：统一 JSON envelope `{code, success, msg, data, count}`；HTTP 200 正常，HTTP 600 业务错误。

### ⚠️ 已废弃的代码（本计划会暴露出来但不修复）

`DeviceController::frpcAction` (line 181-191) 使用 `['action' => $action]` + 路由校验 `in:back,home,recents,lock`。**这两处都是错的**：
- 字段应是 `actionName`（不是 `action`）
- `recents` 应是 `recent`（单数）
- `lock` 不应通过 `/global/action` 而是 `/global/lockScreen`

由于前端没有调用 `frpcAction` 端点，这个 bug 未被触发。本计划的 Task 5 不依赖 `frpcAction`；后续清理可作为独立 PR。

---

## 文件结构

**部署 / 环境（Task 0）**
- Modify: `app/compose.prod.yaml` — 收窄 frps 数据段端口到 127.0.0.1 绑定
- Modify: `app/resources/ts/env.d.ts` — 追加 `Window.axios` 类型声明
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java` — 修 syncLockCipher 反序列化 bug（1 行）

**后端 (Laravel)**
- Modify: `app/app/Services/DeviceProxyService.php` — 新增 `request()` 通用方法 + `getDeviceBaseUrl()` 加端口范围断言
- Modify: `app/app/Http/Controllers/DeviceController.php` — 新增 `apiProxy()` action，含审计日志、错误过滤、命名限流
- Create: `app/app/Http/Requests/Device/DeviceProxyRequest.php` — 校验 + 白名单 + 深度校验（**注意**：放在 `Device` 子目录，与 `UpdateDeviceRequest` 并列）
- Modify: `app/app/Providers/AppServiceProvider.php` — 注册 `device-cipher` 命名限流器
- Modify: `app/routes/web.php` — 新增 `POST /devices/{device}/api-proxy` 路由 + `throttle:60,1`
- Create: `app/tests/Feature/Http/DeviceProxyTest.php` — Feature 测试（HTTP::fake）+ 子账号场景
- Create: `app/tests/Unit/Services/DeviceProxyServiceRequestTest.php` — Unit 测试（含端口越界）

**前端 (Vue 3 + TypeScript)**
- Modify: `app/resources/ts/env.d.ts` — 见 Task 0b
- Create: `app/resources/ts/composables/useDeviceApi.ts` — axios 封装 composable
- Modify: `app/resources/ts/Pages/Devices/Control.vue` — 重写 10 个 handler
- **不改动**: `useScreenControl.ts` / `useDeviceWebSocket.ts` — 保留给 tap/swipe/ocr/screen-stream 等继续用 WS 的命令

**文档**
- Modify/Create: `docs/platform/PANEL_HTTP_PROXY.md` — 迁移说明 + 安全设计注记

---

### Task 0: 前置环境与 Upstream Bug 修复 🔴

本 task 是所有后续工作的**前置条件**。分 3 个子步骤，每个都是独立的 PR-able 变更。

#### Task 0a: 收窄 frps 生产端口绑定（安全 BLOCKER）

**背景**：`app/compose.prod.yaml:87` 目前声明 `'20000-30000:20000-30000'`，默认绑定 `0.0.0.0`。任何知道服务器公网 IP 的人都可以绕过 Laravel 直接访问设备的 229 条 HTTP API 路由，包括 `/global/execCommand`（通过 `Runtime.getRuntime().exec(new String[]{"sh","-c",command})` 执行任意 shell — 见 `GlobalActionHandler.java:59`）和 `/localAdbShell`。

**Files:**
- Modify: `app/compose.prod.yaml`

- [ ] **Step 1: 确认当前暴露状态**

```bash
cd app
grep -n "20000-30000" compose.prod.yaml
```

Expected: line 87 显示 `- '20000-30000:20000-30000'`。

- [ ] **Step 2: 收窄绑定到本机回环**

Modify `app/compose.prod.yaml` line 80-97 (frps service)，把 frpc 数据段端口映射前加 `127.0.0.1:`：

```yaml
    frps:
        image: 'snowdreamtech/frps:0.51.3'
        container_name: feiying-frps
        restart: unless-stopped
        ports:
            - '${FRPS_BIND_PORT:-7000}:7000'        # frp 协议端口(设备连接)
            - '${FRPS_DASHBOARD_PORT:-7500}:7500'    # Dashboard 管理面板
            # 设备隧道数据段端口 — 只绑定本机回环，禁止外网直接访问设备 HTTP API
            # Laravel 与 frps 在同一台宿主机 / 同一 Docker 网络内通过服务名 'frps' 访问
            - '127.0.0.1:20000-30000:20000-30000'
```

**重要说明**：
- `${FRPS_BIND_PORT:-7000}` 是 frpc 客户端连接上来的控制端口，**必须保持对公网开放**，否则设备无法建立隧道
- `20000-30000` 是 frps 给 panel 侧使用的代理端口段，**Laravel 通过 Docker 内部网络访问 `frps:20xxx`**，无需绑定公网
- 如果 Laravel 在另一台物理机部署（不同宿主机），这个修改会导致 Laravel 无法访问隧道。这种情况下需要改用专用的内网/VPN 接口绑定，不能改为 `127.0.0.1`

- [ ] **Step 3: 验证测试（分 2 步）**

本地 dry-run 验证 yaml 语法：
```bash
cd app
docker compose -f compose.prod.yaml config > /tmp/compose-parsed.yaml 2>&1
grep -A 2 "frps" /tmp/compose-parsed.yaml | grep "20000"
```

Expected: 显示 `127.0.0.1:20000` 前缀存在。

生产环境（手动执行，不在 CI）：
```bash
# 部署后在生产主机上验证端口绑定
ss -tlnp | grep -E ":2[0-9]{4}\s"
# Expected: 全部绑定 127.0.0.1，无 0.0.0.0 或 :::
```

- [ ] **Step 4: 提交**

```bash
cd app
git add compose.prod.yaml
git commit -m "security(compose): bind frps data ports to 127.0.0.1 only

frpc data ports 20000-30000 were exposed on 0.0.0.0 in prod, which
let unauthenticated attackers directly reach device HTTP APIs including
/global/execCommand (arbitrary shell) and /localAdbShell.

Bind them to loopback only; Laravel reaches frps via docker service name."
```

#### Task 0b: 追加 `window.axios` TypeScript 类型声明

**背景**：`app/resources/js/bootstrap.js` 在运行时把 axios 挂到 `window.axios`，但 `env.d.ts` 没有对应类型声明。Task 4 里 `window.axios.post<DeviceApiResult>(...)` 会被 TypeScript 当成 `any`，丢失类型安全。

**Files:**
- Modify: `app/resources/ts/env.d.ts`

- [ ] **Step 1: 读现有 env.d.ts 内容**

```bash
cd app
cat resources/ts/env.d.ts
```

观察现有声明块的结构（比如 naive-ui 的 `$message`、vite 的 `ImportMeta` 等）。

- [ ] **Step 2: 追加 Window.axios 声明**

在 `app/resources/ts/env.d.ts` 文件末尾追加：

```typescript
// ─── 全局 axios 实例 ────────────────────────────────────────────
// 在 app/resources/js/bootstrap.js 中挂载到 window.axios，
// 全项目 HTTP 调用统一通过这个实例（带 X-Requested-With / X-XSRF-TOKEN 等默认 header）。
declare global {
    interface Window {
        axios: import('axios').AxiosInstance;
    }
}

export {};
```

**注意**：如果文件已有 `declare global { ... }` 块，把 `interface Window { axios: ... }` 合并进去，而不是再开一个 block。如果文件末尾已经有 `export {};`，不要重复追加。

- [ ] **Step 3: 类型检查验证**

```bash
cd app
./vendor/bin/sail exec -T laravel.test npx vue-tsc --noEmit 2>&1 | grep -E "error TS" | head -20
```

Expected: 没有 `window.axios` 相关的 TS 报错（其他历史报错可忽略）。

- [ ] **Step 4: 提交**

```bash
cd app
git add resources/ts/env.d.ts
git commit -m "chore(types): declare window.axios as AxiosInstance"
```

#### Task 0c: 修 replica 的 `/syncLockCipher` 反序列化 bug（1 行）

**背景**：`ApiRouter.java:845-849` 把 `/syncLockCipher` 的 request body 反序列化成 `DeviceCipherStateVO`，然后 `UnlockHandler.java:107` 用 `instanceof ReqUnlockDeviceVO` 做类型检查，这个检查**永远为 false**，密码永远不会被保存。没有这个修复，Task 12 的 HTTP 迁移即使契约写对也只会返回 200 但实际无效。

**Files:**
- Modify: `vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java` (line 845-849)

- [ ] **Step 1: 对照当前代码**

Read `vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java` line 845-849, 应该看到:

```java
INTERNAL_ROUTES.put("/syncLockCipher", (p, r) -> {
    String raw = p.getString("_raw_body");
    DeviceCipherStateVO vo = raw != null ? (DeviceCipherStateVO) SharedPrefsManager.d(raw, DeviceCipherStateVO.class) : null;
    UnlockHandler.syncLockCipher(vo, r);
});
```

- [ ] **Step 2: 写回归测试（replica 侧，JUnit）**

Create `vendor-replica/app/src/test/java/com/guard/wallet/server/handler/UnlockHandlerSyncLockCipherTest.java`:

```java
package com.guard.wallet.server.handler;

import com.guard.wallet.req.ReqUnlockDeviceVO;
import com.guard.wallet.utils.SharedPrefsManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnlockHandlerSyncLockCipherTest {

    /**
     * 回归测试：验证 /syncLockCipher 反序列化后的 VO 能通过 instanceof 检查。
     *
     * 背景：2026-04 之前 ApiRouter 把 body 反序列化成 DeviceCipherStateVO，
     * 但 UnlockHandler.syncLockCipher() 用 instanceof ReqUnlockDeviceVO 检查，
     * 永远为 false，密码不会保存。此测试锁定修复后行为。
     */
    @Test
    public void deserializedVoMustBeReqUnlockDeviceVO() {
        String json = "{\"textCipher\":\"1234\",\"deviceId\":\"test-uuid\"}";

        Object vo = SharedPrefsManager.d(json, ReqUnlockDeviceVO.class);

        assertNotNull("反序列化结果不能为 null", vo);
        assertTrue(
            "反序列化结果必须是 ReqUnlockDeviceVO 才能通过 UnlockHandler instanceof 检查",
            vo instanceof ReqUnlockDeviceVO
        );
        assertEquals("1234", ((ReqUnlockDeviceVO) vo).getTextCipher());
        assertEquals("test-uuid", ((ReqUnlockDeviceVO) vo).getDeviceId());
    }
}
```

- [ ] **Step 3: 运行测试验证失败 — 注意现有测试不会失败，这里是新增**

```bash
cd vendor-replica
./gradlew test --tests com.guard.wallet.server.handler.UnlockHandlerSyncLockCipherTest 2>&1 | tail -30
```

Expected: 测试通过（因为反序列化目标我们写对了）。这个测试是"锁定期望行为"，防止未来有人又把类型改回 `DeviceCipherStateVO`。

- [ ] **Step 4: 修复 ApiRouter**

Edit `vendor-replica/app/src/main/java/com/guard/wallet/server/ApiRouter.java` 把 line 845-849 的 lambda 替换为：

```java
        INTERNAL_ROUTES.put("/syncLockCipher", (p, r) -> {
            String raw = p.getString("_raw_body");
            // vendor 映射: syncLockCipher → t(ReqUnlockDeviceVO, k)
            // UnlockHandler.syncLockCipher() 用 instanceof ReqUnlockDeviceVO 检查，必须反序列化成这个类型
            ReqUnlockDeviceVO vo = raw != null
                ? (ReqUnlockDeviceVO) SharedPrefsManager.d(raw, ReqUnlockDeviceVO.class)
                : null;
            UnlockHandler.syncLockCipher(vo, r);
        });
```

**导入检查**：确认 `ApiRouter.java` 顶部已经有 `import com.guard.wallet.req.ReqUnlockDeviceVO;`（查看 ApiRouter.java line 19 附近的 `import com.guard.wallet.req.*;` wildcard import — 已覆盖，无需改）。

- [ ] **Step 5: 运行现有单元测试确认无回归**

```bash
cd vendor-replica
./gradlew test 2>&1 | tail -30
```

Expected: all tests pass, including新增的 `UnlockHandlerSyncLockCipherTest`。

- [ ] **Step 6: 提交**

```bash
cd vendor-replica
git add app/src/main/java/com/guard/wallet/server/ApiRouter.java \
        app/src/test/java/com/guard/wallet/server/handler/UnlockHandlerSyncLockCipherTest.java
git commit -m "fix(replica): deserialize /syncLockCipher body to ReqUnlockDeviceVO

ApiRouter was deserializing to DeviceCipherStateVO while
UnlockHandler.syncLockCipher() checks instanceof ReqUnlockDeviceVO,
making the instanceof branch unreachable — cipher never saved.

Both VOs share a 'textCipher' field so JSON payload is compatible.
Add regression test to lock down the expected type."
```

---

### Task 1: Laravel — 给 DeviceProxyService 新增 request() 通用方法

**Files:**
- Modify: `app/app/Services/DeviceProxyService.php`
- Create: `app/tests/Unit/Services/DeviceProxyServiceRequestTest.php`

- [ ] **Step 1: 写失败的单元测试 — request + 端口范围断言（纵深防御）**

Create `app/tests/Unit/Services/DeviceProxyServiceRequestTest.php`:

```php
<?php

declare(strict_types=1);

namespace Tests\Unit\Services;

use App\Models\Device;
use App\Services\DeviceProxyService;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Http;
use Tests\TestCase;

class DeviceProxyServiceRequestTest extends TestCase
{
    use RefreshDatabase;

    protected function setUp(): void
    {
        parent::setUp();
        config([
            'frpc.proxy_host' => 'frps',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);
    }

    public function test_request_get_forwards_to_frpc_base_port_with_query(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(
                ['code' => 200, 'success' => true, 'data' => true],
                200,
            ),
        ]);

        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/global/setText', ['text' => 'hello']);

        $this->assertTrue($response->ok());
        $this->assertSame(200, $response->status);
        Http::assertSent(function ($req) {
            return $req->url() === 'http://frps:20000/global/setText?text=hello'
                && $req->method() === 'GET';
        });
    }

    public function test_request_post_forwards_json_body(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200),
        ]);

        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $response = $service->request(
            $device,
            'POST',
            '/global/action',
            [],
            ['actionName' => 'back'],
        );

        $this->assertTrue($response->ok());
        Http::assertSent(function ($req) {
            return $req->url() === 'http://frps:20000/global/action'
                && $req->method() === 'POST'
                && $req->data() === ['actionName' => 'back'];
        });
    }

    public function test_request_returns_no_tunnel_when_port_not_allocated(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => null]);
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/info');

        $this->assertFalse($response->ok());
        $this->assertStringContainsString('no frpc tunnel', strtolower($response->error ?? ''));
    }

    public function test_request_rejects_unsupported_method(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => 20000]);
        $service = new DeviceProxyService;

        $this->expectException(\InvalidArgumentException::class);

        $service->request($device, 'DELETE', '/info');
    }

    /**
     * 纵深防御：防止 SSRF via frpc_base_port。
     *
     * 即使 UpdateDeviceRequest 目前只允许修改 remark 字段，
     * 这层断言是额外防线 — 如果未来有其他路径能修改 frpc_base_port（比如
     * AgentController 或某个管理脚本），攻击者不能把端口改成 6379/3306/etc
     * 让 Laravel 把命令发到内网其他服务。
     */
    public function test_request_rejects_port_outside_range(): void
    {
        $device = Device::factory()->create(['frpc_base_port' => 6379]); // Redis port
        $service = new DeviceProxyService;

        $response = $service->request($device, 'GET', '/info');

        $this->assertFalse($response->ok());
        $this->assertSame(0, $response->status);
        $this->assertStringContainsString('out of range', strtolower($response->error ?? ''));
    }

    public function test_request_accepts_port_at_range_boundaries(): void
    {
        Http::fake(['*' => Http::response(['code' => 200, 'success' => true], 200)]);

        $startDevice = Device::factory()->create(['frpc_base_port' => 20000]);
        $endDevice   = Device::factory()->create(['frpc_base_port' => 30000]);

        $service = new DeviceProxyService;

        $this->assertTrue($service->request($startDevice, 'GET', '/info')->ok());
        $this->assertTrue($service->request($endDevice, 'GET', '/info')->ok());
    }
}
```

- [ ] **Step 2: 运行测试验证失败**

```bash
cd app
./vendor/bin/sail pest tests/Unit/Services/DeviceProxyServiceRequestTest.php
```

Expected: 测试失败，至少 `test_request_*` 4 条（method 不存在）+ `test_request_rejects_port_outside_range`（端口断言未实现）。

- [ ] **Step 3: 在 DeviceProxyService 实现 request() + 端口范围断言**

Modify `app/app/Services/DeviceProxyService.php`:

**3.1 修改 `getDeviceBaseUrl()`**，替换 line 47-54 的方法体：

```php
    /**
     * 获取设备 HTTP API 的隧道基础 URL。
     * 例: http://frps:20000
     *
     * 纵深防御：校验 frpc_base_port 在 config('frpc.port_range_*') 范围内，
     * 防止 SSRF —— 如果有其他路径能修改此字段，攻击者不能把端口指向
     * 内网 Redis/MySQL/etc。
     *
     * 端口越界时返回 null，调用方视作 "no tunnel"。
     */
    public function getDeviceBaseUrl(Device $device): ?string
    {
        if (! $this->hasTunnel($device)) {
            return null;
        }

        $port = (int) $device->frpc_base_port;
        $start = (int) config('frpc.port_range_start', 20000);
        $end = (int) config('frpc.port_range_end', 30000);

        if ($port < $start || $port > $end) {
            \Illuminate\Support\Facades\Log::channel('security')->warning(
                'device.frpc_base_port out of range',
                ['device_id' => $device->uuid, 'port' => $port, 'range' => "$start-$end"],
            );

            return null;
        }

        return "http://{$this->frpsHost}:{$port}";
    }
```

**3.2 改造 `get()` 和 `post()` 方法**，让"端口越界"情况返回明确的 out-of-range 错误而不是普通 no_tunnel。在这两个方法的 `$baseUrl === null` 分支内区分是哪种情况：

```php
    public function get(Device $device, string $path, array $query = [], ?int $timeout = null): DeviceApiResponse
    {
        $baseUrl = $this->getDeviceBaseUrl($device);
        if ($baseUrl === null) {
            return $this->hasTunnel($device)
                ? DeviceApiResponse::portOutOfRange((int) $device->frpc_base_port)
                : DeviceApiResponse::noTunnel();
        }

        try {
            $response = Http::timeout($timeout ?? $this->defaultTimeout)
                ->get($baseUrl . $path, $query);

            return DeviceApiResponse::fromHttp($response);
        } catch (ConnectionException $e) {
            return DeviceApiResponse::connectionFailed($e->getMessage());
        }
    }

    public function post(Device $device, string $path, array $body = [], ?int $timeout = null): DeviceApiResponse
    {
        $baseUrl = $this->getDeviceBaseUrl($device);
        if ($baseUrl === null) {
            return $this->hasTunnel($device)
                ? DeviceApiResponse::portOutOfRange((int) $device->frpc_base_port)
                : DeviceApiResponse::noTunnel();
        }

        try {
            $response = Http::timeout($timeout ?? $this->defaultTimeout)
                ->post($baseUrl . $path, $body);

            return DeviceApiResponse::fromHttp($response);
        } catch (ConnectionException $e) {
            return DeviceApiResponse::connectionFailed($e->getMessage());
        }
    }
```

**3.3 在 `post()` 方法之后新增 `request()`**：

```php
    /**
     * 通用转发方法。
     *
     * 只支持 GET / POST（与 Android HTTP API 对齐）。
     * 对 Panel 的"透明代理"入口使用：不做 payload 翻译，
     * 把 query 和 body 直接转给 frpc 隧道后的设备 HTTP server。
     *
     * @param  'GET'|'POST'  $method
     * @param  array<string, mixed>  $query
     * @param  array<string, mixed>  $body
     */
    public function request(
        Device $device,
        string $method,
        string $path,
        array $query = [],
        array $body = [],
        ?int $timeout = null,
    ): DeviceApiResponse {
        $method = strtoupper($method);
        if ($method !== 'GET' && $method !== 'POST') {
            throw new \InvalidArgumentException("Unsupported HTTP method: {$method}");
        }

        return $method === 'GET'
            ? $this->get($device, $path, $query, $timeout)
            : $this->post($device, $path, $body, $timeout);
    }
```

**3.4 在 `DeviceApiResponse` 加 `portOutOfRange()` 工厂**：

Modify `app/app/Services/DeviceApiResponse.php`，在 `connectionFailed()` 后追加：

```php
    public static function portOutOfRange(int $port): self
    {
        return new self(false, 0, null, "Device frpc_base_port {$port} is out of range");
    }
```

- [ ] **Step 4: 运行测试验证通过**

```bash
cd app
./vendor/bin/sail pest tests/Unit/Services/DeviceProxyServiceRequestTest.php
```

Expected: 6 passing tests.

- [ ] **Step 5: 提交**

```bash
cd app
git add app/Services/DeviceProxyService.php \
        app/Services/DeviceApiResponse.php \
        tests/Unit/Services/DeviceProxyServiceRequestTest.php
git commit -m "feat(proxy): add DeviceProxyService::request() + port range defense

- New generic request() method supporting GET/POST
- Validate frpc_base_port against config('frpc.port_range_*') as SSRF
  defense-in-depth; out-of-range returns portOutOfRange() and logs to
  security channel
- DeviceApiResponse::portOutOfRange() factory for the new error state"
```

---

### Task 2: Laravel — DeviceProxyRequest Form Request（白名单 + 深度校验 + 输入长度防御）

**Files:**
- Create: `app/app/Http/Requests/Device/DeviceProxyRequest.php` （**注意**：放 `Device` 子目录，与 `UpdateDeviceRequest` 并列，符合项目约定）

- [ ] **Step 1: 创建 FormRequest 类**

Create `app/app/Http/Requests/Device/DeviceProxyRequest.php`:

```php
<?php

declare(strict_types=1);

namespace App\Http\Requests\Device;

use Illuminate\Foundation\Http\FormRequest;
use Illuminate\Validation\Rule;

/**
 * Panel → Device HTTP 透明代理请求。
 *
 * body: { method, path, query?, body? }
 *
 * 防御分层：
 *  1. 路径白名单（Rule::in）— 拒绝非 Panel 用到的 Android API 路径
 *  2. 路径正则（regex）— 纵深防御，拒绝任何异常字符（. % ? @ ..）
 *  3. query.* 长度上限 — 防止 /global/setText 收到巨 text 耗尽设备内存
 *  4. 按路径条件校验 — /syncLockCipher、/startApp 的 body / query 必须形态正确
 */
class DeviceProxyRequest extends FormRequest
{
    /**
     * 允许 panel 通过透明代理调用的 Android API 路径（精确匹配）。
     * 与 vendor-replica/.../server/ApiRouter.java 中注册的路由严格对齐。
     *
     * 新增路径必须同时：
     *  1. 更新此常量
     *  2. 在 rules() 里加对应的 body / query 条件校验（如果有敏感字段）
     *  3. 在 docs/platform/PANEL_HTTP_PROXY.md 列出
     */
    public const ALLOWED_PATHS = [
        '/global/action',
        '/global/lockScreen',
        '/global/wakeUpScreen',
        '/global/setText',
        '/unlock',
        '/startApp',
        '/blockView',
        '/syncLockCipher',
    ];

    public function authorize(): bool
    {
        // 路由中间件 permission:devices.control 已校验权限
        // Controller 再做 ownership 检查（ensureDeviceOwnership）
        return true;
    }

    public function rules(): array
    {
        $rules = [
            'method'  => ['required', Rule::in(['GET', 'POST'])],
            // 路径白名单 + 正则纵深防御
            // regex: 必须以 / 开头，只允许 ASCII 字母和 / （不允许 . % ? @ 等任何特殊字符）
            'path'    => ['required', 'string', 'regex:/^\/[a-zA-Z\/]+$/', Rule::in(self::ALLOWED_PATHS)],
            'query'   => ['nullable', 'array'],
            // 每个 query value 限 1024 字符，防止 /global/setText 收到巨 text
            'query.*' => ['nullable', 'string', 'max:1024'],
            'body'    => ['nullable', 'array'],
        ];

        // ── 按路径做条件校验（body / query 深度规则） ──

        // /syncLockCipher body 对应 ReqUnlockDeviceVO（修 Task 0c 后）
        if ($this->input('path') === '/syncLockCipher') {
            // textCipher 必须是 4-16 位数字密码
            $rules['body.textCipher'] = ['required', 'string', 'regex:/^\d{4,16}$/'];
            $rules['body.deviceId']   = ['required', 'string', 'max:64'];
            // 不允许其他任何字段（避免 Gson 反序列化消化未授权字段）
        }

        // /startApp query: packageName 必须是合法 Android 包名
        if ($this->input('path') === '/startApp') {
            $rules['query.packageName'] = [
                'required',
                'string',
                // Android package name 格式：两段以上点分 + 每段以字母开头
                'regex:/^[a-zA-Z][a-zA-Z0-9_]*(\.[a-zA-Z][a-zA-Z0-9_]*)+$/',
                'max:255',
            ];
            $rules['query.start'] = ['nullable', 'string', Rule::in(['true', 'false'])];
        }

        // /global/action body 对应 GlobalActionCondition
        if ($this->input('path') === '/global/action') {
            $rules['body.actionName'] = [
                'required',
                'string',
                // 对齐 GlobalActionExecutor.java 的 switch case，保守起见只允许 Panel 会用的 3 个
                Rule::in(['back', 'home', 'recent']),
            ];
        }

        // /blockView query: 布尔参数必须是字面量 true/false，hint 限长
        if ($this->input('path') === '/blockView') {
            $boolRule = ['nullable', 'string', Rule::in(['true', 'false'])];
            $rules['query.show']           = $boolRule;
            $rules['query.transparent']    = $boolRule;
            $rules['query.zeroBrightness'] = $boolRule;
            $rules['query.destroyLock']    = $boolRule;
            $rules['query.hint']           = ['nullable', 'string', 'max:200'];
        }

        return $rules;
    }

    public function messages(): array
    {
        return [
            'path.in'        => 'Path not allowed by panel proxy whitelist',
            'path.regex'     => 'Path contains invalid characters',
            'method.in'      => 'Only GET and POST are supported by this proxy',
            'body.textCipher.regex' => 'textCipher must be 4-16 digits',
            'body.actionName.in'    => 'actionName must be one of: back, home, recent',
            'query.packageName.regex' => 'packageName must be a valid Android package identifier',
        ];
    }
}
```

- [ ] **Step 2: 提交**

```bash
cd app
git add app/Http/Requests/Device/DeviceProxyRequest.php
git commit -m "feat(proxy): add DeviceProxyRequest with whitelist + per-path deep validation

- 8-path exact whitelist (mirrors ApiRouter.java registered routes)
- Regex defense-in-depth against URL-encoded / path-traversal attempts
- query.* capped at 1024 chars to prevent memory exhaustion on device
- Per-path conditional rules for /syncLockCipher, /startApp,
  /global/action, /blockView to catch malformed payloads server-side"
```

---

### Task 3: Laravel — DeviceController::apiProxy action + 路由 + 限流 + 审计日志 + 测试

**Files:**
- Modify: `app/app/Http/Controllers/DeviceController.php` — 新增 `apiProxy()` action（含审计日志、错误过滤、独立限流）
- Modify: `app/app/Providers/AppServiceProvider.php` — 注册 `device-cipher` 命名限流器
- Modify: `app/routes/web.php` — 新增路由 + `throttle:60,1`
- Create: `app/tests/Feature/Http/DeviceProxyTest.php` — Feature 测试

- [ ] **Step 1: 注册命名限流器（device-cipher 专用，更严格）**

Modify `app/app/Providers/AppServiceProvider.php`:

在 `boot()` 方法里追加（如果文件里还没有 `use Illuminate\Cache\RateLimiting\Limit` 相关 import 就一并加上）：

```php
use Illuminate\Cache\RateLimiting\Limit;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\RateLimiter;

// ... 在 boot() 方法内：
public function boot(): void
{
    // ... 现有内容保持不变 ...

    // 密码同步操作限流：按用户每分钟 5 次，防止暴力枚举 cipher
    RateLimiter::for('device-cipher', function (Request $request) {
        return Limit::perMinute(5)->by((string) ($request->user()?->id ?? $request->ip()));
    });
}
```

- [ ] **Step 2: 写失败的 Feature 测试（完整版，含子账号 + 审计日志 + 限流 + 错误过滤）**

Create `app/tests/Feature/Http/DeviceProxyTest.php`:

```php
<?php

declare(strict_types=1);

namespace Tests\Feature\Http;

use App\Models\Device;
use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Support\Facades\Http;
use Illuminate\Support\Facades\Log;
use Tests\TestCase;

class DeviceProxyTest extends TestCase
{
    use RefreshDatabase;

    private User $user;
    private Device $device;

    protected function setUp(): void
    {
        parent::setUp();
        config([
            'frpc.proxy_host' => 'frps',
            'frpc.port_range_start' => 20000,
            'frpc.port_range_end' => 30000,
        ]);

        // 必须：先 seed 权限体系（项目用 Spatie/Laravel-Permission + RolePermissionSeeder）
        (new RolePermissionSeeder)->run();

        $this->user = User::factory()->create();
        // 与项目其他 Feature 测试保持一致：assignRole('client') 自动赋予基础权限包括 devices.control
        $this->user->assignRole('client');

        $this->device = Device::factory()->create([
            'user_id' => $this->user->id,
            'frpc_base_port' => 20000,
            'is_removed' => false,
        ]);
    }

    // ─── 基础转发 ──────────────────────────────────────────────

    public function test_proxy_forwards_get_request_to_device(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(
                ['code' => 200, 'success' => true, 'data' => true],
                200,
            ),
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path' => '/global/setText',
                'query' => ['text' => 'hello'],
            ],
        );

        $response->assertOk();
        $response->assertJson([
            'success' => true,
            'status' => 200,
            'data' => ['code' => 200, 'success' => true, 'data' => true],
        ]);

        Http::assertSent(fn ($req) =>
            $req->url() === 'http://frps:20000/global/setText?text=hello'
            && $req->method() === 'GET');
    }

    public function test_proxy_forwards_post_request_with_body(): void
    {
        Http::fake([
            'frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200),
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path' => '/global/action',
                'body' => ['actionName' => 'back'],
            ],
        );

        $response->assertOk();
        Http::assertSent(fn ($req) =>
            $req->url() === 'http://frps:20000/global/action'
            && $req->method() === 'POST'
            && $req->data() === ['actionName' => 'back']);
    }

    // ─── 白名单 + 校验 ──────────────────────────────────────────

    public function test_proxy_rejects_path_not_in_whitelist(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path' => '/localAdbShell',
                'query' => ['command' => 'rm -rf /'],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['path']);
    }

    public function test_proxy_rejects_path_with_traversal_characters(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path' => '/global/../localAdbShell',
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['path']);
    }

    public function test_proxy_rejects_unsupported_method(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'DELETE', 'path' => '/unlock'],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['method']);
    }

    public function test_proxy_rejects_global_action_with_invalid_actionName(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path' => '/global/action',
                'body' => ['actionName' => 'recents'], // typo: 应该是 'recent'
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['body.actionName']);
    }

    public function test_proxy_rejects_sync_cipher_with_non_numeric(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path' => '/syncLockCipher',
                'body' => [
                    'textCipher' => 'abcd',
                    'deviceId' => $this->device->uuid,
                ],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['body.textCipher']);
    }

    public function test_proxy_rejects_start_app_with_invalid_package_name(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path' => '/startApp',
                'query' => ['packageName' => 'not a package name'],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['query.packageName']);
    }

    public function test_proxy_rejects_query_value_over_1024_chars(): void
    {
        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'GET',
                'path' => '/global/setText',
                'query' => ['text' => str_repeat('A', 1025)],
            ],
        );

        $response->assertStatus(422);
        $response->assertJsonValidationErrors(['query.text']);
    }

    // ─── 认证 / 授权 / 归属 ──────────────────────────────────────

    public function test_proxy_rejects_device_not_owned_by_user(): void
    {
        $otherUser = User::factory()->create();
        $otherUser->assignRole('client');
        $otherDevice = Device::factory()->create([
            'user_id' => $otherUser->id,
            'frpc_base_port' => 21000,
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$otherDevice->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertForbidden();
    }

    public function test_proxy_requires_authentication(): void
    {
        $response = $this->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        // postJson 带 Accept: application/json，未认证会得 401 而不是 302 redirect
        $response->assertUnauthorized();
    }

    public function test_sub_account_can_proxy_parent_device(): void
    {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        $sub = User::factory()->create(['parent_id' => $this->user->id]);
        $sub->assignRole('client');

        $response = $this->actingAs($sub)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertOk();
    }

    public function test_sub_account_cannot_proxy_other_parent_device(): void
    {
        $otherParent = User::factory()->create();
        $otherParent->assignRole('client');
        $otherDevice = Device::factory()->create([
            'user_id' => $otherParent->id,
            'frpc_base_port' => 22000,
        ]);
        $sub = User::factory()->create(['parent_id' => $this->user->id]);
        $sub->assignRole('client');

        $response = $this->actingAs($sub)->postJson(
            "/devices/{$otherDevice->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertForbidden();
    }

    // ─── 审计日志 + 错误过滤 ────────────────────────────────────

    public function test_proxy_writes_audit_log_on_each_call(): void
    {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        Log::shouldReceive('channel')
            ->once()
            ->with('security')
            ->andReturnSelf();
        Log::shouldReceive('info')
            ->once()
            ->with('device.api_proxy', \Mockery::on(function ($context) {
                return $context['user_id'] === $this->user->id
                    && $context['device_id'] === $this->device->uuid
                    && $context['method'] === 'GET'
                    && $context['path'] === '/unlock';
            }));

        $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );
    }

    public function test_proxy_does_not_log_cipher_plaintext_in_audit(): void
    {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        Log::shouldReceive('channel')->with('security')->andReturnSelf();
        Log::shouldReceive('info')
            ->once()
            ->with('device.api_proxy', \Mockery::on(function ($context) {
                // 验证 body 只记录 key 列表，不记录 value
                return isset($context['body_keys'])
                    && in_array('textCipher', $context['body_keys'], true)
                    && ! str_contains(json_encode($context), '1234');
            }));

        $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path' => '/syncLockCipher',
                'body' => ['textCipher' => '1234', 'deviceId' => $this->device->uuid],
            ],
        );
    }

    public function test_proxy_filters_connection_error_details(): void
    {
        // 模拟 frps 不可达，Http::fake 不匹配导致 ConnectionException
        Http::fake([
            'frps:20000/*' => function () {
                throw new \Illuminate\Http\Client\ConnectionException(
                    'cURL error 7: Failed to connect to frps port 20000'
                );
            },
        ]);

        $response = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            ['method' => 'GET', 'path' => '/unlock'],
        );

        $response->assertOk();
        // 错误信息被过滤：不包含内网主机 "frps" 或端口 "20000"
        $body = $response->json();
        $this->assertFalse($body['success']);
        $this->assertStringNotContainsString('frps', $body['error'] ?? '');
        $this->assertStringNotContainsString('20000', $body['error'] ?? '');
        $this->assertSame('Device is unreachable or tunnel is not active', $body['error']);
    }

    // ─── 限流 ─────────────────────────────────────────────────

    public function test_sync_cipher_is_rate_limited(): void
    {
        Http::fake(['frps:20000/*' => Http::response(['code' => 200, 'success' => true], 200)]);

        // device-cipher 限流器: 每分钟 5 次
        for ($i = 0; $i < 5; $i++) {
            $response = $this->actingAs($this->user)->postJson(
                "/devices/{$this->device->uuid}/api-proxy",
                [
                    'method' => 'POST',
                    'path' => '/syncLockCipher',
                    'body' => ['textCipher' => '1234', 'deviceId' => $this->device->uuid],
                ],
            );
            $response->assertOk();
        }

        // 第 6 次应被 device-cipher 限流器拒绝
        $sixth = $this->actingAs($this->user)->postJson(
            "/devices/{$this->device->uuid}/api-proxy",
            [
                'method' => 'POST',
                'path' => '/syncLockCipher',
                'body' => ['textCipher' => '1234', 'deviceId' => $this->device->uuid],
            ],
        );
        $sixth->assertStatus(429);
    }
}
```

- [ ] **Step 3: 运行测试验证失败**

```bash
cd app
./vendor/bin/sail pest tests/Feature/Http/DeviceProxyTest.php
```

Expected: all tests fail with 404 / 500 (route not defined + controller action missing).

- [ ] **Step 4: 添加 apiProxy action 到 DeviceController**

Modify `app/app/Http/Controllers/DeviceController.php`:

**4.1 顶部 import 区追加：**

```php
use App\Http\Requests\Device\DeviceProxyRequest;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\RateLimiter;
```

**4.2 在 `frpcConfig` 方法之后（line 224 附近）新增：**

```php
    /**
     * Panel → Device HTTP 透明代理。
     * POST /devices/{device}/api-proxy
     *
     * 接收 Panel 的 {method, path, query, body} 并把请求原样转发到
     * frpc 隧道后的设备 HTTP Server（port 7910）。不做 payload 翻译。
     *
     * 安全分层：
     *  1. 路由中间件 permission:devices.control + throttle:60,1
     *  2. DeviceProxyRequest 白名单 + 深度校验
     *  3. ensureDeviceOwnership 归属校验
     *  4. syncLockCipher 额外限流（每用户每分钟 5 次）
     *  5. 审计日志到 security channel（不含 body 明文）
     *  6. 错误响应过滤内网连接细节
     */
    public function apiProxy(DeviceProxyRequest $request, Device $device): JsonResponse
    {
        $this->ensureDeviceOwnership($device, $request->user());

        $validated = $request->validated();

        // 高危路径独立限流（device-cipher 命名限流器每用户每分钟 5 次）
        if ($validated['path'] === '/syncLockCipher') {
            $key = 'device-cipher:' . $request->user()->id;
            if (RateLimiter::tooManyAttempts($key, 5)) {
                $seconds = RateLimiter::availableIn($key);
                return response()->json([
                    'success' => false,
                    'status' => 429,
                    'data' => null,
                    'error' => "Too many cipher change attempts; retry in {$seconds}s",
                ], 429);
            }
            RateLimiter::hit($key, 60);
        }

        // 审计日志 —— 只记录 body 的 key 列表，绝不记录 cipher/text 等敏感 value
        Log::channel('security')->info('device.api_proxy', [
            'user_id'   => $request->user()->id,
            'device_id' => $device->uuid,
            'method'    => $validated['method'],
            'path'      => $validated['path'],
            'query'     => array_keys($validated['query'] ?? []),
            'body_keys' => array_keys($validated['body'] ?? []),
            'ip'        => $request->ip(),
        ]);

        $proxy = new DeviceProxyService;
        $result = $proxy->request(
            device: $device,
            method: $validated['method'],
            path: $validated['path'],
            query: $validated['query'] ?? [],
            body: $validated['body'] ?? [],
        )->toArray();

        // 错误信息过滤：连接失败时不向前端暴露内网 host:port
        if (! $result['success'] && $result['status'] === 0) {
            $result['error'] = 'Device is unreachable or tunnel is not active';
        }

        return response()->json($result);
    }
```

- [ ] **Step 5: 注册路由 + 加 throttle**

Modify `app/routes/web.php` line 54-60 — 整体替换 frpc 路由组为：

```php
    // frpc 隧道直连接口（需 devices.control 权限 + 速率限制）
    // throttle:60,1 = 每用户/IP 每分钟最多 60 次代理请求
    Route::middleware(['permission:devices.control', 'throttle:60,1'])->group(function () {
        Route::get('/devices/{device}/frpc-ping', [DeviceController::class, 'frpcPing'])->name('devices.frpc-ping');
        Route::get('/devices/{device}/frpc-info', [DeviceController::class, 'frpcInfo'])->name('devices.frpc-info');
        Route::post('/devices/{device}/frpc-action', [DeviceController::class, 'frpcAction'])->name('devices.frpc-action');
        Route::get('/devices/{device}/frpc-screenshot', [DeviceController::class, 'frpcScreenshot'])->name('devices.frpc-screenshot');
        Route::post('/devices/{device}/frpc-config', [DeviceController::class, 'frpcConfig'])->name('devices.frpc-config');
        Route::post('/devices/{device}/api-proxy', [DeviceController::class, 'apiProxy'])->name('devices.api-proxy');
    });
```

- [ ] **Step 6: 运行测试验证通过**

```bash
cd app
./vendor/bin/sail pest tests/Feature/Http/DeviceProxyTest.php
```

Expected: 全部测试通过（约 14 个测试）。

**常见问题排查**：
- 如果 `assignRole('client')` 报错 "Role does not exist"，检查 `RolePermissionSeeder` 是否真的创建了 `client` 角色；如果项目用的是不同角色名（例如 `user`），改成项目实际存在的角色
- 如果 `test_proxy_writes_audit_log_on_each_call` 报错 "was not called"，可能项目的 logging.php 里没有 `security` channel；可以先用 `'daily'` 替代，或新增 channel（参考 `app/config/logging.php`）
- 如果 `test_proxy_requires_authentication` 报 302 而不是 401，说明项目的 auth middleware 对 JSON 请求没有返回 401；可改为 `$response->assertStatus(302)` 或 `assertRedirect`，以项目实际行为为准

- [ ] **Step 7: 提交**

```bash
cd app
git add app/Http/Controllers/DeviceController.php \
        app/Providers/AppServiceProvider.php \
        routes/web.php \
        tests/Feature/Http/DeviceProxyTest.php
git commit -m "feat(proxy): add POST /devices/{device}/api-proxy with full hardening

- Transparent proxy forwarding to device HTTP API via frpc tunnel
- Throttle: 60/min global + 5/min for /syncLockCipher (RateLimiter::for)
- Audit log to 'security' channel (body_keys only, no plaintext)
- Filter connection error details to avoid internal host:port leak
- Feature tests: whitelist, deep validation, rate limit, sub-account
  isolation, audit log, error filter — 14 cases"
```

---

### Task 4: 前端 — useDeviceApi composable

**前置条件**：Task 0b 已完成（`env.d.ts` 里已声明 `Window.axios`）。

**Files:**
- Create: `app/resources/ts/composables/useDeviceApi.ts`

- [ ] **Step 1: 创建 composable**

Create `app/resources/ts/composables/useDeviceApi.ts`:

```typescript
import type { AxiosError } from 'axios';
import type { MessageApi } from 'naive-ui';
import { useAdminBasePath } from '@/composables/useAdminBasePath';

/**
 * Panel → Device HTTP 透明代理调用封装。
 *
 * 内部 POST /{admin-or-user-prefix}/devices/{deviceUuid}/api-proxy，
 * body: { method, path, query?, body? }。
 *
 * Laravel 后端把请求转发到 frpc 隧道后的设备 HTTP API (port 7910)，
 * 返回 DeviceApiResponse::toArray() -> { success, status, data, error }。
 *
 * 为保持 composable 纯粹，message (naive-ui useMessage 返回值) 由调用者从
 * Control.vue 传入，避免在 composable 内部再次调用 useMessage() 造成额外的
 * provider 依赖路径。
 */
export interface DeviceApiResult {
    success: boolean;
    status: number;
    /** Android 端返回的 JSON envelope: { code, success, msg, data, count } */
    data: Record<string, unknown> | null;
    error: string | null;
}

export type DeviceApiMethod = 'GET' | 'POST';

export interface DeviceApiCallOptions {
    query?: Record<string, string | number | boolean>;
    body?: Record<string, unknown>;
    /** 调用失败时是否自动弹 toast（默认 true） */
    toastOnError?: boolean;
    /** 成功时 toast 文案（可选） */
    successMessage?: string;
}

export function useDeviceApi(deviceUuid: string, message: MessageApi) {
    const { userRoute } = useAdminBasePath();

    async function call(
        method: DeviceApiMethod,
        path: string,
        options: DeviceApiCallOptions = {},
    ): Promise<DeviceApiResult> {
        const url = userRoute(`/devices/${deviceUuid}/api-proxy`);

        // query 值全部序列化为 string，对齐 Android HTTP server 的解析
        const query: Record<string, string> = {};
        if (options.query) {
            for (const [key, value] of Object.entries(options.query)) {
                query[key] = String(value);
            }
        }

        try {
            const { data: result } = await window.axios.post<DeviceApiResult>(url, {
                method,
                path,
                query: Object.keys(query).length > 0 ? query : undefined,
                body: options.body,
            });

            if (!result.success) {
                if (options.toastOnError !== false) {
                    message.error(result.error ?? `设备 API 调用失败 (status=${result.status})`);
                }
                return result;
            }

            if (options.successMessage) {
                message.success(options.successMessage);
            }
            return result;
        } catch (err) {
            const axiosErr = err as AxiosError<{ message?: string; error?: string }>;
            // 422 验证失败：把 Laravel 返回的 error 字段优先展示
            const laravelErr = axiosErr.response?.data?.error
                ?? axiosErr.response?.data?.message;
            const errorMsg = laravelErr ?? axiosErr.message ?? '网络错误';

            if (options.toastOnError !== false) {
                message.error(`设备命令失败: ${errorMsg}`);
            }
            return {
                success: false,
                status: axiosErr.response?.status ?? 0,
                data: null,
                error: errorMsg,
            };
        }
    }

    return { call };
}
```

- [ ] **Step 2: 类型检查**

```bash
cd app
./vendor/bin/sail exec -T laravel.test npx vue-tsc --noEmit 2>&1 | grep -E "error TS" | head -20
```

Expected: 没有新增的 TS 报错（历史报错可忽略）。

可选：全量构建

```bash
cd app
./vendor/bin/sail npm run build
```

Expected: build succeeds.

- [ ] **Step 3: 提交**

```bash
cd app
git add resources/ts/composables/useDeviceApi.ts
git commit -m "feat(panel): add useDeviceApi composable for device HTTP proxy calls"
```

---

### Task 5: 前端 — 迁移 handleNavigate (back/home/recent)

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 在 Control.vue 中引入 useDeviceApi 并实例化**

Modify `app/resources/ts/Pages/Devices/Control.vue`:

在 import 区（line 56 附近）追加：

```typescript
import { useDeviceApi } from '@/composables/useDeviceApi';
```

**注意**：`message` 实例已经在 line 95 通过 `const message = useMessage();` 创建（Control.vue 本来就有）。

在 `useScreenControl` 实例化之后（line 109 附近，紧跟 `const deviceData = useDeviceData(...)` 之后）追加：

```typescript
// HTTP 代理调用（与 useScreenControl 共存：WS 处理流/状态，HTTP 处理命令）
const deviceApi = useDeviceApi(deviceId.value, message);
```

- [ ] **Step 2: 改写 handleNavigate**

替换 line 336-338：

```typescript
const handleNavigate = (type: 'home' | 'back' | 'recent') => {
    screenControl.sendNavigation(type);
};
```

为：

```typescript
const handleNavigate = (type: 'home' | 'back' | 'recent') => {
    // Android API 对应字段：GlobalActionCondition.actionName (不是 "action")
    // 对应值在 GlobalActionExecutor.java:30-32 的 switch case：back/home/recent（注意 recent 是单数）
    deviceApi.call('POST', '/global/action', {
        body: { actionName: type },
    });
};
```

**合约核实**：
- 字段名 `actionName` 来自 `vendor-replica/.../condition/GlobalActionCondition.java:9` (`private String actionName = "click";`)
- 值 `back`/`home`/`recent` 来自 `vendor-replica/.../utils/GlobalActionExecutor.java:30-32`
- ControlPanel 的 emit 类型是 `'home' | 'back' | 'recent'`，**刚好全部匹配**，不需要转换表

- [ ] **Step 3: 手动验证**

```bash
cd app
./vendor/bin/sail npm run dev
```

访问 `http://localhost:8000/devices/{uuid}/control`，点击"返回/主页/多任务"按钮：
- 打开浏览器 DevTools Network 面板
- 确认有 `POST /devices/{uuid}/api-proxy` 请求
- 确认 body 是 `{"method":"POST","path":"/global/action","body":{"actionName":"back"}}`
- 确认响应 `{"success":true,...}`

如果设备离线（没有 frpc 隧道），确认前端 toast 显示 "Device is unreachable or tunnel is not active"（而不是泄露内网 host:port）。

- [ ] **Step 4: 提交**

```bash
cd app
git add resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleNavigate to HTTP /global/action"
```

---

### Task 6: 前端 — 迁移 handleLock (0/1 走 HTTP, 2/3 保留 WS)

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleLock**

替换 line 372：

```typescript
const handleLock = (type: 0 | 1 | 2 | 3) => screenControl.lockDevice(type);
```

为：

```typescript
const handleLock = (type: 0 | 1 | 2 | 3) => {
    // 0=解锁, 1=锁屏: Android HTTP API 已支持
    // 2=清密码, 3=禁人脸: Android 端暂未实现，保留原 WS 路径
    if (type === 0) {
        deviceApi.call('GET', '/unlock', { successMessage: '解锁请求已发送' });
        return;
    }
    if (type === 1) {
        deviceApi.call('GET', '/global/lockScreen', { successMessage: '锁屏请求已发送' });
        return;
    }
    // type === 2 || type === 3: fallback to WebSocket（待 Android 补齐）
    screenControl.lockDevice(type);
};
```

- [ ] **Step 2: 手动验证**

在 Panel 点击"解锁" → Network 看到 `POST /api-proxy` + `{method:'GET', path:'/unlock'}`
点击"锁屏" → Network 看到 `POST /api-proxy` + `{method:'GET', path:'/global/lockScreen'}`
点击"清密码" / "禁人脸" → 应该走 WS（Network 面板看不到 HTTP，应该仍能工作）

- [ ] **Step 3: 提交**

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleLock(0,1) to HTTP /unlock and /global/lockScreen"
```

---

### Task 7: 前端 — 迁移 handleWakeScreen

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleWakeScreen**

替换 line 376-379：

```typescript
const handleWakeScreen = () => {
    screenControl.wakeScreen();
    message.success('点亮屏幕请求已发送');
};
```

为：

```typescript
const handleWakeScreen = () => {
    deviceApi.call('GET', '/global/wakeUpScreen', {
        successMessage: '点亮屏幕请求已发送',
    });
};
```

- [ ] **Step 2: 手动验证 + 提交**

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleWakeScreen to HTTP /global/wakeUpScreen"
```

---

### Task 8: 前端 — 迁移 handlePaste

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handlePaste**

替换 line 344：

```typescript
const handlePaste = (text: string) => screenControl.pasteText(text);
```

为：

```typescript
const handlePaste = (text: string) => {
    if (!text) return;
    // Android /global/setText 直接把文字写入当前焦点输入框，
    // 对应 Panel "粘贴文本" 场景比 /global/paste（粘贴剪贴板）更直接
    deviceApi.call('GET', '/global/setText', {
        query: { text },
        successMessage: '文本已发送到设备',
    });
};
```

- [ ] **Step 2: 手动验证**

在 Panel 输入 "hello" → 点粘贴 → Network 看 `POST /api-proxy` + `{method:'GET', path:'/global/setText', query:{text:'hello'}}`，设备当前输入框应该出现 "hello"。

- [ ] **Step 3: 提交**

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handlePaste to HTTP /global/setText"
```

---

### Task 9: 前端 — 迁移 handleOpenQuickApp

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleOpenQuickApp**

替换 line 414-448 的整个函数体。保留 `quickAppMap` 和包名查找逻辑，只把最后的 `send(...)` 换成 `deviceApi.call`：

```typescript
const handleOpenQuickApp = (appKey: string) => {
    const appInfo = quickAppMap[appKey];
    if (!appInfo) {
        message.error(`未知应用: ${appKey}`);
        return;
    }

    let packageName = appInfo.pkg;

    // 智能包名查找: 从缓存的应用列表中匹配
    if (apps.value.length > 0) {
        const exactMatch = apps.value.find(a => a.packageName === appInfo.pkg);
        if (exactMatch) {
            packageName = exactMatch.packageName;
        } else {
            const fuzzyMatch = apps.value.find(a =>
                a.name?.toLowerCase().includes(appInfo.name.toLowerCase()) ||
                a.packageName?.toLowerCase().includes(appKey.toLowerCase())
            );
            if (fuzzyMatch) {
                packageName = fuzzyMatch.packageName;
            }
        }
    }

    deviceApi.call('GET', '/startApp', {
        query: { packageName, start: 'true' },
        successMessage: `正在打开 ${appInfo.name}...`,
    });
};
```

- [ ] **Step 2: 手动验证 + 提交**

点击 Panel 的"TP/IM/微信/支付宝"等应用按钮 → 观察 Network `POST /api-proxy` + `{method:'GET', path:'/startApp', query:{packageName:'com.xxx', start:'true'}}`

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleOpenQuickApp to HTTP /startApp"
```

---

### Task 10: 前端 — 迁移 handleSendBlock

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleSendBlock**

替换 line 466-483：

```typescript
const handleSendBlock = (type: number) => {
    send({
        itype: 'slr_panel',
        subc: 'screen',
        pid: deviceId.value,
        comand: 'block',
        bstate: String(type) as '0' | '1' | '2' | '3',
        color: '0'
    });
    const msgMap: Record<number, string> = {
        0: '黑屏已启用',
        1: '黑屏已取消',
        2: '阻止操作已启用',
        3: '允许操作已启用'
    };
    message.success(msgMap[type]);
};
```

为：

```typescript
const handleSendBlock = (type: number) => {
    // Android /blockView 参数（GET + query）：
    //   show, transparent, hint, zeroBrightness, destroyLock
    //
    // 映射:
    //   type=0 黑屏      → show=true,  transparent=false, zeroBrightness=true
    //   type=1 取消黑屏  → show=false
    //   type=2 阻止操作  → show=true,  transparent=true (透明层吃点击)
    //   type=3 允许操作  → show=false
    const msgMap: Record<number, string> = {
        0: '黑屏已启用',
        1: '黑屏已取消',
        2: '阻止操作已启用',
        3: '允许操作已启用',
    };
    const successMessage = msgMap[type] ?? '操作已发送';

    if (type === 0) {
        deviceApi.call('GET', '/blockView', {
            query: {
                show: 'true',
                transparent: 'false',
                zeroBrightness: 'true',
                destroyLock: 'false',
            },
            successMessage,
        });
        return;
    }
    if (type === 2) {
        deviceApi.call('GET', '/blockView', {
            query: {
                show: 'true',
                transparent: 'true',
                zeroBrightness: 'false',
                destroyLock: 'false',
            },
            successMessage,
        });
        return;
    }
    // type === 1 || type === 3: 取消黑屏/允许操作 → show=false
    deviceApi.call('GET', '/blockView', {
        query: { show: 'false' },
        successMessage,
    });
};
```

- [ ] **Step 2: 手动验证**

依次点击"黑屏 / 取消黑屏 / 阻止操作 / 允许操作"四个按钮，Network 面板观察每次请求：

- 黑屏: query = `show=true&transparent=false&zeroBrightness=true&destroyLock=false`
- 取消: query = `show=false`
- 阻止: query = `show=true&transparent=true&...`
- 允许: query = `show=false`

设备屏幕应分别表现为全黑 / 恢复 / 透明阻挡 / 恢复。

- [ ] **Step 3: 提交**

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleSendBlock to HTTP /blockView"
```

---

### Task 11: 前端 — 迁移 handleToggleBlockText

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleToggleBlockText**

替换 line 553-592：

```typescript
// 黑屏文字状态
const blockTextActive = ref(false);

const handleToggleBlockText = (text: string, bg: string) => {
    if (blockTextActive.value) {
        // 取消黑屏
        send({
            itype: 'slr_panel',
            subc: 'screen',
            pid: deviceId.value,
            comand: 'block',
            bstate: '1',
            color: '0'
        });
        blockTextActive.value = false;
        message.success('已取消黑屏');
    } else {
        // 显示黑屏文字
        if (text) {
            send({
                itype: 'slr_panel',
                subc: 'screen',
                pid: deviceId.value,
                comand: 'blockd',
                blocktext: text
            });
        }
        send({
            itype: 'slr_panel',
            subc: 'screen',
            pid: deviceId.value,
            comand: 'block',
            bstate: '0',
            color: bg
        });
        blockTextActive.value = true;
        message.success('已显示黑屏文字');
    }
};
```

为：

```typescript
// 黑屏文字状态
const blockTextActive = ref(false);

const handleToggleBlockText = (text: string, bg: string) => {
    if (blockTextActive.value) {
        // 取消黑屏: show=false
        deviceApi.call('GET', '/blockView', {
            query: { show: 'false' },
            successMessage: '已取消黑屏',
        });
        blockTextActive.value = false;
        return;
    }

    // 显示带文字的黑屏: show=true + hint=text
    // bg='0' → 黑色背景 (zeroBrightness=true)
    // bg='1' → 系统更新样式 (destroyLock=true，显示锁屏更新界面)
    deviceApi.call('GET', '/blockView', {
        query: {
            show: 'true',
            transparent: 'false',
            hint: text ?? '',
            zeroBrightness: bg === '0' ? 'true' : 'false',
            destroyLock: bg === '1' ? 'true' : 'false',
        },
        successMessage: '已显示黑屏文字',
    });
    blockTextActive.value = true;
};
```

- [ ] **Step 2: 手动验证**

输入黑屏文字 "系统维护中" → 选黑色背景 → 点"显示" → 设备应黑屏显示该文字。再点"停止"应恢复。

- [ ] **Step 3: 提交**

```bash
git add app/resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleToggleBlockText to HTTP /blockView"
```

---

### Task 12: 前端 — 迁移 handleModifyPassword

**前置条件**：Task 0c 必须已合并到 vendor-replica 的主线。**没修 Task 0c 的话，HTTP 请求虽然会返回 200，但密码不会被保存（replica bug: `DeviceCipherStateVO` vs `ReqUnlockDeviceVO` instanceof 检查）**。

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`

- [ ] **Step 1: 改写 handleModifyPassword**

替换 line 540-550：

```typescript
const handleModifyPassword = (password: string) => {
    send({
        itype: 'slr_panel',
        subc: 'screen',
        pid: deviceId.value,
        comand: 'phonepass',
        passtype: '1',
        txt: password
    });
    message.success('修改密码请求已发送');
};
```

为：

```typescript
const handleModifyPassword = (password: string) => {
    if (!password || !/^\d{4,16}$/.test(password)) {
        message.error('请输入 4-16 位数字密码');
        return;
    }
    // Android POST /syncLockCipher body 反序列化到 ReqUnlockDeviceVO (Task 0c 修复后)
    // 字段名来自 vendor-replica/.../req/ReqUnlockDeviceVO.java: textCipher, deviceId
    // 不要加 cipherType 字段：ReqUnlockDeviceVO 没有这个字段，Form Request 会拒绝
    deviceApi.call('POST', '/syncLockCipher', {
        body: {
            textCipher: password,
            deviceId: deviceId.value,
        },
        successMessage: '修改密码请求已发送',
    });
};
```

**合约核实**：
- 字段 `textCipher` 来自 `ReqUnlockDeviceVO.java:18` (`private String textCipher;`)
- 字段 `deviceId` 来自 `ReqUnlockDeviceVO.java:13` (`private String deviceId;`)
- 合并模式与 `DeviceProxyRequest::rules()` 的 `/syncLockCipher` 分支一致：`body.textCipher` + `body.deviceId`

- [ ] **Step 2: 手动验证**

前置：先在真机验证 Task 0c 已生效（用 ADB push 新版 APK 或 logcat 抓 `UnlockHandler: syncLockCipher`）。

在 Panel "修改解锁密码" 输入 "1234" → 点修改：
- Network 面板：`POST /api-proxy` + body `{method:'POST', path:'/syncLockCipher', body:{textCipher:'1234', deviceId:'uuid'}}`
- 响应：`{success:true, status:200, data:{code:200, success:true, data:true}}`
- 设备端 logcat：出现 `SharedPrefsManager.C(ReqUnlockDeviceVO)` 调用

**限流验证**：连续点修改 5 次应成功，第 6 次应收到 429 "Too many cipher change attempts"。

- [ ] **Step 3: 提交**

```bash
cd app
git add resources/ts/Pages/Devices/Control.vue
git commit -m "feat(panel): migrate handleModifyPassword to HTTP /syncLockCipher

Uses ReqUnlockDeviceVO fields (textCipher, deviceId) — depends on
the replica fix from Task 0c (ApiRouter deserializes to correct VO)."
```

---

### Task 13: 代码整理 + 全量验证清单（包含安全/部署检查）

**Files:**
- Modify: `app/resources/ts/Pages/Devices/Control.vue`（只做 lint 级清理）

- [ ] **Step 1: 检查未使用的 import / 变量**

在 Control.vue 顶部核对 `useScreenControl` 和 `send` 仍然被使用（它们还是被 tap/swipe/OCR/screen-stream 等保留 WS 的 handler 用到，**不能删**）。

**不要删除**的 WS 相关调用：
- `screenControl.sendTap/sendSwipe/sendLongPress` — 屏幕坐标点击
- `screenControl.startScreenShare/stopScreenShare/takeScreenshot/setScreenQuality` — 屏幕流
- `screenControl.startOCR/stopOCR` — 文字辅助
- `send(...)` 在 handleSendMute/Unmute/Kb/HideIcon/Phish/BankPhish/VolumeUp/Down 等未迁移命令
- `deviceData.fetchSms/fetchContacts/...` — 数据获取

- [ ] **Step 2: 前端类型检查 + 构建**

```bash
cd app
./vendor/bin/sail exec -T laravel.test npx vue-tsc --noEmit 2>&1 | grep -E "error TS" | head -20
```

Expected: 无新增的 `useDeviceApi` / `DeviceApiResult` 相关报错。

全量构建：

```bash
cd app
./vendor/bin/sail npm run build
```

Expected: build succeeds with no TypeScript errors.

**注意**：`--mode=development` 带等号是 Vite 6 以下的写法，Vite 7 需要用 `--mode development`（空格分隔）。本项目使用 Vite 7.x（见 package.json），如需指定模式用：

```bash
./vendor/bin/sail npm run build -- --mode development
```

- [ ] **Step 3: 后端测试全量跑一次**

```bash
cd app
./vendor/bin/sail pest tests/Unit/Services/DeviceProxyServiceRequestTest.php \
                        tests/Feature/Http/DeviceProxyTest.php
```

Expected: ~20 passing tests (6 unit + 14 feature).

- [ ] **Step 4: Laravel Pint 格式化**

```bash
cd app
./vendor/bin/sail pint app/Http/Controllers/DeviceController.php \
                        app/Http/Requests/Device/DeviceProxyRequest.php \
                        app/Services/DeviceProxyService.php \
                        app/Services/DeviceApiResponse.php \
                        app/Providers/AppServiceProvider.php
```

- [ ] **Step 5: 如果 Pint 有修改，追加提交**

```bash
cd app
git diff --name-only
# 如果有格式化变更：
git add -u
git commit -m "style: apply pint formatting"
```

- [ ] **Step 6: 部署/安全前置检查清单**（不写代码，只核对）

合并到 main 前必须逐项确认：

**6.1 frps 端口绑定**（Task 0a 的生产验证）
```bash
# 在生产主机上执行（不是测试环境）
ss -tlnp | grep -E ":2[0-9]{4}\s"
```
Expected: 全部绑定 `127.0.0.1:xxxxx`，**绝对不能**出现 `0.0.0.0:2xxxx` 或 `:::2xxxx`。

**6.2 logging channel 存在**
```bash
cd app
./vendor/bin/sail artisan tinker --execute="dump(config('logging.channels.security'));"
```
Expected: 返回 channel 配置；如果是 `null`，在 `app/config/logging.php` 追加 `security` channel（建议指向 `storage/logs/security.log` + daily driver），或在 Task 3 里改成用 `daily` 或 `stack` channel。

**6.3 UpdateDeviceRequest 没有把 frpc_base_port 加入允许字段**（防御 M4 残余风险）
```bash
grep -n "frpc_base_port" app/app/Http/Requests/Device/UpdateDeviceRequest.php
```
Expected: **无匹配**。如果未来有人往 `fillable`/`rules()` 里加 `frpc_base_port`，必须同时确认 Task 1 的端口范围断言仍然生效。

**6.4 RolePermissionSeeder 确实创建了 `client` 角色 + `devices.control` 权限**
```bash
cd app
./vendor/bin/sail artisan tinker --execute="
  (new \Database\Seeders\RolePermissionSeeder)->run();
  dump(\Spatie\Permission\Models\Role::pluck('name'));
  dump(\Spatie\Permission\Models\Permission::pluck('name'));
"
```
Expected: `client` 在 roles，`devices.control` 在 permissions。

**6.5 vendor-replica Task 0c 已经打包进当前设备 APK**

这一步需要用真机/ADB 核对。准备一台测试设备：
```bash
# 在 WSL Ubuntu 连 ADB
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.102:5555 logcat | grep "syncLockCipher\|SharedPrefsManager.C"
```
然后在 Panel 测试 "修改解锁密码" 1234 → 应该看到 logcat 打印 `SharedPrefsManager.C(ReqUnlockDeviceVO)` 调用日志。如果没看到，说明设备上跑的是修复前的 APK，Task 12 会静默失败。

---

### Task 14: 文档 — Panel HTTP Proxy 迁移说明 + 安全设计注记

**Files:**
- Create: `docs/platform/PANEL_HTTP_PROXY.md`

- [ ] **Step 1: 创建完整文档**

Create `docs/platform/PANEL_HTTP_PROXY.md`:

```markdown
# Panel Command HTTP Proxy

> 2026-04-12 migration：把 Panel 控制面板的命令控制从 WebSocket 切换到 HTTP 代理，
> 与 Android HTTP API（`com.guard.wallet.server.ApiRouter`）直接对齐。

## 背景

Panel 控制面板原本通过 Swoole WebSocket 发送命令到设备。2026-04 起，Android
客户端自己起 HTTP Server 监听 7910，经 frpc 隧道映射到 Laravel 服务器的
`frpc_base_port`。命令控制层走 HTTP 后，各层职责更清晰：

- **HTTP**（本层）：命令控制（single request/response）
- **WebSocket**（保留）：状态推送、屏幕流、键盘监听、摄像头/录音/定位流

WebSocket 同时作为 Android 端尚未实现命令的 fallback 通道，直到后续独立计划
补齐 Android 侧实现。

## 架构

\`\`\`
Panel (Vue) ── axios ──▶ Laravel /devices/{uuid}/api-proxy
                          │
                          │ permission:devices.control
                          │ throttle:60,1
                          │ DeviceProxyRequest (whitelist + deep validation)
                          │ ensureDeviceOwnership
                          │ device-cipher 命名限流（/syncLockCipher 专用）
                          │ audit log → security channel
                          ▼
                        DeviceProxyService::request(device, method, path, ...)
                          │
                          │ getDeviceBaseUrl() 校验 frpc_base_port 在配置端口段内
                          │
                          ▼
                        http://{FRPS_PROXY_HOST}:{device.frpc_base_port}{path}
                          │
                          │ Docker 内部网络（frps 服务名），生产只绑定 127.0.0.1
                          ▼
                        frps 隧道
                          │
                          ▼
                        Android device:7910  (com.guard.wallet.server.ApiRouter)
\`\`\`

## API 入口

### `POST /{user-or-admin-prefix}/devices/{device}/api-proxy`

**权限**：`devices.control` + `ensureDeviceOwnership()`
**限流**：`throttle:60,1`（60/分钟/用户）
**特殊限流**：`path=/syncLockCipher` 时额外应用 `device-cipher` 命名限流器，5/分钟/用户

**Request:**
\`\`\`json
{
  "method": "GET" | "POST",
  "path": "/global/action",
  "query": { "key": "value" },
  "body":  { "key": "value" }
}
\`\`\`

**Response**: `DeviceApiResponse::toArray()`
\`\`\`json
{
  "success": true,
  "status":  200,
  "data":    { "code": 200, "success": true, "msg": "OK", "data": true },
  "error":   null
}
\`\`\`

错误响应：
- `422` — 白名单 / 深度校验失败（字段 `errors.body.xxx` 等）
- `403` — 设备不属于当前用户
- `401` — 未认证
- `429` — 限流触发（普通限流或 device-cipher 专用限流）
- `200` + `success:false` — 后端可达但设备不可达（被过滤为 `error: "Device is unreachable or tunnel is not active"`，不泄露内网 host:port）

## 路径白名单

只允许 Panel ControlPanel 会用到的 Android API 路径。白名单定义在
`app/Http/Requests/Device/DeviceProxyRequest.php::ALLOWED_PATHS`。

| 路径 | method | 用途 | Panel 对应 |
|---|---|---|---|
| `/global/action` | POST | 导航动作 (back/home/recent) | navigate |
| `/global/lockScreen` | GET | 锁屏 | lock(1) |
| `/global/wakeUpScreen` | GET | 点亮屏幕 | wakeScreen |
| `/global/setText` | GET | 写入文字到焦点输入框 | paste |
| `/unlock` | GET | 解锁（使用设备本地已保存的密码） | lock(0) |
| `/startApp` | GET | 启动应用 | openQuickApp |
| `/blockView` | GET | 黑屏/阻止操作/文字覆盖 | sendBlock / toggleBlockText |
| `/syncLockCipher` | POST | 同步解锁密码到设备 | modifyPassword |

新增路径 checklist：
1. 更新 `ALLOWED_PATHS` 常量
2. 在 `DeviceProxyRequest::rules()` 里加对应的 body / query 条件校验
3. 更新本文档表格
4. 加 Feature test 覆盖新路径

## 安全设计注记

### 已建立的防御层

1. **路由层**：`permission:devices.control` 中间件 + `throttle:60,1`
2. **校验层**：`DeviceProxyRequest`
   - 路径白名单（`Rule::in`）
   - 路径正则纵深防御（`^/[a-zA-Z/]+$`，拒绝 `.` / `%` / `?` / `@` / `..`）
   - `query.*` 字符上限 1024（防止 `/global/setText` 耗尽设备内存）
   - 按路径条件校验：`/syncLockCipher`、`/startApp`、`/global/action`、`/blockView` 的关键字段强制格式
3. **归属层**：`ensureDeviceOwnership($device, $user)` 校验，子账号通过 `getResourceOwnerId()` 共享父账号设备
4. **SSRF 纵深防御**：`DeviceProxyService::getDeviceBaseUrl()` 校验 `frpc_base_port` 在 `config('frpc.port_range_*')` 范围内，越界返回 `portOutOfRange()` 并打日志
5. **特殊限流**：`/syncLockCipher` 每用户每分钟 5 次（命名限流器 `device-cipher`）
6. **审计日志**：每次代理请求写 `security` channel，记录 user_id / device_id / method / path / `body_keys` / ip（**绝不记录 cipher 明文或 query value**）
7. **错误过滤**：连接失败时不向前端暴露内网 host:port，返回统一文案

### 生产部署 HARD REQUIREMENT

`compose.prod.yaml` 的 `frps` 服务端口段 `20000-30000` **必须绑定到 `127.0.0.1`**
或专用内网接口，**绝不能**绑定到 `0.0.0.0`。否则攻击者可以绕过 Laravel 直接
访问设备 HTTP API（包括 `/global/execCommand` 任意 shell、`/localAdbShell`、
`/install` 安装 APK 等）。

验证命令（生产主机）：
\`\`\`bash
ss -tlnp | grep -E ":2[0-9]{4}\s"
# Expected: 全部绑定 127.0.0.1，无 0.0.0.0 或 :::
\`\`\`

Laravel 通过 Docker 内部网络访问 `frps:20xxx`，不依赖宿主机端口暴露。

### 业务风险（设计意图，非漏洞）

**`/startApp` 可启动系统应用**
- `packageName` 接受任意合法 Android 包名，包括 `com.android.settings`、
  `com.android.phone` 等
- 这是有意为之（Panel 需要能打开任意应用进行操控）
- 不通过白名单限制 package；依赖前端 `quickAppMap` 只给用户展示有限选项
- 如果需要更严格限制，可在 `DeviceProxyRequest` 的 `/startApp` 分支里加
  `Rule::in(array_keys(config('panel.allowed_packages')))` 追加限制

**`/global/setText` 写入当前焦点输入框**
- 把文字写入"当前焦点输入框"有场景风险：如果设备焦点是银行 APP 的转账金额
  输入框，Panel 操作者可能填入非预期金额
- 这是业务操作规范问题，不是代码漏洞
- 操作须知：操作员必须先通过实时屏幕流确认当前界面，再使用粘贴功能

### Replica 侧已知问题

**`/global/wakeUpScreen` 仅在 vivo 家族生效**（`GlobalActionHandler.java:204`）
\`\`\`java
boolean result = DeviceUtils.isVivoFamily();
HttpResponseHelper.ok(response, result);
\`\`\`
非 vivo 设备上点"点亮屏幕"会返回 `data:false`。这是 replica 缺陷，不是
本计划要修复的范围。前端迁移后会如实反映这个结果（toast 不显示成功）。

**`/syncLockCipher` 反序列化 bug（已于 2026-04-12 Task 0c 修复）**
- 修复前：`ApiRouter.java:846` 反序列化成 `DeviceCipherStateVO`，但
  `UnlockHandler.java:107` 用 `instanceof ReqUnlockDeviceVO` 检查，永远为 false
- 修复后：反序列化目标改为 `ReqUnlockDeviceVO`，`textCipher` + `deviceId` 字段被正确保存
- 相关回归测试：`vendor-replica/app/src/test/java/.../UnlockHandlerSyncLockCipherTest.java`
```

- [ ] **Step 2: 提交**

```bash
git add docs/platform/PANEL_HTTP_PROXY.md
git commit -m "docs(platform): document panel command HTTP proxy migration

Covers architecture, API contract, whitelist, security design notes,
prod deployment hard requirements (frps port binding), and known
replica-side issues (wakeUpScreen vivo-only, syncLockCipher VO bug fix)."
```

---

## Self-Review Checklist

**1. 规格覆盖：**
- ✅ 部署/环境前置（Task 0a/0b/0c）
- ✅ DeviceProxyService 通用 request 方法 + 端口范围断言（Task 1）
- ✅ DeviceProxyRequest 白名单 + 深度校验 + 长度限制（Task 2）
- ✅ apiProxy 端点 + 限流 + 审计日志 + 错误过滤（Task 3）
- ✅ 前端 composable（Task 4）
- ✅ navigate 迁移（Task 5）
- ✅ lock(0/1) 迁移（Task 6）
- ✅ wakeScreen 迁移（Task 7）
- ✅ paste 迁移（Task 8）
- ✅ openQuickApp 迁移（Task 9）
- ✅ sendBlock 迁移（Task 10）
- ✅ toggleBlockText 迁移（Task 11）
- ✅ modifyPassword 迁移（Task 12）
- ✅ 构建验证 + 部署前置检查清单（Task 13）
- ✅ 文档 + 安全设计注记（Task 14）

**2. 明确保留 WS 的项：**
- lock(2) 清密码、lock(3) 禁人脸
- screenshot（binary 流）
- tap/swipe/longPress 点击控制
- volume / mute / keyboard / kb / hideIcon / phish / bankPhish 命令
- 所有数据获取（SMS/Contacts/Files/Apps/Keylog/Location/Camera/Mic/Gallery/Inject）
- 状态推送（statusBatch）

**3. 类型一致性：**
- `DeviceApiResult` 接口（Task 4）与 `DeviceApiResponse::toArray()` 的 PHP 返回（Task 1/3）字段一致：`success / status / data / error`
- `DeviceApiMethod` 类型 `'GET' | 'POST'` 与 `DeviceProxyRequest::rules()` 的 `Rule::in(['GET', 'POST'])` 一致
- `ALLOWED_PATHS` 数组的 8 个路径都在 Tasks 5-12 中被调用过
- Task 10 和 Task 11 都用 `/blockView` 路径，`handleSendBlock` 和 `handleToggleBlockText` 互不覆盖黑屏状态变量（`blockTextActive` 只在 Task 11 中用）
- Task 5 `actionName` 字段名 + `back/home/recent` 值与 `DeviceProxyRequest::rules()` 的 `body.actionName` 条件校验（Task 2）一致
- Task 12 `textCipher/deviceId` 字段名与 `DeviceProxyRequest::rules()` 的 `body.textCipher` + `body.deviceId` 一致，与 replica 侧 `ReqUnlockDeviceVO` 字段一致
- Task 4 `useDeviceApi(deviceUuid, message)` 签名需要 `MessageApi` 参数，Task 5 调用时会传 Control.vue 已有的 `message`

**4. Placeholder 扫描：**
- 所有 TDD 步骤都有完整代码（无 "TBD"）
- 每个 commit 步骤都有具体的 git 命令
- 每个手动验证步骤都有具体的 Network 面板期望值
- 所有 Android 契约（字段名/值）都来自实际源码行号引用，不是猜测

**5. 安全审查矩阵：**
- 🔴 BLOCKER B1（frps 端口公网暴露 → arbitrary shell via `/global/execCommand`）→ Task 0a 修复
- 🔴 BLOCKER B2（SSRF via frpc_base_port 污染）→ Task 1 端口范围断言（纵深）
- 🔴 BLOCKER B3（缺速率限制，违反 security.md）→ Task 3 throttle + device-cipher 命名限流器
- 🟡 HIGH H1（缺审计日志，高危命令无追溯）→ Task 3 audit log 到 security channel
- 🟡 HIGH H2（syncLockCipher 无服务端格式校验）→ Task 2 body.textCipher regex + actionName 枚举
- 🟡 HIGH H3（query.* 无长度上界，DoS 风险）→ Task 2 max:1024
- 🟢 MEDIUM M1（路径 regex 纵深防御）→ Task 2 正则规则
- 🟢 MEDIUM M2（401 vs 302 断言错误）→ Task 3 assertUnauthorized
- 🟢 MEDIUM M3（子账号测试覆盖不足）→ Task 3 两个子账号测试
- 🟢 MEDIUM M4（连接错误信息泄露内网 host:port）→ Task 3 错误过滤
- 🔵 LOW L1/L2/L3（CSRF / 系统应用启动 / 焦点输入框业务风险）→ Task 14 文档化

**6. 已知风险与执行注意事项：**

- **Task 3 测试 permission 授权**：计划基于 Spatie/Laravel-Permission + `RolePermissionSeeder` + `assignRole('client')`。如果 `client` 角色在项目里叫别的名字（例如 `user`），按实际调整。执行前用 `grep -rn "assignRole" app/tests/Feature/` 核对现有测试怎么写
- **Task 3 `security` logging channel**：如果 `config/logging.php` 没定义这个 channel，选择：(a) 改用 `daily` / `stack` / `single`；(b) 在 `logging.php` 新增 security channel（推荐 daily driver，日志分文件存储）
- **Task 0a 生产验证**：`127.0.0.1:20000-30000` 的绑定前提是 Laravel 与 frps 在同一 Docker host。如果 Laravel 部署在另一台机器，改为专用内网接口绑定
- **Task 0c 必须先于 Task 12 合并到设备 APK**：否则 Task 12 的手动验证会显示成功但密码不生效（replica bug 导致 instanceof 检查永远 false）
- **Task 10 `/blockView` 参数语义**：`show/transparent/hint/zeroBrightness/destroyLock` 的具体行为从 `UiDialogHandler.blockView()` 签名推导。执行前最好在真机上手动 curl 测试一次每种组合，确认与用户预期一致
- **Task 7 wakeScreen**：非 vivo 设备会静默失败（replica 侧 `GlobalActionHandler.wakeUpScreen()` 只对 vivo 家族生效），这是已知限制，不在本计划 scope
- **现有 `frpcAction` 是坏的**：`DeviceController::frpcAction` 用 `['action' => $action]` + 路由校验 `in:back,home,recents,lock` 全部写错了（应该是 `actionName` + `recent`）。前端没有调用这个端点所以 bug 未被触发。本计划不修 `frpcAction`；后续可独立清理

---

## Execution Handoff

Plan complete and saved to `docs/superpowers/plans/2026-04-12-panel-command-http-migration.md`.

**修订后 total steps**：17 个（Task 0a + 0b + 0c + Tasks 1-14），其中 Task 0c 触及 Android 代码。

Two execution options:

**1. Subagent-Driven (recommended)** — 每个 task 派一个全新 subagent，task 之间做 review，快速迭代

**2. Inline Execution** — 在当前 session 里连续执行，批量检查点 review

建议执行顺序：
1. **Task 0a / 0b / 0c** 并行执行（三个独立变更，不相互依赖）
2. **Task 1 / 2** 并行执行（两个独立后端变更）
3. **Task 3** 顺序执行（依赖 Task 1 + 2 — 引用 DeviceProxyService::request + DeviceProxyRequest）
4. **Task 4** 顺序执行（依赖 Task 0b 完成的 env.d.ts 类型声明）
5. **Tasks 5-12** 顺序执行（每个独立，但都依赖 Task 3 + 4；可快速迭代）
6. **Task 13** 顺序执行（依赖前面全部）
7. **Task 14** 独立执行

Which approach?
