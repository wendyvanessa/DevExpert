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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.get
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
 * Vamos a simplificar esta linea de codigo "ViewModelProvider(this).get()"
 * cada vez que sea necesario simplemente llamamos getViewModel()
 *
 * @inline y @reified permite acceder a la función generica en tiempo de ejecución
 *
 * la función retorna un generico de tipo ViewModel,
 * get<T>()le esta diciendo al compilador que se espera un ViewModel del tipo T.
 *
 * @getViewModel(body: T.() -> Unit) este argumento es una lambda que se aplica después de
 * utilizar la instancia del viewModel
 *
 * @this es el argumento que indica en que actividad se almacenará el viewModel
 *
 * la instancia de MainViewModel seguira el siclo de vida de la actividad donde
 * la función de extensión sea llamada.
 *
 */

inline fun <reified T : ViewModel> ViewModelStoreOwner.getViewModel(body: T.() -> Unit = {}): T {
    return ViewModelProvider(this).get<T>().apply(body)
}

/**
 * @observe es para crear los observadores de los liveData.
 */
fun <T> LifecycleOwner.observe(livedata: LiveData<T>, observer: (T) -> Unit){
    livedata.observe(this, Observer(observer))
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