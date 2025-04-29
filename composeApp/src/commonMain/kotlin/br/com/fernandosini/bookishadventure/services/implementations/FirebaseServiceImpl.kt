package br.com.fernandosini.bookishadventure.services.implementations

import br.com.fernandosini.bookishadventure.services.FirebaseService

class FirebaseServiceImpl : FirebaseService {
    override suspend fun initFirebase() {
        super.initFirebase()
    }

    override suspend fun logEvent(eventName: String, params: Map<String, Any>?) {
        super.logEvent(eventName, params)
    }
}