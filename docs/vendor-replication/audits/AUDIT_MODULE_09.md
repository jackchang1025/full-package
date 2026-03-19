# MODULE_09 数据模型 — Vendor 行为审计

## 1. 模块职责

请求/响应/实体数据模型。所有模块的数据传输对象 (VO/DTO)，包括 API 请求体、API 响应体、内部实体。是序列化/反序列化的基础。

## 2. 文件统计

| 分类 | Vendor 文件数 | Vendor 行数 | Replica 文件数 | Replica 行数 | 覆盖率 |
|------|-------------|------------|---------------|-------------|--------|
| req/ | 55 | 3696 | 55 | ~2800 | ✅ 100% 文件覆盖 |
| resp/ | 42 | 4520 | 42 | ~2200 | ✅ 100% 文件覆盖 |
| entity/ | 24 | 6390 | 10/24 | ~460 | ⚠️ 42% 文件覆盖 |
| 总计 | 121 | 14606 | 107 | ~5460 | 88% 文件, 37% 行数 |

## 3. req/ 请求模型 — 全部 55 个文件已有

行数对比 (抽样):
- 大部分 replica 行数为 vendor 的 60-80%
- 差距主要是: 缺少部分 getter/setter、缺少 toString()、缺少构造函数重载
- 功能上基本可用

## 4. resp/ 响应模型 — 10 个空桩需要补齐

### 空桩文件 (5 行, 只有 class 声明)

| 文件 | Vendor 行数 | 影响 |
|------|------------|------|
| ApiResult | 100 | ❌ P0 — 所有 API 响应解析依赖此类 |
| DeviceInfoVO | 517 | ❌ P0 — 设备注册/心跳依赖 |
| UiObjectVO | 615 | ❌ P1 — 节点搜索结果序列化 |
| DeviceDebugVO | 149 | ⚠️ P1 — ADB 调试状态 |
| AttachFileVO | 127 | ⚠️ P2 — 文件附件 |
| DeviceAgentFileVO | 5 | ⚠️ P2 — 代理文件 |
| DevicePairStateVO | 5 | ⚠️ P2 — 配对状态 |
| PushResponseVO | 109 | ⚠️ P1 — 推送响应 |
| SmsRecognizePlug | 5 | ⚠️ P2 — 短信识别 |
| DeviceMediaStoreImageVO | 8 | ⚠️ P2 — 媒体图片 |

### 已实现文件 (32 个, >10 行)

大部分行数为 vendor 的 50-80%，缺少部分字段但基本可用。

## 5. entity/ 实体 — 14 个文件缺失

| 缺失文件 | Vendor 行数 | 影响 | 说明 |
|---------|------------|------|------|
| LangDialog | 271 | P1 | 多语言弹窗文本 (config 依赖) |
| ProcessInfo | 91 | P2 | 进程信息 |
| WIFIState | 55 | P2 | WiFi 状态 |
| CookieVO | 124 | P2 | Cookie 管理 |
| HostCookies | 107 | P2 | 主机 Cookie |
| CacheResponseKey | 55 | P2 | 缓存响应键 |
| CheckPortResult | 49 | P2 | 端口检查结果 |
| NoticeRootChangedVO | 52 | P2 | 根节点变化通知 |
| PairPortAndCodeResult | 54 | P2 | 配对端口结果 |
| RootInActiveWindowResult | 37 | P2 | 活跃窗口根节点 |
| TakeScreenShotResult | 40 | P2 | 截图结果 |
| ADBKey | 7 | P2 | ADB 密钥 |

已在其他位置实现的:
- BuildConfig → config/AppConfig.java ✅
- UiObject → auto/entity/UiNode.java ✅
- UiObjectCollection → auto/entity/UiNodeCollection.java ✅

## 6. 优先修复项

### P0 (阻塞性 — API 响应无法解析)
1. ApiResult — 100 行, 所有 HTTP 回调依赖
2. DeviceInfoVO — 517 行, 设备注册/心跳核心

### P1 (重要功能)
3. UiObjectVO — 615 行, 节点搜索结果
4. DeviceDebugVO — 149 行, ADB 调试
5. PushResponseVO — 109 行, 推送响应
6. LangDialog — 271 行, 多语言

### P2 (完善)
7. 其余 8 个缺失 entity 文件
8. resp/ 已实现文件补齐缺失字段
9. req/ 已实现文件补齐缺失字段
