package com.terasoltechnologies.mvvm_test.networkdfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.terasoltechnologies.mvvm_test.Network.ApiStatus
import com.terasoltechnologies.mvvm_test.Network.MarsProperty
import com.terasoltechnologies.mvvm_test.R
import com.terasoltechnologies.mvvm_test.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.grid_view_item.*
import kotlinx.android.synthetic.main.grid_view_item.view.*
import kotlinx.android.synthetic.main.grid_view_item.view.text_view
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: NetworkViewModel

    private var dataList = ArrayList<MarsProperty>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NetworkViewModel::class.java)

        binding.lifecycleOwner = this

        binding.recyclerView.adapter = HomeAdapter(requireActivity(), dataList)

        // (binding.recyclerView.adapter as HomeAdapter).notifyDataSetChanged()


        viewModel.status.observe(viewLifecycleOwner, Observer { apiStatus ->

            Timber.d(apiStatus.name)

            if (apiStatus == ApiStatus.LOADING) {
                text_loading.visibility = View.VISIBLE
            }
            if (apiStatus == ApiStatus.SUCCESS) {
                text_loading.visibility = View.GONE
            }
            if (apiStatus == ApiStatus.ERROR) {

                text_loading.text = "Error occurs"
            }

        })

        viewModel.apiData.observe(viewLifecycleOwner, Observer {

            if (it != null) {
                //Timber.d(it.toString())
                for (data in it) {
                    dataList.add(data)
                }
                (binding.recyclerView.adapter as HomeAdapter).notifyDataSetChanged()
            }
        })
    }

}


class HomeAdapter(
    private var context: FragmentActivity,
    private var apiData: ArrayList<MarsProperty>
) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.grid_view_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return apiData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       // Glide.with(context).load(apiData[position].img_src).into(holder.imageView)

        //Timber.d(apiData[0].img_src)

        // Picasso.get().load(apiData[position].img_src).into(holder.imageView)

        holder.textView.text = apiData[position].type

    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.mars_image
        var textView: TextView = itemView.text_view
    }
}
