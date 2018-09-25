package com.codemasa.codyabe.takeanote

import java.sql.Date


class TVShow{

    var id: Int = 0
    var title: String = ""
    var releaseDate: Int = 0
    var imageURL : String = ""

    constructor(title: String, releaseDate: Int) {
        this.title = title
        this.releaseDate = releaseDate
    }
    constructor() {
    }
}