package com.example.restyle_mobile

import Beans.Projects
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.restyle_mobile.R

class ProjectsViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var projectId=view.findViewById<TextView>(R.id.textId)
    var projectName=view.findViewById<TextView>(R.id.txtcarNombre)
    var projectDescription=view.findViewById<TextView>(R.id.txtcarDescripción)
    var projectFoto=view.findViewById<ImageView>(R.id.txtcarImagen)

    fun render(projectModel: Projects){
        projectId.text=projectModel.id.toString()
        projectName.text=projectModel.name
        projectDescription.text=projectModel.description

        // Usando Glide para cargar la imagen desde la URL
        Glide.with(projectFoto.context)
            .load(projectModel.image) // URL de la imagen
            .placeholder(R.drawable.ic_launcher_background) // Imagen por defecto mientras carga
            .error(R.drawable.ic_launcher_foreground) // Imagen en caso de error
            .diskCacheStrategy(DiskCacheStrategy.ALL) // Opcional: Configura el caché de disco
            .into(projectFoto)
    }
}