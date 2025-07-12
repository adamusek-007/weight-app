<?php

use App\Http\Controllers\Api\WeightController;
use App\Http\Controllers\AuthController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::get('/user', function (Request $request) {
    return $request->user();
})->middleware('auth:sanctum');

Route::post('/login', [AuthController::class, 'login']);
Route::post('/register', [AuthController::class, 'register']);

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/weights', [WeightController::class, 'store']);
    Route::post('/logout', [AuthController::class, 'logout']);
});




// Route::post('weights', [WeightController::class, 'store']);
// Route::post('register', [RegisterController::class, 'register'])->name('register');
// Route::post('login', [RegisterController::class, 'login'])->name('login');