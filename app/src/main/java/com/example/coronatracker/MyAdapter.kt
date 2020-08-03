package com.example.coronatracker

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class MyAdapter(private val myDataset: JSONArray, private val global: JSONObject) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val countryName: TextView = itemView.findViewById(R.id.countryName)
        val countryFlag: ImageView = itemView.findViewById(R.id.countryFlag)
        val newConfirmed: TextView = itemView.findViewById(R.id.newConfirmed)
        val totalConfirmed: TextView = itemView.findViewById(R.id.totalConfirmed)
        val newDeaths: TextView = itemView.findViewById(R.id.newDeaths)
        val totalDeaths: TextView = itemView.findViewById(R.id.totalDeaths)
        val newRecovered: TextView = itemView.findViewById(R.id.newRecovered)
        val totalRecovered: TextView = itemView.findViewById(R.id.totalRecovered)
        val date: TextView = itemView.findViewById(R.id.date)
        val expandapleView: ConstraintLayout = itemView.findViewById(R.id.expandableView)
        val arrowBtn: Button = itemView.findViewById(R.id.arrowBtn)

        fun bind(country: JSONObject) {
            val context: Context = countryFlag.context
            val id: Int = context.resources.getIdentifier(country["CountryCode"].toString().toLowerCase(), "drawable", context.packageName)

            countryName.text = country["Country"].toString()
            countryFlag.setImageResource(id)
            newConfirmed.text = "New Confirmed: " + country["NewConfirmed"].toString()
            totalConfirmed.text = "Total Confirmed: " + country["TotalConfirmed"].toString()
            newDeaths.text = "New Deaths: " + country["NewDeaths"].toString()
            totalDeaths.text = "Total Deaths: " + country["TotalDeaths"].toString()
            newRecovered.text = "New Recovered: " + country["NewRecovered"].toString()
            totalRecovered.text = "Total Recovered: " + country["TotalRecovered"].toString()
            date.text = "Date: " + country["Date"].toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_corona_card, parent, false)

        return MyViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if (position == 0) {
            holder.countryName.text = "Global"
            holder.countryFlag.setImageResource(R.drawable.globe)
            holder.newConfirmed.text = "New Confirmed: " + global["NewConfirmed"].toString()
            holder.totalConfirmed.text = "Total Confirmed: " + global["TotalConfirmed"].toString()
            holder.newDeaths.text = "New Deaths: " + global["NewDeaths"].toString()
            holder.totalDeaths.text = "Total Deaths: " + global["TotalDeaths"].toString()
            holder.newRecovered.text = "New Recovered: " + global["NewRecovered"].toString()
            holder.totalRecovered.text = "Total Recovered: " + global["TotalRecovered"].toString()
            holder.date.text = "Date: " + LocalDate.now()
        } else {
            val country: JSONObject = myDataset.getJSONObject(position)
            holder.bind(country)
        }

        holder.arrowBtn.setOnClickListener {
            TransitionManager.beginDelayedTransition(holder.expandapleView, AutoTransition())
            if (holder.expandapleView.visibility == View.GONE) {
                holder.expandapleView.visibility = View.VISIBLE
                holder.arrowBtn.setBackgroundResource(R.drawable.arrow_up)
            } else {
                holder.expandapleView.visibility = View.GONE
                holder.arrowBtn.setBackgroundResource(R.drawable.arrow_down)
            }
        }
    }

    override fun getItemCount(): Int {
        return myDataset.length()
    }
}