<?php

use App\Models\User;
use Database\Seeders\RolePermissionSeeder;
use Illuminate\Foundation\Testing\RefreshDatabase;
use Illuminate\Http\UploadedFile;

uses(RefreshDatabase::class);

beforeEach(function () {
    (new RolePermissionSeeder)->run();
    $this->user = User::factory()->create(['subscription_expires_at' => now()->addDays(30)]);
    $this->user->assignRole('client');
    $this->user->givePermissionTo('builds.create');
});

describe('APK Build Stream API', function () {
    it('returns 401 for unauthenticated requests', function () {
        $response = $this->getJson('/builds/stream?name=test&package_name=com.test.app');

        $response->assertStatus(401);
    });

    it('returns validation errors for missing required fields', function () {
        $response = $this->actingAs($this->user)
            ->getJson('/builds/stream');

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['name']);
    });

    it('returns validation errors for invalid package name', function () {
        $response = $this->actingAs($this->user)
            ->getJson('/builds/stream?name=test&package_name=invalid-package-name');

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['package_name']);
    });

    it('returns validation errors for invalid version', function () {
        $response = $this->actingAs($this->user)
            ->getJson('/builds/stream?name=test&package_name=com.test.app&version=abc');

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['version']);
    });

    it('accepts valid request parameters', function () {
        $response = $this->actingAs($this->user)
            ->get('/builds/stream?name=test&package_name=com.test.app&version=1.0');

        $response->assertStatus(200)
            ->assertHeader('Content-Type', 'text/event-stream; charset=UTF-8');
    });

    it('auto-generates package name when not provided', function () {
        $response = $this->actingAs($this->user)
            ->get('/builds/stream?name=test');

        $response->assertStatus(200)
            ->assertHeader('Content-Type', 'text/event-stream; charset=UTF-8');
    });

    it('sub-account stream uses resource owner and accepts valid request', function () {
        $parent = User::factory()->create([
            'subscription_expires_at' => now()->addDays(30),
            'email' => 'parent-build@example.com',
        ]);
        $parent->assignRole('client');
        $parent->givePermissionTo('builds.create');

        $sub = User::factory()->create([
            'parent_id' => $parent->id,
            'subscription_expires_at' => now()->addDays(30),
            'email' => 'sub-build@example.com',
        ]);
        $sub->assignRole('client');
        $sub->givePermissionTo('builds.create');

        $response = $this->actingAs($sub)
            ->get('/builds/stream?name=test&package_name=com.test.sub&version=1.0');

        $response->assertStatus(200)
            ->assertHeader('Content-Type', 'text/event-stream; charset=UTF-8');
    });
});

describe('APK Build Asset API', function () {
    it('returns icons list for authenticated user', function () {
        $response = $this->actingAs($this->user)
            ->getJson('/builds/assets/icons');

        $response->assertStatus(200)
            ->assertJsonStructure(['icons']);
    });

    it('returns backgrounds list for authenticated user', function () {
        $response = $this->actingAs($this->user)
            ->getJson('/builds/assets/backgrounds');

        $response->assertStatus(200)
            ->assertJsonStructure(['backgrounds']);
    });

    it('requires authentication for asset endpoints', function () {
        $this->getJson('/builds/assets/icons')->assertStatus(401);
        $this->getJson('/builds/assets/backgrounds')->assertStatus(401);
    });
});

describe('Icon Upload API', function () {
    it('requires icon file for upload', function () {
        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', []);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['icon']);
    });

    it('validates icon mime type', function () {
        $file = UploadedFile::fake()->create('document.txt', 100);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', ['icon' => $file]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['icon']);
    });

    it('validates icon max size', function () {
        $file = UploadedFile::fake()->image('icon.png', 192, 192)->size(3000);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', ['icon' => $file]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['icon']);
    });

    it('uploads icon successfully', function () {
        $tempDir = sys_get_temp_dir().'/test-icons-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.icons_path' => $tempDir]);

        $file = UploadedFile::fake()->image('icon.png', 192, 192);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', ['icon' => $file]);

        $response->assertStatus(200)
            ->assertJsonStructure([
                'success',
                'icon' => ['name', 'url'],
            ])
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });

    it('accepts jpg format for icon upload', function () {
        $tempDir = sys_get_temp_dir().'/test-icons-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.icons_path' => $tempDir]);

        $file = UploadedFile::fake()->image('icon.jpg', 192, 192);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', ['icon' => $file]);

        $response->assertStatus(200)
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });
});

describe('Icon Delete API', function () {
    it('requires name for icon deletion', function () {
        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/icons', []);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['name']);
    });

    it('deletes existing icon', function () {
        $tempDir = sys_get_temp_dir().'/test-icons-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.icons_path' => $tempDir]);

        $file = UploadedFile::fake()->image('icon.png', 192, 192);

        $uploadResponse = $this->actingAs($this->user)
            ->postJson('/builds/assets/icons', ['icon' => $file]);

        $iconName = $uploadResponse->json('icon.name');

        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/icons', ['name' => $iconName]);

        $response->assertStatus(200)
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });

    it('returns 404 for non-existent icon', function () {
        $tempDir = sys_get_temp_dir().'/test-icons-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.icons_path' => $tempDir]);

        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/icons', ['name' => 'nonexistent.png']);

        $response->assertStatus(404)
            ->assertJson(['success' => false]);

        // Cleanup
        @rmdir($tempDir);
    });
});

describe('Background Upload API', function () {
    it('requires background file for upload', function () {
        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', []);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['background']);
    });

    it('validates background mime type', function () {
        $file = UploadedFile::fake()->create('document.txt', 100);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', ['background' => $file]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['background']);
    });

    it('validates background max size', function () {
        $file = UploadedFile::fake()->image('bg.png', 1920, 1080)->size(6000);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', ['background' => $file]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['background']);
    });

    it('uploads background successfully', function () {
        $tempDir = sys_get_temp_dir().'/test-backgrounds-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.backgrounds_path' => $tempDir]);

        $file = UploadedFile::fake()->image('bg.png', 1920, 1080);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', ['background' => $file]);

        $response->assertStatus(200)
            ->assertJsonStructure([
                'success',
                'background' => ['name', 'url', 'type'],
            ])
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });

    it('accepts type parameter for background', function () {
        $tempDir = sys_get_temp_dir().'/test-backgrounds-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.backgrounds_path' => $tempDir]);

        $file = UploadedFile::fake()->image('bg.png', 1920, 1080);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', [
                'background' => $file,
                'type' => 'abg',
            ]);

        $response->assertStatus(200)
            ->assertJson([
                'success' => true,
                'background' => ['type' => 'abg'],
            ]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });

    it('validates background type parameter', function () {
        $file = UploadedFile::fake()->image('bg.png', 1920, 1080);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', [
                'background' => $file,
                'type' => 'invalid',
            ]);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['type']);
    });

    it('accepts jpg format for background upload', function () {
        $tempDir = sys_get_temp_dir().'/test-backgrounds-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.backgrounds_path' => $tempDir]);

        $file = UploadedFile::fake()->image('bg.jpg', 1920, 1080);

        $response = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', ['background' => $file]);

        $response->assertStatus(200)
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });
});

describe('Background Delete API', function () {
    it('requires name for background deletion', function () {
        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/backgrounds', []);

        $response->assertStatus(422)
            ->assertJsonValidationErrors(['name']);
    });

    it('deletes existing background', function () {
        $tempDir = sys_get_temp_dir().'/test-backgrounds-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.backgrounds_path' => $tempDir]);

        $file = UploadedFile::fake()->image('bg.png', 1920, 1080);

        $uploadResponse = $this->actingAs($this->user)
            ->postJson('/builds/assets/backgrounds', ['background' => $file]);

        $bgName = $uploadResponse->json('background.name');

        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/backgrounds', ['name' => $bgName]);

        $response->assertStatus(200)
            ->assertJson(['success' => true]);

        // Cleanup
        $files = glob($tempDir.'/*');
        if ($files) {
            foreach ($files as $file) {
                if (is_file($file)) {
                    @unlink($file);
                }
            }
        }
        @rmdir($tempDir);
    });

    it('returns 404 for non-existent background', function () {
        $tempDir = sys_get_temp_dir().'/test-backgrounds-'.uniqid();
        mkdir($tempDir, 0777, true);
        config(['apk-builder.backgrounds_path' => $tempDir]);

        $response = $this->actingAs($this->user)
            ->deleteJson('/builds/assets/backgrounds', ['name' => 'nonexistent.png']);

        $response->assertStatus(404)
            ->assertJson(['success' => false]);

        // Cleanup
        @rmdir($tempDir);
    });
});

describe('APK Build CRUD API', function () {
    it('returns builds list for authenticated user', function () {
        $response = $this->actingAs($this->user)
            ->get('/builds');

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Builds/Index', false)
                    ->has('builds')
            );
    });

    it('returns create page with required data', function () {
        $response = $this->actingAs($this->user)
            ->get('/builds/create');

        $response->assertStatus(200)
            ->assertInertia(
                fn ($page) => $page
                    ->component('Builds/Create', false)
                    ->has('templates')
                    ->has('icons')
                    ->has('backgrounds')
            );
    });

    it('store route is removed', function () {
        $response = $this->actingAs($this->user)
            ->post('/builds', ['name' => 'Test Build']);

        $response->assertStatus(405);
    });
});
