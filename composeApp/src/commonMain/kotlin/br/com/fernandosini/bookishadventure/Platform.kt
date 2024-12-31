package br.com.fernandosini.bookishadventure

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform