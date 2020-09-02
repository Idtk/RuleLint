package com.idtk.mylink

import android.app.Application
import android.app.Activity
import android.os.Bundle
import android.Manifest.permission
import android.util.Log
import android.os.Looper

class MainActivity : Activity() {


    private lateinit var test:String
    private var test2:String ? = null
    private var b : AData.BData ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test = "msg"
        test2 = "msg2"

        val a = ABean()
        a.name = "111"
        a.list

        Log.d("tag11",test2!!)
        val ad = AData()
        b = AData.BData()
        val c = AData.BData.CData()
        b!!.c = c
        ad.b = b
    }

}
