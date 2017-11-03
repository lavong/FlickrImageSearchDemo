package com.ingloriousmind.android.flickrimagesearchdemo.photowall

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.ingloriousmind.android.flickrimagesearchdemo.R
import com.ingloriousmind.android.flickrimagesearchdemo.photowall.model.PhotoItem

class PhotowallAdapter(val clickHandler: ClickHandler) : RecyclerView.Adapter<PhotowallAdapter.PhotoViewHolder>() {

    var items: MutableList<PhotoItem> = mutableListOf()
    var largeImageRangeStart: Int = -1
    var largeImageRangeEnd: Int = -1

    override fun onBindViewHolder(holder: PhotoViewHolder, pos: Int) {
        val photoItem = items.get(pos)
        if (pos in largeImageRangeStart..largeImageRangeEnd) {
            Glide.with(holder.image.context).load(photoItem.imageUrlLarge).into(holder.image)
            holder.label.text = "large"
        } else {
            Glide.with(holder.image.context).load(photoItem.imageUrlThumbnail).into(holder.image)
            holder.label.text = "small"
        }
//        holder.label.visibility = if (TextUtils.isEmpty(photoItem.label)) View.GONE else View.VISIBLE
//        holder.label.text = photoItem.label
        holder.itemView.setOnClickListener { clickHandler.onPhotoClicked(photoItem, pos) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_photo_wall, parent, false)
        return PhotoViewHolder(view)
    }

    fun setPhotoItems(photos: List<PhotoItem>) {
        items.clear()
        items.addAll(photos)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun loadLargeImages(rangeStart: Int, rangeEnd: Int) {
        this.largeImageRangeStart = rangeStart
        this.largeImageRangeEnd = rangeEnd
        notifyItemRangeChanged(rangeStart, rangeEnd)
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val label: TextView

        init {
            image = view.findViewById<ImageView>(R.id.item_photo_wall_image) as ImageView
            label = view.findViewById<TextView>(R.id.item_photo_wall_label) as TextView
        }
    }

    interface ClickHandler {
        fun onPhotoClicked(photoItem: PhotoItem, position: Int)
    }

}