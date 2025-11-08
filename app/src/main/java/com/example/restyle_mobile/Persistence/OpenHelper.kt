package Persistence
import Beans.Businesses
import Beans.Projects
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OpenHelper(context:Context): SQLiteOpenHelper(context, "projects.db", null, 1)  {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE projects (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "business_id INTEGER, contractor_id INTEGER, description TEXT, " +
                "finish_date TEXT, image TEXT, name TEXT, start_date TEXT)")


        db?.execSQL("CREATE TABLE businesses (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "address TEXT, city TEXT, description TEXT, " +
                "expertise TEXT, image TEXT, name TEXT, remodeler_id INTEGER)")

        // Insertar automáticamente dos valores en la tabla projects
        val project1 = ContentValues().apply {
            put("business_id", 1)
            put("contractor_id", 1)
            put("description", "Proyecto de construcción de oficina")
            put("finish_date", "2024-12-31")
            put("image", "https://images.squarespace-cdn.com/content/v1/5d0e69beefadae000134e1ee/1575923115022-24SLDAEGKCMJ6YFTQRCA/Bernharts-blog-plan-home-remodeling.jpg")
            put("name", "Construcción Oficina")
            put("start_date", "2024-01-01")
        }

        val project2 = ContentValues().apply {
            put("business_id", 2)
            put("contractor_id", 2)
            put("description", "Proyecto de renovación de vivienda")
            put("finish_date", "2024-11-30")
            put("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRwqV96MdL5YmBHyk-d_7sjh2dwvjSIMLSXAg&s")
            put("name", "Renovación Vivienda")
            put("start_date", "2024-02-15")
        }

        val business1 = ContentValues().apply {
            put("address", "123 Main St")
            put("city", "New York")
            put("description", "Especialistas en renovaciones residenciales, ofreciendo soluciones innovadoras y de alta calidad. Con más de 10 años de experiencia. Ubicados en el distrito de San Miguel en Lima, Perú.")
            put("expertise", "Home renovations")
            put("image", "https://renova-pro.ca/wp-content/uploads/2024/05/cropped-RENOVAPOR-2-1500x1000.png")
            put("name", "RenovaPro")
            put("remodeler_id", 1)
        }
        val business2 = ContentValues().apply {
            put("address", "456 Elm St")
            put("city", "Los Angeles")
            put("description", "Expert in commercial remodeling")
            put("expertise", "Office spaces")
            put("image", "https://media.licdn.com/dms/image/v2/C4E0BAQGRKqgwlprysg/company-logo_200_200/company-logo_200_200/0/1640188159717/pro_commercial_cleaning_inc_logo?e=2147483647&v=beta&t=_dkF0T2G6przXDb2c7b4S19MKswRHz9P7fD0qN461Lk")
            put("name", "Commercial Pro Inc.")
            put("remodeler_id", 1)
        }


        // Insertar los proyectos en la base de datos
        db?.insert("projects", null, project1)
        db?.insert("projects", null, project2)
        db?.insert("businesses", null, business1)
        db?.insert("businesses", null, business2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS projects")
        db?.execSQL("DROP TABLE IF EXISTS businesses")
        onCreate(db)
    }

    fun newProject(p: Projects) {
            val contentValues = ContentValues().apply {
                put("business_id", if (p.business_id != 0) p.business_id else 1)
                put("contractor_id",if (p.contractor_id != 0) p.contractor_id else 1)
                put("description", if (p.description.isNotEmpty()) p.description else "No description provided")
                put("finish_date", if (p.finish_date.isNotEmpty()) p.finish_date else "2025-01-15")
                put("image", if (p.image.isNullOrEmpty()) p.image else "https://www.budgetdumpster.com/images/blog/home-remodel-sketches-600x300.jpg")
                put("name", if (p.name.isNotEmpty()) p.name else "Unnamed Project")
                put("start_date", if (p.start_date.isNotEmpty()) p.start_date else "2024-09-15")
            }
            writableDatabase.insert("projects", null, contentValues)

    }

    fun getProjects():MutableList<Projects> {
        val db=this.readableDatabase
        val query=db.rawQuery("SELECT * FROM projects",null)
        val projects= mutableListOf<Projects>()
        if (query.moveToFirst()){
            do{
                projects.add(Projects(query.getInt(0),query.getInt(1),query.getInt(2),query.getString(3),query.getString(4),query.getString(5),query.getString(6),query.getString(7)))
            }while (query.moveToNext())
        }
        query.close()
        db.close()
        return projects
    }

    fun deleteProject(id:Int){
        writableDatabase.execSQL("DELETE FROM projects WHERE id = $id")
    }

    fun getProjectById(id:Int):Projects?{
        val db=this.readableDatabase
        val query=db.rawQuery("SELECT * FROM projects WHERE id = $id",null)

       return if (query.moveToFirst()){
            val project=Projects(query.getInt(0),query.getInt(1),query.getInt(2),query.getString(3),query.getString(4),query.getString(5),query.getString(6),query.getString(7))
            query.close()
            db.close()
            project
        }else{
            query.close()
            db.close()
            null
        }
    }
    fun getBusinessesById(id:Int):Businesses?{
        val db=this.readableDatabase
        val query=db.rawQuery("SELECT * FROM businesses WHERE id = $id",null)

        return if (query.moveToFirst()){
            val business=Businesses(query.getInt(0),query.getString(1),query.getString(2),query.getString(3),query.getString(4),query.getString(5),query.getString(6),query.getInt(7))
            query.close()
            db.close()
            business
        }else{
            query.close()
            db.close()
            null
        }
    }
    fun UpdateProject(p:Projects){
        val db=this.readableDatabase

        val cursor = db.rawQuery("SELECT * FROM projects WHERE id = ${p.id}",null)

        if (cursor.moveToFirst()) {
            val currentBusinessId = cursor.getInt(cursor.getColumnIndexOrThrow("business_id"))
            val currentContractorId = cursor.getInt(cursor.getColumnIndexOrThrow("contractor_id"))
            val currentDescription = cursor.getString(cursor.getColumnIndexOrThrow("description"))
            val currentFinishDate = cursor.getString(cursor.getColumnIndexOrThrow("finish_date"))
            val currentImage = cursor.getString(cursor.getColumnIndexOrThrow("image"))
            val currentName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val currentStartDate = cursor.getString(cursor.getColumnIndexOrThrow("start_date"))

            cursor.close()

            val businessId = if (p.business_id != 1) p.business_id else currentBusinessId
            val contractorId = if (p.contractor_id != 1) p.contractor_id else currentContractorId
            val description = if (p.description.isNotBlank()) p.description else currentDescription
            val finishDate = if (p.finish_date.isNotBlank()) p.finish_date else currentFinishDate
            val image = if (p.image.isNotBlank()) p.image else currentImage
            val name = if (p.name.isNotBlank()) p.name else currentName
            val startDate = if (p.start_date.isNotBlank()) p.start_date else currentStartDate

            writableDatabase.execSQL("UPDATE projects SET business_id=$businessId, contractor_id=$contractorId, description='$description', finish_date='$finishDate', image='$image', name='$name', start_date='$startDate' WHERE id=${p.id}")
        }else{
            cursor.close()
            throw Exception("Proyecto no encontrado con id: ${p.id}")
        }
    }
}