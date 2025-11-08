package Beans

class Project {

    var id: Int = 0
    var name: String
    var description: String
    var businessId: Int
    var contractorId: Int
    var startDate: String
    var finishDate: String
    var image: String

    constructor(name: String, description: String, businessId: Int, contractorId: Int, startDate: String, finishDate: String, image: String) {
        this.name = name
        this.description = description
        this.businessId = businessId
        this.contractorId = contractorId
        this.startDate = startDate
        this.finishDate = finishDate
        this.image = image
    }
}