package com.example.movieapp.LocalDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [FilmItemModelLocal::class],version = 2)
abstract class LocalDatabase:RoomDatabase() {
    abstract fun localDao():LocalDAO


    companion object{


        @Volatile private var instance :LocalDatabase?=null

        fun getInstance(context:Context) :LocalDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(
                    context.applicationContext,
                    LocalDatabase::class.java,
                    "localdata"
                ).build().also { instance=it }
            }
        }
    }
}