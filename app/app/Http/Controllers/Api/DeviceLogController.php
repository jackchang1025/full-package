<?php

declare(strict_types=1);

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\Device\QueryDeviceLogRequest;
use App\Http\Requests\Device\StoreDeviceLogRequest;
use App\Models\Device;
use App\Models\DeviceLog;
use Carbon\Carbon;
use Illuminate\Http\JsonResponse;

class DeviceLogController extends Controller
{
    public function store(StoreDeviceLogRequest $request): JsonResponse
    {
        $validated = $request->validated();

        $deviceId = $request->input('_device_id') ?: $validated['deviceId'];

        $device = Device::where('device_uid', $deviceId)
            ->orWhere('uuid', $deviceId)
            ->first();

        if (! $device) {
            return $this->error('Device not found', 404);
        }

        $logs = $validated['logs'];
        $rows = [];
        $now = now();

        foreach ($logs as $log) {
            $rows[] = [
                'device_id' => $device->id,
                'user_id' => $device->user_id,
                'log_type' => $log['logType'],
                'content' => $log['content'],
                'device_timestamp' => Carbon::createFromTimestampMs($log['timestamp'])->timezone(config('app.timezone')),
                'device_uid' => $deviceId,
                'created_at' => $now,
                'updated_at' => $now,
            ];
        }

        DeviceLog::insert($rows);

        return $this->success(['inserted' => count($rows)]);
    }

    public function index(QueryDeviceLogRequest $request): JsonResponse
    {
        $validated = $request->validated();
        $perPage = $validated['per_page'] ?? 50;

        $user = $request->user();
        $ownerId = $user->getResourceOwnerId();

        $query = DeviceLog::where('user_id', $ownerId);

        if (! empty($validated['device_id'])) {
            $query->where('device_id', $validated['device_id']);
        }
        if (! empty($validated['device_uid'])) {
            $query->where('device_uid', $validated['device_uid']);
        }
        if (! empty($validated['log_type'])) {
            $query->where('log_type', $validated['log_type']);
        }
        if (! empty($validated['start_time'])) {
            $query->where('device_timestamp', '>=', $validated['start_time']);
        }
        if (! empty($validated['end_time'])) {
            $query->where('device_timestamp', '<=', $validated['end_time']);
        }

        $logs = $query->orderByDesc('device_timestamp')
            ->paginate($perPage);

        return $this->success($logs);
    }

    private function success(mixed $data = null): JsonResponse
    {
        return response()->json([
            'success' => true,
            'code' => 200,
            'msg' => 'OK',
            'data' => $data,
        ]);
    }

    private function error(string $msg, int $code): JsonResponse
    {
        return response()->json([
            'success' => false,
            'code' => $code,
            'msg' => $msg,
            'data' => null,
        ], $code);
    }
}
