package com.example.spacific

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.drawerlayout.widget.DrawerLayout
import com.example.spacific.databinding.FragmentThirdBinding
import com.google.android.material.navigation.NavigationView

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private val binding get() = _binding!!
    private lateinit var drawerLayout: DrawerLayout
    // private lateinit var navigationView: NavigationView  // Uncomment if you use the NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get the DrawerLayout (optional if you plan to use the drawer)
        drawerLayout = root.findViewById(R.id.drawer_layout)

        // Set up click listeners for the cards
        val cardSimton: View = root.findViewById(R.id.simton)
        val cardTrisum: View = root.findViewById(R.id.Trisum)
        val cardBabadogo: View = root.findViewById(R.id.Babadogo)

        cardSimton.setOnClickListener {
            // Handle Card 1 click
            // You can open a new Activity or Fragment, or perform any action
            openCard("Simton")
        }

        cardTrisum.setOnClickListener {
            // Handle Card 2 click
            openCard("Trisum")
        }

        cardBabadogo.setOnClickListener {
            // Handle Card 3 click
            openCard("Babadogo")
        }

        return root
    }

    private fun openCard(cardName: String) {
        // This function will be called when any of the cards is clicked
        // For now, you can just display a toast or navigate to another screen
        // For example:
        when (cardName) {
            "Simton" -> {
                // Navigate to Simton details screen (or perform any other action)
                val intent = Intent(activity, SimtonActivity::class.java)
                startActivity(intent)
            }
            "Trisum" -> {
                // Navigate to Trisum details screen
                val intent = Intent(activity, TrisumActivity::class.java)
                startActivity(intent)
            }
            "Babadogo" -> {
                // Navigate to Babadogo details screen
                val intent = Intent(activity, BabadogoActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
