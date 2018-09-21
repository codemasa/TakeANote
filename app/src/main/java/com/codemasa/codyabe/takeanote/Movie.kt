package com.codemasa.codyabe.takeanote

import java.sql.Date


class Movie{

    var id: Int = 0
    var title: String = ""
    var director: String = ""
    var releaseDate: Int = 0

    constructor(title: String, director: String, releaseDate: Int) {
        this.title = title
        this.director = director
        this.releaseDate = releaseDate
    }
}