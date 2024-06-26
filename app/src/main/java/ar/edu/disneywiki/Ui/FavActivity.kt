package ar.edu.disneywiki.Ui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.disneywiki.R

class FavActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: FavViewModel
    private lateinit var adapter: PersonajeAdapter
    lateinit var txtfav: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.rvPersonajes)
        txtfav = findViewById(R.id.txtNoFav)
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager
        adapter = PersonajeAdapter()
        recyclerView.adapter = adapter
        viewModel = ViewModelProvider(this)[FavViewModel::class.java]
        viewModel.personajes.observe(this){
            adapter.Update(it)
            txtfav.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
        }
        viewModel.init(this)


    }
    override fun onResume() {
        super.onResume()
        viewModel.init(this)
    }
}

