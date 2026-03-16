# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

飞鹰管理系统 V2 - A device management platform migrated from legacy PHP to modern Laravel 12 + Vue 3 stack.

## Repository Structure

```
full-package/
├── app/                      # Main Laravel 12 application
├── android/                  # Android client project (Java, Gradle 8.5)
├── legacy/                   # Legacy PHP system (archived, reference only)
├── docs/                     # Documentation
│   ├── migration/            # New system docs (FRONTEND.md, API.md, etc.)
│   ├── vendor-replication/   # Android APK replication module docs (8 modules)
│   └── legacy/               # Legacy system docs
└── docker/                   # Docker configuration
```

## Development Commands

### Laravel (Web Application)

All commands run from the `app/` directory using Laravel Sail:

```bash
# Start development environment
./dev-start.sh
./vendor/bin/sail npm run dev

# Services
./vendor/bin/sail up -d          # Start containers
./vendor/bin/sail down           # Stop containers

# Database
./vendor/bin/sail artisan migrate
./vendor/bin/sail artisan migrate:fresh --seed

# Cache clearing
./vendor/bin/sail artisan cache:clear
./vendor/bin/sail artisan config:clear
./vendor/bin/sail artisan route:clear

# Run tests
./vendor/bin/sail pest                              # All tests
./vendor/bin/sail pest tests/Feature/WebSocket/    # WebSocket tests only

# Lint PHP
./vendor/bin/sail pint
```

### Android (Client Application)

All commands run from the `android/` directory:

```bash
# Run unit tests (daily development - no APK/device needed)
./gradlew test

# Run specific test class
./gradlew test --tests "com.vendor.rat.network.HttpClientTest"

# Clean build and test
./gradlew clean test

# Build Debug APK (only when real device testing is needed)
./gradlew assembleDebug
```

**Environment** (WSL Ubuntu 22.04):
- JDK 17: `/usr/lib/jvm/java-17-openjdk-amd64`
- Android SDK: `/opt/android-sdk`
- Gradle 8.5 + AGP 8.2.2

## Tech Stack

### Web Application (app/)

| Layer | Technology |
|-------|------------|
| Backend | Laravel 12, PHP 8.5 |
| Frontend | Vue 3 + Inertia.js + TypeScript |
| UI | Naive UI 2.43 + Tailwind CSS 4 |
| Database | MySQL 8.4, Redis |
| WebSocket | PHP + Swoole |
| Build | Vite 7 |
| Dev Environment | Laravel Sail (Docker) |

### Android Client (android/)

| Layer | Technology |
|-------|------------|
| Language | Java 8+ (source compat) |
| Platform | Android API 21-34 |
| Build | Gradle 8.5 + AGP 8.2.2 |
| Network | OkHttp 4.12.0 + Conscrypt 2.5.2 |
| JSON | Gson 2.10.1 |
| Test | JUnit 4.13.2 + Mockito 5.3.1 + Robolectric 4.11.1 |
| Dev Environment | WSL Ubuntu 22.04 + JDK 17 |

## Access URLs (Development)

| Service | URL |
|---------|-----|
| Application | http://localhost:8000 |
| WebSocket | ws://localhost:8081 |
| Vite HMR | http://localhost:5173 |
| MySQL | localhost:3307 |
| Redis | localhost:6380 |

## Code Conventions

### Backend (Laravel)
- Use Eloquent ORM (avoid raw SQL)
- Add validation rules for all inputs
- Follow Laravel best practices

### Frontend (Vue 3)
- Use Composition API with `<script setup>`
- TypeScript for all components
- Naive UI components for UI elements

## Key Documentation

- **Frontend development**: `docs/migration/FRONTEND.md`
- **WebSocket system**: `docs/migration/WEBSOCKET_CLIENT.md`
- **APK builder (Laravel)**: `docs/migration/APK_BUILDER.md`
- **Control panel operations**: `docs/migration/CONTROL_PANEL_SCREEN_OPERATIONS.md`
- **Legacy APK build system**: `docs/legacy/APK_BUILD_SYSTEM.md`
- **Android client modules**: `docs/vendor-replication/README.md`
- **Android testing guide**: `docs/vendor-replication/TESTING_GUIDE.md`

## Important Notes

- Use `npm run dev` for development (not `npm run build`); built files enable caching that breaks HMR
- WebSocket tests use random ports and auto-cleanup; no manual server management needed
- The `legacy/` directory is for reference only; new development goes in `app/`
- The `android/` directory is a standalone Gradle project; do NOT put Android code in `app/` (that's Laravel)
- Android daily development uses `./gradlew test` in WSL; no need to build APK or connect devices for 90% of testing
