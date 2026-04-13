package com.storm.safe.rock.service.modules.command

import org.json.JSONObject

/**
 * Interface for command handlers that process specific sets of commands.
 *
 * Reverse-engineered from JADX: InterfaceC0726jp (jp interface).
 * Each command handler:
 * - declares supported commands via [getSupportedCommands]
 * - checks if it handles a given command via [canHandle]
 * - executes the command via [handle]
 */
interface CommandHandler {

    /**
     * Returns the set of command names this handler supports.
     * Vendor: mo210873a1() → Set<String>
     */
    fun getSupportedCommands(): Set<String>

    /**
     * Check if this handler can handle the given command.
     * Vendor: mo210872a0(String) → boolean
     * Default implementation checks [getSupportedCommands].
     */
    fun canHandle(command: String): Boolean {
        return command in getSupportedCommands()
    }

    /**
     * Execute the given command with parameters.
     * Vendor: mo210874a2(String, JSONObject?, uz0, continuation) → Object
     *
     * @param command The command name
     * @param params Optional JSON parameters
     * @param context The service context wrapper
     */
    suspend fun handle(command: String, params: JSONObject?, context: CommandContext)
}
