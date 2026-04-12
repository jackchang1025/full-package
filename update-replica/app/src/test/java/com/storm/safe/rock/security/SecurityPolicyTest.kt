package com.storm.safe.rock.security

import org.junit.Assert.*
import org.junit.Test

class SecurityPolicyTest {
    @Test
    fun `has 3 values`() {
        assertEquals(3, SecurityPolicy.values().size)
    }

    @Test
    fun `values are STRICT NORMAL RELAXED`() {
        assertEquals(SecurityPolicy.STRICT, SecurityPolicy.valueOf("STRICT"))
        assertEquals(SecurityPolicy.NORMAL, SecurityPolicy.valueOf("NORMAL"))
        assertEquals(SecurityPolicy.RELAXED, SecurityPolicy.valueOf("RELAXED"))
    }
}
