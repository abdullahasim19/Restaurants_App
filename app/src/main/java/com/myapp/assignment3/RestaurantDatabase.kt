package com.myapp.assignment3

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Restaurant::class], version = 1)
abstract class RestaurantDatabase:RoomDatabase() {

    abstract fun restaurantdao():RestaurantDAO
    //singleton
    companion object{
        @Volatile
        private var INSTANCE:RestaurantDatabase?=null

        fun getDatabase(context: Context):RestaurantDatabase{
            if(INSTANCE==null)
            {
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(
                        context.applicationContext,RestaurantDatabase::class.java,"restaurant_db").allowMainThreadQueries().build()
                }
            }
            return  INSTANCE!!
        }

    }
}