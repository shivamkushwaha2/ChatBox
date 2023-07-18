package com.example.chatbox

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth



    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val edEmail:EditText = findViewById(R.id.editTextEmail)
        val edPassword:EditText = findViewById(R.id.editTextPassword)
        val login:Button = findViewById(R.id.cirLoginButton)


        mAuth = FirebaseAuth.getInstance()

        login.setOnClickListener {
            val email = edEmail.text.toString()
            val pswrd = edPassword.text.toString()
            login(email,pswrd)
        }

        val textView : TextView = findViewById(R.id.signUptxt)
        textView.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    private fun login(email: String, pswrd: String) {
        mAuth.signInWithEmailAndPassword(email, pswrd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}