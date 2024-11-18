package com.example.spacific

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.spacific.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set click listeners for each CardView to start a new activity
        binding.simtom.setOnClickListener {
            val intent = Intent(requireContext(), SimtonActivity::class.java)
            startActivity(intent)
        }

        binding.trisum.setOnClickListener {
            val intent = Intent(requireContext(), TrisumActivity::class.java)
            startActivity(intent)
        }

        binding.babadogo.setOnClickListener {
            val intent = Intent(requireContext(), BabadogoActivity::class.java)
            startActivity(intent)
        }

        /* Find each CardView by ID
        val cardView1: CardView = view.findViewById(R.id.simtom)
        val cardView2: CardView = view.findViewById(R.id.trisum)
        val cardView3: CardView = view.findViewById(R.id.babadogo)

        // Set Click Listeners
        cardView1.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_first_to_simtonFragment)
        }

        cardView2.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_first_to_trisumFragment)
        }

        cardView3.setOnClickListener {
            findNavController().navigate(R.id.action_fragment_first_to_babadogoFragment)
        } */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
