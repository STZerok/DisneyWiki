package ar.edu.disneywiki.Ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ar.edu.disneywiki.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var loginButton: ImageButton
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var logoDisney: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loginButton = findViewById(R.id.buttonLogin)
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        logoDisney = findViewById(R.id.imageView)
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
        loginButton.setOnClickListener{

            val intent = googleSignInClient.signInIntent
            startActivityForResult(intent,100)


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            }catch (e:Exception){
                Log.e("disneyWikiTest","onactivityResult: ${e.message}")
            }
        }
    }
    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(account!!.idToken,null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val uid = firebaseUser!!.uid
                val email = firebaseUser!!.email
                if (authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this@LoginActivity,"Cuenta creada...",Toast.LENGTH_SHORT).show()

                }else{
                    Toast.makeText(this@LoginActivity,"Bienvenido ${email}",Toast.LENGTH_SHORT).show()

                }
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@LoginActivity,"Login fallido ${e.message}",Toast.LENGTH_SHORT).show()
            }

    }
}