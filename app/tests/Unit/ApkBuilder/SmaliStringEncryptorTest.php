<?php

use App\Services\ApkBuilder\SmaliStringEncryptor;
use Illuminate\Support\Facades\File;

describe('SmaliStringEncryptor', function () {
    beforeEach(function () {
        $this->buildDir = sys_get_temp_dir() . '/smali_enc_test_' . uniqid();
        mkdir($this->buildDir . '/smali/com/icontrol/protector', 0755, true);
    });

    afterEach(function () {
        if (is_dir($this->buildDir)) {
            exec('rm -rf ' . escapeshellarg($this->buildDir));
        }
    });

    it('replaces const-string with XOR decryption call', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string v0, "android.intent.action.BOOT_COMPLETED"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(1);

        $result = file_get_contents($filePath);
        expect($result)->not->toContain('const-string v0, "android.intent.action.BOOT_COMPLETED"');
        expect($result)->toContain('invoke-static');
        expect($result)->toContain('XorDecryptor');
        expect($result)->toContain('.array-data 1');
        expect($result)->toContain('move-result-object v0');
    });

    it('increases .locals count by 3', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string v0, "hello world"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $encryptor->encryptAllStrings();

        $result = file_get_contents($filePath);
        expect($result)->toContain('.locals 8');
    });

    it('skips methods with .locals >= 14', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 14
    const-string v0, "should not be encrypted"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(0);

        $result = file_get_contents($filePath);
        expect($result)->toContain('const-string v0, "should not be encrypted"');
    });

    it('skips excluded directories', function () {
        $excludedPath = $this->buildDir . '/smali/myobfuscated';
        mkdir($excludedPath, 0755, true);

        $smali = <<<'SMALI'
.class public Lmyobfuscated/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string v0, "should not be encrypted"
    return-void
.end method
SMALI;

        file_put_contents($excludedPath . '/TestClass.smali', $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(0);
    });

    it('skips empty and single-char strings', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string v0, ""
    const-string v1, "x"
    const-string v2, "valid string"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(1);

        $result = file_get_contents($filePath);
        expect($result)->toContain('const-string v0, ""');
        expect($result)->toContain('const-string v1, "x"');
        expect($result)->not->toContain('const-string v2, "valid string"');
    });

    it('handles const-string/jumbo', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string/jumbo v0, "jumbo string value"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(1);

        $result = file_get_contents($filePath);
        expect($result)->not->toContain('const-string/jumbo');
        expect($result)->toContain('invoke-static');
    });

    it('handles multiple const-strings in one method', function () {
        $smali = <<<'SMALI'
.class public Lcom/icontrol/protector/TestClass;
.super Ljava/lang/Object;

.method public test()V
    .locals 5
    const-string v0, "first string"
    const-string v1, "second string"
    const-string v2, "third string"
    return-void
.end method
SMALI;

        $filePath = $this->buildDir . '/smali/com/icontrol/protector/TestClass.smali';
        file_put_contents($filePath, $smali);

        $encryptor = new SmaliStringEncryptor($this->buildDir);
        $count = $encryptor->encryptAllStrings();

        expect($count)->toBe(3);

        $result = file_get_contents($filePath);
        expect(substr_count($result, 'invoke-static'))->toBe(3);
        expect(substr_count($result, '.array-data 1'))->toBe(6);
    });
});

describe('SmaliStringEncryptor XOR symmetry', function () {
    it('produces decryptable output matching Encryptor::encryptBytes', function () {
        $encryptor = new \App\Services\ApkBuilder\Encryptor();

        $plaintext = 'test string for XOR';
        $key = 'abcdefgh';

        $encrypted = $encryptor->encryptBytes($plaintext, $key);
        $decrypted = $encryptor->decryptBytes($encrypted, $key);

        expect($decrypted)->toBe($plaintext);
    });
});
