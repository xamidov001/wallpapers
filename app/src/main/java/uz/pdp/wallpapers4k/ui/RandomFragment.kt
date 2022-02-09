package uz.pdp.wallpapers4k.ui

import android.os.Bundle
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
import androidx.paging.PagingData
import androidx.paging.filter
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import uz.pdp.wallpapers4k.R
import uz.pdp.wallpapers4k.adapters.RecAdapter
import uz.pdp.wallpapers4k.classes.random.RandomClass
import uz.pdp.wallpapers4k.databinding.FragmentRandomBinding
import uz.pdp.wallpapers4k.network.NetworkHelper
import uz.pdp.wallpapers4k.room.entity.SplashEntity
import uz.pdp.wallpapers4k.utils.SplashResource
import uz.pdp.wallpapers4k.utils.SplashResourceRandom
import uz.pdp.wallpapers4k.viewmodel.SplashViewModel
import uz.pdp.wallpapers4k.viewmodel.ViewModelFactory
import kotlin.coroutines.CoroutineContext

class RandomFragment : Fragment(), CoroutineScope {

    private lateinit var binding: FragmentRandomBinding
    private lateinit var recAdapter: RecAdapter
    private var splashViewModel: SplashViewModel? = null
    private var pagingData: PagingData<RandomClass>? = null
    private lateinit var job: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            val networkHelper = NetworkHelper(requireContext())
            if (splashViewModel == null) {
                splashViewModel = ViewModelProvider(this@RandomFragment, ViewModelFactory(networkHelper))[SplashViewModel::class.java]
                splashViewModel?.fetchSplashRandom()?.observe(viewLifecycleOwner, {
                    when (it) {
                        is SplashResourceRandom.Loading -> {
                            recycle.visibility = View.GONE
                        }
                        is SplashResourceRandom.Success -> {
                            spinKit.visibility = View.GONE
                            recycle.visibility = View.VISIBLE
                            launch {
                                pagingData = PagingData.empty()
                                pagingData = it.list
                                recAdapter.submitData(pagingData!!)
                            }
                        }
                        is SplashResourceRandom.Error -> {
                            spinKit.visibility = View.GONE
                            recycle.visibility = View.GONE
                            val textview = TextView(requireContext())
                            val layoutParam = ConstraintLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )
                            layoutParam.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
                            layoutParam.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
                            layoutParam.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                            layoutParam.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
                            textview.text = it.message
                            textview.layoutParams = layoutParam
                        }
                    }
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        job = Job()

        binding.apply {

            recAdapter = RecAdapter(requireContext(), object : RecAdapter.OnMyClickListener {
                override fun onClick(position: RandomClass) {
                    val bundle = Bundle()
                    bundle.putSerializable("image", SplashEntity(position.id, position.user.name, position.urls.regular, position.likes, position.width, position.height))
                    findNavController().navigate(R.id.imageFragment, bundle)
                }

            })
            recycle.adapter = recAdapter

            if (splashViewModel != null) {
                spinKit.visibility = View.GONE
                launch {
                    listSubmit()
                }
            }
        }

        return binding.root
    }

    private suspend fun listSubmit() {
        recAdapter.submitData(pagingData!!)
    }

    override val coroutineContext: CoroutineContext
        get() = job
}