package com.myapp.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var result:List<Restaurant>?=null
    lateinit var recycler:RecyclerView
    var adapter:CustomAdapter?=null
    var navigate:Button?=null
    var check:Int=0
    var database:RestaurantDatabase?=null
    lateinit var fullrestaurants:List<Restaurant>
    lateinit var spinnerFilter:Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigate=findViewById<Button>(R.id.buttonAdd)
        navigate!!.setOnClickListener {
            val i = Intent(this,AddData::class.java)
            startActivityForResult(i,1)

        }
        initialize()

    }

    private fun deleteAllRestaurants()
    {
        database=RestaurantDatabase.getDatabase(this)
        var id:Long=1
        result=database!!.restaurantdao().getRestaurants()

        for (item:Restaurant in result!!)
        {
            var temp=item
            item.setid(id)
            database!!.restaurantdao().deleteRestaurant(temp)
            id++
        }
    }
    private fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recycler)//recycler view
        spinnerFilter=findViewById<Spinner>(R.id.spinnerFilter)//spinner
        var arr=ArrayAdapter.createFromResource(
            this,R.array.filterings,android.R.layout.simple_spinner_item)//setting array adapter for spinner
        arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFilter.adapter=arr
        spinnerFilter.setOnItemSelectedListener(object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(adapter!=null)
                {
                    adapter!!.setSearchType(parent!!.getItemAtPosition(position).toString())
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        })
        database=RestaurantDatabase.getDatabase(this)//getting database instance
        result=database!!.restaurantdao().getRestaurants() //storing the result in result list
        fullrestaurants=result!!.toList()//making another copy of list

        adapter=CustomAdapter(result!!,fullrestaurants)
        recycler.layoutManager=LinearLayoutManager(this)
        recycler.adapter=adapter

        try {
            val searchResult=findViewById<SearchView>(R.id.searchBar)
            searchResult.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter!!.getFilter().filter(newText)
                    return true
                }

            })
        }
        catch (ex:Exception)
        {
            Toast.makeText(this,ex.message.toString(),Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1)
        {
            if(resultCode== RESULT_OK)
            {
                result=database!!.restaurantdao().getRestaurants()
                if(fullrestaurants.size==result!!.size)//if no item added then simply return
                    return
                fullrestaurants=result!!.toList()
                adapter!!.updateView(result!!,fullrestaurants)
            }
        }
    }

}