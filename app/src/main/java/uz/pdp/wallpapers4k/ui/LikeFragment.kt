package uz.pdp.wallpapers4k.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.adapters.RecAdapterLike
import uz.pdp.wallpapers4k.databinding.FragmentLikeBinding
import uz.pdp.wallpapers4k.room.database.MyDatabase
import uz.pdp.wallpapers4k.room.entity.SplashEntity

class LikeFragment : Fragment(R.layout.fragment_like) {

    private val binding by viewBinding(FragmentLikeBinding::bind)
    private lateinit var recAdapterLike: RecAdapterLike
    private lateinit var database: MyDatabase

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = MyDatabase.getInstance(requireContext())

        binding.apply {
            recAdapterLike = RecAdapterLike(requireContext(), object : RecAdapterLike.OnMyClickListener {
                override fun onClick(position: SplashEntity) {
                    val bundle = Bundle()
                    bundle.putSerializable("image", position)
                    findNavController().navigate(R.id.imageFragment, bundle)
                }

            })

            recycle.adapter = recAdapterLike

            recAdapterLike.submitList(database.helper().getAll())


        }
    }

}