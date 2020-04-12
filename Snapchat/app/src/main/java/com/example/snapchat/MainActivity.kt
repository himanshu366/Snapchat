package com.example.snapchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import jdk.nashorn.internal.runtime.ECMAException.getException
import com.google.firebase.auth.FirebaseUser
import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.firebase.auth.AuthResult
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import android.R.attr.password
import android.support.v4.app.SupportActivity
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import sun.jvm.hotspot.utilities.IntArray



class MainActivity : AppCompatActivity() {

    var emailEditText: EditText? = null
    var passwordEditText: EditText? = null
    val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        if (mAuth.currentUser != null)
        {
            logIn()
        }

    }

    fun goClicked(view: View) {
        mAuth.signInWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    logIn()
                } else {
                    mAuth.createUserWithEmailAndPassword(emailEditText?.text.toString(), passwordEditText?.text.toString()).addOnCompleteListener(this) {
                        task ->
                        if (task.isSuccessful) {
                            logIn()
                        } else {
                            Toast.makeText(this, "Login failed, try again", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
    }
    fun logIn() {

    }

}
