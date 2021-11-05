package uz.pdp.wallpapers4k.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import uz.pdp.wallpaperretro.classes.Result
import uz.pdp.wallpapers4k.databinding.ItemBinding
import uz.pdp.wallpapers4k.databinding.ItemPageBinding

class PagerAdapter(var context: Context, var list: ArrayList<Result>, var listener: OnMyClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private var LOADING = 0
    private var DATA = 1

    interface OnMyClickListener {
        fun onClick(position: Result)
    }

    fun adAll(otherList: List<Result>) {
        val size = list.size
        list.addAll(otherList)
        notifyItemRangeChanged(size, list.size)
    }

    fun addLoading() {
        isLoading = true
    }

    inner class VHProgress(var binding: ItemPageBinding): RecyclerView.ViewHolder(binding.root)

    inner class VH(var bind: ItemBinding): RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LOADING) {
            return VHProgress(ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            return VH(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == DATA) {
            val vh = holder as VH
            vh.bind.apply {
                val item = list[position]

                Glide.with(context).load(item.urls.regular).addListener(object :
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
        } else {
            val vhProgress = holder as VHProgress
            vhProgress.binding.apply {

            }
        }
    }

    override fun getItemCount(): Int  = list.size

    override fun getItemViewType(position: Int): Int {
        if (position == list.size-1 && isLoading) {
            return LOADING
        }
        return DATA
    }

}