
package ar.edu.disneywiki.Ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ar.edu.disneywiki.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class PersonajeActivity : AppCompatActivity() {
    lateinit var name2: TextView
    lateinit var vm: PersonajeViewModel
    lateinit var imgPersonaje: ImageView
    lateinit var pb: ProgressBar
    lateinit var peliculas: TextView
    lateinit var cortometraje: TextView
    lateinit var tv: TextView
    lateinit var juegos: TextView
    lateinit var parque: TextView
    lateinit var aliados: TextView
    lateinit var enemigos: TextView
    lateinit var buttonFav: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personaje)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vm = ViewModelProvider(this)[PersonajeViewModel::class.java]
        name2 = findViewById(R.id.txtName2)
        imgPersonaje = findViewById(R.id.imageViewPersonaje)
        pb = findViewById(R.id.progressBar2)
        peliculas = findViewById(R.id.textViewPeliculas)
        cortometraje = findViewById(R.id.textViewCortometraje)
        tv = findViewById(R.id.textViewTv)
        juegos = findViewById(R.id.textViewJuegos)
        parque = findViewById(R.id.textViewParque)
        aliados = findViewById(R.id.textViewAliados)
        enemigos = findViewById(R.id.textViewEnemigos)
        buttonFav = findViewById(R.id.buttonFav)
        val id = intent.getIntExtra("id", -1)!!
        Log.d("disneyWikiTest", "ID recibido en PersonajeActivity: $id")

        vm.personaje.observe(this) {
            buttonFav.setImageResource(if (it.fav) R.drawable.favorito1 else R.drawable.no_favorito)
            name2.text = it.name
            peliculas.text = it.films.joinToString(", ")
            cortometraje.text = it.shortFilms.joinToString(", ")
            tv.text = it.tvShows.joinToString(", ")
            juegos.text = it.videoGames.joinToString(", ")
            parque.text = it.parkAttractions.joinToString(", ")
            aliados.text = it.allies.joinToString(", ")
            enemigos.text = it.enemies.joinToString(", ")
            Picasso.get()
                .load(it.imageUrl)
                .placeholder(R.drawable.placeholder_image)
                .into(imgPersonaje)
            pb.visibility = View.INVISIBLE
        }

        val favoritoDrawable = ContextCompat.getDrawable(this, R.drawable.favorito1)
        val noFavoritoDrawable = ContextCompat.getDrawable(this, R.drawable.no_favorito)

        buttonFav.setOnClickListener {
            lifecycleScope.launch {
                val resultado = vm.guardarPersonajeFav(id, this@PersonajeActivity)
                Log.d("disneyWikiTest", "Resultado de guardarPersonajeFav: $resultado")
                if (resultado) {
                    mostrarMensaje("Personaje agregado a favoritos")
                    actualizarIcono(buttonFav, favoritoDrawable)
                } else {
                    mostrarMensaje("Personaje quitado")
                    actualizarIcono(buttonFav, noFavoritoDrawable)
                }
            }


        }

        pb.visibility = View.VISIBLE
        vm.init(id, this)
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this@PersonajeActivity, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun actualizarIcono(button: ImageButton, drawable: Drawable?) {
        button.setImageDrawable(drawable)
    }
}

