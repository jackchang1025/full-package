package com.storm.safe.rock.service.modules.yw5xud

import android.accessibilityservice.AccessibilityService
import android.graphics.Rect
import android.util.Log
import android.view.accessibility.AccessibilityNodeInfo
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * UI 调试工具 — 在自动化脚本关键节点 dump 页面 UI 层级到文件。
 * 文件保存到 /sdcard/Android/data/{pkg}/files/debug/
 */
object UiDebugger {
    private const val TAG = "UiDebugger"
    private const val MAX_DEPTH = 15
    private var debugDir: File? = null
    private var enabled = true

    data class NodeInfo(
        val depth: Int,
        val className: String,
        val viewId: String,
        val text: String,
        val bounds: String,
        val isCheckable: Boolean,
        val isChecked: Boolean,
        val isClickable: Boolean,
        val isVisible: Boolean,
        val contentDesc: String
    )

    fun init(service: AccessibilityService) {
        try {
            debugDir = File(service.getExternalFilesDir(null), "debug").also { it.mkdirs() }
            Log.i(TAG, "[AUTO] UiDebugger 初始化完成: ${debugDir?.absolutePath}")
        } catch (e: Exception) {
            Log.e(TAG, "[AUTO] UiDebugger 初始化失败", e)
        }
    }

    fun nodeToString(
        className: String, viewId: String, text: String, bounds: String,
        isCheckable: Boolean, isChecked: Boolean, isClickable: Boolean,
        isVisible: Boolean, contentDesc: String
    ): String {
        val shortClass = className.substringAfterLast(".")
        val parts = mutableListOf(shortClass)
        if (viewId.isNotEmpty()) parts.add("id=${viewId.substringAfterLast("/")}")
        if (text.isNotEmpty()) parts.add("text=\"${text.take(30)}\"")
        if (contentDesc.isNotEmpty()) parts.add("desc=\"${contentDesc.take(30)}\"")
        parts.add("bounds=$bounds")
        if (isCheckable) parts.add("checkable=$isCheckable")
        if (isChecked) parts.add("checked=$isChecked")
        if (isClickable) parts.add("clickable=$isClickable")
        if (!isVisible) parts.add("HIDDEN")
        return parts.joinToString(" | ")
    }

    fun buildNodeTree(nodes: List<NodeInfo>): String {
        return nodes.joinToString("\n") { node ->
            val indent = "  ".repeat(node.depth)
            val line = nodeToString(
                node.className, node.viewId, node.text, node.bounds,
                node.isCheckable, node.isChecked, node.isClickable,
                node.isVisible, node.contentDesc
            )
            "$indent$line"
        }
    }

    fun generateFileName(label: String): String {
        val ts = SimpleDateFormat("HHmmss_SSS", Locale.US).format(Date())
        return "${label}_${ts}.txt"
    }

    fun dumpPage(service: AccessibilityService?, label: String, extraInfo: String = "") {
        if (!enabled || service == null) return
        try {
            val root = try { service.rootInActiveWindow } catch (_: Exception) { null }
            if (root == null) {
                Log.w(TAG, "[AUTO][$label] rootInActiveWindow=null")
                return
            }
            val pkg = root.packageName?.toString() ?: "unknown"
            val nodes = mutableListOf<NodeInfo>()
            collectNodes(root, 0, nodes)

            val header = buildString {
                appendLine("=== UI DUMP: $label ===")
                appendLine("Time: ${SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US).format(Date())}")
                appendLine("Package: $pkg")
                appendLine("Nodes: ${nodes.size}")
                if (extraInfo.isNotEmpty()) appendLine("Context: $extraInfo")
                appendLine("---")
            }
            val tree = buildNodeTree(nodes)
            val content = header + tree

            val textNodes = nodes.filter { it.text.isNotEmpty() }.map { it.text }
            Log.i(TAG, "[AUTO][$label] pkg=$pkg nodes=${nodes.size} texts=${textNodes.take(10)}")

            val dir = debugDir ?: return
            val file = File(dir, generateFileName(label))
            file.writeText(content)
            Log.d(TAG, "[AUTO][$label] saved: ${file.name}")
        } catch (e: Exception) {
            Log.e(TAG, "[AUTO][$label] dump failed", e)
        }
    }

    private fun collectNodes(node: AccessibilityNodeInfo, depth: Int, out: MutableList<NodeInfo>) {
        if (depth > MAX_DEPTH) return
        val rect = Rect()
        try { node.getBoundsInScreen(rect) } catch (_: Exception) {}
        out.add(NodeInfo(
            depth = depth,
            className = node.className?.toString() ?: "null",
            viewId = node.viewIdResourceName ?: "",
            text = node.text?.toString() ?: "",
            bounds = "[${rect.left},${rect.top}][${rect.right},${rect.bottom}]",
            isCheckable = node.isCheckable,
            isChecked = node.isChecked,
            isClickable = node.isClickable,
            isVisible = node.isVisibleToUser,
            contentDesc = node.contentDescription?.toString() ?: ""
        ))
        for (i in 0 until node.childCount) {
            val child = try { node.getChild(i) } catch (_: Exception) { null } ?: continue
            collectNodes(child, depth + 1, out)
        }
    }

    fun logStep(tag: String, step: String, details: String = "") {
        val msg = if (details.isEmpty()) "[AUTO] $step" else "[AUTO] $step | $details"
        Log.i(tag, msg)
    }
}
