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
import org.w3c.dom.Text

class MyAdapter(private val myDataset: JSONArray) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        private val countryName: TextView = itemView.findViewById(R.id.countryName)
        private val countryFlag: ImageView = itemView.findViewById(R.id.countryFlag)
        private val newConfirmed: TextView = itemView.findViewById(R.id.newConfirmed)
        private val totalConfirmed: TextView = itemView.findViewById(R.id.totalConfirmed)
        private val newDeaths: TextView = itemView.findViewById(R.id.newDeaths)
        private val totalDeaths: TextView = itemView.findViewById(R.id.totalDeaths)
        private val newRecovered: TextView = itemView.findViewById(R.id.newRecovered)
        private val totalRecovered: TextView = itemView.findViewById(R.id.totalRecovered)
        private val date: TextView = itemView.findViewById(R.id.date)
        val expandapleView: ConstraintLayout = itemView.findViewById(R.id.expandableView)
        val arrowBtn: Button = itemView.findViewById(R.id.arrowBtn)

        fun bind(country: JSONObject) {
            val context: Context = countryFlag.context
            val id: Int = context.resources.getIdentifier(country["CountryCode"].toString().toLowerCase(), "drawable", context.packageName)

            countryName.text = country["Country"].toString()
            countryFlag.setImageResource(id)
            newConfirmed.text = "New Confirmed: " + country["NewConfirmed"].toString()
            totalConfirmed.text = "TotalConfirmed: " + country["TotalConfirmed"].toString()
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
        val country: JSONObject = myDataset.getJSONObject(position)
        holder.bind(country)

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