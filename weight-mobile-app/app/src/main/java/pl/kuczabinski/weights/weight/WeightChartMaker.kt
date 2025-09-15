package pl.kuczabinski.weights.weight

import android.R
import android.content.Context
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import pl.kuczabinski.weights.remote.ApiResponse
import pl.kuczabinski.weights.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeightChartMaker(private val context: Context, private val chart: LineChart) {


    fun loadData(){
        RetrofitClient.getClient(context).getWeights()
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        val weights = response.body()!!.data
                        showWeightChart(weights)
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    private fun showWeightChart(weights: List<WeightEntry>) {
        val entries = parseWeightsToEntries(weights)

        val dataSet = LineDataSet(entries, "Historia wagi").apply {
            color = ContextCompat.getColor(context, R.color.holo_blue_dark)
            valueTextColor = ContextCompat.getColor(context, R.color.black)
            lineWidth = 2f
            circleRadius = 4f
            setCircleColor(ContextCompat.getColor(context, R.color.holo_blue_dark))
        }

        val lineData = LineData(dataSet)
        chart.data = lineData

        val xAxis = chart.xAxis
        xAxis.valueFormatter = object : ValueFormatter() {
            private val sdf = SimpleDateFormat("dd", Locale.getDefault())
            override fun getFormattedValue(value: Float): String {
                return sdf.format(Date(value.toLong()))
            }
        }
        xAxis.labelRotationAngle = -45f

        chart.description.text = "Waga w czasie"
        chart.invalidate()
    }

    private fun parseWeightsToEntries(weights: List<WeightEntry>): List<Entry> {
        val entries = ArrayList<Entry>()
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        weights.forEach { weight ->
            val date = sdf.parse(weight.created_at) ?: Date()
            val xValue = date.time.toFloat() // timestamp jako float
            entries.add(Entry(xValue, weight.value))
        }

        return entries
    }
}