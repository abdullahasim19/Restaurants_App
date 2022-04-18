package com.myapp.assignment3

import android.accounts.AuthenticatorDescription
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Restaurant")
class Restaurant(

    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "location")
    var location:String,
    @ColumnInfo(name="phone")
    var phone:String,
    @ColumnInfo(name="description")
    var description:String,
    @ColumnInfo(name="rating")
    var rating:String){
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
    fun setid(id:Long)
    {
        this.id=id
    }

}


