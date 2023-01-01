package com.example.luck_rent_android.ui.main.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityLocationSelectionBinding
import com.example.luck_rent_android.ui.main.adds.MakeAdd
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.renter.Renter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kodextech.project.kodexlib.base.BaseActivity

class LocationSelection : BaseActivity(), OnMapReadyCallback {
    var country: String? = null
    var location: String? = null
    var city: String? = null
    private lateinit var mMap: GoogleMap


    private var binding: ActivityLocationSelectionBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_selection)
        super.onCreate(savedInstanceState)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding?.nextBtn?.setOnClickListener {
            val intent = Intent(this, NewPassword::class.java)
            intent.putExtra("isFrom", "newPassword")
            startActivity(intent)
            finish()
        }

        setSpinnerValues()
    }

    private fun setSpinnerValues() {
        binding?.spCountry?.setItems(
            "Canada",
            "USA",
        )
        binding?.spCountry?.setOnItemSelectedListener { view, position, id, item ->
            country = binding?.spCountry?.text.toString()
        }
        binding?.spLocation?.setItems(
            "BC",
            "NY",
        )
        binding?.spLocation?.setOnItemSelectedListener { view, position, id, item ->
            location = binding?.spCountry?.text.toString()
        }

        binding?.spCity?.setItems(
            "Vancouver",
            "New York City",
        )

        binding?.spCity?.setOnItemSelectedListener { view, position, id, item ->
            city = binding?.spCountry?.text.toString()
        }

    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.content
    }

    override fun setupContentViewWithBinding() {

    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    override fun setupLoader() {

    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(
            MarkerOptions()
            .position(sydney)
            .title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}