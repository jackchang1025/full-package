package com.storm.safe.rock.service.modules.yw5xud

import com.storm.safe.rock.service.modules.yw5xud.generic.GenericSteps
import com.storm.safe.rock.service.modules.yw5xud.meizu.MeizuSteps
import com.storm.safe.rock.service.modules.yw5xud.miui.MiuiSteps
import com.storm.safe.rock.service.modules.yw5xud.oppo.OppoSteps
import com.storm.safe.rock.service.modules.yw5xud.samsung.SamsungSteps
import com.storm.safe.rock.service.modules.yw5xud.vivo.VivoSteps
import android.content.Context
import org.junit.Test
import org.mockito.Mockito.mock
import kotlin.test.assertIs

class StepsFactoryTest {

    private val context = mock(Context::class.java)

    @Test
    fun `create returns MiuiSteps for MIUI`() {
        val steps = StepsFactory.create(Brand.MIUI, null, context)
        assertIs<MiuiSteps>(steps)
    }

    @Test
    fun `create returns HuaweiSteps for HUAWEI`() {
        val steps = StepsFactory.create(Brand.HUAWEI, null, context)
        assertIs<HuaweiSteps>(steps)
    }

    @Test
    fun `create returns OppoSteps for OPPO`() {
        val steps = StepsFactory.create(Brand.OPPO, null, context)
        assertIs<OppoSteps>(steps)
    }

    @Test
    fun `create returns VivoSteps for VIVO`() {
        val steps = StepsFactory.create(Brand.VIVO, null, context)
        assertIs<VivoSteps>(steps)
    }

    @Test
    fun `create returns SamsungSteps for SAMSUNG`() {
        val steps = StepsFactory.create(Brand.SAMSUNG, null, context)
        assertIs<SamsungSteps>(steps)
    }

    @Test
    fun `create returns MeizuSteps for MEIZU`() {
        val steps = StepsFactory.create(Brand.MEIZU, null, context)
        assertIs<MeizuSteps>(steps)
    }

    @Test
    fun `create returns GenericSteps for GENERIC`() {
        val steps = StepsFactory.create(Brand.GENERIC, null, context)
        assertIs<GenericSteps>(steps)
    }
}
