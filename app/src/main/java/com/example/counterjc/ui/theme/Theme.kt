package com.example.counterjc.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
//private val DarkColorScheme = darkColorScheme(
//    primary = Green80,
//    onPrimary = Green20,
//    primaryContainer = Green30,
//    onPrimaryContainer = Green90,
//    inversePrimary = Green40,
//    secondary = DarkGreen80,
//    onSecondary = DarkGreen20,
//    secondaryContainer = DarkGreen30,
//    onSecondaryContainer = DarkGreen90,
//    tertiary = Violet80,
//    onTertiary = Violet20,
//    tertiaryContainer = Violet30,
//    onTertiaryContainer = Violet90,
//    error = Red80,
//    onError = Red20,
//    errorContainer = Red30,
//    onErrorContainer = Red90,
//    background = Grey10,
//    onBackground = Grey90,
//    surface = GreenGrey30,
//    onSurface = GreenGrey80,
//    inverseSurface = Grey90,
//    inverseOnSurface = Grey10,
//    surfaceVariant = GreenGrey30,
//    onSurfaceVariant = GreenGrey80,
//    outline = GreenGrey80
//)
//
//private val LightColorScheme = lightColorScheme(
//    primary = Green40,
//    onPrimary = Color.White,
//    primaryContainer = Green90,
//    onPrimaryContainer = Green10,
//    inversePrimary = Green80,
//    secondary = DarkGreen40,
//    onSecondary = Color.White,
//    secondaryContainer = DarkGreen90,
//    onSecondaryContainer = DarkGreen10,
//    tertiary = Violet40,
//    onTertiary = Color.White,
//    tertiaryContainer = Violet90,
//    onTertiaryContainer = Violet10,
//    error = Red40,
//    onError = Color.White,
//    errorContainer = Red90,
//    onErrorContainer = Red10,
//    background = Grey99,
//    onBackground = Grey10,
//    surface = GreenGrey90,
//    onSurface = GreenGrey30,
//    inverseSurface = Grey20,
//    inverseOnSurface = Grey95,
//    surfaceVariant = GreenGrey90,
//    onSurfaceVariant = GreenGrey30,
//    outline = GreenGrey50
//)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    onPrimary = Purple20,
    primaryContainer = Purple30,
    onPrimaryContainer = Purple90,
    inversePrimary = Purple40,
    secondary = DarkPurple80,
    onSecondary = DarkPurple20,
    secondaryContainer = DarkPurple30,
    onSecondaryContainer = DarkPurple90,
    tertiary = Green80,
    onTertiary = Green20,
    tertiaryContainer = Green30,
    onTertiaryContainer = Green90,
    error = Red80,
    onError = Red20,
    errorContainer = Red30,
    onErrorContainer = Red90,
    background = Grey10,
    onBackground = Grey90,
    surface = PurpleGrey30,
    onSurface = PurpleGrey80,
    inverseSurface = Grey90,
    inverseOnSurface = Grey10,
    surfaceVariant = PurpleGrey30,
    onSurfaceVariant = PurpleGrey80,
    outline = PurpleGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    onPrimary = Color.White,
    primaryContainer = Purple90,
    onPrimaryContainer = Purple10,
    inversePrimary = Purple80,
    secondary = DarkPurple40,
    onSecondary = Color.White,
    secondaryContainer = DarkPurple90,
    onSecondaryContainer = DarkPurple10,
    tertiary = Green40,
    onTertiary = Color.White,
    tertiaryContainer = Green90,
    onTertiaryContainer = Green10,
    error = Red40,
    onError = Color.White,
    errorContainer = Red90,
    onErrorContainer = Red10,
    background = Grey99,
    onBackground = Grey10,
    surface = PurpleGrey90,
    onSurface = PurpleGrey30,
    inverseSurface = Grey20,
    inverseOnSurface = Grey95,
    surfaceVariant = PurpleGrey90,
    onSurfaceVariant = PurpleGrey30,
    outline = PurpleGrey50
)

@Composable
fun CounterJCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}