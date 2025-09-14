package pl.kuczabinski.weights.remote

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val ENDPOINT_ADDRESS: String = "http://192.168.100.71:8000/api/"

    fun getClient(context: Context):ApiService {

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .build()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(ENDPOINT_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        return retrofit!!.create(ApiService::class.java)
    }
}

