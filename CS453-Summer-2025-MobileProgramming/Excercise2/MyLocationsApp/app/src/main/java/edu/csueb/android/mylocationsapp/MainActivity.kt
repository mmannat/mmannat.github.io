package edu.csueb.android.mylocationsapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    private val LOCATION_UNIV = LatLng(37.6550, -122.0575) // CSU East Bay
    private val LOCATION_CS = LatLng(37.6555, -122.0585) // CS Building
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map!!.addMarker(MarkerOptions().position(LOCATION_CS).title("Find Me Here!"))
    }

    fun onClick_CS(v: View?) {
        map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        val update = CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18f)
        map!!.animateCamera(update)
    }

    fun onClick_Univ(v: View?) {
        map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        val update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14f)
        map!!.animateCamera(update)
    }

    fun onClick_City(v: View?) {
        map!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
        val update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10f)
        map!!.animateCamera(update)
    }
}