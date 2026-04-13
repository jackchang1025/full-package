# JADX 源码参考

> 如何浏览 `update.apk` 的反编译源码。

## 目录结构

```
../jadx-reference/                    # 根目录
├── rock/                             # 主包 (com.storm.safe.rock) — 559 文件, 145K LOC
│   ├── activity/                     # Activity (11 文件)
│   ├── hkdrkgzsfs.java              # Application 类
│   ├── inject/                       # 依赖注入 (1 文件)
│   ├── keepalive/                    # 保活 (1 文件)
│   ├── manager/                      # 管理器 (6 文件)
│   ├── network/                      # 网络 (2 文件)
│   ├── p029ui/                       # UI (2 文件)
│   ├── receiver/                     # 广播接收器 (7 文件)
│   ├── security/                     # 安全 (1 文件)
│   ├── service/                      # 服务 (根目录 17 文件)
│   │   ├── account/                  # 账户 (4 文件)
│   │   └── modules/                  # 功能模块
│   │       ├── base/                 # 基类 (2 文件)
│   │       ├── cipher/               # 密码捕获 (16 文件)
│   │       ├── command/              # C2 命令 (10 文件)
│   │       ├── overlay/              # 屏幕悬浮窗 (2 文件)
│   │       ├── protection/           # 反分析 (2 文件)
│   │       ├── screen/               # 屏幕唤醒 (1 文件)
│   │       ├── setup/                # 开发者选项 + ADB 配对 (18 文件)
│   │       ├── yw5xud/               # 厂商引擎 (189 文件, 117K LOC)
│   │       └── *.java                # 模块根目录文件 (22 文件)
│   ├── util/                         # 工具类 (3 文件)
│   └── view/                         # 视图 (1 文件)
├── io/                               # Socket.IO 库 (32 文件)
├── p000/                             # 混淆/R8 残留 (2,397 文件)
├── native/                           # 原生库 (4 个 .so 文件)
├── update-original.apk               # 原始 APK（MD5 与 ../update.apk 一致）
├── update-fixed.apk                  # JADX 修复后的 APK
└── FILE_INDEX.txt                    # 文件索引
```

## APK 身份信息

| 属性 | 值 |
|------|-----|
| 文件 | `../update.apk` |
| 大小 | 22,375,621 字节 |
| MD5 | `75dbc8c5833f6c3e678a946da20563b5` |
| 包名 | `com.storm.safe.rock` |
| 反编译器 | JADX |

## 请勿混淆

| 路径 | 包名 | 说明 |
|------|------|------|
| `../app/storage/app/apk/apkstub/decompiled_vendor/` | `com.guard.wallet` | 来自旧版 `android/` 项目的另一个 APK |

这两个 APK 架构相似（均为 RAT 框架），但包名、类名和混淆方式不同。`decompiled_vendor` 源码不是 `update-replica` 的有效参考。

## 命名规则

JADX 生成的类名带有 R8 混淆。常见模式:

| 模式 | 示例 | 含义 |
|------|------|------|
| `C0NNNaM` | `C0358a0` | 混淆类，数字索引 |
| `AbstractC0NNNaM` | `AbstractC0361a3` | 抽象基类 |
| `RunnableC0NNNaM` | `RunnableC0282a2` | 实现 Runnable 接口 |
| `Class$Inner.java` | `OpenDevelopmentDelegate$State.java` | 内部类（单独文件） |
| 小写随机字符串 | `hkdrkgzsfs.java` | 字符串混淆的类名 |

当复刻文件重命名混淆类时，需在 FILE_MAPPING.md 中记录映射关系。

## 模块规模 (service/modules/)

| 模块 | 文件数 | LOC | 最大文件 |
|------|--------|-----|---------|
| yw5xud | 189 | 116,698 | C0371a8.java (11,061) |
| command | 46* | 16,926 | — |
| cipher | 16 | 15,868 | — |
| setup | 18 | 7,707 | C0360a2.java (5,666) |
| protection | 2 | 4,970 | — |
| overlay | 2 | 1,364 | — |
| base | 2 | 428 | — |
| screen | 1 | 40 | — |

*command 包含 modules/ 根目录下的文件。

## 阅读反编译代码的建议

1. **先看内部类文件** — 它们通常包含枚举和状态定义，有助于理解父类。
2. **文件名中的 `$`** 表示内部类。`Foo$Bar.java` 是 `class Foo` 内部的 `class Bar`。
3. **JADX WARN 注释** 表示反编译失败。需交叉参考 smali 或 `--show-bad-code` 版本。
4. **`f###x` 字段** 是 JADX 因名称冲突而重命名的字段。`###` 是任意数字；原始名称在注释中。
