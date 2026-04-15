# UILayer 知识缓存
> 生成时间: 2026-04-14 | 文件数: 39 (非内部类) | 总 LOC: 8,148

## 文件清单

### activity/ (11 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 1 | yrsanyhsbh.java | yrsanyhsbh.kt | 387 | Activity，主要功能界面 |
| 2 | yojggfhv.java | yojggfhv.kt | 366 | Activity，功能界面 |
| 3 | izvpcqplqctn.java | izvpcqplqctn.kt | 315 | Activity，功能界面 |
| 4 | syuqattwmgit.java | syuqattwmgit.kt | 287 | Activity，功能界面 |
| 5 | AccessibilityTrampoline.java | AccessibilityTrampoline.kt | 205 | Activity，无障碍服务跳板 |
| 6 | qixvbtmo.java | qixvbtmo.kt | 104 | Activity，辅助界面 |
| 7 | htvekhdt.java | htvekhdt.kt | 102 | Activity，辅助界面 |
| 8 | todoqkrxcctl.java | todoqkrxcctl.kt | 92 | Activity，辅助界面 |
| 9 | PackageVerifyActivity.java | PackageVerifyActivity.kt | 84 | Activity，安装验证界面 |
| 10 | BackgroundTaskActivity.java | BackgroundTaskActivity.kt | 58 | Activity，后台任务 |
| 11 | TransparentHelperActivity.java | TransparentHelperActivity.kt | 14 | Activity，透明辅助 |

### receiver/ (7 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 12 | zbrefryi.java | zbrefryi.kt | 295 | DeviceAdminReceiver，设备管理员 |
| 13 | arniezsqllm.java | arniezsqllm.kt | 213 | BroadcastReceiver，通用广播 |
| 14 | izkmisshyc.java | izkmisshyc.kt | 188 | BroadcastReceiver，功能广播 |
| 15 | hgejzydhoqsl.java | hgejzydhoqsl.kt | 148 | BroadcastReceiver，功能广播 |
| 16 | hhymfsyujsj.java | hhymfsyujsj.kt | 93 | BroadcastReceiver，功能广播 |
| 17 | kksddvryq.java | kksddvryq.kt | 54 | BroadcastReceiver，简单广播 |
| 18 | jrhgpixkephr.java | jrhgpixkephr.kt | 45 | BroadcastReceiver，简单广播 |

### inject/ (1 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 内部类 | 说明 |
|---|----------|------------|---------|--------|------|
| 19 | jbqfkndyx.java | jbqfkndyx.kt | 238 | 1 | Activity，WebView 注入界面 |

### p029ui/ (2 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 20 | umrkmgrri.java | umrkmgrri.kt | 214 | Activity，UI 界面 |
| 21 | ibbnqvnvhxg.java | ibbnqvnvhxg.kt | 67 | Activity，辅助 UI |

### view/ (1 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 22 | ParticleView.java | ParticleView.kt | 131 | 自定义 View，粒子动画 |

### 根级别 (17 文件)

| # | JADX 文件 | Kotlin 文件 | JADX LOC | 说明 |
|---|----------|------------|---------|------|
| 23 | iuzxujjtqev.java | iuzxujjtqev.kt | 2,591 | AppCompatActivity，主设置界面 |
| 24 | JunkRegistry.java | JunkRegistry.kt | 1,516 | 垃圾代码注册表 (混淆填充) |
| 25 | hkdrkgzsfs.java | MyApplication.kt | 123 | Application，应用入口 |
| 26 | AbstractC0241a0.java | MediaProjectionHolder.kt | 92 | MediaProjection 全局持有 |
| 27-39 | AppVariantA~N.java (13) | AppVariantA~N.kt (13) | 9 each | Activity-alias 变体 |
| 40 | DefaultLauncherAlias.java | DefaultLauncherAlias.kt | 9 | 默认启动器别名 |

## 去混淆映射

| JADX 类名 | Kotlin 类名 | 继承 | 职责简述 |
|----------|------------|------|---------|
| iuzxujjtqev | iuzxujjtqev | AppCompatActivity | 主设置界面 (2,591 LOC) |
| hkdrkgzsfs | MyApplication | Application | 应用入口，全局初始化 |
| AbstractC0241a0 | MediaProjectionHolder | abstract | MediaProjection 单例持有 |
| JunkRegistry | JunkRegistry | — | 混淆填充垃圾代码 |
| AccessibilityTrampoline | AccessibilityTrampoline | Activity | 无障碍服务启动跳板 |
| PackageVerifyActivity | PackageVerifyActivity | Activity | 安装包验证 |
| BackgroundTaskActivity | BackgroundTaskActivity | Activity | 后台任务启动 |
| TransparentHelperActivity | TransparentHelperActivity | Activity | 透明 Activity 辅助 |
| zbrefryi | zbrefryi | DeviceAdminReceiver | 设备管理员接收器 |
| jbqfkndyx | jbqfkndyx | Activity | WebView 注入 (含 sendInjectionData 内部类) |
| AppVariantA~N | AppVariantA~N | — | Manifest activity-alias 变体 (13个) |

## 模块间依赖
- **依赖**: service/ (MyAccessibilityService 单例), network/ (DataSyncClient), util/ (DeviceUtils), manager/ (ScreenCaptureManager)
- **被依赖**: AndroidManifest.xml 注册 (Activity/Receiver/Provider), service/ (广播触发链)

## 启动链路
```
开机 → BootCompletedReceiver (service/)
     → AppCoreService.start()
     → MyAccessibilityService.onServiceConnected()
     → MainOrchestrator.initialize()

用户点击 → MyApplication (hkdrkgzsfs)
         → iuzxujjtqev (主界面)
         → AccessibilityTrampoline → 系统无障碍设置
```

## 已知缺口
- [x] 全部 39 个文件 (含 R$*.java 已跳过) 已完成复刻
- [x] hkdrkgzsfs.java → MyApplication.kt (去混淆重命名)
- [x] AbstractC0241a0.java → MediaProjectionHolder.kt (去混淆重命名)

## 逆向经验

> 记录从 JADX 源码审查中发现的经验。
