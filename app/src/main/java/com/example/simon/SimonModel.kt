package com.example.simon

import android.util.Log
import kotlin.random.Random


class SimonModel {

//    private var totalListOfAnswers = List(10) { Random.nextInt(0, 4) }
    private var totalListOfAnswers = mutableListOf<Int>() //( Random.nextInt(0,4) )
    private var userPosition = 0
    private var currentScore = 0
    private var highScore = 0
    private var totalDuration: Long = 0
    var newRound = ( userPosition == totalListOfAnswers.count() )

    fun gameOver() {
        //getScore()
        //
    }

    fun setCurrentDuration(duration: Long) {
        totalDuration = duration
    }

    fun getDuration(): Long {
        return totalDuration
    }

    private fun addAnswer() {
        totalListOfAnswers.add(Random.nextInt(0,4))
    }

    fun getNextAnswer() {

    }

    fun checkAnswer(guess: Int): Boolean {

//        if (userPosition > totalListOfAnswers.count()) {
//            Log.e("TAG","Out of bounds")
//            userPosition = 0
//            return false
//        }

        if( totalListOfAnswers[userPosition] != guess ) { //Lose condition
            Log.e("TAG","Incorrect answer")
            advanceUserPosition()
            return false
        }

        else {
            Log.e("TAG","Correct answer")
            //incrementScore()
            advanceUserPosition()
            return true
        }

    }

    fun getAnswers(): List<Int> {
        //userPosition = 0
        addAnswer()
        return totalListOfAnswers
    }

    private fun advanceUserPosition() {
        //userPosition = (userPosition + 1) % totalListOfAnswers.count()
        userPosition = userPosition + 1
    }

}
//fun getNextAnswer(): Int{
//        if(answerPosition == totalListOfAnswers.count()){
//            return -1
//        }
//        return answerPosition
//    }