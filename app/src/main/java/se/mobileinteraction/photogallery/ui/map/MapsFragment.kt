package se.mobileinteraction.photogallery.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.maps_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import se.mobileinteraction.photogallery.R
import se.mobileinteraction.photogallery.entities.UnsplashPhoto
import se.mobileinteraction.photogallery.utils.afterMeasure
import java.io.IOException
import java.util.*


const val REQUEST_CODE_LOCATION = 123

class MapsFragment : Fragment(R.layout.maps_fragment), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModel<MapsFragmentViewModel>()
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private val adapter: MapsAdapter = MapsAdapter({ isChecked, unsplashPhoto ->
        checkedChangeListener(
            isChecked,
            unsplashPhoto
        )
    },
        { searchWithQuery(it) },
        { viewModel.isFavourited(it) })

    private lateinit var mMap: GoogleMap
    lateinit var placesClient: PlacesClient
    private var countryName: String = ""
    private var myLocation: LatLng = LatLng(59.332320, 18.065252)
    private var fistTime: Boolean = false
    lateinit var locationButton: ImageView
    lateinit var mapFragment: SupportMapFragment


    var placeField = Arrays.asList(
        Place.Field.ID,
        Place.Field.NAME
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapFragment = childFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)


        setupPlacesAutocomplete()
        observeData()
        recyclerView.adapter = adapter
        container.isVisible = false
        recyclerView.afterMeasure {}
        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredGridLayoutManager.orientation = StaggeredGridLayoutManager.VERTICAL
        recyclerView.layoutManager = staggeredGridLayoutManager



        float_button.setOnClickListener {
            clickOnFloatingButton(countryName)
        }


        customMyLocationButton.setOnClickListener {
            //performClick() it's to click the locationButton when I click the listener up "customMyLocationButton"
            locationButton.performClick()
        }
    }


    private fun setupPlacesAutocomplete() {
        initPlaces()
        val autocompleteFragment = childFragmentManager
            .findFragmentById(R.id.place) as AutocompleteSupportFragment
        autocompleteFragment.setPlaceFields(placeField)
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                var location = place.toString()
                var addressList: List<Address>? = null
                val geoCoder = Geocoder(context)

                try {
                    addressList = geoCoder.getFromLocationName(location, 1)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
                val address = addressList!![0]
                val latLng = LatLng(address.latitude, address.longitude)
                mMap.addMarker(MarkerOptions().position(latLng).title(location))
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                location?.let { searchWithQuery(it) }
            }

            override fun onError(p0: Status) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    private fun initPlaces() {
        val API_Places_Key = "AIzaSyAyjZxDR6qrvTczm00Wdb5Zdo5MpcOta9E"
        context?.let { Places.initialize(it, API_Places_Key) }
        placesClient = Places.createClient(context!!)

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(context, R.raw.style_json))
        mMap.setOnMarkerClickListener(this)
        mMap.isMyLocationEnabled = true

        if (!fistTime) {
            mMap.animateCamera(CameraUpdateFactory.newLatLng(myLocation))
            fistTime = true
        }


        with(mMap.uiSettings) {
            isZoomControlsEnabled = false
            isMapToolbarEnabled = true
            isMyLocationButtonEnabled = true
        }

        locationButton =
            (mapFragment.view!!.findViewById<View>(Integer.parseInt("1")).parent as View)
                .findViewById(Integer.parseInt("2"))

        locationButton.visibility = View.GONE

        with(mMap) {
            setOnMapLongClickListener {
                val geocoder = Geocoder(context, Locale.getDefault())
                val address: List<Address> = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                countryName = address[0].countryName

                mMap.clear()
                mMap.addMarker(MarkerOptions().position(it).title(countryName))
                mMap.animateCamera(CameraUpdateFactory.newLatLng(it))
                float_button.isVisible = true
                searchWithQuery(countryName)

            }

            setOnMapClickListener {
                mMap.clear()
                recyclerView.isVisible = false
                float_button.isVisible = false
            }
        }
    }


    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(REQUEST_CODE_LOCATION)
    private fun enableMyLocation() {
        if (hasLocationPermission()) {
            mMap.isMyLocationEnabled = true
        } else {
            EasyPermissions.requestPermissions(
                this, getString(R.string.location),
                REQUEST_CODE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun hasLocationPermission(): Boolean {
        return EasyPermissions.hasPermissions(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
    }


    override fun onMarkerClick(p0: Marker?) = false

    private fun searchWithQuery(query: String) {
        recyclerView.isVisible = true
        if (query != "") {
            viewModel.searchUnsplash(query)
            recyclerView.afterMeasure {}
            container.isVisible = true
        }
    }

    private fun observeData() {
        viewModel.searchPhoto.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setData(it.results)

        })
    }

    internal fun clickOnFloatingButton(query: String) {
        findNavController().navigate(
            MapsFragmentDirections.actionMapsFragmentToSearchResultFragment(query, -1)
        )
    }

    private fun checkedChangeListener(checked: Boolean, photo: UnsplashPhoto) {
        if (checked) {
            viewModel.savePhotoAsFav(photo)
        } else {
            viewModel.deletePhotoFromFav(photo)
        }
    }


}





