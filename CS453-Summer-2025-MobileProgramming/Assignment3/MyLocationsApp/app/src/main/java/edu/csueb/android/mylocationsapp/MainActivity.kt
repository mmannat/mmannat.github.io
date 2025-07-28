package edu.csueb.android.mylocationsapp

import android.content.ContentValues
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback,
    LoaderManager.LoaderCallbacks<Cursor> {

    private val LOCATION_UNIV = LatLng(37.6550, -122.0575)
    private val LOCATION_CS = LatLng(37.6555, -122.0585)
    private var map: GoogleMap? = null
    private val LOADER_ID = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        LoaderManager.getInstance(this).initLoader(LOADER_ID, null, this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map?.setOnMapClickListener { point ->
            map?.addMarker(MarkerOptions().position(point).title("New Marker"))

            val values = ContentValues().apply {
                put(LocationsDB.COLUMN_LAT, point.latitude)
                put(LocationsDB.COLUMN_LNG, point.longitude)
                put(LocationsDB.COLUMN_ZOOM, map?.cameraPosition?.zoom ?: 10f)
            }

            LocationInsertTask().execute(values)
            Toast.makeText(this, "Marker added to map", Toast.LENGTH_SHORT).show()
        }

        map?.setOnMapLongClickListener {
            map?.clear()
            LocationDeleteTask().execute()
            Toast.makeText(this, "All markers removed", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class LocationInsertTask : AsyncTask<ContentValues, Void, Void>() {
        override fun doInBackground(vararg params: ContentValues?): Void? {
            contentResolver.insert(LocationsContentProvider.CONTENT_URI, params[0])
            return null
        }
    }

    private inner class LocationDeleteTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            contentResolver.delete(LocationsContentProvider.CONTENT_URI, null, null)
            return null
        }
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(this, LocationsContentProvider.CONTENT_URI, null, null, null, null)
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        data?.let { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val lat = cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDB.COLUMN_LAT))
                    val lng = cursor.getDouble(cursor.getColumnIndexOrThrow(LocationsDB.COLUMN_LNG))
                    val zoom = cursor.getFloat(cursor.getColumnIndexOrThrow(LocationsDB.COLUMN_ZOOM))

                    val point = LatLng(lat, lng)
                    map?.addMarker(MarkerOptions().position(point).title("Saved Location"))

                    if (cursor.isLast) {
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(point, zoom))
                    }
                } while (cursor.moveToNext())
            }
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {}

    fun onClick_CS(v: View?) {
        map?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_CS, 18f))
    }

    fun onClick_Univ(v: View?) {
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14f))
    }

    fun onClick_City(v: View?) {
        map?.mapType = GoogleMap.MAP_TYPE_SATELLITE
        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 10f))
    }
}
