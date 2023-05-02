package com.example.payparking.activity

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.payparking.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PaymentActivity : AppCompatActivity() {

    lateinit var titleName: String

    lateinit var costOfService: EditText
    lateinit var howWasService: TextView
    lateinit var tipResult: TextView

    lateinit var tipOptions: RadioGroup
    lateinit var optionOne: RadioButton
    lateinit var optionTwo: RadioButton
    lateinit var btnMakePayment: Button

    lateinit var radioResult: RadioButton


    private lateinit var database: DatabaseReference


    // Razor pay


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paymet)

        database = Firebase.database.reference

        tipOptions = findViewById(R.id.tip_options)
        optionOne = findViewById(R.id.option_one)
        optionTwo = findViewById(R.id.option_two)
        btnMakePayment = findViewById(R.id.btnMakePayment)


        costOfService = findViewById(R.id.cost_of_service)
        howWasService = findViewById(R.id.service_question)
        tipResult = findViewById(R.id.tip_result)

        if (intent != null) {
            val payTitle = intent.getStringExtra("title").toString()
            titleName = payTitle
            howWasService.text = titleName
        }


        database.child("markers").child(titleName).get().addOnSuccessListener {

            if (it.exists()) {

                tipResult.text = it.child("title").value.toString()

            } else {

                Toast.makeText(this, "Error in success", Toast.LENGTH_SHORT).show()
            }

        }.addOnFailureListener {

        } // End of database fetch









        btnMakePayment.setOnClickListener {

            val id = tipOptions.checkedRadioButtonId
            radioResult = findViewById(id)

            tipResult.setText("Selected : " + radioResult.text)

            costOfService.hint = radioResult.text




        }


    }




}