package com.example.devexpert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.devexpert.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding

/**
 * @lazy utilizado para posponer la inicialización de MediaAdapter
 * hasta que se llame por primera vez a Adapter.
 *
 *  No es necesario iniciarlo hasta que realmente se necesita.
 */
    private val adapter by lazy {
        MediaAdapter() {mediaItem->
            startActivity<DetailActivity>(DetailActivity.EXTRA_ID to mediaItem.id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.adapter = adapter
        updateItems()

        val textView:TextView = TextView(this).apply2{
            text =  "Hello"
            hint = "Goodbaye"
            textSize = 20f
        }

    }

    fun updateItems(filter: Filter = Filter.None){
        lifecycleScope.launch {
            binding.progress.visibility = View.VISIBLE
            adapter.items  = withContext(Dispatchers.IO){ getFilteredItems(filter) }
            binding.progress.visibility = View.GONE
        }
    }

    /**
     * @filter es el tipo de dato ->
     *      Filter.ByType(MediaItem.Type.PHOTO)
     *      Filter.ByType(MediaItem.Type.VIDEO)
     *      Filter.None
     *
     * Definimos que mostrar al usuario dependiendo del tipo de dato que entre
     */
    private  fun getFilteredItems(filter:Filter): List<MediaItem>{
        return MediaProvider.getItems().let { media ->
            when(filter){
                Filter.None-> media
                is Filter.ByType -> media.filter { it.type == filter.type }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val filter =  when(item.itemId) {
            R.id.filter_photos -> Filter.ByType(MediaItem.Type.PHOTO)
            R.id.filter_videos -> Filter.ByType(MediaItem.Type.VIDEO)
            else -> Filter.None
        }

        updateItems(filter)
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

