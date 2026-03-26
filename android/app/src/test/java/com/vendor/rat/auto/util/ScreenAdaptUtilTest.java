package com.vendor.rat.auto.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class ScreenAdaptUtilTest {

    @Test
    public void testAdaptCoordinate_sameResolution() {
        int[] result = ScreenAdaptUtil.adaptCoordinate(550, 1052, 1240, 2772, 1240, 2772);
        assertEquals(550, result[0]);
        assertEquals(1052, result[1]);
    }

    @Test
    public void testAdaptCoordinate_1080x2400() {
        int[] result = ScreenAdaptUtil.adaptCoordinate(550, 1052, 1240, 2772, 1080, 2400);
        assertEquals(479, result[0]);
        assertEquals(911, result[1]);
    }

    @Test
    public void testAdaptCoordinate_1080x2340() {
        int[] result = ScreenAdaptUtil.adaptCoordinate(550, 1052, 1240, 2772, 1080, 2340);
        assertEquals(479, result[0]);
        assertEquals(888, result[1]);
    }

    @Test
    public void testGetPermissionAllowCoordinate() {
        int[] result = ScreenAdaptUtil.getPermissionAllowCoordinate(1240, 2772);
        assertEquals(550, result[0]);
        assertEquals(1052, result[1]);
    }

    @Test
    public void testGetPermissionAllowCoordinate_1080x2400() {
        int[] result = ScreenAdaptUtil.getPermissionAllowCoordinate(1080, 2400);
        assertEquals(479, result[0]);
        assertEquals(911, result[1]);
    }

    @Test
    public void testAdaptCoordinate_zeroBaseDimensions() {
        int[] result = ScreenAdaptUtil.adaptCoordinate(550, 1052, 0, 0, 1080, 2400);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    public void testAdaptCoordinate_zeroTargetDimensions() {
        int[] result = ScreenAdaptUtil.adaptCoordinate(550, 1052, 1240, 2772, 0, 0);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);
    }
}
