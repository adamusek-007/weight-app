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

    private lateinit var startWeightAddActivityBtn: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = application.getSharedPreferences("pl.kuczabinski.weights", MODE_PRIVATE)

        if (sharedPreferences.getString("loginStatus", "false").equals("false")) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startWeightAddActivityBtn = findViewById(R.id.startWeightActivityBtn);

        startWeightAddActivityBtn.setOnClickListener {
            val intent = Intent(this, WeightAddActivity::class.java)
            startActivity(intent)
        }

    }
}