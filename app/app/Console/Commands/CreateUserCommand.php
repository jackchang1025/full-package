<?php

namespace App\Console\Commands;

use App\Models\User;
use Illuminate\Console\Command;
use Illuminate\Support\Facades\Validator;
use Spatie\Permission\Models\Role;

class CreateUserCommand extends Command
{
    protected $signature = 'create:user
                            {--username= : 用户名}
                            {--email= : 邮箱地址}
                            {--password= : 密码}
                            {--role=client : 角色 (Spatie RBAC 角色名)}
                            {--contact= : 联系方式}
                            {--interactive : 交互模式}';

    protected $description = '创建新用户';

    public function handle(): int
    {
        $isInteractive = $this->option('interactive') || ! $this->option('username');

        if ($isInteractive) {
            return $this->handleInteractive();
        }

        return $this->handleNonInteractive();
    }

    protected function handleInteractive(): int
    {
        $this->info('创建新用户');
        $this->newLine();

        $username = $this->ask('用户名');
        $email = $this->ask('邮箱地址');
        $password = $this->secret('密码');
        $roleNames = Role::where('guard_name', 'web')->pluck('name')->all();
        $role = $this->choice('角色', $roleNames ?: ['client'], 0);
        $contact = $this->ask('联系方式 (可选)', '');

        return $this->createUser($username, $email, $password, $role, $contact);
    }

    protected function handleNonInteractive(): int
    {
        $username = $this->option('username');
        $email = $this->option('email');
        $password = $this->option('password');
        $role = $this->option('role');
        $contact = $this->option('contact') ?? '';

        if (! $username || ! $email || ! $password) {
            $this->error('非交互模式下必须提供 --username, --email, --password');

            return Command::FAILURE;
        }

        return $this->createUser($username, $email, $password, $role, $contact);
    }

    protected function createUser(string $username, string $email, string $password, string $role, string $contact): int
    {
        $validator = Validator::make([
            'username' => $username,
            'email' => $email,
            'password' => $password,
            'role' => $role,
        ], [
            'username' => ['required', 'string', 'max:255', 'unique:users,username'],
            'email' => ['required', 'email', 'unique:users,email'],
            'password' => ['required', 'string', 'min:6'],
            'role' => ['required', 'string', 'exists:roles,name'],
        ]);

        if ($validator->fails()) {
            foreach ($validator->errors()->all() as $error) {
                $this->error($error);
            }

            return Command::FAILURE;
        }

        $user = User::create([
            'username' => $username,
            'email' => $email,
            'password' => $password,
            'contact' => $contact ?: null,
        ]);

        $user->assignRole($role);

        $this->newLine();
        $this->info('用户创建成功!');
        $this->table(
            ['ID', '用户名', '邮箱', '角色'],
            [[$user->id, $user->username, $user->email, $user->getRoleNames()->implode(', ')]]
        );

        return Command::SUCCESS;
    }
}
