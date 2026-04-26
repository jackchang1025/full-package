<?php

return [

    // update-replica 源码路径
    'android_source_path' => env('GRADLE_ANDROID_SOURCE_PATH',
        env('LARAVEL_SAIL') ? '/var/www/update-replica' : dirname(base_path()).'/update-replica'
    ),

    'output_path' => storage_path('app/public/apk/gradle'),

    'temp_path' => env('GRADLE_APK_TEMP_PATH', ''),

    'java_home' => env('JAVA_HOME', '/usr/lib/jvm/java-17-openjdk-amd64'),

    'android_home' => env('ANDROID_HOME', '/opt/android-sdk'),

    // Kotlin 构建较慢，默认 600s
    'timeout' => (int) env('GRADLE_APK_BUILD_TIMEOUT', 600),

    'cleanup_on_success' => true,

    'cleanup_on_failure' => true,

];
