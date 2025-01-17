package br.com.fernandosini.bookishadventure.models

class Address(

    val state:String,
    val city:String,
    val street:String,

    val latitude: Double,
    val longitude: Double
) {
    override fun toString(): String {
        return "PlaceLocation(latitude=$latitude, longitude=$longitude)"
    }
}