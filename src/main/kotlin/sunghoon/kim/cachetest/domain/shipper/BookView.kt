package sunghoon.kim.cachetest.domain.shipper

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class BookView {

    constructor()

    constructor(id: String, name: String) {
        this.id = id
        this.name = name
    }

    @Id
    lateinit var id: String

    var name: String = ""
}