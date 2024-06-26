package ar.edu.disneywiki.Ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.disneywiki.Data.PersonajeRepository
import ar.edu.disneywiki.Model.Personaje
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class PersonajeViewModel : ViewModel() {
    val persoRepo : PersonajeRepository = PersonajeRepository()
    var personaje : MutableLiveData<Personaje> = MutableLiveData<Personaje>()
    var resultado : Boolean = false
    private val coroutineContext: CoroutineContext = newSingleThreadContext("pers")
    private val scope = CoroutineScope(coroutineContext)
    fun init(id : Int,context: Context){
        scope.launch{
            kotlin.runCatching {
                persoRepo.getPersonaje(id,context)
            }.onSuccess {
                Log.d("disneyWikiTest","onsucces personajeactivity")
                personaje.postValue(it ?: Personaje())
                Log.d("disneyWikiTest", it.toString())
            }.onFailure {
                Log.e("disneyWikiTest","Error personaje " + it)
                val perso = Personaje()
                perso.name = "Error"
                personaje.postValue(Personaje())
            }
        }
    }
    suspend fun guardarPersonajeFav(id: Int,context: Context): Boolean {
        return withContext(Dispatchers.IO) {
            persoRepo.guardarPersonajeFav(id,context)
        }
    }
}