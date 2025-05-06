package com.dojomovie.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.dojomovie.app.R
import com.dojomovie.app.activities.DetailFilmActivity
import com.dojomovie.app.adapters.FilmAdapter
import com.dojomovie.app.database.DatabaseHelper
import com.dojomovie.app.databinding.FragmentHomeBinding
import com.dojomovie.app.models.Film
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var filmAdapter: FilmAdapter
    private lateinit var requestQueue: RequestQueue
    private lateinit var googleMap: GoogleMap
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        databaseHelper = DatabaseHelper(requireContext())
        requestQueue = Volley.newRequestQueue(requireContext())
        
        setupRecyclerView()
        setupGoogleMaps()
        fetchFilmsFromAPI()
    }
    
    private fun setupRecyclerView() {
        filmAdapter = FilmAdapter(emptyList()) { film ->
            val intent = Intent(requireContext(), DetailFilmActivity::class.java)
            intent.putExtra("film_id", film.id)
            startActivity(intent)
        }
        
        binding.recyclerViewFilms.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = filmAdapter
        }
    }
    
    private fun setupGoogleMaps() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        
        // Set the location (Jakarta coordinates as specified in the requirements)
        val dojomovieLocation = LatLng(-6.2088, 106.8456)
        
        // Add marker
        googleMap.addMarker(
            MarkerOptions()
                .position(dojomovieLocation)
                .title("DoJo Movie")
        )
        
        // Move camera to the location
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dojomovieLocation, 15f))
    }
    
    private fun fetchFilmsFromAPI() {
        val url = "https://api.npoint.io/66cce8acb8f366d2a508"
        
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val films = mutableListOf<Film>()
                
                for (i in 0 until response.length()) {
                    val filmJson = response.getJSONObject(i)
                    val film = Film(
                        id = filmJson.getInt("id"),
                        title = filmJson.getString("title"),
                        price = filmJson.getDouble("price"),
                        cover = filmJson.getString("cover"),
                        description = filmJson.optString("description"),
                        genre = filmJson.optString("genre"),
                        duration = filmJson.optString("duration"),
                        rating = filmJson.optDouble("rating", 0.0)
                    )
                    
                    // Insert film into database
                    databaseHelper.insertFilm(film)
                    films.add(film)
                }
                
                // Update RecyclerView
                filmAdapter.updateFilms(films)
            },
            { error ->
                Toast.makeText(requireContext(), "Failed to fetch films: ${error.message}", Toast.LENGTH_SHORT).show()
                
                // Load films from database as fallback
                val filmsFromDb = databaseHelper.getAllFilms()
                filmAdapter.updateFilms(filmsFromDb)
            }
        )
        
        requestQueue.add(request)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}