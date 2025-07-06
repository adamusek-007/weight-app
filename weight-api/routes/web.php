<?php

use App\Http\Controllers\Api\WeightController;
use Illuminate\Support\Facades\Route;

Route::get('/', function () {
    return view('welcome');
});


// Route::post('/weights', [WeightController::class, 'store']);