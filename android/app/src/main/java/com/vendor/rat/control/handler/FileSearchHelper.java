package com.vendor.rat.control.handler;

import com.google.gson.JsonArray;

import java.io.File;

/**
 * 文件搜索工具类
 * 从 CommandDispatcher 提取，便于测试
 */
public final class FileSearchHelper {

    private FileSearchHelper() {}

    /**
     * 递归搜索文件
     * @param dir     搜索目录
     * @param pattern 搜索模式 (如 "*.jpg")，去掉 * 后做 contains 匹配
     * @param results 结果数组 (绝对路径)
     * @param limit   最大结果数
     */
    public static void searchFilesRecursive(File dir, String pattern, JsonArray results, int limit) {
        if (!dir.exists() || !dir.isDirectory() || results.size() >= limit) return;
        File[] files = dir.listFiles();
        if (files == null) return;
        String lowerPattern = pattern.replace("*", "").toLowerCase();
        for (File f : files) {
            if (results.size() >= limit) return;
            if (f.isDirectory()) {
                searchFilesRecursive(f, pattern, results, limit);
            } else if (lowerPattern.isEmpty() || f.getName().toLowerCase().contains(lowerPattern)) {
                results.add(f.getAbsolutePath());
            }
        }
    }
}
