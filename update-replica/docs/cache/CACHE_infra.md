# InfraLayer 知识缓存
> 生成时间: 2026-04-14 | 文件数: 13 (非内部类) | 总 LOC: 7,822 | 内部类文件: 18

## 文件清单

### manager/ (6 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 1 | C0260a2.java | ScreenCaptureManager.kt | 3,794 | 7 | MediaProjection 截屏 + 无障碍模式 |
| 2 | C0258a0.java | C0258a0.kt | 551 | 0 | 摄像头辅助管理 |
| 3 | C0263a5.java | C0263a5.kt | 531 | 5 | SmartMediaProjectionManager |
| 4 | C0259a1.java | C0259a1.kt | 487 | 0 | 权限自动授予 (PermissionGranter) |
| 5 | C0262a4.java | CameraCaptureManager.kt | 313 | 0 | 摄像头捕获管理 |
| 6 | C0261a3.java | AudioRecordManager.kt | 45 | 4 | 麦克风录音管理 |

### network/ (2 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 7 | C0268a1.java | DataSyncClient.kt | 841 | 1 | HTTP 数据同步客户端 |
| 8 | C0267a0.java | DataSyncClient.kt (合并) | 436 | 9 | HttpManager，HTTP 请求封装 |

### util/ (3 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 9 | ReflectApi.java | DeviceUtils.kt (合并) | 144 | 0 | 反射 API 工具 |
| 10 | AbstractC0385a0.java | DeviceUtils.kt | 32 | 2 | 设备工具类 (Brand 枚举) |
| 11 | StringUtil.java | StringUtil.kt | 27 | 0 | XOR 加密/解密字符串 |

### security/ (1 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 12 | AbstractC0276a0.java | SecurityChecker.kt | 496 | 4 | Root/Frida/Xposed/模拟器检测 |

### keepalive/ (1 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 13 | KeepAliveWorker.java | KeepAliveWorker.kt | 125 | 1 | WorkManager 保活 Worker |

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| C0260a2 | ScreenCaptureManager | — | 双模式截屏: MediaProjection + AccessibilityService |
| C0258a0 | C0258a0 | — | 摄像头辅助 (CameraManager 封装) |
| C0263a5 | C0263a5 | — | MediaProjection 智能恢复管理 (SmartMediaProjectionManager) |
| C0259a1 | C0259a1 | — | 权限弹窗自动点击 (PermissionGranter) |
| C0262a4 | CameraCaptureManager | — | Camera2 API 拍照 |
| C0261a3 | AudioRecordManager | MediaProjection.Callback | AudioRecord 录音 |
| C0268a1 | DataSyncClient | — | WebSocket/HTTP 数据同步 |
| C0267a0 | *(合并到 DataSyncClient)* | — | HTTP 请求方法 (register/upload/post) |
| ReflectApi | *(合并到 DeviceUtils)* | — | 反射调用工具 |
| AbstractC0385a0 | DeviceUtils | abstract | 设备品牌/型号/API 工具 |
| StringUtil | StringUtil | — | XOR Base64 字符串加解密 |
| AbstractC0276a0 | SecurityChecker | abstract | 安全环境检测 (Root/Hook/模拟器) |
| KeepAliveWorker | KeepAliveWorker | Worker | WorkManager 周期保活 |

## 模块间依赖
- **依赖**: util/ 内部依赖 (StringUtil ← 全模块使用), security/ 被 service/ 启动时调用
- **被依赖**:
  - manager/ ← service/ (MyAccessibilityService 持有 ScreenCaptureManager, CameraCaptureManager, AudioRecordManager, C0263a5, C0259a1)
  - network/ ← service/ + modules/ (NetworkManager 封装 DataSyncClient)
  - util/ ← 全项目 (DeviceUtils 品牌检测, StringUtil 解密)
  - security/ ← service/ (启动时安全检查)
  - keepalive/ ← receiver/ (BootCompletedReceiver 调度)

## 关键架构说明

### ScreenCaptureManager (3,794 LOC) — 模块最大文件
```
截屏请求 → 优先 MediaProjection 模式
  ├── 成功 → ImageReader 获取帧 → JPEG 编码 → Base64
  └── 失败 → 降级无障碍截屏 (AccessibilityService.takeScreenshot)
权限恢复 → SmartMediaProjectionManager 自动重连
```

### DataSyncClient (841 + 436 = 1,277 LOC)
```
HTTP 方法: register, post, uploadDeviceStatus, uploadSms,
  uploadIncomingSms, uploadPasswordCapture, uploadInjectionData, uploadLogs
所有请求使用 StringUtil XOR 加密关键参数
```

## 已知缺口
- [x] 全部 13 个文件已完成复刻
- [x] C0267a0 合并到 DataSyncClient.kt, ReflectApi 合并到 DeviceUtils.kt
- [ ] Replica 额外新增: AssetConfigReader.kt (ZM26 资产解密), SecurityPolicy.kt (安全策略枚举)

## 逆向经验

> 记录从 JADX 源码审查中发现的经验。
