package com.example.wb_week_6_3.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.example.wb_week_6_3.databinding.FragmentResultPiBinding
import com.example.wb_week_6_3.viewModel.PiViewModel
import kotlinx.coroutines.launch


class ResultPiFragment : Fragment() {
    private var _binding: FragmentResultPiBinding? = null
    private val binding get() = _binding!!
    private val piViewModel: PiViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultPiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        piViewModel.viewModelCoroutineScope.launch {
            piViewModel.liveData.observe(activity as LifecycleOwner) {
                binding.tvResultCalculation.text = it
                binding.scrollView.smoothScrollTo(0, binding.tvResultCalculation.bottom)
            }
        }
    }

    companion object {

        fun newInstance() = ResultPiFragment()
    }
}