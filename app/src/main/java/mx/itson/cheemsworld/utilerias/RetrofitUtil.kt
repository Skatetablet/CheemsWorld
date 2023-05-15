package mx.itson.cheemsworld.utilerias

import com.google.gson.GsonBuilder
import mx.itson.cheemsworld.interfaces.CheemsAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitUtil {
    fun getApi() : CheemsAPI {
        val gson = GsonBuilder().create()
        val retrofit = Retrofit.Builder().baseUrl("http://192.168.50.236:8888/cheems/public/api/").addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create(CheemsAPI::class.java)
    }

}