package Beans

class Business{

    var id: Int
    var name: String
    var description: String
    var address: String
    var city: String
    var expertise: String
    var image: String
    var remodelerId: Int

    constructor(id: Int, name: String, description: String, address: String, city: String, expertise: String, image: String, remodelerId: Int) {
        this.id = id
        this.name = name
        this.description = description
        this.address = address
        this.city = city
        this.expertise = expertise
        this.image = image
        this.remodelerId = remodelerId
    }
}