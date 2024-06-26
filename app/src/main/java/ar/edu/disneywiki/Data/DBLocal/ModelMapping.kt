package ar.edu.disneywiki.Data.DBLocal

import ar.edu.disneywiki.Model.Personaje

fun PersonajeLocal.toPersonaje() = Personaje(
    _id,
    films ?: emptyList(),
    shortFilms ?: emptyList(),
    tvShows ?: emptyList(),
    videoGames ?: emptyList(),
    parkAttractions ?: emptyList(),
    allies ?: emptyList(),
    enemies ?: emptyList(),
    sourceUrl,
    name,
    imageUrl,
    createdAt
)
fun List<PersonajeLocal>.toPersonajeList() = map(PersonajeLocal::toPersonaje) as ArrayList<Personaje>


fun Personaje.toPersonajeLocal() = PersonajeLocal(
    _id,
    films,
    shortFilms,
    tvShows,
    videoGames,
    parkAttractions,
    allies,
    enemies,
    sourceUrl,
    name,
    imageUrl,
    createdAt
)
fun List<Personaje>.toPersonajeLocalList() = map(Personaje::toPersonajeLocal) as ArrayList<PersonajeLocal>



