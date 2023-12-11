package com.example.devexpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.devexpert.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
/**
 * @lazy utilizado para posponer la inicialización de MediaAdapter
 * hasta que se llame por primera vez a Adapter.
 *
 *  No es necesario iniciarlo hasta que realmente se necesita.
 */
    private val adapter by lazy {
        MediaAdapter(getItems.ListItems) {mediaItem->
            toast(mediaItem.title)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter


        val textView:TextView = TextView(this).apply2{
            text =  "Hello"
            hint = "Goodbaye"
            textSize = 20f
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     adapter.items = getItems.ListItems.let { media ->
         when(item.itemId){
            R.id.filter_photos -> media.filter { it.type == MediaItem.Type.PHOTO }
            R.id.filter_videos -> media.filter { it.type == MediaItem.Type.VIDEO }
            R.id.filter_all -> media
            else -> emptyList()
         }
     }

        return super.onOptionsItemSelected(item)
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