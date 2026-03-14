<?php

namespace Tests\Feature\Admin;

use App\Models\Admin;
use App\Models\Setting;
use App\Models\User;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Spatie\Permission\Models\Role;
use Tests\TestCase;

class MainAccountLimitTest extends TestCase
{
    use RefreshDatabase;

    private Admin $admin;

    protected function setUp(): void
    {
        parent::setUp();
        Role::firstOrCreate(['name' => 'client', 'guard_name' => 'web']);
        $this->admin = Admin::factory()->create();
    }

    /** 测试：无限制时可以创建主账号 */
    public function test_can_create_main_account_when_no_limit(): void
    {
        Setting::set('max_main_accounts', null);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'testuser',
                'email' => 'test@example.com',
                'password' => 'password123',
                'password_confirmation' => 'password123',
                'roles' => ['client'],
            ]);

        $response->assertRedirect();
        $this->assertDatabaseHas('users', ['email' => 'test@example.com', 'parent_id' => null]);
    }

    /** 测试：限制为 0 时可以创建主账号 */
    public function test_can_create_main_account_when_limit_is_zero(): void
    {
        Setting::set('max_main_accounts', 0);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'testuser',
                'email' => 'test@example.com',
                'password' => 'password123',
                'password_confirmation' => 'password123',
                'roles' => ['client'],
            ]);

        $response->assertRedirect();
        $this->assertDatabaseHas('users', ['email' => 'test@example.com']);
    }

    /** 测试：达到限制时拒绝创建主账号 */
    public function test_cannot_create_main_account_when_limit_reached(): void
    {
        Setting::set('max_main_accounts', 2);
        User::factory()->count(2)->create(['parent_id' => null]);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'testuser',
                'email' => 'test@example.com',
                'password' => 'password123',
                'password_confirmation' => 'password123',
                'roles' => ['client'],
                'parent_id' => null,
            ]);

        $response->assertSessionHasErrors('parent_id');
        $this->assertDatabaseMissing('users', ['email' => 'test@example.com']);
    }

    /** 测试：未达到限制时可以创建主账号 */
    public function test_can_create_main_account_when_under_limit(): void
    {
        Setting::set('max_main_accounts', 3);
        User::factory()->count(2)->create(['parent_id' => null]);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'testuser',
                'email' => 'test@example.com',
                'password' => 'password123',
                'password_confirmation' => 'password123',
                'roles' => ['client'],
            ]);

        $response->assertRedirect();
        $this->assertDatabaseHas('users', ['email' => 'test@example.com', 'parent_id' => null]);
    }

    /** 测试：子账号不受主账号数量限制影响 */
    public function test_can_create_sub_account_when_main_account_limit_reached(): void
    {
        Setting::set('max_main_accounts', 1);
        $parent = User::factory()->create(['parent_id' => null, 'max_sub_accounts' => 5]);

        $response = $this->actingAs($this->admin, 'admin')
            ->post(route('admin.users.store'), [
                'username' => 'subuser',
                'email' => 'sub@example.com',
                'password' => 'password123',
                'password_confirmation' => 'password123',
                'parent_id' => $parent->id,
            ]);

        $response->assertRedirect();
        $this->assertDatabaseHas('users', ['email' => 'sub@example.com', 'parent_id' => $parent->id]);
    }

    /** 测试：系统设置页面显示 max_main_accounts 配置项 */
    public function test_settings_page_shows_max_main_accounts_field(): void
    {
        Setting::set('max_main_accounts', 100);

        $response = $this->actingAs($this->admin, 'admin')
            ->get(route('admin.settings.index'));

        $response->assertOk();
        $response->assertInertia(fn ($page) => $page
            ->has('settings.max_main_accounts')
            ->where('settings.max_main_accounts', 100)
        );
    }

    /** 测试：可以通过设置页面更新 max_main_accounts */
    public function test_can_update_max_main_accounts_via_settings(): void
    {
        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.settings.update'), [
                'app_name' => 'Test App',
                'admin_entry_path' => 'admin',
                'max_main_accounts' => 50,
            ]);

        $response->assertRedirect();
        $this->assertEquals(50, Setting::getInt('max_main_accounts'));
    }

    /** 测试：可以清空 max_main_accounts（不限制） */
    public function test_can_clear_max_main_accounts_limit(): void
    {
        Setting::set('max_main_accounts', 100);

        $response = $this->actingAs($this->admin, 'admin')
            ->put(route('admin.settings.update'), [
                'app_name' => 'Test App',
                'admin_entry_path' => 'admin',
                'max_main_accounts' => null,
            ]);

        $response->assertRedirect();
        $this->assertNull(Setting::get('max_main_accounts'));
    }
}
