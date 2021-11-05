package uz.pdp.wallpapers4k.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.pdp.wallpapers4k.databinding.ItemBinding
import uz.pdp.wallpapers4k.room.entity.SplashEntity

class RecAdapterLike(var context: Context, var listener: OnMyClickListener): ListAdapter<SplashEntity, RecAdapterLike.VH>(MyDiffUtil()) {

    interface OnMyClickListener {
        fun onClick(position: SplashEntity)
    }

    class MyDiffUtil: DiffUtil.ItemCallback<SplashEntity>() {
        override fun areItemsTheSame(oldItem: SplashEntity, newItem: SplashEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SplashEntity, newItem: SplashEntity): Boolean {
            return oldItem == newItem
        }

    }

    inner class VH(var binding: ItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.binding.apply {
            val item = getItem(position)
            Glide.with(context).load(item.url).addListener(object :
                RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    spinKit.visibility = View.GONE
                    return false
                }
            }).into(image)

            image.setOnClickListener {
                listener.onClick(item)
            }
        }
    }
}