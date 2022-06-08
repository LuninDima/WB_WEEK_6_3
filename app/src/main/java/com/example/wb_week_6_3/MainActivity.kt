package com.example.wb_week_6_3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wb_week_6_3.databinding.ActivityMainBinding
import com.example.wb_week_6_3.view.ResultPiFragment
import com.example.wb_week_6_3.view.SettingsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if(savedInstanceState == null){
            loadFragments()
        }
    }

    private fun loadFragments() {
        supportFragmentManager.beginTransaction().replace(R.id.container_fragment_result_pi, ResultPiFragment.newInstance())
            .replace(R.id.container_fragment_settings, SettingsFragment.newInstance()).commit()
    }
}