<?php

use App\Http\Controllers\Api\WeightController;
use App\Http\Controllers\AuthController;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

Route::post('/login', [AuthController::class, 'login']);
Route::post('/register', [AuthController::class, 'register']);

Route::middleware('auth:sanctum')->group(function () {
    Route::post('/weights', [WeightController::class, 'store']);
    Route::post('/logout', [AuthController::class, 'logout']);
    Route::get('/user', function (Request $request) {return $request->user();});
    Route::get('/list', function () {
        return App\Models\User::all();
    });
    Route::get('/weights', [WeightController::class, 'index']);
});
