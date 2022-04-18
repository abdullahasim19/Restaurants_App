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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigate=findViewById<Button>(R.id.buttonAdd)
        navigate!!.setOnClickListener {
            val i = Intent(this,AddData::class.java)
            startActivity(i)
        }

        initialize()
        val searchResult=findViewById<EditText>(R.id.searchBar)
        searchResult.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

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
        setRecyclerview(result!!)
    }
    fun setRecyclerview(result:List<Restaurant>)
    {

        adapter=CustomAdapter(result)

        recycler.layoutManager=LinearLayoutManager(this)
        recycler.adapter=adapter
    }


    override fun onResume() {
        super.onResume()
        if(check==0)
        {
            check=1
            return
        }
        val temp=result
        result=database!!.restaurantdao().getRestaurants()
        if(temp!!.size!=result!!.size) {
            adapter!!.filterlist(result!!)
        }

    }
}