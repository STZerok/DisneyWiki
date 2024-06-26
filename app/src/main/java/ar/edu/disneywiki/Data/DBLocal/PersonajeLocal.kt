package ar.edu.disneywiki.Data.DBLocal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "personajes")
data class PersonajeLocal (
    @PrimaryKey var _id: Int,
    var films: List<String>,
    var shortFilms: List<String>,
    var tvShows: List<String>,
    var videoGames: List<String>,
    var parkAttractions: List<String>,
    var allies: List<String>,
    var enemies: List<String>,
    var sourceUrl: String,
    var name: String,
    var imageUrl: String,
    var createdAt: String,
    var isFav: Boolean = false
)
