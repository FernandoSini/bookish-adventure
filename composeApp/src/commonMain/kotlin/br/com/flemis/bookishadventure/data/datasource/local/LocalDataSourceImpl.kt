package br.com.flemis.bookishadventure.data.datasource.local

import br.com.flemis.bookishadventure.data.datasource.local.dao.models.UserModel
import br.com.flemis.bookishadventure.data.datasource.local.db.AppDatabase
import br.com.flemis.bookishadventure.utils.Preferences
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.getValue

class LocalDataSourceImpl : LocalDataSource, KoinComponent {
    private val appDatabase: AppDatabase by inject()
    private val _preferences: Preferences by inject()
    override suspend fun getUserFromLocal(userID: Long): UserModel? {
        val data = appDatabase.getUserDao().getUserByID(userID)
        return data ?: null
    }

    override suspend fun saveUser(user: UserModel) {
        appDatabase.getUserDao().saveUser(user)
        _preferences.putLong("userId", user.id)
        _preferences.putLong("userId", user.id)

    }

    override suspend fun getCurrentUser(): UserModel {
       return appDatabase.getUserDao().getCurrentUser()
    }
}