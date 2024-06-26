package ar.edu.disneywiki.Model

import com.google.gson.annotations.SerializedName

data class Personaje(
    var _id: Int,
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
    @SerializedName("fav", alternate = ["is_fav", "isfavorite", ""])
    var fav: Boolean = false
    ){
    constructor() : this(
        0,
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        emptyList(),
        "",
        "",
        "",
        ""
    )
}
