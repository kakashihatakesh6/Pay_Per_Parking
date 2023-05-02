package com.example.payparking.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.payparking.R
import com.example.payparking.activity.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfileFragment : Fragment() {

    private lateinit var dbRef: DatabaseReference

    lateinit var txtUserName: TextView
    lateinit var txtUserEmail: TextView
    lateinit var btnBackToHome: Button

    lateinit var rlAccountSettings: RelativeLayout

    private lateinit var database: DatabaseReference


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        dbRef = FirebaseDatabase.getInstance().getReference("Employees")
        //showingData()

        // Database
        database = Firebase.database.reference

        txtUserName = view.findViewById(R.id.txtProfileName)
        txtUserEmail = view.findViewById(R.id.txtProfileEmail)
        btnBackToHome = view.findViewById(R.id.btnBackToHome)
        rlAccountSettings = view.findViewById(R.id.rlAccountSettings)

        btnBackToHome.setOnClickListener {

            Firebase.auth.signOut()
            val intent = Intent(requireActivity(), Login::class.java)
            startActivity(intent)

        }

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).get().addOnSuccessListener {

            if (it.exists()){

                val reqUserName = it.child("name").value.toString()
                val reqEmail = it.child("email").value.toString()
               // val reqPhone = it.child("empPhone").value.toString()

                txtUserName.text = reqUserName
                txtUserEmail.text = reqEmail

            }
            else{
                Toast.makeText(requireActivity(), "Data Does not exist", Toast.LENGTH_LONG).show()
            }



        }.addOnFailureListener {

            Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()

        }

        rlAccountSettings.setOnClickListener{

            replaceFragment(SettingsFragment())



        }


        return view

    } // End of Oncreate View

    private fun replaceFragment(fragment: Fragment) {

        val transaction = requireActivity().supportFragmentManager
            .beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

    }

    private fun showingData(){

        val employee = "Nikhil"

        dbRef.child(employee).get().addOnSuccessListener {

            if (it.exists()){

                val reqUserName = it.child("empName").value
                val reqEmail = it.child("empEmail").value
                val reqPhone = it.child("empPhone").value

                txtUserName.text = reqUserName.toString()
                txtUserEmail.text = reqEmail.toString()


            }
            else{
                Toast.makeText(requireActivity(), "Data Does not exist", Toast.LENGTH_LONG).show()
            }

        }.addOnFailureListener {

            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()

        }
    }  // End of Showing Data


}