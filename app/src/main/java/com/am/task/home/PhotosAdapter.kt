package com.am.task.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.am.task.databinding.ItemPhotoBinding
import com.am.task.remote.model.Photo

class PhotosAdapter(private val mListener: Listener) : ListAdapter<Photo, PhotoViewHOlder>(
    object : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Photo, newItem: Photo) = oldItem == newItem
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHOlder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoBinding.inflate(inflater, parent, false)
        return PhotoViewHOlder(binding)
    }


    override fun onBindViewHolder(holder: PhotoViewHOlder, position: Int) {
        val item = getItem(position)
        holder.binding.photoImageView.load(item.previewURL)
        holder.binding.photoOwnerTextView.text = "Uploaded By ${item.user}"
        holder.binding.root.setOnClickListener {
            mListener.onItemClicked(photo = item)
        }
    }


    interface Listener {
        fun onItemClicked(photo: Photo)
    }

}


class PhotoViewHOlder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)
