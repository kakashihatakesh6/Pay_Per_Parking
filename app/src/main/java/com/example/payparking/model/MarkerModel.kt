package com.example.payparking.model

import com.google.android.gms.maps.model.LatLng

data class MarkerModel(
    val latLng: LatLng? = null,
    val title: String? = null,
    val cap: Int? = null
)
