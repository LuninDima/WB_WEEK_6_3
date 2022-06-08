package com.example.wb_week_6_3.view

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.wb_week_6_3.R
import com.example.wb_week_6_3.databinding.FragmentSettingsBinding
import com.example.wb_week_6_3.utills.getColorForSettingsFragment
import com.example.wb_week_6_3.viewModel.PiViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val piViewModel: PiViewModel by activityViewModels()
    private lateinit var chronometer: Chronometer
    private var chronometerBaseTime: Long = 0
    private var pauseOffSet: Long = 0
    private var isRunning = false
    private var countIntervalChangeColor = 1
    private var color = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewStateRestored(savedInstanceState)
        initButton(savedInstanceState)
        initTimer(savedInstanceState)

    }

    private fun initButton(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            if (isRunning) {
                binding.buttonPlayPausa.text = getString(R.string.button_pausa_text)
            } else
                binding.buttonPlayPausa.text = getString(R.string.button_play_text)
        }
        binding.buttonPlayPausa.setOnClickListener {
            if (!isRunning) {
                binding.buttonPlayPausa.text = getString(R.string.button_pausa_text)
                piViewModel.startCalculatePi()
                isRunning = true
                timerStart()
            } else {
                binding.buttonPlayPausa.text = getString(R.string.button_play_text)
                piViewModel.pauseCalculatePi()
                isRunning = false
                timerPause()
            }
        }
        binding.buttonReset.setOnClickListener {
            piViewModel.resetCalculatePi()
            if (!isRunning) {
                isRunning = true
                binding.buttonPlayPausa.text = getString(R.string.button_pausa_text)
            }
            timerReset()
        }
    }

    private fun initTimer(savedInstanceState: Bundle?) {
        chronometer = binding.chronometer
        if (savedInstanceState != null) {
            binding.fragmentSettings.setBackgroundColor(color)
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
            if (isRunning) {
                chronometer.start()
                chronometer.base = chronometerBaseTime
            }
        }
        piViewModel.viewModelCoroutineScope.launch(Dispatchers.Default) {
            binding.chronometer.setOnChronometerTickListener {
                val elapsedMillis: Long = (SystemClock.elapsedRealtime() - binding.chronometer.base)
                if (elapsedMillis / countIntervalChangeColor in 20000..20999) {
                    countIntervalChangeColor++
                    color = getColorForSettingsFragment()
                    Dispatchers.Main.let {
                        binding.fragmentSettings.setBackgroundColor(color)
                    }

                }
            }
        }
    }

    private fun timerStart() {
            chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
            chronometer.start()
    }

    private fun timerPause() {
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.base
            chronometer.stop()
    }

    private fun timerReset() {
            chronometer.base = SystemClock.elapsedRealtime()
            pauseOffSet = 0
            countIntervalChangeColor = 1
            chronometer.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        chronometerBaseTime = chronometer.base
        outState.putInt("color", color)
        outState.putLong("pauseOffSet", pauseOffSet)
        outState.putBoolean("isRunning", isRunning)
        outState.putInt("countIntervalChangeColor", countIntervalChangeColor)
        outState.putLong("ChronoTime", chronometerBaseTime)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        color = savedInstanceState?.getInt("color") ?: 1
        pauseOffSet = savedInstanceState?.getLong("pauseOffSet") ?: 0
        chronometerBaseTime = savedInstanceState?.getLong("ChronoTime") ?: 0
        isRunning = savedInstanceState?.getBoolean("isRunning") ?: false
        countIntervalChangeColor = savedInstanceState?.getInt("countIntervalChangeColor") ?: 1
    }

    override fun onDestroy() {
        chronometer.stop()
        super.onDestroy()
    }

    companion object {

        fun newInstance() = SettingsFragment()
    }
}