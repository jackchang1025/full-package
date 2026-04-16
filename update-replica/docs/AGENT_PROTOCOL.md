# 模块 Agent 调用协议

> 8 个专属模块 agent，每个负责一个代码模块的 JADX 逆向知识维护。
> Agent 定义在 `.claude/agents/`，知识缓存在 `docs/cache/`。

## Agent ↔ 缓存 一览

| Agent | 缓存文件 | 负责范围 |
|-------|---------|---------|
| `svc-agent` | `CACHE_svc.md` | service/ 根 + account/ |
| `cipher-agent` | `CACHE_cipher.md` | modules/cipher/ |
| `setup-agent` | `CACHE_setup.md` | modules/setup/ |
| `modules-agent` | `CACHE_modules.md` | modules/ 根 + overlay/ + screen/ |
| `cmd-agent` | `CACHE_cmd.md` | modules/command/ |
| `yw5xud-agent` | `CACHE_yw5xud.md` | modules/yw5xud/ |
| `infra-agent` | `CACHE_infra.md` | manager/ + network/ + util/ + security/ |
| `ui-agent` | `CACHE_ui.md` | activity/ + receiver/ + inject/ + view/ |

## 调用方式

```python
# Spawn agent（每次创建新实例）
Agent(name="cipher-agent", prompt="查看 CipherCaptureManager 的 d8 方法逻辑")

# 同会话内复用（保持上下文）
SendMessage(to="cipher-agent", message="继续看 e1 方法")
```

> `subagent_type` 参数**不适用于**自定义 agent，必须用 `name` + `prompt`。

## Agent 工作流

```
1. Agent 启动 → 读缓存文件（~100行）
2. 缓存能回答 → 直接回答
3. 缓存不足 → 读 JADX 原文件 → 更新缓存 → 回答
```

## 并行调用

不同模块 agent 可并行（不修改同一文件）：

```python
Agent(name="cipher-agent", prompt="...", run_in_background=True)
Agent(name="cmd-agent", prompt="...", run_in_background=True)
```

## 限制

- 每次 `Agent()` 创建新实例，无法继承之前会话的上下文
- 跨会话需重新 spawn（但读缓存秒恢复）
- Agent 之间不能互相调用
