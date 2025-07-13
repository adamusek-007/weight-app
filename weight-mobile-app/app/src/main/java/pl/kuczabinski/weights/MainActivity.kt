package pl.kuczabinski.weights

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {

    private lateinit var editTextWeight: EditText
    private lateinit var buttonSend: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = application.getSharedPreferences("pl.kuczabinski.weights", MODE_PRIVATE)

        if(sharedPreferences.getString("loginStatus", "false").equals("false")){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextWeight = findViewById(R.id.editTextWeight)
        buttonSend = findViewById(R.id.buttonSend)

        buttonSend.setOnClickListener {
            sendWeightValueToApi()
        }
    }

    private fun sendWeightValueToApi() {
        val weightValue = editTextWeight.text.toString().toFloatOrNull()
        if (weightValue != null) {
            val weight = Weight(weightValue)
            RetrofitClient.apiService.sendWeight(weight)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Wysłano!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@MainActivity, "Błąd serwera!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Błąd sieci!", Toast.LENGTH_SHORT).show()
                        Log.e("API_ERROR", "onFailure: ${t.message}", t)
                    }
                })
        } else {
            Toast.makeText(this, "Podaj poprawną wagę", Toast.LENGTH_SHORT).show()
        }
    }
}