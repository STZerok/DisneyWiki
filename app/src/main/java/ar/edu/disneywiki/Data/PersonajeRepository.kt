package ar.edu.disneywiki.Data

import android.content.Context
import android.util.Log
import ar.edu.disneywiki.Model.Personaje

class PersonajeRepository {
    private val personajeds = PersonajeDataSource()

    suspend fun getPersonajes(context: Context) : ArrayList<Personaje>{
        return personajeds.getPersonajes(context)
    }
    suspend fun getPersonaje(id:Int,context: Context) : Personaje? {
        return personajeds.getPersonaje(id,context)
    }
    suspend fun guardarPersonajeFav(id:Int,context: Context): Boolean{
        return personajeds.guardarPersonajeFav(id, context)
    }
    suspend fun getPersonajesFav(): ArrayList<Personaje>{
        return personajeds.getPersonajesFav()
    }

}

