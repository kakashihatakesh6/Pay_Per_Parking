package com.example.payparking.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.payparking.ForgetPassActivity
import com.example.payparking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    lateinit var etLoginUsername: EditText
    lateinit var etLoginPassword: EditText
    lateinit var txtForgotPassword: TextView
    lateinit var btnLogin: Button
    lateinit var txtBtnRegister: TextView

    private lateinit var dbRef: DatabaseReference

    // New Changes
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etLoginUsername = findViewById(R.id.etLoginUsername)
        etLoginPassword = findViewById(R.id.etLoginPassword)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtBtnRegister = findViewById(R.id.txtBtnRegister)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        // dbRef = Firebase.database.reference


        // Firebase Auth
        auth = Firebase.auth

        btnLogin.setOnClickListener {

            val validEmail = etLoginUsername.text.toString().trim()
            val validPassword = etLoginPassword.text.toString().trim()

            if (validEmail.isNotEmpty() && validPassword.isNotEmpty()){

                auth.signInWithEmailAndPassword(validEmail, validPassword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            updateUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()
                            // updateUI(null)
                        }
                    }

            }else{
                Toast.makeText(this, "Please enter valid email & Password", Toast.LENGTH_SHORT).show()
            }


            // Sava on Realtime Db
           // saveRDB()

        } //End of LoginBtn

        txtBtnRegister.setOnClickListener {

            val intent = Intent(this@Login, SignUp::class.java)
            startActivity(intent)
        } // End of BtnRegister

        txtForgotPassword.setOnClickListener {
           // updatePassword()
            val intent = Intent(this, PaymentActivity::class.java)
            startActivity(intent)
        } //End of ForgotPasseordBTn


    }

    private fun updatePassword() {
        TODO("Not yet implemented")
    }

    private fun saveRDB() {
        val username = etLoginUsername.text.toString()
        val password = etLoginPassword.text.toString()
        val employee = "Nikhil"

        dbRef.child(employee).get().addOnSuccessListener {

            if (it.exists()) {

                val validUser = it.child("empEmail").value.toString()
                val validPass = it.child("empPassword").value.toString()

                if ((username == validUser) && (password == validPass)) {

                    val intent = Intent(this, Navigation::class.java)
                    startActivity(intent)

                } else {

                    Toast.makeText(this, "Not Valid Fields", Toast.LENGTH_SHORT).show()

                }

            }

        }.addOnFailureListener {

            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()

        }
    }

    private fun updateUI() {

        val intent = Intent(this, Navigation::class.java)
        startActivity(intent)

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI()
        }
    }



}