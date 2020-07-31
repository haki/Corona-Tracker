package com.example.coronatracker

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewManager = LinearLayoutManager(this)

        val queue = Volley.newRequestQueue(this)
        val url = "https://api.covid19api.com/summary"
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
                val countries = response.getJSONArray("Countries")
                viewAdapter = MyAdapter(countries)
                recyclerView = findViewById<RecyclerView>(R.id.recview).apply {
                    setHasFixedSize(true)
                    layoutManager = viewManager
                    adapter = viewAdapter
                }
            },
                Response.ErrorListener { error -> Log.d("JSON", error.toString()) }
            )

        queue.add(jsonObjectRequest)
    }
}