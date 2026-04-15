# 模块 Agent 调用协议

> 本项目使用 8 个专属模块 agent，每个负责一个代码模块的复刻、补全和维护。
> Agent 定义在 `.claude/agents/` 目录，知识缓存在 `docs/cache/` 目录。

## 快速参考

| 需求 | 调用方式 |
|------|---------|
| 补全某模块 stub | `@cipher-agent 补全 tryExtractPin 方法` |
| 查询模块状态 | `@svc-agent 列出所有 STUB 方法` |
| 整个会话专注一个模块 | `claude --agent cipher-agent` |

## Agent 列表

| Agent | 触发场景 | 缓存文件 |
|-------|---------|---------|
| `svc-agent` | AccessibilityService、无障碍、service/ 根 | CACHE_MyAccessibilityService.md + CACHE_ServiceRoot.md |
| `cipher-agent` | 密码捕获、PIN、图案锁、cipher/ | CACHE_CipherCaptureManager.md + CACHE_CipherModule.md |
| `setup-agent` | 开发者选项、ADB 配对、setup/ | CACHE_SystemOptimizeManager.md |
| `modules-agent` | MainOrchestrator、NetworkManager、modules/ 根 | CACHE_MainOrchestrator.md + CACHE_NetworkManager.md + CACHE_RemoteConfigManager.md |
| `cmd-agent` | 远程命令、CommandDispatcher、command/ | CACHE_CommandModule.md |
| `yw5xud-agent` | 厂商引擎、品牌适配、yw5xud/ | CACHE_Yw5xudEngines.md |
| `infra-agent` | ScreenCapture、WebSocket、加解密、manager/ | CACHE_InfraLayer.md |
| `ui-agent` | Activity、Receiver、Application、activity/ | CACHE_UILayer.md |

## 工作流程

```
1. 用户提出需求
2. Claude 根据 CLAUDE.md 路由表 + agent description 选择模块 agent
3. Agent 启动 → 读知识缓存（~100行，5秒）
4. Agent 只读 JADX 中需要补全的具体行范围
5. 完成 → 更新缓存文件（STUB → OK）
6. 运行 ./gradlew test 验证
```

## 缓存更新规则

Agent 每完成一个 stub 补全：
1. 在缓存文件的方法映射表中：`STUB` → `OK`
2. 如发现新缺口：添加到 `## 已知缺口`
3. JADX 行号索引保持准确

## 并行调用

不同模块 agent 可并行工作（不修改同一文件）：

```
@cipher-agent 补全 CipherCaptureManager 的 monitorSystemPasswordInput
@modules-agent 补全 NetworkManager 的心跳逻辑
```

## 限制

- Subagent 不能嵌套 spawn（cipher-agent 不能调用 infra-agent）
- 每次 @mention 或 Agent() 创建新实例（非同一上下文）
- 跨会话需重新 spawn（但读缓存秒恢复）
