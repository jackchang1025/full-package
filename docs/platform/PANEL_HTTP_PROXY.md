# Panel Command HTTP Proxy

> 2026-04 migration: Panel command control layer moved from WebSocket to HTTP proxy,
> aligning directly with Android HTTP API (ApiRouter.java port 7910).

## Architecture

```
Panel (Vue) ── axios ──▶ Laravel /devices/{uuid}/api-proxy
                          │
                          │ permission:devices.control
                          │ throttle:60,1
                          │ DeviceProxyRequest (whitelist + deep validation)
                          │ ensureDeviceOwnership
                          │ /syncLockCipher: 5/min rate limit
                          │ audit log → security channel
                          ▼
                        DeviceProxyService::request()
                          │
                          │ port range validation (SSRF defense)
                          ▼
                        http://frps:{device.frpc_base_port}{path}
                          │
                          ▼
                        Android device:7910 (ApiRouter)
```

## API Endpoint

### `POST /devices/{device}/api-proxy`

**Auth:** `permission:devices.control` + `ensureDeviceOwnership()`
**Rate limit:** 60/min global, 5/min for `/syncLockCipher`

**Request:**
```json
{
  "method": "GET|POST",
  "path": "/global/action",
  "query": { "key": "value" },
  "body": { "key": "value" }
}
```

**Response:**
```json
{
  "success": true,
  "status": 200,
  "data": { "code": 200, "success": true, "data": true },
  "error": null
}
```

## Path Whitelist

Defined in `DeviceProxyRequest::ALLOWED_PATHS`:

| Path | Method | Purpose | Panel Handler |
|------|--------|---------|---------------|
| `/global/action` | POST | Navigation (back/home/recent) | handleNavigate |
| `/global/lockScreen` | GET | Lock screen | handleLock(1) |
| `/global/wakeUpScreen` | GET | Wake screen (vivo only) | handleWakeScreen |
| `/global/setText` | GET | Paste text to focused input | handlePaste |
| `/unlock` | GET | Unlock device | handleLock(0) |
| `/startApp` | GET | Launch app by package name | handleOpenQuickApp |
| `/blockView` | GET | Black screen / block interaction | handleSendBlock, handleToggleBlockText |
| `/syncLockCipher` | POST | Sync unlock password | handleModifyPassword |

### Adding New Paths

1. Add path to `DeviceProxyRequest::ALLOWED_PATHS`
2. Add per-path validation rules in `rules()` if path has sensitive fields
3. Update this document
4. Add Feature test coverage

## Security Layers

1. **Route middleware:** `permission:devices.control` + `throttle:60,1`
2. **Request validation:** Path whitelist + regex defense + per-path field validation
3. **Ownership check:** `ensureDeviceOwnership()` (sub-accounts via `getResourceOwnerId()`)
4. **SSRF defense:** Port range validation in `DeviceProxyService::getDeviceBaseUrl()`
5. **Rate limiting:** `/syncLockCipher` at 5/min per user
6. **Audit logging:** Security channel (body keys only, no plaintext values)
7. **Error filtering:** Connection failures return generic message, no internal host:port

### Production Requirement

`compose.prod.yaml` frps data ports (20000-30000) MUST be bound to `127.0.0.1`.
Without this, attackers can bypass Laravel and directly access device HTTP APIs.

## Preserved on WebSocket

- lock(2) clear password, lock(3) disable face
- Screenshot (binary stream)
- Tap/swipe/longPress (screen coordinates)
- Volume, mute, keyboard, hideIcon, phish commands
- All data fetching (SMS, contacts, files, apps, etc.)
- Status push, screen stream

## Known Limitations

- `/global/wakeUpScreen` only works on vivo devices (replica limitation)
- `/syncLockCipher` requires Task 0c fix (ApiRouter deserialize to ReqUnlockDeviceVO)
