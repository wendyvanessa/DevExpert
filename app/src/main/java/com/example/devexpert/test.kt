package com.example.devexpert

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

// Paradigma de programación funcional

fun add(x:Int, y:Int): Int = x + y

fun test(context: Context){
    var x: Any = 20
    x = "Wendyyyyyyyy"

    Toast.makeText(context,"$x", Toast.LENGTH_SHORT).show()
}

/**
 *@lambdas: funciones que no se declaran, sino que se pasan inmediatamente como una expresión.
 */

fun test2(){

    val sum: (Int,Int) -> Int = {x, y -> x + y }

    val mul= {x: Int, y: Int -> x * y}
    val res = doOp(2,3, sum)
    val res2 = doOp(2,3, ::sum)
}

/**
 * Las funciones también se pueden pasar como referencia
 * agregando al parametro doble dso puntos ::sum
 */
fun sum(x:Int, y: Int): Int = x + y

fun doOp(x:Int, y: Int, op:(Int, Int) -> Int) {
    op(x,y)
}
