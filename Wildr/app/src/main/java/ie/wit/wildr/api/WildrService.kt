package ie.wit.wildr.api

import ie.wit.wildr.models.WildrModel
import retrofit2.Call
import retrofit2.http.*

interface WildrService {
    @GET("/animals")
    fun findall(): Call<List<WildrModel>>

    @GET("/animals/{email}")
    fun findall(@Path("email") email: String?)
            : Call<List<WildrModel>>

    @GET("/animals/{email}/{id}")
    fun get(@Path("email") email: String?,
            @Path("id") id: String): Call<WildrModel>

    @DELETE("/animals/{email}/{id}")
    fun delete(@Path("email") email: String?,
               @Path("id") id: String): Call<WildrWrapper>

    @POST("/animals/{email}")
    fun post(@Path("email") email: String?,
             @Body animal: WildrModel)
            : Call<WildrWrapper>

    @PUT("/animals/{email}/{id}")
    fun put(@Path("email") email: String?,
            @Path("id") id: String,
            @Body animal: WildrModel
    ): Call<WildrWrapper>
}