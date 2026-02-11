<?php

$langBase = dirname(__DIR__).'/lang/'.config('app.locale');

return [
    /*
    |--------------------------------------------------------------------------
    | 权限 / 角色显示名称（中文）
    |--------------------------------------------------------------------------
    | Spatie/laravel-permission 不支持通过注解获取权限名称，权限名仍为英文（如 builds.view）。
    | 此处从语言文件加载中文标签，便于前端展示。实际文案在 lang/zh/permissions.php 与 lang/zh/roles.php 中维护，
    | 无需在本文件中编写配置；新增权限/角色时在对应 lang 文件中补充即可。
    | 注：config 加载早于 Facade 就绪，故使用 require 直接加载语言文件，不使用 Lang::get()。
    */
    'permissions' => file_exists($langBase.'/permissions.php') ? require $langBase.'/permissions.php' : [],
    'roles' => file_exists($langBase.'/roles.php') ? require $langBase.'/roles.php' : [],

    /*
    |--------------------------------------------------------------------------
    | 默认角色名（新建用户未选角色时使用）
    |--------------------------------------------------------------------------
    | 仅当该角色在数据库中存在时才会分配，避免生产环境未执行 RolePermissionSeeder 时报错。
    */
    'default_role' => 'client',
];
