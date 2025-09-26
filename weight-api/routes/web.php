<?php

use App\Http\Controllers\Api\WeightController;
use Illuminate\Support\Facades\Route;

Route::get('/', function (): mixed {
    return "Działam";
});


Route::post('/weights', [WeightController::class, 'store']);