package com.example.kotlin_practice

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.kotlin_practice.databinding.ActivityMapsBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    lateinit var locationPermission: ActivityResultLauncher<Array<String>>
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        Log.d("LOG_CHECK", "MapsActivity :: onCreate() called")

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        locationPermission = registerForActivityResult( ActivityResultContracts.RequestMultiplePermissions()){ results->
            if(results.all { it.value }) { startProcess() }
            else { Toast.makeText(this@MapsActivity, "권한이 필요합니다.",Toast.LENGTH_SHORT).show() }
        }
        locationPermission.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )




    }

    fun startProcess(){
        Log.d("LOG_CHECK", "MapsActivity :: startProcess() called")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@MapsActivity)
    }

    @SuppressLint("MissingPermission")
    fun updateLocation(){
        Log.d("LOG_CHECK", "MapsActivity :: updateLocation() called")
        val locationRequest = LocationRequest.create()
        locationRequest.run {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000
        }
        locationCallback = object:LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let {
                    for((i, location) in it.locations.withIndex()){
                        Log.d("LOG_CHECK", "$i, ${location.latitude} , ${location.longitude}")
                        setLastLocation(location)
                    }
                }
            }
        }
    }

    fun setLastLocation(lastLocation: Location){
        Log.d("LOG_CHECK", "MapsActivity :: setLastLocation() called")
        val LATLNG = LatLng(lastLocation.latitude, lastLocation.longitude)
        val markerOption = MarkerOptions()
            .position(LATLNG)
            .title("Here!!")

        val cameraPosition = CameraPosition.Builder()
            .target(LATLNG)
            .zoom(15.0F)
            .build()

        mMap.clear()
        mMap.addMarker(markerOption)
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("LOG_CHECK", "MapsActivity :: onMapReady() called")
        mMap = googleMap
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this@MapsActivity)
        updateLocation()

    }
}