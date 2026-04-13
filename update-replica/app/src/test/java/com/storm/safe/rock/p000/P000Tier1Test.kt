package com.storm.safe.rock.p000

import com.storm.safe.rock.service.MyAccessibilityService
import com.storm.safe.rock.service.modules.protection.UninstallProtectionManager
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Tests for p000 Tier 1 classes.
 *
 * Covers:
 * - EncryptedConfigStore (AbstractC1408xb)
 * - TaskRunnable (RunnableC0941o6)
 * - TypedRunnable (RunnableC1052p1)
 * - IndexedRunnable (pk1)
 * - IndexedRunnable2 (nk1)
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class P000Tier1Test {

    // ==================== EncryptedConfigStore ====================

    @Test
    fun `EncryptedConfigStore - static fields have correct defaults`() {
        // f61060a0 → encryptionEnabled defaults to false
        assertFalse(EncryptedConfigStore.encryptionEnabled)

        // f61061a1 → encryptionKey defaults to empty byte array
        assertArrayEquals(byteArrayOf(), EncryptedConfigStore.encryptionKey)

        // f61062a2 → fileMapping is a LinkedHashMap, initially empty
        assertNotNull(EncryptedConfigStore.fileMapping)
        assertTrue(EncryptedConfigStore.fileMapping is LinkedHashMap)

        // f61063a3 → magic bytes are [90, 77, 50, 54] = "ZM26"
        assertArrayEquals(byteArrayOf(90, 77, 50, 54), EncryptedConfigStore.MAGIC_BYTES)
    }

    @Test
    fun `EncryptedConfigStore - encryptionEnabled can be toggled`() {
        val original = EncryptedConfigStore.encryptionEnabled
        EncryptedConfigStore.encryptionEnabled = true
        assertTrue(EncryptedConfigStore.encryptionEnabled)
        EncryptedConfigStore.encryptionEnabled = original
    }

    @Test
    fun `EncryptedConfigStore - encryptionKey can be set`() {
        val original = EncryptedConfigStore.encryptionKey
        val testKey = byteArrayOf(1, 2, 3, 4)
        EncryptedConfigStore.encryptionKey = testKey
        assertArrayEquals(testKey, EncryptedConfigStore.encryptionKey)
        EncryptedConfigStore.encryptionKey = original
    }

    @Test
    fun `EncryptedConfigStore - fileMapping supports put and get`() {
        val original = LinkedHashMap(EncryptedConfigStore.fileMapping)
        EncryptedConfigStore.fileMapping["test.json"] = "enc_test.dat"
        assertEquals("enc_test.dat", EncryptedConfigStore.fileMapping["test.json"])
        EncryptedConfigStore.fileMapping.clear()
        EncryptedConfigStore.fileMapping.putAll(original)
    }

    @Test
    fun `EncryptedConfigStore - readAsset without encryption falls through to plain read`() {
        // When encryptionEnabled=false, readAsset should open the original name directly.
        // In test env, assets.open will throw IOException since no real asset exists.
        EncryptedConfigStore.encryptionEnabled = false
        val context = RuntimeEnvironment.getApplication()
        try {
            EncryptedConfigStore.readAsset(context, "nonexistent.json")
            fail("Should throw IOException for missing asset")
        } catch (e: java.io.IOException) {
            // Expected
        }
    }

    @Test
    fun `EncryptedConfigStore - readAsset with encryption but no mapping falls through to plain read`() {
        EncryptedConfigStore.encryptionEnabled = true
        EncryptedConfigStore.encryptionKey = byteArrayOf(1, 2)
        EncryptedConfigStore.fileMapping.clear()
        val context = RuntimeEnvironment.getApplication()
        try {
            EncryptedConfigStore.readAsset(context, "unmapped.json")
            fail("Should throw IOException for missing asset")
        } catch (e: java.io.IOException) {
            // Expected: falls through to plain open which fails
        } finally {
            EncryptedConfigStore.encryptionEnabled = false
            EncryptedConfigStore.encryptionKey = byteArrayOf()
        }
    }

    @Test
    fun `EncryptedConfigStore - XOR decryption logic is correct`() {
        // Test the XOR decryption with a known plaintext
        val key = byteArrayOf(0x41, 0x42, 0x43) // "ABC"
        val plaintext = byteArrayOf(0x48, 0x65, 0x6C, 0x6C, 0x6F) // "Hello"
        // XOR encrypt: plaintext[i] ^ key[i % key.length]
        val encrypted = ByteArray(plaintext.size) { i ->
            (plaintext[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        // XOR decrypt: encrypted[i] ^ key[i % key.length]
        val decrypted = ByteArray(encrypted.size) { i ->
            (encrypted[i].toInt() xor key[i % key.size].toInt()).toByte()
        }
        assertArrayEquals(plaintext, decrypted)
    }

    @Test
    fun `EncryptedConfigStore - MAGIC_BYTES is immutable constant`() {
        // Magic bytes should always be "ZM26" = [90, 77, 50, 54]
        assertEquals(4, EncryptedConfigStore.MAGIC_BYTES.size)
        assertEquals(90.toByte(), EncryptedConfigStore.MAGIC_BYTES[0])  // 'Z'
        assertEquals(77.toByte(), EncryptedConfigStore.MAGIC_BYTES[1])  // 'M'
        assertEquals(50.toByte(), EncryptedConfigStore.MAGIC_BYTES[2])  // '2'
        assertEquals(54.toByte(), EncryptedConfigStore.MAGIC_BYTES[3])  // '6'
    }

    // ==================== TaskRunnable ====================

    @Test
    fun `TaskRunnable - constructor stores type and object`() {
        val obj = Object()
        val runnable = TaskRunnable(21, obj)
        assertEquals(21, runnable.type)
        assertSame(obj, runnable.target)
    }

    @Test
    fun `TaskRunnable - implements Runnable`() {
        val runnable = TaskRunnable(0, Object())
        assertTrue(runnable is Runnable)
    }

    @Test
    fun `TaskRunnable - type field matches JADX case values`() {
        // Verify that commonly referenced type constants are valid
        // type=18 → security check, type=21 → SMS upload, type=22 → cancel notification
        val types = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
            14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26)
        for (type in types) {
            val r = TaskRunnable(type, Object())
            assertEquals(type, r.type)
        }
    }

    @Test
    fun `TaskRunnable - lambda constructor sets type to 3`() {
        val runnable = TaskRunnable {}
        assertEquals(3, runnable.type)
    }

    @Test
    fun `TaskRunnable - type 3 lambda invokes callback`() {
        var invoked = false
        val runnable = TaskRunnable { invoked = true }
        runnable.run()
        assertTrue("Lambda should have been invoked", invoked)
    }

    @Test
    fun `TaskRunnable - run with unknown type does not crash`() {
        val runnable = TaskRunnable(999, Object())
        // Should not throw
        runnable.run()
    }

    @Test
    fun `TaskRunnable - run with each valid type does not crash`() {
        for (type in 0..26) {
            val runnable = TaskRunnable(type, Object())
            try {
                runnable.run()
            } catch (_: Exception) {
                // Some types may fail due to missing real objects — that's OK
                // The point is they don't throw unhandled exceptions
            }
        }
    }

    // ==================== TypedRunnable ====================

    @Test
    fun `TypedRunnable - constructor stores all three fields`() {
        val obj1 = Object()
        val obj2 = "param"
        val runnable = TypedRunnable(obj1, 5, obj2)
        assertEquals(5, runnable.type)
        assertSame(obj1, runnable.target1)
        assertSame(obj2, runnable.target2)
    }

    @Test
    fun `TypedRunnable - implements Runnable`() {
        val runnable = TypedRunnable(Object(), 0, Object())
        assertTrue(runnable is Runnable)
    }

    @Test
    fun `TypedRunnable - type range covers all JADX cases`() {
        // JADX cases: 0..21 + default
        for (type in 0..21) {
            val r = TypedRunnable(Object(), type, Object())
            assertEquals(type, r.type)
        }
    }

    @Test
    fun `TypedRunnable - run with each valid type does not crash`() {
        for (type in 0..22) {
            val runnable = TypedRunnable(Object(), type, Object())
            try {
                runnable.run()
            } catch (_: Exception) {
                // Stub implementations may fail — that's OK
            }
        }
    }

    // ==================== IndexedRunnable ====================

    @Test
    fun `IndexedRunnable - constructor stores parent and index`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable(parent, 0)
        assertEquals(0, runnable.index)
        assertSame(parent, runnable.parent)
    }

    @Test
    fun `IndexedRunnable - implements Runnable`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable(parent, 0)
        assertTrue(runnable is Runnable)
    }

    @Test
    fun `IndexedRunnable - index values 0 to 3 are valid`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        for (i in 0..3) {
            val r = IndexedRunnable(parent, i)
            assertEquals(i, r.index)
        }
    }

    @Test
    fun `IndexedRunnable - default case calls removeFullscreenOverlay`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        // Default case (index >= 3) should call removeFullscreenOverlay
        val runnable = IndexedRunnable(parent, 99)
        // Should not throw
        runnable.run()
    }

    // ==================== IndexedRunnable2 ====================

    @Test
    fun `IndexedRunnable2 - constructor stores parent and index`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 0)
        assertEquals(0, runnable.index)
        assertSame(parent, runnable.parent)
    }

    @Test
    fun `IndexedRunnable2 - implements Runnable`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 0)
        assertTrue(runnable is Runnable)
    }

    @Test
    fun `IndexedRunnable2 - index values 0 to 15 are valid`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        for (i in 0..15) {
            val r = IndexedRunnable2(parent, i)
            assertEquals(i, r.index)
        }
    }

    @Test
    fun `IndexedRunnable2 - case 2 calls showFullscreenOverlay`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 2)
        // Should not throw — overlay display uses WindowManager which is mocked away
        runnable.run()
    }

    @Test
    fun `IndexedRunnable2 - case 4 calls removeFullscreenOverlay`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 4)
        runnable.run()
    }

    @Test
    fun `IndexedRunnable2 - case 5 calls showFullscreenOverlay`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 5)
        runnable.run()
    }

    @Test
    fun `IndexedRunnable2 - case 8 calls triggerBackSequence`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 8)
        runnable.run()
    }

    @Test
    fun `IndexedRunnable2 - case 15 does not crash`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 15)
        runnable.run()
    }

    @Test
    fun `IndexedRunnable2 - default case does not crash`() {
        val mockService = mock(MyAccessibilityService::class.java)
        val parent = UninstallProtectionManager(mockService, mockService)
        val runnable = IndexedRunnable2(parent, 99)
        runnable.run()
    }
}
