package com.example.devexpert

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * @length Si en el llamado de la función no se pasa este parametro, utilizara por defecto
 * Toast.LENGTH_SHORT
 */
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,length).show()
}

fun RecyclerView.ViewHolder.toast(message: String){
    itemView.context.toast(message)
}

/**
 * @LayoutRes comprueba que el parametro que le estamos pasando si es del timpo especificado
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int):View{

    return  LayoutInflater
        .from(context)
        .inflate(R.layout.view_media_item,this,false)
}

/**
 * Creación de función de extención sobre ImageView que se llamará "loadUrl"
 */
fun ImageView.loadUrl(url: String){
    Glide.with(this).load(url).into(this)
}

/**
 * Reifield permite que el acceso a los tipos genericos en tiempo de ejecución.
 * Los tipos genericos <> permiten la utilización de diferentes tipos de datos
 * en este caso un Activity
 */
inline fun <reified T: Activity> Context.startActivity(){
    val intent = Intent(this,T::class.java)
    startActivity(intent)
}