package com.example.simon

import android.content.ClipData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SimonViewModel: ViewModel() {
    var selectedDifficulty = MutableLiveData<Int>()
}