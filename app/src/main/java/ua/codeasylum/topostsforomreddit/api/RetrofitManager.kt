package ua.codeasylum.topostsforomreddit.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://www.reddit.com/"
const val ERR_CONNECT = "Server or internet connection error"
const val ERR_WITH_CODE = "Error! Response code is "
const val ERR_NULL = "some wrong in data class or server"

object RetrofitManager {
    private var retrofitApi: RetrofitApi? = null

    fun getClient(): RetrofitApi? {


        if (retrofitApi == null) {

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            synchronized(Retrofit::class.java) {
                if (retrofitApi == null) {
                    val retrofit = Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .build()
                    retrofitApi = retrofit.create(RetrofitApi::class.java)
                }
            }
        }
        return retrofitApi
    }
}