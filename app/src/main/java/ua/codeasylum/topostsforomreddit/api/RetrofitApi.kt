package ua.codeasylum.topostsforomreddit.api

import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap
import ua.codeasylum.topostsforomreddit.models.Top

interface RetrofitApi {

    @GET("top/.json")
    fun getTopPosts(@QueryMap queryMap: Map<String, String>): Call<Top>
}