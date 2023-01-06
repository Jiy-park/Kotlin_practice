package com.example.kotlin_practice

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FIleUtil {
    fun readTextFile(fullPath:String):String{
        val file = File(fullPath)
        if(file.exists() == false) { return "" }
        val reader = FileReader(file)
        val buffer = BufferedReader(reader)

        var temp = ""
        val result = StringBuffer()
        while(true){
            temp = buffer.readLine()
            if(temp == null) { break }
            else { result.append(buffer) }
        }
        buffer.close()
        return result.toString()
    }

    fun writeTextFile(derectory:String, fileName:String, content:String){
        val dir = File(derectory)
        if(dir.exists() == false) { dir.mkdirs() }
        val fileRoot = derectory + "/" + fileName
        val writer = FileWriter(fileRoot)
        val buffer = BufferedWriter(writer)
        buffer.write(content)
        buffer.close()
    }
}