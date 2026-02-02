<?php

declare(strict_types=1);

namespace Tests\Support;

class MockPanel extends WebSocketTestClient
{
    private string $userEmail;
    private ?string $subscribedDevice = null;

    public function __construct(string $encryptedEmail, array $options = [])
    {
        parent::__construct(
            $options['host'] ?? 'localhost',
            $options['port'] ?? 8081
        );

        $this->userEmail = $encryptedEmail;
    }

    public function getUserEmail(): string
    {
        return $this->userEmail;
    }

    public function checkPhone(int $page = 1, int $pageSize = 100, array $filters = []): bool
    {
        return $this->send([
            'subc' => 'checkphone',
            'email' => $this->userEmail,
            'page' => $page,
            'pageSize' => $pageSize,
            'filters' => $filters,
        ]);
    }

    public function joinDevice(string $deviceId): bool
    {
        $this->subscribedDevice = $deviceId;

        return $this->send([
            'itype' => 'slr_panel',
            'subc' => 'join',
            'pid' => $deviceId,
            'usercheck' => 'test-hash',
        ]);
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
            'usercheck' => 'test-hash',
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
            'usercheck' => 'test-hash',
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
            'usercheck' => 'test-hash',
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
}
