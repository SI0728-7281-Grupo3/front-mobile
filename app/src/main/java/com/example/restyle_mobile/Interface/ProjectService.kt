package Interface

import Beans.Project
import retrofit2.Call
import retrofit2.http.GET

interface ProjectService {

    @GET("project")
    fun getProjects(): Call<List<Project>>
}