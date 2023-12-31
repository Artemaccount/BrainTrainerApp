package com.example.braintrainer.presentation.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.TimeInterpolator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.braintrainer.databinding.FragmentWelcomeBinding
import com.example.braintrainer.R as myR


class WelcomeFragment : Fragment() {

    private lateinit var binding: FragmentWelcomeBinding
    private var count = 0
    private var testMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun View.levitate(y: Float, duration: Long) {
        val yourInterpolator: TimeInterpolator = DecelerateInterpolator()
        this.animate().translationYBy(y).setDuration(duration)
            .setInterpolator(yourInterpolator).setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    levitate(-y, duration)
                }
            })
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.brainImage.levitate(50f, 2000)
        binding.startButton.setOnClickListener {
            launchChooseLevelFragment()
        }
        binding.brainImage.setOnClickListener {
            count++
            if (count == 5) {
                setTestMode()
            }
        }
    }

    private fun setTestMode() {
        testMode = true
        Toast.makeText(requireContext(), "Тестовый режим установлен!", Toast.LENGTH_SHORT)
            .show()
    }

    private fun launchChooseLevelFragment() {
        findNavController().navigate(
            WelcomeFragmentDirections.actionWelcomeFragmentToChooseLevelFragment(
                testMode
            )
        )
    }
}