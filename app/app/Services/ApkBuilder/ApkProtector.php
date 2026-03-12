<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use Illuminate\Support\Facades\File;

/**
 * APK 保护器 — 对应旧版 VB.NET APKProtector.cs。
 * 通过操纵 ZIP 结构和 DEX 头部来干扰反编译工具。
 */
final class ApkProtector
{
    /** ZIP Central Directory 签名 */
    private const CD_SIGNATURE = "\x50\x4b\x01\x02";

    /** ZIP Local File Header 签名 */
    private const LFH_SIGNATURE = "\x50\x4b\x03\x04";

    /** ZIP End of Central Directory 签名 */
    private const EOCD_SIGNATURE = "\x50\x4b\x05\x06";

    /**
     * 旧版 APKProtector.cs 中的目标文件及其 CRC 标识值。
     * 用于在 ZIP Central Directory 中定位特定条目。
     */
    private const TARGET_CRCS = [
        'AndroidManifest.xml' => 20425,
        'resources.arsc' => 28061,
        'classes.dex' => 35000,
    ];

    /** 旧版使用的特殊版本号标记 (0xFEA9 = 65353) */
    private const CORRUPTED_VERSION = 65353;

    private bool $zeroSizes;

    private bool $corruptCRC;

    private bool $corruptOffsets;

    private bool $addFakeExtra;

    private bool $addPadding;

    private bool $addFakeEntries;

    private bool $randomCompressionMethod;

    private bool $addFakeLocalHeaders;

    private bool $enableFakeEncryption;

    private bool $enableEocdTampering;

    private bool $enablePathTraversalEntries;

    private bool $enableUnknownCompression;

    private bool $enableAxmlTampering;

    private int $fakeEntryCount;

    private const FAKE_ENCRYPTION_FLAG = 0xF741; // 对齐 caobizy.apk 的 0xff49 flags (0x0808 | 0xF741 = 0xff49)

    private const TARGET_FILENAMES = [
        'AndroidManifest.xml',
        'classes.dex',
        'classes2.dex',
        'classes3.dex',
        'classes4.dex',
        'classes5.dex',
        'classes6.dex',
        'classes7.dex',
        'resources.arsc',
    ];

    public function __construct(
        bool $zeroSizes = false,
        bool $corruptCRC = true,
        bool $corruptOffsets = false,
        bool $addFakeExtra = true,
        bool $addPadding = false,
        bool $addFakeEntries = true,
        bool $randomCompressionMethod = true,
        bool $addFakeLocalHeaders = true,
        bool $enableFakeEncryption = false,
        bool $enableEocdTampering = false,
        bool $enablePathTraversalEntries = false,
        bool $enableUnknownCompression = false,
        bool $enableAxmlTampering = false,
        int $fakeEntryCount = 120,
    ) {
        $this->zeroSizes = $zeroSizes;
        $this->corruptCRC = $corruptCRC;
        $this->corruptOffsets = $corruptOffsets;
        $this->addFakeExtra = $addFakeExtra;
        $this->addPadding = $addPadding;
        $this->addFakeEntries = $addFakeEntries;
        $this->randomCompressionMethod = $randomCompressionMethod;
        $this->addFakeLocalHeaders = $addFakeLocalHeaders;
        $this->enableFakeEncryption = $enableFakeEncryption;
        $this->enableEocdTampering = $enableEocdTampering;
        $this->enablePathTraversalEntries = $enablePathTraversalEntries;
        $this->enableUnknownCompression = $enableUnknownCompression;
        $this->enableAxmlTampering = $enableAxmlTampering;
        $this->fakeEntryCount = $fakeEntryCount;
    }

    public function protect(string $apkPath): void
    {
        if (! File::exists($apkPath)) {
            return;
        }

        $data = File::get($apkPath);

        $data = $this->corruptCentralDirectory($data);

        // ZIP comment 必须在追加虚假数据之前处理，
        // 否则 addZipComment 会截断 EOCD 之后的所有追加内容
        $data = $this->addZipComment($data);

        if ($this->addFakeExtra) {
            $data = $this->appendFakeExtraField($data);
        }

        if ($this->addPadding) {
            $data = $this->appendRandomPadding($data);
        }

        if ($this->addFakeEntries) {
            $data = $this->appendFakeCentralDirectoryEntry($data);
        }

        if ($this->addFakeLocalHeaders) {
            $data = $this->appendFakeLocalHeader($data);
        }

        if ($this->enableFakeEncryption) {
            $data = $this->addFakeEncryptionFlags($data);
        }

        if ($this->enablePathTraversalEntries) {
            $data = $this->appendPathTraversalEntries($data, $this->fakeEntryCount);
        }

        if ($this->enableUnknownCompression) {
            $data = $this->injectUnknownCompressionMethod($data);
        }

        if ($this->enableEocdTampering) {
            $data = $this->tamperEocd($data);
        }

        File::put($apkPath, $data);
    }

    public function applyFakeEncryption(string $apkPath): void
    {
        if (! File::exists($apkPath)) {
            return;
        }

        $data = File::get($apkPath);
        $data = $this->addFakeEncryptionFlags($data);
        File::put($apkPath, $data);
    }

    /**
     * 修改 APK 文件头部字节 — 严格对齐旧版 VB.NET DexEditor 行为。
     *
     * 旧版 DexEditor 直接将 APK 文件当作原始字节流操作，
     * 在固定偏移量写入特定值来干扰反编译工具：
     *   offset 0:  magic (8 bytes) → ZIP 签名 (PK\x03\x04 + 4 零字节)
     *   offset 32: file_size (4 bytes) → 0
     *   offset 36: header_size (4 bytes) → 9999
     *
     * 注意：这不是修改 ZIP 内的 DEX 文件，而是直接覆盖 APK 文件的前几十个字节。
     * 效果是破坏 ZIP Local File Header 中的部分字段，干扰解析工具。
     *
     * @return int 1 表示已修改，0 表示未修改
     */
    public function modifyDex(string $apkPath): int
    {
        if (! File::exists($apkPath)) {
            return 0;
        }

        $data = File::get($apkPath);

        if (strlen($data) < 40) {
            return 0;
        }

        // magic → ZIP 签名 (旧版 DexMagicType.ZIP)
        $zipMagic = "\x50\x4B\x03\x04\x00\x00\x00\x00";
        $data = substr_replace($data, $zipMagic, 0, 8);

        // file_size → 0 (offset 32, 4 bytes LE)
        $data = substr_replace($data, pack('V', 0), 32, 4);

        // header_size → 9999 (offset 36, 4 bytes LE)
        $data = substr_replace($data, pack('V', 9999), 36, 4);

        File::put($apkPath, $data);

        return 1;
    }

    // ========== ZIP Central Directory 操纵 ==========

    /**
     * 扫描 ZIP Central Directory，对目标文件条目进行破坏性修改。
     * 对应旧版 APKProtector.cs ProtectAPK() 核心逻辑。
     */
    private function corruptCentralDirectory(string $data): string
    {
        $dataLen = strlen($data);
        $offset = 0;

        while (($pos = strpos($data, self::CD_SIGNATURE, $offset)) !== false) {
            if ($pos + 46 > $dataLen) {
                break;
            }

            $filenameLen = unpack('v', substr($data, $pos + 28, 2))[1];

            if ($pos + 46 + $filenameLen > $dataLen) {
                break;
            }

            $filename = substr($data, $pos + 46, $filenameLen);

            if ($this->isTargetFile($filename)) {
                $data = $this->corruptCentralDirectoryEntry($data, $pos);
                $data = $this->corruptLocalHeader($data, $pos);
            }

            $extraLen = unpack('v', substr($data, $pos + 30, 2))[1];
            $commentLen = unpack('v', substr($data, $pos + 32, 2))[1];
            $offset = $pos + 46 + $filenameLen + $extraLen + $commentLen;
        }

        return $data;
    }

    private function isTargetFile(string $filename): bool
    {
        return isset(self::TARGET_CRCS[$filename]);
    }

    /**
     * 破坏 Central Directory 条目的关键字段。
     *
     * ZIP Central Directory Entry 布局:
     *   +8:  version needed (2 bytes)
     *   +16: compressed size (4 bytes)
     *   +20: uncompressed size (4 bytes)
     *   +42: local header offset (4 bytes)
     */
    private function corruptCentralDirectoryEntry(string $data, int $pos): string
    {
        // version needed → 65353 (0xFEA9)
        $data = substr_replace($data, pack('v', self::CORRUPTED_VERSION), $pos + 8, 2);

        if ($this->zeroSizes) {
            // compressed size → 0
            $data = substr_replace($data, pack('V', 0), $pos + 16, 4);
            // uncompressed size → 0
            $data = substr_replace($data, pack('V', 0), $pos + 20, 4);
        }

        if ($this->corruptOffsets) {
            // local header offset → 0xFFFFFFFF
            $data = substr_replace($data, pack('V', 0xFFFFFFFF), $pos + 42, 4);
        }

        return $data;
    }

    /**
     * 破坏对应的 Local File Header。
     *
     * 严格对齐旧版 VB.NET APKProtector.cs 的行为：
     * 无条件修改 version needed、compressed size、uncompressed size；
     * 根据构造函数标志修改 compression method 和 CRC。
     *
     * Local File Header 布局:
     *   +6:  version needed (2 bytes)
     *   +8:  compression method (2 bytes)
     *   +10: last mod time (2 bytes)
     *   +12: last mod date (2 bytes)
     *   +14: CRC-32 (4 bytes)
     *   +18: compressed size (4 bytes)
     *   +22: uncompressed size (4 bytes)
     */
    private function corruptLocalHeader(string $data, int $cdPos): string
    {
        $localOffset = unpack('V', substr($data, $cdPos + 42, 4))[1];

        // 如果 offset 已被破坏为 0xFFFFFFFF，无法定位
        if ($localOffset >= strlen($data) || $localOffset === 0xFFFFFFFF) {
            return $data;
        }

        // 验证 local header 签名
        if (substr($data, $localOffset, 4) !== self::LFH_SIGNATURE) {
            return $data;
        }

        // 旧版无条件操作：
        // compressed size → 0
        $data = substr_replace($data, pack('V', 0), $localOffset + 18, 4);
        // uncompressed size → 200000000
        $data = substr_replace($data, pack('V', 200000000), $localOffset + 22, 4);
        // version needed → 65353
        $data = substr_replace($data, pack('v', self::CORRUPTED_VERSION), $localOffset + 6, 2);

        // compression method → 随机值 20000-50000（旧版 _randomCompressionMethod 标志）
        if ($this->randomCompressionMethod) {
            $randomMethod = random_int(20000, 50000);
            $data = substr_replace($data, pack('v', $randomMethod), $localOffset + 8, 2);
        }

        // CRC → 0xFFFFFFFF（旧版 _corruptCRC 标志）
        if ($this->corruptCRC) {
            $data = substr_replace($data, pack('V', 0xFFFFFFFF), $localOffset + 14, 4);
        }

        // zeroSizes 对 LFH 的额外操作（旧版中 compressed size 已被无条件置零，此处冗余但保持一致）
        if ($this->zeroSizes) {
            $data = substr_replace($data, pack('V', 0), $localOffset + 18, 4);
        }

        return $data;
    }

    // ========== 虚假数据注入 ==========

    /** 追加 4 字节 0xFF 虚假 extra field */
    private function appendFakeExtraField(string $data): string
    {
        return $data . "\xFF\xFF\xFF\xFF";
    }

    /** 追加 1-5 KB 随机填充数据 */
    private function appendRandomPadding(string $data): string
    {
        $size = random_int(1024, 5120);

        return $data . random_bytes($size);
    }

    /**
     * 追加虚假 Central Directory 条目 (文件名 "BTfile.bin")。
     * 对应旧版 AddFakeCentralDirectoryEntries()。
     */
    private function appendFakeCentralDirectoryEntry(string $data): string
    {
        $fakeName = 'BTfile.bin';
        $nameBytes = strlen($fakeName);

        // 46 字节 Central Directory 头 + 文件名
        $entry = self::CD_SIGNATURE;
        $entry .= str_repeat("\x00", 24);                    // +4 to +27: 零填充
        $entry .= pack('v', $nameBytes);                     // +28: filename length
        $entry .= str_repeat("\x00", 16);                    // +30 to +45: 零填充
        $entry .= $fakeName;

        return $data . $entry;
    }

    /**
     * 追加虚假 Local Header (文件名 "AndroidManifest.xml")。
     * 对应旧版 AddFakeLocalHeaders()。
     */
    private function appendFakeLocalHeader(string $data): string
    {
        $fakeName = 'AndroidManifest.xml';

        $header = self::CD_SIGNATURE;
        $header .= str_repeat("\x00", 41);
        $header .= $fakeName;

        return $data . $header;
    }

    // ========== ZIP Comment ==========

    private function addZipComment(string $data): string
    {
        $comment = random_bytes(random_int(100, 300));
        $eocdPos = strrpos($data, self::EOCD_SIGNATURE);

        if ($eocdPos !== false && $eocdPos + 22 <= strlen($data)) {
            return substr($data, 0, $eocdPos + 20) . pack('v', strlen($comment)) . $comment;
        }

        return $data;
    }

    // ========== 增强保护：伪加密标志 ==========

    /**
     * 对目标文件设置伪加密标志 (general purpose bit flag 的 bit 0 + bit 6)。
     * Android 运行时忽略此标志，但分析工具会拒绝处理"加密"条目。
     */
    private function addFakeEncryptionFlags(string $data): string
    {
        $dataLen = strlen($data);

        // 修改 Local File Headers
        $offset = 0;
        while (($pos = strpos($data, self::LFH_SIGNATURE, $offset)) !== false) {
            if ($pos + 30 > $dataLen) {
                break;
            }

            $filenameLen = unpack('v', substr($data, $pos + 26, 2))[1];

            if ($pos + 30 + $filenameLen > $dataLen) {
                break;
            }

            $filename = substr($data, $pos + 30, $filenameLen);

            if (in_array($filename, self::TARGET_FILENAMES, true)) {
                $flags = unpack('v', substr($data, $pos + 6, 2))[1];
                $flags |= self::FAKE_ENCRYPTION_FLAG;
                $data = substr_replace($data, pack('v', $flags), $pos + 6, 2);
            }

            $extraLen = unpack('v', substr($data, $pos + 28, 2))[1];
            $compressedSize = unpack('V', substr($data, $pos + 18, 4))[1];
            $offset = $pos + 30 + $filenameLen + $extraLen + $compressedSize;

            if ($offset <= $pos) {
                $offset = $pos + 4;
            }
        }

        // 修改 Central Directory entries
        $offset = 0;
        while (($pos = strpos($data, self::CD_SIGNATURE, $offset)) !== false) {
            if ($pos + 46 > $dataLen) {
                break;
            }

            $filenameLen = unpack('v', substr($data, $pos + 28, 2))[1];

            if ($pos + 46 + $filenameLen > $dataLen) {
                break;
            }

            $filename = substr($data, $pos + 46, $filenameLen);

            if (in_array($filename, self::TARGET_FILENAMES, true)) {
                $flags = unpack('v', substr($data, $pos + 8, 2))[1];
                $flags |= self::FAKE_ENCRYPTION_FLAG;
                $data = substr_replace($data, pack('v', $flags), $pos + 8, 2);
            }

            $extraLen = unpack('v', substr($data, $pos + 30, 2))[1];
            $commentLen = unpack('v', substr($data, $pos + 32, 2))[1];
            $offset = $pos + 46 + $filenameLen + $extraLen + $commentLen;
        }

        return $data;
    }

    // ========== 增强保护：EOCD 篡改 ==========

    /**
     * 篡改 EOCD 的 disk number 字段，干扰 ZIP 解析器。
     * 不修改 CD offset/size，确保 Android 运行时正常工作。
     */
    private function tamperEocd(string $data): string
    {
        $eocdPos = strrpos($data, self::EOCD_SIGNATURE);

        if ($eocdPos === false || $eocdPos + 22 > strlen($data)) {
            return $data;
        }

        // disk number → 随机大值
        $data = substr_replace($data, pack('v', random_int(0xFFF0, 0xFFFE)), $eocdPos + 4, 2);
        // start disk number → 随机大值
        $data = substr_replace($data, pack('v', random_int(0xFFF0, 0xFFFD)), $eocdPos + 6, 2);

        return $data;
    }

    // ========== 增强保护：路径穿越假条目 ==========

    /**
     * 批量生成路径穿越假 Central Directory 条目。
     * 覆盖 6 种路径模式，混淆 ZIP 解析器对真实文件的定位。
     */
    private function appendPathTraversalEntries(string $data, int $count): string
    {
        $eocdPos = strrpos($data, self::EOCD_SIGNATURE);
        if ($eocdPos === false) {
            return $data;
        }

        $fakeEntries = '';
        $totalFakeSize = 0;

        for ($i = 0; $i < $count; $i++) {
            $filename = $this->generateTraversalFilename($i, $count);
            $entry = $this->buildFakeCdEntry($filename);
            $fakeEntries .= $entry;
            $totalFakeSize += strlen($entry);
        }

        // 在 EOCD 之前插入假条目
        $data = substr($data, 0, $eocdPos) . $fakeEntries . substr($data, $eocdPos);

        // 更新 EOCD: total entries count (+10) 和 CD size (+12)
        $newEocdPos = $eocdPos + $totalFakeSize;
        $currentEntries = unpack('v', substr($data, $newEocdPos + 10, 2))[1];
        $currentCdSize = unpack('V', substr($data, $newEocdPos + 12, 4))[1];

        $data = substr_replace($data, pack('v', $currentEntries + $count), $newEocdPos + 10, 2);
        $data = substr_replace($data, pack('V', $currentCdSize + $totalFakeSize), $newEocdPos + 12, 4);

        // 同步更新 entries on this disk (+8)
        $entriesOnDisk = unpack('v', substr($data, $newEocdPos + 8, 2))[1];
        $data = substr_replace($data, pack('v', $entriesOnDisk + $count), $newEocdPos + 8, 2);

        return $data;
    }

    private function generateTraversalFilename(int $index, int $total): string
    {
        $ratio = $index / max($total, 1);
        $variant = $index % 10;

        if ($ratio < 0.30) {
            // 模式 1: AndroidManifest.xml/..xml
            $dots = str_repeat('.', $variant + 2);

            return "AndroidManifest.xml/{$dots}xml";
        }

        if ($ratio < 0.50) {
            // 模式 2: /AndroidManifest.xml///.xml
            $slashes = str_repeat('/', $variant + 3);

            return "/AndroidManifest.xml{$slashes}.xml";
        }

        if ($ratio < 0.65) {
            // 模式 3: classes.dex/\.xml
            $backslashes = str_repeat('\\', $variant + 1);

            return "classes.dex/{$backslashes}.xml";
        }

        if ($ratio < 0.80) {
            // 模式 4: AndroidManifest.xml\\.xml
            $backslashes = str_repeat('\\', $variant + 2);

            return "AndroidManifest.xml{$backslashes}.xml";
        }

        if ($ratio < 0.90) {
            // 模式 5: /AndroidManifest.xml (绝对路径)
            $slashes = str_repeat('/', $variant + 1);

            return "{$slashes}AndroidManifest.xml";
        }

        // 模式 6: AndroidManifest.xml .xml (空格)
        $spaces = str_repeat(' ', $variant + 1);

        return "AndroidManifest.xml{$spaces}.xml";
    }

    private function buildFakeCdEntry(string $filename): string
    {
        $nameLen = strlen($filename);

        $entry = self::CD_SIGNATURE;                         // +0:  签名 (4 bytes)
        $entry .= pack('v', 0x0014);                         // +4:  version made by
        $entry .= pack('v', 0x0014);                         // +6:  version needed
        $entry .= pack('v', 0x0000);                         // +8:  general purpose bit flag
        $entry .= pack('v', 0x0008);                         // +10: compression method (deflate)
        $entry .= pack('v', random_int(0, 0xFFFF));          // +12: last mod time
        $entry .= pack('v', random_int(0, 0xFFFF));          // +14: last mod date
        $entry .= pack('V', random_int(0, 0xFFFFFFFF));      // +16: CRC-32
        $entry .= pack('V', 0);                               // +20: compressed size
        $entry .= pack('V', 0);                               // +24: uncompressed size
        $entry .= pack('v', $nameLen);                        // +28: filename length
        $entry .= pack('v', 0);                               // +30: extra field length
        $entry .= pack('v', 0);                               // +32: file comment length
        $entry .= pack('v', 0);                               // +34: disk number start
        $entry .= pack('v', 0);                               // +36: internal file attributes
        $entry .= pack('V', 0);                               // +38: external file attributes
        $entry .= pack('V', 0xFFFFFFFF);                      // +42: local header offset (invalid)
        $entry .= $filename;                                   // +46: filename

        return $entry;
    }

    // ========== 增强保护：未知压缩方法 ==========

    /**
     * 在 Central Directory 中注入非标准压缩方法。
     * 只改 CD 不改 LFH，Android 通过 LFH 解压所以不受影响。
     */
    private function injectUnknownCompressionMethod(string $data): string
    {
        $dataLen = strlen($data);
        $offset = 0;

        while (($pos = strpos($data, self::CD_SIGNATURE, $offset)) !== false) {
            if ($pos + 46 > $dataLen) {
                break;
            }

            $filenameLen = unpack('v', substr($data, $pos + 28, 2))[1];

            if ($pos + 46 + $filenameLen > $dataLen) {
                break;
            }

            $filename = substr($data, $pos + 46, $filenameLen);

            if (in_array($filename, self::TARGET_FILENAMES, true)) {
                // CD compression method (+10, 2 bytes) → 非标准值 37386 (0x921A)
                $data = substr_replace($data, pack('v', 37386), $pos + 10, 2);
            }

            $extraLen = unpack('v', substr($data, $pos + 30, 2))[1];
            $commentLen = unpack('v', substr($data, $pos + 32, 2))[1];
            $offset = $pos + 46 + $filenameLen + $extraLen + $commentLen;
        }

        return $data;
    }

}
