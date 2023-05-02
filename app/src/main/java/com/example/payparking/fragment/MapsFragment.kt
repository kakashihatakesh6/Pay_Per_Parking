package com.example.payparking.fragment

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.payparking.ForgetPassActivity
import com.example.payparking.R
import com.example.payparking.activity.PaymentActivity
import com.example.payparking.model.MarkerModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MapsFragment : Fragment(), OnMarkerClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var database: DatabaseReference

    companion object{
        private const val LOCATION_REQUEST_CODE = 1
    }

    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        mMap.setOnMarkerClickListener(this)

        setUpMap()

        mMap.isMyLocationEnabled


        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.style_json
                )
            )
            if (!success) {
                Log.e(ContentValues.TAG, "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e(ContentValues.TAG, "Can't find style. Error: ", e)
        }



    }





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_maps, container, false)

        database = Firebase.database.reference


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())



    }


    private fun setUpMap(){
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)

            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true
        fusedLocationProviderClient.lastLocation.addOnSuccessListener(requireActivity()) { location: Location->

            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                val gecLatLong = LatLng(22.135922222374198, 82.1296845522517)
                placeMarkerOnMap(currentLatLong)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gecLatLong, 12f))
            }

        }

    }

    private fun placeMarkerOnMap(currentLatLong: LatLng) {
        var markerOptions = MarkerOptions().position(currentLatLong)
        markerOptions.title("$currentLatLong")
        mMap.addMarker(markerOptions)

        val gecParking = LatLng(22.135922222374198, 82.1296845522517)
        val marker1 = mMap.addMarker(
            markerOptions
                .position(gecParking)
                .title("Gec Parking")
                .snippet("Parking Capacity: 120")
                .infoWindowAnchor(0.7F, 0.6F)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.beachflag))
                .anchor(0.5f, 0.5f)
                .rotation(90.0f)

        )

        marker1?.showInfoWindow()



        class InfoWindowActivity : AppCompatActivity(),
            GoogleMap.OnInfoWindowClickListener,
            OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                // Add markers to the map and do other map setup.
                // ...
                // Set a listener for info window events.
                googleMap.setOnInfoWindowClickListener(this)
            }

            override fun onInfoWindowClick(marker: Marker) {
                Toast.makeText(
                    this, "Info window clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        val riverViewGround = addMarkers(LatLng(22.130045473997757, 82.12531001523503),"River View Parking",80)
        val gecGround = addMarkers(LatLng(22.13291758012841, 82.12950142870089),"Gec Parking",60)
        val atalParking = addMarkers(LatLng(22.138918948074387, 82.12705605534242),"Atal Parking",300)
        val polyParking = addMarkers(LatLng(22.13598485115738, 82.12577388075385),"Polytechnic",200)
        val ggu1 = addMarkers(LatLng(22.127619687801133, 82.1323596691432),"GGU Parking 1",300)
        val ggu2 = addMarkers(LatLng(22.12869208660016, 82.1334990345605),"GGU Parking 2",300)
        val ggu3 = addMarkers(LatLng(22.12908628647869, 82.13249694208504),"GGU Parking 3",300)
        val ggu4 = addMarkers(LatLng(22.13030702751942, 82.13215833549356),"GGU Parking 4",300)
        val ggu5 = addMarkers(LatLng(22.13057557607492, 82.13435673457296),"GGU Parking 5",300)

    }

    private fun addMarkers(latLng: LatLng, title: String, cap: Int) {

        mMap.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
                .snippet("Parking Capacity: $cap")
                .infoWindowAnchor(0.7F, 0.6F)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
        )

        saveMarkerDetails(latLng, title, cap)

    }

    private fun saveMarkerDetails(latLng: LatLng, title: String, cap: Int) {
        val markerName = title
        val markerData = MarkerModel(latLng, title, cap)
        database.child("markers").child(markerName).setValue(markerData)

    }


    override fun onMarkerClick(marker: Marker): Boolean {

        marker.showInfoWindow()
        val markerTitle = marker.title
        val intent = Intent(requireActivity(), PaymentActivity::class.java)
        intent.putExtra("title",markerTitle)
        startActivity(intent)

        return true
    }


}



