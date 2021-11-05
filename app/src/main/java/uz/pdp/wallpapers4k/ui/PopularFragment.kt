package uz.pdp.wallpapers4k.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.adapters.ViewAdapter
import uz.pdp.wallpapers4k.databinding.FragmentPopularBinding
import uz.pdp.wallpapers4k.databinding.ItemTabBinding

class PopularFragment : Fragment(R.layout.fragment_popular) {

    private val binding by viewBinding(FragmentPopularBinding::bind)
    private lateinit var list: ArrayList<String>
    private lateinit var viewAdapter: ViewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadTabs()

        binding.apply {

            viewAdapter = ViewAdapter(requireActivity(), list)
            viewPager2.adapter = viewAdapter

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val customView = ItemTabBinding.bind(tab?.customView!!)
                    customView.apply {
                        txt.setTextColor(Color.WHITE)
                        card.visibility = View.VISIBLE
                    }

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val customView = ItemTabBinding.bind(tab?.customView!!)
                    customView.apply {
                        txt.setTextColor(Color.parseColor("#6E6E6E"))
                        card.visibility = View.INVISIBLE
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })

            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                val tab_binding = ItemTabBinding.inflate(layoutInflater)
                tab_binding.apply {
                    if (tab.position == 0) {
                        txt.setTextColor(Color.WHITE)
                        card.visibility = View.VISIBLE
                    } else {
                        txt.setTextColor(Color.parseColor("#6E6E6E"))
                        card.visibility = View.INVISIBLE
                    }
                    txt.text = list[position].uppercase()
                }

                tab.customView = tab_binding.root
            }.attach()

        }
    }

    private fun loadTabs() {
        list = ArrayList()
        list.add("all")
        list.add("news")
        list.add("nature")
        list.add("animal")
        list.add("mountain")
        list.add("food")
        list.add("football")
    }

}