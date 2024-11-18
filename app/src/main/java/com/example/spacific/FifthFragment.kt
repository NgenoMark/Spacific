package com.example.spacific

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.spacific.databinding.FragmentFifthBinding

class FifthFragment : Fragment() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: FragmentFifthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFifthBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonToFourth.setOnClickListener {
            findNavController().navigate(R.id.action_fifthFragment_to_fourthFragment)
        }

        binding.buttonToFirst.setOnClickListener {
            findNavController().navigate(R.id.action_fifthFragment_to_FirstFragment)
        }
    } */




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
