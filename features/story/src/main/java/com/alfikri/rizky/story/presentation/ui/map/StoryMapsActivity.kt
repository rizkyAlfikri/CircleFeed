package com.alfikri.rizky.story.presentation.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.alfikri.rizky.story.R
import com.alfikri.rizky.story.databinding.ActivityStoryMapsBinding
import com.alfikri.rizky.story.domain.model.StoryModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*

class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryMapsBinding
    private var storyModels = mutableListOf<StoryModel>()
    private val boundsBuilder = LatLngBounds.Builder()

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.getParcelableArrayListExtra<StoryModel>(STORY_LOCATION_DATA)?.let {
            storyModels.addAll(it)
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        getMyLocation()
        addManyMarker()
    }

    private fun getMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMap.isMyLocationEnabled = true
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getAddressName(lat: Double, lon: Double): String? {
        var addressName: String? = null
        val geocoder = Geocoder(this@StoryMapsActivity, Locale.getDefault())
        try {
            val list = geocoder.getFromLocation(lat, lon, 1)
            if (list != null && list.size != 0) {
                addressName = list[0].getAddressLine(0)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return addressName
    }

    private fun addManyMarker() {
        var isStoryLocationExist = false
        storyModels.forEach { story ->
            if (story.lat != null && story.lon != null) {
                val latLng = LatLng(story.lat, story.lon)
                val addressName = getAddressName(story.lat, story.lon)
                mMap.addMarker(MarkerOptions().position(latLng).title(story.name).snippet(addressName))
                boundsBuilder.include(latLng)
                isStoryLocationExist = true
            }
        }

        if (!isStoryLocationExist) return
        val bounds: LatLngBounds = boundsBuilder.build()
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }

    companion object {
        const val STORY_LOCATION_DATA = "story_location_data"
    }
}