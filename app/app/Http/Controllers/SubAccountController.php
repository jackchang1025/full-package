<?php

namespace App\Http\Controllers;

use App\Exceptions\SubAccountException;
use App\Http\Requests\SubAccount\StoreSubAccountRequest;
use App\Http\Requests\SubAccount\UpdateSubAccountRequest;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;
use Inertia\Inertia;
use Inertia\Response;

/**
 * 子账号管理控制器。
 *
 * 权限检查由路由中间件 permission:teams.manage 统一完成，
 * 控制器内只处理业务规则校验（嵌套禁止、配额、归属）。
 */
class SubAccountController extends Controller
{
    public function index(Request $request): Response
    {
        $user = $this->ensureMainAccount($request);

        $subAccounts = $user->subAccounts()
            ->with('permissions')
            ->orderByDesc('created_at')
            ->paginate(20)
            ->through(fn (User $sub) => [
                'id' => $sub->id,
                'username' => $sub->username,
                'email' => $sub->email,
                'permissions' => $sub->getDirectPermissions()->pluck('name')->values()->all(),
                'created_at' => $sub->created_at?->toISOString(),
            ]);

        return Inertia::render('SubAccounts/Index', [
            'subAccounts' => $subAccounts,
            'maxSubAccounts' => $user->max_sub_accounts,
            'canCreate' => $user->canCreateSubAccount(),
        ]);
    }

    public function create(Request $request): Response
    {
        $user = $this->ensureMainAccount($request);
        $this->ensureQuotaAvailable($user);

        return Inertia::render('SubAccounts/Create', [
            'availablePermissions' => $user->getPermissionsAssignableToSubAccounts(),
            'permissionLabels' => config('permission_labels.permissions', []),
        ]);
    }

    public function store(StoreSubAccountRequest $request)
    {
        $user = $this->ensureMainAccount($request);
        $this->ensureQuotaAvailable($user);

        $validated = $request->validated();

        $subAccount = User::create([
            'username' => $validated['username'],
            'email' => $validated['email'],
            'password' => Hash::make($validated['password']),
            'parent_id' => $user->id,
            'subscription_type' => $user->subscription_type,
            'subscription_expires_at' => $user->subscription_expires_at,
        ]);

        if (! empty($validated['permissions'])) {
            $allowed = array_intersect(
                $validated['permissions'],
                $user->getPermissionsAssignableToSubAccounts()
            );
            $subAccount->syncPermissions($allowed);
        }

        return redirect()->route('sub-accounts.index')
            ->with('success', '子账号已创建');
    }

    public function edit(Request $request, User $sub_account): Response
    {
        $user = $this->ensureMainAccount($request);
        $this->ensureOwnership($user, $sub_account);

        return Inertia::render('SubAccounts/Edit', [
            'subAccount' => [
                'id' => $sub_account->id,
                'username' => $sub_account->username,
                'email' => $sub_account->email,
                'permissions' => $sub_account->getDirectPermissions()->pluck('name')->values()->all(),
            ],
            'availablePermissions' => $user->getPermissionsAssignableToSubAccounts(),
            'permissionLabels' => config('permission_labels.permissions', []),
        ]);
    }

    public function update(UpdateSubAccountRequest $request, User $sub_account)
    {
        $user = $this->ensureMainAccount($request);
        $this->ensureOwnership($user, $sub_account);

        $validated = $request->validated();

        $sub_account->username = $validated['username'];
        $sub_account->email = $validated['email'];

        if ($request->filled('password')) {
            $sub_account->password = Hash::make($validated['password']);
        }

        $sub_account->save();

        if (array_key_exists('permissions', $validated)) {
            $allowed = array_intersect(
                $validated['permissions'],
                $user->getPermissionsAssignableToSubAccounts()
            );
            $sub_account->syncPermissions($allowed);
        }

        return redirect()->route('sub-accounts.index')
            ->with('success', '子账号已更新');
    }

    public function destroy(Request $request, User $sub_account)
    {
        $user = $this->ensureMainAccount($request);
        $this->ensureOwnership($user, $sub_account);

        $sub_account->forceDelete();

        return redirect()->route('sub-accounts.index')
            ->with('success', '子账号已删除');
    }

    // ── 业务规则守卫 ────────────────────────────────

    /**
     * 确保当前用户是主账号（子账号禁止嵌套管理）。
     *
     * @throws SubAccountException
     */
    private function ensureMainAccount(Request $request): User
    {
        $user = $request->user();

        if ($user->isSubAccount()) {
            throw SubAccountException::nested();
        }

        return $user;
    }

    /**
     * 确保主账号子账号配额未满。
     *
     * @throws SubAccountException
     */
    private function ensureQuotaAvailable(User $user): void
    {
        if (! $user->canCreateSubAccount()) {
            throw SubAccountException::quotaExceeded();
        }
    }

    /**
     * 确保目标子账号归属于当前主账号。
     *
     * @throws SubAccountException
     */
    private function ensureOwnership(User $parent, User $subAccount): void
    {
        if ($subAccount->parent_id !== $parent->id) {
            throw SubAccountException::notOwned();
        }
    }
}
