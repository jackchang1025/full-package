package com.storm.safe.rock.auto

import com.storm.safe.rock.auto.condition.CombineFilter
import com.storm.safe.rock.auto.condition.StringCondition
import org.junit.Assert.*
import org.junit.Test

class CombineFilterTest {
    @Test fun `AND filter creates with correct size`() {
        val f = CombineFilter.and(
            StringCondition.textContains("a"),
            StringCondition.className("Switch")
        )
        assertEquals(2, f.filters.size)
    }

    @Test fun `OR filter creates`() {
        val f = CombineFilter.or(StringCondition.textContains("A"), StringCondition.textContains("B"))
        assertNotNull(f)
    }

    @Test fun `switchWidget convenience`() { assertNotNull(CombineFilter.switchWidget()) }
    @Test fun `scrollable convenience`() { assertNotNull(CombineFilter.scrollable()) }
    @Test fun `checkBox convenience`() { assertNotNull(CombineFilter.checkBox()) }
    @Test fun `StringCondition textContains`() { assertNotNull(StringCondition.textContains("x")) }
    @Test fun `StringCondition textEquals`() { assertNotNull(StringCondition.textEquals("x")) }
    @Test fun `StringCondition className`() { assertNotNull(StringCondition.className("Switch")) }
    @Test fun `StringCondition viewId`() { assertNotNull(StringCondition.viewId("android:id/switch_widget")) }
}
