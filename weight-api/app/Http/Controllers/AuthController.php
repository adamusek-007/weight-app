<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Validator;

class AuthController extends Controller
{
    function register(Request $request)
    {
        $validate = Validator::make(
            $request->all(),
            [
                'name' => 'required',
                'email' => 'required|email|unique:users',
                'password' => 'required'
            ]
        );

        if ($validate->fail()) {
            return response()->json(
                [
                    "status" => 'error',
                    'message' => $validate->errors()->getMessages()
                ],
                200
            );
        }

        $validated = $validate->validated();

        $user = new User();
        $user->name = $validated['name'];
        $user->email = $validated['email'];
        $user->password = $validated['password'];
        if ($user->save()) {
            return response()->json(
                [
                    "status" => 'success',
                    'message' => "User created successfully"
                ],
                200
            );
        }
        return response()->json(
            [
                "status" => 'error',
                'message' => "Unknown error occured"
            ],
            200
        );
    }
    public function login(Request $request)
    {
        $validate = Validator::make(
            $request->all(),
            [
                'email' => 'required|email|unique:users',
                'password' => 'required'
            ]
        );
        if ($validate->fails()) {
            return response()->json(
                [
                    "status" => "error",
                    "message" => $validate->errors()->getMessages()
                ]
            );
        }
        $validated = $validate->validated();
        if (Auth::attempt(['email' => $validated['email'], 'password' => $validated['password']])) {
            $user = $request->user();
            $token = $user->createToken('mobile_auth_token')->plainTextToken;
            return response()->json(
                [
                    "status" => "success",
                    "data" => ['user' => $user, 'token' => $token],
                    "message" => "Logged in successfully"
                ]
            );
        }
        return response()->json(
            [
                "status" => "error",
                "message" => "Unknown error occured"
            ]
        );
        // $request->validate([
        //     'email' => 'required|email',
        //     'password' => 'required'
        // ]);

        if (!Auth::attempt($request->only('email', 'password'))) {
            return response()->json(['message' => 'Invalid credentials'], 401);
        }

        $token = $request->user()->createToken('mobile')->plainTextToken;

        return response()->json(['token' => $token]);
    }

    public function logout(Request $request)
    {
        $request->user()->currentAccessToken()->delete();

        return response()->json(['message' => 'Logged out']);
    }

}
