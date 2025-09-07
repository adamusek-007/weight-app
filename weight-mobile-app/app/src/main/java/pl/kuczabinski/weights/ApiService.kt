package pl.kuczabinski.weights

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("weights")
    fun sendWeight(
        @Body weight: Weight
    ): Call<ResponseBody>

    @POST("register")
    fun register(
        @Body user: User
    ): Call<ResponseBody>

    @POST("login")
    fun login(
        @Body user: User
    ): Call<ResponseBody>
}
