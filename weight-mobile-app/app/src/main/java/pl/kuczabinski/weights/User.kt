package pl.kuczabinski.weights

class User {
    var name: String? = null
    var email: String
    var password: String

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    constructor(name: String, email: String, password: String) {
        this.name = name
        this.email = email
        this.password = password
    }
}