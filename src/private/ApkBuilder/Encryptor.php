<?php

namespace ApkBuilder;

/**
 * 加密处理器
 */
class Encryptor
{
    private string $iv = '2230209522049090';
    private string $password = '4814780584699673';
    private string $salt = '2894356330652558';
    private int $iterations = 65536;

    /**
     * 使用 AES-128-CBC 加密字符串
     * 
     * @param string $data 要加密的数据
     * @return string Base64 编码的加密数据
     */
    public function encryptString(string $data): string
    {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, $this->iterations, 16, true);
        $encrypted = openssl_encrypt($data, 'aes-128-cbc', $key, OPENSSL_RAW_DATA, $this->iv);
        return base64_encode($encrypted);
    }

    /**
     * 解密字符串
     * 
     * @param string $encryptedBase64 Base64 编码的加密数据
     * @return string 解密后的数据
     */
    public function decryptString(string $encryptedBase64): string
    {
        $key = hash_pbkdf2('sha1', $this->password, $this->salt, $this->iterations, 16, true);
        $encrypted = base64_decode($encryptedBase64);
        return openssl_decrypt($encrypted, 'aes-128-cbc', $key, OPENSSL_RAW_DATA, $this->iv);
    }

    /**
     * 使用 AES-256-CBC 加密字节数据
     * 
     * @param string $data 要加密的数据
     * @param string $password 加密密钥
     * @return string 加密后的数据
     */
    public function encryptBytes(string $data, string $password): string
    {
        $key = hash('sha256', $password, true);
        $iv = substr(md5($password), 0, 16);
        return openssl_encrypt($data, 'aes-256-cbc', $key, OPENSSL_RAW_DATA, $iv);
    }

    /**
     * 解密字节数据
     * 
     * @param string $data 加密的数据
     * @param string $password 解密密钥
     * @return string 解密后的数据
     */
    public function decryptBytes(string $data, string $password): string
    {
        $key = hash('sha256', $password, true);
        $iv = substr(md5($password), 0, 16);
        return openssl_decrypt($data, 'aes-256-cbc', $key, OPENSSL_RAW_DATA, $iv);
    }

    /**
     * 生成随机密钥
     */
    public static function generateKey(int $length = 16): string
    {
        return bin2hex(random_bytes($length));
    }
}
