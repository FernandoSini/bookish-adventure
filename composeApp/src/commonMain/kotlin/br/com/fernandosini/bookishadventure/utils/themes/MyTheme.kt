package br.com.fernandosini.bookishadventure.utils.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.DMSans_Light
import bookishadventure.composeapp.generated.resources.DMSans_Regular
import bookishadventure.composeapp.generated.resources.DMSans_SemiBold
import bookishadventure.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font


private val darkColorPalette = darkColorScheme(
    background = Color(0xff1d1d24),
    primaryContainer = Color(0xff2B2A38),
    secondaryContainer = Color(0xff1E1E1E),
    inversePrimary = Color(0xffA3A3A0),
    error = Color(0xffE64630),// ou então Color.Red,
    onError = Color.Red,
    surfaceTint = Color.White,
    surfaceContainer = Color(0xffC6E2FF),
    outlineVariant = Color.LightGray/*
            primary = Purple200,
            secondary = Teal200,
            tertiary = Pink80,*/
)
private val lightColorPalette = lightColorScheme(
    background = Color.White,
    primaryContainer = Color(0xffC2C1D1),
    inversePrimary = Color(0xffA3A3A0),
    error = Color(0xffE64630),
    surfaceTint = Color.Black,
    surfaceContainer = Color(0xffC6E2FF),
    onError = Color.Red,
    outlineVariant = Color.LightGray,
    /*  primary = Purple500,
              secondary = Teal200,
              tertiary = Pink40*/
)

@Composable
private fun lightTypography() = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(resource = Res.font.DMSans_Bold)),
        fontSize = 50.sp,
        color = Color.Black
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp, fontFamily = FontFamily(
            Font(Res.font.DMSans_Bold)
        ), color = Color.Black
    ),
    titleMedium = TextStyle(
        color = Color.Black,
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
    ),
    headlineSmall = TextStyle(
        color = Color.Black,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold))
    ),
    headlineMedium = TextStyle(
        color = Color.Black,
        fontSize = 30.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
    ),
    bodyLarge = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily(Font(Res.font.DMSans_Light)),
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily(Font(Res.font.DMSans_Light)),
        fontSize = 15.sp
    ),
    bodySmall = TextStyle(
        color = Color.Black,
        fontFamily = FontFamily(Font(Res.font.DMSans_Regular)),
        fontSize = 13.sp
    ),
)

@Composable
private fun darkTypography() = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(Res.font.DMSans_Bold)),
        fontSize = 50.sp, color = Color.White
    ),
    titleSmall = TextStyle(
        fontSize = 14.sp, fontFamily = FontFamily(
            Font(Res.font.DMSans_Bold)
        ), color = Color.White
    ),
    titleMedium = TextStyle(
        color = Color.White,
        fontSize = 15.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold)),
    ),
    headlineSmall = TextStyle(
        color = Color.White,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_SemiBold))
    ),
    headlineMedium = TextStyle(
        color = Color.White,
        fontSize = 30.sp,
        fontFamily = FontFamily(Font(Res.font.DMSans_Bold))
    ),
    bodyLarge = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(Res.font.DMSans_Light)),
        fontSize = 18.sp
    ),
    bodyMedium = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(Res.font.DMSans_Regular)),
        fontSize = 15.sp
    ),
    bodySmall = TextStyle(
        color = Color.White,
        fontFamily = FontFamily(Font(Res.font.DMSans_Regular)),
        fontSize = 13.sp
    ),
)

@Composable
fun MyCustomTheme(isDarkTheme: Boolean, content: @Composable () -> Unit) {
    val colors = if (isDarkTheme) darkColorPalette else lightColorPalette
    val typos = if (isDarkTheme) darkTypography() else lightTypography()


    MaterialTheme(colorScheme = colors, content = content, typography = typos)
}

