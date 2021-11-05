package uz.pdp.wallpapers4k.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.wallpaperretro.classes.Result
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.adapters.PagerAdapter
import uz.pdp.wallpapers4k.adapters.RecAdapter
import uz.pdp.wallpapers4k.databinding.FragmentPagerBinding
import uz.pdp.wallpapers4k.models.PaginationScrollListener
import uz.pdp.wallpapers4k.network.NetworkHelper
import uz.pdp.wallpapers4k.room.entity.SplashEntity
import uz.pdp.wallpapers4k.utils.SplashResource
import uz.pdp.wallpapers4k.viewmodel.SplashViewModel
import uz.pdp.wallpapers4k.viewmodel.ViewModelFactory

class PagerFragment : Fragment(R.layout.fragment_pager) {

    private val ARG_PARAM1 = "param1"
    private val binding by viewBinding(FragmentPagerBinding::bind)
    private var url: String = ""
    private var PAGE = 1
    private lateinit var splashViewModel: SplashViewModel
    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var list: ArrayList<Result>
    private var isLoading = false
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_PARAM1) as String
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            list = ArrayList()

            pagerAdapter =
                PagerAdapter(requireContext(), list, object : PagerAdapter.OnMyClickListener {
                    override fun onClick(position: Result) {
                        val bundle = Bundle()
                        bundle.putSerializable("image", SplashEntity(position.id, position.user.name, position.urls.regular, position.likes, position.width, position.height))
                        findNavController().navigate(R.id.imageFragment, bundle)
                    }

                })
            val gridLayoutManager = GridLayoutManager(requireContext(), 3)
            recycle.layoutManager = gridLayoutManager
            recycle.adapter = pagerAdapter
            val networkHelper = NetworkHelper(requireContext())
            splashViewModel = ViewModelProvider(
                this@PagerFragment,
                ViewModelFactory(networkHelper)
            )[SplashViewModel::class.java]
            splashViewModel.fetchSplash(url, PAGE).observe(viewLifecycleOwner, {
                when (it) {
                    is SplashResource.Loading -> {
                        recycle.visibility = View.GONE
                    }
                    is SplashResource.Success -> {
                        spinKit.visibility = View.GONE
                        recycle.visibility = View.VISIBLE
                        pagerAdapter.adAll(it.unsplash.results)

                        if (PAGE != 6) {
                            pagerAdapter.addLoading()
                        } else {
                            isLastPage = true
                        }
                    }
                    is SplashResource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            "No connect with Internet",
                            Toast.LENGTH_SHORT
                        ).show()
                        spinKit.visibility = View.GONE
                        recycle.visibility = View.GONE
                        val textview = TextView(requireContext())
                        val layoutParam = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        layoutParam.gravity = Gravity.CENTER
                        textview.text = it.message
                        textview.setTextColor(Color.WHITE)
                        textview.layoutParams = layoutParam
                    }
                }
            })

            recycle.addOnScrollListener(object : PaginationScrollListener(gridLayoutManager) {
                override fun loadMoreItems() {
                    isLoading = true
                    PAGE++
                    getNext(PAGE)
                }

                override fun isLastPage(): Boolean {
                    return isLastPage
                }

                override fun isLoading(): Boolean {
                    return isLoading
                }

            })

        }
    }

    fun getNext(page: Int) {
        binding.apply {
            val networkHelper = NetworkHelper(requireContext())
            splashViewModel = ViewModelProvider(
                this@PagerFragment,
                ViewModelFactory(networkHelper)
            )[SplashViewModel::class.java]
            splashViewModel.fetchSplash(url, page).observe(viewLifecycleOwner, {
                when (it) {
                    is SplashResource.Loading -> {
                    }
                    is SplashResource.Success -> {
                        isLoading = false
                        pagerAdapter.adAll(it.unsplash.results)

                        if (PAGE != 6) {
                            pagerAdapter.addLoading()
                        } else {
                            isLastPage = true
                        }
                    }
                    is SplashResource.Error -> {

                    }
                }
            })

        }
    }


    companion object {
        @JvmStatic
        fun newInstance(list: String) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, list)
                }
            }
    }
}