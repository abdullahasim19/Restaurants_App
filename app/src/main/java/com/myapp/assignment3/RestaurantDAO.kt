package com.myapp.assignment3

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDAO {

    @Insert
    fun insertRestaurant(restaurant: Restaurant)

    @Delete
    fun deleteRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM Restaurant")
    fun getRestaurants():List<Restaurant>
}