# ADB 连接指南

## 环境说明

WSL2 环境下通过 Windows 侧 platform-tools 连接 Android 设备。

## ADB 路径

```
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
```

## 目标设备

| 设备 | 地址 | 系统 | 说明 |
|------|------|------|------|
| 华为鸿蒙系统设备 1 | `192.168.31.162:5555` | 鸿蒙 | 主调试设备 |
| 华为安卓系统设备 2 | `192.168.31.211:5555` | 安卓 | 辅助调试设备 |
| 小米13 (2211133C) | `192.168.31.102:5555` | Android 15 (API 35), 澎湃OS V816 | 小米测试设备 |
| OPPO (PGFM10) | `192.168.31.249:5555` | Android 16 (API 36), ColorOS 16.0.3.500 | OPPO 测试设备 |

## 常用命令

### 连接设备

```bash
# 设备 1 (华为鸿蒙)
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.162:5555
# 设备 2 (华为安卓)
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.211:5555
# 设备 3 (小米13)
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.102:5555
# 设备 4 (OPPO)
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.249:5555
```

### 检查连接状态

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe devices
```

### 安装 APK

```bash
# 设备 1
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 install -r app/build/outputs/apk/debug/app-debug.apk
# 设备 2
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.211:5555 install -r app/build/outputs/apk/debug/app-debug.apk
```

### 卸载应用

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 uninstall com.vendor.rat
```

### 查看日志

```bash
# 全部日志
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 logcat

# 按 tag 过滤
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 logcat -s "MyTag"

# 按包名过滤 (需要先获取 PID)
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 logcat --pid=$(/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 shell pidof com.vendor.rat)
```

### 推送文件到设备

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 push local_file /sdcard/
```

### 从设备拉取文件

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 pull /sdcard/remote_file ./
```

### Shell 交互

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555 shell
```

## 快捷别名 (可选)

在 `~/.bashrc` 中添加：

```bash
alias adb='/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe'
alias adbs='/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.162:5555'
```

添加后执行 `source ~/.bashrc`，之后可直接使用：

```bash
adbs install -r app-debug.apk
adbs logcat
adbs shell
```

## 故障排查

| 问题 | 解决方案 |
|------|----------|
| `device offline` | `adb disconnect` 后重新 `adb connect 192.168.31.162:5555` |
| `no devices` | 确认手机和电脑在同一局域网，手机已开启无线调试 |
| `INSTALL_FAILED_UPDATE_INCOMPATIBLE` | 先卸载旧版本再安装 |
| `adb server version mismatch` | `adb kill-server && adb start-server` |

## ADB 启用无障碍服务

无需手动操作设备 UI，通过 ADB 直接启用无障碍服务：

```bash
# 启用无障碍
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.211:5555 \
    shell settings put secure enabled_accessibility_services \
    com.vendor.rat/com.vendor.rat.service.MyAccessibilityService
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.211:5555 \
    shell settings put secure accessibility_enabled 1

# 验证
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 192.168.31.211:5555 \
    shell settings get secure enabled_accessibility_services
```

## 一键卸载重装 + 授权

```bash
ADB="/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe"
DEVICE="192.168.31.211:5555"

$ADB -s $DEVICE uninstall com.vendor.rat
$ADB -s $DEVICE install -r app/build/outputs/apk/debug/app-debug.apk
$ADB -s $DEVICE shell am start -n com.vendor.rat/.activity.ActivMain
sleep 3
$ADB -s $DEVICE shell settings put secure enabled_accessibility_services \
    com.vendor.rat/com.vendor.rat.service.MyAccessibilityService
$ADB -s $DEVICE shell settings put secure accessibility_enabled 1
```

## E2E 自动化测试脚本

完整的端到端真机测试脚本，自动执行卸载→安装→授权→等待自动化→验证结果：

```bash
cd /home/code/php/project/full-package/android

# 完整测试 (含构建)
./scripts/e2e_huawei_test.sh 192.168.31.162:5555

# 跳过构建
./scripts/e2e_huawei_test.sh 192.168.31.211:5555 --skip-build
```

验证项 (7/7):
1. 构建安装成功
2. 无障碍启用
3. 进入启动管理 (搜索直达/导航/已设置过)
4. 自启动已关闭
5. 权限自动授权
6. 遮罩已关闭
7. 返回应用页面

详见: [TESTING_GUIDE.md](./TESTING_GUIDE.md) 第五-B章
