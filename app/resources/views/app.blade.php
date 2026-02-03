<!DOCTYPE html>
<html lang="{{ str_replace('_', '-', app()->getLocale()) }}">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="csrf-token" content="{{ csrf_token() }}">
    <title inertia>{{ config('app.name') }}</title>
    @php
    $favicon = config('app.favicon', '/favicon.ico');
    $faviconUrl = str_starts_with($favicon, 'http') ? $favicon : asset($favicon);
    @endphp
    <link rel="icon" type="image/x-icon" href="{{ $faviconUrl }}">
    @vite(['resources/css/app.css', 'resources/ts/app.ts'])
    @inertiaHead
</head>

<body class="antialiased">
    @inertia
</body>

</html>