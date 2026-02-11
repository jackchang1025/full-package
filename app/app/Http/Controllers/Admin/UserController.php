<?php

namespace App\Http\Controllers\Admin;

use App\Http\Controllers\Controller;
use App\Http\Requests\Admin\StoreUserRequest;
use App\Http\Requests\Admin\UpdateUserRequest;
use App\Models\User;
use Illuminate\Http\RedirectResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Inertia\Inertia;
use Inertia\Response;
use Spatie\Permission\Models\Permission;
use Spatie\Permission\Models\Role;

class UserController extends Controller
{
    private const PER_PAGE = 20;

    /**
     * 用户列表（树形结构）+ 可选内联编辑数据。
     *
     * ?search=xxx   按用户名/邮箱搜索（含子账号匹配）
     * ?selected=id  同时返回该用户的完整编辑数据（若该用户/其父主账号不在当前页，会重定向到所在页）
     * ?expanded=id1,id2  树展开节点
     */
    public function index(Request $request): Response|RedirectResponse
    {
        $selectedId = $request->integer('selected', 0) ?: null;
        $currentPage = max(1, $request->integer('page', 1));

        if ($selectedId) {
            $redirectPage = $this->resolvePageForSelectedUser($request->input('search'), $selectedId);
            if ($redirectPage !== null && $redirectPage !== $currentPage) {
                $params = array_filter([
                    'page' => $redirectPage,
                    'selected' => $selectedId,
                    'search' => $request->input('search'),
                    'expanded' => $request->input('expanded'),
                ]);

                return redirect()->route('admin.users.index', $params);
            }
        }

        $users = $this->indexUserQuery($request->input('search'))
            ->paginate(self::PER_PAGE)
            ->through(fn (User $u) => $this->transformUserForTree($u));

        $selectedUser = $selectedId ? $this->formatUserForEdit(User::find($selectedId)) : null;

        $expanded = $this->parseExpandedIds($request->input('expanded'));

        return Inertia::render('Admin/Users/Index', [
            'users' => $users,
            'selectedUser' => $selectedUser,
            'roles' => Role::where('guard_name', 'web')->orderBy('name')->pluck('name')->all(),
            'roleLabels' => config('permission_labels.roles', []),
            'permissions' => Permission::where('guard_name', 'web')->orderBy('name')->pluck('name')->all(),
            'permissionLabels' => config('permission_labels.permissions', []),
            'filters' => [
                'search' => $request->input('search', ''),
                'selected' => $selectedId,
                'expanded' => $expanded,
            ],
        ]);
    }

    public function update(UpdateUserRequest $request, User $user): RedirectResponse
    {
        $validated = $request->validated();

        $user->fill([
            'username' => $validated['username'],
            'email' => $validated['email'],
        ]);
        if ($request->filled('password')) {
            $user->password = Hash::make($validated['password']);
        }

        if ($user->isSubAccount()) {
            $user->subscription_expires_at = $user->parent?->subscription_expires_at;
        } else {
            $user->fill([
                'subscription_expires_at' => $validated['subscription_expires_at'] ?? $user->subscription_expires_at,
                'max_sub_accounts' => $validated['max_sub_accounts'] ?? $user->max_sub_accounts,
            ]);
        }
        $user->save();

        $this->syncUserRolesAndPermissions($user, $validated);

        return $this->redirectToIndex(selected: $user->id, expanded: $request->input('expanded'))
            ->with('success', '用户已更新');
    }

    public function store(StoreUserRequest $request): RedirectResponse
    {
        $validated = $request->validated();
        $parent = isset($validated['parent_id']) ? User::find($validated['parent_id']) : null;
        $isSubAccount = $parent !== null;

        if ($isSubAccount && $parent->subAccounts()->count() >= $parent->max_sub_accounts) {
            return back()->withErrors([
                'parent_id' => '该父账号子账号配额已满（上限 '.$parent->max_sub_accounts.' 个）',
            ])->withInput();
        }

        $user = User::create($this->buildUserDataForStore($validated, $parent));

        if ($isSubAccount) {
            $user->syncRoles($parent->getRoleNames()->values()->all());
            $this->syncSubAccountPermissions($user, $validated['direct_permissions'] ?? [], $parent);
        } else {
            $this->assignDefaultOrRequestedRoles($user, $validated['roles'] ?? []);
            if (! empty($validated['direct_permissions'])) {
                $user->syncPermissions($validated['direct_permissions']);
            }
        }

        $expanded = $this->mergeExpandedForRedirect($request->input('expanded'), $validated['parent_id'] ?? null);

        return $this->redirectToIndex(selected: $user->id, expanded: $expanded)
            ->with('success', '用户已创建');
    }

    public function destroy(Request $request, User $user): RedirectResponse
    {
        // 级联删除子账号（触发应用层清理：权限、软删除等）
        $user->subAccounts()->each(fn (User $sub) => $sub->forceDelete());

        $user->forceDelete();

        return $this->redirectToIndex(expanded: $request->input('expanded'))
            ->with('success', '用户已删除');
    }

    /**
     * 树节点用：主账号 + 子账号结构，主账号带可分配给子账号的权限列表（不含 teams.manage）。
     */
    private function transformUserForTree(User $user): array
    {
        return [
            'id' => $user->id,
            'username' => $user->username,
            'email' => $user->email,
            'subscription_expires_at' => $user->subscription_expires_at?->toDateString(),
            'subscription_type' => $user->subscription_type,
            'roles' => $user->getRoleNames()->values()->all(),
            'max_sub_accounts' => $user->max_sub_accounts,
            'sub_accounts_count' => $user->sub_accounts_count,
            'all_permissions' => $user->getPermissionsAssignableToSubAccounts(),
            'children' => $user->subAccounts->map(fn (User $sub) => [
                'id' => $sub->id,
                'username' => $sub->username,
                'email' => $sub->email,
                'subscription_expires_at' => $sub->subscription_expires_at?->toDateString(),
                'subscription_type' => $sub->subscription_type,
                'roles' => $sub->getRoleNames()->values()->all(),
                'parent_id' => $sub->parent_id,
            ])->values()->all(),
        ];
    }

    /**
     * 前端编辑面板用：单用户完整数据；子账号时带父账号到期时间与可分配权限（不含 teams.manage）。
     */
    private function formatUserForEdit(?User $user): ?array
    {
        if ($user === null) {
            return null;
        }

        $user->load('roles', 'permissions');

        $data = [
            'id' => $user->id,
            'username' => $user->username,
            'email' => $user->email,
            'subscription_expires_at' => $user->subscription_expires_at?->toDateString(),
            'subscription_type' => $user->subscription_type,
            'roles' => $user->getRoleNames()->values()->all(),
            'direct_permissions' => $user->getDirectPermissions()->pluck('name')->values()->all(),
            'contact' => $user->contact,
            'max_sub_accounts' => $user->max_sub_accounts,
            'is_sub_account' => $user->isSubAccount(),
            'parent_username' => $user->parent?->username,
            'sub_accounts_count' => $user->subAccounts()->count(),
        ];

        if ($user->isSubAccount() && $user->parent) {
            $data['parent_subscription_expires_at'] = $user->parent->subscription_expires_at?->toDateString();
            $data['parent_permissions'] = $user->parent->getPermissionsAssignableToSubAccounts();
        }

        return $data;
    }

    private function buildUserDataForStore(array $validated, ?User $parent): array
    {
        $data = [
            'username' => $validated['username'],
            'email' => $validated['email'],
            'password' => Hash::make($validated['password']),
            'subscription_type' => 'trial',
        ];

        if ($parent) {
            $data['parent_id'] = $parent->id;
            $data['subscription_expires_at'] = $parent->subscription_expires_at;
        } else {
            $data['subscription_expires_at'] = $validated['subscription_expires_at'] ?? now()->addDays(7)->toDateString();
            if (array_key_exists('max_sub_accounts', $validated)) {
                $data['max_sub_accounts'] = $validated['max_sub_accounts'];
            }
        }

        return $data;
    }

    private function syncUserRolesAndPermissions(User $user, array $validated): void
    {
        if ($user->isSubAccount()) {
            if (array_key_exists('direct_permissions', $validated) && $user->parent) {
                $allowed = $user->parent->getPermissionsAssignableToSubAccounts();
                $chosen = array_intersect($validated['direct_permissions'] ?? [], $allowed);
                $user->syncPermissions($chosen);
            }
        } else {
            if (array_key_exists('roles', $validated)) {
                $user->syncRoles($validated['roles']);
            }
            if (array_key_exists('direct_permissions', $validated)) {
                $user->syncPermissions($validated['direct_permissions']);
            }
        }
    }

    /** 子账号权限仅允许父账号可分配列表（已排除 teams.manage）。 */
    private function syncSubAccountPermissions(User $subUser, array $requestedPermissions, User $parent): void
    {
        $allowed = $parent->getPermissionsAssignableToSubAccounts();
        $chosen = array_intersect($requestedPermissions, $allowed);
        $subUser->syncPermissions($chosen);
    }

    private function assignDefaultOrRequestedRoles(User $user, array $requestedRoles): void
    {
        if ($requestedRoles !== []) {
            $user->syncRoles($requestedRoles);

            return;
        }

        $defaultRole = Role::where('name', config('permission_labels.default_role', 'client'))
            ->where('guard_name', 'web')
            ->first();
        if ($defaultRole) {
            $user->assignRole($defaultRole);
        }
    }

    /**
     * 列表与分页使用的统一查询（排序加 id 保证与 resolvePageForSelectedUser 一致）。
     */
    private function indexUserQuery(?string $search): \Illuminate\Database\Eloquent\Builder
    {
        return User::query()
            ->parentOnly()
            ->search($search)
            ->with(['roles', 'subAccounts' => fn ($q) => $q->with('roles')->orderBy('created_at')])
            ->withCount('subAccounts')
            ->orderByDesc('created_at')
            ->orderByDesc('id');
    }

    /**
     * 计算选中用户所在页码（选中子账号时为其父主账号所在页），与 indexUserQuery 排序一致。
     * 返回 null 表示无需重定向（用户不存在或即当前页）。
     */
    private function resolvePageForSelectedUser(?string $search, int $selectedId): ?int
    {
        $selected = User::find($selectedId);
        if (! $selected) {
            return null;
        }

        $main = $selected->parent_id ? $selected->parent : $selected;
        if (! $main || $main->parent_id !== null) {
            return null;
        }

        $query = User::query()
            ->parentOnly()
            ->search($search)
            ->orderByDesc('created_at')
            ->orderByDesc('id');

        $beforeCount = (clone $query)
            ->where(function ($q) use ($main) {
                $q->where('created_at', '>', $main->created_at)
                    ->orWhere(function ($q2) use ($main) {
                        $q2->where('created_at', $main->created_at)->where('id', '>', $main->id);
                    });
            })
            ->count();

        $position = $beforeCount + 1;

        return (int) ceil($position / self::PER_PAGE);
    }

    /** 解析 URL 中的 expanded=id1,id2 为整数数组。 */
    private function parseExpandedIds(mixed $expanded): array
    {
        if (! is_string($expanded) || $expanded === '') {
            return [];
        }

        return collect(explode(',', $expanded))
            ->map(fn (string $id) => (int) trim($id))
            ->filter(fn (int $id) => $id > 0)
            ->values()
            ->all();
    }

    private function mergeExpandedForRedirect(?string $currentExpanded, ?int $parentId): ?string
    {
        $ids = $this->parseExpandedIds($currentExpanded);
        if ($parentId !== null) {
            $ids[] = $parentId;
        }
        $ids = array_unique($ids);

        return $ids === [] ? null : implode(',', $ids);
    }

    private function redirectToIndex(?int $selected = null, mixed $expanded = null): RedirectResponse
    {
        $params = array_filter([
            'selected' => $selected,
            'expanded' => $expanded,
        ]);

        return redirect()->route('admin.users.index', $params);
    }
}
