package com.storm.safe.rock.service.modules.command

import android.util.Log
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Main command dispatcher that routes JSON commands to registered handlers.
 *
 * Reverse-engineered from JADX: C0350a7 (a7, 138 lines).
 * Vendor name: NetworkCommandDispatcher
 *
 * Fields:
 * - f53597a0 (uz0: context) → [commandContext]
 * - f53598a1 (CopyOnWriteArrayList) → [handlers]
 * - f53599a2 (ConcurrentHashMap) → [handlerCache]
 *
 * Methods:
 * - m211883a0 (a0: dispatch) → [dispatch]
 */
class CommandDispatcher(
    private val commandContext: CommandContext
) {
    companion object {
        private const val TAG = "CommandDispatcher"

        /**
         * The JSON key for the command name.
         * Vendor: StringUtil.m212470a0("KFYcN0w2CA==") → "command"
         * vendor: decrypted at build time, hardcoded as constant.
         */
        const val KEY_COMMAND = "command"
    }

    /** Registered command handlers in order of registration. */
    private val handlers = CopyOnWriteArrayList<CommandHandler>()

    /** Cache: command name → handler for fast lookup. */
    private val handlerCache = ConcurrentHashMap<String, CommandHandler>()

    /**
     * Register a command handler.
     */
    fun registerHandler(handler: CommandHandler) {
        handlers.add(handler)
    }

    /**
     * Remove a command handler.
     */
    fun unregisterHandler(handler: CommandHandler) {
        handlers.remove(handler)
        // Clear cache entries that point to this handler
        handlerCache.entries.removeAll { it.value === handler }
    }

    /**
     * Dispatch a JSON command to the appropriate handler.
     *
     * Vendor: m211883a0 (suspend function)
     * 1. Extract "command" and "params" from JSON
     * 2. Check cache first
     * 3. If not cached, iterate handlers to find one that canHandle
     * 4. Cache the match
     * 5. Execute the handler
     *
     * @return true if command was handled, false otherwise
     */
    suspend fun dispatch(json: JSONObject): Boolean {
        val command = json.optString(KEY_COMMAND, "")
        val params = json.optJSONObject("params")

        if (command.isEmpty()) {
            Log.w(TAG, "收到空命令")
            return false
        }

        Log.d(TAG, "分发命令: $command")

        // Try cached handler first
        val cachedHandler = handlerCache[command]
        if (cachedHandler != null) {
            return try {
                cachedHandler.handle(command, params, commandContext)
                true
            } catch (e: Exception) {
                Log.e(TAG, "处理命令 $command 失败", e)
                false
            }
        }

        // Search through registered handlers
        for (handler in handlers) {
            if (handler.canHandle(command)) {
                handlerCache[command] = handler
                return try {
                    handler.handle(command, params, commandContext)
                    true
                } catch (e: Exception) {
                    Log.e(TAG, "处理命令 $command 失败", e)
                    false
                }
            }
        }

        Log.w(TAG, "未找到命令处理器: $command")
        return false
    }

    /**
     * Get the number of registered handlers.
     */
    fun getHandlerCount(): Int = handlers.size

    /**
     * Get the cache size.
     */
    fun getCacheSize(): Int = handlerCache.size

    /**
     * Clear all handlers and cache.
     */
    fun clear() {
        handlers.clear()
        handlerCache.clear()
    }
}
