package ar.edu.disneywiki.Data.DBLocal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
@Database(entities = [PersonajeLocal::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun PersonajeDAO() : PersonajeDAO
    companion object{
        @Volatile
        private var _instance:AppDataBase? =null
        fun getInstance(context: Context): AppDataBase = _instance ?: synchronized(this){
            _instance ?:buildDatabase(context).also { _instance = it }
        }
        private fun buildDatabase(context: Context): AppDataBase = Room.databaseBuilder(context, AppDataBase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

        suspend fun clean(context: Context) = coroutineScope {
            launch(Dispatchers.IO) {
                getInstance(context).clearAllTables()
            }
        }
    }


}