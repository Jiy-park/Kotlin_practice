package com.example.kotlin_practice

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class CustomText:AppCompatTextView {
    fun process(delimeter:String){
        val one = text.substring(0,4)
        val two = text.substring(4,6)
        val three = text.substring(6)
        val settingText = one+delimeter+two+delimeter+three
        setText(settingText)
    }
    constructor(context: Context):super(context){

    }
    constructor(context: Context,attrs:AttributeSet):super(context,attrs){
        val typed = context.obtainStyledAttributes(attrs,R.styleable.CustomText)//attrs.xml에 정의된 속성(어트리뷰트)을 가져옴
        val size = typed.indexCount // 이건 배열의 사이즈인듯                       //아마 정의된 모든 속성을 배열의 형태로 가져오는거 같은데

        for(i in 0 until size){
            when(typed.getIndex(i)){
                R.styleable.CustomText_delimeter ->{ // 현재 속성을 확인하고 delimeter와 같으면
                    val delimeter = typed.getString(typed.getIndex(i))?:"-" // typed 배열에서 typed.getIndex(i)에 해당하는 문자열을 delimeter 변수에 대입
                    process(delimeter)                                      //default 는 "-"
                }
            }
        }
    }
    constructor(context: Context, attrs: AttributeSet, defStyle:Int):super(context,attrs,defStyle){

    }
}

