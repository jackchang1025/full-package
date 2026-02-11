<?php

namespace App\Console\Commands;

use App\Models\Admin;
use Illuminate\Console\Command;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Facades\Validator;

class CreateAdminCommand extends Command
{
    protected $signature = 'admin:create
                            {--name= : 管理员名称}
                            {--email= : 邮箱}
                            {--password= : 密码}
                            {--interactive : 交互模式}';

    protected $description = '创建总管理员账号（admins 表）';

    public function handle(): int
    {
        $name = $this->option('name') ?: $this->ask('管理员名称');
        $email = $this->option('email') ?: $this->ask('邮箱');
        $password = $this->option('password') ?: $this->secret('密码');

        $validator = Validator::make(
            ['name' => $name, 'email' => $email, 'password' => $password],
            [
                'name' => ['required', 'string', 'max:100'],
                'email' => ['required', 'email', 'unique:admins,email'],
                'password' => ['required', 'string', 'min:6'],
            ]
        );

        if ($validator->fails()) {
            foreach ($validator->errors()->all() as $error) {
                $this->error($error);
            }

            return Command::FAILURE;
        }

        Admin::create([
            'name' => $name,
            'email' => $email,
            'password' => Hash::make($password),
        ]);

        $this->info('管理员创建成功。');
        $this->table(['名称', '邮箱'], [[$name, $email]]);

        return Command::SUCCESS;
    }
}
