package com.example.hondaproject_hetra

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var ButtomNavBar : BottomNavigationView
    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.Frame)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ButtomNavBar= findViewById(R.id.NavButton)
        if (savedInstanceState == null) {
            ReplaceFragment(HomeFragment())
        }
        val currentFragment = getCurrentFragment()


        ButtomNavBar.setOnItemSelectedListener { Item ->
            when(Item.itemId){
                R.id.Home -> {
                    ReplaceFragment(HomeFragment())
                    true
                }
                R.id.Scan -> {
                    ReplaceFragment(ScanFragment())
                    true
                }
                R.id.List -> {
                    ReplaceFragment(ListFragment())
                    true
                }
                else -> {

                    true

                }
            }

        }
    }

    private fun ReplaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.Frame,fragment).commit()

    }
}