package com.myapp.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class AddData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        val btn=findViewById<Button>(R.id.buttonInsert)
        setResult(RESULT_OK)
        btn.setOnClickListener {
            val name=findViewById<EditText>(R.id.restaurant_name).text.toString()
            val location=findViewById<EditText>(R.id.retaurant_location).text.toString()
            val phone=findViewById<EditText>(R.id.restaurant_phone).text.toString()
            val description=findViewById<EditText>(R.id.restaurant_description).text.toString()
            val rating=findViewById<EditText>(R.id.restaurant_rating).text.toString()
            var flag=0
            if(name.isEmpty())
            {
                Toast.makeText(this,"Please enter Name",Toast.LENGTH_SHORT).show()
                flag=1
            }
            if(location.isEmpty())
            {
                Toast.makeText(this,"Please enter Location",Toast.LENGTH_SHORT).show()
                flag=1
            }
            if(phone.isEmpty())
            {
                Toast.makeText(this,"Please enter Phone",Toast.LENGTH_SHORT).show()
                flag=1
            }
            if(description.isEmpty())
            {
                Toast.makeText(this,"Please enter Description",Toast.LENGTH_SHORT).show()
                flag=1
            }
            if(rating.isEmpty())
            {
                Toast.makeText(this,"Please enter Rating",Toast.LENGTH_SHORT).show()
                flag=1
            }
            if(flag==1)
                return@setOnClickListener


            try {
                var database=RestaurantDatabase.getDatabase(this)
                database.restaurantdao().insertRestaurant(Restaurant(name,location,phone,description, rating))
                showDialog()
            }
            catch (e:Exception) {
                Log.d("Error", e.message.toString())
            }

        }
        val btnFinish=findViewById<Button>(R.id.btnEnd)
        btnFinish.setOnClickListener {
            finish()
        }
    }
    fun showDialog()
    {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle("Restaurant Added")
        //set message for alert dialog
        builder.setMessage("Do you want to add more restaurants?")

        //performing positive action
        builder.setPositiveButton("Yes"){dialogInterface, which ->

        }

        //performing negative action
        builder.setNegativeButton("No"){dialogInterface, which ->
            finish()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }
}


