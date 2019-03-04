package com.example.simon

import android.util.Log
import kotlin.random.Random


class SimonModel {

    private var totalListOfAnswers = ArrayList<Int>()
    private var userPosition = 0
    private var currentScore = 0
    private var highScore = 0
    private var totalDuration: Long = 0
    private var difficulty = 0

    fun setDifficulty(diffLevel: Int){
        difficulty = diffLevel
    }

    private fun addAnswer() {
        var index = 0
        while(index < difficulty) {
            index++
            totalListOfAnswers.add(Random.nextInt(0, 4))
        }
    }

    private fun incrementScore() {
        currentScore = (currentScore + 1) * difficulty
        if (currentScore > highScore) {
            highScore = currentScore
        }
    }

    fun getCurrentScore(): Int {
        return currentScore
    }

    fun getHighScore(): Int {
        return highScore
    }

    fun newRound() : Boolean{
        return userPosition == totalListOfAnswers.count()
    }

    fun setCurrentDuration(duration: Long) {
        totalDuration = duration
    }

    fun getDuration(): Long {
        return totalDuration
    }

    fun checkAnswer(guess: Int): Boolean {

//        if (userPosition > totalListOfAnswers.count()) {
//            Log.e("TAG","Out of bounds")
//            userPosition = 0
//            return false
//        }

        if( totalListOfAnswers[userPosition] != guess ) { //Lose condition
            Log.e("TAG","Incorrect answer")
            //advanceUserPosition()
            return false
        }

        else {
            Log.e("TAG","Correct answer")
            incrementScore()
            advanceUserPosition()
            return true
        }

    }

    fun getAnswers(): List<Int> {
        userPosition = 0
        addAnswer()
        return totalListOfAnswers
    }

    private fun advanceUserPosition() {
        userPosition = userPosition + 1
    }

}
//fun getNextAnswer(): Int{
//        if(answerPosition == totalListOfAnswers.count()){
//            return -1
//        }
//        return answerPosition
//    }