package pl.kuczabinski.weights.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPref =
            context.getSharedPreferences("pl.kuczabinski.weights", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)

        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()

        if (token != null) {
            builder.addHeader("Authorization", "Bearer $token")
        }

        builder.addHeader("Accept", "application/json")

        return chain.proceed(builder.build())
    }
}