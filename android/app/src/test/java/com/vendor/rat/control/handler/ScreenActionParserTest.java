package com.vendor.rat.control.handler;

import com.google.gson.JsonObject;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * TDD 测试: screen action 命令解析
 *
 * 验证 CommandDispatcher 对 PanelHandler 下发的 screen 命令的解析逻辑:
 * 1. nav "ho" → 点亮屏幕 (WAKEUP), 不是 HOME
 * 2. L lockit=0 → 解锁
 * 3. vol volstate=0 → 增加音量
 * 4. vol volstate=1 → 减少音量
 */
public class ScreenActionParserTest {

    // ============ nav 命令解析 ============

    @Test
    public void nav_ho_shouldMapToWakeScreen() {
        // Panel 发送 nav "ho" 的意图是点亮屏幕
        assertEquals(NavAction.WAKE_SCREEN, NavAction.fromShortcut("ho"));
    }

    @Test
    public void nav_bak_shouldMapToBack() {
        assertEquals(NavAction.BACK, NavAction.fromShortcut("bak"));
    }

    @Test
    public void nav_rec_shouldMapToRecents() {
        assertEquals(NavAction.RECENTS, NavAction.fromShortcut("rec"));
    }

    @Test
    public void nav_unknown_shouldMapToUnknown() {
        assertEquals(NavAction.UNKNOWN, NavAction.fromShortcut("xyz"));
    }

    @Test
    public void nav_null_shouldMapToUnknown() {
        assertEquals(NavAction.UNKNOWN, NavAction.fromShortcut(null));
    }

    // ============ vol 命令解析 ============

    @Test
    public void vol_0_shouldMapToVolumeUp() {
        // Panel volstate=0 → 增加音量
        assertEquals(VolumeAction.UP, VolumeAction.fromState("0"));
    }

    @Test
    public void vol_1_shouldMapToVolumeDown() {
        // Panel volstate=1 → 减少音量
        assertEquals(VolumeAction.DOWN, VolumeAction.fromState("1"));
    }

    @Test
    public void vol_unknown_shouldMapToUnknown() {
        assertEquals(VolumeAction.UNKNOWN, VolumeAction.fromState("99"));
    }

    @Test
    public void vol_null_shouldMapToUnknown() {
        assertEquals(VolumeAction.UNKNOWN, VolumeAction.fromState(null));
    }

    // ============ lock 命令解析 ============

    @Test
    public void lock_0_shouldMapToUnlock() {
        // Panel lockit=0 → 解锁
        assertEquals(LockAction.UNLOCK, LockAction.fromState("0"));
    }

    @Test
    public void lock_1_shouldMapToLock() {
        // Panel lockit=1 → 锁屏
        assertEquals(LockAction.LOCK, LockAction.fromState("1"));
    }

    @Test
    public void lock_null_shouldMapToUnlock() {
        // 默认解锁
        assertEquals(LockAction.UNLOCK, LockAction.fromState(null));
    }

    // ============ JSON payload 解析 ============

    @Test
    public void parseNavPayload_shouldExtractNav() {
        JsonObject payload = new JsonObject();
        payload.addProperty("nav", "ho");
        assertEquals("ho", ScreenActionParser.getNav(payload));
    }

    @Test
    public void parseVolPayload_shouldExtractVolstate() {
        JsonObject payload = new JsonObject();
        payload.addProperty("volstate", "0");
        assertEquals("0", ScreenActionParser.getVolstate(payload));
    }

    @Test
    public void parseLockPayload_shouldExtractLock() {
        JsonObject payload = new JsonObject();
        payload.addProperty("lock", "1");
        assertEquals("1", ScreenActionParser.getLock(payload));
    }

    @Test
    public void parseLockPayload_missingField_shouldReturnDefault() {
        JsonObject payload = new JsonObject();
        assertEquals("0", ScreenActionParser.getLock(payload));
    }
}
