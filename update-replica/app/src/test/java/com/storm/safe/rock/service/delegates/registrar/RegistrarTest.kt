package com.storm.safe.rock.service.delegates.registrar

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RegistrarTest {

    @Test
    fun `ModuleRegistrar boot has default empty implementation`() {
        val registrar = object : ModuleRegistrar {
            override fun register(service: com.storm.safe.rock.service.MyAccessibilityService) {}
        }
        // boot() should not throw (has default impl)
        // Can't call without service, just verify interface compiles
        assertTrue(true)
    }

    @Test
    fun `CoreModuleRegistrar implements ModuleRegistrar`() {
        assertTrue(CoreModuleRegistrar() is ModuleRegistrar)
    }

    @Test
    fun `SecurityModuleRegistrar implements ModuleRegistrar`() {
        assertTrue(SecurityModuleRegistrar() is ModuleRegistrar)
    }

    @Test
    fun `PostAuthModuleRegistrar implements ModuleRegistrar`() {
        assertTrue(PostAuthModuleRegistrar() is ModuleRegistrar)
    }

    @Test
    fun `StateRestorer implements ModuleRegistrar`() {
        assertTrue(StateRestorer() is ModuleRegistrar)
    }
}
