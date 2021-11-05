package uz.pdp.wallpapers4k.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.utils.widget.ImageFilterView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ja.burhanrashid52.photoeditor.PhotoEditor
import uz.pdp.wallpapers4k.classes.filter.FilterClass
import uz.pdp.wallpapers4k.databinding.ItemFilterBinding

class FilterAdapter(
    var context: Context,
    var url: String,
    var listener: FilterClickListener
) : RecyclerView.Adapter<FilterAdapter.Vh>() {

    interface FilterClickListener {
        fun itemClick(position: Int)
    }

    inner class Vh(var binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {

        holder.binding.apply {
            Glide.with(context).load(url).into(image)

            image.setImageFilter(position)

            imageCard.setOnClickListener {
                listener.itemClick(position)
            }

        }
    }

    override fun getItemCount(): Int = 5

    fun ImageFilterView.setImageFilter(position: Int) {
        when (position) {
            0 -> {
                this.saturation = 2.0f
                this.brightness = 1.0f
                this.warmth = 1.0f
                this.contrast = 1.0f
                this.isDrawingCacheEnabled = true
            }
            1 -> {
                this.brightness = 2.0f
                this.saturation = 1.0f
                this.warmth = 1.0f
                this.contrast = 1.0f
                this.isDrawingCacheEnabled = true
            }
            2 -> {
                this.warmth = 2.0f
                this.saturation = 1.0f
                this.brightness = 1.0f
                this.contrast = 1.0f
                this.isDrawingCacheEnabled = true
            }
            3 -> {
                this.contrast = 2.0f
                this.warmth = 1.0f
                this.saturation = 1.0f
                this.brightness = 1.0f
                this.isDrawingCacheEnabled = true
            }
            else -> {
                this.saturation = 1.0f
                this.warmth = 1.0f
                this.brightness = 1.0f
                this.contrast = 1.0f
                this.isDrawingCacheEnabled = true
            }
        }
    }

}