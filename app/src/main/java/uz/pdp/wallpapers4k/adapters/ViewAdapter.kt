package uz.pdp.wallpapers4k.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import uz.pdp.wallpapers4k.ui.PagerFragment

class ViewAdapter(fragmentActivity: FragmentActivity, var list: List<String>): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        return PagerFragment.newInstance(list[position])
    }
}