package com.example.devexpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.devexpert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = MediaAdapter(getItems()) {mediaItem->
            toast(mediaItem.title)
        }

        val textView:TextView = TextView(this).apply2{
            text =  "Hello"
            hint = "Goodbaye"
            textSize = 20f
        }

    }
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