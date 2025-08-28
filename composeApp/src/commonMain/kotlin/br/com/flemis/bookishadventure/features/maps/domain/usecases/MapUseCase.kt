package br.com.flemis.bookishadventure.features.maps.domain.usecases

import br.com.flemis.bookishadventure.features.maps.data.repository.MapRepositoryImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MapUseCase: KoinComponent {
    private val mapRepository: MapRepositoryImpl by inject()
}