<?php

use App\Services\ApkBuilder\ApkBuildConfig;

describe('ApkBuildConfig mutability', function () {
    it('allows modifying appId after construction', function () {
        $config = new ApkBuildConfig(
            appId: 'com.original.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );
        
        $config->appId = 'com.modified.app';
        
        expect($config->appId)->toBe('com.modified.app');
    });
    
    it('allows modifying appVersion after construction', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );
        
        $config->appVersion = '2.5.3';
        
        expect($config->appVersion)->toBe('2.5.3');
    });
    
    it('toArray reflects modified properties', function () {
        $config = new ApkBuildConfig(
            appId: 'com.test.app',
            userId: '1',
            appName: 'Test',
            appVersion: '1.0',
            websocketUrl: 'ws://localhost:8081'
        );
        
        $config->appId = 'com.new.app';
        $config->appVersion = '3.0.0';
        
        $array = $config->toArray();
        
        expect($array['app_id'])->toBe('com.new.app');
        expect($array['app_version'])->toBe('3.0.0');
    });
});
