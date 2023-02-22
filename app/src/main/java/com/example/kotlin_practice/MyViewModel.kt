package com.example.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel():ViewModel() {
    private val _value = MutableLiveData<Int>()

    val value:LiveData<Int>
        get() = _value

    init {
        _value.value = 0
    }

    fun plus(){
        _value.value = _value.value?.plus(1)
    }
}