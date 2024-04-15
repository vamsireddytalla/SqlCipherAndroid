package com.example.sqlcipher

import android.app.Application
import android.content.Context
import net.zetetic.database.sqlcipher.SQLiteDatabase
import net.zetetic.database.sqlcipher.SQLiteOpenHelper

class DbHelper constructor(context:Application) : SQLiteOpenHelper(context, DATABASE_NAME,"reddy",null, DATABASE_VERSION,
        1,null,null,false) {


        companion object{
            private const val DATABASE_NAME = "vamsi.db"
            private const val DATABASE_VERSION = 1

            @Volatile
            private var INSTANCE: DbHelper? = null

            fun getInstance(application: Application): DbHelper {
                return INSTANCE ?: synchronized(this) {
                    INSTANCE ?: DbHelper(application).also { INSTANCE = it }
                }
            }

        }


    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL("CREATE TABLE emp (id INTEGER PRIMARY KEY,name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Handle database upgrades
        db?.execSQL("DROP TABLE IF EXISTS emp")
        onCreate(db)
    }

}