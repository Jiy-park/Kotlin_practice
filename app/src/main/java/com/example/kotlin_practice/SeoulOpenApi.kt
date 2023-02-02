package com.example.kotlin_practice

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

class SeoulOpenApi {
    companion object{
        val DOMAIN = "http://openAPI.seoul.go.kr:8088/"
        val API_KEY = "4c435378647067793132347476514157"
    }
}

interface SeoulOpenService{
//    @GET("/json/SeoulPublicLibraryInfo/1/200")
//    fun getLibrary(key:String): Call<Library>
    //여러개의 API 키를 사용할 수 있도록 변수와 같이 사용
    @GET("{api_key}/json/SeoulPublicLibraryInfo/1/200")
    fun getLibrary(@Path("api_key") key:String):Call<Library>
}