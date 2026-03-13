<?php

declare(strict_types=1);

use App\Services\ApkBuilder\SmaliProcessor;
use Illuminate\Support\Facades\File;

describe('SmaliProcessor - removeWakeScreenFlags', function () {
    beforeEach(function () {
        $this->buildDir = sys_get_temp_dir() . '/test_smali_' . uniqid();
        mkdir($this->buildDir);
        mkdir($this->buildDir . '/smali');
        mkdir($this->buildDir . '/smali/com');
        mkdir($this->buildDir . '/smali/com/icontrol');
        mkdir($this->buildDir . '/smali/com/icontrol/protector');
        
        $this->processor = new SmaliProcessor($this->buildDir);
        $this->transparentActivityPath = $this->buildDir . '/smali/com/icontrol/protector/TransparentActivity.smali';
    });

    afterEach(function () {
        if (is_dir($this->buildDir)) {
            exec('rm -rf ' . escapeshellarg($this->buildDir));
        }
    });

    it('移除 FLAG_SHOW_WHEN_LOCKED (0x80000) 当禁用自动唤醒时', function () {
        $content = <<<'SMALI'
.method protected onCreate(Landroid/os/Bundle;)V
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object p1
    const/high16 v0, 0x80000
    invoke-virtual {p1, v0}, Landroid/view/Window;->addFlags(I)V
    return-void
.end method
SMALI;
        
        File::put($this->transparentActivityPath, $content);
        
        $this->processor->removeWakeScreenFlags(false);
        
        $result = File::get($this->transparentActivityPath);
        expect($result)->not->toContain('0x80000');
        expect($result)->not->toContain('addFlags');
    });

    it('移除 FLAG_ALLOW_LOCK_WHILE_SCREEN_ON (0x20) 当禁用自动唤醒时', function () {
        $content = <<<'SMALI'
.method protected onCreate(Landroid/os/Bundle;)V
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object p1
    const/16 v0, 0x20
    invoke-virtual {p1, v0, v0}, Landroid/view/Window;->setFlags(II)V
    return-void
.end method
SMALI;
        
        File::put($this->transparentActivityPath, $content);
        
        $this->processor->removeWakeScreenFlags(false);
        
        $result = File::get($this->transparentActivityPath);
        expect($result)->not->toContain('0x20');
        expect($result)->not->toContain('setFlags');
    });

    it('保留 Window flags 当启用自动唤醒时', function () {
        $content = <<<'SMALI'
.method protected onCreate(Landroid/os/Bundle;)V
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object p1
    const/high16 v0, 0x80000
    invoke-virtual {p1, v0}, Landroid/view/Window;->addFlags(I)V
    const/16 v0, 0x20
    invoke-virtual {p1, v0, v0}, Landroid/view/Window;->setFlags(II)V
    return-void
.end method
SMALI;
        
        File::put($this->transparentActivityPath, $content);
        
        $this->processor->removeWakeScreenFlags(true);
        
        $result = File::get($this->transparentActivityPath);
        expect($result)->toContain('0x80000');
        expect($result)->toContain('0x20');
        expect($result)->toContain('addFlags');
        expect($result)->toContain('setFlags');
    });

    it('不报错当 TransparentActivity 文件不存在时', function () {
        expect(fn() => $this->processor->removeWakeScreenFlags(false))->not->toThrow(Exception::class);
    });

    it('同时移除两个 flags 当禁用自动唤醒时', function () {
        $content = <<<'SMALI'
.method protected onCreate(Landroid/os/Bundle;)V
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object p1
    const/high16 v0, 0x80000
    invoke-virtual {p1, v0}, Landroid/view/Window;->addFlags(I)V
    invoke-virtual {p0}, Landroid/app/Activity;->getWindow()Landroid/view/Window;
    move-result-object p1
    const/16 v0, 0x20
    invoke-virtual {p1, v0, v0}, Landroid/view/Window;->setFlags(II)V
    return-void
.end method
SMALI;
        
        File::put($this->transparentActivityPath, $content);
        
        $this->processor->removeWakeScreenFlags(false);
        
        $result = File::get($this->transparentActivityPath);
        expect($result)->not->toContain('0x80000');
        expect($result)->not->toContain('0x20');
        expect($result)->not->toContain('addFlags');
        expect($result)->not->toContain('setFlags');
    });
});
