package com.vendor.rat.auto.util;

/**
 * 屏幕坐标适配工具
 *
 * 基于基准设备 (OPPO Find X6, 1240x2772) 的坐标,
 * 按目标设备分辨率比例缩放。
 */
public class ScreenAdaptUtil {

    private static final int BASE_WIDTH = 1240;
    private static final int BASE_HEIGHT = 2772;

    private static final int PERM_ALLOW_X = 550;
    private static final int PERM_ALLOW_Y = 1052;

    public static int[] adaptCoordinate(int baseX, int baseY,
                                         int baseWidth, int baseHeight,
                                         int targetWidth, int targetHeight) {
        if (baseWidth <= 0 || baseHeight <= 0 || targetWidth <= 0 || targetHeight <= 0) {
            return new int[]{0, 0};
        }
        int x = Math.round((float) baseX * targetWidth / baseWidth);
        int y = Math.round((float) baseY * targetHeight / baseHeight);
        return new int[]{x, y};
    }

    public static int[] getPermissionAllowCoordinate(int screenWidth, int screenHeight) {
        return adaptCoordinate(PERM_ALLOW_X, PERM_ALLOW_Y,
            BASE_WIDTH, BASE_HEIGHT, screenWidth, screenHeight);
    }
}
