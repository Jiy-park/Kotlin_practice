package com.example.kotlin_practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.kotlin_practice.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.LatLngBounds
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        Log.d("LOG_CHECK", "MapsActivity :: onMapReady() called")
        loadLibraries()
    }

    fun loadLibraries(){
        Log.d("LOG_CHECK", "MapsActivity :: loadLibraries() called1")
        val retrofit = Retrofit.Builder()
            .baseUrl(SeoulOpenApi.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val seoulOpenService = retrofit.create(SeoulOpenService::class.java)
        Log.d("LOG_CHECK", "MapsActivity :: loadLibraries() called2")
        seoulOpenService
            .getLibrary(SeoulOpenApi.API_KEY)
            .enqueue(object : Callback<Library>{
                override fun onFailure(call: Call<Library>, t: Throwable) {
                    Log.d("LOG_CHECK", "MapsActivity :: onFailure() called")
                    Toast.makeText(baseContext, "서버에서 데이터를 가져올 수 없습니다.",Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<Library>, response: Response<Library>) {
                    Log.d("LOG_CHECK", "MapsActivity :: onResponse() called")
                    showLibraries(response.body() as Library)
                }
            })
        Log.d("LOG_CHECK", "MapsActivity :: loadLibraries() called3")
    }

    fun showLibraries(libraries: Library){
        Log.d("LOG_CHECK", "MapsActivity :: showLibraries() called")
        val latLngBounds = LatLngBounds.Builder()
        for(lib in libraries.SeoulPublicLibraryInfo.row){
            val position = LatLng(lib.XCNTS.toDouble(), lib.YDNTS.toDouble())
            val marker = MarkerOptions()
                .position(position)
                .title(lib.LBRRY_NAME)
            mMap.addMarker(marker)
            latLngBounds.include(marker.position)
        }
        val bounds = latLngBounds.build()
        val padding = 0
        val update = CameraUpdateFactory.newLatLngBounds(bounds, padding)
        mMap.moveCamera(update)

    }
}