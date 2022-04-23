package com.myapp.assignment3

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(var restaurants:List<Restaurant>,var restaurantsFull: List<Restaurant>):RecyclerView.Adapter<CustomAdapter.ViewHolder>(),Filterable {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.restaurant_ui,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data=restaurants[position]
        holder.name.text=data.name
        holder.location.text=data.location
        holder.phone.text=data.phone
        holder.description.text=data.description
        holder.rating.text=data.rating
    }

    override fun getItemCount(): Int {
       // Log.d("Meow",restaurants.size.toString())
        return restaurants.size
    }
    fun filterlist(rlist:List<Restaurant>)
    {
        restaurants=rlist
        notifyDataSetChanged()
    }

    class ViewHolder(ItemView:View):RecyclerView.ViewHolder(ItemView){
        val name=ItemView.findViewById<TextView>(R.id.restaurant_name)
        val location=ItemView.findViewById<TextView>(R.id.retaurant_location)
        val phone=ItemView.findViewById<TextView>(R.id.restaurant_phone)
        val description=ItemView.findViewById<TextView>(R.id.restaurant_description)
        val rating=ItemView.findViewById<TextView>(R.id.restaurant_rating)

    }
    var restaurantsFilter=object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var tempList=ArrayList<Restaurant>()
            var filteredList:List<Restaurant>
            if(constraint==null || constraint.length==0)
            {
                filteredList=restaurantsFull.toList()
            }
            else
            {
                var pattern=constraint.toString().toLowerCase().trim()
                for (item:Restaurant in restaurantsFull)
                {
                    if(item.name.toLowerCase().contains(pattern))
                    {
                        tempList.add(item)
                    }
                }
                filteredList=tempList.toList()
            }

            var results=FilterResults()
            results.values=filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
           var temps=results!!.values as List<Restaurant>
            restaurants=temps.toList()
            notifyDataSetChanged()
        }

    }
    override fun getFilter(): Filter {
        return restaurantsFilter
    }
}