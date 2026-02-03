<?php

namespace Tests\Feature;

// use Illuminate\Foundation\Testing\RefreshDatabase;
use Tests\TestCase;

class ExampleTest extends TestCase
{
    /**
     * Root path redirects to login when unauthenticated.
     */
    public function test_the_application_redirects_to_login_when_unauthenticated(): void
    {
        $response = $this->get('/');

        $response->assertStatus(302)->assertRedirect(route('login'));
    }
}
