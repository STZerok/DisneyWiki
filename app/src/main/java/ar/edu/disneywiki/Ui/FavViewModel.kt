package ar.edu.disneywiki.Ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.disneywiki.Data.PersonajeRepository
import ar.edu.disneywiki.Model.Personaje
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.CoroutineContext

class FavViewModel: ViewModel() {
    val persoRepo : PersonajeRepository = PersonajeRepository()
    var personajes : MutableLiveData<ArrayList<Personaje>> = MutableLiveData<ArrayList<Personaje>>()
    private val coroutineContext: CoroutineContext = newSingleThreadContext("pers")
    private val scope = CoroutineScope(coroutineContext)
    fun init(context : Context){
        scope.launch{
            kotlin.runCatching {
                persoRepo.getPersonajesFav()
            }.onSuccess {
                personajes.postValue(it)
            }.onFailure {
                personajes.postValue(ArrayList<Personaje>())
            }
        }
    }


}