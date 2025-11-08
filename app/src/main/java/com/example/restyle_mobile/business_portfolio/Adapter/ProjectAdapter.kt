package Adapter

import Beans.Projects
import Persistence.OpenHelper
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.ProjectsViewHolder
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_portfolio.Repository.ProjectRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.app.AlertDialog
import android.content.DialogInterface

class ProjectAdapter(private val projects: MutableList<Projects>) : RecyclerView.Adapter<ProjectsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectsViewHolder {
        return ProjectsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card, parent, false))
    }

    override fun onBindViewHolder(holder: ProjectsViewHolder, position: Int) {
        val project = projects[position]
        holder.render(project)

        val deleteIcon = holder.itemView.findViewById<ImageView>(R.id.delete_icon)
        deleteIcon.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(holder.itemView.context)
            alertDialogBuilder.setTitle("Confirmar eliminación")
            alertDialogBuilder.setMessage("¿Está seguro de que desea eliminar este proyecto?")

            alertDialogBuilder.setPositiveButton("Eliminar") { dialog, which ->
                GlobalScope.launch(Dispatchers.IO) {
                    val dbHelper = OpenHelper(holder.itemView.context)
                    dbHelper.deleteProject(project.id) // Eliminar de la base de datos

                    withContext(Dispatchers.Main) {
                        ProjectRepository.deleteProject(project.id) // Eliminar del repositorio
                        notifyItemRemoved(position)
                        notifyItemRangeChanged(position, itemCount)
                        Toast.makeText(holder.itemView.context, "Proyecto eliminado", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            alertDialogBuilder.setNegativeButton("Cancelar") { dialog, which ->
            }

            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
        return projects.size
    }
}
