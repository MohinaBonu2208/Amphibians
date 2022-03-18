package com.bignerdranch.android.amphibians

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import com.bignerdranch.android.amphibians.databinding.FragmentDetailBinding
import com.bignerdranch.android.amphibians.network.Amphibian


class DetailFragment : Fragment() {
    private val viewModel: ViewModel by activityViewModels()

    private lateinit var amphibianId: String
    private lateinit var binding: FragmentDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            amphibianId = it.getString(AMPHIBIAN).toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentBinding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding.name.text = viewModel.amphibian.value?.name
         binding.description.text = viewModel.amphibian.value?.description
    }
    companion object {
        private const val AMPHIBIAN = "amphibian"
    }
}
