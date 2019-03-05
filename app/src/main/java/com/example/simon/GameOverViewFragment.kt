package com.example.simon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.game_over.*
import kotlinx.android.synthetic.main.game_over.view.*

class GameOverViewFragment: Fragment() {

    interface GameOverListener{
        fun restartButtonPressed()
        fun exitButtonPressed()
    }
    var listener: GameOverListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.game_over, container, false)

        view.restartButton.setOnClickListener {
            listener?.restartButtonPressed()
        }

        view.exitButton.setOnClickListener {
            listener?.exitButtonPressed()
        }
        return view
    }

}