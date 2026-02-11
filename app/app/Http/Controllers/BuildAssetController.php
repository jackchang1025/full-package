<?php

namespace App\Http\Controllers;

use App\Http\Requests\Build\DeleteAssetRequest;
use App\Http\Requests\Build\UploadBackgroundRequest;
use App\Http\Requests\Build\UploadIconRequest;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\File;

class BuildAssetController extends Controller
{
    public function icons(Request $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $iconsPath = config('apk-builder.icons_path').'/'.$userId;

        $icons = $this->listImages($iconsPath, 'icons', $userId);

        return response()->json(['icons' => $icons]);
    }

    public function uploadIcon(UploadIconRequest $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $iconsPath = config('apk-builder.icons_path').'/'.$userId;

        File::ensureDirectoryExists($iconsPath);

        $file = $request->file('icon');
        $filename = md5($file->getClientOriginalName().time()).'.png';
        $outputPath = $iconsPath.'/'.$filename;

        $this->convertToPng($file->getPathname(), $outputPath, 192, 192);

        return response()->json([
            'success' => true,
            'icon' => [
                'name' => $filename,
                'url' => '/storage/icons/'.$userId.'/'.$filename,
            ],
        ]);
    }

    public function deleteIcon(DeleteAssetRequest $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $iconPath = config('apk-builder.icons_path').'/'.$userId.'/'.basename($request->validated('name'));

        if (File::exists($iconPath)) {
            File::delete($iconPath);

            return response()->json(['success' => true]);
        }

        return response()->json(['success' => false, 'message' => '图标不存在'], 404);
    }

    public function backgrounds(Request $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $bgPath = config('apk-builder.backgrounds_path').'/'.$userId;

        $backgrounds = $this->listImages($bgPath, 'backgrounds', $userId);

        return response()->json(['backgrounds' => $backgrounds]);
    }

    public function uploadBackground(UploadBackgroundRequest $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $bgPath = config('apk-builder.backgrounds_path').'/'.$userId;

        File::ensureDirectoryExists($bgPath);

        $file = $request->file('background');
        $type = $request->validated('type', 'blackui');
        $filename = md5($file->getClientOriginalName().time()).'.png';
        $outputPath = $bgPath.'/'.$filename;

        $this->convertToPng($file->getPathname(), $outputPath);

        return response()->json([
            'success' => true,
            'background' => [
                'name' => $filename,
                'url' => '/storage/backgrounds/'.$userId.'/'.$filename,
                'type' => $type,
            ],
        ]);
    }

    public function deleteBackground(DeleteAssetRequest $request): JsonResponse
    {
        $userId = $request->user()->getResourceOwnerId();
        $bgPath = config('apk-builder.backgrounds_path').'/'.$userId.'/'.basename($request->validated('name'));

        if (File::exists($bgPath)) {
            File::delete($bgPath);

            return response()->json(['success' => true]);
        }

        return response()->json(['success' => false, 'message' => '背景图不存在'], 404);
    }

    private function listImages(string $path, string $type, int $userId): array
    {
        $images = [];
        if (File::isDirectory($path)) {
            $files = File::files($path);
            foreach ($files as $file) {
                if (in_array(strtolower($file->getExtension()), ['png', 'jpg', 'jpeg'])) {
                    $images[] = [
                        'name' => $file->getFilename(),
                        'url' => '/storage/'.$type.'/'.$userId.'/'.$file->getFilename(),
                        'created_at' => date('Y-m-d H:i:s', $file->getMTime()),
                    ];
                }
            }
        }

        usort($images, fn ($a, $b) => strtotime($b['created_at']) - strtotime($a['created_at']));

        return $images;
    }

    private function convertToPng(string $inputPath, string $outputPath, ?int $width = null, ?int $height = null): void
    {
        $imageInfo = getimagesize($inputPath);
        $mimeType = $imageInfo['mime'] ?? '';

        $source = match ($mimeType) {
            'image/png' => imagecreatefrompng($inputPath),
            'image/jpeg', 'image/jpg' => imagecreatefromjpeg($inputPath),
            default => imagecreatefromstring(file_get_contents($inputPath)),
        };

        if ($width && $height) {
            $resized = imagecreatetruecolor($width, $height);
            imagesavealpha($resized, true);
            $transparent = imagecolorallocatealpha($resized, 0, 0, 0, 127);
            imagefill($resized, 0, 0, $transparent);

            $srcWidth = imagesx($source);
            $srcHeight = imagesy($source);
            imagecopyresampled($resized, $source, 0, 0, 0, 0, $width, $height, $srcWidth, $srcHeight);

            imagepng($resized, $outputPath);
            imagedestroy($resized);
        } else {
            imagesavealpha($source, true);
            imagepng($source, $outputPath);
        }

        imagedestroy($source);
    }
}
