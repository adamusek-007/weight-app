package pl.kuczabinski.weights.weight

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.ResponseBody
import pl.kuczabinski.weights.R
import pl.kuczabinski.weights.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeightAddActivity : AppCompatActivity() {
    private lateinit var editTextWeight: EditText
    private lateinit var buttonSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_weight_add)
        editTextWeight = findViewById(R.id.editTextWeight)
        buttonSend = findViewById(R.id.buttonSend)

        buttonSend.setOnClickListener {
            sendWeightValueToApi()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun sendWeightValueToApi() {
        val weightValue = editTextWeight.text.toString().toFloatOrNull()
        if (weightValue != null) {
            val weight = Weight(weightValue)
            RetrofitClient.getClient(this).sendWeight(weight)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@WeightAddActivity, "Wysłano!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                this@WeightAddActivity,
                                "Błąd serwera!",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@WeightAddActivity, "Błąd sieci!", Toast.LENGTH_SHORT)
                            .show()
                        Log.e("API_ERROR", "onFailure: ${t.message}", t)
                    }
                })
        } else {
            Toast.makeText(this, "Podaj poprawną wagę", Toast.LENGTH_SHORT).show()
        }
    }
}