package com.example.spacific

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
    private lateinit var navigationView: NavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get the DrawerLayout and NavigationView
        drawerLayout = root.findViewById(R.id.drawer_layout)
        /*navigationView = root.findViewById(R.id.navigation_view)

        // Set up menu button to open drawer
        val menuButton: View = binding.root.findViewById(R.id.menu_button)
        menuButton.setOnClickListener {
            drawerLayout.open()
        }

        Set up navigation item click listener
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_settings -> {
                    // Navigate to Settings screen
                    findNavController().navigate(R.id.action_ThirdFragment_to_SettingsFragment)
                }
                R.id.menu_edit_profile -> {
                    // Navigate to Edit Profile screen
                    findNavController().navigate(R.id.action_ThirdFragment_to_EditProfileFragment)
                }
                R.id.menu_logout -> {
                    // Handle logout (clear session, navigate to login, etc.)
                }
            }
            drawerLayout.close() // Close the drawer after selection
            true
        } */

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
