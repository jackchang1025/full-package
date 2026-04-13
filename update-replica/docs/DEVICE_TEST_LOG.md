# 真机验证日志

## 测试设备: 小米13

| 项目 | 值 |
|------|-----|
| 设备 | Xiaomi 2211133C |
| 系统 | Android 16 (API 36), 澎湃OS |
| ADB | 192.168.31.102:39851 |
| APK | `dev.deltalab2964.swift` (debug build) |
| 测试时间 | 2026-04-13 23:22 |

## 验证结果

| # | 路径 | 结果 | 详情 |
|---|------|------|------|
| 1 | **应用启动** | ✅ PASS | Activity 正常创建渲染，PID 14509 稳定存活 |
| 2 | **ZM26 配置加载** | ✅ PASS | `配置加载成功: language=zh-CN, brand=xiaomi` |
| 3 | **Application 初始化** | ✅ PASS | WorkManager 初始化成功，SecurityCheck 已运行 |
| 4 | **无障碍服务** | ⚠️ 需手动 | Android 安全限制，需进入 设置→无障碍→已安装的服务 手动开启 |
| 5 | **AppCoreService** | ✅ PASS | 前台服务已启动: `ServiceRecord{3f1b469}` |
| 6 | **进程稳定性** | ✅ PASS | 3 秒后 PID 未变，无 FATAL/Crash/ANR |

## 启动时序 (从 logcat)

```
23:21:39.227  Zygote fork 进程
23:21:39.430  WorkManager 初始化
23:21:39.469  ZM26 配置加载成功 (locateValues.json → language=zh-CN, brand=xiaomi)
23:21:39.481  开机广播处理 + JobScheduler 注册
23:21:39.489  zgafaqvswksa Job 调度 (1.5s 后触发)
23:21:39.505  AppCoreService 前台服务启动
```

**启动到完成初始化: ~280ms** (从进程 fork 到配置加载完成)

## 关键修复历史

### 1. AndroidManifest 组件注册 (2026-04-13)
- **问题**: Manifest 缺失 33 个组件注册，Activity 无法启动
- **修复**: 新增 15 Activity + 7 Service + 11 Receiver + 1 Provider

### 2. ZM26 解密算法 (2026-04-13)
- **问题**: `加载配置失败: locateValues.json` — 三个子问题叠加
- **子问题 1**: assets/ 目录为空，缺少 .bt 加密文件
- **子问题 2**: XOR key 用 UTF-8 编码 (32 bytes) 而非 hex 解码 (16 bytes)
- **子问题 3**: salt 取 12 字节 (bytes[4:16]) 而非 8 字节 (bytes[4:12])
- **修复**: 从 update-fixed.apk 提取 assets，修复 key 格式和 salt 长度
- **参考**: `docs/ZM26_ENCRYPTION.md`

## 待完成验证 (需手动操作)

| 路径 | 前提条件 | 验证方法 |
|------|---------|---------|
| WebSocket 连接 | 配置服务器 URL | 服务端日志确认心跳 |
| 命令响应 | WebSocket 连接 | 服务端下发 GET_DEVICE_STATE |
| 密码采集 | 无障碍服务 | 锁屏→输入 PIN→检查上报 |
| 反卸载保护 | 无障碍服务 | 长按桌面图标→检查遮挡 |
| 权限自动化 | 无障碍服务 | 检查电池优化/自启动跳转 |
| 保活恢复 | AppCoreService | 杀进程→检查自动重启 |
