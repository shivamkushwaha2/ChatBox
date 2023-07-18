package com.example.chatbox

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var register: Button
    lateinit var edEmail: EditText
    lateinit var edPassword: EditText
    lateinit var name: EditText
    lateinit var dbRef:DatabaseReference

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
        setContentView(R.layout.activity_sign_up)
        
        textView = findViewById(R.id.logintextview)
        register = findViewById(R.id.regButton)
        mAuth = FirebaseAuth.getInstance()
        edEmail = findViewById(R.id.email)
        edPassword= findViewById(R.id.pswrd)
        name= findViewById(R.id.name)


        textView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {
            val email = edEmail.text.toString()
            val user_name = name.text.toString()
            val pswrd = edPassword.text.toString()
            signUp(user_name, email,pswrd)
        }
        
    }

    private fun signUp(name:String, email: String, pswrd: String) {
        mAuth.createUserWithEmailAndPassword(email, pswrd)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "createUserWithEmail:success")
                    val uid = mAuth.currentUser?.uid!!
                    addUsertoDatabase(name,email,uid)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun addUsertoDatabase(name: String, email: String,uid: String) {
    dbRef = FirebaseDatabase.getInstance().getReference()
        dbRef.child("user").child(uid).setValue( User(name,email, uid))
    }
}