package Fragment

import Adapter.ProjectAdapter
import Persistence.OpenHelper
import Persistence.UserHelper
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restyle_mobile.R
import com.example.restyle_mobile.business_portfolio.Repository.ProjectRepository

class ProjectsFragment : Fragment() {
    lateinit var dbHelper: OpenHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.projects_info, container, false)
        dbHelper = OpenHelper(requireContext())

        // Cargar proyectos en ProjectRepository si aún no están cargados
        if (ProjectRepository.projects.isEmpty()) {
            ProjectRepository.loadInitialProjects(dbHelper)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvProjects)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ProjectAdapter(ProjectRepository.projects)

        return view
    }
}
