package com.idtk.mylink

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

class MainActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Toast.makeText(this, "toast", Toast.LENGTH_SHORT)

        Log.d("tag", "msg")
        val a = ABean()
        a.name = "111"
        a.list

    }


}
