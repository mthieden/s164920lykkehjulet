package com.example.s164920lykkehjulet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel(){
    private val _secretWord = MutableLiveData("")
    val secretWord: LiveData<String> = _secretWord

    private val _categories = MutableLiveData("{'film':['dune', 'batman'],'sport':['fodbold']}")
    val categories: LiveData<String> = _categories

    private val _letters = MutableLiveData("{'A':'HIDDEN'," +
            "'B':'HIDDEN'," +
            "'C':'HIDDEN'," +
            "'D':'HIDDEN'," +
            "'E':'HIDDEN'," +
            "'F':'HIDDEN'," +
            "'G':'HIDDEN'," +
            "'H':'HIDDEN'," +
            "'I':'HIDDEN'," +
            "'J':'HIDDEN'," +
            "'K':'HIDDEN'," +
            "'L':'HIDDEN'," +
            "'M':'HIDDEN'," +
            "'N':'HIDDEN'," +
            "'O':'HIDDEN'," +
            "'P':'HIDDEN'," +
            "'Q':'HIDDEN'," +
            "'R':'HIDDEN'," +
            "'S':'HIDDEN'," +
            "'T':'HIDDEN'," +
            "'U':'HIDDEN'," +
            "'V':'HIDDEN'," +
            "'W':'HIDDEN'," +
            "'X':'HIDDEN'," +
            "'Y':'HIDDEN'," +
            "'Z':'HIDDEN'," +
            "'Æ':'HIDDEN'," +
            "'Ø':'HIDDEN'," +
            "'Å':'HIDDEN'}")
    val letters: LiveData<String> = _letters


}