package pl.kuczabinski.weights.remote

import okhttp3.ResponseBody
import pl.kuczabinski.weights.User
import pl.kuczabinski.weights.Weight
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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

    @GET("weights")
    fun getWeights(): Call<ApiResponse>


}
