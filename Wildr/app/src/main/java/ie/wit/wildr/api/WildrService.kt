package ie.wit.wildr.api

import ie.wit.wildr.models.WildrModel
import retrofit2.Call
import retrofit2.http.*


interface WildrService {
    @GET("/donations")
    fun getall(): Call<List<WildrModel>>

    @GET("/donations/{id}")
    fun get(@Path("id") id: String): Call<WildrModel>

    @DELETE("/donations/{id}")
    fun delete(@Path("id") id: String): Call<WildrWrapper>

    @POST("/donations")
    fun post(@Body donation: WildrModel): Call<WildrWrapper>

    @PUT("/donations/{id}")
    fun put(@Path("id") id: String,
            @Body donation: WildrModel
    ): Call<WildrWrapper>
}

