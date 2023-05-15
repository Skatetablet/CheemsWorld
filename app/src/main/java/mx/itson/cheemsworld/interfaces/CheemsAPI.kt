package mx.itson.cheemsworld.interfaces

import mx.itson.cheemsworld.entidades.Visita
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface CheemsAPI {

    @GET("visitas")
    fun getVisitas(): Call<List<Visita>>

    @POST("post")
    fun postVisita(@Body visita: Visita): Call<Void>

}