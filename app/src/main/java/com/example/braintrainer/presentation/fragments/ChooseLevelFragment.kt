package com.example.braintrainer.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.braintrainer.R
import com.example.braintrainer.databinding.FragmentChooseLevelBinding
import com.example.braintrainer.domain.entities.GameLevel


class ChooseLevelFragment : Fragment() {
    private lateinit var binding: FragmentChooseLevelBinding
    private val args by navArgs<ChooseLevelFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.testMode) {
            binding.levelTest.visibility = View.VISIBLE
        }

        with(binding) {
            levelEasy.setOnClickListener {
                launchGameFragment(GameLevel.EASY)
            }
            levelNormal.setOnClickListener {
                launchGameFragment(GameLevel.NORMAL)
            }
            levelHard.setOnClickListener {
                launchGameFragment(GameLevel.HARD)
            }
            levelImpossible.setOnClickListener {
                launchGameFragment(GameLevel.IMPOSSIBLE)
            }
            levelTest.setOnClickListener {
                launchGameFragment(GameLevel.TEST)
            }
        }
    }

    private fun launchGameFragment(level: GameLevel) {
        findNavController().navigate(
            ChooseLevelFragmentDirections.actionChooseLevelFragmentToGameFragment(level)
        )
    }

}