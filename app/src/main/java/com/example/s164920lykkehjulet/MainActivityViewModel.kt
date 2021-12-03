package com.example.s164920lykkehjulet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){
    private var _guessLetter = MutableLiveData("")
    var guessLetter: MutableLiveData<String> = _guessLetter

    private var _buttonBool = MutableLiveData(false)
    var buttonBool: MutableLiveData<Boolean> = _buttonBool

}