package com.codemasa.codyabe.takeanote

import java.sql.Date


class Album{

    var id: Int = 0
    var title: String = ""
    var artist: String = ""
    var releaseDate: Int = 0
    var imageURL : String = ""
    var favorite : Boolean = false

    constructor(title: String, artist: String, releaseDate: Int) {
        this.title = title
        this.artist = artist
        this.releaseDate = releaseDate
    }
    constructor() {
    }
}