# ADB 连接指南

## 环境说明

WSL2 环境下通过 Windows 侧 platform-tools 连接 Android 设备。

## ADB 路径

```
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
```

## 目标设备

| 设备 | 地址 | 说明 |
|------|------|------|
| 华为鸿蒙系统设备 1 | `192.168.31.162:5555` | 主调试设备 |
| 华为安卓系统设备 2 | `192.168.31.211:5555` | 辅助调试设备 |

## 常用命令

### 连接设备

```bash
# 设备 1
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.162:5555
# 设备 2
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe connect 192.168.31.211:5555
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
