package com.example.spacific

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.spacific.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set OnClickListener for each card to navigate to the corresponding Compose-based activity
        binding.simtonchat.setOnClickListener {
            startActivity(Intent(activity, SimtonchatActivity::class.java))
        }

        binding.trisumchat.setOnClickListener {
            // For example purposes; add other Compose-based activities as needed
            startActivity(Intent(activity, TrisumchatActivity::class.java))
        }

        binding.babadogochat.setOnClickListener {
            startActivity(Intent(activity, BabadogochatActivity::class.java))
        }
    }

    // Function to navigate to the specified chat activity
    private fun navigateToChatActivity(activityClass: Class<*>) {
        val intent = Intent(activity, activityClass)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
