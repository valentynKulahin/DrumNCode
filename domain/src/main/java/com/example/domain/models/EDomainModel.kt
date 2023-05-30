package com.example.domain.models

data class EDomainModel(
    val d: String,     //who play
    val i: Long,     //match id
    val si: String,    //sport id
    val sh: String,    //name
    val tt: Long,     //unix time
    var favourite: Boolean = false
)
