package br.com.flemis.bookishadventure.features.maps.presentation.ui.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.MapState
import br.com.flemis.bookishadventure.features.maps.presentation.ui.states.rememberMapState
import kotlinx.datetime.format.Padding

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
@Target(
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER,
)
expect annotation class NativeMapComposable()

@Composable
expect fun NativeMapWidgetV2(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    mapState: MapState= rememberMapState(),

    content: @Composable @NativeMapComposable () -> Unit ={},


)

