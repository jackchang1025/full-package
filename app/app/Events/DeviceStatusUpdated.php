<?php

namespace App\Events;

use App\Models\Device;
use Illuminate\Broadcasting\InteractsWithSockets;
use Illuminate\Broadcasting\PrivateChannel;
use Illuminate\Contracts\Broadcasting\ShouldBroadcast;
use Illuminate\Foundation\Events\Dispatchable;
use Illuminate\Queue\SerializesModels;

class DeviceStatusUpdated implements ShouldBroadcast
{
    use Dispatchable, InteractsWithSockets, SerializesModels;

    public function __construct(
        public Device $device,
        public bool $isOnline
    ) {}

    public function broadcastOn(): array
    {
        return [
            new PrivateChannel('user.' . $this->device->user_id),
        ];
    }

    public function broadcastWith(): array
    {
        return [
            'device_id' => $this->device->id,
            'uuid' => $this->device->uuid,
            'name' => $this->device->name,
            'is_online' => $this->isOnline,
            'last_seen_at' => $this->device->last_seen_at?->toISOString(),
        ];
    }
}
