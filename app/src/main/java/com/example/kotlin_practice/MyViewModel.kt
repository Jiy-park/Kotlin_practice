package com.example.kotlin_practice;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel():ViewModel() {
    private val _value = MutableLiveData<Int>()
    private val _value2 = MutableLiveData<Int>()

    val value:LiveData<Int>
        get() = _value
    val value2:LiveData<Int>
        get() = _value2


    init {
        _value.value = 0
        _value2.value = 0
    }

    fun plus(){
        _value.value = _value.value?.plus(1)
    }
    fun setValue(){
        _value2.value = _value.value
    }
}