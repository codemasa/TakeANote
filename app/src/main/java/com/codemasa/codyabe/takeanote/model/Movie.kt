package com.codemasa.codyabe.takeanote.model

import java.sql.Date


class Movie{

    var id: Int = 0
    var title: String = ""
    var director: String = ""
    var releaseDate: Int = 0
    var imageURL : String = ""
    var favorite : Boolean = false

    constructor(title: String, director: String, releaseDate: Int, imageURL: String) {
        this.title = title
        this.director = director
        this.releaseDate = releaseDate
        this.imageURL = imageURL
    }
    constructor() {
    }
}