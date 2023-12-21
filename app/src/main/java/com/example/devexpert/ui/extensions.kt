package com.example.devexpert.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.devexpert.R

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
 *
 * @Any es para referirse a cualquier tipo de dato no nulable.
 *
 * @Pair es para agrupar dos elementos de diferentes tipos, en este caso
 * un string y any.
 *
 * @vararg es para recibir una cantidad invariable de parametros en este caso del tipo Pair, es
 * como una matriz de datos.
 *
 *
 */
inline fun <reified T: Activity> Context.startActivity(vararg pairs: Pair<String, Any?>){
    Intent(this,T::class.java)
        .apply2 { putExtras(bundleOf(*pairs)) }
        .also(::startActivity)
}

/**
 * Lambdas con receivers:
 * @<T> se delcara de tipo generica, y como las funciones de extención,
 * se especifica a que tipo "T" se aplicara la misma.
 *
 * se pasa una lambda que recibe un T y no devuelve nada.
 *
 * Lambda con recivers = T.() -> recibe directamente T
 * Lambda normal = (T) -> recibe it
 */
fun <T> T.apply2(body: T.() -> Unit): T {
    this.body()
    return this
}

fun <T,U> T.run2(body: T.() -> U): U {
    return this.body()
}

fun <T,U> T.let2(body: (T) -> U): U {
    return body(this)
}


fun <T,U> T.with2(receiver: T,body: T.() -> U): U {
    return receiver.body()
}

fun <T> T.also2(body: T.() -> Unit): T {
    body(this)
    return this
}