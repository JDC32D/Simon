package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.simon_layout.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val simonModel = SimonModel()

    private fun disableButtonClicks() {
        startButton.isClickable = false
        redButton.isClickable = false
        greenButton.isClickable = false
        yellowButton.isClickable = false
        blueButton.isClickable = false
    }

    private fun enableButtonClicks() {
        startButton.isClickable = true
        redButton.isClickable = true
        greenButton.isClickable = true
        yellowButton.isClickable = true
        blueButton.isClickable = true
    }

    //Use this to attach the SimonModelFragment to the activity
    companion object {
        const val SIMON_FRAG_TAG = "SimonFragment"
    }

    //Need a reference to my view fragment
    private var viewFragment: SimonViewFragment? = null
    private var modelFragment: SimonModelFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Set reference in the XML, so we need to search for it by ID
        viewFragment = supportFragmentManager.findFragmentById(R.id.mainContainer)as? SimonViewFragment

        //If the view fragment has not been attached yet, it will be null, so we need to add it
        if(viewFragment == null) {
            Log.e("TAG","View Fragment does not exist")
            viewFragment = SimonViewFragment()
            supportFragmentManager.beginTransaction()
                //When passing a view, pass ID first
                .add(R.id.mainContainer, viewFragment!!)
                .commit()
        }

        modelFragment = supportFragmentManager.findFragmentByTag(SIMON_FRAG_TAG) as? SimonModelFragment

        if(modelFragment == null) {
            modelFragment = SimonModelFragment()
            supportFragmentManager.beginTransaction()
                /*
                When passing headless, pass fragment first
                That is because there is no ID to attach to, so we have to attach it to something
                 */
                .add(modelFragment!!, SIMON_FRAG_TAG)
                .commit()
        }

        //viewFragment?.listener = this
        viewFragment?.listener = object : SimonViewFragment.SimonListener {
            override fun startButtonPressed() {
                Log.e("TAG", "Delegated from the View to the Controller")
                disableButtonClicks()
                modelFragment?.startSequence(simonModel.getDuration())
                Log.e("TAG","Disabled button clicks")
            }

            override fun greenButtonPressed() {
                simonModel.checkAnswer(0)
            }

            override fun redButtonPressed() {
                simonModel.checkAnswer(1)
            }

            override fun yellowButtonPressed() {
                simonModel.checkAnswer(2)
            }

            override fun blueButtonPressed() {
                simonModel.checkAnswer(3)
            }

            override fun sequenceComplete() {
                enableButtonClicks()
                Log.e("TAG", "Enable button clicks")
            }

            override fun getDuration(time: Long) {
                simonModel.setCurrentDuration(time)
            }

        }
        modelFragment?.listener = modelListener
    }

    private val modelListener = object : SimonModelFragment.Listener {
        override fun sequenceTriggered() {
            Log.e("TAG", "Delegated from the Model to the Controller")
            viewFragment?.runUIUpdate(simonModel.getAnswers())

        }

        override fun sequenceComplete() {
            Log.e("TAG", "SequenceComplete Called")
            viewFragment?.listener?.sequenceComplete()
        }
    }

    /*
    CTRL+I brings up a quick way to implement all these
    I can do this because I am implementing the SimonViewFragment.SimonListener
     */
//    override fun startButtonPressed() {
//        Log.e("TAG", "Delegated from the View to the Controller")
//        modelFragment?.startSequence()
//        modelFragment?.stopSequence()
//    }
//
//    override fun greenButtonPressed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun redButtonPressed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun yellowButtonPressed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun blueButtonPressed() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

//    override fun sequenceTriggered() {
//        Log.e("TAG", "Delegated from the Model to the Controller")
//    }
}
