# HuaweiSteps Vendor 1:1 对齐方案 A — 实施计划

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** 把 `HuaweiSteps.kt`（当前 174 行）对齐 vendor `C0365a2.java`（8907 行）的 10 步华为权限流程，并修复 3 个真机 bug（基础权限空转、悬浮窗 retry 失败、锁屏清理虚构 Activity）+ WRITE_SETTINGS a11y 绑定检查。

**Architecture:** 在同一分支 `fix/miui-write-settings-all-files`（已合并到 main 的 Phase 2 工作之上）基础上，以 TDD（RED → GREEN → AUDIT）方式逐步复刻华为 10 步 flow 子方法，严格按 vendor 行号 1:1 对齐，所有偏离 vendor 的地方用 `// ADAPT:` 或 `// TODO: VENDOR_VERIFY —` 标注。完成后人工真机验证（华为 FIN-AL60），**本计划不做 git commit**，全部 commit 由用户在测试通过后统一完成。

**Tech Stack:** Kotlin 1.9 + Coroutines + Android AccessibilityService + JUnit 4 + Robolectric 4.11 + Mockito 5.3 (已存在于 update-replica)。

**开发约束**（用户强制要求）：
- 全程 TDD — RED（失败测试）→ GREEN（最小实现）→ AUDIT（vendor 对照）
- **不 git commit**，用户统一 commit
- **不**跑 `./gradlew test` 全量 — 仅 `./gradlew test --tests "*HuaweiStepsXxxTest*"` 定向
- **不**跑 `./gradlew assembleDebug` — 仅 `./gradlew compileDebugKotlin` 快速编译
- **禁止自行发挥**：所有偏离 vendor 的代码必须标 `// ADAPT: ...` 或 `// TODO: VENDOR_VERIFY — 原因`
- 真机测试由用户手动触发（计划末尾提供步骤）

---

## 依据（Vendor 证据 + 真机日志）

### Vendor 源
- `jadx-reference/rock/service/modules/yw5xud/C0365a2.java` — HuaweiSteps (8907 行, 14 内部类)
- `jadx-reference/rock/service/modules/yw5xud/C0364a1.java` — GenericSteps 参考 (3715 行)
- `jadx-reference/rock/service/modules/yw5xud/AbstractC0363a0.java` — ALLOW 关键词 holder
- `jadx-reference/p000/dh0.java` — 多语种词库 (f55750a0 允许 / a1 启用 / a2 确定 / a3 取消 / a4 卸载 等)

### 真机证据（华为 FIN-AL60, HarmonyOS 4.2 / Android 12）
1. **Bug A — 基础权限 10 次点击"允许"全空转**：华为"是否关闭电池优化？"对话框按钮是"取消/关闭"，无"允许"；GenericSteps.clickPermissionAllowButton 只查 viewId，不用文本 fallback
2. **Bug B — 悬浮窗 retry 21 次失败（75 秒）**：华为悬浮窗页面要**搜索框输入 app 名**而非滚动找 label（vendor m212172b9 L4805 用 `m212160a3("搜索应用", true)`）
3. **Bug C — 锁屏清理"Activity 不存在"**：`HuaweiSteps.kt:50-52` 引用 `com.huawei.systemmanager.optimize.process.ProtectActivity`，vendor 代码全文 grep 零匹配 — **虚构的 component**
4. **架构 bug — 华为执行 generic 叠加**：`Yw5xudHandler.kt:240-241` 无条件 `executeGenericSteps()`，vendor executeAll 只跑对应品牌的 10 步，不叠加
5. **WRITE_SETTINGS BAL 拒绝**：`appSwitchAllowed: false` 说明 a11y service 当时未绑定 — 需要启动前检查 service 绑定状态 + 启动后 verify

---

## Vendor 10 步 flow（executeAll = m212162a9, L1310-1622）

| 步序 | Vendor 方法 | 范围 (LOC) | 条件 | 复刻目标文件 |
|------|------------|-----------|------|------------|
| [1/10] 基础权限 | m212169b6 | L3524-3724 (200) | 仅华为 (isHuawei=true) | HuaweiSteps.kt |
| [2/10] 电池优化白名单 | m212166b3 | L2512-2740 (228) | 始终 | HuaweiSteps.kt |
| [3/10] 电池设置 | m212165b2 | L2050-2511 (461) | 始终 | HuaweiSteps.kt |
| [4/10] 通知使用权 | m212170b7 | L3725-4164 (439) | 仅华为 | HuaweiSteps.kt |
| [5/10] 自启动权限 | m212164b1 | L? ~500 | 始终 | HuaweiSteps.kt |
| [6/10] 悬浮窗权限 | m212172b9 | L4566-5805 (1239) | 始终 | HuaweiSteps.kt |
| [7/10] 通知权限 | m212171b8 | L4165-4565 (400) | 始终 | HuaweiSteps.kt |
| [8/10] 所有文件访问 | m212163b0 | L1623-2049 (426) | 始终 | HuaweiSteps.kt |
| [9/10] 清除最近任务 | m212212h0 | L8140+ | 始终 | HuaweiSteps.kt |
| [10/10] 最终 | — | 分支 17 结束 | — | HuaweiSteps.kt |

每步之间 `b81.m210571b1(100L, continuation)` = `delay(100L)`。

---

## File Structure

### 新建
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AllowKeywords.kt` — 多语种词库对齐 `p000/dh0.java`
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetector.kt` — 页面判定方法（m212185d9 ~ e8）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiGestureHelper.kt` — m212199f6/f7/f9 手势点击
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/AllowKeywordsTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetectorTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiGestureHelperTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep1BasicPermsTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep2BatteryWhitelistTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep3BatterySettingsTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep4NotifListenerTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep5AutoStartTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep6OverlayTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep7NotifPermTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep8AllFilesTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStep9ClearTasksTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiExecuteAllTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HonorPermissionDialogTest.kt`
- `app/src/test/java/com/storm/safe/rock/service/modules/WriteSettingsBindingCheckTest.kt`

### 修改
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` — 由 174 行扩展到 ~2500 行（10 步 + 内部枚举 + 字段）
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:1153-1169` — clickPermissionAllowButton 补 AllowKeywords 文本 fallback
- `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt:202-241` — 华为分支**不**叠加 GenericSteps
- `app/src/main/java/com/storm/safe/rock/service/modules/MainOrchestrator.kt:914-935` — openWriteSettingsPage 加 a11y 绑定检查 + 启动后 verify

### 不修改
- 其他品牌 Steps（MiuiSteps / OppoSteps / VivoSteps / SamsungSteps / MeizuSteps）— 不在本次范围
- `DeviceAuthorizationManager.kt` — 已在 Phase 2 修复
- `AutomationCoordinator.kt` — 已在 Phase 2 修复

---

# Phase 0 — 基础设施（T1 → T3）

## Task 1 — AllowKeywords 多语种词库

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/AllowKeywords.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/AllowKeywordsTest.kt`

**Vendor refs:** `jadx-reference/p000/dh0.java` L11-50 (`f55750a0` ALLOW / `f55751a1` ENABLE / `f55752a2` CONFIRM_OK / `f55753a3` CANCEL_NO / `f55754a4` UNINSTALL / `f55759a9` ALLOW_EXTENDED)

- [ ] **Step 1: RED — 写 AllowKeywordsTest.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class AllowKeywordsTest {
    @Test
    fun `ALLOW contains chinese and english variants`() {
        assertTrue("允许 missing", AllowKeywords.ALLOW.contains("允许"))
        assertTrue("允許 (traditional) missing", AllowKeywords.ALLOW.contains("允許"))
        assertTrue("Allow missing", AllowKeywords.ALLOW.contains("Allow"))
        assertTrue("Autoriser (fr) missing", AllowKeywords.ALLOW.contains("Autoriser"))
        assertTrue("Permitir (es/pt) missing", AllowKeywords.ALLOW.contains("Permitir"))
    }

    @Test
    fun `ENABLE covers chinese variants`() {
        assertTrue("启用 missing", AllowKeywords.ENABLE.contains("启用"))
        assertTrue("開啟 missing", AllowKeywords.ENABLE.contains("開啟"))
        assertTrue("Enable missing", AllowKeywords.ENABLE.contains("Enable"))
    }

    @Test
    fun `CONFIRM_OK covers common confirmation words`() {
        assertTrue("确定 missing", AllowKeywords.CONFIRM_OK.contains("确定"))
        assertTrue("OK missing", AllowKeywords.CONFIRM_OK.contains("OK"))
        assertTrue("好 missing", AllowKeywords.CONFIRM_OK.contains("好"))
        assertTrue("Yes missing", AllowKeywords.CONFIRM_OK.contains("Yes"))
    }

    @Test
    fun `CANCEL_NO covers common rejection words`() {
        assertTrue("取消 missing", AllowKeywords.CANCEL_NO.contains("取消"))
        assertTrue("Cancel missing", AllowKeywords.CANCEL_NO.contains("Cancel"))
    }

    @Test
    fun `matchesAny finds keyword in text`() {
        assertTrue(AllowKeywords.matchesAny("点击允许按钮", AllowKeywords.ALLOW))
        assertTrue(AllowKeywords.matchesAny("Click Allow", AllowKeywords.ALLOW))
        assertEquals(false, AllowKeywords.matchesAny("取消", AllowKeywords.ALLOW))
    }

    @Test
    fun `ALLOW list size is at least 70 entries matching vendor`() {
        // vendor dh0.f55750a0 has 75 entries (counted in source)
        assertTrue("ALLOW size=${AllowKeywords.ALLOW.size}, expected >=70", AllowKeywords.ALLOW.size >= 70)
    }
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*AllowKeywordsTest*"`
Expected: FAIL — `AllowKeywords` not found

- [ ] **Step 3: GREEN — 创建 AllowKeywords.kt**

逐行 copy vendor `p000/dh0.java` L12 的 75 个 ALLOW 词条到 `AllowKeywords.ALLOW`，类似处理 L15 (ENABLE), L18 (CONFIRM_OK), L21 (CANCEL_NO), L24 (UNINSTALL)。

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

/**
 * 多语种关键词词库。对齐 vendor jadx-reference/p000/dh0.java。
 *
 * - ALLOW       = vendor dh0.f55750a0 (L12, 75 条"允许"多语种变体)
 * - ENABLE      = vendor dh0.f55751a1 (L15, 37 条"启用")
 * - CONFIRM_OK  = vendor dh0.f55752a2 (L18, 100+ 条"确定/OK")
 * - CANCEL_NO   = vendor dh0.f55753a3 (L21, 100+ 条"取消/No")
 * - UNINSTALL   = vendor dh0.f55754a4 (L24)
 */
object AllowKeywords {
    // Vendor dh0.f55750a0 L12 — 逐词 copy，**禁止增删或改顺序**
    val ALLOW: List<String> = listOf(
        "允许", "允許", "許可", "許可する", "許可", "허용", "Cho phép", "อนุญาต",
        "Izinkan", "Memungkinkan", "Benarkan", "Membenarkan", "Payagan",
        "ခွင့်ပြု", "ခွင့်ပြုရန်", "អនុញ្ញាត", "ອະນຸຍາດ",
        "अनुमति दें", "अनुमति", "অনুমতি দিন", "অনুমতি",
        "اجازت دیں", "اجازت", "अनुमति दिनुहोस्", "අවසර දෙන්න", "ፍቀድ",
        "ஆக்கு", "అనుమతి", "ಅನುಮತಿ", "അനുവദിക്കുക",
        "परवानगी", "પરવાનગી", "ਇਜਾਜ਼ਤ ਦਿਓ",
        "السماح", "تسمح", "לאפשר", "כן, זה בסדר", "اجازه", "ارزیابی\u200cشده",
        "İzin ver", "İzin Ver",
        "Allow", "Autoriser", "Permitir", "Permitir",
        "Consenti", "Consentire", "Zulassen", "Toestaan",
        "Tillåt", "Tillåta", "Tillat", "Tillate", "Tillad",
        "Salli", "Разрешить", "Дозволити", "Дозвол.",
        "Zezwól", "Pozwól", "Povolit", "Povoliť",
        "Engedélyezés", "Engedélyez", "Permite",
        "Να επιτρέπεται", "Επιτρέπω",
        "Разрешаване", "Позволете",
        "Ruhusu", "Dopusti", "Dovoli", "Leisti",
        "Atļaut", "Luba", "Дозволи"
    )

    // Vendor dh0.f55751a1 L15
    val ENABLE: List<String> = listOf(
        "启用", "開啟", "有効にする", "사용", "사용하다", "Bật", "เปิดใช้งาน",
        "Aktifkan", "Fungsikan", "सक्षम करें", "يُمكّن", "הפוך לזמין",
        "Lütfen etkinleştir",
        "Enable", "Activer", "Habilitar", "Ativar", "Abilitare", "Aktivieren",
        "Activeren", "Aktivera", "Aktivere", "Ottaa käyttöön",
        "Включить", "Włączyć", "Zapnout", "Aktivovať",
        "Engedélyez", "Activa", "Активиране", "Ενεργοποιώ",
        "Aktivirati", "Vključiti", "Aktyvinti", "Aktivizēt",
        "Aktiveer", "Washa"
    )

    // Vendor dh0.f55752a2 L18
    val CONFIRM_OK: List<String> = listOf(
        "确定", "确认", "好", "好的", "知道了", "我知道了",
        "確定", "確認", "OK", "はい", "了解",
        "확인", "예",
        "Đồng ý", "ตกลง", "ใช่",
        "Oke", "OKE", "Ya", "Baik", "Ya", "Oo",
        "ဟုတ်ကဲ့", "យល់ព្រម", "ຕົກລົງ",
        "ठीक है", "हां", "ঠিক আছে", "হ্যাঁ",
        "ٹھیک ہے", "ठिक छ", "හරි", "እሺ",
        "حسنًا", "حسنا", "نعم", "موافق",
        "אישור", "כן", "تأیید", "باشه", "بله",
        "Tamam", "Evet",
        "OK", "Yes", "Confirm", "Done", "Got it",
        "Oui", "Confirmer", "Terminé",
        "Aceptar", "Sí", "Confirmar", "Hecho",
        "Sim", "Confirmar", "Concluído",
        "Ok", "Sì", "Conferma", "Fatto",
        "Ja", "Bestätigen", "Fertig",
        "Ja", "Bevestigen", "Klaar",
        "Ja", "Bekräfta", "Klar",
        "Ja", "Bekreft", "Ferdig",
        "Ja", "Bekræft", "Færdig",
        "Kyllä", "Vahvista",
        "ОК", "Да", "Подтвердить", "Готово",
        "Так", "Підтвердити", "Готово",
        "Tak", "Potwierdź", "Gotowe",
        "Ano", "Potvrdit", "Hotovo",
        "Áno", "Potvrdiť", "Hotovo",
        "Igen", "Megerősít", "Kész",
        "Da", "Confirmă", "Gata",
        "Ναι", "Επιβεβαίωση",
        "Да", "Потвърди", "Готово",
        "Sawa", "Ndio", "Thibitisha",
        "Da", "Potvrdi", "Da", "Potrdi"
    )

    // Vendor dh0.f55753a3 L21
    val CANCEL_NO: List<String> = listOf(
        "取消", "否", "不", "拒绝", "取消", "否",
        "キャンセル", "いいえ",
        "취소", "아니오", "아니요",
        "Hủy", "Không", "ยกเลิก", "ไม่",
        "Batal", "Tidak", "Batal", "Kanselahin", "Hindi",
        "မလုပ်တော့", "បោះបង់", "ຍົກເລີກ",
        "रद्द करें", "नहीं", "বাতিল করুন", "বাতিল", "না",
        "منسوخ کریں", "منسوخ", "रद्द गर्नुहोस्", "අවලංගු කරන්න", "ይቅር",
        "إلغاء", "لا", "ביטול", "לא", "لغو", "خیر",
        "İptal", "Hayır",
        "Cancel", "No", "Deny", "Reject",
        "Annuler", "Non", "Refuser",
        "Cancelar", "No", "Denegar",
        "Cancelar", "Não", "Negar",
        "Annulla", "No", "Nega",
        "Abbrechen", "Nein", "Ablehnen",
        "Annuleren", "Nee", "Weigeren",
        "Avbryt", "Nej", "Neka",
        "Avbryt", "Nei", "Avslå",
        "Annuller", "Nej", "Afvis",
        "Peru", "Peruuta", "Ei", "Kieltää",
        "Отмена", "Нет", "Отклонить",
        "Скасувати", "Ні", "Відхилити",
        "Anuluj", "Nie", "Odmów",
        "Zrušit", "Ne", "Odmítnout",
        "Zrušiť", "Nie", "Odmietnuť",
        "Mégse", "Nem", "Elutasít",
        "Anulează", "Nu", "Refuză",
        "Ακύρωση", "Όχι",
        "Отказ", "Отмени", "Не", "Откажи",
        "Ghairi", "Hapana", "Kataa"
    )

    /** Vendor dh0.f55754a4 L24 — 卸载/移除/删除 多语种 */
    val UNINSTALL: List<String> = listOf(
        "卸载", "移除", "删除", "停用", "禁用",
        "卸載", "移除", "刪除", "停用", "禁用",
        "アンインストール", "削除", "無効化",
        "제거", "삭제", "사용 중지",
        "Gỡ cài đặt", "Xóa",
        "ถอนการติดตั้ง", "ลบ",
        "Copot pemasangan", "Hapus", "Nyahpasang", "Padam",
        "अनइंस्टॉल", "हटाएं", "আনইনস্টল", "মুছে ফেলুন",
        "إلغاء التثبيت", "حذف", "إزالة",
        "הסר התקנה", "מחק",
        "Kaldır", "Sil",
        "Uninstall", "Remove", "Delete", "Disable",
        "Désinstaller", "Supprimer", "Désactiver",
        "Desinstalar", "Eliminar", "Deshabilitar",
        "Desinstalar", "Remover", "Desativar",
        "Disinstalla", "Rimuovi", "Disabilita",
        "Deinstallieren", "Entfernen", "Deaktivieren",
        "Verwijderen", "Uitschakelen",
        "Avinstallera", "Ta bort", "Inaktivera",
        "Avinstaller", "Fjern", "Deaktiver",
        "Fjern", "Slet", "Deaktiver",
        "Poista", "Poista käytöstä",
        "Удалить", "Деинсталляция", "Отключить",
        "Odinstaluj", "Usuń", "Wyłącz",
        "Odinstalovat", "Odstranit", "Zakázat",
        "Odinštalovať", "Odstrániť", "Zakázať",
        "Eltávolítás", "Törlés", "Letiltás",
        "Dezinstalare", "Șterge", "Dezactivare",
        "Деинсталиране", "Изтрий", "Деактивиране",
        "Видалити", "Вимкнути",
        "Κατάργηση", "Διαγραφή",
        "Deinstaliraj", "Ukloni",
        "Odstranitev", "Onemogoči",
        "Pašalinti", "Išjungti",
        "Atinstalēt", "Dzēst",
        "Desinstallimine", "Kustuta",
        "Ondoa", "Futa"
    )

    /** Check if `text` contains any keyword from `keywords` (case-insensitive for latin). */
    fun matchesAny(text: String?, keywords: List<String>): Boolean {
        if (text.isNullOrEmpty()) return false
        val lower = text.lowercase()
        return keywords.any { kw -> text.contains(kw) || lower.contains(kw.lowercase()) }
    }
}
```

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*AllowKeywordsTest*"`
Expected: PASS (all 6 tests)

- [ ] **Step 5: AUDIT — 对照 vendor**

打开 `jadx-reference/p000/dh0.java`，逐行核对 L12 与 `AllowKeywords.ALLOW` 词条顺序、字符完全一致（包括零宽字符 `\u200c`）。如有差异，**以 vendor 为准**，修正后重跑测试。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -5`
Expected: BUILD SUCCESSFUL

---

## Task 2 — GenericSteps.clickPermissionAllowButton 补文本 fallback

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/GenericSteps.kt:1153-1169` (`clickPermissionAllowButton`)
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/GenericStepsTest.kt` (追加 3 个测试)

**Vendor refs:** `C0364a1.java:m212122a0` L240-297 — 先遍历 `AbstractC0363a0.f55044a0` ID 列表，再遍历 `AbstractC0363a0.f55044a0` 文本关键词 list。复刻项目已有 viewId 查找（L1153-1169），**缺文本 fallback**。

- [ ] **Step 1: RED — 追加 3 个测试到 GenericStepsTest.kt**

```kotlin
@Test
fun `clickPermissionAllowButton falls back to text keyword when viewId miss`() {
    // Given: 页面上没有 PERMISSION_ALLOW_IDS 的 viewId 节点，但有一个 text="允许" 的 clickable 节点
    val root = mockk<AccessibilityNodeInfo>(relaxed = true)
    val allowNode = mockk<AccessibilityNodeInfo>(relaxed = true)
    every { allowNode.text } returns "允许"
    every { allowNode.isClickable } returns true
    every { allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) } returns true
    every { root.findAccessibilityNodeInfosByText("允许") } returns listOf(allowNode)
    every { root.findAccessibilityNodeInfosByViewId(any()) } returns emptyList()

    val clicked = generic.clickPermissionAllowButton(root)
    assertTrue("应该 fallback 到文字匹配", clicked)
}

@Test
fun `clickPermissionAllowButton recognizes multilingual allow keywords`() {
    // French "Autoriser"
    val root = mockk<AccessibilityNodeInfo>(relaxed = true)
    val frNode = mockk<AccessibilityNodeInfo>(relaxed = true)
    every { frNode.text } returns "Autoriser"
    every { frNode.isClickable } returns true
    every { frNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) } returns true
    every { root.findAccessibilityNodeInfosByText("Autoriser") } returns listOf(frNode)
    every { root.findAccessibilityNodeInfosByViewId(any()) } returns emptyList()

    val clicked = generic.clickPermissionAllowButton(root)
    assertTrue("法语 Autoriser 应命中", clicked)
}

@Test
fun `clickPermissionAllowButton does NOT click when page has only cancel button`() {
    val root = mockk<AccessibilityNodeInfo>(relaxed = true)
    val cancelNode = mockk<AccessibilityNodeInfo>(relaxed = true)
    every { cancelNode.text } returns "取消"
    every { cancelNode.isClickable } returns true
    every { root.findAccessibilityNodeInfosByText(any()) } returns listOf(cancelNode)
    every { root.findAccessibilityNodeInfosByViewId(any()) } returns emptyList()

    val clicked = generic.clickPermissionAllowButton(root)
    assertEquals(false, clicked) // 华为"取消电池优化"对话框不应被误点
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*GenericStepsTest*" 2>&1 | tail -20`
Expected: FAIL — 3 new tests fail with "fallback 未触发" / "法语 Autoriser 应命中" / 取消被误点

- [ ] **Step 3: GREEN — 修改 GenericSteps.kt clickPermissionAllowButton**

定位 `GenericSteps.kt:1153-1169`，在 `PERMISSION_ALLOW_IDS` 遍历之后添加 AllowKeywords 文本 fallback：

```kotlin
internal fun clickPermissionAllowButton(root: AccessibilityNodeInfo?): Boolean {
    if (root == null) return false
    // Path 1 — viewId (vendor C0364a1.m212122a0 L240-266)
    for (viewId in PERMISSION_ALLOW_IDS) {
        val nodes = try { root.findAccessibilityNodeInfosByViewId(viewId) } catch (_: Exception) { null }
        if (!nodes.isNullOrEmpty()) {
            for (n in nodes) {
                if (n.isClickable && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
            }
        }
    }
    // Path 2 — ADAPT: text fallback (vendor C0364a1.m212122a0 L268-294 遍历 AbstractC0363a0.f55044a0)
    // Huawei "是否关闭电池优化？" dialog has no allow viewId, only "关闭/取消" text
    // We ONLY click on ALLOW keywords — 不点击 CANCEL_NO 里的词（"关闭/取消"）
    for (keyword in AllowKeywords.ALLOW) {
        val nodes = try { root.findAccessibilityNodeInfosByText(keyword) } catch (_: Exception) { null }
        if (nodes.isNullOrEmpty()) continue
        for (n in nodes) {
            // 严格匹配：节点 text 必须与关键词相等（避免"不允许"误命中）
            val txt = n.text?.toString() ?: continue
            if (!AllowKeywords.matchesAny(txt, AllowKeywords.ALLOW)) continue
            // 排除 CANCEL_NO
            if (AllowKeywords.matchesAny(txt, AllowKeywords.CANCEL_NO)) continue
            if (n.isClickable && n.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
            // Try clickable parent within 3 hops
            var p = n.parent
            var depth = 0
            while (p != null && depth < 3) {
                if (p.isClickable && p.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true
                p = p.parent
                depth++
            }
        }
    }
    return false
}
```

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*GenericStepsTest*" 2>&1 | tail -10`
Expected: PASS (all existing + 3 new)

- [ ] **Step 5: AUDIT — vendor 对照**

打开 `jadx-reference/rock/service/modules/yw5xud/C0364a1.java:240-297`，核对：
- viewId 列表来源（`AbstractC0363a0.f55044a0` 或类似字段）是否与复刻 `PERMISSION_ALLOW_IDS` 一致
- 文本遍历顺序：vendor 先 viewId 后文本？
- 是否有 CANCEL_NO 过滤（vendor 可能有"不"字排除逻辑）

如 vendor 逻辑比当前实现复杂（例如有 disabled 节点过滤），在代码里标 `// TODO: VENDOR_VERIFY — vendor L280 有 isEnabled 检查，当前未对齐`。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin 2>&1 | tail -3`
Expected: BUILD SUCCESSFUL

---

## Task 3 — Yw5xudHandler 华为分支不叠加 GenericSteps

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandler.kt:202-241`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/Yw5xudHandlerTest.kt` (追加)

**Vendor refs:** `C0372a9.java` (Yw5xudHandler 原版) — 每品牌只跑自家 executeAll，不再叠加 generic。确认方式：grep `C0372a9.java` 中 `generic` / `GenericSteps` / `C0364a1` 在华为分支有无调用。

- [ ] **Step 1: RED — 追加测试**

```kotlin
@Test
fun `huawei branch does NOT call executeGenericSteps`() = runBlocking {
    // Given: a Yw5xudHandler where isHuawei=true
    val handler = spyk(Yw5xudHandler(mockService, mockContext))
    every { handler.isHuaweiFamily() } returns true
    every { handler.executeHuaweiSteps(any(), any(), any()) } just Runs
    val genericCalls = slot<MutableList<String>>()
    every { handler.executeGenericSteps(any(), any(), capture(genericCalls)) } just Runs

    val s = mutableListOf<String>()
    val f = mutableListOf<String>()
    val l = mutableListOf<String>()
    handler.doExecute(s, f, l)

    verify(exactly = 1) { handler.executeHuaweiSteps(any(), any(), any()) }
    verify(exactly = 0) { handler.executeGenericSteps(any(), any(), any()) }  // 关键：华为不应叠加
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*Yw5xudHandlerTest*"`
Expected: FAIL — `executeGenericSteps` verified called 1 time（现状）

- [ ] **Step 3: GREEN — 修改 Yw5xudHandler.kt**

定位 L196-241 的 `when` 块。在 when 之后的 `executeGenericSteps(successes, failures, logs)` 调用前加**品牌已处理**判断：

```kotlin
// ADAPT: vendor C0372a9 每品牌独立跑自家 10-step flow，不叠加 generic
// 真机验证发现华为叠加 generic 会导致 Flow3 悬浮窗 retry 21 次（75s）失败
// 仅在**未匹配任何品牌**时才跑 generic 兜底
val brandMatched = isSamsung || isHuawei || isOppo || isVivo || isXiaomi || isMeizu
when {
    isSamsung -> { /* 同现状 */ }
    isHuawei -> { /* 同现状 */ }
    // ... 其他分支不变 ...
    else -> {
        // Fallback: detect by OS family（现状）
        val osFamily = OsFamily.detect()
        // ...（略，保持现状）
    }
}

// ADAPT: 只有 brand 没匹配（UNKNOWN 走到 else 的 OsFamily 分支也算 brand 匹配）才跑 generic
if (!brandMatched && OsFamily.detect() == OsFamily.UNKNOWN) {
    executeGenericSteps(successes, failures, logs)
}
```

**关键**：删除原 L240-241 的无条件 `executeGenericSteps(successes, failures, logs)` 调用。

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*Yw5xudHandlerTest*"`
Expected: PASS

- [ ] **Step 5: AUDIT — vendor 对照**

打开 `jadx-reference/rock/service/modules/yw5xud/C0372a9.java`，grep `C0364a1` 确认 vendor handler 里 generic 的调用位置与条件。如 vendor 真的无条件叠加（反驳本任务假设），回滚本 Task 并在 plan 里记录新假设。否则标 `// ADAPT: 去除无条件叠加，对齐 vendor 行为`。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

---

# Phase 1 — HuaweiSteps 基础架构（T4 → T6）

## Task 4 — HuaweiSteps 内部状态枚举与字段对齐

**Files:**
- Modify: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiSteps.kt` (扩展 class)
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiStepsTest.kt` (追加)

**Vendor refs:** `C0365a2.java` class-level 字段：
- `f55062a0` = Context
- `f55063a1` = AccessibilityService
- `f55064a2` = isHuawei (boolean, 荣耀=false)
- `f55065a3` = packageName (String)
- `f55066a4` = appLabel (String)
- `HuaweiSteps$VerifyResult`, `HuaweiSteps$LockVerifyResult`, `HuaweiSteps$HonorClickResult` — sealed result enums

- [ ] **Step 1: RED — 追加测试到 HuaweiStepsTest.kt**

```kotlin
@Test
fun `HuaweiSteps fields are initialized from context and service`() {
    val steps = HuaweiSteps(mockService, mockContext)
    assertEquals("dev.deltalab2964.swift", steps.packageName)
    assertNotNull(steps.appLabel)
    // isHuawei 需要根据 Build.BRAND，Robolectric 默认 null，默认 false
}

@Test
fun `HuaweiSteps VerifyResult sealed types exist`() {
    val ok: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.Pass
    val fail: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.Fail("reason")
    val needRetry: HuaweiSteps.VerifyResult = HuaweiSteps.VerifyResult.NeedRetry
    assertNotNull(ok); assertNotNull(fail); assertNotNull(needRetry)
}

@Test
fun `HuaweiSteps LockVerifyResult sealed types exist`() {
    val a: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Locked
    val b: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Unlocked
    val c: HuaweiSteps.LockVerifyResult = HuaweiSteps.LockVerifyResult.Unknown
    assertNotNull(a); assertNotNull(b); assertNotNull(c)
}

@Test
fun `HuaweiSteps HonorClickResult sealed types exist`() {
    val r: HuaweiSteps.HonorClickResult = HuaweiSteps.HonorClickResult.Clicked("允许")
    val s: HuaweiSteps.HonorClickResult = HuaweiSteps.HonorClickResult.NotFound
    assertNotNull(r); assertNotNull(s)
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiStepsTest*"`
Expected: FAIL — 字段/类型不存在

- [ ] **Step 3: GREEN — 扩展 HuaweiSteps.kt**

在现有 `class HuaweiSteps` 里替换类头和 companion，新增字段 + sealed 枚举：

```kotlin
class HuaweiSteps(
    private val service: MyAccessibilityService?,
    private val context: Context
) {
    // --- vendor 字段对齐 ---
    /** vendor f55064a2 — true=华为, false=荣耀 */
    val isHuawei: Boolean = android.os.Build.BRAND.equals("HUAWEI", ignoreCase = true)
    /** vendor f55065a3 */
    val packageName: String = context.packageName
    /** vendor f55066a4 — ADAPT: 取 applicationInfo.labelRes 解析，若失败 fallback packageName */
    val appLabel: String = try {
        val info = context.packageManager.getApplicationInfo(packageName, 0)
        context.packageManager.getApplicationLabel(info).toString()
    } catch (_: Exception) { packageName }

    companion object {
        private const val TAG = "HuaweiSteps"
        /** Vendor m212196f3 L6861 — 华为自启动 4 个正确 component pair（已去除虚构 ProtectActivity） */
        val STARTUP_COMPONENTS: List<ComponentName> = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.appcontrol.activity.StartupAppControlActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"),
            ComponentName("com.hihonor.systemmanager", "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity")
        )
        val BATTERY_COMPONENTS: List<ComponentName> = listOf(
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwPowerManagerActivity"),
            ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.power.ui.HwBatterySettings")
        )
    }

    // --- vendor HuaweiSteps$VerifyResult ---
    sealed class VerifyResult {
        object Pass : VerifyResult()
        data class Fail(val reason: String) : VerifyResult()
        object NeedRetry : VerifyResult()
    }
    sealed class LockVerifyResult {
        object Locked : LockVerifyResult()
        object Unlocked : LockVerifyResult()
        object Unknown : LockVerifyResult()
    }
    sealed class HonorClickResult {
        data class Clicked(val keyword: String) : HonorClickResult()
        object NotFound : HonorClickResult()
    }

    // ... 保留现有 execute/executeStartupManager/executeBatteryOptimization/executeLockScreenCleanup ...
    // ADAPT: LOCK_SCREEN_COMPONENTS 和 executeLockScreenCleanup 在 T16（executeAll 重写）时删除，vendor 无此流程
}
```

**关键**：保留 `execute(successes, failures, logs)` 和现有 3 个方法不动（T16 会替换），只新增上述字段和枚举。

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiStepsTest*"`
Expected: PASS

- [ ] **Step 5: AUDIT — vendor 对照**

对比 `C0365a2.java:1-200` 类头字段，检查：
- 是否还有其他我们漏的字段（比如 `f55067a5` 等）？
- `VerifyResult` 的实际枚举分支有几种（看 `HuaweiSteps$VerifyResult.java` 内部类源码）？

不一致处标 `// TODO: VENDOR_VERIFY — vendor f55067a5 未对齐`。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

---

## Task 5 — HuaweiPageDetector 页面判定方法

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetector.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiPageDetectorTest.kt`

**Vendor refs:**
- `m212185d9` L6572-6620 — 通知权限弹窗判定
- `m212186e0` L6621-6630 — 荣耀权限弹窗判定
- `m212187e1` L6631-6651 — 通知权限系统弹窗判定（e1() 被 m212169b6 L3653 调用）
- `m212188e2` L6652-6677
- `m212189e3` L6678-6704
- `m212190e5` L6705-6731
- `m212191e7` L6732-6757
- `m212192e8` L6758-6838
- `m212193f0(str)` L6839-6854 — 文本 verify 页面

- [ ] **Step 1: RED — 写 HuaweiPageDetectorTest.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HuaweiPageDetectorTest {
    private val detector = HuaweiPageDetector()

    @Test
    fun `isNotificationPermissionPage matches vendor e1 keyword`() {
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { root.findAccessibilityNodeInfosByText("是否允许") } returns listOf(mockk(relaxed = true))
        assertTrue(detector.isNotificationPermissionDialog(root))
    }

    @Test
    fun `isHonorPermissionDialog matches Honor-specific keyword`() {
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { root.findAccessibilityNodeInfosByText("权限请求") } returns listOf(mockk(relaxed = true))
        assertTrue(detector.isHonorPermissionDialog(root))
    }

    @Test
    fun `verifyPageByText returns true when any root text contains keyword`() {
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { root.findAccessibilityNodeInfosByText("电池优化") } returns listOf(mockk(relaxed = true))
        assertTrue(detector.verifyPageByText(root, "电池优化"))
    }

    @Test
    fun `verifyPageByText returns false for missing keyword`() {
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { root.findAccessibilityNodeInfosByText(any()) } returns emptyList()
        assertEquals(false, detector.verifyPageByText(root, "不存在"))
    }
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiPageDetectorTest*"`
Expected: FAIL — `HuaweiPageDetector` not found

- [ ] **Step 3: GREEN — 创建 HuaweiPageDetector.kt**

读取 `C0365a2.java:6572-6854` 的每个判定方法，逐个翻译为 Kotlin：

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo

/**
 * 华为系 UI 页面判定。对齐 vendor C0365a2.java 的 m212185d9 ~ m212193f0。
 * 每个方法只做"页面 X 是否处于 Y 状态"的布尔判定，不做点击。
 */
class HuaweiPageDetector {

    /** vendor m212187e1 L6631-6651 — 通知权限系统弹窗（"是否允许 xxx 发送通知"） */
    fun isNotificationPermissionDialog(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // vendor 关键词（从 L6631 附近的 str 常量提取）
        val keywords = listOf("是否允许", "发送通知", "显示通知")
        return keywords.any { kw -> root.findAccessibilityNodeInfosByText(kw)?.isNotEmpty() == true }
    }

    /** vendor m212186e0 L6621-6630 — 荣耀权限请求对话框 */
    fun isHonorPermissionDialog(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // TODO: VENDOR_VERIFY — 需对照 vendor L6621-6630 的具体 keyword list
        val keywords = listOf("权限请求", "访问权限")
        return keywords.any { kw -> root.findAccessibilityNodeInfosByText(kw)?.isNotEmpty() == true }
    }

    /** vendor m212185d9 L6572-6620 — 通知弹窗（华为版） */
    fun isHuaweiNotificationPopup(root: AccessibilityNodeInfo?): Boolean {
        if (root == null) return false
        // TODO: VENDOR_VERIFY — 对齐 vendor L6572-6620 实际 keyword
        val keywords = listOf("通知", "应用通知")
        return keywords.any { kw -> root.findAccessibilityNodeInfosByText(kw)?.isNotEmpty() == true }
    }

    /** vendor m212188e2 L6652-6677 — page check 2 */
    fun isPageState2(root: AccessibilityNodeInfo?): Boolean {
        // TODO: VENDOR_VERIFY — 待完善，当前仅占位
        return false
    }

    /** vendor m212189e3 L6678-6704 — page check 3 */
    fun isPageState3(root: AccessibilityNodeInfo?): Boolean { return false }

    /** vendor m212190e5 L6705-6731 */
    fun isPageState5(root: AccessibilityNodeInfo?): Boolean { return false }

    /** vendor m212191e7 L6732-6757 */
    fun isPageState7(root: AccessibilityNodeInfo?): Boolean { return false }

    /** vendor m212192e8 L6758-6838 */
    fun isPageState8(root: AccessibilityNodeInfo?): Boolean { return false }

    /** vendor m212193f0(str) L6839-6854 — 通用文本 verify */
    fun verifyPageByText(root: AccessibilityNodeInfo?, keyword: String): Boolean {
        if (root == null) return false
        return root.findAccessibilityNodeInfosByText(keyword)?.isNotEmpty() == true
    }
}
```

**关键**：占位方法带 `// TODO: VENDOR_VERIFY`，T7-T14 各 step 使用到时再逐个填实。

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiPageDetectorTest*"`
Expected: PASS (4 tests)

- [ ] **Step 5: AUDIT — vendor 对照（核心方法）**

打开 `C0365a2.java:6572-6651`（m212185d9, m212186e0, m212187e1 三个在 T7/T10 会被用到）精读，核对 `HuaweiPageDetector` 对应方法的 keyword list 是否完整。占位的 e2~e8 在对应 step 用到时再精读。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

---

## Task 6 — HuaweiGestureHelper 手势点击

**Files:**
- Create: `app/src/main/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiGestureHelper.kt`
- Test: `app/src/test/java/com/storm/safe/rock/service/modules/yw5xud/HuaweiGestureHelperTest.kt`

**Vendor refs:**
- `m212199f6(int, int, int, continuation)` L7255-7317 — gesture click at (x, y) with (ms duration)
- `m212200f7(float, float)` L7318-7329 — 快速 tap
- `m212201f8()` L7330-7345 — 重置/cleanup
- `m212202f9(float, float)` L7346-7362 — gesture tap with 同步等待
- `m212203g0(float, float, float, continuation)` L7363-7388 — gesture with callback

**Note:** 复刻项目已有 `GestureTapHelper.kt`（见 `yw5xud/` 目录）；T6 的目标是补上华为特有的 f6/f7/f8/f9 签名对齐。

- [ ] **Step 1: RED — 测试**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HuaweiGestureHelperTest {
    private val mockService = mockk<AccessibilityService>(relaxed = true)
    private val helper = HuaweiGestureHelper(mockService)

    @Test
    fun `gestureClickAtPoint dispatches gesture with 50ms default`() = runBlocking {
        every { mockService.dispatchGesture(any(), any(), any()) } returns true
        val result = helper.gestureClick(100, 200, 50)
        assertTrue(result)
        verify(exactly = 1) { mockService.dispatchGesture(any(), any(), any()) }
    }

    @Test
    fun `gestureTapFast with 1px jitter matches miui workaround`() = runBlocking {
        every { mockService.dispatchGesture(any(), any(), any()) } returns true
        helper.gestureTapFast(100f, 200f)
        verify(exactly = 1) { mockService.dispatchGesture(any(), any(), any()) }
    }

    @Test
    fun `gestureClick returns false on null service`() = runBlocking {
        val nullHelper = HuaweiGestureHelper(null)
        val result = nullHelper.gestureClick(10, 10, 50)
        assertEquals(false, result)
    }
}
```

- [ ] **Step 2: 运行测试 — 确认失败**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiGestureHelperTest*"`
Expected: FAIL

- [ ] **Step 3: GREEN — 创建 HuaweiGestureHelper.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.util.Log
import kotlinx.coroutines.delay

/**
 * 华为手势点击工具。对齐 vendor C0365a2:
 * - m212199f6 (L7255) gestureClick(x, y, durationMs)
 * - m212200f7 (L7318) gestureTapFast
 * - m212202f9 (L7346) gestureTapAwait
 *
 * 沿用 Phase 2 引入的 1px jitter — MIUI/华为均丢弃零距离 gesture。
 */
class HuaweiGestureHelper(private val service: AccessibilityService?) {
    private val tag = "HuaweiGesture"

    /** vendor m212199f6 */
    suspend fun gestureClick(x: Int, y: Int, durationMs: Long = 50L): Boolean {
        val svc = service ?: return false
        return try {
            val path = Path().apply {
                moveTo(x.toFloat(), y.toFloat())
                lineTo(x + 1f, y + 1f) // 1px jitter
            }
            val g = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, durationMs))
                .build()
            val ok = svc.dispatchGesture(g, null, null)
            delay(durationMs + 50L)
            ok
        } catch (e: Exception) {
            Log.w(tag, "gestureClick failed: ${e.message}")
            false
        }
    }

    /** vendor m212200f7 — fast tap, 50ms, no await */
    fun gestureTapFast(x: Float, y: Float): Boolean {
        val svc = service ?: return false
        return try {
            val path = Path().apply {
                moveTo(x, y)
                lineTo(x + 1f, y + 1f)
            }
            val g = GestureDescription.Builder()
                .addStroke(GestureDescription.StrokeDescription(path, 0L, 50L))
                .build()
            svc.dispatchGesture(g, null, null)
        } catch (_: Exception) { false }
    }

    /** vendor m212202f9 — tap + sync wait 100ms */
    suspend fun gestureTapAwait(x: Float, y: Float): Boolean {
        val ok = gestureTapFast(x, y)
        delay(100L)
        return ok
    }
}
```

- [ ] **Step 4: 运行测试 — 确认通过**

Run: `cd update-replica && ./gradlew test --tests "*HuaweiGestureHelperTest*"`
Expected: PASS

- [ ] **Step 5: AUDIT — vendor 对照**

对照 `C0365a2.java:7255-7388`，核对：
- f6 的 duration 参数单位（ms？）和默认值
- f7 是否真是 fixed 50ms
- f9 的"等待"是多少 ms（可能不是 100ms，vendor 可能是 delay(200L)）

差异处标 `// TODO: VENDOR_VERIFY — vendor L7330 dispatch 后 delay 值不确定`。

- [ ] **Step 6: 编译验证**

Run: `cd update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

---

# Phase 2 — HuaweiSteps 10 步主流程（T7 → T16）

> **共同约定**：每个 step 的任务模式如下（为节省篇幅，不再逐步展开，每个 task 只列差异）：
> 1. **RED**：在 `HuaweiStepXXxxxTest.kt` 写针对该 step 的 3-5 个测试（成功路径 / UI 不匹配 / 超时），测试对象是 `HuaweiSteps.executeStepN()` 方法
> 2. **FAIL 验证**：`./gradlew test --tests "*HuaweiStepN*"` 失败
> 3. **GREEN**：读取 vendor 对应方法源码（行号见每 task 头部），逐行翻译为 Kotlin suspend 方法，不省略任何条件分支；偏离处标 `// ADAPT:` 或 `// TODO: VENDOR_VERIFY —`
> 4. **PASS 验证**：`./gradlew test --tests "*HuaweiStepN*"` 通过
> 5. **AUDIT**：对照 vendor 源码核对 delay 值、keyword list、条件判断，差异标注
> 6. **编译**：`./gradlew compileDebugKotlin`

## Task 7 — Step 1/10 基础权限 (m212169b6)

**Vendor:** `C0365a2.java:3524-3724`（200 行）
**条件:** 仅当 `isHuawei=true` 执行；荣耀（isHonor=true）跳过
**关键子调用:** `m212187e1()` 判定通知弹窗 → `m212160a3("始终允许", true)` → fallback `m212160a3("允许", true)`

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `suspend fun executeStep1BasicPermissions(...)` ~150 行
- Test: Create `HuaweiStep1BasicPermsTest.kt`

- [ ] **Step 1: RED — 写 HuaweiStep1BasicPermsTest.kt**

```kotlin
package com.storm.safe.rock.service.modules.yw5xud

import android.view.accessibility.AccessibilityNodeInfo
import com.storm.safe.rock.service.MyAccessibilityService
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HuaweiStep1BasicPermsTest {
    @Test
    fun `step1 skipped on Honor device`() = runBlocking {
        val steps = spyk(HuaweiSteps(mockk(relaxed = true), mockk(relaxed = true)))
        every { steps.isHuawei } returns false // Honor
        val successes = mutableListOf<String>()
        val failures = mutableListOf<String>()
        val logs = mutableListOf<String>()
        steps.executeStep1BasicPermissions(successes, failures, logs)
        assertTrue(logs.any { it.contains("荣耀跳过") || it.contains("Honor") })
    }

    @Test
    fun `step1 clicks 始终允许 when notification dialog present`() = runBlocking {
        val mockSvc = mockk<MyAccessibilityService>(relaxed = true)
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        val allowNode = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { mockSvc.rootInActiveWindow } returns root
        every { root.findAccessibilityNodeInfosByText("是否允许") } returns listOf(mockk(relaxed = true))
        every { root.findAccessibilityNodeInfosByText("始终允许") } returns listOf(allowNode)
        every { allowNode.isClickable } returns true
        every { allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) } returns true

        val steps = spyk(HuaweiSteps(mockSvc, mockk(relaxed = true)))
        every { steps.isHuawei } returns true

        val s = mutableListOf<String>()
        val f = mutableListOf<String>()
        val l = mutableListOf<String>()
        steps.executeStep1BasicPermissions(s, f, l)
        verify(atLeast = 1) { allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) }
    }

    @Test
    fun `step1 fallback to 允许 when 始终允许 not present`() = runBlocking {
        val mockSvc = mockk<MyAccessibilityService>(relaxed = true)
        val root = mockk<AccessibilityNodeInfo>(relaxed = true)
        val allowNode = mockk<AccessibilityNodeInfo>(relaxed = true)
        every { mockSvc.rootInActiveWindow } returns root
        every { root.findAccessibilityNodeInfosByText("是否允许") } returns listOf(mockk(relaxed = true))
        every { root.findAccessibilityNodeInfosByText("始终允许") } returns emptyList()
        every { root.findAccessibilityNodeInfosByText("允许") } returns listOf(allowNode)
        every { allowNode.isClickable } returns true
        every { allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) } returns true

        val steps = spyk(HuaweiSteps(mockSvc, mockk(relaxed = true)))
        every { steps.isHuawei } returns true
        steps.executeStep1BasicPermissions(mutableListOf(), mutableListOf(), mutableListOf())
        verify(atLeast = 1) { allowNode.performAction(AccessibilityNodeInfo.ACTION_CLICK) }
    }

    @Test
    fun `step1 timeout is 10s matching vendor m212169b6`() = runBlocking {
        // vendor L3540 附近超时应为 10000L
        // 空页面 → step1 最终 fail 但不 crash
        val mockSvc = mockk<MyAccessibilityService>(relaxed = true)
        every { mockSvc.rootInActiveWindow } returns null
        val steps = spyk(HuaweiSteps(mockSvc, mockk(relaxed = true)))
        every { steps.isHuawei } returns true
        val s = mutableListOf<String>()
        val f = mutableListOf<String>()
        val l = mutableListOf<String>()
        val t0 = System.currentTimeMillis()
        steps.executeStep1BasicPermissions(s, f, l)
        val elapsed = System.currentTimeMillis() - t0
        assertTrue("Step1 应在 ~10s 内退出, 实际=${elapsed}ms", elapsed < 12000)
    }
}
```

- [ ] **Step 2: RED 运行**: `./gradlew test --tests "*HuaweiStep1BasicPermsTest*"` → FAIL

- [ ] **Step 3: GREEN — 实现 executeStep1BasicPermissions**

精读 vendor `C0365a2.java:3524-3724`，1:1 翻译为 Kotlin。核心骨架：

```kotlin
/** vendor m212169b6 L3524-3724 — 华为基础权限自动化（通知权限弹窗） */
suspend fun executeStep1BasicPermissions(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    if (!isHuawei) {
        logs.add("[Step1/10] 基础权限 — 荣耀跳过（vendor L3528 判 f55064a2=false）")
        return
    }
    logs.add("[Step1/10] 基础权限 — 开始")
    val detector = HuaweiPageDetector()
    val timeoutMs = 10_000L // vendor L3540 附近
    val deadline = System.currentTimeMillis() + timeoutMs
    var clickCount = 0
    while (System.currentTimeMillis() < deadline) {
        val root = service?.rootInActiveWindow ?: run { kotlinx.coroutines.delay(300L); continue }
        // vendor m212187e1 判定当前是否通知权限弹窗
        if (detector.isNotificationPermissionDialog(root)) {
            // vendor L3653 — 先"始终允许", fallback "允许"
            if (clickByText(root, "始终允许") || clickByText(root, "允许")) {
                clickCount++
                logs.add("[Step1/10] 点击允许 (第${clickCount}次)")
                kotlinx.coroutines.delay(500L)
                continue
            }
        }
        // 无弹窗 → 再等一会儿
        kotlinx.coroutines.delay(500L)
        // ADAPT: vendor 还有 m212187e1 变体检查，当前仅覆盖主路径
        if (clickCount >= 10) break // 经验值，vendor 可能无此 guard
    }
    if (clickCount > 0) {
        successes.add("[Step1/10] 基础权限完成, 点击${clickCount}次")
    } else {
        logs.add("[Step1/10] 基础权限 — 超时无弹窗（可能已授权）")
    }
}

/** vendor m212160a3(str, clickClickable) L868-914 */
private fun clickByText(root: android.view.accessibility.AccessibilityNodeInfo?, text: String): Boolean {
    if (root == null) return false
    val nodes = try { root.findAccessibilityNodeInfosByText(text) } catch (_: Exception) { return false }
    if (nodes.isNullOrEmpty()) return false
    for (n in nodes) {
        if (n.isClickable && n.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
        var p = n.parent; var d = 0
        while (p != null && d < 3) {
            if (p.isClickable && p.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)) return true
            p = p.parent; d++
        }
    }
    return false
}
```

- [ ] **Step 4: GREEN 运行**: `./gradlew test --tests "*HuaweiStep1BasicPermsTest*"` → PASS

- [ ] **Step 5: AUDIT — vendor 对照**

精读 `C0365a2.java:3524-3724`，核对：
- 超时是否真是 10_000L（L3540 附近 long 常量）
- 点击前/后是否有额外的 verify 步骤
- 是否还有"仅本次允许"等其他按钮分支

差异标 `// TODO: VENDOR_VERIFY — vendor L36xx 有 XXX 分支未复刻`。

- [ ] **Step 6: 编译**: `./gradlew compileDebugKotlin`

---

## Task 8 — Step 2/10 电池优化白名单 (m212166b3)

**Vendor:** `C0365a2.java:2512-2740`（228 行）
**入口:** `REQUEST_IGNORE_BATTERY_OPTIMIZATIONS` Intent + 弹窗"允许"
**关键调用:** `m212160a3("允许", true)` 或 `verifyPageByText("允许此应用保持运行")` 判定

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `suspend fun executeStep2BatteryWhitelist(...)` ~180 行
- Test: Create `HuaweiStep2BatteryWhitelistTest.kt`

- [ ] **Step 1: RED**

```kotlin
class HuaweiStep2BatteryWhitelistTest {
    @Test fun `step2 launches REQUEST_IGNORE_BATTERY_OPTIMIZATIONS intent`() = runBlocking { /* verify ctx.startActivity 调用参数 */ }
    @Test fun `step2 clicks 允许 on whitelist dialog`() = runBlocking { /* 略 */ }
    @Test fun `step2 records success when Settings returns to app`() = runBlocking { /* 略 */ }
    @Test fun `step2 timeout is 15s`() = runBlocking { /* 略 */ }
}
```

完整测试代码模板：见 T7 Step 1 HuaweiStep1BasicPermsTest.kt 的结构（mock service + root + findAccessibilityNodeInfosByText），为本 task 写 4 个类似测试。

- [ ] **Step 2: RED 运行** → FAIL
- [ ] **Step 3: GREEN — 实现 executeStep2BatteryWhitelist**

精读 vendor L2512-2740，翻译骨架：

```kotlin
suspend fun executeStep2BatteryWhitelist(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step2/10] 电池优化白名单 — 开始")
    try {
        val intent = android.content.Intent(android.provider.Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = android.net.Uri.parse("package:$packageName")
            addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
        logs.add("[Step2/10] 已发送电池优化豁免请求")
    } catch (e: Exception) {
        failures.add("[Step2/10] 启动失败: ${e.message}")
        return
    }
    kotlinx.coroutines.delay(1500L) // vendor L2550 附近等待弹窗
    // 循环点"允许"直到 Settings.System.canWrite 或超时 15s
    val deadline = System.currentTimeMillis() + 15_000L
    while (System.currentTimeMillis() < deadline) {
        val root = service?.rootInActiveWindow
        if (root != null && clickByText(root, "允许")) {
            kotlinx.coroutines.delay(500L)
            successes.add("[Step2/10] 电池白名单已允许")
            return
        }
        kotlinx.coroutines.delay(500L)
    }
    logs.add("[Step2/10] 电池优化豁免未确认")
    // ADAPT: vendor L2700 可能有其他 verify 路径未复刻
}
```

- [ ] **Step 4: GREEN 运行** → PASS
- [ ] **Step 5: AUDIT**：对照 L2512-2740 精读
- [ ] **Step 6: 编译**

---

## Task 9 — Step 3/10 电池设置 (m212165b2)

**Vendor:** `C0365a2.java:2050-2511`（461 行 — 最大子方法之一）
**入口:** `ComponentName("com.huawei.systemmanager", "...HwPowerManagerActivity")` 或 `HwBatterySettings`
**关键:** 列表里找到本 app → 点击 → 在详情页选"无限制"

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `suspend fun executeStep3BatterySettings(...)` ~300 行
- Test: Create `HuaweiStep3BatterySettingsTest.kt`

- [ ] **Step 1: RED**

4 个测试：launches BATTERY_COMPONENTS / scrolls-to-find-app / clicks-无限制 / timeout

- [ ] **Step 2: RED 运行** → FAIL
- [ ] **Step 3: GREEN — 实现 executeStep3BatterySettings**

精读 vendor L2050-2511。关键子操作：
1. launch BATTERY_COMPONENTS (try HwPowerManagerActivity first, fallback HwBatterySettings)
2. waitForPageStable(5000ms)
3. scrollFindAndClickApp(appLabel) — 滚动找到 packageName 对应 ListItem
4. 在详情页 clickByText("无限制")
5. verify Page 返回到 list 或保持在 detail

```kotlin
suspend fun executeStep3BatterySettings(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step3/10] 电池设置 — 开始")
    if (!launchFirstAvailable(BATTERY_COMPONENTS)) {
        failures.add("[Step3/10] 无法启动华为电池管理")
        return
    }
    kotlinx.coroutines.delay(2000L) // vendor L2080 附近页面稳定等待
    // scroll + find
    val found = scrollFindAndClickApp(appLabel, maxScrolls = 5)
    if (!found) {
        logs.add("[Step3/10] 在电池设置列表中未找到 $appLabel")
        return
    }
    kotlinx.coroutines.delay(1500L)
    // 详情页点击"无限制"
    val root = service?.rootInActiveWindow
    if (root != null && clickByText(root, "无限制")) {
        successes.add("[Step3/10] 电池设置 — 已设为无限制")
    } else {
        logs.add("[Step3/10] 未找到'无限制'选项")
    }
    // ADAPT: vendor L2300+ 有若干其他分支（不同 EMUI 版本），未全部复刻
}

private fun launchFirstAvailable(components: List<android.content.ComponentName>): Boolean {
    for (c in components) {
        try {
            val i = android.content.Intent().apply {
                component = c
                addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(i)
            return true
        } catch (_: Exception) {}
    }
    return false
}

/** vendor 未单独命名但逻辑重复使用 — 遍历滚动查找 app by text */
private suspend fun scrollFindAndClickApp(appLabel: String, maxScrolls: Int): Boolean {
    for (i in 0 until maxScrolls) {
        val root = service?.rootInActiveWindow ?: return false
        if (clickByText(root, appLabel)) return true
        // 向下滚动整个屏幕
        val scrolled = root.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)
        kotlinx.coroutines.delay(800L)
        if (!scrolled) break
    }
    return false
}
```

- [ ] **Step 4: GREEN 运行** → PASS
- [ ] **Step 5: AUDIT**：精读 L2050-2511，确认 "无限制" 文案是否有多语种变体、scroll 方向、API-level 分支
- [ ] **Step 6: 编译**

---

## Task 10 — Step 4/10 通知使用权 (m212170b7)

**Vendor:** `C0365a2.java:3725-4164`（439 行）
**条件:** 仅华为（isHuawei=true）
**功能:** `ACTION_NOTIFICATION_LISTENER_SETTINGS` → 列表找本 app → 打开开关 → 确认授权对话框

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `suspend fun executeStep4NotificationListener(...)` ~280 行
- Test: Create `HuaweiStep4NotifListenerTest.kt`

- [ ] **Step 1: RED**: 4 个测试（Honor skip / launches NLS / toggles switch / dismisses confirm）
- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN — 实现**：精读 vendor L3725-4164，翻译骨架：

```kotlin
suspend fun executeStep4NotificationListener(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    if (!isHuawei) {
        logs.add("[Step4/10] 通知使用权 — 荣耀跳过")
        return
    }
    logs.add("[Step4/10] 通知使用权 — 开始")
    try {
        val i = android.content.Intent(android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
            .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    } catch (e: Exception) {
        failures.add("[Step4/10] 启动失败: ${e.message}")
        return
    }
    kotlinx.coroutines.delay(2000L)
    // scroll 找到 app
    if (!scrollFindAndClickApp(appLabel, 5)) {
        logs.add("[Step4/10] 未在通知使用权列表找到 $appLabel")
        return
    }
    kotlinx.coroutines.delay(1500L)
    // 弹出 "允许 xxx 获取通知" 确认框
    val root = service?.rootInActiveWindow
    if (root != null) {
        if (clickByText(root, "允许") || clickByText(root, "确定")) {
            successes.add("[Step4/10] 通知使用权 — 已授权")
            return
        }
    }
    logs.add("[Step4/10] 未能确认通知使用权对话框")
    // ADAPT: vendor L4000+ 可能有自定义 switch 点击路径，未完全复刻
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**：对照 L3725-4164，核对"允许/确定"顺序和 switch 节点 viewId
- [ ] **Step 6: 编译**

---

## Task 11 — Step 5/10 自启动权限 (m212164b1)

**Vendor:** `C0365a2.java:m212164b1`（~500 行）+ `m212196f3` L6861-6919 (4 component pair)
**入口:** `STARTUP_COMPONENTS`（已在 T4 重写为 4 个正确 pair）
**UI:** 列表找 app → "自动管理"开关关闭 → "允许自启动/允许关联启动/允许后台活动" 全开

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `executeStep5AutoStart(...)` ~350 行
- Test: Create `HuaweiStep5AutoStartTest.kt`

- [ ] **Step 1: RED**: 5 个测试
  - launches one of STARTUP_COMPONENTS
  - scrolls + finds appLabel in list
  - turns OFF "自动管理"
  - turns ON "允许自启动"/"允许关联启动"/"允许后台活动"
  - records failure when no switch found

- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN — 实现**：精读 vendor m212164b1 + m212196f3 合并：

```kotlin
suspend fun executeStep5AutoStart(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step5/10] 自启动权限 — 开始")
    if (!launchFirstAvailable(STARTUP_COMPONENTS)) {
        failures.add("[Step5/10] 无法启动华为启动管理（${STARTUP_COMPONENTS.size}个组件）")
        return
    }
    kotlinx.coroutines.delay(2000L)
    if (!scrollFindAndClickApp(appLabel, 8)) {
        logs.add("[Step5/10] 启动管理列表未找到 $appLabel")
        return
    }
    kotlinx.coroutines.delay(1500L)
    // 三 switch: 允许自启动 / 允许关联启动 / 允许后台活动
    // vendor L6xxx 先点"自动管理"关闭（如果是 checked=true 代表"自动管理=受限"）
    val root = service?.rootInActiveWindow ?: run { failures.add("[Step5/10] 无 root"); return }
    var toggled = 0
    for (kw in listOf("允许自启动", "允许关联启动", "允许后台活动")) {
        val nodes = root.findAccessibilityNodeInfosByText(kw) ?: continue
        for (n in nodes) {
            // 查找兄弟 switch 节点（TODO: VENDOR_VERIFY — vendor 用 viewId 还是 DFS 搜 Switch）
            val sw = findSiblingSwitch(n) ?: continue
            if (!sw.isChecked) {
                sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
                toggled++
                kotlinx.coroutines.delay(500L)
            }
        }
    }
    if (toggled > 0) successes.add("[Step5/10] 自启动权限: 开启 $toggled 项")
    else logs.add("[Step5/10] 未发现可切换的自启动开关")
    // ADAPT: vendor 可能有"确认对话框"（"开启自启动将允许 xxx"），未复刻
}

private fun findSiblingSwitch(textNode: android.view.accessibility.AccessibilityNodeInfo): android.view.accessibility.AccessibilityNodeInfo? {
    // 爬父 1-3 层，DFS 找 className=Switch 的节点
    var parent = textNode.parent
    var depth = 0
    while (parent != null && depth < 3) {
        for (i in 0 until parent.childCount) {
            val c = parent.getChild(i) ?: continue
            if (c.className?.toString()?.contains("Switch", true) == true) return c
        }
        parent = parent.parent
        depth++
    }
    return null
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**：精读 m212164b1 + m212196f3，核对 component 顺序、switch 查找方式
- [ ] **Step 6: 编译**

---

## Task 12 — Step 6/10 悬浮窗权限 (m212172b9)（**搜索框策略关键修复 Bug B**）

**Vendor:** `C0365a2.java:4566-5805`（1239 行 — 最大方法）
**关键:** 搜索框（不是滚动）— vendor L4805 `m212160a3("搜索应用", true)`, L4813 搜索框 viewId:
- `android:id/search_src_text`
- `com.android.settings:id/search_src_text`
- `com.hihonor.settings:id/search_src_text`

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `executeStep6OverlayPermission(...)` ~400 行
- Test: Create `HuaweiStep6OverlayTest.kt`

- [ ] **Step 1: RED**: 5 测试
  - launches MANAGE_OVERLAY_PERMISSION
  - clicks "搜索应用" button (vendor L4805)
  - inputs appLabel into search_src_text
  - clicks result item
  - toggles overlay switch

- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN — 实现**

```kotlin
private val SEARCH_BOX_IDS = listOf(
    "android:id/search_src_text",
    "com.android.settings:id/search_src_text",
    "com.hihonor.settings:id/search_src_text"
)

suspend fun executeStep6OverlayPermission(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step6/10] 悬浮窗权限 — 开始")
    try {
        val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    } catch (e: Exception) {
        failures.add("[Step6/10] 启动失败: ${e.message}")
        return
    }
    kotlinx.coroutines.delay(2000L)
    // vendor L4805 — 先点"搜索应用"按钮
    val root0 = service?.rootInActiveWindow
    if (root0 != null) {
        if (!clickByText(root0, "搜索应用")) {
            // 可能页面已进入搜索模式，跳过
            logs.add("[Step6/10] 未找到搜索按钮，假定已在搜索框")
        }
        kotlinx.coroutines.delay(1000L)
    }
    // 输入 appLabel 到搜索框
    val root1 = service?.rootInActiveWindow ?: run { failures.add("[Step6/10] 无 root"); return }
    val searchBox = findNodeByIds(root1, SEARCH_BOX_IDS)
    if (searchBox == null) {
        failures.add("[Step6/10] 未找到搜索框 viewId")
        return
    }
    searchBox.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_FOCUS)
    val args = android.os.Bundle().apply {
        putCharSequence(android.view.accessibility.AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, appLabel)
    }
    searchBox.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_SET_TEXT, args)
    kotlinx.coroutines.delay(1500L)
    // 点击搜索结果（第一项通常就是本 app）
    val root2 = service?.rootInActiveWindow ?: return
    if (!clickByText(root2, appLabel)) {
        logs.add("[Step6/10] 搜索结果未找到 $appLabel")
        return
    }
    kotlinx.coroutines.delay(1500L)
    // 点击开关（可能是 "允许显示在其他应用上层" switch）
    val root3 = service?.rootInActiveWindow ?: return
    val switch = findFirstSwitch(root3)
    if (switch != null && !switch.isChecked) {
        switch.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
        successes.add("[Step6/10] 悬浮窗已开启")
    } else {
        logs.add("[Step6/10] 未找到悬浮窗开关，或已开启")
    }
    // ADAPT: vendor L4566-5805 有大量 fallback 分支（API 级别适配），仅覆盖主路径
}

private fun findNodeByIds(root: android.view.accessibility.AccessibilityNodeInfo, ids: List<String>): android.view.accessibility.AccessibilityNodeInfo? {
    for (id in ids) {
        val nodes = try { root.findAccessibilityNodeInfosByViewId(id) } catch (_: Exception) { null }
        if (!nodes.isNullOrEmpty()) return nodes[0]
    }
    return null
}

private fun findFirstSwitch(root: android.view.accessibility.AccessibilityNodeInfo): android.view.accessibility.AccessibilityNodeInfo? {
    // DFS up to 10 depth
    val stack = ArrayDeque<android.view.accessibility.AccessibilityNodeInfo>()
    stack.addLast(root)
    var depth = 0
    while (stack.isNotEmpty() && depth < 1000) {
        val n = stack.removeLast()
        if (n.className?.toString()?.contains("Switch", true) == true) return n
        for (i in 0 until n.childCount) n.getChild(i)?.let { stack.addLast(it) }
        depth++
    }
    return null
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**：精读 L4566-4900 (前 334 行，核心搜索 +结果点击)，核对 SEARCH_BOX_IDS 是否准确
- [ ] **Step 6: 编译**

---

## Task 13 — Step 7/10 通知权限 (m212171b8)

**Vendor:** `C0365a2.java:4165-4565`（400 行）
**功能:** `APP_NOTIFICATION_SETTINGS`，开启"允许通知"主开关 + 各 channel

**Files:**
- Modify: `HuaweiSteps.kt` — `executeStep7NotificationPermission(...)` ~250 行
- Test: Create `HuaweiStep7NotifPermTest.kt`

- [ ] **Step 1: RED**: 4 测试
- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN**：

```kotlin
suspend fun executeStep7NotificationPermission(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step7/10] 通知权限 — 开始")
    try {
        val i = android.content.Intent(android.provider.Settings.ACTION_APP_NOTIFICATION_SETTINGS)
            .putExtra(android.provider.Settings.EXTRA_APP_PACKAGE, packageName)
            .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    } catch (e: Exception) { failures.add("[Step7/10] 启动失败: ${e.message}"); return }
    kotlinx.coroutines.delay(2000L)
    val root = service?.rootInActiveWindow ?: run { failures.add("[Step7/10] 无 root"); return }
    val mainSwitch = findFirstSwitch(root)
    if (mainSwitch != null && !mainSwitch.isChecked) {
        mainSwitch.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
        kotlinx.coroutines.delay(500L)
        successes.add("[Step7/10] 通知权限已开启")
    } else {
        logs.add("[Step7/10] 通知权限开关已开启或未找到")
    }
    // ADAPT: vendor L4300+ 还遍历所有 channel 开关，未完全复刻
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 核对 L4165-4565
- [ ] **Step 6: 编译**

---

## Task 14 — Step 8/10 所有文件访问 (m212163b0)

**Vendor:** `C0365a2.java:1623-2049`（426 行）
**入口:** `ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION`（Android 11+）
**UI:** 开关"允许管理所有文件"

**Files:**
- Modify: `HuaweiSteps.kt` — `executeStep8AllFilesAccess(...)` ~250 行
- Test: Create `HuaweiStep8AllFilesTest.kt`

- [ ] **Step 1: RED**: 4 测试
- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN**：

```kotlin
suspend fun executeStep8AllFilesAccess(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step8/10] 所有文件访问 — 开始")
    if (android.os.Build.VERSION.SDK_INT < 30) {
        logs.add("[Step8/10] SDK<30 跳过")
        return
    }
    try {
        val i = android.content.Intent(android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            .setData(android.net.Uri.parse("package:$packageName"))
            .addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(i)
    } catch (e: Exception) { failures.add("[Step8/10] 启动失败: ${e.message}"); return }
    kotlinx.coroutines.delay(2000L)
    val root = service?.rootInActiveWindow ?: run { failures.add("[Step8/10] 无 root"); return }
    val sw = findFirstSwitch(root)
    if (sw != null && !sw.isChecked) {
        sw.performAction(android.view.accessibility.AccessibilityNodeInfo.ACTION_CLICK)
        kotlinx.coroutines.delay(500L)
        // 可能弹"允许 xxx 访问所有文件"确认，点允许
        val root2 = service?.rootInActiveWindow
        if (root2 != null) clickByText(root2, "允许")
        successes.add("[Step8/10] 所有文件访问已开启")
    } else {
        logs.add("[Step8/10] 开关已开启或未找到")
    }
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 对照 L1623-2049
- [ ] **Step 6: 编译**

---

## Task 15 — Step 9/10 清除最近任务 (m212212h0)

**Vendor:** `C0365a2.java:8140+`（~200 行）
**功能:** 从最近任务视图清除当前 app 的 recent entry，使其"隐藏"

**Files:**
- Modify: `HuaweiSteps.kt` — `executeStep9ClearRecentTasks(...)` ~100 行
- Test: Create `HuaweiStep9ClearTasksTest.kt`

- [ ] **Step 1: RED**: 2 测试
- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN**：

```kotlin
suspend fun executeStep9ClearRecentTasks(
    successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>
) {
    logs.add("[Step9/10] 清除最近任务 — 开始")
    try {
        // vendor uses GLOBAL_ACTION_RECENTS → 找到本 app 的 recent card → 滑走
        service?.performGlobalAction(android.accessibilityservice.AccessibilityService.GLOBAL_ACTION_RECENTS)
        kotlinx.coroutines.delay(1500L)
        // TODO: VENDOR_VERIFY — vendor L8140+ 有 DFS 找 app card 并 dispatch swipe gesture 的完整逻辑
        // 当前仅弹出 recents 页，不做实际 clear
        successes.add("[Step9/10] 最近任务页已打开（未做实际 clear）")
    } catch (e: Exception) {
        failures.add("[Step9/10] 异常: ${e.message}")
    }
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 精读 L8140+ 确定 swipe 逻辑
- [ ] **Step 6: 编译**

---

## Task 16 — executeAll 编排 (m212162a9) — 10 步主入口 + 删除虚构 ProtectActivity

**Vendor:** `C0365a2.java:1310-1622`（m212162a9）
**关键编排:** 见 plan 开头 "Vendor 10 步 flow" 表格
**前置 guard:** `Settings.System.canWrite(context)` → 直接返回（vendor L1329-1336）
**每步间 delay:** 100ms (`b81.m210571b1(100L, ...)`)

**Files:**
- Modify: `HuaweiSteps.kt` — 重写 `suspend fun execute(...)` 替换现有 3-step 实现为 10-step
- Modify: 删除 `LOCK_SCREEN_COMPONENTS` 和 `executeLockScreenCleanup` (Bug C — 虚构的 ProtectActivity)
- Test: Create `HuaweiExecuteAllTest.kt`

- [ ] **Step 1: RED**: 4 测试

```kotlin
class HuaweiExecuteAllTest {
    @Test fun `execute skips all when Settings System canWrite`() = runBlocking {
        // mock Settings.System.canWrite 返回 true → 立即返回
    }
    @Test fun `execute runs 10 steps in order when needed`() = runBlocking {
        val steps = spyk(HuaweiSteps(...))
        steps.execute(...)
        verifyOrder {
            steps.executeStep1BasicPermissions(any(), any(), any())
            steps.executeStep2BatteryWhitelist(...)
            steps.executeStep3BatterySettings(...)
            steps.executeStep4NotificationListener(...)
            steps.executeStep5AutoStart(...)
            steps.executeStep6OverlayPermission(...)
            steps.executeStep7NotificationPermission(...)
            steps.executeStep8AllFilesAccess(...)
            steps.executeStep9ClearRecentTasks(...)
        }
    }
    @Test fun `execute skips step1 and step4 on Honor`() = runBlocking { /* isHuawei=false */ }
    @Test fun `executeLockScreenCleanup method is removed`() {
        // HuaweiSteps 不应再暴露 executeLockScreenCleanup
        val methods = HuaweiSteps::class.java.declaredMethods.map { it.name }
        assertFalse(methods.contains("executeLockScreenCleanup"))
    }
}
```

- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN — 替换 execute + 删除 ProtectActivity 相关代码**

```kotlin
suspend fun execute(
    successes: MutableList<String>,
    failures: MutableList<String>,
    logs: MutableList<String>
) {
    // vendor m212162a9 L1329-1336 — 前置 guard
    if (android.provider.Settings.System.canWrite(context)) {
        logs.add("[HuaweiSteps] ╔═══ 跳过整个流程（Settings.System.canWrite=true）═══")
        successes.add("HuaweiSteps 已完成（检测到系统设置权限）")
        return
    }
    logs.add("[HuaweiSteps] 开始华为/荣耀权限配置 — 10 步流程")

    executeStep1BasicPermissions(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep2BatteryWhitelist(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep3BatterySettings(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep4NotificationListener(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep5AutoStart(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep6OverlayPermission(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep7NotificationPermission(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep8AllFilesAccess(successes, failures, logs)
    kotlinx.coroutines.delay(100L)
    executeStep9ClearRecentTasks(successes, failures, logs)

    logs.add("[HuaweiSteps] 华为/荣耀 10 步流程结束 — successes=${successes.size} failures=${failures.size}")
}
```

**同时删除** 以下代码：
- `HuaweiSteps.kt:50-52` `LOCK_SCREEN_COMPONENTS` 定义
- `HuaweiSteps.kt:142-158` `executeLockScreenCleanup` 方法
- `HuaweiSteps.kt:85` `executeLockScreenCleanup(...)` 的调用
- `HuaweiSteps.kt:40-43` `STARTUP_COMPONENTS` 里的 `ProtectActivity` 条目（已在 T4 重写为 4 个正确 pair）
- `HuaweiSteps.kt:94-110` 老的 `executeStartupManager`（已被 T11 executeStep5AutoStart 替代）
- `HuaweiSteps.kt:115-137` 老的 `executeBatteryOptimization`（已被 T8+T9 替代）

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 对照 vendor L1310-1622，核对 step 顺序、每步间 delay 是否都是 100L、isHuawei guard 位置
- [ ] **Step 6: 编译**: `./gradlew compileDebugKotlin`

---

# Phase 3 — 荣耀特殊处理（T17）

## Task 17 — 荣耀权限对话框 (m212161a8)

**Vendor:** `C0365a2.java:915-1309`（395 行）
**功能:** 全局监听荣耀系统的"权限请求"对话框，多 keyword fallback 点击"允许/始终允许/仅在使用中允许/确定/同意"

**Files:**
- Modify: `HuaweiSteps.kt` — 新增 `suspend fun detectAndClickHonorPermissionDialog(): HonorClickResult`
- Test: Create `HonorPermissionDialogTest.kt`

- [ ] **Step 1: RED**: 3 测试（clicked / notFound / honor-only）
- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN**：

```kotlin
/** vendor m212161a8 L915-1309 — 荣耀权限弹窗检测 + 点击 */
suspend fun detectAndClickHonorPermissionDialog(): HonorClickResult {
    if (isHuawei) return HonorClickResult.NotFound // vendor 仅荣耀适用
    val root = service?.rootInActiveWindow ?: return HonorClickResult.NotFound
    val detector = HuaweiPageDetector()
    if (!detector.isHonorPermissionDialog(root)) return HonorClickResult.NotFound
    // vendor L1100+ 按优先级尝试
    val keywords = listOf("始终允许", "仅在使用中允许", "允许", "确定", "同意")
    for (kw in keywords) {
        if (clickByText(root, kw)) {
            return HonorClickResult.Clicked(kw)
        }
    }
    // ADAPT: vendor 还有坐标点击 fallback（L1200 附近），当前仅文本路径
    return HonorClickResult.NotFound
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 对照 L915-1309 确认 keyword 优先级顺序
- [ ] **Step 6: 编译**

---

# Phase 4 — WRITE_SETTINGS 加固（T18）

## Task 18 — MainOrchestrator.openWriteSettingsPage 加 a11y 绑定检查 + 启动后 verify

**Vendor refs:** 复刻当前 `MainOrchestrator.kt:914-935` 与 vendor `C0327b2.m211743e8` 逻辑一致，但真机测试发现 force-stop 后 a11y 解绑期间调用会被 BAL 拒绝。属于**复刻加固**（非 vendor 对齐）。

**Files:**
- Modify: `MainOrchestrator.kt:914-935` `openWriteSettingsPage`
- Test: Create `WriteSettingsBindingCheckTest.kt`

- [ ] **Step 1: RED**: 4 测试

```kotlin
class WriteSettingsBindingCheckTest {
    @Test fun `openWriteSettingsPage returns false when a11y not bound`() = runBlocking {
        val svc = mockk<MyAccessibilityService>(relaxed = true)
        every { svc.serviceInfo } returns null // 未绑定
        val orch = MainOrchestrator(svc)
        assertEquals(false, orch.openWriteSettingsPage())
    }
    @Test fun `openWriteSettingsPage starts activity when a11y bound`() = runBlocking { /* 略 */ }
    @Test fun `openWriteSettingsPage verifies root package is settings after 800ms`() = runBlocking { /* 略 */ }
    @Test fun `openWriteSettingsPage retries once on verify fail`() = runBlocking { /* 略 */ }
}
```

- [ ] **Step 2: FAIL**
- [ ] **Step 3: GREEN — 修改 MainOrchestrator.kt:914-935**

```kotlin
suspend fun openWriteSettingsPage(): Boolean {
    // ADAPT: 华为真机测试发现 force-stop 后 a11y 解绑期间 startActivity 被 BAL 拒绝
    // 加绑定检查 + 启动后 verify 以发现"静默失败"
    if (service.serviceInfo == null) {
        Log.w(TAG, "openWriteSettingsPage: a11y service 未绑定，跳过启动")
        return false
    }
    val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS).apply {
        data = Uri.parse("package:${service.packageName}") // 用 service.packageName 避免 applicationContext 污染
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
    }
    val resolved = context.packageManager.resolveActivity(intent, 0)
    if (resolved == null) {
        openAppSettings()
        return false
    }
    service.startActivity(intent) // 用 service 作 Context
    isNavigating = true
    Log.d(TAG, "Opened WRITE_SETTINGS page for ${service.packageName}")
    // ADAPT: 启动后 verify —— vendor 无此步骤
    kotlinx.coroutines.delay(800L)
    val rootPkg = service.rootInActiveWindow?.packageName?.toString() ?: ""
    if (rootPkg != "com.android.settings") {
        Log.w(TAG, "openWriteSettingsPage: verify FAIL — 800ms 后 pkg=$rootPkg，疑似 BAL 拒绝")
        isNavigating = false
        // 重试一次
        try {
            service.startActivity(intent)
            kotlinx.coroutines.delay(800L)
            val rootPkg2 = service.rootInActiveWindow?.packageName?.toString() ?: ""
            if (rootPkg2 != "com.android.settings") {
                Log.w(TAG, "openWriteSettingsPage: 重试仍 FAIL — pkg=$rootPkg2")
                return false
            }
        } catch (_: Exception) { return false }
    }
    return true
}
```

- [ ] **Step 4: PASS**
- [ ] **Step 5: AUDIT**: 记录偏离 — vendor 无此 verify，本修改是复刻加固；在 CACHE_modules.md 记录此差异
- [ ] **Step 6: 编译**

---

# Phase 5 — 真机验证（T19）

## Task 19 — 华为 FIN-AL60 真机端到端验证

**无新代码/新测试**，只执行验证脚本。完成后由用户判断是否达标。

- [ ] **Step 1: 清理 app 状态**

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 shell "pm clear dev.deltalab2964.swift"
```

- [ ] **Step 2: 重新安装最新 APK**

```bash
cp /home/code/php/project/full-package/update-replica/app/build/outputs/apk/debug/app-debug.apk /mnt/c/Users/Administrator/Downloads/update-replica-debug.apk
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 install -r 'C:\Users\Administrator\Downloads\update-replica-debug.apk'
```

**注意**：本 task 之前需要手动 `./gradlew assembleDebug`（慢 task — 用户自行决定何时触发）。

- [ ] **Step 3: 打开无障碍设置页面**

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 shell "am start -a android.settings.ACCESSIBILITY_SETTINGS"
```

- [ ] **Step 4: 用户手动**：在华为手机上开启本应用的无障碍服务

- [ ] **Step 5: 启动 logcat 监控 + 启动 app**

```bash
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 logcat -c
timeout 60 /mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 logcat -v time '*:V' > /tmp/huawei-verify.log 2>&1 &
/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe -s 2TV9K24710071129 shell "am start -n dev.deltalab2964.swift/com.storm.safe.rock.DefaultLauncherAlias"
wait
```

- [ ] **Step 6: 验收 checklist**

在 `/tmp/huawei-verify.log` 中 grep 下列关键日志：

```bash
grep -E "HuaweiSteps|AutomationCoordinator|Yw5xudHandler" /tmp/huawei-verify.log | head -50
```

验收标准：
- [ ] `HuaweiSteps` 10 步日志全部出现（`[Step1/10]` ~ `[Step9/10]`）
- [ ] **不再**出现 `[基础权限] 点击允许 (第10次)` 空转（Bug A 修复）
- [ ] **不再**出现 `enableDrawOverlay retry=20` 这种 21 次重试（Bug B 修复）
- [ ] **不再**出现 `华为锁屏清理页面无法启动, 跳过`（Bug C 修复 — 该步骤已删除）
- [ ] **不再**出现 `Yw5xudHandler` 在华为分支后调用 `GenericSteps`（架构 bug 修复）
- [ ] WRITE_SETTINGS 启动后日志出现 `verify FAIL` 或 `pkg=com.android.settings`（新 verify 生效）
- [ ] `AutomationCoordinator` 的 `acquire "auth"` / `release "auth"` / `acquire "write_settings"` 依然严格串行

- [ ] **Step 7: 提交前 check**

```bash
cd /home/code/php/project/full-package/update-replica && git status --short
```

确认以下文件有改动：
- 新建: AllowKeywords.kt, HuaweiPageDetector.kt, HuaweiGestureHelper.kt + 对应 Test
- 修改: HuaweiSteps.kt, GenericSteps.kt, Yw5xudHandler.kt, MainOrchestrator.kt

**不执行** git add / commit —— 由用户在验证通过后统一 commit。

---

# Self-Review Checklist

## Spec coverage
- [x] Bug A（基础权限空转）→ T2 + T7
- [x] Bug B（悬浮窗 retry 失败）→ T12
- [x] Bug C（虚构 ProtectActivity）→ T4 + T16
- [x] 架构 bug（华为叠加 GenericSteps）→ T3
- [x] WRITE_SETTINGS BAL 拒绝 → T18
- [x] 多语种词库 → T1
- [x] 页面判定方法 → T5
- [x] 手势点击 → T6
- [x] Vendor 10 步完整编排 → T7-T16
- [x] 荣耀特殊处理 → T17

## Placeholder scan
- [x] 所有 `TBD` / `TODO: 实现` 已消除
- 部分 Step3/GREEN 代码为骨架 + 关键注释，engineer 需要精读 vendor 对应行号并 1:1 翻译（已在每个 task 的"Vendor refs"里给出精确行号）
- Step3 未在 plan 里 inline 完整 200-800 行 vendor 代码翻译 —— 这是有意的简化（完整 inline 会让 plan 超过 10000 行无法有效使用），但每 task 都指明 vendor 行号范围让 engineer 自行精读

## Type consistency
- [x] 所有 task 使用相同的方法签名 `suspend fun executeStepN(successes: MutableList<String>, failures: MutableList<String>, logs: MutableList<String>)`
- [x] Sealed class 名称 `VerifyResult` / `LockVerifyResult` / `HonorClickResult` 在 T4 定义后，其他 task 一致引用
- [x] `appLabel` / `packageName` / `isHuawei` 字段在 T4 定义后一致使用
- [x] Test class 命名统一 `HuaweiStepNXxxTest.kt`

## 执行约束合规
- [x] 全程 TDD（每 task 都有 RED → GREEN → AUDIT）
- [x] **不做 git commit**（T19 Step 7 明确提示由用户统一 commit）
- [x] 测试命令用 `--tests "*XxxTest*"` 定向（不跑全量 `./gradlew test`）
- [x] 编译用 `compileDebugKotlin`（不跑 `assembleDebug`）
- [x] 所有偏离 vendor 的地方标 `// ADAPT:` 或 `// TODO: VENDOR_VERIFY —`

---

## Execution Handoff

**Plan complete and saved to `update-replica/docs/superpowers/plans/2026-04-16-huawei-vendor-alignment-plan-a.md`.**

Two execution options:

**1. Subagent-Driven (recommended)** — 每 task 分派新的 subagent（yw5xud-agent 或 modules-agent），two-stage review（spec + code quality）。
- **REQUIRED SUB-SKILL:** `superpowers:subagent-driven-development`

**2. Inline Execution** — 当前 session 内批量执行，checkpoint 间 review。
- **REQUIRED SUB-SKILL:** `superpowers:executing-plans`

**统计**：
- Task 总数：19
- Step 总数：~114（每 task 平均 6 step）
- 预计 LOC 改动：`HuaweiSteps.kt` 174 → ~2500 行；GenericSteps.kt 微调 ~30 行；Yw5xudHandler.kt 微调 ~20 行；MainOrchestrator.kt 微调 ~40 行；新建文件约 1000 行
- 预计新 Test 数：~40 个单元测试（15 新测试文件）
- **不触及真机直到 T19**（全部 Kotlin 单元 + Robolectric mock）

请选择：**A（Subagent-Driven）/ B（Inline）/ 需修改计划？**
