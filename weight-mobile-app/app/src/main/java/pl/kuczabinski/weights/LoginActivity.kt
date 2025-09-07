package pl.kuczabinski.weights

import android.app.AlertDialog
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

        sharedPreferences = application.getSharedPreferences("pl.kuczabinski.weights", MODE_PRIVATE)

        val userIsLogged = checkUserLoginStatus();

        if (userIsLogged) {
            startMainActivity();
        } else {
            constructView();
        }
    }

    private fun constructView() {
        setContentView(R.layout.activity_login)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.btnLogin)
        registerTextView = findViewById(R.id.textReg)

        loginButton.setOnClickListener { loginUser() }

        registerTextView.setOnClickListener {
            startRegisterActivity()
        }
    }

    private fun startRegisterActivity() {
        val intent = Intent(applicationContext, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startMainActivity() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun checkUserLoginStatus(): Boolean {
        return sharedPreferences.getString("loginStatus", "false").equals("true");
    }

    private fun loginUser() {
        val email: String = editTextEmail.text.toString()
        val password: String = editTextPassword.text.toString()
        val user: User = User(email = email, password = password)

        RetrofitClient.apiService.postLogin(user)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        // Return code is between 200 - 300
                        successfulnessActions(response)
                    } else {
                        // Some error occured 404, 500
                        Toast.makeText(
                            applicationContext,
                            "Error please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Error connecting to server
                    Toast.makeText(
                        applicationContext,
                        "Error" + t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun successfulnessActions(response: Response<ResponseBody>) {
        val jsonResponse = getJsonResponse(response)

        var status: String = jsonResponse.getString("status")

        if (status == "success") {
            loginUserInApp(jsonResponse);
        } else {
            displayError(jsonResponse);
        }
    }

    private fun displayError(jsonResponse: JSONObject) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Błąd logowania")
            .setMessage("Hasło niepoprawne!")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun loginUserInApp(jsonResponse: JSONObject) {
        val jsonObjectData: JSONObject = jsonResponse.getJSONObject("data")

        var token: String = jsonObjectData.getString("token")
        val tokenExpiryDateTime = jsonObjectData.getString("expiry_date");

        sharedPreferences.edit {
            putString("token", token)
            putString("token_expiry", tokenExpiryDateTime)
            putString("loginStatus", "true")
        }

        startMainActivity();
    }

    private fun getJsonResponse(response: Response<ResponseBody>): JSONObject {
        val responseBody = response.body()?.string()
        val jsonObject: JSONObject = JSONObject(responseBody)
        return jsonObject
    }

}