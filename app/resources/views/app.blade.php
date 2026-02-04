<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title inertia>{{ config('app.name') }}</title>
    <script>
        window.__APP_NAME__ = @json(config('app.name'));
    </script>
    @php
    $favicon = config('app.favicon', '/favicon.ico');
    $faviconUrl = str_starts_with($favicon, 'http') ? $favicon : asset($favicon);
    // Add cache-busting version based on file modification time or config change
    $faviconVersion = md5($favicon . config('app.name', ''));
    $faviconUrlWithVersion = $faviconUrl . (str_contains($faviconUrl, '?') ? '&' : '?') . 'v=' . substr($faviconVersion, 0, 8);
    // Detect favicon type from extension
    $faviconExt = strtolower(pathinfo(parse_url($favicon, PHP_URL_PATH) ?? '', PATHINFO_EXTENSION));
    $faviconType = match($faviconExt) {
    'png' => 'image/png',
    'svg' => 'image/svg+xml',
    'gif' => 'image/gif',
    'jpg', 'jpeg' => 'image/jpeg',
    'webp' => 'image/webp',
    default => 'image/x-icon',
    };
    @endphp
    <link rel="icon" type="{{ $faviconType }}" href="{{ $faviconUrlWithVersion }}">
    @vite(['resources/css/app.css', 'resources/ts/app.ts'])
    @inertiaHead
</head>

<body class="antialiased">
    @inertia
</body>

</html>