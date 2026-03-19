# APK 模板文件分析与重构可行性调研报告

> 本文档对 `app/storage/app/apk/template` 所用 APK 模板进行分析，并给出模板重构的可行性调研与建议。

---

## 一、模板文件分析

### 1.1 模板来源与部署方式

| 项目 | 说明 |
|------|------|
| **配置路径** | `config('apk-builder.template_path')` → `storage_path('app/apk/template')` |
| **实际位置** | `app/storage/app/apk/template`（仓库中通常为空） |
| **ZIP 源** | `storage_path('app/apk/apkstub/apkstub.zip')` |
| **加载逻辑** | 若 `template_path` 目录不存在或校验失败，则从 `apkstub.zip` 解压到 `template_path` |

当前仓库中：

- `app/storage/app/apk/template`：**空目录**（无子文件）
- `app/storage/app/apk/apkstub/apkstub.zip`：**存在**，为 apktool 解码后的完整模板包

构建时若模板目录无效，会执行 `extractTemplateFromZip()`，将 ZIP 解压到 `template_path`，再复制到临时工作目录进行构建。

### 1.2 模板 ZIP 内容概览（apkstub.zip）

基于 `unzip -l` 与现有文档的统计：

| 指标 | 数值 |
|------|------|
| 总文件数 | 约 24,325 个 |
| 总未压缩大小 | 约 111 MB |
| Smali/XML/YML 等文本类 | 约 23,174 个 |
| 核心配置文件 | `smali/com/icontrol/protector/My_Configs.smali`（约 13.8 KB） |

**顶层结构（apktool 解码格式）：**

```
apkstub.zip 解压后
├── AndroidManifest.xml      # 清单（解码后 XML）
├── apktool.yml              # apktool 元数据、版本等
├── original/                # 原始二进制 AndroidManifest 等
├── res/                     # 资源（anim, drawable, values, xml 等）
├── assets/                  # 资产（.bt 等，含 dexopt）
├── smali/                   # DEX 1：主逻辑 + com.icontrol.protector
├── smali_classes2/          # DEX 2
├── smali_classes3/          # DEX 3
├── smali_classes4/          # DEX 4
└── unknown/                 # 未归类文件（如 version.properties）
```

**核心包与构建强相关：**

- **主包**：`smali/com/icontrol/protector/`  
  - 包含 `My_Configs.smali`（占位符配置）、`ActivMain.smali`、`AccessServices.smali` 等。
- **混淆库**：`smali*/aabab/...`、`smali*/myobfuscated/` 等，为第三方/闭源逻辑，构建时仅随模板整体复制，不做结构性修改。

### 1.3 构建流程对模板的依赖

构建管线（`App\Services\ApkBuilder\ApkBuilder`）对模板的依赖可归纳为以下几类。

#### （1）模板有效性校验

- **唯一硬性校验**：存在文件  
  `smali/com/icontrol/protector/My_Configs.smali`  
  即 `ApkBuilderConstants::CONFIGS_SMALI_RELATIVE`。
- 若模板目录为空或缺少该文件，会尝试从 `apkstub.zip` 解压；若仍无此文件则报错「APK 模板不完整」。

#### （2）必须存在的路径与约定

| 类型 | 路径/约定 | 用途 |
|------|-----------|------|
| Smali 配置 | `smali/com/icontrol/protector/My_Configs.smali` | 占位符替换、校验 |
| 清单 | `AndroidManifest.xml` | 包名、图标引用、res/xml 引用 |
| 资源 | `res/values/strings.xml` | 应用名、无障碍描述 |
| 元数据 | `apktool.yml` | versionCode / versionName |
| 图标 | `res/drawable*/mylogo.png` | 替换为定制图标 + `app_icon.png` |
| 背景 | `res/drawable/blackui.png` | 启动/黑屏背景图 |
| 可选 | `res/xml/*.xml` | 无障碍等，缺失时自动补全 |

#### （3）占位符替换（My_Configs.smali）

`SmaliProcessor::modifyConfig()` 对 `My_Configs.smali` 做字符串替换，占位符与配置对应关系见下表（与文档、代码一致）：

| 占位符 | 配置/来源 | 说明 |
|--------|-----------|------|
| `[Client_N]` | clientName | 客户端名称 |
| `[_NOTIFI_TITLE_]` / `[_NOTIFI_MSG_]` | notifyTitle / notifyMsg | 通知 |
| `[log-title]` / `[log-dis]` / `[log-btn]` / `[log-lng]` | loginTitle / loginDis / loginBtn / lngShort | 登录页 |
| `[USER_DOM]` | 从 websocketUrl 解析 host:port | 服务器地址 |
| `[USER_MAIL]` | 加密后的 email | 用户邮箱 |
| `[BSE_URL]` | 加密后的 appUrl | 基础 URL |
| `[USE-AUTOGRANT]` | loginTitle | 加载页标题 |
| `[USE-SUPER]`～`[USE-CAPLOCK]` | 各布尔/枚举配置 | 功能开关等 |
| `[AST-PAS]` | 随机 assetsKey | 资源加密密钥 |
| `[OBFS]` | 随机 obfuscationString | 混淆串 |
| `[NAME>LNK>ID!]` | clientName>userId>appId | 追踪数据 |

另：根据 `websocketUrl` 是否为 `wss://`，将 `const-string v1, "wss://"` 改为 `"ws://"`。

#### （4）清单与资源修改

- **AndroidManifest.xml**：包名替换、`@drawable/mylogo` → `@drawable/app_icon`、缺失的 `res/xml` 引用补全。
- **res/values/strings.xml**：`BaseName`、`accessibility_service_description` 正则替换。
- **apktool.yml**：`versionCode`、`versionName` 按 `appVersion` 计算并替换。

#### （5）包名重命名

当 `appId` ≠ `com.icontrol.protector` 时：

- 清单中包名全局替换；
- `SmaliProcessor::renamePackage()` 在所有 `ApkBuilderConstants::SMALI_DIRS`（smali, smali_classes2～7）下：
  - 重命名目录 `com/icontrol/protector` → 新包路径；
  - 替换所有 smali 内 `Lcom/icontrol/protector/` 引用。

因此模板与构建器的耦合点集中在：**单文件 My_Configs.smali、固定包名与路径、固定资源文件名（mylogo、blackui、BaseName 等）**。

### 1.4 与旧系统（Legacy）的对比

| 维度 | Legacy | Laravel app |
|------|--------|-------------|
| 模板目录 | `private/apkstub/extracted_apkstub`（常驻解压） | `storage/app/apk/template`（可为空，由 zip 解压） |
| 模板来源 | 同仓库内 `apkstub.zip` / `apkstubg.zip` | 仅 `storage/app/apk/apkstub/apkstub.zip` |
| 占位符与流程 | 与当前 SmaliProcessor 一致 | 同上，并增加 `[NAME>LNK>ID!]`、OBFS 等 |
| 已知风险 | AndroidManifest 膨胀（ReplaceHugePlaceholders 导致 383MB）→ aapt2 失败 | 当前构建不注入巨大占位符，无此问题 |

Legacy 的「模板」与 Laravel 的「模板」本质为同一套 apktool 解码结果；Laravel 侧通过 ZIP 按需解压，减少磁盘常驻占用。

---

## 二、重构可行性调研

### 2.1 重构目标界定

“模板重构”可能指不同层次，本报告区分如下：

| 层次 | 含义 | 范围 |
|------|------|------|
| **A. 存储与交付形式** | 模板如何存放（ZIP/目录）、如何版本化、多模板支持 | 仅构建系统与部署 |
| **B. 占位符与配置契约** | 是否仍用单文件 My_Configs.smali、是否增加/减少占位符、是否改用 JSON/二进制配置 | 模板 + SmaliProcessor + ApkBuildConfig |
| **C. 模板内容与结构** | 是否拆分 DEX、是否更换包名/资源名、是否替换部分 smali 为自有实现 | 需新模板或新 stub |
| **D. 替换为全新客户端** | 用全新 Android 工程（如 Kotlin + 新协议）替代现有 stub | 新应用 + 新构建管线 |

以下按 A→B→C→D 分别评估可行性。

### 2.2 层次 A：存储与交付形式重构

**内容**：  
- 是否始终保留「ZIP → 解压到 template_path」；  
- 是否支持多模板（如 apkstub.zip / apkstubg.zip）；  
- 是否将模板纳入版本管理（Git LFS / 独立制品库）。

**可行性：高**

- 当前已支持「无 template 目录时从 ZIP 解压」，扩展为「按配置选择 ZIP」或「多 ZIP 多模板」仅涉及配置与 `extractTemplateFromZip()` 的路径选择。
- 将 `template_path` 改为可选「已解压目录」或「ZIP 路径」的抽象，接口清晰，对现有 My_Configs 与占位符无影响。
- 风险：多模板时需保证每个 ZIP 都包含 `CONFIGS_SMALI_RELATIVE` 且占位符一致，否则需在配置中区分模板类型并在 SmaliProcessor 中分支（若有差异）。

**建议**：  
- 短期可保持现状（单 ZIP + 解压到 template）。  
- 若需支持「完整权限 / 部分权限」等多模板，可增加 `config('apk-builder.stub_zip_path')` 按 build 类型选择 ZIP，并确保各 ZIP 占位符集合一致。

---

### 2.3 层次 B：占位符与配置契约重构

**内容**：  
- 是否保留「单 My_Configs.smali + 字符串占位符」；  
- 是否改为多文件占位（如按模块拆成多个 smali）；  
- 是否引入外部配置（如 JSON/XML）由运行时读取，而非全部写死进 smali。

**可行性：中高（取决于改动幅度）**

- **保留单文件、仅调整占位符**：  
  - 在现有模板中增删占位符，并同步修改 `SmaliProcessor` 的 `$replacements` 与 `ApkBuildConfig`。  
  - 需重新生成或维护一份「与当前 ApkBuilder 契约一致」的模板（当前模板为闭源，只能基于现有 apkstub 做小范围替换或接受其现有占位符集）。  
  - 风险低，回归测试集中在构建与安装后关键行为（WebSocket、通知、登录页等）。

- **多文件占位 / 外部配置**：  
  - 若将配置从 My_Configs 拆到多个 smali 或 assets 中的 JSON：  
    - 需要模板内 stub 代码能读取新位置（当前 stub 逻辑未知且混淆，难以保证）。  
  - 若仅「构建时」写多个 smali 文件而运行时仍从 My_Configs 读：  
    - 需 stub 内有对应字段与读取逻辑，同样依赖模板实现。  
  - 结论：在**不修改现有 stub 二进制/逻辑**的前提下，占位符契约以当前 My_Configs.smali 单文件为准最稳妥；仅做占位符名称/数量的扩展可行，大规模改为多文件或外部配置**可行性中、风险较高**，需有 stub 源码或逆向确认。

**建议**：  
- 占位符仅做「增量」扩展（如新开关、新字符串），保持单文件、同格式。  
- 若未来有 stub 源码或可替换的自家实现，再考虑多文件或 JSON 配置。

---

### 2.4 层次 C：模板内容与结构重构

**内容**：  
- 更换默认包名、资源名（如 mylogo → 其他）；  
- 拆分/合并 DEX、调整 smali 目录结构；  
- 用「自己维护的 smali 子集」替换模板中部分类（例如仅保留协议与 UI 骨架）。

**可行性：中低**

- **包名/资源名**：  
  - 若把默认包名从 `com.icontrol.protector` 改为其他：需在 ApkBuilderConstants、SmaliProcessor、Manifest 替换逻辑中全面改为新包名；模板 ZIP 也需为新包名版本（当前 ZIP 为 com.icontrol.protector）。  
  - 若把 mylogo/blackui/BaseName 等改为其他名称：需在 ApkBuilder 与 ApkBuilderConstants 中统一改名，并确保模板 ZIP 内资源与清单引用一致。  
  - 技术可行，但需**双轨维护**：要么维护两套模板（旧包名/新包名），要么一次性切换并全量回归。

- **DEX/目录结构**：  
  - 当前 SMALI_DIRS 为 smali～smali_classes7；若模板改为更多或更少 DEX，需同步常量与 renamePackage 的遍历范围。  
  - 若模板来自新反编译结果，目录与类数量可能变化，需重新验证 renamePackage、混淆、apktool 打包是否仍正常。

- **替换部分 smali 为自有实现**：  
  - 需清楚 stub 的入口、依赖关系与混淆引用；当前 2 万+ 文件、深度混淆，替换成本高、易遗漏引用。  
  - 仅当有 stub 源码或详细调用图时，才适合做局部替换；否则**可行性低**。

**建议**：  
- 在未拿到可维护的 stub 源码前，不推荐做模板内部结构或包名/资源名的大改。  
- 若仅为了「去品牌化」或合规，可优先做**最小改动**：仅改 strings.xml 与可见资源，保持包名与 My_Configs 契约不变。

---

### 2.5 层次 D：替换为全新客户端

**内容**：  
- 用全新 Android 工程（如 Kotlin + 新 WebSocket 协议）重写客户端；  
- 构建方式改为 Gradle 构建 + 参数化（如 BuildConfig、assets 配置文件）。

**可行性：高（长期）、成本高**

- 技术栈与构建流程完全可控，可彻底摆脱现有模板的占位符与混淆约束。
- 需要：  
  - 新应用实现现有 stub 的全部业务能力（设备管理、无障碍、通知、登录页、WebSocket 等）；  
  - 新构建管线（Gradle/CI + 产物上传或本地签名）；  
  - 服务端与前端兼容新客户端协议与配置方式。  
- 适合作为**中长期**规划，与当前「基于现有模板的构建」并行一段时间，再逐步切换。

---

## 三、风险与约束总结

| 风险/约束 | 说明 | 缓解 |
|-----------|------|------|
| 模板闭源 | 当前 stub 为第三方/闭源，无源码则难以改内部逻辑与结构 | 仅做存储形式与占位符扩展；大改需新 stub 或新应用 |
| 单点依赖 My_Configs | 构建与运行时强依赖单文件占位符 | 保持该契约，或在新客户端方案中废弃 |
| 包名与路径写死 | DEFAULT_PACKAGE、CONFIGS_SMALI_RELATIVE、SMALI_DIRS 等写死在常量中 | 重构时可将「模板类型」抽象为配置，按类型选路径与包名 |
| 多模板一致性 | 若支持多 ZIP，占位符与资源名需一致 | 配置层区分模板类型，并做构建前校验（如检查必要占位符存在） |
| 历史 APK 兼容 | 已分发 APK 依赖当前协议与配置方式 | 服务端保持对旧客户端的兼容；新客户端可新协议 |

---

## 四、结论与建议

### 4.1 模板现状结论

- **物理位置**：有效模板来自 `app/storage/app/apk/apkstub/apkstub.zip`，解压目标为 `app/storage/app/apk/template`（可为空，按需解压）。
- **结构**：apktool 解码格式，约 2.4 万文件、约 111 MB，核心为 `smali/com/icontrol/protector/My_Configs.smali` 及固定资源名（mylogo、blackui、BaseName 等）。
- **耦合点**：单文件占位符、固定包名、固定路径与资源名；构建器已通过 ApkBuilderConstants 集中管理，便于做「配置化」小步重构。

### 4.2 重构建议（按优先级）

1. **短期（低风险）**  
   - **层次 A**：如需多模板，增加按配置选择 `stub_zip_path`（或等价配置），并保证各 ZIP 占位符一致。  
   - 保持现有单 ZIP + 解压到 template 的机制不变，仅扩展配置项。

2. **中期（可控风险）**  
   - **层次 B**：仅在现有 My_Configs.smali 上做占位符**增量**扩展（新字段、新开关），并同步 SmaliProcessor 与 ApkBuildConfig。  
   - 将 ApkBuilderConstants 中与「当前模板」强绑定的部分（如 DEFAULT_PACKAGE、CONFIGS_SMALI_RELATIVE）改为可配置或按「模板类型」读取，为将来多模板或新 stub 留口子。

3. **长期（高投入）**  
   - **层次 D**：规划新 Android 客户端 + 新构建管线，逐步替代当前基于 apkstub 的构建。  
   - **层次 C**：仅在获得可维护的 stub 源码或明确替换范围时，再考虑模板内部结构、包名、资源名的大规模重构。

### 4.3 不建议的做法

- 在**无 stub 源码或详细逆向**的前提下，对模板内部做大规模 smali 替换或 DEX 重组。  
- 一次性改变默认包名/资源名而不做双轨或全量回归。  
- 将占位符从单文件改为多文件或外部 JSON，而未确认当前 stub 是否支持读取新位置。

---

**文档版本**：1.0  
**编写日期**：2026-02-09  
**依据**：`app/storage/app/apk/` 目录、`app/app/Services/ApkBuilder/` 源码、`docs/legacy/APK_STUB_TEMPLATE.md` 及 APK 构建相关文档。
