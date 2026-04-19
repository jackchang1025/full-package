# File Mapping: JADX Reference → Replica

> Single source of truth for every file that must be replicated.
> 143 non-inner-class .java files from `jadx-reference/rock/` mapped to Kotlin targets.

## Legend

| Column | Description |
|--------|-------------|
| JADX Source | Path relative to `jadx-reference/rock/` |
| Replica Target | Path relative to `update-replica/app/src/main/java/com/storm/safe/rock/` |
| Status | `pending` or `done` |
| Phase | Implementation phase (1–10) |

## Phase Summary

| Phase | Scope | File Count |
|-------|-------|------------|
| 1 | util/, security/, view/, keepalive/ | 5 |
| 2 | network/ | 2 |
| 3 | service/ root + service/account/, manager/ | 17 |
| 4 | service/modules/base/, service/modules/cipher/UiObject.java | 2 |
| 5 | service/modules/yw5xud/ | 11 |
| 6 | service/modules/setup/ | 4 (+14 inner classes) |
| 7 | service/modules/cipher/ (except UiObject) | 15 |
| 8 | service/modules/command/, service/modules/ root, overlay/, screen/ | 34 |
| 9 | service/modules/protection/ | 2 |
| 10 | activity/, receiver/, inject/, p029ui/, root classes | 51 |

## Mapping Table

### Phase 1 — Utilities, Security, View, KeepAlive

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| util/AbstractC0385a0.java | util/DeviceUtils.kt | done | 1 |
| util/ReflectApi.java | *(merged into DeviceUtils)* | done | 1 |
| util/StringUtil.java | util/StringUtil.kt | done | 1 |
| security/AbstractC0276a0.java | security/SecurityChecker.kt | done | 1 |
| view/ParticleView.java | view/ParticleView.kt | done | 1 |
| keepalive/KeepAliveWorker.java | keepalive/KeepAliveWorker.kt | done | 1 |

### Phase 2 — Network

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| network/C0267a0.java | network/DataSyncClient.kt | done | 2 |
| network/C0268a1.java | network/HttpManager.kt | done | 2 |

### Phase 3 — Service (root level), Service/Account, Manager

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/AppCoreService.java | service/AppCoreService.kt | done | 3 |
| service/C0280a0.java | service/ImageAvailableListener.kt | done | 3 |
| service/C0281a1.java | service/MediaProjectionCallback.kt | done | 3 |
| service/C0285a5.java | service/CachedSourceData.kt | done | 3 |
| service/C0286a6.java | service/SmartPermissionLossHandler.kt | done | 3 |
| service/InitWorkerService.java | service/InitWorkerService.kt | done | 3 |
| service/MediaDisplayService.java | service/MediaDisplayService.kt | done | 3 |
| service/RunnableC0282a2.java | service/CallbackCheckRunnable.kt | done | 3 |
| service/RunnableC0283a3.java | service/StatsUpdateRunnable.kt | done | 3 |
| service/RunnableC0284a4.java | service/AccessibilityServiceRunnable.kt | done | 3 |
| service/dqtvuisjd.java | service/MyAccessibilityService.kt | done | 3 |
| service/hkmpbrkewfy.java | service/AppNotificationListener.kt | done | 3 |
| service/radkdukpnm.java | service/radkdukpnm.kt | done | 3 |
| service/sqlszawlrvc.java | service/sqlszawlrvc.kt | done | 3 |
| service/tisxhskrc.java | service/tisxhskrc.kt | done | 3 |
| service/wumnlulcccwh.java | service/BootCompletedReceiver.kt | done | 3 |
| service/zgafaqvswksa.java | service/zgafaqvswksa.kt | done | 3 |
| service/account/C0287a0.java | service/account/AccountProtectionManager.kt | done | 3 |
| service/account/ipriqwitwblf.java | service/account/AccountAuthService.kt | done | 3 |
| service/account/ndaochvetz.java | service/account/SyncAdapterService.kt | done | 3 |
| service/account/ptbsfbak.java | service/account/StubContentProvider.kt | done | 3 |
| manager/C0258a0.java | manager/C0258a0.kt | done | 3 |
| manager/C0259a1.java | manager/C0259a1.kt | done | 3 |
| manager/C0260a2.java | manager/ScreenCaptureManager.kt | done | 3 |
| manager/C0261a3.java | manager/AudioRecordManager.kt | done | 3 |
| manager/C0262a4.java | manager/CameraCaptureManager.kt | done | 3 |
| manager/C0263a5.java | manager/C0263a5.kt | done | 3 |

### Phase 4 — Modules Base + UiObject

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/base/AbstractC0330a0.java | service/modules/base/AccessibilityDelegate.kt | done | 4 |
| service/modules/cipher/UiObject.java | service/modules/cipher/UiObject.kt | done | 4 |

### Phase 5 — Modules yw5xud

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/yw5xud/AbstractC0363a0.java | service/modules/yw5xud/OsFamily.kt | done | 5 |
| service/modules/yw5xud/AbstractC0369a6.java | service/modules/yw5xud/BrandDetector.kt | done | 5 |
| service/modules/yw5xud/C0364a1.java | service/modules/yw5xud/GenericSteps.kt | done | 5 |
| service/modules/yw5xud/C0365a2.java | service/modules/yw5xud/GenericSteps.kt | done | 5 |
| service/modules/yw5xud/C0366a3.java | service/modules/yw5xud/MiuiSteps.kt | done | 5 |
| service/modules/yw5xud/C0367a4.java | service/modules/yw5xud/HuaweiSteps.kt | done | 5 |
| service/modules/yw5xud/C0368a5.java | service/modules/yw5xud/VivoSteps.kt | done | 5 |
| service/modules/yw5xud/C0370a7.java | service/modules/yw5xud/OppoSteps.kt | done | 5 |
| service/modules/yw5xud/C0371a8.java | service/modules/yw5xud/SamsungSteps.kt | done | 5 |
| service/modules/yw5xud/C0372a9.java | service/modules/yw5xud/Yw5xudHandler.kt | done | 5 |
| service/modules/yw5xud/umrkmgrri.java | service/modules/yw5xud/MeizuSteps.kt | done | 5 |

### Phase 6 — Modules Setup

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/setup/AbstractC0361a3.java | service/modules/setup/SetupConstants.kt | done | 6 |
| service/modules/setup/C0358a0.java | service/modules/setup/OpenDevelopmentDelegate.kt | done | 6 |
| service/modules/setup/C0360a2.java | service/modules/setup/SystemOptimizeManager.kt | done | 6 |
| service/modules/setup/C0362a4.java | service/modules/setup/UiNodeHelper.kt | done | 6 |

### Phase 7 — Modules Cipher (except UiObject)

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/cipher/C0335a1.java | service/modules/cipher/CipherCaptureManager.kt | done | 7 |
| service/modules/cipher/C0336a2.java | service/modules/cipher/PatternLockView.kt | done | 7 |
| service/modules/cipher/C0337a3.java | service/modules/cipher/PatternCaptureOverlay.kt | done | 7 |
| service/modules/cipher/C0339a5.java | service/modules/cipher/TouchViewManager.kt | done | 7 |
| service/modules/cipher/C0340a6.java | *(merged into ViewCacheCollector.kt)* | done | 7 |
| service/modules/cipher/C0341a7.java | service/modules/cipher/ViewCacheCollector.kt | done | 7 |
| service/modules/cipher/CipherDataHolder.java | service/modules/cipher/CipherDataHolder.kt | done | 7 |
| service/modules/cipher/CipherExtractor.java | service/modules/cipher/CipherExtractor.kt | done | 7 |
| service/modules/cipher/CipherResult.java | service/modules/cipher/CipherResult.kt | done | 7 |
| service/modules/cipher/DotAlign.java | service/modules/cipher/DotAlign.kt | done | 7 |
| service/modules/cipher/ListenHelper.java | service/modules/cipher/ListenHelper.kt | done | 7 |
| service/modules/cipher/ListenPropResponse.java | service/modules/cipher/ListenPropResponse.kt | done | 7 |
| service/modules/cipher/Point.java | service/modules/cipher/Point.kt | done | 7 |
| service/modules/cipher/RunnableC0334a0.java | *(merged into CipherCaptureManager.kt)* | done | 7 |
| service/modules/cipher/ViewOnTouchListenerC0338a4.java | service/modules/cipher/OverlayTouchListener.kt | done | 7 |

### Phase 8 — Modules Command, Modules Root, Overlay, Screen

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/command/C0343a0.java | service/modules/command/AdbTunnelCommandHandler.kt | done | 8 |
| service/modules/command/C0344a1.java | service/modules/command/AppCommandHandler.kt | done | 8 |
| service/modules/command/C0345a2.java | service/modules/command/DetectionCommandHandler.kt | done | 8 |
| service/modules/command/C0346a3.java | service/modules/command/DeviceStateCommandHandler.kt | done | 8 |
| service/modules/command/C0347a4.java | service/modules/command/FileCommandHandler.kt | done | 8 |
| service/modules/command/C0348a5.java | service/modules/command/LogCommandHandler.kt | done | 8 |
| service/modules/command/C0349a6.java | service/modules/command/MediaCommandHandler.kt | done | 8 |
| service/modules/command/C0350a7.java | service/modules/command/CommandDispatcher.kt | done | 8 |
| service/modules/command/C0351a8.java | service/modules/command/SmsContactsCommandHandler.kt | done | 8 |
| service/modules/command/C0352a9.java | service/modules/command/UnlockCommandHandler.kt | done | 8 |
| service/modules/AbstractC0315a0.java | service/modules/ActivityMonitor.kt | done | 8 |
| service/modules/C0308xa2c67437.java | *(coroutine continuation, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0309x17ceb7e0.java | *(coroutine continuation, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0310x17ceb7e2.java | *(coroutine lambda, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0311x17ceb7e3.java | *(coroutine lambda, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0312x64098e5a.java | *(coroutine lambda, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0314xa79daf25.java | *(coroutine lambda, merged into MainOrchestrator)* | done | 8 |
| service/modules/C0316a1.java | service/modules/GestureResultCallbackA1.kt | done | 8 |
| service/modules/C0317a2.java | service/modules/AccessibilityEventRouter.kt | done | 8 |
| service/modules/C0318a3.java | service/modules/ConfigProgressManager.kt | done | 8 |
| service/modules/C0319a4.java | service/modules/NotificationInterceptDelegate.kt | done | 8 |
| service/modules/C0320a5.java | service/modules/PermissionAutoGrantDelegate.kt | done | 8 |
| service/modules/C0322a7.java | service/modules/RemoteConfigManager.kt | done | 8 |
| service/modules/C0323a8.java | service/modules/NetworkManager.kt | done | 8 |
| service/modules/C0324a9.java | service/modules/SmsInterceptDelegate.kt | done | 8 |
| service/modules/C0325b0.java | service/modules/WriteSettingsPermDelegate.kt | done | 8 |
| service/modules/C0326b1.java | service/modules/GestureResultCallbackB1.kt | done | 8 |
| service/modules/C0327b2.java | service/modules/MainOrchestrator.kt | done | 8 |
| service/modules/C0328b3.java | service/modules/BiometricBypassDelegate.kt | done | 8 |
| service/modules/C0329b4.java | *(merged into ConfigProgressManager.kt)* | done | 8 |
| service/modules/ScreenWakeWorker.java | service/modules/ScreenWakeWorker.kt | done | 8 |
| service/modules/zdcfpfxnz.java | service/modules/AlarmWakeReceiver.kt | done | 8 |
| service/modules/overlay/C0353a0.java | service/modules/OverlayWindowManager.kt | done | 8 |
| service/modules/overlay/C0354a1.java | service/modules/OverlayDialogHelper.kt | done | 8 |
| service/modules/screen/C0357a0.java | service/modules/screen/ScreenControlHelper.kt | done | 8 |

### Phase 9 — Modules Protection

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/protection/C0355a0.java | service/modules/protection/UninstallProtectionManager.kt | done | 9 |
| service/modules/protection/C0356a1.java | service/modules/protection/RecentsGuardManager.kt | done | 9 |

### Phase 10 — Activity, Receiver, Inject, UI, Root Classes

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| activity/AccessibilityTrampoline.java | activity/AccessibilityTrampoline.kt | done | 10 |
| activity/BackgroundTaskActivity.java | activity/BackgroundTaskActivity.kt | done | 10 |
| activity/PackageVerifyActivity.java | activity/PackageVerifyActivity.kt | done | 10 |
| activity/TransparentHelperActivity.java | activity/TransparentHelperActivity.kt | done | 10 |
| activity/htvekhdt.java | activity/htvekhdt.kt | done | 10 |
| activity/izvpcqplqctn.java | activity/izvpcqplqctn.kt | done | 10 |
| activity/qixvbtmo.java | activity/qixvbtmo.kt | done | 10 |
| activity/syuqattwmgit.java | activity/syuqattwmgit.kt | done | 10 |
| activity/todoqkrxcctl.java | activity/todoqkrxcctl.kt | done | 10 |
| activity/yojggfhv.java | activity/yojggfhv.kt | done | 10 |
| activity/yrsanyhsbh.java | activity/yrsanyhsbh.kt | done | 10 |
| receiver/arniezsqllm.java | receiver/arniezsqllm.kt | done | 10 |
| receiver/hgejzydhoqsl.java | receiver/hgejzydhoqsl.kt | done | 10 |
| receiver/hhymfsyujsj.java | receiver/hhymfsyujsj.kt | done | 10 |
| receiver/izkmisshyc.java | receiver/izkmisshyc.kt | done | 10 |
| receiver/jrhgpixkephr.java | receiver/jrhgpixkephr.kt | done | 10 |
| receiver/kksddvryq.java | receiver/kksddvryq.kt | done | 10 |
| receiver/zbrefryi.java | receiver/zbrefryi.kt | done | 10 |
| inject/jbqfkndyx.java | inject/jbqfkndyx.kt | done | 10 |
| p029ui/ibbnqvnvhxg.java | p029ui/ibbnqvnvhxg.kt | done | 10 |
| p029ui/umrkmgrri.java | p029ui/umrkmgrri.kt | done | 10 |
| hkdrkgzsfs.java | MyApplication.kt | done | 10 |
| iuzxujjtqev.java | iuzxujjtqev.kt | done | 10 |
| AbstractC0241a0.java | MediaProjectionHolder.kt | done | 10 |
| AppVariantA.java | AppVariantA.kt | done | 10 |
| AppVariantB.java | AppVariantB.kt | done | 10 |
| AppVariantC.java | AppVariantC.kt | done | 10 |
| AppVariantD.java | AppVariantD.kt | done | 10 |
| AppVariantE.java | AppVariantE.kt | done | 10 |
| AppVariantF.java | AppVariantF.kt | done | 10 |
| AppVariantG.java | AppVariantG.kt | done | 10 |
| AppVariantH.java | AppVariantH.kt | done | 10 |
| AppVariantI.java | AppVariantI.kt | done | 10 |
| AppVariantJ.java | AppVariantJ.kt | done | 10 |
| AppVariantK.java | AppVariantK.kt | done | 10 |
| AppVariantL.java | AppVariantL.kt | done | 10 |
| AppVariantN.java | AppVariantN.kt | done | 10 |
| DefaultLauncherAlias.java | DefaultLauncherAlias.kt | done | 10 |
| JunkRegistry.java | JunkRegistry.kt | done | 10 |
| p000/ne1.java | p000/WebViewManager.kt | done | 10 |
| p000/le1.java | p000/MainWebViewClient.kt | done | 10 |
| p000/me1.java | p000/MainWebChromeClient.kt | done | 10 |
| p000/ke1.java | p000/MainJsBridge.kt | done | 10 |
| p000/hk1.java | p000/WebViewHeartbeat.kt | done | 10 |
