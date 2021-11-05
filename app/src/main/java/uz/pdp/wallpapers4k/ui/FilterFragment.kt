package uz.pdp.wallpapers4k.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.constraintlayout.utils.widget.ImageFilterView
import com.bumptech.glide.Glide
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.classes.filter.FilterClass
import uz.pdp.wallpapers4k.databinding.FragmentFilterBinding
import uz.pdp.wallpapers4k.room.entity.SplashEntity
import ja.burhanrashid52.photoeditor.PhotoFilter
import uz.pdp.wallpapers4k.adapters.FilterAdapter


class FilterFragment : Fragment(R.layout.fragment_filter) {

    private val binding by viewBinding(FragmentFilterBinding::bind)
    private lateinit var splash: SplashEntity
    private lateinit var list: ArrayList<FilterClass>
    private lateinit var filterAdapter: FilterAdapter
    private var lastPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            splash = it.getSerializable("filter") as SplashEntity
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Glide.with(requireContext()).load(splash.url).into(img)

            filterAdapter = FilterAdapter(requireContext(), splash.url, object : FilterAdapter.FilterClickListener{
                override fun itemClick(position: Int) {
                    img.setImageFilter(position)
                    lastPosition = position
                }

            })

            recycle.adapter = filterAdapter

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    setImage(lastPosition, (progress / 100).toFloat())
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {

                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {

                }

            })

        }
    }

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

    private fun setImage(lastSelectedPos: Int, percent: Float) {
        when (lastSelectedPos) {
            0 -> {
                binding.img.saturation = percent
                binding.seekBar.visibility = View.VISIBLE
            }
            1 -> {
                binding.img.brightness = percent
                binding.seekBar.visibility = View.VISIBLE
            }
            2 -> {
                binding.img.warmth = percent
                binding.seekBar.visibility = View.VISIBLE
            }
            3 -> {
                binding.img.contrast = percent
                binding.seekBar.visibility = View.VISIBLE
            }
            else -> {
                binding.img.saturation = 1.0f
                binding.img.brightness = 1.0f
                binding.img.warmth = 1.0f
                binding.img.contrast = 1.0f
                binding.seekBar.visibility = View.INVISIBLE
            }
        }
    }

}