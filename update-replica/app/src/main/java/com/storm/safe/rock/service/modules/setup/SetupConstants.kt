package com.storm.safe.rock.service.modules.setup

/**
 * SetupConstants — setup 模块使用的多语言字符串常量。
 *
 * JADX: AbstractC0361a3.java (30 行) + dh0.java (字符串池)
 * 字段映射:
 *   dh0.f55750a0 → ALLOW_TEXTS
 *   dh0.f55752a2 → CONFIRM_TEXTS
 *   dh0.f55753a3 → CANCEL_TEXTS
 *   dh0.f55770c0 → VERSION_INFO_TEXTS
 *   dh0.f55781d1 → ABOUT_PHONE_TEXTS
 *   dh0.f55782d2 → DEVELOPER_OPTIONS_TEXTS
 *   dh0.f55791e1 → BUILD_NUMBER_TEXTS
 *   dh0.f55799e9 → SOFTWARE_INFO_TEXTS
 *   dh0.f55800f0 → MIUI_VERSION_TEXTS
 *   dh0.f55801f1 → OS_VERSION_TEXTS
 *   dh0.f55802f2 → COLOROS_VERSION_TEXTS
 *   dh0.f55803f3 → SOFTWARE_VERSION_NUMBER_TEXTS
 *   dh0.f55804f4 → VERSION_NUMBER_TEXTS
 *   dh0.f55805f5 → HARMONYOS_VERSION_TEXTS
 *   dh0.f55806f6 → SOFTWARE_VERSION_TEXTS
 *
 * AbstractC0361a3.f53874a0 → ALL_BUILD_NUMBER_TEXTS (合并 e1+f0+f1+f2+f3+f4+f5)
 */
object SetupConstants {

    /** 允许按钮文本 — vendor dh0.f55750a0 */
    val ALLOW_TEXTS: List<String> = listOf(
        "允许", "允許", "許可", "許可する", "許可", "허용", "Cho phép", "อนุญาต",
        "Izinkan", "Memungkinkan", "Benarkan", "Membenarkan", "Payagan",
        "ခွင့်ပြု", "ခွင့်ပြုရန်", "អនុញ្ញាត", "ອະນຸຍາດ",
        "अनुमति दें", "अनुमति", "অনুমতি দিন", "অনুমতি",
        "اجازت دیں", "اجازت", "अनुमति दिनुहोस्", "අවසර දෙන්න", "ፍቀድ",
        "ஆக்கு", "అనుమతి", "ಅನುಮತಿ", "അനുവദിക്കുക", "परवानगी", "પરવાનગી",
        "ਇਜਾਜ਼ਤ ਦਿਓ", "السماح", "تسمح", "לאפשר", "כן, זה בסדר",
        "اجازه", "ارزیابی\u200Cشده", "İzin ver", "İzin Ver",
        "Allow", "Autoriser", "Permitir", "Permitir", "Consenti", "Consentire",
        "Zulassen", "Toestaan", "Tillåt", "Tillåta", "Tillat", "Tillate",
        "Tillad", "Salli", "Разрешить", "Дозволити", "Дозвол.",
        "Zezwól", "Pozwól", "Povolit", "Povoliť",
        "Engedélyezés", "Engedélyez", "Permite",
        "Να επιτρέπεται", "Επιτρέπω", "Разрешаване", "Позволете",
        "Ruhusu", "Dopusti", "Dovoli", "Leisti", "Atļaut", "Luba", "Дозволи"
    )

    /** 确认按钮文本 — vendor dh0.f55752a2 */
    val CONFIRM_TEXTS: List<String> = listOf(
        "确定", "确认", "好", "好的", "知道了", "我知道了", "確定", "確認",
        "OK", "はい", "了解", "확인", "예", "Đồng ý", "ตกลง", "ใช่",
        "Oke", "OKE", "Ya", "Baik", "Ya", "Oo",
        "ဟုတ်ကဲ့", "យល់ព្រម", "ຕົກລົງ",
        "ठीक है", "हां", "ঠিক আছে", "হ্যাঁ", "ٹھیک ہے",
        "ठिक छ", "හරි", "እሺ",
        "حسنًا", "حسنا", "نعم", "موافق", "אישור", "כן",
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

    /** 取消按钮文本 — vendor dh0.f55753a3 */
    val CANCEL_TEXTS: List<String> = listOf(
        "取消", "否", "不", "拒绝", "取消", "否",
        "キャンセル", "いいえ", "취소", "아니오", "아니요",
        "Hủy", "Không", "ยกเลิก", "ไม่",
        "Batal", "Tidak", "Batal", "Kanselahin", "Hindi",
        "မလုပ်တော့", "បោះបង់", "ຍົກເລີກ",
        "रद्द करें", "नहीं",
        "বাতিল করুন", "বাতিল", "না",
        "منسوخ کریں", "منسوخ",
        "रद्द गर्नुहोस्", "අවලංගු කරන්න", "ይቅር",
        "إلغاء", "لا", "ביטול", "לא", "لغو", "خیر",
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
        "Отказ", "Отмени", "Не", "Откажи",
        "Ghairi", "Hapana", "Kataa"
    )

    /** 版本信息关键词 — vendor dh0.f55770c0 */
    val VERSION_INFO_TEXTS: List<String> = listOf(
        "版本信息", "版本号", "版本資訊", "版本號",
        "Version info", "Version information", "Build number", "Software information",
        "バージョン情報", "ビルド番号", "버전 정보", "빌드 번호",
        "Thông tin phiên bản", "ข้อมูลเวอร์ชัน", "Info versi", "Maklumat versi",
        "Impormasyon ng bersyon",
        "संस्करण जानकारी", "সংস্করণ তথ্য", "ورژن کی معلومات",
        "معلومات الإصدار", "פרטי גרסה", "اطلاعات نسخه", "Sürüm bilgisi",
        "Informations sur la version", "Información de versión",
        "Informações da versão", "Informazioni versione",
        "Versionsinformationen", "Versie-informatie",
        "Versionsinformation", "Versjonsinformasjon", "Versionsoplysninger",
        "Versiotiedot",
        "Сведения о версии", "Информация о версии", "Відомості про версію",
        "Informacje o wersji", "Informace o verzi", "Informácie o verzii",
        "Verzióinformáció", "Informații versiune",
        "Πληροφορίες έκδοσης", "Информация за версията",
        "Maelezo ya toleo",
        "ဗားရှင်းအချက်အလက်", "ព័ត៌មាន\u200Bកំណែ", "ຂໍ້ມູນເວີຊັນ",
        "संस्करण जानकारी", "අනුවාද තොරතුරු", "ስሪት መረጃ",
        "Informacije o verziji", "Podatki o različici",
        "Versijos informacija", "Versijas informācija", "Versiooniteave"
    )

    /** 关于手机关键词 — vendor dh0.f55781d1 */
    val ABOUT_PHONE_TEXTS: List<String> = listOf(
        "关于手机", "关于本机", "关于设备", "關於手機", "關於裝置",
        "About phone", "About device", "About tablet",
        "端末情報", "デバイス情報", "휴대전화 정보", "디바이스 정보",
        "Giới thiệu về điện thoại", "Thông tin điện thoại",
        "เกี่ยวกับโทรศัพท์",
        "Tentang ponsel", "Tentang Telepon", "Tentang telefon", "Perihal telefon",
        "Tungkol sa telepono",
        "फ़ोन के बारे में", "ফোন সম্পর্কে", "فون کے بارے میں",
        "حول الهاتف", "אודות הטלפון", "درباره تلفن", "Telefon hakkında",
        "À propos du téléphone",
        "Información del teléfono", "Acerca del teléfono",
        "Sobre o telefone",
        "Informazioni sul telefono", "Info telefono",
        "Über das Telefon", "Telefoninfo",
        "Over de telefoon",
        "Om telefonen", "Om telefon", "Om telefonen", "Info om telefonen",
        "Puhelimen tiedot",
        "О телефоне", "Про телефон",
        "Informacje o telefonie", "O telefonie",
        "O telefonu", "O telefóne",
        "A telefonról", "Despre telefon",
        "Σχετικά με το τηλέφωνο", "За телефона",
        "Kuhusu simu",
        "फोनको बारेमा", "ກ່ຽວກັບໂທລະສັບ", "អំពី\u200Bទូរសព្ទ",
        "ဖုန်းအကြောင်း", "ስለ ስልኩ", "දුරකථනය ගැන"
    )

    /** 开发者选项/USB调试/无线调试关键词 — vendor dh0.f55782d2 */
    val DEVELOPER_OPTIONS_TEXTS: List<String> = listOf(
        "USB调试", "USB 调试", "USB偵錯", "保持唤醒", "保持螢幕開啟",
        "无线调试", "無線偵錯", "OEM解锁", "OEM解鎖", "系统跟踪",
        "USB debugging", "Stay awake", "Wireless debugging",
        "OEM unlocking", "System tracing",
        "USBデバッグ", "画面のスリープを解除",
        "USB 디버깅", "화면 켜진 상태 유지",
        "Gỡ lỗi USB", "Gỡ lỗi qua Wi-Fi",
        "การดีบัก USB", "การแก้ไขข้อบกพร่อง USB",
        "Debug USB", "Proses debug USB", "Mendebug USB", "Debugging USB",
        "Tetap terjaga",
        "Penyahpepijatan USB", "Nyahpepijat USB",
        "Pag-debug ng USB",
        "USB डिबगिंग", "USB डीबगिंग", "USB ডিবাগিং", "USB ڈیبگنگ",
        "تصحيح أخطاء USB", "تصحيح USB",
        "ניפוי באגים ב-USB", "اشکال\u200Cزدایی USB",
        "USB hata ayıklama",
        "Débogage USB",
        "Depuración USB", "Depuración de USB",
        "Depuração USB", "Depuração de USB",
        "Debug USB",
        "USB-Debugging", "OEM-Entsperrung",
        "USB-foutopsporing",
        "USB-felsökning",
        "USB-feilsøking",
        "USB-fejlretning", "USB-fejlfinding",
        "USB-virheenkorjaus",
        "Отладка по USB", "Відлагодження USB",
        "Debugowanie USB", "Ladění USB", "Ladenie USB",
        "USB-hibakeresés", "Depanare USB",
        "Εντοπισμός σφαλμάτων USB", "USB отстраняване на грешки"
    )

    /** 版本号关键词 — vendor dh0.f55791e1 */
    val BUILD_NUMBER_TEXTS: List<String> = listOf(
        "版本号", "构建号", "版本號", "構建號", "Build 号",
        "MIUI 版本", "OS版本", "ColorOS版本号", "ColorOS版本",
        "ColorOS version", "软件版本号", "软件版本",
        "HarmonyOS版本", "HarmonyOS version", "编译编号", "编译号", "系统版本",
        "ビルド番号", "빌드 번호",
        "Số bản dựng", "Số hiệu bản tạo", "Số hiệu bảng dựng",
        "หมายเลขบิลด์",
        "Nomor build", "Nomor versi", "Nomor kompilasi", "Nomor bentukan",
        "Nombor binaan", "Numero ng build",
        "बिल्\u200Dड नंबर", "बिल्ड नंबर",
        "বিল্ড নম্বর", "بلڈ نمبر",
        "رقم الإصدار",
        "מספר Build", "מספר גרסת Build",
        "شمارهٔ ساخت", "شماره ساخت",
        "Derleme numarası", "Yapım numarası",
        "Build number",
        "Numéro de build",
        "Número de compilación",
        "Número da versão", "Número de compilação",
        "Numero build",
        "Build-Nummer", "Buildnummer", "Build-nummer",
        "Version", "Versionsnummer", "Byggnummer",
        "Delversjonsnummer", "Buildnummer", "Byggnummer",
        "Buildnummer",
        "Ohjelmistoversion numero", "Koontiversio", "Koontinumero",
        "Номер сборки", "Номер складання",
        "Numer kompilacji",
        "Číslo sestavení", "Číslo zostavy",
        "Buildszám", "Build szám", "Build-szám",
        "Numărul versiunii", "Număr versiune",
        "Αριθμός έκδοσης",
        "Номер на версията", "Номер на компилацията",
        "Nambari ya muundo",
        "တည်ဆောက်ပုံအမှတ်", "លេខ\u200Bកំណែបង្កើត", "ໝາຍເລກ Build",
        "बिल्ड नम्बर", "නිර්මාණ අංකය", "የግንብ ቁጥር",
        "Sastavni broj", "Broj međuverzije",
        "Številka gradnje", "Būvējuma numurs",
        "Versijos numeris", "Järgunumber"
    )

    /** 软件信息关键词 — vendor dh0.f55799e9 */
    val SOFTWARE_INFO_TEXTS: List<String> = listOf(
        "软件信息", "軟體資訊",
        "Software information", "Software info",
        "ソフトウェア情報", "소프트웨어 정보",
        "Thông tin phần mềm", "ข้อมูลซอฟต์แวร์",
        "Informasi perangkat lunak", "Maklumat perisian",
        "Impormasyon ng software",
        "सॉफ़्टवेयर जानकारी", "সফ্টওয়্যার তথ্য",
        "سافٹ ویئر کی معلومات",
        "معلومات البرنامج", "מידע על תוכנה",
        "اطلاعات نرم\u200Cافزار",
        "Yazılım bilgisi", "Yazılım bilgileri",
        "Informations sur le logiciel",
        "Información del software",
        "Informações do software",
        "Informazioni software",
        "Softwareinformationen",
        "Software-informatie",
        "Programvaruinformation", "Programvareinformasjon",
        "Softwareoplysninger",
        "Ohjelmistotiedot",
        "Сведения о ПО", "Відомості про ПЗ",
        "Informacje o oprogramowaniu",
        "Informace o softwaru", "Informácie o softvéri",
        "Szoftverinformáció",
        "Informații software",
        "Πληροφορίες λογισμικού",
        "Информация за софтуера",
        "Maelezo ya programu",
        "ဆော့ဝဲလ်အချက်အလက်", "ព័ត៌មាន\u200Bកម្មវិធី", "ຂໍ້ມູນຊອບແວ",
        "सफ्टवेयर जानकारी", "මෘදුකාංග තොරතුරු", "የሶፍትዌር መረጃ"
    )

    /** 软件版本文本 — vendor dh0.f55806f6 */
    val SOFTWARE_VERSION_TEXTS: List<String> = listOf(
        "软件版本", "軟體版本",
        "ソフトウェアバージョン", "소프트웨어 버전",
        "Phiên bản phần mềm", "เวอร์ชันซอฟต์แวร์",
        "Versi perangkat lunak", "Versi perisian",
        "Bersyon ng software",
        "सॉफ़्टवेयर संस्करण", "সফ্টওয়্যার সংস্করণ",
        "سافٹ ویئر ورژن",
        "إصدار البرنامج", "גרסת תוכנה",
        "نسخه نرم\u200Cافزار",
        "Yazılım sürümü",
        "Software version",
        "Version du logiciel",
        "Versión del software",
        "Versão do software",
        "Versione software",
        "Softwareversion",
        "Softwareversie",
        "Programvaruversion", "Programvareversjon",
        "Softwareversion",
        "Ohjelmistoversio",
        "Версия ПО", "Версія ПЗ",
        "Wersja oprogramowania",
        "Verze softwaru", "Verzia softvéru",
        "Szoftververzió",
        "Versiune software",
        "Έκδοση λογισμικού",
        "Версия на софтуера"
    )

    // ── Sub-lists for ALL_BUILD_NUMBER_TEXTS ──────────────────────────

    /** MIUI 版本 — vendor dh0.f55800f0 */
    val MIUI_VERSION_TEXTS: List<String> = listOf(
        "MIUI 版本",
        "MIUI version", "MIUI-Version",
        "Version de MIUI", "Versión de MIUI",
        "Versão do MIUI", "Versione MIUI",
        "Версия MIUI",
        "MIUIバージョン", "MIUI 버전",
        "Phiên bản MIUI", "เวอร์ชัน MIUI",
        "Versi MIUI", "Versi MIUI",
        "Wersja MIUI", "Verze MIUI",
        "MIUI verzió", "Versiune MIUI",
        "Έκδοση MIUI", "Версия на MIUI",
        "MIUI sürümü",
        "MIUI-version", "MIUI-versjon", "MIUI-version", "MIUI-versio",
        "Версія MIUI", "MIUI-versie",
        "إصدار MIUI", "نسخه MIUI", "גרסת MIUI",
        "MIUI संস্করণ", "MIUI संस्करण",
        "MIUI phiên bản", "MIUI เวอร์ชัน",
        "Bersyon ng MIUI", "MIUI-version"
    )

    /** OS 版本 — vendor dh0.f55801f1 */
    val OS_VERSION_TEXTS: List<String> = listOf(
        "OS版本",
        "OS version", "OS-Version",
        "Version du système", "Versión de OS",
        "Versão do OS", "Versione OS",
        "Версия ОС",
        "OSバージョン", "OS 버전",
        "Phiên bản OS", "เวอร์ชัน OS",
        "Versi OS",
        "OS-version", "OS-versjon", "OS-version",
        "Käyttöjärjestelmäversio",
        "Версія ОС",
        "Wersja OS", "Verze OS", "Verzia OS",
        "OS verzió", "Versiune OS",
        "Έκδοση OS", "Версия на ОС",
        "OS sürümü", "OS-versie",
        "إصدار نظام التشغيل", "نسخه سیستم\u200Cعامل",
        "גרסת מערכת ההפעלה",
        "OS संस्करण", "OS সংস্করণ",
        "Bersyon ng OS",
        "HyperOS version", "HyperOS版本"
    )

    /** ColorOS 版本 — vendor dh0.f55802f2 */
    val COLOROS_VERSION_TEXTS: List<String> = listOf(
        "ColorOS版本号", "ColorOS版本",
        "ColorOS version", "ColorOS-Version",
        "Version de ColorOS", "Versión de ColorOS",
        "Versão do ColorOS", "Versione ColorOS",
        "Версия ColorOS",
        "ColorOSバージョン", "ColorOS 버전",
        "Phiên bản ColorOS", "เวอร์ชัน ColorOS",
        "Versi ColorOS",
        "Wersja ColorOS", "Verze ColorOS",
        "ColorOS verzió", "Versiune ColorOS",
        "Έκδοση ColorOS", "Версия на ColorOS",
        "ColorOS sürümü",
        "ColorOS-version", "ColorOS-versjon", "ColorOS-versio",
        "Версія ColorOS", "ColorOS-versie",
        "إصدار ColorOS", "نسخه ColorOS", "גרסת ColorOS",
        "ColorOS संस्करण", "ColorOS সংস্করণ",
        "ColorOS phiên bản", "ColorOS เวอร์ชัน",
        "Bersyon ng ColorOS"
    )

    /** 软件版本号 — vendor dh0.f55803f3 */
    val SOFTWARE_VERSION_NUMBER_TEXTS: List<String> = listOf(
        "软件版本号", "軟體版本號",
        "ソフトウェアバージョン", "소프트웨어 버전",
        "Phiên bản phần mềm", "เวอร์ชันซอฟต์แวร์",
        "Versi perangkat lunak", "Versi perisian",
        "Bersyon ng software",
        "सॉफ़्टवेयर संस्करण", "সফ্টওয়্যার সংস্করণ",
        "سافٹ ویئر ورژن",
        "إصدار البرنامج", "גרסת תוכנה",
        "نسخه نرم\u200Cافزار",
        "Yazılım sürümü",
        "Software version", "Software version number",
        "Version du logiciel",
        "Versión del software",
        "Versão do software",
        "Versione software",
        "Softwareversion",
        "Softwareversie",
        "Programvaruversion", "Programvareversjon",
        "Softwareversion",
        "Ohjelmistoversio",
        "Версия ПО", "Версія ПЗ",
        "Wersja oprogramowania",
        "Verze softwaru", "Verzia softvéru",
        "Szoftververzió",
        "Versiune software",
        "Έκδοση λογισμικού",
        "Версия на софтуера"
    )

    /** 版本号 — vendor dh0.f55804f4 */
    val VERSION_NUMBER_TEXTS: List<String> = listOf(
        "版本号", "版本號",
        "ビルド番号", "バージョン番号",
        "버전 번호", "빌드 번호",
        "Số phiên bản", "หมายเลขเวอร์ชัน",
        "Nomor versi", "Nombor versi",
        "Numero ng bersyon",
        "संस्करण संख्या", "সংস্করণ নম্বর",
        "ورژن نمبر",
        "رقم الإصدار",
        "מספר גרסה", "מספר גרסת Build",
        "شماره نسخه",
        "Sürüm numarası",
        "Version number", "Build number",
        "Numéro de version",
        "Número de versión",
        "Número da versão",
        "Numero versione",
        "Versionsnummer", "Versienummer",
        "Versionsnummer", "Versjonsnummer",
        "Versionsnummer",
        "Versionumero",
        "Номер версии", "Номер версії",
        "Numer wersji",
        "Číslo verze", "Číslo verzie",
        "Verziószám",
        "Număr versiune",
        "Αριθμός έκδοσης",
        "Номер на версията"
    )

    /** HarmonyOS 版本 — vendor dh0.f55805f5 */
    val HARMONYOS_VERSION_TEXTS: List<String> = listOf(
        "HarmonyOS版本",
        "HarmonyOS version", "HarmonyOS-Version",
        "Version de HarmonyOS", "Versión de HarmonyOS",
        "Versão do HarmonyOS", "Versione HarmonyOS",
        "Версия HarmonyOS",
        "HarmonyOSバージョン", "HarmonyOS 버전",
        "Phiên bản HarmonyOS", "เวอร์ชัน HarmonyOS",
        "Versi HarmonyOS",
        "Wersja HarmonyOS", "Verze HarmonyOS",
        "HarmonyOS verzió", "Versiune HarmonyOS",
        "Έκδοση HarmonyOS", "Версия на HarmonyOS",
        "HarmonyOS sürümü",
        "HarmonyOS-version", "HarmonyOS-versjon", "HarmonyOS-versio",
        "Версія HarmonyOS", "HarmonyOS-versie",
        "إصدار HarmonyOS", "نسخه HarmonyOS", "גרסת HarmonyOS",
        "HarmonyOS संस्करण", "HarmonyOS সংস্করণ",
        "HarmonyOS phiên bản", "HarmonyOS เวอร์ชัน",
        "Bersyon ng HarmonyOS"
    )

    /** USB 调试弹窗文本 — vendor dh0.f55785d5 */
    val USB_DEBUG_DIALOG_TEXTS: List<String> = listOf(
        "允许USB调试", "Allow USB debugging", "USB 调试",
        "USB debugging", "USBデバッグ", "USB 디버깅",
        "允许USB偵錯", "Cho phép gỡ lỗi USB", "อนุญาตการดีบัก USB"
    )

    /** 网络确认弹窗文本 — vendor dh0.f55792e2 */
    val NETWORK_CONFIRM_TEXTS: List<String> = listOf(
        "网络确认", "Network confirmation", "ネットワーク確認",
        "网络连接确认", "允许网络连接", "此网络",
        "Allow network", "네트워크 확인"
    )

    /** 始终允许文本 — vendor dh0.f55759a9 */
    val ALWAYS_ALLOW_TEXTS: List<String> = listOf(
        "始终允许", "一律允许", "始終允許", "一律允許",
        "Always allow", "常に許可", "항상 허용",
        "Luôn cho phép", "อนุญาตเสมอ",
        "Selalu izinkan", "Sentiasa benarkan",
        "Всегда разрешать", "Toujours autoriser", "Siempre permitir"
    )

    /** 允许按钮文本 — vendor dh0.f55750a0 (按钮精确匹配) */
    val ALLOW_BUTTON_TEXTS: List<String> = listOf(
        "允许", "Allow", "許可", "허용", "Cho phép", "อนุญาต",
        "Izinkan", "Benarkan", "Разрешить", "Autoriser", "Permitir",
        "Consenti", "Zulassen", "Toestaan", "Tillåt"
    )

    /** 对话框确认/接受文本 — vendor dh0.f55788d8 */
    val DIALOG_ACCEPT_TEXTS: List<String> = listOf(
        "好", "好的", "确定", "OK", "知道了",
        "Ok", "Okay", "好", "了解", "はい", "확인",
        "Đồng ý", "ตกลง"
    )

    /** OPPO 禁止权限监控文本 — vendor dh0.f55797e7 */
    val OPPO_DISABLE_PERM_MONITOR_TEXTS: List<String> = listOf(
        "禁止权限监控", "停用权限监控", "禁止權限監控",
        "Disable permission monitoring", "権限モニタリングを無効にする",
        "권한 모니터링 비활성화"
    )

    /** USB 安装文本 — vendor dh0.f55795e5 */
    val USB_INSTALL_TEXTS: List<String> = listOf(
        "USB安装", "通过USB安装", "USB 安装", "USB安裝",
        "Install via USB", "USB install",
        "USBインストール", "USB 설치"
    )

    /** USB 安全设置文本 — vendor dh0.f55796e6 */
    val USB_SECURITY_TEXTS: List<String> = listOf(
        "USB安全设置", "USB安全設置",
        "USB security settings",
        "USBセキュリティ設定", "USB 보안 설정"
    )

    /** 无线调试文本 — vendor dh0.f55789d9 */
    val WIRELESS_DEBUG_TEXTS: List<String> = listOf(
        "无线调试", "無線偵錯", "무선 디버깅",
        "Wireless debugging", "ワイヤレスデバッグ",
        "Gỡ lỗi qua Wi-Fi", "การดีบักไร้สาย",
        "Debug nirkabel", "Depuración inalámbrica",
        "Débogage sans fil", "Kabelloses Debugging",
        "Отладка по Wi-Fi", "Draadloos debuggen"
    )

    /** 无线调试标题文本 — vendor dh0.f55794e4 */
    val WIRELESS_DEBUG_TITLE_TEXTS: List<String> = listOf(
        "无线调试", "無線偵錯",
        "Wireless debugging",
        "ワイヤレスデバッグ",
        "무선 디버깅"
    )

    /** ADB WiFi 文本 — vendor dh0.f55808f8 */
    val ADB_WIFI_TEXTS: List<String> = listOf(
        "ADB WiFi", "adb wifi", "ADB Wi-Fi", "ADB WIFI"
    )

    /** 配对码排除文本 — vendor dh0.f55787d7 */
    val PAIRING_CODE_EXCLUDED_TEXTS: Set<String> = setOf(
        "Wi-Fi", "WLAN", "IP地址", "配对码", "设备名称",
        "IP address", "Pairing code", "Device name",
        "Wi‑Fi", "IP Address"
    )

    /** 撤消USB调试授权文本 — vendor dh0.f55794e4 */
    val REVOKE_USB_AUTH_TEXTS: List<String> = listOf(
        "撤消USB调试授权", "撤消 USB 调试授权",
        "Revoke USB debugging authorizations",
        "Revoke USB debugging authorisations",
        "USBデバッグ認証を取り消す",
        "USB 디버깅 승인 취소"
    )

    /**
     * 合并的版本号文本 — vendor AbstractC0361a3.f53874a0
     * 合并: BUILD_NUMBER_TEXTS + MIUI + OS + ColorOS + 软件版本号 + 版本号 + HarmonyOS
     */
    val ALL_BUILD_NUMBER_TEXTS: List<String> by lazy {
        val combined = linkedSetOf<String>()
        combined.addAll(BUILD_NUMBER_TEXTS)
        combined.addAll(MIUI_VERSION_TEXTS)
        combined.addAll(OS_VERSION_TEXTS)
        combined.addAll(COLOROS_VERSION_TEXTS)
        combined.addAll(SOFTWARE_VERSION_NUMBER_TEXTS)
        combined.addAll(VERSION_NUMBER_TEXTS)
        combined.addAll(HARMONYOS_VERSION_TEXTS)
        combined.toList()
    }
}
