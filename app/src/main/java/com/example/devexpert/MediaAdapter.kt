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
import com.example.devexpert.databinding.ViewMediaItemBinding

class MediaAdapter(val items:List<MediaItem> ) : RecyclerView.Adapter<MediaAdapter.ViewHolder>(){


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


            itemView.setOnClickListener {
                toast("function of extension")
            }


        }
    }

}