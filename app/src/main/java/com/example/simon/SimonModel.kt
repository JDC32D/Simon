package com.example.simon

import android.util.Log
import kotlin.random.Random


class SimonModel {

    val totalListOfAnswers = List(10) { Random.nextInt(0, 4) }
    //val totalListOfAnswers = List[3,2,1,0]
    var answerPosition = 0
    var userAnswers = List( 10 ) {}
    var userPosition = 0
    private set

    fun getNextAnswer(): Int{
        if(answerPosition == totalListOfAnswers.count()){
            return -1
        }
        return answerPosition
    }

    fun checkAnswer(guess: Int){
        if( totalListOfAnswers[userPosition] != guess ) {
            Log.e("TAG","Incorrect answer")
            //advanceUserPosition()
            //return false
        }
        else {
            Log.e("TAG","Correct answer")
            advanceUserPosition()
            //return true
        }
    }

    fun getAnswers(): List<Int> {
        return totalListOfAnswers
    }

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