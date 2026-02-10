<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\StoreRoleRequest;
use App\Http\Requests\Admin\UpdateRoleRequest;
use Inertia\Inertia;
use Inertia\Response;
use Spatie\Permission\Models\Permission;
use Spatie\Permission\Models\Role;
use Spatie\Permission\PermissionRegistrar;

class RoleController extends Controller
{
    private const GUARD = 'web';

    public function index(): Response
    {
        $roles = Role::where('guard_name', self::GUARD)
            ->with('permissions')
            ->orderBy('name')
            ->paginate(20)
            ->through(function (Role $role) {
                return [
                    'id' => $role->id,
                    'name' => $role->name,
                    'permissions' => $role->getPermissionNames()->values()->all(),
                    'users_count' => $role->users()->count(),
                ];
            });

        $allPermissions = Permission::where('guard_name', self::GUARD)->orderBy('name')->pluck('name')->all();
        $permissionLabels = config('permission_labels.permissions', []);

        return Inertia::render('Admin/Roles/Index', [
            'roles' => $roles,
            'allPermissions' => $allPermissions,
            'permissionLabels' => $permissionLabels,
        ]);
    }

    public function create(): Response
    {
        $permissions = Permission::where('guard_name', self::GUARD)->orderBy('name')->pluck('name')->all();
        $permissionLabels = config('permission_labels.permissions', []);

        return Inertia::render('Admin/Roles/Create', [
            'permissions' => $permissions,
            'permissionLabels' => $permissionLabels,
        ]);
    }

    public function store(StoreRoleRequest $request)
    {
        $validated = $request->validated();

        $role = Role::create([
            'name' => $validated['name'],
            'guard_name' => self::GUARD,
        ]);

        $role->syncPermissions($validated['permissions'] ?? []);
        app(PermissionRegistrar::class)->forgetCachedPermissions();

        return redirect()->route('admin.roles.index')->with('success', '角色已创建');
    }

    public function edit(Role $role): Response
    {
        $role->load('permissions');

        $roleData = [
            'id' => $role->id,
            'name' => $role->name,
            'permissions' => $role->getPermissionNames()->values()->all(),
            'users_count' => $role->users()->count(),
        ];

        $permissions = Permission::where('guard_name', self::GUARD)->orderBy('name')->pluck('name')->all();
        $permissionLabels = config('permission_labels.permissions', []);

        return Inertia::render('Admin/Roles/Edit', [
            'role' => $roleData,
            'permissions' => $permissions,
            'permissionLabels' => $permissionLabels,
        ]);
    }

    public function update(UpdateRoleRequest $request, Role $role)
    {
        $validated = $request->validated();

        if (array_key_exists('name', $validated)) {
            $role->name = $validated['name'];
            $role->save();
        }

        if (array_key_exists('permissions', $validated)) {
            $role->syncPermissions($validated['permissions']);
        }

        app(PermissionRegistrar::class)->forgetCachedPermissions();

        return redirect()->route('admin.roles.index')->with('success', '角色已更新');
    }

    public function destroy(Role $role)
    {
        $usersCount = $role->users()->count();
        if ($usersCount > 0) {
            return back()->withErrors(['role' => '该角色下仍有 ' . $usersCount . ' 名用户，无法删除。']);
        }

        $role->delete();
        app(PermissionRegistrar::class)->forgetCachedPermissions();

        return redirect()->route('admin.roles.index')->with('success', '角色已删除');
    }
}
