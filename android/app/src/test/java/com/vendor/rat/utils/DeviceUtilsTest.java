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

    @Test
    public void testGetVendorId_returnsValidRange() {
        int vendorId = DeviceUtils.getVendorId();
        assertTrue("Vendor ID should be >= 0", vendorId >= 0);
        assertTrue("Vendor ID should be <= 14", vendorId <= 14);
    }

    @Test
    public void testGetVendorName_returnsNonNull() {
        String vendorName = DeviceUtils.getVendorName();
        assertNotNull(vendorName);
        assertFalse(vendorName.isEmpty());
    }
}
