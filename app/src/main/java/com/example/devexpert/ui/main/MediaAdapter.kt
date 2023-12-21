package com.example.devexpert.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.devexpert.R
import com.example.devexpert.data.MediaItem
import com.example.devexpert.databinding.ViewMediaItemBinding
import com.example.devexpert.ui.inflate
import com.example.devexpert.ui.loadUrl
import kotlin.properties.Delegates

interface Listener {
    fun onClick(mediaItem: MediaItem)
}

/**
 * Para mejorar la  implementación de la interface Listener que se detalla acontinuación:
 * class MediaAdapter(val items:List<MediaItem>, private val listener: Listener) :
 * se crea una lambda que rebe un MediaItem y no devuelve nada,
 * con ello no hay necesidad de pasar la función creada en la interface y reducimos el codigo
 *
 */
class MediaAdapter(
    items:List<MediaItem> = emptyList(),
    private val listener: (MediaItem)-> Unit
) :
    RecyclerView.Adapter<MediaAdapter.ViewHolder>(){

    // Se actualizara la vista cada vez que la lista de items se mencione
    var items:List<MediaItem> by Delegates.observable(items) { _, _, _ ->
        notifyDataSetChanged()
    }


    //Crear una vista cuando el recyclerview se lo pida
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Inflar la vista
        //val binding = ViewMediaItemBinding.inflate(LayoutInflater.from(parent.context))
        //return ViewHolder(binding.root)

        val view = parent.inflate(R.layout.view_media_item)
        return ViewHolder(view)

    }

    //Actualización de una vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener { listener(item) }
    }

    // Obtenemos el número de elementos que tiene el adapter
    override fun getItemCount(): Int = items.size

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        val binding = ViewMediaItemBinding.bind(view)

        fun bind(mediaItem: MediaItem){
            binding.mediaTitle.text = mediaItem.title
            binding.mediaThumb.loadUrl(mediaItem.url)

            binding.mediaVideoIndicator.visibility = when(mediaItem.type){
                MediaItem.Type.PHOTO -> View.GONE
                MediaItem.Type.VIDEO -> View.VISIBLE
            }


        }
    }

}