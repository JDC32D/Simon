package com.example.simon

import kotlin.random.Random


class SimonModel {

    val totalListOfAnswers = List(10) { Random.nextInt(0, 4) }
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

    fun getAnswers(): List<Int> {
        return totalListOfAnswers
    }

    fun advanceUserPosition() {
        userPosition = userPosition + 1
    }

}