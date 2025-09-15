package pl.kuczabinski.weights

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
import pl.kuczabinski.weights.db.DatabaseProvider
import pl.kuczabinski.weights.db.WeightEntry
import pl.kuczabinski.weights.db.WeightRepository
import pl.kuczabinski.weights.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

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
            readWeightAndSendToApi()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun readWeightAndSendToApi() {
        val weightValue = editTextWeight.text.toString().toLongOrNull()
        if (weightValue != null) {
            val weight = WeightEntry(weight = weightValue)
//            val weight = Weight(weightValue)
            saveWeightToLocalDatabase(weight);
//            sendWeightToAPI(weight)
        } else {
            Toast.makeText(this, "Podaj poprawną wagę", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveWeightToLocalDatabase(weight: WeightEntry) {
        val db = DatabaseProvider.getDatabase(applicationContext);
        val weightDao = db.weightDao();
        val weightRepository = WeightRepository(weightDao);
        weightRepository.addWeightEntry(weight.weightValue)
        weightDao.insert(WeightEntry(weight = 80.3f))
    }

    private fun sendWeightToAPI(weight: Weight) {
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
    }
}