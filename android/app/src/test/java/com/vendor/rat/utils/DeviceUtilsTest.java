package com.vendor.rat.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * DeviceUtils 单元测试
 */
public class DeviceUtilsTest {

    @Test
    public void testGetBrandName_returnsNonNull() {
        String brand = DeviceUtils.getBrandName();
        assertNotNull(brand);
        assertFalse(brand.isEmpty());
    }
}
