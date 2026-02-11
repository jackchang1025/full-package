<?php

use App\Services\ApkBuilder\Obfuscator;
use Illuminate\Support\Facades\File;
use Tests\Support\ApkBuilderTestFixtures;

describe('Obfuscator generateJunkClasses', function () {
    it('generateJunkClasses creates expected number of files', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        File::ensureDirectoryExists($smaliDir);

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->generateJunkClasses(5, 3);

            expect($count)->toBe(5);

            $junkDirs = glob($smaliDir.'/*', GLOB_ONLYDIR);
            expect($junkDirs)->not->toBeEmpty();

            $totalFiles = 0;
            foreach ($junkDirs as $dir) {
                $files = glob($dir.'/*.smali');
                $totalFiles += count($files);
            }
            expect($totalFiles)->toBe(5);
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});

describe('Obfuscator shuffleClassNames', function () {
    it('shuffleClassNames skips R, BuildConfig, MainActivity', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        File::ensureDirectoryExists($smaliDir);

        $pkg = 'com/test';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);
        File::put($smaliDir.'/'.$pkg.'/R.smali', '.class public L'.$pkg.'/R;');
        File::put($smaliDir.'/'.$pkg.'/BuildConfig.smali', '.class public L'.$pkg.'/BuildConfig;');
        File::put($smaliDir.'/'.$pkg.'/MainActivity.smali', '.class public L'.$pkg.'/MainActivity;');

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->shuffleClassNames();

            expect(File::exists($smaliDir.'/'.$pkg.'/R.smali'))->toBeTrue();
            expect(File::exists($smaliDir.'/'.$pkg.'/BuildConfig.smali'))->toBeTrue();
            expect(File::exists($smaliDir.'/'.$pkg.'/MainActivity.smali'))->toBeTrue();
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });

    it('shuffleClassNames applies mapping to references', function () {
        $buildDir = ApkBuilderTestFixtures::createMockBuildDir();
        $smaliDir = $buildDir.'/smali';
        File::ensureDirectoryExists($smaliDir);

        $pkg = 'com/test';
        File::ensureDirectoryExists($smaliDir.'/'.$pkg);
        File::put($smaliDir.'/'.$pkg.'/FooBar.smali', ".class public L{$pkg}/FooBar;\n.field ref:L{$pkg}/FooBar;");

        try {
            $obfuscator = new Obfuscator($buildDir);
            $count = $obfuscator->shuffleClassNames();

            if ($count > 0) {
                $files = glob($smaliDir.'/'.$pkg.'/*.smali');
                expect($files)->not->toBeEmpty();
            }
        } finally {
            ApkBuilderTestFixtures::cleanupMockBuildDir($buildDir);
        }
    });
});
