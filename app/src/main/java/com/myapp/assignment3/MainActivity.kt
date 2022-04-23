package com.myapp.assignment3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigate=findViewById<Button>(R.id.buttonAdd)
        navigate!!.setOnClickListener {
            val i = Intent(this,AddData::class.java)
            startActivityForResult(i,1)

        }

        initialize()
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
    fun filter(text:String)
    {
        if(result==null)
            return

        var filterArray=ArrayList<Restaurant>()
        for (item in result!!)
        {
            if(item.name.toLowerCase().contains(text.toLowerCase()))
            {
                filterArray.add(item)
            }
        }
        var filterlist=filterArray.toList()
        adapter!!.filterlist(filterlist)
    }
    fun initialize()
    {
        recycler=findViewById<RecyclerView>(R.id.recycler)
        database=RestaurantDatabase.getDatabase(this)
        result=database!!.restaurantdao().getRestaurants()
        fullrestaurants=result!!.toList()
        setRecyclerview(result!!)
    }
    fun setRecyclerview(result:List<Restaurant>)
    {

        adapter=CustomAdapter(result,fullrestaurants)

        recycler.layoutManager=LinearLayoutManager(this)
        recycler.adapter=adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==1)
        {
            if(resultCode== RESULT_OK)
            {

                result=database!!.restaurantdao().getRestaurants()
                if(fullrestaurants.size==result!!.size)
                    return
                fullrestaurants=result!!.toList()
                adapter= CustomAdapter(result!!,fullrestaurants)
                recycler.adapter=adapter
                //Log.d("Meow",result!!.size.toString())
                //adapter!!.filterlist(result!!)
            }
        }
    }


}