package pl.kuczabinski.weights

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : ComponentActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var registerTextView: TextView
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferences = application.getSharedPreferences("pl.kuczabinski.weights", MODE_PRIVATE)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.btnLogin)
        loginButton.setOnClickListener { loginUser() }
        registerTextView = findViewById(R.id.textReg)

        registerTextView.setOnClickListener {
            var intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        if (sharedPreferences.getString("loginStatus", "false").equals("true")) {
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginUser() {
        var email: String
        var password: String
        email = editTextEmail.text.toString()
        password = editTextPassword.text.toString()
        var user: User = User(email = email, password = password)
        RetrofitClient.apiService.postLogin(user)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        var responseBody = response.body()?.string()
                        var jsonObject: JSONObject = JSONObject(responseBody)
                        var jsonObjectData: JSONObject = jsonObject.getJSONObject("data")
                        var jsonObjectUser: JSONObject = jsonObjectData.getJSONObject("user")

                        var token: String = jsonObjectData.getString("token")
                        var name: String = jsonObjectUser.getString("name")
                        var email: String = jsonObjectUser.getString("email")

                        var status: String = jsonObject.getString("status")

                        sharedPreferences.edit {
                            putString("token", token)
                            putString("name", name)
                            putString("email", email)
                            putString("loginStatus", "true")
                        }

                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Error please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        applicationContext,
                        "Error" + t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}