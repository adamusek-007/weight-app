package pl.kuczabinski.weights

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import com.github.mikephil.charting.charts.LineChart

class MainActivity : ComponentActivity() {

    private lateinit var startWeightAddActivityBtn: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var weightChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = application.getSharedPreferences("pl.kuczabinski.weights", MODE_PRIVATE)

        if (sharedPreferences.getString("loginStatus", "false").equals("false")) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weightChart = findViewById(R.id.weightChart)

        startWeightAddActivityBtn = findViewById(R.id.startWeightActivityBtn)

        startWeightAddActivityBtn.setOnClickListener {
            val intent = Intent(this, WeightAddActivity::class.java)
            startActivity(intent)
        }

        val chartMaker = WeightChartMaker(this, weightChart);
        chartMaker.loadData();


    }
}