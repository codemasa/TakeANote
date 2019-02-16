package com.codemasa.codyabe.takeanote.model

import java.sql.Date


class Album{

    var id: Int = 0
    var title: String = ""
    var artist: String = ""
    var releaseDate: Int = 0
    var imageURL : String = ""
    var favorite : Boolean = false

    constructor(title: String, artist: String, releaseDate: Int, imageURL: String) {
        this.title = title
        this.artist = artist
        this.releaseDate = releaseDate
        this.imageURL = imageURL
    }
    constructor() {
    }
}