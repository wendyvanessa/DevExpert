package com.example.devexpert

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MediaAdapter(val items:List<MediaItem> ) : RecyclerView.Adapter<MediaAdapter.ViewHolder>(){


    //Crear una vista cuando el recyclerview se lo pida
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Inflar la vista
        val view = parent.inflate(R.layout.view_media_item)
        return ViewHolder(view)

    }

    //Actualización de una vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // Obtenemos el número de elementos que tiene el adapter
    override fun getItemCount(): Int = items.size

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        private val title:TextView = view.findViewById(R.id.mediaTitle)
        private val thumb:ImageView = view.findViewById(R.id.mediaThumb)

        fun bind(mediaItem: MediaItem){
            title.text = mediaItem.title
            thumb.loadUrl(mediaItem.url)

            itemView.setOnClickListener {
                toast("function of extension")
            }
        }
    }

}