package com.surendramaran.yolov8tflite.Adapter

import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.hondaproject_hetra.Model.Image
import com.example.hondaproject_hetra.R


class AdapterImage (private val ImageList : ArrayList<Image>
                    ,    private val onItemClick: (Image) -> Unit
) : RecyclerView.Adapter<AdapterImage.AdapterViewHolder>() {
    class AdapterViewHolder(itemview : View) : RecyclerView.ViewHolder(itemview){
        val image : ImageView = itemview.findViewById(R.id.ListImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.listitem,parent,false)
        return AdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  ImageList.count()
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val Image = ImageList[position]
        holder.image.setImageBitmap(Image.Image)
        holder.itemView.setOnClickListener {
            onItemClick(Image)
        }
    }
}
