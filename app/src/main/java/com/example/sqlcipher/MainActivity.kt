
package com.example.sqlcipher

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.sqlcipher.databinding.ActivityMainBinding
import net.zetetic.database.sqlcipher.SQLiteDatabase

class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding
    private lateinit var dbHelper: DbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        System.loadLibrary("sqlcipher")
        dbHelper = DbHelper.getInstance(this.application)

        binding.addData.setOnClickListener {
            val db: SQLiteDatabase = dbHelper.writableDatabase
            insertData(db,binding.et1.text.toString())
            binding.et1.text?.clear()
            readData(dbHelper.readableDatabase)
        }

        binding.viewData.setOnClickListener {
            readData(dbHelper.readableDatabase)
        }

    }

    fun insertData(db:SQLiteDatabase,input:String){
        val cv = ContentValues()
        cv.put("name",input)
        val res  = db.insert("emp",null,cv)
        Toast.makeText(this, "Inserted Data Id - $res", Toast.LENGTH_SHORT).show()
    }

    fun readData(db: SQLiteDatabase)
    {
        val data =  mutableListOf<String>()
        val cursor = db.rawQuery("Select * from emp",null)
        cursor?.use {
            if (it.moveToFirst()){
                do {
                    data.add(it.getString(1))
                }while (it.moveToNext())
            }
        }
        val arrayAdapter = ArrayAdapter<String>(this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,data)
        binding.listView.adapter = arrayAdapter
    }

}