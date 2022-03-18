package com.bignerdranch.android.amphibians

import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.amphibians.databinding.FragmentListBinding
import com.bignerdranch.android.amphibians.databinding.ListItemBinding
import com.bignerdranch.android.amphibians.network.Amphibian

class ListFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentListBinding.inflate(inflater, container, false)
        binding = view
        viewModel.getAmphibianList()
        return view.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.amphibians.observe(viewLifecycleOwner) { amphibians ->
            updateUI(amphibians)
        }
        viewModel.status.observe(viewLifecycleOwner) { status ->
            updateStatus(status)
        }
    }

    private fun updateUI(frogList: List<Amphibian>) {
        binding.rView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = Adapter(frogList)
        }
    }

    private fun updateStatus(status: AmphibianStatus) {

        when (status) {
            AmphibianStatus.LOADING -> {
                binding.statusImg.visibility = View.VISIBLE
                binding.statusImg.setImageResource(R.drawable.loading_animation)
            }
            AmphibianStatus.DONE -> {
                binding.statusImg.visibility = View.GONE
            }
            AmphibianStatus.ERROR -> {
                binding.statusImg.visibility = View.VISIBLE
                binding.statusImg.setImageResource(R.drawable.error)
            }

        }
    }


    inner class Adapter(private val amphibiansList: List<Amphibian>) :
        RecyclerView.Adapter<Adapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val amphibian = amphibiansList[position]
            holder.bind(amphibian)
        }

        override fun getItemCount() = amphibiansList.size

        inner class ViewHolder(private var binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(amphibian: Amphibian) {
                binding.button.text = amphibian.name
                binding.button.setOnClickListener {
                    viewModel.onItemClicked(amphibian)
                    findNavController().navigate(R.id.action_listFragment_to_detailFragment)
                }
            }
        }
    }
}
//    override fun onDestroyView() {
//        super.onDestroyView()
//        binding = null
//    }

