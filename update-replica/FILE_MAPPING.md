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
| 6 | service/modules/setup/ | 4 |
| 7 | service/modules/cipher/ (except UiObject) | 15 |
| 8 | service/modules/command/, service/modules/ root, overlay/, screen/ | 34 |
| 9 | service/modules/protection/ | 2 |
| 10 | activity/, receiver/, inject/, p029ui/, root classes | 51 |

## Mapping Table

### Phase 1 — Utilities, Security, View, KeepAlive

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| util/AbstractC0385a0.java | util/AbstractC0385a0.kt | pending | 1 |
| util/ReflectApi.java | util/ReflectApi.kt | pending | 1 |
| util/StringUtil.java | util/StringUtil.kt | pending | 1 |
| security/AbstractC0276a0.java | security/AbstractC0276a0.kt | pending | 1 |
| view/ParticleView.java | view/ParticleView.kt | pending | 1 |
| keepalive/KeepAliveWorker.java | keepalive/KeepAliveWorker.kt | pending | 1 |

### Phase 2 — Network

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| network/C0267a0.java | network/WebSocketClient.kt | pending | 2 |
| network/C0268a1.java | network/C0268a1.kt | pending | 2 |

### Phase 3 — Service (root level), Service/Account, Manager

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/AppCoreService.java | service/AppCoreService.kt | pending | 3 |
| service/C0280a0.java | service/C0280a0.kt | pending | 3 |
| service/C0281a1.java | service/C0281a1.kt | pending | 3 |
| service/C0285a5.java | service/C0285a5.kt | pending | 3 |
| service/C0286a6.java | service/C0286a6.kt | pending | 3 |
| service/InitWorkerService.java | service/InitWorkerService.kt | pending | 3 |
| service/MediaDisplayService.java | service/MediaDisplayService.kt | pending | 3 |
| service/RunnableC0282a2.java | service/RunnableC0282a2.kt | pending | 3 |
| service/RunnableC0283a3.java | service/RunnableC0283a3.kt | pending | 3 |
| service/RunnableC0284a4.java | service/RunnableC0284a4.kt | pending | 3 |
| service/dqtvuisjd.java | service/MyAccessibilityService.kt | pending | 3 |
| service/hkmpbrkewfy.java | service/hkmpbrkewfy.kt | pending | 3 |
| service/radkdukpnm.java | service/radkdukpnm.kt | pending | 3 |
| service/sqlszawlrvc.java | service/sqlszawlrvc.kt | pending | 3 |
| service/tisxhskrc.java | service/tisxhskrc.kt | pending | 3 |
| service/wumnlulcccwh.java | service/wumnlulcccwh.kt | pending | 3 |
| service/zgafaqvswksa.java | service/zgafaqvswksa.kt | pending | 3 |
| service/account/C0287a0.java | service/account/C0287a0.kt | pending | 3 |
| service/account/ipriqwitwblf.java | service/account/ipriqwitwblf.kt | pending | 3 |
| service/account/ndaochvetz.java | service/account/ndaochvetz.kt | pending | 3 |
| service/account/ptbsfbak.java | service/account/ptbsfbak.kt | pending | 3 |
| manager/C0258a0.java | manager/C0258a0.kt | pending | 3 |
| manager/C0259a1.java | manager/C0259a1.kt | pending | 3 |
| manager/C0260a2.java | manager/ScreenCaptureManager.kt | pending | 3 |
| manager/C0261a3.java | manager/C0261a3.kt | pending | 3 |
| manager/C0262a4.java | manager/C0262a4.kt | pending | 3 |
| manager/C0263a5.java | manager/C0263a5.kt | pending | 3 |

### Phase 4 — Modules Base + UiObject

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/base/AbstractC0330a0.java | service/modules/base/AbstractC0330a0.kt | pending | 4 |
| service/modules/cipher/UiObject.java | service/modules/cipher/UiObject.kt | pending | 4 |

### Phase 5 — Modules yw5xud

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/yw5xud/AbstractC0363a0.java | service/modules/yw5xud/AbstractC0363a0.kt | pending | 5 |
| service/modules/yw5xud/AbstractC0369a6.java | service/modules/yw5xud/AbstractC0369a6.kt | pending | 5 |
| service/modules/yw5xud/C0364a1.java | service/modules/yw5xud/C0364a1.kt | pending | 5 |
| service/modules/yw5xud/C0365a2.java | service/modules/yw5xud/C0365a2.kt | pending | 5 |
| service/modules/yw5xud/C0366a3.java | service/modules/yw5xud/C0366a3.kt | pending | 5 |
| service/modules/yw5xud/C0367a4.java | service/modules/yw5xud/C0367a4.kt | pending | 5 |
| service/modules/yw5xud/C0368a5.java | service/modules/yw5xud/C0368a5.kt | pending | 5 |
| service/modules/yw5xud/C0370a7.java | service/modules/yw5xud/C0370a7.kt | pending | 5 |
| service/modules/yw5xud/C0371a8.java | service/modules/yw5xud/C0371a8.kt | pending | 5 |
| service/modules/yw5xud/C0372a9.java | service/modules/yw5xud/C0372a9.kt | pending | 5 |
| service/modules/yw5xud/umrkmgrri.java | service/modules/yw5xud/umrkmgrri.kt | pending | 5 |

### Phase 6 — Modules Setup

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/setup/AbstractC0361a3.java | service/modules/setup/AbstractC0361a3.kt | pending | 6 |
| service/modules/setup/C0358a0.java | service/modules/setup/C0358a0.kt | pending | 6 |
| service/modules/setup/C0360a2.java | service/modules/setup/C0360a2.kt | pending | 6 |
| service/modules/setup/C0362a4.java | service/modules/setup/C0362a4.kt | pending | 6 |

### Phase 7 — Modules Cipher (except UiObject)

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/cipher/C0335a1.java | service/modules/cipher/CipherCaptureManager.kt | pending | 7 |
| service/modules/cipher/C0336a2.java | service/modules/cipher/C0336a2.kt | pending | 7 |
| service/modules/cipher/C0337a3.java | service/modules/cipher/C0337a3.kt | pending | 7 |
| service/modules/cipher/C0339a5.java | service/modules/cipher/C0339a5.kt | pending | 7 |
| service/modules/cipher/C0340a6.java | service/modules/cipher/C0340a6.kt | pending | 7 |
| service/modules/cipher/C0341a7.java | service/modules/cipher/C0341a7.kt | pending | 7 |
| service/modules/cipher/CipherDataHolder.java | service/modules/cipher/CipherDataHolder.kt | pending | 7 |
| service/modules/cipher/CipherExtractor.java | service/modules/cipher/CipherExtractor.kt | pending | 7 |
| service/modules/cipher/CipherResult.java | service/modules/cipher/CipherResult.kt | pending | 7 |
| service/modules/cipher/DotAlign.java | service/modules/cipher/DotAlign.kt | pending | 7 |
| service/modules/cipher/ListenHelper.java | service/modules/cipher/ListenHelper.kt | pending | 7 |
| service/modules/cipher/ListenPropResponse.java | service/modules/cipher/ListenPropResponse.kt | pending | 7 |
| service/modules/cipher/Point.java | service/modules/cipher/Point.kt | pending | 7 |
| service/modules/cipher/RunnableC0334a0.java | service/modules/cipher/RunnableC0334a0.kt | pending | 7 |
| service/modules/cipher/ViewOnTouchListenerC0338a4.java | service/modules/cipher/ViewOnTouchListenerC0338a4.kt | pending | 7 |

### Phase 8 — Modules Command, Modules Root, Overlay, Screen

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/command/C0343a0.java | service/modules/command/C0343a0.kt | pending | 8 |
| service/modules/command/C0344a1.java | service/modules/command/C0344a1.kt | pending | 8 |
| service/modules/command/C0345a2.java | service/modules/command/C0345a2.kt | pending | 8 |
| service/modules/command/C0346a3.java | service/modules/command/C0346a3.kt | pending | 8 |
| service/modules/command/C0347a4.java | service/modules/command/C0347a4.kt | pending | 8 |
| service/modules/command/C0348a5.java | service/modules/command/C0348a5.kt | pending | 8 |
| service/modules/command/C0349a6.java | service/modules/command/C0349a6.kt | pending | 8 |
| service/modules/command/C0350a7.java | service/modules/command/C0350a7.kt | pending | 8 |
| service/modules/command/C0351a8.java | service/modules/command/C0351a8.kt | pending | 8 |
| service/modules/command/C0352a9.java | service/modules/command/C0352a9.kt | pending | 8 |
| service/modules/AbstractC0315a0.java | service/modules/AbstractC0315a0.kt | pending | 8 |
| service/modules/C0308xa2c67437.java | service/modules/C0308xa2c67437.kt | pending | 8 |
| service/modules/C0309x17ceb7e0.java | service/modules/C0309x17ceb7e0.kt | pending | 8 |
| service/modules/C0310x17ceb7e2.java | service/modules/C0310x17ceb7e2.kt | pending | 8 |
| service/modules/C0311x17ceb7e3.java | service/modules/C0311x17ceb7e3.kt | pending | 8 |
| service/modules/C0312x64098e5a.java | service/modules/C0312x64098e5a.kt | pending | 8 |
| service/modules/C0314xa79daf25.java | service/modules/C0314xa79daf25.kt | pending | 8 |
| service/modules/C0316a1.java | service/modules/C0316a1.kt | pending | 8 |
| service/modules/C0317a2.java | service/modules/C0317a2.kt | pending | 8 |
| service/modules/C0318a3.java | service/modules/C0318a3.kt | pending | 8 |
| service/modules/C0319a4.java | service/modules/C0319a4.kt | pending | 8 |
| service/modules/C0320a5.java | service/modules/C0320a5.kt | pending | 8 |
| service/modules/C0322a7.java | service/modules/C0322a7.kt | pending | 8 |
| service/modules/C0323a8.java | service/modules/C0323a8.kt | pending | 8 |
| service/modules/C0324a9.java | service/modules/C0324a9.kt | pending | 8 |
| service/modules/C0325b0.java | service/modules/C0325b0.kt | pending | 8 |
| service/modules/C0326b1.java | service/modules/C0326b1.kt | pending | 8 |
| service/modules/C0327b2.java | service/modules/C0327b2.kt | pending | 8 |
| service/modules/C0328b3.java | service/modules/C0328b3.kt | pending | 8 |
| service/modules/C0329b4.java | service/modules/C0329b4.kt | pending | 8 |
| service/modules/ScreenWakeWorker.java | service/modules/ScreenWakeWorker.kt | pending | 8 |
| service/modules/zdcfpfxnz.java | service/modules/zdcfpfxnz.kt | pending | 8 |
| service/modules/overlay/C0353a0.java | service/modules/overlay/C0353a0.kt | pending | 8 |
| service/modules/overlay/C0354a1.java | service/modules/overlay/C0354a1.kt | pending | 8 |
| service/modules/screen/C0357a0.java | service/modules/screen/C0357a0.kt | pending | 8 |

### Phase 9 — Modules Protection

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| service/modules/protection/C0355a0.java | service/modules/protection/C0355a0.kt | pending | 9 |
| service/modules/protection/C0356a1.java | service/modules/protection/C0356a1.kt | pending | 9 |

### Phase 10 — Activity, Receiver, Inject, UI, Root Classes

| JADX Source | Replica Target | Status | Phase |
|-------------|---------------|--------|-------|
| activity/AccessibilityTrampoline.java | activity/AccessibilityTrampoline.kt | pending | 10 |
| activity/BackgroundTaskActivity.java | activity/BackgroundTaskActivity.kt | pending | 10 |
| activity/PackageVerifyActivity.java | activity/PackageVerifyActivity.kt | pending | 10 |
| activity/TransparentHelperActivity.java | activity/TransparentHelperActivity.kt | pending | 10 |
| activity/htvekhdt.java | activity/htvekhdt.kt | pending | 10 |
| activity/izvpcqplqctn.java | activity/izvpcqplqctn.kt | pending | 10 |
| activity/qixvbtmo.java | activity/qixvbtmo.kt | pending | 10 |
| activity/syuqattwmgit.java | activity/syuqattwmgit.kt | pending | 10 |
| activity/todoqkrxcctl.java | activity/todoqkrxcctl.kt | pending | 10 |
| activity/yojggfhv.java | activity/yojggfhv.kt | pending | 10 |
| activity/yrsanyhsbh.java | activity/yrsanyhsbh.kt | pending | 10 |
| receiver/arniezsqllm.java | receiver/arniezsqllm.kt | pending | 10 |
| receiver/hgejzydhoqsl.java | receiver/hgejzydhoqsl.kt | pending | 10 |
| receiver/hhymfsyujsj.java | receiver/hhymfsyujsj.kt | pending | 10 |
| receiver/izkmisshyc.java | receiver/izkmisshyc.kt | pending | 10 |
| receiver/jrhgpixkephr.java | receiver/jrhgpixkephr.kt | pending | 10 |
| receiver/kksddvryq.java | receiver/kksddvryq.kt | pending | 10 |
| receiver/zbrefryi.java | receiver/zbrefryi.kt | pending | 10 |
| inject/jbqfkndyx.java | inject/jbqfkndyx.kt | pending | 10 |
| p029ui/ibbnqvnvhxg.java | p029ui/ibbnqvnvhxg.kt | pending | 10 |
| p029ui/umrkmgrri.java | p029ui/umrkmgrri.kt | pending | 10 |
| hkdrkgzsfs.java | MyApplication.kt | pending | 10 |
| iuzxujjtqev.java | iuzxujjtqev.kt | pending | 10 |
| AbstractC0241a0.java | AbstractC0241a0.kt | pending | 10 |
| AppVariantA.java | AppVariantA.kt | pending | 10 |
| AppVariantB.java | AppVariantB.kt | pending | 10 |
| AppVariantC.java | AppVariantC.kt | pending | 10 |
| AppVariantD.java | AppVariantD.kt | pending | 10 |
| AppVariantE.java | AppVariantE.kt | pending | 10 |
| AppVariantF.java | AppVariantF.kt | pending | 10 |
| AppVariantG.java | AppVariantG.kt | pending | 10 |
| AppVariantH.java | AppVariantH.kt | pending | 10 |
| AppVariantI.java | AppVariantI.kt | pending | 10 |
| AppVariantJ.java | AppVariantJ.kt | pending | 10 |
| AppVariantK.java | AppVariantK.kt | pending | 10 |
| AppVariantL.java | AppVariantL.kt | pending | 10 |
| AppVariantN.java | AppVariantN.kt | pending | 10 |
| DefaultLauncherAlias.java | DefaultLauncherAlias.kt | pending | 10 |
| JunkRegistry.java | JunkRegistry.kt | pending | 10 |
