package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.simon_layout.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    val simonModel = SimonModel()

    private fun disableRadioMenu() {
        getDifficulty()

        easyRadioButton.isClickable = false
        normalRadioButton.isClickable = false
        hardRadioButton.isClickable = false

        easyRadioButton.isVisible = false
        normalRadioButton.isVisible = false
        hardRadioButton.isVisible = false
    }

    private fun disableButtonClicks() {
        //startButton.isClickable = false
        redButton.isClickable = false
        greenButton.isClickable = false
        yellowButton.isClickable = false
        blueButton.isClickable = false
    }

    private fun enableButtonClicks() {
        //startButton.isClickable = true
        redButton.isClickable = true
        greenButton.isClickable = true
        yellowButton.isClickable = true
        blueButton.isClickable = true
    }

    private fun disableStartButton() {
        startButton.isClickable = false
        startButton.isVisible = false
    }

    private fun enableStartButton() {
        startButton.isClickable = true
        startButton.isVisible = true
    }

    //Use this to attach the SimonModelFragment to the activity
    companion object {
        const val SIMON_FRAG_TAG = "SimonFragment"
    }

    //Need a reference to my view fragment
    private var viewFragment: SimonViewFragment? = null
    private var modelFragment: SimonModelFragment? = null
    private var gameOverFragment: GameOverViewFragment? = null
    private val transaction = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Game over fragment is not yet commited with transaction
        gameOverFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as? GameOverViewFragment
        if (gameOverFragment == null)
        {
            gameOverFragment = GameOverViewFragment()
        }

        gameOverFragment?.listener = object : GameOverViewFragment.GameOverListener {

            override fun restartButtonPressed() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        }

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
                Log.e("TAG", "Delegated from the SimonViewFrag to the Controller")
                disableRadioMenu()
                disableStartButton()
                disableButtonClicks()
                modelFragment?.startSequence(simonModel.getDuration())
                Log.e("TAG","Disabled button clicks")
            }

            override fun greenButtonPressed() {
                checkAnswer(0)
            }

            override fun redButtonPressed() {
                checkAnswer(1)
            }

            override fun yellowButtonPressed() {
                checkAnswer(2)
            }

            override fun blueButtonPressed() {
               checkAnswer(3)
            }

            override fun sequenceComplete() {
                enableButtonClicks()
                Log.e("TAG", "Enabled button clicks")
            }

            override fun getDuration(time: Long) {
                simonModel.setCurrentDuration(time)
            }

            override fun updateScoreText() {
                resources.getString(R.string.score_text, simonModel.getCurrentScore())
            }

        }
        modelFragment?.listener = modelListener

    }


    private fun showGameOverScreen(){
        transaction.replace(R.id.mainContainer, gameOverFragment!!)
        transaction.addToBackStack("GameOver")
        transaction.commit()
    }



    private val modelListener = object : SimonModelFragment.Listener {
        override fun sequenceTriggered() {
            Log.e("TAG", "Delegated from the SimonModelFrag to the Controller")
            viewFragment?.runUIUpdate(simonModel.getAnswers())

        }

        override fun sequenceComplete() {
            Log.e("TAG", "SequenceComplete Called")
            viewFragment?.listener?.sequenceComplete()
        }
    }

    fun checkAnswer(guess: Int) {

        if ( simonModel.checkAnswer(guess) ) {
            Toast.makeText( this@MainActivity, "Correct", Toast.LENGTH_SHORT).show()
            //viewFragment?.listener?.updateScoreText()
        } else {
            showGameOverScreen()
        }

        if ( simonModel.newRound() ){
            Log.e("TAG", "Position = answers.count()")
            disableButtonClicks()
            enableStartButton()
            Toast.makeText(this, "Press Start", Toast.LENGTH_LONG).show()
        }

    }


    private fun getDifficulty() {
        if(easyRadioButton.isChecked){
            simonModel.setDifficulty(1)
        }
        if(normalRadioButton.isChecked) {
            simonModel.setDifficulty(2)
        }
        if(hardRadioButton.isChecked) {
            simonModel.setDifficulty(3)
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
