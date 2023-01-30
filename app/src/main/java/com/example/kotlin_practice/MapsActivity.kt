package com.example.kotlin_practice

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.kotlin_practice.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition

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

        val bitmapDrawable:BitmapDrawable // 마커 이미지 변경 ** 롤리팝 버전 이하는 resources로 변경해야 함


        bitmapDrawable = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) getDrawable(R.drawable.marker) as BitmapDrawable //이거 marker.png 사이즈가 너무 큼
                            else resources.getDrawable(R.drawable.marker) as BitmapDrawable

        val scaledBitmap = Bitmap.createScaledBitmap(bitmapDrawable.bitmap, 50, 50, false) //비트맵 스케일링

        val discriptor = BitmapDescriptorFactory.fromBitmap(scaledBitmap)


        val LATLNG = LatLng(37.820981, 127.100627) //위도 경도
        val camera = CameraPosition.builder()
            .target(LATLNG).zoom(15.0F)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(camera)
        mMap.moveCamera(cameraUpdate)


        val markerOptions = MarkerOptions()
            .position(LATLNG)
            .icon(discriptor)
            .title("MY HOME")
            .snippet("37.820981, 127.100627")//이건 하나만 적용되는듯?
        mMap.addMarker(markerOptions)
    }
}