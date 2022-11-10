package com.nirwashh.android.mycomposition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nirwashh.android.mycomposition.R
import com.nirwashh.android.mycomposition.databinding.FragmentGameBinding
import com.nirwashh.android.mycomposition.domain.entity.GameResult
import com.nirwashh.android.mycomposition.domain.entity.GameSettings
import com.nirwashh.android.mycomposition.domain.entity.Level

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var level: Level

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
        Log.d("GameFragment", requireArguments().toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvSum.setOnClickListener {
                launchGameFinishedFragment(level)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun launchGameFinishedFragment(level: Level) {
        val gameResult = when (level) {
            Level.TEST -> GameResult(
                true,
                0,
                0,
                GameSettings(
                    0,
                    0,
                    0,
                    0
                )
            )
            Level.EASY -> GameResult(
                true,
                1,
                0,
                GameSettings(
                    0,
                    0,
                    0,
                    0
                )
            )
            Level.NORMAL -> GameResult(
                true,
                2,
                0,
                GameSettings(
                    0,
                    0,
                    0,
                    0
                )
            )
            Level.HARD -> GameResult(
                true,
                3,
                0,
                GameSettings(
                    0,
                    0,
                    0,
                    0
                )
            )
        }
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    companion object {
        private const val KEY_LEVEL = "key_level"
        const val NAME = "GameFragment"
        fun newInstance(level: Level): GameFragment {
            val args = Bundle()
            args.putParcelable(KEY_LEVEL, level)
            val fragment = GameFragment()
            fragment.arguments = args
            return fragment
        }
    }
}