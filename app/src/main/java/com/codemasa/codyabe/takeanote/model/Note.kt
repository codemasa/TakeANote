package com.codemasa.codyabe.takeanote.model

class Note {
    var noteBody: String = ""
    var category: String = ""
    var createdAt: Long = 0
    var title: String? = ""

    constructor(noteBody: String, category : String, title: String, createdAt: Long) {
        this.noteBody = noteBody
        this.category = category
        this.title = title
        this.createdAt = createdAt
    }
    constructor(){

    }

}