<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Illuminate\Validation\Rule;
use Illuminate\Validation\Rules\Password;
use Inertia\Inertia;
use Inertia\Response;
use Spatie\Permission\Models\Permission;
use Spatie\Permission\Models\Role;

class UserController extends Controller
{
    public function index(Request $request): Response
    {
        $query = User::query()->with('roles');

        if ($request->filled('search')) {
            $search = $request->input('search');
            $query->where(function ($q) use ($search) {
                $q->where('username', 'like', "%{$search}%")
                    ->orWhere('email', 'like', "%{$search}%");
            });
        }

        $users = $query->orderByDesc('created_at')->paginate(20)->through(function (User $u) {
            return [
                'id' => $u->id,
                'username' => $u->username,
                'email' => $u->email,
                'subscription_expires_at' => $u->subscription_expires_at?->toDateString(),
                'subscription_type' => $u->subscription_type,
                'roles' => $u->getRoleNames()->values()->all(),
                'created_at' => $u->created_at?->toISOString(),
            ];
        });

        $roles = Role::where('guard_name', 'web')->pluck('name');
        $roleLabels = config('permission_labels.roles', []);

        return Inertia::render('Admin/Users/Index', [
            'users' => $users,
            'roles' => $roles,
            'roleLabels' => $roleLabels,
            'filters' => ['search' => $request->input('search', '')],
        ]);
    }

    public function edit(User $user): Response
    {
        $user->load('roles', 'permissions');
        $roles = Role::where('guard_name', 'web')->orderBy('name')->pluck('name')->all();
        $roleLabels = config('permission_labels.roles', []);
        $permissions = Permission::where('guard_name', 'web')->orderBy('name')->pluck('name')->all();
        $permissionLabels = config('permission_labels.permissions', []);

        $directPermissionNames = $user->getDirectPermissions()->pluck('name')->values()->all();

        return Inertia::render('Admin/Users/Edit', [
            'user' => [
                'id' => $user->id,
                'username' => $user->username,
                'email' => $user->email,
                'subscription_expires_at' => $user->subscription_expires_at?->toDateString(),
                'subscription_type' => $user->subscription_type,
                'roles' => $user->getRoleNames()->values()->all(),
                'direct_permissions' => $directPermissionNames,
                'contact' => $user->contact,
            ],
            'roles' => $roles,
            'roleLabels' => $roleLabels,
            'permissions' => $permissions,
            'permissionLabels' => $permissionLabels,
        ]);
    }

    public function update(Request $request, User $user)
    {
        $validated = $request->validate([
            'username' => ['required', 'string', 'max:50', Rule::unique('users', 'username')->ignore($user->id)],
            'email' => ['required', 'string', 'email', 'max:255', Rule::unique('users', 'email')->ignore($user->id)],
            'password' => ['nullable', 'string', Password::default(), 'confirmed'],
            'subscription_expires_at' => ['nullable', 'date'],
            'roles' => ['array'],
            'roles.*' => ['string', 'exists:roles,name'],
            'direct_permissions' => ['array'],
            'direct_permissions.*' => ['string', 'exists:permissions,name'],
        ]);

        $user->username = $validated['username'];
        $user->email = $validated['email'];
        if ($request->filled('password')) {
            $user->password = Hash::make($validated['password']);
        }
        if (array_key_exists('subscription_expires_at', $validated)) {
            $user->subscription_expires_at = $validated['subscription_expires_at'];
        }
        $user->save();

        if (array_key_exists('roles', $validated)) {
            $user->syncRoles($validated['roles']);
        }

        if (array_key_exists('direct_permissions', $validated)) {
            $user->syncPermissions($validated['direct_permissions']);
        }

        return redirect()->route('admin.users.index')->with('success', '用户已更新');
    }

    public function create(): Response
    {
        $roles = Role::where('guard_name', 'web')->orderBy('name')->pluck('name')->all();
        $roleLabels = config('permission_labels.roles', []);

        return Inertia::render('Admin/Users/Create', [
            'roles' => $roles,
            'roleLabels' => $roleLabels,
        ]);
    }

    public function store(Request $request)
    {
        $validated = $request->validate([
            'username' => ['required', 'string', 'max:50', 'unique:users,username'],
            'email' => ['required', 'string', 'email', 'max:255', 'unique:users,email'],
            'password' => ['required', 'string', Password::default(), 'confirmed'],
            'subscription_expires_at' => ['nullable', 'date'],
            'roles' => ['array'],
            'roles.*' => ['string', 'exists:roles,name'],
        ]);

        $user = User::create([
            'username' => $validated['username'],
            'email' => $validated['email'],
            'password' => Hash::make($validated['password']),
            'subscription_expires_at' => $validated['subscription_expires_at'] ?? now()->addDays(7)->toDateString(),
            'subscription_type' => 'trial',
        ]);

        if (!empty($validated['roles'])) {
            $user->syncRoles($validated['roles']);
        } else {
            $defaultRole = Role::where('name', config('permission_labels.default_role', 'client'))
                ->where('guard_name', 'web')
                ->first();
            if ($defaultRole) {
                $user->assignRole($defaultRole);
            }
        }

        return redirect()->route('admin.users.index')->with('success', '用户已创建');
    }

    public function destroy(Request $request, User $user)
    {
        $user->delete();

        if ($request->header('X-Inertia')) {
            return back();
        }

        return redirect()->route('admin.users.index')->with('success', '用户已删除');
    }
}
