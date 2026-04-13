# P000 Package Mapping — Minimal Blocking Set

> Only the p000 classes that are actively blocking rock/ functionality.
> Total: 12 classes, ~5,285 JADX LOC

## Tier 1 — Application Startup Blockers

| p000 Class | De-obfuscated Name | JADX LOC | Blocked By | Status |
|------------|-------------------|----------|-----------|--------|
| AbstractC1408xb | EncryptedConfigStore | 91 | hkdrkgzsfs.kt, yojggfhv.kt | done |
| RunnableC0941o6 | TaskRunnable | 786 | arniezsqllm.kt, hkdrkgzsfs.kt | done |
| RunnableC1052p1 | TypedRunnable | 429 | RecentsGuardManager | done |
| pk1 | IndexedRunnable | 1,355 | UninstallProtectionManager, MainOrchestrator | done |
| nk1 | IndexedRunnable2 | 320 | UninstallProtectionManager | done |

## Tier 2 — Protection + WebView Blockers

| p000 Class | De-obfuscated Name | JADX LOC | Blocked By | Status |
|------------|-------------------|----------|-----------|--------|
| dh0 | DangerKeywords | 371 | UninstallProtectionManager | done |
| fb1 | SearchBarViewIds | 11 | RecentsGuardManager, UninstallProtectionManager | done |
| gb1 | UninstallDialogKeywords | 51 | UninstallProtectionManager | done |
| am0 | FullscreenBlockerView | 131 | UninstallProtectionManager | done |
| mk1 | WebViewJsBridge | 34 | jbqfkndyx.kt | done |

## Tier 3 — Activity Blockers

| p000 Class | De-obfuscated Name | JADX LOC | Blocked By | Status |
|------------|-------------------|----------|-----------|--------|
| AbstractC1117qo | PermissionHelper | 1,477 | todoqkrxcctl.kt, htvekhdt.kt, hkdrkgzsfs.kt | done |
| C0107as | AppStatusManager | 229 | yrsanyhsbh.kt, izvpcqplqctn.kt, CipherCaptureManager | done |

## Not Replicated (Kotlin native replacements)

| p000 Class | Kotlin Replacement | Reason |
|------------|-------------------|--------|
| t60 | `android.util.Log` | Logging wrapper — Log.d/i/w/e suffices |
| AbstractC0779a1 | Kotlin `String.contains/startsWith` | String extensions — Kotlin stdlib has equivalents |
| w00 | `() -> T` | Lambda interface — Kotlin native |
| y90 | `by lazy {}` | Lazy holder — Kotlin native |
| AbstractC0770a1 | `mapOf()` | Map factory — Kotlin native |
| AbstractC0767a0 | `lazy {}` | Lazy factory — Kotlin native |
| AbstractC0003a2 | `StringBuilder` | StringBuilder utils — Kotlin native |
| AbstractC0715je | `joinToString()`, `+` | Collection utils — Kotlin native |
| AbstractC0134bh | `arrayOf()`, `toSet()` | Array utils — Kotlin native |
| AbstractC0716jf/jg/jk | `listOf()`, `mutableListOf()` | Collection factories — Kotlin native |
| AbstractC1120qr | *(constructor marker)* | Synthetic class — no runtime behavior |
| j80 | *(annotation)* | Obfuscation marker — no runtime behavior |
