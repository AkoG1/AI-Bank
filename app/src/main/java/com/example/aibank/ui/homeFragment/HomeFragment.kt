package com.example.aibank.ui.homeFragment


import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.example.aibank.R
import com.example.aibank.databinding.FragmentHomeBinding
import com.example.aibank.ui.currencyFragment.CurrencyFragment
import com.example.aibank.ui.BaseFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {


    override fun start() {
        binding.bottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_bottomnavigation_gel))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_launcher_background))
        binding.bottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_launcher_foreground))

        binding.bottomNavigation.setOnClickMenuListener {
            when(it.id){
                1 -> setFragment(CurrencyFragment.newInstance())
//                2 -> setFragment(CryptoFragment.newInstance())
//                3 -> setFragment(ProfileFragment.newInstance())

            }
        }
        binding.bottomNavigation.show(1)

    }

    private fun setFragment(fragment: Fragment){
        childFragmentManager
            .beginTransaction()
            .replace(R.id.homeFragmentContainer,fragment)
            .commit()
    }

}