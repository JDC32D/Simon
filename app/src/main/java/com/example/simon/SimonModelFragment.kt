package com.example.simon

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment

class SimonModelFragment:Fragment() {

    var listener: Listener? = null
    private var handler: Handler? = null
    var isRunning = false
    private set

    private var runnable: Runnable = Runnable {
        listener?.sequenceTriggered()
    }

    private var resume: Runnable = Runnable {
        listener?.sequenceComplete()
    }

    interface Listener {
        fun sequenceTriggered()
        fun sequenceComplete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /*
        retainInstance prevents the fragment from being destroyed
        Typically, you should not put other view code in here once retainInstance is true
        This will eventually be replaced by viewModel
         */
        retainInstance = true
        Log.e("TAG", "Model was created")
    }

//    private fun triggerSequence() {
//        handler?.postDelayed(runnable, 1200)
//    }

    fun startSequence(duration: Long) {
        if(handler == null){
            handler = Handler() //"so, ill sign it"??
            handler?.postDelayed(runnable, 1200)
            handler?.postDelayed(resume, duration+1200)
            isRunning = true
        }
        //listener?.sequenceTriggered()
        handler = null
    }


//    override fun onPause(){
//        super.onPause()
//        Log.e("TAG", "onPause")
//        handler?.removeCallbacks(runnable)
//        handler = null
//    }

//    override fun onResume(){
//        super.onResume()
//        Log.e("TAG", "onResume")
//        if (isRunning){
//            startSequence()
//        }
//    }

    fun stopSequence() {
        handler?.removeCallbacks(runnable)
        isRunning = false
        handler = null
    }

    override fun onDestroy() {
        stopSequence()
        super.onDestroy()
        Log.e("TAG","Fragment destroyed")
    }
}