package com.vendor.rat.control.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 文件列表构建工具类
 * 从 CommandDispatcher 提取，便于测试
 */
public final class FileListHelper {

    private FileListHelper() {}

    /**
     * 构建目录下的文件列表 JSON
     */
    public static JsonArray buildFileList(File dir) {
        JsonArray arr = new JsonArray();
        if (!dir.exists() || !dir.isDirectory()) return arr;

        File[] files = dir.listFiles();
        if (files == null) return arr;

        for (File f : files) {
            JsonObject item = new JsonObject();
            item.addProperty("name", f.getName());
            item.addProperty("path", f.getParent());
            item.addProperty("size", String.valueOf(f.length()));
            item.addProperty("isDirectory", f.isDirectory());
            item.addProperty("lastModified",
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
                    .format(new Date(f.lastModified())));
            arr.add(item);
        }
        return arr;
    }
}
