package com.storm.safe.rock.service.modules.yw5xud

import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test

class AllowKeywordsTest {
    @Test
    fun `ALLOW contains chinese and english variants`() {
        assertTrue("允许 missing", AllowKeywords.ALLOW.contains("允许"))
        assertTrue("允許 (traditional) missing", AllowKeywords.ALLOW.contains("允許"))
        assertTrue("Allow missing", AllowKeywords.ALLOW.contains("Allow"))
        assertTrue("Autoriser (fr) missing", AllowKeywords.ALLOW.contains("Autoriser"))
        assertTrue("Permitir (es/pt) missing", AllowKeywords.ALLOW.contains("Permitir"))
    }

    @Test
    fun `ENABLE covers chinese variants`() {
        assertTrue("启用 missing", AllowKeywords.ENABLE.contains("启用"))
        assertTrue("開啟 missing", AllowKeywords.ENABLE.contains("開啟"))
        assertTrue("Enable missing", AllowKeywords.ENABLE.contains("Enable"))
    }

    @Test
    fun `CONFIRM_OK covers common confirmation words`() {
        assertTrue("确定 missing", AllowKeywords.CONFIRM_OK.contains("确定"))
        assertTrue("OK missing", AllowKeywords.CONFIRM_OK.contains("OK"))
        assertTrue("好 missing", AllowKeywords.CONFIRM_OK.contains("好"))
        assertTrue("Yes missing", AllowKeywords.CONFIRM_OK.contains("Yes"))
    }

    @Test
    fun `CANCEL_NO covers common rejection words`() {
        assertTrue("取消 missing", AllowKeywords.CANCEL_NO.contains("取消"))
        assertTrue("Cancel missing", AllowKeywords.CANCEL_NO.contains("Cancel"))
    }

    @Test
    fun `matchesAny finds keyword in text`() {
        assertTrue(AllowKeywords.matchesAny("点击允许按钮", AllowKeywords.ALLOW))
        assertTrue(AllowKeywords.matchesAny("Click Allow", AllowKeywords.ALLOW))
        assertEquals(false, AllowKeywords.matchesAny("取消", AllowKeywords.ALLOW))
    }

    @Test
    fun `ALLOW list size is at least 70 entries matching vendor`() {
        assertTrue("ALLOW size=${AllowKeywords.ALLOW.size}, expected >=70", AllowKeywords.ALLOW.size >= 70)
    }

    // -- Task 3: 华为专属弹窗确认词 --

    @Test
    fun `ALLOW contains Huawei simplified Chinese keywords`() {
        val huaweiSimplified = listOf(
            "仅使用期间允许", "本次使用允许", "允许本次使用", "本次使用时允许",
            "每次都询问", "忽略", "不再提示", "不再询问", "知道了", "我知道了",
            "允许管理所有文件", "允许访问所有文件",
            "允许使用照片和视频", "允许访问照片和视频",
            "允许通知", "发送通知", "全部允许", "允许全部",
            "开启", "打开", "同意"
        )
        for (kw in huaweiSimplified) {
            assertTrue("Huawei keyword '$kw' missing from ALLOW", AllowKeywords.ALLOW.contains(kw))
        }
    }

    @Test
    fun `ALLOW contains Huawei traditional Chinese keywords`() {
        val huaweiTraditional = listOf(
            "僅使用期間允許", "本次使用允許", "允許本次使用",
            "允許管理所有檔案", "允許存取所有檔案",
            "全部允許", "開啟", "打開"
        )
        for (kw in huaweiTraditional) {
            assertTrue("Huawei traditional keyword '$kw' missing from ALLOW", AllowKeywords.ALLOW.contains(kw))
        }
    }

    @Test
    fun `ALLOW contains Huawei English keywords`() {
        val huaweiEnglish = listOf(
            "Allow always", "While using the app", "Agree", "Permit"
        )
        for (kw in huaweiEnglish) {
            assertTrue("Huawei English keyword '$kw' missing from ALLOW", AllowKeywords.ALLOW.contains(kw))
        }
    }
}
