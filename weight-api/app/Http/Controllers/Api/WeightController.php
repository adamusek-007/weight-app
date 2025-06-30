<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Models\Weight;
use Illuminate\Http\Request;


class WeightController extends Controller
{
    public function store(Request $request)
    {
        $validated = $request->validate([
            'value' => 'required|numeric'
        ]);

        $weight = new Weight();
        $weight->value = $validated['value'];
        $weight->save();

        return response()->json([
            'message' => 'Waga zapisana pomyślnie!',
            'data' => $weight
        ], 201);
    }
}