# Vendor 反编译代码逆向工程项目

## 编译状态: BUILD SUCCESSFUL

```bash
cd androidReverseEngineering && ./gradlew clean compileJava
# BUILD SUCCESSFUL in 1s
```

## 项目概况

| 指标 | 数值 |
|------|------|
| Java 文件 | 889 |
| 代码行数 | 75,351 |
| 编译错误 | 0 |
| 逆向注释 | 732 (@reverse=320, @field=166, @route=226, @reverse-library=20) |
| package-info | 62 |
| 外部库映射 | docs/OBFUSCATION_MAP.md |

## 反编译工具

- **CFR 0.152** — 主反编译器（替代 JADX，消除 98% goto + 100% Method dump）
- **dex2jar** — DEX→JAR 转换
- **Gradle 8.5** — 构建系统

## 包结构

```
src/
├── a1/                    # 基础设施工具类（q.java 63 方法已全部重命名）
├── com/
│   ├── guard/wallet/      # 主业务代码（345 文件）
│   │   ├── activity/      # Activity 入口
│   │   ├── condition/     # UI 自动化条件
│   │   ├── entity/        # 数据实体
│   │   ├── filter/        # 节点过滤器
│   │   ├── helper/        # 辅助类
│   │   ├── http/          # HTTP 网络层
│   │   ├── msg/           # 消息协议
│   │   ├── plug/          # 插件模块
│   │   ├── receiver/      # 广播接收器
│   │   ├── req/           # 请求 VO
│   │   ├── resp/          # 响应 VO
│   │   ├── server/        # 命令路由（b.java 238 方法, 24 路由）
│   │   ├── service/       # 无障碍服务
│   │   ├── stat/          # 统计
│   │   ├── sync/          # 同步
│   │   ├── thread/        # 后台线程
│   │   └── utils/         # 工具类（g.java 129 方法）
│   └── google/json/       # Gson 混淆版（77 文件）
├── o/                     # 厂商引擎层（36 文件, 6 厂商）
└── [38 个外部混淆依赖包]  # OkHttp/Conscrypt/OkIO 等
```

## 深度逆向完成项

### a1/q.java — 钥匙类（63 方法全部重命名）
- `q.logError()` (原 s) — 异常日志, 被调用 327 次
- `q.isNullOrEmpty()` (原 B) — 空值检查, 被调用 218 次
- `q.decryptAES()` (原 m) — AES/ECB 解密
- `q.createSSLContext()` (原 y) — TLS 上下文
- `q.execShellCommand()` (原 u) — Shell 执行
- 其余 58 个方法均已重命名

### server/b.java — 命令路由（24 条路由已映射）
见 `src/com/guard/wallet/server/package-info.java`

### o/ 引擎层 — 6 厂商已识别
| 文件 | 厂商 |
|------|------|
| o/v.java | OPPO ColorOS |
| o/q.java | 小米 MIUI |
| o/n.java, o/h.java | 华为 |
| o/i0.java | Vivo |
| o/e0.java | 传音 |
| o/g.java | AOSP 通用 |

见 `src/o/package-info.java`

### 外部混淆包 — 22 个库已识别
见 `docs/OBFUSCATION_MAP.md`

## 构建

```bash
./gradlew compileJava        # 编译
./gradlew clean compileJava  # 清理后编译
```

依赖:
- Java 17
- Android SDK (android-34)
- BouncyCastle (Maven, compileOnly)
