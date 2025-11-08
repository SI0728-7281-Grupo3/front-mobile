package com.example.restyle_mobile.business_search.Adapter

import Beans.Business
import Beans.Businesses
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R
import com.squareup.picasso.Picasso

class BusinessAdapter(
    private var businessList: List<Businesses>,
    private val onItemClick: (Businesses) -> Unit
):RecyclerView.Adapter<CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return CardViewHolder(layoutInflater.inflate(R.layout.business_card, parent, false))
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val business = businessList[position]
        holder.render(business)

        holder.itemView.setOnClickListener {
            onItemClick(business)
        }
    }

    override fun getItemCount(): Int {
        return businessList.size
    }
}

class CardViewHolder(view: View):RecyclerView.ViewHolder(view) {

    val businessName = view.findViewById<TextView>(R.id.businessCardName)
    val businessExpertise = view.findViewById<TextView>(R.id.businessCardExpertise)
    val businessDescription = view.findViewById<TextView>(R.id.businessCardDescription)
    val businessImage = view.findViewById<ImageView>(R.id.businessCardImage)

    fun render(businessModel: Businesses){
        businessName.text = businessModel.name
        businessExpertise.text = businessModel.expertise
        businessDescription.text = businessModel.description
        Picasso.get().load(businessModel.image)
            .resize(300, 300)
            .centerCrop()
            .into(businessImage)
    }
}