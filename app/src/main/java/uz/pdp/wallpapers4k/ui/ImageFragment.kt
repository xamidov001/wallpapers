package uz.pdp.wallpapers4k.ui

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DownloadManager
import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import uz.pdp.wallpaperretro.classes.Result
import uz.pdp.wallpapers4k.MainActivity
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.databinding.FragmentImageBinding
import uz.pdp.wallpapers4k.databinding.ItemDialogBinding
import uz.pdp.wallpapers4k.room.database.MyDatabase
import uz.pdp.wallpapers4k.room.entity.SplashEntity
import java.io.File

class ImageFragment : Fragment(R.layout.fragment_image) {

    private val binding by viewBinding(FragmentImageBinding::bind)
    private var isSet = false
    private var isLike = false
    private lateinit var database: MyDatabase
    private lateinit var splash: SplashEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            splash = it.getSerializable("image") as SplashEntity
        }
        (activity as MainActivity).hide()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = MyDatabase.getInstance(requireContext())

        loadBlur()

        binding.apply {

            Glide.with(requireActivity()).load(splash.url).into(bigImage)

            backCard.setOnClickListener {
                if (isSet) {
                    isSet = false
                    loadVisibility(isSet)
                } else {
                    findNavController().popBackStack()
                }
            }

            setCard.setOnClickListener {
                if (!isSet) {
                    isSet = true
                    loadVisibility(isSet)
                }
            }

            displayCard.setOnClickListener { setScreen(1) }
            lockCard.setOnClickListener { setScreen(2) }
            lockDisplayCard.setOnClickListener { setScreen(3) }

            likeCard.setOnClickListener {
                if (isLike) {
                    isLike = false
                    database.helper().deleteSplash(splash)
                    likeImage.setImageResource(R.drawable.ic_like_icon)
                } else {
                    isLike = true
                    database.helper().addSplash(splash)
                    likeImage.setImageResource(R.drawable.ic_like_icon_true)
                }
            }

            aboutCard.setOnClickListener {
                val alertDialog = AlertDialog.Builder(requireContext())
                val binding_dialog = ItemDialogBinding.inflate(layoutInflater)
                val create = alertDialog.create()
                create.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                create.window?.setGravity(Gravity.BOTTOM)
                create.setView(binding_dialog.root)

                binding_dialog.apply {
                    blur.setBlur()
                    blurViewDialog.setBlur()

                    websiteTv.text = "Website: ${splash.url}"
                    authorTv.text = "Author: ${splash.name}"
                    downloadTv.text = "Likes: ${splash.counts}"
                    sizeTv.text = "Size: ${splash.width}x${splash.height}"

                }

                create.show()
            }

            filterCard.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("filter", splash)
                findNavController().navigate(R.id.filterFragment, bundle)
            }

            shareCard.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_STREAM, splash.url)
                intent.type = "image/jpeg"
                startActivity(Intent.createChooser(intent, null))
            }

            downloadCard.setOnClickListener {
                downloadImageNew(splash.id, splash.url)
            }

        }
    }


    private fun downloadImageNew(filename: String, downloadUrlOfImage: String) {
        try {
            val dm =
                requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(downloadUrlOfImage)
            val request = DownloadManager.Request(downloadUri)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(filename)
                .setMimeType("image/jpeg")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_PICTURES,
                    File.separator + filename + ".jpg"
                )
            dm.enqueue(request)
            Toast.makeText(requireContext(), "Image download started.", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Image download failed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadBlur() {
        binding.apply {

            aboutBlur.setBlur()
            backBlur.setBlur()
            downloadBlur.setBlur()
            filterBlur.setBlur()
            likeBlur.setBlur()
            setBlur.setBlur()
            shareBlur.setBlur()
            lockBlur.setBlur()
            lockDisplayBlur.setBlur()
            displayBlur.setBlur()

            var have : SplashEntity? = null
            have = database.helper().isHave(splash.id)

            if (have != null) {
                likeImage.setImageResource(R.drawable.ic_like_icon_true)
                isLike = true
            }

        }

    }

    private fun BlurView.setBlur() {
        val radius = 23f
        val decorView = activity?.window?.decorView
        val rootView = decorView?.findViewById<View>(android.R.id.content) as ViewGroup
        val windowBackground = decorView.background
        this.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(requireContext()))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true).setOverlayColor(Color.parseColor("#41FFFFFF"))
    }

    private fun loadVisibility(a: Boolean) {
        binding.apply {

            if (a) {
                displayCard.visibility = View.VISIBLE
                lockCard.visibility = View.VISIBLE
                lockDisplayCard.visibility = View.VISIBLE
                setCard.visibility = View.GONE
                downloadCard.visibility = View.GONE
                filterCard.visibility = View.GONE
                likeCard.visibility = View.GONE
                shareCard.visibility = View.GONE
                aboutCard.visibility = View.GONE
            } else {
                displayCard.visibility = View.GONE
                lockCard.visibility = View.GONE
                lockDisplayCard.visibility = View.GONE
                setCard.visibility = View.VISIBLE
                downloadCard.visibility = View.VISIBLE
                filterCard.visibility = View.VISIBLE
                likeCard.visibility = View.VISIBLE
                shareCard.visibility = View.VISIBLE
                aboutCard.visibility = View.VISIBLE
            }

        }
    }

    @SuppressLint("CheckResult")
    fun setScreen(n: Int) {
        val instance = WallpaperManager.getInstance(requireContext())
        val bitmap = (binding.bigImage.drawable as BitmapDrawable).bitmap
        when (n) {
            1 -> {
                instance.setBitmap(bitmap)
            }
            2 -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    instance.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                }
            }
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    instance.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM)
                }
            }
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).show()
        super.onDestroy()
    }

}