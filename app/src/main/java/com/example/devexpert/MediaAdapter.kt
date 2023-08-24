package com.example.devexpert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MediaAdapter(val items:List<MediaItem> ) : RecyclerView.Adapter<MediaAdapter.ViewHolder>(){


    //Crear una vira cuando el recyclerview se lo pida
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        //Inflar la vista
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_media_item,parent,false)

        return ViewHolder(view)

    }

    //Actualización de una vista
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    // Obtenemos el numero de elementos que tiene el adapter
    override fun getItemCount(): Int = items.size

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){

        private val title:TextView = view.findViewById(R.id.mediaTitle)
        private val thum:ImageView = view.findViewById(R.id.mediaThumb)

        fun bind(mediaItem: MediaItem){
            title.text = mediaItem.title
            Glide.with(thum).load(mediaItem.url).into(thum)
        }
    }

}