package Adapter

import Beans.Businesses
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.BussinessesViewHolder
import com.example.restyle_mobile.R

class BussinessesAdapter(val businesses: List<Businesses>) : RecyclerView.Adapter<BussinessesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BussinessesViewHolder {
        return BussinessesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.business_info, parent, false))
    }

    override fun onBindViewHolder(holder: BussinessesViewHolder, position: Int) {
        holder.render(businesses[position])
    }

    override fun getItemCount(): Int {
        return businesses.size
    }
}
