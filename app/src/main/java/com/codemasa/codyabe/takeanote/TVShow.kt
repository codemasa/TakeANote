package com.codemasa.codyabe.takeanote

import java.sql.Date


class TVShow{

    var id: Int = 0
    var title: String = ""
    var releaseDate: Int = 0
    var imageURL : String = ""
    var season: Int = 0
    var favorite : Boolean = false

    constructor(title: String, season: Int, releaseDate: Int, imageURL: String) {
        this.title = title
        this.season = season
        this.releaseDate = releaseDate
        this.imageURL = imageURL
    }
    constructor() {
    }
}