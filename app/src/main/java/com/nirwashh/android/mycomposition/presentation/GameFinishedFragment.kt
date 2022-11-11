package com.nirwashh.android.mycomposition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.nirwashh.android.mycomposition.R
import com.nirwashh.android.mycomposition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {
    private var _binding: FragmentGameFinishedBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<GameFinishedFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        binding.btnRetry.setOnClickListener { retryGame() }
    }


    private fun bindViews() {
        with(binding) {
            imgResult.setImageResource(setResultImage())
            val requiredAnswers = args.result.gameSettings.minCountOfRightAnswers
            tvRequiredAnswers.text =
                concatenateString(R.string.text_required_answers, requiredAnswers)
            val requiredPercentage = args.result.gameSettings.minPercentOfRightAnswers
            tvRequiredPercentage.text =
                concatenateString(R.string.text_required_percentage, requiredPercentage)
            val score = args.result.countOfRightAnswers
            tvScoreAnswers.text = concatenateString(R.string.text_your_score, score)
            val resultPercentage = getPercentOfRightAnswer(score)
            tvScorePercentage.text =
                concatenateString(R.string.text_right_percentage, resultPercentage)
        }
    }

    private fun getPercentOfRightAnswer(score: Int): Int {
        return if (score == 0) 0
        else ((score / args.result.countOfQuestions.toDouble()) * 100).toInt()
    }


    private fun setResultImage(): Int {
        val isWin = args.result.winner
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

    private fun retryGame() {
        findNavController().popBackStack()
    }
}