package mx.itson.cheemsworld.interfaces

import mx.itson.cheemsworld.entidades.Visita
import retrofit2.http.GET
import retrofit2.Call

interface CheemsAPI {

    @GET("visitas")
    fun getVisitas(): Call<List<Visita>>
}