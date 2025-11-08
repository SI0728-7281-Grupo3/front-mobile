package Beans

class Businesses {

    var id: Int = 0
    var address: String = ""
    var city: String = ""
    var description: String = ""
    var expertise: String = ""
    var image: String = ""
    var name: String = ""
    var remodeler_id: Int = 0

    constructor(id: Int, address: String, city: String, description: String, expertise: String, image: String, name: String, remodeler_id: Int) {
        this.id = id
        this.address = address
        this.city = city
        this.description = description
        this.expertise = expertise
        this.image = image
        this.name = name
        this.remodeler_id = remodeler_id
    }
    constructor(address: String, city: String, description: String, expertise: String, image: String, name: String, remodeler_id: Int) {
        this.address = address
        this.city = city
        this.description = description
        this.expertise = expertise
        this.image = image
        this.name = name
        this.remodeler_id = remodeler_id
    }
}