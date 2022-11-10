package com.nirwashh.android.mycomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.nirwashh.android.mycomposition.R
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
        binding.btnRetry.setOnClickListener { retryGame() }
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
            imgResult.setImageResource(setResultImage())
            val requiredAnswers = gameResult.gameSettings.minCountOfRightAnswers
            tvRequiredAnswers.text =
                concatenateString(R.string.text_required_answers, requiredAnswers)
            val requiredPercentage = gameResult.gameSettings.minPercentOfRightAnswers
            tvRequiredPercentage.text =
                concatenateString(R.string.text_required_percentage, requiredPercentage)
            val score = gameResult.countOfRightAnswers
            tvScoreAnswers.text = concatenateString(R.string.text_your_score, score)
            val resultPercentage = getPercentOfRightAnswer(score)
            tvScorePercentage.text =
                concatenateString(R.string.text_right_percentage, resultPercentage)
        }
    }

    private fun getPercentOfRightAnswer(score: Int): Int {
        return if (score == 0) 0
        else ((score / gameResult.countOfQuestions.toDouble()) * 100).toInt()
    }


    private fun setResultImage(): Int {
        val isWin = gameResult.winner
        return if (isWin) R.drawable.ic_win
        else R.drawable.ic_loose

    }

    private fun concatenateString(resStringId: Int, value: Int) =
        String.format(
            requireContext().resources.getString(resStringId),
            value
        )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_RESULT)?.let {
            gameResult = it
        }
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
            args.putParcelable(KEY_RESULT, gameResult)
            val fragment = GameFinishedFragment()
            fragment.arguments = args
            return fragment
        }
    }
}