package com.example.payparking.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.payparking.R
import com.example.payparking.model.EmployeeModel
import com.example.payparking.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUp : AppCompatActivity() {  //End of Class


    lateinit var etFullName: EditText
    lateinit var etSignUpEmail: EditText
    lateinit var etSignUpPhone: EditText
    lateinit var etSignUpPassword: EditText
    lateinit var btnSignUp: Button
    lateinit var txtGoToLogin: TextView

    private lateinit var dbRef: DatabaseReference

    // New Changes
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etFullName = findViewById(R.id.etFullName)
        etSignUpEmail = findViewById(R.id.etSignUpEmail)
        etSignUpPhone = findViewById(R.id.etSignUpPhone)
        etSignUpPassword = findViewById(R.id.etSignUpPassword)
        btnSignUp = findViewById(R.id.btnSignUp)
        txtGoToLogin = findViewById(R.id.txtGoToLogin)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Database
        database = Firebase.database.reference



        btnSignUp.setOnClickListener {

            val inEmail = etSignUpEmail.text.toString().trim()
            val inPassword = etSignUpPassword.text.toString().trim()

            auth.createUserWithEmailAndPassword(inEmail, inPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = auth.currentUser
                        saveData()
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                        //updateUI(null)
                    }
                }


        }



        txtGoToLogin.setOnClickListener {
            val intent = Intent(this@SignUp, Login::class.java)
            startActivity(intent)
        }


    }  //End of OnCreate

    private fun updateUI(user: FirebaseUser?) {
        val intent = Intent(this, Navigation::class.java)
        startActivity(intent)

    }

    private fun saveData() {

        val inName = etFullName.text.toString().trim()
        val inEmail = etSignUpEmail.text.toString().trim()
        val inPhone = etSignUpPhone.text.toString().trim()
        val inPassword = etSignUpPassword.text.toString().trim()

        val user = UserModel(inName, inEmail, inPhone, inPassword)

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).setValue(user)


    }

    fun saveSignUpData() {
        val fullname = etFullName.text.toString()
        val email = etSignUpEmail.text.toString()
        val phone = etSignUpPhone.text.toString()
        val password = etSignUpPassword.text.toString()

        if (fullname.isNotEmpty()) {

            if (email.isNotEmpty()) {

                if (phone.isNotEmpty()) {

                    if (password.isNotEmpty()) {

                        val empId = fullname

                        val employee = EmployeeModel(empId, fullname, email, phone, password)

                        dbRef.child(empId).setValue(employee)
                            .addOnCompleteListener {
                                Toast.makeText(this, "Registered Successfully", Toast.LENGTH_LONG)
                                    .show()
                            }.addOnFailureListener { err ->
                                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }

                    }

                }
            }

        }
    } // End of saveSignUpData()

    private fun saveUserData() {

        //Getting Inputs
        val fullName = etFullName.text.toString()
        val email = etSignUpEmail.text.toString()
        val phone = etSignUpPhone.text.toString()
        val password = etSignUpPassword.text.toString()

        if (fullName.isEmpty()) {
            etFullName.error = "Full name is required"
        }
        if (email.isEmpty()) {
            etSignUpEmail.error = "Please Enter Email"
        }
        if (phone.isEmpty()) {
            etSignUpPhone.error = "Please Enter Mobile Number"
        }
        if (password.isEmpty()) {
            etSignUpPassword.error = "Please Enter Password"
        }

        if (fullName.isNotEmpty()) {

            if (email.isNotEmpty()) {

                if (phone.isNotEmpty()) {

                    if (password.isNotEmpty()) {

                        val empId = dbRef.push().key!!

                        val employee = EmployeeModel(empId, fullName, email, phone, password)

                        dbRef.child(empId).setValue(employee)
                            .addOnCompleteListener {
                                Toast.makeText(
                                    this,
                                    "Registered Successfully",
                                    Toast.LENGTH_LONG
                                ).show()
                            }.addOnFailureListener { err ->
                                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT)
                                    .show()
                            }

                    }

                }
            }
        }

    }  // End of UserData()

    fun guestOnClick(view: View) {

        val intent = Intent(this, Navigation::class.java)
        startActivity(intent)

    }

}