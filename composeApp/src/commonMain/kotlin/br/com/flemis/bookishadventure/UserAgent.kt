package br.com.flemis.bookishadventure


expect class UserAgent {
    fun getUserAgent():String
}

expect fun createUserAgentInstance(): UserAgent