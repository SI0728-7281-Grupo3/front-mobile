package Beans

class Projects {

    var id: Int = 0
    var business_id: Int = 0
    var contractor_id: Int = 0
    var description: String = ""
    var finish_date: String = ""
    var image: String = ""
    var name: String = ""
    var start_date: String = ""

    constructor(id: Int, business_id: Int, contractor_id: Int, description: String, finish_date: String, image: String, name: String, start_date: String) {
        this.id = id
        this.business_id = business_id
        this.contractor_id = contractor_id
        this.description = description
        this.finish_date = finish_date
        this.image = image
        this.name = name
        this.start_date = start_date
    }
    constructor(business_id: Int, contractor_id: Int, description: String, finish_date: String, image: String, name: String, start_date: String) {
        this.business_id = business_id
        this.contractor_id = contractor_id
        this.description = description
        this.finish_date = finish_date
        this.image = image
        this.name = name
        this.start_date = start_date
    }
    constructor(description: String,image: String, name: String)
    {
        this.description = description
        this.image = image
        this.name = name
    }
}