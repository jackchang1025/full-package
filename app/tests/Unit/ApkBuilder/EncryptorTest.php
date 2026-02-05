<?php

use App\Services\ApkBuilder\Encryptor;

describe('Encryptor encryptString and decryptString', function () {
    it('encryptString and decryptString roundtrip', function () {
        $encryptor = new Encryptor();
        $plain = 'hello world';

        $encrypted = $encryptor->encryptString($plain);
        $decrypted = $encryptor->decryptString($encrypted);

        expect($encrypted)->not->toBe($plain);
        expect($decrypted)->toBe($plain);
    });

    it('encryptString produces deterministic output with fixed IV', function () {
        $encryptor = new Encryptor();
        $plain = 'test';

        $e1 = $encryptor->encryptString($plain);
        $e2 = $encryptor->encryptString($plain);

        expect($e1)->toBe($e2);
        expect($encryptor->decryptString($e1))->toBe($plain);
    });
});

describe('Encryptor encryptBytes and decryptBytes', function () {
    it('encryptBytes and decryptBytes are symmetric', function () {
        $encryptor = new Encryptor();
        $data = 'binary content';
        $key = 'my_secret_key_16b';

        $encrypted = $encryptor->encryptBytes($data, $key);
        $decrypted = $encryptor->decryptBytes($encrypted, $key);

        expect($decrypted)->toBe($data);
    });

    it('XOR encryption is symmetric', function () {
        $encryptor = new Encryptor();
        $original = 'test data';

        $enc = $encryptor->encryptBytes($original, 'key');
        $dec = $encryptor->decryptBytes($enc, 'key');

        expect($dec)->toBe($original);
    });
});

describe('Encryptor generateKey', function () {
    it('generateKey returns correct length hex string', function () {
        $key = Encryptor::generateKey(16);

        expect(strlen($key))->toBe(32);
        expect(ctype_xdigit($key))->toBeTrue();
    });

    it('generateKey with custom length', function () {
        $key = Encryptor::generateKey(8);

        expect(strlen($key))->toBe(16);
    });
});

describe('Encryptor decryptString', function () {
    it('decryptString throws for invalid base64', function () {
        $encryptor = new Encryptor();

        expect(fn() => $encryptor->decryptString('not-valid-base64!!!'))->toThrow(RuntimeException::class);
    });
});
