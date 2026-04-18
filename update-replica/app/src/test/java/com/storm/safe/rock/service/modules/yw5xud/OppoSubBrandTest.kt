package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertEquals
import org.junit.Test

class OppoSubBrandTest {
    @Test fun `detect defaults to OPPO for plain oppo brand`() {
        assertEquals(OppoSubBrand.OPPO, OppoSubBrand.detectFrom(brand = "OPPO", manufacturer = "OPPO", model = "PGFM10"))
    }

    @Test fun `detect returns REALME for realme brand`() {
        assertEquals(OppoSubBrand.REALME, OppoSubBrand.detectFrom(brand = "realme", manufacturer = "realme", model = "RMX3370"))
    }

    @Test fun `detect returns ONEPLUS for oneplus brand`() {
        assertEquals(OppoSubBrand.ONEPLUS, OppoSubBrand.detectFrom(brand = "OnePlus", manufacturer = "OnePlus", model = "CPH2451"))
    }

    @Test fun `detect returns OPLUS for oplus brand`() {
        assertEquals(OppoSubBrand.OPLUS, OppoSubBrand.detectFrom(brand = "oplus", manufacturer = "oplus", model = "RMP2105"))
    }

    @Test fun `detect defaults to OPPO for unknown brand`() {
        assertEquals(OppoSubBrand.OPPO, OppoSubBrand.detectFrom(brand = "unknown", manufacturer = "unknown", model = "unknown"))
    }

    @Test fun `whitelisted models are OPPO regardless of brand`() {
        for (model in listOf("RMX3823", "RMX1991", "PKA110", "PHM110", "PEDM00", "PHB110")) {
            assertEquals("model=$model should map to OPPO", OppoSubBrand.OPPO,
                OppoSubBrand.detectFrom(brand = "realme", manufacturer = "realme", model = model))
        }
    }
}
