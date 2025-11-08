    package com.example.restyle_mobile.business_portfolio.Repository

    import Beans.Projects
    import Persistence.OpenHelper

    object ProjectRepository {
        val projects: MutableList<Projects> = mutableListOf()

        fun deleteProject(projectId: Int) {
            projects.removeIf { it.id == projectId }
        }
        // Ya no necesitas un método getProjects(), puedes acceder a ProjectRepository.projects directamente.
        fun loadInitialProjects(dbHelper: OpenHelper) {
                projects.clear()//
                projects.addAll(dbHelper.getProjects())
        }
    }
