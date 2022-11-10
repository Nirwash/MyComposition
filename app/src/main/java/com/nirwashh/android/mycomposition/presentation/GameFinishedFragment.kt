package com.nirwashh.android.mycomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nirwashh.android.mycomposition.databinding.FragmentGameFinishedBinding
import com.nirwashh.android.mycomposition.domain.entity.GameResult

class GameFinishedFragment : Fragment() {
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseResult()
        onBackPressed()
    }

    private fun onBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }
            })
    }

    private fun parseResult() {
        with(binding) {
            val requiredAnswers = gameResult.gameSettings.minCountOfRightAnswers
            tvRequiredAnswers.text = requiredAnswers.toString()
            val requiredPercentage = gameResult.gameSettings.minPercentOfRightAnswers
            tvRequiredPercentage.text = requiredPercentage.toString()
            val score = gameResult.countOfRightAnswers
            tvScoreAnswers.text = score.toString()
            val resultPercentage = 1//100 / (requiredAnswers / score)
            tvScorePercentage.text = resultPercentage.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun parseArgs() {
        gameResult = requireArguments().getSerializable(KEY_RESULT) as GameResult
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(
            GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    companion object {
        private const val KEY_RESULT = "key_result"
        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            val args = Bundle()
            args.putSerializable(KEY_RESULT, gameResult)
            val fragment = GameFinishedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}