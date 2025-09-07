<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Weight;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Log;


class WeightController extends Controller
{
    public function store(Request $request)
    {
        $validated = $request->validate([
            'weightValue' => 'required|numeric'
        ]);

        $user = Auth::user();

        $weight = new Weight();

        $weight->value = $validated['weightValue'];
        $weight->user_id = $user->id;

        $weight->save();

        return response()->json([
            'message' => 'Waga zapisana pomyślnie!',
            'data' => $weight
        ], 201);
    }
}
