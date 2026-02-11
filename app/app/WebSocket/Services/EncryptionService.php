<?php

declare(strict_types=1);

namespace App\WebSocket\Services;

final class EncryptionService
{
    private string $key;

    private string $iv;

    private string $method;

    public function __construct()
    {
        $this->key = config('websocket.encryption.key');
        $this->iv = config('websocket.encryption.iv');
        $this->method = config('websocket.encryption.method', 'AES-256-CBC');
    }

    public function encrypt(string $data): string
    {
        $encrypted = openssl_encrypt($data, $this->method, $this->key, OPENSSL_RAW_DATA, $this->iv);

        if ($encrypted === false) {
            throw new \RuntimeException('Encryption failed');
        }

        return base64_encode($encrypted);
    }

    public function decrypt(string $data): string
    {
        $decoded = base64_decode($data, true);

        if ($decoded === false) {
            throw new \RuntimeException('Base64 decode failed');
        }

        $decrypted = openssl_decrypt($decoded, $this->method, $this->key, OPENSSL_RAW_DATA, $this->iv);

        if ($decrypted === false) {
            throw new \RuntimeException('Decryption failed');
        }

        return $decrypted;
    }

    public function encryptEmail(string $email): string
    {
        return $this->encrypt($email);
    }

    public function decryptEmail(string $encryptedEmail): string
    {
        return $this->decrypt($encryptedEmail);
    }

    public function isAdminEmail(string $encryptedEmail): bool
    {
        return $encryptedEmail === config('websocket.admin_email_encrypted');
    }
}
