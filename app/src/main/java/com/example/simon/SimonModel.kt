package com.example.simon

import android.util.Log
import kotlin.random.Random


class SimonModel {

//    private var totalListOfAnswers = List(10) { Random.nextInt(0, 4) }
    var totalListOfAnswers = mutableListOf<Int>() //( Random.nextInt(0,4) )
    private var userPosition = 0
    private var currentScore = 0
    private var highScore = 0
    private var totalDuration: Long = 0

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

    fun addAnswer() {
        totalListOfAnswers.add(Random.nextInt(0,4))
    }

    fun getNextAnswer() {

    }

    fun checkAnswer(guess: Int){
        if( totalListOfAnswers[userPosition] != guess ) { //Lose condition
            Log.e("TAG","Incorrect answer")
        }
        else {
            Log.e("TAG","Correct answer")
            //addAnswer()
            advanceUserPosition()
        }
    }

    fun getAnswers(): List<Int> {
        userPosition = 0
        addAnswer()
        return totalListOfAnswers
    }

    //Can simplify this later with (position + 1) % totalListOfAnswers.count()
    private fun advanceUserPosition() {
        if (userPosition != totalListOfAnswers.count()) {
            userPosition++
        }
        else {
            Log.e("TAG", "Reached Limit, restarting")
            userPosition = 0
        }
    }

}
//fun getNextAnswer(): Int{
//        if(answerPosition == totalListOfAnswers.count()){
//            return -1
//        }
//        return answerPosition
//    }