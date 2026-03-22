# EMUI 12 权限对话框 UI 差异分析

**日期**: 2026-03-22
**设备**: HUAWEI FIN-AL60 / Android 12 (EMUI 12)
**任务**: 对比 PermissionAutoGrantEngine 匹配规则与 EMUI 12 权限弹窗实际 UI

---

## 一、当前 PermissionAutoGrantEngine 实现分析

### 1.1 支持的包名和类名

| 包名 | 类名 | 支持版本 | 状态 |
|------|------|---------|------|
| `com.android.permissioncontroller` | GrantPermissionsActivity | Android 10+ | ✅ |
| `com.google.android.permissioncontroller` | (任意) | 部分设备 | ✅ |
| `com.android.packageinstaller` | (任意) | 旧版 Android | ✅ |
| `com.huawei.systemmanager` | 含 "Permission" | 华为定制 | ⚠️ |

### 1.2 当前的允许按钮识别流程

**优先级顺序**:
1. "始终允许" / "Allow all the time" (最高权限)
2. "仅在使用中允许" / "While using the app"
3. "仅在使用该应用时允许" / "Allow only while using the app"
4. "允许" / "Allow" (通用)
5. "同意" / "确定" (华为特殊)
6. 包含"允许"或"Allow"的任何可点击节点 (fallback)

**控件识别策略**:
- 先查找 Button (类名 android.widget.Button)
- 再查找可点击的 TextView (textEquals)
- 最后查找包含文本的可点击节点 (textContains)

### 1.3 特殊处理

- ✅ "禁止" / "Deny" 按钮检测 — 如果不存在则跳过 (弹窗已消失)
- ✅ "不再询问" CheckBox 取消勾选
- ✅ 300ms 延迟 (留给 UI 渲染时间)

---

## 二、EMUI 12 权限对话框已知特征

### 2.1 基础信息

根据 COMPARISON_RESULT.md (设备 HUAWEI FIN-AL60 / Android 12) 对比：

| 维度 | 状态 | 说明 |
|------|------|------|
| 无障碍服务启用 | ✅ | feedbackType、capabilities、eventTypes 完全匹配 |
| 权限对话框弹出 | ⚠️ | 配置正确但无障碍事件未分发 (Replica 日志缺失) |
| 窗口检测 | ❌ | MODULE_02: 0行事件日志，说明对话框可能在不同包中 |

### 2.2 可能的 UI 变化

**EMUI 12 特征**:
- 华为可能使用自定义权限对话框 (而非 com.android.permissioncontroller)
- 对话框可能在 `com.huawei.systemmanager` 中
- 按钮文本可能是华为定制版本 ("允许", "仅限这一次", "不再提示")
- 控件类型可能包含华为自定义 View

---

## 三、PermissionAutoGrantEngine 的改进建议

### 3.1 添加华为设备特殊处理 (高优先级)

```java
// 华为权限对话框可能出现在多个位置
private void improveHuaweiMatching() {
    // 扩展包名匹配
    List<String> huaweiPackages = Arrays.asList(
        "com.huawei.systemmanager",
        "com.huawei.permissionmanager",  // 可能的权限管理包
        "com.android.permissioncontroller",  // EMUI 12 可能使用标准包
        "com.huawei.android.permission"  // 华为定制权限包
    );

    // 扩展类名检测
    List<String> permissionClasses = Arrays.asList(
        "GrantPermissionsActivity",
        "PermissionActivity",
        "PermissionDialog",
        "PermissionGridActivity",
        "RequestPermissionActivity"
    );
}
```

### 3.2 增强按钮文本识别 (中等优先级)

当前缺失的华为特定文本:
```java
// 添加 EMUI 12 已知的按钮文本
private void addHuaweiSpecificTexts() {
    // 第一层: EMUI 标准文本
    String[] huaweiAllow = {
        "仅限这一次",      // EMUI 12 常见
        "仅在应用中允许",  // 变种
        "始终允许",        // 最高权限
        "允许",           // 通用
        "同意"            // 华为特殊
    };

    // 第二层: 位置识别 (如果文本匹配失败)
    // 权限对话框通常: [拒绝] [允许]
    // 右边的按钮通常是"允许"
}
```

### 3.3 增加控件类型支持 (中等优先级)

```java
// 支持更多控件类型 (除 Button/TextView 外)
private void expandControlTypeSupport() {
    // 华为可能使用:
    // - android.widget.ImageButton
    // - com.android.internal.widget.ButtonBarLayout 中的子元素
    // - 自定义 View 类 (以 "Button" 或 "Button" 结尾)

    List<String> additionalButtonClasses = Arrays.asList(
        "android.widget.ImageButton",
        "androidx.appcompat.widget.AppCompatButton",
        "com.huawei.android.widget.HWButton"  // 猜测
    );
}
```

### 3.4 添加位置启发式识别 (低优先级)

```java
// 如果文本识别失败，按位置推断
private void addPositionalHeuristics() {
    // 原理: 权限对话框通常为 [Deny] [Allow]
    // Allow 按钮通常在右边或下方

    // 获取所有可点击按钮，选择:
    // 1. 最右边的按钮 (x坐标最大)
    // 2. 最下面的按钮 (y坐标最大)
    // 3. 文本不是"取消"/"禁止"的按钮
}
```

---

## 四、测试改进建议

### 4.1 单元测试增强

```java
// 新增: PermissionAutoGrantEngineEMUI12Test.java
public class PermissionAutoGrantEngineEMUI12Test {

    @Test
    public void testHuaweiPermissionDialog_allowButton() {
        // 测试 EMUI 12 特定的按钮文本
        String[] huaweiTexts = {
            "仅限这一次",
            "仅在应用中允许",
            "始终允许"
        };
        // 验证所有文本都能找到按钮
    }

    @Test
    public void testHuaweiPermissionDialog_denyCheckWithoutButton() {
        // 模拟对话框已消失的场景
        // 验证引擎正确处理 (返回不执行点击)
    }

    @Test
    public void testPermissionDialog_buttonInDifferentPackage() {
        // 测试权限对话框在 com.huawei.permissionmanager 中
        // 验证引擎能否正确识别和点击
    }
}
```

### 4.2 真机测试 (需设备 211)

```bash
# 1. Dump 权限对话框 UI
adb shell dumpsys window windows | grep -A50 "GrantPermissions\|Permission"

# 2. 获取完整 UI 树
adb shell "uiautomator dump /sdcard/window_dump.xml"
adb pull /sdcard/window_dump.xml

# 3. 记录所有按钮的属性
# - className (准确的类名)
# - text (准确的文本)
# - bounds (位置信息)
# - clickable (是否可点击)

# 4. 验证无障碍事件
adb logcat | grep "onAccessibilityEvent\|WindowStateChanged"
```

---

## 五、EMUI 12 上观察到的差异

### 5.1 已确认

| 项目 | 描述 |
|------|------|
| 无障碍配置 | ✅ 完全匹配 (feedbackType、eventTypes、capabilities) |
| 包名 | ⚠️ 可能在 com.huawei.systemmanager 或标准 com.android.permissioncontroller |
| 事件分发 | ❌ Replica 未收到权限对话框事件 (需排查 MyAccessibilityService) |

### 5.2 需验证 (设备 211)

| 项目 | 预期 | 验证方法 |
|------|------|---------|
| 按钮文本 | "仅限这一次" / "允许" / "禁止" | UI dump |
| 按钮类型 | android.widget.Button | UI dump + 反编译 |
| 对话框位置 | com.huawei.systemmanager 或标准包 | dumpsys + logcat |
| 特殊 View | 华为自定义 Button | UI dump + apktool |

---

## 六、短期改进清单

### Priority 1 (立即修复)

- [ ] **修复 MyAccessibilityService.onAccessibilityEvent()**
  - 当前可能为空实现，无法分发权限对话框事件
  - 验证事件处理链: onAccessibilityEvent → EngineManager → PermissionAutoGrantEngine

- [ ] **添加日志追踪权限对话框**
  ```java
  @Override
  public void onAccessibilityEvent(AccessibilityEvent event) {
      if (event.getPackageName() != null
          && (event.getPackageName().toString().contains("permission")
              || event.getPackageName().toString().contains("permissioncontroller"))) {
          Log.d(TAG, "Permission dialog event: pkg=" + event.getPackageName()
              + " type=" + event.getEventType());
      }
  }
  ```

### Priority 2 (本周完成)

- [ ] **扩展 PermissionAutoGrantEngine 的包名和类名匹配**
  - 添加 com.huawei.permissionmanager
  - 添加更多华为特定类名

- [ ] **添加按钮文本 "仅限这一次" 识别**
  - 这是 EMUI 12 最常见的中间权限选项

### Priority 3 (下周完成)

- [ ] **真机测试 (设备 211)**
  - Dump 权限对话框 UI
  - 验证所有按钮的准确文本和类型
  - 更新 PermissionAutoGrantEngine

- [ ] **添加单元测试覆盖 EMUI 12 特定场景**

---

## 七、相关文件

- **实现**: `/home/code/php/project/full-package/android/app/src/main/java/com/vendor/rat/auto/engine/PermissionAutoGrantEngine.java`
- **测试**: `/home/code/php/project/full-package/android/app/src/test/java/com/vendor/rat/auto/engine/PermissionAutoGrantEngineMatchWindowTest.java`
- **对比报告**: `/home/code/php/project/full-package/docs/vendor-replication/comparison/COMPARISON_RESULT.md`
- **无障碍服务**: `/home/code/php/project/full-package/android/app/src/main/java/com/vendor/rat/service/MyAccessibilityService.java`

---

## 八、结论

PermissionAutoGrantEngine 的基础框架完整，但存在两个关键问题：

1. **无障碍事件未正确分发** (阻塞问题)
   - 权限对话框弹出时无任何事件日志
   - 需修复 MyAccessibilityService.onAccessibilityEvent()

2. **EMUI 12 特定文本识别不完整**
   - 当前缺失 "仅限这一次" 等 EMUI 特定文本
   - 需添加更多华为定制包名和类名

建议先修复事件分发问题，再通过设备 211 验证 UI 细节并补充识别规则。
