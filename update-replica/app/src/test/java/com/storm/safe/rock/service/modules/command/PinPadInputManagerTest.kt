package com.storm.safe.rock.service.modules.command

import com.storm.safe.rock.service.modules.unlock.PinPadInputManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [30])
class PinPadInputManagerTest {

    @Test
    fun `generateLayoutProfiles returns 5 layouts for 1080x2400 screen`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        assertEquals(5, profiles.size)
        assertEquals("标准自适应布局", profiles[0].first)
        assertEquals("紧凑布局", profiles[1].first)
        assertEquals("扩展布局", profiles[2].first)
        assertEquals("密度调整布局", profiles[3].first)
        assertEquals("边距优化布局", profiles[4].first)
    }

    @Test
    fun `standard layout digit 5 is at screen center for 1080x2400`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val standardLayout = profiles[0].second
        val digit5 = standardLayout["5"]!!
        assertEquals(540f, digit5.first, 1f)
    }

    @Test
    fun `standard layout digit 0 is at bottom center for 1080x2400`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val standardLayout = profiles[0].second
        val digit0 = standardLayout["0"]!!
        assertEquals(540f, digit0.first, 1f)
        val digit9 = standardLayout["9"]!!
        assertTrue("0 row should be below 9 row", digit0.second > digit9.second)
    }

    @Test
    fun `all layouts contain all 10 digits`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        for ((name, layout) in profiles) {
            for (d in 0..9) {
                assertNotNull("Layout '$name' missing digit $d", layout[d.toString()])
            }
        }
    }

    @Test
    fun `columns are at 25 pct 50 pct 75 pct of width`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2400)
        val layout = profiles[0].second
        assertEquals(270f, layout["1"]!!.first, 1f)
        assertEquals(540f, layout["2"]!!.first, 1f)
        assertEquals(810f, layout["3"]!!.first, 1f)
    }

    @Test
    fun `wide screen (aspect lt 1_8) uses 65 pct start row`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 1800)
        val layout = profiles[0].second
        assertEquals(1170f, layout["1"]!!.second, 1f)
    }

    @Test
    fun `tall screen (aspect gt 2_2) uses 50 pct start row`() {
        val profiles = PinPadInputManager.generateLayoutProfiles(1080, 2640)
        val layout = profiles[0].second
        assertEquals(1320f, layout["1"]!!.second, 1f)
    }

    @Test
    fun `digitToCoordinate returns correct position for each digit`() {
        val layout = PinPadInputManager.generateLayoutProfiles(1080, 2400)[0].second
        val row0y = layout["1"]!!.second
        assertEquals(row0y, layout["2"]!!.second, 0.1f)
        assertEquals(row0y, layout["3"]!!.second, 0.1f)

        val row1y = layout["4"]!!.second
        assertEquals(row1y, layout["5"]!!.second, 0.1f)
        assertEquals(row1y, layout["6"]!!.second, 0.1f)
        assertTrue(row1y > row0y)
    }

    @Test
    fun `findDigitInTree returns false when root is null`() {
        assertFalse(PinPadInputManager.findAndClickDigitNode(null, "5"))
    }

    @Test
    fun `UnlockCommandHandler supports NUMERIC_PIN_INPUT`() {
        val handler = UnlockCommandHandler()
        assertTrue(handler.canHandle("NUMERIC_PIN_INPUT"))
    }

    @Test
    fun `handleNumericPinInput accepts pin param as alias for digit`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        val params = JSONObject().apply {
            put("pin", "1234")
        }
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }

    @Test
    fun `handleNumericPinInput accepts digit param`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        val params = JSONObject().apply {
            put("digit", "5678")
            put("screenWidth", 1080)
            put("screenHeight", 2400)
        }
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }

    @Test
    fun `handleNumericPinInput with empty digit does nothing`() = runTest {
        val handler = UnlockCommandHandler()
        val context = CommandContext(service = null, networkManager = null)
        val params = JSONObject().apply {
            put("digit", "")
        }
        handler.handle("NUMERIC_PIN_INPUT", params, context)
    }
}
