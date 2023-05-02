package com.example.payparking.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.room.util.query
import com.example.payparking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SettingsFragment : Fragment() {

    lateinit var txtUserName: TextView
    lateinit var txtEmail: TextView
    lateinit var txtPhone: TextView

    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        txtUserName = view.findViewById(R.id.txtUserName)
        txtEmail = view.findViewById(R.id.txtUserEmail)
        txtPhone = view.findViewById(R.id.txtUserPhone)

        database = Firebase.database.reference

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        database.child("user").child(userId).get().addOnSuccessListener {

            if (it.exists()){

                val userName = it.child("name").value.toString()
                val userEmail = it.child("email").value.toString()
                val userPhone = it.child("phone").value.toString()

                txtUserName.text = userName
                txtEmail.text = userEmail
                txtPhone.text = userPhone

            }else{

            }

        }.addOnFailureListener {
            Toast.makeText(requireActivity(),it.toString(),Toast.LENGTH_SHORT).show()
        }











     //






        return view
    }


}