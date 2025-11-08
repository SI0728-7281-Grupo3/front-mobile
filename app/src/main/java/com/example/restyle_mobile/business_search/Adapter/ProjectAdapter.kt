package com.example.restyle_mobile.business_search.Adapter

import Beans.Project
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R
import com.squareup.picasso.Picasso

class ProjectAdapter(private var projectList: List<Project>): RecyclerView.Adapter<ProjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        return ProjectViewHolder(layoutInflater.inflate(R.layout.project_card, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.render(projectList[position])
    }

    override fun getItemCount(): Int {
        return projectList.size
    }
}

class ProjectViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val projectName = view.findViewById<TextView>(R.id.projectCardName)
    val projectImage = view.findViewById<ImageView>(R.id.projectCardImage)

    fun render(projectModel: Project) {
        projectName.text = projectModel.name
        Picasso.get().load(projectModel.image)
            .resize(300, 300)
            .centerCrop()
            .into(projectImage)
    }
}