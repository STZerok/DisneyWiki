package ar.edu.disneywiki.Data.DBLocal

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PersonajeDAO  {
    @Query("SELECT * FROM personajes")
    fun getPersonajes(): List<PersonajeLocal>

    @Query("SELECT * FROM personajes WHERE _id = :id")
    fun getPersonaje(id: Int): PersonajeLocal?

    @Query("SELECT * FROM personajes WHERE _id = :id")
    suspend fun getPersonajeById(id: Int): PersonajeLocal?

    @Query("UPDATE personajes SET isFav = :isFav WHERE _id = :id")
    suspend fun updateIsFav(id: Int, isFav: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPersonaje(vararg personaje: PersonajeLocal)

    @Delete
    fun deletePersonaje(personaje: PersonajeLocal)



}