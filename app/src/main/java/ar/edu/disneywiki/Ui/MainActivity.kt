package ar.edu.disneywiki.Ui

import android.content.Intent

import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ar.edu.disneywiki.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var rvPersonajes: RecyclerView
    private lateinit var adapter: PersonajeAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var buttonFav: Button
    private lateinit var buscador: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        buscador = findViewById(R.id.buscador)
        buscador.requestFocus()
        rvPersonajes = findViewById(R.id.rvPersonajes)
        buttonFav = findViewById(R.id.buttonFav)
        val layoutManager = GridLayoutManager(this, 3)
        rvPersonajes.layoutManager = layoutManager

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        adapter = PersonajeAdapter()
        rvPersonajes.adapter = adapter
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.personajes.observe(this){
            adapter.Update(it)
        }
        viewModel.init(this)

        buttonFav.setOnClickListener {
            val intent = Intent(this, FavActivity::class.java)
            startActivity(intent)

        }
        buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(newText)
                return true
            }
        })

    }
    private fun checkUser(){
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }





}