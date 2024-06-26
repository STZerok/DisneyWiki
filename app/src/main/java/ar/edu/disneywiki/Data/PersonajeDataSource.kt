
package ar.edu.disneywiki.Data

import android.content.Context
import ar.edu.disneywiki.Data.DBLocal.AppDataBase
import ar.edu.disneywiki.Data.DBLocal.toPersonaje
import ar.edu.disneywiki.Data.DBLocal.toPersonajeList
import ar.edu.disneywiki.Data.DBLocal.toPersonajeLocal
import ar.edu.disneywiki.Data.DBLocal.toPersonajeLocalList
import ar.edu.disneywiki.Model.Personaje
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PersonajeDataSource {
    private val API_BASE_URL = "https://api.disneyapi.dev/"
    private val api: PersonajeAPI
    private val db = FirebaseFirestore.getInstance()

    init {
        api = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory((GsonConverterFactory.create()))
            .build().create(PersonajeAPI::class.java)
    }

    suspend fun getPersonajes(context: Context): ArrayList<Personaje> {
        var dbLocal = AppDataBase.getInstance(context)
        var personajesLocal = dbLocal.PersonajeDAO().getPersonajes()
        if (personajesLocal.isNotEmpty()) {
            return personajesLocal.toPersonajeList()
        }
        val result = api.getPersonajes().execute()
        return if (result.isSuccessful) {
            val persList = result.body()?.data ?: ArrayList()
            if (persList.isNotEmpty()) {
                dbLocal.PersonajeDAO().insertPersonaje(*persList.toPersonajeLocalList().toTypedArray())
            }
            persList
        } else {
            ArrayList()
        }
    }

    suspend fun getPersonaje(id: Int, context: Context): Personaje? {
        var dbLocal = AppDataBase.getInstance(context)
        var personaje = dbLocal.PersonajeDAO().getPersonajeById(id)?.toPersonaje()
        if (personaje != null) {
            return personaje // Si lo encuentra en Room, lo devuelve
        }
        delay(1000)

        val result = api.getPersonaje(id).execute()
        return if (result.isSuccessful) {
            personaje = result.body()?.data
            personaje?.let {
                dbLocal.PersonajeDAO().insertPersonaje(it.toPersonajeLocal()) // Guarda en Room
            }
            personaje // Devuelve el personaje obtenido de la API
        } else {
            null // Devuelve null si hubo un error
        }
    }

    suspend fun guardarPersonajeFav(id: Int, context: Context): Boolean {
        return try {
            val documento = db.collection("Fav").document(id.toString()).get().await()
            if (documento.exists()) {
                actualizarFavoritoLocal(context, id, false)
                db.collection("Fav").document(id.toString()).delete().await()
                false // Se quitó de favoritos
            } else {
                val result = api.getPersonaje(id).execute()
                if (result.isSuccessful) {
                    result.body()?.data?.fav = true
                    db.collection("Fav").document(id.toString()).set(result.body()!!.data).await()
                    true // Se agregó a favoritos
                } else {
                    false // Error al obtener el personaje de la API
                }
            }
        } catch (e: Exception) {
            false // Error al acceder a Firebase
        }
    }

    private suspend fun actualizarFavoritoLocal(context: Context, id: Int, isFav: Boolean) {
        val dbLocal = AppDataBase.getInstance(context)
        dbLocal.PersonajeDAO().updateIsFav(id, isFav)
    }

    suspend fun getPersonajesFav(): ArrayList<Personaje> {
        return suspendCoroutine { continuation ->
            db.collection("Fav").get()
                .addOnSuccessListener { documentos ->
                    val personajes = documentos.mapNotNull { documento ->
                        documento.toObject(Personaje::class.java)
                    } as ArrayList<Personaje>
                    continuation.resume(personajes)
                }
                .addOnFailureListener { exception ->
                    continuation.resume(arrayListOf())
                }
        }
    }
}
