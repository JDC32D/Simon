package com.example.simon

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.simon_layout.*
import kotlinx.android.synthetic.main.simon_layout.view.*

class SimonViewFragment: Fragment() {

    //Need to communicate to the Activity
    interface SimonListener {
        fun startButtonPressed()
        fun greenButtonPressed()
        fun redButtonPressed()
        fun yellowButtonPressed()
        fun blueButtonPressed()
        //fun getNextAnswer(): Int
    }
    var listener: SimonListener? = null


    //Give the fragment a view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //attachToRoot must be false, or nothing will draw to the screen
        //container is the ID from the XML file (37:30)
        val view = inflater.inflate(R.layout.simon_layout, container, false)


        view.startButton.setOnClickListener{
            Log.e("TAG", "Pressed ${it}")
            listener?.startButtonPressed()
        }

        view.greenButton.setOnClickListener {
            Log.e("TAG", "Pressed ${it}")
        }

        view.redButton.setOnClickListener{
            Log.e("TAG", "Pressed ${it}")
        }

        view.yellowButton.setOnClickListener {
            Log.e("TAG", "Pressed ${it}")
        }

        view.blueButton.setOnClickListener {
            Log.e("TAG", "Pressed ${it}")
        }

        return view
    }

    //Only flashes 4 times?
    fun runUIUpdate(answers: List<Int>) {
        activity?.let { activity ->
            for (index in 0 until answers.size) {
                val view = when(answers[index]) {
                    0 -> greenButton
                    1 -> redButton
                    2 -> yellowButton
                    else -> blueButton
                }

                val originalColor = view.background as? ColorDrawable
                val flash = ContextCompat.getColor(activity, R.color.flashColor)

                val animator = ValueAnimator.ofObject(
                    ArgbEvaluator(),
                    originalColor?.color,
                    flash,
                    originalColor?.color
                )

                animator.addUpdateListener { valueAnimator ->
                    (valueAnimator.animatedValue as? Int)?.let {
                        view.setBackgroundColor(it)
                    }
                }
                animator.startDelay = (index * 1000).toLong()
                animator?.start()
                println(answers[index])
            }
        }
    }
}
//                val red = ContextCompat.getColor(activity, R.color.redColor)
//                val green = ContextCompat.getColor(activity, R.color.greenColor)
//                val blue = ContextCompat.getColor(activity, R.color.blueColor)
//                val yellow = ContextCompat.getColor(activity, R.color.yellowColor)