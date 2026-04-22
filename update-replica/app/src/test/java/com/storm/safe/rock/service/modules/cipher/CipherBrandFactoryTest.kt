package com.storm.safe.rock.service.modules.cipher

import com.storm.safe.rock.service.modules.cipher.vendor.*
import org.junit.Test
import org.junit.Assert.*

class CipherBrandFactoryTest {

    @Test
    fun `factory creates correct strategy for each brand`() {
        assertTrue(CipherBrandFactory.create(CipherBrand.VIVO) is VivoCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.MIUI) is MiuiCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.OPPO) is OppoCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.SAMSUNG) is SamsungCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.HUAWEI) is HuaweiCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.TECNO) is TecnoCipherStrategy)
        assertTrue(CipherBrandFactory.create(CipherBrand.GENERIC) is GenericCipherStrategy)
    }

    @Test
    fun `all 7 brand enum values exist`() {
        assertEquals(7, CipherBrand.values().size)
    }
}
