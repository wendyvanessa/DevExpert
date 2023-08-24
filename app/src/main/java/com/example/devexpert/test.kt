package com.example.devexpert

import android.content.Context
import android.widget.Toast

// Paradigma de programación funcional

fun add(x:Int, y:Int): Int = x + y

fun test(context: Context){
    var x: Any = 20
    x = "Wendyyyyyyyy"

    Toast.makeText(context,"$x", Toast.LENGTH_SHORT).show()
}