package com.example.spacific

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.spacific.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: FragmentFourthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToFifth.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_FifthFragment)
        }

        binding.buttonToThird.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_ThirdFragment)
        }
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
