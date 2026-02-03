<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Seeder;
use Spatie\Permission\Models\Permission;
use Spatie\Permission\Models\Role;

class RolePermissionSeeder extends Seeder
{
    public function run(): void
    {
        $guardName = 'web';

        // 重置缓存
        app()[\Spatie\Permission\PermissionRegistrar::class]->forgetCachedPermissions();

        // 创建权限
        $permissions = [
            'builds.view',
            'builds.create',
            'builds.delete',
            'devices.view',
            'devices.control',
            'devices.delete',
        ];

        foreach ($permissions as $name) {
            Permission::firstOrCreate(['name' => $name, 'guard_name' => $guardName]);
        }

        // 创建默认角色 client，并赋予基础权限
        $clientRole = Role::firstOrCreate(['name' => 'client', 'guard_name' => $guardName]);
        $clientRole->syncPermissions(Permission::where('guard_name', $guardName)->pluck('name'));

        // 为尚无角色的用户分配 client 角色
        User::whereDoesntHave('roles')->each(function (User $user) use ($clientRole): void {
            $user->assignRole($clientRole);
        });
    }
}
