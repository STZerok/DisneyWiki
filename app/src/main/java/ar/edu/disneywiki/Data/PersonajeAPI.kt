package ar.edu.disneywiki.Data


import ar.edu.disneywiki.Model.ApiResponse
import ar.edu.disneywiki.Model.ApiResponsePersonaje
import ar.edu.disneywiki.Model.Personaje
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PersonajeAPI {
    @GET("character")
    fun getPersonajes(): Call<ApiResponse>

    @GET("character/{id}")
    fun getPersonaje(@Path("id") id:Int): Call<ApiResponsePersonaje>
}



