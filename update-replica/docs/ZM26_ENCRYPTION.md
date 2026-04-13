# ZM26 Encrypted Asset Decryption Specification

## Overview

The vendor APK (`update.apk`, package `com.storm.safe.rock`) encrypts sensitive configuration assets using a custom XOR-based scheme called **ZM26**. Encryption metadata is stored in `assets/zm26_meta.json`, and encrypted files use the `.bt` extension, mapped from logical JSON filenames via the metadata.

This document is the authoritative reference for correctly decrypting any `.bt` file.

---

## zm26_meta.json Format

The metadata file lives at `assets/zm26_meta.json` inside the APK:

```json
{
  "xor_key": "139570579df9041a84691b4aea5cee3f",
  "salt": "3ec8fbb7241e14f4",
  "mapping": {
    "server_config.json": "0.bt",
    "app_config.json": "1.bt",
    "locateValues.json": "2.bt",
    "monitor_config.json": "3.bt"
  }
}
```

| Field | Type | Description |
|-------|------|-------------|
| `xor_key` | hex string (32 chars) | XOR key — **must be hex-decoded** to 16 bytes |
| `salt` | hex string (16 chars) | Salt — also present as raw bytes in each `.bt` file header |
| `mapping` | object | Logical filename → encrypted `.bt` filename |

---

## Binary File Format (.bt files)

Each `.bt` file follows this fixed-header layout:

```
Offset  Size    Description
──────  ──────  ────────────────────────────────────────────────────
0       4       Magic bytes: "ZM26" (0x5A, 0x4D, 0x32, 0x36)
4       8       Salt (matches zm26_meta.json "salt" field, as raw bytes)
12      8       Reserved/padding (not used in decryption)
20      N       Encrypted payload (XOR'd content)
```

**Total header size: 20 bytes.** Payload begins at offset 20.

### Magic Bytes Verification

```
Offset 0: 0x5A  ('Z')
Offset 1: 0x4D  ('M')
Offset 2: 0x32  ('2')
Offset 3: 0x36  ('6')
```

---

## Decryption Algorithm

### Step-by-Step

1. **Read** the `.bt` file as raw bytes.

2. **Verify magic bytes** at offset 0..3:
   ```
   bytes[0] == 0x5A && bytes[1] == 0x4D && bytes[2] == 0x32 && bytes[3] == 0x36
   ```
   If mismatch → reject the file (not ZM26 format).

3. **Extract salt** from the file header:
   ```
   salt = bytes[4..12]   // 8 bytes
   ```

4. **Build the combined XOR key** (24 bytes total):
   - **Hex-decode** the `xor_key` string from `zm26_meta.json` into raw bytes:
     ```
     "139570579df9041a84691b4aea5cee3f" → [0x13, 0x95, 0x70, 0x57, 0x9D, 0xF9, 0x04, 0x1A,
                                            0x84, 0x69, 0x1B, 0x4A, 0xEA, 0x5C, 0xEE, 0x3F]
     ```
     This yields **16 bytes**.
   - **Concatenate** hex-decoded key + salt from file header:
     ```
     combined_key = hex_decoded_key (16 bytes) + salt (8 bytes) = 24 bytes
     ```

   > **CRITICAL:** The `xor_key` string MUST be hex-decoded, NOT UTF-8 encoded.
   > Hex-decoding `"139570579df9041a..."` produces 16 bytes.
   > UTF-8 encoding the same string produces 32 ASCII bytes — this is WRONG.

5. **Extract payload**:
   ```
   payload = bytes[20..end]
   ```

6. **XOR decrypt** byte-by-byte with cyclic key:
   ```
   for i in 0..payload.length:
       decrypted[i] = payload[i] XOR combined_key[i % 24]
   ```

7. **Result** is UTF-8 encoded JSON.

### Pseudocode

```
function decrypt_zm26(bt_file_bytes, xor_key_hex):
    // 1. Validate magic
    assert bt_file_bytes[0:4] == [0x5A, 0x4D, 0x32, 0x36]

    // 2. Extract salt from header
    salt = bt_file_bytes[4:12]          // 8 bytes

    // 3. Build combined key
    hex_key = hex_decode(xor_key_hex)   // 16 bytes
    combined = hex_key + salt           // 24 bytes

    // 4. XOR decrypt payload
    payload = bt_file_bytes[20:]
    decrypted = new byte[payload.length]
    for i in 0..payload.length:
        decrypted[i] = payload[i] ^ combined[i % 24]

    return utf8_decode(decrypted)
```

---

## Common Mistakes (Lessons from Debugging)

These are real mistakes we encountered and debugged during implementation:

| # | Mistake | Symptom | Fix |
|---|---------|---------|-----|
| 1 | **UTF-8 encoding the key string** instead of hex-decoding | `"key".toByteArray(UTF-8)` → 32 ASCII bytes → garbled output | Use `hexStringToByteArray()` → 16 bytes |
| 2 | **Using 12-byte nonce** (bytes[4:16]) instead of 8-byte salt (bytes[4:12]) | First 28 bytes decrypt correctly, then garbage | Salt is bytes[4:12], reserved bytes[12:20] are padding |
| 3 | **Missing the asset files** entirely | `FileNotFoundException` at runtime | Assets must be extracted from the APK into the expected path |
| 4 | **Forgetting cyclic key wrap** | Decryption works for first 24 bytes, fails after | Use `i % 24` for key indexing |

### The Critical Distinction

```
❌ WRONG:  "139570579df9041a84691b4aea5cee3f".toByteArray(Charsets.UTF_8)
           → 32 bytes: [0x31, 0x33, 0x39, 0x35, 0x37, 0x30, ...]  (ASCII codes)

✅ CORRECT: hexStringToByteArray("139570579df9041a84691b4aea5cee3f")
           → 16 bytes: [0x13, 0x95, 0x70, 0x57, 0x9D, 0xF9, ...]  (actual values)
```

---

## Kotlin Implementation Reference

### EncryptedConfigStore.kt (Key Builder)

```kotlin
/**
 * Hex-decode the xor_key from zm26_meta.json, then concatenate with
 * the 8-byte salt from the .bt file header to form the 24-byte XOR key.
 */
private fun buildCombinedKey(xorKeyHex: String, salt: ByteArray): ByteArray {
    val hexKey = hexStringToByteArray(xorKeyHex)  // 16 bytes
    return hexKey + salt                           // 24 bytes
}

private fun hexStringToByteArray(hex: String): ByteArray {
    val len = hex.length
    val data = ByteArray(len / 2)
    for (i in 0 until len step 2) {
        data[i / 2] = ((Character.digit(hex[i], 16) shl 4)
                      + Character.digit(hex[i + 1], 16)).toByte()
    }
    return data
}
```

### hkdrkgzsfs.kt (Decryption Core)

```kotlin
fun decryptBtFile(btBytes: ByteArray, xorKeyHex: String): String {
    // Verify magic bytes: "ZM26"
    require(btBytes.size >= 20) { "File too small for ZM26 format" }
    require(btBytes[0] == 0x5A.toByte() &&
            btBytes[1] == 0x4D.toByte() &&
            btBytes[2] == 0x32.toByte() &&
            btBytes[3] == 0x36.toByte()) { "Invalid ZM26 magic bytes" }

    // Extract 8-byte salt from header
    val salt = btBytes.sliceArray(4 until 12)

    // Build 24-byte combined key
    val combinedKey = buildCombinedKey(xorKeyHex, salt)

    // XOR decrypt payload (starting at offset 20)
    val payload = btBytes.sliceArray(20 until btBytes.size)
    val decrypted = ByteArray(payload.size) { i ->
        (payload[i].toInt() xor combinedKey[i % combinedKey.size].toInt()).toByte()
    }

    return String(decrypted, Charsets.UTF_8)
}
```

---

## File Inventory

### Known Config Assets (0.bt - 3.bt)

| Logical Name | Encrypted Name | Size (bytes) | Content Description |
|---|---|---|---|
| `server_config.json` | `0.bt` | 1,435 | Server URLs, WebSocket config |
| `app_config.json` | `1.bt` | 6,640 | App behavior settings |
| `locateValues.json` | `2.bt` | 33,396 | Multi-lang/brand UI text config (36 languages) |
| `monitor_config.json` | `3.bt` | 764 | Monitoring thresholds |

### Additional Encrypted Assets (4.bt - 10.bt)

| Encrypted Name | Size | Content Description |
|---|---|---|
| `4.bt` - `10.bt` | varies | Additional encrypted assets (brand logos, UI images, etc.) |

These files are not listed in `zm26_meta.json`'s `mapping` but follow the same ZM26 binary format and can be decrypted with the same algorithm.

---

## Source of Assets

The `.bt` files and `zm26_meta.json` are extracted from the **fixed** APK variant, not the original:

- **Source APK:** `jadx-reference/update-fixed.apk`
- **NOT:** `update.apk` (the original has zip-level password protection on `.bt` files, preventing direct extraction)

### Extraction Command

```bash
python3 -c "
import zipfile
z = zipfile.ZipFile('update-fixed.apk')
[open(f'assets/{n}', 'wb').write(z.read(f'assets/{n}'))
 for n in ['0.bt', '1.bt', '2.bt', '3.bt', 'zm26_meta.json']]
"
```

---

## Summary Cheat Sheet

```
File format:   [ZM26 magic (4B)] [salt (8B)] [reserved (8B)] [payload (NB)]
Key build:     hex_decode(xor_key) → 16B  +  salt → 8B  =  24B combined
Decrypt:       payload[i] XOR combined_key[i % 24]
Output:        UTF-8 JSON
```
