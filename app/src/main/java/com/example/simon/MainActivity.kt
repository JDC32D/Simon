package com.example.simon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.simon_layout.*

class MainActivity : AppCompatActivity() {

    val simonModel = SimonModel()
    val simonViewModel = SimonViewModel()


    //Use this to attach the SimonModelFragment to the activity
    companion object {
        const val SIMON_FRAG_TAG = "SimonFragment"
    }

    //Need a reference to my view fragment
    private var viewFragment: SimonViewFragment? = null
    private var modelFragment: SimonModelFragment? = null
    private var gameOverFragment: GameOverViewFragment? = null

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

    fun disableRadioMenu() {
        getDifficulty()

        easyRadioButton.isClickable = false
        normalRadioButton.isClickable = false
        hardRadioButton.isClickable = false

        easyRadioButton.isVisible = false
        normalRadioButton.isVisible = false
        hardRadioButton.isVisible = false
    }

    fun enableRadioMenu() {
        easyRadioButton.isClickable = true
        normalRadioButton.isClickable = true
        hardRadioButton.isClickable = true

        easyRadioButton.isVisible = true
        normalRadioButton.isVisible = true
        hardRadioButton.isVisible = true
    }

    fun disableButtonClicks() {
        //startButton.isClickable = false
        redButton.isClickable = false
        greenButton.isClickable = false
        yellowButton.isClickable = false
        blueButton.isClickable = false
    }

    fun enableButtonClicks() {
        //startButton.isClickable = true
        redButton.isClickable = true
        greenButton.isClickable = true
        yellowButton.isClickable = true
        blueButton.isClickable = true
    }

    fun disableStartButton() {
        startButton.isClickable = false
        startButton.isVisible = false
    }

    fun enableStartButton() {
        startButton.isClickable = true
        startButton.isVisible = true
    }


    private fun showGameOverScreen(){
        gameOverFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as? GameOverViewFragment
        if (gameOverFragment == null) {
            gameOverFragment = GameOverViewFragment()
            supportFragmentManager.saveFragmentInstanceState(viewFragment)
            supportFragmentManager.beginTransaction()
                .remove(viewFragment!!)
                .add(R.id.mainContainer, gameOverFragment!!)
                .commit()
        }

        gameOverFragment?.listener = object : GameOverViewFragment.GameOverListener {

            override fun restartButtonPressed() {
                supportFragmentManager.beginTransaction()
                    .remove(gameOverFragment!!)
                    .add(R.id.mainContainer, viewFragment!!)
                    .commit()
                simonModel.restartGame()
                //Something is wrong with the state of my viewFragment
            }

            override fun exitButtonPressed() {
                finish()
            }
        }
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

}
