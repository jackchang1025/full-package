<?php

declare(strict_types=1);

namespace App\Services\ApkBuilder;

use RuntimeException;

final class Encryptor
{
    private readonly string $iv;
    private readonly string $password;
    private readonly string $salt;
    private readonly int $iterations;

    public function __construct(
        ?string $iv = null,
        ?string $password = null,
        ?string $salt = null,
        ?int $iterations = null,
    ) {
        $config = config('apk-builder.encryption', []);

        $this->iv = $iv ?? $config['iv'] ?? '2230209522049090';
        $this->password = $password ?? $config['password'] ?? '4814780584699673';
        $this->salt = $salt ?? $config['salt'] ?? '2894356330652558';
        $this->iterations = $iterations ?? $config['iterations'] ?? 65536;
    }

    public function encryptString(string $data): string
    {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, $this->iterations, 16, true);
        $encrypted = openssl_encrypt($data, 'aes-128-cbc', $key, OPENSSL_RAW_DATA, $this->iv);

        if ($encrypted === false) {
            throw new RuntimeException('Failed to encrypt string: ' . openssl_error_string());
        }

        return base64_encode($encrypted);
    }

    public function decryptString(string $encryptedBase64): string
    {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, $this->iterations, 16, true);
        $encrypted = base64_decode($encryptedBase64, true);

        if ($encrypted === false) {
            throw new RuntimeException('Invalid base64 encoded string');
        }

        $decrypted = openssl_decrypt($encrypted, 'aes-128-cbc', $key, OPENSSL_RAW_DATA, $this->iv);

        if ($decrypted === false) {
            throw new RuntimeException('Failed to decrypt string: ' . openssl_error_string());
        }

        return $decrypted;
    }

    /**
     * XOR encrypt bytes - matches Android cg0.z() decryption
     * Android side: result[i] = data[i] ^ keyBytes[i % keyBytes.length]
     */
    public function encryptBytes(string $data, string $password): string
    {
        $keyBytes = $password;
        $keyLen = strlen($keyBytes);
        $dataLen = strlen($data);
        $result = '';

        for ($i = 0; $i < $dataLen; $i++) {
            $result .= chr(ord($data[$i]) ^ ord($keyBytes[$i % $keyLen]));
        }

        return $result;
    }

    /**
     * XOR decrypt bytes - same as encrypt (XOR is symmetric)
     */
    public function decryptBytes(string $data, string $password): string
    {
        // XOR encryption/decryption is symmetric
        return $this->encryptBytes($data, $password);
    }

    public static function generateKey(int $length = 16): string
    {
        return bin2hex(random_bytes($length));
    }
}
