package mx.itson.cheemsworld

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import mx.itson.cheemsworld.entidades.Visita
import mx.itson.cheemsworld.utilerias.RetrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener{

    var mapa: GoogleMap? = null

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapaFragment = supportFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapaFragment.getMapAsync(this)
        obtenerVisitas()
        val btnVisita = findViewById<ImageButton>(R.id.agregarVisita)
        btnVisita.setOnClickListener {
            val estaPermitido = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val location = locationManager.getLastKnownLocation(locationManager.getBestProvider(Criteria(),true)!!)
            location?.let { onLocationChanged(it) } }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            mapa = googleMap
            mapa!!.mapType = GoogleMap.MAP_TYPE_HYBRID
            val estaPermitido = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

            if(estaPermitido) {
                googleMap.isMyLocationEnabled = true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            }






        } catch (ex: Exception) {

        }

    }

    fun obtenerVisitas() {
        val call: Call<List<Visita>> = RetrofitUtil.getApi()!!.getVisitas()
        call.enqueue(object : Callback<List<Visita>> {
            override fun onResponse(call: Call<List<Visita>>, response: Response<List<Visita>>) {
                val visitas : List<Visita> = response.body()!!
                mapa?.clear()
                for (v in visitas) {

                    val latlng = LatLng(v.latitud!!, v.longitud!!)
                    mapa!!.addMarker(MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cheems)))
                    mapa?.setOnMarkerClickListener(object: GoogleMap.OnMarkerClickListener {
                        override fun onMarkerClick(marker: Marker): Boolean {

                            val lugar = v.lugar
                            val motivo = v.motivo
                            val responsable = v.responsable
                            val intent = Intent(applicationContext,VerVisitaActivity::class.java)

                            intent.putExtra("lugar", lugar.toString() )
                            intent.putExtra("motivo",motivo.toString() )
                            intent.putExtra("responsable", responsable.toString())
                            startActivity(intent)
                            return false
                        }
                    })

                }

            }

            override fun onFailure(call: Call<List<Visita>>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }



    override fun onLocationChanged(location: Location) {
        val latitud : Double = location.latitude
        val longitud : Double = location.longitude
        val latLng = LatLng(latitud, longitud)

        mapa?.clear()
        mapa?.addMarker(MarkerOptions().position(latLng).draggable(true))
        mapa?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        mapa?.animateCamera(CameraUpdateFactory.zoomTo(8f))
        mapa?.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDrag(p0: Marker) {

            }

            override fun onMarkerDragEnd(marker: Marker) {
                val latLng = marker.position
                val intent = Intent(applicationContext,NuevaVisitaActivity::class.java)
                intent.putExtra("latitud" ,latLng.latitude)
                intent.putExtra("longitud" ,latLng.longitude)
                startActivity(intent)



            }

            override fun onMarkerDragStart(p0: Marker) {

            }
        })

    }

}