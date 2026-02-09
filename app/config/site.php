<?php

return [

    /*
    |--------------------------------------------------------------------------
    | User Entry Path
    |--------------------------------------------------------------------------
    |
    | URL prefix for the user portal (login, dashboard, devices, builds, etc.).
    | Empty string means no prefix (e.g. /login, /dashboard).
    | Example: 'portal' => /portal/login, /portal/dashboard.
    |
    */

    'user_entry_path' => env('SITE_USER_ENTRY_PATH', ''),

    /*
    |--------------------------------------------------------------------------
    | Admin Entry Path
    |--------------------------------------------------------------------------
    |
    | URL prefix for the admin backend (admin login, dashboard, users, etc.).
    |
    */

    'admin_entry_path' => env('SITE_ADMIN_ENTRY_PATH', 'admin'),

    /*
    |--------------------------------------------------------------------------
    | Logo Upload Max Size (KB)
    |--------------------------------------------------------------------------
    |
    | Maximum file size for website logo upload, in kilobytes.
    | 10240 KB = 10 MB.
    |
    */

    'logo_max_size_kb' => (int) env('SITE_LOGO_MAX_SIZE_KB', 10240),

];
